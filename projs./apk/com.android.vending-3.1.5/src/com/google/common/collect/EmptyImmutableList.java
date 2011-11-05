package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class EmptyImmutableList extends ImmutableList<Object> {

   private static final Object[] EMPTY_ARRAY = new Object[0];
   static final EmptyImmutableList INSTANCE = new EmptyImmutableList();
   private static final long serialVersionUID;


   private EmptyImmutableList() {}

   public boolean contains(Object var1) {
      return false;
   }

   public boolean containsAll(Collection<?> var1) {
      return var1.isEmpty();
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 instanceof List) {
         var2 = ((List)var1).isEmpty();
      } else {
         var2 = false;
      }

      return var2;
   }

   public Object get(int var1) {
      int var2 = Preconditions.checkElementIndex(var1, 0);
      throw new AssertionError("unreachable");
   }

   public int hashCode() {
      return 1;
   }

   public int indexOf(Object var1) {
      return -1;
   }

   public boolean isEmpty() {
      return true;
   }

   public UnmodifiableIterator<Object> iterator() {
      return Iterators.emptyIterator();
   }

   public int lastIndexOf(Object var1) {
      return -1;
   }

   public ListIterator<Object> listIterator() {
      return Collections.emptyList().listIterator();
   }

   public ListIterator<Object> listIterator(int var1) {
      int var2 = Preconditions.checkPositionIndex(var1, 0);
      return Collections.emptyList().listIterator();
   }

   Object readResolve() {
      return INSTANCE;
   }

   public int size() {
      return 0;
   }

   public ImmutableList<Object> subList(int var1, int var2) {
      Preconditions.checkPositionIndexes(var1, var2, 0);
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
