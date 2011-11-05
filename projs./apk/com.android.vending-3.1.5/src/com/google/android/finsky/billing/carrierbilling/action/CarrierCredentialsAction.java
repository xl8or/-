package com.google.android.finsky.billing.carrierbilling.action;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.billing.BillingEventRecorder;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.carrierbilling.CarrierBillingUtils;
import com.google.android.finsky.billing.carrierbilling.api.DcbApi;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingCredentials;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingParameters;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingStorage;
import com.google.android.finsky.utils.FinskyLog;

public class CarrierCredentialsAction {

   private final CarrierBillingStorage mDcbStorage;


   public CarrierCredentialsAction() {
      CarrierBillingStorage var1 = BillingLocator.getCarrierBillingStorage();
      this(var1);
   }

   public CarrierCredentialsAction(CarrierBillingStorage var1) {
      this.mDcbStorage = var1;
   }

   public void run(String var1, String var2, Runnable var3, Runnable var4) {
      DcbApi var5 = BillingLocator.createDcbApi();
      CarrierBillingParameters var6 = this.mDcbStorage.getParams();
      if(var6 == null) {
         var4.run();
      } else {
         String var7 = var6.getId();
         CarrierCredentialsAction.1 var8 = new CarrierCredentialsAction.1(var7, var3);
         CarrierCredentialsAction.2 var9 = new CarrierCredentialsAction.2(var7, var4);
         var5.getCredentials(var1, var2, var8, var9);
      }
   }

   class 1 implements Response.Listener<CarrierBillingCredentials> {

      // $FF: synthetic field
      final String val$carrierId;
      // $FF: synthetic field
      final Runnable val$successRunnable;


      1(String var2, Runnable var3) {
         this.val$carrierId = var2;
         this.val$successRunnable = var3;
      }

      public void onResponse(CarrierBillingCredentials var1) {
         if(var1 != null) {
            CarrierCredentialsAction.this.mDcbStorage.setCredentials(var1);
            String var2 = this.val$carrierId;
            boolean var3 = CarrierBillingUtils.areCredentialsValid(CarrierCredentialsAction.this.mDcbStorage);
            BillingEventRecorder.recordSuccess(var2, 1, var3);
         } else {
            Object[] var4 = new Object[0];
            FinskyLog.d("Fetching credentials returned null.", var4);
            String var5 = this.val$carrierId;
            Response.ErrorCode var6 = Response.ErrorCode.SERVER;
            BillingEventRecorder.recordError(var5, 1, var6, "Got null response for getCredentials");
         }

         if(this.val$successRunnable != null) {
            this.val$successRunnable.run();
         }
      }
   }

   class 2 implements Response.ErrorListener {

      // $FF: synthetic field
      final String val$carrierId;
      // $FF: synthetic field
      final Runnable val$errorRunnable;


      2(String var2, Runnable var3) {
         this.val$carrierId = var2;
         this.val$errorRunnable = var3;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Object[] var4 = new Object[]{var2};
         FinskyLog.d("Fetching credentials returned an error: %s", var4);
         BillingEventRecorder.recordError(this.val$carrierId, 1, var1, var2);
         if(this.val$errorRunnable != null) {
            this.val$errorRunnable.run();
         }
      }
   }
}
