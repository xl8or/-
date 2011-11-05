package com.google.android.finsky.download;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadManagerConstants;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadQueueImpl;
import com.google.android.finsky.download.InternalDownload;
import com.google.android.finsky.utils.FinskyLog;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class DownloadProgressManager {

   private static volatile Map<Uri, DownloadProgress> sDownloadProgressMap = null;
   private final Context mContext;
   private Cursor mCursor = null;
   private final DownloadQueueImpl mDownloadQueueImpl;
   private final Handler mHandler;
   private final HandlerThread mHandlerThread;
   private final long mHandlerThreadId;


   public DownloadProgressManager(DownloadQueueImpl var1) {
      FinskyApp var2 = FinskyApp.get();
      this.mContext = var2;
      this.mDownloadQueueImpl = var1;
      HandlerThread var3 = new HandlerThread("Download progress manager runner");
      this.mHandlerThread = var3;
      this.mHandlerThread.start();
      long var4 = this.mHandlerThread.getId();
      this.mHandlerThreadId = var4;
      Looper var6 = this.mHandlerThread.getLooper();
      Handler var7 = new Handler(var6);
      this.mHandler = var7;
      Handler var8 = this.mHandler;
      DownloadProgressManager.1 var9 = new DownloadProgressManager.1();
      var8.post(var9);
   }

   private void assertOnHandlerThread() {
      long var1 = Thread.currentThread().getId();
      long var3 = this.mHandlerThreadId;
      if(var1 != var3) {
         StringBuilder var5 = (new StringBuilder()).append("This should only be run on DownloadProgressManager\'s handler thread (");
         long var6 = this.mHandlerThreadId;
         String var8 = var5.append(var6).append("). ").append("Instead we\'re on thread ").append(var1).toString();
         throw new IllegalStateException(var8);
      }
   }

   private Map<Uri, DownloadProgress> generateDownloadProgressFromCursor() {
      this.assertOnHandlerThread();
      HashMap var1 = new HashMap();
      if(!this.mCursor.requery()) {
         this.mCursor = null;
         Context var2 = this.mContext;
         this.makeCursorIfNeeded(var2);
      }

      if(this.mCursor.getCount() > 0) {
         int var3 = this.mCursor.getColumnIndexOrThrow("_id");
         int var4 = this.mCursor.getColumnIndexOrThrow("current_bytes");
         int var5 = this.mCursor.getColumnIndexOrThrow("total_bytes");
         int var6 = this.mCursor.getColumnIndexOrThrow("status");

         while(this.mCursor.moveToNext()) {
            long var7 = this.mCursor.getLong(var3);
            Uri var9 = ContentUris.withAppendedId(DownloadManagerConstants.getContentUri(), var7);
            int var10 = this.mCursor.getInt(var6);
            long var11 = this.mCursor.getLong(var5);
            long var13 = this.mCursor.getLong(var4);
            DownloadProgress var15 = new DownloadProgress(var13, var11, var10);
            var1.put(var9, var15);
         }
      }

      return var1;
   }

   public static DownloadProgress getCachedProgress(Uri var0) {
      Map var1 = getCachedProgress();
      DownloadProgress var2;
      if(var1 != null) {
         var2 = (DownloadProgress)var1.get(var0);
      } else {
         var2 = null;
      }

      return var2;
   }

   private static Map<Uri, DownloadProgress> getCachedProgress() {
      return sDownloadProgressMap;
   }

   private static Set<Uri> getUris() {
      HashSet var0 = new HashSet();
      Map var1 = getCachedProgress();
      if(var1 != null) {
         Iterator var2 = var1.keySet().iterator();

         while(var2.hasNext()) {
            Uri var3 = (Uri)var2.next();
            var0.add(var3);
         }
      }

      return var0;
   }

   private static Cursor makeCursor(Context var0) {
      Cursor var1 = null;
      ContentResolver var2 = var0.getContentResolver();
      Uri var3 = DownloadManagerConstants.getContentUri();
      Cursor var7 = var2.query(var3, var1, var1, var1, var1);
      if(var7 == null) {
         StringBuilder var8 = (new StringBuilder()).append("Download progress cursor null: ");
         Uri var9 = DownloadManagerConstants.getContentUri();
         String var10 = var8.append(var9).toString();
         Object[] var11 = new Object[0];
         FinskyLog.w(var10, var11);
      } else {
         var1 = var7;
      }

      return var1;
   }

   private void makeCursorIfNeeded(Context var1) {
      this.assertOnHandlerThread();
      if(this.mCursor == null) {
         Cursor var2 = makeCursor(var1);
         this.mCursor = var2;
         Handler var3 = this.mHandler;
         DownloadProgressManager.2 var4 = new DownloadProgressManager.2(var3);
         this.mCursor.registerContentObserver(var4);
      }
   }

   private void onDownloadProgress() {
      this.assertOnHandlerThread();
      Set var1 = getUris();
      Map var2 = Collections.unmodifiableMap(this.generateDownloadProgressFromCursor());
      Map var3 = getCachedProgress();
      if(var3 == null || !var3.equals(var2)) {
         sDownloadProgressMap = var2;
         Set var4 = getUris();
         var1.removeAll(var4);
         DownloadProgressManager.ProgressRunnable var6 = new DownloadProgressManager.ProgressRunnable(var2, var1, var4);
         Looper var7 = Looper.getMainLooper();
         boolean var8 = (new Handler(var7)).post(var6);
      }
   }

   public void cleanup() {
      Handler var1 = this.mHandler;
      DownloadProgressManager.3 var2 = new DownloadProgressManager.3();
      var1.post(var2);
   }

   private class ProgressRunnable implements Runnable {

      private Map<Uri, DownloadProgress> mDownloadProgressMap;
      private Set<Uri> mNewUris;
      private Set<Uri> mOldUris;


      public ProgressRunnable(Map var2, Set var3, Set var4) {
         this.mOldUris = var3;
         this.mNewUris = var4;
         this.mDownloadProgressMap = var2;
      }

      public void run() {
         DownloadQueueImpl var1 = DownloadProgressManager.this.mDownloadQueueImpl;
         Iterator var2 = this.mOldUris.iterator();

         while(var2.hasNext()) {
            Uri var3 = (Uri)var2.next();
            InternalDownload var4 = var1.getExisting(var3);
            if(var4 != null) {
               Download.DownloadState var5 = var4.getState();
               Download.DownloadState var6 = Download.DownloadState.DOWNLOADING;
               if(var5.equals(var6)) {
                  var4.cancel();
               }
            }
         }

         Iterator var7 = this.mNewUris.iterator();

         while(var7.hasNext()) {
            Uri var8 = (Uri)var7.next();
            InternalDownload var9 = var1.getExisting(var8);
            if(var9 != null) {
               DownloadProgress var10 = (DownloadProgress)this.mDownloadProgressMap.get(var8);
               var9.updateDownloadProgress(var10);
            }
         }

      }
   }

   class 2 extends ContentObserver {

      2(Handler var2) {
         super(var2);
      }

      public boolean deliverSelfNotifications() {
         return false;
      }

      public void onChange(boolean var1) {
         DownloadProgressManager.this.onDownloadProgress();
      }
   }

   class 3 implements Runnable {

      3() {}

      public void run() {
         if(DownloadProgressManager.this.mCursor != null) {
            DownloadProgressManager.this.mCursor.close();
            Cursor var1 = DownloadProgressManager.this.mCursor = null;
         }

         boolean var2 = DownloadProgressManager.this.mHandlerThread.quit();
      }
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         DownloadProgressManager var1 = DownloadProgressManager.this;
         Context var2 = DownloadProgressManager.this.mContext;
         var1.makeCursorIfNeeded(var2);
         DownloadProgressManager.this.onDownloadProgress();
      }
   }
}
