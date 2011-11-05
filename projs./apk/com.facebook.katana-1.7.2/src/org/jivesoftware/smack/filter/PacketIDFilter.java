package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class PacketIDFilter implements PacketFilter {

   private String packetID;


   public PacketIDFilter(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Packet ID cannot be null.");
      } else {
         this.packetID = var1;
      }
   }

   public boolean accept(Packet var1) {
      String var2 = this.packetID;
      String var3 = var1.getPacketID();
      return var2.equals(var3);
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("PacketIDFilter by id: ");
      String var2 = this.packetID;
      return var1.append(var2).toString();
   }
}
