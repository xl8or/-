package com.android.email.activity.setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.email.AccountBackupRestore;
import com.android.email.Controller;
import com.android.email.Email;
import com.android.email.EmailAddressValidator;
import com.android.email.Utility;
import com.android.email.VendorPolicyLoader;
import com.android.email.activity.Debug;
import com.android.email.activity.MessageList;
import com.android.email.activity.setup.AccountSettingsUtils;
import com.android.email.activity.setup.AccountSetupAccountType;
import com.android.email.activity.setup.AccountSetupCheckSettings;
import com.android.email.activity.setup.AccountSetupIncoming;
import com.android.email.activity.setup.AccountSetupNames;
import com.android.email.provider.EmailContent;
import com.sonyericsson.email.ui.BrandedAccountsList;
import com.sonyericsson.email.utils.AccountProvider;
import com.sonyericsson.email.utils.customization.AccountData;
import com.sonyericsson.email.utils.customization.CustomizationFactory;
import java.net.URI;
import java.net.URISyntaxException;

public class AccountSetupBasics extends Activity implements OnClickListener, TextWatcher {

   private static final String ACTION_RETURN_TO_CALLER = "com.android.email.AccountSetupBasics.return";
   private static final String ACTION_START_AT_MESSAGE_LIST = "com.android.email.AccountSetupBasics.messageList";
   private static final int DIALOG_DUPLICATE_ACCOUNT = 2;
   private static final int DIALOG_NOTE = 1;
   private static final boolean ENTER_DEBUG_SCREEN = true;
   private static final String EXTRA_ACCOUNT = "com.android.email.AccountSetupBasics.account";
   public static final String EXTRA_EAS_FLOW = "com.android.email.extra.eas_flow";
   private static final String EXTRA_EXIT_AFTER_SETUP = "com.android.email.AccountSetupBasics.exitAfterSetup";
   private static final String EXTRA_IS_PRECONFIGURED_ACCOUNT = "com.android.email.AccountSetupBasics.isPreconfiguredAccount";
   private static final String EXTRA_LOGO = "com.android.email.AccountSetupBasics.logo";
   private static final String EXTRA_PASSWORD = "com.android.email.AccountSetupBasics.password";
   private static final String EXTRA_USERNAME = "com.android.email.AccountSetupBasics.username";
   private static final String STATE_KEY_PROVIDER = "com.android.email.AccountSetupBasics.provider";
   private EmailContent.Account mAccount;
   private ImageView mBrandedLogo;
   private CheckBox mDefaultView;
   private String mDuplicateAccountName;
   private boolean mEasFlowMode;
   private EmailAddressValidator mEmailValidator;
   private EditText mEmailView;
   private boolean mExitAfterSetup;
   private boolean mIsPreconfiguredAccount;
   private Button mManualSetupButton;
   private Button mNextButton;
   private EditText mPasswordView;
   private String mPreConfiguredEmailAddress;
   private AccountSettingsUtils.Provider mProvider;


   public AccountSetupBasics() {
      EmailAddressValidator var1 = new EmailAddressValidator();
      this.mEmailValidator = var1;
   }

   public static void actionAccountCreateFinished(Activity var0, long var1, boolean var3) {
      Intent var4 = new Intent(var0, AccountSetupBasics.class);
      if(var3) {
         Intent var5 = var4.putExtra("com.android.email.AccountSetupBasics.return", (boolean)1);
      }

      var4.putExtra("com.android.email.AccountSetupBasics.messageList", var1);
      Intent var7 = var4.setFlags(67108864);
      var0.startActivity(var4);
   }

   public static void actionAccountCreateFinishedEas(Activity var0, long var1, boolean var3) {
      Intent var4 = new Intent(var0, AccountSetupBasics.class);
      if(var3) {
         Intent var5 = var4.putExtra("com.android.email.AccountSetupBasics.return", (boolean)1);
      }

      var4.putExtra("com.android.email.AccountSetupBasics.messageList", var1);
      Intent var7 = var4.setFlags(67108864);
      Intent var8 = var4.putExtra("com.android.email.extra.eas_flow", (boolean)1);
      var0.startActivity(var4);
   }

