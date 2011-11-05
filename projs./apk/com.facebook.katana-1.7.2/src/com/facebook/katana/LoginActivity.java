package com.facebook.katana;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import com.facebook.katana.AlertDialogs;
import com.facebook.katana.C2DMReceiver;
import com.facebook.katana.HtmlAboutActivity;
import com.facebook.katana.SyncContactsSetupActivity;
import com.facebook.katana.VersionTasks;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.RingtoneUtils;
import com.facebook.katana.util.Toaster;
import com.facebook.katana.util.Utils;
import java.io.IOException;

public class LoginActivity extends BaseFacebookActivity implements OnClickListener {

   private static final int ABOUT_ID = 101;
   private static final int ERROR_DIALOG_ID = 2;
   public static final String EXTRA_ADD_ACCOUNT = "add_account";
   public static final String EXTRA_REDIRECT = "login_redirect";
   private static final int LOGIN_APPROVALS_DIALOG_ID = 3;
   private static final int PROGRESS_LOGIN_DIALOG_ID = 1;
   public static final int REQUEST_REDIRECT = 2210;
   private static final String SAVE_ERROR_MESSAGE = "error_message";
   private boolean mAddAccountHandled;
   private boolean mAddAccountMode;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private String mErrorMessage;
   private boolean mForeground = 0;


   public LoginActivity() {}

   private void login() {
      String var1 = ((EditText)this.findViewById(2131624115)).getText().toString();
      if(var1.length() != 0) {
         String var2 = ((EditText)this.findViewById(2131624116)).getText().toString();
         if(var2.length() != 0) {
            this.showDialog(1);
            AppSession var3 = new AppSession();
            this.mAppSession = var3;
            AppSession var4 = this.mAppSession;
            AppSessionListener var5 = this.mAppSessionListener;
            var4.addListener(var5);
            AppSession var6 = this.mAppSession;
            String var7 = var1.toLowerCase();
            var6.authLogin(this, var7, var2, (boolean)0);
         }
      }
   }

   public static Intent loginIntent(Context var0) {
      Intent var1 = new Intent(var0, LoginActivity.class);
      Intent var2 = var1.setAction("android.intent.action.VIEW");
      Intent var3 = var1.addFlags(67108864);
      return var1;
   }

   public static void redirectThroughLogin(Activity var0) {
      Intent var1 = loginIntent(var0);
      Intent var2 = var1.putExtra("login_redirect", (boolean)1);
      var0.startActivityForResult(var1, 2210);
   }

   private void startNextActivity() {
      Utils.updateErrorReportingUserId(this);
      if(this.getIntent().getBooleanExtra("login_redirect", (boolean)0)) {
         this.setResult(-1);
         this.finish();
      } else {
         C2DMReceiver.getClientLogin(this);
         if(PlatformUtils.platformStorageSupported(this) && !UserValuesManager.getContactsSyncSetupDisplayed(this)) {
            Intent var1 = new Intent(this, SyncContactsSetupActivity.class);
            boolean var2 = this.mAddAccountMode;
            var1.putExtra("add_account", var2);
            if(this.mAddAccountMode) {
               FacebookAuthenticationService.copyCallback(this.getIntent(), var1);
               this.mAddAccountHandled = (boolean)1;
            }

            Intent var4 = (Intent)this.getIntent().getParcelableExtra("com.facebook.katana.continuation_intent");
            if(var4 != null) {
               var1.putExtra("com.facebook.katana.continuation_intent", var4);
            }

            this.startActivity(var1);
         } else {
            Intent var6 = (Intent)this.getIntent().getParcelableExtra("com.facebook.katana.continuation_intent");
            ApplicationUtils.startDefaultActivity(this, var6);
         }
      }
   }

   public static void toLogin(Activity var0) {
      Intent var1 = loginIntent(var0);
      var0.startActivity(var1);
      var0.finish();
   }

   public static void toLogin(Activity var0, Intent var1) {
      Intent var2 = loginIntent(var0);
      if(var1 != null) {
         var2.putExtra("com.facebook.katana.continuation_intent", var1);
      }

      var0.startActivity(var2);
      var0.finish();
   }

