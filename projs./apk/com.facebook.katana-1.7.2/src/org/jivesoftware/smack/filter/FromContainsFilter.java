package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class FromContainsFilter implements PacketFilter {

   private String from;


   public FromContainsFilter(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter cannot be null.");
      } else {
         String var2 = var1.toLowerCase();
         this.from = var2;
      }
   }

   public boolean accept(Packet var1) {
      boolean var2;
      if(var1.getFrom() == null) {
         var2 = false;
      } else {
         String var3 = var1.getFrom().toLowerCase();
         String var4 = this.from;
         if(var3.indexOf(var4) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }
}
