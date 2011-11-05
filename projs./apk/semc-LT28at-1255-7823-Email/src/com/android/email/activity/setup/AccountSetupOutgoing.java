package com.android.email.activity.setup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.android.email.AccountBackupRestore;
import com.android.email.Utility;
import com.android.email.activity.setup.AccountSetupCheckSettings;
import com.android.email.activity.setup.AccountSetupOptions;
import com.android.email.activity.setup.SpinnerOption;
import com.android.email.provider.EmailContent;
import java.net.URI;
import java.net.URISyntaxException;

public class AccountSetupOutgoing extends Activity implements OnClickListener, OnCheckedChangeListener {

   private static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_EXIT_AFTER_SETUP = "exitAfterSetup";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private static final String EXTRA_OUTGOING_AUTH = "outgoingAuth";
   private static final int[] SMTP_PORTS = new int[]{587, 465, 465, 587, 587};
   private static final String[] SMTP_SCHEMES;
   private EmailContent.Account mAccount;
   private boolean mExitAfterSetup;
   private boolean mMakeDefault;
   private Button mNextButton;
   private EditText mPasswordView;
   private EditText mPortView;
   private ViewGroup mRequireLoginSettingsView;
   private CheckBox mRequireLoginView;
   private Spinner mSecurityTypeView;
   private EditText mServerView;
   private EditText mUsernameView;


   static {
      String[] var0 = new String[]{"smtp", "smtp+ssl+", "smtp+ssl+trustallcerts", "smtp+tls+", "smtp+tls+trustallcerts"};
      SMTP_SCHEMES = var0;
   }

   public AccountSetupOutgoing() {}

   public static void actionEditOutgoingSettings(Activity var0, EmailContent.Account var1) {
      Intent var2 = new Intent(var0, AccountSetupOutgoing.class);
      Intent var3 = var2.setAction("android.intent.action.EDIT");
      var2.putExtra("account", var1);
      var0.startActivity(var2);
   }

   public static void actionOutgoingSettings(Activity var0, EmailContent.Account var1, boolean var2, boolean var3, boolean var4) {
      Intent var5 = new Intent(var0, AccountSetupOutgoing.class);
      var5.putExtra("account", var1);
      var5.putExtra("makeDefault", var2);
      var5.putExtra("exitAfterSetup", var3);
      var5.putExtra("outgoingAuth", var4);
      var0.startActivity(var5);
   }

   private void onNext() {
      try {
         URI var1 = this.getUri();
         EmailContent.Account var2 = this.mAccount;
         String var3 = var1.toString();
         var2.setSenderUri(this, var3);
      } catch (URISyntaxException var6) {
         throw new Error(var6);
      }

      EmailContent.Account var4 = this.mAccount;
      AccountSetupCheckSettings.actionValidateSettings(this, var4, (boolean)0, (boolean)1);
   }

   private void updatePortFromSecurityType() {
      int var1 = ((Integer)((SpinnerOption)this.mSecurityTypeView.getSelectedItem()).value).intValue();
      EditText var2 = this.mPortView;
      String var3 = Integer.toString(SMTP_PORTS[var1]);
      var2.setText(var3);
   }

