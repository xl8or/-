package com.android.email.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.android.email.AccountBackupRestore;
import com.android.email.Email;
import com.android.email.SecurityPolicy;
import com.android.exchange.EmailKeyManager;

public class EnterpriseDMReceiver extends BroadcastReceiver {

   public static final String ACCID_KEY = "accountid";
   public static String ACCOUNT_DELETED = "com.android.email.ACCOUNT_DELETED";
   public static final String ALIAS_KEY = "alias";
   public static final String DATA_KEY = "data";
   public static String ENABLE_MESSAGECOMPOSE = "com.android.email.ENABLE_MESSAGECOMPOSE";
   public static String INSTALL_CERTIFICATE = "com.android.email.INSTALL_CERTIFICATE";
   public static final String OLDACCID_KEY = "oldaccountid";
   public static final String PASSWORD_KEY = "password";
   public static String RENAME_CERTIFICATE = "com.android.email.RENAME_CERTIFICATE";
   public static String SIGNATURE_UPDATE = "com.android.email.SIGNATURE_UPDATED";
   private static String TAG = "EnterpriseDMReceiver";


   public EnterpriseDMReceiver() {}

   private void updateSignatureToPrefs(Context var1, String var2) {
      SharedPreferences var3 = PreferenceManager.getDefaultSharedPreferences(var1);
      if(var3 != null) {
         String var4 = TAG;
         String var5 = "Setting to " + var2;
         Log.i(var4, var5);
         var3.edit().putString("signature", var2).commit();
      }
   }

   public void onReceive(Context var1, Intent var2) {
      String var3 = var2.getAction();
      String var4 = SIGNATURE_UPDATE;
      if(var3.equals(var4)) {
         int var5 = Log.i(TAG, "onReceive Signature Update");
         String var6 = var2.getStringExtra("signature");
         if(var6 != null) {
            this.updateSignatureToPrefs(var1, var6);
         }
      } else {
         String var7 = var2.getAction();
         String var8 = INSTALL_CERTIFICATE;
         if(var7.equals(var8)) {
            int var9 = Log.v(TAG, "onReceive Install Certificate");
            EnterpriseDMReceiver.1 var10 = new EnterpriseDMReceiver.1(var2, var1);
            (new Thread(var10)).start();
         } else {
            String var11 = var2.getAction();
            String var12 = ACCOUNT_DELETED;
            if(var11.equals(var12)) {
               int var13 = Log.v(TAG, "onReceive ACCOUNT_DELETED");
               long var14 = var2.getLongExtra("accountid", 65535L);
               if(var14 > 0L) {
                  EmailKeyManager.EmailKeyStore.removeCertificate(var1, var14);
                  AccountBackupRestore.backupAccounts(var1);
                  SecurityPolicy var16 = SecurityPolicy.getInstance(var1);
                  if(var16 != null) {
                     var16.reducePolicies();
                  }
               }
            } else {
               String var17 = var2.getAction();
               String var18 = RENAME_CERTIFICATE;
               if(var17.equals(var18)) {
                  int var19 = Log.v(TAG, "onReceive Rename Certificate");
                  EnterpriseDMReceiver.2 var20 = new EnterpriseDMReceiver.2(var2, var1);
                  (new Thread(var20)).start();
               } else {
                  String var21 = var2.getAction();
                  String var22 = ENABLE_MESSAGECOMPOSE;
                  if(var21.equals(var22)) {
                     int var23 = Log.v(TAG, "onReceive MessageCompose Enable");
                     boolean var24 = Email.setServicesEnabled(var1);
                  }
               }
            }
         }
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final Intent val$intent;


      2(Intent var2, Context var3) {
         this.val$intent = var2;
         this.val$context = var3;
      }

      public void run() {
         long var1 = this.val$intent.getLongExtra("accountid", 65535L);
         long var3 = this.val$intent.getLongExtra("oldaccountid", 65535L);

         try {
            EmailKeyManager.EmailKeyStore.renameCertificate(this.val$context, var1, var3);
            String var5 = EnterpriseDMReceiver.TAG;
            String var6 = "Installed certificate for account " + var1;
            Log.d(var5, var6);
         } catch (Exception var10) {
            int var9 = Log.d(EnterpriseDMReceiver.TAG, "Failed to install certificate");
            var10.printStackTrace();
         }
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final Intent val$intent;


      1(Intent var2, Context var3) {
         this.val$intent = var2;
         this.val$context = var3;
      }

      public void run() {
         String var1 = this.val$intent.getStringExtra("password");
         byte[] var2 = this.val$intent.getByteArrayExtra("data");
         long var3 = this.val$intent.getLongExtra("accountid", 65535L);

         try {
            EmailKeyManager.EmailKeyStore.installCertificate(this.val$context, var2, var1, var3);
            String var5 = EnterpriseDMReceiver.TAG;
            String var6 = "Installed certificate for account  " + var3;
            Log.d(var5, var6);
         } catch (Exception var10) {
            int var9 = Log.d(EnterpriseDMReceiver.TAG, "Failed to install certificate");
            var10.printStackTrace();
         }
      }
   }
}
