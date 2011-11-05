package com.google.android.finsky.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.download.Storage;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PackageManagerUtils;

public class PackageManagerHelper {

   private static final int INSTALL_FORWARD_LOCK = 1;
   private static final int INSTALL_REPLACE_EXISTING = 2;


   public PackageManagerHelper() {}

   private static String getApplicationName(String param0) {
      // $FF: Couldn't be decompiled
   }

   protected static int getInstallFailMessageId(int var0) {
      int var1 = 2131230872;
      switch(var0) {
      case -109:
         var1 = 2131230882;
         break;
      case -108:
         var1 = 2131230882;
         break;
      case -107:
         var1 = 2131230873;
         break;
      case -106:
         var1 = 2131230884;
         break;
      case -105:
         var1 = 2131230883;
         break;
      case -104:
         var1 = 2131230883;
         break;
      case -103:
         var1 = 2131230883;
         break;
      case -101:
         var1 = 2131230882;
         break;
      case -100:
         var1 = 2131230873;
         break;
      case -20:
      case -19:
         var1 = 2131230887;
         break;
      case -18:
         var1 = 2131230886;
         break;
      case -17:
         var1 = 2131230885;
         break;
      case -16:
         var1 = 2131230881;
         break;
      case -14:
         var1 = 2131230880;
         break;
      case -13:
         var1 = 2131230879;
         break;
      case -12:
         var1 = 2131230878;
         break;
      case -11:
         var1 = 2131230873;
         break;
      case -10:
         var1 = 2131230877;
         break;
      case -9:
         var1 = 2131230876;
         break;
      case -7:
         var1 = 2131230875;
      case -5:
      case -1:
         break;
      case -4:
         var1 = 2131230874;
         break;
      case -2:
         var1 = 2131230873;
         break;
      default:
         var1 = -1;
      }

      return var1;
   }

   public static void installPackage(Uri var0, String var1, long var2, String var4, boolean var5, Runnable var6) {
      PackageManagerHelper.OnCompleteListenerNotifier var14 = new PackageManagerHelper.OnCompleteListenerNotifier(var0, var1, var2, var4, var5, var6, (PackageManagerHelper.1)null);
      Void[] var15 = new Void[0];
      var14.execute(var15);
   }

