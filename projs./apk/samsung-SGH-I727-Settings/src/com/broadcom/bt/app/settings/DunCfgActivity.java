package com.broadcom.bt.app.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class DunCfgActivity extends PreferenceActivity {

   public static final String TAG = "ServiceCfgActivity";


   public DunCfgActivity() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968586);
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      return false;
   }

   protected void onResume() {
      super.onResume();
   }
}
