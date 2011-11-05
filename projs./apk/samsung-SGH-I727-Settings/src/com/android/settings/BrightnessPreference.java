package com.android.settings;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IPowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.IPowerManager.Stub;
import android.preference.PreferenceManager;
import android.preference.SeekBarPreference;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class BrightnessPreference extends SeekBarPreference implements OnSeekBarChangeListener, OnCheckedChangeListener {

   private static final int MAXIMUM_BACKLIGHT = 255;
   private static final int MINIMUM_BACKLIGHT = 30;
   private static final String PREF_BATT_TEMPERATURE = "battery_temperature";
   private static final String TAG = "BrightnessPreference";
   private boolean mAutomaticAvailable;
   private BrightnessPreference.BrightnessObserver mBrightnessObserver;
   private CheckBox mCheckBox;
   private ContentResolver mContentResolver;
   private Context mContext;
   private int mCurrentProgress;
   private int mMaxBrightness;
   private int[] mMaxBrightnessTable;
   private int mOldAutomatic;
   private int mOldBrightness;
   private SeekBar mSeekBar;
   SharedPreferences mSharedPreference;
   private int mTemperature;
   private int[] mTemperatureTable;
   private TextView mTextView;


   public BrightnessPreference(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      Resources var3 = this.mContext.getResources();
      boolean var4 = var1.getResources().getBoolean(17629194);
      this.mAutomaticAvailable = var4;
      StringBuilder var5 = (new StringBuilder()).append("BrightnessPreference automatic");
      boolean var6 = this.mAutomaticAvailable;
      String var7 = var5.append(var6).toString();
      int var8 = Log.d("BrightnessPreference", var7);
      this.setDialogLayoutResource(2130903110);
      ContentResolver var9 = var1.getContentResolver();
      this.mContentResolver = var9;
      BrightnessPreference.BrightnessObserver var10 = new BrightnessPreference.BrightnessObserver();
      this.mBrightnessObserver = var10;
      int[] var11 = var3.getIntArray(17236001);
      this.mTemperatureTable = var11;
      int[] var12 = var3.getIntArray(17236002);
      this.mMaxBrightnessTable = var12;
   }

   private void setBrightness(int var1) {
      try {
         IPowerManager var2 = Stub.asInterface(ServiceManager.getService("power"));
         if(var2 != null) {
            var2.setBacklightBrightness(var1);
         }
      } catch (RemoteException var5) {
         int var4 = Log.w("BrightnessPreference", var5);
      }
   }

   private void setMode(int var1) {
      String var2 = "setMode : " + var1;
      int var3 = Log.d("BrightnessPreference", var2);
      if(var1 == 1) {
         this.mSeekBar.setEnabled((boolean)0);
      } else {
         this.mSeekBar.setEnabled((boolean)1);
      }

      boolean var4 = System.putInt(this.getContext().getContentResolver(), "screen_brightness_mode", var1);
   }

   protected void onBindDialogView(View var1) {
      super.onBindDialogView(var1);
      int var2 = Log.d("BrightnessPreference", "onBindDialogView()");
      ContentResolver var3 = this.mContentResolver;
      Uri var4 = System.getUriFor("screen_brightness");
      BrightnessPreference.BrightnessObserver var5 = this.mBrightnessObserver;
      var3.registerContentObserver(var4, (boolean)0, var5);
      TextView var6 = (TextView)var1.findViewById(2131427560);
      this.mTextView = var6;
      SeekBar var7 = getSeekBar(var1);
      this.mSeekBar = var7;
      this.mSeekBar.setMax(225);

      try {
         int var8 = System.getInt(this.getContext().getContentResolver(), "screen_brightness");
         this.mOldBrightness = var8;
         StringBuilder var9 = (new StringBuilder()).append("mOldBrightness :");
         int var10 = this.mOldBrightness;
         String var11 = var9.append(var10).toString();
         int var12 = Log.d("BrightnessPreference", var11);
      } catch (SettingNotFoundException var44) {
         this.mOldBrightness = 255;
      }

      SharedPreferences var13 = PreferenceManager.getDefaultSharedPreferences(this.mContext);
      this.mSharedPreference = var13;
      int var14 = this.mSharedPreference.getInt("battery_temperature", 0);
      this.mTemperature = var14;
      StringBuilder var15 = (new StringBuilder()).append("BatteryTemperature :");
      int var16 = this.mTemperature;
      String var17 = var15.append(var16).toString();
      int var18 = Log.d("BrightnessPreference", var17);
      int var19 = this.mTemperature;
      int var20 = this.returnMaxBrightness(var19);
      this.mMaxBrightness = var20;
      int var21 = this.mOldBrightness;
      int var22 = this.mMaxBrightness;
      if(var21 > var22) {
         SeekBar var23 = this.mSeekBar;
         int var24 = this.mMaxBrightness - 30;
         var23.setProgress(var24);
      } else {
         SeekBar var40 = this.mSeekBar;
         int var41 = this.mOldBrightness - 30;
         var40.setProgress(var41);
      }

      int var25 = this.mTemperature;
      int var26 = this.mTemperatureTable[0];
      if(var25 < var26) {
         this.mTextView.setVisibility(8);
      }

      CheckBox var27 = (CheckBox)var1.findViewById(2131427559);
      this.mCheckBox = var27;
      if(this.mAutomaticAvailable) {
         StringBuilder var28 = (new StringBuilder()).append("mAutomaticAvailable :");
         boolean var29 = this.mAutomaticAvailable;
         String var30 = var28.append(var29).toString();
         int var31 = Log.d("BrightnessPreference", var30);
         this.mCheckBox.setOnCheckedChangeListener(this);

         try {
            int var32 = System.getInt(this.getContext().getContentResolver(), "screen_brightness_mode");
            this.mOldAutomatic = var32;
            StringBuilder var33 = (new StringBuilder()).append("mOldAutomatic :");
            int var34 = this.mOldAutomatic;
            String var35 = var33.append(var34).toString();
            int var36 = Log.d("BrightnessPreference", var35);
         } catch (SettingNotFoundException var43) {
            this.mOldAutomatic = 0;
         }

         CheckBox var37 = this.mCheckBox;
         byte var38;
         if(this.mOldAutomatic != 0) {
            var38 = 1;
         } else {
            var38 = 0;
         }

         var37.setChecked((boolean)var38);
      } else {
         this.mCheckBox.setVisibility(8);
      }

      this.mSeekBar.setOnSeekBarChangeListener(this);
   }

   public void onCheckedChanged(CompoundButton var1, boolean var2) {
      String var3 = "onCheckedChanged : isChecked : " + var2;
      int var4 = Log.d("BrightnessPreference", var3);
      byte var5;
      if(var2) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      this.setMode(var5);
      if(!var2) {
         this.setBrightness(120);
         this.mSeekBar.setProgress(90);
      }
   }

   protected void onDialogClosed(boolean var1) {
      String var2 = "onDialogClosed : " + var1;
      int var3 = Log.d("BrightnessPreference", var2);
      super.onDialogClosed(var1);
      ContentResolver var4 = this.mContentResolver;
      BrightnessPreference.BrightnessObserver var5 = this.mBrightnessObserver;
      var4.unregisterContentObserver(var5);
      if(var1) {
         ContentResolver var6 = this.getContext().getContentResolver();
         int var7 = this.mSeekBar.getProgress() + 30;
         System.putInt(var6, "screen_brightness", var7);
         if(System.getInt(this.getContext().getContentResolver(), "screen_brightness_mode", 0) == 1) {
            int var9 = Log.d("BrightnessPreference", "onDialogClosed(), Automatic mode and no set brightness");
         } else {
            int var11 = Log.d("BrightnessPreference", "onDialogClosed(), Manual mode and set brightness");
            int var12 = this.mSeekBar.getProgress() + 30;
            this.setBrightness(var12);
         }

         Intent var10 = new Intent("android.settings.BRIGHTNESS_CHANGED");
         this.mContext.sendBroadcast(var10);
      } else {
         if(this.mAutomaticAvailable) {
            int var13 = this.mOldAutomatic;
            this.setMode(var13);
         }

         if(!this.mAutomaticAvailable || this.mOldAutomatic == 0) {
            int var14 = this.mOldBrightness;
            this.setBrightness(var14);
         }
      }
   }

   public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
      this.mCurrentProgress = var2;
      int var4 = var2 + 30;
      int var5 = this.mMaxBrightness;
      if(var4 <= var5) {
         int var6 = var2 + 30;
         this.setBrightness(var6);
      }
   }

   public void onStartTrackingTouch(SeekBar var1) {}

   public void onStopTrackingTouch(SeekBar var1) {
      StringBuilder var2 = (new StringBuilder()).append("onStopTrackingTouch() : Current progress : ");
      int var3 = this.mCurrentProgress;
      String var4 = var2.append(var3).toString();
      int var5 = Log.d("BrightnessPreference", var4);
      int var6 = this.mCurrentProgress + 30;
      int var7 = this.mMaxBrightness;
      if(var6 <= var7) {
         int var8 = Log.d("BrightnessPreference", "onStopTrackingTouch() : Current progress is below than MaxBrightness");
      } else {
         int var9 = this.mMaxBrightness - 30;
         var1.setProgress(var9);
         int var10 = Log.d("BrightnessPreference", "onStopTrackingTouch() : Current progress is over than MaxBrightness");
      }
   }

   public int returnMaxBrightness(int var1) {
      int var2 = 0;

      while(true) {
         int var3 = this.mTemperatureTable.length;
         if(var2 >= var3) {
            break;
         }

         int var4 = this.mTemperatureTable[var2];
         if(var1 < var4) {
            break;
         }

         ++var2;
      }

      int var5 = this.mMaxBrightnessTable[var2];
      String var6 = "returnMaxBrightness : Temperature " + var1;
      int var7 = Log.d("BrightnessPreference", var6);
      String var8 = "returnMaxBrightness : MaxBrightness " + var5;
      int var9 = Log.d("BrightnessPreference", var8);
      return var5;
   }

   private class BrightnessObserver extends ContentObserver {

      BrightnessObserver() {
         Handler var2 = new Handler();
         super(var2);
         int var3 = Log.d("BrightnessPreference", "BrightnessObserver()");
      }

      public void onChange(boolean var1) {
         super.onChange(var1);
         int var2 = Log.d("BrightnessPreference", "BrightnessObserver().onChange()");

         try {
            if(BrightnessPreference.this.mSeekBar != null) {
               int var3 = System.getInt(BrightnessPreference.this.mContentResolver, "screen_brightness");
               SeekBar var4 = BrightnessPreference.this.mSeekBar;
               int var5 = var3 - 30;
               var4.setProgress(var5);
               String var6 = "onChange() SCREEN_BRIGHTNESS: " + var3;
               int var7 = Log.d("BrightnessPreference", var6);
            }
         } catch (SettingNotFoundException var11) {
            String var9 = "SettingNotFoundException " + var11;
            int var10 = Log.d("BrightnessPreference", var9);
         }
      }
   }
}
