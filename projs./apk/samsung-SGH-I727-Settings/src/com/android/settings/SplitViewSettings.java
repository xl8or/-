package com.android.settings;

import android.content.ContentResolver;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings.System;

public class SplitViewSettings extends PreferenceActivity {

   private static final String SPLITEVIEW_CALENDAR_KEY = "splitview_calendar_key";
   private static final String SPLITEVIEW_IM_KEY = "splitview_im_key";
   private static final String SPLITEVIEW_MEMO_KEY = "splitview_memo_key";
   private static final String SPLITEVIEW_MESSAGE_KEY = "splitview_message_key";
   private static final String SPLITEVIEW_MUSIC_KEY = "splitview_music_key";
   private static final String SPLITEVIEW_MYFILES_KEY = "splitview_myfiles_key";
   private static final String SPLITEVIEW_PHONE_KEY = "splitview_phone_key";
   private static final String SPLITEVIEW_SOCIALHUB_KEY = "splitview_socialhub_key";
   private static final String TAG = "SplitViewSettings";
   private CheckBoxPreference mSpliteviewCalendar;
   private CheckBoxPreference mSpliteviewIm;
   private CheckBoxPreference mSpliteviewMemo;
   private CheckBoxPreference mSpliteviewMessage;
   private CheckBoxPreference mSpliteviewMusic;
   private CheckBoxPreference mSpliteviewMyfiles;
   private CheckBoxPreference mSpliteviewPhone;
   private CheckBoxPreference mSpliteviewSocialhub;


