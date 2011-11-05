package com.google.android.finsky.utils;

import android.accounts.Account;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import com.android.volley.AuthFailureException;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadImpl;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.utils.ApplicationDismissedDeferrer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PackageManagerHelper;
import com.google.android.vending.remoting.protos.VendingProtos;

public class SelfUpdateScheduler implements Download.DownloadListener {

   public static final String ANDROID_SECURE_AUTH_COOKIE_NAME = "ANDROIDSECURE";
   private static final int POLL_INTERVAL_MS = 10000;
   private final DownloadQueue mDownloadQueue;
   private int mMarketVersion;
   private ApplicationDismissedDeferrer mOnAppExitDeferrer;
   private boolean mUpdateInProgress = 0;


   public SelfUpdateScheduler(DownloadQueue var1, int var2) {
      this.mDownloadQueue = var1;
      this.mMarketVersion = var2;
   }

   public void checkForSelfUpdate(VendingProtos.GetMarketMetadataResponseProto var1) {
      if(this.mUpdateInProgress) {
         Object[] var2 = new Object[0];
         FinskyLog.d("Skipping self-update check as there is an update already queued.", var2);
      } else {
         int var3 = var1.getLatestClientVersionCode();
         if(this.mMarketVersion >= var3) {
            Object[] var4 = new Object[2];
            Integer var5 = Integer.valueOf(this.mMarketVersion);
            var4[0] = var5;
            Integer var6 = Integer.valueOf(var3);
            var4[1] = var6;
            FinskyLog.d("Skipping self-update. Local Version [%d] >= Server Version [%d]", var4);
         } else if(TextUtils.isEmpty(var1.getLatestClientUrl())) {
            Object[] var7 = new Object[0];
            FinskyLog.d("Skipping self-update. No upgrade URL specified.", var7);
         } else {
            this.mUpdateInProgress = (boolean)1;
            String var8 = FinskyApp.get().getCurrentAccountName();
            Account var9 = new Account(var8, "com.google");
            FinskyApp var10 = FinskyApp.get();
            AndroidAuthenticator var11 = new AndroidAuthenticator(var10, var9);
            SelfUpdateScheduler.1 var12 = new SelfUpdateScheduler.1(var1);
            Handler var13 = new Handler();
            String var14 = (String)G.vendingSecureAuthTokenType.get();
            var11.getAuthTokenAsync(var12, var13, var14);
         }
      }
   }

   public void onCancel() {}

   public void onComplete(Download var1) {
      if(this.mOnAppExitDeferrer != null) {
         Object[] var2 = new Object[0];
         FinskyLog.d("Self-update package Uri was already assigned!", var2);
      } else {
         Object[] var3 = new Object[0];
         FinskyLog.d("Self-update ready to be installed, waiting for market to close.", var3);
         FinskyApp var4 = FinskyApp.get();
         ApplicationDismissedDeferrer var5 = new ApplicationDismissedDeferrer(var4);
         this.mOnAppExitDeferrer = var5;
         ApplicationDismissedDeferrer var6 = this.mOnAppExitDeferrer;
         SelfUpdateScheduler.2 var7 = new SelfUpdateScheduler.2(var1);
         var6.runOnApplicationClose(var7, 10000);
      }
   }

   public boolean onError(int var1) {
      Object[] var2 = new Object[1];
      Integer var3 = Integer.valueOf(var1);
      var2[0] = var3;
      FinskyLog.e("Self-update failed because of HTTP error code: %d", var2);
      return false;
   }

   public void onNotificationClicked() {}

   public void onProgress(DownloadProgress var1) {}

   public void onStart() {}

   class 2 implements Runnable {

      // $FF: synthetic field
      final Download val$download;


      2(Download var2) {
         this.val$download = var2;
      }

      public void run() {
         Uri var1 = this.val$download.getContentUri();
         Object var2 = null;
         Object var3 = null;
         PackageManagerHelper.installPackage(var1, (String)null, 65535L, (String)var2, (boolean)0, (Runnable)var3);
      }
   }

   class 1 implements AndroidAuthenticator.AuthTokenListener {

      // $FF: synthetic field
      final VendingProtos.GetMarketMetadataResponseProto val$response;


      1(VendingProtos.GetMarketMetadataResponseProto var2) {
         this.val$response = var2;
      }

      public void onAuthTokenReceived(String var1) {
         String var2 = this.val$response.getLatestClientUrl();
         Notifier var3 = SelfUpdateScheduler.this.mDownloadQueue.getNotificationHelper();
         Object var5 = null;
         DownloadImpl var6 = new DownloadImpl(var2, "", (Download.PackageProperties)null, "ANDROIDSECURE", var1, (Uri)var5, var3, 65535L);
         SelfUpdateScheduler var7 = SelfUpdateScheduler.this;
         var6.addListener(var7);
         SelfUpdateScheduler.this.mDownloadQueue.add(var6);
      }

      public void onErrorReceived(AuthFailureException var1) {
         Object[] var2 = new Object[]{var1};
         FinskyLog.d("Exception occured while getting auth token.", var2);
      }
   }
}
