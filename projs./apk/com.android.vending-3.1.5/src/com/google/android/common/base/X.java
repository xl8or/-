package com.google.android.common.base;


public final class X {

   private X() {}

   public static void assertTrue(boolean var0) {
      if(!var0) {
         throw new RuntimeException("Assertion failed");
      }
   }

   public static void assertTrue(boolean var0, String var1) {
      if(!var0) {
         String var2 = "Assertion failed: " + var1;
         throw new RuntimeException(var2);
      }
   }
}
