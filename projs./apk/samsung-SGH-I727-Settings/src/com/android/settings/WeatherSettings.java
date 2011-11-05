package com.android.settings;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings.System;
import android.util.Log;

public class WeatherSettings extends PreferenceActivity implements OnPreferenceChangeListener {

   public static final String ACTION_SEC_CHANGE_SETTING = "com.sec.android.widgetapp.accuweatherdaemon.action.CHANGE_SETTING";
   public static final String KEY_AUTOREFRESH_INTERVAL = "aw_daemon_service_key_autorefresh_interval";
   public static final String KEY_CITY_NAME = "aw_daemon_service_key_city_name";
   private static final String KEY_REFRESH = "auto_refresh";
   private static final String KEY_SELECT_CITY = "select_city";
   public static final String KEY_TEMP_SCALE = "aw_daemon_service_key_temp_scale";
   private static final String KEY_UNIT = "unit";
   public static final int REQCODE_SEARCHCITY = 33;
   private static final String TAG = "WeatherSettings";
   private ListPreference mRefresh;
   private PreferenceScreen mSelectCity;
   private ListPreference mUnit;


   public WeatherSettings() {}

   private void updateState(boolean var1) {
      ListPreference var2 = this.mUnit;
      byte var3;
      if(System.getInt(this.getContentResolver(), "aw_daemon_service_key_temp_scale", 1) != 0) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      var2.setValueIndex(var3);
      ListPreference var4 = this.mUnit;
      CharSequence var5 = this.mUnit.getEntry();
      var4.setSummary(var5);
      PreferenceScreen var6 = this.mSelectCity;
      String var7 = System.getString(this.getContentResolver(), "aw_daemon_service_key_city_name");
      var6.setSummary(var7);
      ListPreference var8 = this.mRefresh;
      int var9 = System.getInt(this.getContentResolver(), "aw_daemon_service_key_autorefresh_interval", 0);
      var8.setValueIndex(var9);
      ListPreference var10 = this.mRefresh;
      CharSequence var11 = this.mRefresh.getEntry();
      var10.setSummary(var11);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if(var2 != -1) {
         int var4 = Log.e("WeatherSettings", "Select City fail");
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968626);
      ListPreference var2 = (ListPreference)this.findPreference("unit");
      this.mUnit = var2;
      this.mUnit.setOnPreferenceChangeListener(this);
      String[] var3 = new String[]{"°C", "°F"};
      this.mUnit.setEntries(var3);
      String[] var4 = new String[]{"0", "1"};
      this.mUnit.setEntryValues(var4);
      PreferenceScreen var5 = (PreferenceScreen)this.findPreference("select_city");
      this.mSelectCity = var5;
      ListPreference var6 = (ListPreference)this.findPreference("auto_refresh");
      this.mRefresh = var6;
      this.mRefresh.setOnPreferenceChangeListener(this);
   }

   public boolean onPreferenceChange(Preference param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      PreferenceScreen var3 = this.mSelectCity;
      boolean var9;
      if(var2.equals(var3)) {
         Intent var4 = new Intent("android.intent.action.MAIN", (Uri)null);
         Intent var5 = var4.addCategory("android.intent.category.LAUNCHER");
         Intent var6 = var4.putExtra("LAUNCH_MODE", "COLLAB_MODE");
         ComponentName var7 = new ComponentName("com.sec.android.daemonapp.accuweather", "com.sec.android.daemonapp.accuweather.MenuAddActivity");
         var4.setComponent(var7);
         this.startActivityForResult(var4, 33);
         var9 = false;
      } else {
         var9 = true;
      }

      return var9;
   }

   protected void onResume() {
      super.onResume();
      this.updateState((boolean)1);
   }
}
