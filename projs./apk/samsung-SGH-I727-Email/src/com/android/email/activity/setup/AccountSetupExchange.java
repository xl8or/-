package com.android.email.activity.setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.android.email.AccountBackupRestore;
import com.android.email.Email;
import com.android.email.EmailTwSoftkeyItem;
import com.android.email.ExchangeUtils;
import com.android.email.Utility;
import com.android.email.activity.setup.AccountSetupBasics;
import com.android.email.activity.setup.AccountSetupCheckSettings;
import com.android.email.activity.setup.AccountSetupCustomer;
import com.android.email.activity.setup.AccountSetupOptions;
import com.android.email.activity.setup.AutoDiscoverSetupExchange;
import com.android.email.provider.EmailContent;
import com.android.email.service.IEmailService;
import com.android.email.service.IEmailServiceCallback;
import com.digc.seven.SevenSyncProvider;
import java.net.URI;
import java.net.URISyntaxException;

public class AccountSetupExchange extends Activity implements OnClickListener, OnCheckedChangeListener {

   private static final int DIALOG_DUPLICATE_ACCOUNT = 1;
   private static final int DIALOG_EXCHANGE_ACCOUNT_WARNNING = 2;
   static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_ACCOUNT_EDIT = "accountEdit";
   static final String EXTRA_DISABLE_AUTO_DISCOVER = "disableAutoDiscover";
   private static final String EXTRA_EAS_FLOW = "easFlow";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private static final String STATE_DUPLE_ACCOUNTNAME = "com.android.email.DUPLEACCOUNTNAME";
   private EmailContent.Account mAccount;
   private String mCacheLoginCredential;
   private String mDuplicateAccountName;
   private boolean mEASFlowMode;
   private boolean mMakeDefault;
   private EmailTwSoftkeyItem mNextTwSoftkeyItem;
   private EditText mPasswordView;
   private EditText mServerView;
   private CheckBox mSslSecurityView;
   private LinearLayout mSslSecurityViewLayout;
   private CheckBox mTrustCertificatesView;
   private LinearLayout mTrustCertificatesViewLayout;
   private EditText mUsernameView;


   public AccountSetupExchange() {}

   public static void actionEditIncomingSettings(Activity var0, EmailContent.Account var1, boolean var2) {
      Intent var3 = new Intent(var0, AccountSetupExchange.class);
      Intent var4 = var3.setAction("android.intent.action.EDIT");
      var3.putExtra("account", var1);
      var3.putExtra("accountEdit", var2);
      var0.startActivity(var3);
   }

   public static void actionEditOutgoingSettings(Activity var0, EmailContent.Account var1) {
      Intent var2 = new Intent(var0, AccountSetupExchange.class);
      Intent var3 = var2.setAction("android.intent.action.EDIT");
      var2.putExtra("account", var1);
      var0.startActivity(var2);
   }

   public static void actionIncomingSettings(Activity var0, EmailContent.Account var1, boolean var2, boolean var3, boolean var4) {
      Intent var5 = new Intent(var0, AccountSetupExchange.class);
      var5.putExtra("account", var1);
      var5.putExtra("makeDefault", var2);
      var5.putExtra("easFlow", var3);
      if(!var4) {
         Intent var9 = var5.putExtra("disableAutoDiscover", (boolean)1);
      }

      var0.startActivity(var5);
   }

   private void doActivityResultAutoDiscoverNewAccount(int var1, Intent var2) {
      if(var1 == 2) {
         EmailContent.Account var3 = this.mAccount;
         boolean var4 = this.mMakeDefault;
         AutoDiscoverSetupExchange.actionAutoDiscoverSetupExchangeIntent(this, var3, var4, (boolean)1);
      } else if(var1 != 1) {
         if(var1 != 4) {
            if(var2 != null) {
               Parcelable var5 = var2.getParcelableExtra("HostAuth");
               if(var5 != null) {
                  EmailContent.HostAuth var6 = (EmailContent.HostAuth)var5;
                  this.mAccount.mHostAuthSend = var6;
                  this.mAccount.mHostAuthRecv = var6;
                  EmailContent.Account var7 = this.mAccount;
                  this.loadFields(var7);
                  if(this.validateFields()) {
                     this.onNext();
                  }
               }
            }
         }
      }
   }

