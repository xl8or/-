package org.acra;

import android.content.Context;

class DropBoxCollector {

   private static final String[] SYSTEM_TAGS;


   static {
      String[] var0 = new String[]{"system_app_anr", "system_app_wtf", "system_app_crash", "system_server_anr", "system_server_wtf", "system_server_crash", "BATTERY_DISCHARGE_INFO", "SYSTEM_RECOVERY_LOG", "SYSTEM_BOOT", "SYSTEM_LAST_KMSG", "APANIC_CONSOLE", "APANIC_THREADS", "SYSTEM_RESTART", "SYSTEM_TOMBSTONE", "data_app_strictmode"};
      SYSTEM_TAGS = var0;
   }

   DropBoxCollector() {}

   public static String read(Context param0, String[] param1) {
      // $FF: Couldn't be decompiled
   }
}
