package org.jivesoftware.smack;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.PrivacyList;
import org.jivesoftware.smack.PrivacyListListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.IQTypeFilter;
import org.jivesoftware.smack.filter.PacketExtensionFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Privacy;
import org.jivesoftware.smack.packet.PrivacyItem;
import org.jivesoftware.smack.packet.XMPPError;

public class PrivacyListManager {

   private static Map<Connection, PrivacyListManager> instances = new Hashtable();
   private Connection connection;
   private final List<PrivacyListListener> listeners;
   PacketFilter packetFilter;


   static {
      Connection.addConnectionCreationListener(new PrivacyListManager.1());
   }

   private PrivacyListManager(Connection var1) {
      ArrayList var2 = new ArrayList();
      this.listeners = var2;
      PacketFilter[] var3 = new PacketFilter[2];
      IQ.Type var4 = IQ.Type.SET;
      IQTypeFilter var5 = new IQTypeFilter(var4);
      var3[0] = var5;
      PacketExtensionFilter var6 = new PacketExtensionFilter("query", "jabber:iq:privacy");
      var3[1] = var6;
      AndFilter var7 = new AndFilter(var3);
      this.packetFilter = var7;
      this.connection = var1;
      this.init();
   }

   // $FF: synthetic method
   PrivacyListManager(Connection var1, PrivacyListManager.1 var2) {
      this(var1);
   }

   public static PrivacyListManager getInstanceFor(Connection var0) {
      return (PrivacyListManager)instances.get(var0);
   }

   private List<PrivacyItem> getPrivacyListItems(String var1) throws XMPPException {
      Privacy var2 = new Privacy();
      ArrayList var3 = new ArrayList();
      var2.setPrivacyList(var1, var3);
      return this.getRequest(var2).getPrivacyList(var1);
   }

   private Privacy getPrivacyWithListNames() throws XMPPException {
      Privacy var1 = new Privacy();
      return this.getRequest(var1);
   }

   private Privacy getRequest(Privacy var1) throws XMPPException {
      IQ.Type var2 = IQ.Type.GET;
      var1.setType(var2);
      String var3 = this.getUser();
      var1.setFrom(var3);
      String var4 = var1.getPacketID();
      PacketIDFilter var5 = new PacketIDFilter(var4);
      PacketCollector var6 = this.connection.createPacketCollector(var5);
      this.connection.sendPacket(var1);
      long var7 = (long)SmackConfiguration.getPacketReplyTimeout();
      Privacy var9 = (Privacy)var6.nextResult(var7);
      var6.cancel();
      if(var9 == null) {
         throw new XMPPException("No response from server.");
      } else if(var9.getError() != null) {
         XMPPError var10 = var9.getError();
         throw new XMPPException(var10);
      } else {
         return var9;
      }
   }

   private String getUser() {
      return this.connection.getUser();
   }

   private void init() {
      Map var1 = instances;
      Connection var2 = this.connection;
      var1.put(var2, this);
      Connection var4 = this.connection;
      PrivacyListManager.2 var5 = new PrivacyListManager.2();
      var4.addConnectionListener(var5);
      Connection var6 = this.connection;
      PrivacyListManager.3 var7 = new PrivacyListManager.3();
      PacketFilter var8 = this.packetFilter;
      var6.addPacketListener(var7, var8);
   }

   private Packet setRequest(Privacy var1) throws XMPPException {
      IQ.Type var2 = IQ.Type.SET;
      var1.setType(var2);
      String var3 = this.getUser();
      var1.setFrom(var3);
      String var4 = var1.getPacketID();
      PacketIDFilter var5 = new PacketIDFilter(var4);
      PacketCollector var6 = this.connection.createPacketCollector(var5);
      this.connection.sendPacket(var1);
      long var7 = (long)SmackConfiguration.getPacketReplyTimeout();
      Packet var9 = var6.nextResult(var7);
      var6.cancel();
      if(var9 == null) {
         throw new XMPPException("No response from server.");
      } else if(var9.getError() != null) {
         XMPPError var10 = var9.getError();
         throw new XMPPException(var10);
      } else {
         return var9;
      }
   }

   public void addListener(PrivacyListListener var1) {
      List var2 = this.listeners;
      synchronized(var2) {
         this.listeners.add(var1);
      }
   }

   public void createPrivacyList(String var1, List<PrivacyItem> var2) throws XMPPException {
      this.updatePrivacyList(var1, var2);
   }

   public void declineActiveList() throws XMPPException {
      Privacy var1 = new Privacy();
      var1.setDeclineActiveList((boolean)1);
      this.setRequest(var1);
   }

   public void declineDefaultList() throws XMPPException {
      Privacy var1 = new Privacy();
      var1.setDeclineDefaultList((boolean)1);
      this.setRequest(var1);
   }

   public void deletePrivacyList(String var1) throws XMPPException {
      Privacy var2 = new Privacy();
      ArrayList var3 = new ArrayList();
      var2.setPrivacyList(var1, var3);
      this.setRequest(var2);
   }

