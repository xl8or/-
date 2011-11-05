package com.google.android.finsky;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.model.PurchaseStatusTracker;
import com.google.android.finsky.utils.Notifier;

public class PurchaseErrorSurfacer {

   private static PurchaseStatusTracker sPurchaseStatusTracker;


   public PurchaseErrorSurfacer() {}

   public static void initialize(PurchaseStatusTracker var0) {
      if(sPurchaseStatusTracker != null) {
         throw new IllegalStateException("Already initialized.");
      } else {
         sPurchaseStatusTracker = var0;
         PurchaseErrorSurfacer.1 var1 = new PurchaseErrorSurfacer.1();
         var0.attach(var1);
      }
   }

   static class 1 implements PurchaseStatusTracker.PurchaseStatusListener {

      1() {}

      public void onPurchaseStatusChanged(String var1, PurchaseStatusTracker.PurchaseStatus var2) {
         PurchaseStatusTracker.PurchaseState var3 = var2.state;
         PurchaseStatusTracker.PurchaseState var4 = PurchaseStatusTracker.PurchaseState.PURCHASE_COMPLETED_WITH_ERROR;
         if(var3 == var4) {
            DfeApi var5 = FinskyApp.get().getDfeApi();
            String var6 = var2.error.detailsUrl;
            var5.invalidateDetailsCache(var6, (boolean)1);
            Notifier var7 = FinskyInstance.get().getNotifier();
            String var8 = var2.error.title;
            String var9 = var2.error.briefMessage;
            String var10 = var2.error.detailedMessage;
            var7.showPurchaseErrorMessage(var8, var9, var10, var1);
         }
      }
   }
}
