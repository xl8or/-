package com.broadcom.bt.app.settings;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.android.settings.bluetooth.LocalBluetoothManager;
import com.broadcom.bt.app.settings.BluetoothMouseConfirmationDialog;

public class BluetoothMouseConfirmationBroadcastReceiver extends BroadcastReceiver {

   public static final String HID_STATE_CHANGE = "com.broadcom.bt.service.hid.action.STATE_CHANGED";


   public BluetoothMouseConfirmationBroadcastReceiver() {}

   private void connected(Context var1, Intent var2) {
      LocalBluetoothManager var3 = LocalBluetoothManager.getInstance(var1);
      Object var4 = var3.getForegroundActivity();
      if(var4 == null) {
         var4 = var3.getContext();
         Intent var5 = var2.setFlags(268435456);
      }

      var2.setClass((Context)var4, BluetoothMouseConfirmationDialog.class);
      ((Context)var4).startActivity(var2);
   }

   private static String getLastConnectedMouseAddress(Context var0) {
      return PreferenceManager.getDefaultSharedPreferences(var0).getString("lastConnectedMouseAddress", (String)null);
   }

   public static void setLastConnectedMouseAddress(Context var0, String var1) {
      Editor var2 = PreferenceManager.getDefaultSharedPreferences(var0).edit();
      var2.putString("lastConnectedMouseAddress", var1);
      boolean var4 = var2.commit();
   }

   public void onReceive(Context var1, Intent var2) {
      BluetoothDevice var3 = (BluetoothDevice)var2.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
      if(var3.getBluetoothClass().getMajorDeviceClass() == 1280) {
         if(var3.getBluetoothClass().getDeviceClass() == 1408) {
            String var4 = var2.getAction();
            if("com.broadcom.bt.service.hid.action.STATE_CHANGED".equals(var4)) {
               if(var2.getIntExtra("com.broadcom.bt.service.hid.extra.STATE", -1) == 2) {
                  String var5 = var3.getAddress();
                  String var6 = getLastConnectedMouseAddress(var1);
                  if(!var5.equals(var6)) {
                     this.connected(var1, var2);
                  }
               }
            }
         }
      }
   }
}
