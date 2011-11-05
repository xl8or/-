package com.google.android.finsky.billing.carrierbilling.api;

import android.text.TextUtils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.finsky.billing.carrierbilling.JsonUtils;
import com.google.android.finsky.billing.carrierbilling.api.DcbApiContext;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingCredentials;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingParameters;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;
import com.google.android.finsky.billing.carrierbilling.model.EncryptedSubscriberInfo;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import org.json.JSONException;
import org.json.JSONObject;

public class DcbApi {

   private static final float DCB_BACKOFF_MULT = 0.0F;
   private static final int DCB_MAX_RETRIES = 0;
   private static final int DCB_TIMEOUT_MS = ((Integer)G.purchaseStatusTimeoutMs.get()).intValue();
   static final int DEFAULT_PROTOCOL_CLIENT_VERSION = 1;
   static final String PROTOCOL_SERVER_FORMAT = "json";
   private final DcbApiContext mDcbContext;
   private final RequestQueue mRequestQueue;


   public DcbApi(RequestQueue var1, DcbApiContext var2) {
      this.mRequestQueue = var1;
      this.mDcbContext = var2;
   }

   static CarrierBillingCredentials buildCredentials(JSONObject param0) {
      // $FF: Couldn't be decompiled
   }

   private static EncryptedSubscriberInfo buildEncryptedSubscriberInfo(JSONObject var0) {
      int var1 = 0;
      EncryptedSubscriberInfo var2;
      if(var0 == null) {
         var2 = null;
      } else {
         Integer var3 = JsonUtils.getInt(var0, "googlekeyversion");
         Integer var4 = JsonUtils.getInt(var0, "carrierkeyversion");
         EncryptedSubscriberInfo.Builder var5 = new EncryptedSubscriberInfo.Builder();
         String var6 = JsonUtils.getString(var0, "message");
         EncryptedSubscriberInfo.Builder var7 = var5.setMessage(var6);
         String var8 = JsonUtils.getString(var0, "encryptedkey");
         EncryptedSubscriberInfo.Builder var9 = var7.setEncryptedKey(var8);
         String var10 = JsonUtils.getString(var0, "signature");
         EncryptedSubscriberInfo.Builder var11 = var9.setSignature(var10);
         String var12 = JsonUtils.getString(var0, "initvector");
         EncryptedSubscriberInfo.Builder var13 = var11.setInitVector(var12);
         int var14;
         if(var3 != null) {
            var14 = var3.intValue();
         } else {
            var14 = 0;
         }

         EncryptedSubscriberInfo.Builder var15 = var13.setGoogleKeyVersion(var14);
         if(var4 != null) {
            var1 = var4.intValue();
         }

         var2 = var15.setCarrierKeyVersion(var1).build();
      }

      return var2;
   }

   static CarrierBillingProvisioning buildProvisioning(JSONObject param0) {
      // $FF: Couldn't be decompiled
   }

   private static SubscriberInfo buildSubscriberInfo(String var0, String var1, JSONObject var2) {
      SubscriberInfo var3;
      if(var2 == null) {
         var3 = null;
      } else {
         SubscriberInfo.Builder var4 = (new SubscriberInfo.Builder()).setName(var0).setIdentifier(var1);
         String var5 = JsonUtils.getString(var2, "address1");
         SubscriberInfo.Builder var6 = var4.setAddress1(var5);
         String var7 = JsonUtils.getString(var2, "address2");
         SubscriberInfo.Builder var8 = var6.setAddress2(var7);
         String var9 = JsonUtils.getString(var2, "city");
         SubscriberInfo.Builder var10 = var8.setCity(var9);
         String var11 = JsonUtils.getString(var2, "state");
         SubscriberInfo.Builder var12 = var10.setState(var11);
         String var13 = JsonUtils.getString(var2, "postalcode");
         SubscriberInfo.Builder var14 = var12.setPostalCode(var13);
         String var15 = JsonUtils.getString(var2, "country");
         var3 = var14.setCountry(var15).build();
      }

      return var3;
   }

   JSONObject getBaseParametersAsJsonObject() throws JSONException {
      CarrierBillingParameters var1 = this.mDcbContext.getCarrierBillingParameters();
      JSONObject var2 = new JSONObject();
      JSONObject var3 = var2.put("format", "json");
      Integer var4 = Integer.valueOf(var1.getCarrierApiVersion());
      if(var4 == null || var4.intValue() <= 0) {
         var4 = Integer.valueOf(1);
      }

      var2.put("version", var4);
      if(var1.sendSubscriberInfoWithCarrierRequests()) {
         String var6 = this.mDcbContext.getLine1Number();
         if(!TextUtils.isEmpty(var6)) {
            var2.put("line1Number", var6);
         }

         String var8 = this.mDcbContext.getSubscriberId();
         if(!TextUtils.isEmpty(var8)) {
            var2.put("subscriberId", var8);
         }
      }

      return var2;
   }

