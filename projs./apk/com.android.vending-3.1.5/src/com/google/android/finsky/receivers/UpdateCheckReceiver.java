package com.google.android.finsky.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.receivers.UpdatesAvailableReceiver;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.VendingPreferences;

public class UpdateCheckReceiver extends BroadcastReceiver {

   private static final String ACTION_FORCE_UPDATE_CHECK = "com.android.vending.FORCE_UPDATE_CHECK";
   private static final String ACTION_UPDATES_AVAILABLE = "com.android.vending.UPDATES_AVAILABLE";
   private static final String CATEGORY_UPDATES_AVAILABLE = "UPDATES_AVAILABLE";
   private static boolean sSetAutoUpdateAlarm;


   public UpdateCheckReceiver() {}

   private static void forceUpdateCheck(Context var0) {
      long var1 = ((Long)VendingPreferences.LAST_UPDATE_CHECK_TIME.get()).longValue();
      long var3 = System.currentTimeMillis();
      long var5 = ((Long)G.vendingUpdateCheckFrequencyMs.get()).longValue();
      long var7 = var3 - var1;
      if(var7 < 0L) {
         Object[] var9 = new Object[2];
         Long var10 = Long.valueOf(var3);
         var9[0] = var10;
         Long var11 = Long.valueOf(var1);
         var9[1] = var11;
         FinskyLog.d("Wall clock changed by user / system. Current time: %d Last check %d", var9);
         PreferenceFile.SharedPreference var12 = VendingPreferences.LAST_UPDATE_CHECK_TIME;
         Long var13 = Long.valueOf(var3);
         var12.put(var13);
      } else if(var7 >= var5) {
         PreferenceFile.SharedPreference var14 = VendingPreferences.LAST_UPDATE_CHECK_TIME;
         Long var15 = Long.valueOf(var3);
         var14.put(var15);
         Intent var16 = new Intent("com.android.vending.UPDATES_AVAILABLE");
         var16.setClass(var0, UpdatesAvailableReceiver.class);
         Intent var18 = var16.addCategory("UPDATES_AVAILABLE");
         Object[] var19 = new Object[0];
         FinskyLog.d("Force checking for available updates.", var19);
         var0.sendBroadcast(var16);
      }
   }

   public static void initialize(Context var0) {
      if(!sSetAutoUpdateAlarm) {
         sSetAutoUpdateAlarm = (boolean)1;
         Intent var1 = new Intent("com.android.vending.FORCE_UPDATE_CHECK");
         var1.setClass(var0, UpdateCheckReceiver.class);
         PendingIntent var3 = PendingIntent.getBroadcast(var0, 0, var1, 268435456);
         long var4 = ((Long)G.vendingUpdateCheckFrequencyMs.get()).longValue();
         AlarmManager var6 = (AlarmManager)var0.getSystemService("alarm");
         var6.cancel(var3);
         long var7 = SystemClock.elapsedRealtime() + 600000L;
         var6.setRepeating(3, var7, var4, var3);
      }
   }

   public void onReceive(Context var1, Intent var2) {
      forceUpdateCheck(var1);
   }
}
