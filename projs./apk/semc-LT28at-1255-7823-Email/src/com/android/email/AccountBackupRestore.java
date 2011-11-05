package com.android.email;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.android.email.Email;
import com.android.email.ExchangeUtils;
import com.android.email.Preferences;
import com.android.email.SecurityPolicy;
import com.android.email.Utility;

public class AccountBackupRestore {

   public AccountBackupRestore() {}

   public static void backupAccounts(Context var0) {
      if(Email.DEBUG) {
         int var1 = Log.v("Email", "backupAccounts");
      }

      (new AccountBackupRestore.1(var0)).start();
   }

   static void doBackupAccounts(Context param0, Preferences param1) {
      // $FF: Couldn't be decompiled
   }

   static boolean doRestoreAccounts(Context param0, Preferences param1) {
      // $FF: Couldn't be decompiled
   }

   public static void restoreAccountsIfNeeded(Context var0) {
      AsyncTask var1 = Utility.runAsync(new AccountBackupRestore.2(var0));
   }

   static class 1 extends Thread {

      // $FF: synthetic field
      final Context val$context;


      1(Context var1) {
         this.val$context = var1;
      }

      public void run() {
         Context var1 = this.val$context;
         Preferences var2 = Preferences.getPreferences(this.val$context);
         AccountBackupRestore.doBackupAccounts(var1, var2);
      }
   }

   static class 2 implements Runnable {

      // $FF: synthetic field
      final Context val$context;


      2(Context var1) {
         this.val$context = var1;
      }

      public void run() {
         Context var1 = this.val$context;
         Preferences var2 = Preferences.getPreferences(this.val$context);
         if(AccountBackupRestore.doRestoreAccounts(var1, var2)) {
            int var3 = Log.w("Email", "Register services after restoring accounts");
            SecurityPolicy.getInstance(this.val$context).updatePolicies(65535L);
            boolean var4 = Email.setServicesEnabled(this.val$context);
            ExchangeUtils.startExchangeService(this.val$context);
         }
      }
   }
}
