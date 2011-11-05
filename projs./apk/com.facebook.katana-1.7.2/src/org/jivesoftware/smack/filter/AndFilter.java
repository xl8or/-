package org.jivesoftware.smack.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class AndFilter implements PacketFilter {

   private List<PacketFilter> filters;


   public AndFilter() {
      ArrayList var1 = new ArrayList();
      this.filters = var1;
   }

   public AndFilter(PacketFilter ... var1) {
      ArrayList var2 = new ArrayList();
      this.filters = var2;
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter cannot be null.");
      } else {
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PacketFilter var5 = var1[var4];
            if(var5 == null) {
               throw new IllegalArgumentException("Parameter cannot be null.");
            }

            this.filters.add(var5);
         }

      }
   }

   public boolean accept(Packet var1) {
      Iterator var2 = this.filters.iterator();

      boolean var3;
      while(true) {
         if(var2.hasNext()) {
            if(((PacketFilter)var2.next()).accept(var1)) {
               continue;
            }

            var3 = false;
            break;
         }

         var3 = true;
         break;
      }

      return var3;
   }

   public void addFilter(PacketFilter var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter cannot be null.");
      } else {
         this.filters.add(var1);
      }
   }

   public String toString() {
      return this.filters.toString();
   }
}
