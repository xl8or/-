package com.htc.android.mail;

import android.util.Log;

public class ll {

   private static String MsgVersionPrefix = "06021143 ";


   public ll() {}

   public static void d(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      String var3 = MsgVersionPrefix;
      String var4 = var2.append(var3).append(var1).toString();
      Log.d(var0, var4);
   }

   public static void d(String var0, String var1, Exception var2) {
      StringBuilder var3 = new StringBuilder();
      String var4 = MsgVersionPrefix;
      String var5 = var3.append(var4).append(var1).toString();
      Log.d(var0, var5, var2);
   }

   public static void e(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      String var3 = MsgVersionPrefix;
      String var4 = var2.append(var3).append(var1).toString();
      Log.i(var0, var4);
   }

   public static void e(String var0, String var1, Exception var2) {
      StringBuilder var3 = new StringBuilder();
      String var4 = MsgVersionPrefix;
      String var5 = var3.append(var4).append(var1).toString();
      Log.d(var0, var5, var2);
   }

   public static void i(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      String var3 = MsgVersionPrefix;
      String var4 = var2.append(var3).append(var1).toString();
      Log.i(var0, var4);
   }

   public static void i(String var0, String var1, Exception var2) {
      StringBuilder var3 = new StringBuilder();
      String var4 = MsgVersionPrefix;
      String var5 = var3.append(var4).append(var1).toString();
      Log.i(var0, var5, var2);
   }

   public static void w(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      String var3 = MsgVersionPrefix;
      String var4 = var2.append(var3).append(var1).toString();
      Log.i(var0, var4);
   }

   public static void w(String var0, String var1, Exception var2) {
      StringBuilder var3 = new StringBuilder();
      String var4 = MsgVersionPrefix;
      String var5 = var3.append(var4).append(var1).toString();
      Log.d(var0, var5, var2);
   }
}
