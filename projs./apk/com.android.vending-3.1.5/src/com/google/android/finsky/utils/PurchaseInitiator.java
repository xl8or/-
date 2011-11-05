package com.google.android.finsky.utils;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.model.PurchaseStatusTracker;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.navigationmanager.NavigationState;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.remoting.protos.Buy;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.Map;

public class PurchaseInitiator {

   private static Notifier sNotificationHelper;


   public PurchaseInitiator() {}

   private static Response.Listener<VendingProtos.GetAssetResponseProto> createDirectDownloadListener(String var0, int var1, Document var2) {
      Installer var3 = FinskyInstance.get().getInstaller();
      String var4 = FinskyApp.get().getCurrentAccountName();
      String var5 = var2.getAppDetails().getPackageName();
      return new PurchaseInitiator.2(var3, var4, var5, var2, var0, var1);
   }

   private static PurchaseStatusTracker.Error createFreeAppDownloadError(Document var0) {
      PurchaseStatusTracker.Error var1 = new PurchaseStatusTracker.Error();
      String var2 = var0.getTitle();
      var1.docTitle = var2;
      String var3 = var0.getDetailsUrl();
      var1.detailsUrl = var3;
      FinskyApp var4 = FinskyApp.get();
      Object[] var5 = new Object[]{var2};
      String var6 = var4.getString(2131230859, var5);
      var1.title = var6;
      Object[] var7 = new Object[]{var2};
      String var8 = var4.getString(2131230858, var7);
      var1.briefMessage = var8;
      Object[] var9 = new Object[]{var2};
      String var10 = var4.getString(2131230860, var9);
      var1.detailedMessage = var10;
      return var1;
   }

   private static Response.Listener<Buy.BuyResponse> createFreeItemPurchaseListener(String var0, int var1, String var2) {
      return new PurchaseInitiator.3(var2, var0, var1);
   }

   private static Response.ErrorListener createPurchaseErrorListener(NavigationManager var0, String var1, String var2, int var3, String var4) {
      return new PurchaseInitiator.5(var0, var1, var2, var3, var4);
   }

   private static Response.Listener<Buy.PurchaseStatusResponse> createPurchaseStatusListener(NavigationManager var0, String var1, String var2, int var3, String var4) {
      return new PurchaseInitiator.4(var4, var1, var3, var0, var2);
   }

   public static String generateAssetId(DeviceDoc.AppDetails var0) {
      StringBuilder var1 = (new StringBuilder()).append("v2:");
      String var2 = var0.getPackageName();
      StringBuilder var3 = var1.append(var2).append(":");
      int var4 = var0.getMajorVersionNumber();
      StringBuilder var5 = var3.append(var4).append(":");
      int var6 = var0.getVersionCode();
      return var5.append(var6).toString();
   }

   public static void getPaidPurchaseStatus(NavigationManager var0, String var1, String var2, String var3, int var4, String var5, DeviceDoc.AppDetails var6, String var7) {
      PurchaseStatusTracker var8 = FinskyApp.get().getPurchaseStatusTracker();
      PurchaseStatusTracker.PurchaseState var9 = PurchaseStatusTracker.PurchaseState.PURCHASE_INITIATED;
      var8.switchState(var2, var4, var9);
      if(var6 != null) {
         insertNewAssetIntoStore(FinskyInstance.get().getAssetStore(), var6, var7);
      }

      DfeApi var10 = FinskyApp.get().getDfeApi();
      Response.Listener var11 = createPurchaseStatusListener(var0, var2, var3, var4, var5);
      Response.ErrorListener var12 = createPurchaseErrorListener(var0, var2, var3, var4, var5);
      var10.getPurchaseStatus(var1, var11, var12);
   }

   public static void initialize(Notifier var0) {
      sNotificationHelper = var0;
   }

   private static void insertNewAssetIntoStore(AssetStore var0, DeviceDoc.AppDetails var1, String var2) {
      String var3 = FinskyApp.get().getCurrentAccountName();
      String var4 = var1.getPackageName();
      AutoUpdateState var5 = AutoUpdateState.DEFAULT;
      int var6 = var1.getVersionCode();
      String var7 = generateAssetId(var1);
      long var8 = System.currentTimeMillis();
      var0.insertAsset(var4, var5, var3, var6, var7, var2, (String)null, var8);
   }

