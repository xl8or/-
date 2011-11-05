package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Platform;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.SortedSetMultimap;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
final class Synchronized {

   private Synchronized() {}

   public static <K extends Object, V extends Object> BiMap<K, V> biMap(BiMap<K, V> var0, @Nullable Object var1) {
      return new Synchronized.SynchronizedBiMap(var0, var1, (BiMap)null);
   }

   static <E extends Object> Collection<E> collection(Collection<E> var0, @Nullable Object var1) {
      return new Synchronized.SynchronizedCollection(var0, var1);
   }

   static <E extends Object> List<E> list(List<E> var0, @Nullable Object var1) {
      Object var2;
      if(var0 instanceof RandomAccess) {
         var2 = new Synchronized.SynchronizedRandomAccessList(var0, var1);
      } else {
         var2 = new Synchronized.SynchronizedList(var0, var1);
      }

      return (List)var2;
   }

   public static <K extends Object, V extends Object> ListMultimap<K, V> listMultimap(ListMultimap<K, V> var0, @Nullable Object var1) {
      return new Synchronized.SynchronizedListMultimap(var0, var1);
   }

   public static <K extends Object, V extends Object> Map<K, V> map(Map<K, V> var0, @Nullable Object var1) {
      return new Synchronized.SynchronizedMap(var0, var1);
   }

   public static <K extends Object, V extends Object> Multimap<K, V> multimap(Multimap<K, V> var0, @Nullable Object var1) {
      return new Synchronized.SynchronizedMultimap(var0, var1);
   }

   private static <E extends Object> Multiset<E> multiset(Multiset<E> var0, @Nullable Object var1) {
      return new Synchronized.SynchronizedMultiset(var0, var1);
   }

   public static <E extends Object> Set<E> set(Set<E> var0, @Nullable Object var1) {
      return new Synchronized.SynchronizedSet(var0, var1);
   }

   public static <K extends Object, V extends Object> SetMultimap<K, V> setMultimap(SetMultimap<K, V> var0, @Nullable Object var1) {
      return new Synchronized.SynchronizedSetMultimap(var0, var1);
   }

   static <E extends Object> SortedSet<E> sortedSet(SortedSet<E> var0, @Nullable Object var1) {
      return new Synchronized.SynchronizedSortedSet(var0, var1);
   }

   public static <K extends Object, V extends Object> SortedSetMultimap<K, V> sortedSetMultimap(SortedSetMultimap<K, V> var0, @Nullable Object var1) {
      return new Synchronized.SynchronizedSortedSetMultimap(var0, var1);
   }

   private static <E extends Object> Collection<E> typePreservingCollection(Collection<E> var0, @Nullable Object var1) {
      Object var2;
      if(var0 instanceof SortedSet) {
         var2 = sortedSet((SortedSet)var0, var1);
      } else if(var0 instanceof Set) {
         var2 = set((Set)var0, var1);
      } else if(var0 instanceof List) {
         var2 = list((List)var0, var1);
      } else {
         var2 = collection(var0, var1);
      }

      return (Collection)var2;
   }

   public static <E extends Object> Set<E> typePreservingSet(Set<E> var0, @Nullable Object var1) {
      Object var2;
      if(var0 instanceof SortedSet) {
         var2 = sortedSet((SortedSet)var0, var1);
      } else {
         var2 = set(var0, var1);
      }

      return (Set)var2;
   }

   static class SynchronizedMultiset<E extends Object> extends Synchronized.SynchronizedCollection<E> implements Multiset<E> {

      private static final long serialVersionUID;
      private transient Set<E> elementSet;
      private transient Set<Multiset.Entry<E>> entrySet;


      public SynchronizedMultiset(Multiset<E> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      public int add(E var1, int var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            int var4 = this.delegate().add(var1, var2);
            return var4;
         }
      }

      public int count(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            int var3 = this.delegate().count(var1);
            return var3;
         }
      }

      protected Multiset<E> delegate() {
         return (Multiset)super.delegate();
      }

