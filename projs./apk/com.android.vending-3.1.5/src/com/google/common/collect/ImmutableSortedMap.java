package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMapFauxverideShim;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public class ImmutableSortedMap<K extends Object, V extends Object> extends ImmutableSortedMapFauxverideShim<K, V> implements SortedMap<K, V> {

   private static final Entry<?, ?>[] EMPTY_ARRAY = new Entry[0];
   private static final ImmutableMap<Object, Object> NATURAL_EMPTY_MAP;
   private static final Comparator NATURAL_ORDER = Ordering.natural();
   private static final long serialVersionUID;
   private final transient Comparator<? super K> comparator;
   private final transient Entry<K, V>[] entries;
   private transient ImmutableSet<Entry<K, V>> entrySet;
   private final transient int fromIndex;
   private transient ImmutableSortedSet<K> keySet;
   private final transient int toIndex;
   private transient ImmutableCollection<V> values;


   static {
      Entry[] var0 = EMPTY_ARRAY;
      Comparator var1 = NATURAL_ORDER;
      NATURAL_EMPTY_MAP = new ImmutableSortedMap(var0, var1);
   }

   ImmutableSortedMap(Entry<?, ?>[] var1, Comparator<? super K> var2) {
      int var3 = var1.length;
      this(var1, var2, 0, var3);
   }

   private ImmutableSortedMap(Entry<?, ?>[] var1, Comparator<? super K> var2, int var3, int var4) {
      Entry[] var5 = (Entry[])var1;
      this.entries = var5;
      this.comparator = var2;
      this.fromIndex = var3;
      this.toIndex = var4;
   }

   private int binarySearch(Object var1) {
      int var2 = this.fromIndex;
      int var3 = this.toIndex + -1;

      int var5;
      while(true) {
         if(var2 > var3) {
            var5 = -var2 + -1;
            break;
         }

         int var4 = (var3 - var2) / 2;
         var5 = var2 + var4;
         Comparator var6 = this.comparator;
         Object var7 = this.entries[var5].getKey();
         int var8 = ImmutableSortedSet.unsafeCompare(var6, var1, var7);
         if(var8 < 0) {
            var3 = var5 + -1;
         } else {
            if(var8 <= 0) {
               break;
            }

            var2 = var5 + 1;
         }
      }

      return var5;
   }

   public static <K extends Object, V extends Object> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> var0) {
      Ordering var1 = Ordering.natural();
      return copyOfInternal(var0, var1);
   }

   public static <K extends Object, V extends Object> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> var0, Comparator<? super K> var1) {
      Comparator var2 = (Comparator)Preconditions.checkNotNull(var1);
      return copyOfInternal(var0, var2);
   }

   private static <K extends Object, V extends Object> ImmutableSortedMap<K, V> copyOfInternal(Map<? extends K, ? extends V> var0, Comparator<? super K> var1) {
      byte var2 = 0;
      if(var0 instanceof SortedMap) {
         Comparator var3 = ((SortedMap)var0).comparator();
         if(var3 == null) {
            Comparator var4 = NATURAL_ORDER;
            if(var1 == var4) {
               var2 = 1;
            } else {
               var2 = 0;
            }
         } else {
            var2 = var1.equals(var3);
         }
      }

      ImmutableSortedMap var5;
      if(var2 != 0 && var0 instanceof ImmutableSortedMap) {
         var5 = (ImmutableSortedMap)var0;
      } else {
         ArrayList var6 = Lists.newArrayListWithCapacity(var0.size());
         Iterator var7 = var0.entrySet().iterator();

         while(var7.hasNext()) {
            Entry var8 = (Entry)var7.next();
            Object var9 = var8.getKey();
            Object var10 = var8.getValue();
            Entry var11 = entryOf(var9, var10);
            var6.add(var11);
         }

         Entry[] var13 = new Entry[var6.size()];
         Entry[] var14 = (Entry[])var6.toArray(var13);
         if(var2 == 0) {
            sortEntries(var14, var1);
            validateEntries(var14, var1);
         }

         var5 = new ImmutableSortedMap(var14, var1);
      }

      return var5;
   }

   public static <K extends Object, V extends Object> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> var0) {
      Comparator var1;
      if(var0.comparator() == null) {
         var1 = NATURAL_ORDER;
      } else {
         var1 = var0.comparator();
      }

      return copyOfInternal(var0, var1);
   }

   private ImmutableSet<Entry<K, V>> createEntrySet() {
      Object var1;
      if(this.isEmpty()) {
         var1 = ImmutableSet.of();
      } else {
         var1 = new ImmutableSortedMap.EntrySet(this);
      }

      return (ImmutableSet)var1;
   }

   private ImmutableSortedSet<K> createKeySet() {
      Object var1;
      if(this.isEmpty()) {
         var1 = ImmutableSortedSet.emptySet(this.comparator);
      } else {
         Object[] var2 = new Object[this.size()];
         int var3 = this.fromIndex;

         while(true) {
            int var4 = this.toIndex;
            if(var3 >= var4) {
               Comparator var8 = this.comparator;
               var1 = new RegularImmutableSortedSet(var2, var8);
               break;
            }

            int var5 = this.fromIndex;
            int var6 = var3 - var5;
            Object var7 = this.entries[var3].getKey();
            var2[var6] = var7;
            ++var3;
         }
      }

      return (ImmutableSortedSet)var1;
   }

   private ImmutableSortedMap<K, V> createSubmap(int var1, int var2) {
      ImmutableSortedMap var5;
      if(var1 < var2) {
         Entry[] var3 = this.entries;
         Comparator var4 = this.comparator;
         var5 = new ImmutableSortedMap(var3, var4, var1, var2);
      } else {
         var5 = emptyMap(this.comparator);
      }

      return var5;
   }

   private static <K extends Object, V extends Object> ImmutableSortedMap<K, V> emptyMap(Comparator<? super K> var0) {
      ImmutableSortedMap var1;
      if(NATURAL_ORDER.equals(var0)) {
         var1 = of();
      } else {
         Entry[] var2 = EMPTY_ARRAY;
         var1 = new ImmutableSortedMap(var2, var0);
      }

      return var1;
   }

   private int findSubmapIndex(K var1) {
      int var2 = this.binarySearch(var1);
      if(var2 < 0) {
         var2 = -var2 + -1;
      }

      return var2;
   }

   public static <K extends Object & Comparable<K>, V extends Object> ImmutableSortedMap.Builder<K, V> naturalOrder() {
      Ordering var0 = Ordering.natural();
      return new ImmutableSortedMap.Builder(var0);
   }

   public static <K extends Object, V extends Object> ImmutableSortedMap<K, V> of() {
      return (ImmutableSortedMap)NATURAL_EMPTY_MAP;
   }

   public static <K extends Object & Comparable<? super K>, V extends Object> ImmutableSortedMap<K, V> of(K var0, V var1) {
      Entry[] var2 = new Entry[1];
      Entry var3 = entryOf(var0, var1);
      var2[0] = var3;
      Ordering var4 = Ordering.natural();
      return new ImmutableSortedMap(var2, var4);
   }

   public static <K extends Object & Comparable<? super K>, V extends Object> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3) {
      Ordering var4 = Ordering.natural();
      return (new ImmutableSortedMap.Builder(var4)).put(var0, var1).put(var2, var3).build();
   }

   public static <K extends Object & Comparable<? super K>, V extends Object> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      Ordering var6 = Ordering.natural();
      return (new ImmutableSortedMap.Builder(var6)).put(var0, var1).put(var2, var3).put(var4, var5).build();
   }

   public static <K extends Object & Comparable<? super K>, V extends Object> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      Ordering var8 = Ordering.natural();
      return (new ImmutableSortedMap.Builder(var8)).put(var0, var1).put(var2, var3).put(var4, var5).put(var6, var7).build();
   }

   public static <K extends Object & Comparable<? super K>, V extends Object> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      Ordering var10 = Ordering.natural();
      return (new ImmutableSortedMap.Builder(var10)).put(var0, var1).put(var2, var3).put(var4, var5).put(var6, var7).put(var8, var9).build();
   }

   public static <K extends Object, V extends Object> ImmutableSortedMap.Builder<K, V> orderedBy(Comparator<K> var0) {
      return new ImmutableSortedMap.Builder(var0);
   }

   public static <K extends Object & Comparable<K>, V extends Object> ImmutableSortedMap.Builder<K, V> reverseOrder() {
      Ordering var0 = Ordering.natural().reverse();
      return new ImmutableSortedMap.Builder(var0);
   }

   private static void sortEntries(Entry<?, ?>[] var0, Comparator<?> var1) {
      ImmutableSortedMap.1 var2 = new ImmutableSortedMap.1(var1);
      Arrays.sort(var0, var2);
   }

   private static void validateEntries(Entry<?, ?>[] var0, Comparator<?> var1) {
      int var2 = 1;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return;
         }

         int var4 = var2 + -1;
         Object var5 = var0[var4].getKey();
         Object var6 = var0[var2].getKey();
         if(ImmutableSortedSet.unsafeCompare(var1, var5, var6) == 0) {
            StringBuilder var7 = (new StringBuilder()).append("Duplicate keys in mappings ");
            int var8 = var2 + -1;
            Entry var9 = var0[var8];
            StringBuilder var10 = var7.append(var9).append(" and ");
            Entry var11 = var0[var2];
            String var12 = var10.append(var11).toString();
            throw new IllegalArgumentException(var12);
         }

         ++var2;
      }
   }

   public Comparator<? super K> comparator() {
      return this.comparator;
   }

   public boolean containsValue(@Nullable Object var1) {
      boolean var2 = false;
      if(var1 != null) {
         int var3 = this.fromIndex;

         while(true) {
            int var4 = this.toIndex;
            if(var3 >= var4) {
               break;
            }

            if(this.entries[var3].getValue().equals(var1)) {
               var2 = true;
               break;
            }

            ++var3;
         }
      }

      return var2;
   }

   public ImmutableSet<Entry<K, V>> entrySet() {
      ImmutableSet var1 = this.entrySet;
      if(var1 == null) {
         var1 = this.createEntrySet();
         this.entrySet = var1;
      }

      return var1;
   }

   public K firstKey() {
      if(this.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         Entry[] var1 = this.entries;
         int var2 = this.fromIndex;
         return var1[var2].getKey();
      }
   }

   public V get(@Nullable Object var1) {
      Object var2 = null;
      if(var1 != null) {
         int var3;
         try {
            var3 = this.binarySearch(var1);
         } catch (ClassCastException var6) {
            return var2;
         }

         if(var3 >= 0) {
            var2 = this.entries[var3].getValue();
         }
      }

      return var2;
   }

   public ImmutableSortedMap<K, V> headMap(K var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      int var3 = this.findSubmapIndex(var2);
      int var4 = this.fromIndex;
      return this.createSubmap(var4, var3);
   }

   public ImmutableSortedSet<K> keySet() {
      ImmutableSortedSet var1 = this.keySet;
      if(var1 == null) {
         var1 = this.createKeySet();
         this.keySet = var1;
      }

      return var1;
   }

   public K lastKey() {
      if(this.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         Entry[] var1 = this.entries;
         int var2 = this.toIndex + -1;
         return var1[var2].getKey();
      }
   }

   public int size() {
      int var1 = this.toIndex;
      int var2 = this.fromIndex;
      return var1 - var2;
   }

   public ImmutableSortedMap<K, V> subMap(K var1, K var2) {
      Object var3 = Preconditions.checkNotNull(var1);
      Object var4 = Preconditions.checkNotNull(var2);
      byte var5;
      if(this.comparator.compare(var1, var2) <= 0) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      Preconditions.checkArgument((boolean)var5);
      int var6 = this.findSubmapIndex(var1);
      int var7 = this.findSubmapIndex(var2);
      return this.createSubmap(var6, var7);
   }

   public ImmutableSortedMap<K, V> tailMap(K var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      int var3 = this.findSubmapIndex(var2);
      int var4 = this.toIndex;
      return this.createSubmap(var3, var4);
   }

   public ImmutableCollection<V> values() {
      Object var1 = this.values;
      if(var1 == null) {
         var1 = new ImmutableSortedMap.Values(this);
         this.values = (ImmutableCollection)var1;
      }

      return (ImmutableCollection)var1;
   }

   Object writeReplace() {
      return new ImmutableSortedMap.SerializedForm(this);
   }

   private static class ValuesSerializedForm<V extends Object> implements Serializable {

      private static final long serialVersionUID;
      final ImmutableSortedMap<?, V> map;


      ValuesSerializedForm(ImmutableSortedMap<?, V> var1) {
         this.map = var1;
      }

      Object readResolve() {
         return this.map.values();
      }
   }

   private static class SerializedForm extends ImmutableMap.SerializedForm {

      private static final long serialVersionUID;
      private final Comparator<Object> comparator;


      SerializedForm(ImmutableSortedMap<?, ?> var1) {
         super(var1);
         Comparator var2 = var1.comparator();
         this.comparator = var2;
      }

      Object readResolve() {
         Comparator var1 = this.comparator;
         ImmutableSortedMap.Builder var2 = new ImmutableSortedMap.Builder(var1);
         return this.createMap(var2);
      }
   }

   public static final class Builder<K extends Object, V extends Object> extends ImmutableMap.Builder<K, V> {

      private final Comparator<? super K> comparator;


      public Builder(Comparator<? super K> var1) {
         Comparator var2 = (Comparator)Preconditions.checkNotNull(var1);
         this.comparator = var2;
      }

      public ImmutableSortedMap<K, V> build() {
         List var1 = this.entries;
         Entry[] var2 = new Entry[this.entries.size()];
         Entry[] var3 = (Entry[])var1.toArray(var2);
         Comparator var4 = this.comparator;
         ImmutableSortedMap.sortEntries(var3, var4);
         Comparator var5 = this.comparator;
         ImmutableSortedMap.validateEntries(var3, var5);
         Comparator var6 = this.comparator;
         return new ImmutableSortedMap(var3, var6);
      }

      public ImmutableSortedMap.Builder<K, V> put(K var1, V var2) {
         List var3 = this.entries;
         Entry var4 = ImmutableMap.entryOf(var1, var2);
         var3.add(var4);
         return this;
      }

      public ImmutableSortedMap.Builder<K, V> putAll(Map<? extends K, ? extends V> var1) {
         Iterator var2 = var1.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            Object var4 = var3.getKey();
            Object var5 = var3.getValue();
            this.put(var4, var5);
         }

         return this;
      }
   }

   private static class Values<V extends Object> extends ImmutableCollection<V> {

      private final ImmutableSortedMap<?, V> map;


      Values(ImmutableSortedMap<?, V> var1) {
         this.map = var1;
      }

      public boolean contains(Object var1) {
         return this.map.containsValue(var1);
      }

      public UnmodifiableIterator<V> iterator() {
         return new ImmutableSortedMap.Values.1();
      }

      public int size() {
         return this.map.size();
      }

      Object writeReplace() {
         ImmutableSortedMap var1 = this.map;
         return new ImmutableSortedMap.ValuesSerializedForm(var1);
      }

      class 1 extends AbstractIterator<V> {

         int index;


         1() {
            int var2 = Values.this.map.fromIndex;
            this.index = var2;
         }

         protected V computeNext() {
            int var1 = this.index;
            int var2 = Values.this.map.toIndex;
            Object var6;
            if(var1 < var2) {
               Entry[] var3 = Values.this.map.entries;
               int var4 = this.index;
               int var5 = var4 + 1;
               this.index = var5;
               var6 = var3[var4].getValue();
            } else {
               var6 = this.endOfData();
            }

            return var6;
         }
      }
   }

   static class 1 implements Comparator<Entry<?, ?>> {

      // $FF: synthetic field
      final Comparator val$comparator;


      1(Comparator var1) {
         this.val$comparator = var1;
      }

      public int compare(Entry<?, ?> var1, Entry<?, ?> var2) {
         Comparator var3 = this.val$comparator;
         Object var4 = var1.getKey();
         Object var5 = var2.getKey();
         return ImmutableSortedSet.unsafeCompare(var3, var4, var5);
      }
   }

   private static class EntrySetSerializedForm<K extends Object, V extends Object> implements Serializable {

      private static final long serialVersionUID;
      final ImmutableSortedMap<K, V> map;


      EntrySetSerializedForm(ImmutableSortedMap<K, V> var1) {
         this.map = var1;
      }

      Object readResolve() {
         return this.map.entrySet();
      }
   }

   private static class EntrySet<K extends Object, V extends Object> extends ImmutableSet<Entry<K, V>> {

      final transient ImmutableSortedMap<K, V> map;


      EntrySet(ImmutableSortedMap<K, V> var1) {
         this.map = var1;
      }

      public boolean contains(Object var1) {
         boolean var2 = false;
         if(var1 instanceof Entry) {
            Entry var3 = (Entry)var1;
            ImmutableSortedMap var4 = this.map;
            Object var5 = var3.getKey();
            Object var6 = var4.get(var5);
            if(var6 != null) {
               Object var7 = var3.getValue();
               if(var6.equals(var7)) {
                  var2 = true;
               }
            }
         }

         return var2;
      }

      public UnmodifiableIterator<Entry<K, V>> iterator() {
         Entry[] var1 = this.map.entries;
         int var2 = this.map.fromIndex;
         int var3 = this.size();
         return Iterators.forArray(var1, var2, var3);
      }

      public int size() {
         return this.map.size();
      }

      Object writeReplace() {
         ImmutableSortedMap var1 = this.map;
         return new ImmutableSortedMap.EntrySetSerializedForm(var1);
      }
   }
}
