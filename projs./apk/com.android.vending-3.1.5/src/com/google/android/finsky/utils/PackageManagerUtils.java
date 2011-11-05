package com.google.android.finsky.utils;

import android.content.Context;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.PackageManager;
import android.content.pm.IPackageInstallObserver.Stub;
import android.net.Uri;
import android.os.RemoteException;

public class PackageManagerUtils {

   private static final String GOOGLE_FEEDBACK = "com.google.android.feedback";


   public PackageManagerUtils() {}

   public static void freeStorageAndNotify(Context var0, long var1, PackageManagerUtils.FreeSpaceListener var3) {
      PackageManager var4 = var0.getPackageManager();
      PackageManagerUtils.2 var5 = var3.new 2();
      var4.freeStorageAndNotify(var1, var5);
   }

   public static void installPackage(Context var0, Uri var1, PackageManagerUtils.PackageInstallObserver var2, int var3) {
      PackageManager var4 = var0.getPackageManager();
      PackageManagerUtils.1 var5 = var2.new 1();
      var4.installPackage(var1, var5, var3, "com.google.android.feedback");
   }

   public static void uninstallPackage(Context var0, String var1) {
      var0.getPackageManager().deletePackage(var1, (IPackageDeleteObserver)null, 0);
   }

   public interface FreeSpaceListener {

      void onComplete(boolean var1);
   }

   public interface PackageInstallObserver {

      void packageInstalled(String var1, int var2);
   }

   class 1 extends Stub {

      1() {}

      public void packageInstalled(String var1, int var2) throws RemoteException {
         if(PackageManagerUtils.this != null) {
            PackageManagerUtils.this.packageInstalled(var1, var2);
         }
      }
   }

   class 2 extends android.content.pm.IPackageDataObserver.Stub {

      2() {}

      public void onRemoveCompleted(String var1, boolean var2) {
         PackageManagerUtils.this.onComplete(var2);
      }
   }
}
