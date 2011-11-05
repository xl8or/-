package com.google.android.vending.remoting.api;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.vending.remoting.api.VendingApiContext;
import com.google.android.vending.remoting.api.VendingApiPreferences;
import com.google.android.vending.remoting.api.VendingRequest;
import com.google.android.vending.remoting.protos.VendingProtos;

public class GetMarketMetadataRequest extends VendingRequest<VendingProtos.GetMarketMetadataRequestProto, VendingProtos.GetMarketMetadataResponseProto> {

   private boolean mDelivered = 0;
   private final int mDeviceConfigHash;
   private final int mMarketVersion;


   public GetMarketMetadataRequest(VendingProtos.GetMarketMetadataRequestProto var1, int var2, int var3, Response.Listener<VendingProtos.GetMarketMetadataResponseProto> var4, VendingApiContext var5, Response.ErrorListener var6) {
      super("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.GetMarketMetadataRequestProto.class, var1, VendingProtos.GetMarketMetadataResponseProto.class, var4, var5, var6);
      this.mDeviceConfigHash = var2;
      this.mMarketVersion = var3;
      this.setDrainable((boolean)0);
      Integer var12 = (Integer)VendingApiPreferences.getDeviceConfigurationHashProperty(var5.getAccount().name).get();
      if(var12 != null) {
         Integer var13 = Integer.valueOf(this.mDeviceConfigHash);
         if(var12.equals(var13)) {
            if(!((Boolean)G.vendingAlwaysSendConfig.get()).booleanValue()) {
               VendingProtos.GetMarketMetadataRequestProto var14 = var1.clearDeviceConfiguration();
            }
         }
      }
   }

   protected void deliverResponse(VendingProtos.ResponseProto var1) {
      if(!this.mDelivered) {
         this.mDelivered = (boolean)1;
         super.deliverResponse(var1);
      }
   }

   public String getCacheKey() {
      String var1 = super.getCacheKey();
      StringBuilder var2 = new StringBuilder(var1);
      StringBuilder var3 = var2.append("/ttl=");
      Object var4 = G.vendingSyncFrequencyMs.get();
      var3.append(var4);
      StringBuilder var6 = var2.append("/account=");
      String var7 = this.mApiContext.getAccount().name;
      var6.append(var7);
      StringBuilder var9 = var2.append("/devicecfghash=");
      int var10 = this.mDeviceConfigHash;
      var9.append(var10);
      StringBuilder var12 = var2.append("/marketversion=");
      int var13 = this.mMarketVersion;
      var12.append(var13);
      return var2.toString();
   }

   protected Response<VendingProtos.ResponseProto> parseNetworkResponse(NetworkResponse var1) {
      Response var2 = super.parseNetworkResponse(var1);
      Response.ErrorCode var3 = var2.errorCode;
      Response.ErrorCode var4 = Response.ErrorCode.OK;
      if(var3 == var4) {
         PreferenceFile.SharedPreference var5 = VendingApiPreferences.getDeviceConfigurationHashProperty(this.mApiContext.getAccount().name);
         Integer var6 = Integer.valueOf(this.mDeviceConfigHash);
         var5.put(var6);
         Cache.Entry var7 = null;
         VendingProtos.ResponseProto var8 = (VendingProtos.ResponseProto)var2.result;
         boolean var9 = this.handlePendingNotifications(var8, (boolean)0);
         if(!var9 && ((Long)G.vendingSyncFrequencyMs.get()).longValue() > 0L) {
            var7 = new Cache.Entry();
            byte[] var10 = var1.data;
            var7.data = var10;
            long var11 = System.currentTimeMillis();
            long var13 = ((Long)G.vendingSyncFrequencyMs.get()).longValue();
            long var15 = var11 + var13;
            var7.softTtl = var15;
            var7.ttl = Long.MAX_VALUE;
            long var17 = System.currentTimeMillis();
            var7.serverDate = var17;
         } else {
            Object[] var19 = new Object[1];
            Boolean var20 = Boolean.valueOf(var9);
            var19[0] = var20;
            FinskyLog.d("Disabling cache for GetMarketMetadata. hasPendingNotifications=%s", var19);
         }

         var2 = Response.success(var2.result, var7);
      }

      return var2;
   }
}
