package org.jivesoftware.smack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.RosterStorage;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.util.StringUtils;

public class Roster {

   private static Roster.SubscriptionMode defaultSubscriptionMode = Roster.SubscriptionMode.accept_all;
   private Connection connection;
   private final Map<String, RosterEntry> entries;
   private final Map<String, RosterGroup> groups;
   private RosterStorage persistentStorage;
   private Map<String, Map<String, Presence>> presenceMap;
   private Roster.PresencePacketListener presencePacketListener;
   private String requestPacketId;
   boolean rosterInitialized;
   private final List<RosterListener> rosterListeners;
   private Roster.SubscriptionMode subscriptionMode;
   private final List<RosterEntry> unfiledEntries;


   Roster(Connection var1) {
      this.rosterInitialized = (boolean)0;
      Roster.SubscriptionMode var2 = getDefaultSubscriptionMode();
      this.subscriptionMode = var2;
      this.connection = var1;
      if(!var1.getConfiguration().isRosterVersioningAvailable()) {
         this.persistentStorage = null;
      }

      ConcurrentHashMap var3 = new ConcurrentHashMap();
      this.groups = var3;
      CopyOnWriteArrayList var4 = new CopyOnWriteArrayList();
      this.unfiledEntries = var4;
      ConcurrentHashMap var5 = new ConcurrentHashMap();
      this.entries = var5;
      CopyOnWriteArrayList var6 = new CopyOnWriteArrayList();
      this.rosterListeners = var6;
      ConcurrentHashMap var7 = new ConcurrentHashMap();
      this.presenceMap = var7;
      PacketTypeFilter var8 = new PacketTypeFilter(RosterPacket.class);
      Roster.RosterPacketListener var9 = new Roster.RosterPacketListener((Roster.1)null);
      var1.addPacketListener(var9, var8);
      PacketTypeFilter var10 = new PacketTypeFilter(Presence.class);
      Roster.PresencePacketListener var11 = new Roster.PresencePacketListener((Roster.1)null);
      this.presencePacketListener = var11;
      Roster.PresencePacketListener var12 = this.presencePacketListener;
      var1.addPacketListener(var12, var10);
      Roster.1 var13 = new Roster.1();
      var1.addConnectionListener(var13);
   }

   Roster(Connection var1, RosterStorage var2) {
      this(var1);
      this.persistentStorage = var2;
   }

   // $FF: synthetic method
   static RosterStorage access$1000(Roster var0) {
      return var0.persistentStorage;
   }

   // $FF: synthetic method
   static RosterStorage access$1002(Roster var0, RosterStorage var1) {
      var0.persistentStorage = var1;
      return var1;
   }

   // $FF: synthetic method
   static void access$1100(Roster var0, RosterPacket.Item var1, Collection var2, Collection var3, Collection var4) {
      var0.insertRosterItem(var1, var2, var3, var4);
   }

   // $FF: synthetic method
   static void access$1200(Roster var0, Collection var1, Collection var2, Collection var3) {
      var0.fireRosterChangedEvent(var1, var2, var3);
   }

   private void fireRosterChangedEvent(Collection<String> var1, Collection<String> var2, Collection<String> var3) {
      Iterator var4 = this.rosterListeners.iterator();

      while(var4.hasNext()) {
         RosterListener var5 = (RosterListener)var4.next();
         if(!var1.isEmpty()) {
            var5.entriesAdded(var1);
         }

         if(!var2.isEmpty()) {
            var5.entriesUpdated(var2);
         }

         if(!var3.isEmpty()) {
            var5.entriesDeleted(var3);
         }
      }

   }

   private void fireRosterPresenceEvent(Presence var1) {
      Iterator var2 = this.rosterListeners.iterator();

      while(var2.hasNext()) {
         ((RosterListener)var2.next()).presenceChanged(var1);
      }

   }

   public static Roster.SubscriptionMode getDefaultSubscriptionMode() {
      return defaultSubscriptionMode;
   }

   private String getPresenceMapKey(String var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         String var3;
         if(!this.contains(var1)) {
            var3 = StringUtils.parseBareAddress(var1);
         } else {
            var3 = var1;
         }

         var2 = var3.toLowerCase();
      }

