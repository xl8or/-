package com.android.email.activity.setup;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.AlertDialog.Builder;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.android.email.Email;
import com.android.email.ExchangeUtils;
import com.android.email.activity.setup.AccountSettingsUtils;
import com.android.email.activity.setup.AccountSetupCustomer;
import com.android.email.activity.setup.AccountSetupNames;
import com.android.email.activity.setup.SpinnerOption;
import com.android.email.mail.Store;
import com.android.email.mail.store.ExchangeStore;
import com.android.email.provider.EmailContent;
import com.android.exchange.Eas;
import com.android.exchange.EasSyncService;
import com.android.exchange.SyncScheduleData;
import com.digc.seven.SevenSyncProvider;
import java.io.IOException;
import java.util.Set;

public class AccountSetupOptions extends Activity implements OnClickListener, OnTouchListener {

   private static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_EAS_FLOW = "easFlow";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private static final int SYNC_WINDOW_EAS_DEFAULT = 2;
   private String TAG;
   private EmailContent.Account mAccount;
   AccountManagerCallback<Bundle> mAccountManagerCallback;
   private Spinner mCalendarSyncWindowView;
   private TextView mCheckFrequencyText;
   private Spinner mCheckFrequencyView;
   private CheckBox mDefaultView;
   private LinearLayout mDefaultViewLayout;
   private boolean mDonePressed;
   private boolean mEasFlowMode;
   private Spinner mEmailSizeView;
   private Handler mHandler;
   private CheckBox mNotifyView;
   private LinearLayout mNotifyViewLayout;
   private TextView mOffPeakScheduleText;
   private Spinner mOffPeakScheduleView;
   private TextView mPeakScheduleText;
   private Spinner mPeakScheduleView;
   private CheckBox mSyncCalendarView;
   private LinearLayout mSyncCalendarViewLayout;
   private CheckBox mSyncContactsView;
   private LinearLayout mSyncContactsViewLayout;
   private CheckBox mSyncSmsView;
   private LinearLayout mSyncSmsViewLayout;
   private CheckBox mSyncTaskView;
   private LinearLayout mSyncTaskViewLayout;
   private Spinner mSyncWindowView;
   private int oldSyncTime;


   public AccountSetupOptions() {
      Handler var1 = new Handler();
      this.mHandler = var1;
      this.mDonePressed = (boolean)0;
      this.TAG = "AccountSetupOptions";
      AccountSetupOptions.2 var2 = new AccountSetupOptions.2();
      this.mAccountManagerCallback = var2;
   }

   public static void actionOptions(Activity var0, EmailContent.Account var1, boolean var2, boolean var3) {
      Intent var4 = new Intent(var0, AccountSetupOptions.class);
      var4.putExtra("account", var1);
      var4.putExtra("makeDefault", var2);
      var4.putExtra("easFlow", var3);
      var0.startActivity(var4);
   }

   private void enableEASCalendarSyncWindowSpinner() {
      this.findViewById(2131361902).setVisibility(0);
      this.mCalendarSyncWindowView.setVisibility(0);
      CharSequence[] var1 = this.getResources().getTextArray(2131296292);
      CharSequence[] var2 = this.getResources().getTextArray(2131296291);
      SpinnerOption[] var3 = new SpinnerOption[var2.length];
      int var4 = 0;

      while(true) {
         int var5 = var2.length;
         if(var4 >= var5) {
            ArrayAdapter var9 = new ArrayAdapter(this, 17367048, var3);
            var9.setDropDownViewResource(2130903192);
            this.mCalendarSyncWindowView.setAdapter(var9);
            Spinner var10 = this.mCalendarSyncWindowView;
            Integer var11 = Integer.valueOf(this.mAccount.getCalendarSyncLookback());
            SpinnerOption.setSpinnerOptionValue(var10, var11);
            return;
         }

         Integer var6 = Integer.valueOf(var1[var4].toString());
         String var7 = var2[var4].toString();
         SpinnerOption var8 = new SpinnerOption(var6, var7);
         var3[var4] = var8;
         ++var4;
      }
   }