   private void doActivityResultValidateExistingAccount(int var1, Intent var2) {
      if(var1 == -1) {
         if(this.mAccount.isSaved()) {
            EmailContent.Account var3 = this.mAccount;
            ContentValues var4 = this.mAccount.toContentValues();
            var3.update(this, var4);
            EmailContent.HostAuth var6 = this.mAccount.mHostAuthRecv;
            ContentValues var7 = this.mAccount.mHostAuthRecv.toContentValues();
            var6.update(this, var7);
            EmailContent.HostAuth var9 = this.mAccount.mHostAuthSend;
            ContentValues var10 = this.mAccount.mHostAuthSend.toContentValues();
            var9.update(this, var10);
            String var12 = this.mAccount.mHostAuthRecv.mProtocol;
            if("eas".equals(var12)) {
               Object var13 = null;

               try {
                  IEmailService var14 = ExchangeUtils.getExchangeEmailService(this, (IEmailServiceCallback)var13);
                  long var15 = this.mAccount.mId;
                  var14.hostChanged(var15);
               } catch (RemoteException var19) {
                  ;
               }
            }
         } else {
            Uri var17 = this.mAccount.save(this);
         }

         AccountBackupRestore.backupAccounts(this);
         this.finish();
      }
   }

   private void doActivityResultValidateNewAccount(int var1, Intent var2) {
      if(var1 == -1) {
         this.doOptions();
      } else if(var1 == 4) {
         this.finish();
      }
   }

   private void doOptions() {
      boolean var1 = this.getIntent().getBooleanExtra("easFlow", (boolean)0);
      EmailContent.Account var2 = this.mAccount;
      boolean var3 = this.mMakeDefault;
      AccountSetupOptions.actionOptions(this, var2, var3, var1);
      this.finish();
   }

   private URI getAutDiscoverUri(Intent var1) {
      String var3 = "com.android.email.EMAIL";
      String var4 = var1.getStringExtra(var3);
      String var6 = "com.android.email.DOMAIN";
      String var7 = var1.getStringExtra(var6);
      String var9 = "com.android.email.USER";
      String var10 = var1.getStringExtra(var9);
      String var12 = "com.android.email.PASSWORD";
      String var13 = var1.getStringExtra(var12);
      String var15 = "com.android.email.TRUSTCERT";
      byte var16 = 0;
      String var17;
      if(var1.getBooleanExtra(var15, (boolean)var16)) {
         var17 = "eas+ssl+trustallcerts";
      } else {
         var17 = "eas+ssl+";
      }

      int var18 = var4.indexOf(64) + 1;
      String var19 = var4.substring(var18);
      String var21 = "\\";
      if(var10.startsWith(var21)) {
         byte var23 = 1;
         var10 = var10.substring(var23);
      }

      StringBuilder var24 = new StringBuilder();
      String var26 = var24.append(var10).append(":").append(var13).toString();
      if(var7 != null) {
         var26 = var7 + "\\" + var26;
      }

      Object var27 = null;

      URI var28;
      try {
         var28 = new URI(var17, var26, var19, 0, (String)null, (String)null, (String)null);
      } catch (URISyntaxException var29) {
         var29.printStackTrace();
         var28 = (URI)var27;
      }

      return var28;
   }

