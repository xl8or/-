package org.codehaus.jackson.io;


public final class NumberInput {

   static final long L_BILLION = 1000000000L;


   public NumberInput() {}

   public static final int parseInt(char[] var0, int var1, int var2) {
      int var3 = var0[var1] - 48;
      int var4 = var2 + var1;
      int var5 = var1 + 1;
      if(var5 < var4) {
         int var6 = var3 * 10;
         int var7 = var0[var5] - 48;
         var3 = var6 + var7;
         ++var5;
         if(var5 < var4) {
            int var8 = var3 * 10;
            int var9 = var0[var5] - 48;
            var3 = var8 + var9;
            ++var5;
            if(var5 < var4) {
               int var10 = var3 * 10;
               int var11 = var0[var5] - 48;
               var3 = var10 + var11;
               ++var5;
               if(var5 < var4) {
                  int var12 = var3 * 10;
                  int var13 = var0[var5] - 48;
                  var3 = var12 + var13;
                  ++var5;
                  if(var5 < var4) {
                     int var14 = var3 * 10;
                     int var15 = var0[var5] - 48;
                     var3 = var14 + var15;
                     ++var5;
                     if(var5 < var4) {
                        int var16 = var3 * 10;
                        int var17 = var0[var5] - 48;
                        var3 = var16 + var17;
                        ++var5;
                        if(var5 < var4) {
                           int var18 = var3 * 10;
                           int var19 = var0[var5] - 48;
                           var3 = var18 + var19;
                           ++var5;
                           if(var5 < var4) {
                              int var20 = var3 * 10;
                              int var21 = var0[var5] - 48;
                              var3 = var20 + var21;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var3;
   }

   public static final long parseLong(char[] var0, int var1, int var2) {
      int var3 = var2 - 9;
      long var4 = (long)parseInt(var0, var1, var3) * 1000000000L;
      int var6 = var3 + var1;
      long var7 = (long)parseInt(var0, var6, 9);
      return var4 + var7;
   }
}
