package com.android.email.activity.setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.android.email.AccountBackupRestore;
import com.android.email.Email;
import com.android.email.EmailAddressValidator;
import com.android.email.ExchangeUtils;
import com.android.email.Utility;
import com.android.email.activity.setup.AccountSetupBasics;
import com.android.email.activity.setup.AccountSetupCheckSettings;
import com.android.email.activity.setup.AccountSetupCustomer;
import com.android.email.activity.setup.AccountSetupOptions;
import com.android.email.provider.EmailContent;
import com.android.email.service.IEmailService;
import com.android.email.service.IEmailServiceCallback;
import com.android.exchange.SyncScheduleData;
import com.digc.seven.SevenSyncProvider;
import java.net.URI;
import java.net.URISyntaxException;

public class AccountSetupBasicExchange extends Activity implements OnClickListener, OnCheckedChangeListener, TextWatcher {

   private static final int DEFAULT_ACCOUNT_CHECK_INTERVAL = 0;
   private static final int DIALOG_DUPLICATE_ACCOUNT = 1;
   private static final int DIALOG_EXCHANGE_ACCOUNT_WARNNING = 2;
   static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_ACCOUNT_EDIT = "accountEdit";
   static final String EXTRA_DISABLE_AUTO_DISCOVER = "disableAutoDiscover";
   private static final String EXTRA_EAS_FLOW = "easFlow";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private static final String STATE_DUPLE_ACCOUNTNAME = "com.android.email.DUPLEACCOUNTNAME";
   static final String TAG = "AccountSetupBasicExchange";
   private EmailContent.Account mAccount;
   private String mCacheLoginCredential;
   private String mDuplicateAccountName;
   private boolean mEASFlowMode;
   private EmailAddressValidator mEmailValidator;
   private boolean mMakeDefault;
   private Button mNextTwSoftkeyItem;
   private EditText mPasswordView;
   private EditText mServerView;
   private CheckBox mSslSecurityView;
   private LinearLayout mSslSecurityViewLayout;
   private CheckBox mTrustCertificatesView;
   private LinearLayout mTrustCertificatesViewLayout;
   private EditText mUserIdView;
   private EditText mUsernameView;
   private Toast toastForCredential;


   public AccountSetupBasicExchange() {
      EmailAddressValidator var1 = new EmailAddressValidator();
      this.mEmailValidator = var1;
   }

   public static void actionEditIncomingSettings(Activity var0, EmailContent.Account var1, boolean var2) {
      Intent var3 = new Intent(var0, AccountSetupBasicExchange.class);
      Intent var4 = var3.setAction("android.intent.action.EDIT");
      var3.putExtra("account", var1);
      var3.putExtra("accountEdit", var2);
      var0.startActivity(var3);
   }

   public static void actionEditOutgoingSettings(Activity var0, EmailContent.Account var1) {
      Intent var2 = new Intent(var0, AccountSetupBasicExchange.class);
      Intent var3 = var2.setAction("android.intent.action.EDIT");
      var2.putExtra("account", var1);
      var0.startActivity(var2);
   }

   public static void actionIncomingSettings(Activity var0, EmailContent.Account var1, boolean var2, boolean var3, boolean var4) {
      Intent var5 = new Intent(var0, AccountSetupBasicExchange.class);
      var5.putExtra("account", var1);
      var5.putExtra("makeDefault", var2);
      var5.putExtra("easFlow", var3);
      if(!var4) {
         Intent var9 = var5.putExtra("disableAutoDiscover", (boolean)1);
      }

      var0.startActivity(var5);
   }

   public static Intent actionSetupExchangeIntent(Context var0) {
      Intent var1 = new Intent(var0, AccountSetupBasicExchange.class);
      Intent var2 = var1.putExtra("easFlow", (boolean)1);
      return var1;
   }

