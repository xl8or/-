package com.android.settings;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.SystemProperties;
import android.provider.Settings.System;
import android.util.Log;
import com.android.settings.wifi.AccessPointListDialog;
import com.android.settings.wifi.WifiDirectWpsDialog;
import java.util.Iterator;
import java.util.List;

public class NetworkStatusReceiver extends BroadcastReceiver {

   private static final String TAG = "NetworkStatusReceiver";
   private boolean mRequested;
   private boolean mSettingsDialogShow;
   private WifiManager mWifiManager;
   private RunningTaskInfo runTaskInfo;


   public NetworkStatusReceiver() {}

   public static boolean isAirplaneModeOn(Context var0) {
      boolean var1;
      if(System.getInt(var0.getContentResolver(), "airplane_mode_on", 0) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isWifiSettingScreen(Context var1) {
      List var2 = ((ActivityManager)var1.getSystemService("activity")).getRunningTasks(1);
      boolean var4;
      if(var2 == null) {
         int var3 = Log.d("NetworkStatusReceiver", "isWifiSettingScreen() : activityManager.getRunningTasks(1) return null");
         var4 = false;
      } else {
         Iterator var5 = var2.iterator();

         while(true) {
            if(var5.hasNext()) {
               RunningTaskInfo var6 = (RunningTaskInfo)var5.next();
               if(!var6.topActivity.getClassName().equals("com.android.settings.wifi.WifiSettings") && !var6.topActivity.getClassName().equals("com.android.settings.wifi.AdvancedSettings") && !var6.topActivity.getClassName().equals("com.android.settings.wifi.WifiDirectSettings")) {
                  continue;
               }

               var4 = true;
               break;
            }

            var4 = false;
            break;
         }
      }

      return var4;
   }

   private boolean isWirelessSettingScreen(Context var1) {
      Iterator var2 = ((ActivityManager)var1.getSystemService("activity")).getRunningTasks(1).iterator();

      boolean var3;
      while(true) {
         if(var2.hasNext()) {
            if(!((RunningTaskInfo)var2.next()).topActivity.getClassName().equals("com.android.settings.WirelessSettings")) {
               continue;
            }

            var3 = true;
            break;
         }

         var3 = false;
         break;
      }

      return var3;
   }

   public void onReceive(Context var1, Intent var2) {
      WifiManager var3 = (WifiManager)var1.getSystemService("wifi");
      String var4 = var2.getAction();
      String var5 = "action: " + var4;
      int var6 = Log.i("NetworkStatusReceiver", var5);
      if("android.net.wifi.WIFI_AP_STATE_CHANGED".equals(var4)) {
         boolean var7 = var2.getBooleanExtra("wifi_enable_soon", (boolean)0);
         String var8 = "EXTRA_WIFI_ENABLE_SOON: " + var7;
         int var9 = Log.d("NetworkStatusReceiver", var8);
         if(var7) {
            ;
         }
      } else if("android.net.wifi.SHOW_AP_LIST_DIALOG".equals(var4)) {
         if(!this.isWifiSettingScreen(var1) && var3.showApDialog()) {
            List var10 = var3.getScanResults();
            if(var10 == null) {
               int var11 = Log.d("NetworkStatusReceiver", "ScanResults is null");
            } else {
               StringBuilder var17 = (new StringBuilder()).append("ScanResults has ");
               int var18 = var10.size();
               String var19 = var17.append(var18).append(" items").toString();
               int var20 = Log.d("NetworkStatusReceiver", var19);
            }

            if(!var10.isEmpty()) {
               int var12 = Log.i("NetworkStatusReceiver", "Show the dialog");
               Intent var13 = new Intent();
               var13.setClass(var1, AccessPointListDialog.class);
               Intent var15 = var13.setFlags(268435456);
               var1.startActivity(var13);
               boolean var16 = var3.setShowAccessPointListDialog((boolean)0);
            }
         } else {
            boolean var21 = var3.setShowAccessPointListDialog((boolean)0);
         }
      } else if("android.net.wifidirect.PROVISION_DISCOVERY_REQ".equals(var4)) {
         int var22 = Log.i("NetworkStatusReceiver", "DIRECT_PROVISION_REQUEST_ACTION");
         if(!Boolean.parseBoolean(SystemProperties.get("ril.cdma.inecmmode"))) {
            String var23 = var2.getStringExtra("cfg_method");
            String var24 = var2.getStringExtra("dev_address");
            String var25 = var2.getStringExtra("dev_ssid");
            String var26 = var2.getStringExtra("PIN_number");
            String var27 = "cfg: " + var23 + ", addr: " + var24 + ", ssid: " + var25 + ", pin: " + var26;
            int var28 = Log.d("NetworkStatusReceiver", var27);
            Intent var29 = new Intent();
            var29.setClass(var1, WifiDirectWpsDialog.class);
            var29.putExtra("cfg_method", var23);
            var29.putExtra("dev_address", var24);
            var29.putExtra("dev_ssid", var25);
            var29.putExtra("PIN_number", var26);
            var29.putExtra("DialogType", var4);
            Intent var36 = var29.setFlags(268435456);
            var1.startActivity(var29);
         } else {
            int var37 = Log.i("NetworkStatusReceiver", "CBM Mode enabled!!");
         }
      } else if("android.net.wifidirect.STA_DISASSOC".equals(var4)) {
         String var38 = var2.getStringExtra("sta_number");
         String var39 = "DIRECT_STA_DISASSOC_ACTION " + var38;
         int var40 = Log.i("NetworkStatusReceiver", var39);
         if("0".equals(var38)) {
            boolean var41 = var3.disconnectWifiDirectPeer((String)null);
         }
      }
   }
}
