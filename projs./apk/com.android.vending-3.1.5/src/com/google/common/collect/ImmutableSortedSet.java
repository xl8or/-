package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.EmptyImmutableSortedSet;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSetFauxverideShim;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Platform;
import com.google.common.collect.RegularImmutableSortedSet;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

@GwtCompatible(
   serializable = true
)
public abstract class ImmutableSortedSet<E extends Object> extends ImmutableSortedSetFauxverideShim<E> implements SortedSet<E> {

   private static final ImmutableSortedSet<Object> NATURAL_EMPTY_SET;
   private static final Comparator NATURAL_ORDER = Ordering.natural();
   final transient Comparator<? super E> comparator;


   static {
      Comparator var0 = NATURAL_ORDER;
      NATURAL_EMPTY_SET = new EmptyImmutableSortedSet(var0);
   }

   ImmutableSortedSet(Comparator<? super E> var1) {
      this.comparator = var1;
   }

   public static <E extends Object> ImmutableSortedSet<E> copyOf(Iterable<? extends E> var0) {
      return copyOfInternal(Ordering.natural(), var0, (boolean)0);
   }

   public static <E extends Object> ImmutableSortedSet<E> copyOf(Comparator<? super E> var0, Iterable<? extends E> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      return copyOfInternal(var0, var1, (boolean)0);
   }

   public static <E extends Object> ImmutableSortedSet<E> copyOf(Comparator<? super E> var0, Iterator<? extends E> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      return copyOfInternal(var0, var1);
   }

   public static <E extends Object> ImmutableSortedSet<E> copyOf(Iterator<? extends E> var0) {
      return copyOfInternal(Ordering.natural(), var0);
   }

   private static <E extends Object> ImmutableSortedSet<E> copyOfInternal(Comparator<? super E> var0, Iterable<? extends E> var1, boolean var2) {
      boolean var3;
      if(!var2 && !hasSameComparator(var1, var0)) {
         var3 = false;
      } else {
         var3 = true;
      }

      Object var4;
      if(var3 && var1 instanceof ImmutableSortedSet) {
         var4 = (ImmutableSortedSet)var1;
         if(!((ImmutableSortedSet)var4).hasPartialArray()) {
            return (ImmutableSortedSet)var4;
         }
      }

      Object[] var5 = newObjectArray(var1);
      if(var5.length == 0) {
         var4 = emptySet(var0);
      } else {
         Object[] var6 = var5;
         int var7 = var5.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Object var9 = Preconditions.checkNotNull(var6[var8]);
         }

         if(!var3) {
            sort(var5, var0);
            var5 = removeDupes(var5, var0);
         }

         var4 = new RegularImmutableSortedSet(var5, var0);
      }