   public boolean facebookOnBackPressed() {
      this.setTransitioningToBackground((boolean)1);
      this.finish();
      return true;
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131624117:
         this.login();
         return;
      case 2131624118:
         Uri var2 = Uri.parse("http://m.facebook.com/r.php");
         Intent var3 = new Intent("android.intent.action.VIEW", var2);
         this.startActivity(var3);
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      boolean var2 = this.getIntent().getBooleanExtra("add_account", (boolean)0);
      this.mAddAccountMode = var2;
      VersionTasks.getInstance(this).executeUpgrades();
      AppSession var3 = AppSession.getActiveSession(this, (boolean)1);
      if(var3 != null) {
         int[] var4 = LoginActivity.1.$SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
         int var5 = var3.getStatus().ordinal();
         switch(var4[var5]) {
         case 1:
            if(!this.mAddAccountMode) {
               this.startNextActivity();
               this.finish();
               return;
            }
         }
      }

      if(!UserValuesManager.getRegisterRingtone(this)) {
         try {
            Uri var6 = RingtoneUtils.createFacebookRingtone(this);
            if(var6 != null) {
               Editor var7 = PreferenceManager.getDefaultSharedPreferences(this).edit();
               String var8 = var6.toString();
               var7.putString("ringtone", var8);
               boolean var10 = var7.commit();
               UserValuesManager.setRegisterRingtone(this, (boolean)1);
            }
         } catch (IOException var16) {
            ;
         }
      }

      if(this.mAddAccountMode && PlatformUtils.platformStorageSupported(this) && FacebookAuthenticationService.getAccountsCount(this) > 0) {
         Toaster.toast(this, 2131361935);
         this.finish();
      } else {
         this.setContentView(2130903102);
         this.hideSearchButton();
         ((ImageButton)this.findViewById(2131624260)).setEnabled((boolean)0);
         this.findViewById(2131624117).setOnClickListener(this);
         this.findViewById(2131624118).setOnClickListener(this);
         String var11 = AppSession.getUsernameHint(this);
         if(var11 != null) {
            ((EditText)this.findViewById(2131624115)).setText(var11);
            boolean var12 = ((EditText)this.findViewById(2131624116)).requestFocus();
         }

         if(var1 != null) {
            String var13 = var1.getString("error_message");
            this.mErrorMessage = var13;
         }

         LoginActivity.LoginAppSessionListener var14 = new LoginActivity.LoginAppSessionListener((LoginActivity.1)null);
         this.mAppSessionListener = var14;
      }
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 1:
         ProgressDialog var3 = new ProgressDialog(this);
         var3.setProgressStyle(0);
         CharSequence var4 = this.getText(2131361948);
         var3.setMessage(var4);
         var3.setIndeterminate((boolean)1);
         var3.setCancelable((boolean)0);
         var2 = var3;
         break;
      case 2:
         String var5 = this.getString(2131361947);
         String var6 = this.mErrorMessage;
         String var7 = this.getString(2131362013);
         Object var9 = null;
         Object var10 = null;
         Object var11 = null;
         var2 = AlertDialogs.createAlert(this, var5, 17301543, var6, var7, (android.content.DialogInterface.OnClickListener)null, (String)var9, (android.content.DialogInterface.OnClickListener)var10, (OnCancelListener)var11, (boolean)1);
         break;
      case 3:
         String var12 = this.getString(2131361954);
         String var13 = this.mErrorMessage;
         String var14 = this.getString(2131362013);
         Object var16 = null;
         Object var17 = null;
         Object var18 = null;
         var2 = AlertDialogs.createAlert(this, var12, 17301543, var13, var14, (android.content.DialogInterface.OnClickListener)null, (String)var16, (android.content.DialogInterface.OnClickListener)var17, (OnCancelListener)var18, (boolean)1);
         break;
      default:
         var2 = null;
      }

