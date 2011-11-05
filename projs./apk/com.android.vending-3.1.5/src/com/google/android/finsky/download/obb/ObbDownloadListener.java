package com.google.android.finsky.download.obb;

import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Notifier;
import java.io.File;
import java.io.FilenameFilter;

public class ObbDownloadListener implements Download.DownloadListener {

   private Download mNext;
   private Notifier mNotifier;
   private Obb mObb;
   private Download mObbDownload;


   public ObbDownloadListener(Download var1, Obb var2, Download var3, Notifier var4) {
      this.mObb = var2;
      this.mNext = var3;
      this.mObbDownload = var1;
      this.mNotifier = var4;
   }

   private void cancelNext() {
      Obb var1 = this.mObb;
      ObbState var2 = ObbState.NOT_ON_STORAGE;
      var1.setState(var2);
      this.mObb.delete();
      if(this.mNext != null) {
         if(!this.mNext.isCompleted()) {
            this.mNext.cancel();
         }
      }
   }

   private void deleteOlderObbs() {
      File var1 = this.mObb.getFile().getParentFile();
      String var2;
      if(this.mObb.isPatch()) {
         var2 = "patch";
      } else {
         var2 = "main";
      }

      int var3 = this.mObb.getVersionCode();
      ObbDownloadListener.1 var4 = new ObbDownloadListener.1(var2, var3);
      File[] var5 = var1.listFiles(var4);
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         File var8 = var5[var7];
         Object[] var9 = new Object[1];
         String var10 = var8.getName();
         var9[0] = var10;
         FinskyLog.d("Deleting older obb %s", var9);
         boolean var11 = var8.delete();
      }

   }

   public void onCancel() {
      this.cancelNext();
   }

   public void onComplete(Download var1) {
      if(!this.mObb.finalizeTempFile()) {
         Object[] var2 = new Object[0];
         FinskyLog.e("Could not rename from obb temp file to real file name", var2);
         Notifier var3 = this.mNotifier;
         String var4 = this.mObbDownload.getTitle();
         var3.showDownloadErrorMessage(var4, (String)null);
         this.cancelNext();
      } else {
         Obb var5 = this.mObb;
         ObbState var6 = ObbState.DOWNLOADED;
         var5.setState(var6);
         this.deleteOlderObbs();
      }
   }

   public boolean onError(int var1) {
      this.cancelNext();
      return false;
   }

   public void onNotificationClicked() {}

   public void onProgress(DownloadProgress var1) {}

   public void onStart() {
      Obb var1 = this.mObb;
      ObbState var2 = ObbState.DOWNLOADING;
      var1.setState(var2);
      Obb var3 = this.mObb;
      String var4 = this.mObbDownload.getContentUri().toString();
      var3.setContentUri(var4);
   }

   class 1 implements FilenameFilter {

      // $FF: synthetic field
      final String val$fileNamePrefix;
      // $FF: synthetic field
      final int val$versionCode;


      1(String var2, int var3) {
         this.val$fileNamePrefix = var2;
         this.val$versionCode = var3;
      }

      public boolean accept(File var1, String var2) {
         boolean var3 = true;

         label19: {
            int var7;
            int var8;
            try {
               String[] var4 = var2.split("\\.");
               String var5 = var4[0];
               String var6 = this.val$fileNamePrefix;
               if(!var5.equals(var6)) {
                  break label19;
               }

               var7 = Integer.parseInt(var4[1]);
               var8 = this.val$versionCode;
            } catch (Exception var11) {
               Object[] var10 = new Object[]{var2};
               FinskyLog.e("Error parsing filename %s", var10);
               var3 = false;
               return var3;
            }

            if(var7 < var8) {
               return var3;
            }
         }

         var3 = false;
         return var3;
      }
   }
}
