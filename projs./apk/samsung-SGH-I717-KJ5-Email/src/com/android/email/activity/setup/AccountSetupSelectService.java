package com.android.email.activity.setup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.android.email.Email;
import com.android.email.EmailTwSoftkeyItem;
import com.android.email.activity.setup.AccountSetupBasics;
import com.android.email.activity.setup.AccountSetupCustomer;
import com.android.email.combined.common.ExceptionUtil;
import com.digc.seven.SevenSyncProvider;
import com.digc.seven.Z7MailHandler;
import com.seven.Z7.shared.ANSharedCommon;

public class AccountSetupSelectService extends Activity implements OnClickListener {

   public static final int REQUEST_SSO_ADD_ACCOUNT = 300;
   private String accountEmail;
   private long accountId;
   private Context mContext;
   private int sevenAccountKey;
   private CheckBox signonIM;
   private LinearLayout signonIMLayout;
   private String sso_id;
   private String sso_isp;
   private String sso_pw;
   private CheckBox syncCalendar;
   private LinearLayout syncCalendarLayout;
   private CheckBox syncContacts;
   private LinearLayout syncContactsLayout;
   private Z7MailHandler zHandler;


   public AccountSetupSelectService() {}

   private void actionForFinish() {
      // $FF: Couldn't be decompiled
   }

   public static void actionSetNames(Activity var0, long var1, boolean var3, String var4, String var5, String var6, int var7) {
      Intent var8 = new Intent(var0, AccountSetupSelectService.class);
      var8.putExtra("accountId", var1);
      var8.putExtra("easFlow", var3);
      var8.putExtra("SSO_ID", var4);
      var8.putExtra("SSO_PW", var5);
      var8.putExtra("SSO_ISP", var6);
      var8.putExtra("account_key", var1);
      var8.putExtra("account_email", var4);
      var8.putExtra("seven_account_key", var7);
      var0.startActivity(var8);
   }

