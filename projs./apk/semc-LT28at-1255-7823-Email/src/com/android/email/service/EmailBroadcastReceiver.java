package com.android.email.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.email.service.EmailBroadcastProcessorService;

public class EmailBroadcastReceiver extends BroadcastReceiver {

   public EmailBroadcastReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      EmailBroadcastProcessorService.processBroadcastIntent(var1, var2);
   }
}
