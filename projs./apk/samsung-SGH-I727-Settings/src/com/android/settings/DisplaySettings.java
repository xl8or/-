package com.android.settings;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings.System;
import android.util.Log;
import android.view.IWindowManager;
import com.android.settings.BrightnessPreference;
import com.sec.android.hardware.SecHardwareInterface;
import java.util.ArrayList;

public class DisplaySettings extends PreferenceActivity implements OnPreferenceChangeListener {

   private static final int FALLBACK_SCREEN_TIMEOUT_VALUE = 30000;
   private static final String KEY_ACCELEROMETER = "accelerometer";
   private static final String KEY_ANIMATIONS = "animations";
   private static final String KEY_BRIGHTNESS = "brightness";
   private static final String KEY_G_SENSOR = "g_sensor";
   private static final String KEY_MODE_SETTING = "mode";
   private static final String KEY_NOTIFICATION_FLASH = "notification_flash";
   private static final String KEY_PEN_PREFERRED_HAND = "pen_hand_side";
   private static final String KEY_POWER_SAVING_MODE = "power_saving_mode";
   private static final String KEY_SCREEN_DISPLAY = "screen_display";
   private static final String KEY_SCREEN_TIMEOUT = "screen_timeout";
   private static final String KEY_SPLITE_VIEW = "split_view_settings";
   private static final String KEY_TOUCH_KEY_LIGHT = "touch_key_light";
   private static final String KEY_TVOUT_SETTINGS = "tvout_settings";
   private static final String PREF_BATT_TEMPERATURE = "battery_temperature";
   private static final String TAG = "DisplaySettings";
   Editor editor;
   private PreferenceScreen gSensor;
   private CheckBoxPreference mAccelerometer;
   private float[] mAnimationScales;
   private ListPreference mAnimations;
   private int mBattTemperature = 0;
   private BrightnessPreference mBrightnessPreference;
   private BroadcastReceiver mBroadcastReceiver;
   private ContentResolver mContentResolver;
   private final BroadcastReceiver mIntentReceiver;
   private Preference mModeSetting;
   private CheckBoxPreference mNotificationPulse;
   private DisplaySettings.OrientationObserver mOrientationObserver;
   private ListPreference mPenPreferredHand;
   private CheckBoxPreference mPowerSavingMode;
   private PreferenceScreen mScreenDisplay;
   SharedPreferences mSharedPreference;
   private PreferenceScreen mSpliteViewMode;
   private ListPreference mTouchKeyLight;
   private Preference mTvOutSettigs;
   private IWindowManager mWindowManager;
   private ListPreference screenTimeoutPreference;


   public DisplaySettings() {
      DisplaySettings.1 var1 = new DisplaySettings.1();
      this.mIntentReceiver = var1;
      DisplaySettings.2 var2 = new DisplaySettings.2();
      this.mBroadcastReceiver = var2;
   }

   private void changeBacklightvalue() {
      if(this.screenTimeoutPreference != null) {
         ListPreference var1 = this.screenTimeoutPreference;
         String var2 = String.valueOf(System.getInt(this.getContentResolver(), "screen_off_timeout", 30000));
         var1.setValue(var2);
      }

      if(this.screenTimeoutPreference != null) {
         if(this.screenTimeoutPreference.getDialog() != null) {
            this.screenTimeoutPreference.getDialog().dismiss();
         }
      }
   }

   private void changeTouchKeyLightEntry(ListPreference var1) {
      CharSequence[] var2 = var1.getEntries();
      CharSequence[] var3 = var1.getEntryValues();
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      int var6 = 0;

      while(true) {
         int var7 = var3.length - 1;
         if(var6 >= var7) {
            CharSequence[] var12 = new CharSequence[var4.size()];
            CharSequence[] var13 = (CharSequence[])var4.toArray(var12);
            var1.setEntries(var13);
            CharSequence[] var14 = new CharSequence[var5.size()];
            CharSequence[] var15 = (CharSequence[])var5.toArray(var14);
            var1.setEntryValues(var15);
            return;
         }

         CharSequence var8 = var2[var6];
         var4.add(var8);
         CharSequence var10 = var3[var6];
         var5.add(var10);
         ++var6;
      }
   }

