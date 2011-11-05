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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.android.email.AccountBackupRestore;
import com.android.email.EmailTwSoftkeyItem;
import com.android.email.Utility;
import com.android.email.activity.setup.AccountSetupCheckSettings;
import com.android.email.activity.setup.AccountSetupCustomer;
import com.android.email.activity.setup.AccountSetupOutgoing;
import com.android.email.activity.setup.SpinnerOption;
import com.android.email.provider.EmailContent;
import com.digc.seven.SevenSyncProvider;
import java.net.URI;
import java.net.URISyntaxException;

public class AccountSetupIncoming extends Activity implements OnClickListener {

   private static final int DIALOG_DUPLICATE_ACCOUNT = 1;
   private static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_ACCOUNT_EDIT_IMAP_POP = "accountEditImapPop";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private static final String STATE_DUPLE_ACCOUNTNAME = "com.android.email.DUPLEACCOUNTNAME";
   private static final int[] imapPorts;
   private static final String[] imapSchemes;
   private static final int[] popPorts = new int[]{110, 995, 995, 110, 110};
   private static final String[] popSchemes;
   private boolean bFirstSelected = 1;
   private EmailContent.Account mAccount;
   private int[] mAccountPorts;
   private String[] mAccountSchemes;
   private String mCacheLoginCredential;
   private Spinner mDeletePolicyView;
   private String mDuplicateAccountName;
   private EditText mImapPathPrefixView;
   private boolean mMakeDefault;
   private EmailTwSoftkeyItem mNextTwSoftkeyItem;
   private EditText mPasswordView;
   private EditText mPortView;
   private Spinner mSecurityTypeView;
   private EditText mServerView;
   private EditText mUsernameView;


   static {
      String[] var0 = new String[]{"pop3", "pop3+ssl+", "pop3+ssl+trustallcerts", "pop3+tls+", "pop3+tls+trustallcerts"};
      popSchemes = var0;
      imapPorts = new int[]{143, 993, 993, 143, 143};
      String[] var1 = new String[]{"imap", "imap+ssl+", "imap+ssl+trustallcerts", "imap+tls+", "imap+tls+trustallcerts"};
      imapSchemes = var1;
   }

   public AccountSetupIncoming() {}

   public static void actionEditIncomingSettings(Activity var0, EmailContent.Account var1) {
      Intent var2 = new Intent(var0, AccountSetupIncoming.class);
      Intent var3 = var2.setAction("android.intent.action.EDIT");
      var2.putExtra("account", var1);
      var0.startActivity(var2);
   }

   public static void actionEditIncomingSettings(Activity var0, EmailContent.Account var1, boolean var2) {
      Intent var3 = new Intent(var0, AccountSetupIncoming.class);
      Intent var4 = var3.setAction("android.intent.action.EDIT");
      var3.putExtra("account", var1);
      var3.putExtra("accountEditImapPop", var2);
      var0.startActivity(var3);
   }

   public static void actionIncomingSettings(Activity var0, EmailContent.Account var1, boolean var2) {
      Intent var3 = new Intent(var0, AccountSetupIncoming.class);
      var3.putExtra("account", var1);
      var3.putExtra("makeDefault", var2);
      var0.startActivity(var3);
   }

   private URI getUri() throws URISyntaxException, NullPointerException {
      int var1 = ((Integer)((SpinnerOption)this.mSecurityTypeView.getSelectedItem()).value).intValue();
      String var2 = null;
      if(this.mAccountSchemes[var1].startsWith("imap")) {
         StringBuilder var3 = (new StringBuilder()).append("/");
         String var4 = this.mImapPathPrefixView.getText().toString().trim();
         var2 = var3.append(var4).toString();
      }

      String var5 = this.mUsernameView.getText().toString().trim();
      this.mCacheLoginCredential = var5;
      String var6 = this.mAccountSchemes[var1];
      StringBuilder var7 = (new StringBuilder()).append(var5).append(":");
      String var8 = this.mPasswordView.getText().toString().trim();
      String var9 = var7.append(var8).toString();
      String var10 = this.mServerView.getText().toString().trim();
      int var11 = Integer.parseInt(this.mPortView.getText().toString().trim());
      Object var12 = null;
      return new URI(var6, var9, var10, var11, var2, (String)null, (String)var12);
   }

   private void onNext() {
      try {
         URI var1 = this.getUri();
         EmailContent.Account var2 = this.mAccount;
         String var3 = var1.toString();
         var2.setStoreUri(this, var3);
         long var4 = this.mAccount.mId;
         String var6 = var1.getHost();
         String var7 = this.mCacheLoginCredential;
         String var8 = Utility.findDuplicateAccount(this, var4, var6, var7);
         this.mDuplicateAccountName = var8;
         if(this.mDuplicateAccountName != null) {
            this.showDialog(1);
            return;
         }
      } catch (URISyntaxException var14) {
         throw new Error(var14);
      } catch (NullPointerException var15) {
         throw new Error(var15);
      }

      EmailContent.Account var11 = this.mAccount;
      int var12 = ((Integer)((SpinnerOption)this.mDeletePolicyView.getSelectedItem()).value).intValue();
      var11.setDeletePolicy(var12);
      EmailContent.Account var13 = this.mAccount;
      AccountSetupCheckSettings.actionValidateSettings(this, var13, (boolean)1, (boolean)0);
   }