   private void validateFields() {
      byte var1;
      if(Utility.requiredFieldValid((TextView)this.mServerView) && Utility.isPortFieldValid(this.mPortView)) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      if(var1 != 0 && this.mRequireLoginView.isChecked()) {
         if(Utility.requiredFieldValid((TextView)this.mUsernameView) && Utility.requiredFieldValid((TextView)this.mPasswordView)) {
            var1 = 1;
         } else {
            var1 = 0;
         }
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
   }

   URI getUri() throws URISyntaxException {
      int var1 = ((Integer)((SpinnerOption)this.mSecurityTypeView.getSelectedItem()).value).intValue();
      String var2 = null;
      if(this.mRequireLoginView.isChecked()) {
         StringBuilder var3 = new StringBuilder();
         String var4 = this.mUsernameView.getText().toString().trim();
         StringBuilder var5 = var3.append(var4).append(":");
         Editable var6 = this.mPasswordView.getText();
         var2 = var5.append(var6).toString();
      }

      String var7 = SMTP_SCHEMES[var1];
      String var8 = this.mServerView.getText().toString().trim();
      int var9 = Integer.parseInt(this.mPortView.getText().toString().trim());
      Object var10 = null;
      Object var11 = null;
      return new URI(var7, var2, var8, var9, (String)null, (String)var10, (String)var11);
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if(var2 == -1) {
         String var4 = this.getIntent().getAction();
         if("android.intent.action.EDIT".equals(var4)) {
            if(this.mAccount.isSaved()) {
               EmailContent.Account var5 = this.mAccount;
               ContentValues var6 = this.mAccount.toContentValues();
               var5.update(this, var6);
               EmailContent.HostAuth var8 = this.mAccount.mHostAuthSend;
               ContentValues var9 = this.mAccount.mHostAuthSend.toContentValues();
               var8.update(this, var9);
            } else {
               Uri var11 = this.mAccount.save(this);
            }

            AccountBackupRestore.backupAccounts(this);
            this.finish();
         } else {
            EmailContent.Account var12 = this.mAccount;
            boolean var13 = this.mMakeDefault;
            boolean var14 = this.mExitAfterSetup;
            AccountSetupOptions.actionOptions(this, var12, var13, (boolean)0, var14);
            this.finish();
         }
      }
   }

   public void onCheckedChanged(CompoundButton var1, boolean var2) {
      ViewGroup var3 = this.mRequireLoginSettingsView;
      byte var4;
      if(var2) {
         var4 = 0;
      } else {
         var4 = 8;
      }

      var3.setVisibility(var4);
      this.validateFields();
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
      this.setContentView(2130903049);
      this.setTheme(16973829);
      EditText var2 = (EditText)this.findViewById(2131558424);
      this.mUsernameView = var2;
      EditText var3 = (EditText)this.findViewById(2131558416);
      this.mPasswordView = var3;
      EditText var4 = (EditText)this.findViewById(2131558425);
      this.mServerView = var4;
      EditText var5 = (EditText)this.findViewById(2131558430);
      this.mPortView = var5;
      CheckBox var6 = (CheckBox)this.findViewById(2131558447);
      this.mRequireLoginView = var6;
      ViewGroup var7 = (ViewGroup)this.findViewById(2131558448);
      this.mRequireLoginSettingsView = var7;
      Spinner var8 = (Spinner)this.findViewById(2131558431);
      this.mSecurityTypeView = var8;
      Button var9 = (Button)this.findViewById(2131558417);
      this.mNextButton = var9;
      this.mNextButton.setOnClickListener(this);
      this.mRequireLoginView.setOnCheckedChangeListener(this);
      SpinnerOption[] var10 = new SpinnerOption[5];
      Integer var11 = Integer.valueOf(0);
      String var12 = this.getString(2131165348);
      SpinnerOption var13 = new SpinnerOption(var11, var12);
      var10[0] = var13;
      Integer var14 = Integer.valueOf(1);
      String var15 = this.getString(2131165350);
      SpinnerOption var16 = new SpinnerOption(var14, var15);
      var10[1] = var16;
      Integer var17 = Integer.valueOf(2);
      String var18 = this.getString(2131165349);
      SpinnerOption var19 = new SpinnerOption(var17, var18);
      var10[2] = var19;
      Integer var20 = Integer.valueOf(3);
      String var21 = this.getString(2131165352);
      SpinnerOption var22 = new SpinnerOption(var20, var21);
      var10[3] = var22;
      Integer var23 = Integer.valueOf(4);
      String var24 = this.getString(2131165351);
      SpinnerOption var25 = new SpinnerOption(var23, var24);
      var10[4] = var25;
      ArrayAdapter var26 = new ArrayAdapter(this, 17367048, var10);
      var26.setDropDownViewResource(2130903075);
      this.mSecurityTypeView.setAdapter(var26);
      Spinner var27 = this.mSecurityTypeView;
      AccountSetupOutgoing.1 var28 = new AccountSetupOutgoing.1();
      var27.setOnItemSelectedListener(var28);
      AccountSetupOutgoing.2 var29 = new AccountSetupOutgoing.2();
      this.mUsernameView.addTextChangedListener(var29);
      this.mPasswordView.addTextChangedListener(var29);
      this.mServerView.addTextChangedListener(var29);
      this.mPortView.addTextChangedListener(var29);
      EditText var30 = this.mPortView;
      DigitsKeyListener var31 = DigitsKeyListener.getInstance("0123456789");
      var30.setKeyListener(var31);
      EmailContent.Account var32 = (EmailContent.Account)this.getIntent().getParcelableExtra("account");
      this.mAccount = var32;
      boolean var33 = this.getIntent().getBooleanExtra("makeDefault", (boolean)0);
      this.mMakeDefault = var33;
      boolean var34 = this.getIntent().getBooleanExtra("exitAfterSetup", (boolean)0);
      this.mExitAfterSetup = var34;
      if(var1 != null && var1.containsKey("account")) {
         EmailContent.Account var35 = (EmailContent.Account)var1.getParcelable("account");
         this.mAccount = var35;
      }

      try {
         String var36 = this.mAccount.getSenderUri(this);
         URI var37 = new URI(var36);
         String var38 = null;
         String var39 = null;
         if(var37.getUserInfo() != null) {
            String[] var40 = var37.getUserInfo().split(":", 2);
            var38 = var40[0];
            if(var40.length > 1) {
               var39 = var40[1];
            }
         }

         Intent var41 = this.getIntent();
         String var42 = "outgoingAuth";
         byte var43;
         if(var38 != null) {
            var43 = 1;
         } else {
            var43 = 0;
         }

         boolean var44 = var41.getBooleanExtra(var42, (boolean)var43);
         this.mRequireLoginView.setChecked(var44);
         if(var38 != null) {
            this.mUsernameView.setText(var38);
         }

         if(var39 != null) {
            this.mPasswordView.setText(var39);
         }

         int var45 = 0;

         while(true) {
            int var46 = SMTP_SCHEMES.length;
            if(var45 >= var46) {
               if(var37.getHost() != null) {
                  EditText var51 = this.mServerView;
                  String var52 = var37.getHost();
                  var51.setText(var52);
               }

               if(var37.getPort() != -1) {
                  EditText var53 = this.mPortView;
                  String var54 = Integer.toString(var37.getPort());
                  var53.setText(var54);
               } else {
                  this.updatePortFromSecurityType();
               }
               break;
            }

            String var47 = SMTP_SCHEMES[var45];
            String var48 = var37.getScheme();
            if(var47.equals(var48)) {
               Spinner var49 = this.mSecurityTypeView;
               Integer var50 = Integer.valueOf(var45);
               SpinnerOption.setSpinnerOptionValue(var49, var50);
            }

            ++var45;
         }
      } catch (URISyntaxException var56) {
         throw new Error(var56);
      }

      this.validateFields();
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      EmailContent.Account var2 = this.mAccount;
      var1.putParcelable("account", var2);
   }

   class 1 implements OnItemSelectedListener {

      1() {}

      public void onItemSelected(AdapterView var1, View var2, int var3, long var4) {
         AccountSetupOutgoing.this.updatePortFromSecurityType();
      }

      public void onNothingSelected(AdapterView<?> var1) {}
   }

   class 2 implements TextWatcher {

      2() {}

      public void afterTextChanged(Editable var1) {
         AccountSetupOutgoing.this.validateFields();
      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}
   }
}