   private void enableEASEmailSizeSpinner() {
      this.findViewById(2131361900).setVisibility(0);
      this.mEmailSizeView.setVisibility(0);
      int var1 = -1;
      String var2 = this.getString(2131166676);
      CharSequence[] var3 = this.getResources().getTextArray(2131296259);
      CharSequence[] var4 = this.getResources().getTextArray(2131296258);
      int var5 = var4.length;
      SpinnerOption[] var6 = new SpinnerOption[var5];

      for(int var7 = 0; var7 < var5; ++var7) {
         String var8 = var4[var7].toString();
         int var9 = Integer.valueOf(var3[var7].toString()).intValue();
         if(var2.equals(var8)) {
            var1 = var9;
         }

         Integer var10 = Integer.valueOf(var9);
         SpinnerOption var11 = new SpinnerOption(var10, var8);
         var6[var7] = var11;
      }

      ArrayAdapter var12 = new ArrayAdapter(this, 17367048, var6);
      var12.setDropDownViewResource(2130903192);
      this.mEmailSizeView.setAdapter(var12);
      this.mEmailSizeView.setSelection(var1);
   }

   private void enableEASSyncWindowSpinner() {
      this.findViewById(2131361898).setVisibility(0);
      this.mSyncWindowView.setVisibility(0);
      CharSequence[] var1 = this.getResources().getTextArray(2131296263);
      CharSequence[] var2 = this.getResources().getTextArray(2131296262);
      SpinnerOption[] var3 = new SpinnerOption[var2.length];
      int var4 = -1;
      int var5 = 0;

      while(true) {
         int var6 = var2.length;
         if(var5 >= var6) {
            ArrayAdapter var11 = new ArrayAdapter(this, 17367048, var3);
            var11.setDropDownViewResource(2130903192);
            this.mSyncWindowView.setAdapter(var11);
            Spinner var12 = this.mSyncWindowView;
            Integer var13 = Integer.valueOf(this.mAccount.getSyncLookback());
            SpinnerOption.setSpinnerOptionValue(var12, var13);
            if(var4 < 0) {
               return;
            }

            this.mSyncWindowView.setSelection(var4);
            return;
         }

         int var7 = Integer.valueOf(var1[var5].toString()).intValue();
         Integer var8 = Integer.valueOf(var7);
         String var9 = var2[var5].toString();
         SpinnerOption var10 = new SpinnerOption(var8, var9);
         var3[var5] = var10;
         if(var7 == 2) {
            var4 = var5;
         }

         ++var5;
      }
   }

   private void finishOnDone() {
      EmailContent.Account var1 = this.mAccount;
      int var2 = var1.mFlags & -17;
      var1.mFlags = var2;
      EmailContent.Account var3 = this.mAccount;
      AccountSettingsUtils.commitSettings(this, var3);
      boolean var4 = Email.setServicesEnabled(this);
      long var5 = this.mAccount.mId;
      boolean var7 = this.mEasFlowMode;
      Object var9 = null;
      Object var10 = null;
      AccountSetupNames.actionSetNames(this, var5, var7, (String)null, (String)var9, (String)var10, 0);
      ExchangeUtils.startExchangeService(this);
      this.finish();
   }

