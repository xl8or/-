package com.android.exchange.security.ode;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.deviceencryption.DeviceEncryptionManager;
import android.net.Uri;
import android.os.Environment;
import com.android.email.provider.EmailContent;
import com.android.exchange.security.ode.ODEService;
import java.util.ArrayList;

public class ODEUtils {

   public static final int ACCOUNT_FLAGS_COLUMN_FLAGS = 1;
   public static final int ACCOUNT_FLAGS_COLUMN_ID = 0;
   public static final String[] ACCOUNT_FLAGS_PROJECTION;
   private static final String[] ACCOUNT_HOST_AUTH_PROJECTION;
   public static final int HOST_AUTH_COLUMN_ACCOUNT_ID = 0;
   public static final int HOST_AUTH_COLUMN_KEY_RECV = 1;


   static {
      String[] var0 = new String[]{"_id", "flags", "securityFlags"};
      ACCOUNT_FLAGS_PROJECTION = var0;
      String[] var1 = new String[]{"_id", "hostAuthKeyRecv"};
      ACCOUNT_HOST_AUTH_PROJECTION = var1;
   }

   public ODEUtils() {}

   public static boolean checkDeviceEncryptionStatus(Context var0, ComponentName var1) {
      boolean var2 = isSdCardPresent();
      byte var3 = 0;
      ODEService.log("checkDeviceEncryptionStatus - starts");
      if(!pendingExternalEncryption(var0, var1) && !pendingInternalEncryption(var0, var1)) {
         var3 = 1;
         ODEService.log("clear account hold flags - no encryption pending");
      } else if(!pendingInternalEncryption(var0, var1)) {
         if(var2) {
            ODEService.log("clear account hold flags - sd card present");
            if(!pendingExternalEncryption(var0, var1)) {
               var3 = 1;
               ODEService.log("clear account hold flags - no encryption pending");
            } else {
               ODEService.log("clear account hold flags - external encryption still pending");
            }
         } else {
            var3 = 1;
            ODEService.log("clear account hold flags - no sdcard, no encryption pending");
         }
      } else {
         ODEService.log("check media state - no match");
      }

      if(var3 != 0) {
         clearAllAccountHoldFlags(var0, 32);
         clearAllAccountHoldFlags(var0, 1024);
         ODEService.log("checkDeviceEncryptionStatus - clear account hold flags");
      }

      ODEService.log("checkDeviceEncryptionStatus - starts: " + var3);
      return (boolean)var3;
   }

   public static void clearAccountHoldFlags(Context var0, EmailContent.Account var1, int var2) {
      long var3 = var1.mId;
      if(EmailContent.Policies.getNumberOfPoliciesForAccount(var0, var3) > 0) {
         if((var1.mFlags & var2) != 0) {
            ContentValues var5 = new ContentValues();
            int var6 = var1.mFlags;
            int var7 = ~var2;
            int var8 = var6 & var7;
            var1.mFlags = var8;
            Integer var9 = Integer.valueOf(var1.mFlags);
            var5.put("flags", var9);
            Uri var10 = EmailContent.Account.CONTENT_URI;
            long var11 = var1.mId;
            Uri var13 = ContentUris.withAppendedId(var10, var11);
            var0.getContentResolver().update(var13, var5, (String)null, (String[])null);
         }
      }
   }

   public static void clearAllAccountHoldFlags(Context param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   public static DevicePolicyManager getDPM(Context var0) {
      return (DevicePolicyManager)var0.getSystemService("device_policy");
   }

   public static void getExchangeAccounts(Context var0, ArrayList<EmailContent.Account> var1) {
      ContentResolver var2 = var0.getContentResolver();
      Uri var3 = EmailContent.Account.CONTENT_URI;
      String[] var4 = ACCOUNT_HOST_AUTH_PROJECTION;
      Object var5 = null;
      Object var6 = null;
      Cursor var7 = var2.query(var3, var4, (String)null, (String[])var5, (String)var6);
      if(var7 != null) {
         try {
            while(var7.moveToNext()) {
               long var8 = (long)var7.getInt(1);
               EmailContent.HostAuth var10 = EmailContent.HostAuth.restoreHostAuthWithId(var0, var8);
               if(var10 != null) {
                  String var11 = var10.mProtocol;
                  if("eas".equals(var11)) {
                     long var12 = (long)var7.getInt(0);
                     EmailContent.Account var14 = EmailContent.Account.restoreAccountWithId(var0, var12);
                     if(var14 != null) {
                        var1.add(var14);
                        StringBuilder var16 = (new StringBuilder()).append("Exchange account found : ");
                        String var17 = var14.mEmailAddress;
                        ODEService.log(var16.append(var17).toString());
                     }
                  } else {
                     ODEService.log("Non-Exchange account found");
                  }
               }
            }
         } finally {
            var7.close();
         }

      }
   }

   public static boolean isSdCardPresent() {
      return Environment.getExternalStorageStateSd().equals("mounted");
   }

   public static boolean pendingExternalEncryption(Context var0, ComponentName var1) {
      DevicePolicyManager var2 = getDPM(var0);
      boolean var3 = DeviceEncryptionManager.getAppliedStatusForExternal();
      boolean var4 = var2.getRequireStorageCardEncryption(var1);
      boolean var7;
      if(var3 == var4) {
         boolean var5 = DeviceEncryptionManager.getExternalStorageStatus();
         boolean var6 = var2.getRequireStorageCardEncryption(var1);
         if(var5 == var6) {
            var7 = false;
            return var7;
         }
      }

      var7 = true;
      return var7;
   }

   public static boolean pendingInternalEncryption(Context var0, ComponentName var1) {
      DevicePolicyManager var2 = getDPM(var0);
      boolean var3 = DeviceEncryptionManager.getAppliedStatusForInternal();
      boolean var4 = var2.getRequireDeviceEncryption(var1);
      boolean var7;
      if(var3 == var4) {
         boolean var5 = DeviceEncryptionManager.getInternalStorageStatus();
         boolean var6 = var2.getRequireDeviceEncryption(var1);
         if(var5 == var6) {
            var7 = false;
            return var7;
         }
      }

      var7 = true;
      return var7;
   }

   public static void setAccountHoldFlag(Context var0, EmailContent.Account var1, int var2, boolean var3) {
      if(var3) {
         int var4 = var1.mFlags | var2;
         var1.mFlags = var4;
      } else {
         int var8 = var1.mFlags;
         int var9 = ~var2;
         int var10 = var8 & var9;
         var1.mFlags = var10;
      }

      ODEService.log("Account hold flag = " + var2);
      ContentValues var5 = new ContentValues();
      Integer var6 = Integer.valueOf(var1.mFlags);
      var5.put("flags", var6);
      var1.update(var0, var5);
   }
}
