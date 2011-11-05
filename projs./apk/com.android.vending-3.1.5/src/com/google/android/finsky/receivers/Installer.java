package com.google.android.finsky.receivers;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadImpl;
import com.google.android.finsky.download.DownloadListenerRecovery;
import com.google.android.finsky.download.DownloadManager;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.AssetUtils;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.model.PurchaseStatusTracker;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.NotificationManager;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.finsky.utils.PackageManagerHelper;
import com.google.android.finsky.utils.Sets;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.vending.model.Asset;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Installer {

   private final DownloadManager mDownloadManager;
   private final DownloadQueue mDownloadQueue;
   private final List<Installer.InstallerRequestListener> mListeners;
   private final AssetStore mLocalAssetStore;
   private final PackageManager mPackageManager;
   private final Set<String> mPendingAssetPackageNames;


   public Installer(AssetStore var1, PackageManager var2, DownloadQueue var3, DownloadManager var4) {
      this.mLocalAssetStore = var1;
      this.mPackageManager = var2;
      this.mDownloadQueue = var3;
      this.mDownloadManager = var4;
      HashSet var5 = Sets.newHashSet();
      this.mPendingAssetPackageNames = var5;
      ArrayList var6 = Lists.newArrayList();
      this.mListeners = var6;
   }

   @Deprecated
   private void attemptInstallAsset(Asset var1) {
      String var2 = var1.getPackageName();
      String var3 = "Installing package: " + var2;
      Object[] var4 = new Object[0];
      FinskyLog.d(var3, var4);
      LocalAsset var5 = this.mLocalAssetStore.getAsset(var2);
      String var6;
      if(var5 == null) {
         if(!FinskyApp.get().getPackageInfoCache().isSystemPackage(var2)) {
            Object[] var20 = new Object[]{var2};
            FinskyLog.e("Could not get localAsset for non-system asset [%s].", var20);
            return;
         }

         var6 = FinskyApp.get().getCurrentAccountName();
         AssetStore var7 = this.mLocalAssetStore;
         AutoUpdateState var8 = AutoUpdateState.DEFAULT;
         int var9 = (int)var1.getVersionCode();
         String var10 = var1.getId();
         long var11 = System.currentTimeMillis();
         Object var13 = null;
         var7.insertAsset(var2, var8, var6, var9, var10, (String)null, (String)var13, var11);
      } else {
         var6 = var5.getAccount();
      }

      VendingApi var15 = FinskyApp.get().getVendingApi(var6);
      String var16 = (String)VendingPreferences.DIRECT_DOWNLOAD_KEY.get();
      String var17 = var1.getId();
      Installer.DownloadAndInstallGetAssetListener var18 = new Installer.DownloadAndInstallGetAssetListener(this, var6, var2);
      Installer.1 var19 = new Installer.1(var2);
      var15.getAsset(var16, var17, var18, var19);
   }

   private boolean newSystemAppAvailable(Asset var1) {
      PackageInfoCache var2 = FinskyApp.get().getPackageInfoCache();
      String var3 = var1.getPackageName();
      boolean var8;
      if(var2.isSystemPackage(var3) && var2.isPackageInstalled(var3)) {
         long var4 = (long)var2.getPackageVersion(var3);
         long var6 = var1.getVersionCode();
         if(var4 < var6) {
            var8 = true;
            return var8;
         }
      }

      var8 = false;
      return var8;
   }

   private void notifyListeners() {
      Iterator var1 = this.mListeners.iterator();

      while(var1.hasNext()) {
         Installer.InstallerRequestListener var2 = (Installer.InstallerRequestListener)var1.next();

         try {
            var2.onUpdate();
         } catch (Exception var5) {
            Object[] var4 = new Object[0];
            FinskyLog.wtf(var5, "Exception caught while iterating through install listeners", var4);
         }
      }

   }

   public void addListener(Installer.InstallerRequestListener var1) {
      this.mListeners.add(var1);
   }

   public void attemptInstallAssets(List<Asset> var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Asset var3 = (Asset)var2.next();
         Set var4 = this.mPendingAssetPackageNames;
         String var5 = var3.getPackageName();
         var4.add(var5);
      }

      this.notifyListeners();
      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         Asset var8 = (Asset)var7.next();
         this.attemptInstallAsset(var8);
      }

   }

   public void downloadAndInstallAsset(String var1, String var2, Download.PackageProperties var3, String var4, String var5, boolean var6) {
      this.downloadAndInstallAsset(var1, var2, var3, var4, var5, var6, (Download.DownloadListener)null);
   }

   public void downloadAndInstallAsset(String var1, String var2, Download.PackageProperties var3, String var4, String var5, boolean var6, Download.DownloadListener var7) {
      if(var3 != null && var3.packageName != null) {
         PurchaseStatusTracker var8 = FinskyApp.get().getPurchaseStatusTracker();
         String var9 = var3.packageName;
         var8.remove(var9);
      }

      DownloadQueue var10 = this.mDownloadQueue;
      if(var10.get(var1) != null) {
         Object[] var12 = new Object[]{var2};
         FinskyLog.d("Ignoring download for application \'%s\' because the download already exists.", var12);
      } else {
         String var13 = null;
         if(var3 != null) {
            var13 = var3.packageName;
            if(var13 != null) {
               Iterator var14 = this.mDownloadQueue.getAll().iterator();

               while(var14.hasNext()) {
                  Download.PackageProperties var15 = ((Download)var14.next()).getPackageProperties();
                  if(var15 != null && var15.packageName != null && var15.packageName.equals(var13)) {
                     Object[] var16 = new Object[]{var2};
                     FinskyLog.d("Skipping extraneous download for %s since the package is already downloading.", var16);
                     return;
                  }
               }
            }
         }

         Object[] var17 = new Object[]{var2};
         FinskyLog.d("Downloading and installing %s.", var17);
         Notifier var18 = this.mDownloadQueue.getNotificationHelper();
         long var19 = var3.size;
         DownloadImpl var26 = new DownloadImpl(var1, var2, var3, var4, var5, (Uri)null, var18, var19);
         DownloadManager var27 = this.mDownloadManager;
         Installer.DownloadInstallListener var30 = new Installer.DownloadInstallListener(var2, var13, var6, var27);
         var26.addListener(var30);
         if(var7 != null) {
            var26.addListener(var7);
         }

         this.mDownloadQueue.add(var26);
      }
   }

   public void fetchAsset(LocalAsset var1) {
      String var2 = var1.getPackage();
      this.mPendingAssetPackageNames.add(var2);
      String var4 = var1.getAccount();
      VendingApi var5 = FinskyApp.get().getVendingApi(var4);
      String var6 = (String)VendingPreferences.DIRECT_DOWNLOAD_KEY.get();
      String var7 = var1.getAssetId();
      Installer.DownloadAndInstallGetAssetListener var8 = new Installer.DownloadAndInstallGetAssetListener(this, var4, var2);
      Installer.2 var9 = new Installer.2(var2);
      var5.getAsset(var6, var7, var8, var9);
   }

   public List<Asset> getAppsEligibleForAutoUpdate(List<Asset> var1, boolean var2) {
      ArrayList var3 = Lists.newArrayList();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         Asset var5 = (Asset)var4.next();
         String var6 = var5.getPackageName();
         LocalAsset var7 = this.mLocalAssetStore.getAsset(var6);
         boolean var8 = false;
         boolean var9 = false;
         if(this.newSystemAppAvailable(var5)) {
            label31: {
               var8 = true;
               if(var7 != null) {
                  AutoUpdateState var10 = var7.getAutoUpdateState();
                  AutoUpdateState var11 = AutoUpdateState.DISABLED;
                  if(var10 == var11) {
                     var9 = false;
                     break label31;
                  }
               }

               var9 = true;
            }
         } else {
            label43: {
               if(var7 != null) {
                  AssetState var17 = AssetState.INSTALLED;
                  AssetState var18 = var7.getState();
                  if(var17.equals(var18)) {
                     long var20 = var5.getVersionCode();
                     long var22 = (long)var7.getVersionCode();
                     if(var20 > var22) {
                        var8 = true;
                        AutoUpdateState var24 = var7.getAutoUpdateState();
                        AutoUpdateState var25 = AutoUpdateState.ENABLED;
                        if(var24 == var25) {
                           var9 = true;
                        } else {
                           var9 = false;
                        }
                     }
                     break label43;
                  }
               }

               Object[] var19 = new Object[]{var6};
               FinskyLog.w("Server thinks we have an asset that we don\'t have : %s", var19);
            }
         }

         if(var8) {
            String var12 = var5.getPackageName();
            List var13 = var5.getPermissions();
            PackageManager var14 = this.mPackageManager;
            if(!AssetUtils.containsDangerousNewPermissions(var12, var13, var14) && (!var2 || var9)) {
               Object[] var15 = new Object[]{var6};
               FinskyLog.d("Market will auto-update %s", var15);
               var3.add(var5);
            }
         }
      }

      return var3;
   }

   public List<Asset> getAppsWithUpdates(List<Asset> var1) {
      ArrayList var2 = Lists.newArrayList();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Asset var4 = (Asset)var3.next();
         String var5 = var4.getPackageName();
         LocalAsset var6 = this.mLocalAssetStore.getAsset(var5);
         if(this.newSystemAppAvailable(var4)) {
            var2.add(var4);
         } else {
            if(var6 != null) {
               AssetState var8 = AssetState.INSTALLED;
               AssetState var9 = var6.getState();
               if(var8.equals(var9)) {
                  long var11 = var4.getVersionCode();
                  long var13 = (long)var6.getVersionCode();
                  if(var11 > var13) {
                     var2.add(var4);
                  }
                  continue;
               }
            }

            Object[] var10 = new Object[]{var5};
            FinskyLog.w("Server thinks we have an asset that we don\'t have : %s", var10);
         }
      }

      return var2;
   }

   public boolean hasPendingAssetRequests() {
      boolean var1;
      if(!this.mPendingAssetPackageNames.isEmpty()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void removeListener(Installer.InstallerRequestListener var1) {
      this.mListeners.remove(var1);
   }

   public interface InstallerRequestListener {

      void onUpdate();
   }

   private static class DownloadInstallListener implements Download.DownloadListener {

      private final boolean mDoNotifications;
      private final DownloadManager mDownloadManager;
      private final String mPackageName;
      private final String mTitle;


      public DownloadInstallListener(String var1, String var2, boolean var3, DownloadManager var4) {
         this.mTitle = var1;
         this.mPackageName = var2;
         this.mDoNotifications = var3;
         this.mDownloadManager = var4;
      }

      public void onCancel() {}

      public void onComplete(Download var1) {
         PurchaseStatusTracker var2 = FinskyApp.get().getPurchaseStatusTracker();
         String var3 = this.mPackageName;
         var2.remove(var3);
         Download.PackageProperties var4 = var1.getPackageProperties();
         if(var4 != null) {
            long var5 = var4.size;
            String var7 = var4.signature;
            Uri var8 = var1.getContentUri();
            String var9 = this.mTitle;
            boolean var10 = this.mDoNotifications;
            Installer.DownloadInstallListener.1 var11 = new Installer.DownloadInstallListener.1(var8);
            PackageManagerHelper.installPackage(var8, var9, var5, var7, var10, var11);
         } else {
            Object[] var12 = new Object[0];
            FinskyLog.w("Not calling installPackage for completed download without package properties.", var12);
         }
      }

      public boolean onError(int var1) {
         return false;
      }

      public void onNotificationClicked() {
         FinskyApp var1 = FinskyApp.get();
         String var2 = this.mPackageName;
         Intent var3 = NotificationManager.createDefaultClickIntent(var1, var2, (String)null, (String)null);
         Intent var4 = var3.setFlags(268435456);
         var1.startActivity(var3);
      }

      public void onProgress(DownloadProgress var1) {}

      public void onStart() {}

      class 1 implements Runnable {

         // $FF: synthetic field
         final Uri val$uri;


         1(Uri var2) {
            this.val$uri = var2;
         }

         public void run() {
            DownloadManager var1 = DownloadInstallListener.this.mDownloadManager;
            Uri var2 = this.val$uri;
            var1.remove(var2);
         }
      }
   }

   class 1 implements Response.ErrorListener {

      // $FF: synthetic field
      final String val$packageName;


      1(String var2) {
         this.val$packageName = var2;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Set var4 = Installer.this.mPendingAssetPackageNames;
         String var5 = this.val$packageName;
         var4.remove(var5);
         Installer.this.notifyListeners();
         Object[] var7 = new Object[3];
         String var8 = this.val$packageName;
         var7[0] = var8;
         String var9 = var1.toString();
         var7[1] = var9;
         var7[2] = var2;
         FinskyLog.e("Error when attempting install of single asset [%s]. ErrorCode=[%s], Message=[%s]", var7);
      }
   }

   public static class DownloadInstallListenerFilter implements DownloadListenerRecovery.DownloadListenerFilter {

      public DownloadInstallListenerFilter() {}

      public Download.DownloadListener filter(Installer var1, DownloadManager var2, Download var3, String var4, int var5, Obb var6) {
         Installer.DownloadInstallListener var7;
         if(var6 != null) {
            var7 = null;
         } else {
            String var8 = var3.getTitle();
            var7 = new Installer.DownloadInstallListener(var8, var4, (boolean)1, var2);
         }

         return var7;
      }
   }

   public static class DownloadAndInstallGetAssetListener implements Response.Listener<VendingProtos.GetAssetResponseProto> {

      private String mAccount;
      private Installer mInstaller;
      private String mPackageName;


      public DownloadAndInstallGetAssetListener(Installer var1, String var2, String var3) {
         this.mInstaller = var1;
         this.mAccount = var2;
         this.mPackageName = var3;
      }

      protected void downloadAndInstall(VendingProtos.GetAssetResponseProto var1) {
         if(var1 != null && var1.hasInstallAsset()) {
            VendingProtos.GetAssetResponseProto.InstallAsset var3 = var1.getInstallAsset();
            String var4 = var3.getAssetPackage();
            Obb var5 = ObbFactory.createEmpty((boolean)0, var4);
            Obb var6 = ObbFactory.createEmpty((boolean)1, var4);
            Iterator var7 = var1.getAdditionalFileList().iterator();

            while(var7.hasNext()) {
               VendingProtos.FileMetadataProto var8 = (VendingProtos.FileMetadataProto)var7.next();
               long var9 = var8.getSize();
               int var11 = var8.getVersionCode();
               String var12 = var8.getDownloadUrl();
               if(var9 > 0L && var12 != null) {
                  byte var16;
                  if(var8.getFileType() == 1) {
                     var16 = 1;
                  } else {
                     var16 = 0;
                  }

                  ObbState var17 = ObbState.NOT_ON_STORAGE;
                  Obb var18 = ObbFactory.create((boolean)var16, var4, var11, var12, var9, var17);
                  var18.syncStateWithStorage();
                  if(var18.isPatch()) {
                     var6 = var18;
                  } else {
                     var5 = var18;
                  }
               } else {
                  Object[] var13 = new Object[4];
                  var13[0] = var4;
                  Integer var14 = Integer.valueOf(var11);
                  var13[1] = var14;
                  Long var15 = Long.valueOf(var9);
                  var13[2] = var15;
                  var13[3] = var12;
                  FinskyLog.e("Bad obb file from server! [%s] [%d] [%d] [%s]", var13);
               }
            }

            Installer var19 = this.mInstaller;
            String var20 = var3.getBlobUrl();
            String var21 = var3.getAssetName();
            AutoUpdateState var22 = AutoUpdateState.DEFAULT;
            String var23 = this.mAccount;
            int var24 = var3.getVersionCode();
            String var25 = var3.getAssetId();
            boolean var26 = var3.getForwardLocked();
            long var27 = var3.getAssetSize();
            String var29 = var3.getAssetSignature();
            Long var30 = Long.valueOf(var3.getRefundTimeout());
            Download.PackageProperties var32 = new Download.PackageProperties(var4, var22, var23, var24, var25, var26, var27, var29, var30, var5, var6);
            String var33 = var3.getDownloadAuthCookieName();
            String var34 = var3.getDownloadAuthCookieValue();
            var19.downloadAndInstallAsset(var20, var21, var32, var33, var34, (boolean)1);
         } else {
            Object[] var2 = new Object[0];
            FinskyLog.d("Will receive a tickle instead for download.", var2);
         }
      }

      public void onResponse(VendingProtos.GetAssetResponseProto var1) {
         Set var2 = this.mInstaller.mPendingAssetPackageNames;
         String var3 = this.mPackageName;
         var2.remove(var3);
         this.mInstaller.notifyListeners();
         this.downloadAndInstall(var1);
      }
   }

   class 2 implements Response.ErrorListener {

      // $FF: synthetic field
      final String val$packageName;


      2(String var2) {
         this.val$packageName = var2;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Set var4 = Installer.this.mPendingAssetPackageNames;
         String var5 = this.val$packageName;
         var4.remove(var5);
         Installer.this.notifyListeners();
         Object[] var7 = new Object[2];
         String var8 = var1.name();
         var7[0] = var8;
         var7[1] = var2;
         FinskyLog.e("Error getting asset: %s - %s", var7);
      }
   }
}
