package com.android.email.activity.setup;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.email.SecurityPolicy;
import com.android.email.provider.EmailContent;

public class AccountSecurity extends Activity {

   private static final String EXTRA_ACCOUNT_ID = "com.android.email.activity.setup.ACCOUNT_ID";
   private static final int REQUEST_ENABLE = 1;


   public AccountSecurity() {}

   public static Intent actionUpdateSecurityIntent(Context var0, long var1) {
      Intent var3 = new Intent(var0, AccountSecurity.class);
      var3.putExtra("com.android.email.activity.setup.ACCOUNT_ID", var1);
      return var3;
   }

   private void setActivePolicies() {
      SecurityPolicy var1 = SecurityPolicy.getInstance(this);
      if(var1.isActive((SecurityPolicy.PolicySet)null)) {
         var1.clearAccountHoldFlags();
      } else {
         var1.setActivePolicies();
         if(var1.isActive((SecurityPolicy.PolicySet)null)) {
            var1.clearAccountHoldFlags();
         } else {
            Intent var2 = new Intent("android.app.action.SET_NEW_PASSWORD");
            this.startActivity(var2);
         }
      }
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      switch(var1) {
      case 1:
         if(var2 == -1) {
            this.setActivePolicies();
         } else {
            long var4 = this.getIntent().getLongExtra("com.android.email.activity.setup.ACCOUNT_ID", 65535L);
            if(var4 != 65535L) {
               (new AccountSecurity.1(var4)).start();
            }
         }
      default:
         this.finish();
         super.onActivityResult(var1, var2, var3);
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      long var2 = this.getIntent().getLongExtra("com.android.email.activity.setup.ACCOUNT_ID", 65535L);
      SecurityPolicy var4 = SecurityPolicy.getInstance(this);
      var4.clearNotification(var2);
      if(var2 != 65535L) {
         EmailContent.Account var5 = EmailContent.Account.restoreAccountWithId(this, var2);
         if(var5 != null && var5.mSecurityFlags != 0) {
            if(!var4.isActiveAdmin()) {
               long var6 = var5.mHostAuthKeyRecv;
               EmailContent.HostAuth var8 = EmailContent.HostAuth.restoreHostAuthWithId(this, var6);
               if(var8 != null) {
                  Intent var9 = new Intent("android.app.action.ADD_DEVICE_ADMIN");
                  ComponentName var10 = var4.getAdminComponent();
                  var9.putExtra("android.app.extra.DEVICE_ADMIN", var10);
                  Object[] var12 = new Object[1];
                  String var13 = var8.mAddress;
                  var12[0] = var13;
                  String var14 = this.getString(2131165403, var12);
                  var9.putExtra("android.app.extra.ADD_EXPLANATION", var14);
                  this.startActivityForResult(var9, 1);
                  return;
               }
            } else {
               this.setActivePolicies();
            }
         }
      }

      this.finish();
   }

   class 1 extends Thread {

      // $FF: synthetic field
      final long val$accountId;


      1(long var2) {
         this.val$accountId = var2;
      }

      public void run() {
         SecurityPolicy var1 = SecurityPolicy.getInstance(AccountSecurity.this);
         long var2 = this.val$accountId;
         var1.policiesRequired(var2);
      }
   }
}
