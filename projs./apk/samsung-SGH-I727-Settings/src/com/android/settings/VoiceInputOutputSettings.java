package com.android.settings;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings.Secure;
import java.util.HashMap;
import java.util.List;

public class VoiceInputOutputSettings extends PreferenceActivity implements OnPreferenceChangeListener {

   private static final String KEY_PARENT = "parent";
   private static final String KEY_RECOGNIZER = "recognizer";
   private static final String KEY_RECOGNIZER_SETTINGS = "recognizer_settings";
   private static final String KEY_VOICE_INPUT_CATEGORY = "voice_input_category";
   private static final String TAG = "VoiceInputOutputSettings";
   private HashMap<String, ResolveInfo> mAvailableRecognizersMap;
   private PreferenceGroup mParent;
   private ListPreference mRecognizerPref;
   private PreferenceScreen mSettingsPref;
   private PreferenceCategory mVoiceInputCategory;


   public VoiceInputOutputSettings() {}

   private void populateOrRemoveRecognizerPreference() {
      PackageManager var1 = this.getPackageManager();
      Intent var2 = new Intent("android.speech.RecognitionService");
      List var3 = var1.queryIntentServices(var2, 128);
      int var4 = var3.size();
      if(var4 == 0) {
         PreferenceCategory var5 = this.mVoiceInputCategory;
         this.removePreference(var5);
         ListPreference var6 = this.mRecognizerPref;
         this.removePreference(var6);
         PreferenceScreen var7 = this.mSettingsPref;
         this.removePreference(var7);
      } else if(var4 == 1) {
         ListPreference var8 = this.mRecognizerPref;
         this.removePreference(var8);
         ResolveInfo var9 = (ResolveInfo)var3.get(0);
         String var10 = var9.serviceInfo.packageName;
         String var11 = var9.serviceInfo.name;
         String var12 = (new ComponentName(var10, var11)).flattenToShortString();
         this.mAvailableRecognizersMap.put(var12, var9);
         String var14 = Secure.getString(this.getContentResolver(), "voice_recognition_service");
         this.updateSettingsLink(var14);
      } else {
         this.populateRecognizerPreference(var3);
      }
   }

   private void populateRecognizerPreference(List<ResolveInfo> var1) {
      int var2 = var1.size();
      CharSequence[] var3 = new CharSequence[var2];
      CharSequence[] var4 = new CharSequence[var2];
      String var5 = Secure.getString(this.getContentResolver(), "voice_recognition_service");

      for(int var6 = 0; var6 < var2; ++var6) {
         ResolveInfo var7 = (ResolveInfo)var1.get(var6);
         String var8 = var7.serviceInfo.packageName;
         String var9 = var7.serviceInfo.name;
         String var10 = (new ComponentName(var8, var9)).flattenToShortString();
         this.mAvailableRecognizersMap.put(var10, var7);
         PackageManager var12 = this.getPackageManager();
         CharSequence var13 = var7.loadLabel(var12);
         var3[var6] = var13;
         var4[var6] = var10;
      }

      this.mRecognizerPref.setEntries(var3);
      this.mRecognizerPref.setEntryValues(var4);
      this.mRecognizerPref.setDefaultValue(var5);
      this.mRecognizerPref.setValue(var5);
      this.updateSettingsLink(var5);
   }

   private void removePreference(Preference var1) {
      if(var1 != null) {
         this.mParent.removePreference(var1);
      }
   }

   private void updateSettingsLink(String param1) {
      // $FF: Couldn't be decompiled
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968622);
      PreferenceGroup var2 = (PreferenceGroup)this.findPreference("parent");
      this.mParent = var2;
      PreferenceCategory var3 = (PreferenceCategory)this.mParent.findPreference("voice_input_category");
      this.mVoiceInputCategory = var3;
      ListPreference var4 = (ListPreference)this.mParent.findPreference("recognizer");
      this.mRecognizerPref = var4;
      this.mRecognizerPref.setOnPreferenceChangeListener(this);
      PreferenceScreen var5 = (PreferenceScreen)this.mParent.findPreference("recognizer_settings");
      this.mSettingsPref = var5;
      HashMap var6 = new HashMap();
      this.mAvailableRecognizersMap = var6;
      this.populateOrRemoveRecognizerPreference();
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      ListPreference var3 = this.mRecognizerPref;
      if(var1.equals(var3)) {
         String var4 = (String)var2;
         boolean var5 = Secure.putString(this.getContentResolver(), "voice_recognition_service", var4);
         this.updateSettingsLink(var4);
      }

      return true;
   }
}
