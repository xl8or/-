package com.android.settings.wifi;

import android.content.Context;
import android.content.res.Resources;
import android.net.NetworkInfo.DetailedState;

class Summary {

   Summary() {}

   static String get(Context var0, DetailedState var1) {
      return get(var0, (String)null, var1);
   }

   static String get(Context var0, String var1, DetailedState var2) {
      Resources var3 = var0.getResources();
      int var4;
      if(var1 == null) {
         var4 = 2131034133;
      } else {
         var4 = 2131034134;
      }

      String[] var5 = var3.getStringArray(var4);
      int var6 = var2.ordinal();
      int var7 = var5.length;
      String var10;
      if(var6 < var7 && var5[var6].length() != 0) {
         String var8 = var5[var6];
         Object[] var9 = new Object[]{var1};
         var10 = String.format(var8, var9);
      } else {
         var10 = null;
      }

      return var10;
   }
}
