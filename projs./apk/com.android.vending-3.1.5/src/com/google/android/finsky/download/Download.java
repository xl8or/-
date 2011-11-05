package com.google.android.finsky.download;

import android.net.Uri;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.local.AutoUpdateState;

public interface Download {

   void addListener(Download.DownloadListener var1);

   void cancel();

   long getBytesCompleted();

   Uri getContentUri();

   Download.PackageProperties getPackageProperties();

   DownloadProgress getProgress();

   String getTitle();

   String getUrl();

   boolean isCompleted();

   void removeListener(Download.DownloadListener var1);

   public static enum DownloadState {

      // $FF: synthetic field
      private static final Download.DownloadState[] $VALUES;
      CANCELLED("CANCELLED", 4),
      DOWNLOADING("DOWNLOADING", 2),
      ERROR("ERROR", 5),
      QUEUED("QUEUED", 1),
      SUCCESS("SUCCESS", 3),
      UNQUEUED("UNQUEUED", 0);


      static {
         Download.DownloadState[] var0 = new Download.DownloadState[6];
         Download.DownloadState var1 = UNQUEUED;
         var0[0] = var1;
         Download.DownloadState var2 = QUEUED;
         var0[1] = var2;
         Download.DownloadState var3 = DOWNLOADING;
         var0[2] = var3;
         Download.DownloadState var4 = SUCCESS;
         var0[3] = var4;
         Download.DownloadState var5 = CANCELLED;
         var0[4] = var5;
         Download.DownloadState var6 = ERROR;
         var0[5] = var6;
         $VALUES = var0;
      }

      private DownloadState(String var1, int var2) {}
   }

   public interface DownloadListener {

      void onCancel();

      void onComplete(Download var1);

      boolean onError(int var1);

      void onNotificationClicked();

      void onProgress(DownloadProgress var1);

      void onStart();
   }

   public static class PackageProperties {

      public final String account;
      public final String assetId;
      public final AutoUpdateState autoUpdateState;
      public final boolean forwardLocked;
      public final Obb mainObb;
      public final String packageName;
      public final Obb patchObb;
      public final Long refundPeriodEndTime;
      public final String signature;
      public final long size;
      public final int versionCode;


      public PackageProperties(String var1, AutoUpdateState var2, String var3, int var4, String var5, boolean var6, long var7, String var9, Long var10, Obb var11, Obb var12) {
         this.packageName = var1;
         this.autoUpdateState = var2;
         this.account = var3;
         this.versionCode = var4;
         this.assetId = var5;
         this.forwardLocked = var6;
         this.size = var7;
         this.signature = var9;
         this.refundPeriodEndTime = var10;
         this.mainObb = var11;
         this.patchObb = var12;
      }
   }
}