   private URI getUri() throws URISyntaxException {
      boolean var1 = this.mSslSecurityView.isChecked();
      boolean var2 = this.mTrustCertificatesView.isChecked();
      String var3;
      if(var1) {
         if(var2) {
            var3 = "eas+ssl+trustallcerts";
         } else {
            var3 = "eas+ssl+";
         }
      } else {
         var3 = "eas";
      }

      String var4 = this.mUsernameView.getText().toString().trim();
      if(var4.startsWith("\\")) {
         var4 = var4.substring(1);
      }

      this.mCacheLoginCredential = var4;
      StringBuilder var5 = (new StringBuilder()).append(var4).append(":");
      String var6 = this.mPasswordView.getText().toString().trim();
      String var7 = var5.append(var6).toString();
      String var8 = this.mServerView.getText().toString().trim();
      Object var9 = null;
      return new URI(var3, var7, var8, 0, (String)null, (String)null, (String)var9);
   }

   private void onNext() {
      try {
         URI var1 = this.getUri();
         EmailContent.Account var2 = this.mAccount;
         String var3 = var1.toString();
         var2.setStoreUri(this, var3);
         EmailContent.Account var4 = this.mAccount;
         String var5 = var1.toString();
         var4.setSenderUri(this, var5);
         long var6 = this.mAccount.mId;
         String var8 = var1.getHost();
         String var9 = this.mCacheLoginCredential;
         String var10 = Utility.findDuplicateAccount(this, var6, var8, var9);
         this.mDuplicateAccountName = var10;
         if(this.mDuplicateAccountName != null) {
            this.showDialog(1);
            return;
         }
      } catch (URISyntaxException var13) {
         throw new Error(var13);
      }

      EmailContent.Account var12 = this.mAccount;
      AccountSetupCheckSettings.actionValidateSettings(this, var12, (boolean)1, (boolean)0);
   }

   private boolean usernameFieldValid(EditText var1) {
      boolean var2;
      if(Utility.requiredFieldValid((TextView)var1) && !var1.getText().toString().equals("\\")) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private boolean validateFields() {
      EditText var1 = this.mUsernameView;
      byte var2;
      if(this.usernameFieldValid(var1) && Utility.requiredFieldValid((TextView)this.mPasswordView) && Utility.requiredFieldValid((TextView)this.mServerView)) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      if(var2 != 0) {
         try {
            URI var3 = this.getUri();
         } catch (URISyntaxException var6) {
            var2 = 0;
         } catch (NullPointerException var7) {
            var2 = 0;
         }
      }

      this.mNextTwSoftkeyItem.setCompoundDrawables((boolean)var2);
      this.mNextTwSoftkeyItem.setEnabled((boolean)var2);
      this.mNextTwSoftkeyItem.setFocusable((boolean)var2);
      return (boolean)var2;
   }

   void loadFields(EmailContent.Account var1) {
      EmailContent.HostAuth var2 = var1.mHostAuthRecv;
      String var3 = var2.mLogin;
      if(var3 != null) {
         if(var3.indexOf(92) < 0) {
            var3 = "\\" + var3;
         }

         this.mUsernameView.setText(var3);
      }

      if(var2.mPassword != null) {
         EditText var4 = this.mPasswordView;
         String var5 = var2.mPassword;
         var4.setText(var5);
         EditText var6 = this.mPasswordView;
         int var7 = var2.mPassword.length();
         var6.setSelection(var7);
      }

      String var8 = var2.mProtocol;
      if(var8 != null && var8.startsWith("eas")) {
         if(var2.mAddress != null) {
            EditText var12 = this.mServerView;
            String var13 = var2.mAddress;
            var12.setText(var13);
         }

         byte var14;
         if((var2.mFlags & 1) != 0) {
            var14 = 1;
         } else {
            var14 = 0;
         }

         byte var15;
         if((var2.mFlags & 8) != 0) {
            var15 = 1;
         } else {
            var15 = 0;
         }

         this.mSslSecurityView.setChecked((boolean)var14);
         this.mTrustCertificatesView.setChecked((boolean)var15);
         LinearLayout var16 = this.mTrustCertificatesViewLayout;
         byte var17;
         if(var14 != 0) {
            var17 = 0;
         } else {
            var17 = 8;
         }

         var16.setVisibility(var17);
      } else {
         StringBuilder var9 = (new StringBuilder()).append("Unknown account type: ");
         String var10 = var1.getStoreUri(this);
         String var11 = var9.append(var10).toString();
         throw new Error(var11);
      }
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 == 1) {
         String var4 = this.getIntent().getAction();
         if("android.intent.action.EDIT".equals(var4)) {
            this.doActivityResultValidateExistingAccount(var2, var3);
         } else {
            this.doActivityResultValidateNewAccount(var2, var3);
         }
      } else if(var1 == 2) {
         this.doActivityResultAutoDiscoverNewAccount(var2, var3);
      } else if(var1 == 3) {
         this.performAutoDiscoverAgain(var3);
      }
   }

