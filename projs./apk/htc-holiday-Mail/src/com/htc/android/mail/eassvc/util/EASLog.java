package com.htc.android.mail.eassvc.util;

import android.util.Log;
import com.htc.android.mail.eassvc.common.ExchangeSyncSources;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;

public class EASLog {

   static final boolean VERBOSE = true;


   public EASLog() {}

   public static void d(String var0, long var1, String var3) {
      StringBuffer var4 = new StringBuffer(50);
      StringBuffer var5 = var4.append("[Acc ");
      var4.append(var1);
      StringBuffer var7 = var4.append("]");
      var4.append(var3);
      String var9 = var4.toString();
      Log.d(var0, var9);
   }

   public static void d(String var0, ExchangeSyncSources var1, String var2) {
      if(var1 == null) {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[syncSrcs=null]");
         var3.append(var2);
         String var6 = var3.toString();
         d(var0, 65535L, var6);
      } else {
         StringBuffer var7 = new StringBuffer(50);
         StringBuffer var8 = var7.append("[Acc ");
         long var9 = var1.account.accountId;
         var7.append(var9);
         StringBuffer var12 = var7.append("]");
         var7.append(var2);
         String var14 = var7.toString();
         Log.d(var0, var14);
      }
   }

   public static void d(String var0, ExchangeAccount var1, String var2) {
      if(var1 == null) {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[acc=null]");
         var3.append(var2);
         String var6 = var3.toString();
         d(var0, 65535L, var6);
      } else {
         StringBuffer var7 = new StringBuffer(50);
         StringBuffer var8 = var7.append("[Acc ");
         long var9 = var1.accountId;
         var7.append(var9);
         StringBuffer var12 = var7.append("]");
         var7.append(var2);
         String var14 = var7.toString();
         Log.d(var0, var14);
      }
   }

   public static void d(String var0, String var1) {
      StringBuffer var2 = new StringBuffer(50);
      StringBuffer var3 = var2.append("[ NA ]");
      var2.append(var1);
      String var5 = var2.toString();
      Log.d(var0, var5);
   }

   public static void e(String var0, long var1, Exception var3) {
      StringBuffer var4 = new StringBuffer(50);
      StringBuffer var5 = var4.append("[Acc ");
      var4.append(var1);
      StringBuffer var7 = var4.append("]");
      String var8 = var4.toString();
      Log.w(var0, var8, var3);
   }

   public static void e(String var0, long var1, String var3) {
      StringBuffer var4 = new StringBuffer(50);
      StringBuffer var5 = var4.append("[Acc ");
      var4.append(var1);
      StringBuffer var7 = var4.append("]");
      var4.append(var3);
      String var9 = var4.toString();
      Log.w(var0, var9);
   }

   public static void e(String var0, long var1, String var3, Exception var4) {
      StringBuffer var5 = new StringBuffer(50);
      StringBuffer var6 = var5.append("[Acc ");
      var5.append(var1);
      StringBuffer var8 = var5.append("]");
      var5.append(var3);
      String var10 = var5.toString();
      Log.w(var0, var10, var4);
   }

