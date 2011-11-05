package com.android.exchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.exchange.SyncManager;

public class MailboxAlarmReceiver extends BroadcastReceiver {

   public MailboxAlarmReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      long var3 = var2.getLongExtra("mailbox", 65535L);
      StringBuilder var5 = (new StringBuilder()).append("Alarm received for: ");
      String var6 = SyncManager.alarmOwner(var3);
      SyncManager.log(var5.append(var6).toString());
      SyncManager.alert(var1, var3);
   }
}
