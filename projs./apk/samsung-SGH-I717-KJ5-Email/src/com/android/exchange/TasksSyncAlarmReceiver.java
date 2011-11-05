package com.android.exchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TasksSyncAlarmReceiver extends BroadcastReceiver {

   private static String TAG = "TaskSyncAlarmReceiver";
   final String[] MAILBOX_DATA_PROJECTION;


   public TasksSyncAlarmReceiver() {
      String[] var1 = new String[]{"mailboxKey"};
      this.MAILBOX_DATA_PROJECTION = var1;
   }

   private void handleReceive(Context param1) {
      // $FF: Couldn't be decompiled
   }

   public void onReceive(Context var1, Intent var2) {
      int var3 = Log.v(TAG, "onReceive");
      TasksSyncAlarmReceiver.1 var4 = new TasksSyncAlarmReceiver.1(var1);
      (new Thread(var4)).start();
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final Context val$context;


      1(Context var2) {
         this.val$context = var2;
      }

      public void run() {
         TasksSyncAlarmReceiver var1 = TasksSyncAlarmReceiver.this;
         Context var2 = this.val$context;
         var1.handleReceive(var2);
      }
   }
}