   public static void actionNewAccount(Activity var0, boolean var1) {
      Intent var2 = new Intent(var0, AccountSetupBasics.class);
      var2.putExtra("com.android.email.AccountSetupBasics.exitAfterSetup", var1);
      var0.startActivity(var2);
   }

   public static void actionNewAccountWithCredentials(Activity var0, Bitmap var1, String var2, String var3, boolean var4, boolean var5, boolean var6) {
      Intent var7 = new Intent(var0, AccountSetupBasics.class);
      var7.putExtra("com.android.email.AccountSetupBasics.logo", var1);
      var7.putExtra("com.android.email.AccountSetupBasics.username", var2);
      var7.putExtra("com.android.email.AccountSetupBasics.password", var3);
      var7.putExtra("com.android.email.extra.eas_flow", var4);
      var7.putExtra("com.android.email.AccountSetupBasics.exitAfterSetup", var5);
      var7.putExtra("com.android.email.AccountSetupBasics.isPreconfiguredAccount", var6);
      var0.startActivity(var7);
   }

   public static Intent actionSetupExchangeIntent(Context var0, Bitmap var1, boolean var2) {
      Intent var3 = new Intent(var0, AccountSetupBasics.class);
      var3.putExtra("com.android.email.AccountSetupBasics.logo", var1);
      Intent var5 = var3.putExtra("com.android.email.extra.eas_flow", (boolean)1);
      var3.putExtra("com.android.email.AccountSetupBasics.exitAfterSetup", var2);
      return var3;
   }

   private void finishAutoSetup() {
      String var1 = this.mEmailView.getText().toString().trim();
      String var2 = this.mPasswordView.getText().toString().trim();
      String var4 = "@";
      String[] var5 = var1.split(var4);
      String var6 = var5[0];
      String var7;
      if(var5.length > 1) {
         var7 = var5[1];
      } else {
         var7 = "";
      }

      boolean var8 = false;
      boolean var9 = false;

      label46: {
         String var19;
         URI var30;
         try {
            String var10 = this.mProvider.incomingUsernameTemplate;
            String var11 = "\\$email";
            String var13 = var10.replaceAll(var11, var1);
            String var14 = "\\$user";
            String var16 = var13.replaceAll(var14, var6);
            String var17 = "\\$domain";
            var19 = var16.replaceAll(var17, var7);
            URI var20 = this.mProvider.incomingUriTemplate;
            String var21 = var20.getScheme();
            StringBuilder var22 = new StringBuilder();
            StringBuilder var24 = var22.append(var19).append(":");
            String var26 = var24.append(var2).toString();
            String var27 = var20.getHost();
            int var28 = var20.getPort();
            String var29 = var20.getPath();
            var30 = new URI(var21, var26, var27, var28, var29, (String)null, (String)null);
         } catch (URISyntaxException var99) {
            break label46;
         }

         URI var51;
         try {
            String var31 = this.mProvider.outgoingUsernameTemplate;
            String var32 = "\\$email";
            String var34 = var31.replaceAll(var32, var1);
            String var35 = "\\$user";
            String var37 = var34.replaceAll(var35, var6);
            String var38 = "\\$domain";
            String var40 = var37.replaceAll(var38, var7);
            URI var41 = this.mProvider.outgoingUriTemplate;
            String var42 = var41.getScheme();
            StringBuilder var43 = new StringBuilder();
            StringBuilder var45 = var43.append(var40).append(":");
            String var47 = var45.append(var2).toString();
            String var48 = var41.getHost();
            int var49 = var41.getPort();
            String var50 = var41.getPath();
            var51 = new URI(var42, var47, var48, var49, var50, (String)null, (String)null);
         } catch (URISyntaxException var98) {
            break label46;
         }

         long var52 = 65535L;

         try {
            String var54 = var30.getHost();
            String var60 = Utility.findDuplicateAccount(this, var52, var54, var19);
            this.mDuplicateAccountName = var60;
            if(this.mDuplicateAccountName != null) {
               byte var62 = 2;
               this.showDialog(var62);
               return;
            }
         } catch (URISyntaxException var97) {
            break label46;
         }

         EmailContent.Account var68 = new EmailContent.Account();
         this.mAccount = var68;
         EmailContent.Account var69 = this.mAccount;
         String var70 = this.getOwnerName();
         var69.setSenderName(var70);
         EmailContent.Account var71 = this.mAccount;
         var71.setEmailAddress(var1);
         EmailContent.Account var73 = this.mAccount;
         String var74 = this.getSignature();
         var73.setSignature(var74);
         EmailContent.Account var75 = this.mAccount;
         String var76 = var30.toString();
         var75.setStoreUri(this, var76);
         EmailContent.Account var80 = this.mAccount;
         String var81 = var51.toString();
         var80.setSenderUri(this, var81);
         if(var30.toString().startsWith("imap")) {
            this.mAccount.setDeletePolicy(2);
         }

         AccountData var85 = AccountProvider.getSettingsFromDefaultUX(this.getApplicationContext());
         int var86 = var85.getCheckIntervalSeconds();
         int[] var87 = var85.getCheckIntervalList();
         int var88 = AccountSettingsUtils.getCheckIntervalMinutes(var86, var87);
         this.mAccount.setSyncInterval(var88);
         EmailContent.Account var89 = this.mAccount;
         byte var92 = 1;
         byte var93 = 1;
         AccountSetupCheckSettings.actionValidateSettings(this, var89, (boolean)var92, (boolean)var93);
         return;
      }

      byte var67 = 1;
      this.onManualSetup((boolean)var67);
   }