   private void onDone() {
      EmailContent.Account var1 = this.mAccount;
      String var3 = var1.getStoreUri(this);
      Store.StoreInfo var5 = Store.StoreInfo.getStoreInfo(var3, this);
      DevicePolicyManager var6 = (DevicePolicyManager)this.getApplicationContext().getSystemService("device_policy");
      SyncScheduleData var7 = null;
      if(var5 != null) {
         String var8 = var5.mScheme;
         if("eas".equals(var8)) {
            var7 = this.mAccount.getSyncScheduleData();
         }
      }

      EmailContent.Account var9 = this.mAccount;
      String var10 = this.mAccount.getEmailAddress();
      var9.setDisplayName(var10);
      int var11 = this.mAccount.getFlags() & -2;
      if(this.mNotifyView.isChecked()) {
         var11 |= 1;
      }

      label86: {
         EmailContent.Account var12 = this.mAccount;
         var12.setFlags(var11);
         if(var5 != null) {
            String var14 = var5.mScheme;
            if("eas".equals(var14)) {
               int var15 = ((Integer)((SpinnerOption)this.mPeakScheduleView.getSelectedItem()).value).intValue();
               var7.setPeakSchedule(var15);
               int var18 = ((Integer)((SpinnerOption)this.mOffPeakScheduleView.getSelectedItem()).value).intValue();
               var7.setOffPeakSchedule(var18);
               EmailContent.Account var21 = this.mAccount;
               var21.setSyncScheduleData(var7);
               break label86;
            }
         }

         EmailContent.Account var91 = this.mAccount;
         int var92 = ((Integer)((SpinnerOption)this.mCheckFrequencyView.getSelectedItem()).value).intValue();
         var91.setSyncInterval(var92);
      }

      int var23;
      if(this.mSyncWindowView.getVisibility() == 0) {
         var23 = ((Integer)((SpinnerOption)this.mSyncWindowView.getSelectedItem()).value).intValue();
         if(var6 != null) {
            int var24 = var6.getMaxEmailAge((ComponentName)null);
            if(var24 == 0) {
               ;
            }

            if(var24 < var23) {
               StringBuilder var27 = (new StringBuilder()).append("Exchange IT Policy has restricted SyncLookback:");
               String var29 = var27.append(var24).toString();
               int var30 = Log.e("AccountSetupOptions", var29);
               EmailContent.Account var31 = this.mAccount;
               var31.setSyncLookback(var24);
            } else {
               EmailContent.Account var93 = this.mAccount;
               var93.setSyncLookback(var23);
            }
         }

         if(var5 != null) {
            String var33 = var5.mScheme;
            if("eas".equals(var33)) {
               EmailContent.Account var34 = this.mAccount;
               var34.setSyncScheduleData(var7);
            }
         }
      }

      EmailContent.Account var36 = this.mAccount;
      boolean var37 = this.mDefaultView.isChecked();
      var36.setDefaultAccount(var37);
      if(var6 != null) {
         byte var38 = (byte)(((Integer)((SpinnerOption)this.mEmailSizeView.getSelectedItem()).value).intValue() & 255);
         int var39 = Eas.EmailDataSize.parse(var38).toEas12Value() / 1024;
         int var40 = var6.getMaxEmailBodyTruncSize((ComponentName)null);
         String var41 = this.TAG;
         StringBuilder var42 = (new StringBuilder()).append("plainTextSize = ");
         String var44 = var42.append(var40).toString();
         Log.d(var41, var44);
         int var46 = var6.getMaxHtmlEmailBodyTruncSize((ComponentName)null) / 1024;
         String var47 = this.TAG;
         String var48 = "htmlSize = " + var46;
         Log.d(var47, var48);
         boolean var50 = var6.getAllowHTMLEmail((ComponentName)null);
         String var51 = this.TAG;
         String var52 = "isHtmlAllowed = " + var50;
         Log.d(var51, var52);
         int var54;
         if(var50 && var46 > 0) {
            var54 = var46;
         } else {
            var54 = var40;
         }

         int var55;
         if(var54 > 0) {
            var55 = var54 * 1024;
         } else {
            var55 = Integer.MAX_VALUE;
         }

         if(var55 < var39) {
            StringBuilder var58 = (new StringBuilder()).append("Exchange IT Policy has restricted SyncSize:");
            String var60 = var58.append(var55).append(" KB").toString();
            int var61 = Log.e("AccountSetupOptions", var60);
            if(var38 > 0) {
               var38 = Eas.EmailDataSize.parseToByte(var55);
            }
         }

         this.mAccount.setEmailSize(var38);
      } else {
         EmailContent.Account var95 = this.mAccount;
         byte var96 = (byte)(((Integer)((SpinnerOption)this.mEmailSizeView.getSelectedItem()).value).intValue() & 255);
         var95.setEmailSize(var96);
      }

      if(!this.mAccount.isSaved() && this.mAccount.mHostAuthRecv != null && this.mAccount.mHostAuthRecv.mProtocol.equals("eas")) {
         boolean var62 = this.mSyncContactsView.isChecked();
         boolean var63 = this.mSyncCalendarView.isChecked();
         boolean var64 = this.mSyncTaskView.isChecked();
         byte var65 = 0;
         boolean var66 = this.mSyncSmsView.isChecked();
         EmailContent.Account var67 = this.mAccount;
         int var68 = var67.mFlags | 16;
         var67.mFlags = var68;
         if(var66) {
            EmailContent.Account var69 = this.mAccount;
            int var70 = var69.mFlags | 512;
            var69.mFlags = var70;
         }

         EmailContent.Account var71 = this.mAccount;
         String var72 = Double.toString(EasSyncService.protocolVersion);
         var71.mProtocolVersion = var72;
         EmailContent.Account var73 = this.mAccount;
         AccountSettingsUtils.commitSettings(this, var73);
         Application var76 = this.getApplication();
         EmailContent.Account var77 = this.mAccount;
         AccountManagerCallback var78 = this.mAccountManagerCallback;
         ExchangeStore.addSystemAccount(var76, var77, var62, var63, var64, (boolean)var65, var78);
         var23 = ((Integer)((SpinnerOption)this.mCalendarSyncWindowView.getSelectedItem()).value).intValue();
         if(var6 != null) {
            int var80 = var6.getMaxCalendarAge((ComponentName)null);
            if(var80 > 0) {
               if(var23 != 0 && var80 >= var23) {
                  EmailContent.Account var97 = this.mAccount;
                  var97.setCalendarSyncLookback(var23);
                  return;
               }

               String var83 = this.TAG;
               StringBuilder var84 = (new StringBuilder()).append("Exchange IT Policy has restricted CalendarSyncLookback:");
               String var86 = var84.append(var80).toString();
               Log.e(var83, var86);
               EmailContent.Account var88 = this.mAccount;
               var88.setCalendarSyncLookback(var80);
               return;
            }
         }

         EmailContent.Account var100 = this.mAccount;
         var100.setCalendarSyncLookback(var23);
      } else {
         this.finishOnDone();
      }
   }

