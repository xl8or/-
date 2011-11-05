package com.google.android.gsf;


public class MobileConnectionSettings {

   public MobileConnectionSettings() {}

   public static String getDeviceId(long var0) {
      StringBuilder var2 = (new StringBuilder()).append("android-");
      String var3 = Long.toHexString(var0);
      return var2.append(var3).toString();
   }
}