   private void disableUnusableTimeouts(ListPreference var1) {
      DevicePolicyManager var2 = (DevicePolicyManager)this.getSystemService("device_policy");
      long var3;
      if(var2 != null) {
         var3 = var2.getMaximumTimeToLock((ComponentName)null);
      } else {
         var3 = 0L;
      }

      if(var3 != 0L) {
         CharSequence[] var5 = var1.getEntries();
         CharSequence[] var6 = var1.getEntryValues();
         ArrayList var7 = new ArrayList();
         ArrayList var8 = new ArrayList();
         int var9 = 0;

         while(true) {
            int var10 = var6.length;
            if(var9 >= var10) {
               label34: {
                  int var15 = var7.size();
                  int var16 = var5.length;
                  if(var15 == var16) {
                     int var17 = var8.size();
                     int var18 = var6.length;
                     if(var17 == var18) {
                        break label34;
                     }
                  }

                  CharSequence[] var19 = new CharSequence[var7.size()];
                  CharSequence[] var20 = (CharSequence[])var7.toArray(var19);
                  var1.setEntries(var20);
                  CharSequence[] var21 = new CharSequence[var8.size()];
                  CharSequence[] var22 = (CharSequence[])var8.toArray(var21);
                  var1.setEntryValues(var22);
                  int var23 = Integer.valueOf(var1.getValue()).intValue();
                  if((long)var23 <= var3) {
                     String var24 = String.valueOf(var23);
                     var1.setValue(var24);
                  }
               }

               byte var25;
               if(var7.size() > 0) {
                  var25 = 1;
               } else {
                  var25 = 0;
               }

               var1.setEnabled((boolean)var25);
               return;
            }

            if(Long.valueOf(var6[var9].toString()).longValue() <= var3) {
               CharSequence var11 = var5[var9];
               var7.add(var11);
               CharSequence var13 = var6[var9];
               var8.add(var13);
            }

            ++var9;
         }
      }
   }

   private void setPreferredHandSide(int param1) {
      // $FF: Couldn't be decompiled
   }

   private void updateAnimationsSummary(Object var1) {
      CharSequence[] var2 = this.getResources().getTextArray(2131034116);
      CharSequence[] var3 = this.mAnimations.getEntryValues();
      int var4 = 0;

      while(true) {
         int var5 = var3.length;
         if(var4 >= var5) {
            return;
         }

         if(var3[var4].equals(var1)) {
            ListPreference var6 = this.mAnimations;
            CharSequence var7 = var2[var4];
            var6.setSummary(var7);
            return;
         }

         ++var4;
      }
   }

   private void updateState(boolean var1) {
      int var2 = 0;

      try {
         float[] var3 = this.mWindowManager.getAnimationScales();
         this.mAnimationScales = var3;
      } catch (RemoteException var27) {
         int var13 = Log.w("DisplaySettings", var27);
      }

      if(this.mAnimationScales != null) {
         if(this.mAnimationScales.length >= 1) {
            var2 = (int)(this.mAnimationScales[0] + 0.5F) % 10;
         }

         if(this.mAnimationScales.length >= 2) {
            int var4 = ((int)(this.mAnimationScales[1] + 0.5F) & 7) * 10;
            var2 += var4;
         }
      }

      int var5 = 0;
      byte var6 = 0;
      CharSequence[] var7 = this.mAnimations.getEntryValues();
      int var8 = 0;

      while(true) {
         int var9 = var7.length;
         if(var8 >= var9) {
            this.mAnimations.setValueIndex(var5);
            String var14 = this.mAnimations.getValue();
            this.updateAnimationsSummary(var14);
            CheckBoxPreference var15 = this.mAccelerometer;
            byte var16;
            if(System.getInt(this.getContentResolver(), "accelerometer_rotation", 0) != 0) {
               var16 = 1;
            } else {
               var16 = 0;
            }

            var15.setChecked((boolean)var16);
            ListPreference var17 = this.mTouchKeyLight;
            String var18 = String.valueOf(System.getInt(this.getContentResolver(), "button_key_light", 1500));
            var17.setValue(var18);
            ListPreference var19 = this.mTouchKeyLight;
            CharSequence var20 = this.mTouchKeyLight.getEntry();
            var19.setSummary(var20);
            CheckBoxPreference var21 = (CheckBoxPreference)this.findPreference("power_saving_mode");
            this.mPowerSavingMode = var21;
            CheckBoxPreference var22 = this.mPowerSavingMode;
            byte var23;
            if(System.getInt(this.getContentResolver(), "power_saving_mode", 0) != 0) {
               var23 = 1;
            } else {
               var23 = 0;
            }

            var22.setChecked((boolean)var23);
            if(this.mPenPreferredHand == null) {
               return;
            }

            int var24 = Log.d("DisplaySettings", "updateState() : pen settings");
            ListPreference var25 = this.mPenPreferredHand;
            CharSequence var26 = this.mPenPreferredHand.getEntry();
            var25.setSummary(var26);
            return;
         }

         int var10 = Integer.parseInt(var7[var8].toString());
         if(var10 <= var2 && var10 > var6) {
            var5 = var8;
         }

         ++var8;
      }
   }

   protected void onCreate(Bundle param1) {
      // $FF: Couldn't be decompiled
   }