   public void onCheckedChanged(CompoundButton var1, boolean var2) {
      if(var1.getId() == 2131361873) {
         LinearLayout var3 = this.mTrustCertificatesViewLayout;
         byte var4;
         if(var2) {
            var4 = 0;
         } else {
            var4 = 8;
         }

         var3.setVisibility(var4);
      }
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131361862:
         StringBuilder var2 = (new StringBuilder()).append("mEasMode is ");
         boolean var3 = this.mEASFlowMode;
         StringBuilder var4 = var2.append(var3).append(" and isEnable? ");
         boolean var5 = this.mUsernameView.isEnabled();
         Email.log(var4.append(var5).toString());
         if(!this.mEASFlowMode && this.mUsernameView.isEnabled() == 1) {
            SharedPreferences var6 = this.getSharedPreferences("AndroidMail.Main", 0);
            String var7 = var6.getString("activationLicense", (String)null);
            boolean var8 = var6.getBoolean("isCheckIMEI", (boolean)0);
            if(AccountSetupBasics.mShowAlertImei && var8) {
               this.showDialog(2);
               return;
            }

            this.onNext();
            return;
         }

         this.onNext();
         return;
      case 2131361872:
         if(this.mSslSecurityView.isChecked()) {
            this.mSslSecurityView.setChecked((boolean)0);
            return;
         }

         this.mSslSecurityView.setChecked((boolean)1);
         return;
      case 2131361874:
         if(this.mTrustCertificatesView.isChecked()) {
            this.mTrustCertificatesView.setChecked((boolean)0);
            return;
         }

         this.mTrustCertificatesView.setChecked((boolean)1);
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903052);
      EditText var2 = (EditText)this.findViewById(2131361870);
      this.mUsernameView = var2;
      EditText var3 = (EditText)this.findViewById(2131361849);
      this.mPasswordView = var3;
      EditText var4 = (EditText)this.findViewById(2131361871);
      this.mServerView = var4;
      CheckBox var5 = (CheckBox)this.findViewById(2131361873);
      this.mSslSecurityView = var5;
      this.mSslSecurityView.setOnCheckedChangeListener(this);
      this.mSslSecurityView.setOnClickListener(this);
      CheckBox var6 = (CheckBox)this.findViewById(2131361875);
      this.mTrustCertificatesView = var6;
      this.mTrustCertificatesView.setOnClickListener(this);
      EmailTwSoftkeyItem var7 = (EmailTwSoftkeyItem)this.findViewById(2131361862);
      this.mNextTwSoftkeyItem = var7;
      this.mNextTwSoftkeyItem.setOnClickListener(this);
      LinearLayout var8 = (LinearLayout)this.findViewById(2131361872);
      this.mSslSecurityViewLayout = var8;
      this.mSslSecurityViewLayout.setOnClickListener(this);
      LinearLayout var9 = (LinearLayout)this.findViewById(2131361874);
      this.mTrustCertificatesViewLayout = var9;
      this.mTrustCertificatesViewLayout.setOnClickListener(this);
      AccountSetupExchange.1 var10 = new AccountSetupExchange.1();
      if(this.getIntent().getBooleanExtra("accountEdit", (boolean)0)) {
         this.mPasswordView.addTextChangedListener(var10);
         this.mServerView.addTextChangedListener(var10);
         this.mUsernameView.setEnabled((boolean)0);
         this.mUsernameView.setFocusable((boolean)0);
      } else {
         this.mUsernameView.addTextChangedListener(var10);
         this.mPasswordView.addTextChangedListener(var10);
         this.mServerView.addTextChangedListener(var10);
      }

