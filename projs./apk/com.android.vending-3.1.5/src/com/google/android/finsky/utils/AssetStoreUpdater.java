package com.google.android.finsky.utils;

import android.net.Uri;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadListenerRecovery;
import com.google.android.finsky.download.DownloadManager;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.DownloadQueueListener;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.services.CheckinAssetStoreListener;
import com.google.android.finsky.utils.FinskyLog;

public class AssetStoreUpdater implements DownloadQueueListener, PackageMonitorReceiver.PackageStatusListener {

   private AssetStore mAssetStore;
   private CheckinAssetStoreListener mCheckinAssetStoreListener;
   private final DownloadQueue mDownloadQueue;


   public AssetStoreUpdater(AssetStore var1, PackageMonitorReceiver var2, DownloadQueue var3, CheckinAssetStoreListener var4) {
      this.mAssetStore = var1;
      this.mDownloadQueue = var3;
      this.mCheckinAssetStoreListener = var4;
      this.mDownloadQueue.addListener(this);
      var2.attach(this);
   }

   public void onAdd(Download var1) {
      Download.PackageProperties var2 = var1.getPackageProperties();
      if(var2 == null) {
         Object[] var3 = new Object[0];
         FinskyLog.d("Received package with no PackageProperties... Ignoring", var3);
      } else {
         label17: {
            AssetStore var4 = this.mAssetStore;
            String var5 = var2.packageName;
            LocalAsset var6 = var4.getAsset(var5);
            if(var6 != null) {
               if(var6 == null) {
                  break label17;
               }

               AssetState var7 = var6.getState();
               AssetState var8 = AssetState.DOWNLOAD_PENDING;
               if(var7.equals(var8)) {
                  break label17;
               }
            }

            AssetStore var9 = this.mAssetStore;
            String var10 = var2.packageName;
            AutoUpdateState var11 = var2.autoUpdateState;
            String var12 = var2.account;
            int var13 = var2.versionCode;
            String var14 = var2.assetId;
            long var15 = System.currentTimeMillis();
            Object var17 = null;
            var9.insertAsset(var10, var11, var12, var13, var14, (String)null, (String)var17, var15);
         }

         AssetStore var19 = this.mAssetStore;
         String var20 = var2.packageName;
         LocalAsset var21 = var19.getAsset(var20);
         long var22 = var2.size;
         AssetStoreUpdater.AssetStoreDownloadListener var24 = new AssetStoreUpdater.AssetStoreDownloadListener(var21, var1, var22);
         var1.addListener(var24);
      }
   }

   public void onPackageAdded(String var1) {
      LocalAsset var2 = this.mAssetStore.getAsset(var1);
      if(var2 == null) {
         if(FinskyLog.DEBUG) {
            Object[] var3 = new Object[]{var1};
            FinskyLog.v("Sideloaded package %s will not be recognized by Market.", var3);
         }
      } else {
         long var4 = System.currentTimeMillis();
         var2.setStateInstalled(var4);
      }
   }

   public void onPackageAvailabilityChanged(String[] var1, boolean var2) {
      this.mCheckinAssetStoreListener.suspend();
      String[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         LocalAsset var7 = FinskyInstance.get().getAssetStore().getAsset(var6);
         if(var7 == null) {
            if(FinskyLog.DEBUG) {
               String var8 = "onPackageAvailabilityChanged called for an app that\'s not in local asset database: " + var6;
               Object[] var9 = new Object[0];
               FinskyLog.v(var8, var9);
            }
         } else if(var2) {
            AssetState var10 = var7.getState();
            AssetState var11 = AssetState.UNINSTALLED;
            if(var10 != var11) {
               String var12 = "Received ACTION_EXTERNAL_APPLICATIONS_AVAILABLE for package " + var6 + ", but it\'s already installed!";
               Object[] var13 = new Object[0];
               FinskyLog.w(var12, var13);
            } else {
               long var14;
               if(var7.getInstallTime() > 0L) {
                  var14 = var7.getInstallTime();
               } else {
                  var14 = System.currentTimeMillis();
               }

               var7.setStateInstalled(var14);
            }
         } else {
            var7.setStateUninstalling();
            long var16 = System.currentTimeMillis();
            var7.setStateUninstalled(var16);
         }
      }

      this.mCheckinAssetStoreListener.resume();
   }

   public void onPackageChanged(String var1) {}