   protected void onPause() {
      super.onPause();
      BroadcastReceiver var1 = this.mIntentReceiver;
      this.unregisterReceiver(var1);
      int var2 = Log.d("DisplaySettings", "onPause()");
      if("SPH-D710".equalsIgnoreCase("SGH-I727")) {
         BroadcastReceiver var3 = this.mBroadcastReceiver;
         this.unregisterReceiver(var3);
      }
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      String var3 = var1.getKey();
      int var4;
      if("animations".equals(var3)) {
         try {
            var4 = Integer.parseInt((String)var2);
            if(this.mAnimationScales.length >= 1) {
               float[] var5 = this.mAnimationScales;
               float var6 = (float)(var4 % 10);
               var5[0] = var6;
            }

            if(this.mAnimationScales.length >= 2) {
               float[] var7 = this.mAnimationScales;
               float var8 = (float)(var4 / 10 % 10);
               var7[1] = var8;
            }

            try {
               IWindowManager var9 = this.mWindowManager;
               float[] var10 = this.mAnimationScales;
               var9.setAnimationScales(var10);
            } catch (RemoteException var38) {
               int var26 = Log.w("DisplaySettings", var38);
            }

            this.updateAnimationsSummary(var2);
         } catch (NumberFormatException var39) {
            int var28 = Log.e("DisplaySettings", "could not persist animation setting", var39);
         }
      }

      if("screen_timeout".equals(var3)) {
         var4 = Integer.parseInt((String)var2);

         try {
            boolean var11 = System.putInt(this.getContentResolver(), "screen_off_timeout", var4);
            Intent var12 = new Intent("android.settings.SCREENTIMEOUT_CHANGED");
            this.sendBroadcast(var12);
         } catch (NumberFormatException var37) {
            int var30 = Log.e("DisplaySettings", "could not persist screen timeout setting", var37);
         }
      }

      if("touch_key_light".equals(var3)) {
         var4 = Integer.parseInt((String)var2);

         try {
            boolean var13 = System.putInt(this.getContentResolver(), "button_key_light", var4);
            ListPreference var14 = this.mTouchKeyLight;
            String var15 = String.valueOf(var4);
            var14.setValue(var15);
            ListPreference var16 = this.mTouchKeyLight;
            CharSequence var17 = this.mTouchKeyLight.getEntry();
            var16.setSummary(var17);
         } catch (NumberFormatException var36) {
            int var32 = Log.e("DisplaySettings", "could not persist Touch key light setting", var36);
         }
      }

      if("pen_hand_side".equals(var3)) {
         var4 = Integer.parseInt((String)var2);

         try {
            String var18 = "onPreferenceChange(KEY_PEN_PREFERRED_HAND): value =" + var4;
            int var19 = Log.d("DisplaySettings", var18);
            boolean var20 = System.putInt(this.getContentResolver(), "pen_hand_side", var4);
            ListPreference var21 = this.mPenPreferredHand;
            String var22 = String.valueOf(var4);
            var21.setValue(var22);
            ListPreference var23 = this.mPenPreferredHand;
            CharSequence var24 = this.mPenPreferredHand.getEntry();
            var23.setSummary(var24);
            this.setPreferredHandSide(var4);
         } catch (NumberFormatException var35) {
            int var34 = Log.e("DisplaySettings", "could not persist pen setting", var35);
         }
      }

      return true;
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      String var3 = "DisplaySettings";
      int var4 = Log.e(var3, "onPreferenceTreeClick()");
      CheckBoxPreference var5 = this.mAccelerometer;
      ContentResolver var6;
      byte var7;
      boolean var9;
      if(var2.equals(var5)) {
         var6 = this.getContentResolver();
         var3 = "accelerometer_rotation";
         if(this.mAccelerometer.isChecked()) {
            var7 = 1;
         } else {
            var7 = 0;
         }

         System.putInt(var6, var3, var7);
      } else {
         CheckBoxPreference var10 = this.mNotificationPulse;
         if(var2 == var10) {
            boolean var11 = this.mNotificationPulse.isChecked();
            var6 = this.getContentResolver();
            var3 = "notification_light_pulse";
            if(var11) {
               var7 = 1;
            } else {
               var7 = 0;
            }

            System.putInt(var6, var3, var7);
         } else {
            CheckBoxPreference var13 = this.mPowerSavingMode;
            if(var2.equals(var13)) {
               ContentResolver var14 = this.getContentResolver();
               var3 = "power_saving_mode";
               if(this.mPowerSavingMode.isChecked()) {
                  var7 = 1;
               } else {
                  var7 = 0;
               }

               System.putInt(var14, var3, var7);
               SecHardwareInterface.setAmoledACL(this.mPowerSavingMode.isChecked());
            } else {
               Preference var16 = this.mTvOutSettigs;
               if(var2.equals(var16)) {
                  var9 = false;
                  return var9;
               }

               PreferenceScreen var17 = this.gSensor;
               if(var2.equals(var17)) {
                  var9 = false;
                  return var9;
               }

               PreferenceScreen var18 = this.mScreenDisplay;
               if(var2.equals(var18)) {
                  int var19 = Log.e("DisplaySettings", "onPreferenceTreeClick(), mScreenDisplay");
                  var9 = false;
                  return var9;
               }

               Preference var20 = this.mModeSetting;
               if(var2.equals(var20)) {
                  var9 = false;
                  return var9;
               }
            }
         }
      }

      var9 = false;
      return var9;
   }

