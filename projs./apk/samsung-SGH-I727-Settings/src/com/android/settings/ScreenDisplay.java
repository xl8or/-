package com.android.settings;

import android.content.ContentResolver;
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

public class ScreenDisplay extends PreferenceActivity implements OnPreferenceChangeListener {

   public static final String ACTION_SEC_CHANGE_SETTING = "com.sec.android.widgetapp.accuweatherdaemon.action.CHANGE_SETTING";
   private static final String KEY_CLOCK = "clock";
   private static final String KEY_CLOCK_POSITION = "clock_position";
   private static final String KEY_HOME_SCREEN_WALLPAPER = "homescreen_wallpaper";
   private static final String KEY_LOCK_SCREEN_SHORTCUTS = "lockscreen_shortcuts";
   private static final String KEY_LOCK_SCREEN_SHORTCUTS_SETTING = "lockscreen_shortcuts_setting";
   private static final String KEY_LOCK_SCREEN_WALLPAPER = "lockscreen_wallpaper";
   public static final String KEY_SERVICE_STATUS = "aw_daemon_service_key_service_status";
   private static final String KEY_WEATHER = "weather";
   private static final String KEY_WEATHER_Settings = "watehr_settings";
   private static final String TAG = "ScreenDisplay";
   private CheckBoxPreference mClock;
   private ListPreference mClockPosition;
   private PreferenceScreen mHomeScreenWallpaper;
   private PreferenceScreen mLockScreenShortcutsSetting;
   private PreferenceScreen mLockScreenWallpaper;
   private CheckBoxPreference mShortcuts;
   private CheckBoxPreference mWeather;
   private PreferenceScreen mWeatherSettings;


   public ScreenDisplay() {}

