package com.android.settings.applications;

import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageManager.Stub;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

final class CanBeOnSdCardChecker {

   int mInstallLocation;
   final IPackageManager mPm;


   CanBeOnSdCardChecker() {
      IPackageManager var1 = Stub.asInterface(ServiceManager.getService("package"));
      this.mPm = var1;
   }

   boolean check(ApplicationInfo var1) {
      boolean var2 = false;
      if((var1.flags & 262144) != 0) {
         var2 = true;
      } else if((var1.flags & 536870912) == 0 && (var1.flags & 1) == 0) {
         if(var1.installLocation != 2 && var1.installLocation != 0) {
            if(var1.installLocation == -1 && this.mInstallLocation == 2) {
               var2 = true;
            }
         } else {
            var2 = true;
         }
      }

      return var2;
   }

   void init() {
      try {
         int var1 = this.mPm.getInstallLocation();
         this.mInstallLocation = var1;
      } catch (RemoteException var4) {
         int var3 = Log.e("CanBeOnSdCardChecker", "Is Package Manager running?");
      }
   }
}