   private String getOwnerName() {
      String var1 = null;
      if(var1 == null || null.length() == 0) {
         long var2 = EmailContent.Account.getDefaultAccountId(this);
         if(var2 != 65535L) {
            EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(this, var2);
            if(var4 != null) {
               var1 = var4.getSenderName();
            }
         }
      }

      return var1;
   }

   private void onManualSetup(boolean var1) {
      String var2 = this.mEmailView.getText().toString().trim();
      String var3 = this.mPasswordView.getText().toString().trim();
      String[] var4 = var2.split("@");
      String var5 = var4[0].trim();
      String var6;
      if(var4.length > 1) {
         var6 = var4[1].trim();
      } else {
         var6 = "";
      }

      if("d@d.d".equals(var2)) {
         String var7 = "debug";
         if(var7.equals(var3)) {
            this.mEmailView.setText("");
            this.mPasswordView.setText("");
            Intent var9 = new Intent;
            Class var12 = Debug.class;
            var9.<init>(this, var12);
            this.startActivity(var9);
            return;
         }
      }

      EmailContent.Account var15 = new EmailContent.Account();
      this.mAccount = var15;
      EmailContent.Account var16 = this.mAccount;
      String var17 = this.getOwnerName();
      var16.setSenderName(var17);
      this.mAccount.setEmailAddress(var2);
      EmailContent.Account var18 = this.mAccount;
      String var19 = this.getSignature();
      var18.setSignature(var19);

      try {
         StringBuilder var20 = new StringBuilder();
         StringBuilder var22 = var20.append(var5).append(":");
         String var24 = var22.append(var3).toString();
         URI var25 = new URI("placeholder", var24, var6, -1, (String)null, (String)null, (String)null);
         EmailContent.Account var26 = this.mAccount;
         String var27 = var25.toString();
         var26.setStoreUri(this, var27);
         EmailContent.Account var31 = this.mAccount;
         String var32 = var25.toString();
         var31.setSenderUri(this, var32);
      } catch (URISyntaxException var53) {
         AccountSetupBasics.6 var47 = new AccountSetupBasics.6();
         this.runOnUiThread(var47);
         Object var52 = null;
         this.mAccount = (EmailContent.Account)var52;
         return;
      }

      AccountData var36 = AccountProvider.getSettingsFromDefaultUX(this.getApplicationContext());
      int var37 = var36.getCheckIntervalSeconds();
      int[] var38 = var36.getCheckIntervalList();
      int var39 = AccountSettingsUtils.getCheckIntervalMinutes(var37, var38);
      this.mAccount.setSyncInterval(var39);
      EmailContent.Account var40 = this.mAccount;
      boolean var41 = this.mDefaultView.isChecked();
      boolean var42 = this.mEasFlowMode;
      boolean var43 = this.mExitAfterSetup;
      AccountSetupAccountType.actionSelectAccountType(this, var40, var41, var42, var1, var43);
   }

