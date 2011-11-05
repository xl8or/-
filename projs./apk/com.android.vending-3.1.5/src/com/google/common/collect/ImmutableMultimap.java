package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Serialization;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ImmutableMultimap<K extends Object, V extends Object> implements Multimap<K, V>, Serializable {

   private static final long serialVersionUID;
   private transient ImmutableCollection<Entry<K, V>> entries;
   private transient ImmutableMultiset<K> keys;
   final transient ImmutableMap<K, ? extends ImmutableCollection<V>> map;
   final transient int size;
   private transient ImmutableCollection<V> values;


   ImmutableMultimap(ImmutableMap<K, ? extends ImmutableCollection<V>> var1, int var2) {
      this.map = var1;
      this.size = var2;
   }

   public static <K extends Object, V extends Object> ImmutableMultimap.Builder<K, V> builder() {
      return new ImmutableMultimap.Builder();
   }

   public static <K extends Object, V extends Object> ImmutableMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> var0) {
      Object var1;
      if(var0 instanceof ImmutableMultimap) {
         var1 = (ImmutableMultimap)var0;
      } else {
         var1 = ImmutableListMultimap.copyOf(var0);
      }

      return (ImmutableMultimap)var1;
   }

   private ImmutableMultiset<K> createKeys() {
      ImmutableMultiset.Builder var1 = ImmutableMultiset.builder();
      Iterator var2 = this.map.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         Object var4 = var3.getKey();
         int var5 = ((ImmutableCollection)var3.getValue()).size();
         var1.addCopies(var4, var5);
      }

      return var1.build();
   }

   public static <K extends Object, V extends Object> ImmutableMultimap<K, V> of() {
      return ImmutableListMultimap.of();
   }

   public static <K extends Object, V extends Object> ImmutableMultimap<K, V> of(K var0, V var1) {
      return ImmutableListMultimap.of(var0, var1);
   }

   public static <K extends Object, V extends Object> ImmutableMultimap<K, V> of(K var0, V var1, K var2, V var3) {
      return ImmutableListMultimap.of(var0, var1, var2, var3);
   }

   public static <K extends Object, V extends Object> ImmutableMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      return ImmutableListMultimap.of(var0, var1, var2, var3, var4, var5);
   }

   public static <K extends Object, V extends Object> ImmutableMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      return ImmutableListMultimap.of(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   public static <K extends Object, V extends Object> ImmutableMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      return ImmutableListMultimap.of(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   public ImmutableMap<K, Collection<V>> asMap() {
      return this.map;
   }

   public void clear() {
      throw new UnsupportedOperationException();
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
            if(!((ImmutableCollection)var2.next()).contains(var1)) {
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

   public ImmutableCollection<Entry<K, V>> entries() {
      Object var1 = this.entries;
      if(var1 == null) {
         var1 = new ImmutableMultimap.EntryCollection(this);
         this.entries = (ImmutableCollection)var1;
      }

      return (ImmutableCollection)var1;
   }

   public boolean equals(@Nullable Object var1) {
      boolean var5;
      if(var1 instanceof Multimap) {
         Multimap var2 = (Multimap)var1;
         ImmutableMap var3 = this.map;
         Map var4 = var2.asMap();
         var5 = var3.equals(var4);
      } else {
         var5 = false;
      }

      return var5;
   }

   public abstract ImmutableCollection<V> get(K var1);

   public int hashCode() {
      return this.map.hashCode();
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.size == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public ImmutableSet<K> keySet() {
      return this.map.keySet();
   }

   public ImmutableMultiset<K> keys() {
      ImmutableMultiset var1 = this.keys;
      if(var1 == null) {
         var1 = this.createKeys();
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

   public ImmutableCollection<V> removeAll(Object var1) {
      throw new UnsupportedOperationException();
   }

   public ImmutableCollection<V> replaceValues(K var1, Iterable<? extends V> var2) {
      throw new UnsupportedOperationException();
   }

   public int size() {
      return this.size;
   }

   public String toString() {
      return this.map.toString();
   }

   public ImmutableCollection<V> values() {
      Object var1 = this.values;
      if(var1 == null) {
         var1 = new ImmutableMultimap.Values(this);
         this.values = (ImmutableCollection)var1;
      }

      return (ImmutableCollection)var1;
   }

   private static class BuilderMultimap<K extends Object, V extends Object> extends AbstractMultimap<K, V> {

      private static final long serialVersionUID;


      BuilderMultimap() {
         LinkedHashMap var1 = new LinkedHashMap();
         super(var1);
      }

      Collection<V> createCollection() {
         return Lists.newArrayList();
      }
   }

   static class FieldSettersHolder {

      static final Serialization.FieldSetter<ImmutableMultimap> MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
      static final Serialization.FieldSetter<ImmutableMultimap> SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");


      FieldSettersHolder() {}
   }

   private static class EntryCollection<K extends Object, V extends Object> extends ImmutableCollection<Entry<K, V>> {

      private static final long serialVersionUID;
      final ImmutableMultimap<K, V> multimap;


      EntryCollection(ImmutableMultimap<K, V> var1) {
         this.multimap = var1;
      }

      public boolean contains(Object var1) {
         boolean var6;
         if(var1 instanceof Entry) {
            Entry var2 = (Entry)var1;
            ImmutableMultimap var3 = this.multimap;
            Object var4 = var2.getKey();
            Object var5 = var2.getValue();
            var6 = var3.containsEntry(var4, var5);
         } else {
            var6 = false;
         }

         return var6;
      }

      public UnmodifiableIterator<Entry<K, V>> iterator() {
         UnmodifiableIterator var1 = this.multimap.map.entrySet().iterator();
         return new ImmutableMultimap.EntryCollection.1(var1);
      }

      public int size() {
         return this.multimap.size();
      }

      class 1 extends UnmodifiableIterator<Entry<K, V>> {

         K key;
         // $FF: synthetic field
         final Iterator val$mapIterator;
         Iterator<V> valueIterator;


         1(Iterator var2) {
            this.val$mapIterator = var2;
         }

         public boolean hasNext() {
            boolean var1;
            if((this.key == null || !this.valueIterator.hasNext()) && !this.val$mapIterator.hasNext()) {
               var1 = false;
            } else {
               var1 = true;
            }

            return var1;
         }

         public Entry<K, V> next() {
            if(this.key == null || !this.valueIterator.hasNext()) {
               Entry var1 = (Entry)this.val$mapIterator.next();
               Object var2 = var1.getKey();
               this.key = var2;
               UnmodifiableIterator var3 = ((ImmutableCollection)var1.getValue()).iterator();
               this.valueIterator = var3;
            }

            Object var4 = this.key;
            Object var5 = this.valueIterator.next();
            return Maps.immutableEntry(var4, var5);
         }
      }
   }

   private static class Values<V extends Object> extends ImmutableCollection<V> {

      private static final long serialVersionUID;
      final Multimap<?, V> multimap;


      Values(Multimap<?, V> var1) {
         this.multimap = var1;
      }

      public UnmodifiableIterator<V> iterator() {
         Iterator var1 = this.multimap.entries().iterator();
         return new ImmutableMultimap.Values.1(var1);
      }

      public int size() {
         return this.multimap.size();
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

   public static class Builder<K extends Object, V extends Object> {

      private final Multimap<K, V> builderMultimap;


      public Builder() {
         ImmutableMultimap.BuilderMultimap var1 = new ImmutableMultimap.BuilderMultimap();
         this.builderMultimap = var1;
      }

      public ImmutableMultimap<K, V> build() {
         return ImmutableMultimap.copyOf(this.builderMultimap);
      }

      public ImmutableMultimap.Builder<K, V> put(K var1, V var2) {
         Multimap var3 = this.builderMultimap;
         Object var4 = Preconditions.checkNotNull(var1);
         Object var5 = Preconditions.checkNotNull(var2);
         var3.put(var4, var5);
         return this;
      }

      public ImmutableMultimap.Builder<K, V> putAll(Multimap<? extends K, ? extends V> var1) {
         Iterator var2 = var1.asMap().entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            Object var4 = var3.getKey();
            Iterable var5 = (Iterable)var3.getValue();
            this.putAll(var4, var5);
         }

         return this;
      }

      public ImmutableMultimap.Builder<K, V> putAll(K var1, Iterable<? extends V> var2) {
         Multimap var3 = this.builderMultimap;
         Object var4 = Preconditions.checkNotNull(var1);
         Collection var5 = var3.get(var4);
         Iterator var6 = var2.iterator();

         while(var6.hasNext()) {
            Object var7 = Preconditions.checkNotNull(var6.next());
            var5.add(var7);
         }

         return this;
      }

      public ImmutableMultimap.Builder<K, V> putAll(K var1, V ... var2) {
         List var3 = Arrays.asList(var2);
         return this.putAll(var1, (Iterable)var3);
      }
   }
}
