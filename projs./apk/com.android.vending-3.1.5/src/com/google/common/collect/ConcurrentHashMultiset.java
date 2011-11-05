package com.google.common.collect;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Nullable;

public final class ConcurrentHashMultiset<E extends Object> extends AbstractMultiset<E> implements Serializable {

   private static final long serialVersionUID;
   private final transient ConcurrentMap<E, Integer> countMap;
   private transient ConcurrentHashMultiset.EntrySet entrySet;


   @VisibleForTesting
   ConcurrentHashMultiset(ConcurrentMap<E, Integer> var1) {
      Preconditions.checkArgument(var1.isEmpty());
      this.countMap = var1;
   }

   public static <E extends Object> ConcurrentHashMultiset<E> create() {
      ConcurrentHashMap var0 = new ConcurrentHashMap();
      return new ConcurrentHashMultiset(var0);
   }

   public static <E extends Object> ConcurrentHashMultiset<E> create(Iterable<? extends E> var0) {
      ConcurrentHashMultiset var1 = create();
      Iterables.addAll(var1, var0);
      return var1;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      Serialization.FieldSetter var2 = ConcurrentHashMultiset.FieldSettersHolder.COUNT_MAP_FIELD_SETTER;
      ConcurrentHashMap var3 = new ConcurrentHashMap();
      var2.set(this, var3);
      Serialization.populateMultiset(this, var1);
   }

