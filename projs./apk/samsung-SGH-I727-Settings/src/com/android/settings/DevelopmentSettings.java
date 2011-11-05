package com.android.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.util.Log;
import com.android.settings.Utils;

public class DevelopmentSettings extends PreferenceActivity implements OnClickListener, OnDismissListener {

   private static final String ALLOW_MOCK_LOCATION = "allow_mock_location";
   private static final boolean DEBUG = true;
   private static final String ENABLE_ADB = "enable_adb";
   private static final String KEEP_SCREEN_ON = "keep_screen_on";
   private static final String TAG = "DevelopmentSettings";
   private CheckBoxPreference mAllowMockLocation;
   BroadcastReceiver mBroadcastReceiver;
   private CheckBoxPreference mEnableAdb;
   private CheckBoxPreference mKeepScreenOn;
   private boolean mOkClicked;
   private Dialog mOkDialog;
   private int mPluggedType = 0;


   public DevelopmentSettings() {
      DevelopmentSettings.1 var1 = new DevelopmentSettings.1();
      this.mBroadcastReceiver = var1;
   }

   private void ShowErrorPopup() {
      AlertDialog var1 = (new Builder(this)).create();
      CharSequence var2 = this.getResources().getText(2131231442);
      var1.setTitle(var2);
      CharSequence var3 = this.getResources().getText(2131232112);
      var1.setMessage(var3);
      var1.setIcon(17301543);
      CharSequence var4 = this.getResources().getText(2131230852);
      DevelopmentSettings.2 var5 = new DevelopmentSettings.2();
      var1.setButton(var4, var5);
      var1.show();
   }

   private void dismissDialog() {
      if(this.mOkDialog != null) {
         this.mOkDialog.dismiss();
         this.mOkDialog = null;
      }
   }

   private void initIntentFilter() {
      IntentFilter var1 = new IntentFilter();
      var1.addAction("android.intent.action.BATTERY_CHANGED");
      BroadcastReceiver var2 = this.mBroadcastReceiver;
      this.registerReceiver(var2, var1);
   }

   public void onClick(DialogInterface var1, int var2) {
      if(var2 == -1) {
         this.mOkClicked = (boolean)1;
         boolean var3 = Secure.putInt(this.getContentResolver(), "adb_enabled", 1);
         this.mEnableAdb.setChecked((boolean)1);
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968588);
      CheckBoxPreference var2 = (CheckBoxPreference)this.findPreference("enable_adb");
      this.mEnableAdb = var2;
      CheckBoxPreference var3 = (CheckBoxPreference)this.findPreference("keep_screen_on");
      this.mKeepScreenOn = var3;
      CheckBoxPreference var4 = (CheckBoxPreference)this.findPreference("allow_mock_location");
      this.mAllowMockLocation = var4;
   }

   public void onDestroy() {
      this.dismissDialog();
      super.onDestroy();
   }

   public void onDismiss(DialogInterface var1) {
      if(!this.mOkClicked) {
         this.mEnableAdb.setChecked((boolean)0);
      }
   }

   protected void onPause() {
      int var1 = Log.v("DevelopmentSettings", "onPause()");
      super.onPause();
      BroadcastReceiver var2 = this.mBroadcastReceiver;
      this.unregisterReceiver(var2);
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      boolean var3;
      if(Utils.isMonkeyRunning()) {
         var3 = false;
      } else {
         CheckBoxPreference var4 = this.mEnableAdb;
         if(var2.equals(var4)) {
            if(this.mPluggedType == 2) {
               int var5 = Log.d("DevelopmentSettings", "USB connected");
               if(this.mEnableAdb.isChecked()) {
                  this.mEnableAdb.setChecked((boolean)0);
               } else {
                  this.mEnableAdb.setChecked((boolean)1);
               }

               this.ShowErrorPopup();
               var3 = false;
               return var3;
            }

            if(this.mEnableAdb.isChecked()) {
               this.mOkClicked = (boolean)0;
               if(this.mOkDialog != null) {
                  this.dismissDialog();
               }

               Builder var6 = new Builder(this);
               String var7 = this.getResources().getString(2131231750);
               AlertDialog var8 = var6.setMessage(var7).setTitle(2131231749).setIcon(17301543).setPositiveButton(2131230722, this).setNegativeButton(2131230723, this).show();
               this.mOkDialog = var8;
               this.mOkDialog.setOnDismissListener(this);
               this.mEnableAdb.setChecked((boolean)0);
            } else {
               boolean var9 = Secure.putInt(this.getContentResolver(), "adb_enabled", 0);
            }
         } else {
            CheckBoxPreference var10 = this.mKeepScreenOn;
            ContentResolver var11;
            String var12;
            byte var13;
            if(var2.equals(var10)) {
               var11 = this.getContentResolver();
               var12 = "stay_on_while_plugged_in";
               if(this.mKeepScreenOn.isChecked()) {
                  var13 = 3;
               } else {
                  var13 = 0;
               }

               System.putInt(var11, var12, var13);
            } else {
               CheckBoxPreference var15 = this.mAllowMockLocation;
               if(var2.equals(var15)) {
                  var11 = this.getContentResolver();
                  var12 = "mock_location";
                  if(this.mAllowMockLocation.isChecked()) {
                     var13 = 1;
                  } else {
                     var13 = 0;
                  }

                  Secure.putInt(var11, var12, var13);
               }
            }
         }

         var3 = false;
      }

      return var3;
   }

   protected void onResume() {
      int var1 = Log.v("DevelopmentSettings", "onResume()");
      super.onResume();
      PreferenceScreen var2 = this.getPreferenceScreen();
      CheckBoxPreference var3 = this.mKeepScreenOn;
      var2.removePreference(var3);
      CheckBoxPreference var5 = this.mEnableAdb;
      byte var6;
      if(Secure.getInt(this.getContentResolver(), "adb_enabled", 0) != 0) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      var5.setChecked((boolean)var6);
      CheckBoxPreference var7 = this.mKeepScreenOn;
      byte var8;
      if(System.getInt(this.getContentResolver(), "stay_on_while_plugged_in", 0) != 0) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      var7.setChecked((boolean)var8);
      CheckBoxPreference var9 = this.mAllowMockLocation;
      byte var10;
      if(Secure.getInt(this.getContentResolver(), "mock_location", 0) != 0) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      var9.setChecked((boolean)var10);
      this.initIntentFilter();
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {}
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         int var4 = var2.getIntExtra("plugged", 0);
         String var5 = "onReceive [" + var3 + "]" + " pluggedType [" + var4 + "]";
         int var6 = Log.v("DevelopmentSettings", var5);
         if(var3.equals("android.intent.action.BATTERY_CHANGED")) {
            DevelopmentSettings.this.mPluggedType = var4;
         }
      }
   }
}
