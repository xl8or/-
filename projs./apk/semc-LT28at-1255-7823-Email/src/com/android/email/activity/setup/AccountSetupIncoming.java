package com.android.email.activity.setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.android.email.AccountBackupRestore;
import com.android.email.Utility;
import com.android.email.activity.setup.AccountSetupCheckSettings;
import com.android.email.activity.setup.AccountSetupOutgoing;
import com.android.email.activity.setup.SpinnerOption;
import com.android.email.provider.EmailContent;
import java.net.URI;
import java.net.URISyntaxException;

public class AccountSetupIncoming extends Activity implements OnClickListener {

   private static final int DIALOG_DUPLICATE_ACCOUNT = 1;
   private static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_CUSTOMIZE_FLG = "customizeFlg";
   private static final String EXTRA_EXIT_AFTER_SETUP = "exitAfterSetup";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private static final String EXTRA_OUTGOING_AUTH = "outgoingAuth";
   private static final int[] IMAP_PORTS;
   private static final String[] IMAP_SCHEMES;
   private static final int[] POP_PORTS = new int[]{110, 995, 995, 110, 110};
   private static final String[] POP_SCHEMES;
   private EmailContent.Account mAccount;
   private int[] mAccountPorts;
   private String[] mAccountSchemes;
   private String mCacheLoginCredential;
   private Spinner mDeletePolicyView;
   private String mDuplicateAccountName;
   private boolean mExitAfterSetup;
   private EditText mImapPathPrefixView;
   private boolean mMakeDefault;
   private Button mNextButton;
   private boolean mOutgoingAuth = 1;
   private EditText mPasswordView;
   private EditText mPortView;
   private Spinner mSecurityTypeView;
   private EditText mServerView;
   private EditText mUsernameView;


   static {
      String[] var0 = new String[]{"pop3", "pop3+ssl+", "pop3+ssl+trustallcerts", "pop3+tls+", "pop3+tls+trustallcerts"};
      POP_SCHEMES = var0;
      IMAP_PORTS = new int[]{143, 993, 993, 143, 143};
      String[] var1 = new String[]{"imap", "imap+ssl+", "imap+ssl+trustallcerts", "imap+tls+", "imap+tls+trustallcerts"};
      IMAP_SCHEMES = var1;
   }

   public AccountSetupIncoming() {}

   public static void actionEditIncomingSettings(Activity var0, EmailContent.Account var1) {
      Intent var2 = new Intent(var0, AccountSetupIncoming.class);
      Intent var3 = var2.setAction("android.intent.action.EDIT");
      var2.putExtra("account", var1);
      var0.startActivity(var2);
   }

   public static void actionIncomingSettings(Activity var0, EmailContent.Account var1, boolean var2, boolean var3) {
      Intent var4 = new Intent(var0, AccountSetupIncoming.class);
      var4.putExtra("account", var1);
      var4.putExtra("makeDefault", var2);
      var4.putExtra("exitAfterSetup", var3);
      var0.startActivity(var4);
   }

   public static void actionIncomingSettings(Activity var0, EmailContent.Account var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      Intent var6 = new Intent(var0, AccountSetupIncoming.class);
      var6.putExtra("account", var1);
      var6.putExtra("makeDefault", var2);
      var6.putExtra("exitAfterSetup", var3);
      var6.putExtra("customizeFlg", var4);
      var6.putExtra("outgoingAuth", var5);
      var0.startActivity(var6);
   }

   private void onNext() {
      AccountSetupIncoming.4 var1 = new AccountSetupIncoming.4();
      Void[] var2 = new Void[0];
      var1.execute(var2);
   }

   private void updatePortFromSecurityType() {
      int var1 = ((Integer)((SpinnerOption)this.mSecurityTypeView.getSelectedItem()).value).intValue();
      EditText var2 = this.mPortView;
      String var3 = Integer.toString(this.mAccountPorts[var1]);
      var2.setText(var3);
   }

