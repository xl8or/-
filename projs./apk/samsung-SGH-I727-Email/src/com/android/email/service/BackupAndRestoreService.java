package com.android.email.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BackupAndRestoreService extends BroadcastReceiver {

   private static final String BACKUP_BACKUP_FINISH = "com.android.email.service.BACKUP_FINISH";
   public static final String BACKUP_OR_RESTORE = "backup_Or_Restore";
   public static final String BACKUP_PATH = "backup_Path";
   private static final String BACKUP_PREFER_FILE_NAME = "AndroidMail.Main.xml";
   private static final String BACKUP_RESTORE_FINISH = "com.android.email.service.RESTORE_FINISH";
   public static final String CONVENTION_STRING = "com.android.email.service.BROADCAST_DETECT";
   private static final String PREF_FILE = "AndroidMail.Main";
   private static final String SHARED_PREF_FILE_PATH = "./data/data/com.android.email/shared_prefs/AndroidMail.Main.xml";


   public BackupAndRestoreService() {}

   public static void copy(String param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   private void onFinish(Context var1, String var2) {
      if(var2.equals("com.android.email.service.BACKUP_FINISH")) {
         Intent var3 = new Intent("com.android.email.service.BACKUP_FINISH");
         var1.sendBroadcast(var3);
         int var4 = Log.v("Email", "BACKUP_FINISH!!");
      } else if(var2.equals("com.android.email.service.RESTORE_FINISH")) {
         Intent var5 = new Intent("com.android.email.service.RESTORE_FINISH");
         var1.sendBroadcast(var5);
         int var6 = Log.v("Email", "RESTORE_FINISH!!");
      } else {
         int var7 = Log.v("Email", "BACKUP_RESTORE_FAILED!!");
      }
   }

   public void onReceive(Context var1, Intent var2) {
      boolean var3 = var2.getBooleanExtra("backup_Or_Restore", (boolean)0);
      String var4 = var2.getStringExtra("backup_Path");
      if(var3) {
         copy(var4 + "AndroidMail.Main.xml", "./data/data/com.android.email/shared_prefs/AndroidMail.Main.xml");
         this.onFinish(var1, "com.android.email.service.RESTORE_FINISH");
         SharedPreferences var5 = var1.getSharedPreferences("AndroidMail.Main", 0);
      } else {
         String var6 = var4 + "AndroidMail.Main.xml";
         int var7 = Log.v("Email", "Backup!!");
         copy("./data/data/com.android.email/shared_prefs/AndroidMail.Main.xml", var6);
         this.onFinish(var1, "com.android.email.service.BACKUP_FINISH");
      }
   }
}
