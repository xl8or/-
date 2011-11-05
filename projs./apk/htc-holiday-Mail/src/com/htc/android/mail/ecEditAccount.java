package com.htc.android.mail;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IContentProvider;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.RemoteException;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.htc.android.mail.Account;
import com.htc.android.mail.AccountPool;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailAccountUI;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailEventBroadcaster;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.MailRequestHandler;
import com.htc.android.mail.MailScrollView;
import com.htc.android.mail.SMTPAccountErrorNotification;
import com.htc.android.mail.ll;
import com.htc.app.HtcProgressDialog;
import com.htc.widget.HtcSpinner;
import com.htc.widget.HtcAlertDialog.Builder;
import java.lang.ref.WeakReference;

public class ecEditAccount extends Activity {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "ecEditAccount";
   private MailAccountUI UI;
   private OnClickListener confirmChangeAccountEvent;
   private OnClickListener confirmChangeAccountEventInStep1;
   private OnClickListener confirmSaveAccountEvent;
   public OnClickListener gotoSettingListener;
   private int inPort;
   private long mAccountId = 65535L;
   private int mCheckDomain = 0;
   private boolean mDelCurrentAccount = 0;
   Handler mHandler;
   private BroadcastReceiver mIMERecever;
   IntentFilter mIntentfilter;
   private boolean mOnForeground;
   private WeakReference<Handler> mWeakHandler;
   private String orgEmailAddr = "";
   private String orgInServerDomain = "";
   private int orgProtocol = -1;
   private OnKeyListener stopCheckAccountListener;


   public ecEditAccount() {
      MailAccountUI var1 = new MailAccountUI();
      this.UI = var1;
      this.mIntentfilter = null;
      this.mOnForeground = (boolean)0;
      MailRequestHandler var2 = new MailRequestHandler(this);
      this.mHandler = var2;
      Handler var3 = this.mHandler;
      WeakReference var4 = new WeakReference(var3);
      this.mWeakHandler = var4;
      ecEditAccount.3 var5 = new ecEditAccount.3();
      this.confirmChangeAccountEvent = var5;
      ecEditAccount.4 var6 = new ecEditAccount.4();
      this.confirmChangeAccountEventInStep1 = var6;
      ecEditAccount.5 var7 = new ecEditAccount.5();
      this.gotoSettingListener = var7;
      ecEditAccount.6 var8 = new ecEditAccount.6();
      this.stopCheckAccountListener = var8;
      ecEditAccount.7 var9 = new ecEditAccount.7();
      this.confirmSaveAccountEvent = var9;
      ecEditAccount.10 var10 = new ecEditAccount.10();
      this.mIMERecever = var10;
   }

