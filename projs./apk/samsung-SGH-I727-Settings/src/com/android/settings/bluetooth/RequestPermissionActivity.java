package com.android.settings.bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import com.android.settings.bluetooth.LocalBluetoothManager;
import com.android.settings.bluetooth.RequestPermissionHelperActivity;

public class RequestPermissionActivity extends Activity implements OnClickListener {

   private static final int MAX_DISCOVERABLE_TIMEOUT = 300;
   private static final int REQUEST_CODE_START_BT = 1;
   static final int RESULT_BT_STARTING_OR_STARTED = 64536;
   private static final String TAG = "RequestPermissionActivity";
   private AlertDialog mDialog = null;
   private boolean mEnableOnly = 0;
   private LocalBluetoothManager mLocalManager;
   private boolean mNeededToEnableBluetooth;
   private BroadcastReceiver mReceiver;
   private int mTimeout = 120;
   private boolean mUserConfirmed = 0;


   public RequestPermissionActivity() {
      RequestPermissionActivity.1 var1 = new RequestPermissionActivity.1();
      this.mReceiver = var1;
   }

   private void createDialog() {
      Builder var1 = new Builder(this);
      Builder var2 = var1.setIcon(17301659);
      String var3 = this.getString(2131230820);
      var1.setTitle(var3);
      if(this.mNeededToEnableBluetooth) {
         String var5 = this.getString(2131230824);
         var1.setMessage(var5);
         Builder var7 = var1.setCancelable((boolean)0);
      } else {
         Object[] var9 = new Object[1];
         Integer var10 = Integer.valueOf(this.mTimeout);
         var9[0] = var10;
         String var11 = this.getString(2131230823, var9);
         var1.setMessage(var11);
         String var13 = this.getString(2131230722);
         var1.setPositiveButton(var13, this);
         String var15 = this.getString(2131230723);
         var1.setNegativeButton(var15, this);
      }

      AlertDialog var8 = var1.create();
      this.mDialog = var8;
      this.mDialog.show();
   }

   private boolean parseIntent() {
      Intent var1 = this.getIntent();
      boolean var4;
      if(var1 != null && var1.getAction().equals("android.bluetooth.adapter.action.REQUEST_ENABLE")) {
         this.mEnableOnly = (boolean)1;
      } else {
         if(var1 == null || !var1.getAction().equals("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE")) {
            int var10 = Log.e("RequestPermissionActivity", "Error: this activity may be started only with intent android.bluetooth.adapter.action.REQUEST_ENABLE or android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
            this.setResult(0);
            var4 = true;
            return var4;
         }

         int var5 = var1.getIntExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 120);
         this.mTimeout = var5;
         StringBuilder var6 = (new StringBuilder()).append("Timeout = ");
         int var7 = this.mTimeout;
         String var8 = var6.append(var7).toString();
         int var9 = Log.e("RequestPermissionActivity", var8);
         if(this.mTimeout > 300) {
            this.mTimeout = 300;
         } else if(this.mTimeout <= 0) {
            this.mTimeout = 120;
         }
      }

      LocalBluetoothManager var2 = LocalBluetoothManager.getInstance(this);
      this.mLocalManager = var2;
      if(this.mLocalManager == null) {
         int var3 = Log.e("RequestPermissionActivity", "Error: there\'s a problem starting bluetooth");
         this.setResult(0);
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private void persistDiscoverableEndTimestamp(long var1) {
      Editor var3 = this.mLocalManager.getSharedPreferences().edit();
      var3.putLong("discoverable_end_timestamp", var1);
      var3.apply();
   }

   private void proceedAndFinish() {
      int var1;
      if(this.mEnableOnly) {
         var1 = -1;
      } else {
         BluetoothAdapter var2 = this.mLocalManager.getBluetoothAdapter();
         int var3 = this.mTimeout;
         if(var2.setScanMode(23, var3)) {
            BluetoothAdapter var4 = this.mLocalManager.getBluetoothAdapter();
            int var5 = this.mTimeout;
            var4.setDiscoverableTimeout(var5);
            long var6 = System.currentTimeMillis();
            long var8 = (long)(this.mTimeout * 1000);
            long var10 = var6 + var8;
            this.persistDiscoverableEndTimestamp(var10);
            var1 = this.mTimeout;
            if(var1 < 1) {
               var1 = 1;
            }
         } else {
            var1 = 0;
         }
      }

      if(this.mDialog != null) {
         this.mDialog.dismiss();
      }

      this.setResult(var1);
      this.finish();
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 != 1) {
         String var4 = "Unexpected onActivityResult " + var1 + " " + var2;
         int var5 = Log.e("RequestPermissionActivity", var4);
         this.setResult(0);
         this.finish();
      } else if(var2 != '\ufc18') {
         this.setResult(var2);
         this.finish();
      } else {
         this.mUserConfirmed = (boolean)1;
         if(this.mLocalManager.getBluetoothState() == 12) {
            this.proceedAndFinish();
         } else {
            this.createDialog();
         }
      }
   }

   public void onBackPressed() {
      this.setResult(0);
      super.onBackPressed();
   }

   public void onClick(DialogInterface var1, int var2) {
      switch(var2) {
      case -2:
         this.setResult(0);
         this.finish();
         return;
      case -1:
         this.proceedAndFinish();
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(this.parseIntent()) {
         this.finish();
      } else {
         switch(this.mLocalManager.getBluetoothState()) {
         case 10:
         case 11:
         case 13:
            BroadcastReceiver var2 = this.mReceiver;
            IntentFilter var3 = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
            this.registerReceiver(var2, var3);
            Intent var5 = new Intent();
            var5.setClass(this, RequestPermissionHelperActivity.class);
            if(this.mEnableOnly) {
               Intent var7 = var5.setAction("com.android.settings.bluetooth.ACTION_INTERNAL_REQUEST_BT_ON");
            } else {
               Intent var8 = var5.setAction("com.android.settings.bluetooth.ACTION_INTERNAL_REQUEST_BT_ON_AND_DISCOVERABLE");
            }

            this.startActivityForResult(var5, 1);
            this.mNeededToEnableBluetooth = (boolean)1;
            return;
         case 12:
            if(this.mEnableOnly) {
               this.proceedAndFinish();
               return;
            }

            this.createDialog();
            return;
         default:
         }
      }
   }

   protected void onDestroy() {
      super.onDestroy();
      if(this.mNeededToEnableBluetooth) {
         BroadcastReceiver var1 = this.mReceiver;
         this.unregisterReceiver(var1);
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         if(var2 != null) {
            if(RequestPermissionActivity.this.mNeededToEnableBluetooth) {
               String var3 = var2.getAction();
               if("android.bluetooth.adapter.action.STATE_CHANGED".equals(var3)) {
                  if(var2.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE) == 12) {
                     if(RequestPermissionActivity.this.mUserConfirmed) {
                        RequestPermissionActivity.this.proceedAndFinish();
                     }
                  }
               }
            }
         }
      }
   }
}