      return (ImmutableSortedSet)var4;
   }

   private static <E extends Object> ImmutableSortedSet<E> copyOfInternal(Comparator<? super E> var0, Iterator<? extends E> var1) {
      Object var2;
      if(!var1.hasNext()) {
         var2 = emptySet(var0);
      } else {
         ArrayList var3 = Lists.newArrayList();

         while(var1.hasNext()) {
            Object var4 = Preconditions.checkNotNull(var1.next());
            var3.add(var4);
         }

         Object[] var6 = var3.toArray();
         sort(var6, var0);
         Object[] var7 = removeDupes(var6, var0);
         var2 = new RegularImmutableSortedSet(var7, var0);
      }

      return (ImmutableSortedSet)var2;
   }

   public static <E extends Object> ImmutableSortedSet<E> copyOfSorted(SortedSet<E> var0) {
      Comparator var1 = var0.comparator();
      if(var1 == null) {
         var1 = NATURAL_ORDER;
      }

      return copyOfInternal(var1, var0, (boolean)1);
   }

   private static <E extends Object> ImmutableSortedSet<E> emptySet() {
      return NATURAL_EMPTY_SET;
   }

   static <E extends Object> ImmutableSortedSet<E> emptySet(Comparator<? super E> var0) {
      Object var1;
      if(NATURAL_ORDER.equals(var0)) {
         var1 = emptySet();
      } else {
         var1 = new EmptyImmutableSortedSet(var0);
      }

      return (ImmutableSortedSet)var1;
   }

   static boolean hasSameComparator(Iterable<?> var0, Comparator<?> var1) {
      boolean var2 = false;
      if(var0 instanceof SortedSet) {
         Comparator var3 = ((SortedSet)var0).comparator();
         if(var3 == null) {
            Ordering var4 = Ordering.natural();
            if(var1 == var4) {
               var2 = true;
            }
         } else {
            var2 = var1.equals(var3);
         }
      }

      return var2;
   }

   public static <E extends Object & Comparable<E>> ImmutableSortedSet.Builder<E> naturalOrder() {
      Ordering var0 = Ordering.natural();
      return new ImmutableSortedSet.Builder(var0);
   }

   private static <T extends Object> Object[] newObjectArray(Iterable<T> var0) {
      Object var1;
      if(var0 instanceof Collection) {
         var1 = (Collection)var0;
      } else {
         var1 = Lists.newArrayList(var0);
      }

      Object[] var2 = new Object[((Collection)var1).size()];
      return ((Collection)var1).toArray(var2);
   }

   public static <E extends Object> ImmutableSortedSet<E> of() {
      return emptySet();
   }

   public static <E extends Object & Comparable<? super E>> ImmutableSortedSet<E> of(E var0) {
      Object[] var1 = new Object[1];
      Object var2 = Preconditions.checkNotNull(var0);
      var1[0] = var2;
      Ordering var3 = Ordering.natural();
      return new RegularImmutableSortedSet(var1, var3);
   }

   public static <E extends Object & Comparable<? super E>> ImmutableSortedSet<E> of(E var0, E var1) {
      Ordering var2 = Ordering.natural();
      Comparable[] var3 = new Comparable[]{var0, var1};
      return ofInternal(var2, var3);
   }

   public static <E extends Object & Comparable<? super E>> ImmutableSortedSet<E> of(E var0, E var1, E var2) {
      Ordering var3 = Ordering.natural();
      Comparable[] var4 = new Comparable[]{var0, var1, var2};
      return ofInternal(var3, var4);
   }

   public static <E extends Object & Comparable<? super E>> ImmutableSortedSet<E> of(E var0, E var1, E var2, E var3) {
      Ordering var4 = Ordering.natural();
      Comparable[] var5 = new Comparable[]{var0, var1, var2, var3};
      return ofInternal(var4, var5);
   }

   public static <E extends Object & Comparable<? super E>> ImmutableSortedSet<E> of(E var0, E var1, E var2, E var3, E var4) {
      Ordering var5 = Ordering.natural();
      Comparable[] var6 = new Comparable[]{var0, var1, var2, var3, var4};
      return ofInternal(var5, var6);
   }

   public static <E extends Object & Comparable<? super E>> ImmutableSortedSet<E> of(E ... var0) {
      return ofInternal(Ordering.natural(), var0);
   }

   private static <E extends Object> ImmutableSortedSet<E> ofInternal(Comparator<? super E> var0, E ... var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      Object var7;
      switch(var1.length) {
      case 0:
         var7 = emptySet(var0);
         break;
      default:
         Object[] var3 = new Object[var1.length];
         int var4 = 0;

         while(true) {
            int var5 = var1.length;
            if(var4 >= var5) {
               sort(var3, var0);
               Object[] var8 = removeDupes(var3, var0);
               var7 = new RegularImmutableSortedSet(var8, var0);
               break;
            }

            Object var6 = Preconditions.checkNotNull(var1[var4]);
            var3[var4] = var6;
            ++var4;
         }
      }

      return (ImmutableSortedSet)var7;
   }

   public static <E extends Object> ImmutableSortedSet.Builder<E> orderedBy(Comparator<E> var0) {
      return new ImmutableSortedSet.Builder(var0);
   }

   private void readObject(ObjectInputStream var1) throws InvalidObjectException {
      throw new InvalidObjectException("Use SerializedForm");
   }

   private static <E extends Object> Object[] removeDupes(Object[] var0, Comparator<? super E> var1) {
      int var2 = 1;
      int var3 = 1;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            int var8 = var0.length;
            if(var2 != var8) {
               Object[] var9 = new Object[var2];
               Platform.unsafeArrayCopy(var0, 0, var9, 0, var2);
               var0 = var9;
            }

            return var0;
         }

         Object var5 = var0[var3];
         int var6 = var2 + -1;
         Object var7 = var0[var6];
         if(unsafeCompare(var1, var7, var5) != 0) {
            var0[var2] = var5;
            ++var2;
         }

         ++var3;
      }
   }

   public static <E extends Object & Comparable<E>> ImmutableSortedSet.Builder<E> reverseOrder() {
      Ordering var0 = Ordering.natural().reverse();
      return new ImmutableSortedSet.Builder(var0);
   }

   private static <E extends Object> void sort(Object[] var0, Comparator<? super E> var1) {
      Arrays.sort(var0, var1);
   }

   static int unsafeCompare(Comparator<?> var0, Object var1, Object var2) {
      return var0.compare(var1, var2);
   }

   public Comparator<? super E> comparator() {
      return this.comparator;
   }

   abstract boolean hasPartialArray();

   public ImmutableSortedSet<E> headSet(E var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      return this.headSetImpl(var2);
   }

   abstract ImmutableSortedSet<E> headSetImpl(E var1);

   abstract int indexOf(Object var1);

   public ImmutableSortedSet<E> subSet(E var1, E var2) {
      Object var3 = Preconditions.checkNotNull(var1);
      Object var4 = Preconditions.checkNotNull(var2);
      byte var5;
      if(this.comparator.compare(var1, var2) <= 0) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      Preconditions.checkArgument((boolean)var5);
      return this.subSetImpl(var1, var2);
   }

   abstract ImmutableSortedSet<E> subSetImpl(E var1, E var2);

   public ImmutableSortedSet<E> tailSet(E var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      return this.tailSetImpl(var2);
   }

   abstract ImmutableSortedSet<E> tailSetImpl(E var1);

   int unsafeCompare(Object var1, Object var2) {
      return unsafeCompare(this.comparator, var1, var2);
   }

   Object writeReplace() {
      Comparator var1 = this.comparator;
      Object[] var2 = this.toArray();
      return new ImmutableSortedSet.SerializedForm(var1, var2);
   }

   private static class SerializedForm<E extends Object> implements Serializable {

      private static final long serialVersionUID;
      final Comparator<? super E> comparator;
      final Object[] elements;


      public SerializedForm(Comparator<? super E> var1, Object[] var2) {
         this.comparator = var1;
         this.elements = var2;
      }

      Object readResolve() {
         Comparator var1 = this.comparator;
         ImmutableSortedSet.Builder var2 = new ImmutableSortedSet.Builder(var1);
         Object[] var3 = (Object[])this.elements;
         return var2.add(var3).build();
      }
   }

   public static final class Builder<E extends Object> extends ImmutableSet.Builder<E> {

      private final Comparator<? super E> comparator;


      public Builder(Comparator<? super E> var1) {
         Comparator var2 = (Comparator)Preconditions.checkNotNull(var1);
         this.comparator = var2;
      }

      public ImmutableSortedSet.Builder<E> add(E var1) {
         super.add(var1);
         return this;
      }

      public ImmutableSortedSet.Builder<E> add(E ... var1) {
         super.add(var1);
         return this;
      }

      public ImmutableSortedSet.Builder<E> addAll(Iterable<? extends E> var1) {
         super.addAll(var1);
         return this;
      }

      public ImmutableSortedSet.Builder<E> addAll(Iterator<? extends E> var1) {
         super.addAll(var1);
         return this;
      }

      public ImmutableSortedSet<E> build() {
         Comparator var1 = this.comparator;
         Iterator var2 = this.contents.iterator();
         return ImmutableSortedSet.copyOfInternal(var1, var2);
      }
   }
}
