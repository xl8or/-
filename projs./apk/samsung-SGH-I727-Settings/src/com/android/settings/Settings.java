package com.android.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.settings.AirplaneModeEnabler;
import com.android.settings.Utils;

public class Settings extends PreferenceActivity {

   private static final String KEY_CALL_SETTINGS = "call_settings";
   private static final String KEY_DOCK_SETTINGS = "dock_settings";
   private static final String KEY_DOCOMO_SERVICE = "docomo_service";
   private static final String KEY_PARENT = "parent";
   private static final String KEY_SOFTWARE_UPDATE_SETTINGS = "software_update";
   private static final String KEY_SYNC_SETTINGS = "sync_settings";
   private BroadcastReceiver mIntentReceiver;


   public Settings() {
      Settings.1 var1 = new Settings.1();
      this.mIntentReceiver = var1;
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968612);
      int var2 = TelephonyManager.getDefault().getPhoneType();
      PreferenceGroup var3 = (PreferenceGroup)this.findPreference("parent");
      Utils.updatePreferenceToSpecificActivityOrRemove(this, var3, "sync_settings", 0);
      Preference var5 = var3.findPreference("dock_settings");
      if(!this.getResources().getBoolean(2131099649) && var5 != null) {
         var3.removePreference(var5);
      }

      Preference var7 = this.findPreference("docomo_service");
      if(var7 != null) {
         var3.removePreference(var7);
      }
   }

   protected void onPause() {
      BroadcastReceiver var1 = this.mIntentReceiver;
      this.unregisterReceiver(var1);
      super.onPause();
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      String var3 = var2.getKey();
      byte var4 = super.onPreferenceTreeClick(var1, var2);
      if(var3 != null && var3.equals("software_update")) {
         Intent var5 = new Intent("android.intent.action.SOFTWARE_UPDATE_SETTING");
         this.sendBroadcast(var5);
         var4 = 1;
      }

      return (boolean)var4;
   }

   protected void onResume() {
      super.onResume();
      Preference var1 = this.findPreference("call_settings");
      byte var2;
      if(!AirplaneModeEnabler.isAirplaneModeOn(this)) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      var1.setEnabled((boolean)var2);
      IntentFilter var3 = new IntentFilter();
      var3.addAction("android.intent.action.AIRPLANE_MODE");
      BroadcastReceiver var4 = this.mIntentReceiver;
      this.registerReceiver(var4, var3, (String)null, (Handler)null);
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         Boolean var4 = Boolean.valueOf(var2.getBooleanExtra("state", (boolean)0));
         String var5 = "onReceive() : " + var3 + ", " + var4;
         int var6 = Log.i("Settings", var5);
         if(var3.equals("android.intent.action.AIRPLANE_MODE")) {
            if(var2.getBooleanExtra("state", (boolean)0)) {
               Settings.this.findPreference("call_settings").setEnabled((boolean)0);
            } else {
               Settings.this.findPreference("call_settings").setEnabled((boolean)1);
            }
         }
      }
   }
}
