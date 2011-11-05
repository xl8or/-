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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.android.email.AccountBackupRestore;
import com.android.email.EmailTwSoftkeyItem;
import com.android.email.Utility;
import com.android.email.activity.setup.AccountSetupCheckSettings;
import com.android.email.activity.setup.AccountSetupCustomer;
import com.android.email.activity.setup.AccountSetupOptions;
import com.android.email.activity.setup.SpinnerOption;
import com.android.email.provider.EmailContent;
import com.digc.seven.SevenSyncProvider;
import java.net.URI;
import java.net.URISyntaxException;

public class AccountSetupOutgoing extends Activity implements OnClickListener, OnCheckedChangeListener {

   private static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private static final int[] smtpPorts = new int[]{587, 465, 465, 587, 587};
   private static final String[] smtpSchemes;
   private boolean bFirstSelected = 1;
   private EmailContent.Account mAccount;
   private boolean mMakeDefault;
   private EmailTwSoftkeyItem mNextTwSoftkeyItem;
   private EditText mPasswordView;
   private EditText mPortView;
   private ViewGroup mRequireLoginSettingsView;
   private CheckBox mRequireLoginView;
   private LinearLayout mRequireLoginViewLayout;
   private Spinner mSecurityTypeView;
   private EditText mServerView;
   private EditText mUsernameView;


   static {
      String[] var0 = new String[]{"smtp", "smtp+ssl+", "smtp+ssl+trustallcerts", "smtp+tls+", "smtp+tls+trustallcerts"};
      smtpSchemes = var0;
   }

   public AccountSetupOutgoing() {}

   public static void actionEditOutgoingSettings(Activity var0, EmailContent.Account var1) {
      Intent var2 = new Intent(var0, AccountSetupOutgoing.class);
      Intent var3 = var2.setAction("android.intent.action.EDIT");
      var2.putExtra("account", var1);
      var0.startActivity(var2);
   }

   public static void actionOutgoingSettings(Activity var0, EmailContent.Account var1, boolean var2) {
      Intent var3 = new Intent(var0, AccountSetupOutgoing.class);
      var3.putExtra("account", var1);
      var3.putExtra("makeDefault", var2);
      var0.startActivity(var3);
   }

   private URI getUri() throws URISyntaxException {
      int var1 = ((Integer)((SpinnerOption)this.mSecurityTypeView.getSelectedItem()).value).intValue();
      String var2 = null;
      if(this.mRequireLoginView.isChecked()) {
         StringBuilder var3 = new StringBuilder();
         String var4 = this.mUsernameView.getText().toString().trim();
         StringBuilder var5 = var3.append(var4).append(":");
         String var6 = this.mPasswordView.getText().toString().trim();
         var2 = var5.append(var6).toString();
      }

      String var7 = smtpSchemes[var1];
      String var8 = this.mServerView.getText().toString().trim();
      int var9 = Integer.parseInt(this.mPortView.getText().toString().trim());
      Object var10 = null;
      Object var11 = null;
      return new URI(var7, var2, var8, var9, (String)null, (String)var10, (String)var11);
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
      String var3 = Integer.toString(smtpPorts[var1]);
      var2.setText(var3);
      EditText var4 = this.mPortView;
      int var5 = Integer.toString(smtpPorts[var1]).length();
      var4.setSelection(var5);
   }

