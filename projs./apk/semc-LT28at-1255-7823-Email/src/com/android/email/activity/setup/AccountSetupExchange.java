package com.android.email.activity.setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.android.email.AccountBackupRestore;
import com.android.email.ExchangeUtils;
import com.android.email.Utility;
import com.android.email.activity.setup.AccountSetupCheckSettings;
import com.android.email.activity.setup.AccountSetupOptions;
import com.android.email.provider.EmailContent;
import com.android.email.service.IEmailService;
import com.android.email.service.IEmailServiceCallback;
import java.net.URI;
import java.net.URISyntaxException;

public class AccountSetupExchange extends Activity implements OnClickListener, OnCheckedChangeListener {

   private static final int DIALOG_DUPLICATE_ACCOUNT = 1;
   static final String EXTRA_ACCOUNT = "account";
   static final String EXTRA_DISABLE_AUTO_DISCOVER = "disableAutoDiscover";
   private static final String EXTRA_EAS_FLOW = "easFlow";
   private static final String EXTRA_EXIT_AFTER_SETUP = "exitAfterSetup";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private EmailContent.Account mAccount;
   private String mCacheLoginCredential;
   private EditText mDomainView;
   private String mDuplicateAccountName;
   private boolean mExitAfterSetup;
   private boolean mMakeDefault;
   private Button mNextButton;
   private EditText mPasswordView;
   private EditText mServerView;
   private CheckBox mSslSecurityView;
   private CheckBox mTrustCertificatesView;
   private EditText mUsernameView;


   public AccountSetupExchange() {}

   public static void actionEditIncomingSettings(Activity var0, EmailContent.Account var1) {
      Intent var2 = new Intent(var0, AccountSetupExchange.class);
      Intent var3 = var2.setAction("android.intent.action.EDIT");
      var2.putExtra("account", var1);
      var0.startActivity(var2);
   }

   public static void actionEditOutgoingSettings(Activity var0, EmailContent.Account var1) {
      Intent var2 = new Intent(var0, AccountSetupExchange.class);
      Intent var3 = var2.setAction("android.intent.action.EDIT");
      var2.putExtra("account", var1);
      var0.startActivity(var2);
   }

   public static void actionIncomingSettings(Activity var0, EmailContent.Account var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      Intent var6 = new Intent(var0, AccountSetupExchange.class);
      var6.putExtra("account", var1);
      var6.putExtra("makeDefault", var2);
      var6.putExtra("easFlow", var3);
      if(!var4) {
         Intent var10 = var6.putExtra("disableAutoDiscover", (boolean)1);
      }

      var6.putExtra("exitAfterSetup", var5);
      var0.startActivity(var6);
   }