   public static void makeFreePurchase(NavigationManager var0, Document var1, int var2, String var3) {
      PurchaseStatusTracker var4 = FinskyApp.get().getPurchaseStatusTracker();
      String var5 = var1.getDocId();
      if(var1.getBackend() == 3) {
         PurchaseStatusTracker.PurchaseState var6 = PurchaseStatusTracker.PurchaseState.PURCHASE_INITIATED;
         var4.switchState(var5, -1, var6);
         if(var1.getAppDetails() == null) {
            Object[] var7 = new Object[0];
            FinskyLog.e("Document input lacks app details for free app download.", var7);
            PurchaseStatusTracker.Error var8 = createFreeAppDownloadError(var1);
            var4.switchToError(var5, -1, var8);
         } else {
            String var9 = var1.getAppDetails().getPackageName();
            PurchaseInitiator.1 var10 = new PurchaseInitiator.1(var9, var1, var4, var5);
            AssetStore var11 = FinskyInstance.get().getAssetStore();
            DeviceDoc.AppDetails var12 = var1.getAppDetails();
            updateAssetInStore(var11, var12, var3);
            VendingApi var13 = FinskyApp.get().getVendingApi();
            String var14 = (String)VendingPreferences.DIRECT_DOWNLOAD_KEY.get();
            String var15 = generateAssetId(var1.getAppDetails());
            Response.Listener var16 = createDirectDownloadListener(var5, var2, var1);
            var13.getAsset(var14, var15, var16, var10);
         }
      } else {
         PurchaseStatusTracker.PurchaseState var17 = PurchaseStatusTracker.PurchaseState.PURCHASE_INITIATED;
         var4.switchState(var5, var2, var17);
         DfeApi var18 = FinskyApp.get().getDfeApi();
         String var19 = var1.getDocId();
         String var20 = var1.getDocId();
         String var21 = var1.getDetailsUrl();
         Response.Listener var22 = createFreeItemPurchaseListener(var20, var2, var21);
         String var23 = var1.getDocId();
         String var24 = var1.getTitle();
         String var25 = var1.getDetailsUrl();
         Response.ErrorListener var26 = createPurchaseErrorListener(var0, var23, var24, var2, var25);
         Object var28 = null;
         var18.makePurchase(var19, var2, (String)null, (Map)var28, var22, var26);
      }
   }

   private static void switchToCompleted(String var0, int var1) {
      PurchaseStatusTracker var2 = FinskyApp.get().getPurchaseStatusTracker();
      PurchaseStatusTracker.PurchaseStatus var3 = var2.getPurchaseStatus(var0);
      if(var3 != null) {
         PurchaseStatusTracker.PurchaseState var4 = var3.state;
         PurchaseStatusTracker.PurchaseState var5 = PurchaseStatusTracker.PurchaseState.PURCHASE_INITIATED;
         if(var4 == var5) {
            PurchaseStatusTracker.PurchaseState var6 = PurchaseStatusTracker.PurchaseState.PURCHASE_COMPLETED;
            var2.switchState(var0, var1, var6);
            return;
         }
      }

      Object[] var7 = new Object[0];
      FinskyLog.d("Not setting purchase to PURCHASE_COMPLETED, the tickle was faster.", var7);
   }

   private static void switchToError(NavigationManager var0, String var1, String var2, int var3, String var4, String var5, String var6, String var7, String var8) {
      PurchaseStatusTracker.Error var9 = new PurchaseStatusTracker.Error();
      var9.title = var5;
      var9.docTitle = var2;
      var9.sourceUrl = var4;
      var9.briefMessage = var6;
      var9.detailedMessage = var7;
      var9.detailsUrl = var8;
      FinskyApp.get().getPurchaseStatusTracker().switchToError(var1, var3, var9);
      Object[] var10 = new Object[]{var1, var9};
      FinskyLog.w("Error when purchasing document %s: %s", var10);
      if(var0 != null) {
         NavigationState var11 = var0.getCurrentPageType();
         NavigationState var12 = NavigationState.DETAILS;
         if(var11 != var12 || var0.getCurrentDocument() == null || !var0.getCurrentDocument().getDocId().equals(var1)) {
            sNotificationHelper.showPurchaseErrorMessage(var2, var5, var6, var1);
         }
      }
   }

   private static void updateAssetInStore(AssetStore var0, DeviceDoc.AppDetails var1, String var2) {
      String var3 = var1.getPackageName();
      LocalAsset var4 = var0.getAsset(var3);
      if(var4 == null) {
         insertNewAssetIntoStore(var0, var1, var2);
      } else {
         String var5 = FinskyApp.get().getCurrentAccountName();
         var4.setAccount(var5);
         long var6 = System.currentTimeMillis();
         int var8 = var1.getVersionCode();
         String var9 = generateAssetId(var1);
         Object var10 = null;
         var4.setStateDownloadPending(var6, var8, var9, (String)null, (String)var10);
         var4.setExternalReferrer(var2);
      }
   }

   static class 5 implements Response.ErrorListener {

      // $FF: synthetic field
      final String val$detailsUrl;
      // $FF: synthetic field
      final String val$docId;
      // $FF: synthetic field
      final String val$docTitle;
      // $FF: synthetic field
      final NavigationManager val$navigationManager;
      // $FF: synthetic field
      final int val$offerType;


      5(NavigationManager var1, String var2, String var3, int var4, String var5) {
         this.val$navigationManager = var1;
         this.val$docId = var2;
         this.val$docTitle = var3;
         this.val$offerType = var4;
         this.val$detailsUrl = var5;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         String var4 = FinskyApp.get().getString(2131231017);
         String var5 = ErrorStrings.get(FinskyApp.get(), var1, var2);
         NavigationManager var6 = this.val$navigationManager;
         String var7 = this.val$docId;
         String var8 = this.val$docTitle;
         int var9 = this.val$offerType;
         String var10 = this.val$detailsUrl;
         PurchaseInitiator.switchToError(var6, var7, var8, var9, var10, var4, var5, var5, (String)null);
      }
   }

