package com.android.email.activity.setup;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.email.Email;
import com.android.email.EmailTwSoftkeyItem;
import com.android.email.combined.common.ExceptionUtil;
import com.digc.seven.Z7MailHandler;
import com.seven.Z7.app.Z7AppBaseActivity;
import com.seven.Z7.shared.ANSharedCommon;
import com.seven.Z7.shared.Z7ServiceConstants;
import com.seven.Z7.util.Z7ErrorCode2;

public class AccountSetupCheckSettingsPremium extends Z7AppBaseActivity implements OnClickListener {

   private static final String EXTRA_EMAIL_PASSWORD = "extra_email_password";
   private static final String EXTRA_SEVEN_ACCOUNT_KEY = "extra_seven_account_key";
   public static final int REQUEST_CODE_RELOGIN = 10;
   private boolean mDestroyed;
   private AccountSetupCheckSettingsPremium.MyListener mListener;
   private TextView mMessageView;
   private String mPassword;
   private ProgressBar mProgressBar;
   private int mSevenAccountKey;


   public AccountSetupCheckSettingsPremium() {}

   public static void actionValidateSettings(Activity var0, int var1, String var2) {
      Intent var3 = new Intent(var0, AccountSetupCheckSettingsPremium.class);
      var3.putExtra("extra_seven_account_key", var1);
      var3.putExtra("extra_email_password", var2);
      var0.startActivityForResult(var3, 10);
   }

   private void onCancel() {
      Z7MailHandler var1 = this.zHandler;
      AccountSetupCheckSettingsPremium.MyListener var2 = this.mListener;
      var1.unRegisterListener(var2);
      this.mProgressBar.setIndeterminate((boolean)1);
      this.setMessage(2131167056);
      this.onCloseActivity();
   }

   private void onCloseActivity() {
      Handler var1 = this.handler;
      AccountSetupCheckSettingsPremium.3 var2 = new AccountSetupCheckSettingsPremium.3();
      long var3 = SystemClock.uptimeMillis() + 1000L;
      var1.postAtTime(var2, var3);
   }

