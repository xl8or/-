package com.android.settings.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import com.android.settings.bluetooth.BluetoothRequestServiceDialog;
import com.android.settings.bluetooth.LocalBluetoothManager;

public class BluetoothRequestService extends BroadcastReceiver {

   public static final int NOTIFICATION_ID = 17301632;
   private static final String TAG = "BluetoothServiceRequest";
   private static final boolean V;
   private boolean mInForground = 0;
   private LocalBluetoothManager mLocalManager;
   private String mNotificationMsg;
   private Resources mResources;


   public BluetoothRequestService() {}

   public void onReceive(Context var1, Intent var2) {
      int var3 = Log.v("BluetoothServiceRequest", "onReceive");
      String var4 = var2.getAction();
      LocalBluetoothManager var5 = LocalBluetoothManager.getInstance(var1);
      this.mLocalManager = var5;
      Resources var6 = var1.getResources();
      this.mResources = var6;
      if(var2.getAction().equals("broadcom.android.bluetooth.intent.action.BT_SERVICE_ACCESS")) {
         String var7 = var2.getStringExtra("bt_svc_name");
         if("bluetooth_opp_service".equals(var7)) {
            int var8 = Log.v("BluetoothServiceRequest", "OPP access request handled by OPP application");
         } else {
            String var9 = this.mResources.getString(2131232542);
            this.mNotificationMsg = var9;
            var2.setClass(var1, BluetoothRequestServiceDialog.class);
            Intent var11 = var2.setAction("broadcom.android.bluetooth.intent.action.BT_SERVICE_ACCESS");
            Intent var12 = var2.setFlags(268435456);
            var1.startActivity(var2);
         }
      } else {
         StringBuilder var13 = (new StringBuilder()).append("Unknown intent action:  ");
         String var14 = var2.getAction();
         String var15 = var13.append(var14).toString();
         int var16 = Log.e("BluetoothServiceRequest", var15);
      }
   }
}
