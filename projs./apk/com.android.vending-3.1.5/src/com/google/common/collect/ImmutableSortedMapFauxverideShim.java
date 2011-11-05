package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;

@GwtCompatible
abstract class ImmutableSortedMapFauxverideShim<K extends Object, V extends Object> extends ImmutableMap<K, V> {

   ImmutableSortedMapFauxverideShim() {}

   @Deprecated
   public static <K extends Object, V extends Object> ImmutableSortedMap.Builder<K, V> builder() {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K extends Object, V extends Object> ImmutableSortedMap<K, V> of(K var0, V var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K extends Object, V extends Object> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K extends Object, V extends Object> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K extends Object, V extends Object> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <K extends Object, V extends Object> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      throw new UnsupportedOperationException();
   }
}
