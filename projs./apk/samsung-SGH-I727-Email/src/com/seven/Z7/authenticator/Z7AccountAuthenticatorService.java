package com.seven.Z7.authenticator;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class Z7AccountAuthenticatorService extends Service {

   protected Z7AccountAuthenticatorService.Z7AccountAuthenticator mContactsAuthenticator;


   public Z7AccountAuthenticatorService() {}

   protected Z7AccountAuthenticatorService.Z7AccountAuthenticator getAuthenticator() {
      if(this.mContactsAuthenticator == null) {
         Z7AccountAuthenticatorService.Z7AccountAuthenticator var1 = new Z7AccountAuthenticatorService.Z7AccountAuthenticator(this);
         this.mContactsAuthenticator = var1;
      }

      return this.mContactsAuthenticator;
   }

   public IBinder onBind(Intent var1) {
      IBinder var2;
      if(var1.getAction().equals("android.accounts.AccountAuthenticator")) {
         var2 = this.getAuthenticator().getIBinder();
      } else {
         var2 = null;
      }

      return var2;
   }

   public static class MSNAuthenticatorService extends Z7AccountAuthenticatorService {

      public MSNAuthenticatorService() {}
   }

   public static class YahooAuthenticatorService extends Z7AccountAuthenticatorService {

      public YahooAuthenticatorService() {}
   }

   public static class GmailAuthenticatorService extends Z7AccountAuthenticatorService {

      public GmailAuthenticatorService() {}
   }

   public static class WorkAuthenticatorService extends Z7AccountAuthenticatorService {

      public WorkAuthenticatorService() {}
   }

   public static class IMAuthenticatorService extends Z7AccountAuthenticatorService {

      public IMAuthenticatorService() {}
   }

   public class Z7AccountAuthenticator extends AbstractAccountAuthenticator {

      private String Z7TAG = "Z7ContactsAccountAuthenticator";


      public Z7AccountAuthenticator(Context var2) {
         super(var2);
      }

      public void LogPrint(String var1) {
         Log.d(this.Z7TAG, var1);
      }

      public Bundle addAccount(AccountAuthenticatorResponse var1, String var2, String var3, String[] var4, Bundle var5) throws NetworkErrorException {
         String var6 = "Got request to add account with type " + var2 + ", token type " + var3;
         this.LogPrint(var6);
         Bundle var7 = new Bundle();
         Intent var8 = new Intent("intent.seven.action.ADD_ACCOUNT");
         Intent var9 = var8.setFlags(268435456);
         var8.putExtra("accountType", var2);
         var8.putExtra("accountAuthenticatorResponse", var1);
         byte var12;
         if("com.seven.Z7.im".equals(var2)) {
            var12 = 2;
         } else {
            var12 = 1;
         }

         var8.putExtra("filter", var12);
         var7.putParcelable("intent", var8);
         return var7;
      }

      public Bundle confirmCredentials(AccountAuthenticatorResponse var1, Account var2, Bundle var3) {
         this.LogPrint("confirmCredentials -- not implemented");
         return null;
      }

      public Bundle editProperties(AccountAuthenticatorResponse var1, String var2) {
         this.LogPrint("editProperties -- not implemented");
         return null;
      }

      public Bundle getAuthToken(AccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) throws NetworkErrorException {
         if(var3.isEmpty()) {
            this.LogPrint("getAuthToken -- not implemented");
         } else {
            String var5 = "getAuthToken -- not implemented, authTokenType = " + var3;
            this.LogPrint(var5);
         }

         return null;
      }

      public String getAuthTokenLabel(String var1) {
         if(var1.isEmpty()) {
            this.LogPrint("getAuthTokenLabel -- not implemented");
         } else {
            String var2 = "getAuthTokenLabel -- not implemented, authTokenType = " + var1;
            this.LogPrint(var2);
         }

         return null;
      }

      public Bundle hasFeatures(AccountAuthenticatorResponse var1, Account var2, String[] var3) throws NetworkErrorException {
         this.LogPrint("hasFeatures -- not implemented");
         return null;
      }

      public Bundle updateCredentials(AccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) {
         this.LogPrint("updateCredentials -- not implemented");
         return null;
      }
   }

   public static class AOLAuthenticatorService extends Z7AccountAuthenticatorService {

      public AOLAuthenticatorService() {}
   }
}