   public SplitViewSettings() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968615);
      CheckBoxPreference var2 = (CheckBoxPreference)this.findPreference("splitview_phone_key");
      this.mSpliteviewPhone = var2;
      CheckBoxPreference var3 = (CheckBoxPreference)this.findPreference("splitview_message_key");
      this.mSpliteviewMessage = var3;
      CheckBoxPreference var4 = (CheckBoxPreference)this.findPreference("splitview_calendar_key");
      this.mSpliteviewCalendar = var4;
      CheckBoxPreference var5 = (CheckBoxPreference)this.findPreference("splitview_memo_key");
      this.mSpliteviewMemo = var5;
      CheckBoxPreference var6 = (CheckBoxPreference)this.findPreference("splitview_music_key");
      this.mSpliteviewMusic = var6;
      CheckBoxPreference var7 = (CheckBoxPreference)this.findPreference("splitview_myfiles_key");
      this.mSpliteviewMyfiles = var7;
      CheckBoxPreference var8 = (CheckBoxPreference)this.findPreference("splitview_socialhub_key");
      this.mSpliteviewSocialhub = var8;
      CheckBoxPreference var9 = (CheckBoxPreference)this.findPreference("splitview_im_key");
      this.mSpliteviewIm = var9;
   }

   public void onDestroy() {
      super.onDestroy();
   }

   protected void onPause() {
      super.onPause();
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      CheckBoxPreference var3 = this.mSpliteviewPhone;
      ContentResolver var4;
      String var5;
      byte var6;
      if(var2.equals(var3)) {
         var4 = this.getContentResolver();
         var5 = "spliteview_mode_phone";
         if(this.mSpliteviewPhone.isChecked()) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         System.putInt(var4, var5, var6);
      } else {
         CheckBoxPreference var8 = this.mSpliteviewMessage;
         if(var2.equals(var8)) {
            var4 = this.getContentResolver();
            var5 = "spliteview_mode_message";
            if(this.mSpliteviewMessage.isChecked()) {
               var6 = 1;
            } else {
               var6 = 0;
            }

            System.putInt(var4, var5, var6);
         } else {
            CheckBoxPreference var10 = this.mSpliteviewCalendar;
            if(var2.equals(var10)) {
               var4 = this.getContentResolver();
               var5 = "spliteview_mode_calendar";
               if(this.mSpliteviewCalendar.isChecked()) {
                  var6 = 1;
               } else {
                  var6 = 0;
               }

               System.putInt(var4, var5, var6);
            } else {
               CheckBoxPreference var12 = this.mSpliteviewMemo;
               if(var2.equals(var12)) {
                  var4 = this.getContentResolver();
                  var5 = "spliteview_mode_memo";
                  if(this.mSpliteviewMemo.isChecked()) {
                     var6 = 1;
                  } else {
                     var6 = 0;
                  }

                  System.putInt(var4, var5, var6);
               } else {
                  CheckBoxPreference var14 = this.mSpliteviewMusic;
                  if(var2.equals(var14)) {
                     var4 = this.getContentResolver();
                     var5 = "spliteview_mode_music";
                     if(this.mSpliteviewMusic.isChecked()) {
                        var6 = 1;
                     } else {
                        var6 = 0;
                     }

                     System.putInt(var4, var5, var6);
                  } else {
                     CheckBoxPreference var16 = this.mSpliteviewMyfiles;
                     if(var2.equals(var16)) {
                        var4 = this.getContentResolver();
                        var5 = "spliteview_mode_myfiles";
                        if(this.mSpliteviewMyfiles.isChecked()) {
                           var6 = 1;
                        } else {
                           var6 = 0;
                        }

                        System.putInt(var4, var5, var6);
                     } else {
                        CheckBoxPreference var18 = this.mSpliteviewSocialhub;
                        if(var2.equals(var18)) {
                           var4 = this.getContentResolver();
                           var5 = "spliteview_mode_socialhub";
                           if(this.mSpliteviewSocialhub.isChecked()) {
                              var6 = 1;
                           } else {
                              var6 = 0;
                           }

                           System.putInt(var4, var5, var6);
                        } else {
                           CheckBoxPreference var20 = this.mSpliteviewIm;
                           if(var2.equals(var20)) {
                              var4 = this.getContentResolver();
                              var5 = "spliteview_mode_im";
                              if(this.mSpliteviewIm.isChecked()) {
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
      }

      return false;
   }

   protected void onResume() {
      super.onResume();
      CheckBoxPreference var1 = this.mSpliteviewPhone;
      byte var2;
      if(System.getInt(this.getContentResolver(), "spliteview_mode_phone", 0) != 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      var1.setChecked((boolean)var2);
      CheckBoxPreference var3 = this.mSpliteviewMessage;
      byte var4;
      if(System.getInt(this.getContentResolver(), "spliteview_mode_message", 0) != 0) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      var3.setChecked((boolean)var4);
      CheckBoxPreference var5 = this.mSpliteviewCalendar;
      byte var6;
      if(System.getInt(this.getContentResolver(), "spliteview_mode_calendar", 0) != 0) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      var5.setChecked((boolean)var6);
      CheckBoxPreference var7 = this.mSpliteviewMemo;
      byte var8;
      if(System.getInt(this.getContentResolver(), "spliteview_mode_memo", 0) != 0) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      var7.setChecked((boolean)var8);
      CheckBoxPreference var9 = this.mSpliteviewMusic;
      byte var10;
      if(System.getInt(this.getContentResolver(), "spliteview_mode_music", 0) != 0) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      var9.setChecked((boolean)var10);
      CheckBoxPreference var11 = this.mSpliteviewMyfiles;
      byte var12;
      if(System.getInt(this.getContentResolver(), "spliteview_mode_myfiles", 0) != 0) {
         var12 = 1;
      } else {
         var12 = 0;
      }

      var11.setChecked((boolean)var12);
      CheckBoxPreference var13 = this.mSpliteviewSocialhub;
      byte var14;
      if(System.getInt(this.getContentResolver(), "spliteview_mode_socialhub", 0) != 0) {
         var14 = 1;
      } else {
         var14 = 0;
      }

      var13.setChecked((boolean)var14);
      CheckBoxPreference var15 = this.mSpliteviewIm;
      byte var16;
      if(System.getInt(this.getContentResolver(), "spliteview_mode_im", 0) != 0) {
         var16 = 1;
      } else {
         var16 = 0;
      }

      var15.setChecked((boolean)var16);
   }
}