   public static void e(String var0, ExchangeSyncSources var1, Exception var2) {
      if(var1 == null) {
         e(var0, 65535L, "[syncSrcs=null]", var2);
      } else {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[Acc ");
         long var5 = var1.account.accountId;
         var3.append(var5);
         StringBuffer var8 = var3.append("]");
         String var9 = var3.toString();
         Log.w(var0, var9, var2);
      }
   }

   public static void e(String var0, ExchangeSyncSources var1, String var2) {
      if(var1 == null) {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[syncSrcs=null]");
         var3.append(var2);
         String var6 = var3.toString();
         e(var0, 65535L, var6);
      } else {
         StringBuffer var7 = new StringBuffer(50);
         StringBuffer var8 = var7.append("[Acc ");
         long var9 = var1.account.accountId;
         var7.append(var9);
         StringBuffer var12 = var7.append("]");
         var7.append(var2);
         String var14 = var7.toString();
         Log.w(var0, var14);
      }
   }

   public static void e(String var0, ExchangeSyncSources var1, String var2, Exception var3) {
      if(var1 == null) {
         StringBuffer var4 = new StringBuffer(50);
         StringBuffer var5 = var4.append("[syncSrcs=null]");
         var4.append(var2);
         String var7 = var4.toString();
         e(var0, 65535L, var7, var3);
      } else {
         StringBuffer var8 = new StringBuffer(50);
         StringBuffer var9 = var8.append("[Acc ");
         long var10 = var1.account.accountId;
         var8.append(var10);
         StringBuffer var13 = var8.append("]");
         var8.append(var2);
         String var15 = var8.toString();
         Log.w(var0, var15, var3);
      }
   }

   public static void e(String var0, ExchangeAccount var1, Exception var2) {
      if(var1 == null) {
         e(var0, 65535L, "[acc=null]", var2);
      } else {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[Acc ");
         long var5 = var1.accountId;
         var3.append(var5);
         StringBuffer var8 = var3.append("]");
         String var9 = var3.toString();
         Log.w(var0, var9, var2);
      }
   }

   public static void e(String var0, ExchangeAccount var1, String var2) {
      if(var1 == null) {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[acc=null]");
         var3.append(var2);
         String var6 = var3.toString();
         e(var0, 65535L, var6);
      } else {
         StringBuffer var7 = new StringBuffer(50);
         StringBuffer var8 = var7.append("[Acc ");
         long var9 = var1.accountId;
         var7.append(var9);
         StringBuffer var12 = var7.append("]");
         var7.append(var2);
         String var14 = var7.toString();
         Log.w(var0, var14);
      }
   }

   public static void e(String var0, ExchangeAccount var1, String var2, Exception var3) {
      if(var1 == null) {
         StringBuffer var4 = new StringBuffer(50);
         StringBuffer var5 = var4.append("[acc=null]");
         var4.append(var2);
         String var7 = var4.toString();
         e(var0, 65535L, var7, var3);
      } else {
         StringBuffer var8 = new StringBuffer(50);
         StringBuffer var9 = var8.append("[Acc ");
         long var10 = var1.accountId;
         var8.append(var10);
         StringBuffer var13 = var8.append("]");
         var8.append(var2);
         String var15 = var8.toString();
         Log.w(var0, var15, var3);
      }
   }

   public static void e(String var0, Exception var1) {
      Log.w(var0, "[ NA ]", var1);
   }

   public static void e(String var0, String var1) {
      StringBuffer var2 = new StringBuffer(50);
      StringBuffer var3 = var2.append("[ NA ]");
      var2.append(var1);
      String var5 = var2.toString();
      Log.w(var0, var5);
   }

   public static void e(String var0, String var1, Exception var2) {
      StringBuffer var3 = new StringBuffer(50);
      StringBuffer var4 = var3.append("[ NA ]");
      var3.append(var1);
      String var6 = var3.toString();
      Log.w(var0, var6, var2);
   }

   public static void i(String var0, long var1, String var3) {
      StringBuffer var4 = new StringBuffer(50);
      StringBuffer var5 = var4.append("[Acc ");
      var4.append(var1);
      StringBuffer var7 = var4.append("]");
      var4.append(var3);
      String var9 = var4.toString();
      Log.i(var0, var9);
   }

   public static void i(String var0, ExchangeSyncSources var1, String var2) {
      if(var1 == null) {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[syncSrcs=null]");
         var3.append(var2);
         String var6 = var3.toString();
         i(var0, 65535L, var6);
      } else {
         StringBuffer var7 = new StringBuffer(50);
         StringBuffer var8 = var7.append("[Acc ");
         long var9 = var1.account.accountId;
         var7.append(var9);
         StringBuffer var12 = var7.append("]");
         var7.append(var2);
         String var14 = var7.toString();
         Log.i(var0, var14);
      }
   }

   public static void i(String var0, ExchangeAccount var1, String var2) {
      if(var1 == null) {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[acc=null]");
         var3.append(var2);
         String var6 = var3.toString();
         i(var0, 65535L, var6);
      } else {
         StringBuffer var7 = new StringBuffer(50);
         StringBuffer var8 = var7.append("[Acc ");
         long var9 = var1.accountId;
         var7.append(var9);
         StringBuffer var12 = var7.append("]");
         var7.append(var2);
         StringBuilder var14 = (new StringBuilder()).append("[Acc ");
         long var15 = var1.accountId;
         String var17 = var14.append(var15).append("]").append(var2).toString();
         Log.i(var0, var17);
      }
   }

   public static void i(String var0, String var1) {
      StringBuffer var2 = new StringBuffer(50);
      StringBuffer var3 = var2.append("[ NA ]");
      var2.append(var1);
      String var5 = var2.toString();
      Log.i(var0, var5);
   }

   public static void v(String var0, long var1, String var3) {
      StringBuffer var4 = new StringBuffer(50);
      StringBuffer var5 = var4.append("[Acc ");
      var4.append(var1);
      StringBuffer var7 = var4.append("]");
      var4.append(var3);
      String var9 = var4.toString();
      Log.v(var0, var9);
   }

   public static void v(String var0, ExchangeSyncSources var1, String var2) {
      if(var1 == null) {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[syncSrcs=null]");
         var3.append(var2);
         String var6 = var3.toString();
         v(var0, 65535L, var6);
      } else {
         StringBuffer var7 = new StringBuffer(50);
         StringBuffer var8 = var7.append("[Acc ");
         long var9 = var1.account.accountId;
         var7.append(var9);
         StringBuffer var12 = var7.append("]");
         var7.append(var2);
         String var14 = var7.toString();
         Log.v(var0, var14);
      }
   }

   public static void v(String var0, ExchangeAccount var1, String var2) {
      if(var1 == null) {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[acc=null]");
         var3.append(var2);
         String var6 = var3.toString();
         v(var0, 65535L, var6);
      } else {
         StringBuffer var7 = new StringBuffer(50);
         StringBuffer var8 = var7.append("[Acc ");
         long var9 = var1.accountId;
         var7.append(var9);
         StringBuffer var12 = var7.append("]");
         var7.append(var2);
         String var14 = var7.toString();
         Log.v(var0, var14);
      }
   }

   public static void v(String var0, String var1) {
      StringBuffer var2 = new StringBuffer(50);
      StringBuffer var3 = var2.append("[ NA ]");
      var2.append(var1);
      String var5 = var2.toString();
      Log.v(var0, var5);
   }

   public static void w(String var0, long var1, String var3) {
      StringBuffer var4 = new StringBuffer(50);
      StringBuffer var5 = var4.append("[Acc ");
      var4.append(var1);
      StringBuffer var7 = var4.append("]");
      var4.append(var3);
      String var9 = var4.toString();
      Log.w(var0, var9);
   }

   public static void w(String var0, ExchangeSyncSources var1, String var2) {
      if(var1 == null) {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[syncSrcs=null]");
         var3.append(var2);
         String var6 = var3.toString();
         w(var0, 65535L, var6);
      } else {
         StringBuffer var7 = new StringBuffer(50);
         StringBuffer var8 = var7.append("[Acc ");
         long var9 = var1.account.accountId;
         var7.append(var9);
         StringBuffer var12 = var7.append("]");
         var7.append(var2);
         String var14 = var7.toString();
         Log.w(var0, var14);
      }
   }

   public static void w(String var0, ExchangeAccount var1, String var2) {
      if(var1 == null) {
         StringBuffer var3 = new StringBuffer(50);
         StringBuffer var4 = var3.append("[acc=null]");
         var3.append(var2);
         String var6 = var3.toString();
         w(var0, 65535L, var6);
      } else {
         StringBuffer var7 = new StringBuffer(50);
         StringBuffer var8 = var7.append("[Acc ");
         long var9 = var1.accountId;
         var7.append(var9);
         StringBuffer var12 = var7.append("]");
         var7.append(var2);
         String var14 = var7.toString();
         Log.w(var0, var14);
      }
   }

   public static void w(String var0, String var1) {
      StringBuffer var2 = new StringBuffer(50);
      StringBuffer var3 = var2.append("[ NA ]");
      var2.append(var1);
      String var5 = var2.toString();
      Log.w(var0, var5);
   }
}
