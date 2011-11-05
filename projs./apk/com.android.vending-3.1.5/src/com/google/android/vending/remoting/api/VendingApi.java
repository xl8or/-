package com.google.android.vending.remoting.api;

import android.text.TextUtils;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.google.android.vending.model.AssetList;
import com.google.android.vending.remoting.api.GetMarketMetadataRequest;
import com.google.android.vending.remoting.api.VendingApiContext;
import com.google.android.vending.remoting.api.VendingRequest;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.List;

public class VendingApi {

   public static final String PARAM_PROTOCOL_VERSION = "version";
   public static final String PARAM_REQUEST_PROTO = "request";
   public static final int PROTOCOL_VERSION = 2;
   public static final String USER_AGENT_APP_VERSION = "Android-Market/2";
   public static final String VENDING_MACHINE_SSL_URL = "https://android.clients.google.com/vending/api/ApiRequest";
   private final VendingApiContext mApiContext;
   private final RequestQueue mRequestQueue;


   public VendingApi(RequestQueue var1, VendingApiContext var2) {
      this.mRequestQueue = var1;
      this.mApiContext = var2;
   }

   public void ackNotifications(VendingProtos.AckNotificationsRequestProto var1, Response.Listener<VendingProtos.AckNotificationsResponseProto> var2, Response.ErrorListener var3) {
      VendingApiContext var4 = this.mApiContext;
      VendingRequest var8 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.AckNotificationsRequestProto.class, var1, VendingProtos.AckNotificationsResponseProto.class, var2, var4, var3);
      var8.setDrainable((boolean)0);
      this.mRequestQueue.add(var8);
   }

   public void checkForPendingNotifications(Response.ErrorListener var1) {
      VendingProtos.CheckForNotificationsRequestProto var2 = new VendingProtos.CheckForNotificationsRequestProto();
      VendingApi.1 var3 = new VendingApi.1();
      VendingApiContext var4 = this.mApiContext;
      VendingRequest var6 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.CheckForNotificationsRequestProto.class, var2, VendingProtos.GetMarketMetadataResponseProto.class, var3, var4, var1);
      this.mRequestQueue.add(var6);
   }

   public void checkLicense(VendingProtos.CheckLicenseRequestProto var1, Response.Listener<VendingProtos.CheckLicenseResponseProto> var2, Response.ErrorListener var3) {
      VendingApiContext var4 = this.mApiContext;
      VendingRequest var8 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.CheckLicenseRequestProto.class, var1, VendingProtos.CheckLicenseResponseProto.class, var2, var4, var3);
      this.mRequestQueue.add(var8);
   }

   public void fetchAssetInfo(String var1, Response.Listener<AssetList> var2, Response.ErrorListener var3) {
      VendingProtos.AssetsRequestProto var4 = new VendingProtos.AssetsRequestProto();
      var4.addAssetId(var1);
      VendingApi.AssetListConverter var6 = new VendingApi.AssetListConverter(var2);
      VendingApiContext var7 = this.mApiContext;
      VendingRequest var9 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.AssetsRequestProto.class, var4, VendingProtos.AssetsResponseProto.class, var6, var7, var3);
      this.mRequestQueue.add(var9);
   }

   public void flagAsset(String var1, int var2, String var3, Response.Listener<VendingProtos.ModifyCommentResponseProto> var4, Response.ErrorListener var5) {
      VendingProtos.ModifyCommentRequestProto var6 = new VendingProtos.ModifyCommentRequestProto();
      var6.setAssetId(var1);
      var6.setFlagType(var2);
      if(!TextUtils.isEmpty(var3)) {
         var6.setFlagMessage(var3);
      }

      VendingApiContext var10 = this.mApiContext;
      VendingRequest var13 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.ModifyCommentRequestProto.class, var6, VendingProtos.ModifyCommentResponseProto.class, var4, var10, var5);
      this.mRequestQueue.add(var13);
   }

   public VendingApiContext getApiContext() {
      return this.mApiContext;
   }

   public void getAsset(String var1, String var2, Response.Listener<VendingProtos.GetAssetResponseProto> var3, Response.ErrorListener var4) {
      VendingProtos.GetAssetRequestProto var5 = new VendingProtos.GetAssetRequestProto();
      var5.setAssetId(var2);
      if(var1 != null) {
         var5.setDirectDownloadKey(var1);
      }

      VendingApiContext var8 = this.mApiContext;
      VendingRequest var11 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.GetAssetRequestProto.class, var5, VendingProtos.GetAssetResponseProto.class, var3, var8, var4);
      this.mRequestQueue.add(var11);
   }

   public void getBillingCountries(Response.Listener<List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country>> var1, Response.ErrorListener var2) {
      VendingProtos.PurchaseMetadataRequestProto var3 = new VendingProtos.PurchaseMetadataRequestProto();
      VendingApi.CountriesConverter var4 = new VendingApi.CountriesConverter(var1);
      VendingApiContext var5 = this.mApiContext;
      VendingRequest var7 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.PurchaseMetadataRequestProto.class, var3, VendingProtos.PurchaseMetadataResponseProto.class, var4, var5, var2);
      this.mRequestQueue.add(var7);
   }

   public void getInAppPurchaseInformation(VendingProtos.InAppPurchaseInformationRequestProto var1, Response.Listener<VendingProtos.InAppPurchaseInformationResponseProto> var2, Response.ErrorListener var3) {
      VendingApiContext var4 = this.mApiContext;
      VendingRequest var8 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.InAppPurchaseInformationRequestProto.class, var1, VendingProtos.InAppPurchaseInformationResponseProto.class, var2, var4, var3);
      this.mRequestQueue.add(var8);
   }

   public void getMetadata(VendingProtos.GetMarketMetadataRequestProto var1, int var2, int var3, Response.Listener<VendingProtos.GetMarketMetadataResponseProto> var4, Response.ErrorListener var5) {
      VendingApiContext var6 = this.mApiContext;
      GetMarketMetadataRequest var12 = new GetMarketMetadataRequest(var1, var2, var3, var4, var6, var5);
      this.mRequestQueue.add(var12);
   }

   public void getVendingHistory(Response.Listener<AssetList> var1, Response.ErrorListener var2) {
      VendingProtos.AssetsRequestProto var3 = new VendingProtos.AssetsRequestProto();
      VendingProtos.AssetsRequestProto var4 = var3.setRetrieveVendingHistory((boolean)1);
      VendingProtos.AssetsRequestProto var5 = var3.setRetrieveExtendedInfo((boolean)1);
      VendingApi.AssetListConverter var6 = new VendingApi.AssetListConverter(var1);
      VendingApiContext var7 = this.mApiContext;
      VendingRequest var9 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.AssetsRequestProto.class, var3, VendingProtos.AssetsResponseProto.class, var6, var7, var2);
      this.mRequestQueue.add(var9);
   }

   public void reconstructDatabase(VendingProtos.ReconstructDatabaseRequestProto var1, Response.Listener<VendingProtos.ReconstructDatabaseResponseProto> var2, Response.ErrorListener var3) {
      VendingApiContext var4 = this.mApiContext;
      VendingRequest var8 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.ReconstructDatabaseRequestProto.class, var1, VendingProtos.ReconstructDatabaseResponseProto.class, var2, var4, var3);
      var8.setDrainable((boolean)0);
      this.mRequestQueue.add(var8);
   }

   public void recordBillingEvent(VendingProtos.BillingEventRequestProto var1, Response.Listener<VendingProtos.BillingEventResponseProto> var2, Response.ErrorListener var3) {
      VendingApiContext var4 = this.mApiContext;
      VendingRequest var8 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.BillingEventRequestProto.class, var1, VendingProtos.BillingEventResponseProto.class, var2, var4, var3);
      var8.setDrainable((boolean)0);
      this.mRequestQueue.add(var8);
   }

   public void refundAsset(VendingProtos.RefundRequestProto var1, Response.Listener<VendingProtos.RefundResponseProto> var2, Response.ErrorListener var3) {
      VendingApiContext var4 = this.mApiContext;
      VendingRequest var8 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.RefundRequestProto.class, var1, VendingProtos.RefundResponseProto.class, var2, var4, var3);
      var8.setDrainable((boolean)0);
      this.mRequestQueue.add(var8);
   }

   public void restoreApplications(VendingProtos.RestoreApplicationsRequestProto var1, Response.Listener<VendingProtos.RestoreApplicationsResponseProto> var2, Response.ErrorListener var3) {
      VendingApiContext var4 = this.mApiContext;
      VendingRequest var8 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.RestoreApplicationsRequestProto.class, var1, VendingProtos.RestoreApplicationsResponseProto.class, var2, var4, var3);
      var8.setDrainable((boolean)0);
      this.mRequestQueue.add(var8);
   }

   public void restoreInAppTransactions(VendingProtos.InAppRestoreTransactionsRequestProto var1, Response.Listener<VendingProtos.InAppRestoreTransactionsResponseProto> var2, Response.ErrorListener var3) {
      VendingApiContext var4 = this.mApiContext;
      VendingRequest var8 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.InAppRestoreTransactionsRequestProto.class, var1, VendingProtos.InAppRestoreTransactionsResponseProto.class, var2, var4, var3);
      this.mRequestQueue.add(var8);
   }

   public void syncContent(VendingProtos.ContentSyncRequestProto var1, Response.Listener<VendingProtos.ContentSyncResponseProto> var2, Response.ErrorListener var3) {
      VendingApiContext var4 = this.mApiContext;
      VendingRequest var8 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.ContentSyncRequestProto.class, var1, VendingProtos.ContentSyncResponseProto.class, var2, var4, var3);
      var8.setDrainable((boolean)0);
      this.mRequestQueue.add(var8);
   }

   class 1 implements Response.Listener<VendingProtos.GetMarketMetadataResponseProto> {

      1() {}

      public void onResponse(VendingProtos.GetMarketMetadataResponseProto var1) {}
   }

   private static class CountriesConverter implements Response.Listener<VendingProtos.PurchaseMetadataResponseProto> {

      private final Response.Listener<List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country>> mListener;


      public CountriesConverter(Response.Listener<List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country>> var1) {
         this.mListener = var1;
      }

      public void onResponse(VendingProtos.PurchaseMetadataResponseProto var1) {
         Response.Listener var2 = this.mListener;
         List var3 = var1.getCountries().getCountryList();
         var2.onResponse(var3);
      }
   }

   private static class AssetListConverter implements Response.Listener<VendingProtos.AssetsResponseProto> {

      private final Response.Listener<AssetList> mListener;


      public AssetListConverter(Response.Listener<AssetList> var1) {
         this.mListener = var1;
      }

      public void onResponse(VendingProtos.AssetsResponseProto var1) {
         AssetList var2 = new AssetList(var1);
         this.mListener.onResponse(var2);
      }
   }
}
