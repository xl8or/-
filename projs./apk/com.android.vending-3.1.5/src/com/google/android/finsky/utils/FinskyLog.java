package com.google.android.finsky.utils;

import android.util.Log;
import java.util.Locale;

public class FinskyLog {

   public static final boolean DEBUG = Log.isLoggable(TAG, 2);
   private static String TAG = "Finsky";


   public FinskyLog() {}

   private static String buildMessage(String var0, Object ... var1) {
      String var2;
      if(var1 == null) {
         var2 = var0;
      } else {
         var2 = String.format(Locale.US, var0, var1);
      }

      StackTraceElement[] var3 = (new Throwable()).fillInStackTrace().getStackTrace();
      String var4 = "<unknown>";
      int var5 = 2;

      while(true) {
         int var6 = var3.length;
         if(var5 >= var6) {
            break;
         }

         if(!var3[var5].getClass().equals(FinskyLog.class)) {
            String var7 = var3[var5].getClassName();
            int var8 = var7.lastIndexOf(46) + 1;
            String var9 = var7.substring(var8);
            int var10 = var9.lastIndexOf(36) + 1;
            String var11 = var9.substring(var10);
            StringBuilder var12 = (new StringBuilder()).append(var11).append(".");
            String var13 = var3[var5].getMethodName();
            var4 = var12.append(var13).toString();
            break;
         }

         ++var5;
      }

      Locale var14 = Locale.US;
      Object[] var15 = new Object[3];
      Long var16 = Long.valueOf(Thread.currentThread().getId());
      var15[0] = var16;
      var15[1] = var4;
      var15[2] = var2;
      return String.format(var14, "[%d] %s: %s", var15);
   }

   public static void d(String var0, Object ... var1) {
      String var2 = TAG;
      String var3 = buildMessage(var0, var1);
      Log.d(var2, var3);
   }

   public static void e(String var0, Object ... var1) {
      String var2 = TAG;
      String var3 = buildMessage(var0, var1);
      Log.e(var2, var3);
   }

   public static void v(String var0, Object ... var1) {
      String var2 = TAG;
      String var3 = buildMessage(var0, var1);
      Log.v(var2, var3);
   }

   public static void w(String var0, Object ... var1) {
      String var2 = TAG;
      String var3 = buildMessage(var0, var1);
      Log.w(var2, var3);
   }

   public static void wtf(String var0, Object ... var1) {
      String var2 = TAG;
      String var3 = buildMessage(var0, var1);
      Log.e(var2, var3);
      String var5 = TAG;
      String var6 = buildMessage(var0, var1);
      Log.wtf(var5, var6);
   }

   public static void wtf(Throwable var0, String var1, Object ... var2) {
      String var3 = TAG;
      String var4 = buildMessage(var1, var2);
      Log.e(var3, var4, var0);
      String var6 = TAG;
      String var7 = buildMessage(var1, var2);
      Log.wtf(var6, var7, var0);
   }
}