   private boolean isAccountForGmail() {
      boolean var1;
      if(this.sevenAccountKey != -1) {
         var1 = ANSharedCommon.getAccountName(this.sevenAccountKey).equals("gmail");
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isAccountForMsn() {
      boolean var1;
      if(this.sevenAccountKey != -1) {
         var1 = ANSharedCommon.getAccountName(this.sevenAccountKey).equals("msn");
      } else {
         var1 = false;
      }

      return var1;
   }

   public void actionAccountToIM() {
      Intent var1 = new Intent("com.seven.Z7.ACTION_IM_SSO");
      if(this.sso_isp.equals("gmail")) {
         this.sso_isp = "google";
      } else if(this.sso_isp.equals("msn")) {
         this.sso_isp = "ms";
      } else if(this.sso_isp.equals("yahoo")) {
         this.sso_isp = "yahoo";
      }

      String var2 = this.sso_id;
      var1.putExtra("SSO_ID", var2);
      String var4 = this.sso_pw;
      var1.putExtra("SSO_PW", var4);
      String var6 = this.sso_isp;
      var1.putExtra("SSO_ISP", var6);
      this.startActivityForResult(var1, 300);
   }

   public boolean isExistIMPackage() {
      Intent var1 = new Intent();
      Intent var2 = var1.setAction("com.seven.Z7.ACTION_IM");
      Intent var3 = var1.setFlags(69206016);
      boolean var4;
      if(this.mContext.getPackageManager().queryIntentActivities(var1, 0).isEmpty()) {
         var4 = false;
      } else {
         var4 = true;
      }

      return var4;
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 == 300) {
         Email.logd("AccountSetupSelectService", "onActivityResult actionForFinish  ");
         this.actionForFinish();
      }
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131361880:
         try {
            if(this.syncContacts.isChecked()) {
               Z7MailHandler var2 = this.zHandler;
               int var3 = this.sevenAccountKey;
               var2.setServiceState(var3, 258, 1);
            }

            if(this.syncCalendar != null && this.syncCalendar.isChecked()) {
               Z7MailHandler var5 = this.zHandler;
               int var6 = this.sevenAccountKey;
               var5.setServiceState(var6, 257, 1);
            }
         } catch (RemoteException var11) {
            AccountSetupSelectService.1 var9 = new AccountSetupSelectService.1();
            AccountSetupSelectService.2 var10 = new AccountSetupSelectService.2();
            ExceptionUtil.showDialogException(this, var11, var9, var10);
            return;
         }

         if(this.signonIM != null && this.signonIM.isChecked()) {
            this.actionAccountToIM();
            Email.logd("AccountSetupSelectService", "actionAccountToIM ");
            return;
         }

         if(AccountSetupBasics.sso_fromIM) {
            this.setResult(300);
         }

         Email.logd("AccountSetupSelectService", "actionForFinish  ");
         this.actionForFinish();
         return;
      case 2131361957:
         if(this.syncContacts.isChecked()) {
            this.syncContacts.setChecked((boolean)0);
            return;
         }

         this.syncContacts.setChecked((boolean)1);
         return;
      case 2131361964:
         if(this.syncCalendar.isChecked()) {
            this.syncCalendar.setChecked((boolean)0);
            return;
         }

         this.syncCalendar.setChecked((boolean)1);
         return;
      case 2131361969:
         if(this.signonIM.isChecked()) {
            this.signonIM.setChecked((boolean)0);
            return;
         }

         this.signonIM.setChecked((boolean)1);
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.mContext = this;
      Intent var2 = this.getIntent();
      long var3 = var2.getLongExtra("account_key", 65535L);
      this.accountId = var3;
      String var5 = var2.getStringExtra("account_email");
      this.accountEmail = var5;
      int var6 = var2.getIntExtra("seven_account_key", -1);
      this.sevenAccountKey = var6;
      String var7 = var2.getStringExtra("SSO_ID");
      this.sso_id = var7;
      String var8 = var2.getStringExtra("SSO_PW");
      this.sso_pw = var8;
      String var9 = var2.getStringExtra("SSO_ISP");
      this.sso_isp = var9;
      this.setContentView(2130903066);
      String var10 = this.accountEmail;
      this.setTitle(var10);
      CheckBox var11 = (CheckBox)this.findViewById(2131361962);
      this.syncContacts = var11;
      LinearLayout var12 = (LinearLayout)this.findViewById(2131361957);
      this.syncContactsLayout = var12;
      LinearLayout var13 = (LinearLayout)this.findViewById(2131361964);
      this.syncCalendarLayout = var13;
      LinearLayout var14 = (LinearLayout)this.findViewById(2131361969);
      this.signonIMLayout = var14;
      this.syncContactsLayout.setOnClickListener(this);
      this.syncCalendarLayout.setOnClickListener(this);
      this.signonIMLayout.setOnClickListener(this);
      if(this.isAccountForGmail()) {
         this.findViewById(2131361964).setVisibility(8);
         this.findViewById(2131361957).setVisibility(8);
      } else if(this.isAccountForMsn()) {
         this.findViewById(2131361964).setVisibility(8);
      } else {
         CheckBox var17 = (CheckBox)this.findViewById(2131361968);
         this.syncCalendar = var17;
      }

      if(this.isExistIMPackage()) {
         CheckBox var15 = (CheckBox)this.findViewById(2131361973);
         this.signonIM = var15;
         if(AccountSetupBasics.sso_fromIM) {
            this.findViewById(2131361969).setVisibility(8);
         } else {
            this.findViewById(2131361969).setVisibility(0);
         }

         AccountSetupBasics.sso_fromIM = (boolean)0;
      } else {
         this.signonIMLayout.setVisibility(8);
      }

      ((EmailTwSoftkeyItem)this.findViewById(2131361880)).setOnClickListener(this);
      Z7MailHandler var16 = Z7MailHandler.getInstance(this);
      this.zHandler = var16;
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      byte var3;
      if(var1 == 84) {
         var3 = 1;
      } else if(var1 == 4) {
         this.actionForFinish();
         var3 = 1;
      } else {
         var3 = super.onKeyDown(var1, var2);
      }

      return (boolean)var3;
   }

   protected void onRestoreInstanceState(Bundle var1) {
      super.onRestoreInstanceState(var1);
      if(AccountSetupCustomer.getInstance().getEmailType() == 1) {
         if(SevenSyncProvider.checkSevenApkVer(this)) {
            ;
         }
      }
   }

   class 1 implements android.content.DialogInterface.OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         AccountSetupSelectService.this.actionForFinish();
      }
   }

   class 2 implements OnKeyListener {

      2() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         if(var2 == 4) {
            var1.dismiss();
            AccountSetupSelectService.this.actionForFinish();
         }

         return false;
      }
   }
}
