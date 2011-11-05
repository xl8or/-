package com.google.android.finsky.receivers;

import android.content.Context;
import android.content.Intent;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.download.DownloadQueueImpl;
import com.google.android.finsky.download.DownloadReceiver;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.model.PurchaseStatusTracker;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.vending.model.Asset;
import com.google.android.vending.model.AssetList;
import com.google.android.vending.remoting.api.VendingApi;

public class DeclineAssetReceiver extends DownloadReceiver {

   private static DownloadQueueImpl sDownloadQueueImpl;


   public DeclineAssetReceiver() {}

   private void handleAssetDeclined(LocalAsset var1, String var2, String var3, String var4) {
      PurchaseStatusTracker.Error var5 = new PurchaseStatusTracker.Error();
      var5.docTitle = var2;
      var5.title = var3;
      var5.briefMessage = var4;
      String var6 = var5.briefMessage;
      var5.detailedMessage = var6;
      PurchaseStatusTracker var7 = FinskyApp.get().getPurchaseStatusTracker();
      String var8 = var1.getPackage();
      var7.switchToError(var8, 1, var5);
      AssetState var9 = var1.getState();
      AssetState var10 = AssetState.DOWNLOAD_PENDING;
      if(var9 == var10) {
         DownloadQueueImpl var11 = sDownloadQueueImpl;
         String var12 = var1.getPackage();
         if(var11.getByPackageName(var12) != null) {
            Object[] var13 = new Object[0];
            FinskyLog.d("Download queued already, ignore.", var13);
         } else {
            Object[] var14 = new Object[0];
            FinskyLog.d("Set local asset state to DOWNLOAD_FAILED.", var14);
            var1.setStateDownloadFailed();
         }
      }
   }

   private void handleUnknownAssetDeclined(String var1, String var2) {
      FinskyInstance.get().getNotifier().showMessage(var1, var2, var2);
      FinskyApp.get().getPurchaseStatusTracker().clearPurchaseStatusMap();
   }

   public static void initialize(DownloadQueueImpl var0) {
      sDownloadQueueImpl = var0;
   }

   public void onReceive(Context var1, Intent var2) {
      if(var2.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
         this.setResultCode(-1);
      }

      String var3 = var2.getStringExtra("decline_reason");
      if(var3 == null) {
         var3 = "-1";
      }

      String var4 = var2.getStringExtra("assetid");
      String var5 = var2.getStringExtra("asset_name");
      Object[] var6 = new Object[]{var3, var4, var5};
      FinskyLog.d("Received DECLINE_ASSET tickle %s for asset ID %s %s", var6);
      FinskyApp var7 = FinskyApp.get();
      String var8 = var7.getString(2131230889);
      Object[] var9 = new Object[]{var5};
      String var10 = var7.getString(2131230890, var9);
      LocalAsset var11 = FinskyInstance.get().getAssetStore().getAssetById(var4);
      if(var11 == null) {
         Object[] var12 = new Object[]{var4};
         FinskyLog.d("Cannot associate asset ID %s with any local asset. Fetching from AMAS.", var12);
         VendingApi var13 = FinskyApp.get().getVendingApi();
         DeclineAssetReceiver.1 var14 = new DeclineAssetReceiver.1(var5, var8, var10);
         DeclineAssetReceiver.2 var15 = new DeclineAssetReceiver.2(var8, var10);
         var13.fetchAssetInfo(var4, var14, var15);
      } else {
         this.handleAssetDeclined(var11, var5, var8, var10);
      }
   }

   class 1 implements Response.Listener<AssetList> {

      // $FF: synthetic field
      final String val$assetName;
      // $FF: synthetic field
      final String val$message;
      // $FF: synthetic field
      final String val$title;


      1(String var2, String var3, String var4) {
         this.val$assetName = var2;
         this.val$title = var3;
         this.val$message = var4;
      }

      public void onResponse(AssetList var1) {
         if(var1.getAssets().size() > 0) {
            String var2 = ((Asset)var1.getAssets().get(0)).getPackageName();
            LocalAsset var3 = FinskyInstance.get().getAssetStore().getAsset(var2);
            if(var3 != null) {
               DeclineAssetReceiver var4 = DeclineAssetReceiver.this;
               String var5 = this.val$assetName;
               String var6 = this.val$title;
               String var7 = this.val$message;
               var4.handleAssetDeclined(var3, var5, var6, var7);
               return;
            }
         }

         Object[] var8 = new Object[0];
         FinskyLog.d("Could still not identify local asset from asset ID.", var8);
         DeclineAssetReceiver var9 = DeclineAssetReceiver.this;
         String var10 = this.val$title;
         String var11 = this.val$message;
         var9.handleUnknownAssetDeclined(var10, var11);
      }
   }

   class 2 implements Response.ErrorListener {

      // $FF: synthetic field
      final String val$message;
      // $FF: synthetic field
      final String val$title;


      2(String var2, String var3) {
         this.val$title = var2;
         this.val$message = var3;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Object[] var4 = new Object[0];
         FinskyLog.d("Error while fetching asset info.", var4);
         DeclineAssetReceiver var5 = DeclineAssetReceiver.this;
         String var6 = this.val$title;
         String var7 = this.val$message;
         var5.handleUnknownAssetDeclined(var6, var7);
      }
   }
}
