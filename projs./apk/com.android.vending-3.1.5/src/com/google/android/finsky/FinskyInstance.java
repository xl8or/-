package com.google.android.finsky;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.PurchaseErrorSurfacer;
import com.google.android.finsky.billing.iab.PendingNotificationsService;
import com.google.android.finsky.download.DownloadBroadcastReceiver;
import com.google.android.finsky.download.DownloadManagerImpl;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.DownloadQueueImpl;
import com.google.android.finsky.download.DownloadRestarterRule;
import com.google.android.finsky.download.DownloadUriUrlMap;
import com.google.android.finsky.download.obb.ObbPackageTracker;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.AssetStoreFactory;
import com.google.android.finsky.local.InconsistentAssetRemoverRule;
import com.google.android.finsky.local.checker.InstallStateVerifierRule;
import com.google.android.finsky.local.checker.SanityChecker;
import com.google.android.finsky.receivers.DeclineAssetReceiver;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.receivers.RemoveAssetReceiver;
import com.google.android.finsky.receivers.ServerNotificationReceiver;
import com.google.android.finsky.receivers.UpdateCheckReceiver;
import com.google.android.finsky.services.CheckinAssetStoreListener;
import com.google.android.finsky.services.ContentSyncService;
import com.google.android.finsky.utils.AssetStoreUpdater;
import com.google.android.finsky.utils.NotificationManager;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.finsky.utils.PurchaseInitiator;
import com.google.android.finsky.utils.SelfUpdateScheduler;
import com.google.android.finsky.utils.UninstallRefundTracker;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.finsky.utils.persistence.FileBasedKeyValueStore;
import com.google.android.finsky.utils.persistence.WriteThroughKeyValueStore;
import com.google.android.vending.remoting.api.PendingNotificationsHandler;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.io.File;

public class FinskyInstance {

   private static FinskyInstance sFinskyInstance;
   private final AssetStore mAssetStore;
   private final CheckinAssetStoreListener mCheckinAssetStoreListener;
   private final Context mContext;
   private final DownloadQueueImpl mDownloadQueue;
   private final Installer mInstaller;
   private VendingProtos.GetMarketMetadataResponseProto mMarketMetadata;
   private final Notifier mNotificationHelper;
   private final PackageInfoCache mPackageInfoCache;
   private final PendingNotificationsHandler mPendingNotificationsHandler;
   private final SelfUpdateScheduler mSelfUpdateScheduler;


