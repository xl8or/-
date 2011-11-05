package com.google.android.finsky.download.obb;

import android.os.Environment;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadListenerRecovery;
import com.google.android.finsky.download.DownloadManager;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbImpl;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import java.io.File;

public class ObbFactory {

   private static AssetStore sAssetStore;


   public ObbFactory() {}

   public static Obb create(boolean var0, String var1, int var2, String var3, long var4, ObbState var6) {
      AssetStore var7 = sAssetStore;
      return new ObbImpl(var7, var0, var1, var2, var3, var4, var6);
   }

   public static Obb createEmpty(boolean var0, String var1) {
      AssetStore var2 = sAssetStore;
      ObbState var3 = ObbState.NOT_APPLICABLE;
      return new ObbImpl(var2, var0, var1, -1, "", 65535L, var3);
   }

   public static DownloadListenerRecovery.DownloadListenerFilter getObbDownloadListenerFilter() {
      return new ObbFactory.ObbDownloadListenerFilter();
   }

   public static File getParentDirectory(String var0) {
      File var1 = Environment.getExternalStorageDirectory();
      File var2 = new File(var1, "Android");
      File var3 = new File(var2, "obb");
      return new File(var3, var0);
   }

   public static void initialize(AssetStore var0) {
      sAssetStore = var0;
   }

   static class ObbDownloadListenerFilter implements DownloadListenerRecovery.DownloadListenerFilter {

      ObbDownloadListenerFilter() {}

      public Download.DownloadListener filter(Installer var1, DownloadManager var2, Download var3, String var4, int var5, Obb var6) {
         ObbFactory.ObbDownloadListenerFilter.1 var7;
         if(var6 == null) {
            var7 = null;
         } else {
            var7 = new ObbFactory.ObbDownloadListenerFilter.1(var6, var4, var1);
         }

         return var7;
      }

      class 1 implements Download.DownloadListener {

         // $FF: synthetic field
         final Installer val$installer;
         // $FF: synthetic field
         final Obb val$obb;
         // $FF: synthetic field
         final String val$packageName;


         1(Obb var2, String var3, Installer var4) {
            this.val$obb = var2;
            this.val$packageName = var3;
            this.val$installer = var4;
         }

         private void delete() {
            Obb var1 = this.val$obb;
            ObbState var2 = ObbState.NOT_ON_STORAGE;
            var1.setState(var2);
            this.val$obb.delete();
         }

         public void onCancel() {
            this.delete();
         }

         public void onComplete(Download var1) {
            if(!this.val$obb.finalizeTempFile()) {
               Object[] var2 = new Object[0];
               FinskyLog.e("Could not rename from obb temp file to real file name", var2);
               this.delete();
            } else {
               Obb var3 = this.val$obb;
               ObbState var4 = ObbState.DOWNLOADED;
               var3.setState(var4);
               AssetStore var5 = ObbFactory.sAssetStore;
               String var6 = this.val$packageName;
               LocalAsset var7 = var5.getAsset(var6);
               var7.resetDownloadPendingState();
               this.val$installer.fetchAsset(var7);
            }
         }

         public boolean onError(int var1) {
            this.delete();
            return false;
         }

         public void onNotificationClicked() {}

         public void onProgress(DownloadProgress var1) {}

         public void onStart() {}
      }
   }
}
