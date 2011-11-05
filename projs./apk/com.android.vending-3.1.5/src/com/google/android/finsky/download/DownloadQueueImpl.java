package com.google.android.finsky.download;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadBroadcastReceiver;
import com.google.android.finsky.download.DownloadListenerRecovery;
import com.google.android.finsky.download.DownloadManager;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadProgressManager;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.DownloadQueueListener;
import com.google.android.finsky.download.DownloadRequest;
import com.google.android.finsky.download.DownloadUriUrlMap;
import com.google.android.finsky.download.InternalDownload;
import com.google.android.finsky.download.Storage;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PackageManagerUtils;
import com.google.android.finsky.utils.ParameterizedRunnable;
import com.google.android.finsky.utils.Utils;
import com.google.wireless.gdata.data.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class DownloadQueueImpl implements DownloadQueue {

   private static final int DEFAULT_MAX_CONCURRENT_DOWNLOADS = 1;
   static LinkedList<DownloadQueueListener> sCollectiveListeners;
   private final double BYTES_IN_MB;
   private Context mContext;
   private final DownloadManager mDownloadManager;
   private DownloadProgressManager mDownloadProgressManager;
   private final int mMaxConcurrent;
   private Notifier mNotificationHelper;
   private LinkedHashMap<String, InternalDownload> mPendingQueue;
   private HashMap<String, InternalDownload> mRunningMap;
   private DownloadUriUrlMap mUriUrlMap;


   public DownloadQueueImpl(Context var1, Notifier var2, int var3, DownloadManager var4, DownloadUriUrlMap var5) {
      double var6 = Math.pow(2.0D, 20.0D);
      this.BYTES_IN_MB = var6;
      this.setupQueue();
      this.mNotificationHelper = var2;
      this.mMaxConcurrent = var3;
      this.mDownloadManager = var4;
      this.mUriUrlMap = var5;
      this.mContext = var1;
   }

   public DownloadQueueImpl(Context var1, Notifier var2, DownloadManager var3, DownloadUriUrlMap var4) {
      this(var1, var2, 1, var3, var4);
   }

   private void enqueueDownload(InternalDownload var1) {
      FinskyApp var2 = FinskyApp.get();
      String var3 = var2.getPackageName();
      String var4 = DownloadBroadcastReceiver.class.getName();
      DownloadRequest var5 = var1.createDownloadRequest(var3, var4);
      DownloadManager var6 = this.mDownloadManager;
      DownloadQueueImpl.2 var7 = new DownloadQueueImpl.2(var2, var1);
      var6.enqueue(var5, var7);
   }

   private static List<DownloadQueueListener> getCollectiveListeners() {
      Utils.ensureOnMainThread();
      if(sCollectiveListeners == null) {
         sCollectiveListeners = new LinkedList();
      }

      return sCollectiveListeners;
   }

   private InternalDownload getExisting(String var1) {
      InternalDownload var2;
      if(this.mRunningMap.containsKey(var1)) {
         var2 = (InternalDownload)this.mRunningMap.get(var1);
      } else if(this.mPendingQueue.containsKey(var1)) {
         var2 = (InternalDownload)this.mPendingQueue.get(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   private static void notifyAggregateListeners() {
      Iterator var0 = getCollectiveListeners().iterator();

      while(var0.hasNext()) {
         DownloadQueueListener var1 = (DownloadQueueListener)var0.next();

         try {
            var1.onUpdate();
         } catch (Exception var4) {
            Object[] var3 = new Object[0];
            FinskyLog.wtf(var4, "Exception caught while iterating through global Downloads listener", var3);
         }
      }

   }

   private static void notifyAggregateListenersAdd(Download var0) {
      Iterator var1 = getCollectiveListeners().iterator();

      while(var1.hasNext()) {
         DownloadQueueListener var2 = (DownloadQueueListener)var1.next();

         try {
            var2.onAdd(var0);
         } catch (Exception var5) {
            Object[] var4 = new Object[0];
            FinskyLog.wtf(var5, "Exception caught while iterating through global Downloads listener", var4);
         }
      }

   }

   private void remove(InternalDownload var1) {
      Object[] var2 = new Object[1];
      String var3 = var1.toString();
      var2[0] = var3;
      FinskyLog.d("Download %s removed from DownloadQueue", var2);
      String var4 = var1.getUrl();
      if(this.mPendingQueue.containsKey(var4)) {
         this.mPendingQueue.remove(var4);
      } else {
         HashMap var6 = this.mRunningMap;
         String var7 = var1.getUrl();
         var6.remove(var7);
         Looper var9 = this.mContext.getMainLooper();
         Handler var10 = new Handler(var9);
         DownloadQueueImpl.1 var11 = new DownloadQueueImpl.1(var1);
         var10.post(var11);
         this.startNextDownload();
      }
   }

   private void setupQueue() {
      LinkedHashMap var1 = Maps.newLinkedHashMap();
      this.mPendingQueue = var1;
      HashMap var2 = Maps.newHashMap();
      this.mRunningMap = var2;
   }

   private void startNextDownload() {
      int var1 = this.mRunningMap.size();
      int var2 = this.mMaxConcurrent;
      if(var1 < var2) {
         long var3 = 0L;

         String var6;
         for(Iterator var5 = this.mPendingQueue.keySet().iterator(); var5.hasNext(); var3 = Math.max(((InternalDownload)this.mPendingQueue.get(var6)).getSize(), var3)) {
            var6 = (String)var5.next();
         }

         Context var7 = this.mContext;
         DownloadQueueImpl.PurgeCacheCallback var8 = new DownloadQueueImpl.PurgeCacheCallback((DownloadQueueImpl.1)null);
         PackageManagerUtils.freeStorageAndNotify(var7, var3, var8);
      }
   }

   public void add(InternalDownload var1) {
      label37: {
         Utils.ensureOnMainThread();
         String var2 = var1.getUrl();
         if(this.getExisting(var2) == null) {
            Download.DownloadState var3 = var1.getState();
            Download.DownloadState var4 = Download.DownloadState.UNQUEUED;
            if(var3.equals(var4)) {
               break label37;
            }
         }

         Object[] var5 = new Object[0];
         FinskyLog.wtf("Tried to add invalid download to DownloadQueue.", var5);
      }

      if(!Storage.externalStorageAvailable() && var1.getPackageProperties() != null) {
         label30: {
            ObbState var6 = var1.getPackageProperties().mainObb.getState();
            ObbState var7 = ObbState.NOT_APPLICABLE;
            if(var6 == var7) {
               ObbState var8 = var1.getPackageProperties().patchObb.getState();
               ObbState var9 = ObbState.NOT_APPLICABLE;
               if(var8 == var9) {
                  break label30;
               }
            }

            Notifier var10 = this.mNotificationHelper;
            String var11 = var1.getTitle();
            String var12 = var1.getPackageProperties().packageName;
            var10.showExternalStorageMissing(var11, var12);
            Object[] var13 = new Object[0];
            FinskyLog.e("Missing external storage", var13);
            Download.DownloadState var14 = Download.DownloadState.ERROR;
            var1.setState(var14);
            notifyAggregateListeners();
            return;
         }
      }

      Iterator var15 = var1.getWrappedObbDownloads().iterator();

      while(var15.hasNext()) {
         InternalDownload var16 = (InternalDownload)var15.next();
         this.add(var16);
      }

      DownloadManager var17 = this.mDownloadManager;
      var1.setDownloadManager(var17);
      Object[] var18 = new Object[1];
      String var19 = var1.toString();
      var18[0] = var19;
      FinskyLog.d("Download %s added to DownloadQueue", var18);
      DownloadQueueImpl.DownloadCompleteListener var20 = new DownloadQueueImpl.DownloadCompleteListener(var1);
      var1.addListener(var20);
      LinkedHashMap var21 = this.mPendingQueue;
      String var22 = var1.getUrl();
      var21.put(var22, var1);
      if(this.mDownloadProgressManager == null) {
         DownloadProgressManager var24 = new DownloadProgressManager(this);
         this.mDownloadProgressManager = var24;
      }

      Download.DownloadState var25 = Download.DownloadState.QUEUED;
      var1.setState(var25);
      notifyAggregateListenersAdd(var1);
      this.startNextDownload();
   }

   public void addListener(DownloadQueueListener var1) {
      boolean var2 = getCollectiveListeners().add(var1);
   }

   public void addRecoveredDownload(InternalDownload var1) {
      Utils.ensureOnMainThread();
      Download.DownloadState var2 = var1.getState();
      String var3 = var1.getUrl();
      DownloadManager var4 = this.mDownloadManager;
      var1.setDownloadManager(var4);
      Object[] var5 = new Object[1];
      String var6 = var2.toString();
      var5[0] = var6;
      FinskyLog.d("Download queue recovering download in state %s.", var5);
      Download.DownloadState var7 = Download.DownloadState.DOWNLOADING;
      if(var2.equals(var7)) {
         this.mRunningMap.put(var3, var1);
      } else {
         Object[] var12 = new Object[0];
         FinskyLog.wtf("Recovered downloads that are not currently running should either be restarted or have their asset state switched to INSTALLED", var12);
      }

      DownloadUriUrlMap var9 = this.mUriUrlMap;
      String var10 = var1.getContentUri().toString();
      var9.put(var10, var3);
      if(this.mDownloadProgressManager == null) {
         DownloadProgressManager var11 = new DownloadProgressManager(this);
         this.mDownloadProgressManager = var11;
      }
   }

   public void cancelAll() {
      Object[] var1 = new Object[0];
      FinskyLog.d("Cancelling all downloads.", var1);
      Iterator var2 = this.mPendingQueue.values().iterator();

      while(var2.hasNext()) {
         ((InternalDownload)var2.next()).cancel();
      }

      Iterator var3 = this.mRunningMap.values().iterator();

      while(var3.hasNext()) {
         ((InternalDownload)var3.next()).cancel();
      }

   }

   public Download get(String var1) {
      Utils.ensureOnMainThread();
      if(var1 != null && !StringUtils.isEmpty(var1)) {
         return this.getExisting(var1);
      } else {
         throw new IllegalStateException("Bad url");
      }
   }

   public Collection<Download> getAll() {
      Utils.ensureOnMainThread();
      ArrayList var1 = Lists.newArrayList();
      Collection var2 = this.mRunningMap.values();
      var1.addAll(var2);
      Collection var4 = this.mPendingQueue.values();
      var1.addAll(var4);
      return var1;
   }

   public Download getByPackageName(String var1) {
      Utils.ensureOnMainThread();
      if(var1 != null && !StringUtils.isEmpty(var1)) {
         Iterator var2 = this.mPendingQueue.values().iterator();

         InternalDownload var3;
         Download.PackageProperties var4;
         do {
            if(!var2.hasNext()) {
               var2 = this.mRunningMap.values().iterator();

               while(true) {
                  if(var2.hasNext()) {
                     InternalDownload var5 = (InternalDownload)var2.next();
                     var4 = var5.getPackageProperties();
                     if(var4 == null || !var4.packageName.equals(var1)) {
                        continue;
                     }

                     var3 = var5;
                     return var3;
                  }

                  var3 = null;
                  return var3;
               }
            }

            var3 = (InternalDownload)var2.next();
            var4 = var3.getPackageProperties();
         } while(var4 == null || !var4.packageName.equals(var1));

         return var3;
      } else {
         throw new IllegalStateException("Bad url");
      }
   }

   public DownloadListenerRecovery.DownloadListenerFilter getDownloadListenerFilter() {
      return new DownloadQueueImpl.3();
   }

   public DownloadManager getDownloadManager() {
      return this.mDownloadManager;
   }

   DownloadProgressManager getDownloadProgessManager() {
      return this.mDownloadProgressManager;
   }

   InternalDownload getExisting(Uri var1) {
      InternalDownload var2 = null;
      Utils.ensureOnMainThread();
      String var3;
      if(var1 != null) {
         var3 = var1.toString();
      } else {
         var3 = null;
      }

      if(!TextUtils.isEmpty(var3)) {
         String var4 = this.mUriUrlMap.get(var3);
         var2 = this.getExisting(var4);
      }

      return var2;
   }

   public Notifier getNotificationHelper() {
      return this.mNotificationHelper;
   }

   public DownloadUriUrlMap getUriUrlMap() {
      return this.mUriUrlMap;
   }

   public void removeListener(DownloadQueueListener var1) {
      boolean var2 = getCollectiveListeners().remove(var1);
   }

   void startDownload(InternalDownload var1) {
      if(var1 != null) {
         Object[] var2 = new Object[1];
         String var3 = var1.toString();
         var2[0] = var3;
         FinskyLog.d("Download %s starting", var2);
         HashMap var4 = this.mRunningMap;
         String var5 = var1.getUrl();
         var4.put(var5, var1);
         this.enqueueDownload(var1);
      }
   }

   class 2 implements ParameterizedRunnable<Uri> {

      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final InternalDownload val$download;


      2(Context var2, InternalDownload var3) {
         this.val$context = var2;
         this.val$download = var3;
      }

      public void run(Uri var1) {
         Looper var2 = this.val$context.getMainLooper();
         Handler var3 = new Handler(var2);
         DownloadQueueImpl.2.1 var4 = new DownloadQueueImpl.2.1(var1);
         var3.post(var4);
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final Uri val$uri;


         1(Uri var2) {
            this.val$uri = var2;
         }

         public void run() {
            if(2.this.val$download.isCompleted()) {
               DownloadManager var1 = DownloadQueueImpl.this.mDownloadManager;
               Uri var2 = this.val$uri;
               var1.remove(var2);
            } else {
               InternalDownload var3 = 2.this.val$download;
               Uri var4 = this.val$uri;
               var3.setContentUri(var4);
               DownloadUriUrlMap var5 = DownloadQueueImpl.this.mUriUrlMap;
               String var6 = this.val$uri.toString();
               String var7 = 2.this.val$download.getUrl();
               var5.put(var6, var7);
               InternalDownload var8 = 2.this.val$download;
               Download.DownloadState var9 = Download.DownloadState.DOWNLOADING;
               var8.setState(var9);
            }
         }
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final InternalDownload val$download;


      1(InternalDownload var2) {
         this.val$download = var2;
      }

      public void run() {
         Uri var1 = this.val$download.internalGetContentUri();
         if(var1 != null) {
            DownloadUriUrlMap var2 = DownloadQueueImpl.this.mUriUrlMap;
            String var3 = var1.toString();
            var2.remove(var3);
         }
      }
   }

   private class PurgeCacheCallback implements PackageManagerUtils.FreeSpaceListener {

      private PurgeCacheCallback() {}

      // $FF: synthetic method
      PurgeCacheCallback(DownloadQueueImpl.1 var2) {
         this();
      }

      public void onComplete(boolean var1) {
         if(!var1) {
            Object[] var2 = new Object[0];
            FinskyLog.w("Could not free required amount of space for download", var2);
         }

         Looper var3 = DownloadQueueImpl.this.mContext.getMainLooper();
         Handler var4 = new Handler(var3);
         DownloadQueueImpl var5 = DownloadQueueImpl.this;
         DownloadQueueImpl.StartNextDownloadRunnable var6 = var5.new StartNextDownloadRunnable((DownloadQueueImpl.1)null);
         var4.post(var6);
      }
   }

   class 3 implements DownloadListenerRecovery.DownloadListenerFilter {

      3() {}

      public Download.DownloadListener filter(Installer var1, DownloadManager var2, Download var3, String var4, int var5, Obb var6) {
         DownloadQueueImpl var7 = DownloadQueueImpl.this;
         InternalDownload var8 = (InternalDownload)var3;
         return var7.new DownloadCompleteListener(var8);
      }
   }

   private class StartNextDownloadRunnable implements Runnable {

      private StartNextDownloadRunnable() {}

      // $FF: synthetic method
      StartNextDownloadRunnable(DownloadQueueImpl.1 var2) {
         this();
      }

      public void run() {
         Utils.ensureOnMainThread();
         int var1 = DownloadQueueImpl.this.mRunningMap.size();
         int var2 = DownloadQueueImpl.this.mMaxConcurrent;
         if(var1 < var2) {
            InternalDownload var5 = null;
            LinkedList var6 = new LinkedList();
            Iterator var7 = DownloadQueueImpl.this.mPendingQueue.keySet().iterator();

            while(var7.hasNext()) {
               String var8 = (String)var7.next();
               InternalDownload var9 = (InternalDownload)DownloadQueueImpl.this.mPendingQueue.get(var8);
               var6.add(var8);
               Download.DownloadState var11 = var9.getState();
               Download.DownloadState var12 = Download.DownloadState.QUEUED;
               if(var11.equals(var12)) {
                  long var13 = var9.getSize();
                  long var15 = Storage.cachePartitionAvailableSpace();
                  long var17 = Storage.dataPartitionAvailableSpace();
                  long var19 = Storage.externalStorageAvailableSpace();
                  if(FinskyLog.DEBUG) {
                     Object[] var21 = new Object[4];
                     double var22 = (double)var13;
                     double var24 = DownloadQueueImpl.this.BYTES_IN_MB;
                     Double var26 = Double.valueOf(var22 / var24);
                     var21[0] = var26;
                     double var27 = (double)var15;
                     double var29 = DownloadQueueImpl.this.BYTES_IN_MB;
                     Double var31 = Double.valueOf(var27 / var29);
                     var21[1] = var31;
                     double var32 = (double)var17;
                     double var34 = DownloadQueueImpl.this.BYTES_IN_MB;
                     Double var36 = Double.valueOf(var32 / var34);
                     var21[2] = var36;
                     double var37 = (double)var19;
                     double var39 = DownloadQueueImpl.this.BYTES_IN_MB;
                     Double var41 = Double.valueOf(var37 / var39);
                     var21[3] = var41;
                     FinskyLog.v("b/4503710 : Download size : %f, Cache partition space : %f, Data partition space : %f, External storage space : %f", var21);
                  }

                  if(var9.getRequestedDestination() != null) {
                     if(var19 < var13) {
                        String var42;
                        if(var9.getPackageProperties() != null) {
                           var42 = var9.getPackageProperties().packageName;
                        } else {
                           var42 = null;
                        }

                        Notifier var43 = DownloadQueueImpl.this.mNotificationHelper;
                        String var44 = var9.getTitle();
                        var43.showExternalStorageFull(var44, var42);
                        var9.cancel();
                        continue;
                     }
                  } else if(var15 < var13 || var17 < var13) {
                     if(var9.getPackageProperties() != null) {
                        Notifier var47 = DownloadQueueImpl.this.mNotificationHelper;
                        String var48 = var9.getTitle();
                        String var49 = var9.getPackageProperties().packageName;
                        var47.showCacheFullMessage(var48, var49);
                     }

                     var9.cancel();
                     continue;
                  }

                  var5 = var9;
                  break;
               }
            }

            Iterator var50 = var6.iterator();

            while(var50.hasNext()) {
               String var51 = (String)var50.next();
               LinkedHashMap var52 = DownloadQueueImpl.this.mPendingQueue;
               var52.remove(var51);
            }

            DownloadQueueImpl.this.startDownload(var5);
            if(DownloadQueueImpl.this.mRunningMap.size() == 0) {
               if(DownloadQueueImpl.this.mDownloadProgressManager != null) {
                  DownloadQueueImpl.this.mDownloadProgressManager.cleanup();
                  DownloadProgressManager var55 = DownloadQueueImpl.this.mDownloadProgressManager = null;
               }
            }
         }
      }
   }

   private class DownloadCompleteListener implements Download.DownloadListener {

      InternalDownload mDownload;


      public DownloadCompleteListener(InternalDownload var2) {
         this.mDownload = var2;
      }

      private void removeFromDownloadManager() {
         Uri var1 = this.mDownload.internalGetContentUri();
         if(var1 != null) {
            DownloadQueueImpl.this.mDownloadManager.remove(var1);
         }
      }

      public void onCancel() {
         Object[] var1 = new Object[1];
         String var2 = this.mDownload.toString();
         var1[0] = var2;
         FinskyLog.d("Download %s: onCancel called.", var1);
         DownloadQueueImpl var3 = DownloadQueueImpl.this;
         InternalDownload var4 = this.mDownload;
         var3.remove(var4);
         DownloadQueueImpl.notifyAggregateListeners();
         this.removeFromDownloadManager();
      }

      public void onComplete(Download var1) {
         Object[] var2 = new Object[1];
         String var3 = this.mDownload.toString();
         var2[0] = var3;
         FinskyLog.d("Download %s: onComplete called", var2);
         DownloadQueueImpl var4 = DownloadQueueImpl.this;
         InternalDownload var5 = this.mDownload;
         var4.remove(var5);
         DownloadQueueImpl.notifyAggregateListeners();
      }

      public boolean onError(int var1) {
         byte var2 = 0;
         Object[] var3 = new Object[2];
         String var4 = this.mDownload.toString();
         var3[var2] = var4;
         Integer var5 = Integer.valueOf(var1);
         var3[1] = var5;
         FinskyLog.d("Download %s: onError called with %d.", var3);
         if(var1 != 403 && var1 != 401) {
            DownloadQueueImpl var6 = DownloadQueueImpl.this;
            InternalDownload var7 = this.mDownload;
            var6.remove(var7);
            DownloadQueueImpl.notifyAggregateListeners();
            if(this.mDownload.getPackageProperties() != null) {
               Notifier var8 = DownloadQueueImpl.this.mNotificationHelper;
               String var9 = this.mDownload.getTitle();
               String var10 = this.mDownload.getPackageProperties().packageName;
               var8.showDownloadErrorMessage(var9, var10);
            }

            this.removeFromDownloadManager();
         } else {
            FinskyApp.get().getVendingApi().getApiContext().scheduleReauthentication((boolean)1);
            var2 = 1;
         }

         return (boolean)var2;
      }

      public void onNotificationClicked() {
         Object[] var1 = new Object[1];
         String var2 = this.mDownload.toString();
         var1[0] = var2;
         FinskyLog.d("Download %s: onNotificationClicked called.", var1);
      }

      public void onProgress(DownloadProgress var1) {
         Object[] var2 = new Object[2];
         String var3 = this.mDownload.toString();
         var2[0] = var3;
         String var4 = var1.toString();
         var2[1] = var4;
         FinskyLog.d("Download %s: onProgress called with %s.", var2);
         DownloadQueueImpl.notifyAggregateListeners();
      }

      public void onStart() {
         Object[] var1 = new Object[1];
         String var2 = this.mDownload.toString();
         var1[0] = var2;
         FinskyLog.d("Download %s: onStart called.", var1);
      }
   }
}
