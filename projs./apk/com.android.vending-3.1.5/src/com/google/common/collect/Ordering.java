package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ByFunctionOrdering;
import com.google.common.collect.ComparatorOrdering;
import com.google.common.collect.CompoundOrdering;
import com.google.common.collect.ExplicitOrdering;
import com.google.common.collect.LexicographicalOrdering;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.NaturalOrdering;
import com.google.common.collect.NullsFirstOrdering;
import com.google.common.collect.NullsLastOrdering;
import com.google.common.collect.Platform;
import com.google.common.collect.ReverseOrdering;
import com.google.common.collect.UsingToStringOrdering;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@GwtCompatible
public abstract class Ordering<T extends Object> implements Comparator<T> {

   static final int LEFT_IS_GREATER = 1;
   static final int RIGHT_IS_GREATER = 255;


   protected Ordering() {}

   public static Ordering<Object> arbitrary() {
      return Ordering.ArbitraryOrderingHolder.ARBITRARY_ORDERING;
   }

   @GwtCompatible(
      serializable = true
   )
   public static <T extends Object> Ordering<T> compound(Iterable<? extends Comparator<? super T>> var0) {
      return new CompoundOrdering(var0);
   }

   @GwtCompatible(
      serializable = true
   )
   public static <T extends Object> Ordering<T> explicit(T var0, T ... var1) {
      return explicit(Lists.asList(var0, var1));
   }

   @GwtCompatible(
      serializable = true
   )
   public static <T extends Object> Ordering<T> explicit(List<T> var0) {
      return new ExplicitOrdering(var0);
   }

   @Deprecated
   @GwtCompatible(
      serializable = true
   )
   public static <T extends Object> Ordering<T> from(Ordering<T> var0) {
      return (Ordering)Preconditions.checkNotNull(var0);
   }

   @GwtCompatible(
      serializable = true
   )
   public static <T extends Object> Ordering<T> from(Comparator<T> var0) {
      Object var1;
      if(var0 instanceof Ordering) {
         var1 = (Ordering)var0;
      } else {
         var1 = new ComparatorOrdering(var0);
      }

      return (Ordering)var1;
   }

   @GwtCompatible(
      serializable = true
   )
   public static <C extends Object & Comparable> Ordering<C> natural() {
      return NaturalOrdering.INSTANCE;
   }

   @GwtCompatible(
      serializable = true
   )
   public static Ordering<Object> usingToString() {
      return UsingToStringOrdering.INSTANCE;
   }

   public int binarySearch(List<? extends T> var1, T var2) {
      return Collections.binarySearch(var1, var2, this);
   }

   @GwtCompatible(
      serializable = true
   )
   public <U extends T> Ordering<U> compound(Comparator<? super U> var1) {
      Comparator var2 = (Comparator)Preconditions.checkNotNull(var1);
      return new CompoundOrdering(this, var2);
   }

   public boolean isOrdered(Iterable<? extends T> var1) {
      Iterator var2 = var1.iterator();
      Object var4;
      boolean var5;
      if(var2.hasNext()) {
         for(Object var3 = var2.next(); var2.hasNext(); var3 = var4) {
            var4 = var2.next();
            if(this.compare(var3, var4) > 0) {
               var5 = false;
               return var5;
            }
         }
      }

      var5 = true;
      return var5;
   }

   public boolean isStrictlyOrdered(Iterable<? extends T> var1) {
      Iterator var2 = var1.iterator();
      Object var4;
      boolean var5;
      if(var2.hasNext()) {
         for(Object var3 = var2.next(); var2.hasNext(); var3 = var4) {
            var4 = var2.next();
            if(this.compare(var3, var4) >= 0) {
               var5 = false;
               return var5;
            }
         }
      }

      var5 = true;
      return var5;
   }

   @GwtCompatible(
      serializable = true
   )
   public <S extends T> Ordering<Iterable<S>> lexicographical() {
      return new LexicographicalOrdering(this);
   }

   public <E extends T> E max(Iterable<E> var1) {
      Iterator var2 = var1.iterator();

      Object var3;
      Object var4;
      for(var3 = var2.next(); var2.hasNext(); var3 = this.max(var3, var4)) {
         var4 = var2.next();
      }

      return var3;
   }

