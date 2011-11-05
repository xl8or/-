package com.google.wireless.gdata.data;


public final class StringUtils {

   private StringUtils() {}

   public static boolean isEmpty(String var0) {
      boolean var1;
      if(var0 != null && var0.length() != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean isEmptyOrWhitespace(String var0) {
      boolean var1 = true;
      if(var0 != null) {
         int var2 = var0.length();

         for(int var3 = 0; var3 < var2; ++var3) {
            if(!Character.isWhitespace(var0.charAt(var3))) {
               var1 = false;
               break;
            }
         }
      }

      return var1;
   }

   public static int parseInt(String var0, int var1) {
      if(var0 != null) {
         int var2;
         try {
            var2 = Integer.parseInt(var0);
         } catch (NumberFormatException var4) {
            return var1;
         }

         var1 = var2;
      }

      return var1;
   }
}
