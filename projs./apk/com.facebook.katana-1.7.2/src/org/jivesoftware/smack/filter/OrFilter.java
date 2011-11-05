package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class OrFilter implements PacketFilter {

   private PacketFilter[] filters;
   private int size;


   public OrFilter() {
      this.size = 0;
      PacketFilter[] var1 = new PacketFilter[3];
      this.filters = var1;
   }

   public OrFilter(PacketFilter var1, PacketFilter var2) {
      if(var1 != null && var2 != null) {
         this.size = 2;
         PacketFilter[] var3 = new PacketFilter[2];
         this.filters = var3;
         this.filters[0] = var1;
         this.filters[1] = var2;
      } else {
         throw new IllegalArgumentException("Parameters cannot be null.");
      }
   }

   public boolean accept(Packet var1) {
      int var2 = 0;

      boolean var4;
      while(true) {
         int var3 = this.size;
         if(var2 >= var3) {
            var4 = false;
            break;
         }

         if(this.filters[var2].accept(var1)) {
            var4 = true;
            break;
         }

         ++var2;
      }

      return var4;
   }

   public void addFilter(PacketFilter var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter cannot be null.");
      } else {
         int var2 = this.size;
         int var3 = this.filters.length;
         if(var2 == var3) {
            PacketFilter[] var4 = new PacketFilter[this.filters.length + 2];
            byte var5 = 0;

            while(true) {
               int var6 = this.filters.length;
               if(var5 >= var6) {
                  this.filters = var4;
                  break;
               }

               PacketFilter var7 = this.filters[var5];
               var4[var5] = var7;
               int var8 = var5 + 1;
            }
         }

         PacketFilter[] var9 = this.filters;
         int var10 = this.size;
         var9[var10] = var1;
         int var11 = this.size + 1;
         this.size = var11;
      }
   }

   public String toString() {
      return this.filters.toString();
   }
}
