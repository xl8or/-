package com.android.settings;

import android.content.ContentResolver;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings.System;

public class PhysicalKeyboardSettings extends PreferenceActivity {

   private final int[] mSettingsDefault;
   private final String[] mSettingsSystemId;
   private final String[] mSettingsUiKey;


   public PhysicalKeyboardSettings() {
      String[] var1 = new String[]{"auto_caps", "auto_replace", "auto_punctuate"};
      this.mSettingsUiKey = var1;
      String[] var2 = new String[]{"auto_caps", "auto_replace", "auto_punctuate"};
      this.mSettingsSystemId = var2;
      int[] var3 = new int[]{1, 1, 1};
      this.mSettingsDefault = var3;
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968598);
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      int var3 = 0;

      byte var11;
      while(true) {
         int var4 = this.mSettingsUiKey.length;
         if(var3 >= var4) {
            var11 = super.onPreferenceTreeClick(var1, var2);
            break;
         }

         String var5 = this.mSettingsUiKey[var3];
         String var6 = var2.getKey();
         if(var5.equals(var6)) {
            ContentResolver var7 = this.getContentResolver();
            String var8 = this.mSettingsSystemId[var3];
            byte var9;
            if(((CheckBoxPreference)var2).isChecked()) {
               var9 = 1;
            } else {
               var9 = 0;
            }

            System.putInt(var7, var8, var9);
            var11 = 1;
            break;
         }

         ++var3;
      }

      return (boolean)var11;
   }

   protected void onResume() {
      super.onResume();
      ContentResolver var1 = this.getContentResolver();
      int var2 = 0;

      while(true) {
         int var3 = this.mSettingsUiKey.length;
         if(var2 >= var3) {
            return;
         }

         String var4 = this.mSettingsUiKey[var2];
         CheckBoxPreference var5 = (CheckBoxPreference)this.findPreference(var4);
         String var6 = this.mSettingsSystemId[var2];
         int var7 = this.mSettingsDefault[var2];
         byte var8;
         if(System.getInt(var1, var6, var7) > 0) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         var5.setChecked((boolean)var8);
         ++var2;
      }
   }
}