   private int removeAllOccurrences(@Nullable Object var1) {
      int var2;
      int var3;
      try {
         var2 = unbox((Integer)this.countMap.remove(var1));
      } catch (NullPointerException var6) {
         var3 = 0;
         return var3;
      } catch (ClassCastException var7) {
         var3 = 0;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   private List<E> snapshot() {
      ArrayList var1 = Lists.newArrayListWithExpectedSize(this.size());
      Iterator var2 = this.entrySet().iterator();

      while(var2.hasNext()) {
         Multiset.Entry var3 = (Multiset.Entry)var2.next();
         Object var4 = var3.getElement();

         for(int var5 = var3.getCount(); var5 > 0; var5 += -1) {
            var1.add(var4);
         }
      }

      return var1;
   }

   private static int unbox(Integer var0) {
      int var1;
      if(var0 == null) {
         var1 = 0;
      } else {
         var1 = var0.intValue();
      }

      return var1;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMultiset(HashMultiset.create(this), var1);
   }

   public int add(E var1, int var2) {
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
         Preconditions.checkArgument((boolean)var4, "Invalid occurrences: %s", var5);

         while(true) {
            int var7 = this.count(var1);
            if(var7 == 0) {
               ConcurrentMap var8 = this.countMap;
               Integer var9 = Integer.valueOf(var2);
               if(var8.putIfAbsent(var1, var9) == null) {
                  break;
               }
            } else {
               int var10 = Integer.MAX_VALUE - var7;
               byte var11;
               if(var2 <= var10) {
                  var11 = 1;
               } else {
                  var11 = 0;
               }

               Object[] var12 = new Object[2];
               Integer var13 = Integer.valueOf(var2);
               var12[0] = var13;
               Integer var14 = Integer.valueOf(var7);
               var12[1] = var14;
               Preconditions.checkArgument((boolean)var11, "Overflow adding %s occurrences to a count of %s", var12);
               int var15 = var7 + var2;
               ConcurrentMap var16 = this.countMap;
               Integer var17 = Integer.valueOf(var7);
               Integer var18 = Integer.valueOf(var15);
               if(var16.replace(var1, var17, var18)) {
                  var3 = var7;
                  break;
               }
            }
         }
      }

      return var3;
   }

   public int count(@Nullable Object var1) {
      int var2;
      int var3;
      try {
         var2 = unbox((Integer)this.countMap.get(var1));
      } catch (NullPointerException var6) {
         var3 = 0;
         return var3;
      } catch (ClassCastException var7) {
         var3 = 0;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   Set<E> createElementSet() {
      Set var1 = this.countMap.keySet();
      return new ConcurrentHashMultiset.1(var1);
   }

   public Set<Multiset.Entry<E>> entrySet() {
      ConcurrentHashMultiset.EntrySet var1 = this.entrySet;
      if(var1 == null) {
         var1 = new ConcurrentHashMultiset.EntrySet((ConcurrentHashMultiset.1)null);
         this.entrySet = var1;
      }

      return var1;
   }

   public int remove(@Nullable Object var1, int var2) {
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
         Preconditions.checkArgument((boolean)var4, "Invalid occurrences: %s", var5);

         while(true) {
            var3 = this.count(var1);
            if(var3 == 0) {
               var3 = 0;
               break;
            }

            if(var2 >= var3) {
               ConcurrentMap var7 = this.countMap;
               Integer var8 = Integer.valueOf(var3);
               if(var7.remove(var1, var8)) {
                  break;
               }
            } else {
               ConcurrentMap var10 = this.countMap;
               Integer var11 = Integer.valueOf(var3);
               Integer var12 = Integer.valueOf(var3 - var2);
               if(var10.replace(var1, var11, var12)) {
                  break;
               }
            }
         }
      }

      return var3;
   }

   public boolean removeExactly(@Nullable Object var1, int var2) {
      boolean var3 = true;
      if(var2 != 0) {
         byte var4;
         if(var2 > 0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         Object[] var5 = new Object[1];
         Integer var6 = Integer.valueOf(var2);
         var5[0] = var6;
         Preconditions.checkArgument((boolean)var4, "Invalid occurrences: %s", var5);

         while(true) {
            int var7 = this.count(var1);
            if(var2 > var7) {
               var3 = false;
               break;
            }

            if(var2 == var7) {
               ConcurrentMap var8 = this.countMap;
               Integer var9 = Integer.valueOf(var2);
               if(var8.remove(var1, var9)) {
                  break;
               }
            } else {
               ConcurrentMap var11 = this.countMap;
               Integer var12 = Integer.valueOf(var7);
               Integer var13 = Integer.valueOf(var7 - var2);
               if(var11.replace(var1, var12, var13)) {
                  break;
               }
            }
         }
      }

      return var3;
   }

   public int setCount(E var1, int var2) {
      Multisets.checkNonnegative(var2, "count");
      int var3;
      if(var2 == 0) {
         var3 = this.removeAllOccurrences(var1);
      } else {
         ConcurrentMap var4 = this.countMap;
         Integer var5 = Integer.valueOf(var2);
         var3 = unbox((Integer)var4.put(var1, var5));
      }

      return var3;
   }

   public boolean setCount(E var1, int var2, int var3) {
      byte var4 = 1;
      Multisets.checkNonnegative(var2, "oldCount");
      Multisets.checkNonnegative(var3, "newCount");
      if(var3 == 0) {
         if(var2 == 0) {
            if(this.countMap.containsKey(var1)) {
               var4 = 0;
            }
         } else {
            ConcurrentMap var5 = this.countMap;
            Integer var6 = Integer.valueOf(var2);
            var4 = var5.remove(var1, var6);
         }
      } else if(var2 == 0) {
         ConcurrentMap var7 = this.countMap;
         Integer var8 = Integer.valueOf(var3);
         if(var7.putIfAbsent(var1, var8) != null) {
            var4 = 0;
         }
      } else {
         ConcurrentMap var9 = this.countMap;
         Integer var10 = Integer.valueOf(var2);
         Integer var11 = Integer.valueOf(var3);
         var4 = var9.replace(var1, var10, var11);
      }

      return (boolean)var4;
   }

   public int size() {
      long var1 = 0L;

      long var4;
      for(Iterator var3 = this.countMap.values().iterator(); var3.hasNext(); var1 += var4) {
         var4 = (long)((Integer)var3.next()).intValue();
      }

      return (int)Math.min(var1, 2147483647L);
   }

   public Object[] toArray() {
      return this.snapshot().toArray();
   }

   public <T extends Object> T[] toArray(T[] var1) {
      return this.snapshot().toArray(var1);
   }

   private class EntrySet extends AbstractSet<Multiset.Entry<E>> {

      private EntrySet() {}

      // $FF: synthetic method
      EntrySet(ConcurrentHashMultiset.1 var2) {
         this();
      }

      private List<Multiset.Entry<E>> snapshot() {
         ArrayList var1 = Lists.newArrayListWithExpectedSize(this.size());
         Iterator var2 = this.iterator();

         while(var2.hasNext()) {
            Multiset.Entry var3 = (Multiset.Entry)var2.next();
            var1.add(var3);
         }

         return var1;
      }

      public void clear() {
         ConcurrentHashMultiset.this.countMap.clear();
      }

      public boolean contains(Object var1) {
         boolean var2 = false;
         if(var1 instanceof Multiset.Entry) {
            Multiset.Entry var3 = (Multiset.Entry)var1;
            Object var4 = var3.getElement();
            int var5 = var3.getCount();
            if(var5 > 0 && ConcurrentHashMultiset.this.count(var4) == var5) {
               var2 = true;
            }
         }

         return var2;
      }

      public int hashCode() {
         return ConcurrentHashMultiset.this.countMap.hashCode();
      }

      public boolean isEmpty() {
         return ConcurrentHashMultiset.this.countMap.isEmpty();
      }

      public Iterator<Multiset.Entry<E>> iterator() {
         Iterator var1 = ConcurrentHashMultiset.this.countMap.entrySet().iterator();
         return new ConcurrentHashMultiset.EntrySet.1(var1);
      }

      public boolean remove(Object var1) {
         boolean var7;
         if(var1 instanceof Multiset.Entry) {
            Multiset.Entry var2 = (Multiset.Entry)var1;
            Object var3 = var2.getElement();
            int var4 = var2.getCount();
            ConcurrentMap var5 = ConcurrentHashMultiset.this.countMap;
            Integer var6 = Integer.valueOf(var4);
            var7 = var5.remove(var3, var6);
         } else {
            var7 = false;
         }

         return var7;
      }

      public int size() {
         return ConcurrentHashMultiset.this.countMap.size();
      }

      public Object[] toArray() {
         return this.snapshot().toArray();
      }

      public <T extends Object> T[] toArray(T[] var1) {
         return this.snapshot().toArray(var1);
      }

      class 1 implements Iterator<Multiset.Entry<E>> {

         // $FF: synthetic field
         final Iterator val$backingIterator;


         1(Iterator var2) {
            this.val$backingIterator = var2;
         }

         public boolean hasNext() {
            return this.val$backingIterator.hasNext();
         }

         public Multiset.Entry<E> next() {
            Entry var1 = (Entry)this.val$backingIterator.next();
            Object var2 = var1.getKey();
            int var3 = ((Integer)var1.getValue()).intValue();
            return Multisets.immutableEntry(var2, var3);
         }

         public void remove() {
            this.val$backingIterator.remove();
         }
      }
   }

   class 1 extends ForwardingSet<E> {

      // $FF: synthetic field
      final Set val$delegate;


      1(Set var2) {
         this.val$delegate = var2;
      }

      protected Set<E> delegate() {
         return this.val$delegate;
      }

      public boolean remove(Object var1) {
         boolean var2 = false;

         boolean var3;
         try {
            var3 = this.val$delegate.remove(var1);
         } catch (NullPointerException var6) {
            return var2;
         } catch (ClassCastException var7) {
            return var2;
         }

         var2 = var3;
         return var2;
      }
   }

   private static class FieldSettersHolder {

      static final Serialization.FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");


      private FieldSettersHolder() {}
   }
}
