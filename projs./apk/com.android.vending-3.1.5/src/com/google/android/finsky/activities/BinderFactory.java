package com.google.android.finsky.activities;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.activities.DetailsSummaryAppsViewBinder;
import com.google.android.finsky.activities.DetailsSummaryMoviesViewBinder;
import com.google.android.finsky.activities.DetailsSummaryViewBinder;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.utils.PackageInfoCache;

public class BinderFactory {

   public BinderFactory() {}

   public static DetailsSummaryViewBinder getSummaryViewBinder(DfeToc var0, int var1) {
      Object var2;
      switch(var1) {
      case 3:
         PackageMonitorReceiver var3 = FinskyApp.get().getPackageMonitorReceiver();
         AssetStore var4 = FinskyInstance.get().getAssetStore();
         PackageInfoCache var5 = FinskyApp.get().getPackageInfoCache();
         var2 = new DetailsSummaryAppsViewBinder(var3, var4, var5, var0);
         break;
      case 4:
         PackageInfoCache var6 = FinskyApp.get().getPackageInfoCache();
         var2 = new DetailsSummaryMoviesViewBinder(var6, var0);
         break;
      default:
         var2 = new DetailsSummaryViewBinder(var0);
      }

      return (DetailsSummaryViewBinder)var2;
   }
}
