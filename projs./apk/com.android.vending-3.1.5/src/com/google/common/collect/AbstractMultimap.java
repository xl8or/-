package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Platform;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMultimap<K extends Object, V extends Object> implements Multimap<K, V>, Serializable {

   private static final long serialVersionUID = 2447537837011683357L;
   private transient Map<K, Collection<V>> asMap;
   private transient Collection<Entry<K, V>> entries;
   private transient Set<K> keySet;
   private transient Map<K, Collection<V>> map;
   private transient Multiset<K> multiset;
   private transient int totalSize;
   private transient Collection<V> valuesCollection;


   protected AbstractMultimap(Map<K, Collection<V>> var1) {
      Preconditions.checkArgument(var1.isEmpty());
      this.map = var1;
   }

   // $FF: synthetic method
   static int access$208(AbstractMultimap var0) {
      int var1 = var0.totalSize;
      int var2 = var1 + 1;
      var0.totalSize = var2;
      return var1;
   }

   // $FF: synthetic method
   static int access$210(AbstractMultimap var0) {
      int var1 = var0.totalSize;
      int var2 = var1 + -1;
      var0.totalSize = var2;
      return var1;
   }

   // $FF: synthetic method
   static int access$212(AbstractMultimap var0, int var1) {
      int var2 = var0.totalSize + var1;
      var0.totalSize = var2;
      return var2;
   }

   // $FF: synthetic method
   static int access$220(AbstractMultimap var0, int var1) {
      int var2 = var0.totalSize - var1;
      var0.totalSize = var2;
      return var2;
   }

   private Map<K, Collection<V>> createAsMap() {
      Object var2;
      if(this.map instanceof SortedMap) {
         SortedMap var1 = (SortedMap)this.map;
         var2 = new AbstractMultimap.SortedAsMap(var1);
      } else {
         Map var3 = this.map;
         var2 = new AbstractMultimap.AsMap(var3);
      }

      return (Map)var2;
   }

   private Collection<Entry<K, V>> createEntries() {
      Object var1;
      if(this instanceof SetMultimap) {
         var1 = new AbstractMultimap.EntrySet((AbstractMultimap.1)null);
      } else {
         var1 = new AbstractMultimap.Entries((AbstractMultimap.1)null);
      }

      return (Collection)var1;
   }

   private Set<K> createKeySet() {
      Object var2;
      if(this.map instanceof SortedMap) {
         SortedMap var1 = (SortedMap)this.map;
         var2 = new AbstractMultimap.SortedKeySet(var1);
      } else {
         Map var3 = this.map;
         var2 = new AbstractMultimap.KeySet(var3);
      }

      return (Set)var2;
   }

   private Collection<V> getOrCreateCollection(@Nullable K var1) {
      Collection var2 = (Collection)this.map.get(var1);
      if(var2 == null) {
         var2 = this.createCollection(var1);
         this.map.put(var1, var2);
      }

      return var2;
   }

   private Iterator<V> iteratorOrListIterator(Collection<V> var1) {
      Object var2;
      if(var1 instanceof List) {
         var2 = ((List)var1).listIterator();
      } else {
         var2 = var1.iterator();
      }

      return (Iterator)var2;
   }

   private int removeValuesForKey(Object var1) {
      int var2 = 0;

      Collection var3;
      try {
         var3 = (Collection)this.map.remove(var1);
      } catch (NullPointerException var7) {
         return var2;
      } catch (ClassCastException var8) {
         return var2;
      }

      var2 = 0;
      if(var3 != null) {
         var2 = var3.size();
         var3.clear();
         int var4 = this.totalSize - var2;
         this.totalSize = var4;
      }

      return var2;
   }

   private Collection<V> unmodifiableCollectionSubclass(Collection<V> var1) {
      Object var2;
      if(var1 instanceof SortedSet) {
         var2 = Collections.unmodifiableSortedSet((SortedSet)var1);
      } else if(var1 instanceof Set) {
         var2 = Collections.unmodifiableSet((Set)var1);
      } else if(var1 instanceof List) {
         var2 = Collections.unmodifiableList((List)var1);
      } else {
         var2 = Collections.unmodifiableCollection(var1);
      }

      return (Collection)var2;
   }

   private Collection<V> wrapCollection(@Nullable K var1, Collection<V> var2) {
      Object var4;
      if(var2 instanceof SortedSet) {
         SortedSet var3 = (SortedSet)var2;
         var4 = new AbstractMultimap.WrappedSortedSet(var1, var3, (AbstractMultimap.WrappedCollection)null);
      } else if(var2 instanceof Set) {
         Set var5 = (Set)var2;
         var4 = new AbstractMultimap.WrappedSet(var1, var5);
      } else if(var2 instanceof List) {
         List var6 = (List)var2;
         var4 = this.wrapList(var1, var6, (AbstractMultimap.WrappedCollection)null);
      } else {
         var4 = new AbstractMultimap.WrappedCollection(var1, var2, (AbstractMultimap.WrappedCollection)null);
      }

      return (Collection)var4;
   }

   private List<V> wrapList(K var1, List<V> var2, @Nullable AbstractMultimap.WrappedCollection var3) {
      Object var4;
      if(var2 instanceof RandomAccess) {
         var4 = new AbstractMultimap.RandomAccessWrappedList(var1, var2, var3);
      } else {
         var4 = new AbstractMultimap.WrappedList(var1, var2, var3);
      }

      return (List)var4;
   }

   public Map<K, Collection<V>> asMap() {
      Map var1 = this.asMap;
      if(var1 == null) {
         var1 = this.createAsMap();
         this.asMap = var1;
      }

      return var1;
   }

   Map<K, Collection<V>> backingMap() {
      return this.map;
   }

   public void clear() {
      Iterator var1 = this.map.values().iterator();

      while(var1.hasNext()) {
         ((Collection)var1.next()).clear();
      }

      this.map.clear();
      this.totalSize = 0;
   }

   public boolean containsEntry(@Nullable Object var1, @Nullable Object var2) {
      Collection var3 = (Collection)this.map.get(var1);
      boolean var4;
      if(var3 != null && var3.contains(var2)) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean containsKey(@Nullable Object var1) {
      return this.map.containsKey(var1);
   }

   public boolean containsValue(@Nullable Object var1) {
      Iterator var2 = this.map.values().iterator();

      boolean var3;
      while(true) {
         if(var2.hasNext()) {
            if(!((Collection)var2.next()).contains(var1)) {
               continue;
            }

            var3 = true;
            break;
         }

         var3 = false;
         break;
      }

      return var3;
   }

   abstract Collection<V> createCollection();

   Collection<V> createCollection(@Nullable K var1) {
      return this.createCollection();
   }

   Iterator<Entry<K, V>> createEntryIterator() {
      return new AbstractMultimap.EntryIterator();
   }

   public Collection<Entry<K, V>> entries() {
      Collection var1 = this.entries;
      if(this.entries == null) {
         var1 = this.createEntries();
         this.entries = var1;
      }

      return var1;
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 instanceof Multimap) {
         Multimap var3 = (Multimap)var1;
         Map var4 = this.map;
         Map var5 = var3.asMap();
         var2 = var4.equals(var5);
      } else {
         var2 = false;
      }

      return var2;
   }

   public Collection<V> get(@Nullable K var1) {
      Collection var2 = (Collection)this.map.get(var1);
      if(var2 == null) {
         var2 = this.createCollection(var1);
      }

      return this.wrapCollection(var1, var2);
   }

   public int hashCode() {
      return this.map.hashCode();
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.totalSize == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Set<K> keySet() {
      Set var1 = this.keySet;
      if(var1 == null) {
         var1 = this.createKeySet();
         this.keySet = var1;
      }

      return var1;
   }

   public Multiset<K> keys() {
      Object var1 = this.multiset;
      if(var1 == null) {
         var1 = new AbstractMultimap.MultisetView((AbstractMultimap.1)null);
         this.multiset = (Multiset)var1;
      }

      return (Multiset)var1;
   }

   public boolean put(@Nullable K var1, @Nullable V var2) {
      boolean var4;
      if(this.getOrCreateCollection(var1).add(var2)) {
         int var3 = this.totalSize + 1;
         this.totalSize = var3;
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean putAll(Multimap<? extends K, ? extends V> var1) {
      boolean var2 = false;

      boolean var7;
      for(Iterator var3 = var1.entries().iterator(); var3.hasNext(); var2 |= var7) {
         Entry var4 = (Entry)var3.next();
         Object var5 = var4.getKey();
         Object var6 = var4.getValue();
         var7 = this.put(var5, var6);
      }

      return var2;
   }

   public boolean putAll(@Nullable K var1, Iterable<? extends V> var2) {
      boolean var3;
      if(!var2.iterator().hasNext()) {
         var3 = false;
      } else {
         Collection var4 = this.getOrCreateCollection(var1);
         int var5 = var4.size();
         var3 = false;
         boolean var12;
         if(var2 instanceof Collection) {
            Collection var6 = (Collection)var2;
            var3 = var4.addAll(var6);
         } else {
            for(Iterator var10 = var2.iterator(); var10.hasNext(); var3 |= var12) {
               Object var11 = var10.next();
               var12 = var4.add(var11);
            }
         }

         int var7 = this.totalSize;
         int var8 = var4.size() - var5;
         int var9 = var7 + var8;
         this.totalSize = var9;
      }

      return var3;
   }

   public boolean remove(@Nullable Object var1, @Nullable Object var2) {
      Collection var3 = (Collection)this.map.get(var1);
      byte var4;
      if(var3 == null) {
         var4 = 0;
      } else {
         var4 = var3.remove(var2);
         if(var4 != 0) {
            int var5 = this.totalSize + -1;
            this.totalSize = var5;
            if(var3.isEmpty()) {
               this.map.remove(var1);
            }
         }
      }

      return (boolean)var4;
   }

   public Collection<V> removeAll(@Nullable Object var1) {
      Collection var2 = (Collection)this.map.remove(var1);
      Collection var3 = this.createCollection();
      if(var2 != null) {
         var3.addAll(var2);
         int var5 = this.totalSize;
         int var6 = var2.size();
         int var7 = var5 - var6;
         this.totalSize = var7;
         var2.clear();
      }

      return this.unmodifiableCollectionSubclass(var3);
   }

   public Collection<V> replaceValues(@Nullable K var1, Iterable<? extends V> var2) {
      Iterator var3 = var2.iterator();
      Collection var4;
      if(!var3.hasNext()) {
         var4 = this.removeAll(var1);
      } else {
         Collection var5 = this.getOrCreateCollection(var1);
         Collection var6 = this.createCollection();
         var6.addAll(var5);
         int var8 = this.totalSize;
         int var9 = var5.size();
         int var10 = var8 - var9;
         this.totalSize = var10;
         var5.clear();

         while(var3.hasNext()) {
            Object var11 = var3.next();
            if(var5.add(var11)) {
               int var12 = this.totalSize + 1;
               this.totalSize = var12;
            }
         }

         var4 = this.unmodifiableCollectionSubclass(var6);
      }

      return var4;
   }

   final void setMap(Map<K, Collection<V>> var1) {
      this.map = var1;
      this.totalSize = 0;

      int var7;
      for(Iterator var2 = var1.values().iterator(); var2.hasNext(); this.totalSize = var7) {
         Collection var3 = (Collection)var2.next();
         byte var4;
         if(!var3.isEmpty()) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         Preconditions.checkArgument((boolean)var4);
         int var5 = this.totalSize;
         int var6 = var3.size();
         var7 = var5 + var6;
      }

   }

   public int size() {
      return this.totalSize;
   }

   public String toString() {
      return this.map.toString();
   }

   public Collection<V> values() {
      Object var1 = this.valuesCollection;
      if(var1 == null) {
         var1 = new AbstractMultimap.Values((AbstractMultimap.1)null);
         this.valuesCollection = (Collection)var1;
      }

      return (Collection)var1;
   }

   private class AsMap extends AbstractMap<K, Collection<V>> {

      transient Set<Entry<K, Collection<V>>> entrySet;
      final transient Map<K, Collection<V>> submap;


      AsMap(Map var2) {
         this.submap = var2;
      }

      public boolean containsKey(Object var1) {
         return Maps.safeContainsKey(this.submap, var1);
      }

      public Set<Entry<K, Collection<V>>> entrySet() {
         Object var1 = this.entrySet;
         if(this.entrySet == null) {
            var1 = new AbstractMultimap.AsMap.AsMapEntries();
            this.entrySet = (Set)var1;
         }

         return (Set)var1;
      }

      public boolean equals(@Nullable Object var1) {
         boolean var2;
         if(this != var1 && !this.submap.equals(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public Collection<V> get(Object var1) {
         Collection var2 = (Collection)Maps.safeGet(this.submap, var1);
         Collection var3;
         if(var2 == null) {
            var3 = null;
         } else {
            var3 = AbstractMultimap.this.wrapCollection(var1, var2);
         }

         return var3;
      }

      public int hashCode() {
         return this.submap.hashCode();
      }

      public Set<K> keySet() {
         return AbstractMultimap.this.keySet();
      }

      public Collection<V> remove(Object var1) {
         Collection var2 = (Collection)this.submap.remove(var1);
         Collection var3;
         if(var2 == null) {
            var3 = null;
         } else {
            var3 = AbstractMultimap.this.createCollection();
            var3.addAll(var2);
            AbstractMultimap var5 = AbstractMultimap.this;
            int var6 = var2.size();
            AbstractMultimap.access$220(var5, var6);
            var2.clear();
         }

         return var3;
      }

      public String toString() {
         return this.submap.toString();
      }

      class AsMapEntries extends AbstractSet<Entry<K, Collection<V>>> {

         AsMapEntries() {}

         public boolean contains(Object var1) {
            return Collections2.safeContains(AsMap.this.submap.entrySet(), var1);
         }

         public Iterator<Entry<K, Collection<V>>> iterator() {
            AbstractMultimap.AsMap var1 = AsMap.this;
            return var1.new AsMapIterator();
         }

         public boolean remove(Object var1) {
            boolean var2;
            if(!this.contains(var1)) {
               var2 = false;
            } else {
               Entry var3 = (Entry)var1;
               AbstractMultimap var4 = AbstractMultimap.this;
               Object var5 = var3.getKey();
               var4.removeValuesForKey(var5);
               var2 = true;
            }

            return var2;
         }

         public int size() {
            return AsMap.this.submap.size();
         }
      }

      class AsMapIterator implements Iterator<Entry<K, Collection<V>>> {

         Collection<V> collection;
         final Iterator<Entry<K, Collection<V>>> delegateIterator;


         AsMapIterator() {
            Iterator var2 = AsMap.this.submap.entrySet().iterator();
            this.delegateIterator = var2;
         }

         public boolean hasNext() {
            return this.delegateIterator.hasNext();
         }

         public Entry<K, Collection<V>> next() {
            Entry var1 = (Entry)this.delegateIterator.next();
            Object var2 = var1.getKey();
            Collection var3 = (Collection)var1.getValue();
            this.collection = var3;
            AbstractMultimap var4 = AbstractMultimap.this;
            Collection var5 = this.collection;
            Collection var6 = var4.wrapCollection(var2, var5);
            return Maps.immutableEntry(var2, var6);
         }

         public void remove() {
            this.delegateIterator.remove();
            AbstractMultimap var1 = AbstractMultimap.this;
            int var2 = this.collection.size();
            AbstractMultimap.access$220(var1, var2);
            this.collection.clear();
         }
      }
   }

   private class KeySet extends AbstractSet<K> {

      final Map<K, Collection<V>> subMap;


      KeySet(Map var2) {
         this.subMap = var2;
      }

      public boolean contains(Object var1) {
         return this.subMap.containsKey(var1);
      }

      public boolean containsAll(Collection<?> var1) {
         return this.subMap.keySet().containsAll(var1);
      }

      public boolean equals(@Nullable Object var1) {
         boolean var2;
         if(this != var1 && !this.subMap.keySet().equals(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public int hashCode() {
         return this.subMap.keySet().hashCode();
      }

      public Iterator<K> iterator() {
         return new AbstractMultimap.KeySet.1();
      }

      public boolean remove(Object var1) {
         int var2 = 0;
         Collection var3 = (Collection)this.subMap.remove(var1);
         if(var3 != null) {
            var2 = var3.size();
            var3.clear();
            AbstractMultimap.access$220(AbstractMultimap.this, var2);
         }

         boolean var5;
         if(var2 > 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         return var5;
      }

      public int size() {
         return this.subMap.size();
      }

      class 1 implements Iterator<K> {

         Entry<K, Collection<V>> entry;
         final Iterator<Entry<K, Collection<V>>> entryIterator;


         1() {
            Iterator var2 = KeySet.this.subMap.entrySet().iterator();
            this.entryIterator = var2;
         }

         public boolean hasNext() {
            return this.entryIterator.hasNext();
         }

         public K next() {
            Entry var1 = (Entry)this.entryIterator.next();
            this.entry = var1;
            return this.entry.getKey();
         }

         public void remove() {
            byte var1;
            if(this.entry != null) {
               var1 = 1;
            } else {
               var1 = 0;
            }

            Preconditions.checkState((boolean)var1);
            Collection var2 = (Collection)this.entry.getValue();
            this.entryIterator.remove();
            AbstractMultimap var3 = AbstractMultimap.this;
            int var4 = var2.size();
            AbstractMultimap.access$220(var3, var4);
            var2.clear();
         }
      }
   }

   private class MultisetEntryIterator implements Iterator<Multiset.Entry<K>> {

      final Iterator<Entry<K, Collection<V>>> asMapIterator;


      private MultisetEntryIterator() {
         Iterator var2 = AbstractMultimap.this.asMap().entrySet().iterator();
         this.asMapIterator = var2;
      }

      // $FF: synthetic method
      MultisetEntryIterator(AbstractMultimap.1 var2) {
         this();
      }

      public boolean hasNext() {
         return this.asMapIterator.hasNext();
      }

      public Multiset.Entry<K> next() {
         AbstractMultimap var1 = AbstractMultimap.this;
         Entry var2 = (Entry)this.asMapIterator.next();
         return var1.new MultisetEntry(var2);
      }

      public void remove() {
         this.asMapIterator.remove();
      }
   }

   private class WrappedCollection extends AbstractCollection<V> {

      final AbstractMultimap.WrappedCollection ancestor;
      final Collection<V> ancestorDelegate;
      Collection<V> delegate;
      final K key;


      WrappedCollection(Object var2, @Nullable Collection var3, AbstractMultimap.WrappedCollection var4) {
         this.key = var2;
         this.delegate = var3;
         this.ancestor = var4;
         Collection var5;
         if(var4 == null) {
            var5 = null;
         } else {
            var5 = var4.getDelegate();
         }

         this.ancestorDelegate = var5;
      }

      public boolean add(V var1) {
         this.refreshIfEmpty();
         boolean var2 = this.delegate.isEmpty();
         boolean var3 = this.delegate.add(var1);
         if(var3) {
            int var4 = AbstractMultimap.access$208(AbstractMultimap.this);
            if(var2) {
               this.addToMap();
            }
         }

         return var3;
      }

      public boolean addAll(Collection<? extends V> var1) {
         byte var2;
         if(var1.isEmpty()) {
            var2 = 0;
         } else {
            int var3 = this.size();
            var2 = this.delegate.addAll(var1);
            if(var2 != 0) {
               int var4 = this.delegate.size();
               AbstractMultimap var5 = AbstractMultimap.this;
               int var6 = var4 - var3;
               AbstractMultimap.access$212(var5, var6);
               if(var3 == 0) {
                  this.addToMap();
               }
            }
         }

         return (boolean)var2;
      }

      void addToMap() {
         if(this.ancestor != null) {
            this.ancestor.addToMap();
         } else {
            Map var1 = AbstractMultimap.this.map;
            Object var2 = this.key;
            Collection var3 = this.delegate;
            var1.put(var2, var3);
         }
      }

      public void clear() {
         int var1 = this.size();
         if(var1 != 0) {
            this.delegate.clear();
            AbstractMultimap.access$220(AbstractMultimap.this, var1);
            this.removeIfEmpty();
         }
      }

      public boolean contains(Object var1) {
         this.refreshIfEmpty();
         return this.delegate.contains(var1);
      }

      public boolean containsAll(Collection<?> var1) {
         this.refreshIfEmpty();
         return this.delegate.containsAll(var1);
      }

      public boolean equals(@Nullable Object var1) {
         byte var2;
         if(var1 == this) {
            var2 = 1;
         } else {
            this.refreshIfEmpty();
            var2 = this.delegate.equals(var1);
         }

         return (boolean)var2;
      }

      AbstractMultimap.WrappedCollection getAncestor() {
         return this.ancestor;
      }

      Collection<V> getDelegate() {
         return this.delegate;
      }

      K getKey() {
         return this.key;
      }

      public int hashCode() {
         this.refreshIfEmpty();
         return this.delegate.hashCode();
      }

      public Iterator<V> iterator() {
         this.refreshIfEmpty();
         return new AbstractMultimap.WrappedCollection.WrappedIterator();
      }

      void refreshIfEmpty() {
         if(this.ancestor != null) {
            this.ancestor.refreshIfEmpty();
            Collection var1 = this.ancestor.getDelegate();
            Collection var2 = this.ancestorDelegate;
            if(var1 != var2) {
               throw new ConcurrentModificationException();
            }
         } else if(this.delegate.isEmpty()) {
            Map var3 = AbstractMultimap.this.map;
            Object var4 = this.key;
            Collection var5 = (Collection)var3.get(var4);
            if(var5 != null) {
               this.delegate = var5;
            }
         }
      }

      public boolean remove(Object var1) {
         this.refreshIfEmpty();
         boolean var2 = this.delegate.remove(var1);
         if(var2) {
            int var3 = AbstractMultimap.access$210(AbstractMultimap.this);
            this.removeIfEmpty();
         }

         return var2;
      }

      public boolean removeAll(Collection<?> var1) {
         byte var2;
         if(var1.isEmpty()) {
            var2 = 0;
         } else {
            int var3 = this.size();
            var2 = this.delegate.removeAll(var1);
            if(var2 != 0) {
               int var4 = this.delegate.size();
               AbstractMultimap var5 = AbstractMultimap.this;
               int var6 = var4 - var3;
               AbstractMultimap.access$212(var5, var6);
               this.removeIfEmpty();
            }
         }

         return (boolean)var2;
      }

      void removeIfEmpty() {
         if(this.ancestor != null) {
            this.ancestor.removeIfEmpty();
         } else if(this.delegate.isEmpty()) {
            Map var1 = AbstractMultimap.this.map;
            Object var2 = this.key;
            var1.remove(var2);
         }
      }

      public boolean retainAll(Collection<?> var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         int var3 = this.size();
         boolean var4 = this.delegate.retainAll(var1);
         if(var4) {
            int var5 = this.delegate.size();
            AbstractMultimap var6 = AbstractMultimap.this;
            int var7 = var5 - var3;
            AbstractMultimap.access$212(var6, var7);
            this.removeIfEmpty();
         }

         return var4;
      }

      public int size() {
         this.refreshIfEmpty();
         return this.delegate.size();
      }

      public String toString() {
         this.refreshIfEmpty();
         return this.delegate.toString();
      }

      class WrappedIterator implements Iterator<V> {

         final Iterator<V> delegateIterator;
         final Collection<V> originalDelegate;


         WrappedIterator() {
            Collection var2 = WrappedCollection.thisx.delegate;
            this.originalDelegate = var2;
            AbstractMultimap var3 = AbstractMultimap.this;
            Collection var4 = WrappedCollection.this.delegate;
            Iterator var5 = var3.iteratorOrListIterator(var4);
            this.delegateIterator = var5;
         }

         WrappedIterator(Iterator var2) {
            Collection var3 = WrappedCollection.this.delegate;
            this.originalDelegate = var3;
            this.delegateIterator = var2;
         }

         Iterator<V> getDelegateIterator() {
            this.validateIterator();
            return this.delegateIterator;
         }

         public boolean hasNext() {
            this.validateIterator();
            return this.delegateIterator.hasNext();
         }

         public V next() {
            this.validateIterator();
            return this.delegateIterator.next();
         }

         public void remove() {
            this.delegateIterator.remove();
            int var1 = AbstractMultimap.access$210(AbstractMultimap.this);
            WrappedCollection.this.removeIfEmpty();
         }

         void validateIterator() {
            WrappedCollection.this.refreshIfEmpty();
            Collection var1 = WrappedCollection.this.delegate;
            Collection var2 = this.originalDelegate;
            if(var1 != var2) {
               throw new ConcurrentModificationException();
            }
         }
      }
   }

   private class Entries extends AbstractCollection<Entry<K, V>> {

      private Entries() {}

      // $FF: synthetic method
      Entries(AbstractMultimap.1 var2) {
         this();
      }

      public void clear() {
         AbstractMultimap.this.clear();
      }

      public boolean contains(Object var1) {
         byte var2;
         if(!(var1 instanceof Entry)) {
            var2 = 0;
         } else {
            Entry var3 = (Entry)var1;
            AbstractMultimap var4 = AbstractMultimap.this;
            Object var5 = var3.getKey();
            Object var6 = var3.getValue();
            var2 = var4.containsEntry(var5, var6);
         }

         return (boolean)var2;
      }

      public Iterator<Entry<K, V>> iterator() {
         return AbstractMultimap.this.createEntryIterator();
      }

      public boolean remove(Object var1) {
         byte var2;
         if(!(var1 instanceof Entry)) {
            var2 = 0;
         } else {
            Entry var3 = (Entry)var1;
            AbstractMultimap var4 = AbstractMultimap.this;
            Object var5 = var3.getKey();
            Object var6 = var3.getValue();
            var2 = var4.remove(var5, var6);
         }

         return (boolean)var2;
      }

      public int size() {
         return AbstractMultimap.this.totalSize;
      }
   }

   private class WrappedSortedSet extends AbstractMultimap.WrappedCollection implements SortedSet<V> {

      WrappedSortedSet(Object var2, @Nullable SortedSet var3, AbstractMultimap.WrappedCollection var4) {
         super(var2, var3, var4);
      }

      public Comparator<? super V> comparator() {
         return this.getSortedSetDelegate().comparator();
      }

      public V first() {
         this.refreshIfEmpty();
         return this.getSortedSetDelegate().first();
      }

      SortedSet<V> getSortedSetDelegate() {
         return (SortedSet)this.getDelegate();
      }

      public SortedSet<V> headSet(V var1) {
         ((AbstractMultimap.WrappedSortedSet)this).refreshIfEmpty();
         AbstractMultimap.WrappedSortedSet var2 = new AbstractMultimap.WrappedSortedSet;
         AbstractMultimap var3 = AbstractMultimap.this;
         Object var4 = ((AbstractMultimap.WrappedSortedSet)this).getKey();
         SortedSet var5 = ((AbstractMultimap.WrappedSortedSet)this).getSortedSetDelegate().headSet(var1);
         if(((AbstractMultimap.WrappedSortedSet)this).getAncestor() != null) {
            this = ((AbstractMultimap.WrappedSortedSet)this).getAncestor();
         }

         var2.<init>(var4, var5, (AbstractMultimap.WrappedCollection)this);
         return var2;
      }

      public V last() {
         this.refreshIfEmpty();
         return this.getSortedSetDelegate().last();
      }

      public SortedSet<V> subSet(V var1, V var2) {
         ((AbstractMultimap.WrappedSortedSet)this).refreshIfEmpty();
         AbstractMultimap.WrappedSortedSet var3 = new AbstractMultimap.WrappedSortedSet;
         AbstractMultimap var4 = AbstractMultimap.this;
         Object var5 = ((AbstractMultimap.WrappedSortedSet)this).getKey();
         SortedSet var6 = ((AbstractMultimap.WrappedSortedSet)this).getSortedSetDelegate().subSet(var1, var2);
         if(((AbstractMultimap.WrappedSortedSet)this).getAncestor() != null) {
            this = ((AbstractMultimap.WrappedSortedSet)this).getAncestor();
         }

         var3.<init>(var5, var6, (AbstractMultimap.WrappedCollection)this);
         return var3;
      }

      public SortedSet<V> tailSet(V var1) {
         ((AbstractMultimap.WrappedSortedSet)this).refreshIfEmpty();
         AbstractMultimap.WrappedSortedSet var2 = new AbstractMultimap.WrappedSortedSet;
         AbstractMultimap var3 = AbstractMultimap.this;
         Object var4 = ((AbstractMultimap.WrappedSortedSet)this).getKey();
         SortedSet var5 = ((AbstractMultimap.WrappedSortedSet)this).getSortedSetDelegate().tailSet(var1);
         if(((AbstractMultimap.WrappedSortedSet)this).getAncestor() != null) {
            this = ((AbstractMultimap.WrappedSortedSet)this).getAncestor();
         }

         var2.<init>(var4, var5, (AbstractMultimap.WrappedCollection)this);
         return var2;
      }
   }

   private class SortedAsMap extends AbstractMultimap.AsMap implements SortedMap<K, Collection<V>> {

      SortedSet<K> sortedKeySet;


      SortedAsMap(SortedMap var2) {
         super(var2);
      }

      public Comparator<? super K> comparator() {
         return this.sortedMap().comparator();
      }

      public K firstKey() {
         return this.sortedMap().firstKey();
      }

      public SortedMap<K, Collection<V>> headMap(K var1) {
         AbstractMultimap var2 = AbstractMultimap.this;
         SortedMap var3 = this.sortedMap().headMap(var1);
         return var2.new SortedAsMap(var3);
      }

      public SortedSet<K> keySet() {
         Object var1 = this.sortedKeySet;
         if(var1 == null) {
            AbstractMultimap var2 = AbstractMultimap.this;
            SortedMap var3 = this.sortedMap();
            var1 = var2.new SortedKeySet(var3);
            this.sortedKeySet = (SortedSet)var1;
         }

         return (SortedSet)var1;
      }

      public K lastKey() {
         return this.sortedMap().lastKey();
      }

      SortedMap<K, Collection<V>> sortedMap() {
         return (SortedMap)this.submap;
      }

      public SortedMap<K, Collection<V>> subMap(K var1, K var2) {
         AbstractMultimap var3 = AbstractMultimap.this;
         SortedMap var4 = this.sortedMap().subMap(var1, var2);
         return var3.new SortedAsMap(var4);
      }

      public SortedMap<K, Collection<V>> tailMap(K var1) {
         AbstractMultimap var2 = AbstractMultimap.this;
         SortedMap var3 = this.sortedMap().tailMap(var1);
         return var2.new SortedAsMap(var3);
      }
   }

   private class SortedKeySet extends AbstractMultimap.KeySet implements SortedSet<K> {

      SortedKeySet(SortedMap var2) {
         super(var2);
      }

      public Comparator<? super K> comparator() {
         return this.sortedMap().comparator();
      }

      public K first() {
         return this.sortedMap().firstKey();
      }

      public SortedSet<K> headSet(K var1) {
         AbstractMultimap var2 = AbstractMultimap.this;
         SortedMap var3 = this.sortedMap().headMap(var1);
         return var2.new SortedKeySet(var3);
      }

      public K last() {
         return this.sortedMap().lastKey();
      }

      SortedMap<K, Collection<V>> sortedMap() {
         return (SortedMap)this.subMap;
      }

      public SortedSet<K> subSet(K var1, K var2) {
         AbstractMultimap var3 = AbstractMultimap.this;
         SortedMap var4 = this.sortedMap().subMap(var1, var2);
         return var3.new SortedKeySet(var4);
      }

      public SortedSet<K> tailSet(K var1) {
         AbstractMultimap var2 = AbstractMultimap.this;
         SortedMap var3 = this.sortedMap().tailMap(var1);
         return var2.new SortedKeySet(var3);
      }
   }

   private class MultisetView extends AbstractMultiset<K> {

      transient Set<Multiset.Entry<K>> entrySet;


      private MultisetView() {}

      // $FF: synthetic method
      MultisetView(AbstractMultimap.1 var2) {
         this();
      }

      public void clear() {
         AbstractMultimap.this.clear();
      }

      public int count(Object param1) {
         // $FF: Couldn't be decompiled
      }

      public Set<K> elementSet() {
         return AbstractMultimap.this.keySet();
      }

      public Set<Multiset.Entry<K>> entrySet() {
         Object var1 = this.entrySet;
         if(var1 == null) {
            var1 = new AbstractMultimap.MultisetView.EntrySet((AbstractMultimap.1)null);
            this.entrySet = (Set)var1;
         }

         return (Set)var1;
      }

      public Iterator<K> iterator() {
         AbstractMultimap var1 = AbstractMultimap.this;
         return var1.new MultisetKeyIterator((AbstractMultimap.1)null);
      }

      public int remove(Object var1, int var2) {
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

            Preconditions.checkArgument((boolean)var4);

            Collection var5;
            try {
               var5 = (Collection)AbstractMultimap.this.map.get(var1);
            } catch (NullPointerException var13) {
               return var3;
            } catch (ClassCastException var14) {
               return var3;
            }

            if(var5 != null) {
               int var6 = var5.size();
               if(var2 >= var6) {
                  var3 = AbstractMultimap.this.removeValuesForKey(var1);
               } else {
                  Iterator var9 = var5.iterator();

                  for(int var10 = 0; var10 < var2; ++var10) {
                     Object var11 = var9.next();
                     var9.remove();
                  }

                  AbstractMultimap.access$220(AbstractMultimap.this, var2);
                  var3 = var6;
               }
            }
         }

         return var3;
      }

      public int size() {
         return AbstractMultimap.this.totalSize;
      }

      private class EntrySet extends AbstractSet<Multiset.Entry<K>> {

         private EntrySet() {}

         // $FF: synthetic method
         EntrySet(AbstractMultimap.1 var2) {
            this();
         }

         public void clear() {
            AbstractMultimap.this.clear();
         }

         public boolean contains(Object var1) {
            boolean var2 = false;
            if(var1 instanceof Multiset.Entry) {
               Multiset.Entry var3 = (Multiset.Entry)var1;
               Map var4 = AbstractMultimap.this.map;
               Object var5 = var3.getElement();
               Collection var6 = (Collection)var4.get(var5);
               if(var6 != null) {
                  int var7 = var6.size();
                  int var8 = var3.getCount();
                  if(var7 == var8) {
                     var2 = true;
                  }
               }
            }

            return var2;
         }

         public Iterator<Multiset.Entry<K>> iterator() {
            AbstractMultimap var1 = AbstractMultimap.this;
            return var1.new MultisetEntryIterator((AbstractMultimap.1)null);
         }

         public boolean remove(Object var1) {
            boolean var4;
            if(this.contains(var1)) {
               AbstractMultimap var2 = AbstractMultimap.this;
               Object var3 = ((Multiset.Entry)var1).getElement();
               if(var2.removeValuesForKey(var3) > 0) {
                  var4 = true;
                  return var4;
               }
            }

            var4 = false;
            return var4;
         }

         public int size() {
            return AbstractMultimap.this.map.size();
         }
      }
   }

   private class WrappedList extends AbstractMultimap.WrappedCollection implements List<V> {

      WrappedList(Object var2, @Nullable List var3, AbstractMultimap.WrappedCollection var4) {
         super(var2, var3, var4);
      }

      public void add(int var1, V var2) {
         this.refreshIfEmpty();
         boolean var3 = this.getDelegate().isEmpty();
         this.getListDelegate().add(var1, var2);
         int var4 = AbstractMultimap.access$208(AbstractMultimap.this);
         if(var3) {
            this.addToMap();
         }
      }

      public boolean addAll(int var1, Collection<? extends V> var2) {
         byte var3;
         if(var2.isEmpty()) {
            var3 = 0;
         } else {
            int var4 = this.size();
            var3 = this.getListDelegate().addAll(var1, var2);
            if(var3 != 0) {
               int var5 = this.getDelegate().size();
               AbstractMultimap var6 = AbstractMultimap.this;
               int var7 = var5 - var4;
               AbstractMultimap.access$212(var6, var7);
               if(var4 == 0) {
                  this.addToMap();
               }
            }
         }

         return (boolean)var3;
      }

      public V get(int var1) {
         this.refreshIfEmpty();
         return this.getListDelegate().get(var1);
      }

      List<V> getListDelegate() {
         return (List)this.getDelegate();
      }

      public int indexOf(Object var1) {
         this.refreshIfEmpty();
         return this.getListDelegate().indexOf(var1);
      }

      public int lastIndexOf(Object var1) {
         this.refreshIfEmpty();
         return this.getListDelegate().lastIndexOf(var1);
      }

      public ListIterator<V> listIterator() {
         this.refreshIfEmpty();
         return new AbstractMultimap.WrappedList.WrappedListIterator();
      }

      public ListIterator<V> listIterator(int var1) {
         this.refreshIfEmpty();
         return new AbstractMultimap.WrappedList.WrappedListIterator(var1);
      }

      public V remove(int var1) {
         this.refreshIfEmpty();
         Object var2 = this.getListDelegate().remove(var1);
         int var3 = AbstractMultimap.access$210(AbstractMultimap.this);
         this.removeIfEmpty();
         return var2;
      }

      public V set(int var1, V var2) {
         this.refreshIfEmpty();
         return this.getListDelegate().set(var1, var2);
      }

      @GwtIncompatible("List.subList")
      public List<V> subList(int var1, int var2) {
         ((AbstractMultimap.WrappedList)this).refreshIfEmpty();
         AbstractMultimap var3 = AbstractMultimap.this;
         Object var4 = ((AbstractMultimap.WrappedList)this).getKey();
         List var5 = Platform.subList(((AbstractMultimap.WrappedList)this).getListDelegate(), var1, var2);
         if(((AbstractMultimap.WrappedList)this).getAncestor() != null) {
            this = ((AbstractMultimap.WrappedList)this).getAncestor();
         }

         return var3.wrapList(var4, var5, (AbstractMultimap.WrappedCollection)this);
      }

      private class WrappedListIterator extends AbstractMultimap.WrappedCollection.WrappedIterator implements ListIterator<V> {

         WrappedListIterator() {
            super();
         }

         public WrappedListIterator(int var2) {
            ListIterator var3 = WrappedList.this.getListDelegate().listIterator(var2);
            super(var3);
         }

         private ListIterator<V> getDelegateListIterator() {
            return (ListIterator)this.getDelegateIterator();
         }

         public void add(V var1) {
            boolean var2 = WrappedList.this.isEmpty();
            this.getDelegateListIterator().add(var1);
            int var3 = AbstractMultimap.access$208(AbstractMultimap.this);
            if(var2) {
               WrappedList.this.addToMap();
            }
         }

         public boolean hasPrevious() {
            return this.getDelegateListIterator().hasPrevious();
         }

         public int nextIndex() {
            return this.getDelegateListIterator().nextIndex();
         }

         public V previous() {
            return this.getDelegateListIterator().previous();
         }

         public int previousIndex() {
            return this.getDelegateListIterator().previousIndex();
         }

         public void set(V var1) {
            this.getDelegateListIterator().set(var1);
         }
      }
   }

   private class ValueIterator implements Iterator<V> {

      final Iterator<Entry<K, V>> entryIterator;


      private ValueIterator() {
         Iterator var2 = AbstractMultimap.this.createEntryIterator();
         this.entryIterator = var2;
      }

      // $FF: synthetic method
      ValueIterator(AbstractMultimap.1 var2) {
         this();
      }

      public boolean hasNext() {
         return this.entryIterator.hasNext();
      }

      public V next() {
         return ((Entry)this.entryIterator.next()).getValue();
      }

      public void remove() {
         this.entryIterator.remove();
      }
   }

   private class EntryIterator implements Iterator<Entry<K, V>> {

      Collection<V> collection;
      K key;
      final Iterator<Entry<K, Collection<V>>> keyIterator;
      Iterator<V> valueIterator;


      EntryIterator() {
         Iterator var2 = AbstractMultimap.this.map.entrySet().iterator();
         this.keyIterator = var2;
         if(this.keyIterator.hasNext()) {
            this.findValueIteratorAndKey();
         } else {
            Iterator var3 = Iterators.emptyModifiableIterator();
            this.valueIterator = var3;
         }
      }

      void findValueIteratorAndKey() {
         Entry var1 = (Entry)this.keyIterator.next();
         Object var2 = var1.getKey();
         this.key = var2;
         Collection var3 = (Collection)var1.getValue();
         this.collection = var3;
         Iterator var4 = this.collection.iterator();
         this.valueIterator = var4;
      }

      public boolean hasNext() {
         boolean var1;
         if(!this.keyIterator.hasNext() && !this.valueIterator.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Entry<K, V> next() {
         if(!this.valueIterator.hasNext()) {
            this.findValueIteratorAndKey();
         }

         Object var1 = this.key;
         Object var2 = this.valueIterator.next();
         return Maps.immutableEntry(var1, var2);
      }

      public void remove() {
         this.valueIterator.remove();
         if(this.collection.isEmpty()) {
            this.keyIterator.remove();
         }

         int var1 = AbstractMultimap.access$210(AbstractMultimap.this);
      }
   }

   private class MultisetKeyIterator implements Iterator<K> {

      final Iterator<Entry<K, V>> entryIterator;


      private MultisetKeyIterator() {
         Iterator var2 = AbstractMultimap.this.entries().iterator();
         this.entryIterator = var2;
      }

      // $FF: synthetic method
      MultisetKeyIterator(AbstractMultimap.1 var2) {
         this();
      }

      public boolean hasNext() {
         return this.entryIterator.hasNext();
      }

      public K next() {
         return ((Entry)this.entryIterator.next()).getKey();
      }

      public void remove() {
         this.entryIterator.remove();
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class MultisetEntry extends Multisets.AbstractEntry<K> {

      final Entry<K, Collection<V>> entry;


      public MultisetEntry(Entry var2) {
         this.entry = var2;
      }

      public int getCount() {
         return ((Collection)this.entry.getValue()).size();
      }

      public K getElement() {
         return this.entry.getKey();
      }
   }

   private class WrappedSet extends AbstractMultimap.WrappedCollection implements Set<V> {

      WrappedSet(Object var2, Set var3) {
         super(var2, var3, (AbstractMultimap.WrappedCollection)null);
      }
   }

   private class RandomAccessWrappedList extends AbstractMultimap.WrappedList implements RandomAccess {

      RandomAccessWrappedList(Object var2, @Nullable List var3, AbstractMultimap.WrappedCollection var4) {
         super(var2, var3, var4);
      }
   }

   private class EntrySet extends AbstractMultimap.Entries implements Set<Entry<K, V>> {

      private EntrySet() {
         super((AbstractMultimap.1)null);
      }

      // $FF: synthetic method
      EntrySet(AbstractMultimap.1 var2) {
         this();
      }

      public boolean equals(@Nullable Object var1) {
         return Collections2.setEquals(this, var1);
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }
   }

   private class Values extends AbstractCollection<V> {

      private Values() {}

      // $FF: synthetic method
      Values(AbstractMultimap.1 var2) {
         this();
      }

      public void clear() {
         AbstractMultimap.this.clear();
      }

      public boolean contains(Object var1) {
         return AbstractMultimap.this.containsValue(var1);
      }

      public Iterator<V> iterator() {
         AbstractMultimap var1 = AbstractMultimap.this;
         return var1.new ValueIterator((AbstractMultimap.1)null);
      }

      public int size() {
         return AbstractMultimap.this.totalSize;
      }
   }
}
