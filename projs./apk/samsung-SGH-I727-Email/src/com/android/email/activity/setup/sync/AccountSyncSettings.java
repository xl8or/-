package com.android.email.activity.setup.sync;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.email.Email;
import com.android.email.activity.Welcome;
import com.android.email.activity.setup.AccountSettingsUtils;
import com.android.email.combined.common.ExceptionUtil;
import com.android.email.provider.EmailContent;
import com.digc.seven.Z7MailHandler;
import com.seven.Z7.shared.PreferenceConstants;
import java.util.Date;
import java.util.HashMap;

public class AccountSyncSettings extends PreferenceActivity implements PreferenceConstants.EmailAccountPreferences, PreferenceConstants.GeneralPreferences {

   public static final String EXTRA_ACCOUNT_ID = "account_id";
   public static final String EXTRA_IS_PEAK_START_TIME = "is_peak_start_time";
   public static final String PEAK_TIME_DELIMITER = ":";
   private String[] daysForSummary;
   private boolean isPeakStartTime;
   private EmailContent.Account mAccount;
   private boolean mAccountDirty;
   private long mAccountId = 65535L;
   private ListPreference mOffPeakTimeSchedule;
   private ListPreference mPeakDays;
   private ListPreference mPeakEndTime;
   private ListPreference mPeakStartTime;
   private ListPreference mPeakTimeSchedule;
   OnTimeSetListener mTimeSetListener;
   private boolean[] peakDaysNewValues;
   private boolean[] peakDaysValues;
   private TimePickerDialog timePickerDialog;
   private Z7MailHandler zHandler;


   public AccountSyncSettings() {
      boolean[] var1 = new boolean[]{false, false, false, false, false, false, false};
      this.peakDaysValues = var1;
      boolean[] var2 = new boolean[]{false, false, false, false, false, false, false};
      this.peakDaysNewValues = var2;
      AccountSyncSettings.1 var3 = new AccountSyncSettings.1();
      this.mTimeSetListener = var3;
   }

   public static void actionAccountSyncSettings(Activity var0, long var1) {
      Intent var3 = new Intent(var0, AccountSyncSettings.class);
      var3.putExtra("account_id", var1);
      var0.startActivity(var3);
   }

   private void actionSettingsPeakTime() {
      String[] var1;
      if(this.isPeakStartTime) {
         var1 = this.mAccount.mPeakStartTime.split(":");
      } else {
         var1 = this.mAccount.mPeakEndTime.split(":");
      }

      if(var1.length == 2) {
         if(this.timePickerDialog == null) {
            OnTimeSetListener var2 = this.mTimeSetListener;
            int var3 = Integer.parseInt(var1[0]);
            int var4 = Integer.parseInt(var1[1]);
            boolean var5 = this.is24Hour();
            TimePickerDialog var7 = new TimePickerDialog(this, var2, var3, var4, var5);
            this.timePickerDialog = var7;
         } else {
            TimePickerDialog var8 = this.timePickerDialog;
            int var9 = Integer.parseInt(var1[0]);
            int var10 = Integer.parseInt(var1[1]);
            var8.updateTime(var9, var10);
         }
      } else if(this.timePickerDialog == null) {
         OnTimeSetListener var11 = this.mTimeSetListener;
         boolean var12 = this.is24Hour();
         byte var14 = 0;
         TimePickerDialog var15 = new TimePickerDialog(this, var11, 0, var14, var12);
         this.timePickerDialog = var15;
      }

      this.timePickerDialog.show();
   }

   private void arrayCopy(boolean[] var1, boolean[] var2) {
      int var3 = var1.length;
      System.arraycopy(var1, 0, var2, 0, var3);
   }

   private void expressPeakDays() {
      StringBuilder var1 = new StringBuilder();
      int var2 = 0;

      while(true) {
         int var3 = this.peakDaysNewValues.length;
         if(var2 >= var3) {
            ListPreference var7 = this.mPeakDays;
            String var8 = var1.toString();
            var7.setSummary(var8);
            return;
         }

         if(this.peakDaysNewValues[var2]) {
            if(false) {
               StringBuilder var4 = var1.append(", ");
            }

            String var5 = this.daysForSummary[var2];
            var1.append(var5);
         }

         ++var2;
      }
   }

