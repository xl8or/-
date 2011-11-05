package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class EmptyImmutableSet extends ImmutableSet<Object> {

   private static final Object[] EMPTY_ARRAY = new Object[0];
   static final EmptyImmutableSet INSTANCE = new EmptyImmutableSet();
   private static final long serialVersionUID;


   private EmptyImmutableSet() {}

   public boolean contains(Object var1) {
      return false;
   }

   public boolean containsAll(Collection<?> var1) {
      return var1.isEmpty();
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 instanceof Set) {
         var2 = ((Set)var1).isEmpty();
      } else {
         var2 = false;
      }

      return var2;
   }

   public final int hashCode() {
      return 0;
   }

   public boolean isEmpty() {
      return true;
   }

   boolean isHashCodeFast() {
      return true;
   }

   public UnmodifiableIterator<Object> iterator() {
      return Iterators.emptyIterator();
   }

   Object readResolve() {
      return INSTANCE;
   }

   public int size() {
      return 0;
   }

   public Object[] toArray() {
      return EMPTY_ARRAY;
   }

   public <T extends Object> T[] toArray(T[] var1) {
      if(var1.length > 0) {
         var1[0] = false;
      }

      return var1;
   }

   public String toString() {
      return "[]";
   }
}
