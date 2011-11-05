package com.broadcom.bt.app.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import com.android.settings.bluetooth.LocalBluetoothManager;

public class BluetoothStateActivity extends Activity {

   private static final String TAG = "BleutoothStateActivity";
   private int bluetoothState;
   private LocalBluetoothManager mLocalManager;
   private ProgressDialog mProgressDialog;
   private final BroadcastReceiver mReceiver;


   public BluetoothStateActivity() {
      BluetoothStateActivity.3 var1 = new BluetoothStateActivity.3();
      this.mReceiver = var1;
   }

   private void onBluetoothStateChanged(int var1) {
      String var2 = "OnBluetoothStateChanged()" + var1;
      int var3 = Log.i("BleutoothStateActivity", var2);
      if(var1 == 12) {
         this.setResult(-1);
         this.finish();
      } else if(var1 == 10) {
         this.setResult(0);
         this.finish();
      } else if(var1 == 11) {
         ProgressDialog var4 = new ProgressDialog(this);
         this.mProgressDialog = var4;
         ProgressDialog var5 = this.mProgressDialog;
         String var6 = this.getString(2131231062);
         var5.setMessage(var6);
         this.mProgressDialog.setIndeterminate((boolean)1);
         this.mProgressDialog.show();
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      int var2 = Log.i("BleutoothStateActivity", "onCreate()");
      boolean var3 = this.requestWindowFeature(1);
      LocalBluetoothManager var4 = LocalBluetoothManager.getInstance(this);
      this.mLocalManager = var4;
      if(this.mLocalManager == null) {
         this.finish();
      } else {
         int var5 = this.mLocalManager.getBluetoothState();
         this.bluetoothState = var5;
         if(this.bluetoothState == 10) {
            int var6 = Log.i("BleutoothStateActivity", "Bluetooth is Off");
            Builder var7 = (new Builder(this)).setTitle(2131231442);
            String var8 = this.getString(2131232566);
            Builder var9 = var7.setMessage(var8);
            String var10 = this.getString(2131232562);
            BluetoothStateActivity.2 var11 = new BluetoothStateActivity.2();
            Builder var12 = var9.setPositiveButton(var10, var11);
            String var13 = this.getString(2131232563);
            BluetoothStateActivity.1 var14 = new BluetoothStateActivity.1();
            AlertDialog var15 = var12.setNegativeButton(var13, var14).show();
         } else if(this.bluetoothState == 12) {
            this.setResult(-1);
            this.finish();
         }
      }
   }

   protected void onDestroy() {
      super.onDestroy();
      int var1 = Log.i("BleutoothStateActivity", "OnDestroy()");
   }

   protected void onPause() {
      super.onPause();
      int var1 = Log.i("BleutoothStateActivity", "OnPause()");
      BroadcastReceiver var2 = this.mReceiver;
      this.unregisterReceiver(var2);
   }

   protected void onResume() {
      super.onResume();
      BroadcastReceiver var1 = this.mReceiver;
      IntentFilter var2 = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
      this.registerReceiver(var1, var2);
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         BluetoothStateActivity.this.mLocalManager.setBluetoothEnabled((boolean)1);
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         BluetoothStateActivity.this.finish();
      }
   }

   class 3 extends BroadcastReceiver {

      3() {}

      public void onReceive(Context var1, Intent var2) {
         if(var2.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
            BluetoothStateActivity var3 = BluetoothStateActivity.this;
            int var4 = BluetoothStateActivity.this.mLocalManager.getBluetoothState();
            var3.onBluetoothStateChanged(var4);
         }
      }
   }
}
