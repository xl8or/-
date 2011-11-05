package com.android.exchange;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.email.provider.EmailContent;
import com.android.exchange.SyncManager;
import java.util.ArrayList;
import java.util.Iterator;

public class EmailSyncAlarmReceiver extends BroadcastReceiver {

   private static String TAG = "EmailSyncAlarm";
   final String[] MAILBOX_DATA_PROJECTION;


   public EmailSyncAlarmReceiver() {
      String[] var1 = new String[]{"mailboxKey"};
      this.MAILBOX_DATA_PROJECTION = var1;
   }

   private void handleReceive(Context var1) {
      ArrayList var2 = new ArrayList();
      ContentResolver var3 = var1.getContentResolver();
      int var4 = 0;
      String var5 = SyncManager.getEasAccountSelector();
      Uri var6 = EmailContent.Message.DELETED_CONTENT_URI;
      String[] var7 = this.MAILBOX_DATA_PROJECTION;
      Object var8 = null;
      Cursor var9 = var3.query(var6, var7, var5, (String[])null, (String)var8);

      try {
         while(var9.moveToNext()) {
            ++var4;
            long var10 = var9.getLong(0);
            Long var12 = Long.valueOf(var10);
            if(!var2.contains(var12)) {
               Long var13 = Long.valueOf(var10);
               var2.add(var13);
            }
         }
      } finally {
         var9.close();
      }

      Uri var16 = EmailContent.Message.UPDATED_CONTENT_URI;
      String[] var17 = this.MAILBOX_DATA_PROJECTION;
      Object var18 = null;
      Cursor var19 = var3.query(var16, var17, var5, (String[])null, (String)var18);

      try {
         while(var19.moveToNext()) {
            ++var4;
            long var20 = var19.getLong(0);
            Long var22 = Long.valueOf(var20);
            if(!var2.contains(var22)) {
               Long var23 = Long.valueOf(var20);
               var2.add(var23);
            }
         }
      } finally {
         var19.close();
      }

      Iterator var26 = var2.iterator();

      while(var26.hasNext()) {
         SyncManager.serviceRequest(((Long)var26.next()).longValue(), 0);
      }

      String var27 = TAG;
      StringBuilder var28 = (new StringBuilder()).append("Changed/Deleted messages: ").append(var4).append(", mailboxes: ");
      int var29 = var2.size();
      String var30 = var28.append(var29).toString();
      Log.v(var27, var30);
   }

   public void onReceive(Context var1, Intent var2) {
      int var3 = Log.v(TAG, "onReceive");
      EmailSyncAlarmReceiver.1 var4 = new EmailSyncAlarmReceiver.1(var1);
      (new Thread(var4)).start();
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final Context val$context;


      1(Context var2) {
         this.val$context = var2;
      }

      public void run() {
         EmailSyncAlarmReceiver var1 = EmailSyncAlarmReceiver.this;
         Context var2 = this.val$context;
         var1.handleReceive(var2);
      }
   }
}