      public Set<E> elementSet() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.elementSet == null) {
               Set var2 = this.delegate().elementSet();
               Object var3 = this.mutex;
               Set var4 = Synchronized.typePreservingSet(var2, var3);
               this.elementSet = var4;
            }

            Set var5 = this.elementSet;
            return var5;
         }
      }

      public Set<Multiset.Entry<E>> entrySet() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.entrySet == null) {
               Set var2 = this.delegate().entrySet();
               Object var3 = this.mutex;
               Set var4 = Synchronized.typePreservingSet(var2, var3);
               this.entrySet = var4;
            }

            Set var5 = this.entrySet;
            return var5;
         }
      }

      public boolean equals(Object var1) {
         byte var2;
         if(var1 == this) {
            var2 = 1;
         } else {
            Object var3 = this.mutex;
            synchronized(var3) {
               var2 = this.delegate().equals(var1);
            }
         }

         return (boolean)var2;
      }

      public int hashCode() {
         Object var1 = this.mutex;
         synchronized(var1) {
            int var2 = this.delegate().hashCode();
            return var2;
         }
      }

      public int remove(Object var1, int var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            int var4 = this.delegate().remove(var1, var2);
            return var4;
         }
      }

      public int setCount(E var1, int var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            int var4 = this.delegate().setCount(var1, var2);
            return var4;
         }
      }

      public boolean setCount(E var1, int var2, int var3) {
         Object var4 = this.mutex;
         synchronized(var4) {
            boolean var5 = this.delegate().setCount(var1, var2, var3);
            return var5;
         }
      }
   }

   private static class SynchronizedSetMultimap<K extends Object, V extends Object> extends Synchronized.SynchronizedMultimap<K, V> implements SetMultimap<K, V> {

      private static final long serialVersionUID;
      transient Set<Entry<K, V>> entrySet;


      SynchronizedSetMultimap(SetMultimap<K, V> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      protected SetMultimap<K, V> delegate() {
         return (SetMultimap)super.delegate();
      }

      public Set<Entry<K, V>> entries() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.entrySet == null) {
               Set var2 = this.delegate().entries();
               Object var3 = this.mutex;
               Set var4 = Synchronized.set(var2, var3);
               this.entrySet = var4;
            }

            Set var5 = this.entrySet;
            return var5;
         }
      }

      public Set<V> get(K var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Set var3 = this.delegate().get(var1);
            Object var4 = this.mutex;
            Set var5 = Synchronized.set(var3, var4);
            return var5;
         }
      }

      public Set<V> removeAll(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Set var3 = this.delegate().removeAll(var1);
            return var3;
         }
      }

      public Set<V> replaceValues(K var1, Iterable<? extends V> var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            Set var4 = this.delegate().replaceValues(var1, var2);
            return var4;
         }
      }
   }

   static class SynchronizedMap<K extends Object, V extends Object> extends Synchronized.SynchronizedObject implements Map<K, V> {

      private static final long serialVersionUID;
      private transient Set<Entry<K, V>> entrySet;
      private transient Set<K> keySet;
      private transient Collection<V> values;


      public SynchronizedMap(Map<K, V> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      public void clear() {
         Object var1 = this.mutex;
         synchronized(var1) {
            this.delegate().clear();
         }
      }

      public boolean containsKey(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().containsKey(var1);
            return var3;
         }
      }

      public boolean containsValue(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().containsValue(var1);
            return var3;
         }
      }

      protected Map<K, V> delegate() {
         return (Map)super.delegate();
      }

      public Set<Entry<K, V>> entrySet() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.entrySet == null) {
               Set var2 = this.delegate().entrySet();
               Object var3 = this.mutex;
               Set var4 = Synchronized.set(var2, var3);
               this.entrySet = var4;
            }

            Set var5 = this.entrySet;
            return var5;
         }
      }

      public boolean equals(Object var1) {
         byte var2;
         if(var1 == this) {
            var2 = 1;
         } else {
            Object var3 = this.mutex;
            synchronized(var3) {
               var2 = this.delegate().equals(var1);
            }
         }

         return (boolean)var2;
      }

      public V get(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Object var3 = this.delegate().get(var1);
            return var3;
         }
      }

      public int hashCode() {
         Object var1 = this.mutex;
         synchronized(var1) {
            int var2 = this.delegate().hashCode();
            return var2;
         }
      }

      public boolean isEmpty() {
         Object var1 = this.mutex;
         synchronized(var1) {
            boolean var2 = this.delegate().isEmpty();
            return var2;
         }
      }

      public Set<K> keySet() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.keySet == null) {
               Set var2 = this.delegate().keySet();
               Object var3 = this.mutex;
               Set var4 = Synchronized.set(var2, var3);
               this.keySet = var4;
            }

            Set var5 = this.keySet;
            return var5;
         }
      }

      public V put(K var1, V var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            Object var4 = this.delegate().put(var1, var2);
            return var4;
         }
      }

      public void putAll(Map<? extends K, ? extends V> var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            this.delegate().putAll(var1);
         }
      }

      public V remove(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Object var3 = this.delegate().remove(var1);
            return var3;
         }
      }

      public int size() {
         Object var1 = this.mutex;
         synchronized(var1) {
            int var2 = this.delegate().size();
            return var2;
         }
      }

      public Collection<V> values() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.values == null) {
               Collection var2 = this.delegate().values();
               Object var3 = this.mutex;
               Collection var4 = Synchronized.collection(var2, var3);
               this.values = var4;
            }

            Collection var5 = this.values;
            return var5;
         }
      }
   }

   static class SynchronizedBiMap<K extends Object, V extends Object> extends Synchronized.SynchronizedMap<K, V> implements BiMap<K, V>, Serializable {

      private static final long serialVersionUID;
      private transient BiMap<V, K> inverse;
      private transient Set<V> valueSet;


      public SynchronizedBiMap(BiMap<K, V> var1, @Nullable Object var2, @Nullable BiMap<V, K> var3) {
         super(var1, var2);
         this.inverse = var3;
      }

      protected BiMap<K, V> delegate() {
         return (BiMap)super.delegate();
      }

      public V forcePut(K var1, V var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            Object var4 = this.delegate().forcePut(var1, var2);
            return var4;
         }
      }

      public BiMap<V, K> inverse() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.inverse == null) {
               BiMap var2 = this.delegate().inverse();
               Object var3 = this.mutex;
               Synchronized.SynchronizedBiMap var4 = new Synchronized.SynchronizedBiMap(var2, var3, this);
               this.inverse = var4;
            }

            BiMap var5 = this.inverse;
            return var5;
         }
      }

      public Set<V> values() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.valueSet == null) {
               Set var2 = this.delegate().values();
               Object var3 = this.mutex;
               Set var4 = Synchronized.set(var2, var3);
               this.valueSet = var4;
            }

            Set var5 = this.valueSet;
            return var5;
         }
      }
   }

   static class SynchronizedAsMap<K extends Object, V extends Object> extends Synchronized.SynchronizedMap<K, Collection<V>> {

      private static final long serialVersionUID;
      private transient Set<Entry<K, Collection<V>>> asMapEntrySet;
      private transient Collection<Collection<V>> asMapValues;


      public SynchronizedAsMap(Map<K, Collection<V>> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      public boolean containsValue(Object var1) {
         return this.values().contains(var1);
      }

      public Set<Entry<K, Collection<V>>> entrySet() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.asMapEntrySet == null) {
               Set var2 = this.delegate().entrySet();
               Object var3 = this.mutex;
               Synchronized.SynchronizedAsMapEntries var4 = new Synchronized.SynchronizedAsMapEntries(var2, var3);
               this.asMapEntrySet = var4;
            }

            Set var5 = this.asMapEntrySet;
            return var5;
         }
      }

      public Collection<V> get(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Collection var3 = (Collection)super.get(var1);
            Collection var4;
            if(var3 == null) {
               var4 = null;
            } else {
               Object var5 = this.mutex;
               var4 = Synchronized.typePreservingCollection(var3, var5);
            }

            return var4;
         }
      }

      public Collection<Collection<V>> values() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.asMapValues == null) {
               Collection var2 = this.delegate().values();
               Object var3 = this.mutex;
               Synchronized.SynchronizedAsMapValues var4 = new Synchronized.SynchronizedAsMapValues(var2, var3);
               this.asMapValues = var4;
            }

            Collection var5 = this.asMapValues;
            return var5;
         }
      }
   }

   static class SynchronizedRandomAccessList<E extends Object> extends Synchronized.SynchronizedList<E> implements RandomAccess {

      private static final long serialVersionUID;


      public SynchronizedRandomAccessList(List<E> var1, @Nullable Object var2) {
         super(var1, var2);
      }
   }

   private static class SynchronizedListMultimap<K extends Object, V extends Object> extends Synchronized.SynchronizedMultimap<K, V> implements ListMultimap<K, V> {

      private static final long serialVersionUID;


      SynchronizedListMultimap(ListMultimap<K, V> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      protected ListMultimap<K, V> delegate() {
         return (ListMultimap)super.delegate();
      }

      public List<V> get(K var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            List var3 = this.delegate().get(var1);
            Object var4 = this.mutex;
            List var5 = Synchronized.list(var3, var4);
            return var5;
         }
      }

      public List<V> removeAll(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            List var3 = this.delegate().removeAll(var1);
            return var3;
         }
      }

      public List<V> replaceValues(K var1, Iterable<? extends V> var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            List var4 = this.delegate().replaceValues(var1, var2);
            return var4;
         }
      }
   }

   private static class SynchronizedMultimap<K extends Object, V extends Object> extends Synchronized.SynchronizedObject implements Multimap<K, V> {

      private static final long serialVersionUID;
      transient Map<K, Collection<V>> asMap;
      transient Collection<Entry<K, V>> entries;
      transient Set<K> keySet;
      transient Multiset<K> keys;
      transient Collection<V> valuesCollection;


      SynchronizedMultimap(Multimap<K, V> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      public Map<K, Collection<V>> asMap() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.asMap == null) {
               Map var2 = this.delegate().asMap();
               Object var3 = this.mutex;
               Synchronized.SynchronizedAsMap var4 = new Synchronized.SynchronizedAsMap(var2, var3);
               this.asMap = var4;
            }

            Map var5 = this.asMap;
            return var5;
         }
      }

      public void clear() {
         Object var1 = this.mutex;
         synchronized(var1) {
            this.delegate().clear();
         }
      }

      public boolean containsEntry(Object var1, Object var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            boolean var4 = this.delegate().containsEntry(var1, var2);
            return var4;
         }
      }

      public boolean containsKey(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().containsKey(var1);
            return var3;
         }
      }

      public boolean containsValue(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().containsValue(var1);
            return var3;
         }
      }

      protected Multimap<K, V> delegate() {
         return (Multimap)super.delegate();
      }

      public Collection<Entry<K, V>> entries() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.entries == null) {
               Collection var2 = this.delegate().entries();
               Object var3 = this.mutex;
               Collection var4 = Synchronized.typePreservingCollection(var2, var3);
               this.entries = var4;
            }

            Collection var5 = this.entries;
            return var5;
         }
      }

      public boolean equals(Object var1) {
         byte var2;
         if(var1 == this) {
            var2 = 1;
         } else {
            Object var3 = this.mutex;
            synchronized(var3) {
               var2 = this.delegate().equals(var1);
            }
         }

         return (boolean)var2;
      }

      public Collection<V> get(K var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Collection var3 = this.delegate().get(var1);
            Object var4 = this.mutex;
            Collection var5 = Synchronized.typePreservingCollection(var3, var4);
            return var5;
         }
      }

      public int hashCode() {
         Object var1 = this.mutex;
         synchronized(var1) {
            int var2 = this.delegate().hashCode();
            return var2;
         }
      }

      public boolean isEmpty() {
         Object var1 = this.mutex;
         synchronized(var1) {
            boolean var2 = this.delegate().isEmpty();
            return var2;
         }
      }

      public Set<K> keySet() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.keySet == null) {
               Set var2 = this.delegate().keySet();
               Object var3 = this.mutex;
               Set var4 = Synchronized.typePreservingSet(var2, var3);
               this.keySet = var4;
            }

            Set var5 = this.keySet;
            return var5;
         }
      }

      public Multiset<K> keys() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.keys == null) {
               Multiset var2 = this.delegate().keys();
               Object var3 = this.mutex;
               Multiset var4 = Synchronized.multiset(var2, var3);
               this.keys = var4;
            }

            Multiset var5 = this.keys;
            return var5;
         }
      }

      public boolean put(K var1, V var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            boolean var4 = this.delegate().put(var1, var2);
            return var4;
         }
      }

      public boolean putAll(Multimap<? extends K, ? extends V> var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().putAll(var1);
            return var3;
         }
      }

      public boolean putAll(K var1, Iterable<? extends V> var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            boolean var4 = this.delegate().putAll(var1, var2);
            return var4;
         }
      }

      public boolean remove(Object var1, Object var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            boolean var4 = this.delegate().remove(var1, var2);
            return var4;
         }
      }

      public Collection<V> removeAll(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Collection var3 = this.delegate().removeAll(var1);
            return var3;
         }
      }

      public Collection<V> replaceValues(K var1, Iterable<? extends V> var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            Collection var4 = this.delegate().replaceValues(var1, var2);
            return var4;
         }
      }

      public int size() {
         Object var1 = this.mutex;
         synchronized(var1) {
            int var2 = this.delegate().size();
            return var2;
         }
      }

      public Collection<V> values() {
         Object var1 = this.mutex;
         synchronized(var1) {
            if(this.valuesCollection == null) {
               Collection var2 = this.delegate().values();
               Object var3 = this.mutex;
               Collection var4 = Synchronized.collection(var2, var3);
               this.valuesCollection = var4;
            }

            Collection var5 = this.valuesCollection;
            return var5;
         }
      }
   }

   static class SynchronizedCollection<E extends Object> extends Synchronized.SynchronizedObject implements Collection<E> {

      private static final long serialVersionUID;


      public SynchronizedCollection(Collection<E> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      public boolean add(E var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().add(var1);
            return var3;
         }
      }

      public boolean addAll(Collection<? extends E> var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().addAll(var1);
            return var3;
         }
      }

      public void clear() {
         Object var1 = this.mutex;
         synchronized(var1) {
            this.delegate().clear();
         }
      }

      public boolean contains(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().contains(var1);
            return var3;
         }
      }

      public boolean containsAll(Collection<?> var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().containsAll(var1);
            return var3;
         }
      }

      protected Collection<E> delegate() {
         return (Collection)super.delegate();
      }

      public boolean isEmpty() {
         Object var1 = this.mutex;
         synchronized(var1) {
            boolean var2 = this.delegate().isEmpty();
            return var2;
         }
      }

      public Iterator<E> iterator() {
         return this.delegate().iterator();
      }

      public boolean remove(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().remove(var1);
            return var3;
         }
      }

      public boolean removeAll(Collection<?> var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().removeAll(var1);
            return var3;
         }
      }

      public boolean retainAll(Collection<?> var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = this.delegate().retainAll(var1);
            return var3;
         }
      }

      public int size() {
         Object var1 = this.mutex;
         synchronized(var1) {
            int var2 = this.delegate().size();
            return var2;
         }
      }

      public Object[] toArray() {
         Object var1 = this.mutex;
         synchronized(var1) {
            Object[] var2 = this.delegate().toArray();
            return var2;
         }
      }

      public <T extends Object> T[] toArray(T[] var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Object[] var3 = this.delegate().toArray(var1);
            return var3;
         }
      }
   }

   static class SynchronizedSet<E extends Object> extends Synchronized.SynchronizedCollection<E> implements Set<E> {

      private static final long serialVersionUID;


      public SynchronizedSet(Set<E> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      protected Set<E> delegate() {
         return (Set)super.delegate();
      }

      public boolean equals(Object var1) {
         byte var2;
         if(var1 == this) {
            var2 = 1;
         } else {
            Object var3 = this.mutex;
            synchronized(var3) {
               var2 = this.delegate().equals(var1);
            }
         }

         return (boolean)var2;
      }

      public int hashCode() {
         Object var1 = this.mutex;
         synchronized(var1) {
            int var2 = this.delegate().hashCode();
            return var2;
         }
      }
   }

   static class SynchronizedAsMapValues<V extends Object> extends Synchronized.SynchronizedCollection<Collection<V>> {

      private static final long serialVersionUID;


      SynchronizedAsMapValues(Collection<Collection<V>> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      public Iterator<Collection<V>> iterator() {
         Iterator var1 = super.iterator();
         return new Synchronized.SynchronizedAsMapValues.1(var1);
      }

      class 1 extends ForwardingIterator<Collection<V>> {

         // $FF: synthetic field
         final Iterator val$iterator;


         1(Iterator var2) {
            this.val$iterator = var2;
         }

         protected Iterator<Collection<V>> delegate() {
            return this.val$iterator;
         }

         public Collection<V> next() {
            Collection var1 = (Collection)this.val$iterator.next();
            Object var2 = SynchronizedAsMapValues.this.mutex;
            return Synchronized.typePreservingCollection(var1, var2);
         }
      }
   }

   static class SynchronizedList<E extends Object> extends Synchronized.SynchronizedCollection<E> implements List<E> {

      private static final long serialVersionUID;


      public SynchronizedList(List<E> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      public void add(int var1, E var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            this.delegate().add(var1, var2);
         }
      }

      public boolean addAll(int var1, Collection<? extends E> var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            boolean var4 = this.delegate().addAll(var1, var2);
            return var4;
         }
      }

      protected List<E> delegate() {
         return (List)super.delegate();
      }

      public boolean equals(Object var1) {
         byte var2;
         if(var1 == this) {
            var2 = 1;
         } else {
            Object var3 = this.mutex;
            synchronized(var3) {
               var2 = this.delegate().equals(var1);
            }
         }

         return (boolean)var2;
      }

      public E get(int var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Object var3 = this.delegate().get(var1);
            return var3;
         }
      }

      public int hashCode() {
         Object var1 = this.mutex;
         synchronized(var1) {
            int var2 = this.delegate().hashCode();
            return var2;
         }
      }

      public int indexOf(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            int var3 = this.delegate().indexOf(var1);
            return var3;
         }
      }

      public int lastIndexOf(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            int var3 = this.delegate().lastIndexOf(var1);
            return var3;
         }
      }

      public ListIterator<E> listIterator() {
         return this.delegate().listIterator();
      }

      public ListIterator<E> listIterator(int var1) {
         return this.delegate().listIterator(var1);
      }

      public E remove(int var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Object var3 = this.delegate().remove(var1);
            return var3;
         }
      }

      public E set(int var1, E var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            Object var4 = this.delegate().set(var1, var2);
            return var4;
         }
      }

      @GwtIncompatible("List.subList")
      public List<E> subList(int var1, int var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            List var4 = Platform.subList(this.delegate(), var1, var2);
            Object var5 = this.mutex;
            List var6 = Synchronized.list(var4, var5);
            return var6;
         }
      }
   }

   static class SynchronizedSortedSet<E extends Object> extends Synchronized.SynchronizedSet<E> implements SortedSet<E> {

      private static final long serialVersionUID;


      public SynchronizedSortedSet(SortedSet<E> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      public Comparator<? super E> comparator() {
         Object var1 = this.mutex;
         synchronized(var1) {
            Comparator var2 = this.delegate().comparator();
            return var2;
         }
      }

      protected SortedSet<E> delegate() {
         return (SortedSet)super.delegate();
      }

      public E first() {
         Object var1 = this.mutex;
         synchronized(var1) {
            Object var2 = this.delegate().first();
            return var2;
         }
      }

      public SortedSet<E> headSet(E var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            SortedSet var3 = this.delegate().headSet(var1);
            Object var4 = this.mutex;
            SortedSet var5 = Synchronized.sortedSet(var3, var4);
            return var5;
         }
      }

      public E last() {
         Object var1 = this.mutex;
         synchronized(var1) {
            Object var2 = this.delegate().last();
            return var2;
         }
      }

      public SortedSet<E> subSet(E var1, E var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            SortedSet var4 = this.delegate().subSet(var1, var2);
            Object var5 = this.mutex;
            SortedSet var6 = Synchronized.sortedSet(var4, var5);
            return var6;
         }
      }

      public SortedSet<E> tailSet(E var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            SortedSet var3 = this.delegate().tailSet(var1);
            Object var4 = this.mutex;
            SortedSet var5 = Synchronized.sortedSet(var3, var4);
            return var5;
         }
      }
   }

   private static class SynchronizedSortedSetMultimap<K extends Object, V extends Object> extends Synchronized.SynchronizedSetMultimap<K, V> implements SortedSetMultimap<K, V> {

      private static final long serialVersionUID;


      SynchronizedSortedSetMultimap(SortedSetMultimap<K, V> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      protected SortedSetMultimap<K, V> delegate() {
         return (SortedSetMultimap)super.delegate();
      }

      public SortedSet<V> get(K var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            SortedSet var3 = this.delegate().get(var1);
            Object var4 = this.mutex;
            SortedSet var5 = Synchronized.sortedSet(var3, var4);
            return var5;
         }
      }

      public SortedSet<V> removeAll(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            SortedSet var3 = this.delegate().removeAll(var1);
            return var3;
         }
      }

      public SortedSet<V> replaceValues(K var1, Iterable<? extends V> var2) {
         Object var3 = this.mutex;
         synchronized(var3) {
            SortedSet var4 = this.delegate().replaceValues(var1, var2);
            return var4;
         }
      }

      public Comparator<? super V> valueComparator() {
         Object var1 = this.mutex;
         synchronized(var1) {
            Comparator var2 = this.delegate().valueComparator();
            return var2;
         }
      }
   }

   static class SynchronizedObject implements Serializable {

      private static final long serialVersionUID;
      private final Object delegate;
      protected final Object mutex;


      public SynchronizedObject(Object var1, @Nullable Object var2) {
         Object var3 = Preconditions.checkNotNull(var1);
         this.delegate = var3;
         if(var2 == null) {
            var2 = this;
         }

         this.mutex = var2;
      }

      private void writeObject(ObjectOutputStream var1) throws IOException {
         Object var2 = this.mutex;
         synchronized(var2) {
            var1.defaultWriteObject();
         }
      }

      protected Object delegate() {
         return this.delegate;
      }

      public String toString() {
         Object var1 = this.mutex;
         synchronized(var1) {
            String var2 = this.delegate.toString();
            return var2;
         }
      }
   }

   static class SynchronizedAsMapEntries<K extends Object, V extends Object> extends Synchronized.SynchronizedSet<Entry<K, Collection<V>>> {

      private static final long serialVersionUID;


      public SynchronizedAsMapEntries(Set<Entry<K, Collection<V>>> var1, @Nullable Object var2) {
         super(var1, var2);
      }

      public boolean contains(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = Maps.containsEntryImpl(this.delegate(), var1);
            return var3;
         }
      }

      public boolean containsAll(Collection<?> var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = Collections2.containsAll(this.delegate(), var1);
            return var3;
         }
      }

      public boolean equals(Object var1) {
         byte var2;
         if(var1 == this) {
            var2 = 1;
         } else {
            Object var3 = this.mutex;
            synchronized(var3) {
               var2 = Collections2.setEquals(this.delegate(), var1);
            }
         }

         return (boolean)var2;
      }

      public Iterator<Entry<K, Collection<V>>> iterator() {
         Iterator var1 = super.iterator();
         return new Synchronized.SynchronizedAsMapEntries.1(var1);
      }

      public boolean remove(Object var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = Maps.removeEntryImpl(this.delegate(), var1);
            return var3;
         }
      }

      public boolean removeAll(Collection<?> var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = Iterators.removeAll(this.delegate().iterator(), var1);
            return var3;
         }
      }

      public boolean retainAll(Collection<?> var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            boolean var3 = Iterators.retainAll(this.delegate().iterator(), var1);
            return var3;
         }
      }

      public Object[] toArray() {
         Object var1 = this.mutex;
         synchronized(var1) {
            Object[] var2 = ObjectArrays.toArrayImpl(this.delegate());
            return var2;
         }
      }

      public <T extends Object> T[] toArray(T[] var1) {
         Object var2 = this.mutex;
         synchronized(var2) {
            Object[] var3 = ObjectArrays.toArrayImpl(this.delegate(), var1);
            return var3;
         }
      }

      class 1 extends ForwardingIterator<Entry<K, Collection<V>>> {

         // $FF: synthetic field
         final Iterator val$iterator;


         1(Iterator var2) {
            this.val$iterator = var2;
         }

         protected Iterator<Entry<K, Collection<V>>> delegate() {
            return this.val$iterator;
         }

         public Entry<K, Collection<V>> next() {
            Entry var1 = (Entry)this.val$iterator.next();
            return new Synchronized.SynchronizedAsMapEntries.1.1(var1);
         }

         class 1 extends ForwardingMapEntry<K, Collection<V>> {

            // $FF: synthetic field
            final Entry val$entry;


            1(Entry var2) {
               this.val$entry = var2;
            }

            protected Entry<K, Collection<V>> delegate() {
               return this.val$entry;
            }

            public Collection<V> getValue() {
               Collection var1 = (Collection)this.val$entry.getValue();
               Object var2 = SynchronizedAsMapEntries.this.mutex;
               return Synchronized.typePreservingCollection(var1, var2);
            }
         }
      }
   }
}
