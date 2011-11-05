package com.android.settings;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings.System;
import android.util.Log;
import com.android.settings.RingerVolumePreference;
import com.android.settings.VibrationFeedbackPreference;

public class SoundSettings extends PreferenceActivity implements OnPreferenceChangeListener {

   private static final int FALLBACK_EMERGENCY_TONE_VALUE = 0;
   private static final int FALLBACK_SCREEN_TIMEOUT_VALUE = 30000;
   public static final String GPS_SET = "GPSSoundSettings";
   private static final String KEY_DTMF_TONE = "dtmf_tone";
   private static final String KEY_EMERGENCY_TONE = "emergency_tone";
   private static final String KEY_GPS_NOTIFICATION_SOUNDS = "gps_notification_sounds";
   private static final String KEY_HAPTIC_FEEDBACK = "haptic_feedback";
   private static final String KEY_LOCK_SOUNDS = "lock_sounds";
   private static final String KEY_NOTIFICATION_PULSE = "notification_pulse";
   private static final String KEY_SILENT = "silent";
   private static final String KEY_SOUND_EFFECTS = "sound_effects";
   private static final String KEY_SOUND_SETTINGS = "sound_settings";
   private static final String KEY_VIBRATE = "vibrate";
   private static final String KEY_VIBRATION_INTENSITY = "vibration_feedback_intensity";
   private static final String TAG = "SoundSettings";
   private static final String VALUE_VIBRATE_ALWAYS = "always";
   private static final String VALUE_VIBRATE_NEVER = "never";
   private static final String VALUE_VIBRATE_ONLY_SILENT = "silent";
   private static final String VALUE_VIBRATE_UNLESS_SILENT = "notsilent";
   private static Context myContext;
   SharedPreferences GpsSoundSettings;
   private AudioManager mAudioManager;
   private CheckBoxPreference mDtmfTone;
   Editor mEditor;
   private CheckBoxPreference mGpsNotificationSounds;
   private CheckBoxPreference mHapticFeedback;
   private CheckBoxPreference mLockSounds;
   private CheckBoxPreference mNotificationPulse;
   private BroadcastReceiver mReceiver;
   private RingerVolumePreference mRingerVolume;
   private CheckBoxPreference mSilent;
   private CheckBoxPreference mSoundEffects;
   private PreferenceGroup mSoundSettings;
   private ListPreference mVibrate;
   private VibrationFeedbackPreference mVibrationIntensity;


   public SoundSettings() {
      SoundSettings.1 var1 = new SoundSettings.1();
      this.mReceiver = var1;
   }

