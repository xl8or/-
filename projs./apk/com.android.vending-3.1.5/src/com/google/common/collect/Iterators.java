package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.Platform;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtCompatible
public final class Iterators {

   static final UnmodifiableIterator<Object> EMPTY_ITERATOR = new Iterators.1();
   private static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR = new Iterators.2();


   private Iterators() {}

   public static <T extends Object> boolean addAll(Collection<T> var0, Iterator<? extends T> var1) {
      Object var2 = Preconditions.checkNotNull(var0);

      boolean var3;
      boolean var5;
      for(var3 = false; var1.hasNext(); var3 |= var5) {
         Object var4 = var1.next();
         var5 = var0.add(var4);
      }

      return var3;
   }

   public static <T extends Object> boolean all(Iterator<T> var0, Predicate<? super T> var1) {
      Object var2 = Preconditions.checkNotNull(var1);

      boolean var4;
      while(true) {
         if(var0.hasNext()) {
            Object var3 = var0.next();
            if(var1.apply(var3)) {
               continue;
            }

            var4 = false;
            break;
         }

         var4 = true;
         break;
      }

      return var4;
   }

   public static <T extends Object> boolean any(Iterator<T> var0, Predicate<? super T> var1) {
      Object var2 = Preconditions.checkNotNull(var1);

      boolean var4;
      while(true) {
         if(var0.hasNext()) {
            Object var3 = var0.next();
            if(!var1.apply(var3)) {
               continue;
            }

            var4 = true;
            break;
         }

         var4 = false;
         break;
      }

      return var4;
   }

   public static <T extends Object> Enumeration<T> asEnumeration(Iterator<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterators.14(var0);
   }

