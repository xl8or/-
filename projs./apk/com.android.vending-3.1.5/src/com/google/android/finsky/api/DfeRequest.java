package com.google.android.finsky.api;

import android.net.Uri;
import android.util.Log;
import com.android.volley.AuthFailureException;
import com.android.volley.Cache;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.ServerException;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.MicroProtoHelper;
import com.android.volley.toolbox.MicroProtoPrinter;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.api.DfeRetryPolicy;
import com.google.android.finsky.config.G;
import com.google.android.finsky.remoting.protos.Response;
import com.google.android.finsky.utils.FinskyLog;
import com.google.protobuf.micro.MessageMicro;
import java.util.Iterator;
import java.util.Map;

public class DfeRequest<T extends MessageMicro> extends Request<Response.ResponseWrapper> {

   private static final boolean DEBUG = FinskyLog.DEBUG;
   private static final boolean PROTO_DEBUG = Log.isLoggable("DfeProto", 2);
   private static final String TAG_PROTO_LOG = "DfeProto";
   private final DfeApiContext mApiContext;
   private com.android.volley.Response<T> mListener;
   private final Class<T> mResponseClass;
   private boolean mResponseDelivered;


   public DfeRequest(String var1, DfeApiContext var2, Class<T> var3, com.android.volley.Response<T> var4, com.android.volley.Response var5) {
      String var6 = Uri.withAppendedPath(DfeApi.BASE_URI, var1).toString();
      super(var6, var5);
      DfeRetryPolicy var7 = new DfeRetryPolicy(var2);
      this.setRetryPolicy(var7);
      this.mApiContext = var2;
      this.mListener = var4;
      this.mResponseClass = var3;
   }

   private void handlePrefetch(Response.ResponseWrapper var1, Cache.Entry var2) {
      if(var1.getPreFetchCount() >= 1) {
         Cache var3 = this.mApiContext.getCache();
         long var4 = System.currentTimeMillis();
         Iterator var6 = var1.getPreFetchList().iterator();

         while(var6.hasNext()) {
            Response.PreFetch var7 = (Response.PreFetch)var6.next();
            Cache.Entry var8 = new Cache.Entry();
            byte[] var9 = var7.getResponse().toByteArray();
            var8.data = var9;
            String var10 = var7.getEtag();
            var8.etag = var10;
            long var11 = var2.serverDate;
            var8.serverDate = var11;
            long var13 = var7.getTtl() + var4;
            var8.ttl = var13;
            long var15 = var7.getSoftTtl() + var4;
            var8.softTtl = var15;
            Uri var17 = DfeApi.BASE_URI;
            String var18 = var7.getUrl();
            String var19 = Uri.withAppendedPath(var17, var18).toString();
            String var20 = this.makeCacheKey(var19);
            var3.put(var20, var8);
         }

         Response.ResponseWrapper var21 = var1.clearPreFetch();
         byte[] var22 = var1.toByteArray();
         var2.data = var22;
      }
   }

   private com.android.volley.Response<Response.ResponseWrapper> handleServerCommands(Response.ResponseWrapper var1) {
      com.android.volley.Response var2 = null;
      if(var1.hasCommands()) {
         Response.ServerCommands var3 = var1.getCommands();
         if(var3.hasLogErrorStacktrace()) {
            String var4 = var3.getLogErrorStacktrace();
            Object[] var5 = new Object[0];
            FinskyLog.d(var4, var5);
         }

         if(var3.getClearCache()) {
            this.mApiContext.getCache().clear();
         }

         if(var3.hasDisplayErrorMessage()) {
            com.android.volley.Response var6 = com.android.volley.Response.SERVER;
            String var7 = var3.getDisplayErrorMessage();
            var2 = com.android.volley.Response.error(var6, var7, (NetworkError)null);
         }
      }

      return var2;
   }

