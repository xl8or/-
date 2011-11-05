package com.google.android.vending.remoting.api;

import android.content.Context;
import android.util.Base64;
import com.android.volley.AuthFailureException;
import com.android.volley.Cache;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.MicroProtoHelper;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Maps;
import com.google.android.vending.remoting.api.PendingNotificationsHandler;
import com.google.android.vending.remoting.api.VendingApiContext;
import com.google.android.vending.remoting.api.VendingRetryPolicy;
import com.google.android.vending.remoting.protos.VendingProtos;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.MessageMicro;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class VendingRequest<T extends MessageMicro, U extends MessageMicro> extends Request<VendingProtos.ResponseProto> {

   private static final int BASE64_FLAGS = 11;
   protected final VendingApiContext mApiContext;
   private final Response.Listener<U> mListener;
   private final T mRequest;
   private final Class<T> mRequestClass;
   private final Class<U> mResponseClass;
   private final boolean mUseSecureAuthToken;


   protected VendingRequest(String var1, Class<T> var2, T var3, Class<U> var4, Response.Listener<U> var5, VendingApiContext var6, Response.ErrorListener var7) {
      super(var1, var7);
      boolean var8 = var1.startsWith("https");
      this.mUseSecureAuthToken = var8;
      this.mRequest = var3;
      this.mRequestClass = var2;
      this.mResponseClass = var4;
      this.mListener = var5;
      this.mApiContext = var6;
      VendingApiContext var9 = this.mApiContext;
      boolean var10 = this.mUseSecureAuthToken;
      VendingRetryPolicy var11 = new VendingRetryPolicy(var9, var10);
      this.setRetryPolicy(var11);
   }

   public static <T extends MessageMicro, U extends MessageMicro> VendingRequest<T, U> make(String var0, Class<T> var1, T var2, Class<U> var3, Response.Listener<U> var4, VendingApiContext var5, Response.ErrorListener var6) {
      return new VendingRequest(var0, var1, var2, var3, var4, var5, var6);
   }

   public void deliverError(Response.ErrorCode var1, String var2, NetworkError var3) {
      Response.ErrorCode var4 = Response.ErrorCode.AUTH;
      if(var1 == var4) {
         VendingApiContext var5 = this.mApiContext;
         boolean var6 = this.mUseSecureAuthToken;
         var5.invalidateAuthToken(var6);
      }

      super.deliverError(var1, var2, var3);
   }

   protected void deliverResponse(VendingProtos.ResponseProto var1) {
      if(var1.getResponseCount() != 1) {
         throw new IllegalArgumentException("Only exactly one response message is allowed.");
      } else {
         VendingProtos.ResponseProto.Response var2 = var1.getResponse(0);
         Class var3 = this.mResponseClass;
         MessageMicro var4 = MicroProtoHelper.getParsedResponseFromWrapper(var2, VendingProtos.ResponseProto.Response.class, var3);
         this.mListener.onResponse(var4);
      }
   }

   public Map<String, String> getHeaders() throws AuthFailureException {
      return this.mApiContext.getHeaders();
   }

   public Map<String, String> getPostParams() throws AuthFailureException {
      HashMap var1 = Maps.newHashMap();
      MessageMicro var2 = this.mRequest;
      String var3 = this.serializeRequestProto(var2);
      var1.put("request", var3);
      String var5 = String.valueOf(2);
      var1.put("version", var5);
      return var1;
   }

   protected boolean handlePendingNotifications(VendingProtos.ResponseProto var1, boolean var2) {
      boolean var3 = false;
      if(var1.hasPendingNotifications()) {
         VendingProtos.PendingNotificationsProto var4 = var1.getPendingNotifications();
         PendingNotificationsHandler var5 = FinskyInstance.get().getPendingNotificationsHandler();
         if(var5 != null) {
            Context var6 = FinskyApp.get().getApplicationContext();
            String var7 = this.mApiContext.getAccount().name;
            if(var5.handlePendingNotifications(var6, var7, var4, var2)) {
               var3 = true;
            }
         }
      }

      return var3;
   }

   public boolean isSecure() {
      return this.mUseSecureAuthToken;
   }

   protected Response<VendingProtos.ResponseProto> parseNetworkResponse(NetworkResponse var1) {
      Response var7;
      Response var8;
      try {
         byte[] var2 = var1.data;
         ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
         int var4 = var1.data.length;
         VendingProtos.ResponseProto var5 = VendingProtos.ResponseProto.parseFrom(CodedInputStreamMicro.newInstance((InputStream)(new GZIPInputStream(var3, var4))));
         this.handlePendingNotifications(var5, (boolean)1);
         var7 = Response.success(var5, (Cache.Entry)null);
      } catch (IOException var12) {
         String var10 = "Cannot parse Vending ResponseProto: " + var12;
         Object[] var11 = new Object[0];
         FinskyLog.w(var10, var11);
         var8 = Response.error(Response.ErrorCode.NETWORK, (String)null, (NetworkError)null);
         return var8;
      }

      var8 = var7;
      return var8;
   }

   String serializeRequestProto(T var1) throws AuthFailureException {
      VendingProtos.RequestProto.Request var2 = new VendingProtos.RequestProto.Request();
      Class var3 = this.mRequestClass;
      MicroProtoHelper.setRequestInWrapper(var2, VendingProtos.RequestProto.Request.class, var1, var3);
      VendingProtos.RequestProto var4 = new VendingProtos.RequestProto();
      VendingApiContext var5 = this.mApiContext;
      boolean var6 = this.mUseSecureAuthToken;
      VendingProtos.RequestPropertiesProto var7 = var5.getRequestProperties(var6);
      var4.setRequestProperties(var7);
      var4.addRequest(var2);
      return Base64.encodeToString(var4.toByteArray(), 11);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = super.toString();
      StringBuilder var3 = var1.append(var2).append(" ");
      String var4 = this.mRequestClass.getSimpleName();
      return var3.append(var4).toString();
   }
}
