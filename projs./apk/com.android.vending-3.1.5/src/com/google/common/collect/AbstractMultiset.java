package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMultiset<E extends Object> extends AbstractCollection<E> implements Multiset<E> {

   private transient Set<E> elementSet;


   AbstractMultiset() {}

   public int add(E var1, int var2) {
      throw new UnsupportedOperationException();
   }

   public boolean add(@Nullable E var1) {
      this.add(var1, 1);
      return true;
   }

   public boolean addAll(Collection<? extends E> var1) {
      boolean var2;
      if(var1.isEmpty()) {
         var2 = false;
      } else {
         if(var1 instanceof Multiset) {
            Iterator var3 = ((Multiset)var1).entrySet().iterator();

            while(var3.hasNext()) {
               Multiset.Entry var4 = (Multiset.Entry)var3.next();
               Object var5 = var4.getElement();
               int var6 = var4.getCount();
               this.add(var5, var6);
            }
         } else {
            super.addAll(var1);
         }

         var2 = true;
      }

      return var2;
   }

   public void clear() {
      this.entrySet().clear();
   }

   public boolean contains(@Nullable Object var1) {
      return this.elementSet().contains(var1);
   }

   public boolean containsAll(Collection<?> var1) {
      return this.elementSet().containsAll(var1);
   }

   public int count(Object var1) {
      Iterator var2 = this.entrySet().iterator();

      int var4;
      while(true) {
         if(var2.hasNext()) {
            Multiset.Entry var3 = (Multiset.Entry)var2.next();
            if(!Objects.equal(var3.getElement(), var1)) {
               continue;
            }

            var4 = var3.getCount();
            break;
         }

         var4 = 0;
         break;
      }

      return var4;
   }

   Set<E> createElementSet() {
      return new AbstractMultiset.ElementSet((AbstractMultiset.1)null);
   }

   public Set<E> elementSet() {
      Set var1 = this.elementSet;
      if(var1 == null) {
         var1 = this.createElementSet();
         this.elementSet = var1;
      }

      return var1;
   }

   public abstract Set<Multiset.Entry<E>> entrySet();

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if(var1 != this) {
         if(var1 instanceof Multiset) {
            Multiset var3 = (Multiset)var1;
            int var4 = this.size();
            int var5 = var3.size();
            if(var4 != var5) {
               var2 = false;
            } else {
               Iterator var6 = var3.entrySet().iterator();

               while(var6.hasNext()) {
                  Multiset.Entry var7 = (Multiset.Entry)var6.next();
                  Object var8 = var7.getElement();
                  int var9 = this.count(var8);
                  int var10 = var7.getCount();
                  if(var9 != var10) {
                     var2 = false;
                     break;
                  }
               }
            }
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public int hashCode() {
      return this.entrySet().hashCode();
   }

   public boolean isEmpty() {
      return this.entrySet().isEmpty();
   }

   public Iterator<E> iterator() {
      return new AbstractMultiset.MultisetIterator();
   }

   public int remove(Object var1, int var2) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object var1) {
      byte var2 = 1;
      if(this.remove(var1, var2) <= 0) {
         var2 = 0;
      }

      return (boolean)var2;
   }

   public boolean removeAll(Collection<?> var1) {
      Object var2;
      if(var1 instanceof Multiset) {
         var2 = ((Multiset)var1).elementSet();
      } else {
         var2 = var1;
      }

      return this.elementSet().removeAll((Collection)var2);
   }

   public boolean retainAll(Collection<?> var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      Iterator var3 = this.entrySet().iterator();
      boolean var4 = false;

      while(var3.hasNext()) {
         Object var5 = ((Multiset.Entry)var3.next()).getElement();
         if(!var1.contains(var5)) {
            var3.remove();
            var4 = true;
         }
      }

      return var4;
   }

   public int setCount(E var1, int var2) {
      return Multisets.setCountImpl(this, var1, var2);
   }

   public boolean setCount(E var1, int var2, int var3) {
      return Multisets.setCountImpl(this, var1, var2, var3);
   }

   public int size() {
      long var1 = 0L;

      long var4;
      for(Iterator var3 = this.entrySet().iterator(); var3.hasNext(); var1 += var4) {
         var4 = (long)((Multiset.Entry)var3.next()).getCount();
      }

      return (int)Math.min(var1, 2147483647L);
   }

   public String toString() {
      return this.entrySet().toString();
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class MultisetIterator implements Iterator<E> {

      private boolean canRemove;
      private Multiset.Entry<E> currentEntry;
      private final Iterator<Multiset.Entry<E>> entryIterator;
      private int laterCount;
      private int totalCount;


      MultisetIterator() {
         Iterator var2 = AbstractMultiset.this.entrySet().iterator();
         this.entryIterator = var2;
      }

      public boolean hasNext() {
         boolean var1;
         if(this.laterCount <= 0 && !this.entryIterator.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public E next() {
         if(!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            if(this.laterCount == 0) {
               Multiset.Entry var1 = (Multiset.Entry)this.entryIterator.next();
               this.currentEntry = var1;
               int var2 = this.currentEntry.getCount();
               this.laterCount = var2;
               this.totalCount = var2;
            }

            int var3 = this.laterCount + -1;
            this.laterCount = var3;
            this.canRemove = (boolean)1;
            return this.currentEntry.getElement();
         }
      }

      public void remove() {
         Preconditions.checkState(this.canRemove, "no calls to next() since the last call to remove()");
         if(this.totalCount == 1) {
            this.entryIterator.remove();
         } else {
            AbstractMultiset var2 = AbstractMultiset.this;
            Object var3 = this.currentEntry.getElement();
            var2.remove(var3);
         }

         int var1 = this.totalCount + -1;
         this.totalCount = var1;
         this.canRemove = (boolean)0;
      }
   }

   private class ElementSet extends AbstractSet<E> {

      private ElementSet() {}

      // $FF: synthetic method
      ElementSet(AbstractMultiset.1 var2) {
         this();
      }

      public Iterator<E> iterator() {
         Iterator var1 = AbstractMultiset.this.entrySet().iterator();
         return new AbstractMultiset.ElementSet.1(var1);
      }

      public int size() {
         return AbstractMultiset.this.entrySet().size();
      }

      class 1 implements Iterator<E> {

         // $FF: synthetic field
         final Iterator val$entryIterator;


         1(Iterator var2) {
            this.val$entryIterator = var2;
         }

         public boolean hasNext() {
            return this.val$entryIterator.hasNext();
         }

         public E next() {
            return ((Multiset.Entry)this.val$entryIterator.next()).getElement();
         }

         public void remove() {
            this.val$entryIterator.remove();
         }
      }
   }
}
