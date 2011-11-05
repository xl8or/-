package com.htc.android.mail.eassvc.provision;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.htc.android.mail.eassvc.EASAppSvc;
import com.htc.android.mail.eassvc.util.EASLog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class EASDeviceAdmin extends DeviceAdminReceiver {

   static final String TAG = "EASDeviceAdmin";
   static long lastupdatetime = Long.MIN_VALUE;


   public EASDeviceAdmin() {}

   private static long getPasswordChangedTime(Context param0) {
      // $FF: Couldn't be decompiled
   }

   static SharedPreferences getSamplePreferences(Context var0) {
      String var1 = DeviceAdminReceiver.class.getName();
      return var0.getSharedPreferences(var1, 0);
   }

   public static boolean isPasswordExpire(Context var0, int var1) {
      long var2 = (new Date()).getTime();
      long var4 = getPasswordChangedTime(var0);
      long var6 = Long.valueOf((long)var1).longValue() * 24L * 60L * 60L * 1000L;
      boolean var8;
      if(var6 < 0L) {
         var8 = false;
      } else {
         boolean var9;
         if(var4 + var6 < var2) {
            var9 = true;
         } else {
            var9 = false;
         }

         if(var9) {
            StringBuilder var10 = (new StringBuilder()).append("isPasswordExpire(): changeTime=");
            String var11 = (new Date(var4)).toLocaleString();
            StringBuilder var12 = var10.append(var11).append(",nowTime=");
            String var13 = (new Date(var2)).toLocaleString();
            String var14 = var12.append(var13).append(",exp=").append(var6).toString();
            EASLog.d("EASDeviceAdmin", var14);
         }

         var8 = var9;
      }

      return var8;
   }

   public static void updateChangePasswordTime(Context var0) {
      synchronized("pass_change") {
         EASLog.d("EASDeviceAdmin", "onPasswordChanged");
         long var1 = (new Date()).getTime();
         if(lastupdatetime > Long.MIN_VALUE) {
            long var3 = lastupdatetime;
            if(var1 - var3 < 500L) {
               return;
            }
         }

         EASLog.d("EASDeviceAdmin", "go");
         File var5 = var0.getDir("config", 0);
         File var6 = new File(var5, "pass_change");

         try {
            FileWriter var7 = new FileWriter(var6);
            String var8 = String.valueOf(var1);
            var7.write(var8);
            var7.flush();
            var7.close();
         } catch (IOException var11) {
            var11.printStackTrace();
         }

         lastupdatetime = var1;
      }
   }

   public CharSequence onDisableRequested(Context var1, Intent var2) {
      EASLog.d("EASDeviceAdmin", "onDisableRequested");
      return var1.getText(2131362505);
   }

   public void onDisabled(Context var1, Intent var2) {
      EASLog.d("EASDeviceAdmin", "onDisabled");
      DevicePolicyManager var3 = (DevicePolicyManager)var1.getSystemService("device_policy");
      ComponentName var4 = new ComponentName(var1, EASDeviceAdmin.class);
      var3.removeActiveAdmin(var4);
      var1.getPackageManager().setComponentEnabledSetting(var4, 2, 1);
      Intent var5 = new Intent("com.htc.eas.deleteaccount");
      var5.setClass(var1, EASAppSvc.class);
      var1.startService(var5);
   }

   public void onEnabled(Context var1, Intent var2) {
      EASLog.d("EASDeviceAdmin", "onEnabled");
   }

   public void onPasswordChanged(Context var1, Intent var2) {
      updateChangePasswordTime(var1);
   }

   public void onPasswordFailed(Context var1, Intent var2) {}

   public void onPasswordSucceeded(Context var1, Intent var2) {}
}
