package com.google.android.finsky.billing.iab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.billing.iab.MarketBillingService;
import com.google.android.finsky.utils.FinskyLog;

public class BillingTickleReceiver extends BroadcastReceiver {

   public BillingTickleReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      String var3 = var2.getStringExtra("asset_package");
      this.setResultCode(-1);
      if(var2.hasCategory("com.android.vending.billing.IN_APP_NOTIFY")) {
         String var4 = var2.getStringExtra("notification_id");
         if(!MarketBillingService.sendNotify(var1, var3, var4)) {
            this.setResultCode(0);
         }
      } else {
         Object[] var5 = new Object[]{var3};
         FinskyLog.w("Intent does not contain a supported category; package: %s", var5);
         this.setResultCode(0);
      }
   }
}
