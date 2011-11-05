package com.google.android.finsky.analytics;

import android.os.Handler;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.G;
import com.google.android.finsky.remoting.protos.Log;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DfeAnalytics implements Analytics {

   private static final int DISPATCH_PERIOD_MS = ((Integer)G.logsDispatchIntervalSeconds.get()).intValue() * 1000;
   private static final int MAX_LOGS_BEFORE_FLUSH = ((Integer)G.maxLogQueueSizeBeforeFlush.get()).intValue();
   private DfeApi mDfeApi;
   private final Handler mHandler;
   private final Runnable mLogFlusher;
   private List<Log.ClickLogEvent> mPendingEvents;


   public DfeAnalytics(Handler var1) {
      ArrayList var2 = Lists.newArrayList();
      this.mPendingEvents = var2;
      DfeAnalytics.3 var3 = new DfeAnalytics.3();
      this.mLogFlusher = var3;
      this.mHandler = var1;
   }

   public DfeAnalytics(Handler var1, DfeApi var2) {
      this(var1);
      this.mDfeApi = var2;
   }

   private void flushLogs() {
      Log.LogRequest var1 = new Log.LogRequest();
      int var2 = this.mPendingEvents.size();
      Iterator var3 = this.mPendingEvents.iterator();

      while(var3.hasNext()) {
         Log.ClickLogEvent var4 = (Log.ClickLogEvent)var3.next();
         var1.addClickEvent(var4);
      }

      this.mPendingEvents.clear();
      DfeAnalytics.1 var6 = new DfeAnalytics.1(var2);
      DfeAnalytics.2 var7 = new DfeAnalytics.2(var2);
      this.mDfeApi.log(var1, var6, var7);
   }

   private static Log.ClickLogEvent makeClickLogEvent(String var0, String var1, String var2, String var3) {
      Log.ClickLogEvent var4 = new Log.ClickLogEvent();
      long var5 = System.currentTimeMillis();
      var4.setEventTime(var5);
      if(var0 != null) {
         var4.setReferrerUrl(var0);
      }

      if(var1 != null) {
         var4.setReferrerListId(var1);
      }

      if(var2 != null) {
         var4.setUrl(var2);
      }

      if(var3 != null) {
         var4.setListId(var3);
      }

      return var4;
   }

   private void scheduleFlush() {
      Handler var1 = this.mHandler;
      Runnable var2 = this.mLogFlusher;
      var1.removeCallbacks(var2);
      int var3 = this.mPendingEvents.size();
      int var4 = MAX_LOGS_BEFORE_FLUSH;
      if(var3 < var4) {
         Handler var5 = this.mHandler;
         Runnable var6 = this.mLogFlusher;
         long var7 = (long)DISPATCH_PERIOD_MS;
         var5.postDelayed(var6, var7);
      } else {
         Handler var10 = this.mHandler;
         Runnable var11 = this.mLogFlusher;
         var10.post(var11);
      }
   }

   public void logListViewOnPage(String var1, String var2, String var3, String var4) {
      if(FinskyLog.DEBUG) {
         Object[] var5 = new Object[]{var1, var2, var3, var4};
         FinskyLog.v("Logging list view: referrerUrl=[%s], referrerCookie=[%s], currentPageUrl=[%s], listCookie=[%s]", var5);
      }

      List var6 = this.mPendingEvents;
      Log.ClickLogEvent var7 = makeClickLogEvent(var1, var2, var3, var4);
      var6.add(var7);
      this.scheduleFlush();
   }

   public void logPageView(String var1, String var2, String var3) {
      if(FinskyLog.DEBUG) {
         Object[] var4 = new Object[]{var1, var2, var3};
         FinskyLog.v("Logging page view: referrerUrl=[%s], referrerCookie=[%s], currentPageUrl=[%s]", var4);
      }

      List var5 = this.mPendingEvents;
      Log.ClickLogEvent var6 = makeClickLogEvent(var1, var2, var3, (String)null);
      var5.add(var6);
      this.scheduleFlush();
   }

   public void reset() {
      this.mPendingEvents.clear();
      DfeApi var1 = FinskyApp.get().getDfeApi();
      this.mDfeApi = var1;
   }

   class 3 implements Runnable {

      3() {}

      public void run() {
         DfeAnalytics.this.flushLogs();
      }
   }

   class 2 implements Response.ErrorListener {

      // $FF: synthetic field
      final int val$currentLogCount;


      2(int var2) {
         this.val$currentLogCount = var2;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Object[] var4 = new Object[4];
         Integer var5 = Integer.valueOf(this.val$currentLogCount);
         var4[0] = var5;
         String var6 = var1.name();
         var4[1] = var6;
         var4[2] = var2;
         var4[3] = var3;
         FinskyLog.e("Failed to send %d analytics event because of [%s, %s, %s]", var4);
      }
   }

   class 1 implements Response.Listener<Log.LogResponse> {

      // $FF: synthetic field
      final int val$currentLogCount;


      1(int var2) {
         this.val$currentLogCount = var2;
      }

      public void onResponse(Log.LogResponse var1) {
         Object[] var2 = new Object[1];
         Integer var3 = Integer.valueOf(this.val$currentLogCount);
         var2[0] = var3;
         FinskyLog.v("Logged %d analytics events successfully.", var2);
      }
   }
}
