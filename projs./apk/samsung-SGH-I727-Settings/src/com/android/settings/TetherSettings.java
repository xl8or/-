package com.android.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings.System;
import android.util.Log;
import android.webkit.WebView;
import com.android.settings.Utils;
import com.android.settings.wifi.WifiApEnabler;
import com.android.settings.wifi.WifiApSettings;
import com.android.settings.wifi.WifiApSettings_Help;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class TetherSettings extends PreferenceActivity {

   private static final int DIALOG_DISCONNECT_WIFI = 4;
   private static final int DIALOG_NO_SIM = 2;
   private static final int DIALOG_TETHER_HELP = 1;
   private static final int DIALOG_TURN_ON = 9;
   public static final String HELP_NOT_SHOW_AGAIN = "ap_help_not_show_again";
   private static final String HELP_PATH = "html/%y%z/tethering_help.html";
   private static final String HELP_URL = "file:///android_asset/html/%y%z/tethering_%xhelp.html";
   private static final String TAG = "TetherSettings";
   private static final String TETHERING_HELP = "tethering_help";
   private static final String USB_HELP_MODIFIER = "usb_";
   private static final String USB_TETHER_SETTINGS = "usb_tether_settings";
   private static final String WIFI_AP_SETTINGS = "wifi_ap_settings";
   private static final String WIFI_HELP_MODIFIER = "wifi_";
   private ContentResolver mContentResolver;
   private BroadcastReceiver mTetherChangeReceiver;
   private PreferenceScreen mTetherHelp;
   private ArrayList mUsbIfaces;
   private String[] mUsbRegexs;
   private CheckBoxPreference mUsbTether;
   private WebView mView;
   private WifiApEnabler mWifiApEnabler;
   private PreferenceScreen mWifiApSettings;
   private String[] mWifiRegexs;


   public TetherSettings() {}

   private String findIface(String[] var1, String[] var2) {
      String[] var3 = var1;
      int var4 = var1.length;

      String var11;
      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         String[] var7 = var2;
         int var8 = var2.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String var10 = var7[var9];
            if(var6.matches(var10)) {
               var11 = var6;
               return var11;
            }
         }
      }

      var11 = null;
      return var11;
   }

   private void updateState() {
      ConnectivityManager var1 = (ConnectivityManager)this.getSystemService("connectivity");
      String[] var2 = var1.getTetherableIfaces();
      String[] var3 = var1.getTetheredIfaces();
      String[] var4 = var1.getTetheringErroredIfaces();
      this.updateState(var2, var3, var4);
   }

   private void updateState(Object[] var1, Object[] var2, Object[] var3) {
      String var5 = "connectivity";
      ConnectivityManager var6 = (ConnectivityManager)this.getSystemService(var5);
      boolean var7 = false;
      boolean var8 = false;
      int var9 = 0;
      boolean var10 = false;
      String var11 = Environment.getExternalStorageState();
      boolean var12 = "shared".equals(var11);
      Object[] var13 = var1;
      int var14 = var1.length;

      for(int var15 = 0; var15 < var14; ++var15) {
         String var16 = (String)var13[var15];
         String[] var17 = this.mUsbRegexs;
         int var18 = var17.length;

         for(int var19 = 0; var19 < var18; ++var19) {
            String var20 = var17[var19];
            if(var16.matches(var20)) {
               var8 = true;
               if(var9 == 0) {
                  var9 = var6.getLastTetherError(var16);
               }
            }
         }
      }

      Object[] var21 = var2;
      int var22 = var2.length;

      for(int var23 = 0; var23 < var22; ++var23) {
         String var24 = (String)var21[var23];
         String[] var25 = this.mUsbRegexs;
         int var26 = var25.length;

         for(int var27 = 0; var27 < var26; ++var27) {
            String var28 = var25[var27];
            if(var24.matches(var28)) {
               var7 = true;
            }
         }
      }

      Object[] var29 = var3;
      int var30 = var3.length;

      for(int var31 = 0; var31 < var30; ++var31) {
         String var32 = (String)var29[var31];
         String[] var33 = this.mUsbRegexs;
         int var34 = var33.length;

         for(int var35 = 0; var35 < var34; ++var35) {
            String var36 = var33[var35];
            if(var32.matches(var36)) {
               var10 = true;
            }
         }
      }

      if(var7) {
         this.mUsbTether.setSummary(2131231490);
         this.mUsbTether.setEnabled((boolean)1);
         this.mUsbTether.setChecked((boolean)1);
      } else if(var8) {
         if(var9 == 0) {
            this.mUsbTether.setSummary(2131231489);
         } else {
            this.mUsbTether.setSummary(2131231493);
         }

         this.mUsbTether.setEnabled((boolean)1);
         this.mUsbTether.setChecked((boolean)0);
      } else if(var10) {
         this.mUsbTether.setSummary(2131231493);
         this.mUsbTether.setEnabled((boolean)0);
         this.mUsbTether.setChecked((boolean)0);
      } else if(var12) {
         this.mUsbTether.setSummary(2131231491);
         this.mUsbTether.setEnabled((boolean)0);
         this.mUsbTether.setChecked((boolean)0);
      } else {
         this.mUsbTether.setSummary(2131231492);
         this.mUsbTether.setEnabled((boolean)0);
         this.mUsbTether.setChecked((boolean)0);
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968618);
      PreferenceScreen var2 = (PreferenceScreen)this.findPreference("wifi_ap_settings");
      this.mWifiApSettings = var2;
      CheckBoxPreference var3 = (CheckBoxPreference)this.findPreference("usb_tether_settings");
      this.mUsbTether = var3;
      PreferenceScreen var4 = (PreferenceScreen)this.findPreference("tethering_help");
      this.mTetherHelp = var4;
      ConnectivityManager var5 = (ConnectivityManager)this.getSystemService("connectivity");
      String[] var6 = var5.getTetherableUsbRegexs();
      this.mUsbRegexs = var6;
      if(this.mUsbRegexs.length == 0 || Utils.isMonkeyRunning()) {
         PreferenceScreen var7 = this.getPreferenceScreen();
         CheckBoxPreference var8 = this.mUsbTether;
         var7.removePreference(var8);
         this.setTitle(2131231482);
      }

      String[] var10 = var5.getTetherableWifiRegexs();
      this.mWifiRegexs = var10;
      if(this.mWifiRegexs.length != 0) {
         if(this.mUsbRegexs.length != 0) {
            this.setTitle(2131231483);
         }
      } else {
         PreferenceScreen var12 = this.getPreferenceScreen();
         PreferenceScreen var13 = this.mWifiApSettings;
         var12.removePreference(var13);
         this.setTitle(2131231481);
      }

      WebView var11 = new WebView(this);
      this.mView = var11;
      this.updateState();
   }

   protected Dialog onCreateDialog(int var1) {
      AlertDialog var23;
      if(var1 == 1) {
         Locale var2 = Locale.getDefault();
         AssetManager var3 = this.getAssets();
         String var4 = var2.getLanguage().toLowerCase();
         String var5 = "html/%y%z/tethering_help.html".replace("%y", var4);
         StringBuilder var6 = (new StringBuilder()).append("_");
         String var7 = var2.getCountry().toLowerCase();
         String var8 = var6.append(var7).toString();
         String var9 = var5.replace("%z", var8);
         boolean var10 = true;
         InputStream var11 = null;
         boolean var37 = false;

         label127: {
            InputStream var12;
            label126: {
               try {
                  var37 = true;
                  var12 = var3.open(var9);
                  var37 = false;
                  break label126;
               } catch (Exception var41) {
                  var37 = false;
               } finally {
                  if(var37) {
                     if(var11 != null) {
                        try {
                           var11.close();
                        } catch (Exception var38) {
                           int var31 = Log.w("TetherSettings", var38);
                        }
                     }

                  }
               }

               var10 = false;
               if(var11 != null) {
                  try {
                     var11.close();
                  } catch (Exception var39) {
                     int var28 = Log.w("TetherSettings", var39);
                  }
               }
               break label127;
            }

            var11 = var12;
            if(var12 != null) {
               try {
                  var11.close();
               } catch (Exception var40) {
                  int var25 = Log.w("TetherSettings", var40);
               }
            }
         }

         String var13 = var2.getLanguage().toLowerCase();
         String var14 = "file:///android_asset/html/%y%z/tethering_%xhelp.html".replace("%y", var13);
         String var15 = "%z";
         String var18;
         if(var10) {
            StringBuilder var16 = (new StringBuilder()).append("_");
            String var17 = var2.getCountry().toLowerCase();
            var18 = var16.append(var17).toString();
         } else {
            var18 = "";
         }

         String var19 = var14.replace(var15, var18);
         String var20;
         if(this.mUsbRegexs.length != 0 && this.mWifiRegexs.length == 0) {
            var20 = var19.replace("%x", "usb_");
         } else if(this.mWifiRegexs.length != 0 && this.mUsbRegexs.length == 0) {
            var20 = var19.replace("%x", "wifi_");
         } else {
            var20 = var19.replace("%x", "");
         }

         this.mView.loadUrl(var20);
         Builder var21 = (new Builder(this)).setCancelable((boolean)1).setTitle(2131231496);
         WebView var22 = this.mView;
         var23 = var21.setView(var22).create();
      } else {
         var23 = null;
      }

      return var23;
   }

   protected void onPause() {
      super.onPause();
      BroadcastReceiver var1 = this.mTetherChangeReceiver;
      this.unregisterReceiver(var1);
      this.mTetherChangeReceiver = null;
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      CheckBoxPreference var3 = this.mUsbTether;
      boolean var9;
      if(var2.equals(var3)) {
         boolean var4 = this.mUsbTether.isChecked();
         ConnectivityManager var5 = (ConnectivityManager)this.getSystemService("connectivity");
         String var8;
         if(var4) {
            String[] var6 = var5.getTetherableIfaces();
            String[] var7 = this.mUsbRegexs;
            var8 = this.findIface(var6, var7);
            if(var8 == null) {
               this.updateState();
               var9 = true;
               return var9;
            }

            if(var5.tether(var8) != 0) {
               this.mUsbTether.setChecked((boolean)0);
               this.mUsbTether.setSummary(2131231493);
               var9 = true;
               return var9;
            }

            this.mUsbTether.setSummary("");
         } else {
            String[] var10 = var5.getTetheredIfaces();
            String[] var11 = this.mUsbRegexs;
            var8 = this.findIface(var10, var11);
            if(var8 == null) {
               this.updateState();
               var9 = true;
               return var9;
            }

            if(var5.untether(var8) != 0) {
               this.mUsbTether.setSummary(2131231493);
               var9 = true;
               return var9;
            }

            this.mUsbTether.setSummary("");
         }
      } else {
         PreferenceScreen var12 = this.mTetherHelp;
         if(var2.equals(var12)) {
            this.showDialog(1);
         } else {
            PreferenceScreen var13 = this.mWifiApSettings;
            if(var2.equals(var13)) {
               int var15;
               label32: {
                  int var14;
                  try {
                     var14 = System.getInt(this.mContentResolver, "ap_help_not_show_again", 0);
                  } catch (NullPointerException var20) {
                     var15 = 0;
                     break label32;
                  }

                  var15 = var14;
               }

               Intent var16 = new Intent("android.intent.action.VIEW");
               if(var15 == 0) {
                  var16.setClass(this, WifiApSettings_Help.class);
                  this.startActivity(var16);
               } else {
                  var16.setClass(this, WifiApSettings.class);
                  this.startActivity(var16);
               }
            }
         }
      }

      var9 = false;
      return var9;
   }

   protected void onResume() {
      super.onResume();
      IntentFilter var1 = new IntentFilter("android.net.conn.TETHER_STATE_CHANGED");
      TetherSettings.TetherChangeReceiver var2 = new TetherSettings.TetherChangeReceiver((TetherSettings.1)null);
      this.mTetherChangeReceiver = var2;
      BroadcastReceiver var3 = this.mTetherChangeReceiver;
      Intent var4 = this.registerReceiver(var3, var1);
      IntentFilter var5 = new IntentFilter("android.net.wifi.WIFI_AP_STATE_CHANGED");
      BroadcastReceiver var6 = this.mTetherChangeReceiver;
      Intent var7 = this.registerReceiver(var6, var5);
      IntentFilter var8 = new IntentFilter();
      var8.addAction("android.intent.action.MEDIA_SHARED");
      var8.addAction("android.intent.action.MEDIA_UNSHARED");
      var8.addDataScheme("file");
      BroadcastReceiver var9 = this.mTetherChangeReceiver;
      this.registerReceiver(var9, var8);
      if(var4 != null) {
         this.mTetherChangeReceiver.onReceive(this, var4);
      }

      if(var7 != null) {
         this.mTetherChangeReceiver.onReceive(this, var7);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class TetherChangeReceiver extends BroadcastReceiver {

      private TetherChangeReceiver() {}

      // $FF: synthetic method
      TetherChangeReceiver(TetherSettings.1 var2) {
         this();
      }

      public void onReceive(Context var1, Intent var2) {
         if(var2.getAction().equals("android.net.conn.TETHER_STATE_CHANGED")) {
            ArrayList var3 = var2.getStringArrayListExtra("availableArray");
            ArrayList var4 = var2.getStringArrayListExtra("activeArray");
            ArrayList var5 = var2.getStringArrayListExtra("erroredArray");
            TetherSettings var6 = TetherSettings.this;
            Object[] var7 = var3.toArray();
            Object[] var8 = var4.toArray();
            Object[] var9 = var5.toArray();
            var6.updateState(var7, var8, var9);
         } else if(var2.getAction().equals("android.intent.action.MEDIA_SHARED") || var2.getAction().equals("android.intent.action.MEDIA_UNSHARED")) {
            TetherSettings.this.updateState();
         }
      }
   }
}
