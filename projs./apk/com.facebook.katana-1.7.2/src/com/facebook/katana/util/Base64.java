package com.facebook.katana.util;


public class Base64 {

   public static final int DEFAULT = 0;
   private static final char[] INDEX = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
   public static final int NO_PADDING = 1;
   private static final char PAD = '=';
   private static final char URL62 = '-';
   private static final char URL63 = '_';
   public static final int URL_SAFE = 2;


   public Base64() {}

   private static String encodeInt(int var0, int var1, int var2) {
      StringBuilder var3 = new StringBuilder(4);

      for(int var4 = 0; var4 < 4; ++var4) {
         if(var4 > var2) {
            if(!isNoPadding(var1)) {
               StringBuilder var5 = var3.append('=');
            }
         } else {
            int var6 = (3 - var4) * 6;
            int var7 = 63 << var6;
            int var8 = (var0 & var7) >> var6;
            if(var8 == 62 && isUrlSafe(var1)) {
               StringBuilder var9 = var3.append('-');
            } else if(var8 == 63 && isUrlSafe(var1)) {
               StringBuilder var10 = var3.append('_');
            } else {
               char var11 = INDEX[var8];
               var3.append(var11);
            }
         }
      }

      return var3.toString();
   }

   public static String encodeToString(byte[] var0, int var1) {
      String var2;
      if(var0 == null) {
         var2 = "";
      } else {
         int var3 = 0;
         StringBuilder var4 = new StringBuilder();
         int var5 = 0;

         while(true) {
            int var6 = var0.length;
            if(var5 >= var6) {
               if(var0.length % 3 != 0) {
                  int var13 = var0.length % 3;
                  String var14 = encodeInt(var3, var1, var13);
                  var4.append(var14);
               }

               var2 = var4.toString();
               break;
            }

            int var7 = var0[var5] & 255;
            int var8 = var5 % 3;
            int var9 = (2 - var8) * 8;
            int var10 = var7 << var9;
            var3 += var10;
            if(var5 % 3 == 2) {
               String var11 = encodeInt(var3, var1, 3);
               var4.append(var11);
            }

            ++var5;
         }
      }

      return var2;
   }

   private static boolean isNoPadding(int var0) {
      boolean var1;
      if((var0 & 1) == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isUrlSafe(int var0) {
      boolean var1;
      if((var0 & 2) == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
