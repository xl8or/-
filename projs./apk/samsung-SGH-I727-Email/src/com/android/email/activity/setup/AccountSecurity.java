package com.android.email.activity.setup;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.email.SecurityPolicy;
import com.android.email.provider.EmailContent;
import com.android.exchange.security.ode.ODEService;

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
            Intent var2 = new Intent(this, ODEService.class);
            Intent var3 = var2.putExtra("startProc", "onPolicyChanged");
            this.startService(var2);
         } else {
            Intent var5 = new Intent("android.app.action.SET_NEW_PASSWORD");
            Intent var6 = var5.putExtra("email_device_policy", "email");
            this.startActivity(var5);
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
         if(var5 != null && EmailContent.Policies.getNumberOfPoliciesForAccount(this, var2) > 0) {
            if(!var4.isActiveAdmin()) {
               Intent var6 = new Intent("android.app.action.ADD_DEVICE_ADMIN");
               ComponentName var7 = var4.getAdminComponent();
               var6.putExtra("android.app.extra.DEVICE_ADMIN", var7);
               Object[] var9 = new Object[1];
               String var10 = var5.getDisplayName();
               var9[0] = var10;
               String var11 = this.getString(2131167074, var9);
               var6.putExtra("android.app.extra.ADD_EXPLANATION", var11);
               this.startActivityForResult(var6, 1);
               return;
            }

            this.setActivePolicies();
         }
      }

      this.finish();
   }

   public boolean onSearchRequested() {
      return false;
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
