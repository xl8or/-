package com.samsung.android.bt.app.sap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;
import com.samsung.android.bt.app.sap.BluetoothSAPDialog;

public class BluetoothSAPNotification extends BroadcastReceiver {

   public static final int SAP_NOTIFICATION_ID = 1000;
   private static final String TAG = "BluetoothSAPNotification";
   private Context ctx;
   private BluetoothDevice device;
   private String mAddr;
   private int mConnected;
   private String mDeviceName;


   public BluetoothSAPNotification() {}

   private void updateNotification() {
      int var1 = Log.v("BluetoothSAPNotification", "updateNotification");
      Context var2 = this.ctx;
      Intent var3 = new Intent(var2, BluetoothSAPDialog.class);
      Intent var4 = var3.setAction("broadcom.android.bluetooth.intent.action.BT_SERVICE_CONNECTION");
      Intent var5 = var3.putExtra("SVC_NAME", "bluetooth_sap");
      String var6 = this.ctx.getResources().getString(2131232626);
      long var7 = System.currentTimeMillis();
      Notification var9 = new Notification(17301632, var6, var7);
      PendingIntent var10 = PendingIntent.getActivity(this.ctx, 0, var3, 134217728);
      Context var11 = this.ctx;
      String var12 = this.ctx.getResources().getString(2131232628);
      Resources var13 = this.ctx.getResources();
      Object[] var14 = new Object[1];
      String var15;
      if(this.device != null) {
         var15 = this.device.getName();
      } else {
         var15 = this.ctx.getResources().getString(2131231013);
      }

      var14[0] = var15;
      String var16 = var13.getString(2131232629, var14);
      var9.setLatestEventInfo(var11, var12, var16, var10);
      int var17 = var9.flags | 8;
      var9.flags = var17;
      int var18 = var9.flags | 32;
      var9.flags = var18;
      int var19 = var9.flags | 2;
      var9.flags = var19;
      int var20 = var9.defaults | 1;
      var9.defaults = var20;
      Context var21 = this.ctx;
      Context var22 = this.ctx;
      ((NotificationManager)var21.getSystemService("notification")).notify(1000, var9);
   }

   public void destroyNotification() {
      int var1 = Log.v("BluetoothSAPNotification", "destroyNotification");
      Context var2 = this.ctx;
      Context var3 = this.ctx;
      ((NotificationManager)var2.getSystemService("notification")).cancel(1000);
   }

   public void onReceive(Context var1, Intent var2) {
      int var3 = Log.v("BluetoothSAPNotification", "onReceive");
      String var4 = var2.getAction();
      this.ctx = var1;
      if(var4.equals("broadcom.android.bluetooth.intent.action.BT_SERVICE_CONNECTION")) {
         String var5 = var2.getStringExtra("SVC_NAME");
         byte var6 = var2.getByteExtra("broadcom.android.bluetooth.intent.BLUETOOTH_SERVICE_CONNECTED", (byte)0);
         if(var5.equals("bluetooth_sap")) {
            if(var6 == 1) {
               BluetoothDevice var7 = (BluetoothDevice)var2.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
               this.device = var7;
               this.updateNotification();
            } else if(var6 == 0) {
               this.destroyNotification();
               Context var8 = this.ctx;
               String var9 = this.ctx.getString(2131232627);
               Toast.makeText(var8, var9, 0).show();
            }
         }
      } else if(var4.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
         int var10 = var2.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
         if(13 == var10) {
            this.destroyNotification();
         }
      }
   }
}
