package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.ObjectArrays;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public final class Iterables {

   private Iterables() {}

   public static <T extends Object> boolean addAll(Collection<T> var0, Iterable<? extends T> var1) {
      boolean var3;
      if(var1 instanceof Collection) {
         Collection var2 = (Collection)var1;
         var3 = var0.addAll(var2);
      } else {
         Iterator var4 = var1.iterator();
         var3 = Iterators.addAll(var0, var4);
      }

      return var3;
   }

   public static <T extends Object> boolean all(Iterable<T> var0, Predicate<? super T> var1) {
      return Iterators.all(var0.iterator(), var1);
   }

   public static <T extends Object> boolean any(Iterable<T> var0, Predicate<? super T> var1) {
      return Iterators.any(var0.iterator(), var1);
   }

   public static <T extends Object> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> var0) {
      Iterables.3 var1 = new Iterables.3();
      Iterable var2 = transform(var0, var1);
      return new Iterables.4(var2);
   }

   public static <T extends Object> Iterable<T> concat(Iterable<? extends T> var0, Iterable<? extends T> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      Iterable[] var4 = new Iterable[]{var0, var1};
      return concat((Iterable)Arrays.asList(var4));
   }

   public static <T extends Object> Iterable<T> concat(Iterable<? extends T> var0, Iterable<? extends T> var1, Iterable<? extends T> var2) {
      Object var3 = Preconditions.checkNotNull(var0);
      Object var4 = Preconditions.checkNotNull(var1);
      Object var5 = Preconditions.checkNotNull(var2);
      Iterable[] var6 = new Iterable[]{var0, var1, var2};
      return concat((Iterable)Arrays.asList(var6));
   }

   public static <T extends Object> Iterable<T> concat(Iterable<? extends T> var0, Iterable<? extends T> var1, Iterable<? extends T> var2, Iterable<? extends T> var3) {
      Object var4 = Preconditions.checkNotNull(var0);
      Object var5 = Preconditions.checkNotNull(var1);
      Object var6 = Preconditions.checkNotNull(var2);
      Object var7 = Preconditions.checkNotNull(var3);
      Iterable[] var8 = new Iterable[]{var0, var1, var2, var3};
      return concat((Iterable)Arrays.asList(var8));
   }

   public static <T extends Object> Iterable<T> concat(Iterable<? extends T> ... var0) {
      return concat((Iterable)ImmutableList.of((Object[])var0));
   }

   public static <T extends Object> Iterable<T> consumingIterable(Iterable<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterables.10(var0);
   }

   public static boolean contains(Iterable<?> var0, @Nullable Object var1) {
      byte var2 = 0;
      if(var0 instanceof Collection) {
         Collection var3 = (Collection)var0;

         byte var4;
         try {
            var4 = var3.contains(var1);
         } catch (NullPointerException var7) {
            return (boolean)var2;
         } catch (ClassCastException var8) {
            return (boolean)var2;
         }

         var2 = var4;
      } else {
         var2 = Iterators.contains(var0.iterator(), var1);
      }

      return (boolean)var2;
   }

   public static <T extends Object> Iterable<T> cycle(Iterable<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterables.2(var0);
   }

   public static <T extends Object> Iterable<T> cycle(T ... var0) {
      return cycle((Iterable)Lists.newArrayList(var0));
   }

   public static boolean elementsEqual(Iterable<?> var0, Iterable<?> var1) {
      Iterator var2 = var0.iterator();
      Iterator var3 = var1.iterator();
      return Iterators.elementsEqual(var2, var3);
   }

   public static <T extends Object> Iterable<T> filter(Iterable<T> var0, Predicate<? super T> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new Iterables.7(var0, var1);
   }

   @GwtIncompatible("Class.isInstance")
   public static <T extends Object> Iterable<T> filter(Iterable<?> var0, Class<T> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new Iterables.8(var0, var1);
   }

   public static <T extends Object> T find(Iterable<T> var0, Predicate<? super T> var1) {
      return Iterators.find(var0.iterator(), var1);
   }

   public static int frequency(Iterable<?> var0, @Nullable Object var1) {
      int var2;
      if(var0 instanceof Multiset) {
         var2 = ((Multiset)var0).count(var1);
      } else if(var0 instanceof Set) {
         if(((Set)var0).contains(var1)) {
            var2 = 1;
         } else {
            var2 = 0;
         }
      } else {
         var2 = Iterators.frequency(var0.iterator(), var1);
      }

      return var2;
   }

   public static <T extends Object> T get(Iterable<T> var0, int var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3;
      if(var0 instanceof List) {
         var3 = ((List)var0).get(var1);
      } else {
         if(var0 instanceof Collection) {
            int var4 = ((Collection)var0).size();
            Preconditions.checkElementIndex(var1, var4);
         } else if(var1 < 0) {
            String var6 = "position cannot be negative: " + var1;
            throw new IndexOutOfBoundsException(var6);
         }

         var3 = Iterators.get(var0.iterator(), var1);
      }

      return var3;
   }

   public static <T extends Object> T getLast(Iterable<T> var0) {
      Object var3;
      if(var0 instanceof List) {
         List var1 = (List)var0;
         if(var1.isEmpty()) {
            throw new NoSuchElementException();
         }

         int var2 = var1.size() + -1;
         var3 = var1.get(var2);
      } else if(var0 instanceof SortedSet) {
         var3 = ((SortedSet)var0).last();
      } else {
         var3 = Iterators.getLast(var0.iterator());
      }

      return var3;
   }

   public static <T extends Object> T getOnlyElement(Iterable<T> var0) {
      return Iterators.getOnlyElement(var0.iterator());
   }

   public static <T extends Object> T getOnlyElement(Iterable<T> var0, @Nullable T var1) {
      return Iterators.getOnlyElement(var0.iterator(), var1);
   }

   public static <T extends Object> int indexOf(Iterable<T> var0, Predicate<? super T> var1) {
      return Iterators.indexOf(var0.iterator(), var1);
   }

   public static <T extends Object> boolean isEmpty(Iterable<T> var0) {
      boolean var1;
      if(!var0.iterator().hasNext()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static <T extends Object> Iterable<List<T>> paddedPartition(Iterable<T> var0, int var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      byte var3;
      if(var1 > 0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Preconditions.checkArgument((boolean)var3);
      return new Iterables.6(var0, var1);
   }

   public static <T extends Object> Iterable<List<T>> partition(Iterable<T> var0, int var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      byte var3;
      if(var1 > 0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Preconditions.checkArgument((boolean)var3);
      return new Iterables.5(var0, var1);
   }

   static boolean remove(Iterable<?> var0, @Nullable Object var1) {
      Iterator var2 = var0.iterator();

      boolean var3;
      while(true) {
         if(var2.hasNext()) {
            if(!Objects.equal(var2.next(), var1)) {
               continue;
            }

            var2.remove();
            var3 = true;
            break;
         }

         var3 = false;
         break;
      }

      return var3;
   }

   public static boolean removeAll(Iterable<?> var0, Collection<?> var1) {
      boolean var4;
      if(var0 instanceof Collection) {
         Collection var2 = (Collection)var0;
         Collection var3 = (Collection)Preconditions.checkNotNull(var1);
         var4 = var2.removeAll(var3);
      } else {
         var4 = Iterators.removeAll(var0.iterator(), var1);
      }

      return var4;
   }

   public static <T extends Object> boolean removeIf(Iterable<T> var0, Predicate<? super T> var1) {
      boolean var4;
      if(var0 instanceof RandomAccess && var0 instanceof List) {
         List var2 = (List)var0;
         Predicate var3 = (Predicate)Preconditions.checkNotNull(var1);
         var4 = removeIfFromRandomAccessList(var2, var3);
      } else {
         var4 = Iterators.removeIf(var0.iterator(), var1);
      }

      return var4;
   }

   private static <T extends Object> boolean removeIfFromRandomAccessList(List<T> var0, Predicate<? super T> var1) {
      int var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = var0.size();
         if(var2 >= var4) {
            int var7 = var0.size();
            ListIterator var8 = var0.listIterator(var7);

            for(int var9 = var2 - var3; var9 > 0; var9 += -1) {
               Object var10 = var8.previous();
               var8.remove();
            }

            boolean var11;
            if(var2 != var3) {
               var11 = true;
            } else {
               var11 = false;
            }

            return var11;
         }

         Object var5 = var0.get(var2);
         if(!var1.apply(var5)) {
            if(var2 > var3) {
               var0.set(var3, var5);
            }

            ++var3;
         }

         ++var2;
      }
   }

   public static boolean retainAll(Iterable<?> var0, Collection<?> var1) {
      boolean var4;
      if(var0 instanceof Collection) {
         Collection var2 = (Collection)var0;
         Collection var3 = (Collection)Preconditions.checkNotNull(var1);
         var4 = var2.retainAll(var3);
      } else {
         var4 = Iterators.retainAll(var0.iterator(), var1);
      }

      return var4;
   }

   public static <T extends Object> Iterable<T> reverse(List<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterables.11(var0);
   }

   public static int size(Iterable<?> var0) {
      int var1;
      if(var0 instanceof Collection) {
         var1 = ((Collection)var0).size();
      } else {
         var1 = Iterators.size(var0.iterator());
      }

      return var1;
   }

   @GwtIncompatible("Array.newInstance(Class, int)")
   public static <T extends Object> T[] toArray(Iterable<? extends T> var0, Class<T> var1) {
      Object var2;
      if(var0 instanceof Collection) {
         var2 = (Collection)var0;
      } else {
         var2 = Lists.newArrayList(var0);
      }

      int var3 = ((Collection)var2).size();
      Object[] var4 = ObjectArrays.newArray(var1, var3);
      return ((Collection)var2).toArray(var4);
   }

   public static String toString(Iterable<?> var0) {
      return Iterators.toString(var0.iterator());
   }

   public static <F extends Object, T extends Object> Iterable<T> transform(Iterable<F> var0, Function<? super F, ? extends T> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new Iterables.9(var0, var1);
   }

   public static <T extends Object> Iterable<T> unmodifiableIterable(Iterable<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Iterables.1(var0);
   }

   static class 9 extends Iterables.IterableWithToString<T> {

      // $FF: synthetic field
      final Iterable val$fromIterable;
      // $FF: synthetic field
      final Function val$function;


      9(Iterable var1, Function var2) {
         this.val$fromIterable = var1;
         this.val$function = var2;
      }

      public Iterator<T> iterator() {
         Iterator var1 = this.val$fromIterable.iterator();
         Function var2 = this.val$function;
         return Iterators.transform(var1, var2);
      }
   }

   static class 8 extends Iterables.IterableWithToString<T> {

      // $FF: synthetic field
      final Class val$type;
      // $FF: synthetic field
      final Iterable val$unfiltered;


      8(Iterable var1, Class var2) {
         this.val$unfiltered = var1;
         this.val$type = var2;
      }

      public Iterator<T> iterator() {
         Iterator var1 = this.val$unfiltered.iterator();
         Class var2 = this.val$type;
         return Iterators.filter(var1, var2);
      }
   }

   abstract static class IterableWithToString<E extends Object> implements Iterable<E> {

      IterableWithToString() {}

      public String toString() {
         return Iterables.toString(this);
      }
   }

   static class 5 extends Iterables.IterableWithToString<List<T>> {

      // $FF: synthetic field
      final Iterable val$iterable;
      // $FF: synthetic field
      final int val$size;


      5(Iterable var1, int var2) {
         this.val$iterable = var1;
         this.val$size = var2;
      }

      public Iterator<List<T>> iterator() {
         Iterator var1 = this.val$iterable.iterator();
         int var2 = this.val$size;
         return Iterators.partition(var1, var2);
      }
   }

   static class 4 extends Iterables.IterableWithToString<T> {

      // $FF: synthetic field
      final Iterable val$iterators;


      4(Iterable var1) {
         this.val$iterators = var1;
      }

      public Iterator<T> iterator() {
         return Iterators.concat(this.val$iterators.iterator());
      }
   }

   static class 7 extends Iterables.IterableWithToString<T> {

      // $FF: synthetic field
      final Predicate val$predicate;
      // $FF: synthetic field
      final Iterable val$unfiltered;


      7(Iterable var1, Predicate var2) {
         this.val$unfiltered = var1;
         this.val$predicate = var2;
      }

      public Iterator<T> iterator() {
         Iterator var1 = this.val$unfiltered.iterator();
         Predicate var2 = this.val$predicate;
         return Iterators.filter(var1, var2);
      }
   }

   static class 6 extends Iterables.IterableWithToString<List<T>> {

      // $FF: synthetic field
      final Iterable val$iterable;
      // $FF: synthetic field
      final int val$size;


      6(Iterable var1, int var2) {
         this.val$iterable = var1;
         this.val$size = var2;
      }

      public Iterator<List<T>> iterator() {
         Iterator var1 = this.val$iterable.iterator();
         int var2 = this.val$size;
         return Iterators.paddedPartition(var1, var2);
      }
   }

   static class 1 implements Iterable<T> {

      // $FF: synthetic field
      final Iterable val$iterable;


      1(Iterable var1) {
         this.val$iterable = var1;
      }

      public Iterator<T> iterator() {
         return Iterators.unmodifiableIterator(this.val$iterable.iterator());
      }

      public String toString() {
         return this.val$iterable.toString();
      }
   }

   static class 11 extends Iterables.IterableWithToString<T> {

      // $FF: synthetic field
      final List val$list;


      11(List var1) {
         this.val$list = var1;
      }

      public Iterator<T> iterator() {
         List var1 = this.val$list;
         int var2 = this.val$list.size();
         ListIterator var3 = var1.listIterator(var2);
         return new Iterables.11.1(var3);
      }

      class 1 implements Iterator<T> {

         // $FF: synthetic field
         final ListIterator val$listIter;


         1(ListIterator var2) {
            this.val$listIter = var2;
         }

         public boolean hasNext() {
            return this.val$listIter.hasPrevious();
         }

         public T next() {
            return this.val$listIter.previous();
         }

         public void remove() {
            this.val$listIter.remove();
         }
      }
   }

   static class 2 implements Iterable<T> {

      // $FF: synthetic field
      final Iterable val$iterable;


      2(Iterable var1) {
         this.val$iterable = var1;
      }

      public Iterator<T> iterator() {
         return Iterators.cycle(this.val$iterable);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         String var2 = this.val$iterable.toString();
         return var1.append(var2).append(" (cycled)").toString();
      }
   }

   static class 3 implements Function<Iterable<? extends T>, Iterator<? extends T>> {

      3() {}

      public Iterator<? extends T> apply(Iterable<? extends T> var1) {
         return var1.iterator();
      }
   }

   static class 10 implements Iterable<T> {

      // $FF: synthetic field
      final Iterable val$iterable;


      10(Iterable var1) {
         this.val$iterable = var1;
      }

      public Iterator<T> iterator() {
         return Iterators.consumingIterator(this.val$iterable.iterator());
      }
   }
}
