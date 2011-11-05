package com.android.settings;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.settings.ZoneList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeSettings extends PreferenceActivity implements OnSharedPreferenceChangeListener, OnTimeSetListener, OnDateSetListener {

   private static final int DIALOG_AUTO_TIME = 2;
   private static final int DIALOG_DATEPICKER = 0;
   private static final int DIALOG_TIMEPICKER = 1;
   private static final String HOURS_12 = "12";
   private static final String HOURS_24 = "24";
   private static final String KEY_AUTO_TIME = "auto_time";
   private static final String KEY_DATE_FORMAT = "date_format";
   private static final String TAG = "DateTimeSettings";
   private long beforeTime;
   private CheckBoxPreference mAutoPref;
   private ListPreference mDateFormat;
   private Preference mDatePref;
   private Calendar mDummyDate;
   private BroadcastReceiver mIntentReceiver;
   private Preference mTime24Pref;
   private Preference mTimePref;
   private Preference mTimeZone;


   public DateTimeSettings() {
      DateTimeSettings.2 var1 = new DateTimeSettings.2();
      this.mIntentReceiver = var1;
   }

   private void broadCastDrmTimeUpdation() {
      if(this.getPreferenceScreen().getSharedPreferences().getBoolean("auto_time", (boolean)1)) {
         int var1 = Log.i("DateTimeSettings", "Nitz Time will be updated");
      } else {
         int var2 = Log.i("DateTimeSettings", "BROADCASTING INTENT");
         Date var3 = Calendar.getInstance().getTime();
         Intent var4 = new Intent("android.intent.action.DRM_UPDATE_SECURE_CLOCK");
         long var5 = this.beforeTime / 1000L;
         var4.putExtra("beforeTime", var5);
         long var8 = var3.getTime() / 1000L;
         var4.putExtra("afterTime", var8);
         this.getApplicationContext().sendBroadcast(var4);
      }
   }

   private char[] formatOffset(int var1) {
      int var2 = var1 / 1000 / 60;
      char[] var3 = new char[9];
      var3[0] = 71;
      var3[1] = 77;
      var3[2] = 84;
      if(var2 < 0) {
         var3[3] = 45;
         var2 = -var2;
      } else {
         var3[3] = 43;
      }

      int var4 = var2 / 60;
      int var5 = var2 % 60;
      char var6 = (char)(var4 / 10 + 48);
      var3[4] = var6;
      char var7 = (char)(var4 % 10 + 48);
      var3[5] = var7;
      var3[6] = 58;
      char var8 = (char)(var5 / 10 + 48);
      var3[7] = var8;
      char var9 = (char)(var5 % 10 + 48);
      var3[8] = var9;
      return var3;
   }

   private boolean getAutoState() {
      int var1;
      boolean var2;
      try {
         var1 = System.getInt(this.getContentResolver(), "auto_time");
      } catch (SettingNotFoundException var4) {
         var2 = true;
         return var2;
      }

      if(var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private String getDateFormat() {
      String var1 = System.getString(this.getContentResolver(), "date_format");
      if(var1 == null) {
         var1 = "MM-dd-yyyy";
      }

      return var1;
   }

   private String getTimeZoneName(String param1) {
      // $FF: Couldn't be decompiled
   }

   private String getTimeZoneText() {
      TimeZone var1 = Calendar.getInstance().getTimeZone();
      Date var2 = new Date();
      boolean var3 = var1.inDaylightTime(var2);
      StringBuilder var4 = new StringBuilder();
      long var5 = Calendar.getInstance().getTimeInMillis();
      int var7 = var1.getOffset(var5);
      char[] var8 = this.formatOffset(var7);
      StringBuilder var9 = var4.append(var8).append(", ");
      String var10 = var1.getDisplayName(var3, 1);
      if(var10.startsWith("GMT")) {
         String var11 = var1.getID();
         String var12 = this.getTimeZoneName(var11);
         var4.append(var12);
      } else {
         var4.append(var10);
      }

      return var4.toString();
   }

   private void initUI() {
      boolean var1 = this.getAutoState();
      Calendar var2 = Calendar.getInstance();
      this.mDummyDate = var2;
      Calendar var3 = this.mDummyDate;
      int var4 = this.mDummyDate.get(1);
      byte var5 = 0;
      var3.set(var4, 11, 31, 13, 0, var5);
      CheckBoxPreference var6 = (CheckBoxPreference)this.findPreference("auto_time");
      this.mAutoPref = var6;
      this.mAutoPref.setChecked(var1);
      Preference var7 = this.findPreference("time");
      this.mTimePref = var7;
      Preference var8 = this.findPreference("24 hour");
      this.mTime24Pref = var8;
      Preference var9 = this.findPreference("timezone");
      this.mTimeZone = var9;
      Preference var10 = this.findPreference("date");
      this.mDatePref = var10;
      ListPreference var11 = (ListPreference)this.findPreference("date_format");
      this.mDateFormat = var11;
      String[] var12 = new String[this.getResources().getStringArray(2131034114).length];
      String var13 = this.getDateFormat();
      if(var13 == null) {
         var13 = "";
      }

      this.mDateFormat.setEntries(2131034113);
      this.mDateFormat.setEntryValues(2131034114);
      this.mDateFormat.setValue(var13);
      Preference var14 = this.mTimePref;
      byte var15;
      if(!var1) {
         var15 = 1;
      } else {
         var15 = 0;
      }

      var14.setEnabled((boolean)var15);
      Preference var16 = this.mDatePref;
      byte var17;
      if(!var1) {
         var17 = 1;
      } else {
         var17 = 0;
      }

      var16.setEnabled((boolean)var17);
      Preference var18 = this.mTimeZone;
      byte var19;
      if(!var1) {
         var19 = 1;
      } else {
         var19 = 0;
      }

      var18.setEnabled((boolean)var19);
   }

   private boolean is24Hour() {
      return DateFormat.is24HourFormat(this);
   }

   private void set24Hour(boolean var1) {
      ContentResolver var2 = this.getContentResolver();
      String var3;
      if(var1) {
         var3 = "24";
      } else {
         var3 = "12";
      }

      System.putString(var2, "time_12_24", var3);
   }

   private void setDateFormat(String var1) {
      if(var1.length() == 0) {
         var1 = null;
      }

      boolean var2 = System.putString(this.getContentResolver(), "date_format", var1);
   }

   private void setDefaultDateValue() {
      Calendar var1 = Calendar.getInstance();
      long var2 = var1.getTimeInMillis();
      int var4 = var1.get(1);
      String var5 = "currentTime = " + var2;
      int var6 = Log.i("DateTimeSettings", var5);
      String var7 = "year = " + var4;
      int var8 = Log.i("DateTimeSettings", var7);
      if(var4 > 2036 || var4 < 2000) {
         var1.set(1, 2000);
         var1.set(2, 0);
         var1.set(5, 1);
         long var9 = var1.getTimeInMillis();
         if(var9 / 1000L < 2147483647L) {
            boolean var11 = SystemClock.setCurrentTimeMillis(var9);
         }
      }
   }

   private void timeUpdated() {
      Intent var1 = new Intent("android.intent.action.TIME_SET");
      this.sendBroadcast(var1);
   }

   private void updateTimeAndDateDisplay() {
      int var1 = Log.i("DateTimeSettings", "Nitz Time will be updated");
      this.setDefaultDateValue();
      java.text.DateFormat var2 = DateFormat.getDateFormat(this);
      Date var3 = Calendar.getInstance().getTime();
      Date var4 = this.mDummyDate.getTime();
      Preference var5 = this.mTimePref;
      String var6 = DateFormat.getTimeFormat(this).format(var3);
      var5.setSummary(var6);
      Preference var7 = this.mTimeZone;
      String var8 = this.getTimeZoneText();
      var7.setSummary(var8);
      StringBuilder var9 = (new StringBuilder()).append("updateTimeAndDateDisplay : Current Timezone ");
      String var10 = this.getTimeZoneText();
      String var11 = var9.append(var10).toString();
      int var12 = Log.d("DateTimeSettings", var11);
      Preference var13 = this.mDatePref;
      String var14 = var2.format(var3);
      var13.setSummary(var14);
      String[] var15 = this.getResources().getStringArray(2131034114);
      String[] var16 = this.getResources().getStringArray(2131034113);
      int var17 = 0;

      while(true) {
         int var18 = var15.length;
         if(var17 >= var18) {
            return;
         }

         String var19 = this.getDateFormat();
         String var20 = var15[var17];
         if(var19.equals(var20)) {
            ListPreference var21 = this.mDateFormat;
            String var22 = var16[var17];
            var21.setSummary(var22);
            return;
         }

         ++var17;
      }
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      this.updateTimeAndDateDisplay();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(this.getIntent().getBooleanExtra("manual_set", (boolean)0)) {
         boolean var2 = System.putInt(this.getContentResolver(), "auto_time", 0);
         Toast.makeText(this, 2131232282, 1).show();
      }

      this.addPreferencesFromResource(2130968587);
      this.initUI();
   }

   public Dialog onCreateDialog(int var1) {
      Object var2;
      Object var3;
      switch(var1) {
      case 0:
         Calendar var4 = Calendar.getInstance();
         int var5 = var4.get(1);
         int var6 = var4.get(2);
         int var7 = var4.get(5);
         var2 = new DatePickerDialog(this, this, var5, var6, var7);
         break;
      case 1:
         Calendar var10 = Calendar.getInstance();
         int var11 = var10.get(11);
         int var12 = var10.get(12);
         boolean var13 = DateFormat.is24HourFormat(this);
         var2 = new TimePickerDialog(this, this, var11, var12, var13);
         String var16 = this.getResources().getString(2131230833);
         ((Dialog)var2).setTitle(var16);
         break;
      case 2:
         Builder var17 = new Builder(this);
         Builder var18 = var17.setTitle(2131232279);
         Builder var19 = var17.setIcon(17301543);
         Builder var20 = var17.setMessage(2131232280);
         DateTimeSettings.1 var21 = new DateTimeSettings.1();
         var17.setPositiveButton(2131230722, var21);
         Builder var23 = var17.setNegativeButton(2131230723, (OnClickListener)null);
         var3 = var17.create();
         return (Dialog)var3;
      default:
         var2 = null;
      }

      var3 = var2;
      return (Dialog)var3;
   }

   public void onDateSet(DatePicker var1, int var2, int var3, int var4) {
      Calendar var5 = Calendar.getInstance();
      long var6 = var5.getTimeInMillis();
      this.beforeTime = var6;
      var5.set(1, var2);
      var5.set(2, var3);
      var5.set(5, var4);
      long var8 = var5.getTimeInMillis();
      if(var8 / 1000L < 2147483647L) {
         boolean var10 = SystemClock.setCurrentTimeMillis(var8);
      }

      this.updateTimeAndDateDisplay();
   }

   protected void onPause() {
      super.onPause();
      BroadcastReceiver var1 = this.mIntentReceiver;
      this.unregisterReceiver(var1);
      this.getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      Preference var3 = this.mDatePref;
      if(var2.equals(var3)) {
         this.removeDialog(0);
         this.showDialog(0);
      } else {
         Preference var4 = this.mTimePref;
         if(var2.equals(var4)) {
            this.removeDialog(1);
            this.showDialog(1);
         } else {
            Preference var5 = this.mTime24Pref;
            if(var2.equals(var5)) {
               boolean var6 = ((CheckBoxPreference)this.mTime24Pref).isChecked();
               this.set24Hour(var6);
               this.updateTimeAndDateDisplay();
               this.timeUpdated();
            } else {
               Preference var7 = this.mTimeZone;
               if(var2.equals(var7)) {
                  Intent var8 = new Intent();
                  var8.setClass(this, ZoneList.class);
                  this.startActivityForResult(var8, 0);
               }
            }
         }
      }

      return false;
   }

   public void onPrepareDialog(int var1, Dialog var2) {
      switch(var1) {
      case 0:
         DatePickerDialog var3 = (DatePickerDialog)var2;
         Calendar var4 = Calendar.getInstance();
         var3.twSetRangeOfYear(2000, 2036);
         int var5 = var4.get(1);
         int var6 = var4.get(2);
         int var7 = var4.get(5);
         var3.updateDate(var5, var6, var7);
         return;
      case 1:
         TimePickerDialog var8 = (TimePickerDialog)var2;
         Calendar var9 = Calendar.getInstance();
         int var10 = var9.get(11);
         int var11 = var9.get(12);
         var8.updateTime(var10, var11);
         return;
      default:
      }
   }

   protected void onResume() {
      super.onResume();
      this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
      CheckBoxPreference var1 = (CheckBoxPreference)this.mTime24Pref;
      boolean var2 = this.is24Hour();
      var1.setChecked(var2);
      IntentFilter var3 = new IntentFilter();
      var3.addAction("android.intent.action.TIME_TICK");
      var3.addAction("android.intent.action.TIME_SET");
      var3.addAction("android.intent.action.TIMEZONE_CHANGED");
      BroadcastReceiver var4 = this.mIntentReceiver;
      this.registerReceiver(var4, var3, (String)null, (Handler)null);
      this.updateTimeAndDateDisplay();
   }

   public void onSharedPreferenceChanged(SharedPreferences var1, String var2) {
      if(var2.equals("date_format")) {
         String var3 = this.getResources().getString(2131230836);
         String var4 = var1.getString(var2, var3);
         boolean var5 = System.putString(this.getContentResolver(), "date_format", var4);
         this.updateTimeAndDateDisplay();
      } else if(var2.equals("auto_time")) {
         boolean var6 = var1.getBoolean(var2, (boolean)1);
         ContentResolver var7 = this.getContentResolver();
         byte var8;
         if(var6) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         System.putInt(var7, "auto_time", var8);
         Preference var10 = this.mTimePref;
         byte var11;
         if(!var6) {
            var11 = 1;
         } else {
            var11 = 0;
         }

         var10.setEnabled((boolean)var11);
         Preference var12 = this.mDatePref;
         byte var13;
         if(!var6) {
            var13 = 1;
         } else {
            var13 = 0;
         }

         var12.setEnabled((boolean)var13);
         Preference var14 = this.mTimeZone;
         byte var15;
         if(!var6) {
            var15 = 1;
         } else {
            var15 = 0;
         }

         var14.setEnabled((boolean)var15);
      }
   }

   public void onTimeSet(TimePicker var1, int var2, int var3) {
      Calendar var4 = Calendar.getInstance();
      long var5 = var4.getTimeInMillis();
      this.beforeTime = var5;
      var4.set(11, var2);
      var4.set(12, var3);
      var4.set(13, 0);
      long var7 = var4.getTimeInMillis();
      if(var7 / 1000L < 2147483647L) {
         boolean var9 = SystemClock.setCurrentTimeMillis(var7);
      }

      this.updateTimeAndDateDisplay();
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         DateTimeSettings.this.mAutoPref.setChecked((boolean)1);
         DateTimeSettings.this.mAutoPref.setPersistent((boolean)0);
         boolean var3 = System.putInt(DateTimeSettings.this.getContentResolver(), "auto_time", 1);
         Context var4 = DateTimeSettings.this.getApplicationContext();
         ProgressDialog var5 = new ProgressDialog(var4);
         CharSequence var6 = DateTimeSettings.this.getText(2131232281);
         var5.setMessage(var6);
         var5.setIndeterminate((boolean)1);
         var5.setCancelable((boolean)0);
         var5.getWindow().setType(2003);
         var5.show();
         Handler var7 = new Handler();
         DateTimeSettings.1.1 var8 = new DateTimeSettings.1.1();
         var7.postDelayed(var8, 1500L);
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            ((PowerManager)DateTimeSettings.this.getSystemService("power")).reboot((String)null);
         }
      }
   }

   class 2 extends BroadcastReceiver {

      2() {}

      public void onReceive(Context var1, Intent var2) {
         int var3 = Log.i("DateTimeSettings", "BroadcastReceiver - updateTimeAndDateDisplay");
         StringBuilder var4 = (new StringBuilder()).append("Received event : ");
         String var5 = var2.getAction();
         String var6 = var4.append(var5).toString();
         int var7 = Log.i("DateTimeSettings", var6);
         if(var2.getAction().equals("android.intent.action.TIMEZONE_CHANGED")) {
            String var8 = var2.getStringExtra("time-zone");
            TimeZone.setDefault(TimeZone.getTimeZone(var8));
            String var9 = "TimeZone.setDefault(TimeZone.getTimeZone(tz)): tz " + var8;
            int var10 = Log.i("DateTimeSettings", var9);
         }

         DateTimeSettings.this.updateTimeAndDateDisplay();
      }
   }
}
