package com.android.email.activity.setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.android.email.AccountBackupRestore;
import com.android.email.EmailTwSoftkeyItem;
import com.android.email.Utility;
import com.android.email.activity.setup.AccountSetupCheckSettingsPremium;
import com.android.email.provider.EmailContent;

public class AccountSetupIncomingPremium extends Activity implements OnClickListener {

   private static final int DIALOG_DUPLICATE_ACCOUNT = 1;
   private static final String EXTRA_ACCOUNT_KEY = "account_key";
   private static final String STATE_DUPLE_ACCOUNTNAME = "com.android.email.DUPLEACCOUNTNAME";
   private EmailContent.Account mAccount;
   private String mCacheLoginCredential;
   private String mDuplicateAccountName;
   private boolean mMakeDefault;
   private EmailTwSoftkeyItem mNextTwSoftkeyItem;
   private EditText mPasswordView;
   private EditText mUsernameView;


   public AccountSetupIncomingPremium() {}

   public static void actionEditIncomingSettings(Activity var0, long var1) {
      Intent var3 = new Intent(var0, AccountSetupIncomingPremium.class);
      Intent var4 = var3.setAction("android.intent.action.EDIT");
      var3.putExtra("account_key", var1);
      var0.startActivity(var3);
   }

   private void onNext() {
      int var1 = (int)this.mAccount.mSevenAccountKey;
      String var2 = this.mPasswordView.getText().toString().trim();
      AccountSetupCheckSettingsPremium.actionValidateSettings(this, var1, var2);
   }

   private void validateFields() {
      EmailTwSoftkeyItem var1 = this.mNextTwSoftkeyItem;
      boolean var2 = Utility.requiredFieldValid((TextView)this.mPasswordView);
      var1.setEnabled(var2);
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if(var2 == -1) {
         ContentValues var4 = new ContentValues();
         String var5 = this.mPasswordView.getText().toString().trim();
         var4.put("password", var5);
         ContentResolver var6 = this.getContentResolver();
         Uri var7 = EmailContent.HostAuth.CONTENT_URI;
         String[] var8 = new String[1];
         String var9 = Long.toString(this.mAccount.mHostAuthKeyRecv);
         var8[0] = var9;
         var6.update(var7, var4, "_id=?", var8);
         ContentResolver var11 = this.getContentResolver();
         Uri var12 = EmailContent.HostAuth.CONTENT_URI;
         String[] var13 = new String[1];
         String var14 = Long.toString(this.mAccount.mHostAuthKeySend);
         var13[0] = var14;
         var11.update(var12, var4, "_id=?", var13);
         AccountBackupRestore.backupAccounts(this);
         this.finish();
      }
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131361862:
         this.onNext();
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903055);
      EditText var2 = (EditText)this.findViewById(2131361870);
      this.mUsernameView = var2;
      EditText var3 = (EditText)this.findViewById(2131361849);
      this.mPasswordView = var3;
      EmailTwSoftkeyItem var4 = (EmailTwSoftkeyItem)this.findViewById(2131361862);
      this.mNextTwSoftkeyItem = var4;
      this.mNextTwSoftkeyItem.setOnClickListener(this);
      AccountSetupIncomingPremium.1 var5 = new AccountSetupIncomingPremium.1();
      this.mUsernameView.setEnabled((boolean)0);
      this.mUsernameView.setFocusable((boolean)0);
      this.mPasswordView.addTextChangedListener(var5);
      long var6 = this.getIntent().getLongExtra("account_key", 65535L);
      EmailContent.Account var8 = EmailContent.Account.restoreAccountWithId(this, var6);
      this.mAccount = var8;
      if(var1 != null && var1.containsKey("account_key")) {
         long var9 = this.getIntent().getLongExtra("account_key", 65535L);
         EmailContent.Account var11 = EmailContent.Account.restoreAccountWithId(this, var9);
         this.mAccount = var11;
      }

      if(this.mAccount == null) {
         this.finish();
      } else {
         EditText var12 = this.mUsernameView;
         String var13 = this.mAccount.mEmailAddress;
         var12.setText(var13);
         EmailContent.Account var14 = this.mAccount;
         long var15 = this.mAccount.mHostAuthKeyRecv;
         EmailContent.HostAuth var17 = EmailContent.HostAuth.restoreHostAuthWithId(this, var15);
         var14.mHostAuthRecv = var17;
         String var18 = this.mAccount.mHostAuthRecv.mPassword;
         if(var18 != null && !"".equals(var18)) {
            this.mPasswordView.setText(var18);
         }

         this.validateFields();
      }
   }

   public Dialog onCreateDialog(int var1) {
      AlertDialog var2;
      switch(var1) {
      case 1:
         Builder var3 = (new Builder(this)).setIcon(17301543).setTitle(2131167097);
         Object[] var4 = new Object[1];
         String var5 = this.mDuplicateAccountName;
         var4[0] = var5;
         String var6 = this.getString(2131166355, var4);
         Builder var7 = var3.setMessage(var6);
         AccountSetupIncomingPremium.2 var8 = new AccountSetupIncomingPremium.2();
         var2 = var7.setPositiveButton(2131166260, var8).create();
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   public void onPrepareDialog(int var1, Dialog var2) {
      switch(var1) {
      case 1:
         if(this.mDuplicateAccountName == null) {
            return;
         }

         AlertDialog var3 = (AlertDialog)var2;
         Object[] var4 = new Object[1];
         String var5 = this.mDuplicateAccountName;
         var4[0] = var5;
         String var6 = this.getString(2131166355, var4);
         var3.setMessage(var6);
         return;
      default:
      }
   }

   protected void onRestoreInstanceState(Bundle var1) {
      super.onRestoreInstanceState(var1);
      String var2 = var1.getString("com.android.email.DUPLEACCOUNTNAME");
      this.mDuplicateAccountName = var2;
   }

   public void onResume() {
      super.onResume();
      long var1 = this.getIntent().getLongExtra("account_key", 65535L);
      EmailContent.Account var3 = EmailContent.Account.restoreAccountWithId(this, var1);
      this.mAccount = var3;
      if(this.mAccount == null) {
         this.finish();
      }
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      long var2 = this.mAccount.mAccountKey;
      var1.putLong("account_key", var2);
      String var4 = this.mDuplicateAccountName;
      var1.putString("com.android.email.DUPLEACCOUNTNAME", var4);
   }

   public boolean onSearchRequested() {
      return false;
   }

   class 1 implements TextWatcher {

      1() {}

      public void afterTextChanged(Editable var1) {
         AccountSetupIncomingPremium.this.validateFields();
      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}
   }

   class 2 implements android.content.DialogInterface.OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         AccountSetupIncomingPremium.this.dismissDialog(1);
      }
   }
}
