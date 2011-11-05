package com.android.settings.powersavingmode;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings.System;
import android.util.Log;
import com.android.settings.powersavingmode.PowerSavingModeLog;

public class PowerSavingModeSettings extends PreferenceActivity implements OnPreferenceChangeListener {

   public static final String KEY_ADJUST_BRIGHTNESS = "disable_brightness";
   public static final String KEY_BRIGHTNESS_SETTING = "brightness_setting";
   public static final String KEY_DISABLE_BLUETOOTH = "disable_bluetooth";
   public static final String KEY_DISABLE_GPS = "disable_gps";
   public static final String KEY_DISABLE_SYNC = "disable_sync";
   public static final String KEY_DISABLE_WIFI = "disable_wifi";
   public static final String KEY_ENABLE_MODE = "use_power_saving_mode";
   public static final String KEY_ON_BATTERY_LEVEL = "on_battery_level";
   public static final String KEY_SCREENTIMEOUT_SETTING = "screentimeout_setting";
   public static final String NEW_POWER_SAVING_MODE = "new_power_saving_mode";
   public static final String PSM_BATTERY_LEVEL = "psm_battery_level";
   public static final String PSM_BLUETOOTH = "psm_bluetooth";
   public static final String PSM_BRIGHTNESS = "psm_brightness";
   public static final String PSM_BRIGHTNESS_LEVEL = "psm_brightness_level";
   public static final String PSM_GPS = "psm_gps";
   public static final String PSM_SCREEN_TIMEOUT = "psm_screen_timeout";
   public static final String PSM_SYNC = "psm_sync";
   public static final String PSM_WIFI = "psm_wifi";
   private static final String TAG = "PowerSavingModeSettings";
   private CheckBoxPreference mAdjustBrightness;
   private ListPreference mBrightnessSetting;
   private ContentResolver mContentResolver;
   private CheckBoxPreference mDisableBluetooth;
   private CheckBoxPreference mDisableGps;
   private CheckBoxPreference mDisableSync;
   private CheckBoxPreference mDisableWifi;
   private CheckBoxPreference mEnableMode;
   private ListPreference mModeValueSetting;
   private ListPreference mScreentimeout;
   private int psm_battery_level;
   private int psm_brightness_level;
   private int psm_screen_timeout;


   public PowerSavingModeSettings() {}

   private void toggleBrightnessSetting(boolean var1) {
      if(var1 && this.mAdjustBrightness.isChecked()) {
         this.mBrightnessSetting.setEnabled((boolean)1);
      } else {
         this.mBrightnessSetting.setEnabled((boolean)0);
      }
   }

   private void updateUIFromPreferences() {
      int var1 = System.getInt(this.mContentResolver, "psm_battery_level", 50);
      ListPreference var2 = this.mModeValueSetting;
      Context var3 = this.getApplicationContext();
      Object[] var4 = new Object[1];
      StringBuilder var5 = new StringBuilder();
      String var6 = String.valueOf(var1);
      String var7 = var5.append(var6).append("%").toString();
      var4[0] = var7;
      String var8 = var3.getString(2131232375, var4);
      var2.setSummary(var8);
      int var9 = System.getInt(this.mContentResolver, "psm_brightness_level", 10);
      ListPreference var10 = this.mBrightnessSetting;
      Context var11 = this.getApplicationContext();
      Object[] var12 = new Object[1];
      StringBuilder var13 = new StringBuilder();
      String var14 = String.valueOf(var9);
      String var15 = var13.append(var14).append("%").toString();
      var12[0] = var15;
      String var16 = var11.getString(2131232387, var12);
      var10.setSummary(var16);
      boolean var17 = this.mEnableMode.isChecked();
      this.mModeValueSetting.setEnabled(var17);
      this.mDisableWifi.setEnabled(var17);
      this.mDisableBluetooth.setEnabled(var17);
      this.mDisableGps.setEnabled(var17);
      this.mDisableSync.setEnabled(var17);
      this.mAdjustBrightness.setEnabled(var17);
      this.toggleBrightnessSetting(var17);
      this.mScreentimeout.setEnabled(var17);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968601);
      ContentResolver var2 = this.getContentResolver();
      this.mContentResolver = var2;
      CheckBoxPreference var3 = (CheckBoxPreference)this.findPreference("use_power_saving_mode");
      this.mEnableMode = var3;
      ListPreference var4 = (ListPreference)this.findPreference("on_battery_level");
      this.mModeValueSetting = var4;
      this.mModeValueSetting.setOnPreferenceChangeListener(this);
      CheckBoxPreference var5 = (CheckBoxPreference)this.findPreference("disable_wifi");
      this.mDisableWifi = var5;
      CheckBoxPreference var6 = (CheckBoxPreference)this.findPreference("disable_bluetooth");
      this.mDisableBluetooth = var6;
      CheckBoxPreference var7 = (CheckBoxPreference)this.findPreference("disable_gps");
      this.mDisableGps = var7;
      CheckBoxPreference var8 = (CheckBoxPreference)this.findPreference("disable_sync");
      this.mDisableSync = var8;
      CheckBoxPreference var9 = (CheckBoxPreference)this.findPreference("disable_brightness");
      this.mAdjustBrightness = var9;
      ListPreference var10 = (ListPreference)this.findPreference("brightness_setting");
      this.mBrightnessSetting = var10;
      this.mBrightnessSetting.setOnPreferenceChangeListener(this);
      ListPreference var11 = (ListPreference)this.findPreference("screentimeout_setting");
      this.mScreentimeout = var11;
      CheckBoxPreference var12 = this.mEnableMode;
      byte var13;
      if(System.getInt(this.mContentResolver, "new_power_saving_mode", 0) == 1) {
         var13 = 1;
      } else {
         var13 = 0;
      }