   private void doActivityResultAutoDiscoverNewAccount(int var1, Intent var2) {
      if(var1 == 1) {
         this.finish();
      } else if(var2 != null) {
         Parcelable var3 = var2.getParcelableExtra("HostAuth");
         if(var3 != null) {
            EmailContent.HostAuth var4 = (EmailContent.HostAuth)var3;
            this.mAccount.mHostAuthSend = var4;
            this.mAccount.mHostAuthRecv = var4;
            EmailContent.Account var5 = this.mAccount;
            this.loadFields(var5);
            if(this.validateFields()) {
               this.onNext();
            }
         }
      }
   }

   private void doActivityResultValidateExistingAccount(int var1, Intent var2) {
      int var3 = Log.v("AccountSetupBasicExchange", "account_setup_exchange_username_label");
      if(var1 == -1) {
         if(this.mAccount.isSaved()) {
            int var4 = Log.v("AccountSetupBasicExchange", "account is saved!");
            EmailContent.Account var5 = this.mAccount;
            ContentValues var6 = this.mAccount.toContentValues();
            var5.update(this, var6);
            EmailContent.HostAuth var8 = this.mAccount.mHostAuthRecv;
            ContentValues var9 = this.mAccount.mHostAuthRecv.toContentValues();
            var8.update(this, var9);
            EmailContent.HostAuth var11 = this.mAccount.mHostAuthSend;
            ContentValues var12 = this.mAccount.mHostAuthSend.toContentValues();
            var11.update(this, var12);
            String var14 = this.mAccount.mHostAuthRecv.mProtocol;
            if("eas".equals(var14)) {
               Object var15 = null;

               try {
                  IEmailService var16 = ExchangeUtils.getExchangeEmailService(this, (IEmailServiceCallback)var15);
                  long var17 = this.mAccount.mId;
                  var16.hostChanged(var17);
               } catch (RemoteException var22) {
                  ;
               }
            }
         } else {
            int var19 = Log.v("AccountSetupBasicExchange", "account save");
            Uri var20 = this.mAccount.save(this);
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

   private void onExchange(boolean var1) {
      this.mAccount.setDeletePolicy(2);
      this.mAccount.setSyncInterval(-1);
      this.mAccount.setSyncLookback(1);
      String var2 = SystemProperties.get("ro.csc.sales_code");
      SyncScheduleData var3;
      if(!"H3G".equals(var2) && !"3IE".equals(var2)) {
         if("NZC".equals(var2)) {
            byte var5 = -1;
            var3 = new SyncScheduleData(480, 1020, 62, -1, var5, 1);
         } else {
            byte var6 = -1;
            var3 = new SyncScheduleData(480, 1020, 62, -1, var6, 0);
         }
      } else {
         var3 = new SyncScheduleData(480, 1020, 62, 15, 15, 0);
      }

      this.mAccount.setSyncScheduleData(var3);
      boolean var4 = this.mAccount.setCalendarSyncLookback(4);
   }

   private void onNext() {
      try {
         EmailContent.Account var1 = new EmailContent.Account();
         this.mAccount = var1;
         EmailContent.Account var2 = this.mAccount;
         String var3 = this.mUserIdView.getText().toString();
         var2.setEmailAddress(var3);
         this.mAccount.setSyncInterval(0);
         URI var4 = this.getUri();
         EmailContent.Account var5 = this.mAccount;
         String var6 = var4.toString();
         var5.setStoreUri(this, var6);
         EmailContent.Account var7 = this.mAccount;
         String var8 = var4.toString();
         var7.setSenderUri(this, var8);
         boolean var9 = this.mEASFlowMode;
         this.onExchange(var9);
         long var10 = this.mAccount.mId;
         String var12 = var4.getHost();
         String var13 = this.mCacheLoginCredential;
         String var14 = Utility.findDuplicateAccount(this, var10, var12, var13);
         this.mDuplicateAccountName = var14;
         if(this.mDuplicateAccountName != null) {
            this.showDialog(1);
            return;
         }
      } catch (URISyntaxException var17) {
         throw new Error(var17);
      }

      EmailContent.Account var16 = this.mAccount;
      AccountSetupCheckSettings.actionValidateSettings(this, var16, (boolean)1, (boolean)0);
   }

   private void showsCredentialToast() {
      boolean var3;
      label20: {
         if(Utility.requiredFieldValid((TextView)this.mPasswordView)) {
            EmailAddressValidator var1 = this.mEmailValidator;
            Editable var2 = this.mUserIdView.getText();
            if(var1.isValid(var2)) {
               var3 = true;
               break label20;
            }
         }

         var3 = false;
      }

      if(var3) {
         if(this.toastForCredential == null) {
            String var4 = this.getString(2131167002);
            Toast var5 = Toast.makeText(this, var4, 0);
            this.toastForCredential = var5;
         }

         this.toastForCredential.cancel();
         this.toastForCredential.show();
      }
   }

   private boolean usernameFieldValid(EditText var1) {
      int var2 = Log.v("AccountSetupBasicExchange", "usernameFieldValid");
      boolean var3;
      if(Utility.requiredFieldValid((TextView)var1) && !var1.getText().toString().equals("\\")) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   private boolean validateFields() {
      byte var4;
      label29: {
         EditText var1 = this.mUsernameView;
         if(this.usernameFieldValid(var1) && Utility.requiredFieldValid((TextView)this.mPasswordView) && Utility.requiredFieldValid((TextView)this.mServerView)) {
            EmailAddressValidator var2 = this.mEmailValidator;
            Editable var3 = this.mUserIdView.getText();
            if(var2.isValid(var3)) {
               var4 = 1;
               break label29;
            }
         }

         var4 = 0;
      }

      if(var4 != 0) {
         try {
            URI var5 = this.getUri();
         } catch (URISyntaxException var9) {
            var4 = 0;
         }
      }

      Drawable var6 = this.getResources().getDrawable(2130837923);
      Drawable var7 = this.getResources().getDrawable(2130837924);
      if(var4 != 0) {
         this.mNextTwSoftkeyItem.setCompoundDrawablesWithIntrinsicBounds((Drawable)null, (Drawable)null, var6, (Drawable)null);
      } else {
         this.mNextTwSoftkeyItem.setCompoundDrawablesWithIntrinsicBounds((Drawable)null, (Drawable)null, var7, (Drawable)null);
      }

      this.mNextTwSoftkeyItem.setEnabled((boolean)var4);
      return (boolean)var4;
   }

   public void afterTextChanged(Editable var1) {
      boolean var2 = this.validateFields();
   }

   public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   public void divideString(String var1) {
      if(var1.indexOf("@") != 0) {
         String[] var2 = var1.split("@");
         StringBuilder var3 = (new StringBuilder()).append("\\");
         String var4 = var2[0].trim();
         String var5 = var3.append(var4).toString();
         this.mUsernameView.setText(var5);
         if(var2.length >= 2) {
            String var6 = var2[1].trim();
            this.mServerView.setText(var6);
         }
      }
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
      }

      String var6 = var2.mProtocol;
      if(var6 != null && var6.startsWith("eas")) {
         if(var2.mAddress != null) {
            EditText var10 = this.mServerView;
            String var11 = var2.mAddress;
            var10.setText(var11);
         }

         byte var12;
         if((var2.mFlags & 1) != 0) {
            var12 = 1;
         } else {
            var12 = 0;
         }

         byte var13;
         if((var2.mFlags & 8) != 0) {
            var13 = 1;
         } else {
            var13 = 0;
         }

         this.mSslSecurityView.setChecked((boolean)var12);
         this.mTrustCertificatesView.setChecked((boolean)var13);
         CheckBox var14 = this.mTrustCertificatesView;
         byte var15;
         if(var12 != 0) {
            var15 = 0;
         } else {
            var15 = 8;
         }

         var14.setVisibility(var15);
      } else {
         StringBuilder var7 = (new StringBuilder()).append("Unknown account type: ");
         String var8 = var1.getStoreUri(this);
         String var9 = var7.append(var8).toString();
         throw new Error(var9);
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
      }
   }

   public void onCheckedChanged(CompoundButton var1, boolean var2) {
      if(var1.getId() == 2131361838) {
         CheckBox var3 = this.mTrustCertificatesView;
         byte var4;
         if(var2) {
            var4 = 0;
         } else {
            var4 = 8;
         }

         var3.setVisibility(var4);
         TextView var5 = (TextView)this.findViewById(2131361842);
         byte var6;
         if(var2) {
            var6 = 0;
         } else {
            var6 = 8;
         }

         var5.setVisibility(var6);
         TextView var7 = (TextView)this.findViewById(2131361840);
         byte var8;
         if(var2) {
            var8 = 0;
         } else {
            var8 = 8;
         }

         var7.setVisibility(var8);
         TextView var10 = (TextView)this.findViewById(2131361843);
         byte var9;
         if(var2) {
            var9 = 0;
         } else {
            var9 = 8;
         }

         var10.setVisibility(var9);
      }
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131361837:
         if(this.mSslSecurityView.isChecked()) {
            this.mSslSecurityView.setChecked((boolean)0);
            return;
         }

         this.mSslSecurityView.setChecked((boolean)1);
         return;
      case 2131361839:
         if(this.mTrustCertificatesView.isChecked()) {
            this.mTrustCertificatesView.setChecked((boolean)0);
            return;
         }

         this.mTrustCertificatesView.setChecked((boolean)1);
         return;
      case 2131361845:
         this.showsCredentialToast();
         StringBuilder var2 = (new StringBuilder()).append("mEasMode is ");
         boolean var3 = this.mEASFlowMode;
         StringBuilder var4 = var2.append(var3).append(" and isEnable? ");
         boolean var5 = this.mUsernameView.isEnabled();
         Email.log(var4.append(var5).toString());
         SharedPreferences var6 = this.getSharedPreferences("AndroidMail.Main", 0);
         if(!this.mEASFlowMode && this.mUsernameView.isEnabled() == 1) {
            boolean var7 = var6.getBoolean("isCheckIMEI", (boolean)0);
            if(AccountSetupBasics.mShowAlertImei && var7) {
               this.showDialog(2);
               return;
            }

            this.onNext();
            return;
         }

         this.onNext();
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903047);
      int var2 = Log.v("AccountSetupBasicExchange", "onCreate");
      EditText var3 = (EditText)this.findViewById(2131361835);
      this.mUsernameView = var3;
      EditText var4 = (EditText)this.findViewById(2131361833);
      this.mUserIdView = var4;
      EditText var5 = (EditText)this.findViewById(2131361834);
      this.mPasswordView = var5;
      EditText var6 = (EditText)this.findViewById(2131361836);
      this.mServerView = var6;
      CheckBox var7 = (CheckBox)this.findViewById(2131361838);
      this.mSslSecurityView = var7;
      this.mSslSecurityView.setOnCheckedChangeListener(this);
      this.mSslSecurityView.setOnClickListener(this);
      CheckBox var8 = (CheckBox)this.findViewById(2131361841);
      this.mTrustCertificatesView = var8;
      this.mTrustCertificatesView.setOnClickListener(this);
      Button var9 = (Button)this.findViewById(2131361845);
      this.mNextTwSoftkeyItem = var9;
      this.mNextTwSoftkeyItem.setOnClickListener(this);
      LinearLayout var10 = (LinearLayout)this.findViewById(2131361837);
      this.mSslSecurityViewLayout = var10;
      this.mSslSecurityViewLayout.setOnClickListener(this);
      LinearLayout var11 = (LinearLayout)this.findViewById(2131361839);
      this.mTrustCertificatesViewLayout = var11;
      this.mTrustCertificatesViewLayout.setOnClickListener(this);
      AccountSetupBasicExchange.1 var12 = new AccountSetupBasicExchange.1();
      this.mUserIdView.addTextChangedListener(this);
      this.mPasswordView.addTextChangedListener(this);
      this.mServerView.addTextChangedListener(var12);
      boolean var13 = this.validateFields();
      this.mSslSecurityView.setChecked((boolean)1);
   }

   public Dialog onCreateDialog(int var1) {
      String var2 = "onCreateDialog " + var1;
      int var3 = Log.v("AccountSetupBasicExchange", var2);
      AlertDialog var4;
      switch(var1) {
      case 1:
         Builder var5 = (new Builder(this)).setIcon(17301543).setTitle(2131166358);
         Object[] var6 = new Object[1];
         String var7 = this.mDuplicateAccountName;
         var6[0] = var7;
         String var8 = this.getString(2131166359, var6);
         Builder var9 = var5.setMessage(var8);
         AccountSetupBasicExchange.2 var10 = new AccountSetupBasicExchange.2();
         var4 = var9.setPositiveButton(2131166262, var10).create();
         break;
      case 2:
         View var11 = LayoutInflater.from(this).inflate(2130903085, (ViewGroup)null);
         CheckBox var12 = (CheckBox)var11.findViewById(2131362061);
         var12.setOnClickListener(this);
         SharedPreferences var13 = this.getSharedPreferences("AndroidMail.Main", 0);
         Builder var14 = (new Builder(this)).setView(var11);
         AccountSetupBasicExchange.5 var15 = new AccountSetupBasicExchange.5(var13, var12);
         Builder var16 = var14.setPositiveButton(2131166262, var15);
         AccountSetupBasicExchange.4 var17 = new AccountSetupBasicExchange.4();
         Builder var18 = var16.setNegativeButton(2131166263, var17);
         AccountSetupBasicExchange.3 var19 = new AccountSetupBasicExchange.3();
         var4 = var18.setOnCancelListener(var19).create();
         break;
      default:
         var4 = null;
      }

      return var4;
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
         String var6 = this.getString(2131166359, var4);
         var3.setMessage(var6);
         return;
      default:
      }
   }

   protected void onRestoreInstanceState(Bundle var1) {
      int var2 = Log.v("AccountSetupBasicExchange", "onRestoreInstanceState");
      super.onRestoreInstanceState(var1);
      if(AccountSetupCustomer.getInstance().getEmailType() != 1 || !SevenSyncProvider.checkSevenApkVer(this)) {
         String var3 = var1.getString("com.android.email.DUPLEACCOUNTNAME");
         this.mDuplicateAccountName = var3;
      }
   }

   protected void onResume() {
      super.onResume();
      int var1 = Log.v("AccountSetupBasicExchange", "onResume");
      this.findViewById(2131361845).setClickable((boolean)1);
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      int var2 = Log.v("AccountSetupBasicExchange", "onSaveInstanceState");
      EmailContent.Account var3 = this.mAccount;
      var1.putParcelable("account", var3);
      String var4 = this.mDuplicateAccountName;
      var1.putString("com.android.email.DUPLEACCOUNTNAME", var4);
   }

   public boolean onSearchRequested() {
      return false;
   }

   public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
      String var5 = this.mUserIdView.getText().toString();
      this.divideString(var5);
   }

   class 3 implements OnCancelListener {

      3() {}

      public void onCancel(DialogInterface var1) {
         AccountSetupBasicExchange.this.dismissDialog(2);
      }
   }

   class 2 implements android.content.DialogInterface.OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         AccountSetupBasicExchange.this.dismissDialog(1);
      }
   }

   class 1 implements TextWatcher {

      1() {}

      public void afterTextChanged(Editable var1) {
         boolean var2 = AccountSetupBasicExchange.this.validateFields();
      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}
   }

   class 4 implements android.content.DialogInterface.OnClickListener {

      4() {}

      public void onClick(DialogInterface var1, int var2) {
         AccountSetupBasicExchange.this.dismissDialog(2);
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
         AccountSetupBasicExchange.this.dismissDialog(2);
         Editor var3 = this.val$mSharedPreferences.edit();
         boolean var4 = this.val$checkboxImei.isChecked();
         var3.putBoolean("isCheckIMEI", var4);
         boolean var6 = var3.commit();
         AccountSetupBasicExchange.this.onNext();
      }
   }
}