      return (Dialog)var2;
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      MenuItem var3 = var1.add(0, 101, 0, 2131361934).setIcon(17301569);
      return true;
   }

   public void onDestroy() {
      if(this.isFinishing() && this.mAddAccountMode && !this.mAddAccountHandled) {
         FacebookAuthenticationService.addAccountError(this.getIntent(), 400, "User canceled");
      }

      super.onDestroy();
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      switch(var1.getItemId()) {
      case 101:
         Intent var2 = new Intent(this, HtmlAboutActivity.class);
         this.startActivity(var2);
      default:
         return super.onOptionsItemSelected(var1);
      }
   }

   public void onPause() {
      super.onPause();
      this.mForeground = (boolean)0;
      if(this.mAppSession != null) {
         AppSession var1 = this.mAppSession;
         AppSessionListener var2 = this.mAppSessionListener;
         var1.removeListener(var2);
      }
   }

   public void onResume() {
      super.onResume();
      this.mForeground = (boolean)1;
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession != null) {
         int[] var2 = LoginActivity.1.$SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
         int var3 = this.mAppSession.getStatus().ordinal();
         switch(var2[var3]) {
         case 1:
            if(!this.mAddAccountMode) {
               this.startNextActivity();
            }

            this.finish();
            return;
         case 2:
            AppSession var4 = this.mAppSession;
            AppSessionListener var5 = this.mAppSessionListener;
            var4.addListener(var5);
            return;
         default:
            this.removeDialog(1);
            this.mAppSession = null;
         }
      } else {
         this.removeDialog(1);
      }
   }

   protected void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      if(this.mErrorMessage != null) {
         String var2 = this.mErrorMessage;
         var1.putString("error_message", var2);
      }
   }

   public boolean onSearchRequested() {
      return true;
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus = new int[AppSession.LoginStatus.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
            int var1 = AppSession.LoginStatus.STATUS_LOGGED_IN.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
            int var3 = AppSession.LoginStatus.STATUS_LOGGING_IN.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
            int var5 = AppSession.LoginStatus.STATUS_LOGGED_OUT.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
            int var7 = AppSession.LoginStatus.STATUS_LOGGING_OUT.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var12) {
            ;
         }
      }
   }

   private class LoginAppSessionListener extends AppSessionListener {

      private LoginAppSessionListener() {}

      // $FF: synthetic method
      LoginAppSessionListener(LoginActivity.1 var2) {
         this();
      }

      public void onLoginComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {
         LoginActivity.this.removeDialog(1);
         if(var3 == 200) {
            LoginActivity.this.startNextActivity();
            LoginActivity.this.finish();
         } else {
            AppSessionListener var6 = LoginActivity.this.mAppSessionListener;
            var1.removeListener(var6);
            AppSession var7 = LoginActivity.this.mAppSession = null;
            if(var3 == 0) {
               if(var5 instanceof FacebookApiException) {
                  var3 = ((FacebookApiException)var5).getErrorCode();
                  String var8 = ((FacebookApiException)var5).getErrorMsg();
               } else {
                  String var12 = var5.getMessage();
               }
            }

            switch(var3) {
            case 401:
               LoginActivity var13 = LoginActivity.this;
               String var14 = LoginActivity.this.getString(2131361938);
               var13.mErrorMessage = var14;
               if(!LoginActivity.this.mForeground) {
                  return;
               }

               LoginActivity.this.showDialog(2);
               return;
            case 406:
               LoginActivity var16 = LoginActivity.this;
               String var17 = LoginActivity.this.getString(2131361955);
               var16.mErrorMessage = var17;
               if(!LoginActivity.this.mForeground) {
                  return;
               }

               LoginActivity.this.showDialog(3);
               ((EditText)LoginActivity.this.findViewById(2131624116)).setText("");
               return;
            default:
               LoginActivity var9 = LoginActivity.this;
               String var10 = LoginActivity.this.getString(2131361939);
               var9.mErrorMessage = var10;
               if(LoginActivity.this.mForeground) {
                  LoginActivity.this.showDialog(2);
               }
            }
         }
      }
   }
}