   private void doActivityResultAutoDiscoverNewAccount(int var1, Intent var2) {
      if(var2 != null) {
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
            if(this.mAccount.mHostAuthRecv.mProtocol.equals("eas")) {
               Object var12 = null;

               try {
                  IEmailService var13 = ExchangeUtils.getExchangeEmailService(this, (IEmailServiceCallback)var12);
                  long var14 = this.mAccount.mId;
                  var13.hostChanged(var14);
               } catch (RemoteException var18) {
                  ;
               }
            }
         } else {
            Uri var16 = this.mAccount.save(this);
         }

         AccountBackupRestore.backupAccounts(this);
         this.finish();
      }
   }

   private void doActivityResultValidateNewAccount(int var1, Intent var2) {
      if(var1 == -1) {
         this.doOptions();
      } else if(var1 == 2) {
         this.finish();
      }
   }

   private void doOptions() {
      boolean var1 = this.getIntent().getBooleanExtra("easFlow", (boolean)0);
      EmailContent.Account var2 = this.mAccount;
      boolean var3 = this.mMakeDefault;
      boolean var4 = this.mExitAfterSetup;
      AccountSetupOptions.actionOptions(this, var2, var3, var1, var4);
      this.finish();
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
      AccountSetupCheckSettings.actionValidateSettingsHaveErrorMsg(this, var12, (boolean)1, (boolean)0, (boolean)1);
   }

   private boolean validateFields() {
      byte var1;
      if(Utility.requiredFieldValid((TextView)this.mUsernameView) && Utility.requiredFieldValid((TextView)this.mPasswordView) && Utility.requiredFieldValid((TextView)this.mServerView)) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      if(var1 != 0) {
         try {
            URI var2 = this.getUri();
         } catch (URISyntaxException var6) {
            var1 = 0;
         }
      }

      this.mNextButton.setEnabled((boolean)var1);
      Button var3 = this.mNextButton;
      short var4;
      if(var1 != 0) {
         var4 = 255;
      } else {
         var4 = 128;
      }

      Utility.setCompoundDrawablesAlpha(var3, var4);
      return (boolean)var1;
   }

   URI getUri() throws URISyntaxException {
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

      String var4 = this.mDomainView.getText().toString().trim();
      String var5 = this.mUsernameView.getText().toString().trim();
      if(var5.startsWith("\\")) {
         var5 = var5.substring(1);
      }

      String var6 = var4 + "\\" + var5;
      this.mCacheLoginCredential = var6;
      StringBuilder var7 = (new StringBuilder()).append(var4).append("\\").append(var5).append(":");
      Editable var8 = this.mPasswordView.getText();
      String var9 = var7.append(var8).toString();
      String var10 = this.mServerView.getText().toString().trim();
      Object var11 = null;
      return new URI(var3, var9, var10, 0, (String)null, (String)null, (String)var11);
   }

   void loadFields(EmailContent.Account var1) {
      EmailContent.HostAuth var2 = var1.mHostAuthRecv;
      String var3 = null;
      String var4 = var2.mLogin;
      if(var4 != null && var4.contains("\\")) {
         int var5 = var4.indexOf("\\");
         var3 = var4.substring(0, var5);
         int var6 = var5 + 1;
         var4 = var4.substring(var6);
      }

      if(var3 != null) {
         this.mDomainView.setText(var3);
      }

      if(var4 != null) {
         this.mUsernameView.setText(var4);
      }

      if(var2.mPassword != null) {
         EditText var7 = this.mPasswordView;
         String var8 = var2.mPassword;
         var7.setText(var8);
      }

      String var9 = var2.mProtocol;
      if(var9 != null && var9.startsWith("eas")) {
         if(var2.mAddress != null) {
            EditText var13 = this.mServerView;
            String var14 = var2.mAddress;
            var13.setText(var14);
         }

         byte var15;
         if((var2.mFlags & 1) != 0) {
            var15 = 1;
         } else {
            var15 = 0;
         }

         byte var16;
         if((var2.mFlags & 8) != 0) {
            var16 = 1;
         } else {
            var16 = 0;
         }

         this.mSslSecurityView.setChecked((boolean)var15);
         this.mTrustCertificatesView.setChecked((boolean)var16);
         CheckBox var17 = this.mTrustCertificatesView;
         byte var18;
         if(var15 != 0) {
            var18 = 0;
         } else {
            var18 = 8;
         }

         var17.setVisibility(var18);
      } else {
         StringBuilder var10 = (new StringBuilder()).append("Unknown account type: ");
         String var11 = var1.getStoreUri(this);
         String var12 = var10.append(var11).toString();
         throw new Error(var12);
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
      if(var1.getId() == 2131558426) {
         CheckBox var3 = this.mTrustCertificatesView;
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
      case 2131558417:
         this.onNext();
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903045);
      EditText var2 = (EditText)this.findViewById(2131558423);
      this.mDomainView = var2;
      EditText var3 = (EditText)this.findViewById(2131558424);
      this.mUsernameView = var3;
      EditText var4 = (EditText)this.findViewById(2131558416);
      this.mPasswordView = var4;
      EditText var5 = (EditText)this.findViewById(2131558425);
      this.mServerView = var5;
      CheckBox var6 = (CheckBox)this.findViewById(2131558426);
      this.mSslSecurityView = var6;
      this.mSslSecurityView.setOnCheckedChangeListener(this);
      CheckBox var7 = (CheckBox)this.findViewById(2131558427);
      this.mTrustCertificatesView = var7;
      Button var8 = (Button)this.findViewById(2131558417);
      this.mNextButton = var8;
      this.mNextButton.setOnClickListener(this);
      AccountSetupExchange.1 var9 = new AccountSetupExchange.1();
      this.mUsernameView.addTextChangedListener(var9);
      this.mPasswordView.addTextChangedListener(var9);
      this.mServerView.addTextChangedListener(var9);
      Intent var10 = this.getIntent();
      EmailContent.Account var11 = (EmailContent.Account)var10.getParcelableExtra("account");
      this.mAccount = var11;
      boolean var12 = var10.getBooleanExtra("makeDefault", (boolean)0);
      this.mMakeDefault = var12;
      boolean var13 = var10.getBooleanExtra("exitAfterSetup", (boolean)0);
      this.mExitAfterSetup = var13;
      if(var1 != null && var1.containsKey("account")) {
         EmailContent.Account var14 = (EmailContent.Account)var1.getParcelable("account");
         this.mAccount = var14;
      }

      EmailContent.Account var15 = this.mAccount;
      this.loadFields(var15);
      boolean var16 = this.validateFields();
      String var17 = this.mAccount.mHostAuthRecv.mLogin;
      String var18 = this.mAccount.mHostAuthRecv.mPassword;
      if(var17 != null) {
         if(var18 != null) {
            String var19 = var10.getAction();
            if(!"android.intent.action.EDIT".equals(var19)) {
               if(!var10.getBooleanExtra("disableAutoDiscover", (boolean)0)) {
                  EmailContent.Account var20 = this.mAccount;
                  String var21 = this.mAccount.mEmailAddress;
                  AccountSetupCheckSettings.actionAutoDiscover(this, var20, var21, var18);
               }
            }
         }
      }
   }

   public Dialog onCreateDialog(int var1) {
      AlertDialog var2;
      switch(var1) {
      case 1:
         Builder var3 = (new Builder(this, 2131361797)).setIcon(17301543).setTitle(2131165324);
         Object[] var4 = new Object[1];
         String var5 = this.mDuplicateAccountName;
         var4[0] = var5;
         String var6 = this.getString(2131165325, var4);
         Builder var7 = var3.setMessage(var6);
         AccountSetupExchange.2 var8 = new AccountSetupExchange.2();
         var2 = var7.setPositiveButton(2131165215, var8).create();
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
         String var6 = this.getString(2131165325, var4);
         var3.setMessage(var6);
         return;
      default:
      }
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      EmailContent.Account var2 = this.mAccount;
      var1.putParcelable("account", var2);
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
}