   private void onNextSemc() {
      if(!this.mEasFlowMode) {
         String var1 = this.mEmailView.getText().toString().trim();
         String var2 = this.mPasswordView.getText().toString().trim();
         String[] var3 = var1.split("@");
         String var4;
         if(var3.length > 1) {
            var4 = var3[1];
         } else {
            var4 = "";
         }

         if(var4.equalsIgnoreCase("mopera.net")) {
            AccountSetupCheckSettings.actionAutoDiscoverSemcWithoutValidating(this, var1, var2);
         } else {
            if(this.mIsPreconfiguredAccount && var1.compareToIgnoreCase("telstra") == 0) {
               var1 = this.mPreConfiguredEmailAddress;
            }

            AccountSetupCheckSettings.actionAutoDiscoverSemc(this, var1, var2);
         }
      } else {
         (new AccountSetupBasics.4()).start();
      }
   }

   private void validateFields() {
      byte var3;
      label32: {
         if(Utility.requiredFieldValid((TextView)this.mEmailView) && Utility.requiredFieldValid((TextView)this.mPasswordView)) {
            EmailAddressValidator var1 = this.mEmailValidator;
            String var2 = this.mEmailView.getText().toString().trim();
            if(var1.isValid(var2) || this.mIsPreconfiguredAccount) {
               var3 = 1;
               break label32;
            }
         }

         var3 = 0;
      }

      this.mNextButton.setEnabled((boolean)var3);
      Button var4 = this.mManualSetupButton;
      byte var5;
      if(!this.mIsPreconfiguredAccount && var3 != 0) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      var4.setEnabled((boolean)var5);
      Button var6 = this.mNextButton;
      short var7;
      if(this.mNextButton.isEnabled()) {
         var7 = 255;
      } else {
         var7 = 128;
      }

      Utility.setCompoundDrawablesAlpha(var6, var7);
   }

   public void afterTextChanged(Editable var1) {
      this.validateFields();
   }

   public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   String getCDFSignature(CustomizationFactory var1) {
      AccountData var2 = var1.getCustomization(this).getDefaultSettings();
      String var3 = "";
      if(var2 != null) {
         var3 = var2.getSignature();
      }

      return var3;
   }

   String getDefaultSignature(CustomizationFactory var1) {
      AccountData var2 = var1.getDefaultCustomization(this).getDefaultSettings();
      String var3 = "";
      if(var2 != null) {
         var3 = var2.getSignature();
      }

      return var3;
   }

