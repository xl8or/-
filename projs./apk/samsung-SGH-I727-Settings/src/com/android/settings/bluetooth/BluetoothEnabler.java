package com.android.settings.bluetooth;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.widget.Toast;
import com.android.settings.bluetooth.LocalBluetoothManager;

public class BluetoothEnabler implements OnPreferenceChangeListener {

   private final CheckBoxPreference mCheckBox;
   private final Context mContext;
   private final IntentFilter mIntentFilter;
   private final LocalBluetoothManager mLocalManager;
   private final CharSequence mOriginalSummary;
   private final BroadcastReceiver mReceiver;


   public BluetoothEnabler(Context var1, CheckBoxPreference var2) {
      BluetoothEnabler.1 var3 = new BluetoothEnabler.1();
      this.mReceiver = var3;
      this.mContext = var1;
      this.mCheckBox = var2;
      CharSequence var4 = var2.getSummary();
      this.mOriginalSummary = var4;
      var2.setPersistent((boolean)0);
      LocalBluetoothManager var5 = LocalBluetoothManager.getInstance(var1);
      this.mLocalManager = var5;
      if(this.mLocalManager == null) {
         var2.setEnabled((boolean)0);
      }

      IntentFilter var6 = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
      this.mIntentFilter = var6;
   }

   private void handleStateChanged(int var1) {
      switch(var1) {
      case 10:
         this.mCheckBox.setChecked((boolean)0);
         CheckBoxPreference var2 = this.mCheckBox;
         CharSequence var3 = this.mOriginalSummary;
         var2.setSummary(var3);
         this.mCheckBox.setEnabled((boolean)1);
         return;
      case 11:
         this.mCheckBox.setSummary(2131231062);
         this.mCheckBox.setEnabled((boolean)0);
         return;
      case 12:
         this.mCheckBox.setChecked((boolean)1);
         this.mCheckBox.setSummary((CharSequence)null);
         this.mCheckBox.setEnabled((boolean)1);
         return;
      case 13:
         this.mCheckBox.setSummary(2131231063);
         this.mCheckBox.setEnabled((boolean)0);
         return;
      default:
         this.mCheckBox.setChecked((boolean)0);
         this.mCheckBox.setSummary(2131231064);
         this.mCheckBox.setEnabled((boolean)1);
      }
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      boolean var3 = ((Boolean)var2).booleanValue();
      boolean var4;
      if(((DevicePolicyManager)this.mContext.getSystemService("device_policy")).getAllowBluetoothMode((ComponentName)null) == 0) {
         Toast.makeText(this.mContext, 2131232560, 1).show();
         var4 = false;
      } else {
         this.mLocalManager.setBluetoothEnabled(var3);
         this.mCheckBox.setEnabled((boolean)0);
         var4 = false;
      }

      return var4;
   }

   public void pause() {
      if(this.mLocalManager != null) {
         Context var1 = this.mContext;
         BroadcastReceiver var2 = this.mReceiver;
         var1.unregisterReceiver(var2);
         this.mCheckBox.setOnPreferenceChangeListener((OnPreferenceChangeListener)null);
      }
   }

   public void resume() {
      if(this.mLocalManager != null) {
         int var1 = this.mLocalManager.getBluetoothState();
         this.handleStateChanged(var1);
         Context var2 = this.mContext;
         BroadcastReceiver var3 = this.mReceiver;
         IntentFilter var4 = this.mIntentFilter;
         var2.registerReceiver(var3, var4);
         this.mCheckBox.setOnPreferenceChangeListener(this);
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         int var3 = var2.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
         BluetoothEnabler.this.handleStateChanged(var3);
      }
   }
}
