package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractListMultimap;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.AbstractSortedSetMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.Synchronized;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public final class Multimaps {

   private Multimaps() {}

   public static <K extends Object, V extends Object> SetMultimap<K, V> forMap(Map<K, V> var0) {
      return new Multimaps.MapMultimap(var0);
   }

   public static <K extends Object, V extends Object> ImmutableListMultimap<K, V> index(Iterable<V> var0, Function<? super V, K> var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      ImmutableListMultimap.Builder var3 = ImmutableListMultimap.builder();
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         Preconditions.checkNotNull(var5, var0);
         Object var7 = var1.apply(var5);
         var3.put(var7, var5);
      }

      return var3.build();
   }

   public static <K extends Object, V extends Object, M extends Object & Multimap<K, V>> M invertFrom(Multimap<? extends V, ? extends K> var0, M var1) {
      Iterator var2 = var0.entries().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         Object var4 = var3.getValue();
         Object var5 = var3.getKey();
         var1.put(var4, var5);
      }

      return var1;
   }

   public static <K extends Object, V extends Object> ListMultimap<K, V> newListMultimap(Map<K, Collection<V>> var0, Supplier<? extends List<V>> var1) {
      return new Multimaps.CustomListMultimap(var0, var1);
   }

   public static <K extends Object, V extends Object> Multimap<K, V> newMultimap(Map<K, Collection<V>> var0, Supplier<? extends Collection<V>> var1) {
      return new Multimaps.CustomMultimap(var0, var1);
   }

   public static <K extends Object, V extends Object> SetMultimap<K, V> newSetMultimap(Map<K, Collection<V>> var0, Supplier<? extends Set<V>> var1) {
      return new Multimaps.CustomSetMultimap(var0, var1);
   }

   public static <K extends Object, V extends Object> SortedSetMultimap<K, V> newSortedSetMultimap(Map<K, Collection<V>> var0, Supplier<? extends SortedSet<V>> var1) {
      return new Multimaps.CustomSortedSetMultimap(var0, var1);
   }

   public static <K extends Object, V extends Object> ListMultimap<K, V> synchronizedListMultimap(ListMultimap<K, V> var0) {
      return Synchronized.listMultimap(var0, (Object)null);
   }

   public static <K extends Object, V extends Object> Multimap<K, V> synchronizedMultimap(Multimap<K, V> var0) {
      return Synchronized.multimap(var0, (Object)null);
   }

   public static <K extends Object, V extends Object> SetMultimap<K, V> synchronizedSetMultimap(SetMultimap<K, V> var0) {
      return Synchronized.setMultimap(var0, (Object)null);
   }

   public static <K extends Object, V extends Object> SortedSetMultimap<K, V> synchronizedSortedSetMultimap(SortedSetMultimap<K, V> var0) {
      return Synchronized.sortedSetMultimap(var0, (Object)null);
   }

   private static <K extends Object, V extends Object> Set<Entry<K, Collection<V>>> unmodifiableAsMapEntries(Set<Entry<K, Collection<V>>> var0) {
      Set var1 = Collections.unmodifiableSet(var0);
      return new Multimaps.UnmodifiableAsMapEntries(var1);
   }

   private static <K extends Object, V extends Object> Entry<K, Collection<V>> unmodifiableAsMapEntry(Entry<K, Collection<V>> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Multimaps.1(var0);
   }

   private static <K extends Object, V extends Object> Collection<Entry<K, V>> unmodifiableEntries(Collection<Entry<K, V>> var0) {
      Object var1;
      if(var0 instanceof Set) {
         var1 = Maps.unmodifiableEntrySet((Set)var0);
      } else {
         Collection var2 = Collections.unmodifiableCollection(var0);
         var1 = new Maps.UnmodifiableEntries(var2);
      }

      return (Collection)var1;
   }

   public static <K extends Object, V extends Object> ListMultimap<K, V> unmodifiableListMultimap(ListMultimap<K, V> var0) {
      return new Multimaps.UnmodifiableListMultimap(var0);
   }

   public static <K extends Object, V extends Object> Multimap<K, V> unmodifiableMultimap(Multimap<K, V> var0) {
      return new Multimaps.UnmodifiableMultimap(var0);
   }

   public static <K extends Object, V extends Object> SetMultimap<K, V> unmodifiableSetMultimap(SetMultimap<K, V> var0) {
      return new Multimaps.UnmodifiableSetMultimap(var0);
   }

   public static <K extends Object, V extends Object> SortedSetMultimap<K, V> unmodifiableSortedSetMultimap(SortedSetMultimap<K, V> var0) {
      return new Multimaps.UnmodifiableSortedSetMultimap(var0);
   }

   private static <V extends Object> Collection<V> unmodifiableValueCollection(Collection<V> var0) {
      Object var1;
      if(var0 instanceof SortedSet) {
         var1 = Collections.unmodifiableSortedSet((SortedSet)var0);
      } else if(var0 instanceof Set) {
         var1 = Collections.unmodifiableSet((Set)var0);
      } else if(var0 instanceof List) {
         var1 = Collections.unmodifiableList((List)var0);
      } else {
         var1 = Collections.unmodifiableCollection(var0);
      }

      return (Collection)var1;
   }

   private static class UnmodifiableAsMapValues<V extends Object> extends ForwardingCollection<Collection<V>> {

      final Collection<Collection<V>> delegate;


      UnmodifiableAsMapValues(Collection<Collection<V>> var1) {
         Collection var2 = Collections.unmodifiableCollection(var1);
         this.delegate = var2;
      }

      public boolean contains(Object var1) {
         return Iterators.contains(this.iterator(), var1);
      }

      public boolean containsAll(Collection<?> var1) {
         return Collections2.containsAll(this, var1);
      }

      protected Collection<Collection<V>> delegate() {
         return this.delegate;
      }

      public Iterator<Collection<V>> iterator() {
         Iterator var1 = this.delegate.iterator();
         return new Multimaps.UnmodifiableAsMapValues.1(var1);
      }

      public Object[] toArray() {
         return ObjectArrays.toArrayImpl(this);
      }

      public <T extends Object> T[] toArray(T[] var1) {
         return ObjectArrays.toArrayImpl(this, var1);
      }

      class 1 implements Iterator<Collection<V>> {

         // $FF: synthetic field
         final Iterator val$iterator;


         1(Iterator var2) {
            this.val$iterator = var2;
         }

         public boolean hasNext() {
            return this.val$iterator.hasNext();
         }

         public Collection<V> next() {
            return Multimaps.unmodifiableValueCollection((Collection)this.val$iterator.next());
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      }
   }

   private static class CustomSetMultimap<K extends Object, V extends Object> extends AbstractSetMultimap<K, V> {

      private static final long serialVersionUID;
      transient Supplier<? extends Set<V>> factory;


      CustomSetMultimap(Map<K, Collection<V>> var1, Supplier<? extends Set<V>> var2) {
         super(var1);
         Supplier var3 = (Supplier)Preconditions.checkNotNull(var2);
         this.factory = var3;
      }

      private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         var1.defaultReadObject();
         Supplier var2 = (Supplier)var1.readObject();
         this.factory = var2;
         Map var3 = (Map)var1.readObject();
         this.setMap(var3);
      }

      private void writeObject(ObjectOutputStream var1) throws IOException {
         var1.defaultWriteObject();
         Supplier var2 = this.factory;
         var1.writeObject(var2);
         Map var3 = this.backingMap();
         var1.writeObject(var3);
      }

      protected Set<V> createCollection() {
         return (Set)this.factory.get();
      }
   }

   private static class CustomListMultimap<K extends Object, V extends Object> extends AbstractListMultimap<K, V> {

      private static final long serialVersionUID;
      transient Supplier<? extends List<V>> factory;


      CustomListMultimap(Map<K, Collection<V>> var1, Supplier<? extends List<V>> var2) {
         super(var1);
         Supplier var3 = (Supplier)Preconditions.checkNotNull(var2);
         this.factory = var3;
      }

      private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         var1.defaultReadObject();
         Supplier var2 = (Supplier)var1.readObject();
         this.factory = var2;
         Map var3 = (Map)var1.readObject();
         this.setMap(var3);
      }

      private void writeObject(ObjectOutputStream var1) throws IOException {
         var1.defaultWriteObject();
         Supplier var2 = this.factory;
         var1.writeObject(var2);
         Map var3 = this.backingMap();
         var1.writeObject(var3);
      }

      protected List<V> createCollection() {
         return (List)this.factory.get();
      }
   }

   private static class CustomMultimap<K extends Object, V extends Object> extends AbstractMultimap<K, V> {

      private static final long serialVersionUID;
      transient Supplier<? extends Collection<V>> factory;


      CustomMultimap(Map<K, Collection<V>> var1, Supplier<? extends Collection<V>> var2) {
         super(var1);
         Supplier var3 = (Supplier)Preconditions.checkNotNull(var2);
         this.factory = var3;
      }

      private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         var1.defaultReadObject();
         Supplier var2 = (Supplier)var1.readObject();
         this.factory = var2;
         Map var3 = (Map)var1.readObject();
         this.setMap(var3);
      }

      private void writeObject(ObjectOutputStream var1) throws IOException {
         var1.defaultWriteObject();
         Supplier var2 = this.factory;
         var1.writeObject(var2);
         Map var3 = this.backingMap();
         var1.writeObject(var3);
      }

      protected Collection<V> createCollection() {
         return (Collection)this.factory.get();
      }
   }

   private static class UnmodifiableSetMultimap<K extends Object, V extends Object> extends Multimaps.UnmodifiableMultimap<K, V> implements SetMultimap<K, V> {

      private static final long serialVersionUID;


      UnmodifiableSetMultimap(SetMultimap<K, V> var1) {
         super(var1);
      }

      public SetMultimap<K, V> delegate() {
         return (SetMultimap)super.delegate();
      }

      public Set<Entry<K, V>> entries() {
         return Maps.unmodifiableEntrySet(this.delegate().entries());
      }

      public Set<V> get(K var1) {
         return Collections.unmodifiableSet(this.delegate().get(var1));
      }

      public Set<V> removeAll(Object var1) {
         throw new UnsupportedOperationException();
      }

      public Set<V> replaceValues(K var1, Iterable<? extends V> var2) {
         throw new UnsupportedOperationException();
      }
   }

   private static class UnmodifiableMultimap<K extends Object, V extends Object> extends ForwardingMultimap<K, V> implements Serializable {

      private static final long serialVersionUID;
      final Multimap<K, V> delegate;
      transient Collection<Entry<K, V>> entries;
      transient Set<K> keySet;
      transient Multiset<K> keys;
      transient Map<K, Collection<V>> map;
      transient Collection<V> values;


      UnmodifiableMultimap(Multimap<K, V> var1) {
         this.delegate = var1;
      }

      public Map<K, Collection<V>> asMap() {
         Object var1 = this.map;
         if(var1 == null) {
            Map var2 = Collections.unmodifiableMap(this.delegate.asMap());
            var1 = new Multimaps.UnmodifiableMultimap.1(var2);
            this.map = (Map)var1;
         }

         return (Map)var1;
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      protected Multimap<K, V> delegate() {
         return this.delegate;
      }

      public Collection<Entry<K, V>> entries() {
         Collection var1 = this.entries;
         if(var1 == null) {
            var1 = Multimaps.unmodifiableEntries(this.delegate.entries());
            this.entries = var1;
         }

         return var1;
      }

      public Collection<V> get(K var1) {
         return Multimaps.unmodifiableValueCollection(this.delegate.get(var1));
      }

      public Set<K> keySet() {
         Set var1 = this.keySet;
         if(var1 == null) {
            var1 = Collections.unmodifiableSet(this.delegate.keySet());
            this.keySet = var1;
         }

         return var1;
      }

      public Multiset<K> keys() {
         Multiset var1 = this.keys;
         if(var1 == null) {
            var1 = Multisets.unmodifiableMultiset(this.delegate.keys());
            this.keys = var1;
         }

         return var1;
      }

      public boolean put(K var1, V var2) {
         throw new UnsupportedOperationException();
      }

      public boolean putAll(Multimap<? extends K, ? extends V> var1) {
         throw new UnsupportedOperationException();
      }

      public boolean putAll(K var1, Iterable<? extends V> var2) {
         throw new UnsupportedOperationException();
      }

      public boolean remove(Object var1, Object var2) {
         throw new UnsupportedOperationException();
      }

      public Collection<V> removeAll(Object var1) {
         throw new UnsupportedOperationException();
      }

      public Collection<V> replaceValues(K var1, Iterable<? extends V> var2) {
         throw new UnsupportedOperationException();
      }

      public Collection<V> values() {
         Collection var1 = this.values;
         if(var1 == null) {
            var1 = Collections.unmodifiableCollection(this.delegate.values());
            this.values = var1;
         }

         return var1;
      }

      class 1 extends ForwardingMap<K, Collection<V>> {

         Collection<Collection<V>> asMapValues;
         Set<Entry<K, Collection<V>>> entrySet;
         // $FF: synthetic field
         final Map val$unmodifiableMap;


         1(Map var2) {
            this.val$unmodifiableMap = var2;
         }

         public boolean containsValue(Object var1) {
            return this.values().contains(var1);
         }

         protected Map<K, Collection<V>> delegate() {
            return this.val$unmodifiableMap;
         }

         public Set<Entry<K, Collection<V>>> entrySet() {
            Set var1 = this.entrySet;
            if(var1 == null) {
               var1 = Multimaps.unmodifiableAsMapEntries(this.val$unmodifiableMap.entrySet());
               this.entrySet = var1;
            }

            return var1;
         }

         public Collection<V> get(Object var1) {
            Collection var2 = (Collection)this.val$unmodifiableMap.get(var1);
            Collection var3;
            if(var2 == null) {
               var3 = null;
            } else {
               var3 = Multimaps.unmodifiableValueCollection(var2);
            }

            return var3;
         }

         public Collection<Collection<V>> values() {
            Object var1 = this.asMapValues;
            if(var1 == null) {
               Collection var2 = this.val$unmodifiableMap.values();
               var1 = new Multimaps.UnmodifiableAsMapValues(var2);
               this.asMapValues = (Collection)var1;
            }

            return (Collection)var1;
         }
      }
   }

   private static class CustomSortedSetMultimap<K extends Object, V extends Object> extends AbstractSortedSetMultimap<K, V> {

      private static final long serialVersionUID;
      transient Supplier<? extends SortedSet<V>> factory;
      transient Comparator<? super V> valueComparator;


      CustomSortedSetMultimap(Map<K, Collection<V>> var1, Supplier<? extends SortedSet<V>> var2) {
         super(var1);
         Supplier var3 = (Supplier)Preconditions.checkNotNull(var2);
         this.factory = var3;
         Comparator var4 = ((SortedSet)var2.get()).comparator();
         this.valueComparator = var4;
      }

      private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         var1.defaultReadObject();
         Supplier var2 = (Supplier)var1.readObject();
         this.factory = var2;
         Comparator var3 = ((SortedSet)this.factory.get()).comparator();
         this.valueComparator = var3;
         Map var4 = (Map)var1.readObject();
         this.setMap(var4);
      }

      private void writeObject(ObjectOutputStream var1) throws IOException {
         var1.defaultWriteObject();
         Supplier var2 = this.factory;
         var1.writeObject(var2);
         Map var3 = this.backingMap();
         var1.writeObject(var3);
      }

      protected SortedSet<V> createCollection() {
         return (SortedSet)this.factory.get();
      }

      public Comparator<? super V> valueComparator() {
         return this.valueComparator;
      }
   }

   static class 1 extends AbstractMapEntry<K, Collection<V>> {

      // $FF: synthetic field
      final Entry val$entry;


      1(Entry var1) {
         this.val$entry = var1;
      }

      public K getKey() {
         return this.val$entry.getKey();
      }

      public Collection<V> getValue() {
         return Multimaps.unmodifiableValueCollection((Collection)this.val$entry.getValue());
      }
   }

   private static class UnmodifiableSortedSetMultimap<K extends Object, V extends Object> extends Multimaps.UnmodifiableSetMultimap<K, V> implements SortedSetMultimap<K, V> {

      private static final long serialVersionUID;


      UnmodifiableSortedSetMultimap(SortedSetMultimap<K, V> var1) {
         super(var1);
      }

      public SortedSetMultimap<K, V> delegate() {
         return (SortedSetMultimap)super.delegate();
      }

      public SortedSet<V> get(K var1) {
         return Collections.unmodifiableSortedSet(this.delegate().get(var1));
      }

      public SortedSet<V> removeAll(Object var1) {
         throw new UnsupportedOperationException();
      }

      public SortedSet<V> replaceValues(K var1, Iterable<? extends V> var2) {
         throw new UnsupportedOperationException();
      }

      public Comparator<? super V> valueComparator() {
         return this.delegate().valueComparator();
      }
   }

   private static class UnmodifiableListMultimap<K extends Object, V extends Object> extends Multimaps.UnmodifiableMultimap<K, V> implements ListMultimap<K, V> {

      private static final long serialVersionUID;


      UnmodifiableListMultimap(ListMultimap<K, V> var1) {
         super(var1);
      }

      public ListMultimap<K, V> delegate() {
         return (ListMultimap)super.delegate();
      }

      public List<V> get(K var1) {
         return Collections.unmodifiableList(this.delegate().get(var1));
      }

      public List<V> removeAll(Object var1) {
         throw new UnsupportedOperationException();
      }

      public List<V> replaceValues(K var1, Iterable<? extends V> var2) {
         throw new UnsupportedOperationException();
      }
   }

   static class UnmodifiableAsMapEntries<K extends Object, V extends Object> extends ForwardingSet<Entry<K, Collection<V>>> {

      private final Set<Entry<K, Collection<V>>> delegate;


      UnmodifiableAsMapEntries(Set<Entry<K, Collection<V>>> var1) {
         this.delegate = var1;
      }

      public boolean contains(Object var1) {
         return Maps.containsEntryImpl(this.delegate(), var1);
      }

      public boolean containsAll(Collection<?> var1) {
         return Collections2.containsAll(this, var1);
      }

      protected Set<Entry<K, Collection<V>>> delegate() {
         return this.delegate;
      }

      public boolean equals(@Nullable Object var1) {
         return Collections2.setEquals(this, var1);
      }

      public Iterator<Entry<K, Collection<V>>> iterator() {
         Iterator var1 = this.delegate.iterator();
         return new Multimaps.UnmodifiableAsMapEntries.1(var1);
      }

      public Object[] toArray() {
         return ObjectArrays.toArrayImpl(this);
      }

      public <T extends Object> T[] toArray(T[] var1) {
         return ObjectArrays.toArrayImpl(this, var1);
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
            return Multimaps.unmodifiableAsMapEntry((Entry)this.val$iterator.next());
         }
      }
   }

   private static class MapMultimap<K extends Object, V extends Object> implements SetMultimap<K, V>, Serializable {

      private static final Joiner.MapJoiner joiner = Joiner.on("], ").withKeyValueSeparator("=[").useForNull("null");
      private static final long serialVersionUID = 7845222491160860175L;
      transient Map<K, Collection<V>> asMap;
      final Map<K, V> map;


      MapMultimap(Map<K, V> var1) {
         Map var2 = (Map)Preconditions.checkNotNull(var1);
         this.map = var2;
      }

      public Map<K, Collection<V>> asMap() {
         Object var1 = this.asMap;
         if(var1 == null) {
            var1 = new Multimaps.MapMultimap.AsMap();
            this.asMap = (Map)var1;
         }

         return (Map)var1;
      }

      public void clear() {
         this.map.clear();
      }

      public boolean containsEntry(Object var1, Object var2) {
         Set var3 = this.map.entrySet();
         Entry var4 = Maps.immutableEntry(var1, var2);
         return var3.contains(var4);
      }

      public boolean containsKey(Object var1) {
         return this.map.containsKey(var1);
      }

      public boolean containsValue(Object var1) {
         return this.map.containsValue(var1);
      }

      public Set<Entry<K, V>> entries() {
         return this.map.entrySet();
      }

      public boolean equals(@Nullable Object var1) {
         boolean var2 = true;
         if(var1 != this) {
            if(var1 instanceof Multimap) {
               Multimap var3 = (Multimap)var1;
               int var4 = this.size();
               int var5 = var3.size();
               if(var4 == var5) {
                  Map var6 = this.asMap();
                  Map var7 = var3.asMap();
                  if(var6.equals(var7)) {
                     return var2;
                  }
               }

               var2 = false;
            } else {
               var2 = false;
            }
         }

         return var2;
      }

      public Set<V> get(K var1) {
         return new Multimaps.MapMultimap.1(var1);
      }

      public int hashCode() {
         return this.map.hashCode();
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public Set<K> keySet() {
         return this.map.keySet();
      }

      public Multiset<K> keys() {
         return Multisets.forSet(this.map.keySet());
      }

      public boolean put(K var1, V var2) {
         throw new UnsupportedOperationException();
      }

      public boolean putAll(Multimap<? extends K, ? extends V> var1) {
         throw new UnsupportedOperationException();
      }

      public boolean putAll(K var1, Iterable<? extends V> var2) {
         throw new UnsupportedOperationException();
      }

      public boolean remove(Object var1, Object var2) {
         Set var3 = this.map.entrySet();
         Entry var4 = Maps.immutableEntry(var1, var2);
         return var3.remove(var4);
      }

      public Set<V> removeAll(Object var1) {
         HashSet var2 = new HashSet(2);
         if(this.map.containsKey(var1)) {
            Object var3 = this.map.remove(var1);
            var2.add(var3);
         }

         return var2;
      }

      public Set<V> replaceValues(K var1, Iterable<? extends V> var2) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return this.map.size();
      }

      public String toString() {
         String var1;
         if(this.map.isEmpty()) {
            var1 = "{}";
         } else {
            int var2 = this.map.size() * 16;
            StringBuilder var3 = (new StringBuilder(var2)).append('{');
            Joiner.MapJoiner var4 = joiner;
            Map var5 = this.map;
            var4.appendTo(var3, var5);
            var1 = var3.append("]}").toString();
         }

         return var1;
      }

      public Collection<V> values() {
         return this.map.values();
      }

      class AsMap extends Maps.ImprovedAbstractMap<K, Collection<V>> {

         AsMap() {}

         public boolean containsKey(Object var1) {
            return MapMultimap.this.map.containsKey(var1);
         }

         protected Set<Entry<K, Collection<V>>> createEntrySet() {
            Multimaps.MapMultimap var1 = MapMultimap.this;
            return var1.new AsMapEntries();
         }

         public Collection<V> get(Object var1) {
            Set var2 = MapMultimap.this.get(var1);
            if(var2.isEmpty()) {
               var2 = null;
            }

            return var2;
         }

         public Collection<V> remove(Object var1) {
            Set var2 = MapMultimap.this.removeAll(var1);
            if(var2.isEmpty()) {
               var2 = null;
            }

            return var2;
         }
      }

      class AsMapEntries extends AbstractSet<Entry<K, Collection<V>>> {

         AsMapEntries() {}

         public boolean contains(Object var1) {
            boolean var2 = true;
            boolean var3 = false;
            if(var1 instanceof Entry) {
               Entry var4 = (Entry)var1;
               if(var4.getValue() instanceof Set) {
                  label14: {
                     Set var5 = (Set)var4.getValue();
                     if(var5.size() == 1) {
                        Multimaps.MapMultimap var6 = MapMultimap.this;
                        Object var7 = var4.getKey();
                        Object var8 = var5.iterator().next();
                        if(var6.containsEntry(var7, var8)) {
                           break label14;
                        }
                     }

                     var2 = false;
                  }

                  var3 = var2;
               }
            }

            return var3;
         }

         public Iterator<Entry<K, Collection<V>>> iterator() {
            return new Multimaps.MapMultimap.AsMapEntries.1();
         }

         public boolean remove(Object var1) {
            boolean var2 = true;
            boolean var3 = false;
            if(var1 instanceof Entry) {
               Entry var4 = (Entry)var1;
               if(var4.getValue() instanceof Set) {
                  label14: {
                     Set var5 = (Set)var4.getValue();
                     if(var5.size() == 1) {
                        Set var6 = MapMultimap.this.map.entrySet();
                        Object var7 = var4.getKey();
                        Object var8 = var5.iterator().next();
                        Entry var9 = Maps.immutableEntry(var7, var8);
                        if(var6.remove(var9)) {
                           break label14;
                        }
                     }

                     var2 = false;
                  }

                  var3 = var2;
               }
            }

            return var3;
         }

         public int size() {
            return MapMultimap.this.map.size();
         }

         class 1 implements Iterator<Entry<K, Collection<V>>> {

            final Iterator<K> keys;


            1() {
               Iterator var2 = MapMultimap.this.map.keySet().iterator();
               this.keys = var2;
            }

            public boolean hasNext() {
               return this.keys.hasNext();
            }

            public Entry<K, Collection<V>> next() {
               Object var1 = this.keys.next();
               return new Multimaps.MapMultimap.AsMapEntries.1.1(var1);
            }

            public void remove() {
               this.keys.remove();
            }

            class 1 extends AbstractMapEntry<K, Collection<V>> {

               // $FF: synthetic field
               final Object val$key;


               1(Object var2) {
                  this.val$key = var2;
               }

               public K getKey() {
                  return this.val$key;
               }

               public Collection<V> getValue() {
                  Multimaps.MapMultimap var1 = MapMultimap.this;
                  Object var2 = this.val$key;
                  return var1.get(var2);
               }
            }
         }
      }

      class 1 extends AbstractSet<V> {

         // $FF: synthetic field
         final Object val$key;


         1(Object var2) {
            this.val$key = var2;
         }

         public Iterator<V> iterator() {
            return new Multimaps.MapMultimap.1.1();
         }

         public int size() {
            Map var1 = MapMultimap.this.map;
            Object var2 = this.val$key;
            byte var3;
            if(var1.containsKey(var2)) {
               var3 = 1;
            } else {
               var3 = 0;
            }

            return var3;
         }

         class 1 implements Iterator<V> {

            int i;


            1() {}

            public boolean hasNext() {
               boolean var3;
               if(this.i == 0) {
                  Map var1 = MapMultimap.this.map;
                  Object var2 = 1.this.val$key;
                  if(var1.containsKey(var2)) {
                     var3 = true;
                     return var3;
                  }
               }

               var3 = false;
               return var3;
            }

            public V next() {
               if(!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  int var1 = this.i + 1;
                  this.i = var1;
                  Map var2 = MapMultimap.this.map;
                  Object var3 = 1.this.val$key;
                  return var2.get(var3);
               }
            }

            public void remove() {
               byte var1 = 1;
               if(this.i != var1) {
                  var1 = 0;
               }

               Preconditions.checkState((boolean)var1);
               this.i = -1;
               Map var2 = MapMultimap.this.map;
               Object var3 = 1.this.val$key;
               var2.remove(var3);
            }
         }
      }
   }
}