      Intent var11 = this.getIntent();
      EmailContent.Account var12 = (EmailContent.Account)var11.getParcelableExtra("account");
      this.mAccount = var12;
      boolean var13 = var11.getBooleanExtra("makeDefault", (boolean)0);
      this.mMakeDefault = var13;
      boolean var14 = var11.getBooleanExtra("easFlow", (boolean)0);
      this.mEASFlowMode = var14;
      if(var1 != null && var1.containsKey("account")) {
         EmailContent.Account var15 = (EmailContent.Account)var1.getParcelable("account");
         this.mAccount = var15;
      }

      EmailContent.Account var16 = this.mAccount;
      this.loadFields(var16);
      boolean var17 = this.validateFields();
      this.performAutoDiscover(var11);
      SharedPreferences var18 = this.getSharedPreferences("AndroidMail.Main", 0);
      if(!var18.getBoolean("isSet", (boolean)0)) {
         Editor var19 = var18.edit();
         Editor var20 = var19.putBoolean("isSet", (boolean)1);
         Editor var21 = var19.putBoolean("isCheckIMEI", (boolean)1);
         boolean var22 = var19.commit();
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
         AccountSetupExchange.2 var8 = new AccountSetupExchange.2();
         var2 = var7.setPositiveButton(2131166260, var8).create();
         break;
      case 2:
         View var9 = LayoutInflater.from(this).inflate(2130903085, (ViewGroup)null);
         CheckBox var10 = (CheckBox)var9.findViewById(2131362056);
         var10.setOnClickListener(this);
         SharedPreferences var11 = this.getSharedPreferences("AndroidMail.Main", 0);
         Builder var12 = (new Builder(this)).setView(var9);
         AccountSetupExchange.5 var13 = new AccountSetupExchange.5(var11, var10);
         Builder var14 = var12.setPositiveButton(2131166260, var13);
         AccountSetupExchange.4 var15 = new AccountSetupExchange.4();
         Builder var16 = var14.setNegativeButton(2131166261, var15);
         AccountSetupExchange.3 var17 = new AccountSetupExchange.3();
         var2 = var16.setOnCancelListener(var17).create();
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
      if(AccountSetupCustomer.getInstance().getEmailType() != 1 || !SevenSyncProvider.checkSevenApkVer(this)) {
         String var2 = var1.getString("com.android.email.DUPLEACCOUNTNAME");
         this.mDuplicateAccountName = var2;
      }
   }

   protected void onResume() {
      super.onResume();
      this.findViewById(2131361862).setClickable((boolean)1);
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      EmailContent.Account var2 = this.mAccount;
      var1.putParcelable("account", var2);
      String var3 = this.mDuplicateAccountName;
      var1.putString("com.android.email.DUPLEACCOUNTNAME", var3);
   }

   public boolean onSearchRequested() {
      return false;
   }

