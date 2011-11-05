package com.google.android.common.base;

import com.google.android.common.base.Preconditions;

public final class Strings {

   private Strings() {}

   public static String emptyToNull(String var0) {
      if(isNullOrEmpty(var0)) {
         var0 = null;
      }

      return var0;
   }

   public static boolean isNullOrEmpty(String var0) {
      boolean var1;
      if(var0 != null && var0.length() != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static String nullToEmpty(String var0) {
      if(var0 == null) {
         var0 = "";
      }

      return var0;
   }

   public static String padEnd(String var0, int var1, char var2) {
      Object var3 = Preconditions.checkNotNull(var0);
      if(var0.length() < var1) {
         StringBuilder var4 = new StringBuilder(var1);
         var4.append(var0);

         for(int var6 = var0.length(); var6 < var1; ++var6) {
            var4.append(var2);
         }

         var0 = var4.toString();
      }

      return var0;
   }

   public static String padStart(String var0, int var1, char var2) {
      Object var3 = Preconditions.checkNotNull(var0);
      if(var0.length() < var1) {
         StringBuilder var4 = new StringBuilder(var1);

         for(int var5 = var0.length(); var5 < var1; ++var5) {
            var4.append(var2);
         }

         var4.append(var0);
         var0 = var4.toString();
      }

      return var0;
   }

   public static String repeat(String var0, int var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      byte var3;
      if(var1 >= 0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Object[] var4 = new Object[1];
      Integer var5 = Integer.valueOf(var1);
      var4[0] = var5;
      Preconditions.checkArgument((boolean)var3, "invalid count: %s", var4);
      int var6 = var0.length() * var1;
      StringBuilder var7 = new StringBuilder(var6);

      for(int var8 = 0; var8 < var1; ++var8) {
         var7.append(var0);
      }

      return var7.toString();
   }
}
