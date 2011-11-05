package com.htc.android.mail;

import android.accounts.AccountAuthenticatorResponse;
import android.app.Activity;
import android.app.Dialog;
import android.app.backup.BackupManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailAccountUI;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailEventBroadcaster;
import com.htc.android.mail.MailListTab;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.MailRequestHandler;
import com.htc.android.mail.MailScrollView;
import com.htc.android.mail.ll;
import com.htc.app.HtcProgressDialog;
import com.htc.util.skin.HtcSkinUtil;
import com.htc.widget.HtcAlertDialog.Builder;
import java.lang.ref.WeakReference;

public class ecNewAccount extends Activity {

   public static final int ACCOUNTLIST_CALLING = 91;
   public static final int ACCOUNTLIST_NEW_ACCOUNT_FAIL = 101;
   public static final int ACCOUNTLIST_NEW_ACCOUNT_OK = 104;
   public static final int ACCOUNT_AND_SYNC = 96;
   public static final String ACTION_SILDER_STATE = "com.htc.launcher.ThemeChooser.action.silder_change";
   public static final int BACK_TO_PROVIDER = 200;
   public static final int COMPOSE_CALLING = 90;
   public static final int COMPOSE_NEW_ACCOUNT_FAIL = 100;
   public static final int COMPOSE_NEW_ACCOUNT_OK = 103;
   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   public static final String EXTRA_SILDER_STATE = "silder_state";
   public static final int MAILLIST_CALLING = 92;
   public static final int MAILLIST_NEW_ACCOUNT_FAIL = 102;
   public static final int MAILLIST_NEW_ACCOUNT_OK = 105;
   public static final int MAILTAB_CALLING = 95;
   public static final int MAILTAB_NEW_ACCOUNT_OK = 109;
   public static final int MAILWHEEL_CALLING = 93;
   public static final int MAILWHEEL_NEW_ACCOUNT_OK = 107;
   public static final int OOBE_CALLING = 94;
   public static final int OOBE_NEW_ACCOUNT_OK = 108;
   private static final String TAG = "ecNewAccount";
   public static final int TRANSFER_TO_MAILLIST = 106;
   private static String providerStr = "";
   public OnClickListener DiffDomainListener;
   public OnClickListener NoNetworkGoStep4Listener;
   private MailAccountUI UI;
   private String cmd;
   private OnClickListener confirmSaveAccountEvent;
   public OnClickListener gotoSettingListener;
   private BackupManager mBackupManager;
   Handler mHandler;
   private BroadcastReceiver mIMERecever;
   IntentFilter mIntentfilter;
   private boolean mIsDestroyed;
   private boolean mOnForeground;
   private WeakReference<Handler> mWeakHandler;
   private Uri m_uri = null;
   private OnKeyListener stopCheckAccountListener;


   public ecNewAccount() {
      MailAccountUI var1 = new MailAccountUI();
      this.UI = var1;
      this.mIntentfilter = null;
      this.mOnForeground = (boolean)0;
      MailRequestHandler var2 = new MailRequestHandler(this);
      this.mHandler = var2;
      Handler var3 = this.mHandler;
      WeakReference var4 = new WeakReference(var3);
      this.mWeakHandler = var4;
      this.mIsDestroyed = (boolean)0;
      this.cmd = null;
      ecNewAccount.3 var5 = new ecNewAccount.3();
      this.NoNetworkGoStep4Listener = var5;
      ecNewAccount.4 var6 = new ecNewAccount.4();
      this.DiffDomainListener = var6;
      ecNewAccount.5 var7 = new ecNewAccount.5();
      this.stopCheckAccountListener = var7;
      ecNewAccount.6 var8 = new ecNewAccount.6();
      this.gotoSettingListener = var8;
      ecNewAccount.7 var9 = new ecNewAccount.7();
      this.confirmSaveAccountEvent = var9;
      ecNewAccount.8 var10 = new ecNewAccount.8();
      this.mIMERecever = var10;
   }

   private void btnFncL() {
      switch(this.UI.curNewAccountStep) {
      case 1:
         this.UI.smartLogin = (boolean)0;
         this.UI.curNewAccountStep = 2;
         this.UI.setScreen2();
         this.UI.checkScreen2();
         this.UI.updateScreen();
         return;
      case 2:
         this.UI.curNewAccountStep = 1;
         this.UI.updateScreen();
         return;
      case 3:
         this.UI.curNewAccountStep = 2;
         this.UI.checkScreen2();
         this.UI.updateScreen();
         return;
      case 4:
         if(this.UI.smartLogin) {
            this.UI.curNewAccountStep = 1;
         } else {
            this.UI.curNewAccountStep = 3;
            this.UI.checkScreen3();
         }

         this.UI.updateScreen();
         return;
      default:
      }
   }

