package com.android.email;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.GateConfig;
import android.util.Log;

public class EmailGateReceiver extends BroadcastReceiver {

   public EmailGateReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      StringBuilder var3 = (new StringBuilder()).append("EmailGateReceiver. received GATE intent. action = ");
      String var4 = var2.getAction();
      String var5 = var3.append(var4).toString();
      int var6 = Log.i("GATE", var5);
      if(var2.getAction().equals("com.sec.android.gate.GATE")) {
         GateConfig.setGateEnabled(var2.getBooleanExtra("ENABLED", (boolean)0));
         StringBuilder var7 = (new StringBuilder()).append("EmailGateReceiver. received GATE intent. enabled = ");
         boolean var8 = GateConfig.isGateEnabled();
         String var9 = var7.append(var8).toString();
         int var10 = Log.i("GATE", var9);
      } else if(var2.getAction().equals("com.sec.android.gate.LCDTEXT")) {
         GateConfig.setGateLcdtextEnabled(var2.getBooleanExtra("ENABLED", (boolean)0));
         StringBuilder var11 = (new StringBuilder()).append("EmailGateReceiver. received LCDTEXT intent. enabled = ");
         boolean var12 = GateConfig.isGateLcdtextEnabled();
         String var13 = var11.append(var12).toString();
         int var14 = Log.i("GATE", var13);
      }
   }
}
