package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class PacketTypeFilter implements PacketFilter {

   Class packetType;


   public PacketTypeFilter(Class var1) {
      if(!Packet.class.isAssignableFrom(var1)) {
         throw new IllegalArgumentException("Packet type must be a sub-class of Packet.");
      } else {
         this.packetType = var1;
      }
   }

   public boolean accept(Packet var1) {
      return this.packetType.isInstance(var1);
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("PacketTypeFilter: ");
      String var2 = this.packetType.getName();
      return var1.append(var2).toString();
   }
}