   private String getPhoneVibrateSettingValue() {
      boolean var1;
      if(System.getInt(this.getContentResolver(), "vibrate_in_silent", 1) == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      int var2 = this.mAudioManager.getVibrateSetting(0);
      String var3;
      if(var1) {
         if(var2 == 0) {
            this.mAudioManager.setVibrateSetting(0, 2);
         }

         if(var2 == 1) {
            var3 = "always";
         } else {
            var3 = "silent";
         }
      } else {
         if(var2 == 2) {
            this.mAudioManager.setVibrateSetting(0, 0);
         }

         if(var2 == 1) {
            var3 = "notsilent";
         } else {
            var3 = "never";
         }
      }

      return var3;
   }

   private void setPhoneVibrateSettingValue(String var1) {
      byte var2;
      boolean var3;
      if(var1.equals("notsilent")) {
         var2 = 1;
         var3 = false;
      } else if(var1.equals("never")) {
         var2 = 0;
         var3 = false;
      } else if(var1.equals("silent")) {
         var2 = 2;
         var3 = true;
      } else {
         var2 = 1;
         var3 = true;
      }

      ContentResolver var4 = this.getContentResolver();
      String var5 = "vibrate_in_silent";
      byte var6;
      if(var3) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      System.putInt(var4, var5, var6);
      if(this.mSilent.isChecked()) {
         AudioManager var8 = this.mAudioManager;
         byte var9;
         if(var3) {
            var9 = 1;
         } else {
            var9 = 0;
         }

         var8.setRingerMode(var9);
      }

      this.mAudioManager.setVibrateSetting(0, var2);
   }

   private void updateState(boolean var1) {
      byte var2;
      if(this.mAudioManager.getRingerMode() != 2) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      byte var3 = this.mSilent.isChecked();
      if(var2 != var3 || var1) {
         this.mSilent.setChecked((boolean)var2);
      }

      String var4 = this.getPhoneVibrateSettingValue();
      String var5 = this.mVibrate.getValue();
      if(!var4.equals(var5) || var1) {
         this.mVibrate.setValue(var4);
      }

      ListPreference var6 = this.mVibrate;
      CharSequence var7 = this.mVibrate.getEntry();
      var6.setSummary(var7);
      boolean var8;
      if((System.getInt(this.getContentResolver(), "mode_ringer_streams_affected", 0) & 16) != 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      CheckBoxPreference var9 = this.mSilent;
      int var10;
      if(var8) {
         var10 = 2131231240;
      } else {
         var10 = 2131231239;
      }

      var9.setSummary(var10);
   }

   protected void onCreate(Bundle param1) {
      // $FF: Couldn't be decompiled
   }

   protected void onPause() {
      super.onPause();
      BroadcastReceiver var1 = this.mReceiver;
      this.unregisterReceiver(var1);
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      String var3 = var1.getKey();
      if("emergency_tone".equals(var3)) {
         int var4 = Integer.parseInt((String)var2);

         try {
            boolean var5 = System.putInt(this.getContentResolver(), "emergency_tone", var4);
         } catch (NumberFormatException var10) {
            int var7 = Log.e("SoundSettings", "could not persist emergency tone setting", var10);
         }
      } else {
         ListPreference var8 = this.mVibrate;
         if(var1.equals(var8)) {
            String var9 = var2.toString();
            this.setPhoneVibrateSettingValue(var9);
            this.updateState((boolean)0);
         }
      }

      return true;
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      boolean var3 = true;
      CheckBoxPreference var4 = this.mSilent;
      if(var2.equals(var4)) {
         if(this.mSilent.isChecked()) {
            int var5 = System.getInt(this.getContentResolver(), "vibrate_in_silent", 1);
            boolean var6;
            if(1 == var5) {
               var6 = true;
            } else {
               var6 = false;
            }

            AudioManager var7 = this.mAudioManager;
            byte var8;
            if(var6) {
               var8 = 1;
            } else {
               var8 = 0;
            }

            var7.setRingerMode(var8);
         } else {
            this.mAudioManager.setRingerMode(2);
         }

         this.updateState((boolean)0);
      } else {
         CheckBoxPreference var9 = this.mGpsNotificationSounds;
         if(var2 == var9) {
            SharedPreferences var10 = myContext.getSharedPreferences("GPSSoundSettings", 2);
            this.GpsSoundSettings = var10;
            Editor var11 = this.GpsSoundSettings.edit();
            this.mEditor = var11;
            Editor var12 = this.mEditor;
            boolean var13 = this.mGpsNotificationSounds.isChecked();
            var12.putBoolean("GPSSoundSetting", var13);
            boolean var15 = this.mEditor.commit();
         } else {
            CheckBoxPreference var16 = this.mDtmfTone;
            byte var27;
            String var29;
            ContentResolver var28;
            if(var2.equals(var16)) {
               var28 = this.getContentResolver();
               var29 = "dtmf_tone";
               if(this.mDtmfTone.isChecked()) {
                  var27 = 1;
               } else {
                  var27 = 0;
               }

               System.putInt(var28, var29, var27);
            } else {
               CheckBoxPreference var18 = this.mSoundEffects;
               if(var2.equals(var18)) {
                  if(this.mSoundEffects.isChecked()) {
                     this.mAudioManager.loadSoundEffects();
                  } else {
                     this.mAudioManager.unloadSoundEffects();
                  }

                  var28 = this.getContentResolver();
                  var29 = "sound_effects_enabled";
                  if(this.mSoundEffects.isChecked()) {
                     var27 = 1;
                  } else {
                     var27 = 0;
                  }

                  System.putInt(var28, var29, var27);
               } else {
                  CheckBoxPreference var20 = this.mHapticFeedback;
                  if(var2.equals(var20)) {
                     var28 = this.getContentResolver();
                     var29 = "haptic_feedback_enabled";
                     if(this.mHapticFeedback.isChecked()) {
                        var27 = 1;
                     } else {
                        var27 = 0;
                     }

                     System.putInt(var28, var29, var27);
                  } else {
                     CheckBoxPreference var22 = this.mLockSounds;
                     if(var2.equals(var22)) {
                        var28 = this.getContentResolver();
                        var29 = "lockscreen_sounds_enabled";
                        if(this.mLockSounds.isChecked()) {
                           var27 = 1;
                        } else {
                           var27 = 0;
                        }

                        System.putInt(var28, var29, var27);
                     } else {
                        CheckBoxPreference var24 = this.mNotificationPulse;
                        if(var2.equals(var24)) {
                           boolean var25 = this.mNotificationPulse.isChecked();
                           var28 = this.getContentResolver();
                           var29 = "notification_light_pulse";
                           if(var25) {
                              var27 = 1;
                           } else {
                              var27 = 0;
                           }

                           System.putInt(var28, var29, var27);
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   protected void onResume() {
      super.onResume();
      this.updateState((boolean)1);
      IntentFilter var1 = new IntentFilter("android.media.RINGER_MODE_CHANGED");
      BroadcastReceiver var2 = this.mReceiver;
      this.registerReceiver(var2, var1);
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         if(var2.getAction().equals("android.media.RINGER_MODE_CHANGED")) {
            if(SoundSettings.this.mAudioManager.getRingerMode() != 2) {
               SoundSettings var3 = SoundSettings.this;
               RingerVolumePreference var4 = (RingerVolumePreference)SoundSettings.this.findPreference("ring_volume");
               var3.mRingerVolume = var4;
               Dialog var6 = SoundSettings.this.mRingerVolume.getDialog();
               if(var6 != null) {
                  int var7 = Log.d("SoundSettings", "Volume control is shown");
                  var6.dismiss();
               } else {
                  String var8 = "Volume control is not shown" + var6;
                  int var9 = Log.d("SoundSettings", var8);
               }
            }

            SoundSettings.this.updateState((boolean)0);
         }
      }
   }
}
