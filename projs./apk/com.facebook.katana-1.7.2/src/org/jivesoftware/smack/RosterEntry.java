package org.jivesoftware.smack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.RosterPacket;

public class RosterEntry {

   private final Connection connection;
   private String name;
   private final Roster roster;
   private RosterPacket.ItemStatus status;
   private RosterPacket.ItemType type;
   private String user;


   RosterEntry(String var1, String var2, RosterPacket.ItemType var3, RosterPacket.ItemStatus var4, Roster var5, Connection var6) {
      this.user = var1;
      this.name = var2;
      this.type = var3;
      this.status = var4;
      this.roster = var5;
      this.connection = var6;
   }

   static RosterPacket.Item toRosterItem(RosterEntry var0) {
      String var1 = var0.getUser();
      String var2 = var0.getName();
      RosterPacket.Item var3 = new RosterPacket.Item(var1, var2);
      RosterPacket.ItemType var4 = var0.getType();
      var3.setItemType(var4);
      RosterPacket.ItemStatus var5 = var0.getStatus();
      var3.setItemStatus(var5);
      Iterator var6 = var0.getGroups().iterator();

      while(var6.hasNext()) {
         String var7 = ((RosterGroup)var6.next()).getName();
         var3.addGroupName(var7);
      }

      return var3;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else if(var1 != null && var1 instanceof RosterEntry) {
         String var3 = this.user;
         String var4 = ((RosterEntry)var1).getUser();
         var2 = var3.equals(var4);
      } else {
         var2 = false;
      }

      return var2;
   }

   public Collection<RosterGroup> getGroups() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.roster.getGroups().iterator();

      while(var2.hasNext()) {
         RosterGroup var3 = (RosterGroup)var2.next();
         if(var3.contains(this)) {
            var1.add(var3);
         }
      }

      return Collections.unmodifiableCollection(var1);
   }

   public String getName() {
      return this.name;
   }

   public RosterPacket.ItemStatus getStatus() {
      return this.status;
   }

   public RosterPacket.ItemType getType() {
      return this.type;
   }

   public String getUser() {
      return this.user;
   }

   public void setName(String var1) {
      if(var1 != null) {
         String var2 = this.name;
         if(var1.equals(var2)) {
            return;
         }
      }

      this.name = var1;
      RosterPacket var3 = new RosterPacket();
      IQ.Type var4 = IQ.Type.SET;
      var3.setType(var4);
      RosterPacket.Item var5 = toRosterItem(this);
      var3.addRosterItem(var5);
      this.connection.sendPacket(var3);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      if(this.name != null) {
         String var2 = this.name;
         StringBuilder var3 = var1.append(var2).append(": ");
      }

      String var4 = this.user;
      var1.append(var4);
      Collection var6 = this.getGroups();
      if(!var6.isEmpty()) {
         StringBuilder var7 = var1.append(" [");
         Iterator var8 = var6.iterator();
         String var9 = ((RosterGroup)var8.next()).getName();
         var1.append(var9);

         while(var8.hasNext()) {
            StringBuilder var11 = var1.append(", ");
            String var12 = ((RosterGroup)var8.next()).getName();
            var1.append(var12);
         }

         StringBuilder var14 = var1.append("]");
      }

      return var1.toString();
   }

   void updateState(String var1, RosterPacket.ItemType var2, RosterPacket.ItemStatus var3) {
      this.name = var1;
      this.type = var2;
      this.status = var3;
   }
}