   private void updateState() {
      CheckBoxPreference var1 = this.mShortcuts;
      byte var2;
      if(System.getInt(this.getContentResolver(), "lockscreen_shortcuts", 0) != 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      var1.setChecked((boolean)var2);
      PreferenceScreen var3 = this.mLockScreenShortcutsSetting;
      boolean var4 = this.mShortcuts.isChecked();
      var3.setEnabled(var4);
      CheckBoxPreference var5 = this.mClock;
      byte var6;
      if(System.getInt(this.getContentResolver(), "show_clock", 0) != 0) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      var5.setChecked((boolean)var6);
      CheckBoxPreference var7 = this.mWeather;
      byte var8;
      if(System.getInt(this.getContentResolver(), "aw_daemon_service_key_service_status", 0) != 0) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      var7.setChecked((boolean)var8);
      ListPreference var9 = this.mClockPosition;
      String var10 = String.valueOf(System.getInt(this.getContentResolver(), "clock_position", 0));
      var9.setValue(var10);
      ListPreference var11 = this.mClockPosition;
      CharSequence var12 = this.mClockPosition.getEntry();
      var11.setSummary(var12);
   }

   protected void onCreate(Bundle var1) {
      int var2 = Log.e("ScreenDisplay", "onCreate()");
      super.onCreate(var1);
      ContentResolver var3 = this.getContentResolver();
      this.addPreferencesFromResource(2130968605);
      CheckBoxPreference var4 = (CheckBoxPreference)this.findPreference("lockscreen_shortcuts");
      this.mShortcuts = var4;
      CheckBoxPreference var5 = (CheckBoxPreference)this.findPreference("clock");
      this.mClock = var5;
      this.mClock.setPersistent((boolean)0);
      CheckBoxPreference var6 = (CheckBoxPreference)this.findPreference("weather");
      this.mWeather = var6;
      this.mWeather.setPersistent((boolean)0);
      ListPreference var7 = (ListPreference)this.findPreference("clock_position");
      this.mClockPosition = var7;
      this.mClockPosition.setOnPreferenceChangeListener(this);
      PreferenceScreen var8 = (PreferenceScreen)this.findPreference("homescreen_wallpaper");
      this.mHomeScreenWallpaper = var8;
      PreferenceScreen var9 = (PreferenceScreen)this.findPreference("lockscreen_wallpaper");
      this.mLockScreenWallpaper = var9;
      PreferenceScreen var10 = (PreferenceScreen)this.findPreference("lockscreen_shortcuts_setting");
      this.mLockScreenShortcutsSetting = var10;
      PreferenceScreen var11 = (PreferenceScreen)this.findPreference("watehr_settings");
      this.mWeatherSettings = var11;
      if(!"GT-I9220".equalsIgnoreCase("SGH-I727")) {
         PreferenceScreen var12 = this.getPreferenceScreen();
         CheckBoxPreference var13 = this.mShortcuts;
         var12.removePreference(var13);
         PreferenceScreen var15 = this.getPreferenceScreen();
         PreferenceScreen var16 = this.mLockScreenShortcutsSetting;
         var15.removePreference(var16);
      }

      PreferenceScreen var18 = this.getPreferenceScreen();
      CheckBoxPreference var19 = this.mClock;
      var18.removePreference(var19);
      PreferenceScreen var21 = this.getPreferenceScreen();
      CheckBoxPreference var22 = this.mWeather;
      var21.removePreference(var22);
      PreferenceScreen var24 = this.getPreferenceScreen();
      PreferenceScreen var25 = this.mWeatherSettings;
      var24.removePreference(var25);
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      String var3 = var1.getKey();
      if("clock_position".equals(var3)) {
         int var4 = Integer.parseInt((String)var2);

         try {
            boolean var5 = System.putInt(this.getContentResolver(), "clock_position", var4);
            ListPreference var6 = this.mClockPosition;
            String var7 = String.valueOf(var4);
            var6.setValue(var7);
            ListPreference var8 = this.mClockPosition;
            CharSequence var9 = this.mClockPosition.getEntry();
            var8.setSummary(var9);
         } catch (NumberFormatException var12) {
            int var11 = Log.e("ScreenDisplay", "could not persist Clock position value", var12);
         }
      }

      return true;
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      String var3 = "lockscreen_shortcuts";
      PreferenceScreen var4 = this.mHomeScreenWallpaper;
      boolean var8;
      if(var2.equals(var4)) {
         Intent var5 = new Intent("android.intent.action.SET_WALLPAPER");
         String var6 = this.getString(2131232175);
         Intent var7 = Intent.createChooser(var5, var6);
         this.startActivity(var7);
         var8 = false;
      } else {
         PreferenceScreen var9 = this.mLockScreenWallpaper;
         if(var2.equals(var9)) {
            var8 = false;
         } else {
            CheckBoxPreference var10 = this.mClock;
            byte var26;
            if(var2.equals(var10)) {
               ContentResolver var11 = this.getContentResolver();
               String var12 = "show_clock";
               if(this.mClock.isChecked()) {
                  var26 = 1;
               } else {
                  var26 = 0;
               }

               System.putInt(var11, var12, var26);
            } else {
               CheckBoxPreference var14 = this.mWeather;
               if(var2.equals(var14)) {
                  ContentResolver var15 = this.getContentResolver();
                  String var16 = "aw_daemon_service_key_service_status";
                  if(this.mWeather.isChecked()) {
                     var26 = 1;
                  } else {
                     var26 = 0;
                  }

                  System.putInt(var15, var16, var26);
                  Intent var18 = new Intent("com.sec.android.widgetapp.accuweatherdaemon.action.CHANGE_SETTING");
                  this.sendBroadcast(var18);
               } else {
                  PreferenceScreen var19 = this.mWeatherSettings;
                  if(var2.equals(var19)) {
                     var8 = false;
                     return var8;
                  }

                  CheckBoxPreference var20 = this.mShortcuts;
                  if(var2.equals(var20)) {
                     if(this.mShortcuts.isChecked()) {
                        boolean var21 = System.putInt(this.getContentResolver(), "lockscreen_shortcuts", 1);
                     } else {
                        boolean var24 = System.putInt(this.getContentResolver(), "lockscreen_shortcuts", 0);
                     }

                     PreferenceScreen var22 = this.mLockScreenShortcutsSetting;
                     boolean var23 = this.mShortcuts.isChecked();
                     var22.setEnabled(var23);
                  } else {
                     PreferenceScreen var25 = this.mLockScreenShortcutsSetting;
                     if(var2.equals(var25)) {
                        var8 = false;
                        return var8;
                     }
                  }
               }
            }

            var8 = true;
         }
      }

      return var8;
   }

   protected void onResume() {
      super.onResume();
      this.updateState();
   }
}
