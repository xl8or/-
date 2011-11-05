package com.android.volley.toolbox;

import android.content.Context;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerException;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.GoogleHttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.google.android.finsky.utils.Maps;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.cookie.DateUtils;

public class BasicNetwork implements Network {

   protected static final boolean DEBUG = VolleyLog.DEBUG;
   private static int SLOW_REQUEST_THRESHOLD_MS = 3000;
   protected final Context mContext;
   protected final HttpStack mHttpStack;


   public BasicNetwork(Context var1) {
      GoogleHttpClientStack var2 = new GoogleHttpClientStack(var1);
      this(var1, var2);
   }

   public BasicNetwork(Context var1, HttpStack var2) {
      this.mContext = var1;
      this.mHttpStack = var2;
   }

   private void addCacheHeaders(Map<String, String> var1, Cache.Entry var2) {
      if(var2 != null) {
         if(var2.etag != null) {
            String var3 = var2.etag;
            var1.put("If-None-Match", var3);
         }

         if(var2.serverDate > 0L) {
            long var5 = var2.serverDate;
            String var7 = DateUtils.formatDate(new Date(var5));
            var1.put("If-Modified-Since", var7);
         }
      }
   }

   private static void attemptRetryOnException(String var0, Request<?> var1, NetworkError var2) throws NetworkError {
      RetryPolicy var3 = var1.getRetryPolicy();
      int var4 = var1.getTimeoutMs();

      try {
         var3.retry(var2);
      } catch (NetworkError var12) {
         Object[] var9 = new Object[]{var0, null};
         Integer var10 = Integer.valueOf(var4);
         var9[1] = var10;
         String var11 = String.format("%s-timeout-giveup [timeout=%s]", var9);
         var1.addMarker(var11);
         throw var12;
      }

      Object[] var5 = new Object[]{var0, null};
      Integer var6 = Integer.valueOf(var4);
      var5[1] = var6;
      String var7 = String.format("%s-retry [timeout=%s]", var5);
      var1.addMarker(var7);
   }

   private static Map<String, String> convertHeaders(Header[] var0) {
      HashMap var1 = Maps.newHashMap();
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return var1;
         }

         String var4 = var0[var2].getName();
         String var5 = var0[var2].getValue();
         var1.put(var4, var5);
         ++var2;
      }
   }

   static byte[] entityToBytes(HttpEntity var0) throws IOException, ServerException {
      boolean var15 = false;

      byte[] var6;
      try {
         var15 = true;
         InputStream var1 = var0.getContent();
         if(var1 == null) {
            throw new ServerException();
         }

         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         byte[] var4 = new byte[1024];

         while(true) {
            int var5 = var1.read(var4);
            if(var5 == -1) {
               var6 = var3.toByteArray();
               var15 = false;
               break;
            }

            var3.write(var4, 0, var5);
         }
      } finally {
         if(var15) {
            try {
               var0.consumeContent();
            } catch (IOException var16) {
               Object[] var11 = new Object[0];
               VolleyLog.v("Error occured when calling consumingContent", var11);
            }

         }
      }

      try {
         var0.consumeContent();
      } catch (IOException var17) {
         Object[] var9 = new Object[0];
         VolleyLog.v("Error occured when calling consumingContent", var9);
      }

      return var6;
   }

   private void logSlowRequests(long var1, Request<?> var3, byte[] var4, StatusLine var5) {
      if(!DEBUG) {
         long var6 = (long)SLOW_REQUEST_THRESHOLD_MS;
         if(var1 <= var6) {
            return;
         }
      }

      String var8 = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]";
      Object[] var9 = new Object[5];
      var9[0] = var3;
      Long var10 = Long.valueOf(var1);
      var9[1] = var10;
      byte var11 = 2;
      Object var12;
      if(var4 != null) {
         var12 = Integer.valueOf(var4.length);
      } else {
         var12 = "null";
      }

      var9[var11] = var12;
      Integer var13 = Integer.valueOf(var5.getStatusCode());
      var9[3] = var13;
      Integer var14 = Integer.valueOf(var3.getRetryPolicy().getCurrentRetryCount());
      var9[4] = var14;
      VolleyLog.d(var8, var9);
   }

   protected void logError(String var1, String var2, long var3) {
      long var5 = System.currentTimeMillis();
      Object[] var7 = new Object[]{var1, null, null};
      Long var8 = Long.valueOf(var5 - var3);
      var7[1] = var8;
      var7[2] = var2;
      VolleyLog.v("HTTP ERROR(%s) %d ms to fetch %s", var7);
   }

   public NetworkResponse performRequest(Request<?> param1) throws NetworkError {
      // $FF: Couldn't be decompiled
   }
}
