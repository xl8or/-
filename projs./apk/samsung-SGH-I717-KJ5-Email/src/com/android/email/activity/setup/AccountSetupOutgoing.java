package com.android.email.activity.setup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
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

   private boolean isFromAccountSettingMenu() {
      String var1 = this.getIntent().getAction();
      boolean var2;
      if("android.intent.action.EDIT".equals(var1) == 1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
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

               String var3 = this.getString(2131166621);
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
      case 2131361947:
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
      int var14 = 2131361883;
      EditText var15 = (EditText)this.findViewById(var14);
      this.mPortView = var15;
      int var17 = 2131361948;
      CheckBox var18 = (CheckBox)this.findViewById(var17);
      this.mRequireLoginView = var18;
      int var20 = 2131361949;
      ViewGroup var21 = (ViewGroup)this.findViewById(var20);
      this.mRequireLoginSettingsView = var21;
      int var23 = 2131361882;
      Spinner var24 = (Spinner)this.findViewById(var23);
      this.mSecurityTypeView = var24;
      int var26 = 2131361947;
      LinearLayout var27 = (LinearLayout)this.findViewById(var26);
      this.mRequireLoginViewLayout = var27;
      int var29 = 2131361862;
      EmailTwSoftkeyItem var30 = (EmailTwSoftkeyItem)this.findViewById(var29);
      this.mNextTwSoftkeyItem = var30;
      LinearLayout var31 = this.mRequireLoginViewLayout;
      var31.setOnClickListener(this);
      EmailTwSoftkeyItem var33 = this.mNextTwSoftkeyItem;
      var33.setOnClickListener(this);
      CheckBox var35 = this.mRequireLoginView;
      var35.setOnClickListener(this);
      CheckBox var37 = this.mRequireLoginView;
      var37.setOnCheckedChangeListener(this);
      SpinnerOption[] var39 = new SpinnerOption[5];
      Integer var40 = Integer.valueOf(0);
      int var42 = 2131166367;
      String var43 = this.getString(var42);
      SpinnerOption var44 = new SpinnerOption(var40, var43);
      var39[0] = var44;
      Integer var45 = Integer.valueOf(1);
      int var47 = 2131166369;
      String var48 = this.getString(var47);
      SpinnerOption var49 = new SpinnerOption(var45, var48);
      var39[1] = var49;
      Integer var50 = Integer.valueOf(2);
      int var52 = 2131166368;
      String var53 = this.getString(var52);
      SpinnerOption var54 = new SpinnerOption(var50, var53);
      var39[2] = var54;
      Integer var55 = Integer.valueOf(3);
      int var57 = 2131166371;
      String var58 = this.getString(var57);
      SpinnerOption var59 = new SpinnerOption(var55, var58);
      var39[3] = var59;
      Integer var60 = Integer.valueOf(4);
      int var62 = 2131166370;
      String var63 = this.getString(var62);
      SpinnerOption var64 = new SpinnerOption(var60, var63);
      var39[4] = var64;
      ArrayAdapter var65 = new ArrayAdapter;
      int var68 = 17367048;
      var65.<init>(this, var68, var39);
      int var71 = 17367049;
      var65.setDropDownViewResource(var71);
      Spinner var72 = this.mSecurityTypeView;
      var72.setAdapter(var65);
      byte var74 = 1;
      this.bFirstSelected = (boolean)var74;
      Spinner var75 = this.mSecurityTypeView;
      AccountSetupOutgoing.1 var76 = new AccountSetupOutgoing.1();
      var75.setOnItemSelectedListener(var76);
      AccountSetupOutgoing.2 var79 = new AccountSetupOutgoing.2();
      EditText var82 = this.mUsernameView;
      var82.addTextChangedListener(var79);
      EditText var84 = this.mPasswordView;
      var84.addTextChangedListener(var79);
      EditText var86 = this.mServerView;
      var86.addTextChangedListener(var79);
      EditText var88 = this.mPortView;
      var88.addTextChangedListener(var79);
      EditText var90 = this.mPortView;
      DigitsKeyListener var91 = DigitsKeyListener.getInstance("0123456789");
      var90.setKeyListener(var91);
      EmailContent.Account var92 = (EmailContent.Account)this.getIntent().getParcelableExtra("account");
      this.mAccount = var92;
      boolean var93 = this.getIntent().getBooleanExtra("makeDefault", (boolean)0);
      this.mMakeDefault = var93;
      if(var1 != null) {
         String var95 = "account";
         if(var1.containsKey(var95)) {
            String var97 = "account";
            EmailContent.Account var98 = (EmailContent.Account)var1.getParcelable(var97);
            this.mAccount = var98;
         }
      }

      try {
         URI var99 = new URI;
         EmailContent.Account var100 = this.mAccount;
         String var102 = var100.getSenderUri(this);
         var99.<init>(var102);
         String var105 = null;
         String var106 = null;
         if(var99.getUserInfo() != null) {
            String[] var107 = var99.getUserInfo().split(":", 2);
            var105 = var107[0];
            int var108 = var107.length;
            byte var109 = 1;
            if(var108 > var109) {
               var106 = var107[1];
            }
         }

         if(var105 != null && var105.length() > 0) {
            EditText var110 = this.mUsernameView;
            var110.setText(var105);
            this.mRequireLoginView.setChecked((boolean)1);
            EditText var112 = this.mUsernameView;
            int var113 = var105.length();
            var112.setSelection(var113);
         }

         if(var106 != null) {
            EditText var114 = this.mPasswordView;
            var114.setText(var106);
            EditText var116 = this.mPasswordView;
            int var117 = var106.length();
            var116.setSelection(var117);
         }

         int var118 = 0;

         while(true) {
            int var119 = smtpSchemes.length;
            if(var118 >= var119) {
               String var126;
               if(this.getIntent().getAction() == null) {
                  var126 = var99.getHost();
                  if(var126 != null) {
                     StringBuffer var127 = new StringBuffer();
                     String var129 = "smtp";
                     if(!var126.startsWith(var129)) {
                        String var131 = "mopera.net";
                        if(!var126.endsWith(var131)) {
                           String var133 = "smtp.";
                           var127.append(var133);
                        }
                     }

                     var127.append(var126);
                     EditText var136 = this.mServerView;
                     String var137 = var127.toString().trim();
                     var136.setText(var137);
                     EditText var138 = this.mServerView;
                     int var139 = this.mServerView.getText().length();
                     var138.setSelection(var139);
                  }
               } else {
                  var126 = var99.getHost();
                  if(var126 != null) {
                     EditText var154 = this.mServerView;
                     String var155 = var126.trim();
                     var154.setText(var155);
                     EditText var156 = this.mServerView;
                     int var157 = this.mServerView.getText().length();
                     var156.setSelection(var157);
                  }
               }

               int var140 = var99.getPort();
               char var141 = '\uffff';
               if(var140 != var141) {
                  String var142 = Integer.toString(var99.getPort());
                  if(var142 != null) {
                     EditText var143 = this.mPortView;
                     var143.setText(var142);
                     EditText var145 = this.mPortView;
                     int var146 = var142.length();
                     var145.setSelection(var146);
                  }
               } else {
                  this.updatePortFromSecurityType();
               }
               break;
            }

            String var122 = smtpSchemes[var118];
            String var123 = var99.getScheme();
            if(var122.equals(var123)) {
               Spinner var124 = this.mSecurityTypeView;
               Integer var125 = Integer.valueOf(var118);
               SpinnerOption.setSpinnerOptionValue(var124, var125);
            }

            ++var118;
         }
      } catch (URISyntaxException var162) {
         Error var159 = new Error(var162);
         throw var159;
      }

      String var147 = SystemProperties.get("ro.csc.sales_code");
      String var148 = "TNL CRO DTM MAX MBM TPL TRG TMZ";
      if(var148.contains(var147)) {
         byte var150 = this.isFromAccountSettingMenu();
         byte var151 = 1;
         if(var150 != var151) {
            this.mServerView.setText("");
            Spinner var152 = this.mSecurityTypeView;
            Integer var153 = Integer.valueOf(0);
            SpinnerOption.setSpinnerOptionValue(var152, var153);
            this.mPortView.setText("");
         }
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