   public String getSignature() {
      CustomizationFactory var1 = CustomizationFactory.getInstance();
      String var2 = this.getCDFSignature(var1);
      if(var2 != null && var2.trim().length() > 0) {
         Object[] var3 = new Object[]{var2};
         var2 = this.getString(2131165196, var3);
      } else {
         Object[] var4 = new Object[1];
         String var5 = this.getDefaultSignature(var1);
         var4[0] = var5;
         var2 = this.getString(2131165195, var4);
      }

      return var2;
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if(var2 == -1) {
         if(var3.hasExtra("account")) {
            EmailContent.Account var4 = (EmailContent.Account)var3.getParcelableExtra("account");
            this.mAccount = var4;
         }

         String var5 = this.mAccount.getEmailAddress();
         boolean var6 = this.mDefaultView.isChecked();
         if(!this.mIsPreconfiguredAccount) {
            this.mAccount.setDisplayName(var5);
         }

         this.mAccount.setDefaultAccount(var6);
         EmailContent.Account var7 = this.mAccount;
         String var8 = this.getSignature();
         var7.setSignature(var8);
         if(this.mAccount.mHostAuthRecv.mProtocol.startsWith("imap")) {
            this.mAccount.setDeletePolicy(2);
         }

         String var9 = this.mAccount.mHostAuthRecv.mAddress;
         String var10 = this.mAccount.mHostAuthRecv.mLogin;
         String var11 = Utility.findDuplicateAccount(this, 65535L, var9, var10);
         this.mDuplicateAccountName = var11;
         if(this.mDuplicateAccountName != null) {
            this.showDialog(2);
            return;
         }

         AccountData var12 = AccountProvider.getSettingsFromDefaultUX(this.getApplicationContext());
         int var13 = var12.getCheckIntervalSeconds();
         int[] var14 = var12.getCheckIntervalList();
         int var15 = AccountSettingsUtils.getCheckIntervalMinutes(var13, var14);
         this.mAccount.setSyncInterval(var15);
         Uri var16 = this.mAccount.save(this);
         AccountBackupRestore.backupAccounts(this);
         boolean var17 = Email.setServicesEnabled(this);
         long var18 = this.mAccount.mId;
         boolean var20 = this.mExitAfterSetup;
         boolean var21 = this.mIsPreconfiguredAccount;
         AccountSetupNames.actionSetNames(this, var18, (boolean)0, var20, var21);
         this.finish();
      }

      if(var2 == 4) {
         if(var3.hasExtra("account")) {
            EmailContent.Account var22 = (EmailContent.Account)var3.getParcelableExtra("account");
            this.mAccount = var22;
         }

         byte var23;
         if((this.mAccount.getFlags() & 4) != 0) {
            var23 = 1;
         } else {
            var23 = 0;
         }

         EmailContent.Account var24 = this.mAccount;
         boolean var25 = this.mDefaultView.isChecked();
         boolean var26 = this.mExitAfterSetup;
         AccountSetupIncoming.actionIncomingSettings(this, var24, var25, var26, (boolean)1, (boolean)var23);
         this.finish();
      }

      if(var2 == 3) {
         AsyncTask var27 = Utility.runAsync(new AccountSetupBasics.5());
      }
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131558417:
         this.onNextSemc();
         return;
      case 2131558418:
      default:
         return;
      case 2131558419:
         (new AccountSetupBasics.7()).start();
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      Intent var2 = this.getIntent();
      long var7;
      if(var2.getBooleanExtra("com.android.email.AccountSetupBasics.return", (boolean)0)) {
         if(BrandedAccountsList.checkBrandAccountSettings(this) == 1) {
            BrandedAccountsList.actionExit(this);
         }

         String var4 = "com.android.email.AccountSetupBasics.messageList";
         long var5 = 65535L;
         var7 = var2.getLongExtra(var4, var5);
         if(var7 >= 0L) {
            int var10 = 2131165189;
            String var11 = this.getString(var10);
            byte var14 = 1;
            Toast.makeText(this, var11, var14).show();
            if(!this.getIntent().getBooleanExtra("com.android.email.extra.eas_flow", (boolean)0)) {
               Controller var15 = Controller.getInstance(this.getApplication());
               long var16 = var15.findOrCreateMailboxOfType(var7, 0);
               var15.updateMailbox(var7, var16, (Controller.Result)null);
            } else {
               Controller.getInstance(this.getApplication()).updateMailboxList(var7, (Controller.Result)null);
            }
         }

         this.finish();
      } else {
         String var19 = "com.android.email.AccountSetupBasics.messageList";
         long var20 = 65535L;
         var7 = var2.getLongExtra(var19, var20);
         if(var7 >= 0L) {
            if(BrandedAccountsList.checkBrandAccountSettings(this) == 1) {
               BrandedAccountsList.actionExit(this);
            }

            byte var25 = 0;
            MessageList.actionHandleAccount(this, var7, var25);
            this.finish();
         } else {
            int var27 = 2130903043;
            this.setContentView(var27);
            int var29 = 2131558415;
            EditText var30 = (EditText)this.findViewById(var29);
            this.mEmailView = var30;
            int var32 = 2131558416;
            EditText var33 = (EditText)this.findViewById(var32);
            this.mPasswordView = var33;
            int var35 = 2131558418;
            CheckBox var36 = (CheckBox)this.findViewById(var35);
            this.mDefaultView = var36;
            int var38 = 2131558417;
            Button var39 = (Button)this.findViewById(var38);
            this.mNextButton = var39;
            int var41 = 2131558419;
            Button var42 = (Button)this.findViewById(var41);
            this.mManualSetupButton = var42;
            int var44 = 2131558414;
            ImageView var45 = (ImageView)this.findViewById(var44);
            this.mBrandedLogo = var45;
            Bitmap var46 = (Bitmap)var2.getParcelableExtra("com.android.email.AccountSetupBasics.logo");
            if(var46 != null) {
               this.mBrandedLogo.setImageBitmap(var46);
            } else {
               int var95 = 2131558413;
               this.findViewById(var95).setVisibility(0);
               this.mBrandedLogo.setVisibility(8);
            }

            Button var47 = this.mNextButton;
            var47.setOnClickListener(this);
            Button var49 = this.mManualSetupButton;
            var49.setOnClickListener(this);
            EditText var51 = this.mEmailView;
            var51.addTextChangedListener(this);
            EditText var53 = this.mPasswordView;
            var53.addTextChangedListener(this);
            AccountSetupBasics.1 var55 = new AccountSetupBasics.1();
            Void[] var58 = new Void[0];
            var55.execute(var58);
            boolean var60 = var2.getBooleanExtra("com.android.email.extra.eas_flow", (boolean)0);
            this.mEasFlowMode = var60;
            if(this.mEasFlowMode) {
               int var62 = 2131558413;
               TextView var63 = (TextView)this.findViewById(var62);
               boolean var64 = VendorPolicyLoader.getInstance(this).useAlternateExchangeStrings();
               int var65;
               if(var64) {
                  var65 = 2131165315;
               } else {
                  var65 = 2131165314;
               }

               this.setTitle(var65);
               int var68;
               if(var64) {
                  var68 = 2131165318;
               } else {
                  var68 = 2131165317;
               }

               var63.setText(var68);
            }

            boolean var71 = var2.getBooleanExtra("com.android.email.AccountSetupBasics.exitAfterSetup", (boolean)0);
            this.mExitAfterSetup = var71;
            boolean var72 = var2.getBooleanExtra("com.android.email.AccountSetupBasics.isPreconfiguredAccount", (boolean)0);
            this.mIsPreconfiguredAccount = var72;
            if(this.mIsPreconfiguredAccount) {
               this.mEmailView.setFocusableInTouchMode((boolean)0);
               this.mEmailView.setEnabled((boolean)0);
               this.mPasswordView.setFocusableInTouchMode((boolean)0);
               this.mPasswordView.setEnabled((boolean)0);
            }

            if(var2.hasExtra("com.android.email.AccountSetupBasics.username")) {
               EditText var73 = this.mEmailView;
               String var74 = var2.getStringExtra("com.android.email.AccountSetupBasics.username");
               var73.setText(var74);
               if(this.mIsPreconfiguredAccount) {
                  String var75 = var2.getStringExtra("com.android.email.AccountSetupBasics.username");
                  this.mPreConfiguredEmailAddress = var75;
                  String var76 = var2.getStringExtra("com.android.email.AccountSetupBasics.username");
                  String var77 = "@";
                  String[] var78 = var76.split(var77);
                  String var79;
                  if(var78.length > 1) {
                     var79 = var78[1].trim();
                  } else {
                     var79 = "";
                  }

                  String[] var80 = var79.split("\\.");
                  String var81;
                  if(var80.length > 1) {
                     var81 = var80[1].trim();
                  } else {
                     var81 = "";
                  }

                  if(var81.compareToIgnoreCase("telstra") == 0) {
                     this.mEmailView.setText("Telstra");
                  }
               }
            }

            if(var2.hasExtra("com.android.email.AccountSetupBasics.password")) {
               EditText var82 = this.mPasswordView;
               String var83 = var2.getStringExtra("com.android.email.AccountSetupBasics.password");
               var82.setText(var83);
            }

            if(var1 != null) {
               String var85 = "com.android.email.AccountSetupBasics.account";
               if(var1.containsKey(var85)) {
                  String var87 = "com.android.email.AccountSetupBasics.account";
                  EmailContent.Account var88 = (EmailContent.Account)var1.getParcelable(var87);
                  this.mAccount = var88;
               }
            }

            if(var1 != null) {
               String var90 = "com.android.email.AccountSetupBasics.provider";
               if(var1.containsKey(var90)) {
                  String var92 = "com.android.email.AccountSetupBasics.provider";
                  AccountSettingsUtils.Provider var93 = (AccountSettingsUtils.Provider)var1.getSerializable(var92);
                  this.mProvider = var93;
               }
            }
         }
      }
   }

