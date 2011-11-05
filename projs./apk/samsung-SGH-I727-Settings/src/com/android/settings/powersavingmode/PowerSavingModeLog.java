package com.android.settings.powersavingmode;

import android.util.Log;

public class PowerSavingModeLog {

   private static final boolean DEBUG = true;
   private static final String LOGTAG = "PowerSavingMode";
   public static boolean LOGV = 1;


   public PowerSavingModeLog() {}

   static void e(String var0) {
      int var1 = Log.e("PowerSavingMode", var0);
   }

   static void v(String var0) {
      StringBuilder var1 = new StringBuilder();
      String var2 = Thread.currentThread().getName();
      String var3 = var1.append(var2).append(" ").append(var0).toString();
      int var4 = Log.v("PowerSavingMode", var3);
   }
}
