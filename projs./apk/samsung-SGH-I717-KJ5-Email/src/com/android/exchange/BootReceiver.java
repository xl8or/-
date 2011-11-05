package com.android.exchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.email.ExchangeUtils;
import com.android.exchange.security.ode.ODEService;

public class BootReceiver extends BroadcastReceiver {

   public BootReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      int var3 = Log.d("Exchange", "BootReceiver onReceive");
      ExchangeUtils.startExchangeService(var1);
      if(!ODEService.ODEErrorExist(var1)) {
         Intent var4 = new Intent(var1, ODEService.class);
         Intent var5 = var4.putExtra("startProc", "powerup_recovery");
         var1.startService(var4);
      }
   }
}
