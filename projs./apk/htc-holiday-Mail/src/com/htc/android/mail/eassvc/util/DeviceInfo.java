package com.htc.android.mail.eassvc.util;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.os.Build.VERSION;
import android.security.Md5MessageDigest;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.htc.android.mail.Mail;

public class DeviceInfo {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final String FILE_DEVICE_ID = "device_id";
   private static String mUUID = null;


   public DeviceInfo() {}

   public static final String getAndroidVersion() {
      return VERSION.RELEASE;
   }

   public static String getDeviceFriendlyName() {
      return SystemProperties.get("ro.product.name", "Not Available");
   }

   public static final String getDeviceID() throws Exception {
      // $FF: Couldn't be decompiled
   }

   public static final String getDeviceModel() {
      return SystemProperties.get("ro.product.model", "Not Available").replaceAll("-", "").replaceAll("_", "");
   }

   public static final String getDeviceType() {
      return Build.PRODUCT.replaceAll("-", "").replaceAll("_", "").replaceAll(" ", "");
   }

   public static final String getIMEI() {
      String var0 = TelephonyManager.getDefault().getDeviceId();
      if(TextUtils.isEmpty(var0) || var0.equals("00000000000000") || var0.equals("000000000000000")) {
         var0 = SystemProperties.get("ril.cdma.esn");
      }

      StringBuilder var1 = new StringBuilder();
      String var6;
      if(var0.length() >= 4 && !TextUtils.isEmpty(var0)) {
         StringBuilder var2 = var1.append("************");
         int var3 = var0.length() - 4;
         String var4 = var0.substring(var3);
         var1.append(var4);
         var6 = var1.toString();
      } else {
         var6 = "Not Available";
      }

      return var6;
   }

   private static String getMd5(String var0) {
      Md5MessageDigest var1 = new Md5MessageDigest();
      var1.reset();
      byte[] var2 = var0.getBytes();
      var1.update(var2);
      return toHexString(var1.digest(), "");
   }

   public static final String getMobileOperator(Context var0) {
      TelephonyManager var1 = (TelephonyManager)var0.getSystemService("phone");
      String var3;
      if(var1 != null) {
         String var2 = var1.getNetworkOperatorName();
         if(var2 != null) {
            var3 = var2;
         } else {
            var3 = "Not Available";
         }
      } else {
         var3 = "Not Available";
      }

      return var3;
   }

   public static final String getPhoneNumber(Context var0) {
      TelephonyManager var1 = (TelephonyManager)var0.getSystemService("phone");
      StringBuilder var2 = new StringBuilder();
      if(var1 != null) {
         String var3 = var1.getLine1Number();
         if(!TextUtils.isEmpty(var3)) {
            int var4 = var3.length();
            if(var4 < 4 && var4 > 0) {
               int var5 = 0;

               while(true) {
                  int var6 = 10 - var4;
                  if(var5 >= var6) {
                     var2.append(var3);
                     break;
                  }

                  StringBuilder var7 = var2.append('*');
                  ++var5;
               }
            } else if(var4 >= 4) {
               StringBuilder var9 = var2.append("******");
               int var10 = var3.length() - 4;
               String var11 = var3.substring(var10);
               var2.append(var11);
            } else {
               StringBuilder var13 = var2.append("Not Available");
            }
         } else {
            StringBuilder var14 = var2.append("Not Available");
         }
      } else {
         StringBuilder var15 = var2.append("Not Available");
      }

      return var2.toString();
   }

   private static String toHexString(byte[] var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      byte[] var3 = var0;
      int var4 = var0.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = Integer.toHexString(var3[var5] & 255);
         StringBuilder var7 = var2.append(var6).append(var1);
      }

      return var2.toString();
   }
}
