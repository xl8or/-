package org.xbill.DNS;


public final class Serial {

   private static final long MAX32 = 4294967295L;


   private Serial() {}

   public static int compare(long var0, long var2) {
      if(var0 >= 0L && var0 <= 4294967295L) {
         if(var2 >= 0L && var2 <= 4294967295L) {
            long var6 = var0 - var2;
            if(var6 >= 4294967295L) {
               var6 -= 4294967296L;
            } else if(var6 < -4294967295L) {
               var6 += 4294967296L;
            }

            return (int)var6;
         } else {
            String var5 = var2 + " out of range";
            throw new IllegalArgumentException(var5);
         }
      } else {
         String var4 = var0 + " out of range";
         throw new IllegalArgumentException(var4);
      }
   }

   public static long increment(long var0) {
      if(var0 >= 0L && var0 <= 4294967295L) {
         long var3;
         if(var0 == 4294967295L) {
            var3 = 0L;
         } else {
            var3 = 1L + var0;
         }

         return var3;
      } else {
         String var2 = var0 + " out of range";
         throw new IllegalArgumentException(var2);
      }
   }
}
