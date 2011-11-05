package com.google.android.finsky.utils;

import java.util.Arrays;

public class Objects {

   private Objects() {}

   public static boolean equal(Object var0, Object var1) {
      boolean var2;
      if(var0 != var1 && (var0 == null || !var0.equals(var1))) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public static int hashCode(Object ... var0) {
      return Arrays.hashCode(var0);
   }
}
