package com.android.email.service;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.email.Email;
import com.android.email.ExchangeUtils;
import com.android.email.Preferences;
import com.android.email.VendorPolicyLoader;
import com.android.email.service.EasAuthenticatorService;
import com.android.email.service.EasAuthenticatorServiceAlternate;
import com.android.email.service.MailService;

public class EmailBroadcastProcessorService extends IntentService {

   public EmailBroadcastProcessorService() {
      String var1 = EmailBroadcastProcessorService.class.getName();
      super(var1);
      this.setIntentRedelivery((boolean)1);
   }

   private void enableComponentsIfNecessary() {
      if(Email.setServicesEnabled(this)) {
         MailService.actionReschedule(this);
      }
   }

   private void onBootCompleted() {
      int var1 = Log.d("Email", "BOOT_COMPLETED");
      this.performOneTimeInitialization();
      this.enableComponentsIfNecessary();
      ExchangeUtils.startExchangeService(this);
   }

   private void performOneTimeInitialization() {
      Preferences var1 = Preferences.getPreferences(this);
      int var2 = var1.getOneTimeInitializationProgress();
      int var3 = var2;
      if(var2 < 1) {
         int var4 = Log.i("Email", "Onetime initialization: 1");
         var2 = 1;
         if(VendorPolicyLoader.getInstance(this).useAlternateExchangeStrings()) {
            this.setComponentEnabled(EasAuthenticatorServiceAlternate.class, (boolean)1);
            this.setComponentEnabled(EasAuthenticatorService.class, (boolean)0);
         }

         ExchangeUtils.enableEasCalendarSync(this);
      }

      if(var2 != var3) {
         var1.setOneTimeInitializationProgress(var2);
         int var5 = Log.i("Email", "Onetime initialization: completed.");
      }
   }

   public static void processBroadcastIntent(Context var0, Intent var1) {
      Intent var2 = new Intent(var0, EmailBroadcastProcessorService.class);
      var2.putExtra("android.intent.extra.INTENT", var1);
      var0.startService(var2);
   }

   private void setComponentEnabled(Class<?> var1, boolean var2) {
      String var3 = var1.getName();
      ComponentName var4 = new ComponentName(this, var3);
      PackageManager var5 = this.getPackageManager();
      byte var6;
      if(var2) {
         var6 = 1;
      } else {
         var6 = 2;
      }

      var5.setComponentEnabledSetting(var4, var6, 1);
   }

   protected void onHandleIntent(Intent var1) {
      String var2 = ((Intent)var1.getParcelableExtra("android.intent.extra.INTENT")).getAction();
      if("android.intent.action.BOOT_COMPLETED".equals(var2)) {
         this.onBootCompleted();
      } else if("android.intent.action.DEVICE_STORAGE_LOW".equals(var2)) {
         MailService.actionCancel(this);
      } else if("android.intent.action.DEVICE_STORAGE_OK".equals(var2)) {
         this.enableComponentsIfNecessary();
      }
   }
}