   private void validateFields() {
      byte var1;
      if(Utility.requiredFieldValid((TextView)this.mUsernameView) && Utility.requiredFieldValid((TextView)this.mPasswordView) && Utility.requiredFieldValid((TextView)this.mServerView) && Utility.isPortFieldValid(this.mPortView)) {
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
   }

   URI getUri() throws URISyntaxException {
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
      Editable var8 = this.mPasswordView.getText();
      String var9 = var7.append(var8).toString();
      String var10 = this.mServerView.getText().toString().trim();
      int var11 = Integer.parseInt(this.mPortView.getText().toString().trim());
      Object var12 = null;
      return new URI(var6, var9, var10, var11, var2, (String)null, (String)var12);
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
            } catch (URISyntaxException var30) {
               ;
            }

            EmailContent.Account var25 = this.mAccount;
            boolean var26 = this.mMakeDefault;
            boolean var27 = this.mExitAfterSetup;
            boolean var28 = this.mOutgoingAuth;
            AccountSetupOutgoing.actionOutgoingSettings(this, var25, var26, var27, var28);
            this.finish();
         }
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
      int var3 = 2130903046;
      this.setContentView(var3);
      int var5 = 16973829;
      this.setTheme(var5);
      int var7 = 2131558424;
      EditText var8 = (EditText)this.findViewById(var7);
      this.mUsernameView = var8;
      int var10 = 2131558416;
      EditText var11 = (EditText)this.findViewById(var10);
      this.mPasswordView = var11;
      int var13 = 2131558429;
      TextView var14 = (TextView)this.findViewById(var13);
      int var16 = 2131558425;
      EditText var17 = (EditText)this.findViewById(var16);
      this.mServerView = var17;
      int var19 = 2131558430;
      EditText var20 = (EditText)this.findViewById(var19);
      this.mPortView = var20;
      int var22 = 2131558431;
      Spinner var23 = (Spinner)this.findViewById(var22);
      this.mSecurityTypeView = var23;
      int var25 = 2131558433;
      Spinner var26 = (Spinner)this.findViewById(var25);
      this.mDeletePolicyView = var26;
      int var28 = 2131558435;
      EditText var29 = (EditText)this.findViewById(var28);
      this.mImapPathPrefixView = var29;
      int var31 = 2131558417;
      Button var32 = (Button)this.findViewById(var31);
      this.mNextButton = var32;
      Button var33 = this.mNextButton;
      var33.setOnClickListener(this);
      SpinnerOption[] var35 = new SpinnerOption[5];
      Integer var36 = Integer.valueOf(0);
      int var38 = 2131165348;
      String var39 = this.getString(var38);
      SpinnerOption var40 = new SpinnerOption(var36, var39);
      var35[0] = var40;
      Integer var41 = Integer.valueOf(1);
      int var43 = 2131165350;
      String var44 = this.getString(var43);
      SpinnerOption var45 = new SpinnerOption(var41, var44);
      var35[1] = var45;
      Integer var46 = Integer.valueOf(2);
      int var48 = 2131165349;
      String var49 = this.getString(var48);
      SpinnerOption var50 = new SpinnerOption(var46, var49);
      var35[2] = var50;
      Integer var51 = Integer.valueOf(3);
      int var53 = 2131165352;
      String var54 = this.getString(var53);
      SpinnerOption var55 = new SpinnerOption(var51, var54);
      var35[3] = var55;
      Integer var56 = Integer.valueOf(4);
      int var58 = 2131165351;
      String var59 = this.getString(var58);
      SpinnerOption var60 = new SpinnerOption(var56, var59);
      var35[4] = var60;
      SpinnerOption[] var61 = new SpinnerOption[2];
      Integer var62 = Integer.valueOf(0);
      int var64 = 2131165354;
      String var65 = this.getString(var64);
      SpinnerOption var66 = new SpinnerOption(var62, var65);
      var61[0] = var66;
      Integer var67 = Integer.valueOf(2);
      int var69 = 2131165355;
      String var70 = this.getString(var69);
      SpinnerOption var71 = new SpinnerOption(var67, var70);
      var61[1] = var71;
      ArrayAdapter var72 = new ArrayAdapter;
      int var75 = 17367048;
      var72.<init>(this, var75, var35);
      int var78 = 2130903075;
      var72.setDropDownViewResource(var78);
      Spinner var79 = this.mSecurityTypeView;
      var79.setAdapter(var72);
      ArrayAdapter var81 = new ArrayAdapter;
      int var84 = 17367048;
      var81.<init>(this, var84, var61);
      int var87 = 2130903075;
      var81.setDropDownViewResource(var87);
      Spinner var88 = this.mDeletePolicyView;
      var88.setAdapter(var81);
      Spinner var90 = this.mSecurityTypeView;
      AccountSetupIncoming.1 var91 = new AccountSetupIncoming.1();
      var90.setOnItemSelectedListener(var91);
      AccountSetupIncoming.2 var94 = new AccountSetupIncoming.2();
      EditText var97 = this.mUsernameView;
      var97.addTextChangedListener(var94);
      EditText var99 = this.mPasswordView;
      var99.addTextChangedListener(var94);
      EditText var101 = this.mServerView;
      var101.addTextChangedListener(var94);
      EditText var103 = this.mPortView;
      var103.addTextChangedListener(var94);
      EditText var105 = this.mPortView;
      DigitsKeyListener var106 = DigitsKeyListener.getInstance("0123456789");
      var105.setKeyListener(var106);
      EmailContent.Account var107 = (EmailContent.Account)this.getIntent().getParcelableExtra("account");
      this.mAccount = var107;
      boolean var108 = this.getIntent().getBooleanExtra("makeDefault", (boolean)0);
      this.mMakeDefault = var108;
      boolean var109 = this.getIntent().getBooleanExtra("exitAfterSetup", (boolean)0);
      this.mExitAfterSetup = var109;
      boolean var110 = this.getIntent().getBooleanExtra("outgoingAuth", (boolean)1);
      this.mOutgoingAuth = var110;
      if(var1 != null) {
         String var112 = "account";
         if(var1.containsKey(var112)) {
            String var114 = "account";
            EmailContent.Account var115 = (EmailContent.Account)var1.getParcelable(var114);
            this.mAccount = var115;
         }
      }