   private void setMessage(int var1) {
      Handler var2 = this.handler;
      AccountSetupCheckSettingsPremium.2 var3 = new AccountSetupCheckSettingsPremium.2(var1);
      var2.post(var3);
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131361865:
         this.onCancel();
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903050);
      TextView var2 = (TextView)this.findViewById(2131361863);
      this.mMessageView = var2;
      ProgressBar var3 = (ProgressBar)this.findViewById(2131361864);
      this.mProgressBar = var3;
      ((EmailTwSoftkeyItem)this.findViewById(2131361865)).setOnClickListener(this);
      this.setMessage(2131167053);
      this.mProgressBar.setIndeterminate((boolean)1);
      Intent var4 = this.getIntent();
      int var5 = var4.getIntExtra("extra_seven_account_key", -1);
      this.mSevenAccountKey = var5;
      String var6 = var4.getStringExtra("extra_email_password");
      this.mPassword = var6;
      Handler var7 = this.handler;
      AccountSetupCheckSettingsPremium.MyListener var8 = new AccountSetupCheckSettingsPremium.MyListener(var7);
      this.mListener = var8;
      (new AccountSetupCheckSettingsPremium.1()).start();
   }

   public void onDestroy() {
      super.onDestroy();
      Z7MailHandler var1 = this.zHandler;
      AccountSetupCheckSettingsPremium.MyListener var2 = this.mListener;
      var1.unRegisterListener(var2);
      this.mDestroyed = (boolean)1;
   }

   public boolean onSearchRequested() {
      return false;
   }

   private class MyListener extends Email.Z7ConnectionListener {

      public MyListener(Handler var2) {
         super(var2);
      }

      public void onCallback(Bundle var1) {
         Z7ServiceConstants.SystemCallbackType var2 = Z7ServiceConstants.SystemCallbackType.fromId(var1.getInt("event-id"));
         if(var2 != null) {
            AccountSetupCheckSettingsPremium.MyListener.1 var3 = new AccountSetupCheckSettingsPremium.MyListener.1();
            int[] var4 = AccountSetupCheckSettingsPremium.4.$SwitchMap$com$seven$Z7$shared$Z7ServiceConstants$SystemCallbackType;
            int var5 = var2.ordinal();
            switch(var4[var5]) {
            case 1:
               int var6 = var1.getInt("result");
               int var7 = AccountSetupCheckSettingsPremium.this.mSevenAccountKey;
               if(var6 != var7) {
                  return;
               } else {
                  if(!var1.getBoolean("obj", (boolean)0)) {
                     return;
                  }

                  AccountSetupCheckSettingsPremium.this.setResult(-1);
                  AccountSetupCheckSettingsPremium.this.finish();
                  return;
               }
            case 2:
               int var8 = var1.getInt("result");
               int var9 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SUBSCRIPTION_EXPIRED.getValue();
               if(var8 == var9) {
                  AccountSetupCheckSettingsPremium.this.showDialog(10002);
                  return;
               } else {
                  int var10 = var1.getInt("result");
                  int var11 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_LOGIN_FAILED_CE.getValue();
                  if(var10 == var11) {
                     AccountSetupCheckSettingsPremium var12 = AccountSetupCheckSettingsPremium.this;
                     String var13 = ANSharedCommon.getErrorTitle(var1.getInt("result"));
                     String var14 = var1.getString("obj");
                     var12.showPopupReport(var13, var14, var3);
                     return;
                  } else {
                     int var15 = var1.getInt("result");
                     int var16 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SUBSCRIPTION_TRIALS_NOT_ACCEPTED.getValue();
                     if(var15 == var16) {
                        AccountSetupCheckSettingsPremium.this.showDialog(10004);
                        return;
                     } else {
                        int var17 = var1.getInt("result");
                        int var18 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SUBSCRIPTION_LIMIT_EXCEEDED.getValue();
                        if(var17 == var18) {
                           AccountSetupCheckSettingsPremium.this.showDialog(10005);
                           return;
                        } else {
                           int var19 = var1.getInt("result");
                           int var20 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SERVICE_SUBSCRIPTION_REQUIRED.getValue();
                           if(var19 == var20) {
                              AccountSetupCheckSettingsPremium.this.showDialog(10003);
                              return;
                           }

                           String var21 = ANSharedCommon.getErrorTitle(var1.getInt("result"));
                           String var22 = var1.getString("obj");
                           if(var21 == null || var21.length() == 0) {
                              var21 = AccountSetupCheckSettingsPremium.this.getString(2131165652);
                           }

                           if(var22 == null || var22.length() == 0) {
                              var22 = AccountSetupCheckSettingsPremium.this.getString(2131165674);
                           }

                           AccountSetupCheckSettingsPremium.this.showPopupReport(var21, var22, var3);
                           return;
                        }
                     }
                  }
               }
            default:
            }
         }
      }

      class 1 implements android.content.DialogInterface.OnClickListener {

         1() {}

         public void onClick(DialogInterface var1, int var2) {
            AccountSetupCheckSettingsPremium.this.setResult(0);
            AccountSetupCheckSettingsPremium.this.finish();
         }
      }
   }

   // $FF: synthetic class
   static class 4 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$seven$Z7$shared$Z7ServiceConstants$SystemCallbackType = new int[Z7ServiceConstants.SystemCallbackType.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$seven$Z7$shared$Z7ServiceConstants$SystemCallbackType;
            int var1 = Z7ServiceConstants.SystemCallbackType.Z7_CALLBACK_LOGIN_RESULT.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$seven$Z7$shared$Z7ServiceConstants$SystemCallbackType;
            int var3 = Z7ServiceConstants.SystemCallbackType.Z7_CALLBACK_PROVISIONING_FAILED.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }

   class 3 implements Runnable {

      3() {}

      public void run() {
         if(!AccountSetupCheckSettingsPremium.this.mDestroyed) {
            AccountSetupCheckSettingsPremium.this.finish();
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
         if(!AccountSetupCheckSettingsPremium.this.mDestroyed) {
            TextView var1 = AccountSetupCheckSettingsPremium.this.mMessageView;
            AccountSetupCheckSettingsPremium var2 = AccountSetupCheckSettingsPremium.this;
            int var3 = this.val$resId;
            String var4 = var2.getString(var3);
            var1.setText(var4);
         }
      }
   }

   class 1 extends Thread {

      1() {}

      public void run() {
         try {
            Z7MailHandler var1 = AccountSetupCheckSettingsPremium.this.zHandler;
            AccountSetupCheckSettingsPremium.MyListener var2 = AccountSetupCheckSettingsPremium.this.mListener;
            var1.registerListener(var2);
            Z7MailHandler var3 = AccountSetupCheckSettingsPremium.this.zHandler;
            int var4 = AccountSetupCheckSettingsPremium.this.mSevenAccountKey;
            String var5 = AccountSetupCheckSettingsPremium.this.mPassword;
            var3.relogin(var4, var5);
         } catch (RemoteException var10) {
            AccountSetupCheckSettingsPremium var8 = AccountSetupCheckSettingsPremium.this;
            AccountSetupCheckSettingsPremium.1.1 var9 = new AccountSetupCheckSettingsPremium.1.1(var10);
            var8.runOnUiThread(var9);
         }
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final RemoteException val$e;


         1(RemoteException var2) {
            this.val$e = var2;
         }

         public void run() {
            AccountSetupCheckSettingsPremium var1 = AccountSetupCheckSettingsPremium.this;
            RemoteException var2 = this.val$e;
            ExceptionUtil.showDialogException(var1, var2);
         }
      }
   }
}