   static class 1 implements Response.ErrorListener {

      // $FF: synthetic field
      final Document val$doc;
      // $FF: synthetic field
      final String val$docId;
      // $FF: synthetic field
      final String val$packageName;
      // $FF: synthetic field
      final PurchaseStatusTracker val$tracker;


      1(String var1, Document var2, PurchaseStatusTracker var3, String var4) {
         this.val$packageName = var1;
         this.val$doc = var2;
         this.val$tracker = var3;
         this.val$docId = var4;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Object[] var4 = new Object[3];
         String var5 = this.val$packageName;
         var4[0] = var5;
         String var6 = var1.toString();
         var4[1] = var6;
         var4[2] = var2;
         FinskyLog.e("Error when attempting direct download of asset [%s]. ErrorCode=[%s], Message=[%s]", var4);
         PurchaseStatusTracker.Error var7 = PurchaseInitiator.createFreeAppDownloadError(this.val$doc);
         PurchaseStatusTracker var8 = this.val$tracker;
         String var9 = this.val$docId;
         var8.switchToError(var9, -1, var7);
      }
   }

   static class 2 extends Installer.DownloadAndInstallGetAssetListener {

      // $FF: synthetic field
      final String val$docId;
      // $FF: synthetic field
      final Document val$document;
      // $FF: synthetic field
      final int val$offerType;


      2(Installer var1, String var2, String var3, Document var4, String var5, int var6) {
         super(var1, var2, var3);
         this.val$document = var4;
         this.val$docId = var5;
         this.val$offerType = var6;
      }

      public void onResponse(VendingProtos.GetAssetResponseProto var1) {
         DfeApi var2 = FinskyApp.get().getDfeApi();
         String var3 = this.val$document.getDetailsUrl();
         var2.invalidateDetailsCache(var3, (boolean)1);
         String var4 = this.val$docId;
         int var5 = this.val$offerType;
         PurchaseInitiator.switchToCompleted(var4, var5);
         super.onResponse(var1);
      }
   }

   static class 3 implements Response.Listener<Buy.BuyResponse> {

      // $FF: synthetic field
      final String val$detailsUrl;
      // $FF: synthetic field
      final String val$docId;
      // $FF: synthetic field
      final int val$offerType;


      3(String var1, String var2, int var3) {
         this.val$detailsUrl = var1;
         this.val$docId = var2;
         this.val$offerType = var3;
      }

      public void onResponse(Buy.BuyResponse var1) {
         DfeApi var2 = FinskyApp.get().getDfeApi();
         String var3 = this.val$detailsUrl;
         var2.invalidateDetailsCache(var3, (boolean)1);
         String var4 = this.val$docId;
         int var5 = this.val$offerType;
         PurchaseInitiator.switchToCompleted(var4, var5);
      }
   }

   static class 4 implements Response.Listener<Buy.PurchaseStatusResponse> {

      // $FF: synthetic field
      final String val$detailsUrl;
      // $FF: synthetic field
      final String val$docId;
      // $FF: synthetic field
      final String val$docTitle;
      // $FF: synthetic field
      final NavigationManager val$navigationManager;
      // $FF: synthetic field
      final int val$offerType;


      4(String var1, String var2, int var3, NavigationManager var4, String var5) {
         this.val$detailsUrl = var1;
         this.val$docId = var2;
         this.val$offerType = var3;
         this.val$navigationManager = var4;
         this.val$docTitle = var5;
      }

      public void onResponse(Buy.PurchaseStatusResponse var1) {
         DfeApi var2 = FinskyApp.get().getDfeApi();
         String var3 = this.val$detailsUrl;
         var2.invalidateDetailsCache(var3, (boolean)1);
         int var4 = var1.getStatus();
         if(var4 == 1) {
            String var5 = this.val$docId;
            int var6 = this.val$offerType;
            PurchaseInitiator.switchToCompleted(var5, var6);
         } else {
            String var7 = var1.getStatusMsg();
            String var8 = var1.getBriefMessage();
            String var9 = var1.getStatusTitle();
            String var10 = var1.getInfoUrl();
            if(FinskyLog.DEBUG) {
               Object[] var11 = new Object[4];
               Integer var12 = Integer.valueOf(var4);
               var11[0] = var12;
               var11[1] = var9;
               var11[2] = var7;
               var11[3] = var10;
               FinskyLog.v("Purchase Status response has error code %d, title %s,message %s and info URL %s", var11);
            }

            NavigationManager var13 = this.val$navigationManager;
            String var14 = this.val$docId;
            String var15 = this.val$docTitle;
            int var16 = this.val$offerType;
            String var17 = this.val$detailsUrl;
            PurchaseInitiator.switchToError(var13, var14, var15, var16, var17, var9, var8, var7, var10);
         }
      }
   }
}
