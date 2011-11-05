package com.android.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class UsbSettings extends Activity {

   private static final int CONNECTED_DIALOG = 4;
   private static final int CONNECT_DIALOG = 2;
   private static final int DEBUGGING_DIALOG = 1;
   private static final int DEBUGGING_OFF_DIALOG = 5;
   private static final int DLG_ERROR_SHARING = 3;
   private static final int KIES_MODE = 0;
   private static final String TAG = "UsbSettings";
   private static final int UMS_MODE = 2;
   private static final String USBMENUSEL_PATH = "/sys/devices/platform/android_usb/UsbMenuSel";
   private static final String USB_SETTING_MODE = "usb_setting_mode";
   private BroadcastReceiver mBatteryReceiver;
   private Button mMountButton;
   private ProgressBar mProgressBar;
   private StorageEventListener mStorageListener;
   private StorageManager mStorageManager = null;
   private Button mUnmountButton;


   public UsbSettings() {
      UsbSettings.1 var1 = new UsbSettings.1();
      this.mBatteryReceiver = var1;
      UsbSettings.2 var2 = new UsbSettings.2();
      this.mStorageListener = var2;
   }

   private void switchDisplay(boolean var1) {
      if(var1) {
         this.mProgressBar.setVisibility(8);
         this.mUnmountButton.setVisibility(0);
         this.mMountButton.setVisibility(8);
      } else {
         this.mProgressBar.setVisibility(8);
         this.mUnmountButton.setVisibility(8);
         this.mMountButton.setVisibility(0);
      }
   }

   public void changeUMS() {
      // $FF: Couldn't be decompiled
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(this.mStorageManager == null) {
         StorageManager var2 = (StorageManager)this.getSystemService("storage");
         this.mStorageManager = var2;
         if(this.mStorageManager == null) {
            int var3 = Log.w("UsbSettings", "Failed to get StorageManager");
         }
      }

      this.setContentView(2130903136);
      Button var4 = (Button)this.findViewById(2131427641);
      this.mMountButton = var4;
      Button var5 = this.mMountButton;
      UsbSettings.3 var6 = new UsbSettings.3();
      var5.setOnClickListener(var6);
      Button var7 = (Button)this.findViewById(2131427642);
      this.mUnmountButton = var7;
      Button var8 = this.mUnmountButton;
      UsbSettings.4 var9 = new UsbSettings.4();
      var8.setOnClickListener(var9);
      ProgressBar var10 = (ProgressBar)this.findViewById(2131427643);
      this.mProgressBar = var10;
   }

   public Dialog onCreateDialog(int var1, Bundle var2) {
      AlertDialog var3;
      switch(var1) {
      case 1:
         Builder var4 = (new Builder(this)).setTitle(2131232223).setMessage(2131232224);
         UsbSettings.5 var5 = new UsbSettings.5();
         var3 = var4.setPositiveButton(17039370, var5).setNegativeButton(17039360, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 2:
         Builder var6 = (new Builder(this)).setTitle(2131232213).setMessage(2131232222);
         UsbSettings.7 var7 = new UsbSettings.7();
         Builder var8 = var6.setNegativeButton(17039360, var7).setCancelable((boolean)1);
         UsbSettings.6 var9 = new UsbSettings.6();
         var3 = var8.setOnCancelListener(var9).create();
         break;
      case 3:
      default:
         var3 = null;
         break;
      case 4:
         var3 = (new Builder(this)).setTitle(2131231442).setNeutralButton(2131231632, (OnClickListener)null).setMessage(2131232225).setCancelable((boolean)1).create();
         break;
      case 5:
         Builder var10 = (new Builder(this)).setTitle(2131232223).setMessage(2131232224);
         UsbSettings.8 var11 = new UsbSettings.8();
         var3 = var10.setPositiveButton(17039370, var11).setNegativeButton(17039360, (OnClickListener)null).setCancelable((boolean)1).create();
      }

      return var3;
   }

   protected void onPause() {
      super.onPause();
      if(this.mStorageManager != null) {
         if(this.mStorageListener != null) {
            StorageManager var1 = this.mStorageManager;
            StorageEventListener var2 = this.mStorageListener;
            var1.unregisterListener(var2);
         }
      }
   }

   protected void onResume() {
      super.onResume();
      StorageManager var1 = this.mStorageManager;
      StorageEventListener var2 = this.mStorageListener;
      var1.registerListener(var2);

      try {
         boolean var3 = this.mStorageManager.isUsbMassStorageEnabled();
         this.switchDisplay(var3);
      } catch (Exception var6) {
         int var5 = Log.e("UsbSettings", "Failed to read UMS enable state", var6);
      }
   }

   class 7 implements OnClickListener {

      7() {}

      public void onClick(DialogInterface var1, int var2) {
         SystemProperties.set("persist.service.usb.setting", "0");
         boolean var3 = Secure.putInt(UsbSettings.this.getContentResolver(), "usb_setting_mode", 0);
      }
   }

   class 8 implements OnClickListener {

      8() {}

      public void onClick(DialogInterface var1, int var2) {
         boolean var3 = Secure.putInt(UsbSettings.this.getContentResolver(), "adb_enabled", 0);
         SystemProperties.set("persist.service.adb.enable", "0");
      }
   }

   class 3 implements android.view.View.OnClickListener {

      3() {}

      public void onClick(View var1) {
         if(((PowerManager)UsbSettings.this.getSystemService("power")).getPlugType() == 2) {
            UsbSettings.this.showDialog(4);
         } else if(Secure.getInt(UsbSettings.this.getContentResolver(), "adb_enabled", 0) != 0) {
            UsbSettings.this.showDialog(1);
         } else {
            UsbSettings.this.showDialog(2);
            UsbSettings.this.changeUMS();
            boolean var2 = Secure.putInt(UsbSettings.this.getContentResolver(), "usb_setting_mode", 2);
            IntentFilter var3 = new IntentFilter();
            var3.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            UsbSettings var4 = UsbSettings.this;
            BroadcastReceiver var5 = UsbSettings.this.mBatteryReceiver;
            var4.registerReceiver(var5, var3);
         }
      }
   }

   class 4 implements android.view.View.OnClickListener {

      4() {}

      public void onClick(View var1) {
         if(Secure.getInt(UsbSettings.this.getContentResolver(), "adb_enabled", 0) != 0) {
            UsbSettings.this.showDialog(5);
         } else {
            SystemProperties.set("persist.service.usb.setting", "0");
         }
      }
   }

   class 5 implements OnClickListener {

      5() {}

      public void onClick(DialogInterface var1, int var2) {
         boolean var3 = Secure.putInt(UsbSettings.this.getContentResolver(), "adb_enabled", 0);
         SystemProperties.set("persist.service.adb.enable", "0");
         if(((PowerManager)UsbSettings.this.getSystemService("power")).getPlugType() == 2) {
            UsbSettings.this.showDialog(4);
         } else {
            UsbSettings.this.showDialog(2);
            UsbSettings.this.changeUMS();
            boolean var4 = Secure.putInt(UsbSettings.this.getContentResolver(), "usb_setting_mode", 2);
            IntentFilter var5 = new IntentFilter();
            var5.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            UsbSettings var6 = UsbSettings.this;
            BroadcastReceiver var7 = UsbSettings.this.mBatteryReceiver;
            var6.registerReceiver(var7, var5);
         }
      }
   }

   class 6 implements OnCancelListener {

      6() {}

      public void onCancel(DialogInterface var1) {
         SystemProperties.set("persist.service.usb.setting", "0");
         boolean var2 = Secure.putInt(UsbSettings.this.getContentResolver(), "usb_setting_mode", 0);
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         if(((PowerManager)var1.getSystemService("power")).getPlugType() == 2) {
            UsbSettings.this.removeDialog(2);
            UsbSettings var3 = UsbSettings.this;
            BroadcastReceiver var4 = UsbSettings.this.mBatteryReceiver;
            var3.unregisterReceiver(var4);
         }
      }
   }

   class 2 extends StorageEventListener {

      2() {}

      public void onStorageStateChanged(String var1, String var2, String var3) {
         String var4 = Environment.getExternalStorageDirectory().toString();
         if(var1.equals(var4) && var3.equals("shared")) {
            UsbSettings.this.switchDisplay((boolean)1);
         }

         String var5 = Environment.getExternalStorageDirectorySd().toString();
         if(Environment.getExternalStorageStateSd().equals("removed")) {
            if(var1.equals(var4)) {
               if(var3.equals("mounted")) {
                  UsbSettings.this.switchDisplay((boolean)0);
               }
            }
         } else if(var3.equals("mounted")) {
            UsbSettings.this.switchDisplay((boolean)0);
         }
      }
   }
}
