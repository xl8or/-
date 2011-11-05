package com.android.email;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.email.BluetoothMAPService;

public class BluetoothMAPReceiver extends BroadcastReceiver {

   private static final String TAG = "BluetoothMAPReceiver";


   public BluetoothMAPReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      String var3 = var2.getAction();
      String var4 = "Received action " + var3;
      int var5 = Log.d("BluetoothMAPReceiver", var4);
      if(var3.equals("com.broadcom.bt.service.map.DS_DISCOVER")) {
         BluetoothMAPService.registerMAPProvider(var1);
      } else if("broadcom.bt.intent.action.BT_SVC_STATE_CHANGE".equals(var3)) {
         String var6 = var2.getStringExtra("bt_svc_name");
         int var7 = var2.getIntExtra("bt_svc_state", -1);
         if("bluetooth_map".equals(var6)) {
            if(var7 == 1) {
               BluetoothMAPService.unregisterMAPProvider(var1);
            }
         }
      }
   }
}
