package com.android.email.activity.setup;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.android.email.EmailTwSoftkeyItem;
import com.android.email.activity.setup.AccountSetupExchange;
import com.android.email.provider.EmailContent;
import java.net.URI;
import java.net.URISyntaxException;

public class AutoDiscoverSetupExchange extends Activity {

   private static final String KEY_EXTRA_ACCOUNT = "com.android.email.ACCOUNT";
   public static final String KEY_EXTRA_DOMAIN = "com.android.email.DOMAIN";
   private static final String KEY_EXTRA_EAS_FLOW = "com.android.email.EAS_FLOW";
   public static final String KEY_EXTRA_EMAIL = "com.android.email.EMAIL";
   private static final String KEY_EXTRA_MAKE_DEFAULT = "com.android.email.MAKE_DEFAULT";
   public static final String KEY_EXTRA_PASSWORD = "com.android.email.PASSWORD";
   public static final String KEY_EXTRA_TRUST_CERT = "com.android.email.TRUSTCERT";
   public static final String KEY_EXTRA_USER = "com.android.email.USER";
   private static final String TAG = AutoDiscoverSetupExchange.class.getSimpleName();
   private EmailContent.Account mAccount;
   public EditText mDomainText;
   private boolean mEasFlowMode;
   public EditText mEmailText;
   private boolean mMakeDefault;
   private EmailTwSoftkeyItem mNextButton;
   public EditText mPasswordText;
   boolean mTrustCerti;
   private CheckBox mTrustedCertificates = null;
   public EditText mUserNameText;


   public AutoDiscoverSetupExchange() {}

   public static void actionAutoDiscoverSetupExchangeIntent(Activity var0, EmailContent.Account var1, boolean var2, boolean var3) {
      Intent var4 = new Intent(var0, AutoDiscoverSetupExchange.class);
      var4.putExtra("com.android.email.ACCOUNT", var1);
      var4.putExtra("com.android.email.MAKE_DEFAULT", var2);
      var4.putExtra("com.android.email.EAS_FLOW", var3);
      var0.startActivityForResult(var4, 3);
   }

   private void enableDisableNextButton() {
      byte var1;
      if(!TextUtils.isEmpty(this.mEmailText.getText().toString()) && !TextUtils.isEmpty(this.mPasswordText.getText().toString()) && !TextUtils.isEmpty(this.mUserNameText.getText().toString())) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      this.mNextButton.setEnabled((boolean)var1);
      this.mNextButton.setFocusable((boolean)var1);
      Resources var2 = this.getResources();
      if(var1 != 0) {
         EmailTwSoftkeyItem var3 = this.mNextButton;
         Drawable var4 = var2.getDrawable(2130837923);
         var3.setEmailTwSoftkeyItemImage(var4);
      } else {
         EmailTwSoftkeyItem var5 = this.mNextButton;
         Drawable var6 = var2.getDrawable(2130837924);
         var5.setEmailTwSoftkeyItemImage(var6);
      }
   }

   private void readArgumentsFromIntent() {
      EmailContent.Account var1 = (EmailContent.Account)this.getIntent().getParcelableExtra("com.android.email.ACCOUNT");
      this.mAccount = var1;
      if(this.mAccount == null) {
         int var2 = Log.e(TAG, "Expecting Account in KEY_EXTRA_ACCOUNT");
         this.finish();
      }

      boolean var3 = this.getIntent().getBooleanExtra("com.android.email.MAKE_DEFAULT", (boolean)0);
      this.mMakeDefault = var3;
      boolean var4 = this.getIntent().getBooleanExtra("com.android.email.EAS_FLOW", (boolean)0);
      this.mEasFlowMode = var4;
   }

   private void setSmartValues() {
      URI var2;
      try {
         String var1 = this.mAccount.getStoreUri(this);
         var2 = new URI(var1);
      } catch (URISyntaxException var12) {
         int var11 = Log.e(TAG, "Failed to restore Account store URI");
         return;
      }

      if(var2.getUserInfo() != null) {
         String[] var3 = var2.getUserInfo().split(":", 2);
         EditText var4 = this.mUserNameText;
         String var5 = var3[0].trim();
         var4.setText(var5);
         if(var3.length > 1) {
            EditText var6 = this.mPasswordText;
            String var7 = var3[1].trim();
            var6.setText(var7);
         }
      }

      EditText var8 = this.mEmailText;
      String var9 = this.mAccount.mEmailAddress;
      var8.setText(var9);
   }