   protected void onResume() {
      super.onResume();
      IntentFilter var1 = new IntentFilter();
      var1.addAction("android.intent.action.BATTERY_CHANGED");
      BroadcastReceiver var2 = this.mIntentReceiver;
      this.registerReceiver(var2, var1);
      int var4 = Log.d("DisplaySettings", "onResume() : registerReceiver Intent.ACTION_BATTERY_CHANGED");
      if("SPH-D710".equalsIgnoreCase("SGH-I727")) {
         BroadcastReceiver var5 = this.mBroadcastReceiver;
         IntentFilter var6 = new IntentFilter("com.android.settings.BACKLIGHTCHANGED");
         this.registerReceiver(var5, var6, (String)null, (Handler)null);
      }

      ContentResolver var8 = this.mContentResolver;
      Uri var9 = System.getUriFor("accelerometer_rotation");
      DisplaySettings.OrientationObserver var10 = this.mOrientationObserver;
      var8.registerContentObserver(var9, (boolean)0, var10);
      this.updateState((boolean)1);
   }

   protected void onStop() {
      super.onStop();
      if(this.mBrightnessPreference.getDialog() != null && this.mBrightnessPreference.getDialog().isShowing()) {
         int var1 = Log.d("DisplaySettings", "onStop() : Dismiss brightness dialog");
         this.mBrightnessPreference.getDialog().dismiss();
      }

      ContentResolver var2 = this.mContentResolver;
      DisplaySettings.OrientationObserver var3 = this.mOrientationObserver;
      var2.unregisterContentObserver(var3);
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         String var4 = "mIntentReceiver.onReceive: action=" + var3 + ", intent=" + var2;
         int var5 = Log.d("DisplaySettings", var4);
         if("android.intent.action.BATTERY_CHANGED".equals(var3)) {
            DisplaySettings var6 = DisplaySettings.this;
            int var7 = var2.getIntExtra("temperature", 0);
            var6.mBattTemperature = var7;
            if(DisplaySettings.this.mBattTemperature == 0) {
               int var9 = Log.d("DisplaySettings", "mIntentReceiver.onReceive: BatteryManager.EXTRA_TEMPERATURE return 0");
            } else {
               DisplaySettings var10 = DisplaySettings.this;
               Editor var11 = DisplaySettings.this.mSharedPreference.edit();
               var10.editor = var11;
               Editor var12 = DisplaySettings.this.editor;
               int var13 = DisplaySettings.this.mBattTemperature;
               var12.putInt("battery_temperature", var13);
               boolean var15 = DisplaySettings.this.editor.commit();
               StringBuilder var16 = (new StringBuilder()).append("mIntentReceiver.onReceive: Battery temperature");
               int var17 = DisplaySettings.this.mBattTemperature;
               String var18 = var16.append(var17).toString();
               int var19 = Log.d("DisplaySettings", var18);
            }

            if(var2.getIntExtra("level", 0) <= 10) {
               if(var2.getIntExtra("status", 1) == 2) {
                  DisplaySettings.this.mBrightnessPreference.setEnabled((boolean)1);
               } else {
                  DisplaySettings.this.mBrightnessPreference.setEnabled((boolean)0);
               }
            } else {
               DisplaySettings.this.mBrightnessPreference.setEnabled((boolean)1);
            }
         }
      }
   }

   class 2 extends BroadcastReceiver {

      2() {}

      public void onReceive(Context var1, Intent var2) {
         if(var2.getAction().equals("com.android.settings.BACKLIGHTCHANGED")) {
            DisplaySettings.this.changeBacklightvalue();
         }
      }
   }

   private class OrientationObserver extends ContentObserver {

      OrientationObserver() {
         Handler var2 = new Handler();
         super(var2);
         int var3 = Log.d("DisplaySettings", "OrientationObserver()");
      }

      public void onChange(boolean var1) {
         super.onChange(var1);
         int var2 = Log.d("DisplaySettings", "OrientationObserver().onChange()");
         CheckBoxPreference var3 = DisplaySettings.this.mAccelerometer;
         byte var4;
         if(System.getInt(DisplaySettings.this.getContentResolver(), "accelerometer_rotation", 0) != 0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         var3.setChecked((boolean)var4);
      }
   }
}
