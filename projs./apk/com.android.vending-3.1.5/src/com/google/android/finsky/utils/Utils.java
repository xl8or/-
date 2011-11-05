package com.google.android.finsky.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Looper;
import com.google.android.finsky.utils.FinskyLog;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {

   public Utils() {}

   public static void checkUrlIsSecure(String var0) {
      label20: {
         boolean var2;
         try {
            URL var1 = new URL(var0);
            if(var1.getProtocol().toLowerCase().equals("https")) {
               return;
            }

            var2 = var1.getHost().toLowerCase().endsWith("corp.google.com");
         } catch (MalformedURLException var7) {
            String var4 = "Cannot parse URL: " + var0;
            Object[] var5 = new Object[0];
            FinskyLog.d(var4, var5);
            break label20;
         }

         if(var2) {
            return;
         }
      }

      String var6 = "Insecure URL: " + var0;
      throw new RuntimeException(var6);
   }

   public static void ensureNotOnMainThread() {
      Looper var0 = Looper.myLooper();
      Looper var1 = Looper.getMainLooper();
      if(var0 == var1) {
         throw new IllegalStateException("This method cannot be called from the UI thread.");
      }
   }

   public static void ensureOnMainThread() {
      Looper var0 = Looper.myLooper();
      Looper var1 = Looper.getMainLooper();
      if(var0 != var1) {
         throw new IllegalStateException("This method must be called from the UI thread.");
      }
   }

   public static String getPreferenceKey(String var0, String var1) {
      return var1 + ":" + var0;
   }

   public static boolean isBackgroundDataEnabled(Context var0) {
      return ((ConnectivityManager)var0.getSystemService("connectivity")).getBackgroundDataSetting();
   }
}