   private void btnFncR() {
      switch(this.UI.curNewAccountStep) {
      case 1:
         if(!this.checkNetworkAccess()) {
            return;
         } else if(!this.UI.checkScreen1()) {
            return;
         } else if(this.UI.checkSameEmailAddress()) {
            this.showDialog(15);
            return;
         } else {
            if(this.UI.okAddr != null && this.UI.okPwd != null) {
               String var1 = this.UI.okAddr;
               String var2 = this.UI.mEmailAddress.getText().toString();
               if(var1.equals(var2)) {
                  String var3 = this.UI.okPwd;
                  String var4 = this.UI.mPassword.getText().toString();
                  if(var3.equals(var4)) {
                     this.UI.curNewAccountStep = 4;
                     this.UI.updateScreen();
                     return;
                  }
               }
            }

            MailAccountUI var5 = this.UI;
            WeakReference var6 = this.mWeakHandler;
            var5.verifySmart(this, var6);
            return;
         }
      case 2:
         if(DEBUG) {
            ll.d("ecNewAccount", "NEW_ACCOUNT_STEP_2>");
         }

         if(!this.checkNetworkAccess()) {
            return;
         } else if(this.UI.checkSameEmailAddress()) {
            this.showDialog(15);
            return;
         } else {
            MailAccountUI var7 = this.UI;
            String var8 = this.UI.mPort.getText().toString().trim();
            if(!var7.checkPortNumber(var8)) {
               return;
            } else {
               if(DEBUG) {
                  ll.d("ecNewAccount", "checkAccountWithPop3>");
               }

               if(this.UI.ifVerifyScreen2() == 1) {
                  MailAccountUI var9 = this.UI;
                  WeakReference var10 = this.mWeakHandler;
                  var9.checkAccountWithPop3(this, var10);
                  return;
               }

               this.UI.curNewAccountStep = 3;
               this.UI.setScreen3();
               this.UI.checkScreen3();
               this.UI.updateScreen();
               return;
            }
         }
      case 3:
         if(DEBUG) {
            ll.d("ecNewAccount", "NEW_ACCOUNT_STEP_3>");
         }

         if(!this.checkNetworkAccess()) {
            return;
         } else {
            MailAccountUI var11 = this.UI;
            String var12 = this.UI.mOutPort.getText().toString().trim();
            if(!var11.checkPortNumber(var12)) {
               return;
            } else {
               if(DEBUG) {
                  ll.d("ecNewAccount", "checkAccountWithSmtp>");
               }

               MailAccountUI var13 = this.UI;
               int var14 = this.UI.mProtocol.getSelectedItemPosition();
               var13.mProtocolValue = var14;
               if(this.UI.ifVerifyScreen3() == 1) {
                  MailAccountUI var15 = this.UI;
                  WeakReference var16 = this.mWeakHandler;
                  var15.checkAccountWithSmtp(this, var16);
                  return;
               }

               this.UI.curNewAccountStep = 4;
               this.UI.checkScreen4();
               this.UI.updateScreen();
               return;
            }
         }
      case 4:
         if(this.UI.checkSameAccountName()) {
            if(this.isFinishing()) {
               if(!DEBUG) {
                  return;
               }

               ll.d("ecNewAccount", "return in finish>");
               return;
            }

            if(this.mIsDestroyed) {
               if(!DEBUG) {
                  return;
               }

               ll.d("ecNewAccount", "return in mIsDestroyed>");
               return;
            }

            this.showDialog(1);
            return;
         }

         this.doSaveAccount();
         return;
      default:
      }
   }

