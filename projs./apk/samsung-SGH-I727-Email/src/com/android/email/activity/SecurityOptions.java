package com.android.email.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.email.SecurityPolicy;
import com.android.email.activity.AccountManager;
import com.android.email.provider.EmailContent;

public class SecurityOptions extends Activity implements OnClickListener, OnKeyListener {

   public static final String ENCRYPTED = "encrypted";
   private static final String EXTRA_ACCOUNT_ID = "account_id";
   public static final String SIGNED = "signed";
   private static final String STATE_ENCRYPT_OPTION = "com.android.email.securityoption.encrypt";
   private static final String STATE_SIGN_OPTION = "com.android.email.securityoption.sign";
   private CheckBox encryptCheckBox;
   private RelativeLayout encryptLayout;
   private TextView encryptTextView;
   private EmailContent.Account mAccount;
   private EmailContent.Message mDraft;
   private CheckBox signCheckBox;
   private RelativeLayout signLayout;
   private TextView signTextView;


   public SecurityOptions() {
      EmailContent.Message var1 = new EmailContent.Message();
      this.mDraft = var1;
   }

   private void configureSmimeOptions() {
      SecurityPolicy var1 = SecurityPolicy.getInstance(this);
      Long var2 = Long.valueOf(this.mAccount.mId);
      SecurityPolicy.PolicySet var3 = var1.getAccountPolicy(var2);
      if(var3.mRequireEncryptedSMIMEMessages) {
         this.encryptLayout.setEnabled((boolean)0);
         this.encryptCheckBox.setEnabled((boolean)0);
         this.encryptTextView.setEnabled((boolean)0);
         this.mDraft.mEncrypted = (boolean)1;
      } else {
         this.encryptLayout.setEnabled((boolean)1);
         this.encryptCheckBox.setEnabled((boolean)1);
         this.encryptTextView.setEnabled((boolean)1);
      }

      if(var3.mRequireSignedSMIMEMessages) {
         this.signLayout.setEnabled((boolean)0);
         this.signCheckBox.setEnabled((boolean)0);
         this.signTextView.setEnabled((boolean)0);
         this.mDraft.mSigned = (boolean)1;
      } else {
         this.signLayout.setEnabled((boolean)1);
         this.signCheckBox.setEnabled((boolean)1);
         this.signTextView.setEnabled((boolean)1);
      }
   }

   private void disableOrEnableOptions(boolean var1) {
      this.encryptLayout.setEnabled(var1);
      this.encryptCheckBox.setEnabled(var1);
      this.encryptTextView.setEnabled(var1);
      this.signLayout.setEnabled(var1);
      this.signCheckBox.setEnabled(var1);
      this.signTextView.setEnabled(var1);
   }

   private void setAccount(Intent var1) {
      long var2 = var1.getLongExtra("account_id", 65535L);
      if(var2 == 65535L) {
         var2 = EmailContent.Account.getDefaultAccountId(this);
      }

      if(var2 == 65535L) {
         AccountManager.actionShowAccounts(this);
         this.finish();
      } else {
         EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(this, var2);
         this.mAccount = var4;
      }
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131362503:
         this.encryptCheckBox.toggle();
         EmailContent.Message var2 = this.mDraft;
         boolean var3 = this.encryptCheckBox.isChecked();
         var2.mEncrypted = var3;
         return;
      case 2131362504:
      case 2131362505:
      default:
         return;
      case 2131362506:
         this.signCheckBox.toggle();
         EmailContent.Message var4 = this.mDraft;
         boolean var5 = this.signCheckBox.isChecked();
         var4.mSigned = var5;
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903189);
      RelativeLayout var2 = (RelativeLayout)this.findViewById(2131362503);
      this.encryptLayout = var2;
      RelativeLayout var3 = (RelativeLayout)this.findViewById(2131362506);
      this.signLayout = var3;
      CheckBox var4 = (CheckBox)this.findViewById(2131362505);
      this.encryptCheckBox = var4;
      CheckBox var5 = (CheckBox)this.findViewById(2131362508);
      this.signCheckBox = var5;
      TextView var6 = (TextView)this.findViewById(2131362504);
      this.encryptTextView = var6;
      TextView var7 = (TextView)this.findViewById(2131362507);
      this.signTextView = var7;
      this.encryptLayout.setOnClickListener(this);
      this.signLayout.setOnClickListener(this);
      this.encryptCheckBox.setOnKeyListener(this);
      this.signCheckBox.setOnKeyListener(this);
      Intent var8 = this.getIntent();
      this.setAccount(var8);
      if(var1 != null) {
         EmailContent.Message var9 = this.mDraft;
         boolean var10 = var1.getBoolean("com.android.email.securityoption.sign");
         var9.mSigned = var10;
         EmailContent.Message var11 = this.mDraft;
         boolean var12 = var1.getBoolean("com.android.email.securityoption.encrypt");
         var11.mEncrypted = var12;
      } else {
         EmailContent.Message var13 = this.mDraft;
         boolean var14 = this.getIntent().getBooleanExtra("signed", (boolean)0);
         var13.mSigned = var14;
         EmailContent.Message var15 = this.mDraft;
         boolean var16 = this.getIntent().getBooleanExtra("encrypted", (boolean)0);
         var15.mEncrypted = var16;
      }
   }

   public boolean onKey(View var1, int var2, KeyEvent var3) {
      if(var3.getAction() == 0 && var3.getKeyCode() == 66) {
         switch(var1.getId()) {
         case 2131362505:
            this.encryptCheckBox.toggle();
            EmailContent.Message var4 = this.mDraft;
            boolean var5 = this.encryptCheckBox.isChecked();
            var4.mEncrypted = var5;
         case 2131362506:
         case 2131362507:
         default:
            break;
         case 2131362508:
            this.signCheckBox.toggle();
            EmailContent.Message var6 = this.mDraft;
            boolean var7 = this.signCheckBox.isChecked();
            var6.mSigned = var7;
         }
      }

      return false;
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      if(var1 == 4) {
         System.out.println("BACK PRESSED");
         Intent var3 = new Intent();
         boolean var4 = this.signCheckBox.isChecked();
         var3.putExtra("signed", var4);
         boolean var6 = this.encryptCheckBox.isChecked();
         var3.putExtra("encrypted", var6);
         this.setResult(-1, var3);
      }

      return super.onKeyDown(var1, var2);
   }

   protected void onPause() {
      super.onPause();
   }

   protected void onResume() {
      super.onResume();
      this.configureSmimeOptions();
      CheckBox var1 = this.signCheckBox;
      boolean var2 = this.mDraft.mSigned;
      var1.setChecked(var2);
      CheckBox var3 = this.encryptCheckBox;
      boolean var4 = this.mDraft.mEncrypted;
      var3.setChecked(var4);
   }

   public void onSaveInstanceState(Bundle var1) {
      if(this.mDraft != null) {
         boolean var2 = this.mDraft.mSigned;
         var1.putBoolean("com.android.email.securityoption.sign", var2);
         boolean var3 = this.mDraft.mEncrypted;
         var1.putBoolean("com.android.email.securityoption.encrypt", var3);
      }

      super.onSaveInstanceState(var1);
   }
}
