package com.android.exchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.exchange.SyncManager;

public class MailboxAlarmReceiver extends BroadcastReceiver {

   public MailboxAlarmReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      long var3 = var2.getLongExtra("mailbox", 65535L);
      if(var3 == 0L) {
         Intent var5 = new Intent(var1, SyncManager.class);
         var1.startService(var5);
      } else {
         StringBuilder var7 = (new StringBuilder()).append("Alarm received for: ");
         String var8 = SyncManager.alarmOwner(var3);
         SyncManager.log(var7.append(var8).toString());
         SyncManager.alert(var1, var3);
      }
   }
}