      return var2;
   }

   private void insertRosterItem(RosterPacket.Item var1, Collection<String> var2, Collection<String> var3, Collection<String> var4) {
      String var5 = var1.getUser();
      String var6 = var1.getName();
      RosterPacket.ItemType var7 = var1.getItemType();
      RosterPacket.ItemStatus var8 = var1.getItemStatus();
      Connection var9 = this.connection;
      RosterEntry var11 = new RosterEntry(var5, var6, var7, var8, this, var9);
      RosterPacket.ItemType var12 = RosterPacket.ItemType.remove;
      RosterPacket.ItemType var13 = var1.getItemType();
      if(var12.equals(var13)) {
         Map var14 = this.entries;
         String var15 = var1.getUser();
         if(var14.containsKey(var15)) {
            Map var16 = this.entries;
            String var17 = var1.getUser();
            var16.remove(var17);
         }

         if(this.unfiledEntries.contains(var11)) {
            this.unfiledEntries.remove(var11);
         }

         StringBuilder var20 = new StringBuilder();
         String var21 = StringUtils.parseName(var1.getUser());
         StringBuilder var22 = var20.append(var21).append("@");
         String var23 = StringUtils.parseServer(var1.getUser());
         String var24 = var22.append(var23).toString();
         this.presenceMap.remove(var24);
         if(var4 != null) {
            String var26 = var1.getUser();
            var4.add(var26);
         }
      } else {
         Map var33 = this.entries;
         String var34 = var1.getUser();
         if(!var33.containsKey(var34)) {
            Map var35 = this.entries;
            String var36 = var1.getUser();
            var35.put(var36, var11);
            if(var2 != null) {
               String var38 = var1.getUser();
               var2.add(var38);
            }
         } else {
            Map var41 = this.entries;
            String var42 = var1.getUser();
            var41.put(var42, var11);
            if(var3 != null) {
               String var44 = var1.getUser();
               var3.add(var44);
            }
         }

         if(!var1.getGroupNames().isEmpty()) {
            this.unfiledEntries.remove(var11);
         } else if(!this.unfiledEntries.contains(var11)) {
            this.unfiledEntries.add(var11);
         }
      }

      ArrayList var28 = new ArrayList();
      Iterator var29 = this.getGroups().iterator();

      while(var29.hasNext()) {
         RosterGroup var30 = (RosterGroup)var29.next();
         if(var30.contains(var11)) {
            String var31 = var30.getName();
            var28.add(var31);
         }
      }

      RosterPacket.ItemType var47 = RosterPacket.ItemType.remove;
      RosterPacket.ItemType var48 = var1.getItemType();
      if(!var47.equals(var48)) {
         ArrayList var49 = new ArrayList();

         RosterGroup var53;
         for(Iterator var50 = var1.getGroupNames().iterator(); var50.hasNext(); var53.addEntryLocal(var11)) {
            String var51 = (String)var50.next();
            var49.add(var51);
            var53 = this.getGroup(var51);
            if(var53 == null) {
               RosterGroup var54 = this.createGroup(var51);
               this.groups.put(var51, var54);
            }
         }

         Iterator var56 = var49.iterator();

         while(var56.hasNext()) {
            String var57 = (String)var56.next();
            var28.remove(var57);
         }
      }

      Iterator var59 = var28.iterator();

      while(var59.hasNext()) {
         String var60 = (String)var59.next();
         RosterGroup var61 = this.getGroup(var60);
         var61.removeEntryLocal(var11);
         if(var61.getEntryCount() == 0) {
            this.groups.remove(var60);
         }
      }

      Iterator var63 = this.getGroups().iterator();

      while(var63.hasNext()) {
         RosterGroup var64 = (RosterGroup)var63.next();
         if(var64.getEntryCount() == 0) {
            Map var65 = this.groups;
            String var66 = var64.getName();
            var65.remove(var66);
         }
      }

   }

   private void insertRosterItems(List<RosterPacket.Item> var1) {
      ArrayList var2 = new ArrayList();
      ArrayList var3 = new ArrayList();
      ArrayList var4 = new ArrayList();
      Iterator var5 = var1.iterator();

      while(var5.hasNext()) {
         RosterPacket.Item var6 = (RosterPacket.Item)var5.next();
         this.insertRosterItem(var6, var2, var3, var4);
      }

      this.fireRosterChangedEvent(var2, var3, var4);
   }

   public static void setDefaultSubscriptionMode(Roster.SubscriptionMode var0) {
      defaultSubscriptionMode = var0;
   }

   private void setOfflinePresences() {
      Iterator var1 = this.presenceMap.keySet().iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         Map var3 = (Map)this.presenceMap.get(var2);
         if(var3 != null) {
            Iterator var4 = var3.keySet().iterator();

            while(var4.hasNext()) {
               String var5 = (String)var4.next();
               Presence.Type var6 = Presence.Type.unavailable;
               Presence var7 = new Presence(var6);
               String var8 = var2 + "/" + var5;
               var7.setFrom(var8);
               this.presencePacketListener.processPacket(var7);
            }
         }
      }

   }

   public void addRosterListener(RosterListener var1) {
      if(!this.rosterListeners.contains(var1)) {
         this.rosterListeners.add(var1);
      }
   }

   void cleanup() {
      this.rosterListeners.clear();
   }

   public boolean contains(String var1) {
      boolean var2;
      if(this.getEntry(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void createEntry(String var1, String var2, String[] var3) throws XMPPException {
      RosterPacket var4 = new RosterPacket();
      IQ.Type var5 = IQ.Type.SET;
      var4.setType(var5);
      RosterPacket.Item var6 = new RosterPacket.Item(var1, var2);
      if(var3 != null) {
         int var7 = var3.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String var9 = var3[var8];
            if(var9 != null && var9.trim().length() > 0) {
               var6.addGroupName(var9);
            }
         }
      }

      var4.addRosterItem(var6);
      Connection var10 = this.connection;
      String var11 = var4.getPacketID();
      PacketIDFilter var12 = new PacketIDFilter(var11);
      PacketCollector var13 = var10.createPacketCollector(var12);
      this.connection.sendPacket(var4);
      long var14 = (long)SmackConfiguration.getPacketReplyTimeout();
      IQ var16 = (IQ)var13.nextResult(var14);
      var13.cancel();
      if(var16 == null) {
         throw new XMPPException("No response from the server.");
      } else {
         IQ.Type var17 = var16.getType();
         IQ.Type var18 = IQ.Type.ERROR;
         if(var17 == var18) {
            XMPPError var19 = var16.getError();
            throw new XMPPException(var19);
         } else {
            Presence.Type var20 = Presence.Type.subscribe;
            Presence var21 = new Presence(var20);
            var21.setTo(var1);
            this.connection.sendPacket(var21);
         }
      }
   }

   public RosterGroup createGroup(String var1) {
      if(this.groups.containsKey(var1)) {
         String var2 = "Group with name " + var1 + " alread exists.";
         throw new IllegalArgumentException(var2);
      } else {
         Connection var3 = this.connection;
         RosterGroup var4 = new RosterGroup(var1, var3);
         this.groups.put(var1, var4);
         return var4;
      }
   }

   public Collection<RosterEntry> getEntries() {
      HashSet var1 = new HashSet();
      Iterator var2 = this.getGroups().iterator();

      while(var2.hasNext()) {
         Collection var3 = ((RosterGroup)var2.next()).getEntries();
         var1.addAll(var3);
      }

      List var5 = this.unfiledEntries;
      var1.addAll(var5);
      return Collections.unmodifiableCollection(var1);
   }

   public RosterEntry getEntry(String var1) {
      RosterEntry var2;
      if(var1 == null) {
         var2 = null;
      } else {
         Map var3 = this.entries;
         String var4 = var1.toLowerCase();
         var2 = (RosterEntry)var3.get(var4);
      }

      return var2;
   }

   public int getEntryCount() {
      return this.getEntries().size();
   }

   public RosterGroup getGroup(String var1) {
      return (RosterGroup)this.groups.get(var1);
   }

   public int getGroupCount() {
      return this.groups.size();
   }

   public Collection<RosterGroup> getGroups() {
      return Collections.unmodifiableCollection(this.groups.values());
   }

   public Presence getPresence(String var1) {
      String var2 = StringUtils.parseBareAddress(var1);
      String var3 = this.getPresenceMapKey(var2);
      Map var4 = (Map)this.presenceMap.get(var3);
      Presence var6;
      if(var4 == null) {
         Presence.Type var5 = Presence.Type.unavailable;
         var6 = new Presence(var5);
         var6.setFrom(var1);
      } else {
         Iterator var7 = var4.keySet().iterator();
         Presence var8 = null;

         while(var7.hasNext()) {
            String var9 = (String)var7.next();
            Presence var10 = (Presence)var4.get(var9);
            if(var10.isAvailable()) {
               if(var8 != null) {
                  int var11 = var10.getPriority();
                  int var12 = var8.getPriority();
                  if(var11 <= var12) {
                     label37: {
                        int var13 = var10.getPriority();
                        int var14 = var8.getPriority();
                        if(var13 == var14) {
                           Presence.Mode var15 = var10.getMode();
                           if(var15 == null) {
                              var15 = Presence.Mode.available;
                           }

                           Presence.Mode var16 = var8.getMode();
                           if(var16 == null) {
                              Presence.Mode var17 = Presence.Mode.available;
                           }

                           if(var15.compareTo(var16) < 0) {
                              break label37;
                           }
                        }

                        var10 = var8;
                     }
                  }
               }

               var8 = var10;
            }
         }

         if(var8 == null) {
            Presence.Type var18 = Presence.Type.unavailable;
            var6 = new Presence(var18);
            var6.setFrom(var1);
         } else {
            var6 = var8;
         }
      }

      return var6;
   }

   public Presence getPresenceResource(String var1) {
      String var2 = this.getPresenceMapKey(var1);
      String var3 = StringUtils.parseResource(var1);
      Map var4 = (Map)this.presenceMap.get(var2);
      Presence var6;
      if(var4 == null) {
         Presence.Type var5 = Presence.Type.unavailable;
         var6 = new Presence(var5);
         var6.setFrom(var1);
      } else {
         Presence var8 = (Presence)var4.get(var3);
         if(var8 == null) {
            Presence.Type var7 = Presence.Type.unavailable;
            var6 = new Presence(var7);
            var6.setFrom(var1);
         } else {
            var6 = var8;
         }
      }

      return var6;
   }

   public Iterator<Presence> getPresences(String var1) {
      String var2 = this.getPresenceMapKey(var1);
      Map var3 = (Map)this.presenceMap.get(var2);
      Iterator var7;
      if(var3 == null) {
         Presence.Type var4 = Presence.Type.unavailable;
         Presence var5 = new Presence(var4);
         var5.setFrom(var1);
         Presence[] var6 = new Presence[]{var5};
         var7 = Arrays.asList(var6).iterator();
      } else {
         ArrayList var8 = new ArrayList();
         Iterator var9 = var3.values().iterator();

         while(var9.hasNext()) {
            Presence var14 = (Presence)var9.next();
            if(var14.isAvailable()) {
               var8.add(var14);
            }
         }

         if(!var8.isEmpty()) {
            var7 = var8.iterator();
         } else {
            Presence.Type var11 = Presence.Type.unavailable;
            Presence var12 = new Presence(var11);
            var12.setFrom(var1);
            Presence[] var13 = new Presence[]{var12};
            var7 = Arrays.asList(var13).iterator();
         }
      }

      return var7;
   }

   public Roster.SubscriptionMode getSubscriptionMode() {
      return this.subscriptionMode;
   }

   public Collection<RosterEntry> getUnfiledEntries() {
      return Collections.unmodifiableList(this.unfiledEntries);
   }

   public int getUnfiledEntryCount() {
      return this.unfiledEntries.size();
   }

   public void reload() {
      RosterPacket var1 = new RosterPacket();
      if(this.persistentStorage != null) {
         String var2 = this.persistentStorage.getRosterVersion();
         var1.setVersion(var2);
      }

      String var3 = var1.getPacketID();
      this.requestPacketId = var3;
      String var4 = this.requestPacketId;
      PacketIDFilter var5 = new PacketIDFilter(var4);
      Connection var6 = this.connection;
      Roster.RosterResultListener var7 = new Roster.RosterResultListener((Roster.1)null);
      var6.addPacketListener(var7, var5);
      this.connection.sendPacket(var1);
   }

   public void removeEntry(RosterEntry var1) throws XMPPException {
      Map var2 = this.entries;
      String var3 = var1.getUser();
      if(var2.containsKey(var3)) {
         RosterPacket var4 = new RosterPacket();
         IQ.Type var5 = IQ.Type.SET;
         var4.setType(var5);
         RosterPacket.Item var6 = RosterEntry.toRosterItem(var1);
         RosterPacket.ItemType var7 = RosterPacket.ItemType.remove;
         var6.setItemType(var7);
         var4.addRosterItem(var6);
         Connection var8 = this.connection;
         String var9 = var4.getPacketID();
         PacketIDFilter var10 = new PacketIDFilter(var9);
         PacketCollector var11 = var8.createPacketCollector(var10);
         this.connection.sendPacket(var4);
         long var12 = (long)SmackConfiguration.getPacketReplyTimeout();
         IQ var17 = (IQ)var11.nextResult(var12);
         var11.cancel();
         if(var17 == null) {
            throw new XMPPException("No response from the server.");
         } else {
            IQ.Type var14 = var17.getType();
            IQ.Type var15 = IQ.Type.ERROR;
            if(var14 == var15) {
               XMPPError var16 = var17.getError();
               throw new XMPPException(var16);
            }
         }
      }
   }

   public void removeRosterListener(RosterListener var1) {
      this.rosterListeners.remove(var1);
   }

   public void setSubscriptionMode(Roster.SubscriptionMode var1) {
      this.subscriptionMode = var1;
   }

   public static enum SubscriptionMode {

      // $FF: synthetic field
      private static final Roster.SubscriptionMode[] $VALUES;
      accept_all("accept_all", 0),
      manual("manual", 2),
      reject_all("reject_all", 1);


      static {
         Roster.SubscriptionMode[] var0 = new Roster.SubscriptionMode[3];
         Roster.SubscriptionMode var1 = accept_all;
         var0[0] = var1;
         Roster.SubscriptionMode var2 = reject_all;
         var0[1] = var2;
         Roster.SubscriptionMode var3 = manual;
         var0[2] = var3;
         $VALUES = var0;
      }

      private SubscriptionMode(String var1, int var2) {}
   }

   private class RosterPacketListener implements PacketListener {

      private RosterPacketListener() {}

      // $FF: synthetic method
      RosterPacketListener(Roster.1 var2) {
         this();
      }

      public void processPacket(Packet param1) {
         // $FF: Couldn't be decompiled
      }
   }

   class 1 implements ConnectionListener {

      1() {}

      public void connectionClosed() {
         Roster.this.setOfflinePresences();
      }

      public void connectionClosedOnError(Exception var1) {
         Roster.this.setOfflinePresences();
      }

      public void reconnectingIn(int var1) {}

      public void reconnectionFailed(Exception var1) {}

      public void reconnectionSuccessful() {}
   }

   private class RosterResultListener implements PacketListener {

      private RosterResultListener() {}

      // $FF: synthetic method
      RosterResultListener(Roster.1 var2) {
         this();
      }

      public void processPacket(Packet param1) {
         // $FF: Couldn't be decompiled
      }
   }

   private class PresencePacketListener implements PacketListener {

      private PresencePacketListener() {}

      // $FF: synthetic method
      PresencePacketListener(Roster.1 var2) {
         this();
      }

      public void processPacket(Packet var1) {
         Presence var2 = (Presence)var1;
         String var3 = var2.getFrom();
         String var4 = Roster.this.getPresenceMapKey(var3);
         Presence.Type var5 = var2.getType();
         Presence.Type var6 = Presence.Type.available;
         if(var5 == var6) {
            Object var7;
            if(Roster.this.presenceMap.get(var4) == null) {
               var7 = new ConcurrentHashMap();
               Roster.this.presenceMap.put(var4, var7);
            } else {
               var7 = (Map)Roster.this.presenceMap.get(var4);
            }

            Object var9 = ((Map)var7).remove("");
            String var10 = StringUtils.parseResource(var3);
            ((Map)var7).put(var10, var2);
            if((RosterEntry)Roster.this.entries.get(var4) != null) {
               Roster.this.fireRosterPresenceEvent(var2);
            }
         } else {
            Presence.Type var12 = var2.getType();
            Presence.Type var13 = Presence.Type.unavailable;
            if(var12 == var13) {
               String var14 = StringUtils.parseResource(var3);
               if("".equals(var14)) {
                  Object var15;
                  if(Roster.this.presenceMap.get(var4) == null) {
                     var15 = new ConcurrentHashMap();
                     Roster.this.presenceMap.put(var4, var15);
                  } else {
                     var15 = (Map)Roster.this.presenceMap.get(var4);
                  }

                  ((Map)var15).put("", var2);
               } else if(Roster.this.presenceMap.get(var4) != null) {
                  Map var18 = (Map)Roster.this.presenceMap.get(var4);
                  String var19 = StringUtils.parseResource(var3);
                  var18.put(var19, var2);
               }

               if((RosterEntry)Roster.this.entries.get(var4) != null) {
                  Roster.this.fireRosterPresenceEvent(var2);
               }
            } else {
               Presence.Type var21 = var2.getType();
               Presence.Type var22 = Presence.Type.subscribe;
               if(var21 == var22) {
                  Roster.SubscriptionMode var23 = Roster.this.subscriptionMode;
                  Roster.SubscriptionMode var24 = Roster.SubscriptionMode.accept_all;
                  if(var23 == var24) {
                     Presence.Type var25 = Presence.Type.subscribed;
                     Presence var26 = new Presence(var25);
                     String var27 = var2.getFrom();
                     var26.setTo(var27);
                     Roster.this.connection.sendPacket(var26);
                  } else {
                     Roster.SubscriptionMode var28 = Roster.this.subscriptionMode;
                     Roster.SubscriptionMode var29 = Roster.SubscriptionMode.reject_all;
                     if(var28 == var29) {
                        Presence.Type var30 = Presence.Type.unsubscribed;
                        Presence var31 = new Presence(var30);
                        String var32 = var2.getFrom();
                        var31.setTo(var32);
                        Roster.this.connection.sendPacket(var31);
                     }
                  }
               } else {
                  Presence.Type var33 = var2.getType();
                  Presence.Type var34 = Presence.Type.unsubscribe;
                  if(var33 == var34) {
                     Roster.SubscriptionMode var35 = Roster.this.subscriptionMode;
                     Roster.SubscriptionMode var36 = Roster.SubscriptionMode.manual;
                     if(var35 != var36) {
                        Presence.Type var37 = Presence.Type.unsubscribed;
                        Presence var38 = new Presence(var37);
                        String var39 = var2.getFrom();
                        var38.setTo(var39);
                        Roster.this.connection.sendPacket(var38);
                     }
                  } else {
                     Presence.Type var40 = var2.getType();
                     Presence.Type var41 = Presence.Type.error;
                     if(var40 == var41) {
                        String var42 = StringUtils.parseResource(var3);
                        if("".equals(var42)) {
                           Object var43;
                           if(!Roster.this.presenceMap.containsKey(var4)) {
                              var43 = new ConcurrentHashMap();
                              Roster.this.presenceMap.put(var4, var43);
                           } else {
                              var43 = (Map)Roster.this.presenceMap.get(var4);
                              ((Map)var43).clear();
                           }

                           ((Map)var43).put("", var2);
                           if((RosterEntry)Roster.this.entries.get(var4) != null) {
                              Roster.this.fireRosterPresenceEvent(var2);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
