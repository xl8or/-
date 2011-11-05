package com.google.android.common.base;


public final class ByteArrays {

   private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();


   private ByteArrays() {}

   public static String toHexString(byte[] var0) {
      int var1 = var0.length * 2;
      StringBuilder var2 = new StringBuilder(var1);
      byte[] var3 = var0;
      int var4 = var0.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         byte var6 = var3[var5];
         char[] var7 = HEX_DIGITS;
         int var8 = var6 >> 4 & 15;
         char var9 = var7[var8];
         StringBuilder var10 = var2.append(var9);
         char[] var11 = HEX_DIGITS;
         int var12 = var6 & 15;
         char var13 = var11[var12];
         var10.append(var13);
      }

      return var2.toString();
   }
}