   public static <T extends Object> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterators.5(var0);
   }

   public static <T extends Object> Iterator<T> concat(Iterator<? extends T> var0, Iterator<? extends T> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      Iterator[] var4 = new Iterator[]{var0, var1};
      return concat(Arrays.asList(var4).iterator());
   }

   public static <T extends Object> Iterator<T> concat(Iterator<? extends T> var0, Iterator<? extends T> var1, Iterator<? extends T> var2) {
      Object var3 = Preconditions.checkNotNull(var0);
      Object var4 = Preconditions.checkNotNull(var1);
      Object var5 = Preconditions.checkNotNull(var2);
      Iterator[] var6 = new Iterator[]{var0, var1, var2};
      return concat(Arrays.asList(var6).iterator());
   }

   public static <T extends Object> Iterator<T> concat(Iterator<? extends T> var0, Iterator<? extends T> var1, Iterator<? extends T> var2, Iterator<? extends T> var3) {
      Object var4 = Preconditions.checkNotNull(var0);
      Object var5 = Preconditions.checkNotNull(var1);
      Object var6 = Preconditions.checkNotNull(var2);
      Object var7 = Preconditions.checkNotNull(var3);
      Iterator[] var8 = new Iterator[]{var0, var1, var2, var3};
      return concat(Arrays.asList(var8).iterator());
   }

   public static <T extends Object> Iterator<T> concat(Iterator<? extends T> ... var0) {
      return concat((Iterator)ImmutableList.of((Object[])var0).iterator());
   }

   public static <T extends Object> Iterator<T> consumingIterator(Iterator<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterators.9(var0);
   }

   public static boolean contains(Iterator<?> var0, @Nullable Object var1) {
      boolean var2 = true;
      if(var1 == null) {
         while(var0.hasNext()) {
            if(var0.next() == null) {
               return var2;
            }
         }
      } else {
         while(var0.hasNext()) {
            Object var3 = var0.next();
            if(var1.equals(var3)) {
               return var2;
            }
         }
      }

      var2 = false;
      return var2;
   }

   public static <T extends Object> Iterator<T> cycle(Iterable<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterators.4(var0);
   }

   public static <T extends Object> Iterator<T> cycle(T ... var0) {
      return cycle((Iterable)Lists.newArrayList(var0));
   }

   public static boolean elementsEqual(Iterator<?> var0, Iterator<?> var1) {
      boolean var2 = false;

      Object var3;
      Object var4;
      do {
         if(!var0.hasNext()) {
            if(!var1.hasNext()) {
               var2 = true;
            }
            break;
         }

         if(!var1.hasNext()) {
            break;
         }

         var3 = var0.next();
         var4 = var1.next();
      } while(Objects.equal(var3, var4));

      return var2;
   }

   public static <T extends Object> UnmodifiableIterator<T> emptyIterator() {
      return EMPTY_ITERATOR;
   }

   static <T extends Object> Iterator<T> emptyModifiableIterator() {
      return EMPTY_MODIFIABLE_ITERATOR;
   }

   public static <T extends Object> UnmodifiableIterator<T> filter(Iterator<T> var0, Predicate<? super T> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new Iterators.7(var0, var1);
   }

   @GwtIncompatible("Class.isInstance")
   public static <T extends Object> UnmodifiableIterator<T> filter(Iterator<?> var0, Class<T> var1) {
      Predicate var2 = Predicates.instanceOf(var1);
      return filter(var0, var2);
   }

   public static <T extends Object> T find(Iterator<T> var0, Predicate<? super T> var1) {
      return filter(var0, var1).next();
   }

   public static <T extends Object> UnmodifiableIterator<T> forArray(T ... var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterators.10(var0);
   }

   static <T extends Object> UnmodifiableIterator<T> forArray(T[] var0, int var1, int var2) {
      byte var3;
      if(var2 >= 0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Preconditions.checkArgument((boolean)var3);
      int var4 = var1 + var2;
      int var5 = var0.length;
      Preconditions.checkPositionIndexes(var1, var4, var5);
      return new Iterators.11(var1, var4, var0);
   }

   public static <T extends Object> UnmodifiableIterator<T> forEnumeration(Enumeration<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterators.13(var0);
   }

   public static int frequency(Iterator<?> var0, @Nullable Object var1) {
      int var2 = 0;
      if(var1 == null) {
         while(var0.hasNext()) {
            if(var0.next() == null) {
               ++var2;
            }
         }
      } else {
         while(var0.hasNext()) {
            Object var3 = var0.next();
            if(var1.equals(var3)) {
               ++var2;
            }
         }
      }

      return var2;
   }

   public static <T extends Object> T get(Iterator<T> var0, int var1) {
      if(var1 < 0) {
         String var2 = "position (" + var1 + ") must not be negative";
         throw new IndexOutOfBoundsException(var2);
      } else {
         int var3;
         int var5;
         for(var3 = 0; var0.hasNext(); var3 = var5) {
            Object var4 = var0.next();
            var5 = var3 + 1;
            if(var3 == var1) {
               return var4;
            }
         }

         String var6 = "position (" + var1 + ") must be less than the number of elements that remained (" + var3 + ")";
         throw new IndexOutOfBoundsException(var6);
      }
   }

   public static <T extends Object> T getLast(Iterator<T> var0) {
      Object var1;
      do {
         var1 = var0.next();
      } while(var0.hasNext());

      return var1;
   }

   public static <T extends Object> T getOnlyElement(Iterator<T> var0) {
      Object var1 = var0.next();
      if(!var0.hasNext()) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         String var3 = "expected one element but was: <" + var1;
         var2.append(var3);

         for(int var5 = 0; var5 < 4 && var0.hasNext(); ++var5) {
            StringBuilder var6 = (new StringBuilder()).append(", ");
            Object var7 = var0.next();
            String var8 = var6.append(var7).toString();
            var2.append(var8);
         }

         if(var0.hasNext()) {
            StringBuilder var10 = var2.append(", ...");
         }

         StringBuilder var11 = var2.append(">");
         String var12 = var2.toString();
         throw new IllegalArgumentException(var12);
      }
   }

   public static <T extends Object> T getOnlyElement(Iterator<T> var0, @Nullable T var1) {
      if(var0.hasNext()) {
         var1 = getOnlyElement(var0);
      }

      return var1;
   }

   public static <T extends Object> int indexOf(Iterator<T> var0, Predicate<? super T> var1) {
      Object var2 = Preconditions.checkNotNull(var1, "predicate");
      int var3 = 0;

      while(true) {
         if(!var0.hasNext()) {
            var3 = -1;
            break;
         }

         Object var4 = var0.next();
         if(var1.apply(var4)) {
            break;
         }

         ++var3;
      }

      return var3;
   }

   public static <T extends Object> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> var0, int var1) {
      return partitionImpl(var0, var1, (boolean)1);
   }

   public static <T extends Object> UnmodifiableIterator<List<T>> partition(Iterator<T> var0, int var1) {
      return partitionImpl(var0, var1, (boolean)0);
   }

   private static <T extends Object> UnmodifiableIterator<List<T>> partitionImpl(Iterator<T> var0, int var1, boolean var2) {
      Object var3 = Preconditions.checkNotNull(var0);
      byte var4;
      if(var1 > 0) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      Preconditions.checkArgument((boolean)var4);
      return new Iterators.6(var0, var1, var2);
   }

   public static <T extends Object> PeekingIterator<T> peekingIterator(Iterator<? extends T> var0) {
      Iterators.PeekingImpl var1;
      if(var0 instanceof Iterators.PeekingImpl) {
         var1 = (Iterators.PeekingImpl)var0;
      } else {
         var1 = new Iterators.PeekingImpl(var0);
      }

      return var1;
   }

   public static boolean removeAll(Iterator<?> var0, Collection<?> var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      boolean var3 = false;

      while(var0.hasNext()) {
         Object var4 = var0.next();
         if(var1.contains(var4)) {
            var0.remove();
            var3 = true;
         }
      }

      return var3;
   }

   public static <T extends Object> boolean removeIf(Iterator<T> var0, Predicate<? super T> var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      boolean var3 = false;

      while(var0.hasNext()) {
         Object var4 = var0.next();
         if(var1.apply(var4)) {
            var0.remove();
            var3 = true;
         }
      }

      return var3;
   }

   public static boolean retainAll(Iterator<?> var0, Collection<?> var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      boolean var3 = false;

      while(var0.hasNext()) {
         Object var4 = var0.next();
         if(!var1.contains(var4)) {
            var0.remove();
            var3 = true;
         }
      }

      return var3;
   }

   public static <T extends Object> UnmodifiableIterator<T> singletonIterator(@Nullable T var0) {
      return new Iterators.12(var0);
   }

   public static int size(Iterator<?> var0) {
      int var1;
      for(var1 = 0; var0.hasNext(); ++var1) {
         Object var2 = var0.next();
      }

      return var1;
   }

   @GwtIncompatible("Array.newArray")
   public static <T extends Object> T[] toArray(Iterator<? extends T> var0, Class<T> var1) {
      return Iterables.toArray(Lists.newArrayList(var0), var1);
   }

   public static String toString(Iterator<?> var0) {
      String var1;
      if(!var0.hasNext()) {
         var1 = "[]";
      } else {
         StringBuilder var2 = new StringBuilder();
         StringBuilder var3 = var2.append('[');
         Object var4 = var0.next();
         var3.append(var4);

         while(var0.hasNext()) {
            StringBuilder var6 = var2.append(", ");
            Object var7 = var0.next();
            var6.append(var7);
         }

         var1 = var2.append(']').toString();
      }

      return var1;
   }

   public static <F extends Object, T extends Object> Iterator<T> transform(Iterator<F> var0, Function<? super F, ? extends T> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new Iterators.8(var0, var1);
   }

   public static <T extends Object> UnmodifiableIterator<T> unmodifiableIterator(Iterator<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterators.3(var0);
   }

   static class 12 extends UnmodifiableIterator<T> {

      boolean done;
      // $FF: synthetic field
      final Object val$value;


      12(Object var1) {
         this.val$value = var1;
      }

      public boolean hasNext() {
         boolean var1;
         if(!this.done) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public T next() {
         if(this.done) {
            throw new NoSuchElementException();
         } else {
            this.done = (boolean)1;
            return this.val$value;
         }
      }
   }

   static class 11 extends UnmodifiableIterator<T> {

      int i;
      // $FF: synthetic field
      final Object[] val$array;
      // $FF: synthetic field
      final int val$end;
      // $FF: synthetic field
      final int val$offset;


      11(int var1, int var2, Object[] var3) {
         this.val$offset = var1;
         this.val$end = var2;
         this.val$array = var3;
         int var4 = this.val$offset;
         this.i = var4;
      }

      public boolean hasNext() {
         int var1 = this.i;
         int var2 = this.val$end;
         boolean var3;
         if(var1 < var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public T next() {
         if(!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            Object[] var1 = this.val$array;
            int var2 = this.i;
            int var3 = var2 + 1;
            this.i = var3;
            return var1[var2];
         }
      }
   }

   private static class PeekingImpl<E extends Object> implements PeekingIterator<E> {

      private boolean hasPeeked;
      private final Iterator<? extends E> iterator;
      private E peekedElement;


      public PeekingImpl(Iterator<? extends E> var1) {
         Iterator var2 = (Iterator)Preconditions.checkNotNull(var1);
         this.iterator = var2;
      }

      public boolean hasNext() {
         boolean var1;
         if(!this.hasPeeked && !this.iterator.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public E next() {
         Object var1;
         if(!this.hasPeeked) {
            var1 = this.iterator.next();
         } else {
            var1 = this.peekedElement;
            this.hasPeeked = (boolean)0;
            this.peekedElement = null;
         }

         return var1;
      }

      public E peek() {
         if(!this.hasPeeked) {
            Object var1 = this.iterator.next();
            this.peekedElement = var1;
            this.hasPeeked = (boolean)1;
         }

         return this.peekedElement;
      }

      public void remove() {
         byte var1;
         if(!this.hasPeeked) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         Preconditions.checkState((boolean)var1, "Can\'t remove after you\'ve peeked at next");
         this.iterator.remove();
      }
   }

   static class 14 implements Enumeration<T> {

      // $FF: synthetic field
      final Iterator val$iterator;


      14(Iterator var1) {
         this.val$iterator = var1;
      }

      public boolean hasMoreElements() {
         return this.val$iterator.hasNext();
      }

      public T nextElement() {
         return this.val$iterator.next();
      }
   }

   static class 13 extends UnmodifiableIterator<T> {

      // $FF: synthetic field
      final Enumeration val$enumeration;


      13(Enumeration var1) {
         this.val$enumeration = var1;
      }

      public boolean hasNext() {
         return this.val$enumeration.hasMoreElements();
      }

      public T next() {
         return this.val$enumeration.nextElement();
      }
   }

   static class 8 implements Iterator<T> {

      // $FF: synthetic field
      final Iterator val$fromIterator;
      // $FF: synthetic field
      final Function val$function;


      8(Iterator var1, Function var2) {
         this.val$fromIterator = var1;
         this.val$function = var2;
      }

      public boolean hasNext() {
         return this.val$fromIterator.hasNext();
      }

      public T next() {
         Object var1 = this.val$fromIterator.next();
         return this.val$function.apply(var1);
      }

      public void remove() {
         this.val$fromIterator.remove();
      }
   }

   static class 10 extends UnmodifiableIterator<T> {

      int i;
      final int length;
      // $FF: synthetic field
      final Object[] val$array;


      10(Object[] var1) {
         this.val$array = var1;
         int var2 = this.val$array.length;
         this.length = var2;
         this.i = 0;
      }

      public boolean hasNext() {
         int var1 = this.i;
         int var2 = this.length;
         boolean var3;
         if(var1 < var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public T next() {
         int var1 = this.i;
         int var2 = this.length;
         if(var1 < var2) {
            Object[] var3 = this.val$array;
            int var4 = this.i;
            int var5 = var4 + 1;
            this.i = var5;
            return var3[var4];
         } else {
            throw new NoSuchElementException();
         }
      }
   }

   static class 9 extends UnmodifiableIterator<T> {

      // $FF: synthetic field
      final Iterator val$iterator;


      9(Iterator var1) {
         this.val$iterator = var1;
      }

      public boolean hasNext() {
         return this.val$iterator.hasNext();
      }

      public T next() {
         Object var1 = this.val$iterator.next();
         this.val$iterator.remove();
         return var1;
      }
   }

   static class 4 implements Iterator<T> {

      Iterator<T> iterator;
      Iterator<T> removeFrom;
      // $FF: synthetic field
      final Iterable val$iterable;


      4(Iterable var1) {
         this.val$iterable = var1;
         UnmodifiableIterator var2 = Iterators.EMPTY_ITERATOR;
         this.iterator = var2;
      }

      public boolean hasNext() {
         if(!this.iterator.hasNext()) {
            Iterator var1 = this.val$iterable.iterator();
            this.iterator = var1;
         }

         return this.iterator.hasNext();
      }

      public T next() {
         if(!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            Iterator var1 = this.iterator;
            this.removeFrom = var1;
            return this.iterator.next();
         }
      }

      public void remove() {
         byte var1;
         if(this.removeFrom != null) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         Preconditions.checkState((boolean)var1, "no calls to next() since last call to remove()");
         this.removeFrom.remove();
         this.removeFrom = null;
      }
   }

   static class 5 implements Iterator<T> {

      Iterator<? extends T> current;
      Iterator<? extends T> removeFrom;
      // $FF: synthetic field
      final Iterator val$inputs;


      5(Iterator var1) {
         this.val$inputs = var1;
         UnmodifiableIterator var2 = Iterators.EMPTY_ITERATOR;
         this.current = var2;
      }

      public boolean hasNext() {
         while(true) {
            boolean var1 = this.current.hasNext();
            if(var1 || !this.val$inputs.hasNext()) {
               return var1;
            }

            Iterator var2 = (Iterator)this.val$inputs.next();
            this.current = var2;
         }
      }

      public T next() {
         if(!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            Iterator var1 = this.current;
            this.removeFrom = var1;
            return this.current.next();
         }
      }

      public void remove() {
         byte var1;
         if(this.removeFrom != null) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         Preconditions.checkState((boolean)var1, "no calls to next() since last call to remove()");
         this.removeFrom.remove();
         this.removeFrom = null;
      }
   }

   static class 6 extends UnmodifiableIterator<List<T>> {

      // $FF: synthetic field
      final Iterator val$iterator;
      // $FF: synthetic field
      final boolean val$pad;
      // $FF: synthetic field
      final int val$size;


      6(Iterator var1, int var2, boolean var3) {
         this.val$iterator = var1;
         this.val$size = var2;
         this.val$pad = var3;
      }

      public boolean hasNext() {
         return this.val$iterator.hasNext();
      }

      public List<T> next() {
         if(!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            Object[] var1 = new Object[this.val$size];
            int var2 = 0;

            while(true) {
               int var3 = this.val$size;
               if(var2 >= var3 || !this.val$iterator.hasNext()) {
                  List var5 = Collections.unmodifiableList(Arrays.asList(var1));
                  if(!this.val$pad) {
                     int var6 = this.val$size;
                     if(var2 != var6) {
                        var5 = Platform.subList(var5, 0, var2);
                     }
                  }

                  return var5;
               }

               Object var4 = this.val$iterator.next();
               var1[var2] = var4;
               ++var2;
            }
         }
      }
   }

   static class 7 extends AbstractIterator<T> {

      // $FF: synthetic field
      final Predicate val$predicate;
      // $FF: synthetic field
      final Iterator val$unfiltered;


      7(Iterator var1, Predicate var2) {
         this.val$unfiltered = var1;
         this.val$predicate = var2;
      }

      protected T computeNext() {
         while(true) {
            Object var1;
            if(this.val$unfiltered.hasNext()) {
               var1 = this.val$unfiltered.next();
               if(!this.val$predicate.apply(var1)) {
                  continue;
               }
            } else {
               var1 = this.endOfData();
            }

            return var1;
         }
      }
   }

   static class 1 extends UnmodifiableIterator<Object> {

      1() {}

      public boolean hasNext() {
         return false;
      }

      public Object next() {
         throw new NoSuchElementException();
      }
   }

   static class 2 implements Iterator<Object> {

      2() {}

      public boolean hasNext() {
         return false;
      }

      public Object next() {
         throw new NoSuchElementException();
      }

      public void remove() {
         throw new IllegalStateException();
      }
   }

   static class 3 extends UnmodifiableIterator<T> {

      // $FF: synthetic field
      final Iterator val$iterator;


      3(Iterator var1) {
         this.val$iterator = var1;
      }

      public boolean hasNext() {
         return this.val$iterator.hasNext();
      }

      public T next() {
         return this.val$iterator.next();
      }
   }
}