   private final void bind() {
      Uri var1 = this.getIntent().getData();
      if(var1 != null) {
         String var2 = var1.toString();
         String var3 = MailProvider.sAccountsURI.toString();
         if(var2.startsWith(var3)) {
            long var4 = ContentUris.parseId(var1);
            this.mAccountId = var4;
            Account var6 = MailProvider.getAccount(this.mAccountId);
            String var7 = var6.inServer;
            this.orgInServerDomain = var7;
            HtcSpinner var8 = this.UI.mProtocol;
            int var9 = var6.protocol;
            var8.setSelection(var9);
            int var10 = var6.protocol;
            this.orgProtocol = var10;
            EditText var11 = this.UI.mServer;
            String var12 = var6.inServer;
            var11.setText(var12);
            EditText var13 = this.UI.mOutServer;
            String var14 = var6.outServer;
            var13.setText(var14);
            EditText var15 = this.UI.mName;
            String var16 = var6.name;
            var15.setText(var16);
            String var17 = var6.emailAddress;
            this.orgEmailAddr = var17;
            AutoCompleteTextView var18 = this.UI.mEmailAddress;
            String var19 = var6.emailAddress;
            var18.setText(var19);
            this.UI.mEmailAddress.setNextFocusDownId(33685507);
            EditText var20 = this.UI.mInPassword;
            String var21 = var6.password;
            var20.setText(var21);
            EditText var22 = this.UI.mOutPassword;
            String var23 = var6.outpassword;
            var22.setText(var23);
            EditText var24 = this.UI.mInUsername;
            String var25 = var6.userName;
            var24.setText(var25);
            EditText var26 = this.UI.mOutUsername;
            String var27 = var6.outuserName;
            var26.setText(var27);
            EditText var28 = this.UI.mDesc;
            Context var29 = this.getApplicationContext();
            String var30 = var6.getDescription(var29);
            var28.setText(var30);
            HtcSpinner var31 = this.UI.mSecurityIn;
            int var32 = var6.useSSLin;
            var31.setSelection(var32);
            HtcSpinner var33 = this.UI.mSecurityOut;
            int var34 = var6.useSSLout;
            var33.setSelection(var34);
            ToggleButton var35 = this.UI.mRequireLogin;
            byte var36;
            if(var6.smtpauth == 1) {
               var36 = 1;
            } else {
               var36 = 0;
            }

            var35.setChecked((boolean)var36);
            MailAccountUI var37 = this.UI;
            int var38 = var6.smtpauth;
            var37.screen3requirelogin = var38;
            MailAccountUI var39 = this.UI;
            String var40 = var6.provider;
            var39.providerStr = var40;
            MailAccountUI var41 = this.UI;
            long var42 = (long)var6.providerid;
            var41.mProviderId = var42;
            MailAccountUI var44 = this.UI;
            String var45 = var6.userName;
            var44.screen2name = var45;
            MailAccountUI var46 = this.UI;
            String var47 = var6.password;
            var46.screen2pwd = var47;
            MailAccountUI var48 = this.UI;
            String var49 = var6.inServer;
            var48.screen2pop = var49;
            MailAccountUI var50 = this.UI;
            String var51 = String.valueOf(var6.inPort);
            var50.screen2port = var51;
            MailAccountUI var52 = this.UI;
            String var53 = var6.outuserName;
            var52.screen3name = var53;
            MailAccountUI var54 = this.UI;
            String var55 = var6.outpassword;
            var54.screen3pwd = var55;
            MailAccountUI var56 = this.UI;
            String var57 = var6.outServer;
            var56.screen3srv = var57;
            MailAccountUI var58 = this.UI;
            String var59 = String.valueOf(var6.outPort);
            var58.screen3port = var59;
            EditText var60 = this.UI.mPort;
            String var61 = String.valueOf(var6.inPort);
            var60.setText(var61);
            EditText var62 = this.UI.mOutPort;
            String var63 = String.valueOf(var6.outPort);
            var62.setText(var63);
            if(var6.defaultaccount > 0) {
               this.UI.mDefaultChkbox.setVisibility(4);
               this.UI.mDefaultChkboxText.setVisibility(4);
            }

            ContentResolver var64 = this.getContentResolver();
            StringBuilder var65 = new StringBuilder();
            Uri var66 = MailProvider.AccountVerify;
            StringBuilder var67 = var65.append(var66).append("/");
            long var68 = this.mAccountId;
            Uri var70 = Uri.parse(var67.append(var68).toString());
            Object var71 = null;
            Object var72 = null;
            Object var73 = null;
            Cursor var74 = var64.query(var70, (String[])null, (String)var71, (String[])var72, (String)var73);
            if(!this.getIntent().getBooleanExtra("doCheck", (boolean)0)) {
               if(var74 != null && var74.moveToNext()) {
                  MailAccountUI var75 = this.UI;
                  int var76 = var74.getColumnIndexOrThrow("AccountVerify");
                  int var77 = var74.getInt(var76);
                  var75.VerifyAccount = var77;
               }
            } else {
               this.UI.VerifyAccount = 0;
            }

            if(var74 != null) {
               var74.close();
            }

            SMTPAccountErrorNotification var78 = new SMTPAccountErrorNotification(this);
            long var79 = this.mAccountId;
            var78.clearNotification(var79);
            return;
         }
      }

      this.finish();
   }

