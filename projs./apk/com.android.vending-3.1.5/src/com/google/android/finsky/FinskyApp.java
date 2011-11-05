package com.google.android.finsky;

import android.accounts.Account;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.provider.SearchRecentSuggestions;
import com.android.vending.VendingBackupAgent;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.analytics.DfeAnalytics;
import com.google.android.finsky.analytics.StubAnalytics;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.GservicesValue;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.model.PurchaseStatusTracker;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.DenyAllNetwork;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.finsky.utils.Utils;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.api.VendingApiFactory;
import java.io.File;

public class FinskyApp extends Application {

   public static final String ACTION_DFE_API_CONTEXT_CHANGED = "com.google.android.finsky.action.DFE_API_CONTEXT_CHANGED";
   private static final String BOOT_RECEIVER = "com.google.android.finsky.billing.iab.BootCompletedReceiver";
   private static final String FINSKY_PACKAGE_NAME = "com.google.android.finsky";
   private static final int IMAGE_CACHE_SIZE = 4194304;
   private static final String IMAGE_CACHE_SUBDIR = "images";
   private static final int MAIN_CACHE_SIZE = 1048576;
   private static final String MAIN_CACHE_SUBDIR = "main";
   private static FinskyApp sInstance;
   private Analytics mAnalytics;
   private VendingApiFactory mApiFactory;
   private Cache mBitmapCache;
   private BitmapLoader mBitmapLoader;
   private RequestQueue mBitmapRequestQueue;
   private Cache mCache;
   private DfeApi mDfeApi;
   private PackageInfoCache mPackageInfoCache;
   private PackageMonitorReceiver mPackageMonitorReceiver;
   private PurchaseStatusTracker mPurchaseStatusTracker;
   private SearchRecentSuggestions mRecentSuggestions;
   private RequestQueue mRequestQueue;
   private DfeToc mToc;


   public FinskyApp() {}

   private void cleanupOldFinsky() {
      if(this.mPackageInfoCache.isPackageInstalled("com.google.android.finsky")) {
         try {
            PackageManager var1 = this.getPackageManager();
            PackageInfo var2 = var1.getPackageInfo("com.google.android.finsky", 0);
            if(var1.getApplicationEnabledSetting("com.google.android.finsky") != 2) {
               var1.setApplicationEnabledSetting("com.google.android.finsky", 2, 1);
            }
         } catch (SecurityException var7) {
            Object[] var4 = new Object[0];
            FinskyLog.w("Unable to disable old finsky package.", var4);
         } catch (NameNotFoundException var8) {
            Object[] var6 = new Object[]{"com.google.android.finsky"};
            FinskyLog.d("%s doesn\'t exist; ignoring", var6);
         }
      }
   }

   private Network createNetwork() {
      Object var1;
      if(Utils.isBackgroundDataEnabled(this)) {
         var1 = new BasicNetwork(this);
      } else {
         var1 = new DenyAllNetwork();
      }

      return (Network)var1;
   }

   private void enableBootReceiver() {
      try {
         StringBuilder var1 = new StringBuilder();
         String var2 = this.getPackageName();
         ComponentName var3 = ComponentName.unflattenFromString(var1.append(var2).append("/").append("com.google.android.finsky.billing.iab.BootCompletedReceiver").toString());
         PackageManager var4 = this.getPackageManager();
         if(var4.getComponentEnabledSetting(var3) != 1) {
            var4.setComponentEnabledSetting(var3, 1, 1);
         }
      } catch (SecurityException var7) {
         Object[] var6 = new Object[0];
         FinskyLog.wtf("Unable to enable the boot receiver.", var6);
      }
   }

   public static FinskyApp get() {
      return sInstance;
   }

   private File getCacheDir(String var1) {
      File var2 = this.getCacheDir();
      File var3 = new File(var2, var1);
      boolean var4 = var3.mkdirs();
      return var3;
   }

   public void clearCacheAsync(Runnable var1) {
      RequestQueue var2 = this.mRequestQueue;
      Cache var3 = this.mCache;
      FinskyApp.1 var4 = new FinskyApp.1(var1);
      ClearCacheRequest var5 = new ClearCacheRequest(var3, var4);
      var2.add(var5);
   }

   public void drainAllRequests(int var1) {
      get().getRequestQueue().drain(var1);
      get().getBitmapLoader().drain(var1);
   }

   public Analytics getAnalytics() {
      return this.mAnalytics;
   }

   public BitmapLoader getBitmapLoader() {
      return this.mBitmapLoader;
   }

   public Cache getCache() {
      return this.mCache;
   }

   public String getCurrentAccountName() {
      String var1;
      if(this.mDfeApi != null) {
         var1 = this.mDfeApi.getCurrentAccountName();
      } else {
         var1 = null;
      }

      return var1;
   }

   public DfeApi getDfeApi() {
      return this.mDfeApi;
   }