   private void showErrorDialog(int var1, Object ... var2) {
      Handler var3 = this.mHandler;
      AccountSetupOptions.3 var4 = new AccountSetupOptions.3(var1, var2);
      var3.post(var4);
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131361856:
         if(this.mDefaultView.isChecked()) {
            this.mDefaultView.setChecked((boolean)0);
            return;
         }

         this.mDefaultView.setChecked((boolean)1);
         return;
      case 2131361862:
         if(this.mDonePressed) {
            return;
         }

         this.onDone();
         this.mDonePressed = (boolean)1;
         return;
      case 2131361904:
         if(this.mNotifyView.isChecked()) {
            this.mNotifyView.setChecked((boolean)0);
            return;
         }

         this.mNotifyView.setChecked((boolean)1);
         return;
      case 2131361906:
         if(this.mSyncContactsView.isChecked()) {
            this.mSyncContactsView.setChecked((boolean)0);
            return;
         }

         this.mSyncContactsView.setChecked((boolean)1);
         return;
      case 2131361908:
         if(this.mSyncCalendarView.isChecked()) {
            this.mSyncCalendarView.setChecked((boolean)0);
            return;
         }

         this.mSyncCalendarView.setChecked((boolean)1);
         return;
      case 2131361910:
         if(this.mSyncTaskView.isChecked()) {
            this.mSyncTaskView.setChecked((boolean)0);
            return;
         }

         this.mSyncTaskView.setChecked((boolean)1);
         return;
      case 2131361912:
         if(this.mSyncSmsView.isChecked()) {
            this.mSyncSmsView.setChecked((boolean)0);
            return;
         }

         this.mSyncSmsView.setChecked((boolean)1);
         return;
      default:
      }
   }

   public void onCreate(Bundle param1) {
      // $FF: Couldn't be decompiled
   }

   protected void onRestoreInstanceState(Bundle var1) {
      super.onRestoreInstanceState(var1);
      if(AccountSetupCustomer.getInstance().getEmailType() == 1) {
         if(SevenSyncProvider.checkSevenApkVer(this)) {
            ;
         }
      }
   }

   public boolean onSearchRequested() {
      return false;
   }

   public boolean onTouch(View var1, MotionEvent var2) {
      boolean var3;
      switch(var1.getId()) {
      case 2131361856:
         switch(var2.getAction()) {
         case 0:
            this.mDefaultView.setPressed((boolean)1);
            var3 = true;
            return var3;
         case 1:
            this.mDefaultView.setPressed((boolean)0);
            if(this.mDefaultView.isChecked()) {
               this.mDefaultView.setChecked((boolean)0);
            } else {
               this.mDefaultView.setChecked((boolean)1);
            }

            var1.playSoundEffect(0);
            var3 = true;
            return var3;
         case 2:
            this.mDefaultView.setPressed((boolean)0);
            var3 = true;
            return var3;
         case 3:
            this.mDefaultView.setPressed((boolean)0);
            var3 = true;
            return var3;
         }
      case 2131361904:
         switch(var2.getAction()) {
         case 0:
            this.mNotifyView.setPressed((boolean)1);
            var3 = true;
            return var3;
         case 1:
            this.mNotifyView.setPressed((boolean)0);
            if(this.mNotifyView.isChecked()) {
               this.mNotifyView.setChecked((boolean)0);
            } else {
               this.mNotifyView.setChecked((boolean)1);
            }

            var1.playSoundEffect(0);
            var3 = true;
            return var3;
         case 2:
            this.mNotifyView.setPressed((boolean)0);
            var3 = true;
            return var3;
         case 3:
            this.mNotifyView.setPressed((boolean)0);
            var3 = true;
            return var3;
         }
      case 2131361906:
         switch(var2.getAction()) {
         case 0:
            this.mSyncContactsView.setPressed((boolean)1);
            var3 = true;
            return var3;
         case 1:
            this.mSyncContactsView.setPressed((boolean)0);
            if(this.mSyncContactsView.isChecked()) {
               this.mSyncContactsView.setChecked((boolean)0);
            } else {
               this.mSyncContactsView.setChecked((boolean)1);
            }

            var1.playSoundEffect(0);
            var3 = true;
            return var3;
         case 2:
            this.mSyncContactsView.setPressed((boolean)0);
            var3 = true;
            return var3;
         case 3:
            this.mSyncContactsView.setPressed((boolean)0);
            var3 = true;
            return var3;
         }
      case 2131361908:
         switch(var2.getAction()) {
         case 0:
            this.mSyncCalendarView.setPressed((boolean)1);
            var3 = true;
            return var3;
         case 1:
            this.mSyncCalendarView.setPressed((boolean)0);
            if(this.mSyncCalendarView.isChecked()) {
               this.mSyncCalendarView.setChecked((boolean)0);
            } else {
               this.mSyncCalendarView.setChecked((boolean)1);
            }

            var1.playSoundEffect(0);
            var3 = true;
            return var3;
         case 2:
            this.mSyncCalendarView.setPressed((boolean)0);
            var3 = true;
            return var3;
         case 3:
            this.mSyncCalendarView.setPressed((boolean)0);
            var3 = true;
            return var3;
         }
      case 2131361910:
         switch(var2.getAction()) {
         case 0:
            this.mSyncTaskView.setPressed((boolean)1);
            var3 = true;
            return var3;
         case 1:
            this.mSyncTaskView.setPressed((boolean)0);
            if(this.mSyncTaskView.isChecked()) {
               this.mSyncTaskView.setChecked((boolean)0);
            } else {
               this.mSyncTaskView.setChecked((boolean)1);
            }

            var1.playSoundEffect(0);
            var3 = true;
            return var3;
         case 2:
            this.mSyncTaskView.setPressed((boolean)0);
            var3 = true;
            return var3;
         case 3:
            this.mSyncTaskView.setPressed((boolean)0);
            var3 = true;
            return var3;
         }
      case 2131361912:
         switch(var2.getAction()) {
         case 0:
            this.mSyncSmsView.setPressed((boolean)1);
            var3 = true;
            return var3;
         case 1:
            this.mSyncSmsView.setPressed((boolean)0);
            if(this.mSyncSmsView.isChecked()) {
               this.mSyncSmsView.setChecked((boolean)0);
            } else {
               this.mSyncSmsView.setChecked((boolean)1);
            }

            var1.playSoundEffect(0);
            var3 = true;
            return var3;
         case 2:
            this.mSyncSmsView.setPressed((boolean)0);
            var3 = true;
            return var3;
         case 3:
            this.mSyncSmsView.setPressed((boolean)0);
            var3 = true;
            return var3;
         }
      default:
         var3 = false;
         return var3;
      }
   }

   class 1 implements OnItemSelectedListener {

      1() {}

      public void onItemSelected(AdapterView var1, View var2, int var3, long var4) {
         if(AccountSetupOptions.this.oldSyncTime == -1 && ((Integer)((SpinnerOption)AccountSetupOptions.this.mCheckFrequencyView.getSelectedItem()).value).intValue() != -1) {
            AccountSetupOptions var6 = AccountSetupOptions.this;
            Builder var7 = (new Builder(var6)).setIcon(17301543).setTitle(2131167120);
            String var8 = AccountSetupOptions.this.getString(2131167121);
            Builder var9 = var7.setMessage(var8);
            AccountSetupOptions.1.1 var10 = new AccountSetupOptions.1.1();
            AlertDialog var11 = var9.setPositiveButton(2131166262, var10).show();
         }

         AccountSetupOptions var12 = AccountSetupOptions.this;
         int var13 = ((Integer)((SpinnerOption)AccountSetupOptions.this.mCheckFrequencyView.getSelectedItem()).value).intValue();
         var12.oldSyncTime = var13;
      }

      public void onNothingSelected(AdapterView<?> var1) {}

      class 1 implements android.content.DialogInterface.OnClickListener {

         1() {}

         public void onClick(DialogInterface var1, int var2) {
            var1.dismiss();
         }
      }
   }

   class 2 implements AccountManagerCallback<Bundle> {

      2() {}

      public void run(AccountManagerFuture<Bundle> var1) {
         try {
            Set var2 = ((Bundle)var1.getResult()).keySet();
            Handler var3 = AccountSetupOptions.this.mHandler;
            AccountSetupOptions.2.1 var4 = new AccountSetupOptions.2.1();
            var3.post(var4);
            return;
         } catch (OperationCanceledException var17) {
            int var7 = Log.d("Email", "addAccount was canceled");
         } catch (IOException var18) {
            String var12 = "addAccount failed: " + var18;
            int var13 = Log.d("Email", var12);
         } catch (AuthenticatorException var19) {
            String var15 = "addAccount failed: " + var19;
            int var16 = Log.d("Email", var15);
         }

         AccountSetupOptions var8 = AccountSetupOptions.this;
         Object[] var9 = new Object[1];
         Integer var10 = Integer.valueOf(2131166923);
         var9[0] = var10;
         var8.showErrorDialog(2131166414, var9);
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            AccountSetupOptions.this.finishOnDone();
         }
      }
   }

   class 3 implements Runnable {

      // $FF: synthetic field
      final Object[] val$args;
      // $FF: synthetic field
      final int val$msgResId;


      3(int var2, Object[] var3) {
         this.val$msgResId = var2;
         this.val$args = var3;
      }

      public void run() {
         AccountSetupOptions var1 = AccountSetupOptions.this;
         Builder var2 = (new Builder(var1)).setIcon(17301543);
         String var3 = AccountSetupOptions.this.getString(2131166406);
         Builder var4 = var2.setTitle(var3);
         AccountSetupOptions var5 = AccountSetupOptions.this;
         int var6 = this.val$msgResId;
         Object[] var7 = this.val$args;
         String var8 = var5.getString(var6, var7);
         Builder var9 = var4.setMessage(var8).setCancelable((boolean)1);
         String var10 = AccountSetupOptions.this.getString(2131166421);
         AccountSetupOptions.3.1 var11 = new AccountSetupOptions.3.1();
         AlertDialog var12 = var9.setPositiveButton(var10, var11).show();
      }

      class 1 implements android.content.DialogInterface.OnClickListener {

         1() {}

         public void onClick(DialogInterface var1, int var2) {
            AccountSetupOptions.this.finish();
         }
      }
   }
}
