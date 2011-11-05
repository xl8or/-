package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Platform;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible
public final class Lists {

   private Lists() {}

   public static <E extends Object> List<E> asList(@Nullable E var0, @Nullable E var1, E[] var2) {
      return new Lists.TwoPlusArrayList(var0, var1, var2);
   }

   public static <E extends Object> List<E> asList(@Nullable E var0, E[] var1) {
      return new Lists.OnePlusArrayList(var0, var1);
   }

   @VisibleForTesting
   static int computeArrayListCapacity(int var0) {
      byte var1;
      if(var0 >= 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      long var2 = (long)var0;
      long var4 = 5L + var2;
      long var6 = (long)(var0 / 10);
      return (int)Math.min(var4 + var6, 2147483647L);
   }

   @GwtCompatible(
      serializable = true
   )
   public static <E extends Object> ArrayList<E> newArrayList() {
      return new ArrayList();
   }

   @GwtCompatible(
      serializable = true
   )
   public static <E extends Object> ArrayList<E> newArrayList(Iterable<? extends E> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      ArrayList var3;
      if(var0 instanceof Collection) {
         Collection var2 = (Collection)var0;
         var3 = new ArrayList(var2);
      } else {
         var3 = newArrayList(var0.iterator());
      }

      return var3;
   }

   @GwtCompatible(
      serializable = true
   )
   public static <E extends Object> ArrayList<E> newArrayList(Iterator<? extends E> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      ArrayList var2 = newArrayList();

      while(var0.hasNext()) {
         Object var3 = var0.next();
         var2.add(var3);
      }

      return var2;
   }

   @GwtCompatible(
      serializable = true
   )
   public static <E extends Object> ArrayList<E> newArrayList(E ... var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      int var2 = computeArrayListCapacity(var0.length);
      ArrayList var3 = new ArrayList(var2);
      Collections.addAll(var3, var0);
      return var3;
   }

   @GwtCompatible(
      serializable = true
   )
   public static <E extends Object> ArrayList<E> newArrayListWithCapacity(int var0) {
      return new ArrayList(var0);
   }

   @GwtCompatible(
      serializable = true
   )
   public static <E extends Object> ArrayList<E> newArrayListWithExpectedSize(int var0) {
      int var1 = computeArrayListCapacity(var0);
      return new ArrayList(var1);
   }

   @GwtCompatible(
      serializable = true
   )
   public static <E extends Object> LinkedList<E> newLinkedList() {
      return new LinkedList();
   }

   @GwtCompatible(
      serializable = true
   )
   public static <E extends Object> LinkedList<E> newLinkedList(Iterable<? extends E> var0) {
      LinkedList var1 = newLinkedList();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         var1.add(var3);
      }

      return var1;
   }