   private void setupExchange(boolean var1) {
      EmailContent.Account var2 = this.mAccount;
      boolean var3 = this.mMakeDefault;
      boolean var4 = this.mEasFlowMode;
      AccountSetupExchange.actionIncomingSettings(this, var2, var3, var4, var1);
      this.finish();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903074);
      this.readArgumentsFromIntent();
      CheckBox var2 = (CheckBox)this.findViewById(2131361875);
      this.mTrustedCertificates = var2;
      AutoDiscoverSetupExchange.1 var3 = new AutoDiscoverSetupExchange.1();
      EmailTwSoftkeyItem var4 = (EmailTwSoftkeyItem)this.findViewById(2131361850);
      this.mNextButton = var4;
      EditText var5 = (EditText)((EditText)this.findViewById(2131361983));
      this.mEmailText = var5;
      EditText var6 = (EditText)((EditText)this.findViewById(2131361849));
      this.mPasswordText = var6;
      EditText var7 = (EditText)((EditText)this.findViewById(2131361984));
      this.mDomainText = var7;
      EditText var8 = (EditText)((EditText)this.findViewById(2131361985));
      this.mUserNameText = var8;
      boolean var9 = this.mDomainText.requestFocus();
      this.mEmailText.addTextChangedListener(var3);
      this.mPasswordText.addTextChangedListener(var3);
      this.mUserNameText.addTextChangedListener(var3);
      this.setSmartValues();
      EmailTwSoftkeyItem var10 = (EmailTwSoftkeyItem)this.findViewById(2131361986);
      AutoDiscoverSetupExchange.2 var11 = new AutoDiscoverSetupExchange.2();
      var10.setOnClickListener(var11);
      EmailTwSoftkeyItem var12 = this.mNextButton;
      AutoDiscoverSetupExchange.3 var13 = new AutoDiscoverSetupExchange.3();
      var12.setOnClickListener(var13);
      LinearLayout var14 = (LinearLayout)this.findViewById(2131361874);
      AutoDiscoverSetupExchange.4 var15 = new AutoDiscoverSetupExchange.4();
      var14.setOnClickListener(var15);
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         AutoDiscoverSetupExchange.this.setupExchange((boolean)0);
      }
   }

   class 1 implements TextWatcher {

      1() {}

      public void afterTextChanged(Editable var1) {
         AutoDiscoverSetupExchange.this.enableDisableNextButton();
      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(View var1) {
         if(AutoDiscoverSetupExchange.this.mTrustedCertificates.isChecked()) {
            AutoDiscoverSetupExchange.this.mTrustedCertificates.setChecked((boolean)0);
         } else {
            AutoDiscoverSetupExchange.this.mTrustedCertificates.setChecked((boolean)1);
         }
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         String var2 = AutoDiscoverSetupExchange.this.mEmailText.getText().toString().trim();
         String var3 = AutoDiscoverSetupExchange.this.mPasswordText.getText().toString().trim();
         String var4 = AutoDiscoverSetupExchange.this.mDomainText.getText().toString().trim();
         String var5 = AutoDiscoverSetupExchange.this.mUserNameText.getText().toString().trim();
         boolean var6 = AutoDiscoverSetupExchange.this.mTrustedCertificates.isChecked();
         Intent var7 = new Intent();
         var7.putExtra("com.android.email.EMAIL", var2);
         var7.putExtra("com.android.email.DOMAIN", var4);
         var7.putExtra("com.android.email.PASSWORD", var3);
         var7.putExtra("com.android.email.USER", var5);
         var7.putExtra("com.android.email.TRUSTCERT", var6);
         AutoDiscoverSetupExchange.this.setResult(-1, var7);
         AutoDiscoverSetupExchange.this.finish();
      }
   }
}