   private static boolean isAlreadyInstalled(String var0) {
      boolean var1 = false;
      if(var0 != null) {
         PackageInfo var2;
         try {
            var2 = FinskyApp.get().getPackageManager().getPackageInfo(var0, 0);
         } catch (NameNotFoundException var4) {
            return var1;
         }

         if(var2 != null) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }

   protected static void notifyFailedInstall(String var0, int var1) {
      int var2 = getInstallFailMessageId(var1);
      FinskyApp var3 = FinskyApp.get();
      String var4;
      if(var2 >= 0) {
         var4 = var3.getString(var2);
      } else {
         Object[] var9 = new Object[1];
         Integer var10 = Integer.valueOf(var1);
         var9[0] = var10;
         var4 = var3.getString(2131230888, var9);
      }

      Looper var5 = Looper.getMainLooper();
      Handler var6 = new Handler(var5);
      PackageManagerHelper.1 var7 = new PackageManagerHelper.1(var0, var4);
      var6.post(var7);
   }

   private static void notifySuccessfulInstall(String var0, boolean var1) {
      Notifier var2 = FinskyInstance.get().getNotifier();
      String var3 = getApplicationName(var0);
      var2.showSuccessfulInstallMessage(var3, var0, var1);
   }

   public static void uninstallPackage(String var0) {
      PackageManagerUtils.uninstallPackage(FinskyApp.get(), var0);
   }

   static class 1 implements Runnable {

      // $FF: synthetic field
      final String val$message;
      // $FF: synthetic field
      final String val$packageName;


      1(String var1, String var2) {
         this.val$packageName = var1;
         this.val$message = var2;
      }

      public void run() {
         Notifier var1 = FinskyInstance.get().getNotifier();
         String var2 = this.val$packageName;
         var1.hideAllMessages(var2);
         String var3 = PackageManagerHelper.getApplicationName(this.val$packageName);
         String var4 = this.val$packageName;
         String var5 = this.val$message;
         var1.showInstallationFailureMessage(var3, var4, var5);
      }
   }

   private static class OnCompleteListenerNotifier extends AsyncTask<Void, Void, Uri> {

      private final Uri mContentUri;
      private final boolean mDoNotifications;
      private final String mExpectedSignature;
      private final long mExpectedSize;
      private final Runnable mPostInstallRunnable;
      private final String mTitle;
      private volatile boolean mVerified;


      private OnCompleteListenerNotifier(Uri var1, String var2, long var3, String var5, boolean var6, Runnable var7) {
         this.mContentUri = var1;
         this.mTitle = var2;
         this.mExpectedSize = var3;
         this.mExpectedSignature = var5;
         this.mDoNotifications = var6;
         this.mPostInstallRunnable = var7;
      }

      // $FF: synthetic method
      OnCompleteListenerNotifier(Uri var1, String var2, long var3, String var5, boolean var6, Runnable var7, PackageManagerHelper.1 var8) {
         this(var1, var2, var3, var5, var6, var7);
      }

      private static boolean verifyApk(Context param0, Uri param1, long param2, String param4) {
         // $FF: Couldn't be decompiled
      }

      protected Uri doInBackground(Void ... var1) {
         if(this.mExpectedSize >= 0L) {
            if(FinskyLog.DEBUG) {
               Object[] var2 = new Object[2];
               String var3 = this.mTitle;
               var2[0] = var3;
               Long var4 = Long.valueOf(this.mExpectedSize);
               var2[1] = var4;
               FinskyLog.v("Performing verification of \'%s\' (expectedSize=%d)...", var2);
            }

            FinskyApp var5 = FinskyApp.get();
            Uri var6 = this.mContentUri;
            long var7 = this.mExpectedSize;
            String var9 = this.mExpectedSignature;
            boolean var10 = verifyApk(var5, var6, var7, var9);
            this.mVerified = var10;
            if(FinskyLog.DEBUG) {
               Object[] var11 = new Object[2];
               String var12 = this.mTitle;
               var11[0] = var12;
               Boolean var13 = Boolean.valueOf(this.mVerified);
               var11[1] = var13;
               FinskyLog.v("Verification of \'%s\' finished (result=%s).", var11);
            }
         } else {
            Object[] var14 = new Object[0];
            FinskyLog.d("Signature check not required.", var14);
            this.mVerified = (boolean)1;
         }

         return Storage.getFileUriForContentUri(this.mContentUri);
      }

      protected void onPostExecute(Uri var1) {
         AssetStore var2 = FinskyInstance.get().getAssetStore();
         Uri var3 = this.mContentUri;
         LocalAsset var4 = var2.getAsset(var3);
         if(!this.mVerified) {
            Object[] var5 = new Object[0];
            FinskyLog.w("Signature check failed, aborting installation.", var5);
            var4.cleanupInstallFailure();
            if(this.mDoNotifications) {
               PackageManagerHelper.notifyFailedInstall(var4.getPackage(), -1);
            }

            if(this.mPostInstallRunnable != null) {
               this.mPostInstallRunnable.run();
            }
         } else {
            int var6 = 2;
            if(var4 == null) {
               Object[] var7 = new Object[0];
               FinskyLog.w("Installing package with no associated asset.", var7);
            } else if(var4.isForwardLocked()) {
               var6 |= 1;
            }

            String var8;
            if(var4 != null) {
               var8 = var4.getPackage();
            } else {
               var8 = "";
            }

            boolean var9 = PackageManagerHelper.isAlreadyInstalled(var8);
            PackageManagerHelper.OnCompleteListenerNotifier.1 var10 = new PackageManagerHelper.OnCompleteListenerNotifier.1(var8, var9, var4);
            if(this.mDoNotifications) {
               Notifier var11 = FinskyInstance.get().getNotifier();
               String var12 = this.mTitle;
               var11.showInstallingMessage(var12, var8, var9);
            }

            FinskyApp var13 = FinskyApp.get();
            if(var1 == null) {
               var1 = this.mContentUri;
            }

            PackageManagerUtils.installPackage(var13, var1, var10, var6);
         }
      }

      class 1 implements PackageManagerUtils.PackageInstallObserver {

         // $FF: synthetic field
         final LocalAsset val$asset;
         // $FF: synthetic field
         final boolean val$isAlreadyInstalled;
         // $FF: synthetic field
         final String val$packageName;


         1(String var2, boolean var3, LocalAsset var4) {
            this.val$packageName = var2;
            this.val$isAlreadyInstalled = var3;
            this.val$asset = var4;
         }

         public void packageInstalled(String var1, int var2) {
            try {
               String var3 = "Package install status for \"" + var1 + "\" is " + var2;
               Object[] var4 = new Object[0];
               FinskyLog.d(var3, var4);
               if(OnCompleteListenerNotifier.this.mDoNotifications) {
                  if(var1 == null) {
                     var1 = this.val$packageName;
                  } else {
                     String var8 = this.val$packageName;
                     if(!var1.equals(var8)) {
                        StringBuilder var9 = (new StringBuilder()).append("Package name mismatsch: ours: ");
                        String var10 = this.val$packageName;
                        String var11 = var9.append(var10).append(" package manager\'s: ").append(var1).toString();
                        Object[] var12 = new Object[0];
                        FinskyLog.w(var11, var12);
                     }
                  }

                  if(var2 == 1) {
                     boolean var5 = this.val$isAlreadyInstalled;
                     PackageManagerHelper.notifySuccessfulInstall(var1, var5);
                     if(OnCompleteListenerNotifier.this.mPostInstallRunnable != null) {
                        OnCompleteListenerNotifier.this.mPostInstallRunnable.run();
                     }
                  } else {
                     PackageManagerHelper.notifyFailedInstall(var1, var2);
                     Looper var13 = FinskyApp.get().getMainLooper();
                     Handler var14 = new Handler(var13);
                     PackageManagerHelper.OnCompleteListenerNotifier.1.1 var15 = new PackageManagerHelper.OnCompleteListenerNotifier.1.1();
                     var14.post(var15);
                  }
               }
            } catch (Exception var17) {
               Object[] var7 = new Object[0];
               FinskyLog.wtf(var17, "Package install observer exception", var7);
            }
         }

         class 1 implements Runnable {

            1() {}

            public void run() {
               1.this.val$asset.cleanupInstallFailure();
               if(OnCompleteListenerNotifier.this.mPostInstallRunnable != null) {
                  OnCompleteListenerNotifier.this.mPostInstallRunnable.run();
               }
            }
         }
      }
   }
}
