package com.android.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class TestingSettings extends PreferenceActivity {

   public TestingSettings() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968616);
   }
}
