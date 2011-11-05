package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public final class Collections2 {

   static final Joiner standardJoiner = Joiner.on(", ");


   private Collections2() {}

   static boolean containsAll(Collection<?> var0, Collection<?> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Iterator var3 = var1.iterator();

      boolean var5;
      while(true) {
         if(var3.hasNext()) {
            Object var4 = var3.next();
            if(var0.contains(var4)) {
               continue;
            }

            var5 = false;
            break;
         }

         var5 = true;
         break;
      }

      return var5;
   }

   public static <E extends Object> Collection<E> filter(Collection<E> var0, Predicate<? super E> var1) {
      Collections2.FilteredCollection var2;
      if(var0 instanceof Collections2.FilteredCollection) {
         var2 = ((Collections2.FilteredCollection)var0).createCombined(var1);
      } else {
         Collection var3 = (Collection)Preconditions.checkNotNull(var0);
         Predicate var4 = (Predicate)Preconditions.checkNotNull(var1);
         var2 = new Collections2.FilteredCollection(var3, var4);
      }

      return var2;
   }

   static boolean safeContains(Collection<?> var0, Object var1) {
      boolean var2;
      boolean var3;
      try {
         var2 = var0.contains(var1);
      } catch (ClassCastException var5) {
         var3 = false;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   static boolean setEquals(Set<?> var0, @Nullable Object var1) {
      boolean var2 = true;
      if(var1 != var0) {
         if(var1 instanceof Set) {
            Set var3 = (Set)var1;
            int var4 = var0.size();
            int var5 = var3.size();
            if(var4 != var5 || !var0.containsAll(var3)) {
               var2 = false;
            }
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   static <E extends Object> Collection<E> toCollection(Iterable<E> var0) {
      Object var1;
      if(var0 instanceof Collection) {
         var1 = (Collection)var0;
      } else {
         var1 = Lists.newArrayList(var0);
      }

      return (Collection)var1;
   }

   public static <F extends Object, T extends Object> Collection<T> transform(Collection<F> var0, Function<? super F, T> var1) {
      return new Collections2.TransformedCollection(var0, var1);
   }

   static class FilteredCollection<E extends Object> implements Collection<E> {

      final Predicate<? super E> predicate;
      final Collection<E> unfiltered;


      FilteredCollection(Collection<E> var1, Predicate<? super E> var2) {
         this.unfiltered = var1;
         this.predicate = var2;
      }

      public boolean add(E var1) {
         Preconditions.checkArgument(this.predicate.apply(var1));
         return this.unfiltered.add(var1);
      }

      public boolean addAll(Collection<? extends E> var1) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            Object var3 = var2.next();
            Preconditions.checkArgument(this.predicate.apply(var3));
         }

         return this.unfiltered.addAll(var1);
      }

      public void clear() {
         Collection var1 = this.unfiltered;
         Predicate var2 = this.predicate;
         Iterables.removeIf(var1, var2);
      }

      public boolean contains(Object var1) {
         boolean var2 = false;
         Object var3 = var1;

         boolean var4;
         try {
            if(!this.predicate.apply(var3)) {
               return var2;
            }

            var4 = this.unfiltered.contains(var1);
         } catch (NullPointerException var7) {
            return var2;
         } catch (ClassCastException var8) {
            return var2;
         }

         if(var4) {
            var2 = true;
         }

         return var2;
      }

      public boolean containsAll(Collection<?> var1) {
         Iterator var2 = var1.iterator();

         boolean var4;
         while(true) {
            if(var2.hasNext()) {
               Object var3 = var2.next();
               if(this.contains(var3)) {
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

      Collections2.FilteredCollection<E> createCombined(Predicate<? super E> var1) {
         Collection var2 = this.unfiltered;
         Predicate var3 = Predicates.and(this.predicate, var1);
         return new Collections2.FilteredCollection(var2, var3);
      }

      public boolean isEmpty() {
         Iterator var1 = this.unfiltered.iterator();
         Predicate var2 = this.predicate;
         boolean var3;
         if(!Iterators.any(var1, var2)) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public Iterator<E> iterator() {
         Iterator var1 = this.unfiltered.iterator();
         Predicate var2 = this.predicate;
         return Iterators.filter(var1, var2);
      }

      public boolean remove(Object var1) {
         boolean var2 = false;
         Object var3 = var1;

         boolean var4;
         try {
            if(!this.predicate.apply(var3)) {
               return var2;
            }

            var4 = this.unfiltered.remove(var1);
         } catch (NullPointerException var7) {
            return var2;
         } catch (ClassCastException var8) {
            return var2;
         }

         if(var4) {
            var2 = true;
         }

         return var2;
      }

      public boolean removeAll(Collection<?> var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         Collections2.FilteredCollection.1 var3 = new Collections2.FilteredCollection.1(var1);
         return Iterables.removeIf(this.unfiltered, var3);
      }

      public boolean retainAll(Collection<?> var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         Collections2.FilteredCollection.2 var3 = new Collections2.FilteredCollection.2(var1);
         return Iterables.removeIf(this.unfiltered, var3);
      }

      public int size() {
         return Iterators.size(this.iterator());
      }

      public Object[] toArray() {
         return Lists.newArrayList(this.iterator()).toArray();
      }

      public <T extends Object> T[] toArray(T[] var1) {
         return Lists.newArrayList(this.iterator()).toArray(var1);
      }

      public String toString() {
         return Iterators.toString(this.iterator());
      }

      class 1 implements Predicate<E> {

         // $FF: synthetic field
         final Collection val$collection;


         1(Collection var2) {
            this.val$collection = var2;
         }

         public boolean apply(E var1) {
            boolean var2;
            if(FilteredCollection.this.predicate.apply(var1) && this.val$collection.contains(var1)) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }
      }

      class 2 implements Predicate<E> {

         // $FF: synthetic field
         final Collection val$collection;


         2(Collection var2) {
            this.val$collection = var2;
         }

         public boolean apply(E var1) {
            boolean var2;
            if(FilteredCollection.this.predicate.apply(var1) && !this.val$collection.contains(var1)) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }
      }
   }

   static class TransformedCollection<F extends Object, T extends Object> extends AbstractCollection<T> {

      final Collection<F> fromCollection;
      final Function<? super F, ? extends T> function;


      TransformedCollection(Collection<F> var1, Function<? super F, ? extends T> var2) {
         Collection var3 = (Collection)Preconditions.checkNotNull(var1);
         this.fromCollection = var3;
         Function var4 = (Function)Preconditions.checkNotNull(var2);
         this.function = var4;
      }

      public void clear() {
         this.fromCollection.clear();
      }

      public boolean isEmpty() {
         return this.fromCollection.isEmpty();
      }

      public Iterator<T> iterator() {
         Iterator var1 = this.fromCollection.iterator();
         Function var2 = this.function;
         return Iterators.transform(var1, var2);
      }

      public int size() {
         return this.fromCollection.size();
      }
   }
}
