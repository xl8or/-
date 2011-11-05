package com.android.settings.powersavingmode;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncInfo;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiManager;
import android.os.IPowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.IPowerManager.Stub;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.util.Log;
import com.android.settings.powersavingmode.PowerSavingModeLog;
import com.android.settings.powersavingmode.PowerSavingModeSettings;

public class PowerNotiReceiver extends BroadcastReceiver {

   private static final String ACTION_POWERSAVING = "android.settings.ACTION_POWERSAVING";
   private static final int ID_NOTY_MODE_ENABLED = 152;
   private static final int ID_NOTY_MODE_PAUSE = 151;
   private static final int ID_NOTY_MODE_START = 150;
   private static final int MAXIMUM_BACKLIGHT = 255;
   private static final int MINIMUM_BACKLIGHT = 30;
   private static final String NEW_POWER_SAVING_MODE = "new_power_saving_mode";
   private static final int POWERSAVINGMODE_NOTY_ID = 1;
   private static final String POWERSAVING_CHANGED = "android.settings.POWERSAVING_CHANGED";
   private static final String PSM_BLUETOOTH = "psm_bluetooth";
   private static final String PSM_BRIGHTNESS = "psm_brightness";
   private static final String PSM_BRIGHTNESS_LEVEL = "psm_brightness_level";
   private static final String PSM_GPS = "psm_gps";
   private static final String PSM_SCREEN_TIMEOUT = "psm_screen_timeout";
   private static final String PSM_SYNC = "psm_sync";
   private static final String PSM_WIFI = "psm_wifi";
   private static final String TAG = "PowerNotiReceiver";
   private static boolean mCharging = 1;
   private int mBatteryStatus;
   private ContentResolver mContentResolver;
   private Context mContext;
   private int mNotificationMode;


   public PowerNotiReceiver() {}

   private void adjustBrightness() {
      if(System.getInt(this.mContentResolver, "psm_brightness", 0) == 1) {
         int var1 = System.getInt(this.mContentResolver, "psm_brightness_level", 10);
         int var2;
         if(var1 == 100) {
            var2 = (int)((double)var1 / 100.0D * 255.0D);
         } else {
            var2 = (int)((double)var1 / 100.0D * 255.0D + 10.0D);
         }

         String var3 = "PSM_brightness = " + var1 + "%, " + "set_brightness = " + var2;
         int var4 = Log.d("PowerNotiReceiver", var3);
         this.doAdjustBrightness(0, var2);
      }
   }

   private void adjustScreentimeout() {
      int var1 = System.getInt(this.mContentResolver, "psm_screen_timeout", 15000);
      this.doAdjustScreentimeout(var1);
   }

   private void clearNotification() {
      ((NotificationManager)this.mContext.getSystemService("notification")).cancel(1);
   }

   private void doAdjustBrightness(int var1, int var2) {
      boolean var3 = System.putInt(this.mContext.getContentResolver(), "screen_brightness_mode", var1);
      IPowerManager var4 = Stub.asInterface(ServiceManager.getService("power"));
      if(var4 != null) {
         try {
            var4.setBacklightBrightness(var2);
         } catch (RemoteException var10) {
            StringBuilder var8 = (new StringBuilder()).append("RemoteException");
            String var9 = var10.getMessage();
            PowerSavingModeLog.e(var8.append(var9).toString());
         }
      }

      boolean var5 = System.putInt(this.mContext.getContentResolver(), "screen_brightness", var2);
      Intent var6 = new Intent("android.settings.BRIGHTNESS_CHANGED");
      this.mContext.sendBroadcast(var6);
   }

   private void doAdjustScreentimeout(int var1) {
      boolean var2 = System.putInt(this.mContext.getContentResolver(), "screen_off_timeout", var1);
      Intent var3 = new Intent("android.settings.SCREENTIMEOUT_CHANGED");
      this.mContext.sendBroadcast(var3);
   }

   private void doPowerSaveAction(Context var1) {
      this.stopWifi();
      this.stopBluetooth();
      Context var2 = this.mContext;
      this.stopGps(var2);
      Context var3 = this.mContext;
      this.stopSync(var3);
      this.adjustBrightness();
      this.adjustScreentimeout();
   }

   private void showNotification(int var1) {
      this.mNotificationMode = var1;
      StringBuilder var2 = (new StringBuilder()).append("mNotificationMode=");
      int var3 = this.mNotificationMode;
      String var4 = var2.append(var3).toString();
      int var5 = Log.d("PowerNotiReceiver", var4);
      NotificationManager var6 = (NotificationManager)this.mContext.getSystemService("notification");
      int var7 = 2130837552;
      String var8 = "";
      String var9 = "";
      if(var1 == 150) {
         var8 = this.mContext.getResources().getString(2131232398);
         var9 = this.mContext.getResources().getString(2131232398);
      } else if(var1 == 151) {
         var8 = this.mContext.getResources().getString(2131232400);
         var9 = this.mContext.getResources().getString(2131232401);
      } else if(var1 == 152) {
         var7 = 2130837687;
         var8 = this.mContext.getResources().getString(2131232398);
         var9 = this.mContext.getResources().getString(2131232398);
      }

      long var10 = java.lang.System.currentTimeMillis();
      Notification var12 = new Notification(var7, var8, var10);
      int var13 = var12.flags | 2;
      var12.flags = var13;
      String var14 = this.mContext.getResources().getString(2131232369);
      Context var15 = this.mContext;
      Intent var16 = new Intent(var15, PowerSavingModeSettings.class);
      Intent var17 = var16.setAction("android.intent.action.MAIN");
      PendingIntent var18 = PendingIntent.getActivity(this.mContext, 0, var16, 0);
      Context var19 = this.mContext;
      var12.setLatestEventInfo(var19, var14, var9, var18);
      var6.notify(1, var12);
   }

