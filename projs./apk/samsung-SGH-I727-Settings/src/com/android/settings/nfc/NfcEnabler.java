package com.android.settings.nfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.util.Log;

public class NfcEnabler implements OnPreferenceChangeListener {

   private static final String TAG = "NfcEnabler";
   private final CheckBoxPreference mCheckbox;
   private final Context mContext;
   private final Handler mHandler;
   private final IntentFilter mIntentFilter;
   private final NfcAdapter mNfcAdapter;
   private boolean mNfcState;
   private final BroadcastReceiver mReceiver;


   public NfcEnabler(Context var1, CheckBoxPreference var2) {
      Handler var3 = new Handler();
      this.mHandler = var3;
      NfcEnabler.1 var4 = new NfcEnabler.1();
      this.mReceiver = var4;
      this.mContext = var1;
      this.mCheckbox = var2;
      NfcAdapter var5 = NfcAdapter.getDefaultAdapter(var1);
      this.mNfcAdapter = var5;
      if(this.mNfcAdapter == null) {
         this.mCheckbox.setEnabled((boolean)0);
      }

      IntentFilter var6 = new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGE");
      this.mIntentFilter = var6;
   }

   private void handleNfcStateChanged(boolean var1) {
      this.mCheckbox.setChecked(var1);
      this.mCheckbox.setEnabled((boolean)1);
      this.mCheckbox.setSummary(2131231055);
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      boolean var3 = ((Boolean)var2).booleanValue();
      this.mCheckbox.setEnabled((boolean)0);
      (new NfcEnabler.2("toggleNFC", var3)).start();
      return false;
   }

   public void pause() {
      if(this.mNfcAdapter != null) {
         Context var1 = this.mContext;
         BroadcastReceiver var2 = this.mReceiver;
         var1.unregisterReceiver(var2);
         this.mCheckbox.setOnPreferenceChangeListener((OnPreferenceChangeListener)null);
      }
   }

   public void resume() {
      if(this.mNfcAdapter != null) {
         Context var1 = this.mContext;
         BroadcastReceiver var2 = this.mReceiver;
         IntentFilter var3 = this.mIntentFilter;
         var1.registerReceiver(var2, var3);
         this.mCheckbox.setOnPreferenceChangeListener(this);
         boolean var5 = this.mNfcAdapter.isEnabled();
         this.mNfcState = var5;
         CheckBoxPreference var6 = this.mCheckbox;
         boolean var7 = this.mNfcState;
         var6.setChecked(var7);
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         if("android.nfc.action.ADAPTER_STATE_CHANGE".equals(var3)) {
            NfcEnabler var4 = NfcEnabler.this;
            boolean var5 = var2.getBooleanExtra("android.nfc.isEnabled", (boolean)0);
            var4.handleNfcStateChanged(var5);
         }
      }
   }

   class 2 extends Thread {

      // $FF: synthetic field
      final boolean val$desiredState;


      2(String var2, boolean var3) {
         super(var2);
         this.val$desiredState = var3;
      }

      public void run() {
         StringBuilder var1 = (new StringBuilder()).append("Setting NFC enabled state to: ");
         boolean var2 = this.val$desiredState;
         String var3 = var1.append(var2).toString();
         int var4 = Log.d("NfcEnabler", var3);
         boolean var5;
         if(this.val$desiredState) {
            var5 = NfcEnabler.this.mNfcAdapter.enable();
         } else {
            var5 = NfcEnabler.this.mNfcAdapter.disable();
         }

         if(var5) {
            StringBuilder var6 = (new StringBuilder()).append("Successfully changed NFC enabled state to ");
            boolean var7 = this.val$desiredState;
            String var8 = var6.append(var7).toString();
            int var9 = Log.d("NfcEnabler", var8);
            Handler var10 = NfcEnabler.this.mHandler;
            NfcEnabler.2.1 var11 = new NfcEnabler.2.1();
            var10.post(var11);
         } else {
            StringBuilder var13 = (new StringBuilder()).append("Error setting NFC enabled state to ");
            boolean var14 = this.val$desiredState;
            String var15 = var13.append(var14).toString();
            int var16 = Log.w("NfcEnabler", var15);
            Handler var17 = NfcEnabler.this.mHandler;
            NfcEnabler.2.2 var18 = new NfcEnabler.2.2();
            var17.post(var18);
         }
      }

      class 2 implements Runnable {

         2() {}

         public void run() {
            NfcEnabler.this.mCheckbox.setEnabled((boolean)1);
            NfcEnabler.this.mCheckbox.setSummary(2131231056);
         }
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            NfcEnabler var1 = NfcEnabler.this;
            boolean var2 = 2.this.val$desiredState;
            var1.handleNfcStateChanged(var2);
         }
      }
   }
}
