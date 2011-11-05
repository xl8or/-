package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableEntry;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.google.common.collect.Synchronized;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public final class Maps {

   static final Joiner.MapJoiner standardJoiner = Collections2.standardJoiner.withKeyValueSeparator("=");


   private Maps() {}

   static int capacity(int var0) {
      byte var1;
      if(var0 >= 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      return Math.max(var0 * 2, 16);
   }

   static <K extends Object, V extends Object> boolean containsEntryImpl(Collection<Entry<K, V>> var0, Object var1) {
      byte var2;
      if(!(var1 instanceof Entry)) {
         var2 = 0;
      } else {
         Entry var3 = unmodifiableEntry((Entry)var1);
         var2 = var0.contains(var3);
      }

      return (boolean)var2;
   }

   public static <K extends Object, V extends Object> MapDifference<K, V> difference(Map<? extends K, ? extends V> var0, Map<? extends K, ? extends V> var1) {
      HashMap var2 = newHashMap();
      HashMap var3 = new HashMap(var1);
      HashMap var4 = newHashMap();
      HashMap var5 = newHashMap();
      boolean var6 = true;
      Iterator var7 = var0.entrySet().iterator();

      while(var7.hasNext()) {
         Entry var8 = (Entry)var7.next();
         Object var9 = var8.getKey();
         Object var10 = var8.getValue();
         if(var1.containsKey(var9)) {
            Object var11 = var3.remove(var9);
            if(Objects.equal(var10, var11)) {
               var4.put(var9, var10);
            } else {
               var6 = false;
               Maps.ValueDifferenceImpl var13 = new Maps.ValueDifferenceImpl(var10, var11);
               var5.put(var9, var13);
            }
         } else {
            var6 = false;
            var2.put(var9, var10);
         }
      }

      byte var16;
      if(var6 && var3.isEmpty()) {
         var16 = 1;
      } else {
         var16 = 0;
      }

      return new Maps.MapDifferenceImpl((boolean)var16, var2, var3, var4, var5);
   }

   public static <K extends Object, V extends Object> Map<K, V> filterEntries(Map<K, V> var0, Predicate<? super Entry<K, V>> var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      Object var3;
      if(var0 instanceof Maps.AbstractFilteredMap) {
         var3 = filterFiltered((Maps.AbstractFilteredMap)var0, var1);
      } else {
         Map var4 = (Map)Preconditions.checkNotNull(var0);
         var3 = new Maps.FilteredEntryMap(var4, var1);
      }

      return (Map)var3;
   }

   private static <K extends Object, V extends Object> Map<K, V> filterFiltered(Maps.AbstractFilteredMap<K, V> var0, Predicate<? super Entry<K, V>> var1) {
      Predicate var2 = Predicates.and(var0.predicate, var1);
      Map var3 = var0.unfiltered;
      return new Maps.FilteredEntryMap(var3, var2);
   }

   public static <K extends Object, V extends Object> Map<K, V> filterKeys(Map<K, V> var0, Predicate<? super K> var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      Maps.2 var3 = new Maps.2(var1);
      Object var4;
      if(var0 instanceof Maps.AbstractFilteredMap) {
         var4 = filterFiltered((Maps.AbstractFilteredMap)var0, var3);
      } else {
         Map var5 = (Map)Preconditions.checkNotNull(var0);
         var4 = new Maps.FilteredKeyMap(var5, var1, var3);
      }

      return (Map)var4;
   }

   public static <K extends Object, V extends Object> Map<K, V> filterValues(Map<K, V> var0, Predicate<? super V> var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      Maps.3 var3 = new Maps.3(var1);
      return filterEntries(var0, var3);
   }

   public static ImmutableMap<String, String> fromProperties(Properties var0) {
      ImmutableMap.Builder var1 = ImmutableMap.builder();
      Enumeration var2 = var0.propertyNames();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         String var4 = var0.getProperty(var3);
         var1.put(var3, var4);
      }

      return var1.build();
   }

   public static <K extends Object, V extends Object> Entry<K, V> immutableEntry(@Nullable K var0, @Nullable V var1) {
      return new ImmutableEntry(var0, var1);
   }

   public static <K extends Enum<K>, V extends Object> EnumMap<K, V> newEnumMap(Class<K> var0) {
      Class var1 = (Class)Preconditions.checkNotNull(var0);
      return new EnumMap(var1);
   }

   public static <K extends Enum<K>, V extends Object> EnumMap<K, V> newEnumMap(Map<K, ? extends V> var0) {
      return new EnumMap(var0);
   }

   public static <K extends Object, V extends Object> HashMap<K, V> newHashMap() {
      return new HashMap();
   }

   public static <K extends Object, V extends Object> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> var0) {
      return new HashMap(var0);
   }

   public static <K extends Object, V extends Object> HashMap<K, V> newHashMapWithExpectedSize(int var0) {
      int var1 = capacity(var0);
      return new HashMap(var1);
   }

   public static <K extends Object, V extends Object> IdentityHashMap<K, V> newIdentityHashMap() {
      return new IdentityHashMap();
   }

   public static <K extends Object, V extends Object> LinkedHashMap<K, V> newLinkedHashMap() {
      return new LinkedHashMap();
   }

   public static <K extends Object, V extends Object> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> var0) {
      return new LinkedHashMap(var0);
   }

   public static <K extends Object & Comparable, V extends Object> TreeMap<K, V> newTreeMap() {
      return new TreeMap();
   }

   public static <C extends Object, K extends C, V extends Object> TreeMap<K, V> newTreeMap(@Nullable Comparator<C> var0) {
      return new TreeMap(var0);
   }

   public static <K extends Object, V extends Object> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> var0) {
      return new TreeMap(var0);
   }

   static <K extends Object, V extends Object> boolean removeEntryImpl(Collection<Entry<K, V>> var0, Object var1) {
      byte var2;
      if(!(var1 instanceof Entry)) {
         var2 = 0;
      } else {
         Entry var3 = unmodifiableEntry((Entry)var1);
         var2 = var0.remove(var3);
      }

      return (boolean)var2;
   }

   static boolean safeContainsKey(Map<?, ?> var0, Object var1) {
      boolean var2;
      boolean var3;
      try {
         var2 = var0.containsKey(var1);
      } catch (ClassCastException var5) {
         var3 = false;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   static <V extends Object> V safeGet(Map<?, V> var0, Object var1) {
      Object var2;
      Object var3;
      try {
         var2 = var0.get(var1);
      } catch (ClassCastException var5) {
         var3 = null;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   public static <K extends Object, V extends Object> BiMap<K, V> synchronizedBiMap(BiMap<K, V> var0) {
      return Synchronized.biMap(var0, (Object)null);
   }

   public static <K extends Object, V1 extends Object, V2 extends Object> Map<K, V2> transformValues(Map<K, V1> var0, Function<? super V1, V2> var1) {
      return new Maps.TransformedValuesMap(var0, var1);
   }

   public static <K extends Object, V extends Object> ImmutableMap<K, V> uniqueIndex(Iterable<V> var0, Function<? super V, K> var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      ImmutableMap.Builder var3 = ImmutableMap.builder();
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         Object var6 = var1.apply(var5);
         var3.put(var6, var5);
      }

      return var3.build();
   }

   public static <K extends Object, V extends Object> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> var0) {
      return new Maps.UnmodifiableBiMap(var0, (BiMap)null);
   }

   private static <K extends Object, V extends Object> Entry<K, V> unmodifiableEntry(Entry<K, V> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      return new Maps.1(var0);
   }

   static <K extends Object, V extends Object> Set<Entry<K, V>> unmodifiableEntrySet(Set<Entry<K, V>> var0) {
      Set var1 = Collections.unmodifiableSet(var0);
      return new Maps.UnmodifiableEntrySet(var1);
   }

   static class UnmodifiableEntrySet<K extends Object, V extends Object> extends Maps.UnmodifiableEntries<K, V> implements Set<Entry<K, V>> {

      UnmodifiableEntrySet(Set<Entry<K, V>> var1) {
         super(var1);
      }

      public boolean equals(@Nullable Object var1) {
         return Collections2.setEquals(this, var1);
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }
   }

   private static class FilteredKeyMap<K extends Object, V extends Object> extends Maps.AbstractFilteredMap<K, V> {

      Set<Entry<K, V>> entrySet;
      Predicate<? super K> keyPredicate;
      Set<K> keySet;


      FilteredKeyMap(Map<K, V> var1, Predicate<? super K> var2, Predicate<Entry<K, V>> var3) {
         super(var1, var3);
         this.keyPredicate = var2;
      }

      public boolean containsKey(Object var1) {
         boolean var2;
         if(this.unfiltered.containsKey(var1) && this.keyPredicate.apply(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public Set<Entry<K, V>> entrySet() {
         Set var1 = this.entrySet;
         if(var1 == null) {
            Set var2 = this.unfiltered.entrySet();
            Predicate var3 = this.predicate;
            var1 = Sets.filter(var2, var3);
            this.entrySet = var1;
         }

         return var1;
      }

      public Set<K> keySet() {
         Set var1 = this.keySet;
         if(var1 == null) {
            Set var2 = this.unfiltered.keySet();
            Predicate var3 = this.keyPredicate;
            var1 = Sets.filter(var2, var3);
            this.keySet = var1;
         }

         return var1;
      }
   }

   private static class MapDifferenceImpl<K extends Object, V extends Object> implements MapDifference<K, V> {

      final boolean areEqual;
      final Map<K, MapDifference.ValueDifference<V>> differences;
      final Map<K, V> onBoth;
      final Map<K, V> onlyOnLeft;
      final Map<K, V> onlyOnRight;


      MapDifferenceImpl(boolean var1, Map<K, V> var2, Map<K, V> var3, Map<K, V> var4, Map<K, MapDifference.ValueDifference<V>> var5) {
         this.areEqual = var1;
         Map var6 = Collections.unmodifiableMap(var2);
         this.onlyOnLeft = var6;
         Map var7 = Collections.unmodifiableMap(var3);
         this.onlyOnRight = var7;
         Map var8 = Collections.unmodifiableMap(var4);
         this.onBoth = var8;
         Map var9 = Collections.unmodifiableMap(var5);
         this.differences = var9;
      }

      public boolean areEqual() {
         return this.areEqual;
      }

      public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
         return this.differences;
      }

      public Map<K, V> entriesInCommon() {
         return this.onBoth;
      }

      public Map<K, V> entriesOnlyOnLeft() {
         return this.onlyOnLeft;
      }

      public Map<K, V> entriesOnlyOnRight() {
         return this.onlyOnRight;
      }

      public boolean equals(Object var1) {
         boolean var2 = true;
         if(var1 != this) {
            if(var1 instanceof MapDifference) {
               MapDifference var3 = (MapDifference)var1;
               Map var4 = this.entriesOnlyOnLeft();
               Map var5 = var3.entriesOnlyOnLeft();
               if(var4.equals(var5)) {
                  Map var6 = this.entriesOnlyOnRight();
                  Map var7 = var3.entriesOnlyOnRight();
                  if(var6.equals(var7)) {
                     Map var8 = this.entriesInCommon();
                     Map var9 = var3.entriesInCommon();
                     if(var8.equals(var9)) {
                        Map var10 = this.entriesDiffering();
                        Map var11 = var3.entriesDiffering();
                        if(var10.equals(var11)) {
                           return var2;
                        }
                     }
                  }
               }

               var2 = false;
            } else {
               var2 = false;
            }
         }

         return var2;
      }

      public int hashCode() {
         Object[] var1 = new Object[4];
         Map var2 = this.entriesOnlyOnLeft();
         var1[0] = var2;
         Map var3 = this.entriesOnlyOnRight();
         var1[1] = var3;
         Map var4 = this.entriesInCommon();
         var1[2] = var4;
         Map var5 = this.entriesDiffering();
         var1[3] = var5;
         return Objects.hashCode(var1);
      }

      public String toString() {
         String var1;
         if(this.areEqual) {
            var1 = "equal";
         } else {
            StringBuilder var2 = new StringBuilder("not equal");
            if(!this.onlyOnLeft.isEmpty()) {
               StringBuilder var3 = var2.append(": only on left=");
               Map var4 = this.onlyOnLeft;
               var3.append(var4);
            }

            if(!this.onlyOnRight.isEmpty()) {
               StringBuilder var6 = var2.append(": only on right=");
               Map var7 = this.onlyOnRight;
               var6.append(var7);
            }

            if(!this.differences.isEmpty()) {
               StringBuilder var9 = var2.append(": value differences=");
               Map var10 = this.differences;
               var9.append(var10);
            }

            var1 = var2.toString();
         }

         return var1;
      }
   }

   private abstract static class AbstractFilteredMap<K extends Object, V extends Object> extends AbstractMap<K, V> {

      final Predicate<? super Entry<K, V>> predicate;
      final Map<K, V> unfiltered;
      Collection<V> values;


      AbstractFilteredMap(Map<K, V> var1, Predicate<? super Entry<K, V>> var2) {
         this.unfiltered = var1;
         this.predicate = var2;
      }

      boolean apply(Object var1, V var2) {
         Predicate var4 = this.predicate;
         Entry var5 = Maps.immutableEntry(var1, var2);
         return var4.apply(var5);
      }

      public boolean containsKey(Object var1) {
         boolean var3;
         if(this.unfiltered.containsKey(var1)) {
            Object var2 = this.unfiltered.get(var1);
            if(this.apply(var1, var2)) {
               var3 = true;
               return var3;
            }
         }

         var3 = false;
         return var3;
      }

      public V get(Object var1) {
         Object var2 = this.unfiltered.get(var1);
         if(var2 == null || !this.apply(var1, var2)) {
            var2 = null;
         }

         return var2;
      }

      public boolean isEmpty() {
         return this.entrySet().isEmpty();
      }

      public V put(K var1, V var2) {
         Preconditions.checkArgument(this.apply(var1, var2));
         return this.unfiltered.put(var1, var2);
      }

      public void putAll(Map<? extends K, ? extends V> var1) {
         Iterator var2 = var1.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            Object var4 = var3.getKey();
            Object var5 = var3.getValue();
            Preconditions.checkArgument(this.apply(var4, var5));
         }

         this.unfiltered.putAll(var1);
      }

      public V remove(Object var1) {
         Object var2;
         if(this.containsKey(var1)) {
            var2 = this.unfiltered.remove(var1);
         } else {
            var2 = null;
         }

         return var2;
      }

      public Collection<V> values() {
         Object var1 = this.values;
         if(var1 == null) {
            var1 = new Maps.AbstractFilteredMap.Values();
            this.values = (Collection)var1;
         }

         return (Collection)var1;
      }

      class Values extends AbstractCollection<V> {

         Values() {}

         public void clear() {
            AbstractFilteredMap.this.entrySet().clear();
         }

         public boolean isEmpty() {
            return AbstractFilteredMap.this.entrySet().isEmpty();
         }

         public Iterator<V> iterator() {
            Iterator var1 = AbstractFilteredMap.this.entrySet().iterator();
            return new Maps.AbstractFilteredMap.Values.1(var1);
         }

         public boolean remove(Object var1) {
            Iterator var2 = AbstractFilteredMap.this.unfiltered.entrySet().iterator();

            boolean var5;
            while(true) {
               if(var2.hasNext()) {
                  Entry var3 = (Entry)var2.next();
                  Object var4 = var3.getValue();
                  if(!Objects.equal(var1, var4) || !AbstractFilteredMap.this.predicate.apply(var3)) {
                     continue;
                  }

                  var2.remove();
                  var5 = true;
                  break;
               }

               var5 = false;
               break;
            }

            return var5;
         }

         public boolean removeAll(Collection<?> var1) {
            Object var2 = Preconditions.checkNotNull(var1);
            boolean var3 = false;
            Iterator var4 = AbstractFilteredMap.this.unfiltered.entrySet().iterator();

            while(var4.hasNext()) {
               Entry var5 = (Entry)var4.next();
               Object var6 = var5.getValue();
               if(var1.contains(var6) && AbstractFilteredMap.this.predicate.apply(var5)) {
                  var4.remove();
                  var3 = true;
               }
            }

            return var3;
         }

         public boolean retainAll(Collection<?> var1) {
            Object var2 = Preconditions.checkNotNull(var1);
            boolean var3 = false;
            Iterator var4 = AbstractFilteredMap.this.unfiltered.entrySet().iterator();

            while(var4.hasNext()) {
               Entry var5 = (Entry)var4.next();
               Object var6 = var5.getValue();
               if(!var1.contains(var6) && AbstractFilteredMap.this.predicate.apply(var5)) {
                  var4.remove();
                  var3 = true;
               }
            }

            return var3;
         }

         public int size() {
            return AbstractFilteredMap.this.entrySet().size();
         }

         public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
         }

         public <T extends Object> T[] toArray(T[] var1) {
            return Lists.newArrayList(this.iterator()).toArray(var1);
         }

         class 1 extends UnmodifiableIterator<V> {

            // $FF: synthetic field
            final Iterator val$entryIterator;


            1(Iterator var2) {
               this.val$entryIterator = var2;
            }

            public boolean hasNext() {
               return this.val$entryIterator.hasNext();
            }

            public V next() {
               return ((Entry)this.val$entryIterator.next()).getValue();
            }
         }
      }
   }

   static class UnmodifiableEntries<K extends Object, V extends Object> extends ForwardingCollection<Entry<K, V>> {

      private final Collection<Entry<K, V>> entries;


      UnmodifiableEntries(Collection<Entry<K, V>> var1) {
         this.entries = var1;
      }

      public boolean contains(Object var1) {
         return Maps.containsEntryImpl(this.delegate(), var1);
      }

      public boolean containsAll(Collection<?> var1) {
         return Collections2.containsAll(this, var1);
      }

      protected Collection<Entry<K, V>> delegate() {
         return this.entries;
      }

      public Iterator<Entry<K, V>> iterator() {
         Iterator var1 = super.iterator();
         return new Maps.UnmodifiableEntries.1(var1);
      }

      public Object[] toArray() {
         return ObjectArrays.toArrayImpl(this);
      }

      public <T extends Object> T[] toArray(T[] var1) {
         return ObjectArrays.toArrayImpl(this, var1);
      }

      class 1 extends ForwardingIterator<Entry<K, V>> {

         // $FF: synthetic field
         final Iterator val$delegate;


         1(Iterator var2) {
            this.val$delegate = var2;
         }

         protected Iterator<Entry<K, V>> delegate() {
            return this.val$delegate;
         }

         public Entry<K, V> next() {
            return Maps.unmodifiableEntry((Entry)super.next());
         }
      }
   }

   @GwtCompatible
   abstract static class ImprovedAbstractMap<K extends Object, V extends Object> extends AbstractMap<K, V> {

      private transient Set<Entry<K, V>> entrySet;
      private transient Set<K> keySet;
      private transient Collection<V> values;


      ImprovedAbstractMap() {}

      protected abstract Set<Entry<K, V>> createEntrySet();

      public Set<Entry<K, V>> entrySet() {
         Set var1 = this.entrySet;
         if(var1 == null) {
            var1 = this.createEntrySet();
            this.entrySet = var1;
         }

         return var1;
      }

      public boolean isEmpty() {
         return this.entrySet().isEmpty();
      }

      public Set<K> keySet() {
         Object var1 = this.keySet;
         if(var1 == null) {
            Set var2 = super.keySet();
            var1 = new Maps.ImprovedAbstractMap.1(var2);
            this.keySet = (Set)var1;
         }

         return (Set)var1;
      }

      public Collection<V> values() {
         Object var1 = this.values;
         if(var1 == null) {
            Collection var2 = super.values();
            var1 = new Maps.ImprovedAbstractMap.2(var2);
            this.values = (Collection)var1;
         }

         return (Collection)var1;
      }

      class 1 extends ForwardingSet<K> {

         // $FF: synthetic field
         final Set val$delegate;


         1(Set var2) {
            this.val$delegate = var2;
         }

         protected Set<K> delegate() {
            return this.val$delegate;
         }

         public boolean isEmpty() {
            return ImprovedAbstractMap.this.isEmpty();
         }
      }

      class 2 extends ForwardingCollection<V> {

         // $FF: synthetic field
         final Collection val$delegate;


         2(Collection var2) {
            this.val$delegate = var2;
         }

         protected Collection<V> delegate() {
            return this.val$delegate;
         }

         public boolean isEmpty() {
            return ImprovedAbstractMap.this.isEmpty();
         }
      }
   }

   private static class FilteredEntryMap<K extends Object, V extends Object> extends Maps.AbstractFilteredMap<K, V> {

      Set<Entry<K, V>> entrySet;
      final Set<Entry<K, V>> filteredEntrySet;
      Set<K> keySet;


      FilteredEntryMap(Map<K, V> var1, Predicate<? super Entry<K, V>> var2) {
         super(var1, var2);
         Set var3 = var1.entrySet();
         Predicate var4 = this.predicate;
         Set var5 = Sets.filter(var3, var4);
         this.filteredEntrySet = var5;
      }

      public Set<Entry<K, V>> entrySet() {
         Object var1 = this.entrySet;
         if(var1 == null) {
            var1 = new Maps.FilteredEntryMap.EntrySet((Maps.1)null);
            this.entrySet = (Set)var1;
         }

         return (Set)var1;
      }

      public Set<K> keySet() {
         Object var1 = this.keySet;
         if(var1 == null) {
            var1 = new Maps.FilteredEntryMap.KeySet((Maps.1)null);
            this.keySet = (Set)var1;
         }

         return (Set)var1;
      }

      private class EntrySet extends ForwardingSet<Entry<K, V>> {

         private EntrySet() {}

         // $FF: synthetic method
         EntrySet(Maps.1 var2) {
            this();
         }

         protected Set<Entry<K, V>> delegate() {
            return FilteredEntryMap.this.filteredEntrySet;
         }

         public Iterator<Entry<K, V>> iterator() {
            Iterator var1 = FilteredEntryMap.this.filteredEntrySet.iterator();
            return new Maps.FilteredEntryMap.EntrySet.1(var1);
         }

         class 1 extends UnmodifiableIterator<Entry<K, V>> {

            // $FF: synthetic field
            final Iterator val$iterator;


            1(Iterator var2) {
               this.val$iterator = var2;
            }

            public boolean hasNext() {
               return this.val$iterator.hasNext();
            }

            public Entry<K, V> next() {
               Entry var1 = (Entry)this.val$iterator.next();
               return new Maps.FilteredEntryMap.EntrySet.1.1(var1);
            }

            class 1 extends ForwardingMapEntry<K, V> {

               // $FF: synthetic field
               final Entry val$entry;


               1(Entry var2) {
                  this.val$entry = var2;
               }

               protected Entry<K, V> delegate() {
                  return this.val$entry;
               }

               public V setValue(V var1) {
                  Maps.FilteredEntryMap var2 = FilteredEntryMap.this;
                  Object var3 = this.val$entry.getKey();
                  Preconditions.checkArgument(var2.apply(var3, var1));
                  return super.setValue(var1);
               }
            }
         }
      }

      private class KeySet extends AbstractSet<K> {

         private KeySet() {}

         // $FF: synthetic method
         KeySet(Maps.1 var2) {
            this();
         }

         public void clear() {
            FilteredEntryMap.this.filteredEntrySet.clear();
         }

         public boolean contains(Object var1) {
            return FilteredEntryMap.this.containsKey(var1);
         }

         public Iterator<K> iterator() {
            Iterator var1 = FilteredEntryMap.this.filteredEntrySet.iterator();
            return new Maps.FilteredEntryMap.KeySet.1(var1);
         }

         public boolean remove(Object var1) {
            boolean var3;
            if(FilteredEntryMap.this.containsKey(var1)) {
               FilteredEntryMap.this.unfiltered.remove(var1);
               var3 = true;
            } else {
               var3 = false;
            }

            return var3;
         }

         public boolean removeAll(Collection<?> var1) {
            Object var2 = Preconditions.checkNotNull(var1);
            boolean var3 = false;

            boolean var6;
            for(Iterator var4 = var1.iterator(); var4.hasNext(); var3 |= var6) {
               Object var5 = var4.next();
               var6 = this.remove(var5);
            }

            return var3;
         }

         public boolean retainAll(Collection<?> var1) {
            Object var2 = Preconditions.checkNotNull(var1);
            boolean var3 = false;
            Iterator var4 = FilteredEntryMap.this.unfiltered.entrySet().iterator();

            while(var4.hasNext()) {
               Entry var5 = (Entry)var4.next();
               Object var6 = var5.getKey();
               if(!var1.contains(var6) && FilteredEntryMap.this.predicate.apply(var5)) {
                  var4.remove();
                  var3 = true;
               }
            }

            return var3;
         }

         public int size() {
            return FilteredEntryMap.this.filteredEntrySet.size();
         }

         public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
         }

         public <T extends Object> T[] toArray(T[] var1) {
            return Lists.newArrayList(this.iterator()).toArray(var1);
         }

         class 1 extends UnmodifiableIterator<K> {

            // $FF: synthetic field
            final Iterator val$iterator;


            1(Iterator var2) {
               this.val$iterator = var2;
            }

            public boolean hasNext() {
               return this.val$iterator.hasNext();
            }

            public K next() {
               return ((Entry)this.val$iterator.next()).getKey();
            }
         }
      }
   }

   private static class TransformedValuesMap<K extends Object, V1 extends Object, V2 extends Object> extends AbstractMap<K, V2> {

      Maps.TransformedValuesMap.EntrySet entrySet;
      final Map<K, V1> fromMap;
      final Function<? super V1, V2> function;


      TransformedValuesMap(Map<K, V1> var1, Function<? super V1, V2> var2) {
         Map var3 = (Map)Preconditions.checkNotNull(var1);
         this.fromMap = var3;
         Function var4 = (Function)Preconditions.checkNotNull(var2);
         this.function = var4;
      }

      public void clear() {
         this.fromMap.clear();
      }

      public boolean containsKey(Object var1) {
         return this.fromMap.containsKey(var1);
      }

      public Set<Entry<K, V2>> entrySet() {
         Maps.TransformedValuesMap.EntrySet var1 = this.entrySet;
         if(var1 == null) {
            var1 = new Maps.TransformedValuesMap.EntrySet();
            this.entrySet = var1;
         }

         return var1;
      }

      public V2 get(Object var1) {
         Object var2 = this.fromMap.get(var1);
         Object var3;
         if(var2 == null && !this.fromMap.containsKey(var1)) {
            var3 = null;
         } else {
            var3 = this.function.apply(var2);
         }

         return var3;
      }

      public V2 remove(Object var1) {
         Object var4;
         if(this.fromMap.containsKey(var1)) {
            Function var2 = this.function;
            Object var3 = this.fromMap.remove(var1);
            var4 = var2.apply(var3);
         } else {
            var4 = null;
         }

         return var4;
      }

      public int size() {
         return this.fromMap.size();
      }

      class EntrySet extends AbstractSet<Entry<K, V2>> {

         EntrySet() {}

         public void clear() {
            TransformedValuesMap.this.fromMap.clear();
         }

         public boolean contains(Object var1) {
            byte var2 = 0;
            if(var1 instanceof Entry) {
               Entry var3 = (Entry)var1;
               Object var4 = var3.getKey();
               Object var5 = var3.getValue();
               Object var6 = TransformedValuesMap.this.get(var4);
               if(var6 != null) {
                  var2 = var6.equals(var5);
               } else if(var5 == null && TransformedValuesMap.this.containsKey(var4)) {
                  var2 = 1;
               }
            }

            return (boolean)var2;
         }

         public Iterator<Entry<K, V2>> iterator() {
            Iterator var1 = TransformedValuesMap.this.fromMap.entrySet().iterator();
            return new Maps.TransformedValuesMap.EntrySet.1(var1);
         }

         public boolean remove(Object var1) {
            boolean var4;
            if(this.contains(var1)) {
               Object var2 = ((Entry)var1).getKey();
               TransformedValuesMap.this.fromMap.remove(var2);
               var4 = true;
            } else {
               var4 = false;
            }

            return var4;
         }

         public int size() {
            return TransformedValuesMap.this.size();
         }

         class 1 implements Iterator<Entry<K, V2>> {

            // $FF: synthetic field
            final Iterator val$mapIterator;


            1(Iterator var2) {
               this.val$mapIterator = var2;
            }

            public boolean hasNext() {
               return this.val$mapIterator.hasNext();
            }

            public Entry<K, V2> next() {
               Entry var1 = (Entry)this.val$mapIterator.next();
               return new Maps.TransformedValuesMap.EntrySet.1.1(var1);
            }

            public void remove() {
               this.val$mapIterator.remove();
            }

            class 1 extends AbstractMapEntry<K, V2> {

               // $FF: synthetic field
               final Entry val$entry;


               1(Entry var2) {
                  this.val$entry = var2;
               }

               public K getKey() {
                  return this.val$entry.getKey();
               }

               public V2 getValue() {
                  Function var1 = TransformedValuesMap.this.function;
                  Object var2 = this.val$entry.getValue();
                  return var1.apply(var2);
               }
            }
         }
      }
   }

   private static class UnmodifiableBiMap<K extends Object, V extends Object> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {

      private static final long serialVersionUID;
      final BiMap<? extends K, ? extends V> delegate;
      transient BiMap<V, K> inverse;
      final Map<K, V> unmodifiableMap;
      transient Set<V> values;


      UnmodifiableBiMap(BiMap<? extends K, ? extends V> var1, @Nullable BiMap<V, K> var2) {
         Map var3 = Collections.unmodifiableMap(var1);
         this.unmodifiableMap = var3;
         this.delegate = var1;
         this.inverse = var2;
      }

      protected Map<K, V> delegate() {
         return this.unmodifiableMap;
      }

      public V forcePut(K var1, V var2) {
         throw new UnsupportedOperationException();
      }

      public BiMap<V, K> inverse() {
         Object var1 = this.inverse;
         if(var1 == null) {
            BiMap var2 = this.delegate.inverse();
            var1 = new Maps.UnmodifiableBiMap(var2, this);
            this.inverse = (BiMap)var1;
         }

         return (BiMap)var1;
      }

      public Set<V> values() {
         Set var1 = this.values;
         if(var1 == null) {
            var1 = Collections.unmodifiableSet(this.delegate.values());
            this.values = var1;
         }

         return var1;
      }
   }

   static class ValueDifferenceImpl<V extends Object> implements MapDifference.ValueDifference<V> {

      private final V left;
      private final V right;


      ValueDifferenceImpl(@Nullable V var1, @Nullable V var2) {
         this.left = var1;
         this.right = var2;
      }

      public boolean equals(@Nullable Object var1) {
         boolean var2 = false;
         if(var1 instanceof MapDifference.ValueDifference) {
            MapDifference.ValueDifference var3 = (MapDifference.ValueDifference)var1;
            Object var4 = this.left;
            Object var5 = var3.leftValue();
            if(Objects.equal(var4, var5)) {
               Object var6 = this.right;
               Object var7 = var3.rightValue();
               if(Objects.equal(var6, var7)) {
                  var2 = true;
               }
            }
         }

         return var2;
      }

      public int hashCode() {
         Object[] var1 = new Object[2];
         Object var2 = this.left;
         var1[0] = var2;
         Object var3 = this.right;
         var1[1] = var3;
         return Objects.hashCode(var1);
      }

      public V leftValue() {
         return this.left;
      }

      public V rightValue() {
         return this.right;
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("(");
         Object var2 = this.left;
         StringBuilder var3 = var1.append(var2).append(", ");
         Object var4 = this.right;
         return var3.append(var4).append(")").toString();
      }
   }

   static class 1 extends AbstractMapEntry<K, V> {

      // $FF: synthetic field
      final Entry val$entry;


      1(Entry var1) {
         this.val$entry = var1;
      }

      public K getKey() {
         return this.val$entry.getKey();
      }

      public V getValue() {
         return this.val$entry.getValue();
      }
   }

   static class 2 implements Predicate<Entry<K, V>> {

      // $FF: synthetic field
      final Predicate val$keyPredicate;


      2(Predicate var1) {
         this.val$keyPredicate = var1;
      }

      public boolean apply(Entry<K, V> var1) {
         Predicate var2 = this.val$keyPredicate;
         Object var3 = var1.getKey();
         return var2.apply(var3);
      }
   }

   static class 3 implements Predicate<Entry<K, V>> {

      // $FF: synthetic field
      final Predicate val$valuePredicate;


      3(Predicate var1) {
         this.val$valuePredicate = var1;
      }

      public boolean apply(Entry<K, V> var1) {
         Predicate var2 = this.val$valuePredicate;
         Object var3 = var1.getValue();
         return var2.apply(var3);
      }
   }
}
