package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Platform;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible
public final class ObjectArrays {

   private ObjectArrays() {}

   private static <T extends Object> T[] arraysCopyOf(T[] var0, int var1) {
      Object[] var2 = newArray(var0, var1);
      int var3 = Math.min(var0.length, var1);
      Platform.unsafeArrayCopy(var0, 0, var2, 0, var3);
      return var2;
   }

   public static <T extends Object> T[] concat(@Nullable T var0, T[] var1) {
      int var2 = var1.length + 1;
      Object[] var3 = newArray(var1, var2);
      var3[0] = var0;
      int var4 = var1.length;
      Platform.unsafeArrayCopy(var1, 0, var3, 1, var4);
      return var3;
   }

   public static <T extends Object> T[] concat(T[] var0, @Nullable T var1) {
      int var2 = var0.length + 1;
      Object[] var3 = arraysCopyOf(var0, var2);
      int var4 = var0.length;
      var3[var4] = var1;
      return var3;
   }

   @GwtIncompatible("Array.newInstance(Class, int)")
   public static <T extends Object> T[] concat(T[] var0, T[] var1, Class<T> var2) {
      int var3 = var0.length;
      int var4 = var1.length;
      int var5 = var3 + var4;
      Object[] var6 = newArray(var2, var5);
      int var7 = var0.length;
      Platform.unsafeArrayCopy(var0, 0, var6, 0, var7);
      int var8 = var0.length;
      int var9 = var1.length;
      Platform.unsafeArrayCopy(var1, 0, var6, var8, var9);
      return var6;
   }

   private static Object[] fillArray(Iterable<?> var0, Object[] var1) {
      int var2 = 0;

      int var5;
      for(Iterator var3 = var0.iterator(); var3.hasNext(); var2 = var5) {
         Object var4 = var3.next();
         var5 = var2 + 1;
         var1[var2] = var4;
      }

      return var1;
   }

   @GwtIncompatible("Array.newInstance(Class, int)")
   public static <T extends Object> T[] newArray(Class<T> var0, int var1) {
      return Platform.newArray(var0, var1);
   }

   public static <T extends Object> T[] newArray(T[] var0, int var1) {
      return Platform.newArray(var0, var1);
   }

   static Object[] toArrayImpl(Collection<?> var0) {
      Object[] var1 = new Object[var0.size()];
      return fillArray(var0, var1);
   }

   static <T extends Object> T[] toArrayImpl(Collection<?> var0, T[] var1) {
      int var2 = var0.size();
      if(var1.length < var2) {
         var1 = newArray(var1, var2);
      }

      fillArray(var0, var1);
      if(var1.length > var2) {
         var1[var2] = false;
      }

      return var1;
   }
}