      var12.setChecked((boolean)var13);
      CheckBoxPreference var14 = this.mDisableWifi;
      byte var15;
      if(System.getInt(this.mContentResolver, "psm_wifi", 0) == 1) {
         var15 = 1;
      } else {
         var15 = 0;
      }

      var14.setChecked((boolean)var15);
      CheckBoxPreference var16 = this.mDisableBluetooth;
      byte var17;
      if(System.getInt(this.mContentResolver, "psm_bluetooth", 0) == 1) {
         var17 = 1;
      } else {
         var17 = 0;
      }

      var16.setChecked((boolean)var17);
      CheckBoxPreference var18 = this.mDisableGps;
      byte var19;
      if(System.getInt(this.mContentResolver, "psm_gps", 0) == 1) {
         var19 = 1;
      } else {
         var19 = 0;
      }

      var18.setChecked((boolean)var19);
      CheckBoxPreference var20 = this.mDisableSync;
      byte var21;
      if(System.getInt(this.mContentResolver, "psm_sync", 0) == 1) {
         var21 = 1;
      } else {
         var21 = 0;
      }

      var20.setChecked((boolean)var21);
      CheckBoxPreference var22 = this.mAdjustBrightness;
      byte var23;
      if(System.getInt(this.mContentResolver, "psm_brightness", 0) == 1) {
         var23 = 1;
      } else {
         var23 = 0;
      }

      var22.setChecked((boolean)var23);
      this.mModeValueSetting.setOnPreferenceChangeListener(this);
      this.mBrightnessSetting.setOnPreferenceChangeListener(this);
      this.mScreentimeout.setOnPreferenceChangeListener(this);
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      ListPreference var3 = this.mModeValueSetting;
      boolean var15;
      if(var1.equals(var3)) {
         int var4 = Log.d("PowerSavingModeSettings", "set battery level is changed");
         int var5 = Integer.parseInt((String)var2);
         System.putInt(this.mContentResolver, "psm_battery_level", var5);
         ListPreference var7 = this.mModeValueSetting;
         Context var8 = this.getApplicationContext();
         Object[] var9 = new Object[1];
         StringBuilder var10 = new StringBuilder();
         String var11 = var2.toString();
         String var12 = var10.append(var11).append("%").toString();
         var9[0] = var12;
         String var13 = var8.getString(2131232375, var9);
         var7.setSummary(var13);
         Intent var14 = new Intent("android.settings.POWERSAVING_CHANGED");
         this.sendBroadcast(var14);
         var15 = true;
      } else {
         ListPreference var16 = this.mBrightnessSetting;
         if(var1.equals(var16)) {
            int var17 = Log.d("PowerSavingModeSettings", "Brightness level is changed");
            int var18 = Integer.parseInt((String)var2);
            System.putInt(this.mContentResolver, "psm_brightness_level", var18);
            ListPreference var20 = this.mBrightnessSetting;
            Context var21 = this.getApplicationContext();
            Object[] var22 = new Object[1];
            StringBuilder var23 = new StringBuilder();
            String var24 = var2.toString();
            String var25 = var23.append(var24).append("%").toString();
            var22[0] = var25;
            String var26 = var21.getString(2131232387, var22);
            var20.setSummary(var26);
            var15 = true;
         } else {
            ListPreference var27 = this.mScreentimeout;
            if(var1.equals(var27)) {
               int var28 = Log.d("PowerSavingModeSettings", "Screentimeout is changed");
               int var29 = Integer.parseInt((String)var2);
               System.putInt(this.mContentResolver, "psm_screen_timeout", var29);
               var15 = true;
            } else {
               var15 = false;
            }
         }
      }