   private void stopBluetooth() {
      if(System.getInt(this.mContentResolver, "psm_bluetooth", 0) == 1) {
         BluetoothAdapter var1 = BluetoothAdapter.getDefaultAdapter();
         if(var1.getConnectedDeviceCount() == 0) {
            int var2 = Log.d("PowerNotiReceiver", "doAction : disableBluetooth");
            boolean var3 = var1.disable();
         } else {
            int var4 = Log.d("PowerNotiReceiver", "Not disable Bluetooth because it\'s being used");
         }
      } else {
         int var5 = Log.d("PowerNotiReceiver", "PSM_BLUETOOTH = psm_bluetooth, do not disable Bluetooth because PSM_BLUETOOTH is unchecked");
      }
   }

   private void stopGps(Context var1) {
      if(System.getInt(this.mContentResolver, "psm_gps", 0) == 1) {
         if(!this.isGpsRunning()) {
            int var2 = Log.d("PowerNotiReceiver", "doAction : disableGPS");
            Secure.setLocationProviderEnabled(var1.getContentResolver(), "gps", (boolean)0);
            Intent var3 = new Intent("android.settings.GPS_CHANGED");
            var1.sendBroadcast(var3);
         } else {
            int var4 = Log.d("PowerNotiReceiver", "Not disable Gps because it\'s being used");
         }
      } else {
         int var5 = Log.d("PowerNotiReceiver", "PSM_GPS = psm_gps, do not disable Gps because PSM_GPS is unchecked");
      }
   }

   private void stopSync(Context var1) {
      if(System.getInt(this.mContentResolver, "psm_sync", 0) == 1) {
         boolean var2 = ((ConnectivityManager)var1.getSystemService("connectivity")).getBackgroundDataSetting();
         boolean var3 = ContentResolver.getMasterSyncAutomatically();
         SyncInfo var4 = ContentResolver.getCurrentSync();
         if(var2) {
            if(var3) {
               if(var4 == null) {
                  ContentResolver.setMasterSyncAutomatically((boolean)0);
               } else {
                  int var5 = Log.d("PowerNotiReceiver", "Not disable Auto Sync because it\'s being used");
               }
            }
         }
      } else {
         int var6 = Log.d("PowerNotiReceiver", "PSM_SYNC = psm_sync, do not disable Auto Sync because PSM_SYNC is unchecked");
      }
   }

   private void stopWifi() {
      if(System.getInt(this.mContentResolver, "psm_wifi", 0) == 1) {
         WifiManager var1 = (WifiManager)this.mContext.getSystemService("wifi");
         DetailedState var2 = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getNetworkInfo(1).getDetailedState();
         DetailedState var3 = DetailedState.CONNECTED;
         if(var2 != var3) {
            int var4 = Log.d("PowerNotiReceiver", "doAction : disableWifi");
            boolean var5 = var1.setWifiEnabled((boolean)0);
         } else {
            int var6 = Log.d("PowerNotiReceiver", "Not disable Wifi because it\'s connected to AP");
         }
      } else {
         int var7 = Log.d("PowerNotiReceiver", "PSM_WIFI = psm_wifi, do not disable Wifi because PSM_WIFI is unchecked");
      }
   }

   public boolean isGpsRunning() {
      return ((LocationManager)this.mContext.getSystemService("location")).isGpsRunning();
   }

   public void onReceive(Context var1, Intent var2) {
      String var3 = var2.getAction();
      this.mContext = var1;
      ContentResolver var4 = this.mContext.getContentResolver();
      this.mContentResolver = var4;
      String var5 = "PowerNotiReceiver " + var2;
      int var6 = Log.d("PowerNotiReceiver", var5);
      if(!var3.equals("android.intent.action.BOOT_COMPLETED") && !var3.equals("android.settings.POWERSAVING_CHANGED")) {
         if("android.intent.action.ACTION_POWER_CONNECTED".equals(var3)) {
            boolean var10 = PreferenceManager.getDefaultSharedPreferences(var1).getBoolean("use_power_saving_mode", (boolean)0);
            mCharging = (boolean)0;
            if(var10) {
               this.showNotification(151);
            }
         } else if("android.intent.action.ACTION_POWER_DISCONNECTED".equals(var3)) {
            boolean var11 = PreferenceManager.getDefaultSharedPreferences(var1).getBoolean("use_power_saving_mode", (boolean)0);
            mCharging = (boolean)1;
            if(var11) {
               this.showNotification(150);
            }
         } else if(var3.equals("android.settings.ACTION_POWERSAVING")) {
            Context var12 = this.mContext;
            this.doPowerSaveAction(var12);
            this.showNotification(152);
         }
      } else {
         boolean var7 = PreferenceManager.getDefaultSharedPreferences(var1).getBoolean("use_power_saving_mode", (boolean)0);
         String var8 = "enableMode=" + var7;
         int var9 = Log.d("PowerNotiReceiver", var8);
         if(var7) {
            if(mCharging) {
               this.showNotification(150);
            } else {
               this.showNotification(151);
            }
         } else {
            this.clearNotification();
         }
      }
   }
}