      try {
         URI var116 = new URI;
         EmailContent.Account var117 = this.mAccount;
         String var119 = var117.getStoreUri(this);
         var116.<init>(var119);
         String var122 = null;
         String var123 = null;
         if(var116.getUserInfo() != null) {
            String[] var124 = var116.getUserInfo().split(":", 2);
            var122 = var124[0];
            int var125 = var124.length;
            byte var126 = 1;
            if(var125 > var126) {
               var123 = var124[1];
            }
         }

         if(var122 != null) {
            if(this.getIntent().getBooleanExtra("customizeFlg", (boolean)0)) {
               this.mUsernameView.setText("");
            } else {
               EditText var143 = this.mUsernameView;
               var143.setText(var122);
            }
         }

         if(var123 != null) {
            EditText var127 = this.mPasswordView;
            var127.setText(var123);
         }

         if(var116.getScheme().startsWith("pop3")) {
            int var130 = 2131165344;
            var14.setText(var130);
            int[] var131 = POP_PORTS;
            this.mAccountPorts = var131;
            String[] var132 = POP_SCHEMES;
            this.mAccountSchemes = var132;
            int var134 = 2131558434;
            this.findViewById(var134).setVisibility(8);
         } else {
            if(!var116.getScheme().startsWith("imap")) {
               StringBuilder var157 = (new StringBuilder()).append("Unknown account type: ");
               EmailContent.Account var158 = this.mAccount;
               String var160 = var158.getStoreUri(this);
               String var161 = var157.append(var160).toString();
               throw new Error(var161);
            }

            int var150 = 2131165345;
            var14.setText(var150);
            int[] var151 = IMAP_PORTS;
            this.mAccountPorts = var151;
            String[] var152 = IMAP_SCHEMES;
            this.mAccountSchemes = var152;
            int var154 = 2131558432;
            this.findViewById(var154).setVisibility(8);
            this.mDeletePolicyView.setVisibility(8);
            if(var116.getPath() != null && var116.getPath().length() > 0) {
               EditText var155 = this.mImapPathPrefixView;
               String var156 = var116.getPath().substring(1);
               var155.setText(var156);
            }
         }

         int var135 = 0;

         while(true) {
            int var136 = this.mAccountSchemes.length;
            if(var135 >= var136) {
               Spinner var162 = this.mDeletePolicyView;
               Integer var163 = Integer.valueOf(this.mAccount.getDeletePolicy());
               SpinnerOption.setSpinnerOptionValue(var162, var163);
               if(var116.getHost() != null) {
                  EditText var164 = this.mServerView;
                  String var165 = var116.getHost();
                  var164.setText(var165);
               }

               int var166 = var116.getPort();
               char var167 = '\uffff';
               if(var166 != var167) {
                  EditText var168 = this.mPortView;
                  String var169 = Integer.toString(var116.getPort());
                  var168.setText(var169);
               } else {
                  this.updatePortFromSecurityType();
               }
               break;
            }

            String var139 = this.mAccountSchemes[var135];
            String var140 = var116.getScheme();
            if(var139.equals(var140)) {
               Spinner var141 = this.mSecurityTypeView;
               Integer var142 = Integer.valueOf(var135);
               SpinnerOption.setSpinnerOptionValue(var141, var142);
            }

            ++var135;
         }
      } catch (URISyntaxException var170) {
         Error var146 = new Error(var170);
         throw var146;
      }