   private void updatePortFromSecurityType() {
      int var1 = ((Integer)((SpinnerOption)this.mSecurityTypeView.getSelectedItem()).value).intValue();
      EditText var2 = this.mPortView;
      String var3 = Integer.toString(this.mAccountPorts[var1]);
      var2.setText(var3);
      EditText var4 = this.mPortView;
      int var5 = Integer.toString(this.mAccountPorts[var1]).length();
      var4.setSelection(var5);
   }

   private void validateFields() {
      byte var1;
      if(Utility.requiredFieldValid((TextView)this.mUsernameView) && Utility.requiredFieldValid((TextView)this.mPasswordView) && Utility.requiredFieldValid((TextView)this.mServerView) && Utility.requiredFieldValid((TextView)this.mPortView)) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      if(var1 != 0) {
         label28: {
            try {
               int var2 = this.getUri().getPort();
               if(var2 >= 0 && var2 <= '\uffff') {
                  break label28;
               }

               String var3 = this.getString(2131166613);
               Toast.makeText(this, var3, 1).show();
            } catch (URISyntaxException var6) {
               var1 = 0;
               break label28;
            } catch (NullPointerException var7) {
               var1 = 0;
               break label28;
            }

            var1 = 0;
         }
      }

      this.mNextTwSoftkeyItem.setEnabled((boolean)var1);
      this.mNextTwSoftkeyItem.setFocusable((boolean)var1);
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if(var2 == -1) {
         String var4 = this.getIntent().getAction();
         if("android.intent.action.EDIT".equals(var4)) {
            if(this.mAccount.isSaved()) {
               EmailContent.Account var5 = this.mAccount;
               ContentValues var6 = this.mAccount.toContentValues();
               var5.update(this, var6);
               EmailContent.HostAuth var8 = this.mAccount.mHostAuthRecv;
               ContentValues var9 = this.mAccount.mHostAuthRecv.toContentValues();
               var8.update(this, var9);
            } else {
               Uri var11 = this.mAccount.save(this);
            }

            AccountBackupRestore.backupAccounts(this);
            this.finish();
         } else {
            try {
               String var12 = this.mAccount.getSenderUri(this);
               URI var13 = new URI(var12);
               String var14 = var13.getScheme();
               StringBuilder var15 = new StringBuilder();
               String var16 = this.mUsernameView.getText().toString().trim();
               StringBuilder var17 = var15.append(var16).append(":");
               String var18 = this.mPasswordView.getText().toString().trim();
               String var19 = var17.append(var18).toString();
               String var20 = var13.getHost();
               int var21 = var13.getPort();
               URI var22 = new URI(var14, var19, var20, var21, (String)null, (String)null, (String)null);
               EmailContent.Account var23 = this.mAccount;
               String var24 = var22.toString();
               var23.setSenderUri(this, var24);
            } catch (URISyntaxException var28) {
               ;
            }

            EmailContent.Account var25 = this.mAccount;
            boolean var26 = this.mMakeDefault;
            AccountSetupOutgoing.actionOutgoingSettings(this, var25, var26);
            this.finish();
         }
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

   public void onCreate(Bundle param1) {
      // $FF: Couldn't be decompiled
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
         AccountSetupIncoming.3 var8 = new AccountSetupIncoming.3();
         var2 = var7.setPositiveButton(2131166260, var8).create();
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      byte var4;
      if(var1 == 19) {
         if(this.mNextTwSoftkeyItem.isEnabled() == 1) {
            boolean var3 = this.mNextTwSoftkeyItem.requestFocus();
         } else if(this.mDeletePolicyView.getVisibility() == 0) {
            boolean var5 = this.mDeletePolicyView.requestFocus();
         } else {
            if(this.mImapPathPrefixView.getVisibility() != 0) {
               StringBuilder var7 = (new StringBuilder()).append("Unknown account type: ");
               String var8 = this.mAccount.getStoreUri(this);
               String var9 = var7.append(var8).toString();
               throw new Error(var9);
            }

            boolean var6 = this.mImapPathPrefixView.requestFocus();
         }

         var4 = 1;
      } else if(var1 == 20) {
         if(this.getIntent().getBooleanExtra("accountEditImapPop", (boolean)0)) {
            boolean var10 = this.mPasswordView.requestFocus();
         } else {
            boolean var11 = this.mUsernameView.requestFocus();
         }

         var4 = 1;
      } else {
         var4 = super.onKeyDown(var1, var2);
      }

      return (boolean)var4;
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

   class 3 implements android.content.DialogInterface.OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         AccountSetupIncoming.this.dismissDialog(1);
      }
   }

   class 2 implements TextWatcher {

      2() {}

      public void afterTextChanged(Editable var1) {
         AccountSetupIncoming.this.validateFields();
      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}
   }

   class 1 implements OnItemSelectedListener {

      1() {}

      public void onItemSelected(AdapterView var1, View var2, int var3, long var4) {
         if(AccountSetupIncoming.this.bFirstSelected == 1) {
            boolean var6 = (boolean)(AccountSetupIncoming.this.bFirstSelected = (boolean)0);
         } else {
            AccountSetupIncoming.this.updatePortFromSecurityType();
         }
      }

      public void onNothingSelected(AdapterView<?> var1) {}
   }
}
