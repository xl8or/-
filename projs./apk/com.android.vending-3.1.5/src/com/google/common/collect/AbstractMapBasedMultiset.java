package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMapBasedMultiset<E extends Object> extends AbstractMultiset<E> implements Serializable {

   private static final long serialVersionUID = -2250766705698539974L;
   private transient Map<E, AtomicInteger> backingMap;
   private transient AbstractMapBasedMultiset.EntrySet entrySet;
   private transient long size;


   protected AbstractMapBasedMultiset(Map<E, AtomicInteger> var1) {
      Map var2 = (Map)Preconditions.checkNotNull(var1);
      this.backingMap = var2;
      long var3 = (long)super.size();
      this.size = var3;
   }

   // $FF: synthetic method
   static long access$210(AbstractMapBasedMultiset var0) {
      long var1 = var0.size;
      long var3 = var1 - 1L;
      var0.size = var3;
      return var1;
   }

   // $FF: synthetic method
   static long access$222(AbstractMapBasedMultiset var0, long var1) {
      long var3 = var0.size - var1;
      var0.size = var3;
      return var3;
   }

   private static int getAndSet(AtomicInteger var0, int var1) {
      int var2;
      if(var0 == null) {
         var2 = 0;
      } else {
         var2 = var0.getAndSet(var1);
      }

      return var2;
   }

   private void readObjectNoData() throws ObjectStreamException {
      throw new InvalidObjectException("Stream data required");
   }

   private int removeAllOccurrences(@Nullable Object var1, Map<E, AtomicInteger> var2) {
      int var3 = 0;
      AtomicInteger var4 = (AtomicInteger)var2.remove(var1);
      if(var4 != null) {
         var3 = var4.getAndSet(0);
         long var5 = this.size;
         long var7 = (long)var3;
         long var9 = var5 - var7;
         this.size = var9;
      }

      return var3;
   }

   public int add(@Nullable E var1, int var2) {
      int var3;
      if(var2 == 0) {
         var3 = this.count(var1);
      } else {
         byte var4;
         if(var2 > 0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         Object[] var5 = new Object[1];
         Integer var6 = Integer.valueOf(var2);
         var5[0] = var6;
         Preconditions.checkArgument((boolean)var4, "occurrences cannot be negative: %s", var5);
         AtomicInteger var7 = (AtomicInteger)this.backingMap.get(var1);
         if(var7 == null) {
            var3 = 0;
            Map var8 = this.backingMap;
            AtomicInteger var9 = new AtomicInteger(var2);
            var8.put(var1, var9);
         } else {
            var3 = var7.get();
            long var17 = (long)var3;
            long var19 = (long)var2;
            long var21 = var17 + var19;
            byte var23;
            if(var21 <= 2147483647L) {
               var23 = 1;
            } else {
               var23 = 0;
            }

            Object[] var24 = new Object[1];
            Long var25 = Long.valueOf(var21);
            var24[0] = var25;
            Preconditions.checkArgument((boolean)var23, "too many occurrences: %s", var24);
            var7.getAndAdd(var2);
         }

         long var11 = this.size;
         long var13 = (long)var2;
         long var15 = var11 + var13;
         this.size = var15;
      }

      return var3;
   }

   Map<E, AtomicInteger> backingMap() {
      return this.backingMap;
   }

   public int count(@Nullable Object var1) {
      AtomicInteger var2 = (AtomicInteger)this.backingMap.get(var1);
      int var3;
      if(var2 == null) {
         var3 = 0;
      } else {
         var3 = var2.get();
      }

      return var3;
   }

   Set<E> createElementSet() {
      Map var1 = this.backingMap;
      return new AbstractMapBasedMultiset.MapBasedElementSet(var1);
   }

   public Set<Multiset.Entry<E>> entrySet() {
      AbstractMapBasedMultiset.EntrySet var1 = this.entrySet;
      if(var1 == null) {
         var1 = new AbstractMapBasedMultiset.EntrySet((AbstractMapBasedMultiset.1)null);
         this.entrySet = var1;
      }

      return var1;
   }

   public Iterator<E> iterator() {
      return new AbstractMapBasedMultiset.MapBasedMultisetIterator();
   }

   public int remove(@Nullable Object var1, int var2) {
      int var3 = 0;
      if(var2 == 0) {
         var3 = this.count(var1);
      } else {
         byte var4;
         if(var2 > 0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         Object[] var5 = new Object[1];
         Integer var6 = Integer.valueOf(var2);
         var5[0] = var6;
         Preconditions.checkArgument((boolean)var4, "occurrences cannot be negative: %s", var5);
         AtomicInteger var7 = (AtomicInteger)this.backingMap.get(var1);
         if(var7 != null) {
            int var8 = var7.get();
            int var9;
            if(var8 > var2) {
               var9 = var2;
            } else {
               var9 = var8;
               this.backingMap.remove(var1);
            }

            int var10 = -var9;
            var7.addAndGet(var10);
            long var12 = this.size;
            long var14 = (long)var9;
            long var16 = var12 - var14;
            this.size = var16;
            var3 = var8;
         }
      }

      return var3;
   }

   void setBackingMap(Map<E, AtomicInteger> var1) {
      this.backingMap = var1;
   }

   public int setCount(E var1, int var2) {
      Multisets.checkNonnegative(var2, "count");
      int var3;
      if(var2 == 0) {
         var3 = getAndSet((AtomicInteger)this.backingMap.remove(var1), var2);
      } else {
         AtomicInteger var10 = (AtomicInteger)this.backingMap.get(var1);
         var3 = getAndSet(var10, var2);
         if(var10 == null) {
            Map var11 = this.backingMap;
            AtomicInteger var12 = new AtomicInteger(var2);
            var11.put(var1, var12);
         }
      }

      long var4 = this.size;
      long var6 = (long)(var2 - var3);
      long var8 = var4 + var6;
      this.size = var8;
      return var3;
   }

   public int size() {
      return (int)Math.min(this.size, 2147483647L);
   }

   class MapBasedElementSet extends ForwardingSet<E> {

      private final Set<E> delegate;
      private final Map<E, AtomicInteger> map;


      MapBasedElementSet(Map var2) {
         this.map = var2;
         Set var3 = var2.keySet();
         this.delegate = var3;
      }

      public void clear() {
         Map var1 = this.map;
         Map var2 = AbstractMapBasedMultiset.this.backingMap;
         if(var1 == var2) {
            AbstractMapBasedMultiset.this.clear();
         } else {
            Iterator var3 = this.iterator();

            while(var3.hasNext()) {
               Object var4 = var3.next();
               var3.remove();
            }

         }
      }

      protected Set<E> delegate() {
         return this.delegate;
      }

      public Map<E, AtomicInteger> getMap() {
         return this.map;
      }

      public Iterator<E> iterator() {
         Iterator var1 = this.map.entrySet().iterator();
         return new AbstractMapBasedMultiset.MapBasedElementSet.1(var1);
      }

      public boolean remove(Object var1) {
         AbstractMapBasedMultiset var2 = AbstractMapBasedMultiset.this;
         Map var3 = this.map;
         boolean var4;
         if(var2.removeAllOccurrences(var1, var3) != 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }

      public boolean removeAll(Collection<?> var1) {
         return Iterators.removeAll(this.iterator(), var1);
      }

      public boolean retainAll(Collection<?> var1) {
         return Iterators.retainAll(this.iterator(), var1);
      }

      class 1 implements Iterator<E> {

         Entry<E, AtomicInteger> toRemove;
         // $FF: synthetic field
         final Iterator val$entries;


         1(Iterator var2) {
            this.val$entries = var2;
         }

         public boolean hasNext() {
            return this.val$entries.hasNext();
         }

         public E next() {
            Entry var1 = (Entry)this.val$entries.next();
            this.toRemove = var1;
            return this.toRemove.getKey();
         }

         public void remove() {
            byte var1;
            if(this.toRemove != null) {
               var1 = 1;
            } else {
               var1 = 0;
            }

            Preconditions.checkState((boolean)var1, "no calls to next() since the last call to remove()");
            AbstractMapBasedMultiset var2 = AbstractMapBasedMultiset.this;
            long var3 = (long)((AtomicInteger)this.toRemove.getValue()).getAndSet(0);
            AbstractMapBasedMultiset.access$222(var2, var3);
            this.val$entries.remove();
            this.toRemove = null;
         }
      }
   }

   private class MapBasedMultisetIterator implements Iterator<E> {

      boolean canRemove;
      Entry<E, AtomicInteger> currentEntry;
      final Iterator<Entry<E, AtomicInteger>> entryIterator;
      int occurrencesLeft;


      MapBasedMultisetIterator() {
         Iterator var2 = AbstractMapBasedMultiset.this.backingMap.entrySet().iterator();
         this.entryIterator = var2;
      }

      public boolean hasNext() {
         boolean var1;
         if(this.occurrencesLeft <= 0 && !this.entryIterator.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public E next() {
         if(this.occurrencesLeft == 0) {
            Entry var1 = (Entry)this.entryIterator.next();
            this.currentEntry = var1;
            int var2 = ((AtomicInteger)this.currentEntry.getValue()).get();
            this.occurrencesLeft = var2;
         }

         int var3 = this.occurrencesLeft + -1;
         this.occurrencesLeft = var3;
         this.canRemove = (boolean)1;
         return this.currentEntry.getKey();
      }

      public void remove() {
         Preconditions.checkState(this.canRemove, "no calls to next() since the last call to remove()");
         if(((AtomicInteger)this.currentEntry.getValue()).get() <= 0) {
            throw new ConcurrentModificationException();
         } else {
            if(((AtomicInteger)this.currentEntry.getValue()).addAndGet(-1) == 0) {
               this.entryIterator.remove();
            }

            long var1 = AbstractMapBasedMultiset.access$210(AbstractMapBasedMultiset.this);
            this.canRemove = (boolean)0;
         }
      }
   }

   private class EntrySet extends AbstractSet<Multiset.Entry<E>> {

      private EntrySet() {}

      // $FF: synthetic method
      EntrySet(AbstractMapBasedMultiset.1 var2) {
         this();
      }

      public void clear() {
         Iterator var1 = AbstractMapBasedMultiset.this.backingMap.values().iterator();

         while(var1.hasNext()) {
            ((AtomicInteger)var1.next()).set(0);
         }

         AbstractMapBasedMultiset.this.backingMap.clear();
         long var2 = AbstractMapBasedMultiset.this.size = 0L;
      }

      public boolean contains(Object var1) {
         boolean var2 = false;
         if(var1 instanceof Multiset.Entry) {
            Multiset.Entry var3 = (Multiset.Entry)var1;
            AbstractMapBasedMultiset var4 = AbstractMapBasedMultiset.this;
            Object var5 = var3.getElement();
            int var6 = var4.count(var5);
            int var7 = var3.getCount();
            if(var6 == var7 && var6 > 0) {
               var2 = true;
            }
         }

         return var2;
      }

      public Iterator<Multiset.Entry<E>> iterator() {
         Iterator var1 = AbstractMapBasedMultiset.this.backingMap.entrySet().iterator();
         return new AbstractMapBasedMultiset.EntrySet.1(var1);
      }

      public boolean remove(Object var1) {
         boolean var2 = false;
         if(this.contains(var1)) {
            Multiset.Entry var3 = (Multiset.Entry)var1;
            Map var4 = AbstractMapBasedMultiset.this.backingMap;
            Object var5 = var3.getElement();
            int var6 = ((AtomicInteger)var4.remove(var5)).getAndSet(0);
            AbstractMapBasedMultiset var7 = AbstractMapBasedMultiset.this;
            long var8 = (long)var6;
            AbstractMapBasedMultiset.access$222(var7, var8);
            var2 = true;
         }

         return var2;
      }

      public int size() {
         return AbstractMapBasedMultiset.this.backingMap.size();
      }

      class 1 implements Iterator<Multiset.Entry<E>> {

         Entry<E, AtomicInteger> toRemove;
         // $FF: synthetic field
         final Iterator val$backingEntries;


         1(Iterator var2) {
            this.val$backingEntries = var2;
         }

         public boolean hasNext() {
            return this.val$backingEntries.hasNext();
         }

         public Multiset.Entry<E> next() {
            Entry var1 = (Entry)this.val$backingEntries.next();
            this.toRemove = var1;
            return new AbstractMapBasedMultiset.EntrySet.1.1(var1);
         }

         public void remove() {
            byte var1;
            if(this.toRemove != null) {
               var1 = 1;
            } else {
               var1 = 0;
            }

            Preconditions.checkState((boolean)var1, "no calls to next() since the last call to remove()");
            AbstractMapBasedMultiset var2 = AbstractMapBasedMultiset.this;
            long var3 = (long)((AtomicInteger)this.toRemove.getValue()).getAndSet(0);
            AbstractMapBasedMultiset.access$222(var2, var3);
            this.val$backingEntries.remove();
            this.toRemove = null;
         }

         class 1 extends Multisets.AbstractEntry<E> {

            // $FF: synthetic field
            final Entry val$mapEntry;


            1(Entry var2) {
               this.val$mapEntry = var2;
            }

            public int getCount() {
               int var1 = ((AtomicInteger)this.val$mapEntry.getValue()).get();
               if(var1 == 0) {
                  Map var2 = AbstractMapBasedMultiset.this.backingMap;
                  Object var3 = this.getElement();
                  AtomicInteger var4 = (AtomicInteger)var2.get(var3);
                  if(var4 != null) {
                     var1 = var4.get();
                  }
               }

               return var1;
            }

            public E getElement() {
               return this.val$mapEntry.getKey();
            }
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
