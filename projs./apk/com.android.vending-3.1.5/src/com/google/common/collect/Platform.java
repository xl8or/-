package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.MapMaker;
import java.lang.reflect.Array;
import java.util.List;

@GwtCompatible(
   emulated = true
)
class Platform {

   private Platform() {}

   static <T extends Object> T[] clone(T[] var0) {
      return (Object[])var0.clone();
   }

   @GwtIncompatible("Class.isInstance")
   static boolean isInstance(Class<?> var0, Object var1) {
      return var0.isInstance(var1);
   }

   @GwtIncompatible("Array.newInstance(Class, int)")
   static <T extends Object> T[] newArray(Class<T> var0, int var1) {
      return (Object[])((Object[])Array.newInstance(var0, var1));
   }

   static <T extends Object> T[] newArray(T[] var0, int var1) {
      return (Object[])((Object[])Array.newInstance(var0.getClass().getComponentType(), var1));
   }

   @GwtIncompatible("List.subList")
   static <T extends Object> List<T> subList(List<T> var0, int var1, int var2) {
      return var0.subList(var1, var2);
   }

   static MapMaker tryWeakKeys(MapMaker var0) {
      return var0.weakKeys();
   }

   static void unsafeArrayCopy(Object[] var0, int var1, Object[] var2, int var3, int var4) {
      System.arraycopy(var0, var1, var2, var3, var4);
   }
}