   private FinskyInstance(Context var1, PackageMonitorReceiver var2, PackageInfoCache var3) {
      FinskyInstance.1 var4 = new FinskyInstance.1();
      this.mPendingNotificationsHandler = var4;
      this.mContext = var1;
      this.mPackageInfoCache = var3;
      AssetStore var5 = (new AssetStoreFactory()).makeAssetStore(var1);
      this.mAssetStore = var5;
      Context var6 = this.mContext;
      AssetStore var7 = this.mAssetStore;
      NotificationManager var8 = new NotificationManager(var6, var7);
      this.mNotificationHelper = var8;
      DownloadManagerImpl var9 = new DownloadManagerImpl(var1);
      File var10 = var1.getDir("uri_url_map", 0);
      FileBasedKeyValueStore var11 = new FileBasedKeyValueStore(var10, "market_download_data");
      WriteThroughKeyValueStore var12 = new WriteThroughKeyValueStore(var11);
      var12.forceSynchronousLoad();
      Context var13 = this.mContext;
      Notifier var14 = this.mNotificationHelper;
      DownloadUriUrlMap var15 = new DownloadUriUrlMap(var12);
      DownloadQueueImpl var16 = new DownloadQueueImpl(var13, var14, var9, var15);
      this.mDownloadQueue = var16;
      RemoveAssetReceiver.initialize(this.mNotificationHelper);
      DownloadBroadcastReceiver.initialize(this.mDownloadQueue);
      DeclineAssetReceiver.initialize(this.mDownloadQueue);
      PurchaseInitiator.initialize(this.mNotificationHelper);
      ServerNotificationReceiver.initialize(this.mNotificationHelper);
      PurchaseErrorSurfacer.initialize(FinskyApp.get().getPurchaseStatusTracker());
      UpdateCheckReceiver.initialize(this.mContext);
      AssetStore var17 = this.mAssetStore;
      PackageManager var18 = var1.getPackageManager();
      DownloadQueueImpl var19 = this.mDownloadQueue;
      Installer var20 = new Installer(var17, var18, var19, var9);
      this.mInstaller = var20;
      Context var21 = this.mContext;
      ContentSyncService.Facade var22 = ContentSyncService.get();
      CheckinAssetStoreListener var23 = new CheckinAssetStoreListener(var21, var22);
      this.mCheckinAssetStoreListener = var23;
      AssetStore var24 = this.mAssetStore;
      CheckinAssetStoreListener var25 = this.mCheckinAssetStoreListener;
      var24.addListener(var25);
      AssetStore var26 = this.mAssetStore;
      DownloadQueueImpl var27 = this.mDownloadQueue;
      CheckinAssetStoreListener var28 = this.mCheckinAssetStoreListener;
      new AssetStoreUpdater(var26, var2, var27, var28);
      DownloadQueueImpl var30 = this.mDownloadQueue;
      String var31 = var1.getPackageName();
      int var32 = var3.getPackageVersion(var31);
      SelfUpdateScheduler var33 = new SelfUpdateScheduler(var30, var32);
      this.mSelfUpdateScheduler = var33;
      Context var34 = this.mContext;
      AssetStore var35 = this.mAssetStore;
      new UninstallRefundTracker(var34, var35, var2);
      AssetStore var37 = this.mAssetStore;
      SanityChecker.SanityCheckRule[] var38 = new SanityChecker.SanityCheckRule[3];
      Context var39 = this.mContext;
      CheckinAssetStoreListener var40 = this.mCheckinAssetStoreListener;
      InconsistentAssetRemoverRule var41 = new InconsistentAssetRemoverRule(var39, var40);
      var38[0] = var41;
      PackageInfoCache var42 = this.mPackageInfoCache;
      InstallStateVerifierRule var43 = new InstallStateVerifierRule(var42);
      var38[1] = var43;
      Context var44 = this.mContext;
      DownloadQueueImpl var45 = this.mDownloadQueue;
      PackageInfoCache var46 = this.mPackageInfoCache;
      Installer var47 = this.mInstaller;
      DownloadRestarterRule var48 = new DownloadRestarterRule(var44, var45, var46, var47, var9);
      var38[2] = var48;
      (new SanityChecker(var37, var38)).run();
      ObbPackageTracker var49 = new ObbPackageTracker();
      var2.attach(var49);
      int var50 = this.getVersionCode();
      if(this.wasMarketUpgraded(var50)) {
         var9.removeAll();
      }
   }

   public static FinskyInstance get() {
      return sFinskyInstance;
   }

   public static void initialize(Context var0, PackageMonitorReceiver var1, PackageInfoCache var2) {
      if(sFinskyInstance != null) {
         throw new IllegalStateException("Initialize called twice");
      } else {
         sFinskyInstance = new FinskyInstance(var0, var1, var2);
      }
   }

   public static boolean isPhonesky() {
      return true;
   }

   private boolean wasMarketUpgraded(int var1) {
      boolean var2;
      if(VendingPreferences.RECONCILED_VERSION.exists() && ((Integer)VendingPreferences.RECONCILED_VERSION.get()).intValue() == var1) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public AssetStore getAssetStore() {
      return this.mAssetStore;
   }

   public CheckinAssetStoreListener getCheckinAssetStoreListener() {
      return this.mCheckinAssetStoreListener;
   }

   public DownloadQueue getDownloadQueue() {
      return this.mDownloadQueue;
   }

   public Installer getInstaller() {
      return this.mInstaller;
   }

   public VendingProtos.GetMarketMetadataResponseProto getMarketMetadata() {
      return this.mMarketMetadata;
   }

   public Notifier getNotifier() {
      return this.mNotificationHelper;
   }

   public PendingNotificationsHandler getPendingNotificationsHandler() {
      return this.mPendingNotificationsHandler;
   }

   public SelfUpdateScheduler getSelfUpdateScheduler() {
      return this.mSelfUpdateScheduler;
   }

   public int getVersionCode() {
      PackageInfoCache var1 = this.mPackageInfoCache;
      String var2 = this.mContext.getPackageName();
      return var1.getPackageVersion(var2);
   }

   public void setMarketMetadata(VendingProtos.GetMarketMetadataResponseProto var1) {
      this.mMarketMetadata = var1;
   }

   class 1 implements PendingNotificationsHandler {

      1() {}

      public boolean handlePendingNotifications(Context var1, String var2, VendingProtos.PendingNotificationsProto var3, boolean var4) {
         return PendingNotificationsService.handlePendingNotifications(var1, var2, var3, var4);
      }
   }
}