   public <E extends T> E max(E var1, E var2) {
      if(this.compare(var1, var2) < 0) {
         var1 = var2;
      }

      return var1;
   }

   public <E extends T> E max(E var1, E var2, E var3, E ... var4) {
      Object var5 = this.max(var1, var2);
      Object var6 = this.max(var5, var3);
      Object[] var7 = var4;
      int var8 = var4.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Object var10 = var7[var9];
         var6 = this.max(var6, var10);
      }

      return var6;
   }

   public <E extends T> E min(Iterable<E> var1) {
      Iterator var2 = var1.iterator();

      Object var3;
      Object var4;
      for(var3 = var2.next(); var2.hasNext(); var3 = this.min(var3, var4)) {
         var4 = var2.next();
      }

      return var3;
   }

   public <E extends T> E min(E var1, E var2) {
      if(this.compare(var1, var2) > 0) {
         var1 = var2;
      }

      return var1;
   }

   public <E extends T> E min(E var1, E var2, E var3, E ... var4) {
      Object var5 = this.min(var1, var2);
      Object var6 = this.min(var5, var3);
      Object[] var7 = var4;
      int var8 = var4.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Object var10 = var7[var9];
         var6 = this.min(var6, var10);
      }

      return var6;
   }

   @GwtCompatible(
      serializable = true
   )
   public <S extends T> Ordering<S> nullsFirst() {
      return new NullsFirstOrdering(this);
   }

   @GwtCompatible(
      serializable = true
   )
   public <S extends T> Ordering<S> nullsLast() {
      return new NullsLastOrdering(this);
   }

   @GwtCompatible(
      serializable = true
   )
   public <F extends Object> Ordering<F> onResultOf(Function<F, ? extends T> var1) {
      return new ByFunctionOrdering(var1, this);
   }

   @GwtCompatible(
      serializable = true
   )
   public <S extends T> Ordering<S> reverse() {
      return new ReverseOrdering(this);
   }

   public <E extends T> List<E> sortedCopy(Iterable<E> var1) {
      ArrayList var2 = Lists.newArrayList(var1);
      Collections.sort(var2, this);
      return var2;
   }

   @VisibleForTesting
   static class ArbitraryOrdering extends Ordering<Object> {

      private Map<Object, Integer> uids;


      ArbitraryOrdering() {
         MapMaker var1 = Platform.tryWeakKeys(new MapMaker());
         Ordering.ArbitraryOrdering.1 var2 = new Ordering.ArbitraryOrdering.1();
         ConcurrentMap var3 = var1.makeComputingMap(var2);
         this.uids = var3;
      }

      public int compare(Object var1, Object var2) {
         int var3;
         if(var1 == var2) {
            var3 = 0;
         } else {
            int var4 = this.identityHashCode(var1);
            int var5 = this.identityHashCode(var2);
            if(var4 != var5) {
               if(var4 < var5) {
                  var3 = -1;
               } else {
                  var3 = 1;
               }
            } else {
               Integer var6 = (Integer)this.uids.get(var1);
               Integer var7 = (Integer)this.uids.get(var2);
               int var8 = var6.compareTo(var7);
               if(var8 == 0) {
                  throw new AssertionError();
               }

               var3 = var8;
            }
         }

         return var3;
      }

      int identityHashCode(Object var1) {
         return System.identityHashCode(var1);
      }

      public String toString() {
         return "Ordering.arbitrary()";
      }

      class 1 implements Function<Object, Integer> {

         final AtomicInteger counter;


         1() {
            AtomicInteger var2 = new AtomicInteger(0);
            this.counter = var2;
         }

         public Integer apply(Object var1) {
            return Integer.valueOf(this.counter.getAndIncrement());
         }
      }
   }

   @VisibleForTesting
   static class IncomparableValueException extends ClassCastException {

      private static final long serialVersionUID;
      final Object value;


      IncomparableValueException(Object var1) {
         String var2 = "Cannot compare value: " + var1;
         super(var2);
         this.value = var1;
      }
   }

   private static class ArbitraryOrderingHolder {

      static final Ordering<Object> ARBITRARY_ORDERING = new Ordering.ArbitraryOrdering();


      private ArbitraryOrderingHolder() {}
   }
}