   public PrivacyList getActiveList() throws XMPPException {
      String var2;
      byte var6;
      label14: {
         Privacy var1 = this.getPrivacyWithListNames();
         var2 = var1.getActiveName();
         if(var1.getActiveName() != null && var1.getDefaultName() != null) {
            String var3 = var1.getActiveName();
            String var4 = var1.getDefaultName();
            if(var3.equals(var4)) {
               var6 = 1;
               break label14;
            }
         }

         var6 = 0;
      }

      List var5 = this.getPrivacyListItems(var2);
      return new PrivacyList((boolean)1, (boolean)var6, var2, var5);
   }

   public PrivacyList getDefaultList() throws XMPPException {
      String var2;
      byte var6;
      label14: {
         Privacy var1 = this.getPrivacyWithListNames();
         var2 = var1.getDefaultName();
         if(var1.getActiveName() != null && var1.getDefaultName() != null) {
            String var3 = var1.getActiveName();
            String var4 = var1.getDefaultName();
            if(var3.equals(var4)) {
               var6 = 1;
               break label14;
            }
         }

         var6 = 0;
      }

      List var5 = this.getPrivacyListItems(var2);
      return new PrivacyList((boolean)var6, (boolean)1, var2, var5);
   }

   public PrivacyList getPrivacyList(String var1) throws XMPPException {
      List var2 = this.getPrivacyListItems(var1);
      return new PrivacyList((boolean)0, (boolean)0, var1, var2);
   }

   public PrivacyList[] getPrivacyLists() throws XMPPException {
      Privacy var1 = this.getPrivacyWithListNames();
      Set var2 = var1.getPrivacyListNames();
      PrivacyList[] var3 = new PrivacyList[var2.size()];
      int var4 = 0;

      for(Iterator var5 = var2.iterator(); var5.hasNext(); ++var4) {
         String var6 = (String)var5.next();
         String var7 = var1.getActiveName();
         boolean var8 = var6.equals(var7);
         String var9 = var1.getDefaultName();
         boolean var10 = var6.equals(var9);
         List var11 = this.getPrivacyListItems(var6);
         PrivacyList var12 = new PrivacyList(var8, var10, var6, var11);
         var3[var4] = var12;
      }

      return var3;
   }

   public void setActiveListName(String var1) throws XMPPException {
      Privacy var2 = new Privacy();
      var2.setActiveName(var1);
      this.setRequest(var2);
   }

   public void setDefaultListName(String var1) throws XMPPException {
      Privacy var2 = new Privacy();
      var2.setDefaultName(var1);
      this.setRequest(var2);
   }

   public void updatePrivacyList(String var1, List<PrivacyItem> var2) throws XMPPException {
      Privacy var3 = new Privacy();
      var3.setPrivacyList(var1, var2);
      this.setRequest(var3);
   }

   static class 1 implements ConnectionCreationListener {

      1() {}

      public void connectionCreated(Connection var1) {
         new PrivacyListManager(var1, (PrivacyListManager.1)null);
      }
   }

   class 2 implements ConnectionListener {

      2() {}

      public void connectionClosed() {
         Map var1 = PrivacyListManager.instances;
         Connection var2 = PrivacyListManager.this.connection;
         var1.remove(var2);
      }

      public void connectionClosedOnError(Exception var1) {}

      public void reconnectingIn(int var1) {}

      public void reconnectionFailed(Exception var1) {}

      public void reconnectionSuccessful() {}
   }

   class 3 implements PacketListener {

      3() {}

      public void processPacket(Packet var1) {
         if(var1 != null) {
            if(var1.getError() == null) {
               Privacy var2 = (Privacy)var1;
               List var3 = PrivacyListManager.this.listeners;
               synchronized(var3) {
                  Iterator var4 = PrivacyListManager.this.listeners.iterator();

                  while(var4.hasNext()) {
                     PrivacyListListener var5 = (PrivacyListListener)var4.next();
                     Iterator var6 = var2.getItemLists().entrySet().iterator();

                     while(var6.hasNext()) {
                        Entry var7 = (Entry)var6.next();
                        String var8 = (String)var7.getKey();
                        List var9 = (List)var7.getValue();
                        if(var9.isEmpty()) {
                           var5.updatedPrivacyList(var8);
                        } else {
                           var5.setPrivacyList(var8, var9);
                        }
                     }
                  }
               }

               PrivacyListManager.3.1 var11 = new PrivacyListManager.3.1();
               IQ.Type var12 = IQ.Type.RESULT;
               var11.setType(var12);
               String var13 = var1.getFrom();
               var11.setFrom(var13);
               String var14 = var1.getPacketID();
               var11.setPacketID(var14);
               PrivacyListManager.this.connection.sendPacket(var11);
            }
         }
      }

      class 1 extends IQ {

         1() {}

         public String getChildElementXML() {
            return "";
         }
      }
   }
}
