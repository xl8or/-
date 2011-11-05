package com.android.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.settings.AirplaneModeEnabler;
import com.android.settings.bluetooth.BluetoothEnabler;
import com.android.settings.nfc.NfcEnabler;
import com.android.settings.wifi.WifiEnabler;

public class WirelessSettings extends PreferenceActivity implements OnClickListener, OnDismissListener {

   public static final String EXIT_ECM_RESULT = "exit_ecm_result";
   private static final String KEY_BT_SETTINGS = "bt_settings";
   private static final String KEY_MOBILE_NETWORKS = "mobile_networks";
   private static final String KEY_SYNCHRONISE = "synchronise";
   private static final String KEY_TETHER_SETTINGS = "tether_settings";
   private static final String KEY_TOGGLE_AIRPLANE = "toggle_airplane";
   private static final String KEY_TOGGLE_NFC = "toggle_nfc";
   private static final String KEY_USB_SETTINGS = "usb_settings";
   private static final String KEY_VPN_SETTINGS = "vpn_settings";
   private static final String KEY_WIFI_SETTINGS = "wifi_settings";
   private static final String KEY_WIMAX_SETTINGS = "wimax_settings";
   private static final String LOG_TAG = "WirelessSettings";
   public static final int REQUEST_CODE_EXIT_ECM = 1;
   private AirplaneModeEnabler mAirplaneModeEnabler;
   private CheckBoxPreference mAirplaneModePreference;
   private BluetoothEnabler mBtEnabler;
   private NfcEnabler mNfcEnabler;
   private TelephonyManager mTelMan;
   private Dialog mUSBDialog;
   private WifiEnabler mWifiEnabler;


   public WirelessSettings() {}

   private void dismissDialog() {
      if(this.mUSBDialog != null) {
         this.mUSBDialog.dismiss();
         this.mUSBDialog = null;
      }
   }

   private void enableDataPreference() {
      String var1 = SystemProperties.get("gsm.sim.operator.numeric");
      String var2 = SystemProperties.get("ril.currentplmn");
      if("45005".equals(var1) && !"oversea".equals(var2)) {
         this.findPreference("data_network_settings_key").setEnabled((boolean)1);
      } else {
         this.findPreference("data_network_settings_key").setEnabled((boolean)0);
      }
   }