      this.validateFields();
   }

   public Dialog onCreateDialog(int var1) {
      AlertDialog var2;
      switch(var1) {
      case 1:
         Builder var3 = (new Builder(this)).setIcon(17301543).setTitle(2131165324);
         Object[] var4 = new Object[1];
         String var5 = this.mDuplicateAccountName;
         var4[0] = var5;
         String var6 = this.getString(2131165325, var4);
         Builder var7 = var3.setMessage(var6);
         AccountSetupIncoming.3 var8 = new AccountSetupIncoming.3();
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

   class 4 extends AsyncTask<Void, Void, String> {

      private URI uri = null;


      4() {}

      protected String doInBackground(Void ... var1) {
         EmailContent.Account var2 = AccountSetupIncoming.this.mAccount;
         AccountSetupIncoming var3 = AccountSetupIncoming.this;
         String var4 = this.uri.toString();
         var2.setStoreUri(var3, var4);
         AccountSetupIncoming var5 = AccountSetupIncoming.this;
         AccountSetupIncoming var6 = AccountSetupIncoming.this;
         long var7 = AccountSetupIncoming.this.mAccount.mId;
         String var9 = this.uri.getHost();
         String var10 = AccountSetupIncoming.this.mCacheLoginCredential;
         String var11 = Utility.findDuplicateAccount(var6, var7, var9, var10);
         var5.mDuplicateAccountName = var11;
         return AccountSetupIncoming.this.mDuplicateAccountName;
      }

      protected void onPostExecute(String var1) {
         if(var1 != null) {
            AccountSetupIncoming.this.showDialog(1);
         } else {
            EmailContent.Account var2 = AccountSetupIncoming.this.mAccount;
            int var3 = ((Integer)((SpinnerOption)AccountSetupIncoming.this.mDeletePolicyView.getSelectedItem()).value).intValue();
            var2.setDeletePolicy(var3);
            AccountSetupIncoming var4 = AccountSetupIncoming.this;
            EmailContent.Account var5 = AccountSetupIncoming.this.mAccount;
            AccountSetupCheckSettings.actionValidateSettings(var4, var5, (boolean)1, (boolean)0);
         }
      }

      protected void onPreExecute() {
         try {
            URI var1 = AccountSetupIncoming.this.getUri();
            this.uri = var1;
         } catch (URISyntaxException var3) {
            throw new Error(var3);
         }
      }
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
         AccountSetupIncoming.this.updatePortFromSecurityType();
      }

      public void onNothingSelected(AdapterView<?> var1) {}
   }
}
