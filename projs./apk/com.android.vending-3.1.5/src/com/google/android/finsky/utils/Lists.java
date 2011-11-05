package com.google.android.finsky.utils;

import java.util.ArrayList;

public class Lists {

   public Lists() {}

   public static <T extends Object> ArrayList<T> newArrayList() {
      return new ArrayList();
   }

   public static <T extends Object> ArrayList<T> newArrayList(T ... var0) {
      int var1 = var0.length;
      ArrayList var2 = new ArrayList(var1);
      Object[] var3 = var0;
      int var4 = var0.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object var6 = var3[var5];
         var2.add(var6);
      }

      return var2;
   }
}
