package com.android.email;

import android.content.Context;
import android.util.Log;
import com.android.email.Email;
import com.android.email.ExchangeUtils;
import com.android.email.Preferences;
import com.android.email.SecurityPolicy;
import java.io.File;

public class AccountBackupRestore {

   public AccountBackupRestore() {}

   public static void backupAccounts(Context var0) {
      if(Email.DEBUG) {
         int var1 = Log.v("Email", "backupAccounts");
      }

      Preferences var2 = Preferences.getPreferences(var0);
      doBackupAccounts(var0, var2);
   }

   static void doBackupAccounts(Context param0, Preferences param1) {
      // $FF: Couldn't be decompiled
   }

   static boolean doRestoreAccounts(Context param0, Preferences param1) {
      // $FF: Couldn't be decompiled
   }

   public static void restoreAccountsIfNeeded(Context var0) {
      File var1 = new File("/data/data/com.android.email/shared_prefs");
      if(var1 != null) {
         File[] var2 = var1.listFiles();
         if(var2 != null) {
            int var3 = 0;

            while(true) {
               int var4 = var2.length;
               if(var3 >= var4) {
                  break;
               }

               StringBuilder var5 = (new StringBuilder()).append("FileName[").append(var3).append("]=");
               String var6 = var2[var3].getName();
               String var7 = var5.append(var6).toString();
               int var8 = Log.d("AccountBackupRestore", var7);
               ++var3;
            }
         }
      }

      int var9 = Log.d("AccountBackupRestore", "Preference File List End");
      Preferences var10 = Preferences.getPreferences(var0);
      if(doRestoreAccounts(var0, var10)) {
         int var11 = Log.w("Email", "Register services after restoring accounts");
         SecurityPolicy.getInstance(var0).updatePolicies(65535L);
         boolean var12 = Email.setServicesEnabled(var0);
         ExchangeUtils.startExchangeService(var0);
      }
   }
}