   private boolean checkNetworkAccess() {
      boolean var1;
      if(!MailCommon.checkNetwork(this)) {
         this.showDialog(16);
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private void doSaveAccount() {
      if(DEBUG) {
         ll.d("ecNewAccount", "doSaveAccount new>");
      }

      if(this.UI.mDefaultChkbox.isChecked()) {
         Account var1 = MailProvider.getAccount(MailProvider.getDefaultAccountId());
         MailProvider.resetDefaultAccount();
      }

      this.m_uri = null;
      ContentValues var2;
      if(this.UI.smartLogin) {
         var2 = this.UI.gatherSmartValues();
      } else {
         var2 = this.UI.gatherValues((boolean)1);
      }

      if(DEBUG) {
         StringBuilder var3 = (new StringBuilder()).append("VerifyAccount = ");
         int var4 = this.UI.VerifyAccount;
         String var5 = var3.append(var4).toString();
         ll.d("ecNewAccount", var5);
      }

      Integer var6 = Integer.valueOf(this.UI.VerifyAccount);
      var2.put("AccountVerify", var6);
      ecNewAccount.insertAccountTask var7 = new ecNewAccount.insertAccountTask(this);
      ContentValues[] var8 = new ContentValues[]{var2};
      var7.execute(var8);
   }

   private void processAccountCreatedProcedure(Uri var1, long var2) {
      Mail.setServicesEnabled(this.getApplicationContext());
      Bundle var4 = new Bundle();
      AccountAuthenticatorResponse var5 = (AccountAuthenticatorResponse)this.getIntent().getParcelableExtra("accountAuthenticatorResponse");
      if(var5 != null) {
         var5.onResult(var4);
      }

      Toast.makeText(this, 2131361929, 0).show();
      if(this.UI.mCallingActivity < 0) {
         this.UI.mCallingActivity = 93;
      }

      Mail.mMailEvent.setAccountChange();
      Mail.mMailEvent.setAccountID(var2);
      MailEventBroadcaster var6 = Mail.mMailEvent;
      Context var7 = this.getApplicationContext();
      var6.flush(var7);
      this.mBackupManager.dataChanged();
      if(DEBUG) {
         ll.d("ecNewAccount", "BackupManager.dataChanged()");
      }

      MailAccountUI var8 = this.UI;
      String var9 = this.UI.mEmailAddress.getText().toString();
      var8.updateEmailAddrToPublicAccount(var9);
      Intent var10 = new Intent("com.htc.launcher.ThemeChooser.action.silder_change");
      Intent var11 = var10.putExtra("silder_state", (boolean)0);
      this.sendBroadcast(var10);
   }

   private void setResultAndClose(long var1) {
      Intent var3 = new Intent();
      int var4 = (int)var1;
      var3.putExtra("AccountId", var4);
      String var6 = this.UI.mDesc.getText().toString();
      var3.putExtra("AccountName", var6);
      if(this.UI.mCallingActivity == 90) {
         this.setResult(103);
      } else if(this.UI.mCallingActivity == 91) {
         this.setResult(104);
      } else if(this.UI.mCallingActivity == 92) {
         this.setResult(105);
      } else if(this.UI.mCallingActivity == 95) {
         this.setResult(109, var3);
      } else {
         this.setResult(107, var3);
      }

      this.finish();
   }

   public void backScreen() {
      switch(this.UI.curNewAccountStep) {
      case 1:
         this.setResult(200);
         this.finish();
         return;
      case 2:
         this.UI.curNewAccountStep = 1;
         this.UI.updateScreen();
         return;
      case 3:
         this.UI.curNewAccountStep = 2;
         this.UI.updateScreen();
         return;
      case 4:
         if(this.UI.smartLogin) {
            this.UI.curNewAccountStep = 1;
         } else {
            this.UI.curNewAccountStep = 3;
            this.UI.checkScreen3();
         }

         this.UI.updateScreen();
         return;
      default:
      }
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 == 50) {
         switch(this.UI.curNewAccountStep) {
         case 1:
            if(!DEBUG) {
               return;
            }

            ll.d("ecNewAccount", "onActivityResult > NEW_ACCOUNT_STEP_1");
            return;
         case 2:
            this.UI.checkScreen2();
            return;
         case 3:
            this.UI.checkScreen3();
            return;
         default:
         }
      }
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      int var2 = HtcSkinUtil.getDrawableResIdentifier(this, "common_app_bkg", 34080439);
      if(var2 != 0) {
         this.getWindow().setBackgroundDrawableResource(var2);
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(DEBUG) {
         ll.d("Jerry", "ecNewAccount on Create>");
      }

      boolean var2 = this.requestWindowFeature(1);
      this.setContentView(2130903055);
      int var3 = (int)this.getResources().getDimension(2131165225);
      int var4 = (int)this.getResources().getDimension(2131165228);
      this.UI.setScreenMinHeight(var3, var4);
      MailScrollView var5 = (MailScrollView)this.findViewById(2131296372);
      View var6 = this.findViewById(2131296373);
      var5.setChildView(var6);
      IntentFilter var7 = new IntentFilter("HTC_IME_CURRENT_STATE");
      this.mIntentfilter = var7;
      BroadcastReceiver var8 = this.mIMERecever;
      IntentFilter var9 = this.mIntentfilter;
      this.registerReceiver(var8, var9);
      BackupManager var11 = new BackupManager(this);
      this.mBackupManager = var11;
      boolean var12 = this.UI.CreateAccountUI(this);
      Intent var13 = this.getIntent();
      Bundle var14 = this.getIntent().getExtras();
      if(var14 != null) {
         String var15 = var14.getString("_domain");
         long var16 = var14.getLong("_id");
         providerStr = var14.getString("provider");
         if(var15 != null && var15.trim().length() > 0) {
            this.UI.mOri_domain = var15;
         }
      } else {
         this.UI.mOri_domain = "";
      }

      MailAccountUI var18 = this.UI;
      int var19 = this.getIntent().getIntExtra("CallingActivity", -1);
      var18.mCallingActivity = var19;
      if(this.UI.mCallingActivity == 94) {
         ;
      }

      Button var20 = this.UI.btnL;
      ecNewAccount.1 var21 = new ecNewAccount.1();
      var20.setOnClickListener(var21);
      Button var22 = this.UI.btnR;
      ecNewAccount.2 var23 = new ecNewAccount.2();
      var22.setOnClickListener(var23);
      this.UI.checkScreen2();
      this.UI.checkScreen4();
      this.UI.curNewAccountStep = 1;
      this.UI.updateScreen();
      boolean var24 = this.UI.mEmailAddress.requestFocus();
      if(!providerStr.equalsIgnoreCase("other")) {
         MailAccountUI var25 = this.UI;
         String var26 = providerStr;
         var25.setDesc(var26);
      }

      var5.setRoundedCornerEnabled((boolean)1, (boolean)0);
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 0:
         HtcProgressDialog var13 = new HtcProgressDialog(this);
         String var14 = this.getString(2131362009);
         var13.setMessage(var14);
         var13.setIndeterminate((boolean)1);
         var13.setCancelable((boolean)1);
         OnKeyListener var15 = this.stopCheckAccountListener;
         var13.setOnKeyListener(var15);
         var2 = var13;
         break;
      case 1:
         boolean var8 = ((EditText)this.findViewById(2131296381)).requestFocus();
         Builder var9 = new Builder(this);
         CharSequence var10 = this.getText(2131361879);
         Builder var11 = var9.setTitle(var10);
         String var12 = this.getString(2131362012);
         var2 = var11.setMessage(var12).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 2:
         Builder var34 = (new Builder(this)).setIcon(17301543);
         CharSequence var35 = this.getText(2131361879);
         Builder var36 = var34.setTitle(var35);
         String var37 = this.getString(2131362008);
         var2 = var36.setMessage(var37).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 3:
         Builder var38 = new Builder(this);
         CharSequence var39 = this.getText(2131361879);
         Builder var40 = var38.setTitle(var39);
         String var41 = this.getString(2131362007);
         Builder var42 = var40.setMessage(var41).setNegativeButton(2131361931, (OnClickListener)null);
         OnClickListener var43 = this.confirmSaveAccountEvent;
         var2 = var42.setPositiveButton(2131362432, var43).setCancelable((boolean)1).create();
         break;
      case 4:
         Builder var44 = new Builder(this);
         CharSequence var45 = this.getText(2131361879);
         Builder var46 = var44.setTitle(var45);
         String var47 = this.getString(2131362006);
         var2 = var46.setMessage(var47).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 5:
         HtcProgressDialog var16 = new HtcProgressDialog(this);
         String var17 = this.getString(2131362010);
         var16.setMessage(var17);
         var16.setIndeterminate((boolean)1);
         var16.setCancelable((boolean)1);
         OnKeyListener var18 = this.stopCheckAccountListener;
         var16.setOnKeyListener(var18);
         var2 = var16;
         break;
      case 6:
         HtcProgressDialog var31 = new HtcProgressDialog(this);
         String var32 = this.getString(2131362011);
         var31.setMessage(var32);
         var31.setIndeterminate((boolean)1);
         var31.setCancelable((boolean)1);
         OnKeyListener var33 = this.stopCheckAccountListener;
         var31.setOnKeyListener(var33);
         var2 = var31;
         break;
      case 7:
         Builder var25 = (new Builder(this)).setIcon(17301543);
         CharSequence var26 = this.getText(2131361879);
         Builder var27 = var25.setTitle(var26);
         String var28 = this.getString(2131362229);
         Builder var29 = var27.setMessage(var28);
         OnClickListener var30 = this.DiffDomainListener;
         var2 = var29.setPositiveButton(2131362432, var30).setCancelable((boolean)1).create();
         break;
      case 8:
         Builder var19 = (new Builder(this)).setIcon(17301543);
         CharSequence var20 = this.getText(2131361879);
         Builder var21 = var19.setTitle(var20);
         String var22 = this.getString(2131362232);
         Builder var23 = var21.setMessage(var22).setPositiveButton(2131362432, (OnClickListener)null);
         OnClickListener var24 = this.NoNetworkGoStep4Listener;
         var2 = var23.setNegativeButton(2131362210, var24).setCancelable((boolean)1).create();
         break;
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      default:
         var2 = super.onCreateDialog(var1);
         break;
      case 15:
         boolean var3 = ((EditText)this.findViewById(2131296388)).requestFocus();
         Builder var4 = (new Builder(this)).setIcon(17301543);
         CharSequence var5 = this.getText(2131361879);
         Builder var6 = var4.setTitle(var5);
         String var7 = this.getString(2131362013);
         var2 = var6.setMessage(var7).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 16:
         Builder var48 = new Builder(this);
         CharSequence var49 = this.getText(2131362234);
         Builder var50 = var48.setTitle(var49);
         String var51 = this.getString(2131362233);
         Builder var52 = var50.setMessage(var51);
         OnClickListener var53 = this.gotoSettingListener;
         var2 = var52.setPositiveButton(2131362145, var53).setNegativeButton(2131361931, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 17:
         Builder var54 = (new Builder(this)).setIcon(17301543);
         CharSequence var55 = this.getText(2131361879);
         Builder var56 = var54.setTitle(var55);
         String var57 = this.getString(2131361932);
         var2 = var56.setMessage(var57).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 18:
         Builder var58 = new Builder(this);
         CharSequence var59 = this.getText(2131361879);
         Builder var60 = var58.setTitle(var59);
         String var61 = this.getString(2131362242);
         var2 = var60.setMessage(var61).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
      }

      return (Dialog)var2;
   }

   protected final void onDestroy() {
      super.onDestroy();
      if(DEBUG) {
         ll.i("ecNewAccount", "onDestroy>");
      }

      this.mIsDestroyed = (boolean)1;
      this.UI.cancelRequest(this);
      if(this.mIMERecever != null) {
         BroadcastReceiver var1 = this.mIMERecever;
         this.unregisterReceiver(var1);
      }
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      byte var3;
      if(var1 == 4) {
         this.backScreen();
         var3 = 1;
      } else {
         var3 = super.onKeyDown(var1, var2);
      }

      return (boolean)var3;
   }

   protected void onPause() {
      super.onPause();
      if(DEBUG) {
         ll.i("ecNewAccount", "onPause>");
      }

      this.mOnForeground = (boolean)0;
   }

   protected final void onResume() {
      super.onResume();
      if(DEBUG) {
         ll.d("Jerry", "ecNewAccount on Resume>");
      }

      this.mOnForeground = (boolean)1;
      if(this.UI != null) {
         MailAccountUI var1 = this.UI;
         int var2 = this.UI.getSipHeight();
         var1.resetPresentScreenHeight(var2);
      }

      if(this.UI.mCallingActivity == 94) {
         ;
      }
   }

   protected void onStart() {
      super.onStart();
      if(DEBUG) {
         ll.d("ecNewAccount", "onStart> ");
      }
   }

   protected void onStop() {
      super.onStop();
      if(DEBUG) {
         ll.i("ecNewAccount", "onStop>");
      }
   }

   class 8 extends BroadcastReceiver {

      8() {}

      public void onReceive(Context var1, Intent var2) {
         MailAccountUI var3 = ecNewAccount.this.UI;
         boolean var4 = ecNewAccount.this.mOnForeground;
         var3.onIMEReceive(var2, var4);
      }
   }

   class 7 implements OnClickListener {

      7() {}

      public void onClick(DialogInterface var1, int var2) {
         ecNewAccount.this.doSaveAccount();
      }
   }

   private class insertAccountTask extends AsyncTask<ContentValues, Void, Uri> {

      private HtcProgressDialog mDialog;
      protected final WeakReference<ecNewAccount> mTarget;


      public insertAccountTask(ecNewAccount var2) {
         WeakReference var3 = new WeakReference(var2);
         this.mTarget = var3;
      }

      protected Uri doInBackground(ContentValues ... var1) {
         ecNewAccount var2 = (ecNewAccount)this.mTarget.get();
         Uri var3;
         if(var2 == null) {
            var3 = null;
         } else if(var1 == null) {
            var3 = null;
         } else {
            ContentValues var4 = var1[0];
            var3 = Account.applyBatchAccountToDatabases(var2, var4);
         }

         return var3;
      }

      protected void onPostExecute(Uri var1) {
         ecNewAccount var2 = (ecNewAccount)this.mTarget.get();
         if(var2 != null) {
            if(!var2.isFinishing()) {
               if(var1 == null) {
                  if(ecNewAccount.DEBUG) {
                     ll.d("ecNewAccount", "account uri is null");
                  }

                  this.mDialog.dismiss();
               } else {
                  long var3 = ContentUris.parseId(var1);
                  if(ecNewAccount.DEBUG) {
                     String var5 = "new account id>" + var3;
                     ll.d("ecNewAccount", var5);
                  }

                  ecNewAccount.this.processAccountCreatedProcedure(var1, var3);
                  if(var2.UI.mCallingActivity != 90 && var2.UI.mCallingActivity != 94 && var2.UI.mCallingActivity != 95 && var2.UI.mCallingActivity != 96) {
                     Intent var6 = new Intent();
                     var6.setData(var1);
                     var6.setClass(var2, MailListTab.class);
                     Intent var9 = var6.putExtra("refresh", (boolean)1);
                     ecNewAccount.this.startActivity(var6);
                     ecNewAccount.this.finish();
                  } else {
                     ecNewAccount.this.setResultAndClose(var3);
                  }

                  this.mDialog.dismiss();
               }
            }
         }
      }

      protected void onPreExecute() {
         ecNewAccount var1 = (ecNewAccount)this.mTarget.get();
         if(var1 != null) {
            if(!var1.isFinishing()) {
               HtcProgressDialog var2 = new HtcProgressDialog(var1);
               this.mDialog = var2;
               HtcProgressDialog var3 = this.mDialog;
               CharSequence var4 = var1.getText(2131362428);
               var3.setMessage(var4);
               this.mDialog.setCancelable((boolean)0);
               this.mDialog.show();
            }
         }
      }
   }

   class 1 implements android.view.View.OnClickListener {

      1() {}

      public void onClick(View var1) {
         ecNewAccount.this.btnFncL();
      }
   }

   class 2 implements android.view.View.OnClickListener {

      2() {}

      public void onClick(View var1) {
         ecNewAccount.this.btnFncR();
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         if(ecNewAccount.DEBUG) {
            ll.d("ecNewAccount", "goClick");
         }

         ecNewAccount.this.UI.mUserCancelCheck = (boolean)0;
         ecNewAccount.this.UI.curNewAccountStep = 4;
         ecNewAccount.this.UI.checkScreen4();
         ecNewAccount.this.UI.updateScreen();
      }
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(DialogInterface var1, int var2) {
         if(ecNewAccount.this.UI.mDiffDomain) {
            ecNewAccount.this.UI.curNewAccountStep = 2;
            ecNewAccount.this.UI.setScreen2();
            ecNewAccount.this.UI.updateScreen();
            ecNewAccount.this.UI.mDiffDomain = (boolean)0;
         }
      }
   }

   class 5 implements OnKeyListener {

      5() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         boolean var6;
         if(var2 == 4) {
            if(!ecNewAccount.this.UI.mUserCancelCheck) {
               ecNewAccount.this.UI.mUserCancelCheck = (boolean)1;
               if(ecNewAccount.DEBUG) {
                  ll.d("JerryDebug", "cancelCheck is true");
               }

               ecNewAccount.this.UI.CancelCheckAccount();
            }

            ecNewAccount var4 = ecNewAccount.this;
            MailAccountUI var5 = ecNewAccount.this.UI;
            var4.dismissDialog(0);
            var6 = true;
         } else {
            var6 = false;
         }

         return var6;
      }
   }

   class 6 implements OnClickListener {

      6() {}

      public void onClick(DialogInterface var1, int var2) {
         Intent var3 = new Intent("android.intent.action.VIEW");
         ComponentName var4 = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
         var3.setComponent(var4);
         ecNewAccount.this.startActivityForResult(var3, 50);
      }
   }
}
