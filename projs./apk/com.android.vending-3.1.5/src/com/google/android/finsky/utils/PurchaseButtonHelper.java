package com.google.android.finsky.utils;

import android.content.Context;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.Common;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PackageInfoCache;

public class PurchaseButtonHelper {

   public PurchaseButtonHelper() {}

   public static void bindPurchaseButton(TextView var0, Document var1, String var2, NavigationManager var3, boolean var4) {
      stylePurchaseButton(var1, var4, var0);
   }

   private static void styleFromOffer(TextView var0, Common.Offer var1) {
      if(var1 != null) {
         if(var1.getOfferType() == 4) {
            FinskyApp var2 = FinskyApp.get();
            Object[] var3 = new Object[1];
            String var4 = var1.getFormattedAmount();
            var3[0] = var4;
            String var5 = var2.getString(2131230957, var3);
            var0.setText(var5);
         } else {
            String var6 = var1.getFormattedAmount();
            var0.setText(var6);
         }
      } else {
         var0.setVisibility(8);
      }
   }

   private static void stylePurchaseButton(Document var0, boolean var1, TextView var2) {
      if(var1) {
         Context var3 = var2.getContext();
         int var4 = var0.getBackend();
         int var5 = CorpusMetadata.getBackendForegroundColor(var3, var4);
         var2.setTextColor(var5);
      }

      var2.setVisibility(0);
      FinskyApp var6 = FinskyApp.get();
      Common.Offer var7 = var0.getDefaultOffer();
      switch(var0.getBackend()) {
      case 1:
         if(var0.ownedByUser((PackageInfoCache)null)) {
            var2.setText(2131230912);
            return;
         }

         styleFromOffer(var2, var7);
         return;
      case 2:
         if(var0.ownedByUser((PackageInfoCache)null)) {
            var2.setText(2131230912);
            return;
         }

         styleFromOffer(var2, var7);
         return;
      case 3:
         PackageInfoCache var10 = var6.getPackageInfoCache();
         if(var0.isLocallyAvailable(var10)) {
            DeviceDoc.AppDetails var11 = var0.getAppDetails();
            if(var0.ownedByUser(var10) && var11.hasVersionCode()) {
               String var12 = var11.getPackageName();
               int var13 = var10.getPackageVersion(var12);
               if(var11.getVersionCode() > var13) {
                  var2.setText(2131230914);
                  return;
               }

               var2.setText(2131230911);
               return;
            }

            var2.setText(2131230911);
            return;
         } else {
            if(var0.ownedByUser(var10) && var7 != null && var7.getMicros() > 0L) {
               var2.setText(2131230912);
               return;
            }

            styleFromOffer(var2, var7);
            return;
         }
      case 4:
         if(var0.ownedByUser((PackageInfoCache)null)) {
            var2.setText(2131230913);
            return;
         }

         styleFromOffer(var2, var7);
         return;
      default:
         Object[] var8 = new Object[1];
         Integer var9 = Integer.valueOf(var0.getBackend());
         var8[0] = var9;
         FinskyLog.wtf("Unsupported backend: %d", var8);
         styleFromOffer(var2, var7);
      }
   }
}