   private void validateFields() {
      byte var1;
      if(Utility.requiredFieldValid((TextView)this.mServerView) && Utility.requiredFieldValid((TextView)this.mPortView)) {
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
         label32: {
            try {
               int var2 = this.getUri().getPort();
               if(var2 >= 0 && var2 <= '\uffff') {
                  break label32;
               }

               String var3 = this.getString(2131166613);
               Toast.makeText(this, var3, 1).show();
            } catch (URISyntaxException var6) {
               var1 = 0;
               break label32;
            } catch (NullPointerException var7) {
               var1 = 0;
               break label32;
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
            AccountSetupOptions.actionOptions(this, var12, var13, (boolean)0);
            this.finish();
         }
      }
   }

   public void onCheckedChanged(CompoundButton var1, boolean var2) {
      var1.playSoundEffect(9);
      if(var2) {
         this.mRequireLoginSettingsView.setVisibility(0);
         this.mPortView.setImeOptions(5);
      } else {
         if(this.mUsernameView.hasFocus() || this.mPasswordView.hasFocus()) {
            boolean var5 = this.mPortView.requestFocus();
         }

         this.mRequireLoginSettingsView.setVisibility(8);
         this.mPortView.setImeOptions(6);
      }

      InputMethodManager var3 = (InputMethodManager)this.getSystemService("input_method");
      EditText var4 = this.mPortView;
      var3.restartInput(var4);
      this.validateFields();
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131361862:
         this.onNext();
         return;
      case 2131361944:
         if(this.mRequireLoginView.isChecked()) {
            this.mRequireLoginView.setChecked((boolean)0);
            return;
         }

         this.mRequireLoginView.setChecked((boolean)1);
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      int var3 = 2130903062;
      this.setContentView(var3);
      int var5 = 2131361870;
      EditText var6 = (EditText)this.findViewById(var5);
      this.mUsernameView = var6;
      int var8 = 2131361849;
      EditText var9 = (EditText)this.findViewById(var8);
      this.mPasswordView = var9;
      int var11 = 2131361871;
      EditText var12 = (EditText)this.findViewById(var11);
      this.mServerView = var12;
      int var14 = 2131361880;
      EditText var15 = (EditText)this.findViewById(var14);
      this.mPortView = var15;
      int var17 = 2131361945;
      CheckBox var18 = (CheckBox)this.findViewById(var17);
      this.mRequireLoginView = var18;
      int var20 = 2131361946;
      ViewGroup var21 = (ViewGroup)this.findViewById(var20);
      this.mRequireLoginSettingsView = var21;
      int var23 = 2131361879;
      Spinner var24 = (Spinner)this.findViewById(var23);
      this.mSecurityTypeView = var24;
      int var26 = 2131361944;
      LinearLayout var27 = (LinearLayout)this.findViewById(var26);
      this.mRequireLoginViewLayout = var27;
      LinearLayout var28 = this.mRequireLoginViewLayout;
      var28.setOnClickListener(this);
      int var31 = 2131361862;
      EmailTwSoftkeyItem var32 = (EmailTwSoftkeyItem)this.findViewById(var31);
      this.mNextTwSoftkeyItem = var32;
      EmailTwSoftkeyItem var33 = this.mNextTwSoftkeyItem;
      var33.setOnClickListener(this);
      CheckBox var35 = this.mRequireLoginView;
      var35.setOnCheckedChangeListener(this);
      SpinnerOption[] var37 = new SpinnerOption[5];
      Integer var38 = Integer.valueOf(0);
      int var40 = 2131166363;
      String var41 = this.getString(var40);
      SpinnerOption var42 = new SpinnerOption(var38, var41);
      var37[0] = var42;
      Integer var43 = Integer.valueOf(1);
      int var45 = 2131166365;
      String var46 = this.getString(var45);
      SpinnerOption var47 = new SpinnerOption(var43, var46);
      var37[1] = var47;
      Integer var48 = Integer.valueOf(2);
      int var50 = 2131166364;
      String var51 = this.getString(var50);
      SpinnerOption var52 = new SpinnerOption(var48, var51);
      var37[2] = var52;
      Integer var53 = Integer.valueOf(3);
      int var55 = 2131166367;
      String var56 = this.getString(var55);
      SpinnerOption var57 = new SpinnerOption(var53, var56);
      var37[3] = var57;
      Integer var58 = Integer.valueOf(4);
      int var60 = 2131166366;
      String var61 = this.getString(var60);
      SpinnerOption var62 = new SpinnerOption(var58, var61);
      var37[4] = var62;
      ArrayAdapter var63 = new ArrayAdapter;
      int var66 = 17367048;
      var63.<init>(this, var66, var37);
      int var69 = 17367049;
      var63.setDropDownViewResource(var69);
      Spinner var70 = this.mSecurityTypeView;
      var70.setAdapter(var63);
      byte var72 = 1;
      this.bFirstSelected = (boolean)var72;
      Spinner var73 = this.mSecurityTypeView;
      AccountSetupOutgoing.1 var74 = new AccountSetupOutgoing.1();
      var73.setOnItemSelectedListener(var74);
      AccountSetupOutgoing.2 var77 = new AccountSetupOutgoing.2();
      EditText var80 = this.mUsernameView;
      var80.addTextChangedListener(var77);
      EditText var82 = this.mPasswordView;
      var82.addTextChangedListener(var77);
      EditText var84 = this.mServerView;
      var84.addTextChangedListener(var77);
      EditText var86 = this.mPortView;
      var86.addTextChangedListener(var77);
      EditText var88 = this.mPortView;
      DigitsKeyListener var89 = DigitsKeyListener.getInstance("0123456789");
      var88.setKeyListener(var89);
      EmailContent.Account var90 = (EmailContent.Account)this.getIntent().getParcelableExtra("account");
      this.mAccount = var90;
      boolean var91 = this.getIntent().getBooleanExtra("makeDefault", (boolean)0);
      this.mMakeDefault = var91;
      if(var1 != null) {
         String var93 = "account";
         if(var1.containsKey(var93)) {
            String var95 = "account";
            EmailContent.Account var96 = (EmailContent.Account)var1.getParcelable(var95);
            this.mAccount = var96;
         }
      }

      try {
         URI var97 = new URI;
         EmailContent.Account var98 = this.mAccount;
         String var100 = var98.getSenderUri(this);
         var97.<init>(var100);
         String var103 = null;
         String var104 = null;
         if(var97.getUserInfo() != null) {
            String[] var105 = var97.getUserInfo().split(":", 2);
            var103 = var105[0];
            int var106 = var105.length;
            byte var107 = 1;
            if(var106 > var107) {
               var104 = var105[1];
            }
         }

         if(var103 != null && var103.length() > 0) {
            EditText var108 = this.mUsernameView;
            var108.setText(var103);
            this.mRequireLoginView.setChecked((boolean)1);
            EditText var110 = this.mUsernameView;
            int var111 = var103.length();
            var110.setSelection(var111);
         }

         if(var104 != null) {
            EditText var112 = this.mPasswordView;
            var112.setText(var104);
            EditText var114 = this.mPasswordView;
            int var115 = var104.length();
            var114.setSelection(var115);
         }

         int var116 = 0;

         while(true) {
            int var117 = smtpSchemes.length;
            if(var116 >= var117) {
               String var124;
               if(this.getIntent().getAction() == null) {
                  var124 = var97.getHost();
                  if(var124 != null) {
                     StringBuffer var125 = new StringBuffer();
                     String var127 = "smtp";
                     if(!var124.startsWith(var127)) {
                        String var129 = "mopera.net";
                        if(!var124.endsWith(var129)) {
                           String var131 = "smtp.";
                           var125.append(var131);
                        }
                     }

                     var125.append(var124);
                     EditText var134 = this.mServerView;
                     String var135 = var125.toString().trim();
                     var134.setText(var135);
                     EditText var136 = this.mServerView;
                     int var137 = this.mServerView.getText().length();
                     var136.setSelection(var137);
                  }
               } else {
                  var124 = var97.getHost();
                  if(var124 != null) {
                     EditText var145 = this.mServerView;
                     String var146 = var124.trim();
                     var145.setText(var146);
                     EditText var147 = this.mServerView;
                     int var148 = this.mServerView.getText().length();
                     var147.setSelection(var148);
                  }
               }

               int var138 = var97.getPort();
               char var139 = '\uffff';
               if(var138 != var139) {
                  String var140 = Integer.toString(var97.getPort());
                  if(var140 != null) {
                     EditText var141 = this.mPortView;
                     var141.setText(var140);
                     EditText var143 = this.mPortView;
                     int var144 = var140.length();
                     var143.setSelection(var144);
                  }
               } else {
                  this.updatePortFromSecurityType();
               }
               break;
            }

            String var120 = smtpSchemes[var116];
            String var121 = var97.getScheme();
            if(var120.equals(var121)) {
               Spinner var122 = this.mSecurityTypeView;
               Integer var123 = Integer.valueOf(var116);
               SpinnerOption.setSpinnerOptionValue(var122, var123);
            }

            ++var116;
         }
      } catch (URISyntaxException var153) {
         Error var150 = new Error(var153);
         throw var150;
      }

      this.validateFields();
   }

   protected void onRestoreInstanceState(Bundle var1) {
      super.onRestoreInstanceState(var1);
      if(AccountSetupCustomer.getInstance().getEmailType() == 1) {
         if(SevenSyncProvider.checkSevenApkVer(this)) {
            ;
         }
      }
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      EmailContent.Account var2 = this.mAccount;
      var1.putParcelable("account", var2);
   }

   public boolean onSearchRequested() {
      return false;
   }

   class 1 implements OnItemSelectedListener {

      1() {}

      public void onItemSelected(AdapterView var1, View var2, int var3, long var4) {
         if(AccountSetupOutgoing.this.bFirstSelected == 1) {
            boolean var6 = (boolean)(AccountSetupOutgoing.this.bFirstSelected = (boolean)0);
         } else {
            AccountSetupOutgoing.this.updatePortFromSecurityType();
         }
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
