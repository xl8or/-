package com.android.settings;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Vibrator;
import android.preference.SeekBarPreference;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class VibrationFeedbackPreference extends SeekBarPreference implements OnSeekBarChangeListener, OnKeyListener {

   private static final int MAXIMUM_VIBRATION_INTENSITY = 5;
   private static final int[] SEEKBAR_ID = new int[]{2131427565, 2131427566, 2131427567};
   private static final String TAG = "VibrationFeedbackPreference";
   private int currentLevel;
   private SeekBar mHapticFeedbackSeekBar;
   private int mHapticFeedbackVibrationIntensity;
   private SeekBar mIncomingCallSeekBar;
   private int mIncomingCallVibrationIntensity;
   private int[] mIntensityValue;
   private SeekBar mNotificationSeekBar;
   private int mNotificationVibrationIntensity;
   private int mOldHapticFeedbackVibrationIntensity;
   private int mOldHapticFeedbackVibrationSlideLevel;
   private int mOldIncomingCallVibrationSlideLevel;
   private int mOldNotificationVibrationSlideLevel;
   Vibrator mVibrator;


   public VibrationFeedbackPreference(Context var1, AttributeSet var2) {
      super(var1, var2);
      int[] var3 = new int[]{0, 1000, 3500, 6000, 8000, 8700};
      this.mIntensityValue = var3;
      Vibrator var4 = (Vibrator)this.getContext().getSystemService("vibrator");
      this.mVibrator = var4;
      this.currentLevel = 0;
      this.setDialogLayoutResource(2130903112);
      if(!"GT-I9103".equals("SGH-I727") && !"GT-I9220".equals("SGH-I727")) {
         if("SGH-T989".equals("SGH-I727")) {
            this.mIntensityValue[1] = 2500;
            this.mIntensityValue[2] = 4500;
            this.mIntensityValue[3] = 7000;
            this.mIntensityValue[4] = 8500;
            this.mIntensityValue[5] = 10000;
         } else if("SPH-D710".equals("SGH-I727")) {
            this.mIntensityValue[5] = 9750;
         }
      } else {
         this.mIntensityValue[1] = 2000;
         this.mIntensityValue[2] = 4000;
         this.mIntensityValue[3] = 6000;
         this.mIntensityValue[4] = 8000;
         this.mIntensityValue[5] = 10000;
      }
   }

   private int convertIntensityToLevel(int var1) {
      int var2 = 0;

      int var7;
      while(true) {
         int var3 = this.mIntensityValue.length;
         if(var2 >= var3) {
            int var8 = this.mIntensityValue.length;
            if(var2 == var8) {
               var2 += -1;
               String var9 = "Intensity : " + var1;
               int var10 = Log.e("VibrationFeedbackPreference", var9);
               String var11 = "There is no matched item. return max level" + var2;
               int var12 = Log.e("VibrationFeedbackPreference", var11);
            }

            var7 = var2;
            break;
         }

         int var4 = this.mIntensityValue[var2];
         if(var1 == var4) {
            String var5 = "convertIntensityToLevel : return level" + var2;
            int var6 = Log.d("VibrationFeedbackPreference", var5);
            var7 = var2;
            break;
         }

         ++var2;
      }

      return var7;
   }

   private int convertLevelToIntensity(int var1) {
      int var2 = this.mIntensityValue.length;
      int var7;
      if(var1 < var2) {
         StringBuilder var3 = (new StringBuilder()).append("convertLevelToIntensity : mLevel[").append(var1).append("]").append("Intensity[");
         int var4 = this.mIntensityValue[var1];
         String var5 = var3.append(var4).append("]").toString();
         int var6 = Log.d("VibrationFeedbackPreference", var5);
         var7 = this.mIntensityValue[var1];
      } else {
         StringBuilder var8 = (new StringBuilder()).append("convertLevelToIntensity : return max intensity ");
         int[] var9 = this.mIntensityValue;
         int var10 = this.mIntensityValue.length - 1;
         int var11 = var9[var10];
         String var12 = var8.append(var11).toString();
         int var13 = Log.e("VibrationFeedbackPreference", var12);
         int[] var14 = this.mIntensityValue;
         int var15 = this.mIntensityValue.length - 1;
         var7 = var14[var15];
      }

      return var7;
   }

   private void setHapticFeedbackVibrationIntensity(int var1) {
      String var2 = "setHapticFeedbackVibrationIntensity : level[" + var1 + "]";
      int var3 = Log.d("VibrationFeedbackPreference", var2);
      int var4 = this.convertLevelToIntensity(var1);
      this.mHapticFeedbackVibrationIntensity = var4;
      StringBuilder var5 = (new StringBuilder()).append("setHapticFeedbackVibrationIntensity : mHapticFeedbackVibrationIntensity[");
      int var6 = this.mHapticFeedbackVibrationIntensity;
      String var7 = var5.append(var6).append("]").toString();
      int var8 = Log.d("VibrationFeedbackPreference", var7);
      ContentResolver var9 = this.getContext().getContentResolver();
      int var10 = this.mHapticFeedbackVibrationIntensity;
      System.putInt(var9, "VIB_FEEDBACK_MAGNITUDE", var10);
      StringBuilder var12 = (new StringBuilder()).append("setHapticFeedbackVibrationIntensity : Settings.System.VIB_FEEDBACK_MAGNITUDE[");
      int var13 = this.mHapticFeedbackVibrationIntensity;
      String var14 = var12.append(var13).append("]").toString();
      int var15 = Log.d("VibrationFeedbackPreference", var14);
   }

   private void setIncomingCallVibrationIntensity(int var1) {
      String var2 = "setIncomingCallVibrationIntensity : level[" + var1 + "]";
      int var3 = Log.d("VibrationFeedbackPreference", var2);
      boolean var4 = System.putInt(this.getContext().getContentResolver(), "VIB_RECVCALL_MAGNITUDE", var1);
      String var5 = "setIncomingCallVibrationIntensity : Settings.System.VIB_RECVCALL_MAGNITUDE[" + var1 + "]";
      int var6 = Log.d("VibrationFeedbackPreference", var5);
   }

   private void setNotificationVibrationIntensity(int var1) {
      String var2 = "setNotificationVibrationIntensity : level[" + var1 + "]";
      int var3 = Log.d("VibrationFeedbackPreference", var2);
      boolean var4 = System.putInt(this.getContext().getContentResolver(), "VIB_NOTIFICATION_MAGNITUDE", var1);
      String var5 = "setNotificationVibrationIntensity : Settings.System.VIB_NOTIFICATION_MAGNITUDE[" + var1 + "]";
      int var6 = Log.d("VibrationFeedbackPreference", var5);
   }

   protected void onBindDialogView(View var1) {
      super.onBindDialogView(var1);
      int var2 = SEEKBAR_ID[0];
      SeekBar var3 = (SeekBar)var1.findViewById(var2);
      this.mIncomingCallSeekBar = var3;
      this.mIncomingCallSeekBar.setSoundEffectsEnabled((boolean)1);
      int var4 = SEEKBAR_ID[1];
      SeekBar var5 = (SeekBar)var1.findViewById(var4);
      this.mNotificationSeekBar = var5;
      this.mNotificationSeekBar.setSoundEffectsEnabled((boolean)1);
      int var6 = SEEKBAR_ID[2];
      SeekBar var7 = (SeekBar)var1.findViewById(var6);
      this.mHapticFeedbackSeekBar = var7;
      this.mHapticFeedbackSeekBar.setSoundEffectsEnabled((boolean)1);
      this.mIncomingCallSeekBar.setMax(5);
      int var8 = Log.d("VibrationFeedbackPreference", "onBindDialogView : mIncomingCallSeekBar.setMax(MAXIMUM_VIBRATION_INTENSITY[5])");
      int var9 = System.getInt(this.getContext().getContentResolver(), "VIB_RECVCALL_MAGNITUDE", 4);
      this.mOldIncomingCallVibrationSlideLevel = var9;
      StringBuilder var10 = (new StringBuilder()).append("onBindDialogView : Settings.System.VIB_RECVCALL_MAGNITUDE[");
      int var11 = this.mOldIncomingCallVibrationSlideLevel;
      String var12 = var10.append(var11).append("]").toString();
      int var13 = Log.d("VibrationFeedbackPreference", var12);
      StringBuilder var14 = (new StringBuilder()).append("onBindDialogView : mOldSlideLevel[");
      int var15 = this.mOldIncomingCallVibrationSlideLevel;
      String var16 = var14.append(var15).append("]").toString();
      int var17 = Log.d("VibrationFeedbackPreference", var16);
      SeekBar var18 = this.mIncomingCallSeekBar;
      int var19 = this.mOldIncomingCallVibrationSlideLevel;
      var18.setProgress(var19);
      StringBuilder var20 = (new StringBuilder()).append("onBindDialogView : mSeekBar.setProgress(");
      int var21 = this.mOldIncomingCallVibrationSlideLevel;
      String var22 = var20.append(var21).append(")").toString();
      int var23 = Log.d("VibrationFeedbackPreference", var22);
      this.mIncomingCallSeekBar.setOnSeekBarChangeListener(this);
      this.mNotificationSeekBar.setMax(5);
      int var24 = Log.d("VibrationFeedbackPreference", "onBindDialogView : mNotificationSeekBar.setMax(MAXIMUM_VIBRATION_INTENSITY[5])");
      int var25 = System.getInt(this.getContext().getContentResolver(), "VIB_NOTIFICATION_MAGNITUDE", 4);
      this.mOldNotificationVibrationSlideLevel = var25;
      StringBuilder var26 = (new StringBuilder()).append("onBindDialogView : Settings.System.VIB_NOTIFICATION_MAGNITUDE[");
      int var27 = this.mOldNotificationVibrationSlideLevel;
      String var28 = var26.append(var27).append("]").toString();
      int var29 = Log.d("VibrationFeedbackPreference", var28);
      StringBuilder var30 = (new StringBuilder()).append("onBindDialogView : mOldSlideLevel[");
      int var31 = this.mOldNotificationVibrationSlideLevel;
      String var32 = var30.append(var31).append("]").toString();
      int var33 = Log.d("VibrationFeedbackPreference", var32);
      SeekBar var34 = this.mNotificationSeekBar;
      int var35 = this.mOldNotificationVibrationSlideLevel;
      var34.setProgress(var35);
      StringBuilder var36 = (new StringBuilder()).append("onBindDialogView : mNotificationSeekBar.setProgress(");
      int var37 = this.mOldNotificationVibrationSlideLevel;
      String var38 = var36.append(var37).append(")").toString();
      int var39 = Log.d("VibrationFeedbackPreference", var38);
      this.mNotificationSeekBar.setOnSeekBarChangeListener(this);
      this.mHapticFeedbackSeekBar.setMax(5);
      int var40 = Log.d("VibrationFeedbackPreference", "onBindDialogView : mHapticFeedbackSeekBar.setMax(MAXIMUM_VIBRATION_INTENSITY[5])");
      int var41 = System.getInt(this.getContext().getContentResolver(), "VIB_FEEDBACK_MAGNITUDE", 10000);
      this.mOldHapticFeedbackVibrationIntensity = var41;
      StringBuilder var42 = (new StringBuilder()).append("onBindDialogView : Settings.System.VIB_FEEDBACK_MAGNITUDE[");
      int var43 = this.mOldHapticFeedbackVibrationIntensity;
      String var44 = var42.append(var43).append("]").toString();
      int var45 = Log.d("VibrationFeedbackPreference", var44);
      int var46 = this.mOldHapticFeedbackVibrationIntensity;
      int var47 = this.convertIntensityToLevel(var46);
      this.mOldHapticFeedbackVibrationSlideLevel = var47;
      int var48 = this.mOldHapticFeedbackVibrationSlideLevel;
      this.currentLevel = var48;
      StringBuilder var49 = (new StringBuilder()).append("onBindDialogView : mOldSlideLevel[");
      int var50 = this.mOldHapticFeedbackVibrationSlideLevel;
      String var51 = var49.append(var50).append("]").toString();
      int var52 = Log.d("VibrationFeedbackPreference", var51);
      SeekBar var53 = this.mHapticFeedbackSeekBar;
      int var54 = this.mOldHapticFeedbackVibrationSlideLevel;
      var53.setProgress(var54);
      StringBuilder var55 = (new StringBuilder()).append("onBindDialogView : mHapticFeedbackSeekBar.setProgress(");
      int var56 = this.mOldHapticFeedbackVibrationSlideLevel;
      String var57 = var55.append(var56).append(")").toString();
      int var58 = Log.d("VibrationFeedbackPreference", var57);
      this.mHapticFeedbackSeekBar.setOnSeekBarChangeListener(this);
      var1.setOnKeyListener(this);
      var1.setFocusableInTouchMode((boolean)1);
      boolean var59 = var1.requestFocus();
   }

   protected void onDialogClosed(boolean var1) {
      super.onDialogClosed(var1);
      if(var1) {
         StringBuilder var2 = (new StringBuilder()).append("onDialogClosed : positiveResult = true, Incoming call progress[");
         int var3 = this.mIncomingCallSeekBar.getProgress();
         String var4 = var2.append(var3).append("]").toString();
         int var5 = Log.d("VibrationFeedbackPreference", var4);
         int var6 = this.mIncomingCallSeekBar.getProgress();
         this.mIncomingCallVibrationIntensity = var6;
         StringBuilder var7 = (new StringBuilder()).append("onDialogClosed : mIncomingCallVibrationIntensity[");
         int var8 = this.mIncomingCallVibrationIntensity;
         String var9 = var7.append(var8).append("]").toString();
         int var10 = Log.d("VibrationFeedbackPreference", var9);
         ContentResolver var11 = this.getContext().getContentResolver();
         int var12 = this.mIncomingCallVibrationIntensity;
         System.putInt(var11, "VIB_RECVCALL_MAGNITUDE", var12);
         StringBuilder var14 = (new StringBuilder()).append("onDialogClosed : Settings.System.VIB_RECVCALL_MAGNITUDE[");
         int var15 = this.mIncomingCallVibrationIntensity;
         String var16 = var14.append(var15).append("]").toString();
         int var17 = Log.d("VibrationFeedbackPreference", var16);
         StringBuilder var18 = (new StringBuilder()).append("onDialogClosed : positiveResult = true, Notification progress[");
         int var19 = this.mNotificationSeekBar.getProgress();
         String var20 = var18.append(var19).append("]").toString();
         int var21 = Log.d("VibrationFeedbackPreference", var20);
         int var22 = this.mNotificationSeekBar.getProgress();
         this.mNotificationVibrationIntensity = var22;
         StringBuilder var23 = (new StringBuilder()).append("onDialogClosed : mNotificationVibrationIntensity[");
         int var24 = this.mNotificationVibrationIntensity;
         String var25 = var23.append(var24).append("]").toString();
         int var26 = Log.d("VibrationFeedbackPreference", var25);
         ContentResolver var27 = this.getContext().getContentResolver();
         int var28 = this.mNotificationVibrationIntensity;
         System.putInt(var27, "VIB_NOTIFICATION_MAGNITUDE", var28);
         StringBuilder var30 = (new StringBuilder()).append("onDialogClosed : Settings.System.VIB_NOTIFICATION_MAGNITUDE[");
         int var31 = this.mNotificationVibrationIntensity;
         String var32 = var30.append(var31).append("]").toString();
         int var33 = Log.d("VibrationFeedbackPreference", var32);
         StringBuilder var34 = (new StringBuilder()).append("onDialogClosed : positiveResult = true, Haptic feedback progress[");
         int var35 = this.mHapticFeedbackSeekBar.getProgress();
         String var36 = var34.append(var35).append("]").toString();
         int var37 = Log.d("VibrationFeedbackPreference", var36);
         int var38 = this.mHapticFeedbackSeekBar.getProgress();
         int var39 = this.convertLevelToIntensity(var38);
         this.mHapticFeedbackVibrationIntensity = var39;
         StringBuilder var40 = (new StringBuilder()).append("onDialogClosed : mHapticFeedbackVibrationIntensity[");
         int var41 = this.mHapticFeedbackVibrationIntensity;
         String var42 = var40.append(var41).append("]").toString();
         int var43 = Log.d("VibrationFeedbackPreference", var42);
         ContentResolver var44 = this.getContext().getContentResolver();
         int var45 = this.mHapticFeedbackVibrationIntensity;
         System.putInt(var44, "VIB_FEEDBACK_MAGNITUDE", var45);
         StringBuilder var47 = (new StringBuilder()).append("onDialogClosed : Settings.System.VIB_FEEDBACK_MAGNITUDE[");
         int var48 = this.mHapticFeedbackVibrationIntensity;
         String var49 = var47.append(var48).append("]").toString();
         int var50 = Log.d("VibrationFeedbackPreference", var49);
      } else {
         int var51 = this.mOldIncomingCallVibrationSlideLevel;
         this.setIncomingCallVibrationIntensity(var51);
         int var52 = this.mOldNotificationVibrationSlideLevel;
         this.setNotificationVibrationIntensity(var52);
         int var53 = this.mOldHapticFeedbackVibrationSlideLevel;
         this.setHapticFeedbackVibrationIntensity(var53);
      }
   }

   public boolean onKey(View var1, int var2, KeyEvent var3) {
      String var4 = "onKey() Keycode : " + var2;
      int var5 = Log.e("VibrationFeedbackPreference", var4);
      byte var6;
      if(var3.getAction() == 0) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      String var7 = " onKey() Keycode: isdown : " + var6;
      int var8 = Log.e("VibrationFeedbackPreference", var7);
      boolean var9;
      switch(var2) {
      case 24:
         if(var6 != 0 && this.currentLevel < 5) {
            int var13 = this.currentLevel + 1;
            this.currentLevel = var13;
            SeekBar var14 = this.mHapticFeedbackSeekBar;
            int var15 = this.currentLevel;
            var14.setProgress(var15);
         }

         var9 = true;
         break;
      case 25:
         if(var6 != 0 && this.currentLevel > 0) {
            int var10 = this.currentLevel - 1;
            this.currentLevel = var10;
            SeekBar var11 = this.mHapticFeedbackSeekBar;
            int var12 = this.currentLevel;
            var11.setProgress(var12);
         }

         var9 = true;
         break;
      default:
         var9 = false;
      }

      return var9;
   }

   public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
      SeekBar var4 = this.mIncomingCallSeekBar;
      if(var1.equals(var4)) {
         String var5 = "onProgressChanged : setIncomingCallVibrationIntensity(progress[" + var2 + "])";
         int var6 = Log.d("VibrationFeedbackPreference", var5);
         this.setIncomingCallVibrationIntensity(var2);
         this.mVibrator.vibrateCall(13);
      } else {
         SeekBar var7 = this.mNotificationSeekBar;
         if(var1.equals(var7)) {
            String var8 = "onProgressChanged : setNotificationVibrationIntensity(progress[" + var2 + "])";
            int var9 = Log.d("VibrationFeedbackPreference", var8);
            this.setNotificationVibrationIntensity(var2);
            this.mVibrator.vibrateNotification(17);
         } else {
            this.currentLevel = var2;
            String var10 = "onProgressChanged : setHapticFeedbackVibrationIntensity(progress[" + var2 + "])";
            int var11 = Log.d("VibrationFeedbackPreference", var10);
            this.setHapticFeedbackVibrationIntensity(var2);
            this.mVibrator.vibrateImmVibe(9);
         }
      }
   }

   public void onStartTrackingTouch(SeekBar var1) {}

   public void onStopTrackingTouch(SeekBar var1) {}
}
