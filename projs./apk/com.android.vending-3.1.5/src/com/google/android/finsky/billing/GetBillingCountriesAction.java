package com.google.android.finsky.billing;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.List;

public class GetBillingCountriesAction {

   public GetBillingCountriesAction() {}

   private boolean canSkip() {
      long var1 = ((Long)BillingPreferences.LAST_BILLING_COUNTRIES_REFRESH_MILLIS.get()).longValue();
      long var3 = System.currentTimeMillis();
      boolean var5;
      if(!this.enoughTimePassed(var3, var1)) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   boolean enoughTimePassed(long var1, long var3) {
      long var5 = ((Long)G.vendingBillingCountriesRefreshMs.get()).longValue() + var3;
      boolean var7;
      if(var1 > var5) {
         var7 = true;
      } else {
         var7 = false;
      }

      return var7;
   }

   public void run(String var1, Runnable var2) {
      if(this.canSkip()) {
         if(var2 != null) {
            var2.run();
         }

         Object[] var3 = new Object[0];
         FinskyLog.d("Skip getting fresh list of billing countries.", var3);
      } else {
         VendingApi var4 = FinskyApp.get().getVendingApi(var1);
         GetBillingCountriesAction.1 var5 = new GetBillingCountriesAction.1(var2);
         GetBillingCountriesAction.2 var6 = new GetBillingCountriesAction.2(var2);
         var4.getBillingCountries(var5, var6);
      }
   }

   class 2 implements Response.ErrorListener {

      // $FF: synthetic field
      final Runnable val$finishRunnable;


      2(Runnable var2) {
         this.val$finishRunnable = var2;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Object[] var4 = new Object[]{var1, var2};
         FinskyLog.w("%s PurchaseMetadataRequest failed: %s", var4);
         if(this.val$finishRunnable != null) {
            this.val$finishRunnable.run();
         }
      }
   }

   class 1 implements Response.Listener<List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country>> {

      // $FF: synthetic field
      final Runnable val$finishRunnable;


      1(Runnable var2) {
         this.val$finishRunnable = var2;
      }

      public void onResponse(List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country> var1) {
         BillingLocator.setBillingCountries(var1);
         Object[] var2 = new Object[1];
         Integer var3 = Integer.valueOf(var1.size());
         var2[0] = var3;
         FinskyLog.d("Received and stored %d billing countries.", var2);
         if(this.val$finishRunnable != null) {
            this.val$finishRunnable.run();
         }
      }
   }
}
