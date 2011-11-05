package com.android.email.activity.setup;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import com.android.email.Email;
import com.android.email.ExchangeUtils;
import com.android.email.activity.setup.AccountSettingsUtils;
import com.android.email.activity.setup.AccountSetupNames;
import com.android.email.activity.setup.SpinnerOption;
import com.android.email.mail.Store;
import com.android.email.mail.store.ExchangeStore;
import com.android.email.provider.EmailContent;
import com.sonyericsson.email.utils.AccountProvider;
import com.sonyericsson.email.utils.customization.AccountData;
import java.io.IOException;
import java.util.Set;

public class AccountSetupOptions extends Activity implements OnClickListener {

   private static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_EAS_FLOW = "easFlow";
   private static final String EXTRA_EXIT_AFTER_SETUP = "exitAfterSetup";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private static final int SYNC_WINDOW_EAS_DEFAULT = 2;
   private EmailContent.Account mAccount;
   AccountManagerCallback<Bundle> mAccountManagerCallback;
   private Spinner mCheckFrequencyView;
   private CheckBox mDefaultView;
   private boolean mDonePressed;
   private boolean mEasFlowMode;
   private boolean mExitAfterSetup;
   private Handler mHandler;
   private CheckBox mNotifyView;
   private CheckBox mSyncCalendarView;
   private CheckBox mSyncContactsView;
   private Spinner mSyncWindowView;
   private int oldFreq;


   public AccountSetupOptions() {
      Handler var1 = new Handler();
      this.mHandler = var1;
      this.mDonePressed = (boolean)0;
      AccountSetupOptions.2 var2 = new AccountSetupOptions.2();
      this.mAccountManagerCallback = var2;
   }

   public static void actionOptions(Activity var0, EmailContent.Account var1, boolean var2, boolean var3, boolean var4) {
      Intent var5 = new Intent(var0, AccountSetupOptions.class);
      var5.putExtra("account", var1);
      var5.putExtra("makeDefault", var2);
      var5.putExtra("easFlow", var3);
      var5.putExtra("exitAfterSetup", var4);
      var0.startActivity(var5);
   }

