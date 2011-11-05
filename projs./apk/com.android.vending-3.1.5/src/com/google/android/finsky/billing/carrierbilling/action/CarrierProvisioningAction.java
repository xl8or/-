package com.google.android.finsky.billing.carrierbilling.action;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.billing.BillingEventRecorder;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.billing.carrierbilling.api.DcbApi;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingParameters;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingStorage;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.utils.FinskyLog;

public class CarrierProvisioningAction {

   private final DcbApi mDcbApi;
   private final CarrierBillingStorage mDcbStorage;


   public CarrierProvisioningAction() {
      CarrierBillingStorage var1 = BillingLocator.getCarrierBillingStorage();
      DcbApi var2 = BillingLocator.createDcbApi();
      this(var1, var2);
   }

   public CarrierProvisioningAction(CarrierBillingStorage var1, DcbApi var2) {
      this.mDcbStorage = var1;
      this.mDcbApi = var2;
   }

   private void fetchProvisioning(String var1, Runnable var2, Runnable var3) {
      CarrierBillingParameters var4 = this.mDcbStorage.getParams();
      if(var4 == null) {
         var3.run();
      } else {
         String var5 = var4.getId();
         CarrierProvisioningAction.1 var7 = new CarrierProvisioningAction.1(var5, var2);
         CarrierProvisioningAction.2 var9 = new CarrierProvisioningAction.2(var5, var3);
         this.mDcbApi.getProvisioning(var1, var7, var9);
         long var11 = ((Long)G.vendingCarrierProvisioningRefreshFrequencyMs.get()).longValue();
         long var13 = ((Long)G.vendingCarrierProvisioningRetryMs.get()).longValue();
         long var15 = Math.min(var11, var13);
         long var17 = System.currentTimeMillis();
         PreferenceFile.SharedPreference var19 = BillingPreferences.EARLIEST_PROVISIONING_CHECK_TIME_MILLIS;
         Long var20 = Long.valueOf(var17 + var15);
         var19.put(var20);
         PreferenceFile.SharedPreference var21 = BillingPreferences.LAST_PROVISIONING_TIME_MILLIS;
         Long var22 = Long.valueOf(var17);
         var21.put(var22);
      }
   }

   public void forceRun(Runnable var1, Runnable var2) {
      this.fetchProvisioning((String)null, var1, var2);
   }

   public void forceRun(Runnable var1, Runnable var2, String var3) {
      this.fetchProvisioning(var3, var1, var2);
   }

   public void run(Runnable var1) {
      long var2 = System.currentTimeMillis();
      long var4 = SystemClock.elapsedRealtime();
      long var6 = ((Long)BillingPreferences.LAST_PROVISIONING_TIME_MILLIS.get()).longValue();
      long var8 = ((Long)BillingPreferences.EARLIEST_PROVISIONING_CHECK_TIME_MILLIS.get()).longValue();
      if(this.shouldFetchProvisioning(var2, var4, var6, var8)) {
         String var10 = (String)BillingPreferences.ACCEPTED_CARRIER_TOS_VERSION.get();
         this.fetchProvisioning(var10, var1, var1);
      } else {
         if(var1 != null) {
            var1.run();
         }

         Object[] var11 = new Object[0];
         FinskyLog.d("No need to fetch provisioning from carrier.", var11);
      }
   }

   public void runIfNotOnWifi(Context var1, Runnable var2) {
      if(((ConnectivityManager)var1.getSystemService("connectivity")).getNetworkInfo(1).isConnectedOrConnecting()) {
         Object[] var3 = new Object[0];
         FinskyLog.d("Wifi interface active. Skipping DCB provisioning check.", var3);
         if(var2 != null) {
            var2.run();
         }
      } else {
         this.run(var2);
      }
   }

   boolean shouldFetchProvisioning(long var1, long var3, long var5, long var7) {
      CarrierBillingParameters var9 = this.mDcbStorage.getParams();
      boolean var11;
      if(var9 != null && var9.getGetProvisioningUrl() != null) {
         boolean var12;
         if(this.mDcbStorage.getProvisioning() != null) {
            var12 = true;
         } else {
            var12 = false;
         }

         boolean var13;
         if(var1 - var5 > var3) {
            var13 = true;
         } else {
            var13 = false;
         }

         boolean var14;
         if(var1 > var7) {
            var14 = true;
         } else {
            var14 = false;
         }

         if(var14) {
            var11 = true;
         } else if(!var12 && var13) {
            var11 = true;
         } else {
            var11 = false;
         }
      } else {
         Object[] var10 = new Object[0];
         FinskyLog.d("Required CarrierBillingParams missing. Shouldn\'t fetch provisioning.", var10);
         var11 = false;
      }

      return var11;
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
         Object[] var4 = new Object[]{var1, var2};
         FinskyLog.d("CarrierProvisioningAction encountered an error (%s): %s", var4);
         long var5 = System.currentTimeMillis();
         long var7 = ((Long)G.vendingCarrierProvisioningRetryMs.get()).longValue();
         PreferenceFile.SharedPreference var9 = BillingPreferences.EARLIEST_PROVISIONING_CHECK_TIME_MILLIS;
         Long var10 = Long.valueOf(var5 + var7);
         var9.put(var10);
         BillingEventRecorder.recordError(this.val$carrierId, 0, var1, var2);
         if(this.val$errorRunnable != null) {
            this.val$errorRunnable.run();
         }
      }
   }

   class 1 implements Response.Listener<CarrierBillingProvisioning> {

      // $FF: synthetic field
      final String val$carrierId;
      // $FF: synthetic field
      final Runnable val$successRunnable;


      1(String var2, Runnable var3) {
         this.val$carrierId = var2;
         this.val$successRunnable = var3;
      }

      public void onResponse(CarrierBillingProvisioning var1) {
         long var2 = System.currentTimeMillis();
         if(var1 == null) {
            Object[] var4 = new Object[0];
            FinskyLog.w("Fetching provisioning returned null.", var4);
            long var5 = ((Long)G.vendingCarrierProvisioningRetryMs.get()).longValue();
            PreferenceFile.SharedPreference var7 = BillingPreferences.EARLIEST_PROVISIONING_CHECK_TIME_MILLIS;
            Long var8 = Long.valueOf(var2 + var5);
            var7.put(var8);
            String var9 = this.val$carrierId;
            Response.ErrorCode var10 = Response.ErrorCode.SERVER;
            BillingEventRecorder.recordError(var9, 0, var10, "Got null response for getProvisioning");
         } else {
            long var11 = ((Long)G.vendingCarrierProvisioningRefreshFrequencyMs.get()).longValue();
            PreferenceFile.SharedPreference var13 = BillingPreferences.EARLIEST_PROVISIONING_CHECK_TIME_MILLIS;
            Long var14 = Long.valueOf(var2 + var11);
            var13.put(var14);
            CarrierProvisioningAction.this.mDcbStorage.setProvisioning(var1);
            String var15 = this.val$carrierId;
            boolean var16 = var1.isProvisioned();
            BillingEventRecorder.recordSuccess(var15, 0, var16);
         }

         if(this.val$successRunnable != null) {
            this.val$successRunnable.run();
         }
      }
   }
}