   public static boolean isRadioAllowed(Context var0, String var1) {
      boolean var2;
      if(!AirplaneModeEnabler.isAirplaneModeOn(var0)) {
         var2 = true;
      } else {
         String var3 = System.getString(var0.getContentResolver(), "airplane_mode_toggleable_radios");
         if(var3 != null && var3.contains(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 == 1) {
         Boolean var4 = Boolean.valueOf(var3.getBooleanExtra("exit_ecm_result", (boolean)0));
         AirplaneModeEnabler var5 = this.mAirplaneModeEnabler;
         boolean var6 = var4.booleanValue();
         boolean var7 = this.mAirplaneModePreference.isChecked();
         var5.setAirplaneModeInECM(var6, var7);
      }
   }

   public void onClick(DialogInterface var1, int var2) {
      if(var2 == -1) {
         boolean var3 = Secure.putInt(this.getContentResolver(), "adb_enabled", 0);
         Intent var4 = new Intent();
         Intent var5 = var4.setAction("android.intent.action.MAIN");
         Intent var6 = var4.setClassName("com.android.settings", "com.android.settings.UsbSettings");
         this.startActivity(var4);
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968634);
      PreferenceScreen var2 = this.getPreferenceScreen();
      Preference var3 = this.findPreference("synchronise");
      var2.removePreference(var3);
      CheckBoxPreference var5 = (CheckBoxPreference)this.findPreference("toggle_airplane");
      CheckBoxPreference var6 = (CheckBoxPreference)this.findPreference("toggle_nfc");
      AirplaneModeEnabler var7 = new AirplaneModeEnabler(this, var5);
      this.mAirplaneModeEnabler = var7;
      CheckBoxPreference var8 = (CheckBoxPreference)this.findPreference("toggle_airplane");
      this.mAirplaneModePreference = var8;
      NfcEnabler var9 = new NfcEnabler(this, var6);
      this.mNfcEnabler = var9;
      String var10 = System.getString(this.getContentResolver(), "airplane_mode_toggleable_radios");
      boolean var11 = this.getResources().getBoolean(17629220);
      if(!var11) {
         PreferenceScreen var12 = this.getPreferenceScreen();
         Preference var13 = this.findPreference("wimax_settings");
         if(var13 != null) {
            var12.removePreference(var13);
         }
      } else if(var10 == null || !var10.contains("wimax") && var11) {
         this.findPreference("wimax_settings").setDependency("toggle_airplane");
      }

      if(var10 == null || !var10.contains("wifi")) {
         this.findPreference("wifi_settings").setDependency("toggle_airplane");
         this.findPreference("vpn_settings").setDependency("toggle_airplane");
      }

      if(NfcAdapter.getDefaultAdapter(this) == null) {
         boolean var15 = this.getPreferenceScreen().removePreference(var6);
      }

      if("SGH-I777".equals("SGH-I727") || "SGH-I727".equals("SGH-I727")) {
         boolean var16 = this.getPreferenceScreen().removePreference(var6);
      }

      ConnectivityManager var17 = (ConnectivityManager)this.getSystemService("connectivity");
      if(!var17.isTetheringSupported()) {
         PreferenceScreen var18 = this.getPreferenceScreen();
         Preference var19 = this.findPreference("tether_settings");
         var18.removePreference(var19);
      } else {
         String[] var24 = var17.getTetherableUsbRegexs();
         String[] var25 = var17.getTetherableWifiRegexs();
         Preference var26 = this.findPreference("tether_settings");
         if(var25.length != 0) {
            if(var24.length == 0) {
               var26.setTitle(2131231482);
               var26.setSummary(2131231485);
            } else {
               var26.setTitle(2131231483);
               var26.setSummary(2131231486);
            }
         } else {
            var26.setTitle(2131231481);
            var26.setSummary(2131231484);
         }
      }

      PreferenceScreen var21 = this.getPreferenceScreen();
      Preference var22 = this.findPreference("kies_via_wifi");
      var21.removePreference(var22);
   }

   protected void onDestroy() {
      this.dismissDialog();
      super.onDestroy();
   }

   public void onDismiss(DialogInterface var1) {}

   protected void onPause() {
      super.onPause();
      this.mAirplaneModeEnabler.pause();
      this.mNfcEnabler.pause();
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      String var3 = var2.getKey();
      String var4 = "onPreferenceTreeClick : key[" + var3 + "]";
      int var5 = Log.d("WirelessSettings", var4);
      boolean var20;
      if("mobile_networks".equals(var3)) {
         int var6 = Log.d("WirelessSettings", "onPreferenceTreeClick : preference.getKey() == mobile_networks");
         TelephonyManager var7 = TelephonyManager.getDefault();
         this.mTelMan = var7;
         if(this.mTelMan.getSimState() == 0 || this.mTelMan.getSimState() == 1) {
            Builder var8 = new Builder(this);
            Builder var9 = var8.setCancelable((boolean)0);
            String var10 = this.getString(2131232069);
            var8.setTitle(var10);
            String var12 = this.getString(2131232070);
            var8.setMessage(var12);
            WirelessSettings.1 var14 = new WirelessSettings.1();
            var8.setNegativeButton(2131231632, var14);
            Builder var16 = var8.setCancelable((boolean)1);
            WirelessSettings.2 var17 = new WirelessSettings.2();
            var8.setOnCancelListener(var17);
            AlertDialog var19 = var8.show();
            var20 = true;
            return var20;
         }
      }

      if("synchronise".equals(var3)) {
         Intent var21 = new Intent();
         Intent var22 = var21.setAction("android.intent.action.MAIN");
         Intent var23 = var21.setClassName("com.smlds", "com.smlds.smluiMenuList_eu_open");
         Intent var24 = var21.setFlags(268435456);
         this.startActivity(var21);
      }

      if(this.findPreference("usb_settings") == var2) {
         Intent var25 = new Intent();
         Intent var26 = var25.setAction("android.intent.action.MAIN");
         Intent var27 = var25.setClassName("com.android.settings", "com.android.settings.UsbSettings");
         this.startActivity(var25);
      }

      CheckBoxPreference var28 = this.mAirplaneModePreference;
      if(var2.equals(var28) && Boolean.parseBoolean(SystemProperties.get("ril.cdma.inecmmode"))) {
         Intent var29 = new Intent("android.intent.action.ACTION_SHOW_NOTICE_ECM_BLOCK_OTHERS", (Uri)null);
         this.startActivityForResult(var29, 1);
         var20 = true;
      } else {
         var20 = false;
      }

      return var20;
   }

   protected void onResume() {
      super.onResume();
      this.mAirplaneModeEnabler.resume();
      this.mNfcEnabler.resume();

      try {
         if(Secure.getInt(this.getContentResolver(), "bluetooth_enabled", 1) == 0) {
            int var1 = Log.d("WirelessSettings", "BT is Disabled");
            if(ServiceManager.getService("bluetooth") != null) {
               this.findPreference("bt_settings").setEnabled((boolean)0);
            }
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      try {
         if(Secure.getInt(this.getContentResolver(), "wifi_enabled", 1) == 0) {
            int var2 = Log.d("WirelessSettings", "WiFi is Disabled");
            this.findPreference("wifi_settings").setEnabled((boolean)0);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   class 2 implements OnCancelListener {

      2() {}

      public void onCancel(DialogInterface var1) {}
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {}
   }
}