   public Request<?> getCredentials(String var1, String var2, Response.Listener<CarrierBillingCredentials> var3, Response.ErrorListener var4) {
      CarrierBillingParameters var5 = this.mDcbContext.getCarrierBillingParameters();
      JSONObject var6 = this.getCredentialsParametersAsJsonObject(var1, var2);
      String var7 = var5.getGetCredentialsUrl();
      DcbApi.CredentialsJsonConverter var8 = new DcbApi.CredentialsJsonConverter(var3);
      DcbApi.RequestQueueErrorListener var9 = new DcbApi.RequestQueueErrorListener(var4);
      JsonObjectRequest var10 = new JsonObjectRequest(var7, var6, var8, var9);
      int var11 = DCB_TIMEOUT_MS;
      DefaultRetryPolicy var12 = new DefaultRetryPolicy(var11, 0, 0.0F);
      var10.setRetryPolicy(var12);
      this.mRequestQueue.start();
      return this.mRequestQueue.add(var10);
   }

   JSONObject getCredentialsParametersAsJsonObject(String var1, String var2) {
      JSONObject var3;
      try {
         var3 = this.getBaseParametersAsJsonObject();
         if(var1 != null) {
            var3.put("provisioningId", var1);
         }

         if(!TextUtils.isEmpty(var2)) {
            var3.put("password", var2);
         }
      } catch (JSONException var8) {
         Object[] var7 = new Object[]{var8};
         FinskyLog.e("JSONException while creating credentials request: %s", var7);
         var3 = null;
      }

      return var3;
   }

   public Request<?> getProvisioning(String var1, Response.Listener<CarrierBillingProvisioning> var2, Response.ErrorListener var3) {
      CarrierBillingParameters var4 = this.mDcbContext.getCarrierBillingParameters();
      JSONObject var5;
      if(((Boolean)G.vendingCarrierProvisioningUseTosVersion.get()).booleanValue()) {
         var5 = this.getProvisioningParametersAsJsonObject(var1);
      } else {
         var5 = this.getProvisioningParametersAsJsonObject((String)null);
      }

      String var6 = var4.getGetProvisioningUrl();
      DcbApi.ProvisioningJsonConverter var7 = new DcbApi.ProvisioningJsonConverter(var2);
      DcbApi.RequestQueueErrorListener var8 = new DcbApi.RequestQueueErrorListener(var3);
      JsonObjectRequest var9 = new JsonObjectRequest(var6, var5, var7, var8);
      int var10 = DCB_TIMEOUT_MS;
      DefaultRetryPolicy var11 = new DefaultRetryPolicy(var10, 0, 0.0F);
      var9.setRetryPolicy(var11);
      this.mRequestQueue.start();
      return this.mRequestQueue.add(var9);
   }

   JSONObject getProvisioningParametersAsJsonObject(String var1) {
      JSONObject var2;
      try {
         var2 = this.getBaseParametersAsJsonObject();
         if(var1 != null) {
            var2.put("acceptedTosVersion", var1);
         }
      } catch (JSONException var6) {
         Object[] var5 = new Object[]{var6};
         FinskyLog.e("JSONException while creating provisioning request: %s", var5);
         var2 = null;
      }

      return var2;
   }

   private class RequestQueueErrorListener implements Response.ErrorListener {

      private final Response.ErrorListener mErrorListener;


      public RequestQueueErrorListener(Response.ErrorListener var2) {
         this.mErrorListener = var2;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         DcbApi.this.mRequestQueue.stop();
         this.mErrorListener.onErrorResponse(var1, var2, var3);
      }
   }

   private class CredentialsJsonConverter implements Response.Listener<JSONObject> {

      private final Response.Listener<CarrierBillingCredentials> mListener;


      public CredentialsJsonConverter(Response.Listener var2) {
         this.mListener = var2;
      }

      public void onResponse(JSONObject var1) {
         DcbApi.this.mRequestQueue.stop();
         CarrierBillingCredentials var2 = DcbApi.buildCredentials(var1);
         this.mListener.onResponse(var2);
      }
   }

   private class ProvisioningJsonConverter implements Response.Listener<JSONObject> {

      private final Response.Listener<CarrierBillingProvisioning> mListener;


      public ProvisioningJsonConverter(Response.Listener var2) {
         this.mListener = var2;
      }

      public void onResponse(JSONObject var1) {
         DcbApi.this.mRequestQueue.stop();
         CarrierBillingProvisioning var2 = DcbApi.buildProvisioning(var1);
         this.mListener.onResponse(var2);
      }
   }
}
