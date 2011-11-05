package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class NotFilter implements PacketFilter {

   private PacketFilter filter;


   public NotFilter(PacketFilter var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter cannot be null.");
      } else {
         this.filter = var1;
      }
   }

   public boolean accept(Packet var1) {
      boolean var2;
      if(!this.filter.accept(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
