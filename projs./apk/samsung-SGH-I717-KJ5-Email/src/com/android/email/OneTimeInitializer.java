package com.android.email;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.email.ExchangeUtils;
import com.android.email.Preferences;
import com.android.email.VendorPolicyLoader;
import com.android.email.service.EasAuthenticatorService;
import com.android.email.service.EasAuthenticatorServiceAlternate;

public class OneTimeInitializer extends BroadcastReceiver {

   public OneTimeInitializer() {}

   private void initialize(Context var1) {
      int var2 = Log.d("Email", "OneTimeInitializer: initializing...");
      Preferences var3 = Preferences.getPreferences(var1);
      int var4 = var3.getOneTimeInitializationProgress();
      if(var4 < 1) {
         var4 = 1;
         if(VendorPolicyLoader.getInstance(var1).useAlternateExchangeStrings()) {
            this.setComponentEnabled(var1, EasAuthenticatorServiceAlternate.class, (boolean)1);
            this.setComponentEnabled(var1, EasAuthenticatorService.class, (boolean)0);
         }

         ExchangeUtils.enableEasCalendarSync(var1);
      }

      var3.setOneTimeInitializationProgress(var4);
      Class var5 = this.getClass();
      this.setComponentEnabled(var1, var5, (boolean)0);
   }

   private void setComponentEnabled(Context var1, Class<?> var2, boolean var3) {
      String var4 = var2.getName();
      ComponentName var5 = new ComponentName(var1, var4);
      PackageManager var6 = var1.getPackageManager();
      byte var7;
      if(var3) {
         var7 = 1;
      } else {
         var7 = 2;
      }

      var6.setComponentEnabledSetting(var5, var7, 1);
   }

   public void onReceive(Context var1, Intent var2) {
      String var3 = var2.getAction();
      if("android.intent.action.BOOT_COMPLETED".equals(var3)) {
         this.initialize(var1);
      }
   }
}
