package com.android.email.service;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import com.android.email.activity.setup.AccountSetupBasics;

public class EasAuthenticatorService extends Service {

   public static final String OPTIONS_CALENDAR_SYNC_ENABLED = "calendar";
   public static final String OPTIONS_CONTACTS_SYNC_ENABLED = "contacts";
   public static final String OPTIONS_PASSWORD = "password";
   public static final String OPTIONS_USERNAME = "username";


   public EasAuthenticatorService() {}

   public IBinder onBind(Intent var1) {
      String var2 = var1.getAction();
      IBinder var3;
      if("android.accounts.AccountAuthenticator".equals(var2)) {
         var3 = (new EasAuthenticatorService.EasAuthenticator(this)).getIBinder();
      } else {
         var3 = null;
      }

      return var3;
   }

   class EasAuthenticator extends AbstractAccountAuthenticator {

      public EasAuthenticator(Context var2) {
         super(var2);
      }

      public Bundle addAccount(AccountAuthenticatorResponse var1, String var2, String var3, String[] var4, Bundle var5) throws NetworkErrorException {
         Bundle var13;
         if(var5 != null && var5.containsKey("password") && var5.containsKey("username")) {
            String var6 = var5.getString("username");
            Account var7 = new Account(var6, "com.android.exchange");
            AccountManager var8 = AccountManager.get(EasAuthenticatorService.this);
            String var9 = var5.getString("password");
            var8.addAccountExplicitly(var7, var9, (Bundle)null);
            byte var11 = 0;
            if(var5.containsKey("contacts") && var5.getBoolean("contacts")) {
               var11 = 1;
            }

            ContentResolver.setIsSyncable(var7, "com.android.contacts", 1);
            ContentResolver.setSyncAutomatically(var7, "com.android.contacts", (boolean)var11);
            byte var12 = 0;
            if(var5.containsKey("calendar") && var5.getBoolean("calendar")) {
               var12 = 1;
            }

            ContentResolver.setIsSyncable(var7, "com.android.calendar", 1);
            ContentResolver.setSyncAutomatically(var7, "com.android.calendar", (boolean)var12);
            var13 = new Bundle();
            String var14 = var5.getString("username");
            var13.putString("authAccount", var14);
            var13.putString("accountType", "com.android.exchange");
         } else {
            var13 = new Bundle();
            Intent var15 = AccountSetupBasics.actionSetupExchangeIntent(EasAuthenticatorService.this, (Bitmap)null, (boolean)1);
            var15.putExtra("accountAuthenticatorResponse", var1);
            var13.putParcelable("intent", var15);
         }

         return var13;
      }

      public Bundle confirmCredentials(AccountAuthenticatorResponse var1, Account var2, Bundle var3) {
         return null;
      }

      public Bundle editProperties(AccountAuthenticatorResponse var1, String var2) {
         return null;
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

      public Bundle updateCredentials(AccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) {
         return null;
      }
   }
}
