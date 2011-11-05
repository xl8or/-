package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Serialization;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public final class LinkedHashMultimap<K extends Object, V extends Object> extends AbstractSetMultimap<K, V> {

   private static final int DEFAULT_VALUES_PER_KEY = 8;
   private static final long serialVersionUID;
   @VisibleForTesting
   transient int expectedValuesPerKey;
   transient Collection<Entry<K, V>> linkedEntries;


   private LinkedHashMultimap() {
      LinkedHashMap var1 = new LinkedHashMap();
      super(var1);
      this.expectedValuesPerKey = 8;
      LinkedHashSet var2 = Sets.newLinkedHashSet();
      this.linkedEntries = var2;
   }

   private LinkedHashMultimap(int var1, int var2) {
      LinkedHashMap var3 = new LinkedHashMap(var1);
      super(var3);
      this.expectedValuesPerKey = 8;
      byte var4;
      if(var2 >= 0) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      Preconditions.checkArgument((boolean)var4);
      this.expectedValuesPerKey = var2;
      int var5 = var1 * var2;
      LinkedHashSet var6 = new LinkedHashSet(var5);
      this.linkedEntries = var6;
   }

   private LinkedHashMultimap(Multimap<? extends K, ? extends V> var1) {
      int var2 = Maps.capacity(var1.keySet().size());
      LinkedHashMap var3 = new LinkedHashMap(var2);
      super(var3);
      this.expectedValuesPerKey = 8;
      int var4 = Maps.capacity(var1.size());
      LinkedHashSet var5 = new LinkedHashSet(var4);
      this.linkedEntries = var5;
      this.putAll(var1);
   }

   public static <K extends Object, V extends Object> LinkedHashMultimap<K, V> create() {
      return new LinkedHashMultimap();
   }

   public static <K extends Object, V extends Object> LinkedHashMultimap<K, V> create(int var0, int var1) {
      return new LinkedHashMultimap(var0, var1);
   }

   public static <K extends Object, V extends Object> LinkedHashMultimap<K, V> create(Multimap<? extends K, ? extends V> var0) {
      return new LinkedHashMultimap(var0);
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = var1.readInt();
      this.expectedValuesPerKey = var2;
      int var3 = Serialization.readCount(var1);
      int var4 = Maps.capacity(var3);
      LinkedHashMap var5 = new LinkedHashMap(var4);
      this.setMap(var5);
      int var6 = this.expectedValuesPerKey * var3;
      LinkedHashSet var7 = new LinkedHashSet(var6);
      this.linkedEntries = var7;
      Serialization.populateMultimap(this, var1, var3);
      this.linkedEntries.clear();
      int var8 = 0;

      while(true) {
         int var9 = this.size();
         if(var8 >= var9) {
            return;
         }

         Object var10 = var1.readObject();
         Object var11 = var1.readObject();
         Collection var12 = this.linkedEntries;
         Entry var13 = Maps.immutableEntry(var10, var11);
         var12.add(var13);
         ++var8;
      }
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      int var2 = this.expectedValuesPerKey;
      var1.writeInt(var2);
      Serialization.writeMultimap(this, var1);
      Iterator var3 = this.linkedEntries.iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Object var5 = var4.getKey();
         var1.writeObject(var5);
         Object var6 = var4.getValue();
         var1.writeObject(var6);
      }

   }

   Collection<V> createCollection(@Nullable K var1) {
      Set var2 = this.createCollection();
      return new LinkedHashMultimap.SetDecorator(var1, var2);
   }

   Set<V> createCollection() {
      int var1 = Maps.capacity(this.expectedValuesPerKey);
      return new LinkedHashSet(var1);
   }

   Iterator<Entry<K, V>> createEntryIterator() {
      Iterator var1 = this.linkedEntries.iterator();
      return new LinkedHashMultimap.1(var1);
   }

   public Set<Entry<K, V>> entries() {
      return super.entries();
   }

   public Set<V> replaceValues(@Nullable K var1, Iterable<? extends V> var2) {
      return super.replaceValues(var1, var2);
   }

   public Collection<V> values() {
      return super.values();
   }

   class 1 implements Iterator<Entry<K, V>> {

      Entry<K, V> entry;
      // $FF: synthetic field
      final Iterator val$delegateIterator;


      1(Iterator var2) {
         this.val$delegateIterator = var2;
      }

      public boolean hasNext() {
         return this.val$delegateIterator.hasNext();
      }

      public Entry<K, V> next() {
         Entry var1 = (Entry)this.val$delegateIterator.next();
         this.entry = var1;
         return this.entry;
      }

      public void remove() {
         this.val$delegateIterator.remove();
         LinkedHashMultimap var1 = LinkedHashMultimap.this;
         Object var2 = this.entry.getKey();
         Object var3 = this.entry.getValue();
         var1.remove(var2, var3);
      }
   }

   private class SetDecorator extends ForwardingSet<V> {

      final Set<V> delegate;
      final K key;


      SetDecorator(Object var2, Set var3) {
         this.delegate = var3;
         this.key = var2;
      }

      public boolean add(@Nullable V var1) {
         boolean var2 = this.delegate.add(var1);
         if(var2) {
            Collection var3 = LinkedHashMultimap.this.linkedEntries;
            Entry var4 = this.createEntry(var1);
            var3.add(var4);
         }

         return var2;
      }

      public boolean addAll(Collection<? extends V> var1) {
         boolean var2 = this.delegate.addAll(var1);
         if(var2) {
            Collection var3 = LinkedHashMultimap.this.linkedEntries;
            Set var4 = this.delegate();
            Collection var5 = this.createEntries(var4);
            var3.addAll(var5);
         }

         return var2;
      }

      public void clear() {
         Collection var1 = LinkedHashMultimap.this.linkedEntries;
         Set var2 = this.delegate();
         Collection var3 = this.createEntries(var2);
         var1.removeAll(var3);
         this.delegate.clear();
      }

      <E extends Object> Collection<Entry<K, E>> createEntries(Collection<E> var1) {
         ArrayList var2 = Lists.newArrayListWithExpectedSize(var1.size());
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            Entry var5 = this.createEntry(var4);
            var2.add(var5);
         }

         return var2;
      }

      <E extends Object> Entry<K, E> createEntry(@Nullable E var1) {
         return Maps.immutableEntry(this.key, var1);
      }

      protected Set<V> delegate() {
         return this.delegate;
      }

      public Iterator<V> iterator() {
         Iterator var1 = this.delegate.iterator();
         return new LinkedHashMultimap.SetDecorator.1(var1);
      }

      public boolean remove(@Nullable Object var1) {
         boolean var2 = this.delegate.remove(var1);
         if(var2) {
            Collection var3 = LinkedHashMultimap.this.linkedEntries;
            Entry var4 = this.createEntry(var1);
            var3.remove(var4);
         }

         return var2;
      }

      public boolean removeAll(Collection<?> var1) {
         boolean var2 = this.delegate.removeAll(var1);
         if(var2) {
            Collection var3 = LinkedHashMultimap.this.linkedEntries;
            Collection var4 = this.createEntries(var1);
            var3.removeAll(var4);
         }

         return var2;
      }

      public boolean retainAll(Collection<?> var1) {
         boolean var2 = false;
         Iterator var3 = this.delegate.iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            if(!var1.contains(var4)) {
               var3.remove();
               Collection var5 = LinkedHashMultimap.this.linkedEntries;
               Entry var6 = Maps.immutableEntry(this.key, var4);
               var5.remove(var6);
               var2 = true;
            }
         }

         return var2;
      }

      class 1 implements Iterator<V> {

         // $FF: synthetic field
         final Iterator val$delegateIterator;
         V value;


         1(Iterator var2) {
            this.val$delegateIterator = var2;
         }

         public boolean hasNext() {
            return this.val$delegateIterator.hasNext();
         }

         public V next() {
            Object var1 = this.val$delegateIterator.next();
            this.value = var1;
            return this.value;
         }

         public void remove() {
            this.val$delegateIterator.remove();
            Collection var1 = LinkedHashMultimap.this.linkedEntries;
            LinkedHashMultimap.SetDecorator var2 = SetDecorator.this;
            Object var3 = this.value;
            Entry var4 = var2.createEntry(var3);
            var1.remove(var4);
         }
      }
   }
}
