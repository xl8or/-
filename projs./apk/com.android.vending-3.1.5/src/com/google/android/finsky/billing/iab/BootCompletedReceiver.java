package com.google.android.finsky.billing.iab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.billing.iab.PendingNotificationsService;

public class BootCompletedReceiver extends BroadcastReceiver {

   public BootCompletedReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      if(AccountHandler.getAccounts(var1).length != 0) {
         FinskyApp var3 = FinskyApp.get();
         BootCompletedReceiver.1 var4 = new BootCompletedReceiver.1(var1);
         var3.clearCacheAsync(var4);
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final Context val$context;


      1(Context var2) {
         this.val$context = var2;
      }

      public void run() {
         String var1 = PendingNotificationsService.ACTION_RESTART_ALARM;
         Intent var2 = new Intent(var1);
         Context var3 = this.val$context;
         var2.setClass(var3, PendingNotificationsService.class);
         this.val$context.startService(var2);
      }
   }
}
