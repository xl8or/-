package com.google.android.finsky.adapters;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.LocalAsset;

public class DownloadProgressHelper {

   private static CharSequence sDownloadStatusFormatBytes;
   private static CharSequence sDownloadStatusFormatPercents;


   public DownloadProgressHelper() {}

   public static void configureDownloadProgressUi(Context var0, LocalAsset var1, Download var2, TextView var3, TextView var4, ProgressBar var5) {
      initializeStrings(var0);
      var4.setText(" ");
      var3.setText(" ");
      AssetState var6 = var1.getState();
      AssetState var7 = AssetState.INSTALLING;
      if(var6 == var7) {
         var5.setIndeterminate((boolean)1);
      } else if(var2 == null) {
         var5.setIndeterminate((boolean)1);
      } else {
         var3.setText(2131231034);
         long var8 = var2.getBytesCompleted();
         long var10 = var2.getPackageProperties().size;
         Download.PackageProperties var12 = var2.getPackageProperties();
         if(var12.mainObb != null) {
            long var13 = getObbCompleted(var12.mainObb);
            var8 += var13;
            long var15 = var12.mainObb.getSize();
            var10 += var15;
         }

         if(var12.patchObb != null) {
            long var17 = getObbCompleted(var12.patchObb);
            var8 += var17;
            long var19 = var12.patchObb.getSize();
            var10 += var19;
         }

         boolean var21;
         if(var8 > 0L && var10 > 0L && var8 <= var10) {
            var21 = true;
         } else {
            var21 = false;
         }

         if(!var21) {
            var5.setIndeterminate((boolean)1);
         } else {
            int var22 = (int)(100L * var8 / var10);
            var5.setIndeterminate((boolean)0);
            var5.setProgress(var22);
            CharSequence var23 = sDownloadStatusFormatPercents;
            CharSequence[] var24 = new CharSequence[1];
            String var25 = Integer.toString(var22);
            var24[0] = var25;
            CharSequence var26 = TextUtils.expandTemplate(var23, var24);
            var4.setText(var26);
            CharSequence var27 = sDownloadStatusFormatBytes;
            CharSequence[] var28 = new CharSequence[2];
            String var29 = Formatter.formatFileSize(var0, var8);
            var28[0] = var29;
            String var30 = Formatter.formatFileSize(var0, var10);
            var28[1] = var30;
            CharSequence var31 = TextUtils.expandTemplate(var27, var28);
            var3.setText(var31);
         }
      }
   }

   private static long getObbCompleted(Obb var0) {
      long var1 = 0L;
      ObbState var3 = var0.getState();
      ObbState var4 = ObbState.DOWNLOADING;
      if(var3 == var4) {
         DownloadQueue var5 = FinskyInstance.get().getDownloadQueue();
         String var6 = var0.getUrl();
         Download var7 = var5.get(var6);
         if(var7 != null) {
            var1 = var7.getBytesCompleted();
         }
      } else {
         ObbState var8 = ObbState.DOWNLOADED;
         if(var3 == var8) {
            var1 = var0.getSize();
         }
      }

      return var1;
   }

   private static void initializeStrings(Context var0) {
      if(sDownloadStatusFormatPercents == null) {
         sDownloadStatusFormatPercents = Html.fromHtml(var0.getString(2131231025));
      }

      if(sDownloadStatusFormatBytes == null) {
         sDownloadStatusFormatBytes = Html.fromHtml(var0.getString(2131231026));
      }
   }
}
