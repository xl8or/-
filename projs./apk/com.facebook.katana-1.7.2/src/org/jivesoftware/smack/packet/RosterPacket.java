package org.jivesoftware.smack.packet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.util.StringUtils;

public class RosterPacket extends IQ {

   private final List<RosterPacket.Item> rosterItems;
   private String version;


   public RosterPacket() {
      ArrayList var1 = new ArrayList();
      this.rosterItems = var1;
   }

   public void addRosterItem(RosterPacket.Item var1) {
      List var2 = this.rosterItems;
      synchronized(var2) {
         this.rosterItems.add(var1);
      }
   }

   public String getChildElementXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<query xmlns=\"jabber:iq:roster\" ");
      if(this.version != null) {
         StringBuilder var3 = (new StringBuilder()).append(" ver=\"");
         String var4 = this.version;
         String var5 = var3.append(var4).append("\" ").toString();
         var1.append(var5);
      }

      StringBuilder var7 = var1.append(">");
      List var8 = this.rosterItems;
      synchronized(var8) {
         Iterator var9 = this.rosterItems.iterator();

         while(true) {
            if(!var9.hasNext()) {
               break;
            }

            String var10 = ((RosterPacket.Item)var9.next()).toXML();
            var1.append(var10);
         }
      }

      StringBuilder var13 = var1.append("</query>");
      return var1.toString();
   }

   public int getRosterItemCount() {
      List var1 = this.rosterItems;
      synchronized(var1) {
         int var2 = this.rosterItems.size();
         return var2;
      }
   }

   public Collection<RosterPacket.Item> getRosterItems() {
      List var1 = this.rosterItems;
      synchronized(var1) {
         List var2 = this.rosterItems;
         List var3 = Collections.unmodifiableList(new ArrayList(var2));
         return var3;
      }
   }

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String var1) {
      this.version = var1;
   }

   public static class Item {

      private final Set<String> groupNames;
      private RosterPacket.ItemStatus itemStatus;
      private RosterPacket.ItemType itemType;
      private String name;
      private String user;


      public Item(String var1, String var2) {
         String var3 = var1.toLowerCase();
         this.user = var3;
         this.name = var2;
         this.itemType = null;
         this.itemStatus = null;
         CopyOnWriteArraySet var4 = new CopyOnWriteArraySet();
         this.groupNames = var4;
      }

      public void addGroupName(String var1) {
         this.groupNames.add(var1);
      }

      public Set<String> getGroupNames() {
         return Collections.unmodifiableSet(this.groupNames);
      }

      public RosterPacket.ItemStatus getItemStatus() {
         return this.itemStatus;
      }

      public RosterPacket.ItemType getItemType() {
         return this.itemType;
      }

      public String getName() {
         return this.name;
      }

      public String getUser() {
         return this.user;
      }

      public void removeGroupName(String var1) {
         this.groupNames.remove(var1);
      }

      public void setItemStatus(RosterPacket.ItemStatus var1) {
         this.itemStatus = var1;
      }

      public void setItemType(RosterPacket.ItemType var1) {
         this.itemType = var1;
      }

      public void setName(String var1) {
         this.name = var1;
      }

      public String toXML() {
         StringBuilder var1 = new StringBuilder();
         StringBuilder var2 = var1.append("<item jid=\"");
         String var3 = this.user;
         StringBuilder var4 = var2.append(var3).append("\"");
         if(this.name != null) {
            StringBuilder var5 = var1.append(" name=\"");
            String var6 = StringUtils.escapeForXML(this.name);
            StringBuilder var7 = var5.append(var6).append("\"");
         }

         if(this.itemType != null) {
            StringBuilder var8 = var1.append(" subscription=\"");
            RosterPacket.ItemType var9 = this.itemType;
            StringBuilder var10 = var8.append(var9).append("\"");
         }

         if(this.itemStatus != null) {
            StringBuilder var11 = var1.append(" ask=\"");
            RosterPacket.ItemStatus var12 = this.itemStatus;
            StringBuilder var13 = var11.append(var12).append("\"");
         }

         StringBuilder var14 = var1.append(">");

         StringBuilder var17;
         StringBuilder var19;
         String var18;
         for(Iterator var15 = this.groupNames.iterator(); var15.hasNext(); var19 = var17.append(var18).append("</group>")) {
            String var16 = (String)var15.next();
            var17 = var1.append("<group>");
            var18 = StringUtils.escapeForXML(var16);
         }

         StringBuilder var20 = var1.append("</item>");
         return var1.toString();
      }
   }

   public static enum ItemType {

      // $FF: synthetic field
      private static final RosterPacket.ItemType[] $VALUES;
      both("both", 3),
      from("from", 2),
      none("none", 0),
      remove("remove", 4),
      to("to", 1);


      static {
         RosterPacket.ItemType[] var0 = new RosterPacket.ItemType[5];
         RosterPacket.ItemType var1 = none;
         var0[0] = var1;
         RosterPacket.ItemType var2 = to;
         var0[1] = var2;
         RosterPacket.ItemType var3 = from;
         var0[2] = var3;
         RosterPacket.ItemType var4 = both;
         var0[3] = var4;
         RosterPacket.ItemType var5 = remove;
         var0[4] = var5;
         $VALUES = var0;
      }

      private ItemType(String var1, int var2) {}
   }

   public static class ItemStatus {

      public static final RosterPacket.ItemStatus SUBSCRIPTION_PENDING = new RosterPacket.ItemStatus("subscribe");
      public static final RosterPacket.ItemStatus UNSUBSCRIPTION_PENDING = new RosterPacket.ItemStatus("unsubscribe");
      private String value;


      private ItemStatus(String var1) {
         this.value = var1;
      }

      public static RosterPacket.ItemStatus fromString(String var0) {
         RosterPacket.ItemStatus var1;
         if(var0 == null) {
            var1 = null;
         } else {
            String var2 = var0.toLowerCase();
            if("unsubscribe".equals(var2)) {
               var1 = UNSUBSCRIPTION_PENDING;
            } else if("subscribe".equals(var2)) {
               var1 = SUBSCRIPTION_PENDING;
            } else {
               var1 = null;
            }
         }

         return var1;
      }

      public String toString() {
         return this.value;
      }
   }
}