   public Dialog onCreateDialog(int var1) {
      AlertDialog var9;
      if(var1 == 1) {
         if(this.mProvider != null && this.mProvider.note != null) {
            Builder var2 = (new Builder(this)).setIcon(17301543).setTitle(17039380);
            String var3 = this.mProvider.note;
            Builder var4 = var2.setMessage(var3);
            String var5 = this.getString(2131165215);
            AccountSetupBasics.2 var6 = new AccountSetupBasics.2();
            Builder var7 = var4.setPositiveButton(var5, var6);
            String var8 = this.getString(2131165216);
            var9 = var7.setNegativeButton(var8, (android.content.DialogInterface.OnClickListener)null).create();
            return var9;
         }
      } else if(var1 == 2) {
         Builder var10 = (new Builder(this)).setIcon(17301543).setTitle(2131165324);
         Object[] var11 = new Object[1];
         String var12 = this.mDuplicateAccountName;
         var11[0] = var12;
         String var13 = this.getString(2131165325, var11);
         Builder var14 = var10.setMessage(var13);
         AccountSetupBasics.3 var15 = new AccountSetupBasics.3();
         var9 = var14.setPositiveButton(2131165215, var15).create();
         return var9;
      }

      var9 = null;
      return var9;
   }

   public void onPrepareDialog(int var1, Dialog var2) {
      switch(var1) {
      case 1:
         if(this.mProvider == null) {
            return;
         } else {
            if(this.mProvider.note == null) {
               return;
            }

            AlertDialog var3 = (AlertDialog)var2;
            String var4 = this.mProvider.note;
            var3.setMessage(var4);
            return;
         }
      case 2:
         if(this.mDuplicateAccountName == null) {
            return;
         }

         AlertDialog var5 = (AlertDialog)var2;
         Object[] var6 = new Object[1];
         String var7 = this.mDuplicateAccountName;
         var6[0] = var7;
         String var8 = this.getString(2131165325, var6);
         var5.setMessage(var8);
         return;
      default:
      }
   }

