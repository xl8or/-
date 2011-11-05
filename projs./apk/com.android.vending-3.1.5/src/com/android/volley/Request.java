package com.android.volley;

import android.os.Handler;
import android.os.Looper;
import com.android.volley.AuthFailureException;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyLog;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Request<T extends Object> implements Comparable<Request<T>> {

   private static final String DEFAULT_POST_PARAMS_ENCODING = "UTF-8";
   private static final long SLOW_REQUEST_THRESHOLD_MS = 3000L;
   private Cache.Entry mCacheEntry;
   private boolean mCanceled;
   private boolean mDrainable;
   private final Response.ErrorListener mErrorListener;
   private final VolleyLog.MarkerLog mEventLog;
   private long mRequestBirthTime;
   private RequestQueue mRequestQueue;
   private boolean mResponseDelivered;
   private RetryPolicy mRetryPolicy;
   private Integer mSequence;
   private boolean mShouldCache;
   private final String mUrl;


   public Request(String var1, Response.ErrorListener var2) {
      VolleyLog.MarkerLog var3;
      if(VolleyLog.MarkerLog.ENABLED) {
         var3 = new VolleyLog.MarkerLog();
      } else {
         var3 = null;
      }

      this.mEventLog = var3;
      this.mShouldCache = (boolean)1;
      this.mCanceled = (boolean)0;
      this.mDrainable = (boolean)1;
      this.mResponseDelivered = (boolean)0;
      this.mRequestBirthTime = 0L;
      this.mCacheEntry = null;
      this.mUrl = var1;
      this.mErrorListener = var2;
      DefaultRetryPolicy var4 = new DefaultRetryPolicy();
      this.setRetryPolicy(var4);
   }

   private byte[] encodePostParameters(Map<String, String> var1, String var2) {
      StringBuilder var3 = new StringBuilder();

      try {
         StringBuilder var11;
         for(Iterator var4 = var1.entrySet().iterator(); var4.hasNext(); var11 = var3.append('&')) {
            Entry var5 = (Entry)var4.next();
            String var6 = URLEncoder.encode((String)var5.getKey(), var2);
            var3.append(var6);
            StringBuilder var8 = var3.append('=');
            String var9 = URLEncoder.encode((String)var5.getValue(), var2);
            var3.append(var9);
         }

         byte[] var14 = var3.toString().getBytes(var2);
         return var14;
      } catch (UnsupportedEncodingException var15) {
         String var13 = "Encoding not supported: " + var2;
         throw new RuntimeException(var13, var15);
      }
   }

   public void addMarker(String var1) {
      if(VolleyLog.MarkerLog.ENABLED) {
         VolleyLog.MarkerLog var2 = this.mEventLog;
         long var3 = Thread.currentThread().getId();
         var2.add(var1, var3);
      } else if(this.mRequestBirthTime == 0L) {
         long var5 = System.currentTimeMillis();
         this.mRequestBirthTime = var5;
      }
   }

   public void cancel() {
      this.mCanceled = (boolean)1;
   }

   public int compareTo(Request<T> var1) {
      Request.Priority var2 = this.getPriority();
      Request.Priority var3 = var1.getPriority();
      int var6;
      if(var2 == var3) {
         int var4 = this.mSequence.intValue();
         int var5 = var1.mSequence.intValue();
         var6 = var4 - var5;
      } else {
         int var7 = var3.ordinal();
         int var8 = var2.ordinal();
         var6 = var7 - var8;
      }

      return var6;
   }

   public void deliverError(Response.ErrorCode var1, String var2, NetworkError var3) {
      if(this.mErrorListener != null) {
         this.mErrorListener.onErrorResponse(var1, var2, var3);
      }
   }

   protected abstract void deliverResponse(T var1);

   void finish(String var1) {
      if(this.mRequestQueue != null) {
         this.mRequestQueue.finish(this);
      }

      if(VolleyLog.MarkerLog.ENABLED) {
         long var2 = Thread.currentThread().getId();
         Looper var4 = Looper.myLooper();
         Looper var5 = Looper.getMainLooper();
         if(var4 != var5) {
            Looper var6 = Looper.getMainLooper();
            Handler var7 = new Handler(var6);
            Request.1 var8 = new Request.1(var1, var2);
            var7.post(var8);
         } else {
            this.mEventLog.add(var1, var2);
            VolleyLog.MarkerLog var10 = this.mEventLog;
            String var11 = this.toString();
            var10.finish(var11);
         }
      } else {
         long var12 = System.currentTimeMillis();
         long var14 = this.mRequestBirthTime;
         long var16 = var12 - var14;
         if(var16 >= 3000L) {
            Object[] var18 = new Object[2];
            Long var19 = Long.valueOf(var16);
            var18[0] = var19;
            String var20 = this.toString();
            var18[1] = var20;
            VolleyLog.d("%d ms: %s", var18);
         }
      }
   }

   public Cache.Entry getCacheEntry() {
      return this.mCacheEntry;
   }

   public String getCacheKey() {
      return this.getUrl();
   }

   public Map<String, String> getHeaders() throws AuthFailureException {
      return Collections.emptyMap();
   }

   public byte[] getPostBody() throws AuthFailureException {
      Map var1 = this.getPostParams();
      byte[] var3;
      if(var1 != null && var1.size() > 0) {
         String var2 = this.getPostParamsEncoding();
         var3 = this.encodePostParameters(var1, var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public String getPostBodyContentType() {
      StringBuilder var1 = (new StringBuilder()).append("application/x-www-form-urlencoded; charset=");
      String var2 = this.getPostParamsEncoding();
      return var1.append(var2).toString();
   }

   protected Map<String, String> getPostParams() throws AuthFailureException {
      return null;
   }

   protected String getPostParamsEncoding() {
      return "UTF-8";
   }

   public Request.Priority getPriority() {
      return Request.Priority.NORMAL;
   }

   public RetryPolicy getRetryPolicy() {
      return this.mRetryPolicy;
   }

   public final int getSequence() {
      if(this.mSequence == null) {
         throw new IllegalStateException("getSequence called before setSequence");
      } else {
         return this.mSequence.intValue();
      }
   }

   public final int getTimeoutMs() {
      return this.mRetryPolicy.getCurrentTimeout();
   }

   public String getUrl() {
      return this.mUrl;
   }

   public boolean hasHadResponseDelivered() {
      return this.mResponseDelivered;
   }

   public boolean isCanceled() {
      return this.mCanceled;
   }

   public boolean isDrainable() {
      return this.mDrainable;
   }

   public void markDelivered() {
      this.mResponseDelivered = (boolean)1;
   }

   protected NetworkError parseNetworkError(NetworkError var1) {
      return var1;
   }

   protected abstract Response<T> parseNetworkResponse(NetworkResponse var1);

   public void setCacheEntry(Cache.Entry var1) {
      this.mCacheEntry = var1;
   }

   public void setDrainable(boolean var1) {
      this.mDrainable = var1;
   }

   public void setRequestQueue(RequestQueue var1) {
      this.mRequestQueue = var1;
   }

   public void setRetryPolicy(RetryPolicy var1) {
      this.mRetryPolicy = var1;
   }

   public final void setSequence(int var1) {
      Integer var2 = Integer.valueOf(var1);
      this.mSequence = var2;
   }

   public final void setShouldCache(boolean var1) {
      this.mShouldCache = var1;
   }

   public final boolean shouldCache() {
      return this.mShouldCache;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2;
      if(this.mCanceled) {
         var2 = "[X] ";
      } else {
         var2 = "[ ] ";
      }

      StringBuilder var3 = var1.append(var2);
      String var4 = this.getUrl();
      StringBuilder var5 = var3.append(var4).append(" ");
      Request.Priority var6 = this.getPriority();
      StringBuilder var7 = var5.append(var6).append(" ");
      Integer var8 = this.mSequence;
      return var7.append(var8).toString();
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final String val$tag;
      // $FF: synthetic field
      final long val$threadId;


      1(String var2, long var3) {
         this.val$tag = var2;
         this.val$threadId = var3;
      }

      public void run() {
         VolleyLog.MarkerLog var1 = Request.this.mEventLog;
         String var2 = this.val$tag;
         long var3 = this.val$threadId;
         var1.add(var2, var3);
         VolleyLog.MarkerLog var5 = Request.this.mEventLog;
         String var6 = this.toString();
         var5.finish(var6);
      }
   }

   public static enum Priority {

      // $FF: synthetic field
      private static final Request.Priority[] $VALUES;
      HIGH("HIGH", 2),
      IMMEDIATE("IMMEDIATE", 3),
      LOW("LOW", 0),
      NORMAL("NORMAL", 1);


      static {
         Request.Priority[] var0 = new Request.Priority[4];
         Request.Priority var1 = LOW;
         var0[0] = var1;
         Request.Priority var2 = NORMAL;
         var0[1] = var2;
         Request.Priority var3 = HIGH;
         var0[2] = var3;
         Request.Priority var4 = IMMEDIATE;
         var0[3] = var4;
         $VALUES = var0;
      }

      private Priority(String var1, int var2) {}
   }
}