      return var15;
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      String var3 = "PowerSavingModeSettings";
      if(PowerSavingModeLog.LOGV) {
         PowerSavingModeLog.v("onPreferenceTreeClick");
      }

      CheckBoxPreference var4 = this.mEnableMode;
      String var11;
      boolean var14;
      byte var31;
      if(var2.equals(var4)) {
         int var5 = Log.d("PowerSavingModeSettings", "mEnableMode is selected");
         StringBuilder var6 = (new StringBuilder()).append("mEnableMode : ");
         boolean var7 = this.mEnableMode.isChecked();
         String var8 = var6.append(var7).toString();
         int var9 = Log.d("PowerSavingModeSettings", var8);
         this.updateUIFromPreferences();
         ContentResolver var10 = this.mContentResolver;
         var11 = "new_power_saving_mode";
         if(this.mEnableMode.isChecked()) {
            var31 = 1;
         } else {
            var31 = 0;
         }

         System.putInt(var10, var11, var31);
         Intent var13 = new Intent("android.settings.POWERSAVING_CHANGED");
         this.sendBroadcast(var13);
         var14 = true;
      } else {
         CheckBoxPreference var15 = this.mDisableWifi;
         if(var2.equals(var15)) {
            ContentResolver var16 = this.mContentResolver;
            var11 = "psm_wifi";
            if(this.mDisableWifi.isChecked()) {
               var31 = 1;
            } else {
               var31 = 0;
            }

            System.putInt(var16, var11, var31);
            var14 = true;
         } else {
            CheckBoxPreference var18 = this.mDisableBluetooth;
            if(var2.equals(var18)) {
               ContentResolver var19 = this.mContentResolver;
               var11 = "psm_bluetooth";
               if(this.mDisableBluetooth.isChecked()) {
                  var31 = 1;
               } else {
                  var31 = 0;
               }

               System.putInt(var19, var11, var31);
               var14 = true;
            } else {
               CheckBoxPreference var21 = this.mDisableGps;
               if(var2.equals(var21)) {
                  ContentResolver var22 = this.mContentResolver;
                  var11 = "psm_gps";
                  if(this.mDisableGps.isChecked()) {
                     var31 = 1;
                  } else {
                     var31 = 0;
                  }

                  System.putInt(var22, var11, var31);
                  var14 = true;
               } else {
                  CheckBoxPreference var24 = this.mDisableSync;
                  if(var2.equals(var24)) {
                     ContentResolver var25 = this.mContentResolver;
                     var11 = "psm_sync";
                     if(this.mDisableSync.isChecked()) {
                        var31 = 1;
                     } else {
                        var31 = 0;
                     }

                     System.putInt(var25, var11, var31);
                     var14 = true;
                  } else {
                     CheckBoxPreference var27 = this.mAdjustBrightness;
                     if(var2.equals(var27)) {
                        ContentResolver var28 = this.mContentResolver;
                        var11 = "psm_brightness";
                        if(this.mAdjustBrightness.isChecked()) {
                           var31 = 1;
                        } else {
                           var31 = 0;
                        }

                        System.putInt(var28, var11, var31);
                        boolean var30 = this.mEnableMode.isChecked();
                        this.toggleBrightnessSetting(var30);
                        var14 = true;
                     } else {
                        var14 = false;
                     }
                  }
               }
            }
         }
      }

      return var14;
   }

   public void onResume() {
      super.onResume();
      this.updateUIFromPreferences();
   }
}
