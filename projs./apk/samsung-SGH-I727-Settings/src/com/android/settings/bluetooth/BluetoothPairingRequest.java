package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.settings.bluetooth.BluetoothPairingDialog;
import com.android.settings.bluetooth.LocalBluetoothManager;

public class BluetoothPairingRequest extends BroadcastReceiver {

   public static final int NOTIFICATION_ID = 17301632;


   public BluetoothPairingRequest() {}

   public void onReceive(Context var1, Intent var2) {
      if(var2.getAction().equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
         LocalBluetoothManager var3 = LocalBluetoothManager.getInstance(var1);
         BluetoothDevice var4 = (BluetoothDevice)var2.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
         int var5 = var2.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE);
         Intent var6 = new Intent();
         var6.setClass(var1, BluetoothPairingDialog.class);
         var6.putExtra("android.bluetooth.device.extra.DEVICE", var4);
         var6.putExtra("android.bluetooth.device.extra.PAIRING_VARIANT", var5);
         if(var5 == 2 || var5 == 4) {
            int var10 = var2.getIntExtra("android.bluetooth.device.extra.PASSKEY", Integer.MIN_VALUE);
            var6.putExtra("android.bluetooth.device.extra.PASSKEY", var10);
         }

         Intent var12 = var6.setAction("android.bluetooth.device.action.PAIRING_REQUEST");
         Intent var13 = var6.setFlags(268435456);
         var1.startActivity(var6);
      }
   }
}
