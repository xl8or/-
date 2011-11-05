package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

@GwtCompatible
abstract class ImmutableSortedSetFauxverideShim<E extends Object> extends ImmutableSet<E> {

   ImmutableSortedSetFauxverideShim() {}

   @Deprecated
   public static <E extends Object> ImmutableSortedSet.Builder<E> builder() {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E extends Object> ImmutableSortedSet<E> of(E var0) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E extends Object> ImmutableSortedSet<E> of(E var0, E var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E extends Object> ImmutableSortedSet<E> of(E var0, E var1, E var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E extends Object> ImmutableSortedSet<E> of(E var0, E var1, E var2, E var3) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E extends Object> ImmutableSortedSet<E> of(E var0, E var1, E var2, E var3, E var4) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static <E extends Object> ImmutableSortedSet<E> of(E ... var0) {
      throw new UnsupportedOperationException();
   }
}