   public void onResume() {
      super.onResume();
      this.validateFields();
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      EmailContent.Account var2 = this.mAccount;
      var1.putParcelable("com.android.email.AccountSetupBasics.account", var2);
      if(this.mProvider != null) {
         AccountSettingsUtils.Provider var3 = this.mProvider;
         var1.putSerializable("com.android.email.AccountSetupBasics.provider", var3);
      }
   }

   public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   class 1 extends AsyncTask<Void, Void, Boolean> {

      1() {}

      protected Boolean doInBackground(Void ... var1) {
         AccountSetupBasics var2 = AccountSetupBasics.this;
         Uri var3 = EmailContent.Account.CONTENT_URI;
         byte var4;
         if(EmailContent.count(var2, var3, (String)null, (String[])null) > 0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         return Boolean.valueOf((boolean)var4);
      }

      protected void onPostExecute(Boolean var1) {
         if(var1.booleanValue()) {
            AccountSetupBasics.this.mDefaultView.setVisibility(0);
         }
      }
   }

   class 4 extends Thread {

      4() {}

      public void run() {
         AccountSetupBasics.this.onManualSetup((boolean)1);
      }
   }

   class 5 implements Runnable {

      5() {}

      public void run() {
         AccountSetupBasics.this.onManualSetup((boolean)0);
      }
   }

   class 2 implements android.content.DialogInterface.OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         AccountSetupBasics.this.finishAutoSetup();
      }
   }

   class 3 implements android.content.DialogInterface.OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         AccountSetupBasics.this.dismissDialog(2);
      }
   }

   class 6 implements Runnable {

      6() {}

      public void run() {
         Toast.makeText(AccountSetupBasics.this, 2131165323, 1).show();
      }
   }

   class 7 extends Thread {

      7() {}

      public void run() {
         AccountSetupBasics.this.onManualSetup((boolean)0);
      }
   }
}
