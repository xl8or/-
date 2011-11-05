package com.google.android.common.base;

import com.google.android.common.base.CharMatcher;

final class Platform {

   private static final ThreadLocal<char[]> DEST_TL = new Platform.1();


   private Platform() {}

   static char[] charBufferFromThreadLocal() {
      return (char[])DEST_TL.get();
   }

   static boolean isInstance(Class<?> var0, Object var1) {
      return var0.isInstance(var1);
   }

   static CharMatcher precomputeCharMatcher(CharMatcher var0) {
      return var0.precomputedInternal();
   }

   static class 1 extends ThreadLocal<char[]> {

      1() {}

      protected char[] initialValue() {
         return new char[1024];
      }
   }
}
