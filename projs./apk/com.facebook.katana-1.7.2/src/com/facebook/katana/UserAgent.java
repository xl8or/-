package com.facebook.katana;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.util.StringUtils;

public class UserAgent {

   public static final String BRANCH_NAME = "release-1.7.2";
   public static final String FB_APP_NAME = "FBAN";
   public static final String FB_APP_VERSION = "FBAV";
   public static final String FB_BRANCH = "FBBR";
   public static final String FB_CARRIER = "FBCR";
   public static final String FB_DEVICE = "FBDV";
   public static final String FB_DISPLAY_METRICS = "FBDM";
   public static final String FB_LOCALE = "FBLC";
   public static final String FB_PACKAGE_NAME = "FBPN";
   public static final String FB_SYSTEM_VERSION = "FBSV";


   public UserAgent() {}

   private static String cleanString(String var0) {
      return StringUtils.xmlEncodeNonLatin(var0).replace("/", "-").replace(";", "-");
   }

   public static String getAppVersion(Context var0) {
      String var9;
      String var10;
      try {
         PackageManager var1 = var0.getPackageManager();
         String var2 = var0.getPackageName();
         PackageInfo var3 = var1.getPackageInfo(var2, 0);
         String var4 = var3.versionName;
         StringBuilder var5 = new StringBuilder(var4);
         if(Constants.isBetaBuild()) {
            StringBuilder var6 = var5.append("/");
            int var7 = var3.versionCode;
            var6.append(var7);
         }

         var9 = var5.toString();
      } catch (NameNotFoundException var12) {
         var10 = "unknown";
         return var10;
      }

      var10 = var9;
      return var10;
   }

   public static String getCarrier(Context var0) {
      return ((TelephonyManager)var0.getSystemService("phone")).getNetworkOperatorName();
   }

   public static String getDevice() {
      return Build.MODEL;
   }

   public static String getDisplayMetrics(Context var0) {
      DisplayMetrics var1 = var0.getResources().getDisplayMetrics();
      StringBuilder var2 = (new StringBuilder()).append("{density=");
      float var3 = var1.density;
      StringBuilder var4 = var2.append(var3).append(",width=");
      int var5 = var1.widthPixels;
      StringBuilder var6 = var4.append(var5).append(",height=");
      int var7 = var1.heightPixels;
      return var6.append(var7).append("}").toString();
   }

   public static String getLocale(Context var0) {
      return var0.getResources().getConfiguration().locale.toString();
   }

   public static String getOSVersion() {
      return VERSION.RELEASE;
   }

   public static String getProductName() {
      return "FB4A";
   }

   public static String getUserAgentString(Context var0, Boolean var1) {
      StringBuilder var2 = new StringBuilder();
      StringBuilder var3 = var2.append("[");
      Object[] var4 = new Object[16];
      var4[0] = "FBAN";
      String var5 = cleanString(getProductName());
      var4[1] = var5;
      var4[2] = "FBAV";
      String var6 = cleanString(getAppVersion(var0));
      var4[3] = var6;
      var4[4] = "FBPN";
      String var7 = var0.getPackageName();
      var4[5] = var7;
      var4[6] = "FBDV";
      String var8 = cleanString(getDevice());
      var4[7] = var8;
      var4[8] = "FBSV";
      String var9 = cleanString(getOSVersion());
      var4[9] = var9;
      var4[10] = "FBDM";
      String var10 = cleanString(getDisplayMetrics(var0));
      var4[11] = var10;
      var4[12] = "FBCR";
      String var11 = cleanString(getCarrier(var0));
      var4[13] = var11;
      var4[14] = "FBLC";
      String var12 = cleanString(getLocale(var0));
      var4[15] = var12;
      String var13 = String.format("%s/%s;%s/%s;%s/%s;%s/%s;%s/%s;%s/%s;%s/%s;%s/%s;", var4);
      var2.append(var13);
      if(var1.booleanValue()) {
         Object[] var15 = new Object[]{"FB_FW", "1"};
         String var16 = String.format("%s/%s;", var15);
         var2.append(var16);
      }

      if(FacebookAffiliation.isCurrentUserEmployee()) {
         Object[] var18 = new Object[]{"FBBR", "release-1.7.2"};
         String var19 = String.format("%s/%s;", var18);
         var2.append(var19);
      }

      StringBuilder var21 = var2.append("]");
      return var2.toString();
   }
}
