package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Handler;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.widget.Toast;
import com.android.settings.bluetooth.LocalBluetoothManager;
import com.broadcom.bt.service.framework.BluetoothServiceConfig;

public class BluetoothDiscoverableEnabler implements OnPreferenceChangeListener {

   static final int DEFAULT_DISCOVERABLE_TIMEOUT = 120;
   static final String SHARED_PREFERENCES_KEY_DISCOVERABLE_END_TIMESTAMP = "discoverable_end_timestamp";
   private static final String SYSTEM_PROPERTY_DISCOVERABLE_TIMEOUT = "debug.bt.discoverable_time";
   private static final String TAG = "BluetoothDiscoverableEnabler";
   private final CheckBoxPreference mCheckBoxPreference;
   private final Context mContext;
   private final LocalBluetoothManager mLocalManager;
   private final BroadcastReceiver mReceiver;
   private final Handler mUiHandler;
   private final Runnable mUpdateCountdownSummaryRunnable;


   public BluetoothDiscoverableEnabler(Context var1, CheckBoxPreference var2) {
      BluetoothDiscoverableEnabler.1 var3 = new BluetoothDiscoverableEnabler.1();
      this.mReceiver = var3;
      BluetoothDiscoverableEnabler.2 var4 = new BluetoothDiscoverableEnabler.2();
      this.mUpdateCountdownSummaryRunnable = var4;
      this.mContext = var1;
      Handler var5 = new Handler();
      this.mUiHandler = var5;
      this.mCheckBoxPreference = var2;
      var2.setPersistent((boolean)0);
      LocalBluetoothManager var6 = LocalBluetoothManager.getInstance(var1);
      this.mLocalManager = var6;
      if(this.mLocalManager == null) {
         var2.setEnabled((boolean)0);
      }
   }

   private int getDiscoverableTimeout() {
      int var1 = SystemProperties.getInt("debug.bt.discoverable_time", -1);
      if(var1 <= 0) {
         var1 = 120;
      }

      return var1;
   }

   private void handleModeChanged(int var1) {
      if(var1 == 23) {
         this.mCheckBoxPreference.setChecked((boolean)1);
         this.updateCountdownSummary();
      } else {
         this.mCheckBoxPreference.setChecked((boolean)0);
      }
   }

   private void persistDiscoverableEndTimestamp(long var1) {
      Editor var3 = this.mLocalManager.getSharedPreferences().edit();
      var3.putLong("discoverable_end_timestamp", var1);
      var3.apply();
   }

   private void setEnabled(boolean var1) {
      BluetoothAdapter var2 = this.mLocalManager.getBluetoothAdapter();
      if(var1) {
         int var3 = this.getDiscoverableTimeout();
         var2.setDiscoverableTimeout(var3);
         CheckBoxPreference var4 = this.mCheckBoxPreference;
         Resources var5 = this.mContext.getResources();
         Object[] var6 = new Object[1];
         Integer var7 = Integer.valueOf(var3);
         var6[0] = var7;
         String var8 = var5.getString(2131230796, var6);
         var4.setSummaryOn(var8);
         long var9 = System.currentTimeMillis();
         long var11 = (long)(var3 * 1000);
         long var13 = var9 + var11;
         this.persistDiscoverableEndTimestamp(var13);
         boolean var15 = var2.setScanMode(23);
      } else {
         boolean var16 = var2.setScanMode(21);
      }
   }

   private void updateCountdownSummary() {
      if(this.mLocalManager.getBluetoothAdapter().getScanMode() == 23) {
         long var1 = System.currentTimeMillis();
         long var3 = this.mLocalManager.getSharedPreferences().getLong("discoverable_end_timestamp", 0L);
         if(var1 > var3) {
            this.mCheckBoxPreference.setSummaryOn((CharSequence)null);
         } else {
            String var5 = String.valueOf((var3 - var1) / 1000L);
            CheckBoxPreference var6 = this.mCheckBoxPreference;
            Resources var7 = this.mContext.getResources();
            Object[] var8 = new Object[]{var5};
            String var9 = var7.getString(2131230796, var8);
            var6.setSummaryOn(var9);
            synchronized(this) {
               Handler var10 = this.mUiHandler;
               Runnable var11 = this.mUpdateCountdownSummaryRunnable;
               var10.removeCallbacks(var11);
               Handler var12 = this.mUiHandler;
               Runnable var13 = this.mUpdateCountdownSummaryRunnable;
               var12.postDelayed(var13, 1000L);
            }
         }
      }
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      boolean var3;
      if(((Boolean)var2).booleanValue() && !BluetoothServiceConfig.isScanAllowed(this.mContext)) {
         Toast.makeText(this.mContext, 2131232613, 1).show();
         var3 = false;
      } else {
         boolean var4 = ((Boolean)var2).booleanValue();
         this.setEnabled(var4);
         var3 = true;
      }

      return var3;
   }

   public void pause() {
      if(this.mLocalManager != null) {
         Handler var1 = this.mUiHandler;
         Runnable var2 = this.mUpdateCountdownSummaryRunnable;
         var1.removeCallbacks(var2);
         this.mCheckBoxPreference.setOnPreferenceChangeListener((OnPreferenceChangeListener)null);
         Context var3 = this.mContext;
         BroadcastReceiver var4 = this.mReceiver;
         var3.unregisterReceiver(var4);
      }
   }

   public void resume() {
      if(this.mLocalManager != null) {
         IntentFilter var1 = new IntentFilter("android.bluetooth.adapter.action.SCAN_MODE_CHANGED");
         Context var2 = this.mContext;
         BroadcastReceiver var3 = this.mReceiver;
         var2.registerReceiver(var3, var1);
         this.mCheckBoxPreference.setOnPreferenceChangeListener(this);
         int var5 = this.mLocalManager.getBluetoothAdapter().getScanMode();
         this.handleModeChanged(var5);
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         if("android.bluetooth.adapter.action.SCAN_MODE_CHANGED".equals(var3)) {
            int var4 = var2.getIntExtra("android.bluetooth.adapter.extra.SCAN_MODE", Integer.MIN_VALUE);
            if(var4 != Integer.MIN_VALUE) {
               BluetoothDiscoverableEnabler.this.handleModeChanged(var4);
            }
         }
      }
   }

   class 2 implements Runnable {

      2() {}

      public void run() {
         BluetoothDiscoverableEnabler.this.updateCountdownSummary();
      }
   }
}
