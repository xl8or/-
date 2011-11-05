package com.android.email.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.email.service.MailService;

public final class MailPushReceiver extends BroadcastReceiver {

   static final String MIMETYPE_WAP_EMN = "application/vnd.wap.emn+wbxml";


   public MailPushReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      String var3 = var2.getAction();
      if("android.provider.Telephony.WAP_PUSH_RECEIVED".equals(var3)) {
         String var4 = var2.getType();
         if("application/vnd.wap.emn+wbxml".equals(var4)) {
            MailService.actionGetNewMail(var1, var2);
         }
      }
   }
}
