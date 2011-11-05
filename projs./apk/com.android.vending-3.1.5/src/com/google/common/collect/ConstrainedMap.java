package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.Iterators;
import com.google.common.collect.MapConstraint;
import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
class ConstrainedMap<K extends Object, V extends Object> extends ForwardingMap<K, V> {

   final MapConstraint<? super K, ? super V> constraint;
   final Map<K, V> delegate;
   private volatile Set<Entry<K, V>> entrySet;


   ConstrainedMap(Map<K, V> var1, MapConstraint<? super K, ? super V> var2) {
      Map var3 = (Map)Preconditions.checkNotNull(var1);
      this.delegate = var3;
      MapConstraint var4 = (MapConstraint)Preconditions.checkNotNull(var2);
      this.constraint = var4;
   }

   private static <K extends Object, V extends Object> Entry<K, V> constrainedEntry(Entry<K, V> var0, MapConstraint<? super K, ? super V> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new ConstrainedMap.1(var0, var1);
   }

   private static <K extends Object, V extends Object> Set<Entry<K, V>> constrainedEntrySet(Set<Entry<K, V>> var0, MapConstraint<? super K, ? super V> var1) {
      return new ConstrainedMap.ConstrainedEntrySet(var0, var1);
   }

   protected Map<K, V> delegate() {
      return this.delegate;
   }

   public Set<Entry<K, V>> entrySet() {
      if(this.entrySet == null) {
         Set var1 = this.delegate.entrySet();
         MapConstraint var2 = this.constraint;
         Set var3 = constrainedEntrySet(var1, var2);
         this.entrySet = var3;
      }

      return this.entrySet;
   }

   public V put(K var1, V var2) {
      this.constraint.checkKeyValue(var1, var2);
      return this.delegate.put(var1, var2);
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         Object var4 = var3.getKey();
         Object var5 = var3.getValue();
         this.put(var4, var5);
      }

   }

   private static class ConstrainedEntries<K extends Object, V extends Object> extends ForwardingCollection<Entry<K, V>> {

      final MapConstraint<? super K, ? super V> constraint;
      final Collection<Entry<K, V>> entries;


      ConstrainedEntries(Collection<Entry<K, V>> var1, MapConstraint<? super K, ? super V> var2) {
         this.entries = var1;
         this.constraint = var2;
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
         Iterator var1 = this.entries.iterator();
         return new ConstrainedMap.ConstrainedEntries.1(var1);
      }

      public boolean remove(Object var1) {
         return Maps.removeEntryImpl(this.delegate(), var1);
      }

      public boolean removeAll(Collection<?> var1) {
         return Iterators.removeAll(this.iterator(), var1);
      }

      public boolean retainAll(Collection<?> var1) {
         return Iterators.retainAll(this.iterator(), var1);
      }

      public Object[] toArray() {
         return ObjectArrays.toArrayImpl(this);
      }

      public <T extends Object> T[] toArray(T[] var1) {
         return ObjectArrays.toArrayImpl(this, var1);
      }

      class 1 extends ForwardingIterator<Entry<K, V>> {

         // $FF: synthetic field
         final Iterator val$iterator;


         1(Iterator var2) {
            this.val$iterator = var2;
         }

         protected Iterator<Entry<K, V>> delegate() {
            return this.val$iterator;
         }

         public Entry<K, V> next() {
            Entry var1 = (Entry)this.val$iterator.next();
            MapConstraint var2 = ConstrainedEntries.this.constraint;
            return ConstrainedMap.constrainedEntry(var1, var2);
         }
      }
   }

   static class ConstrainedEntrySet<K extends Object, V extends Object> extends ConstrainedMap.ConstrainedEntries<K, V> implements Set<Entry<K, V>> {

      ConstrainedEntrySet(Set<Entry<K, V>> var1, MapConstraint<? super K, ? super V> var2) {
         super(var1, var2);
      }

      public boolean equals(@Nullable Object var1) {
         return Collections2.setEquals(this, var1);
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }
   }

   static class 1 extends ForwardingMapEntry<K, V> {

      // $FF: synthetic field
      final MapConstraint val$constraint;
      // $FF: synthetic field
      final Entry val$entry;


      1(Entry var1, MapConstraint var2) {
         this.val$entry = var1;
         this.val$constraint = var2;
      }

      protected Entry<K, V> delegate() {
         return this.val$entry;
      }

      public V setValue(V var1) {
         MapConstraint var2 = this.val$constraint;
         Object var3 = this.getKey();
         var2.checkKeyValue(var3, var1);
         return this.val$entry.setValue(var1);
      }
   }
}
