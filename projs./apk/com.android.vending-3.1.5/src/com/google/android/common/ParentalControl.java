package com.google.android.common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import com.android.common.OperationScheduler;
import com.google.android.gsf.Gservices;

public class ParentalControl {

   private static final int APN_ALREADY_ACTIVE = 0;
   private static final int APN_REQUEST_STARTED = 1;
   private static final long DEFAULT_TIMEOUT_MILLIS = 43200000L;
   private static final String FEATURE_ENABLE_HIPRI = "enableHIPRI";
   private static final int HIPRI_ATTEMPTS = 20;
   private static final int HIPRI_ATTEMPT_MILLIS = 1000;
   private static final String KEY_ENABLED = "enabled";
   private static final String KEY_LANDING_URL = "landingUrl";
   private static final String LITMUS_URL = "http://android.clients.google.com/content/default";
   private static final String PREFS_NAME = "ParentalControl";
   private static final String TAG = "ParentalControl";
   public static final String VENDING_APP = "vending";
   public static final String YOUTUBE_APP = "youtube";


   private ParentalControl() {}

   public static Uri getLandingPage(Context var0, String var1) {
      Uri var2 = null;
      if(isEnabled(var0, var1)) {
         String var3 = var0.getSharedPreferences("ParentalControl", 0).getString("landingUrl", "");
         if(var3.length() != 0) {
            var2 = Uri.parse(var3);
         }
      }

      return var2;
   }

   public static boolean getLastCheckState(Context var0) {
      return var0.getSharedPreferences("ParentalControl", 0).getBoolean("enabled", (boolean)0);
   }

   public static long getLastCheckTimeMillis(Context var0) {
      SharedPreferences var1 = var0.getSharedPreferences("ParentalControl", 0);
      return (new OperationScheduler(var1)).getLastSuccessTimeMillis();
   }

   public static boolean isEnabled(Context var0, String var1) {
      byte var2 = 0;
      if(Looper.myLooper() != null) {
         Looper var3 = Looper.myLooper();
         Looper var4 = Looper.getMainLooper();
         if(var3 == var4) {
            int var5 = Log.wtf("ParentalControl", "Network request on main thread");
         }
      }

      ContentResolver var6 = var0.getContentResolver();
      if(Gservices.getBoolean(var6, "parental_control_check_enabled", (boolean)0)) {
         if(var1 != null) {
            String var7 = Gservices.getString(var6, "parental_control_apps_list");
            if(var7 != null && !var7.contains(var1)) {
               return (boolean)var2;
            }
         }

         maybeCheckState(var0);
         var2 = getLastCheckState(var0);
      }

      return (boolean)var2;
   }

   private static boolean isHipriActive(ConnectivityManager var0) {
      return var0.getNetworkInfo(5).isConnected();
   }

   private static void maybeCheckState(Context param0) {
      // $FF: Couldn't be decompiled
   }

   private static boolean waitForHipri(ConnectivityManager var0) {
      boolean var1 = true;
      boolean var2 = isHipriActive(var0);
      int var3 = var0.startUsingNetworkFeature(0, "enableHIPRI");
      if(!var2) {
         if(var3 != 0 && var3 != 1) {
            String var4 = "Parental control unchanged: Mobile network error, code " + var3;
            int var5 = Log.e("ParentalControl", var4);
            var1 = false;
         } else {
            int var6 = 0;

            while(true) {
               if(var6 >= 20) {
                  int var8 = Log.e("ParentalControl", "Parental control unchanged: Timed out waiting for mobile network");
                  var1 = false;
                  break;
               }

               int var7 = Log.i("ParentalControl", "Waiting 1000ms for mobile network");
               SystemClock.sleep(1000L);
               if(isHipriActive(var0)) {
                  break;
               }

               ++var6;
            }
         }
      }

      return var1;
   }
}
