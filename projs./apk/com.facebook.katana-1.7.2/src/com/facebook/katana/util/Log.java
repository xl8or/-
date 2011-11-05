package com.facebook.katana.util;

import com.facebook.katana.Constants;

public class Log {

   public static final int ASSERT = 7;
   public static final int DEBUG = 3;
   public static final int ERROR = 6;
   public static final int INFO = 4;
   public static final int VERBOSE = 2;
   public static final int WARN = 5;


   public Log() {}

   public static void d(String var0, String var1) {
      if(Constants.isBetaBuild()) {
         android.util.Log.d(var0, var1);
      }
   }

   public static void d(String var0, String var1, Throwable var2) {
      if(Constants.isBetaBuild()) {
         android.util.Log.d(var0, var1, var2);
      }
   }

   public static void e(String var0, String var1) {
      if(Constants.isBetaBuild()) {
         android.util.Log.e(var0, var1);
      }
   }

   public static void e(String var0, String var1, Throwable var2) {
      if(Constants.isBetaBuild()) {
         android.util.Log.e(var0, var1, var2);
      }
   }

   public static void i(String var0, String var1) {
      if(Constants.isBetaBuild()) {
         android.util.Log.i(var0, var1);
      }
   }

   public static void i(String var0, String var1, Throwable var2) {
      if(Constants.isBetaBuild()) {
         android.util.Log.i(var0, var1, var2);
      }
   }

   public static boolean isLoggable(String var0, int var1) {
      boolean var2;
      if(Constants.isBetaBuild() && android.util.Log.isLoggable(var0, var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static void v(String var0, String var1) {
      if(Constants.isBetaBuild()) {
         android.util.Log.v(var0, var1);
      }
   }

   public static void v(String var0, String var1, Throwable var2) {
      if(Constants.isBetaBuild()) {
         android.util.Log.v(var0, var1, var2);
      }
   }

   public static void w(String var0, String var1) {
      if(Constants.isBetaBuild()) {
         android.util.Log.w(var0, var1);
      }
   }

   public static void w(String var0, String var1, Throwable var2) {
      if(Constants.isBetaBuild()) {
         android.util.Log.w(var0, var1, var2);
      }
   }
}
