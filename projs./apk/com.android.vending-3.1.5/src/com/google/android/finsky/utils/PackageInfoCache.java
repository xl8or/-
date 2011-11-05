package com.google.android.finsky.utils;

import android.content.pm.PackageManager;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.utils.Maps;
import java.util.HashMap;
import java.util.Map;

public class PackageInfoCache {

   private final Map<String, PackageInfoCache.PackageInfo> mCache;
   private final PackageManager mPackageManager;


   public PackageInfoCache() {
      HashMap var1 = Maps.newHashMap();
      this.mCache = var1;
      this.mPackageManager = null;
   }

   public PackageInfoCache(PackageManager var1, PackageMonitorReceiver var2) {
      HashMap var3 = Maps.newHashMap();
      this.mCache = var3;
      this.mPackageManager = var1;
      PackageInfoCache.1 var4 = new PackageInfoCache.1();
      var2.attach(var4);
   }

   private void updatePackageInfo(String param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean canLaunch(String var1) {
      synchronized(this){}

      boolean var2;
      try {
         this.updatePackageInfo(var1);
         var2 = ((PackageInfoCache.PackageInfo)this.mCache.get(var1)).canLaunch;
      } finally {
         ;
      }

      return var2;
   }

   public int getPackageVersion(String var1) {
      synchronized(this){}

      int var2;
      try {
         this.updatePackageInfo(var1);
         var2 = ((PackageInfoCache.PackageInfo)this.mCache.get(var1)).versionCode;
      } finally {
         ;
      }

      return var2;
   }

   public String getPackageVersionName(String var1) {
      synchronized(this){}

      String var2;
      try {
         this.updatePackageInfo(var1);
         var2 = ((PackageInfoCache.PackageInfo)this.mCache.get(var1)).versionName;
      } finally {
         ;
      }

      return var2;
   }

   public boolean isPackageInstalled(String var1) {
      synchronized(this){}
      boolean var6 = false;

      int var2;
      try {
         var6 = true;
         var2 = this.getPackageVersion(var1);
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      boolean var3;
      if(var2 != -1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isSystemPackage(String var1) {
      synchronized(this){}

      boolean var2;
      try {
         this.updatePackageInfo(var1);
         var2 = ((PackageInfoCache.PackageInfo)this.mCache.get(var1)).isSystemApp;
      } finally {
         ;
      }

      return var2;
   }

   public boolean isUpdatedSystemPackage(String var1) {
      synchronized(this){}

      boolean var2;
      try {
         this.updatePackageInfo(var1);
         var2 = ((PackageInfoCache.PackageInfo)this.mCache.get(var1)).isUpdatedSystemApp;
      } finally {
         ;
      }

      return var2;
   }

   class 1 implements PackageMonitorReceiver.PackageStatusListener {

      1() {}

      public void onPackageAdded(String var1) {
         Object var2 = PackageInfoCache.this.mCache.remove(var1);
         PackageInfoCache.this.updatePackageInfo(var1);
      }

      public void onPackageAvailabilityChanged(String[] var1, boolean var2) {
         String[] var3 = var1;
         int var4 = var1.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            Object var7 = PackageInfoCache.this.mCache.remove(var6);
            if(var2) {
               PackageInfoCache.this.updatePackageInfo(var6);
            }
         }

      }

      public void onPackageChanged(String var1) {
         Object var2 = PackageInfoCache.this.mCache.remove(var1);
         PackageInfoCache.this.updatePackageInfo(var1);
      }

      public void onPackageRemoved(String var1, boolean var2) {
         Object var3 = PackageInfoCache.this.mCache.remove(var1);
      }
   }

   private static final class PackageInfo {

      final boolean canLaunch;
      final boolean isSystemApp;
      final boolean isUpdatedSystemApp;
      final int versionCode;
      final String versionName;


      PackageInfo(int var1, String var2, boolean var3, boolean var4, boolean var5) {
         this.versionCode = var1;
         this.versionName = var2;
         this.isSystemApp = var3;
         this.isUpdatedSystemApp = var4;
         this.canLaunch = var5;
      }
   }
}
