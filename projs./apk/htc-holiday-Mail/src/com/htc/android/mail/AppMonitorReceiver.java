package com.htc.android.mail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.htc.android.mail.MailShortcut;
import com.htc.android.mail.ll;

public class AppMonitorReceiver extends BroadcastReceiver {

   private static final String TAG = "AppMonitorReceiver";


   public AppMonitorReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      StringBuilder var3 = (new StringBuilder()).append("onReceive>").append(var2).append(",");
      String var4 = var2.getStringExtra("favorite_intent");
      String var5 = var3.append(var4).toString();
      ll.d("AppMonitorReceiver", var5);
      String var6 = var2.getAction();
      if("com.htc.launcher.action.ACTION_ITEM_ADDED".equals(var6)) {
         String var7 = var2.getStringExtra("favorite_intent");
         if(var7 == null) {
            ll.i("AppMonitorReceiver", "EXTRA_INTENT is null");
         } else if(var7.contains("component=com.htc.android.mail/.MailListTab")) {
            ll.d("AppMonitorReceiver", "update mail shortcut>");
            MailShortcut.updateMailAPshortcutInLine(var1, (boolean)0);
         }
      }
   }
}
