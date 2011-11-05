package com.sonyericsson.email.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.email.Email;

public class MasterResetReceiver extends BroadcastReceiver {

   public MasterResetReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      Email.masterReset(var1);
   }
}