   private void enableEASSyncWindowSpinner() {
      this.findViewById(2131558442).setVisibility(0);
      this.mSyncWindowView.setVisibility(0);
      CharSequence[] var1 = this.getResources().getTextArray(2131099653);
      CharSequence[] var2 = this.getResources().getTextArray(2131099652);
      SpinnerOption[] var3 = new SpinnerOption[var2.length];
      int var4 = -1;
      int var5 = 0;

      while(true) {
         int var6 = var2.length;
         if(var5 >= var6) {
            ArrayAdapter var11 = new ArrayAdapter(this, 17367048, var3);
            var11.setDropDownViewResource(2130903075);
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
      (new AccountSetupOptions.4()).start();
      long var4 = this.mAccount.mId;
      boolean var6 = this.mEasFlowMode;
      boolean var7 = this.mExitAfterSetup;
      AccountSetupNames.actionSetNames(this, var4, var6, var7);
      ExchangeUtils.startExchangeService(this);
      this.finish();
   }

   private void onDone() {
      EmailContent.Account var1 = this.mAccount;
      String var2 = this.mAccount.getEmailAddress();
      var1.setDisplayName(var2);
      int var3 = this.mAccount.getFlags() & -2;
      if(this.mNotifyView.isChecked()) {
         var3 |= 1;
      }

      this.mAccount.setFlags(var3);
      EmailContent.Account var4 = this.mAccount;
      int var5 = ((Integer)((SpinnerOption)this.mCheckFrequencyView.getSelectedItem()).value).intValue();
      var4.setSyncInterval(var5);
      if(this.mSyncWindowView.getVisibility() == 0) {
         int var6 = ((Integer)((SpinnerOption)this.mSyncWindowView.getSelectedItem()).value).intValue();
         this.mAccount.setSyncLookback(var6);
      }

      EmailContent.Account var7 = this.mAccount;
      boolean var8 = this.mDefaultView.isChecked();
      var7.setDefaultAccount(var8);
      if(!this.mAccount.isSaved() && this.mAccount.mHostAuthRecv != null && this.mAccount.mHostAuthRecv.mProtocol.equals("eas")) {
         boolean var9 = this.mSyncContactsView.isChecked();
         boolean var10 = this.mSyncCalendarView.isChecked();
         EmailContent.Account var11 = this.mAccount;
         int var12 = var11.mFlags | 16;
         var11.mFlags = var12;
         EmailContent.Account var13 = this.mAccount;
         AccountSettingsUtils.commitSettings(this, var13);
         Application var14 = this.getApplication();
         EmailContent.Account var15 = this.mAccount;
         AccountManagerCallback var16 = this.mAccountManagerCallback;
         ExchangeStore.addSystemAccount(var14, var15, var9, var10, var16);
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
      case 2131558417:
         if(this.mDonePressed) {
            return;
         }

         this.onDone();
         this.mDonePressed = (boolean)1;
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903048);
      this.setTheme(16973829);
      Spinner var2 = (Spinner)this.findViewById(2131558441);
      this.mCheckFrequencyView = var2;
      Spinner var3 = (Spinner)this.findViewById(2131558443);
      this.mSyncWindowView = var3;
      CheckBox var4 = (CheckBox)this.findViewById(2131558418);
      this.mDefaultView = var4;
      CheckBox var5 = (CheckBox)this.findViewById(2131558444);
      this.mNotifyView = var5;
      CheckBox var6 = (CheckBox)this.findViewById(2131558445);
      this.mSyncContactsView = var6;
      CheckBox var7 = (CheckBox)this.findViewById(2131558446);
      this.mSyncCalendarView = var7;
      this.findViewById(2131558417).setOnClickListener(this);
      EmailContent.Account var8 = (EmailContent.Account)this.getIntent().getParcelableExtra("account");
      this.mAccount = var8;
      boolean var9 = this.getIntent().getBooleanExtra("makeDefault", (boolean)0);
      boolean var10 = this.getIntent().getBooleanExtra("exitAfterSetup", (boolean)0);
      this.mExitAfterSetup = var10;
      AccountData var11 = AccountProvider.getSettingsFromDefaultUX(this.getApplicationContext());
      Store.StoreInfo var12 = Store.StoreInfo.getStoreInfo(this.mAccount.getStoreUri(this), this);
      Context var13 = this.getApplicationContext();
      byte var14;
      if(var12 != null) {
         var14 = var12.mPushSupported;
      } else {
         var14 = 0;
      }

      String[] var15 = AccountSettingsUtils.getCheckIntervalValueList(var13, (boolean)var14, var11);
      Context var16 = this.getApplicationContext();
      byte var17;
      if(var12 != null) {
         var17 = var12.mPushSupported;
      } else {
         var17 = 0;
      }

      String[] var18 = AccountSettingsUtils.getCheckIntervalEntryList(var16, (boolean)var17, var11);
      SpinnerOption[] var19 = new SpinnerOption[var18.length];
      int var20 = 0;

      while(true) {
         int var21 = var18.length;
         if(var20 >= var21) {
            ArrayAdapter var25 = new ArrayAdapter(this, 17367048, var19);
            var25.setDropDownViewResource(2130903075);
            this.mCheckFrequencyView.setAdapter(var25);
            int var26 = ((Integer)((SpinnerOption)this.mCheckFrequencyView.getSelectedItem()).value).intValue();
            this.oldFreq = var26;
            Spinner var27 = this.mCheckFrequencyView;
            AccountSetupOptions.1 var28 = new AccountSetupOptions.1();
            var27.setOnItemSelectedListener(var28);
            if(var12 != null && var12.mVisibleLimitDefault == -1) {
               this.enableEASSyncWindowSpinner();
            }

            if(this.mAccount.mIsDefault || var9) {
               this.mDefaultView.setChecked((boolean)1);
            }

            CheckBox var29 = this.mNotifyView;
            byte var30;
            if((this.mAccount.getFlags() & 1) != 0) {
               var30 = 1;
            } else {
               var30 = 0;
            }

            var29.setChecked((boolean)var30);
            Spinner var31 = this.mCheckFrequencyView;
            Integer var32 = Integer.valueOf(this.mAccount.getSyncInterval());
            SpinnerOption.setSpinnerOptionValue(var31, var32);
            boolean var33 = this.getIntent().getBooleanExtra("easFlow", (boolean)0);
            this.mEasFlowMode = var33;
            if(var12 == null) {
               return;
            } else {
               String var34 = var12.mScheme;
               if(!"eas".equals(var34)) {
                  return;
               } else {
                  this.mSyncContactsView.setVisibility(0);
                  this.mSyncContactsView.setChecked((boolean)1);
                  this.mSyncCalendarView.setVisibility(0);
                  this.mSyncCalendarView.setChecked((boolean)1);
                  this.findViewById(2131558440).setVisibility(0);
                  return;
               }
            }
         }

         Integer var22 = Integer.valueOf(var15[var20].toString());
         String var23 = var18[var20].toString();
         SpinnerOption var24 = new SpinnerOption(var22, var23);
         var19[var20] = var24;
         ++var20;
      }
   }

   class 1 implements OnItemSelectedListener {

      1() {}

      public void onItemSelected(AdapterView<?> var1, View var2, int var3, long var4) {
         label24: {
            label26: {
               int var6 = ((Integer)((SpinnerOption)AccountSetupOptions.this.mCheckFrequencyView.getSelectedItem()).value).intValue();
               if(var6 > 0) {
                  int var7 = AccountSetupOptions.this.oldFreq;
                  if(var6 < var7) {
                     break label26;
                  }
               }

               if((AccountSetupOptions.this.oldFreq != -1 || var6 == -1) && (AccountSetupOptions.this.oldFreq == -1 || var6 != -1)) {
                  break label24;
               }
            }

            AccountSetupOptions var8 = AccountSetupOptions.this;
            Builder var9 = new Builder(var8);
            Builder var10 = var9.setMessage(2131165468);
            AccountSetupOptions.1.1 var11 = new AccountSetupOptions.1.1();
            var9.setPositiveButton(2131165215, var11);
            AlertDialog var13 = var9.show();
         }

         AccountSetupOptions var14 = AccountSetupOptions.this;
         int var15 = ((Integer)((SpinnerOption)AccountSetupOptions.this.mCheckFrequencyView.getSelectedItem()).value).intValue();
         var14.oldFreq = var15;
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
         Integer var10 = Integer.valueOf(2131165450);
         var9[0] = var10;
         var8.showErrorDialog(2131165390, var9);
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
         String var3 = AccountSetupOptions.this.getString(2131165383);
         Builder var4 = var2.setTitle(var3);
         AccountSetupOptions var5 = AccountSetupOptions.this;
         int var6 = this.val$msgResId;
         Object[] var7 = this.val$args;
         String var8 = var5.getString(var6, var7);
         Builder var9 = var4.setMessage(var8).setCancelable((boolean)1);
         String var10 = AccountSetupOptions.this.getString(2131165404);
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

   class 4 extends Thread {

      4() {}

      public void run() {
         boolean var1 = Email.setServicesEnabled(AccountSetupOptions.this);
      }
   }
}