   private void btnFncL() {
      switch(this.UI.curNewAccountStep) {
      case 1:
         this.finish();
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
         if(!this.UI.checkScreen1()) {
            return;
         } else if(this.UI.checkSameEmailAddress()) {
            this.showDialog(15);
            return;
         } else {
            String var1 = this.UI.mEmailAddress.getText().toString().trim();
            if(this.orgEmailAddr.trim().compareToIgnoreCase(var1) != 0) {
               this.showDialog(19);
               return;
            }

            this.goStep2();
            return;
         }
      case 2:
         if(!this.checkNetworkAccess()) {
            return;
         } else {
            MailAccountUI var2 = this.UI;
            String var3 = this.UI.mPort.getText().toString().trim();
            if(!var2.checkPortNumber(var3)) {
               return;
            } else {
               String var4 = this.UI.mServer.getText().toString().trim();
               String var5 = this.UI.mInUsername.getText().toString().trim();
               String var6 = this.UI.mInEmailAddress.getText().toString().trim();
               if(this.UI.mEmailAddress.getText().toString().trim().compareToIgnoreCase(var6) != 0 && this.orgEmailAddr.trim().compareToIgnoreCase(var6) != 0) {
                  int var7 = var6.lastIndexOf(64);
                  if(var6 != null && var7 >= 0) {
                     int var8 = var6.indexOf(64);
                     if(var7 == var8) {
                        AutoCompleteTextView var9 = this.UI.mEmailAddress;
                        Editable var10 = this.UI.mInEmailAddress.getText();
                        var9.setText(var10);
                        this.showDialog(9);
                        return;
                     }
                  }

                  this.showDialog(17);
                  return;
               } else if(this.orgInServerDomain.trim().compareToIgnoreCase(var4) != 0) {
                  this.showDialog(11);
                  return;
               } else if(this.UI.screen2name.trim().compareToIgnoreCase(var5) != 0) {
                  this.showDialog(10);
                  return;
               } else {
                  int var11 = this.UI.mProtocol.getSelectedItemPosition();
                  int var12 = this.orgProtocol;
                  if(var11 != var12) {
                     this.showDialog(12);
                     return;
                  } else {
                     if(this.UI.ifVerifyScreen2() == 1) {
                        MailAccountUI var13 = this.UI;
                        WeakReference var14 = this.mWeakHandler;
                        var13.checkAccountWithPop3(this, var14);
                        return;
                     }

                     this.UI.curNewAccountStep = 3;
                     this.UI.checkScreen3();
                     this.UI.updateScreen();
                     return;
                  }
               }
            }
         }
      case 3:
         if(!this.checkNetworkAccess()) {
            return;
         } else {
            MailAccountUI var15 = this.UI;
            String var16 = this.UI.mOutPort.getText().toString().trim();
            if(!var15.checkPortNumber(var16)) {
               return;
            } else {
               MailAccountUI var17 = this.UI;
               int var18 = this.UI.mProtocol.getSelectedItemPosition();
               var17.mProtocolValue = var18;
               if(this.UI.ifVerifyScreen3() == 1) {
                  MailAccountUI var19 = this.UI;
                  WeakReference var20 = this.mWeakHandler;
                  var19.checkAccountWithSmtp(this, var20);
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
            this.showDialog(1);
            return;
         }

         this.doUpdateAccount();
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

   private boolean doSaveAccount() {
      ll.d("ecEditAccount", "doSaveAccount edit>");
      if(this.UI.mDefaultChkbox.isChecked()) {
         Account var1 = MailProvider.getAccount(MailProvider.getDefaultAccountId());
         MailProvider.resetDefaultAccount();
      }

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
         ll.d("ecEditAccount", var5);
      }

      Integer var6 = Integer.valueOf(this.UI.VerifyAccount);
      var2.put("AccountVerify", var6);
      Uri var7 = Account.applyBatchAccountToDatabases(this, var2);
      String var8 = "doSaveAccount edit>" + var7;
      ll.d("ecEditAccount", var8);
      boolean var9;
      if(var7 == null) {
         var9 = false;
      } else {
         ecEditAccount.8 var10 = new ecEditAccount.8();
         this.runOnUiThread(var10);
         long var11 = ContentUris.parseId(var7);
         this.mAccountId = var11;
         if(DEBUG) {
            String var13 = " SetAccountID = " + var7;
            ll.d("ecEditAccount", var13);
         }

         Intent var14 = new Intent();
         long var15 = ContentUris.parseId(var7);
         var14.putExtra("accountId", var15);
         this.setResult(4, var14);
         Account var18 = MailProvider.getAccount(this.mAccountId);
         Context var19 = this.getApplicationContext();
         var18.refresh(var19);
         this.finish();
         var9 = true;
      }

      return var9;
   }

   private final void doUpdateAccount() {
      this.showDialog(13);
      ecEditAccount.9 var1 = new ecEditAccount.9();
      (new Thread(var1)).start();
   }

   private void goStep2() {
      this.UI.smartLogin = (boolean)0;
      this.UI.curNewAccountStep = 2;
      this.UI.setScreen2();
      this.UI.checkScreen2();
      this.UI.updateScreen();
      this.UI.btnL.setVisibility(0);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 == 50) {
         switch(this.UI.curNewAccountStep) {
         case 1:
            boolean var4 = this.UI.checkScreen1();
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

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      boolean var2 = this.requestWindowFeature(1);
      this.setContentView(2130903055);
      int var3 = (int)this.getResources().getDimension(2131165225);
      int var4 = (int)this.getResources().getDimension(2131165228);
      this.UI.setScreenMinHeight(var3, var4);
      IntentFilter var5 = new IntentFilter("HTC_IME_CURRENT_STATE");
      this.mIntentfilter = var5;
      BroadcastReceiver var6 = this.mIMERecever;
      IntentFilter var7 = this.mIntentfilter;
      this.registerReceiver(var6, var7);
      MailScrollView var9 = (MailScrollView)this.findViewById(2131296372);
      View var10 = this.findViewById(2131296373);
      var9.setChildView(var10);
      this.UI.mInEditMode = (boolean)1;
      boolean var11 = this.UI.CreateAccountUI(this);
      Button var12 = this.UI.btnL;
      ecEditAccount.1 var13 = new ecEditAccount.1();
      var12.setOnClickListener(var13);
      Button var14 = this.UI.btnR;
      ecEditAccount.2 var15 = new ecEditAccount.2();
      var14.setOnClickListener(var15);
      this.bind();
      this.UI.checkScreen2();
      this.UI.checkScreen4();
      this.UI.curNewAccountStep = 1;
      this.UI.updateScreen();
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 0:
         HtcProgressDialog var29 = new HtcProgressDialog(this);
         String var30 = this.getString(2131362009);
         var29.setMessage(var30);
         var29.setIndeterminate((boolean)1);
         var29.setCancelable((boolean)1);
         OnKeyListener var31 = this.stopCheckAccountListener;
         var29.setOnKeyListener(var31);
         var2 = var29;
         break;
      case 1:
         boolean var24 = ((EditText)this.findViewById(2131296381)).requestFocus();
         Builder var25 = new Builder(this);
         CharSequence var26 = this.getText(2131361879);
         Builder var27 = var25.setTitle(var26);
         String var28 = this.getString(2131362012);
         var2 = var27.setMessage(var28).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)0).create();
         break;
      case 2:
         Builder var46 = new Builder(this);
         CharSequence var47 = this.getText(2131361879);
         Builder var48 = var46.setTitle(var47);
         String var49 = this.getString(2131362008);
         var2 = var48.setMessage(var49).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 3:
         Builder var50 = new Builder(this);
         CharSequence var51 = this.getText(2131361879);
         Builder var52 = var50.setTitle(var51);
         String var53 = this.getString(2131362007);
         Builder var54 = var52.setMessage(var53).setNegativeButton(2131361931, (OnClickListener)null);
         OnClickListener var55 = this.confirmSaveAccountEvent;
         var2 = var54.setPositiveButton(2131362432, var55).setCancelable((boolean)1).create();
         break;
      case 4:
         Builder var56 = new Builder(this);
         CharSequence var57 = this.getText(2131361879);
         Builder var58 = var56.setTitle(var57);
         String var59 = this.getString(2131362006);
         var2 = var58.setMessage(var59).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 5:
         HtcProgressDialog var32 = new HtcProgressDialog(this);
         String var33 = this.getString(2131362010);
         var32.setMessage(var33);
         var32.setIndeterminate((boolean)1);
         var32.setCancelable((boolean)1);
         OnKeyListener var34 = this.stopCheckAccountListener;
         var32.setOnKeyListener(var34);
         var2 = var32;
         break;
      case 6:
         HtcProgressDialog var43 = new HtcProgressDialog(this);
         String var44 = this.getString(2131362011);
         var43.setMessage(var44);
         var43.setIndeterminate((boolean)1);
         var43.setCancelable((boolean)1);
         OnKeyListener var45 = this.stopCheckAccountListener;
         var43.setOnKeyListener(var45);
         var2 = var43;
         break;
      case 7:
         Builder var39 = new Builder(this);
         CharSequence var40 = this.getText(2131361879);
         Builder var41 = var39.setTitle(var40);
         String var42 = this.getString(2131362229);
         var2 = var41.setMessage(var42).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)0).create();
         break;
      case 8:
         Builder var35 = new Builder(this);
         CharSequence var36 = this.getText(2131361879);
         Builder var37 = var35.setTitle(var36);
         String var38 = this.getString(2131362232);
         var2 = var37.setMessage(var38).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)0).create();
         break;
      case 9:
      case 10:
      case 11:
      case 12:
         String[] var3 = new String[4];
         String var4 = this.getString(2131361907);
         var3[0] = var4;
         String var5 = this.getString(2131361908);
         var3[1] = var5;
         String var6 = this.getString(2131361909);
         var3[2] = var6;
         String var7 = this.getString(2131361910);
         var3[3] = var7;
         Builder var8 = new Builder(this);
         CharSequence var9 = this.getText(2131361879);
         Builder var10 = var8.setTitle(var9);
         int var11 = var1 - 9;
         String var12 = var3[var11];
         Builder var13 = var10.setMessage(var12).setNegativeButton(2131361931, (OnClickListener)null);
         OnClickListener var14 = this.confirmChangeAccountEvent;
         var2 = var13.setPositiveButton(2131362432, var14).setCancelable((boolean)1).create();
         break;
      case 13:
         HtcProgressDialog var60 = new HtcProgressDialog(this);
         String var61 = this.getString(2131361911);
         var60.setMessage(var61);
         var60.setIndeterminate((boolean)1);
         var60.setCancelable((boolean)0);
         var2 = var60;
         break;
      case 14:
      default:
         var2 = super.onCreateDialog(var1);
         break;
      case 15:
         boolean var19 = ((EditText)this.findViewById(2131296388)).requestFocus();
         Builder var20 = (new Builder(this)).setIcon(17301543);
         CharSequence var21 = this.getText(2131361879);
         Builder var22 = var20.setTitle(var21);
         String var23 = this.getString(2131362013);
         var2 = var22.setMessage(var23).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 16:
         Builder var62 = new Builder(this);
         CharSequence var63 = this.getText(2131362234);
         Builder var64 = var62.setTitle(var63);
         String var65 = this.getString(2131362233);
         Builder var66 = var64.setMessage(var65);
         OnClickListener var67 = this.gotoSettingListener;
         var2 = var66.setPositiveButton(2131362145, var67).setNegativeButton(2131361931, (OnClickListener)null).setCancelable((boolean)0).create();
         break;
      case 17:
         Builder var68 = new Builder(this);
         CharSequence var69 = this.getText(2131361879);
         Builder var70 = var68.setTitle(var69);
         String var71 = this.getString(2131361932);
         var2 = var70.setMessage(var71).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 18:
         Builder var72 = new Builder(this);
         CharSequence var73 = this.getText(2131361879);
         Builder var74 = var72.setTitle(var73);
         String var75 = this.getString(2131362242);
         var2 = var74.setMessage(var75).setPositiveButton(2131362432, (OnClickListener)null).setCancelable((boolean)1).create();
         break;
      case 19:
         Builder var15 = new Builder(this);
         CharSequence var16 = this.getText(2131361879);
         Builder var17 = var15.setTitle(var16).setMessage(2131361907).setNegativeButton(2131361931, (OnClickListener)null);
         OnClickListener var18 = this.confirmChangeAccountEventInStep1;
         var2 = var17.setPositiveButton(2131362432, var18).setCancelable((boolean)1).create();
      }

      return (Dialog)var2;
   }

   protected final void onDestroy() {
      super.onDestroy();
      this.UI.cancelRequest(this);
      if(this.mIMERecever != null) {
         BroadcastReceiver var1 = this.mIMERecever;
         this.unregisterReceiver(var1);
      }
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      byte var3;
      if(var1 == 4) {
         this.btnFncL();
         var3 = 1;
      } else {
         var3 = super.onKeyDown(var1, var2);
      }

      return (boolean)var3;
   }

   protected void onPause() {
      super.onPause();
      if(DEBUG) {
         ll.i("ecEditAccount", "onPause>");
      }

      this.mOnForeground = (boolean)0;
   }

   protected final void onResume() {
      super.onResume();
      this.mOnForeground = (boolean)1;
      if(this.UI != null) {
         MailAccountUI var1 = this.UI;
         int var2 = this.UI.getSipHeight();
         var1.resetPresentScreenHeight(var2);
      }
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(DialogInterface var1, int var2) {
         boolean var3 = (boolean)(ecEditAccount.this.mDelCurrentAccount = (boolean)1);
         ecEditAccount.this.goStep2();
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         boolean var3 = (boolean)(ecEditAccount.this.mDelCurrentAccount = (boolean)1);
         ecEditAccount var4 = ecEditAccount.this;
         int var5 = ecEditAccount.this.UI.mProtocol.getSelectedItemPosition();
         var4.orgProtocol = var5;
         if(ecEditAccount.this.UI.ifVerifyScreen2() == 1) {
            MailAccountUI var7 = ecEditAccount.this.UI;
            ecEditAccount var8 = ecEditAccount.this;
            WeakReference var9 = ecEditAccount.this.mWeakHandler;
            var7.checkAccountWithPop3(var8, var9);
         } else {
            ecEditAccount.this.UI.curNewAccountStep = 3;
            ecEditAccount.this.UI.checkScreen3();
            ecEditAccount.this.UI.updateScreen();
         }
      }
   }

   class 2 implements android.view.View.OnClickListener {

      2() {}

      public void onClick(View var1) {
         ecEditAccount.this.btnFncR();
      }
   }

   class 1 implements android.view.View.OnClickListener {

      1() {}

      public void onClick(View var1) {
         ecEditAccount.this.btnFncL();
      }
   }

   class 8 implements Runnable {

      8() {}

      public void run() {
         Toast.makeText(ecEditAccount.this, 2131361929, 0).show();
      }
   }

   class 7 implements OnClickListener {

      7() {}

      public void onClick(DialogInterface var1, int var2) {
         ecEditAccount.this.doUpdateAccount();
      }
   }

   class 6 implements OnKeyListener {

      6() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         boolean var6;
         if(var2 == 4) {
            if(!ecEditAccount.this.UI.mUserCancelCheck) {
               ecEditAccount.this.UI.mUserCancelCheck = (boolean)1;
               ll.d("JerryDebug", "cancelCheck is true");
               ecEditAccount.this.UI.CancelCheckAccount();
            }

            ecEditAccount var4 = ecEditAccount.this;
            MailAccountUI var5 = ecEditAccount.this.UI;
            var4.dismissDialog(0);
            var6 = true;
         } else {
            var6 = false;
         }

         return var6;
      }
   }

   class 5 implements OnClickListener {

      5() {}

      public void onClick(DialogInterface var1, int var2) {
         Intent var3 = new Intent("android.intent.action.VIEW");
         ComponentName var4 = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
         var3.setComponent(var4);
         ecEditAccount.this.startActivityForResult(var3, 50);
      }
   }

   class 10 extends BroadcastReceiver {

      10() {}

      public void onReceive(Context var1, Intent var2) {
         MailAccountUI var3 = ecEditAccount.this.UI;
         boolean var4 = ecEditAccount.this.mOnForeground;
         var3.onIMEReceive(var2, var4);
      }
   }

   class 9 implements Runnable {

      9() {}

      public void run() {
         if(ecEditAccount.DEBUG) {
            ll.d("ecEditAccount", "doUpdateAccount bg>");
         }

         IContentProvider var1 = MailProvider.instance();
         if(ecEditAccount.this.mDelCurrentAccount) {
            try {
               boolean var2 = (boolean)(ecEditAccount.this.mDelCurrentAccount = (boolean)0);
               AccountPool var3 = AccountPool.getInstance(ecEditAccount.this);
               Context var4 = ecEditAccount.this.getApplicationContext();
               long var5 = ecEditAccount.this.mAccountId;
               var3.removeAccount(var4, var5);
               StringBuilder var7 = new StringBuilder();
               Uri var8 = MailProvider.sAccountsURI;
               StringBuilder var9 = var7.append(var8).append("/");
               long var10 = ecEditAccount.this.mAccountId;
               Uri var12 = Uri.parse(var9.append(var10).toString());
               var1.delete(var12, (String)null, (String[])null);
            } catch (DeadObjectException var49) {
               ;
            } catch (RemoteException var50) {
               ;
            }

            if(ecEditAccount.this.doSaveAccount()) {
               MailAccountUI var14 = ecEditAccount.this.UI;
               String var15 = ecEditAccount.this.UI.mEmailAddress.getText().toString();
               var14.updateEmailAddrToPublicAccount(var15);
            }
         } else {
            if(ecEditAccount.this.UI.mDefaultChkbox.isChecked()) {
               MailProvider.getAccount(MailProvider.getDefaultAccountId()).defaultaccount = 0;
               MailProvider.resetDefaultAccount();
            }

            try {
               StringBuilder var16 = new StringBuilder();
               Uri var17 = MailProvider.sAccountsURI;
               StringBuilder var18 = var16.append(var17).append("/");
               long var19 = ecEditAccount.this.mAccountId;
               Uri var21 = Uri.parse(var18.append(var19).toString());
               ContentValues var22 = ecEditAccount.this.UI.gatherValues((boolean)0);
               var1.update(var21, var22, (String)null, (String[])null);
            } catch (DeadObjectException var51) {
               ;
            } catch (RemoteException var52) {
               ;
            }

            ContentValues var24 = new ContentValues();
            var24.clear();
            Integer var25 = Integer.valueOf(ecEditAccount.this.UI.VerifyAccount);
            var24.put("AccountVerify", var25);
            ContentResolver var26 = ecEditAccount.this.getContentResolver();
            StringBuilder var27 = new StringBuilder();
            Uri var28 = MailProvider.AccountVerify;
            StringBuilder var29 = var27.append(var28).append("/");
            long var30 = ecEditAccount.this.mAccountId;
            Uri var32 = Uri.parse(var29.append(var30).toString());
            var26.update(var32, var24, (String)null, (String[])null);
            Mail.setServicesEnabled(ecEditAccount.this);
            Mail.mMailEvent.setAccountChange();
            MailEventBroadcaster var34 = Mail.mMailEvent;
            long var35 = ecEditAccount.this.mAccountId;
            var34.setAccountID(var35);
            MailEventBroadcaster var37 = Mail.mMailEvent;
            Context var38 = ecEditAccount.this.getApplicationContext();
            var37.flush(var38);
            ecEditAccount.this.setResult(-1);
            Account var39 = MailProvider.getAccount(ecEditAccount.this.mAccountId);
            Context var40 = ecEditAccount.this.getApplicationContext();
            var39.refresh(var40);
            MailAccountUI var41 = ecEditAccount.this.UI;
            String var42 = ecEditAccount.this.UI.mEmailAddress.getText().toString();
            var41.updateEmailAddrToPublicAccount(var42);
            ecEditAccount var43 = ecEditAccount.this;
            ecEditAccount.9.1 var44 = new ecEditAccount.9.1();
            var43.runOnUiThread(var44);
            ecEditAccount.this.finish();
         }
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            Toast.makeText(ecEditAccount.this, 2131361929, 0).show();
         }
      }
   }
}
