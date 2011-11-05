package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class ToContainsFilter implements PacketFilter {

   private String to;


   public ToContainsFilter(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter cannot be null.");
      } else {
         String var2 = var1.toLowerCase();
         this.to = var2;
      }
   }

   public boolean accept(Packet var1) {
      boolean var2;
      if(var1.getTo() == null) {
         var2 = false;
      } else {
         String var3 = var1.getTo().toLowerCase();
         String var4 = this.to;
         if(var3.indexOf(var4) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }
}
