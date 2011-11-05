package com.google.android.finsky.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParameterizedRunnable;
import java.util.ArrayList;
import java.util.List;

public class PackageMonitorReceiver extends BroadcastReceiver {

   private static final String DATA_SCHEME = "package";
   private final List<PackageMonitorReceiver.PackageStatusListener> mListeners;


   public PackageMonitorReceiver() {
      ArrayList var1 = new ArrayList();
      this.mListeners = var1;
   }

   private String getPackageName(Intent var1) {
      Uri var2 = var1.getData();
      String var3;
      if(var2 != null) {
         var3 = var2.getSchemeSpecificPart();
      } else {
         var3 = null;
      }

      return var3;
   }

   private void notifyListeners(ParameterizedRunnable<PackageMonitorReceiver.PackageStatusListener> var1) {
      for(int var2 = this.mListeners.size() + -1; var2 >= 0; var2 += -1) {
         Object var3 = this.mListeners.get(var2);
         var1.run(var3);
      }

   }

   public void attach(PackageMonitorReceiver.PackageStatusListener var1) {
      this.mListeners.add(var1);
   }

   public void detach(PackageMonitorReceiver.PackageStatusListener var1) {
      this.mListeners.remove(var1);
   }

   public void onReceive(Context var1, Intent var2) {
      byte var3 = 1;
      String var4 = var2.getAction();
      if(!"android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(var4) && !"android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(var4)) {
         String var8 = this.getPackageName(var2);
         if(var8 != null) {
            if(!"android.intent.action.PACKAGE_REMOVED".equals(var4)) {
               if("android.intent.action.PACKAGE_ADDED".equals(var4)) {
                  PackageMonitorReceiver.3 var11 = new PackageMonitorReceiver.3(var8);
                  this.notifyListeners(var11);
               } else if("android.intent.action.PACKAGE_CHANGED".equals(var4)) {
                  PackageMonitorReceiver.4 var12 = new PackageMonitorReceiver.4(var8);
                  this.notifyListeners(var12);
               } else {
                  Object[] var13 = new Object[]{var4};
                  FinskyLog.w("Unhandled intent type action type: %s", var13);
               }
            } else {
               Bundle var9 = var2.getExtras();
               if(var9 == null || !var9.getBoolean("android.intent.extra.REPLACING", (boolean)0)) {
                  var3 = 0;
               }

               PackageMonitorReceiver.2 var10 = new PackageMonitorReceiver.2(var8, (boolean)var3);
               this.notifyListeners(var10);
            }
         }
      } else {
         String[] var5 = var2.getStringArrayExtra("android.intent.extra.changed_package_list");
         boolean var6 = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(var4);
         PackageMonitorReceiver.1 var7 = new PackageMonitorReceiver.1(var5, var6);
         this.notifyListeners(var7);
      }
   }

   public void registerReceiver(Context var1) {
      IntentFilter var2 = new IntentFilter();
      var2.addAction("android.intent.action.PACKAGE_ADDED");
      var2.addAction("android.intent.action.PACKAGE_CHANGED");
      var2.addAction("android.intent.action.PACKAGE_REMOVED");
      var2.addDataScheme("package");
      var1.registerReceiver(this, var2);
   }

   class 3 implements ParameterizedRunnable<PackageMonitorReceiver.PackageStatusListener> {

      // $FF: synthetic field
      final String val$packageName;


      3(String var2) {
         this.val$packageName = var2;
      }

      public void run(PackageMonitorReceiver.PackageStatusListener var1) {
         String var2 = this.val$packageName;
         var1.onPackageAdded(var2);
      }
   }

   class 4 implements ParameterizedRunnable<PackageMonitorReceiver.PackageStatusListener> {

      // $FF: synthetic field
      final String val$packageName;


      4(String var2) {
         this.val$packageName = var2;
      }

      public void run(PackageMonitorReceiver.PackageStatusListener var1) {
         String var2 = this.val$packageName;
         var1.onPackageChanged(var2);
      }
   }

   class 1 implements ParameterizedRunnable<PackageMonitorReceiver.PackageStatusListener> {

      // $FF: synthetic field
      final boolean val$available;
      // $FF: synthetic field
      final String[] val$changedPackages;


      1(String[] var2, boolean var3) {
         this.val$changedPackages = var2;
         this.val$available = var3;
      }

      public void run(PackageMonitorReceiver.PackageStatusListener var1) {
         String[] var2 = this.val$changedPackages;
         boolean var3 = this.val$available;
         var1.onPackageAvailabilityChanged(var2, var3);
      }
   }

   class 2 implements ParameterizedRunnable<PackageMonitorReceiver.PackageStatusListener> {

      // $FF: synthetic field
      final String val$packageName;
      // $FF: synthetic field
      final boolean val$replacing;


      2(String var2, boolean var3) {
         this.val$packageName = var2;
         this.val$replacing = var3;
      }

      public void run(PackageMonitorReceiver.PackageStatusListener var1) {
         String var2 = this.val$packageName;
         boolean var3 = this.val$replacing;
         var1.onPackageRemoved(var2, var3);
      }
   }

   public interface PackageStatusListener {

      void onPackageAdded(String var1);

      void onPackageAvailabilityChanged(String[] var1, boolean var2);

      void onPackageChanged(String var1);

      void onPackageRemoved(String var1, boolean var2);
   }
}
