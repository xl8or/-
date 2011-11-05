package com.google.android.finsky.download;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadImpl;
import com.google.android.finsky.download.DownloadListenerRecovery;
import com.google.android.finsky.download.DownloadManager;
import com.google.android.finsky.download.DownloadManagerConstants;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.local.checker.SanityChecker;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.finsky.utils.PackageManagerHelper;
import com.google.android.finsky.utils.Utils;
import com.google.wireless.gdata.data.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DownloadRestarterRule implements SanityChecker.SanityCheckRule {

   private final long MS_IN_HOUR = 3600000L;
   private final long PRUNE_OLD_DOWNLOAD_LIFETIME = 86400000L;
   private final Context mContext;
   private final DownloadManager mDownloadManager;
   private final DownloadQueue mDownloadQueue;
   private final Installer mInstaller;
   private final PackageInfoCache mPackageInfoCache;


   public DownloadRestarterRule(Context var1, DownloadQueue var2, PackageInfoCache var3, Installer var4, DownloadManager var5) {
      this.mDownloadQueue = var2;
      this.mInstaller = var4;
      this.mDownloadManager = var5;
      this.mPackageInfoCache = var3;
      this.mContext = var1;
   }

   public int run(AssetStore var1) {
      AssetState var2 = AssetState.DOWNLOAD_PENDING;
      Collection var3 = var1.getAssetsByState(var2);
      AssetState var4 = AssetState.DOWNLOADING;
      Collection var5 = var1.getAssetsByState(var4);
      ArrayList var6 = Lists.newArrayList();
      var6.addAll(var3);
      var6.addAll(var5);
      if(var6.size() != 0) {
         Object[] var9 = new Object[0];
         FinskyLog.d("Recovering from Market exit when restoring / downloading assets.", var9);
      }

      DownloadRestarterRule.DownloadRestarter var10 = new DownloadRestarterRule.DownloadRestarter(var6, (DownloadRestarterRule.1)null);
      DownloadManager[] var11 = new DownloadManager[1];
      DownloadManager var12 = this.mDownloadManager;
      var11[0] = var12;
      var10.execute(var11);
      return var6.size();
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class DownloadRecords {

      private boolean mInitialized;
      private HashMap<String, DownloadRestarterRule.DownloadRecord> mUriRowMap;


      private DownloadRecords() {
         HashMap var2 = Maps.newHashMap();
         this.mUriRowMap = var2;
      }

      // $FF: synthetic method
      DownloadRecords(DownloadRestarterRule.1 var2) {
         this();
      }

      private DownloadRestarterRule.DownloadRecord getRecord(String var1) {
         if(!this.mInitialized) {
            throw new IllegalStateException("UnpackedCursor never initialized");
         } else {
            return (DownloadRestarterRule.DownloadRecord)this.mUriRowMap.get(var1);
         }
      }

      private boolean initializeAndPrune(Cursor var1, Map<String, String> var2) {
         boolean var3;
         if(var1 == null) {
            var3 = false;
         } else {
            int var4 = var1.getColumnIndex("_id");
            int var5 = var1.getColumnIndex("status");
            int var6 = var1.getColumnIndex("uri");
            int var7 = var1.getColumnIndex("title");
            int var8 = var1.getColumnIndex("lastmod");
            int var9 = 0;
            if(var4 != -1 && var5 != -1 && var8 != -1) {
               while(var1.moveToNext()) {
                  DownloadRestarterRule var10 = DownloadRestarterRule.this;
                  DownloadRestarterRule.DownloadRecord var11 = var10.new DownloadRecord((DownloadRestarterRule.1)null);
                  String var12 = DownloadManagerConstants.getContentUriString(var1.getString(var4));
                  var11.contentUri = var12;
                  String var14;
                  if(var6 == -1) {
                     String var13 = var11.contentUri;
                     var14 = (String)var2.get(var13);
                  } else {
                     var14 = var1.getString(var6);
                  }

                  var11.sourceUrl = var14;
                  int var15 = var1.getInt(var5);
                  var11.status = var15;
                  long var16 = var1.getLong(var8);
                  var11.lastModified = var16;
                  String var18;
                  if(var1.isNull(var7)) {
                     var18 = "";
                  } else {
                     var18 = var1.getString(var7);
                  }

                  var11.title = var18;
                  long var19 = System.currentTimeMillis();
                  long var21 = var11.lastModified;
                  long var23 = var19 - var21;
                  if(var23 > 86400000L) {
                     ++var9;
                     Object[] var25 = new Object[1];
                     Long var26 = Long.valueOf(var23 / 3600000L);
                     var25[0] = var26;
                     FinskyLog.d("Pruning download that is %d hours old from the DownloadManager cursor.", var25);
                     DownloadManager var27 = DownloadRestarterRule.this.mDownloadManager;
                     Uri var28 = Uri.parse(var11.contentUri);
                     var27.remove(var28);
                  } else if(TextUtils.isEmpty(var11.sourceUrl)) {
                     Object[] var29 = new Object[0];
                     FinskyLog.e("Could not determine source url of download.", var29);
                  } else {
                     HashMap var30 = this.mUriRowMap;
                     String var31 = var11.contentUri;
                     var30.put(var31, var11);
                  }
               }

               Object[] var33 = new Object[1];
               Integer var34 = Integer.valueOf(var9);
               var33[0] = var34;
               FinskyLog.d("Pruned %d old downloads from the cursor.", var33);
               this.mInitialized = (boolean)1;
               var3 = true;
            } else {
               var3 = false;
            }
         }

         return var3;
      }

      private boolean isInitialized() {
         return this.mInitialized;
      }
   }

   private class DownloadRestarter extends AsyncTask<DownloadManager, Void, DownloadRestarterRule.DownloadRecords> {

      private final Collection<LocalAsset> mToRestore;
      private final Map<String, String> mUriUrlMap;


      private DownloadRestarter(Collection var2) {
         Utils.ensureOnMainThread();
         this.mToRestore = var2;
         Map var3 = DownloadRestarterRule.this.mDownloadQueue.getUriUrlMap().getGenericMap();
         this.mUriUrlMap = var3;
      }

      // $FF: synthetic method
      DownloadRestarter(Collection var2, DownloadRestarterRule.1 var3) {
         this(var2);
      }

      private boolean downloadedSuccessfully(DownloadRestarterRule.DownloadRecord var1) {
         boolean var2;
         if(var1 != null) {
            var2 = DownloadManagerConstants.isStatusSuccess(var1.status);
         } else {
            var2 = false;
         }

         return var2;
      }

      private DownloadRestarterRule.DownloadRecord getDownloadRecord(DownloadRestarterRule.DownloadRecords var1, Uri var2) {
         DownloadRestarterRule.DownloadRecord var3;
         if(var2 != null && !StringUtils.isEmpty(var2.toString())) {
            String var4 = var2.toString();
            var3 = var1.getRecord(var4);
         } else {
            var3 = null;
         }

         return var3;
      }

      private DownloadRestarterRule.DownloadRecord getDownloadRecord(DownloadRestarterRule.DownloadRecords var1, Obb var2) {
         Uri var3 = Uri.parse(var2.getContentUri());
         return this.getDownloadRecord(var1, var3);
      }

      private boolean handleAssetResuming(DownloadRestarterRule.DownloadRecords var1, LocalAsset var2) {
         Uri var3 = var2.getContentUri();
         DownloadRestarterRule.DownloadRecord var4 = this.getDownloadRecord(var1, var3);
         boolean var10;
         if(this.isDownloading(var4)) {
            String var5 = var4.sourceUrl;
            String var6 = var4.title;
            this.queueResumedDownload(var5, var6, var3, var2, (Obb)null);
            var10 = true;
         } else if(this.downloadedSuccessfully(var4)) {
            AssetState var11 = var2.getState();
            AssetState var12 = AssetState.DOWNLOADING;
            if(!var11.equals(var12)) {
               Object[] var13 = new Object[0];
               FinskyLog.w("Inconsistent state while installing recovered download. Resetting.", var13);
               var2.resetInstallingState();
            } else {
               var2.setStateInstalling();
            }

            Uri var14 = Uri.parse(var4.contentUri);
            String var15 = var4.title;
            long var16 = var2.getSize();
            String var18 = var2.getSignature();
            PackageManagerHelper.installPackage(var14, var15, var16, var18, (boolean)1, (Runnable)null);
            Looper var19 = DownloadRestarterRule.this.mContext.getMainLooper();
            Handler var20 = new Handler(var19);
            DownloadRestarterRule.DownloadRestarter.1 var21 = new DownloadRestarterRule.DownloadRestarter.1(var4);
            var20.post(var21);
            var10 = true;
         } else {
            PackageInfoCache var23 = DownloadRestarterRule.this.mPackageInfoCache;
            String var24 = var2.getPackage();
            if(var23.isPackageInstalled(var24)) {
               var2.resetInstalledState();
               var10 = true;
            } else {
               var10 = false;
            }
         }

         return var10;
      }

      private boolean handleObbResuming(DownloadRestarterRule.DownloadRecord var1, LocalAsset var2, Obb var3) {
         boolean var4 = false;
         if(this.isDownloading(var1)) {
            String var5 = var1.sourceUrl;
            String var6 = var1.title;
            Uri var7 = Uri.parse(var1.contentUri);
            this.queueResumedDownload(var5, var6, var7, var2, var3);
            var4 = true;
         } else if(this.downloadedSuccessfully(var1)) {
            if(!var3.finalizeTempFile()) {
               Object[] var12 = new Object[0];
               FinskyLog.e("Unable to rename obb download to completed file name.", var12);
               var3.delete();
               ObbState var13 = ObbState.NOT_ON_STORAGE;
               var3.setState(var13);
               var4 = true;
            } else {
               this.restartAssetDownload(var2);
               var4 = true;
            }
         }

         return var4;
      }

      private boolean handleObbResumingOrRestart(DownloadRestarterRule.DownloadRecords var1, LocalAsset var2) {
         byte var3 = 1;
         Obb var4 = var2.getObb((boolean)0);
         Obb var5 = var2.getObb((boolean)var3);
         ObbState var6 = var5.getState();
         ObbState var7 = ObbState.NOT_APPLICABLE;
         if(var6 != var7) {
            DownloadRestarterRule.DownloadRecord var8 = this.getDownloadRecord(var1, var5);
            if(this.handleObbResuming(var8, var2, var5)) {
               Object[] var9 = new Object[0];
               FinskyLog.d("Handling downloading / finished patch obb followed by lone asset download.", var9);
            } else {
               DownloadRestarterRule.DownloadRecord var10 = this.getDownloadRecord(var1, var4);
               if(this.handleObbResuming(var10, var2, var4)) {
                  Object[] var11 = new Object[0];
                  FinskyLog.d("Handling downloading / finished main obb, patch obb, and lone asset download.", var11);
               } else {
                  Object[] var12 = new Object[0];
                  FinskyLog.d("Restarting full asset download with any associated obb\'s.(patch obb exists, and a main obb may exist).", var12);
                  this.restartAssetDownload(var2);
               }
            }
         } else {
            ObbState var13 = var4.getState();
            ObbState var14 = ObbState.NOT_APPLICABLE;
            if(var13 != var14) {
               DownloadRestarterRule.DownloadRecord var15 = this.getDownloadRecord(var1, var4);
               if(this.handleObbResuming(var15, var2, var4)) {
                  Object[] var16 = new Object[0];
                  FinskyLog.d("Handling downloading / finished main obb followed by stripped asset download.", var16);
                  return (boolean)var3;
               }
            }

            Object[] var17 = new Object[0];
            FinskyLog.d("No OBB\'s associated to this asset.", var17);
            var3 = 0;
         }

         return (boolean)var3;
      }

      private void handleUnexpectedCursor() {
         Object[] var1 = new Object[0];
         FinskyLog.w("Can\'t resume repeat downloads since system DownloadManager failed to give us an appropriate Cursor. Market will reset interrupted downloads and risk a duplicate.", var1);
         Iterator var2 = this.mToRestore.iterator();

         while(var2.hasNext()) {
            LocalAsset var3 = (LocalAsset)var2.next();
            this.restartAssetDownload(var3);
         }

      }

      private boolean isDownloading(DownloadRestarterRule.DownloadRecord var1) {
         boolean var2;
         if(var1 != null) {
            var2 = DownloadManagerConstants.isStatusInformational(var1.status);
         } else {
            var2 = false;
         }

         return var2;
      }

      private Download queueResumedDownload(String var1, String var2, Uri var3, LocalAsset var4, Obb var5) {
         DownloadImpl var6 = new DownloadImpl;
         Download.PackageProperties var7;
         if(var5 != null) {
            var7 = null;
         } else {
            String var22 = var4.getPackage();
            AutoUpdateState var23 = var4.getAutoUpdateState();
            String var24 = var4.getAccount();
            int var25 = var4.getVersionCode();
            String var26 = var4.getAssetId();
            boolean var27 = var4.isForwardLocked();
            long var28 = var4.getSize();
            String var30 = var4.getSignature();
            Long var31 = var4.getRefundPeriodEndTime();
            Obb var32 = var4.getObb((boolean)0);
            Obb var33 = var4.getObb((boolean)1);
            var7 = new Download.PackageProperties(var22, var23, var24, var25, var26, var27, var28, var30, var31, var32, var33);
         }

         Notifier var8 = DownloadRestarterRule.this.mDownloadQueue.getNotificationHelper();
         long var9 = var4.getSize();
         var6.<init>(var1, var2, var7, (String)null, (String)null, (Uri)null, var8, var9);
         Download.DownloadState var14 = Download.DownloadState.DOWNLOADING;
         var6.setState(var14);
         var6.setContentUri(var3);
         DownloadRestarterRule.this.mDownloadQueue.addRecoveredDownload(var6);
         Installer var16 = DownloadRestarterRule.this.mInstaller;
         DownloadManager var17 = DownloadRestarterRule.this.mDownloadManager;
         String var18 = var4.getPackage();
         int var19 = var4.getVersionCode();
         DownloadListenerRecovery.recoverListeners(var16, var17, var6, var18, var19, var5);
         return var6;
      }

      private void restartAssetDownload(LocalAsset var1) {
         Object[] var2 = new Object[1];
         String var3 = var1.getPackage();
         var2[0] = var3;
         FinskyLog.d("Restarting asset download : %s", var2);
         var1.resetDownloadPendingState();
         DownloadRestarterRule.this.mInstaller.fetchAsset(var1);
      }

      protected DownloadRestarterRule.DownloadRecords doInBackground(DownloadManager ... var1) {
         Cursor var2 = var1[0].queryAllDownloads();
         DownloadRestarterRule.DownloadRecords var3;
         if(var2 == null) {
            var3 = null;
         } else {
            DownloadRestarterRule var4 = DownloadRestarterRule.this;
            var3 = var4.new DownloadRecords((DownloadRestarterRule.1)null);
            Map var5 = this.mUriUrlMap;
            var3.initializeAndPrune(var2, var5);
            var2.close();
         }

         return var3;
      }

      protected void onPostExecute(DownloadRestarterRule.DownloadRecords var1) {
         if(var1 != null && var1.isInitialized()) {
            Iterator var2 = this.mToRestore.iterator();

            while(var2.hasNext()) {
               LocalAsset var3 = (LocalAsset)var2.next();
               Object[] var4 = new Object[1];
               String var5 = var3.getPackage();
               var4[0] = var5;
               FinskyLog.d("Recovering restore of %s", var4);
               if(this.handleObbResumingOrRestart(var1, var3)) {
                  Object[] var6 = new Object[1];
                  String var7 = var3.getPackage();
                  var6[0] = var7;
                  FinskyLog.d("Handled obb resuming / restart for asset : %s", var6);
               } else if(this.handleAssetResuming(var1, var3)) {
                  Object[] var8 = new Object[1];
                  String var9 = var3.getPackage();
                  var8[0] = var9;
                  FinskyLog.d("Resumed asset download : %s", var8);
               } else {
                  this.restartAssetDownload(var3);
               }
            }

         } else {
            this.handleUnexpectedCursor();
         }
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final DownloadRestarterRule.DownloadRecord val$record;


         1(DownloadRestarterRule.DownloadRecord var2) {
            this.val$record = var2;
         }

         public void run() {
            Map var1 = DownloadRestarter.this.mUriUrlMap;
            String var2 = this.val$record.contentUri;
            var1.remove(var2);
         }
      }
   }

   private class DownloadRecord {

      public String contentUri;
      public long lastModified;
      public String sourceUrl;
      public int status;
      public String title;


      private DownloadRecord() {}

      // $FF: synthetic method
      DownloadRecord(DownloadRestarterRule.1 var2) {
         this();
      }
   }
}
