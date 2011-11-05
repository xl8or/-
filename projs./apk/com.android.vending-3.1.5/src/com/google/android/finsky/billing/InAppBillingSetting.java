package com.google.android.finsky.billing;

import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.utils.VendingPreferences;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class InAppBillingSetting {

   private static final String PREFERENCES_PREFIX = "IAB_";


   public InAppBillingSetting() {}

   private static PreferenceFile.SharedPreference<Boolean> getSharedPreference(String var0) {
      PreferenceFile var1 = VendingPreferences.getMainPrefs();

      try {
         String var2 = URLEncoder.encode(var0, "UTF-8");
         String var3 = "IAB_" + var2;
         Boolean var4 = Boolean.valueOf((boolean)0);
         PreferenceFile.SharedPreference var5 = var1.value(var3, var4);
         return var5;
      } catch (UnsupportedEncodingException var7) {
         throw new UnsupportedOperationException("Caught exception while encoding IAB status.", var7);
      }
   }

   public static boolean isEnabled(String var0) {
      boolean var1 = false;
      if(var0 != null) {
         PreferenceFile.SharedPreference var2 = getSharedPreference(var0);
         boolean var3;
         if(var2 != null && ((Boolean)var2.get()).booleanValue()) {
            var3 = true;
         } else {
            var3 = false;
         }

         var1 = var3;
      }

      return var1;
   }

   public static void setEnabled(String var0, boolean var1) {
      PreferenceFile.SharedPreference var2 = getSharedPreference(var0);
      if(var2 != null) {
         Boolean var3 = Boolean.valueOf(var1);
         var2.put(var3);
      }
   }
}
