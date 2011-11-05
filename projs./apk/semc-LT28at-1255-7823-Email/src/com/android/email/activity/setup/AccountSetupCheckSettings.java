package com.android.email.activity.setup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.email.mail.AuthenticationFailedException;
import com.android.email.provider.EmailContent;

public class AccountSetupCheckSettings extends Activity implements OnClickListener {

   private static final boolean DBG_FORCE_RESULT_OK = false;
   private static final boolean DBG_SKIP_CHECK_ERR = false;
   private static final boolean DBG_SKIP_CHECK_OK = false;
   public static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_AUTO_DISCOVER = "autoDiscover";
   private static final String EXTRA_AUTO_DISCOVER_PASSWORD = "password";
   private static final String EXTRA_AUTO_DISCOVER_SEMC = "autoDiscoverSemc";
   private static final String EXTRA_AUTO_DISCOVER_SEMC_WITHOUT_VALIDATING = "autoDiscoverSemcWithoutValidating";
   private static final String EXTRA_AUTO_DISCOVER_USERNAME = "userName";
   private static final String EXTRA_CHECK_INCOMING = "checkIncoming";
   private static final String EXTRA_CHECK_OUTGOING = "checkOutgoing";
   private static final String EXTRA_ERROR_MSG = "haveErrorMsg";
   private static final String EXTRA_MANU_DISCOVER = "manuDiscover";
   public static final int REQUEST_CODE_AUTO_DISCOVER = 2;
   public static final int REQUEST_CODE_AUTO_DISCOVER_SEMC = 3;
   public static final int REQUEST_CODE_AUTO_DISCOVER_SEMC_WITHOUT_VALIDATING = 4;
   public static final int REQUEST_CODE_VALIDATE = 1;
   public static final int RESULT_AUTO_DISCOVER_AUTH_FAILED = 1;
   public static final int RESULT_AUTO_DISCOVER_SEMC_FAILED = 3;
   public static final int RESULT_AUTO_DISCOVER_SEMC_WITHOUT_VALIDATING = 4;
   public static final int RESULT_SECURITY_REQUIRED_USER_CANCEL = 2;
   private EmailContent.Account mAccount;
   private boolean mAutoDiscover;
   private boolean mCanceled;
   private boolean mCheckIncoming;
   private boolean mCheckOutgoing;
   private boolean mDestroyed;
   private final Handler mHandler;
   private boolean mHaveErrorMsg;
   private TextView mMessageView;
   private ProgressBar mProgressBar;


   public AccountSetupCheckSettings() {
      Handler var1 = new Handler();
      this.mHandler = var1;
   }

   // $FF: synthetic method
   static boolean access$100(AccountSetupCheckSettings var0) {
      return var0.mCanceled;
   }

   // $FF: synthetic method
   static boolean access$200(AccountSetupCheckSettings var0) {
      return var0.mAutoDiscover;
   }

   // $FF: synthetic method
   static EmailContent.Account access$302(AccountSetupCheckSettings var0, EmailContent.Account var1) {
      var0.mAccount = var1;
      return var1;
   }

   // $FF: synthetic method
   static boolean access$400(AccountSetupCheckSettings var0) {
      return var0.mCheckIncoming;
   }

   // $FF: synthetic method
   static boolean access$402(AccountSetupCheckSettings var0, boolean var1) {
      var0.mCheckIncoming = var1;
      return var1;
   }

   // $FF: synthetic method
   static boolean access$500(AccountSetupCheckSettings var0) {
      return var0.mCheckOutgoing;
   }

   // $FF: synthetic method
   static boolean access$502(AccountSetupCheckSettings var0, boolean var1) {
      var0.mCheckOutgoing = var1;
      return var1;
   }

   // $FF: synthetic method
   static void access$600(AccountSetupCheckSettings var0, int var1) {
      var0.setMessage(var1);
   }

   // $FF: synthetic method
   static void access$700(AccountSetupCheckSettings var0, boolean var1, int var2, Object[] var3) {
      var0.showErrorDialog(var1, var2, var3);
   }

   // $FF: synthetic method
   static boolean access$800(AccountSetupCheckSettings var0) {
      return var0.mHaveErrorMsg;
   }

   // $FF: synthetic method
   static void access$900(AccountSetupCheckSettings var0) {
      var0.showSecurityRequiredDialog();
   }

   public static void actionAutoDiscover(Activity var0, EmailContent.Account var1, String var2, String var3) {
      Intent var4 = new Intent(var0, AccountSetupCheckSettings.class);
      var4.putExtra("account", var1);
      Intent var6 = var4.putExtra("autoDiscover", (boolean)1);
      var4.putExtra("userName", var2);
      var4.putExtra("password", var3);
      var0.startActivityForResult(var4, 2);
   }

