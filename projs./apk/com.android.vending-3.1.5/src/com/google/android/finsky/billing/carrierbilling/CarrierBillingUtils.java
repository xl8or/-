package com.google.android.finsky.billing.carrierbilling;

import android.net.ConnectivityManager;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingCredentials;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingStorage;
import com.google.android.finsky.config.G;

public class CarrierBillingUtils {

   private CarrierBillingUtils() {}

   public static boolean areCredentialsValid(CarrierBillingStorage var0) {
      boolean var1 = false;
      CarrierBillingCredentials var2 = var0.getCredentials();
      if(var2 != null) {
         long var3 = ((Long)G.vendingCarrierCredentialsBufferMs.get()).longValue();
         long var5 = var2.getExpirationTime() - var3;
         long var7 = System.currentTimeMillis();
         boolean var9;
         if(isProvisioned(var0) && !TextUtils.isEmpty(var2.getCredentials()) && var5 > var7) {
            var9 = true;
         } else {
            var9 = false;
         }

         var1 = var9;
      }

      return var1;
   }

   public static boolean isProvisioned(CarrierBillingStorage var0) {
      CarrierBillingProvisioning var1 = var0.getProvisioning();
      byte var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.isProvisioned();
      }

      return (boolean)var2;
   }

   public static boolean isRadioNetworkAvailable() {
      return ((ConnectivityManager)FinskyApp.get().getSystemService("connectivity")).getNetworkInfo(0).isAvailable();
   }
}