   public DfeApi getDfeApi(Account var1) {
      RequestQueue var2 = this.mRequestQueue;
      DfeApiContext var3 = DfeApiContext.create(var1);
      return new DfeApi(var2, var3);
   }

   public PackageInfoCache getPackageInfoCache() {
      return this.mPackageInfoCache;
   }

   public PackageMonitorReceiver getPackageMonitorReceiver() {
      return this.mPackageMonitorReceiver;
   }

   public PurchaseStatusTracker getPurchaseStatusTracker() {
      return this.mPurchaseStatusTracker;
   }

   public SearchRecentSuggestions getRecentSuggestions() {
      return this.mRecentSuggestions;
   }

   public RequestQueue getRequestQueue() {
      return this.mRequestQueue;
   }

   public DfeToc getToc() {
      return this.mToc;
   }

   public VendingApi getVendingApi() {
      String var1 = this.getCurrentAccountName();
      return this.getVendingApi(var1);
   }

   public VendingApi getVendingApi(String var1) {
      return this.mApiFactory.getApi(var1);
   }

   public void onCreate() {
      sInstance = this;
      GservicesValue.init(this);
      PreferenceFile.init(this);
      File var1 = this.getCacheDir("main");
      DiskBasedCache var2 = new DiskBasedCache(var1, 1048576);
      this.mCache = var2;
      Cache var3 = this.mCache;
      Network var4 = this.createNetwork();
      RequestQueue var5 = new RequestQueue(var3, var4, 2);
      this.mRequestQueue = var5;
      this.mRequestQueue.start();
      Context var6 = this.getApplicationContext();
      RequestQueue var7 = this.mRequestQueue;
      VendingApiFactory var8 = new VendingApiFactory(var6, var7);
      this.mApiFactory = var8;
      PackageMonitorReceiver var9 = new PackageMonitorReceiver();
      this.mPackageMonitorReceiver = var9;
      PackageManager var10 = this.getPackageManager();
      PackageMonitorReceiver var11 = this.mPackageMonitorReceiver;
      PackageInfoCache var12 = new PackageInfoCache(var10, var11);
      this.mPackageInfoCache = var12;
      File var13 = this.getCacheDir("images");
      DiskBasedCache var14 = new DiskBasedCache(var13, 4194304);
      this.mBitmapCache = var14;
      Cache var15 = this.mBitmapCache;
      Network var16 = this.createNetwork();
      RequestQueue var17 = new RequestQueue(var15, var16);
      this.mBitmapRequestQueue = var17;
      this.mBitmapRequestQueue.start();
      RequestQueue var18 = this.mBitmapRequestQueue;
      BitmapLoader var19 = new BitmapLoader(var18);
      this.mBitmapLoader = var19;
      if(((Boolean)G.analyticsEnabled.get()).booleanValue()) {
         Looper var20 = this.getMainLooper();
         Handler var21 = new Handler(var20);
         DfeAnalytics var22 = new DfeAnalytics(var21);
         this.mAnalytics = var22;
      } else {
         StubAnalytics var27 = new StubAnalytics();
         this.mAnalytics = var27;
      }

      PurchaseStatusTracker var23 = new PurchaseStatusTracker();
      this.mPurchaseStatusTracker = var23;
      BillingLocator.initSingleton();
      VendingBackupAgent.registerWithBackup(this.getApplicationContext());
      PackageMonitorReceiver var24 = this.mPackageMonitorReceiver;
      PackageInfoCache var25 = this.mPackageInfoCache;
      FinskyInstance.initialize(this, var24, var25);
      SearchRecentSuggestions var26 = new SearchRecentSuggestions(this, "com.google.android.finsky.RecentSuggestionsProvider", 1);
      this.mRecentSuggestions = var26;
      this.cleanupOldFinsky();
      this.enableBootReceiver();
   }

   public void setApiContext(DfeApiContext var1) {
      RequestQueue var2 = this.mRequestQueue;
      DfeApi var3 = new DfeApi(var2, var1);
      this.mDfeApi = var3;
      this.mAnalytics.reset();
      Intent var4 = new Intent("com.google.android.finsky.action.DFE_API_CONTEXT_CHANGED");
      this.sendBroadcast(var4);
      if(FinskyLog.DEBUG) {
         Object[] var5 = new Object[]{var1};
         FinskyLog.d("Created new APIs with contexts (dfe=%s)", var5);
      }
   }

   public void setToc(DfeToc var1) {
      this.mToc = var1;
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final Runnable val$callback;


      1(Runnable var2) {
         this.val$callback = var2;
      }

      public void run() {
         RequestQueue var1 = FinskyApp.this.mBitmapRequestQueue;
         Cache var2 = FinskyApp.this.mBitmapCache;
         Runnable var3 = this.val$callback;
         ClearCacheRequest var4 = new ClearCacheRequest(var2, var3);
         var1.add(var4);
      }
   }
}
