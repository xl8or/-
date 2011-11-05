package com.google.android.vending.remoting.api;

import com.google.android.finsky.config.PreferenceFile;

public class VendingApiPreferences {

   private static PreferenceFile sPrefs = new PreferenceFile("vending_preferences", 0);


   public VendingApiPreferences() {}

   public static PreferenceFile.SharedPreference<Integer> getDeviceConfigurationHashProperty(String var0) {
      PreferenceFile var1 = sPrefs;
      String var2 = "device_configuration_hash_" + var0;
      Integer var3 = Integer.valueOf(-1);
      return var1.value(var2, var3);
   }
}
