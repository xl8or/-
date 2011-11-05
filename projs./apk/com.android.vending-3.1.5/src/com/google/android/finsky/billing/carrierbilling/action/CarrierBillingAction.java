package com.google.android.finsky.billing.carrierbilling.action;

import com.google.android.finsky.billing.BillingLocator;

public class CarrierBillingAction {

   public CarrierBillingAction() {}

   private boolean canSkip() {
      return BillingLocator.getCarrierBillingStorage().isInit();
   }

   public void run(Runnable var1) {
      if(this.canSkip()) {
         var1.run();
      } else {
         BillingLocator.initCarrierBillingStorage(var1);
      }
   }
}
