package com.android.settings.bluetooth;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController.AlertParams;
import com.android.settings.bluetooth.LocalBluetoothManager;

public class RequestPermissionHelperActivity extends AlertActivity implements OnClickListener {

   public static final String ACTION_INTERNAL_REQUEST_BT_ON = "com.android.settings.bluetooth.ACTION_INTERNAL_REQUEST_BT_ON";
   public static final String ACTION_INTERNAL_REQUEST_BT_ON_AND_DISCOVERABLE = "com.android.settings.bluetooth.ACTION_INTERNAL_REQUEST_BT_ON_AND_DISCOVERABLE";
   private static final String TAG = "RequestPermissionHelperActivity";
   private boolean mEnableOnly;
   private LocalBluetoothManager mLocalManager;
   private int mTimeout;


   public RequestPermissionHelperActivity() {}

   private boolean parseIntent() {
      Intent var1 = this.getIntent();
      boolean var4;
      if(var1 != null && var1.getAction().equals("com.android.settings.bluetooth.ACTION_INTERNAL_REQUEST_BT_ON")) {
         this.mEnableOnly = (boolean)1;
      } else {
         if(var1 == null || !var1.getAction().equals("com.android.settings.bluetooth.ACTION_INTERNAL_REQUEST_BT_ON_AND_DISCOVERABLE")) {
            this.setResult(0);
            var4 = true;
            return var4;
         }

         this.mEnableOnly = (boolean)0;
         int var5 = var1.getIntExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 120);
         this.mTimeout = var5;
      }

      LocalBluetoothManager var2 = LocalBluetoothManager.getInstance(this);
      this.mLocalManager = var2;
      if(this.mLocalManager == null) {
         int var3 = Log.e("RequestPermissionHelperActivity", "Error: there\'s a problem starting bluetooth");
         this.setResult(0);
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   void createDialog() {
      AlertParams var1 = this.mAlertParams;
      var1.mIconId = 17301659;
      String var2 = this.getString(2131230820);
      var1.mTitle = var2;
      View var3 = this.getLayoutInflater().inflate(2130903050, (ViewGroup)null);
      var1.mView = var3;
      TextView var4 = (TextView)var3.findViewById(2131427362);
      if(this.mEnableOnly) {
         String var5 = this.getString(2131230821);
         var4.setText(var5);
      } else {
         Object[] var8 = new Object[1];
         Integer var9 = Integer.valueOf(this.mTimeout);
         var8[0] = var9;
         String var10 = this.getString(2131230823, var8);
         var4.setText(var10);
      }

      String var6 = this.getString(2131230722);
      var1.mPositiveButtonText = var6;
      var1.mPositiveButtonListener = this;
      String var7 = this.getString(2131230723);
      var1.mNegativeButtonText = var7;
      var1.mNegativeButtonListener = this;
      this.setupAlert();
   }

   public void onBackPressed() {
      this.setResult(0);
      super.onBackPressed();
   }

   public void onClick(DialogInterface var1, int var2) {
      char var5;
      switch(var2) {
      case -2:
         var5 = 0;
         break;
      case -1:
         int var3 = 30;

         int var4;
         do {
            try {
               var4 = this.mLocalManager.getBluetoothState();
               Thread.sleep(100L);
            } catch (InterruptedException var7) {
               break;
            }

            if(var4 != 13) {
               break;
            }

            var3 += -1;
         } while(var3 > 0);

         if(var4 != 11 && var4 != 12 && !this.mLocalManager.getBluetoothAdapter().enable()) {
            var5 = 0;
         } else {
            var5 = '\ufc18';
         }
         break;
      default:
         return;
      }

      this.setResult(var5);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(this.parseIntent()) {
         this.finish();
      } else {
         this.createDialog();
      }
   }
}
