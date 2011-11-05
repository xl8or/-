package com.android.settings.bluetooth;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.android.settings.bluetooth.LocalBluetoothManager;

public class BluetoothNamePreference extends EditTextPreference implements TextWatcher {

   private static final int BLUETOOTH_NAME_MAX_LENGTH_BYTES = 32;
   private static final String TAG = "BluetoothNamePreference";
   private LocalBluetoothManager mLocalManager;
   private BroadcastReceiver mReceiver;


   public BluetoothNamePreference(Context var1, AttributeSet var2) {
      super(var1, var2);
      BluetoothNamePreference.1 var3 = new BluetoothNamePreference.1();
      this.mReceiver = var3;
      LocalBluetoothManager var4 = LocalBluetoothManager.getInstance(var1);
      this.mLocalManager = var4;
      this.setSummaryToName();
   }

   private void setSummaryToName() {
      BluetoothAdapter var1 = this.mLocalManager.getBluetoothAdapter();
      if(var1.isEnabled()) {
         String var2 = var1.getName();
         this.setText(var2);
         String var3 = var1.getName();
         this.setSummary(var3);
      }
   }

   public void afterTextChanged(Editable var1) {
      Dialog var2 = this.getDialog();
      int var3 = 0;
      if(var2 instanceof AlertDialog) {
         int var4 = var1.length();
         String var5 = var1.toString();
         String var6 = " deviceName is [ " + var5 + "]";
         int var7 = Log.d("BluetoothNamePreference", var6);

         for(int var8 = 0; var8 < var4; ++var8) {
            if(var5.charAt(var8) == 32) {
               ++var3;
            }
         }

         if(var4 > 0 && var4 != var3) {
            ((AlertDialog)var2).getButton(-1).setEnabled((boolean)1);
         } else {
            ((AlertDialog)var2).getButton(-1).setEnabled((boolean)0);
         }
      }
   }

   public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   protected void onClick() {
      super.onClick();
      if(this.getEditText() != null) {
         ;
      }
   }

   public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   public void pause() {
      EditText var1 = this.getEditText();
      if(var1 != null) {
         var1.removeTextChangedListener(this);
      }

      Context var2 = this.getContext();
      BroadcastReceiver var3 = this.mReceiver;
      var2.unregisterReceiver(var3);
   }

   protected boolean persistString(String var1) {
      boolean var2 = this.mLocalManager.getBluetoothAdapter().setName(var1);
      return true;
   }

   public void resume() {
      IntentFilter var1 = new IntentFilter();
      var1.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
      var1.addAction("android.bluetooth.adapter.action.LOCAL_NAME_CHANGED");
      Context var2 = this.getContext();
      BroadcastReceiver var3 = this.mReceiver;
      var2.registerReceiver(var3, var1);
      EditText var5 = this.getEditText();
      if(var5 != null) {
         InputFilter[] var6 = new InputFilter[1];
         BluetoothNamePreference.Utf8ByteLengthFilter var7 = new BluetoothNamePreference.Utf8ByteLengthFilter(32);
         var6[0] = var7;
         var5.setFilters(var6);
         var5.addTextChangedListener(this);
         Dialog var8 = this.getDialog();
         if(var8 instanceof AlertDialog) {
            Button var9 = ((AlertDialog)var8).getButton(-1);
            byte var10;
            if(var5.getText().toString().trim().length() > 0) {
               var10 = 1;
            } else {
               var10 = 0;
            }

            var9.setEnabled((boolean)var10);
         }
      }
   }

   public static class Utf8ByteLengthFilter implements InputFilter {

      private int mMaxBytes;


      public Utf8ByteLengthFilter(int var1) {
         this.mMaxBytes = var1;
      }

      public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6) {
         int var7 = 0;

         for(int var8 = var2; var8 < var3; ++var8) {
            char var9 = var1.charAt(var8);
            byte var10;
            if(var9 < 128) {
               var10 = 1;
            } else if(var9 < 2048) {
               var10 = 2;
            } else {
               var10 = 3;
            }

            var7 += var10;
         }

         int var11 = var4.length();
         int var12 = 0;

         int var13;
         for(var13 = 0; var13 < var11; ++var13) {
            if(var13 < var5 || var13 >= var6) {
               char var14 = var4.charAt(var13);
               byte var15;
               if(var14 < 128) {
                  var15 = 1;
               } else if(var14 < 2048) {
                  var15 = 2;
               } else {
                  var15 = 3;
               }

               var12 += var15;
            }
         }

         int var16 = this.mMaxBytes;
         int var17 = var16 - var12;
         Object var19;
         if(var17 <= 0) {
            var19 = "";
         } else if(var17 >= var7) {
            var19 = null;
         } else {
            var13 = var2;

            while(true) {
               if(var13 >= var3) {
                  var19 = null;
                  break;
               }

               char var18 = var1.charAt(var13);
               if(var18 < 128) {
                  var16 = 1;
               } else if(var18 < 2048) {
                  ;
               }

               var17 -= var16;
               if(var17 < 0) {
                  var19 = var1.subSequence(var2, var13);
                  break;
               }

               ++var13;
            }
         }

         return (CharSequence)var19;
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         if(var3.equals("android.bluetooth.adapter.action.LOCAL_NAME_CHANGED")) {
            BluetoothNamePreference.this.setSummaryToName();
         } else if(var3.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
            if(var2.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE) == 12) {
               BluetoothNamePreference.this.setSummaryToName();
            }
         }
      }
   }
}
