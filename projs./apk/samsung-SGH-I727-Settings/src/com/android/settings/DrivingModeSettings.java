package com.android.settings;

import android.content.ContentResolver;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings.System;

public class DrivingModeSettings extends PreferenceActivity {

   private static final String ALARM_NOTIFICATION = "alarm_notification";
   private static final String EMAIL_NOTIFICATION = "email_notification";
   private static final String INCOMING_CALL_NOTIFICATION = "incoming_call_notification";
   private static final String MESSAGE_NOTIFICATION = "message_notification";
   private static final String SCHEDULE_NOTIFICATION = "schedule_notification";
   private static final String TAG = "DrivingModeSettings";
   private static final String UNLOCK_SCREEN_CONTENTS = "unlock_screen_contents";
   private static final String VOICE_MAIL_NOTIFICATION = "voice_mail_notification";
   private CheckBoxPreference mAlarmNotification;
   private CheckBoxPreference mEmailNotification;
   private CheckBoxPreference mIncomingCallNotification;
   private CheckBoxPreference mMessageNotification;
   private CheckBoxPreference mScheduleNotification;
   private CheckBoxPreference mUnlockScreenContents;
   private CheckBoxPreference mVoiceMailNotification;


   public DrivingModeSettings() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968596);
      CheckBoxPreference var2 = (CheckBoxPreference)this.findPreference("incoming_call_notification");
      this.mIncomingCallNotification = var2;
      CheckBoxPreference var3 = (CheckBoxPreference)this.findPreference("message_notification");
      this.mMessageNotification = var3;
      CheckBoxPreference var4 = (CheckBoxPreference)this.findPreference("email_notification");
      this.mEmailNotification = var4;
      CheckBoxPreference var5 = (CheckBoxPreference)this.findPreference("voice_mail_notification");
      this.mVoiceMailNotification = var5;
      CheckBoxPreference var6 = (CheckBoxPreference)this.findPreference("alarm_notification");
      this.mAlarmNotification = var6;
      CheckBoxPreference var7 = (CheckBoxPreference)this.findPreference("schedule_notification");
      this.mScheduleNotification = var7;
      CheckBoxPreference var8 = (CheckBoxPreference)this.findPreference("unlock_screen_contents");
      this.mUnlockScreenContents = var8;
   }

   public void onDestroy() {
      super.onDestroy();
   }

   protected void onPause() {
      super.onPause();
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      CheckBoxPreference var3 = this.mIncomingCallNotification;
      ContentResolver var4;
      String var5;
      byte var6;
      if(var2.equals(var3)) {
         var4 = this.getContentResolver();
         var5 = "driving_mode_incoming_call_notification";
         if(this.mIncomingCallNotification.isChecked()) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         System.putInt(var4, var5, var6);
      } else {
         CheckBoxPreference var8 = this.mMessageNotification;
         if(var2.equals(var8)) {
            var4 = this.getContentResolver();
            var5 = "driving_mode_message_notification";
            if(this.mMessageNotification.isChecked()) {
               var6 = 1;
            } else {
               var6 = 0;
            }

            System.putInt(var4, var5, var6);
         } else {
            CheckBoxPreference var10 = this.mEmailNotification;
            if(var2.equals(var10)) {
               var4 = this.getContentResolver();
               var5 = "driving_mode_email_notification";
               if(this.mEmailNotification.isChecked()) {
                  var6 = 1;
               } else {
                  var6 = 0;
               }

               System.putInt(var4, var5, var6);
            } else {
               CheckBoxPreference var12 = this.mVoiceMailNotification;
               if(var2.equals(var12)) {
                  var4 = this.getContentResolver();
                  var5 = "driving_mode_voice_mail_notification";
                  if(this.mVoiceMailNotification.isChecked()) {
                     var6 = 1;
                  } else {
                     var6 = 0;
                  }

                  System.putInt(var4, var5, var6);
               } else {
                  CheckBoxPreference var14 = this.mAlarmNotification;
                  if(var2.equals(var14)) {
                     var4 = this.getContentResolver();
                     var5 = "driving_mode_alarm_notification";
                     if(this.mAlarmNotification.isChecked()) {
                        var6 = 1;
                     } else {
                        var6 = 0;
                     }

                     System.putInt(var4, var5, var6);
                  } else {
                     CheckBoxPreference var16 = this.mScheduleNotification;
                     if(var2.equals(var16)) {
                        var4 = this.getContentResolver();
                        var5 = "driving_mode_schedule_notification";
                        if(this.mScheduleNotification.isChecked()) {
                           var6 = 1;
                        } else {
                           var6 = 0;
                        }

                        System.putInt(var4, var5, var6);
                     } else {
                        CheckBoxPreference var18 = this.mUnlockScreenContents;
                        if(var2.equals(var18)) {
                           var4 = this.getContentResolver();
                           var5 = "driving_mode_unlock_screen_contents";
                           if(this.mUnlockScreenContents.isChecked()) {
                              var6 = 1;
                           } else {
                              var6 = 0;
                           }

                           System.putInt(var4, var5, var6);
                        }
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   protected void onResume() {
      super.onResume();
      CheckBoxPreference var1 = this.mIncomingCallNotification;
      byte var2;
      if(System.getInt(this.getContentResolver(), "driving_mode_incoming_call_notification", 0) != 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      var1.setChecked((boolean)var2);
      CheckBoxPreference var3 = this.mMessageNotification;
      byte var4;
      if(System.getInt(this.getContentResolver(), "driving_mode_message_notification", 0) != 0) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      var3.setChecked((boolean)var4);
      CheckBoxPreference var5 = this.mEmailNotification;
      byte var6;
      if(System.getInt(this.getContentResolver(), "driving_mode_email_notification", 0) != 0) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      var5.setChecked((boolean)var6);
      CheckBoxPreference var7 = this.mVoiceMailNotification;
      byte var8;
      if(System.getInt(this.getContentResolver(), "driving_mode_voice_mail_notification", 0) != 0) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      var7.setChecked((boolean)var8);
      CheckBoxPreference var9 = this.mAlarmNotification;
      byte var10;
      if(System.getInt(this.getContentResolver(), "driving_mode_alarm_notification", 0) != 0) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      var9.setChecked((boolean)var10);
      CheckBoxPreference var11 = this.mScheduleNotification;
      byte var12;
      if(System.getInt(this.getContentResolver(), "driving_mode_schedule_notification", 0) != 0) {
         var12 = 1;
      } else {
         var12 = 0;
      }

      var11.setChecked((boolean)var12);
      CheckBoxPreference var13 = this.mUnlockScreenContents;
      byte var14;
      if(System.getInt(this.getContentResolver(), "driving_mode_unlock_screen_contents", 0) != 0) {
         var14 = 1;
      } else {
         var14 = 0;
      }

      var13.setChecked((boolean)var14);
   }
}
