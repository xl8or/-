package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
class EmptyImmutableSortedSet<E extends Object> extends ImmutableSortedSet<E> {

   private static final Object[] EMPTY_ARRAY = new Object[0];


   EmptyImmutableSortedSet(Comparator<? super E> var1) {
      super(var1);
   }

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

   public E first() {
      throw new NoSuchElementException();
   }

   boolean hasPartialArray() {
      return false;
   }

   public int hashCode() {
      return 0;
   }

   ImmutableSortedSet<E> headSetImpl(E var1) {
      return this;
   }

   int indexOf(Object var1) {
      return -1;
   }

   public boolean isEmpty() {
      return true;
   }

   public UnmodifiableIterator<E> iterator() {
      return Iterators.emptyIterator();
   }

   public E last() {
      throw new NoSuchElementException();
   }

   public int size() {
      return 0;
   }

   ImmutableSortedSet<E> subSetImpl(E var1, E var2) {
      return this;
   }

   ImmutableSortedSet<E> tailSetImpl(E var1) {
      return this;
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