   private String getSelectedPeakDays() {
      StringBuilder var1 = new StringBuilder();
      boolean[] var2 = this.peakDaysValues;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         if(var2[var4]) {
            StringBuilder var5 = var1.append("1");
         } else {
            StringBuilder var6 = var1.append("0");
         }
      }

      return var1.toString();
   }

   private String getTimeAppliedCurFormat(String var1) {
      String[] var2 = var1.split(":");
      int var3 = Integer.parseInt(var2[0]);
      int var4 = Integer.parseInt(var2[1]);
      java.text.DateFormat var5 = DateFormat.getTimeFormat(this);
      Date var6 = new Date();
      var6.setHours(var3);
      var6.setMinutes(var4);
      return var5.format(var6);
   }

   private boolean is24Hour() {
      return DateFormat.is24HourFormat(this);
   }

   private void saveSettings() {
      if(this.mAccount != null) {
         HashMap var1 = new HashMap();
         int var2 = Integer.parseInt(this.mPeakTimeSchedule.getValue());
         int var3 = Integer.parseInt(this.mOffPeakTimeSchedule.getValue());
         if(this.mAccount.mPeakTime != var2 || this.mAccount.mOffPeakTime != var3) {
            if(var2 == 0 && var3 == 0) {
               String var4 = PEAK_TIME_KEYS[0];
               Boolean var5 = Boolean.valueOf((boolean)0);
               var1.put(var4, var5);
               String var7 = PEAK_TIME_KEYS[1];
               Boolean var8 = Boolean.valueOf((boolean)0);
               var1.put(var7, var8);
            } else if(var2 == 1 && var3 == 1) {
               String var37 = PEAK_TIME_KEYS[0];
               Boolean var38 = Boolean.valueOf((boolean)1);
               var1.put(var37, var38);
               String var40 = PEAK_TIME_KEYS[1];
               Boolean var41 = Boolean.valueOf((boolean)1);
               var1.put(var40, var41);
            } else {
               String var43 = PEAK_TIME_KEYS[0];
               Boolean var44 = Boolean.valueOf((boolean)0);
               var1.put(var43, var44);
               String var46 = PEAK_TIME_KEYS[1];
               Boolean var47 = Boolean.valueOf((boolean)1);
               var1.put(var46, var47);
            }

            this.mAccount.mPeakTime = var2;
            this.mAccount.mOffPeakTime = var3;
         }

         boolean var10;
         if(this.mAccount.mPeakTime == 1 && this.mAccount.mOffPeakTime == 1) {
            var10 = true;
         } else {
            var10 = false;
         }

         boolean var11;
         if(this.mAccount.mPeakTime == 0 && this.mAccount.mOffPeakTime == 0) {
            var11 = true;
         } else {
            var11 = false;
         }

         boolean var12;
         if(this.mAccount.mPeakTime == 0 && this.mAccount.mOffPeakTime == 1) {
            var12 = true;
         } else {
            var12 = false;
         }

         String var13 = this.getSelectedPeakDays();
         String var14 = this.mAccount.mDays;
         if(!var13.equals(var14)) {
            this.mAccount.mDays = var13;
         }

         int var31;
         if(!var10) {
            String[] var15 = this.mAccount.mPeakStartTime.split(":");
            String[] var16 = this.mAccount.mPeakEndTime.split(":");
            if(var11) {
               String var17 = PEAK_TIME_KEYS[2];
               long var18 = Long.parseLong(Integer.toString(0)) * 60L;
               long var20 = Long.parseLong(Integer.toString(0));
               Long var22 = Long.valueOf(var18 + var20);
               var1.put(var17, var22);
               String var24 = PEAK_TIME_KEYS[3];
               long var25 = Long.parseLong(Integer.toString(23)) * 60L;
               long var27 = Long.parseLong(Integer.toString(59));
               Long var29 = Long.valueOf(var25 + var27);
               var1.put(var24, var29);

               for(var31 = 0; var31 < 7; ++var31) {
                  String[] var32 = PEAK_TIME_KEYS;
                  int var33 = var31 + 4;
                  String var34 = var32[var33];
                  Boolean var35 = Boolean.valueOf((boolean)0);
                  var1.put(var34, var35);
               }
            } else if(!var12) {
               String var49 = PEAK_TIME_KEYS[2];
               long var50 = Long.parseLong(var16[0]) * 60L;
               long var52 = Long.parseLong(var16[1]);
               Long var54 = Long.valueOf(var50 + var52);
               var1.put(var49, var54);
               String var56 = PEAK_TIME_KEYS[3];
               long var57 = Long.parseLong(var15[0]) * 60L;
               long var59 = Long.parseLong(var15[1]);
               Long var61 = Long.valueOf(var57 + var59);
               var1.put(var56, var61);
               var31 = 0;

               while(true) {
                  int var63 = var13.length();
                  if(var31 >= var63) {
                     break;
                  }

                  if(var13.charAt(var31) == 49) {
                     String[] var64 = PEAK_TIME_KEYS;
                     int var65 = var31 + 4;
                     String var66 = var64[var65];
                     Boolean var67 = Boolean.valueOf((boolean)1);
                     var1.put(var66, var67);
                  } else {
                     String[] var69 = PEAK_TIME_KEYS;
                     int var70 = var31 + 4;
                     String var71 = var69[var70];
                     Boolean var72 = Boolean.valueOf((boolean)0);
                     var1.put(var71, var72);
                  }

                  ++var31;
               }
            } else {
               String var74 = PEAK_TIME_KEYS[2];
               long var75 = Long.parseLong(var15[0]) * 60L;
               long var77 = Long.parseLong(var15[1]);
               Long var79 = Long.valueOf(var75 + var77);
               var1.put(var74, var79);
               String var81 = PEAK_TIME_KEYS[3];
               long var82 = Long.parseLong(var16[0]) * 60L;
               long var84 = Long.parseLong(var16[1]);
               Long var86 = Long.valueOf(var82 + var84);
               var1.put(var81, var86);
               var31 = 0;

               while(true) {
                  int var88 = var13.length();
                  if(var31 >= var88) {
                     break;
                  }

                  if(var13.charAt(var31) == 49) {
                     String[] var89 = PEAK_TIME_KEYS;
                     int var90 = var31 + 4;
                     String var91 = var89[var90];
                     Boolean var92 = Boolean.valueOf((boolean)0);
                     var1.put(var91, var92);
                  } else {
                     String[] var94 = PEAK_TIME_KEYS;
                     int var95 = var31 + 4;
                     String var96 = var94[var95];
                     Boolean var97 = Boolean.valueOf((boolean)1);
                     var1.put(var96, var97);
                  }

                  ++var31;
               }
            }
         } else {
            for(var31 = 0; var31 < 7; ++var31) {
               String[] var99 = PEAK_TIME_KEYS;
               int var100 = var31 + 4;
               String var101 = var99[var100];
               Boolean var102 = Boolean.valueOf((boolean)1);
               var1.put(var101, var102);
            }
         }

         AccountSyncSettings.7 var104 = new AccountSyncSettings.7(var1);
         (new Thread(var104)).start();
         EmailContent.Account var105 = this.mAccount;
         AccountSettingsUtils.commitSyncSettings(this, var105);
         boolean var106 = Email.setServicesEnabled(this);
         Toast.makeText(this, 2131166992, 0).show();
      }
   }

   private void setDataForView() {
      ListPreference var1 = this.mPeakTimeSchedule;
      String var2 = String.valueOf(this.mAccount.mPeakTime);
      var1.setValue(var2);
      ListPreference var3 = this.mPeakTimeSchedule;
      CharSequence var4 = this.mPeakTimeSchedule.getEntry();
      var3.setSummary(var4);
      ListPreference var5 = this.mOffPeakTimeSchedule;
      String var6 = String.valueOf(this.mAccount.mOffPeakTime);
      var5.setValue(var6);
      ListPreference var7 = this.mOffPeakTimeSchedule;
      CharSequence var8 = this.mOffPeakTimeSchedule.getEntry();
      var7.setSummary(var8);
      this.expressPeakDays();
      ListPreference var9 = this.mPeakStartTime;
      String var10 = this.mAccount.mPeakStartTime;
      String var11 = this.getTimeAppliedCurFormat(var10);
      var9.setSummary(var11);
      ListPreference var12 = this.mPeakEndTime;
      String var13 = this.mAccount.mPeakEndTime;
      String var14 = this.getTimeAppliedCurFormat(var13);
      var12.setSummary(var14);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      long var2 = this.getIntent().getLongExtra("account_id", 65535L);
      this.mAccountId = var2;
      if(this.mAccountId == 65535L) {
         this.finish();
      } else {
         long var4 = this.mAccountId;
         EmailContent.Account var6 = EmailContent.Account.restoreAccountWithId(this, var4);
         this.mAccount = var6;
         if(this.mAccount == null) {
            this.finish();
         } else {
            Z7MailHandler var7 = Z7MailHandler.getInstance(this);
            this.zHandler = var7;
            this.mAccountDirty = (boolean)0;
            this.addPreferencesFromResource(2131034118);
            PreferenceScreen var8 = (PreferenceScreen)this.findPreference("sync_schedule");
            AccountSyncSettings.2 var9 = new AccountSyncSettings.2(this);
            this.mPeakStartTime = var9;
            this.mPeakStartTime.setTitle(2131166961);
            this.mPeakStartTime.setOrder(4);
            ListPreference var10 = this.mPeakStartTime;
            var8.addPreference(var10);
            AccountSyncSettings.3 var12 = new AccountSyncSettings.3(this);
            this.mPeakEndTime = var12;
            this.mPeakEndTime.setTitle(2131166962);
            this.mPeakEndTime.setOrder(5);
            ListPreference var13 = this.mPeakEndTime;
            var8.addPreference(var13);
            ListPreference var15 = (ListPreference)this.findPreference("peak_time_schedule");
            this.mPeakTimeSchedule = var15;
            ListPreference var16 = this.mPeakTimeSchedule;
            AccountSyncSettings.4 var17 = new AccountSyncSettings.4();
            var16.setOnPreferenceChangeListener(var17);
            ListPreference var18 = (ListPreference)this.findPreference("off_peak_time_schedule");
            this.mOffPeakTimeSchedule = var18;
            ListPreference var19 = this.mOffPeakTimeSchedule;
            AccountSyncSettings.5 var20 = new AccountSyncSettings.5();
            var19.setOnPreferenceChangeListener(var20);
            AccountSyncSettings.6 var21 = new AccountSyncSettings.6(this);
            this.mPeakDays = var21;
            this.mPeakDays.setOrder(3);
            this.mPeakDays.setTitle(2131166960);
            this.mPeakDays.setEntries(2131296297);
            this.mPeakDays.setEntryValues(2131296298);
            this.mPeakDays.setDialogTitle(2131166960);
            ListPreference var22 = this.mPeakDays;
            var8.addPreference(var22);
            String[] var24 = new String[7];
            String var25 = this.getString(2131166970);
            var24[0] = var25;
            String var26 = this.getString(2131166971);
            var24[1] = var26;
            String var27 = this.getString(2131166972);
            var24[2] = var27;
            String var28 = this.getString(2131166973);
            var24[3] = var28;
            String var29 = this.getString(2131166974);
            var24[4] = var29;
            String var30 = this.getString(2131166975);
            var24[5] = var30;
            String var31 = this.getString(2131166976);
            var24[6] = var31;
            this.daysForSummary = var24;
            if(this.mAccount.mDays.length() == 7) {
               int var32 = 0;

               while(true) {
                  int var33 = this.peakDaysValues.length;
                  if(var32 >= var33) {
                     break;
                  }

                  if(this.mAccount.mDays.charAt(var32) == 49) {
                     this.peakDaysValues[var32] = true;
                  } else {
                     this.peakDaysValues[var32] = false;
                  }

                  ++var32;
               }
            }

            boolean[] var34 = this.peakDaysValues;
            boolean[] var35 = this.peakDaysNewValues;
            this.arrayCopy(var34, var35);
         }
      }
   }

   protected void onDestroy() {
      super.onDestroy();
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      byte var3;
      if(var1 == 84) {
         var3 = 1;
      } else {
         if(var1 == 4) {
            this.saveSettings();
         }

         var3 = super.onKeyDown(var1, var2);
      }

      return (boolean)var3;
   }

   public void onResume() {
      super.onResume();
      if(Email.getNotifyUiAccountsChanged()) {
         Welcome.actionStart(this);
         this.finish();
      } else {
         long var1 = this.mAccountId;
         EmailContent.Account var3 = EmailContent.Account.restoreAccountWithId(this, var1);
         this.mAccount = var3;
         if(this.mAccount == null) {
            this.finish();
         } else {
            if(this.mAccountDirty) {
               long var4 = this.mAccount.mId;
               EmailContent.Account var6 = EmailContent.Account.restoreAccountWithId(this, var4);
               if(var6 == null) {
                  this.finish();
                  return;
               }

               EmailContent.Account var7 = this.mAccount;
               int var8 = var6.getDeletePolicy();
               var7.setDeletePolicy(var8);
               this.mAccountDirty = (boolean)0;
            }

            long var9 = this.mAccountId;
            EmailContent.Account var11 = EmailContent.Account.restoreAccountWithId(this, var9);
            String var12 = this.mAccount.mPeakStartTime;
            String var13 = var11.mPeakStartTime;
            if(!var12.equals(var13)) {
               EmailContent.Account var14 = this.mAccount;
               String var15 = var11.mPeakStartTime;
               var14.mPeakStartTime = var15;
            }

            String var16 = this.mAccount.mPeakEndTime;
            String var17 = var11.mPeakEndTime;
            if(!var16.equals(var17)) {
               EmailContent.Account var18 = this.mAccount;
               String var19 = var11.mPeakEndTime;
               var18.mPeakEndTime = var19;
            }

            this.setDataForView();
         }
      }
   }

   class 6 extends ListPreference {

      6(Context var2) {
         super(var2);
      }

      protected void onPrepareDialogBuilder(Builder var1) {
         AccountSyncSettings.6.3 var2 = new AccountSyncSettings.6.3();
         Builder var3 = var1.setPositiveButton(2131166260, var2);
         AccountSyncSettings.6.2 var4 = new AccountSyncSettings.6.2();
         Builder var5 = var3.setNegativeButton(2131166261, var4);
         boolean[] var6 = AccountSyncSettings.this.peakDaysNewValues;
         AccountSyncSettings.6.1 var7 = new AccountSyncSettings.6.1();
         var5.setMultiChoiceItems(2131296297, var6, var7);
      }

      class 2 implements OnClickListener {

         2() {}

         public void onClick(DialogInterface var1, int var2) {
            AccountSyncSettings var3 = AccountSyncSettings.this;
            boolean[] var4 = AccountSyncSettings.this.peakDaysValues;
            boolean[] var5 = AccountSyncSettings.this.peakDaysNewValues;
            var3.arrayCopy(var4, var5);
            var1.dismiss();
         }
      }

      class 3 implements OnClickListener {

         3() {}

         public void onClick(DialogInterface var1, int var2) {
            AccountSyncSettings var3 = AccountSyncSettings.this;
            boolean[] var4 = AccountSyncSettings.this.peakDaysNewValues;
            boolean[] var5 = AccountSyncSettings.this.peakDaysValues;
            var3.arrayCopy(var4, var5);
            AccountSyncSettings.this.expressPeakDays();
         }
      }

      class 1 implements OnMultiChoiceClickListener {

         1() {}

         public void onClick(DialogInterface var1, int var2, boolean var3) {
            AccountSyncSettings.this.peakDaysNewValues[var2] = var3;
         }
      }
   }

   class 7 implements Runnable {

      // $FF: synthetic field
      final HashMap val$changedSettings;


      7(HashMap var2) {
         this.val$changedSettings = var2;
      }

      public void run() {
         try {
            Z7MailHandler var1 = AccountSyncSettings.this.zHandler;
            HashMap var2 = this.val$changedSettings;
            var1.updateSettings(0, var2);
         } catch (RemoteException var6) {
            AccountSyncSettings var4 = AccountSyncSettings.this;
            AccountSyncSettings.7.1 var5 = new AccountSyncSettings.7.1(var6);
            var4.runOnUiThread(var5);
         }
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final RemoteException val$e;


         1(RemoteException var2) {
            this.val$e = var2;
         }

         public void run() {
            AccountSyncSettings var1 = AccountSyncSettings.this;
            RemoteException var2 = this.val$e;
            ExceptionUtil.showDialogException(var1, var2);
         }
      }
   }

   class 4 implements OnPreferenceChangeListener {

      4() {}

      public boolean onPreferenceChange(Preference var1, Object var2) {
         String var3 = var2.toString();
         int var4 = AccountSyncSettings.this.mPeakTimeSchedule.findIndexOfValue(var3);
         ListPreference var5 = AccountSyncSettings.this.mPeakTimeSchedule;
         CharSequence var6 = AccountSyncSettings.this.mPeakTimeSchedule.getEntries()[var4];
         var5.setSummary(var6);
         AccountSyncSettings.this.mPeakTimeSchedule.setValue(var3);
         return false;
      }
   }

   class 5 implements OnPreferenceChangeListener {

      5() {}

      public boolean onPreferenceChange(Preference var1, Object var2) {
         String var3 = var2.toString();
         int var4 = AccountSyncSettings.this.mOffPeakTimeSchedule.findIndexOfValue(var3);
         ListPreference var5 = AccountSyncSettings.this.mOffPeakTimeSchedule;
         CharSequence var6 = AccountSyncSettings.this.mOffPeakTimeSchedule.getEntries()[var4];
         var5.setSummary(var6);
         AccountSyncSettings.this.mOffPeakTimeSchedule.setValue(var3);
         return false;
      }
   }

   class 2 extends ListPreference {

      2(Context var2) {
         super(var2);
      }

      protected void onClick() {
         boolean var1 = (boolean)(AccountSyncSettings.this.isPeakStartTime = (boolean)1);
         AccountSyncSettings.this.actionSettingsPeakTime();
      }
   }

   class 3 extends ListPreference {

      3(Context var2) {
         super(var2);
      }

      protected void onClick() {
         boolean var1 = (boolean)(AccountSyncSettings.this.isPeakStartTime = (boolean)0);
         AccountSyncSettings.this.actionSettingsPeakTime();
      }
   }

   class 1 implements OnTimeSetListener {

      1() {}

      public void onTimeSet(TimePicker var1, int var2, int var3) {
         String var4 = Integer.toString(var2);
         if(var4.length() == 1) {
            var4 = "0" + var4;
         }

         String var5 = Integer.toString(var3);
         if(var5.length() == 1) {
            var5 = "0" + var5;
         }

         String var6 = var4 + ":" + var5;
         if(AccountSyncSettings.this.isPeakStartTime) {
            AccountSyncSettings.this.mAccount.mPeakStartTime = var6;
            ListPreference var7 = AccountSyncSettings.this.mPeakStartTime;
            AccountSyncSettings var8 = AccountSyncSettings.this;
            String var9 = AccountSyncSettings.this.mAccount.mPeakStartTime;
            String var10 = var8.getTimeAppliedCurFormat(var9);
            var7.setSummary(var10);
         } else {
            AccountSyncSettings.this.mAccount.mPeakEndTime = var6;
            ListPreference var11 = AccountSyncSettings.this.mPeakEndTime;
            AccountSyncSettings var12 = AccountSyncSettings.this;
            String var13 = AccountSyncSettings.this.mAccount.mPeakEndTime;
            String var14 = var12.getTimeAppliedCurFormat(var13);
            var11.setSummary(var14);
         }
      }
   }
}