   public void performAutoDiscover(Intent var1) {
      String var2 = this.mAccount.mHostAuthRecv.mLogin;
      String var3 = this.mAccount.mHostAuthRecv.mPassword;
      Boolean var4 = Boolean.valueOf((boolean)0);

      label29: {
         Boolean var6;
         try {
            String var5 = var1.getAction();
            var6 = Boolean.valueOf("android.intent.action.EDIT".equals(var5));
         } catch (NullPointerException var16) {
            StringBuilder var12 = (new StringBuilder()).append("Intent.ACTION_EDIT.equals(intent.getAction()) Line");
            String var13 = var16.toString();
            String var14 = var12.append(var13).toString();
            int var15 = Log.v("Email", var14);
            break label29;
         }

         var4 = var6;
      }

      if(var2 != null) {
         if(var3 != null) {
            if(!var4.booleanValue()) {
               if(!var1.getBooleanExtra("disableAutoDiscover", (boolean)0)) {
                  EmailContent.Account var7 = this.mAccount;
                  String var8 = this.mAccount.mEmailAddress;
                  byte var10 = 0;
                  AccountSetupCheckSettings.actionAutoDiscover(this, var7, var8, var3, (String)null, (boolean)0, (boolean)var10);
               }
            }
         }
      }
   }

   public void performAutoDiscoverAgain(Intent var1) {
      if(var1 == null) {
         this.finish();
      } else {
         String var2 = var1.getStringExtra("com.android.email.USER");
         String var3 = var1.getStringExtra("com.android.email.PASSWORD");
         String var4 = var1.getStringExtra("com.android.email.DOMAIN");
         String var5 = var1.getStringExtra("com.android.email.EMAIL");
         boolean var6 = var1.getBooleanExtra("com.android.email.TRUSTCERT", (boolean)0);
         if(var4 != null) {
            EditText var7 = this.mUsernameView;
            String var8 = var4 + "\\" + var2;
            var7.setText(var8);
         }

         this.mPasswordView.setText(var3);
         int var9 = var5.indexOf(64);
         EditText var10 = this.mServerView;
         int var11 = var9 + 1;
         String var12 = var5.substring(var11);
         var10.setText(var12);
         this.mTrustCertificatesView.setChecked(var6);
         URI var13 = this.getAutDiscoverUri(var1);
         EmailContent.Account var14 = this.mAccount;
         String var15 = var1.getStringExtra("com.android.email.EMAIL");
         var14.mEmailAddress = var15;
         if(var13 != null) {
            EmailContent.Account var16 = this.mAccount;
            String var17 = var13.toString();
            var16.setStoreUri(this, var17);
            EmailContent.Account var18 = this.mAccount;
            String var19 = var13.toString();
            var18.setSenderUri(this, var19);
         }

         if(var2 != null) {
            if(var3 != null) {
               EmailContent.Account var20 = this.mAccount;
               String var21 = this.mAccount.mEmailAddress;
               AccountSetupCheckSettings.actionAutoDiscover(this, var20, var21, var3, var4, var6, (boolean)1);
            }
         }
      }
   }

   class 4 implements android.content.DialogInterface.OnClickListener {

      4() {}

      public void onClick(DialogInterface var1, int var2) {
         AccountSetupExchange.this.dismissDialog(2);
      }
   }

   class 5 implements android.content.DialogInterface.OnClickListener {

      // $FF: synthetic field
      final CheckBox val$checkboxImei;
      // $FF: synthetic field
      final SharedPreferences val$mSharedPreferences;


      5(SharedPreferences var2, CheckBox var3) {
         this.val$mSharedPreferences = var2;
         this.val$checkboxImei = var3;
      }

      public void onClick(DialogInterface var1, int var2) {
         AccountSetupExchange.this.dismissDialog(2);
         Editor var3 = this.val$mSharedPreferences.edit();
         boolean var4 = this.val$checkboxImei.isChecked();
         var3.putBoolean("isCheckIMEI", var4);
         boolean var6 = var3.commit();
         AccountSetupExchange.this.onNext();
      }
   }

   class 1 implements TextWatcher {

      1() {}

      public void afterTextChanged(Editable var1) {
         boolean var2 = AccountSetupExchange.this.validateFields();
      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}
   }

   class 2 implements android.content.DialogInterface.OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         AccountSetupExchange.this.dismissDialog(1);
      }
   }

   class 3 implements OnCancelListener {

      3() {}

      public void onCancel(DialogInterface var1) {
         AccountSetupExchange.this.dismissDialog(2);
      }
   }
}
