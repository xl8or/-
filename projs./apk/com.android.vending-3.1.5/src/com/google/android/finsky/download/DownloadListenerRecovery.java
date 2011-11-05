package com.google.android.finsky.download;

import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadManager;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.AssetStoreUpdater;

public class DownloadListenerRecovery {

   public static final DownloadListenerRecovery.DownloadListenerFilter[] LISTENER_FILTERS;


   static {
      DownloadListenerRecovery.DownloadListenerFilter[] var0 = new DownloadListenerRecovery.DownloadListenerFilter[4];
      AssetStore var1 = FinskyInstance.get().getAssetStore();
      AssetStoreUpdater.AssetStoreDownloadListenerFilter var2 = new AssetStoreUpdater.AssetStoreDownloadListenerFilter(var1);
      var0[0] = var2;
      Installer.DownloadInstallListenerFilter var3 = new Installer.DownloadInstallListenerFilter();
      var0[1] = var3;
      DownloadListenerRecovery.DownloadListenerFilter var4 = FinskyInstance.get().getDownloadQueue().getDownloadListenerFilter();
      var0[2] = var4;
      DownloadListenerRecovery.DownloadListenerFilter var5 = ObbFactory.getObbDownloadListenerFilter();
      var0[3] = var5;
      LISTENER_FILTERS = var0;
   }

   public DownloadListenerRecovery() {}

   public static void recoverListeners(Installer var0, DownloadManager var1, Download var2, String var3, int var4, Obb var5) {
      DownloadListenerRecovery.DownloadListenerFilter[] var6 = LISTENER_FILTERS;
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         DownloadListenerRecovery.DownloadListenerFilter var9 = var6[var8];
         Download.DownloadListener var16 = var9.filter(var0, var1, var2, var3, var4, var5);
         if(var16 != null) {
            var2.addListener(var16);
         }
      }

   }

   public interface DownloadListenerFilter {

      Download.DownloadListener filter(Installer var1, DownloadManager var2, Download var3, String var4, int var5, Obb var6);
   }
}
