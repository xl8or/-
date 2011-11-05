package com.android.settings;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings.System;
import android.util.Log;
import com.android.settings.bluetooth.DockEventReceiver;

public class DockSettings extends PreferenceActivity implements OnPreferenceChangeListener {

   private static final int DIALOG_NOT_DOCKED = 1;
   private static final String KEY_AUDIO_SETTINGS = "dock_audio";
   private static final String KEY_AUTO_LAUNCH = "auto_launch";
   private static final String KEY_CRADLE_ENABLE = "cradle_enable";
   private static final String KEY_DOCK_SOUNDS = "dock_sounds";
   private static final String TAG = "DockSettings";
   private Preference mAudioSettings;
   private ListPreference mAutoLaunch;
   private CheckBoxPreference mCradleEnable;
   private Intent mDockIntent;
   private CheckBoxPreference mDockSounds;
   private BroadcastReceiver mReceiver;


   public DockSettings() {
      DockSettings.1 var1 = new DockSettings.1();
      this.mReceiver = var1;
   }

   private Dialog createUndockedMessage() {
      Builder var1 = new Builder(this);
      Builder var2 = var1.setTitle(2131231284);
      Builder var3 = var1.setMessage(2131231285);
      Builder var4 = var1.setPositiveButton(17039370, (OnClickListener)null);
      return var1.create();
   }

   private void handleDockChange(Intent var1) {
      if(this.mAudioSettings != null) {
         int var2 = var1.getIntExtra("android.intent.extra.DOCK_STATE", 0);
         boolean var3;
         if(var1.getParcelableExtra("android.bluetooth.device.extra.DEVICE") != null) {
            var3 = true;
         } else {
            var3 = false;
         }

         if(!var3) {
            this.mAudioSettings.setEnabled((boolean)0);
            this.mAudioSettings.setSummary(2131231283);
         } else {
            this.mAudioSettings.setEnabled((boolean)1);
            this.mDockIntent = var1;
            int var5 = 2131231283;
            switch(var2) {
            case 0:
               var5 = 2131231282;
               break;
            case 1:
               var5 = 2131231280;
               break;
            case 2:
               var5 = 2131231281;
            }

            this.mAudioSettings.setSummary(var5);
         }

         if(var2 != 0) {
            byte var4 = 1;

            try {
               this.dismissDialog(var4);
            } catch (IllegalArgumentException var8) {
               int var7 = Log.w("DockSettings", var8);
            }
         }
      }
   }

   private void initDockSettings() {
      ContentResolver var1 = this.getContentResolver();
      PreferenceScreen var2 = this.getPreferenceScreen();
      Preference var3 = this.findPreference("dock_audio");
      var2.removePreference(var3);
      PreferenceScreen var5 = this.getPreferenceScreen();
      Preference var6 = this.findPreference("dock_sounds");
      var5.removePreference(var6);
      PreferenceScreen var8 = this.getPreferenceScreen();
      Preference var9 = this.findPreference("auto_launch");
      var8.removePreference(var9);
      CheckBoxPreference var11 = (CheckBoxPreference)this.findPreference("cradle_enable");
      this.mCradleEnable = var11;
      CheckBoxPreference var12 = this.mCradleEnable;
      byte var13;
      if(System.getInt(var1, "cradle_enable", 0) == 1) {
         var13 = 1;
      } else {
         var13 = 0;
      }

      var12.setChecked((boolean)var13);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      ContentResolver var2 = this.getContentResolver();
      this.addPreferencesFromResource(2130968595);
      this.initDockSettings();
   }

   public Dialog onCreateDialog(int var1) {
      Dialog var2;
      if(var1 == 1) {
         var2 = this.createUndockedMessage();
      } else {
         var2 = null;
      }

      return var2;
   }

   protected void onPause() {
      super.onPause();
      BroadcastReceiver var1 = this.mReceiver;
      this.unregisterReceiver(var1);
   }

   public boolean onPreferenceChange(Preference var1, Object var2) {
      ListPreference var3 = this.mAutoLaunch;
      if(var1.equals(var3)) {
         int var4 = Integer.parseInt((String)var2);

         try {
            boolean var5 = System.putInt(this.getContentResolver(), "cradle_launch", var4);
            String var6 = "Auto Launch value : " + var4;
            int var7 = Log.d("DockSettings", var6);
         } catch (NumberFormatException var10) {
            int var9 = Log.e("DockSettings", "could not persist emergency tone setting", var10);
         }
      }

      return true;
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      Preference var3 = this.mAudioSettings;
      if(var2.equals(var3)) {
         int var4;
         if(this.mDockIntent != null) {
            var4 = this.mDockIntent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
         } else {
            var4 = 0;
         }

         if(var4 == 0) {
            this.showDialog(1);
         } else {
            Intent var5 = this.mDockIntent;
            Intent var6 = new Intent(var5);
            Intent var7 = var6.setAction("com.android.settings.bluetooth.action.DOCK_SHOW_UI");
            var6.setClass(this, DockEventReceiver.class);
            this.sendBroadcast(var6);
         }
      } else {
         CheckBoxPreference var9 = this.mDockSounds;
         byte var12;
         if(var2.equals(var9)) {
            ContentResolver var10 = this.getContentResolver();
            String var11 = "dock_sounds_enabled";
            if(this.mDockSounds.isChecked()) {
               var12 = 1;
            } else {
               var12 = 0;
            }

            System.putInt(var10, var11, var12);
         } else {
            CheckBoxPreference var14 = this.mCradleEnable;
            if(var2.equals(var14)) {
               boolean var15 = this.mCradleEnable.isChecked();
               ContentResolver var16 = this.getContentResolver();
               String var17 = "cradle_enable";
               if(var15) {
                  var12 = 1;
               } else {
                  var12 = 0;
               }

               System.putInt(var16, var17, var12);
               if(System.getInt(this.getContentResolver(), "cradle_connect", 0) != 0) {
                  int var19 = Log.v("DockSettings", "Cradle is connected:");
                  Intent var20 = new Intent();
                  Intent var21 = var20.setAction("com.sec.android.intent.action.INTERNAL_SPEAKER");
                  byte var22;
                  if(var15) {
                     var22 = 1;
                  } else {
                     var22 = 0;
                  }

                  var20.putExtra("state", var22);
                  this.getBaseContext().sendBroadcast(var20);
                  String var24 = "PhoneSpeakerState(0 Phone, 1 Line out): " + var22;
                  int var25 = Log.v("DockSettings", var24);
               } else {
                  int var26 = Log.v("DockSettings", "Cradle is not connected:");
                  Intent var27 = new Intent();
                  Intent var28 = var27.setAction("com.sec.android.intent.action.INTERNAL_SPEAKER");
                  Intent var29 = var27.putExtra("state", 0);
                  this.getBaseContext().sendBroadcast(var27);
                  String var30 = "PhoneSpeakerState(0 Phone, 1 Line out): " + 0;
                  int var31 = Log.v("DockSettings", var30);
               }
            }
         }
      }

      return true;
   }

   protected void onResume() {
      super.onResume();
      IntentFilter var1 = new IntentFilter("android.intent.action.DOCK_EVENT");
      BroadcastReceiver var2 = this.mReceiver;
      this.registerReceiver(var2, var1);
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         if(var2.getAction().equals("android.intent.action.DOCK_EVENT")) {
            DockSettings.this.handleDockChange(var2);
         }
      }
   }
}
