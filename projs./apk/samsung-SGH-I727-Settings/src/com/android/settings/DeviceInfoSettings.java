package com.android.settings;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Build.VERSION;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.provider.Settings.Secure;
import android.util.Log;
import com.android.internal.app.PlatLogoActivity;
import com.android.settings.Utils;
import java.util.List;

public class DeviceInfoSettings extends PreferenceActivity implements OnClickListener, OnDismissListener {

   private static final String KEY_CONTAINER = "container";
   private static final String KEY_CONTRIBUTORS = "contributors";
   private static final String KEY_COPYRIGHT = "copyright";
   private static final String KEY_LICENSE = "license";
   private static final String KEY_SYSTEM_TUTORIAL = "system_tutorial";
   private static final String KEY_SYSTEM_UPDATE_SETTINGS = "system_update_settings";
   private static final String KEY_TEAM = "team";
   private static final String KEY_TERMS = "terms";
   private static final String KEY_UPDATE_SETTING = "additional_system_update_settings";
   private static final String PROPERTY_URL_SAFETYLEGAL = "ro.url.safetylegal";
   private static final String TAG = "DeviceInfoSettings";
   long[] mHits;
   private String mUnAvailable = "Unavailable";


   public DeviceInfoSettings() {
      Object var1 = null;
      this.mHits = (long[])var1;
   }

   private String getFormattedKernelVersion() {
      // $FF: Couldn't be decompiled
   }

   private void removePreferenceIfPropertyMissing(PreferenceGroup var1, String var2, String var3) {
      if(SystemProperties.get(var3).equals("")) {
         try {
            Preference var4 = this.findPreference(var2);
            var1.removePreference(var4);
         } catch (RuntimeException var9) {
            String var7 = "Property \'" + var3 + "\' missing and no \'" + var2 + "\' preference";
            int var8 = Log.d("DeviceInfoSettings", var7);
         }
      }
   }

   private void setStringSummary(String var1, String var2) {
      try {
         this.findPreference(var1).setSummary(var2);
      } catch (RuntimeException var6) {
         Preference var4 = this.findPreference(var1);
         String var5 = this.getResources().getString(2131230724);
         var4.setSummary(var5);
      }
   }

   private void setValueSummary(String var1, String var2) {
      try {
         Preference var3 = this.findPreference(var1);
         String var4 = this.getResources().getString(2131230724);
         String var5 = SystemProperties.get(var2, var4);
         var3.setSummary(var5);
      } catch (RuntimeException var9) {
         Preference var7 = this.findPreference(var1);
         String var8 = this.getResources().getString(2131230724);
         var7.setSummary(var8);
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
      this.addPreferencesFromResource(2130968590);
      String var2 = Secure.getString(this.getContentResolver(), "default_input_method");
      ComponentName var3 = ComponentName.unflattenFromString(var2);
      List var4 = null;
      if(var3 != null) {
         StringBuilder var5 = new StringBuilder();
         String var6 = var3.getPackageName();
         String var7 = var5.append(var6).append(".tutorial").toString();
         Intent var8 = new Intent(var7);
         var4 = this.getPackageManager().queryIntentActivities(var8, 0);
      }

      if(var4 == null || var4.isEmpty()) {
         PreferenceScreen var9 = this.getPreferenceScreen();
         Preference var10 = this.findPreference("system_tutorial");
         var9.removePreference(var10);
      }

      String var12 = VERSION.RELEASE;
      this.setStringSummary("firmware_version", var12);
      this.findPreference("firmware_version").setEnabled((boolean)1);
      this.setValueSummary("baseband_version", "gsm.version.baseband");
      String var13 = Build.MODEL;
      this.setStringSummary("device_model", var13);
      String var14 = Build.DISPLAY;
      this.setStringSummary("build_number", var14);
      Preference var15 = this.findPreference("kernel_version");
      String var16 = this.getFormattedKernelVersion();
      var15.setSummary(var16);
      PreferenceScreen var17 = this.getPreferenceScreen();
      this.removePreferenceIfPropertyMissing(var17, "safetylegal", "ro.url.safetylegal");
      if("com.sec.android.inputmethod.iwnnime.japan/.iWnnIME".equals(var2) == 1) {
         Preference var18 = this.findPreference("system_tutorial");
         if(var18 != null) {
            boolean var19 = this.getPreferenceScreen().removePreference(var18);
         }
      }

      PreferenceGroup var20 = (PreferenceGroup)this.findPreference("container");
      Utils.updatePreferenceToSpecificActivityOrRemove(this, var20, "terms", 1);
      Utils.updatePreferenceToSpecificActivityOrRemove(this, var20, "license", 1);
      Utils.updatePreferenceToSpecificActivityOrRemove(this, var20, "copyright", 1);
      Utils.updatePreferenceToSpecificActivityOrRemove(this, var20, "team", 1);
      PreferenceScreen var25 = this.getPreferenceScreen();
      Utils.updatePreferenceToSpecificActivityOrRemove(this, var25, "contributors", 1);
      Preference var27 = this.findPreference("system_update_settings");
      if(var27 != null) {
         var25.removePreference(var27);
      }

      Preference var29 = this.findPreference("system_tutorial");
      if(var29 != null) {
         var25.removePreference(var29);
      }

      if(!this.getResources().getBoolean(2131099650)) {
         PreferenceScreen var31 = this.getPreferenceScreen();
         Preference var32 = this.findPreference("additional_system_update_settings");
         var31.removePreference(var32);
      }
   }

   protected void onDestroy() {
      super.onDestroy();
   }

   public void onDismiss(DialogInterface var1) {}

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      if(var2.getKey().equals("firmware_version")) {
         long[] var3 = this.mHits;
         long[] var4 = this.mHits;
         int var5 = this.mHits.length - 1;
         System.arraycopy(var3, 1, var4, 0, var5);
         long[] var6 = this.mHits;
         int var7 = this.mHits.length - 1;
         long var8 = SystemClock.uptimeMillis();
         var6[var7] = var8;
         long var10 = this.mHits[0];
         long var12 = SystemClock.uptimeMillis() - 500L;
         if(var10 >= var12) {
            Intent var14 = new Intent("android.intent.action.MAIN");
            String var15 = PlatLogoActivity.class.getName();
            var14.setClassName("android", var15);

            try {
               this.startActivity(var14);
            } catch (Exception var20) {
               int var19 = Log.d("DeviceInfoSettings", "StartActivity failed.. Please try again");
            }
         }
      }

      if(this.findPreference("system_update_settings") == var2) {
         Intent var17 = new Intent("android.intent.action.SOFTWARE_UPDATE_SETTING");
         this.sendBroadcast(var17);
      }

      return super.onPreferenceTreeClick(var1, var2);
   }
}