   public static void actionAutoDiscoverSemc(Activity var0, String var1, String var2) {
      Intent var3 = new Intent(var0, AccountSetupCheckSettings.class);
      Intent var4 = var3.putExtra("autoDiscoverSemc", (boolean)1);
      var3.putExtra("userName", var1);
      var3.putExtra("password", var2);
      var0.startActivityForResult(var3, 3);
   }

   public static void actionAutoDiscoverSemcWithoutValidating(Activity var0, String var1, String var2) {
      Intent var3 = new Intent(var0, AccountSetupCheckSettings.class);
      Intent var4 = var3.putExtra("autoDiscoverSemcWithoutValidating", (boolean)1);
      var3.putExtra("userName", var1);
      var3.putExtra("password", var2);
      var0.startActivityForResult(var3, 4);
   }

   public static void actionValidateSettings(Activity var0, EmailContent.Account var1, boolean var2, boolean var3) {
      Intent var4 = new Intent(var0, AccountSetupCheckSettings.class);
      var4.putExtra("account", var1);
      Intent var6 = var4.putExtra("manuDiscover", (boolean)1);
      var4.putExtra("checkIncoming", var2);
      var4.putExtra("checkOutgoing", var3);
      var0.startActivityForResult(var4, 1);
   }

   public static void actionValidateSettingsHaveErrorMsg(Activity var0, EmailContent.Account var1, boolean var2, boolean var3, boolean var4) {
      Intent var5 = new Intent(var0, AccountSetupCheckSettings.class);
      var5.putExtra("account", var1);
      var5.putExtra("checkIncoming", var2);
      var5.putExtra("checkOutgoing", var3);
      var5.putExtra("haveErrorMsg", var4);
      var0.startActivityForResult(var5, 1);
   }

   private void onCancel() {
      this.mCanceled = (boolean)1;
      this.setMessage(2131165330);
      this.finish();
   }

   private void setMessage(int var1) {
      Handler var2 = this.mHandler;
      AccountSetupCheckSettings.2 var3 = new AccountSetupCheckSettings.2(var1);
      var2.post(var3);
   }

   private void showErrorDialog(boolean var1, int var2, Object ... var3) {
      Handler var4 = this.mHandler;
      AccountSetupCheckSettings.3 var5 = new AccountSetupCheckSettings.3(var2, var3, var1);
      var4.post(var5);
   }

