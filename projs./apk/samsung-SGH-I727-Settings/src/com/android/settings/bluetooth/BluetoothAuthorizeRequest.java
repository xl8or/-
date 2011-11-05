package com.android.settings.bluetooth;

import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.android.settings.bluetooth.BluetoothAuthorizeDialog;
import com.android.settings.bluetooth.LocalBluetoothManager;

public class BluetoothAuthorizeRequest extends BroadcastReceiver {

   public static final int NOTIFICATION_ID = 17301632;
   private static final String TAG = "BluetoothAuthorizeRequest";
   private BluetoothDevice mDevice;
   private String mService;


   public BluetoothAuthorizeRequest() {}

   public void onReceive(Context var1, Intent var2) {
      String var3 = var2.getAction();
      DevicePolicyManager var4 = (DevicePolicyManager)var1.getSystemService("device_policy");
      BluetoothDevice var5 = (BluetoothDevice)var2.getParcelableExtra("broadcom.android.bluetooth.intent.DEVICE");
      this.mDevice = var5;
      String var6 = var2.getStringExtra("broadcom.android.bluetooth.intent.SERVICE");
      this.mService = var6;
      if(var4.getAllowBluetoothMode((ComponentName)null) == 1) {
         Toast.makeText(var1, 2131232561, 1).show();
         BluetoothDevice var7 = this.mDevice;
         String var8 = this.mService;
         var7.authorizeService(var8, (boolean)0, (boolean)0);
      } else if(var3.equals("broadcom.android.bluetooth.intent.action.AUTHORIZE_REQUEST")) {
         LocalBluetoothManager var10 = LocalBluetoothManager.getInstance(var1);
         BluetoothDevice var11 = (BluetoothDevice)var2.getParcelableExtra("broadcom.android.bluetooth.intent.DEVICE");
         int var12 = var2.getIntExtra("broadcom.android.bluetooth.intent.PAIRING_VARIANT", -16777216);
         String var13 = var2.getStringExtra("broadcom.android.bluetooth.intent.SERVICE");
         if(var13.equalsIgnoreCase("sap_weak_linkkey")) {
            int var14 = Log.v("BluetoothAuthorizeRequest", "Rejecting SAP connection authorization due to weak link key");
            var11.authorizeService(var13, (boolean)0, (boolean)0);
            Toast.makeText(var1, 2131232625, 1).show();
         } else {
            Intent var16 = new Intent();
            var16.setClass(var1, BluetoothAuthorizeDialog.class);
            var16.putExtra("broadcom.android.bluetooth.intent.DEVICE", var11);
            var16.putExtra("broadcom.android.bluetooth.intent.SERVICE", var13);
            Intent var20 = var16.setAction("broadcom.android.bluetooth.intent.action.AUTHORIZE_REQUEST");
            Intent var21 = var16.setFlags(268435456);
            var1.startActivity(var16);
         }
      }
   }
}