   public void onPackageRemoved(String var1, boolean var2) {
      LocalAsset var3 = this.mAssetStore.getAsset(var1);
      if(var3 == null) {
         if(FinskyLog.DEBUG) {
            Object[] var4 = new Object[]{var1};
            FinskyLog.v("Package %s removed, but is not in database.", var4);
         }
      } else {
         int[] var5 = AssetStoreUpdater.1.$SwitchMap$com$google$android$finsky$local$AssetState;
         int var6 = var3.getState().ordinal();
         switch(var5[var6]) {
         case 1:
            Object[] var9 = new Object[]{var1};
            FinskyLog.d("Setting %s to Uninstalled.", var9);
            long var10 = System.currentTimeMillis();
            var3.setStateUninstalled(var10);
            return;
         case 2:
            Object[] var12 = new Object[]{var1};
            FinskyLog.d("Ignoring package removal likely caused by update (%s).", var12);
            return;
         case 3:
            Object[] var13 = new Object[]{var1};
            FinskyLog.d("Package removed normally [%s].", var13);
            long var14 = System.currentTimeMillis();
            var3.setStateUninstalled(var14);
            return;
         default:
            Object[] var7 = new Object[]{var1, null};
            String var8 = var3.getState().toString();
            var7[1] = var8;
            FinskyLog.w("Package removal of %s in state %s.", var7);
            var3.resetUninstalledState();
         }
      }
   }

   public void onUpdate() {}

   public static class AssetStoreDownloadListenerFilter implements DownloadListenerRecovery.DownloadListenerFilter {

      private AssetStore mAssetStore;


      public AssetStoreDownloadListenerFilter(AssetStore var1) {
         this.mAssetStore = var1;
      }

      public Download.DownloadListener filter(Installer var1, DownloadManager var2, Download var3, String var4, int var5, Obb var6) {
         AssetStoreUpdater.AssetStoreDownloadListener var10;
         if(var6 == null) {
            LocalAsset var7 = this.mAssetStore.getAsset(var4);
            if(var7.getVersionCode() == var5) {
               long var8 = var7.getSize();
               var10 = new AssetStoreUpdater.AssetStoreDownloadListener(var7, var3, var8);
               return var10;
            }

            if(FinskyLog.DEBUG) {
               Object[] var11 = new Object[0];
               FinskyLog.v("AssetStoreDownloadListenerFilter ignoring irrelevant download", var11);
            }
         }

         var10 = null;
         return var10;
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$local$AssetState = new int[AssetState.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var1 = AssetState.INSTALLED.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var3 = AssetState.INSTALLING.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var5 = AssetState.UNINSTALLING.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var9) {
            ;
         }
      }
   }

   private static class AssetStoreDownloadListener implements Download.DownloadListener {

      LocalAsset mAsset;
      Download mDownload;
      long mSize;


      public AssetStoreDownloadListener(LocalAsset var1, Download var2, long var3) {
         this.mAsset = var1;
         this.mSize = var3;
         this.mDownload = var2;
      }

      public void onCancel() {
         StringBuilder var1 = (new StringBuilder()).append("b4332527: onCancel: ");
         Download var2 = this.mDownload;
         StringBuilder var3 = var1.append(var2).append(", ");
         String var4 = this.mAsset.getPackage();
         String var5 = var3.append(var4).toString();
         Object[] var6 = new Object[0];
         FinskyLog.d(var5, var6);
         AssetState var7 = AssetState.DOWNLOAD_PENDING;
         AssetState var8 = this.mAsset.getState();
         if(var7.equals(var8)) {
            this.mAsset.setStateCancelPending();
         } else {
            this.mAsset.setStateDownloadCancelled();
         }
      }

      public void onComplete(Download var1) {
         this.mAsset.setStateInstalling();
      }

      public boolean onError(int var1) {
         this.mAsset.setStateDownloadFailed();
         return false;
      }

      public void onNotificationClicked() {}

      public void onProgress(DownloadProgress var1) {}

      public void onStart() {
         Download.PackageProperties var1 = this.mDownload.getPackageProperties();
         LocalAsset var2 = this.mAsset;
         long var3 = System.currentTimeMillis();
         Uri var5 = this.mDownload.getContentUri();
         long var6 = this.mSize;
         String var8 = var1.signature;
         boolean var9 = var1.forwardLocked;
         Long var10 = var1.refundPeriodEndTime;
         Obb var11 = var1.mainObb;
         Obb var12 = var1.patchObb;
         var2.setStateDownloading(var3, var5, var6, var8, var9, var10, var11, var12);
      }
   }
}