   private void showSecurityRequiredDialog() {
      Handler var1 = this.mHandler;
      AccountSetupCheckSettings.4 var2 = new AccountSetupCheckSettings.4();
      var1.post(var2);
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131558422:
         this.onCancel();
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903044);
      TextView var2 = (TextView)this.findViewById(2131558420);
      this.mMessageView = var2;
      ProgressBar var3 = (ProgressBar)this.findViewById(2131558421);
      this.mProgressBar = var3;
      ((Button)this.findViewById(2131558422)).setOnClickListener(this);
      this.setMessage(2131165327);
      this.mProgressBar.setIndeterminate((boolean)1);
      Intent var4 = this.getIntent();
      EmailContent.Account var5 = (EmailContent.Account)var4.getParcelableExtra("account");
      this.mAccount = var5;
      boolean var6 = var4.getBooleanExtra("checkIncoming", (boolean)0);
      this.mCheckIncoming = var6;
      boolean var7 = var4.getBooleanExtra("checkOutgoing", (boolean)0);
      this.mCheckOutgoing = var7;
      boolean var8 = var4.getBooleanExtra("autoDiscover", (boolean)0);
      this.mAutoDiscover = var8;
      boolean var9 = var4.getBooleanExtra("haveErrorMsg", (boolean)0);
      this.mHaveErrorMsg = var9;
      boolean var10 = var4.getBooleanExtra("autoDiscoverSemc", (boolean)0);
      boolean var11 = var4.getBooleanExtra("autoDiscoverSemcWithoutValidating", (boolean)0);
      (new AccountSetupCheckSettings.1(var4, var10, var11)).start();
   }

   public void onDestroy() {
      super.onDestroy();
      this.mDestroyed = (boolean)1;
      this.mCanceled = (boolean)1;
   }

   class 4 implements Runnable {

      4() {}

      public void run() {
         if(!AccountSetupCheckSettings.this.mDestroyed) {
            AccountSetupCheckSettings.this.mProgressBar.setIndeterminate((boolean)0);
            String var1 = AccountSetupCheckSettings.this.mAccount.mHostAuthRecv.mAddress;
            String[] var2 = new String[]{var1};
            AccountSetupCheckSettings var3 = AccountSetupCheckSettings.this;
            Builder var4 = (new Builder(var3, 2131361797)).setIcon(17301543);
            String var5 = AccountSetupCheckSettings.this.getString(2131165397);
            Builder var6 = var4.setTitle(var5);
            String var7 = AccountSetupCheckSettings.this.getString(2131165398, var2);
            Builder var8 = var6.setMessage(var7).setCancelable((boolean)1);
            String var9 = AccountSetupCheckSettings.this.getString(2131165215);
            AccountSetupCheckSettings.4.2 var10 = new AccountSetupCheckSettings.4.2();
            Builder var11 = var8.setPositiveButton(var9, var10);
            String var12 = AccountSetupCheckSettings.this.getString(2131165216);
            AccountSetupCheckSettings.4.1 var13 = new AccountSetupCheckSettings.4.1();
            AlertDialog var14 = var11.setNegativeButton(var12, var13).show();
         }
      }

      class 1 implements android.content.DialogInterface.OnClickListener {

         1() {}

         public void onClick(DialogInterface var1, int var2) {
            AccountSetupCheckSettings.this.setResult(2);
            AccountSetupCheckSettings.this.finish();
         }
      }

      class 2 implements android.content.DialogInterface.OnClickListener {

         2() {}

         public void onClick(DialogInterface var1, int var2) {
            AccountSetupCheckSettings.this.setResult(-1);
            AccountSetupCheckSettings.this.finish();
         }
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final int val$resId;


      2(int var2) {
         this.val$resId = var2;
      }

      public void run() {
         if(!AccountSetupCheckSettings.this.mDestroyed) {
            TextView var1 = AccountSetupCheckSettings.this.mMessageView;
            AccountSetupCheckSettings var2 = AccountSetupCheckSettings.this;
            int var3 = this.val$resId;
            String var4 = var2.getString(var3);
            var1.setText(var4);
         }
      }
   }

   class 3 implements Runnable {

      // $FF: synthetic field
      final Object[] val$args;
      // $FF: synthetic field
      final boolean val$autoDiscoverAuthException;
      // $FF: synthetic field
      final int val$msgResId;


      3(int var2, Object[] var3, boolean var4) {
         this.val$msgResId = var2;
         this.val$args = var3;
         this.val$autoDiscoverAuthException = var4;
      }

      public void run() {
         if(!AccountSetupCheckSettings.this.mDestroyed) {
            AccountSetupCheckSettings.this.mProgressBar.setIndeterminate((boolean)0);
            AccountSetupCheckSettings var1 = AccountSetupCheckSettings.this;
            Builder var2 = (new Builder(var1, 2131361797)).setIcon(17301543);
            String var3 = AccountSetupCheckSettings.this.getString(2131165383);
            Builder var4 = var2.setTitle(var3);
            AccountSetupCheckSettings var5 = AccountSetupCheckSettings.this;
            int var6 = this.val$msgResId;
            Object[] var7 = this.val$args;
            String var8 = var5.getString(var6, var7);
            Builder var9 = var4.setMessage(var8).setCancelable((boolean)1);
            String var10 = AccountSetupCheckSettings.this.getString(2131165404);
            AccountSetupCheckSettings.3.1 var11 = new AccountSetupCheckSettings.3.1();
            AlertDialog var12 = var9.setPositiveButton(var10, var11).show();
         }
      }

      class 1 implements android.content.DialogInterface.OnClickListener {

         1() {}

         public void onClick(DialogInterface var1, int var2) {
            if(3.this.val$autoDiscoverAuthException) {
               AccountSetupCheckSettings.this.setResult(1);
            }

            AccountSetupCheckSettings.this.finish();
         }
      }
   }

   class 1 extends Thread {

      // $FF: synthetic field
      final Intent val$intent;
      // $FF: synthetic field
      final boolean val$semcAutoDiscover;
      // $FF: synthetic field
      final boolean val$semcAutoDiscoverWithoutValidating;


      1(Intent var2, boolean var3, boolean var4) {
         this.val$intent = var2;
         this.val$semcAutoDiscover = var3;
         this.val$semcAutoDiscoverWithoutValidating = var4;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   private class AutoDiscoverAuthenticationException extends AuthenticationFailedException {

      private static final long serialVersionUID = 1L;


      public AutoDiscoverAuthenticationException(String var2) {
         super(var2);
      }
   }
}