   public static <T extends Object> List<List<T>> partition(List<T> var0, int var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      byte var3;
      if(var1 > 0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Preconditions.checkArgument((boolean)var3);
      Object var4;
      if(var0 instanceof RandomAccess) {
         var4 = new Lists.RandomAccessPartition(var0, var1);
      } else {
         var4 = new Lists.Partition(var0, var1);
      }

      return (List)var4;
   }

   public static <F extends Object, T extends Object> List<T> transform(List<F> var0, Function<? super F, ? extends T> var1) {
      Object var2;
      if(var0 instanceof RandomAccess) {
         var2 = new Lists.TransformingRandomAccessList(var0, var1);
      } else {
         var2 = new Lists.TransformingSequentialList(var0, var1);
      }

      return (List)var2;
   }

   private static class RandomAccessPartition<T extends Object> extends Lists.Partition<T> implements RandomAccess {

      RandomAccessPartition(List<T> var1, int var2) {
         super(var1, var2);
      }
   }

   private static class OnePlusArrayList<E extends Object> extends AbstractList<E> implements Serializable, RandomAccess {

      private static final long serialVersionUID;
      final E first;
      final E[] rest;


      OnePlusArrayList(@Nullable E var1, E[] var2) {
         this.first = var1;
         Object[] var3 = (Object[])Preconditions.checkNotNull(var2);
         this.rest = var3;
      }

      public E get(int var1) {
         int var2 = this.size();
         Preconditions.checkElementIndex(var1, var2);
         Object var4;
         if(var1 == 0) {
            var4 = this.first;
         } else {
            Object[] var5 = this.rest;
            int var6 = var1 + -1;
            var4 = var5[var6];
         }

         return var4;
      }

      public int size() {
         return this.rest.length + 1;
      }
   }

   private static class TransformingRandomAccessList<F extends Object, T extends Object> extends AbstractList<T> implements RandomAccess, Serializable {

      private static final long serialVersionUID;
      final List<F> fromList;
      final Function<? super F, ? extends T> function;


      TransformingRandomAccessList(List<F> var1, Function<? super F, ? extends T> var2) {
         List var3 = (List)Preconditions.checkNotNull(var1);
         this.fromList = var3;
         Function var4 = (Function)Preconditions.checkNotNull(var2);
         this.function = var4;
      }

      public void clear() {
         this.fromList.clear();
      }

      public T get(int var1) {
         Function var2 = this.function;
         Object var3 = this.fromList.get(var1);
         return var2.apply(var3);
      }

      public boolean isEmpty() {
         return this.fromList.isEmpty();
      }

      public T remove(int var1) {
         Function var2 = this.function;
         Object var3 = this.fromList.remove(var1);
         return var2.apply(var3);
      }

      public int size() {
         return this.fromList.size();
      }
   }

   private static class Partition<T extends Object> extends AbstractList<List<T>> {

      final List<T> list;
      final int size;


      Partition(List<T> var1, int var2) {
         this.list = var1;
         this.size = var2;
      }

      public List<T> get(int var1) {
         int var2 = this.size();
         Preconditions.checkElementIndex(var1, var2);
         int var4 = this.size;
         int var5 = var1 * var4;
         int var6 = this.size + var5;
         int var7 = this.list.size();
         int var8 = Math.min(var6, var7);
         return Platform.subList(this.list, var5, var8);
      }

      public boolean isEmpty() {
         return this.list.isEmpty();
      }

      public int size() {
         int var1 = this.list.size();
         int var2 = this.size;
         int var3 = var1 + var2 + -1;
         int var4 = this.size;
         return var3 / var4;
      }
   }

   private static class TwoPlusArrayList<E extends Object> extends AbstractList<E> implements Serializable, RandomAccess {

      private static final long serialVersionUID;
      final E first;
      final E[] rest;
      final E second;


      TwoPlusArrayList(@Nullable E var1, @Nullable E var2, E[] var3) {
         this.first = var1;
         this.second = var2;
         Object[] var4 = (Object[])Preconditions.checkNotNull(var3);
         this.rest = var4;
      }

      public E get(int var1) {
         Object var6;
         switch(var1) {
         case 0:
            var6 = this.first;
            break;
         case 1:
            var6 = this.second;
            break;
         default:
            int var2 = this.size();
            Preconditions.checkElementIndex(var1, var2);
            Object[] var4 = this.rest;
            int var5 = var1 + -2;
            var6 = var4[var5];
         }

         return var6;
      }

      public int size() {
         return this.rest.length + 2;
      }
   }

   private static class TransformingSequentialList<F extends Object, T extends Object> extends AbstractSequentialList<T> implements Serializable {

      private static final long serialVersionUID;
      final List<F> fromList;
      final Function<? super F, ? extends T> function;


      TransformingSequentialList(List<F> var1, Function<? super F, ? extends T> var2) {
         List var3 = (List)Preconditions.checkNotNull(var1);
         this.fromList = var3;
         Function var4 = (Function)Preconditions.checkNotNull(var2);
         this.function = var4;
      }

      public void clear() {
         this.fromList.clear();
      }

      public ListIterator<T> listIterator(int var1) {
         ListIterator var2 = this.fromList.listIterator(var1);
         return new Lists.TransformingSequentialList.1(var2);
      }

      public int size() {
         return this.fromList.size();
      }

      class 1 implements ListIterator<T> {

         // $FF: synthetic field
         final ListIterator val$delegate;


         1(ListIterator var2) {
            this.val$delegate = var2;
         }

         public void add(T var1) {
            throw new UnsupportedOperationException();
         }

         public boolean hasNext() {
            return this.val$delegate.hasNext();
         }

         public boolean hasPrevious() {
            return this.val$delegate.hasPrevious();
         }

         public T next() {
            Function var1 = TransformingSequentialList.this.function;
            Object var2 = this.val$delegate.next();
            return var1.apply(var2);
         }

         public int nextIndex() {
            return this.val$delegate.nextIndex();
         }

         public T previous() {
            Function var1 = TransformingSequentialList.this.function;
            Object var2 = this.val$delegate.previous();
            return var1.apply(var2);
         }

         public int previousIndex() {
            return this.val$delegate.previousIndex();
         }

         public void remove() {
            this.val$delegate.remove();
         }

         public void set(T var1) {
            throw new UnsupportedOperationException("not supported");
         }
      }
   }
}
