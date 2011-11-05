package com.android.email.service;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.android.email.AccountBackupRestore;
import com.android.email.Email;
import com.android.email.SecurityPolicy;
import com.android.email.certificateManager.CertificateMgr;
import com.android.email.provider.EmailContent;
import com.android.exchange.SyncManager;

public class EnterpriseDMReceiver extends BroadcastReceiver {

   public static final String ACCID_KEY = "accountid";
   public static String ACCOUNT_DELETED = "com.android.email.ACCOUNT_DELETED";
   public static final String ALIAS_KEY = "alias";
   public static final String DATA_KEY = "data";
   public static String ENABLE_MESSAGECOMPOSE = "com.android.email.ENABLE_MESSAGECOMPOSE";
   public static String GET_DEVICEID = "com.android.email.GET_DEVICEID";
   public static String INSTALL_CERTIFICATE = "com.android.email.INSTALL_CERTIFICATE";
   public static final String PASSWORD_KEY = "password";
   public static String RENAME_CERTIFICATE = "com.android.email.RENAME_CERTIFICATE";
   public static String SIGNATURE_UPDATE = "com.android.email.SIGNATURE_UPDATED";
   private static String TAG = "EnterpriseDMReceiver";


   public EnterpriseDMReceiver() {}

   // $FF: synthetic method
   static void access$000(EnterpriseDMReceiver var0, Context var1, byte[] var2, long var3, String var5) {
      var0.installCertificate(var1, var2, var3, var5);
   }

   private void installCertificate(Context param1, byte[] param2, long param3, String param5) {
      // $FF: Couldn't be decompiled
   }

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
               String var14 = var2.getStringExtra("alias");
               if(var14 != null) {
                  try {
                     boolean var15 = CertificateMgr.getInstance(SyncManager.getDeviceId(var1), var1).removeCertificate(var14);
                     String var16 = TAG;
                     String var17 = "Remove Certificate Status - " + var15;
                     Log.v(var16, var17);
                  } catch (Exception var37) {
                     var37.printStackTrace();
                  }
               }

               AccountBackupRestore.backupAccounts(var1);
               SecurityPolicy var19 = SecurityPolicy.getInstance(var1);
               if(var19 != null) {
                  var19.reducePolicies();
               }
            } else {
               String var20 = var2.getAction();
               String var21 = RENAME_CERTIFICATE;
               if(var20.equals(var21)) {
                  int var22 = Log.v(TAG, "onReceive Rename Certificate");
                  EnterpriseDMReceiver.2 var23 = new EnterpriseDMReceiver.2(var2, var1);
                  (new Thread(var23)).start();
               } else {
                  String var24 = var2.getAction();
                  String var25 = ENABLE_MESSAGECOMPOSE;
                  if(var24.equals(var25)) {
                     int var26 = Log.v(TAG, "onReceive MessageCompose Enable");
                     boolean var27 = Email.setServicesEnabled(var1);
                  } else {
                     String var28 = var2.getAction();
                     String var29 = GET_DEVICEID;
                     if(var28.equals(var29)) {
                        int var30 = Log.v(TAG, "onReceive Get Device Id");

                        try {
                           String var31 = SyncManager.getDeviceId(var1);
                           String var32 = TAG;
                           String var33 = "Device Id - " + var31;
                           Log.d(var32, var33);
                           Intent var35 = new Intent("android.intent.action.enterprise.GET_DEVICEID");
                           if(var31 != null) {
                              var35.putExtra("deviceid", var31);
                           }

                           var1.sendBroadcast(var35);
                        } catch (Exception var38) {
                           var38.printStackTrace();
                        }
                     }
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
         String var3 = this.val$intent.getStringExtra("alias");
         EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(this.val$context, var1);
         String var5 = EnterpriseDMReceiver.TAG;
         String var6 = "Alias - " + var3;
         Log.v(var5, var6);
         if(var4 != null) {
            if(var3 != null) {
               ContentValues var8 = new ContentValues();
               var8.put("cbaCertificateAlias", var3);
               Context var9 = this.val$context;
               var4.update(var9, var8);
            }
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
         if(var2 != null) {
            if(var3 != 65535L) {
               EnterpriseDMReceiver var5 = EnterpriseDMReceiver.this;
               Context var6 = this.val$context;
               EnterpriseDMReceiver.access$000(var5, var6, var2, var3, var1);
            }
         }
      }
   }
}
