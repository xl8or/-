package com.google.android.finsky.billing.carrierbilling.api;

import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingParameters;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingStorage;

public class DcbApiContext {

   private final CarrierBillingStorage mDcbStorage;
   private final String mLine1Number;
   private final String mSubscriberId;


   public DcbApiContext(CarrierBillingStorage var1, String var2, String var3) {
      this.mDcbStorage = var1;
      this.mLine1Number = var2;
      this.mSubscriberId = var3;
   }

   public CarrierBillingParameters getCarrierBillingParameters() {
      return this.mDcbStorage.getParams();
   }

   public String getLine1Number() {
      return this.mLine1Number;
   }

   public String getSubscriberId() {
      return this.mSubscriberId;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder("[DcbApiContext: ")).append("Line1Number: ");
      String var2 = this.mLine1Number;
      StringBuilder var3 = var1.append(var2).append(", ").append("SubscriberId: ");
      String var4 = this.mSubscriberId;
      return var3.append(var4).append("]").toString();
   }
}
