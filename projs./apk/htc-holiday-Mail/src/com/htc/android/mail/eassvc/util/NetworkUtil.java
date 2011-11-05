package com.htc.android.mail.eassvc.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.provider.Settings.System;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.util.EASLog;

public class NetworkUtil {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   protected static final String TAG = "EAS_NetowrkUtil";


   public NetworkUtil() {}

   public static boolean checkNetwork(Context var0) {
      boolean var1 = isWifiNetwork(var0);
      boolean var2 = isMobileNetwork(var0);
      boolean var3 = isUSBNetwork(var0);
      if(DEBUG) {
         String var4 = "EAS_NetowrkUtil";
         StringBuilder var5 = (new StringBuilder()).append("checkNetwork(): ");
         String var6;
         if(var1) {
            var6 = " Wifi";
         } else {
            var6 = "";
         }

         StringBuilder var7 = var5.append(var6);
         String var8;
         if(var2) {
            var8 = " Mobile";
         } else {
            var8 = "";
         }

         StringBuilder var9 = var7.append(var8);
         String var10;
         if(var3) {
            var10 = " USB";
         } else {
            var10 = "";
         }

         String var11 = var9.append(var10).toString();
         EASLog.d(var4, var11);
      }

      boolean var12;
      if(!var1 && !var2 && !var3) {
         var12 = false;
      } else {
         var12 = true;
      }

      return var12;
   }

   public static boolean isAirplaneModeOn(Context var0) {
      boolean var1;
      if(System.getInt(var0.getContentResolver(), "airplane_mode_on", 0) == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isDataRoaming(Context var0) {
      NetworkInfo var1 = ((ConnectivityManager)var0.getSystemService("connectivity")).getActiveNetworkInfo();
      byte var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         byte var3 = var1.isRoaming();
         if(DEBUG) {
            String var4 = "EAS_NetowrkUtil";
            StringBuilder var5 = (new StringBuilder()).append("isDataRoaming(): ");
            String var6 = var1.getTypeName();
            StringBuilder var7 = var5.append(var6);
            String var8;
            if(var3 != 0) {
               var8 = " roaming";
            } else {
               var8 = "";
            }

            String var9 = var7.append(var8).toString();
            EASLog.d(var4, var9);
         }

         var2 = var3;
      }

      return (boolean)var2;
   }

   public static boolean isMobileNetwork(Context var0) {
      NetworkInfo var1 = ((ConnectivityManager)var0.getSystemService("connectivity")).getNetworkInfo(0);
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         boolean var3 = var1.isConnected();
         State var4 = var1.getState();
         State var5 = State.SUSPENDED;
         boolean var6;
         if(var4 == var5) {
            var6 = true;
         } else {
            var6 = false;
         }

         if(var6) {
            EASLog.v("EAS_NetowrkUtil", "isMobileNetwork: suspended");
         }

         if(!var3 && !var6) {
            var2 = false;
         } else {
            var2 = true;
         }
      }

      return var2;
   }

   public static boolean isNetworkAllowAutosync(Context var0, long var1, boolean var3) {
      boolean var4 = isAirplaneModeOn(var0);
      boolean var5 = isWifiNetwork(var0);
      boolean var6 = isUSBNetwork(var0);
      boolean var7;
      if(var4 && !var5 && !var6) {
         if(DEBUG) {
            EASLog.d("EAS_NetowrkUtil", var1, "isNetworkAllowAutosync(): AirplaneMode & no syncable network.");
         }

         var7 = false;
      } else if(isDataRoaming(var0)) {
         if(DEBUG) {
            StringBuilder var8 = (new StringBuilder()).append("isNetworkAllowAutosync(): Roaming ");
            String var9;
            if(var5) {
               var9 = ", Wifi";
            } else {
               var9 = "";
            }

            StringBuilder var10 = var8.append(var9);
            String var11;
            if(var3) {
               var11 = ", Connect while roaming";
            } else {
               var11 = "";
            }

            StringBuilder var12 = var10.append(var11);
            String var13;
            if(var6) {
               var13 = ", USBNet";
            } else {
               var13 = "";
            }

            String var14 = var12.append(var13).toString();
            EASLog.d("EAS_NetowrkUtil", var1, var14);
         }

         if(!var5 && !var6 && !var3) {
            var7 = false;
         } else {
            var7 = true;
         }
      } else {
         var7 = true;
      }

      return var7;
   }

   public static boolean isUSBNetwork(Context var0) {
      NetworkInfo var1 = ((ConnectivityManager)var0.getSystemService("connectivity")).getNetworkInfo(15);
      byte var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.isConnected();
      }

      return (boolean)var2;
   }

   public static boolean isWiMaxNetwork(Context var0) {
      NetworkInfo var1 = ((ConnectivityManager)var0.getSystemService("connectivity")).getNetworkInfo(6);
      byte var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.isConnected();
      }

      return (boolean)var2;
   }

   public static boolean isWifiNetwork(Context var0) {
      NetworkInfo var1 = ((ConnectivityManager)var0.getSystemService("connectivity")).getNetworkInfo(1);
      byte var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.isConnected();
      }

      return (boolean)var2;
   }
}
