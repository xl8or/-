package com.google.android.finsky.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadManagerConstants;
import com.google.android.finsky.download.DownloadQueueImpl;
import com.google.android.finsky.download.InternalDownload;
import com.google.android.finsky.utils.FinskyLog;

public class DownloadBroadcastReceiver extends BroadcastReceiver {

   private static DownloadQueueImpl sDownloadQueueImpl;


   public DownloadBroadcastReceiver() {}

   private int getHttpStatusForUri(Uri var1) {
      Cursor var2 = sDownloadQueueImpl.getDownloadManager().queryStatus(var1);
      int var3;
      if(var2 != null && var2.getCount() == 1) {
         boolean var4 = var2.moveToFirst();
         var3 = var2.getInt(0);
         var2.close();
      } else {
         var3 = -1;
      }

      return var3;
   }

   public static void initialize(DownloadQueueImpl var0) {
      sDownloadQueueImpl = var0;
   }

   public void onReceive(Context var1, Intent var2) {
      Object[] var3 = new Object[0];
      FinskyLog.d("Intent received at DownloadBroadcastReceiver", var3);
      Uri var4 = var2.getData();
      String var5 = var2.getAction();
      if(sDownloadQueueImpl.getExisting(var4) == null) {
         Object[] var6 = new Object[0];
         FinskyLog.d("DownloadBroadcastReceiver could not find download in queue.", var6);
      } else {
         DownloadBroadcastReceiver.1 var7 = new DownloadBroadcastReceiver.1(var4, var5);
         Uri[] var8 = new Uri[]{var4};
         var7.execute(var8);
      }
   }

   class 1 extends AsyncTask<Uri, Uri, Integer> {

      // $FF: synthetic field
      final String val$action;
      // $FF: synthetic field
      final Uri val$contentUri;


      1(Uri var2, String var3) {
         this.val$contentUri = var2;
         this.val$action = var3;
      }

      protected Integer doInBackground(Uri ... var1) {
         int var2;
         if(var1.length < 1) {
            var2 = -1;
         } else {
            DownloadBroadcastReceiver var3 = DownloadBroadcastReceiver.this;
            Uri var4 = var1[0];
            var2 = var3.getHttpStatusForUri(var4);
         }

         return Integer.valueOf(var2);
      }

      protected void onPostExecute(Integer var1) {
         DownloadQueueImpl var2 = DownloadBroadcastReceiver.sDownloadQueueImpl;
         Uri var3 = this.val$contentUri;
         InternalDownload var4 = var2.getExisting(var3);
         if(var4 == null) {
            Object[] var5 = new Object[0];
            FinskyLog.d("DownloadBroadcastReceiver could not find download in queue.", var5);
         } else {
            if(var1.intValue() != -1) {
               int var6 = var1.intValue();
               var4.setHttpStatus(var6);
            } else {
               Object[] var7 = new Object[0];
               FinskyLog.e("DownloadBroadcastReceiver received invalid HTTP status of -1", var7);
            }

            if(this.val$action.equals("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED")) {
               var4.onNotificationClicked();
            } else if(this.val$action.equals("android.intent.action.DOWNLOAD_COMPLETED")) {
               if(DownloadManagerConstants.isStatusSuccess(var1.intValue())) {
                  Download.DownloadState var8 = Download.DownloadState.SUCCESS;
                  var4.setState(var8);
               } else {
                  Download.DownloadState var9 = Download.DownloadState.ERROR;
                  var4.setState(var9);
               }
            } else {
               Object[] var10 = new Object[0];
               FinskyLog.wtf("Invalid DownloadBroadcastReceiver intent", var10);
            }
         }
      }
   }
}
