package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingMultiset;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public final class Multisets {

   private Multisets() {}

   static void checkNonnegative(int var0, String var1) {
      byte var2;
      if(var0 >= 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      Object[] var3 = new Object[]{var1, null};
      Integer var4 = Integer.valueOf(var0);
      var3[1] = var4;
      Preconditions.checkArgument((boolean)var2, "%s cannot be negative: %s", var3);
   }

   static <E extends Object> Multiset<E> forSet(Set<E> var0) {
      return new Multisets.SetMultiset(var0);
   }

   public static <E extends Object> Multiset.Entry<E> immutableEntry(@Nullable E var0, int var1) {
      byte var2;
      if(var1 >= 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      Preconditions.checkArgument((boolean)var2);
      return new Multisets.1(var0, var1);
   }

   static int inferDistinctElements(Iterable<?> var0) {
      int var1;
      if(var0 instanceof Multiset) {
         var1 = ((Multiset)var0).elementSet().size();
      } else {
         var1 = 11;
      }

      return var1;
   }

   public static <E extends Object> Multiset<E> intersection(Multiset<E> var0, Multiset<?> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new Multisets.2(var0, var1);
   }

   static <E extends Object> int setCountImpl(Multiset<E> var0, E var1, int var2) {
      checkNonnegative(var2, "count");
      int var3 = var0.count(var1);
      int var4 = var2 - var3;
      if(var4 > 0) {
         var0.add(var1, var4);
      } else if(var4 < 0) {
         int var6 = -var4;
         var0.remove(var1, var6);
      }

      return var3;
   }

   static <E extends Object> boolean setCountImpl(Multiset<E> var0, E var1, int var2, int var3) {
      checkNonnegative(var2, "oldCount");
      checkNonnegative(var3, "newCount");
      boolean var5;
      if(var0.count(var1) == var2) {
         var0.setCount(var1, var3);
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public static <E extends Object> Multiset<E> unmodifiableMultiset(Multiset<? extends E> var0) {
      return new Multisets.UnmodifiableMultiset(var0);
   }

   abstract static class AbstractEntry<E extends Object> implements Multiset.Entry<E> {

      AbstractEntry() {}

      public boolean equals(@Nullable Object var1) {
         boolean var2 = false;
         if(var1 instanceof Multiset.Entry) {
            Multiset.Entry var3 = (Multiset.Entry)var1;
            int var4 = this.getCount();
            int var5 = var3.getCount();
            if(var4 == var5) {
               Object var6 = this.getElement();
               Object var7 = var3.getElement();
               if(Objects.equal(var6, var7)) {
                  var2 = true;
               }
            }
         }

         return var2;
      }

      public int hashCode() {
         Object var1 = this.getElement();
         int var2;
         if(var1 == null) {
            var2 = 0;
         } else {
            var2 = var1.hashCode();
         }

         int var3 = this.getCount();
         return var2 ^ var3;
      }

      public String toString() {
         String var1 = String.valueOf(this.getElement());
         int var2 = this.getCount();
         if(var2 != 1) {
            var1 = var1 + " x " + var2;
         }

         return var1;
      }
   }

   private static class UnmodifiableMultiset<E extends Object> extends ForwardingMultiset<E> implements Serializable {

      private static final long serialVersionUID;
      final Multiset<? extends E> delegate;
      transient Set<E> elementSet;
      transient Set<Multiset.Entry<E>> entrySet;


      UnmodifiableMultiset(Multiset<? extends E> var1) {
         this.delegate = var1;
      }

      public int add(E var1, int var2) {
         throw new UnsupportedOperationException();
      }

      public boolean add(E var1) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection<? extends E> var1) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      protected Multiset<E> delegate() {
         return this.delegate;
      }

      public Set<E> elementSet() {
         Set var1 = this.elementSet;
         if(var1 == null) {
            var1 = Collections.unmodifiableSet(this.delegate.elementSet());
            this.elementSet = var1;
         }

         return var1;
      }

      public Set<Multiset.Entry<E>> entrySet() {
         Set var1 = this.entrySet;
         if(var1 == null) {
            var1 = Collections.unmodifiableSet(this.delegate.entrySet());
            this.entrySet = var1;
         }

         return var1;
      }

      public Iterator<E> iterator() {
         return Iterators.unmodifiableIterator(this.delegate.iterator());
      }

      public int remove(Object var1, int var2) {
         throw new UnsupportedOperationException();
      }

      public boolean remove(Object var1) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection<?> var1) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection<?> var1) {
         throw new UnsupportedOperationException();
      }

      public int setCount(E var1, int var2) {
         throw new UnsupportedOperationException();
      }

      public boolean setCount(E var1, int var2, int var3) {
         throw new UnsupportedOperationException();
      }
   }

   static class 1 extends Multisets.AbstractEntry<E> {

      // $FF: synthetic field
      final Object val$e;
      // $FF: synthetic field
      final int val$n;


      1(Object var1, int var2) {
         this.val$e = var1;
         this.val$n = var2;
      }

      public int getCount() {
         return this.val$n;
      }

      public E getElement() {
         return this.val$e;
      }
   }

   static class 2 extends AbstractMultiset<E> {

      final Set<Multiset.Entry<E>> entrySet;
      // $FF: synthetic field
      final Multiset val$multiset1;
      // $FF: synthetic field
      final Multiset val$multiset2;


      2(Multiset var1, Multiset var2) {
         this.val$multiset1 = var1;
         this.val$multiset2 = var2;
         Multisets.2.1 var3 = new Multisets.2.1();
         this.entrySet = var3;
      }

      public int count(Object var1) {
         int var2 = this.val$multiset1.count(var1);
         int var3;
         if(var2 == 0) {
            var3 = 0;
         } else {
            int var4 = this.val$multiset2.count(var1);
            var3 = Math.min(var2, var4);
         }

         return var3;
      }

      Set<E> createElementSet() {
         Set var1 = this.val$multiset1.elementSet();
         Set var2 = this.val$multiset2.elementSet();
         return Sets.intersection(var1, var2);
      }

      public Set<Multiset.Entry<E>> entrySet() {
         return this.entrySet;
      }

      class 1 extends AbstractSet<Multiset.Entry<E>> {

         1() {}

         public boolean contains(Object var1) {
            boolean var2 = false;
            if(var1 instanceof Multiset.Entry) {
               Multiset.Entry var3 = (Multiset.Entry)var1;
               int var4 = var3.getCount();
               if(var4 > 0) {
                  Multisets.2 var5 = 2.this;
                  Object var6 = var3.getElement();
                  if(var5.count(var6) == var4) {
                     var2 = true;
                  }
               }
            }

            return var2;
         }

         public boolean isEmpty() {
            return 2.this.elementSet().isEmpty();
         }

         public Iterator<Multiset.Entry<E>> iterator() {
            Iterator var1 = 2.this.val$multiset1.entrySet().iterator();
            return new Multisets.2.1.1(var1);
         }

         public int size() {
            return 2.this.elementSet().size();
         }

         class 1 extends AbstractIterator<Multiset.Entry<E>> {

            // $FF: synthetic field
            final Iterator val$iterator1;


            1(Iterator var2) {
               this.val$iterator1 = var2;
            }

            protected Multiset.Entry<E> computeNext() {
               while(true) {
                  Multiset.Entry var6;
                  if(this.val$iterator1.hasNext()) {
                     Multiset.Entry var1 = (Multiset.Entry)this.val$iterator1.next();
                     Object var2 = var1.getElement();
                     int var3 = var1.getCount();
                     int var4 = 2.this.val$multiset2.count(var2);
                     int var5 = Math.min(var3, var4);
                     if(var5 <= 0) {
                        continue;
                     }

                     var6 = Multisets.immutableEntry(var2, var5);
                  } else {
                     var6 = (Multiset.Entry)this.endOfData();
                  }

                  return var6;
               }
            }
         }
      }
   }

   private static class SetMultiset<E extends Object> extends ForwardingCollection<E> implements Multiset<E>, Serializable {

      private static final long serialVersionUID;
      final Set<E> delegate;
      transient Set<E> elementSet;
      transient Set<Multiset.Entry<E>> entrySet;


      SetMultiset(Set<E> var1) {
         Set var2 = (Set)Preconditions.checkNotNull(var1);
         this.delegate = var2;
      }

      public int add(E var1, int var2) {
         throw new UnsupportedOperationException();
      }

      public boolean add(E var1) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection<? extends E> var1) {
         throw new UnsupportedOperationException();
      }

      public int count(Object var1) {
         byte var2;
         if(this.delegate.contains(var1)) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         return var2;
      }

      protected Set<E> delegate() {
         return this.delegate;
      }

      public Set<E> elementSet() {
         Object var1 = this.elementSet;
         if(var1 == null) {
            var1 = new Multisets.SetMultiset.ElementSet();
            this.elementSet = (Set)var1;
         }

         return (Set)var1;
      }

      public Set<Multiset.Entry<E>> entrySet() {
         Object var1 = this.entrySet;
         if(var1 == null) {
            var1 = new Multisets.SetMultiset.EntrySet();
            this.entrySet = (Set)var1;
         }

         return (Set)var1;
      }

      public boolean equals(@Nullable Object var1) {
         boolean var2 = false;
         if(var1 instanceof Multiset) {
            Multiset var3 = (Multiset)var1;
            int var4 = this.size();
            int var5 = var3.size();
            if(var4 == var5) {
               Set var6 = this.delegate;
               Set var7 = var3.elementSet();
               if(var6.equals(var7)) {
                  var2 = true;
               }
            }
         }

         return var2;
      }

      public int hashCode() {
         int var1 = 0;

         int var5;
         for(Iterator var2 = this.iterator(); var2.hasNext(); var1 += var5) {
            Object var3 = var2.next();
            int var4;
            if(var3 == null) {
               var4 = 0;
            } else {
               var4 = var3.hashCode();
            }

            var5 = var4 ^ 1;
         }

         return var1;
      }

      public int remove(Object var1, int var2) {
         int var3 = 1;
         if(var2 == 0) {
            var3 = this.count(var1);
         } else {
            byte var4;
            if(var2 > 0) {
               var4 = 1;
            } else {
               var4 = 0;
            }

            Preconditions.checkArgument((boolean)var4);
            if(!this.delegate.remove(var1)) {
               var3 = 0;
            }
         }

         return var3;
      }

      public int setCount(E var1, int var2) {
         Multisets.checkNonnegative(var2, "count");
         int var3 = this.count(var1);
         if(var2 != var3) {
            if(var2 != 0) {
               throw new UnsupportedOperationException();
            }

            this.remove(var1);
            var2 = 1;
         }

         return var2;
      }

      public boolean setCount(E var1, int var2, int var3) {
         return Multisets.setCountImpl(this, var1, var2, var3);
      }

      class EntrySet extends AbstractSet<Multiset.Entry<E>> {

         EntrySet() {}

         public Iterator<Multiset.Entry<E>> iterator() {
            return new Multisets.SetMultiset.EntrySet.1();
         }

         public int size() {
            return SetMultiset.this.delegate.size();
         }

         class 1 implements Iterator<Multiset.Entry<E>> {

            final Iterator<E> elements;


            1() {
               Iterator var2 = SetMultiset.this.delegate.iterator();
               this.elements = var2;
            }

            public boolean hasNext() {
               return this.elements.hasNext();
            }

            public Multiset.Entry<E> next() {
               return Multisets.immutableEntry(this.elements.next(), 1);
            }

            public void remove() {
               this.elements.remove();
            }
         }
      }

      class ElementSet extends ForwardingSet<E> {

         ElementSet() {}

         public boolean add(E var1) {
            throw new UnsupportedOperationException();
         }

         public boolean addAll(Collection<? extends E> var1) {
            throw new UnsupportedOperationException();
         }

         protected Set<E> delegate() {
            return SetMultiset.this.delegate;
         }
      }
   }
}
