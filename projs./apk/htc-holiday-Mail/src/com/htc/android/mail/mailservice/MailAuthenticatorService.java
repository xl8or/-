package com.htc.android.mail.mailservice;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.htc.android.mail.AccountPool;
import com.htc.android.mail.ecNewAccount;

public class MailAuthenticatorService extends Service {

   private MailAuthenticatorService.MailAuthenticator mMailAuthenticator;


   public MailAuthenticatorService() {}

   public IBinder onBind(Intent var1) {
      IBinder var2;
      if(var1 == null) {
         var2 = null;
      } else {
         String var3 = var1.getAction();
         if("android.accounts.AccountAuthenticator".equals(var3)) {
            var2 = this.mMailAuthenticator.getIBinder();
         } else {
            var2 = null;
         }
      }

      return var2;
   }

   public void onCreate() {
      super.onCreate();
      MailAuthenticatorService.MailAuthenticator var1 = new MailAuthenticatorService.MailAuthenticator(this);
      this.mMailAuthenticator = var1;
   }

   class MailAuthenticator extends AbstractAccountAuthenticator {

      private Context mContext;


      public MailAuthenticator(Context var2) {
         super(var2);
         this.mContext = var2;
      }

      public Bundle addAccount(AccountAuthenticatorResponse var1, String var2, String var3, String[] var4, Bundle var5) throws NetworkErrorException {
         MailAuthenticatorService var6 = MailAuthenticatorService.this;
         Intent var7 = new Intent(var6, ecNewAccount.class);
         var7.putExtra("accountAuthenticatorResponse", var1);
         Intent var9 = var7.putExtra("CallingActivity", 96);
         Intent var10 = var7.putExtra("provider", "other");
         Bundle var11 = new Bundle();
         var11.putParcelable("intent", var7);
         return var11;
      }

      public Bundle confirmCredentials(AccountAuthenticatorResponse var1, Account var2, Bundle var3) throws NetworkErrorException {
         return null;
      }

      public Bundle editProperties(AccountAuthenticatorResponse var1, String var2) {
         return null;
      }

      public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse var1, Account var2) throws NetworkErrorException {
         AccountPool var3 = AccountPool.getInstance(this.mContext);
         Context var4 = this.mContext;
         com.htc.android.mail.Account var5 = var3.getAccount(var4, var2);
         if(var5 != null) {
            Context var6 = this.mContext;
            var5.delete(var6);
         }

         return super.getAccountRemovalAllowed(var1, var2);
      }

      public Bundle getAuthToken(AccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) throws NetworkErrorException {
         return null;
      }

      public String getAuthTokenLabel(String var1) {
         return null;
      }

      public Bundle hasFeatures(AccountAuthenticatorResponse var1, Account var2, String[] var3) throws NetworkErrorException {
         return null;
      }

      public Bundle updateCredentials(AccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) throws NetworkErrorException {
         return null;
      }
   }
}
