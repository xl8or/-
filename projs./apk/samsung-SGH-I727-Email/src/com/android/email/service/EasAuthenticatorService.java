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
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.android.email.activity.setup.AccountSetupBasics;
import com.android.exchange.EasSyncService;

public class EasAuthenticatorService extends Service {

   public static final String OPTIONS_CALENDAR_SYNC_ENABLED = "calendar";
   public static final String OPTIONS_CONTACTS_SYNC_ENABLED = "contacts";
   public static final String OPTIONS_NOTES_SYNC_ENABLED = "notes";
   public static final String OPTIONS_PASSWORD = "password";
   public static final String OPTIONS_TASKS_SYNC_ENABLED = "tasks";
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
         Bundle var36;
         if(var5 != null) {
            String var7 = "password";
            if(var5.containsKey(var7)) {
               String var9 = "username";
               if(var5.containsKey(var9)) {
                  String var11 = "username";
                  String var12 = var5.getString(var11);
                  Account var13 = new Account(var12, "com.android.exchange");
                  AccountManager var14 = AccountManager.get(EasAuthenticatorService.this);
                  String var16 = "password";
                  String var17 = var5.getString(var16);
                  var14.addAccountExplicitly(var13, var17, (Bundle)null);
                  byte var19 = 0;
                  String var21 = "contacts";
                  if(var5.containsKey(var21)) {
                     String var23 = "contacts";
                     if(var5.getBoolean(var23)) {
                        var19 = 1;
                     }
                  }

                  ContentResolver.setIsSyncable(var13, "com.android.contacts", 1);
                  ContentResolver.setSyncAutomatically(var13, "com.android.contacts", (boolean)var19);
                  byte var24 = 0;
                  String var26 = "calendar";
                  if(var5.containsKey(var26)) {
                     String var28 = "calendar";
                     if(var5.getBoolean(var28)) {
                        var24 = 1;
                     }
                  }

                  ContentResolver.setIsSyncable(var13, "com.android.calendar", 1);
                  ContentResolver.setSyncAutomatically(var13, "com.android.calendar", (boolean)var24);
                  byte var29 = 0;
                  String var31 = "tasks";
                  if(var5.containsKey(var31)) {
                     String var33 = "tasks";
                     if(var5.getBoolean(var33)) {
                        var29 = 1;
                     }
                  }

                  String var34 = "synctask checked is " + var29;
                  int var35 = Log.d("EASAuthenticatorService", var34);
                  if(EasSyncService.protocolVersion < 12.0D) {
                     ContentResolver.setIsSyncable(var13, "tasks", 0);
                     var29 = 0;
                  } else {
                     ContentResolver.setIsSyncable(var13, "tasks", 1);
                  }

                  ContentResolver.setSyncAutomatically(var13, "tasks", (boolean)var29);
                  var36 = new Bundle();
                  String var38 = "username";
                  String var39 = var5.getString(var38);
                  var36.putString("authAccount", var39);
                  var36.putString("accountType", "com.android.exchange");
                  return var36;
               }
            }
         }

         var36 = new Bundle();
         Intent var40 = AccountSetupBasics.actionSetupExchangeIntent(EasAuthenticatorService.this);
         var40.putExtra("accountAuthenticatorResponse", var1);
         var36.putParcelable("intent", var40);
         return var36;
      }

      public Bundle confirmCredentials(AccountAuthenticatorResponse var1, Account var2, Bundle var3) {
         return null;
      }

      public Bundle editProperties(AccountAuthenticatorResponse var1, String var2) {
         return null;
      }

      public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse var1, Account var2) throws NetworkErrorException {
         Intent var3 = new Intent("com.android.email.action.ACCOUNT_UPDATED");
         EasAuthenticatorService.this.sendBroadcast(var3);
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

      public Bundle updateCredentials(AccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) {
         return null;
      }
   }
}