   private void logProtoResponse(Response.ResponseWrapper var1) {
      String var2 = (String)G.protoLogUrlRegexp.get();
      if(this.getUrl().matches(var2)) {
         Class var3 = MicroProtoPrinter.class;
         synchronized(var3) {
            StringBuilder var4 = (new StringBuilder()).append("Response for ");
            String var5 = this.getUrl();
            String var6 = var4.append(var5).toString();
            int var7 = Log.v("DfeProto", var6);
            String[] var8 = MicroProtoPrinter.prettyPrint("ResponseWrapper", Response.ResponseWrapper.class, var1).split("\n");
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               String var11 = var8[var10];
               String var12 = "| " + var11;
               int var13 = Log.v("DfeProto", var12);
            }

         }
      } else {
         StringBuilder var15 = (new StringBuilder()).append("Url does not match regexp: url=");
         String var16 = this.getUrl();
         String var17 = var15.append(var16).append(" / regexp=").append(var2).toString();
         int var18 = Log.v("DfeProto", var17);
      }
   }

   private String makeCacheKey(String var1) {
      StringBuilder var2 = (new StringBuilder()).append(var1).append("/account=");
      String var3 = this.mApiContext.getAccountName();
      return var2.append(var3).toString();
   }

   public static Cache.Entry parseCacheHeaders(NetworkResponse var0) {
      Cache.Entry var1 = HttpHeaderParser.parseCacheHeaders(var0);
      long var2 = System.currentTimeMillis();

      try {
         String var4 = (String)var0.headers.get("X-DFE-Soft-TTL");
         if(var4 != null) {
            long var5 = Long.parseLong(var4) + var2;
            var1.softTtl = var5;
         }

         String var7 = (String)var0.headers.get("X-DFE-Hard-TTL");
         if(var7 != null) {
            long var8 = Long.parseLong(var7) + var2;
            var1.ttl = var8;
         }
      } catch (NumberFormatException var19) {
         Object[] var17 = new Object[1];
         Map var18 = var0.headers;
         var17[0] = var18;
         FinskyLog.d("Invalid TTL: %s", var17);
         var1.softTtl = 0L;
         var1.ttl = 0L;
      }

      long var10 = var1.ttl;
      long var12 = var1.softTtl;
      long var14 = Math.max(var10, var12);
      var1.ttl = var14;
      return var1;
   }

   private static Response.ResponseWrapper parseWrapper(NetworkResponse param0, boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public void deliverError(com.android.volley.Response var1, String var2, NetworkError var3) {
      com.android.volley.Response var4 = com.android.volley.Response.AUTH;
      if(var1 == var4) {
         this.mApiContext.invalidateAuthToken();
      }

      if(!this.mResponseDelivered) {
         super.deliverError(var1, var2, var3);
      } else {
         Object[] var5 = new Object[4];
         var5[0] = this;
         String var6 = var1.name();
         var5[1] = var6;
         var5[2] = var2;
         var5[3] = var3;
         FinskyLog.d("Not delivering error response for request=[%s], errorCode=[%s], message=[%s], error=[%s] because response already delivered.", var5);
      }
   }

   public void deliverResponse(Response.ResponseWrapper var1) {
      Response.Payload var2 = var1.getPayload();
      Class var3 = this.mResponseClass;
      MessageMicro var4 = MicroProtoHelper.getParsedResponseFromWrapper(var2, Response.Payload.class, var3);
      if(var4 != null) {
         if(!this.mResponseDelivered) {
            this.mListener.onResponse(var4);
            this.mResponseDelivered = (boolean)1;
         } else {
            Object[] var5 = new Object[]{this};
            FinskyLog.d("Not delivering second response for request=[%s]", var5);
         }
      } else {
         Object[] var6 = new Object[]{this};
         FinskyLog.e("Null parsed response for request=[%s]", var6);
         com.android.volley.Response var7 = com.android.volley.Response.SERVER;
         ServerException var8 = new ServerException();
         this.deliverError(var7, (String)null, var8);
      }
   }

   public String getCacheKey() {
      String var1 = super.getUrl();
      return this.makeCacheKey(var1);
   }

   public Map<String, String> getHeaders() throws AuthFailureException {
      return this.mApiContext.getHeaders();
   }

   public String getUrl() {
      char var1 = 63;
      String var2 = super.getUrl();
      String var3 = (String)G.ipCountryOverride.get();
      if(var3 != null) {
         StringBuilder var4 = (new StringBuilder()).append(var2);
         if(var2.indexOf(63) != -1) {
            var1 = 38;
         }

         String var5 = var4.append(var1).toString();
         var2 = var5 + "ipCountryOverride=" + var3;
      }

      return var2;
   }

   protected NetworkError parseNetworkError(NetworkError var1) {
      if(var1 instanceof ServerException && var1.networkResponse != null) {
         Response.ResponseWrapper var2 = parseWrapper(var1.networkResponse, (boolean)0);
         if(var2 != null) {
            com.android.volley.Response var3 = this.handleServerCommands(var2);
            if(var3 != null && var3.message != null) {
               String var4 = var3.message;
               var1.displayError = var4;
            }
         }
      }

      return var1;
   }

   public com.android.volley.Response<Response.ResponseWrapper> parseNetworkResponse(NetworkResponse var1) {
      if(DEBUG) {
         Object[] var2 = new Object[1];
         Integer var3 = Integer.valueOf(var1.data.length);
         var2[0] = var3;
         FinskyLog.v("Response size: %d", var2);
      }

      Response.ResponseWrapper var4 = parseWrapper(var1, (boolean)0);
      com.android.volley.Response var5;
      if(var4 == null) {
         var5 = com.android.volley.Response.error(com.android.volley.Response.SERVER, (String)null, (NetworkError)null);
      } else {
         if(PROTO_DEBUG) {
            this.logProtoResponse(var4);
         }

         var5 = this.handleServerCommands(var4);
         if(var5 == null) {
            Cache.Entry var6 = parseCacheHeaders(var1);
            this.handlePrefetch(var4, var6);
            var5 = com.android.volley.Response.success(var4, var6);
         }
      }

      return var5;
   }

   public void setListener(com.android.volley.Response<T> var1) {
      this.mListener = var1;
   }
}
