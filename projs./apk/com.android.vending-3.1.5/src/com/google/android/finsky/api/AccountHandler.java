package com.google.android.finsky.api;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.android.volley.Request;
import com.google.android.finsky.utils.SharedPreferencesUtils;

public class AccountHandler {

   public static final String AUTH_ACCOUNT_TYPE = "com.google";
   public static final String AUTH_TOKEN_TYPE = "androidmarket";
   private static final String KEY_ACCOUNT_NAME = "account";


   public AccountHandler() {}

   public static Account findAccount(String var0, Context var1) {
      Account var2;
      if(TextUtils.isEmpty(var0)) {
         var2 = null;
      } else {
         Account[] var3 = getAccounts(var1);
         int var4 = var3.length;
         int var5 = 0;

         while(true) {
            if(var5 >= var4) {
               var2 = null;
               break;
            }

            var2 = var3[var5];
            if(var2.name.equals(var0)) {
               break;
            }

            ++var5;
         }
      }

      return var2;
   }

   public static Account getAccountFromPreferences(Context var0, SharedPreferences var1) {
      String var2 = var1.getString("account", (String)null);
      Account var3;
      if(hasAccount(var2, var0)) {
         var3 = new Account(var2, "com.google");
      } else {
         var3 = getFirstAccount(var0);
      }

      return var3;
   }

   public static Account[] getAccounts(Context var0) {
      return AccountManager.get(var0).getAccountsByType("com.google");
   }

   public static Account getFirstAccount(Context var0) {
      Account[] var1 = getAccounts(var0);
      Account var2;
      if(var1.length > 0) {
         var2 = var1[0];
      } else {
         var2 = null;
      }

      return var2;
   }

   public static boolean hasAccount(String var0, Context var1) {
      boolean var2;
      if(findAccount(var0, var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static void invalidateAuthToken(Context var0, Request<?> var1, String var2) {
      AccountManager.get(var0).invalidateAuthToken("com.google", var2);
   }

   public static void saveAccountToPreferences(String var0, SharedPreferences var1) {
      if(var0 != null) {
         Editor var2 = var1.edit();
         var2.putString("account", var0);
         boolean var4 = SharedPreferencesUtils.commit(var2);
      }
   }
}
