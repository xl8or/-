package com.google.android.finsky.billing.carrierbilling;

import android.accounts.Account;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.analytics.DfeAnalytics;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.FopFactory;
import com.google.android.finsky.billing.Instrument;
import com.google.android.finsky.billing.carrierbilling.CarrierBillingUtils;
import com.google.android.finsky.billing.carrierbilling.action.CarrierProvisioningAction;
import com.google.android.finsky.billing.carrierbilling.flow.CompleteCarrierBillingFlow;
import com.google.android.finsky.billing.carrierbilling.flow.CreateCarrierBillingFlow;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingCredentials;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingStorage;
import com.google.android.finsky.config.G;
import com.google.android.finsky.remoting.protos.Buy;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Maps;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CarrierBillingInstrument extends Instrument {

   private static final String REQUEST_CREDENTIALS_TOKEN_KEY = "dcbct";
   private static final String REQUEST_CREDENTIALS_TOKEN_TIMEOUT_KEY = "dcbctt";
   private static final String REQUEST_SIM_IDENTIFIER_KEY = "dcbch";
   private static final String REQUEST_SUBSCRIBER_CURRENCY_KEY = "dcbsc";
   private static final String REQUEST_TRANSACTION_LIMIT_KEY = "dcbtl";


   public CarrierBillingInstrument(Buy.BuyResponse.CheckoutInfo.CheckoutOption var1, Drawable var2) {
      super(var1, var2);
   }

   public static void registerWithFactory(FopFactory var0) {
      CarrierBillingInstrument.1 var1 = new CarrierBillingInstrument.1();
      var0.registerFormOfPayment(2, var1);
   }

   public BillingFlow completePurchase(BillingFlowContext var1, BillingFlowListener var2, Bundle var3) {
      Analytics var4 = FinskyApp.get().getAnalytics();
      return new CompleteCarrierBillingFlow(var1, var2, var4, var3);
   }

   public Map<String, String> getCompleteParams() {
      CarrierBillingCredentials var1 = BillingLocator.getCarrierBillingStorage().getCredentials();
      Map var2;
      if(var1 == null) {
         var2 = null;
      } else {
         HashMap var3 = Maps.newHashMap();
         String var4 = var1.getCredentials();
         var3.put("dcbct", var4);
         Long var6 = Long.valueOf(var1.getExpirationTime());
         if(var6 != null) {
            String var7 = var6.toString();
            var3.put("dcbctt", var7);
         }

         var2 = Collections.unmodifiableMap(var3);
      }

      return var2;
   }

   public boolean isValid() {
      boolean var1;
      if(!CarrierBillingUtils.areCredentialsValid(BillingLocator.getCarrierBillingStorage()) && !CarrierBillingUtils.isRadioNetworkAvailable()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   static class 1 extends FopFactory.FormOfPayment {

      1() {}

      public boolean canAdd() {
         return CarrierBillingUtils.isRadioNetworkAvailable();
      }

      public BillingFlow create(BillingFlowContext var1, BillingFlowListener var2, Bundle var3) {
         String var4 = var3.getString("authAccount");
         FinskyApp var5 = FinskyApp.get();
         Account var6 = AccountHandler.findAccount(var4, var5);
         CreateCarrierBillingFlow var8;
         if(var6 == null) {
            Object[] var7 = new Object[0];
            FinskyLog.e("Invalid account passed in parameters.", var7);
            var8 = null;
         } else {
            DfeApi var9 = FinskyApp.get().getDfeApi(var6);
            FinskyApp var10 = FinskyApp.get();
            String var11 = (String)G.checkoutAuthTokenType.get();
            AndroidAuthenticator var12 = new AndroidAuthenticator(var10, var6, var11);
            Looper var13 = Looper.getMainLooper();
            Handler var14 = new Handler(var13);
            DfeAnalytics var15 = new DfeAnalytics(var14, var9);
            var8 = new CreateCarrierBillingFlow(var1, var2, var12, var9, var15, var3);
         }

         return var8;
      }

      public Instrument get(Buy.BuyResponse.CheckoutInfo.CheckoutOption var1, Drawable var2) {
         FinskyApp var3 = FinskyApp.get();
         Object[] var4 = new Object[1];
         String var5 = BillingLocator.getCarrierBillingStorage().getParams().getName();
         var4[0] = var5;
         String var6 = var3.getString(2131230809, var4);
         var1.setFormOfPayment(var6);
         return new CarrierBillingInstrument(var1, var2);
      }

      public Drawable getAddIcon() {
         return FinskyApp.get().getResources().getDrawable(2130837544);
      }

      public String getAddText() {
         FinskyApp var1 = FinskyApp.get();
         Object[] var2 = new Object[1];
         String var3 = BillingLocator.getCarrierBillingStorage().getParams().getName();
         var2[0] = var3;
         return var1.getString(2131230809, var2);
      }

      public Map<String, String> getPrepareParams() {
         Map var1 = null;
         CarrierBillingStorage var2 = BillingLocator.getCarrierBillingStorage();
         CarrierBillingProvisioning var3 = var2.getProvisioning();
         if(var3 == null) {
            (new CarrierProvisioningAction()).run((Runnable)null);
         } else if(var3.isProvisioned()) {
            HashMap var4 = Maps.newHashMap();
            String var5 = var2.getCurrentSimIdentifier();
            var4.put("dcbch", var5);
            Long var7 = Long.valueOf(var3.getTransactionLimit());
            if(var7 != null) {
               String var8 = var7.toString();
               var4.put("dcbtl", var8);
            }

            String var10 = var3.getSubscriberCurrency();
            if(var10 != null) {
               var4.put("dcbsc", var10);
            }

            var1 = Collections.unmodifiableMap(var4);
         } else {
            Object[] var12 = new Object[0];
            FinskyLog.d("Not provisioned, not including identifier with params", var12);
         }

         return var1;
      }
   }
}
