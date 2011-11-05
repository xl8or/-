package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractBiMap<K extends Object, V extends Object> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {

   private static final long serialVersionUID;
   private transient Map<K, V> delegate;
   private transient Set<Entry<K, V>> entrySet;
   private transient AbstractBiMap<V, K> inverse;
   private transient Set<K> keySet;
   private transient Set<V> valueSet;


   private AbstractBiMap(Map<K, V> var1, AbstractBiMap<V, K> var2) {
      this.delegate = var1;
      this.inverse = var2;
   }

   // $FF: synthetic method
   AbstractBiMap(Map var1, AbstractBiMap var2, AbstractBiMap.1 var3) {
      this(var1, var2);
   }

   AbstractBiMap(Map<K, V> var1, Map<V, K> var2) {
      this.setDelegates(var1, var2);
   }

   private V putInBothMaps(@Nullable K var1, @Nullable V var2, boolean var3) {
      boolean var4 = this.containsKey(var1);
      if(var4) {
         Object var5 = this.get(var1);
         if(Objects.equal(var2, var5)) {
            return var2;
         }
      }

      if(var3) {
         Object var6 = this.inverse().remove(var2);
      } else {
         byte var8;
         if(!this.containsValue(var2)) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         Object[] var9 = new Object[]{var2};
         Preconditions.checkArgument((boolean)var8, "value already present: %s", var9);
      }

      Object var7 = this.delegate.put(var1, var2);
      this.updateInverseMap(var1, var4, var7, var2);
      var2 = var7;
      return var2;
   }

   private V removeFromBothMaps(Object var1) {
      Object var2 = this.delegate.remove(var1);
      this.removeFromInverseMap(var2);
      return var2;
   }

   private void removeFromInverseMap(V var1) {
      this.inverse.delegate.remove(var1);
   }

   private void updateInverseMap(K var1, boolean var2, V var3, V var4) {
      if(var2) {
         this.removeFromInverseMap(var3);
      }

      this.inverse.delegate.put(var4, var1);
   }

   public void clear() {
      this.delegate.clear();
      this.inverse.delegate.clear();
   }

   public boolean containsValue(Object var1) {
      return this.inverse.containsKey(var1);
   }

   protected Map<K, V> delegate() {
      return this.delegate;
   }

   public Set<Entry<K, V>> entrySet() {
      Object var1 = this.entrySet;
      if(var1 == null) {
         var1 = new AbstractBiMap.EntrySet((AbstractBiMap.1)null);
         this.entrySet = (Set)var1;
      }

      return (Set)var1;
   }

   public V forcePut(K var1, V var2) {
      return this.putInBothMaps(var1, var2, (boolean)1);
   }

   public BiMap<V, K> inverse() {
      return this.inverse;
   }

   public Set<K> keySet() {
      Object var1 = this.keySet;
      if(var1 == null) {
         var1 = new AbstractBiMap.KeySet((AbstractBiMap.1)null);
         this.keySet = (Set)var1;
      }

      return (Set)var1;
   }

   public V put(K var1, V var2) {
      return this.putInBothMaps(var1, var2, (boolean)0);
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

   public V remove(Object var1) {
      Object var2;
      if(this.containsKey(var1)) {
         var2 = this.removeFromBothMaps(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   void setDelegates(Map<K, V> var1, Map<V, K> var2) {
      byte var3 = 1;
      byte var4;
      if(this.delegate == null) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      Preconditions.checkState((boolean)var4);
      byte var5;
      if(this.inverse == null) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      Preconditions.checkState((boolean)var5);
      Preconditions.checkArgument(var1.isEmpty());
      Preconditions.checkArgument(var2.isEmpty());
      if(var1 == var2) {
         var3 = 0;
      }

      Preconditions.checkArgument((boolean)var3);
      this.delegate = var1;
      AbstractBiMap.Inverse var6 = new AbstractBiMap.Inverse(var2, this, (AbstractBiMap.1)null);
      this.inverse = var6;
   }

   void setInverse(AbstractBiMap<V, K> var1) {
      this.inverse = var1;
   }

   public Set<V> values() {
      Object var1 = this.valueSet;
      if(var1 == null) {
         var1 = new AbstractBiMap.ValueSet((AbstractBiMap.1)null);
         this.valueSet = (Set)var1;
      }

      return (Set)var1;
   }

   private class EntrySet extends ForwardingSet<Entry<K, V>> {

      final Set<Entry<K, V>> esDelegate;


      private EntrySet() {
         Set var2 = AbstractBiMap.this.delegate.entrySet();
         this.esDelegate = var2;
      }

      // $FF: synthetic method
      EntrySet(AbstractBiMap.1 var2) {
         this();
      }

      public void clear() {
         AbstractBiMap.this.clear();
      }

      public boolean contains(Object var1) {
         return Maps.containsEntryImpl(this.delegate(), var1);
      }

      public boolean containsAll(Collection<?> var1) {
         return Collections2.containsAll(this, var1);
      }

      protected Set<Entry<K, V>> delegate() {
         return this.esDelegate;
      }

      public Iterator<Entry<K, V>> iterator() {
         Iterator var1 = this.esDelegate.iterator();
         return new AbstractBiMap.EntrySet.1(var1);
      }

      public boolean remove(Object var1) {
         boolean var2;
         if(!this.esDelegate.remove(var1)) {
            var2 = false;
         } else {
            Entry var3 = (Entry)var1;
            Map var4 = AbstractBiMap.this.inverse.delegate;
            Object var5 = var3.getValue();
            var4.remove(var5);
            var2 = true;
         }

         return var2;
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

      class 1 implements Iterator<Entry<K, V>> {

         Entry<K, V> entry;
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
            this.entry = var1;
            Entry var2 = this.entry;
            return new AbstractBiMap.EntrySet.1.1(var2);
         }

         public void remove() {
            byte var1;
            if(this.entry != null) {
               var1 = 1;
            } else {
               var1 = 0;
            }

            Preconditions.checkState((boolean)var1);
            Object var2 = this.entry.getValue();
            this.val$iterator.remove();
            AbstractBiMap.this.removeFromInverseMap(var2);
         }

         class 1 extends ForwardingMapEntry<K, V> {

            // $FF: synthetic field
            final Entry val$finalEntry;


            1(Entry var2) {
               this.val$finalEntry = var2;
            }

            protected Entry<K, V> delegate() {
               return this.val$finalEntry;
            }

            public V setValue(V var1) {
               Preconditions.checkState(EntrySet.thisx.contains(this), "entry no longer in map");
               Object var2 = this.getValue();
               if(!Objects.equal(var1, var2)) {
                  byte var3;
                  if(!AbstractBiMap.this.containsValue(var1)) {
                     var3 = 1;
                  } else {
                     var3 = 0;
                  }

                  Object[] var4 = new Object[]{var1};
                  Preconditions.checkArgument((boolean)var3, "value already present: %s", var4);
                  Object var5 = this.val$finalEntry.setValue(var1);
                  AbstractBiMap var6 = AbstractBiMap.this;
                  Object var7 = this.getKey();
                  Object var8 = var6.get(var7);
                  Preconditions.checkState(Objects.equal(var1, var8), "entry no longer in map");
                  AbstractBiMap var9 = AbstractBiMap.this;
                  Object var10 = this.getKey();
                  var9.updateInverseMap(var10, (boolean)1, var5, var1);
                  var1 = var5;
               }

               return var1;
            }
         }
      }
   }

   private static class Inverse<K extends Object, V extends Object> extends AbstractBiMap<K, V> {

      private static final long serialVersionUID;


      private Inverse(Map<K, V> var1, AbstractBiMap<V, K> var2) {
         super(var1, var2, (AbstractBiMap.1)null);
      }

      // $FF: synthetic method
      Inverse(Map var1, AbstractBiMap var2, AbstractBiMap.1 var3) {
         this(var1, var2);
      }

      private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         var1.defaultReadObject();
         AbstractBiMap var2 = (AbstractBiMap)var1.readObject();
         this.setInverse(var2);
      }

      private void writeObject(ObjectOutputStream var1) throws IOException {
         var1.defaultWriteObject();
         BiMap var2 = this.inverse();
         var1.writeObject(var2);
      }

      Object readResolve() {
         return this.inverse().inverse();
      }
   }

   private class KeySet extends ForwardingSet<K> {

      private KeySet() {}

      // $FF: synthetic method
      KeySet(AbstractBiMap.1 var2) {
         this();
      }

      public void clear() {
         AbstractBiMap.this.clear();
      }

      protected Set<K> delegate() {
         return AbstractBiMap.this.delegate.keySet();
      }

      public Iterator<K> iterator() {
         Iterator var1 = AbstractBiMap.this.delegate.entrySet().iterator();
         return new AbstractBiMap.KeySet.1(var1);
      }

      public boolean remove(Object var1) {
         boolean var2;
         if(!this.contains(var1)) {
            var2 = false;
         } else {
            AbstractBiMap.this.removeFromBothMaps(var1);
            var2 = true;
         }

         return var2;
      }

      public boolean removeAll(Collection<?> var1) {
         return Iterators.removeAll(this.iterator(), var1);
      }

      public boolean retainAll(Collection<?> var1) {
         return Iterators.retainAll(this.iterator(), var1);
      }

      class 1 implements Iterator<K> {

         Entry<K, V> entry;
         // $FF: synthetic field
         final Iterator val$iterator;


         1(Iterator var2) {
            this.val$iterator = var2;
         }

         public boolean hasNext() {
            return this.val$iterator.hasNext();
         }

         public K next() {
            Entry var1 = (Entry)this.val$iterator.next();
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
            Object var2 = this.entry.getValue();
            this.val$iterator.remove();
            AbstractBiMap.this.removeFromInverseMap(var2);
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class ValueSet extends ForwardingSet<V> {

      final Set<V> valuesDelegate;


      private ValueSet() {
         Set var2 = AbstractBiMap.this.inverse.keySet();
         this.valuesDelegate = var2;
      }

      // $FF: synthetic method
      ValueSet(AbstractBiMap.1 var2) {
         this();
      }

      protected Set<V> delegate() {
         return this.valuesDelegate;
      }

      public Iterator<V> iterator() {
         Iterator var1 = AbstractBiMap.this.delegate.values().iterator();
         return new AbstractBiMap.ValueSet.1(var1);
      }

      public Object[] toArray() {
         return ObjectArrays.toArrayImpl(this);
      }

      public <T extends Object> T[] toArray(T[] var1) {
         return ObjectArrays.toArrayImpl(this, var1);
      }

      public String toString() {
         return Iterators.toString(this.iterator());
      }

      class 1 implements Iterator<V> {

         // $FF: synthetic field
         final Iterator val$iterator;
         V valueToRemove;


         1(Iterator var2) {
            this.val$iterator = var2;
         }

         public boolean hasNext() {
            return this.val$iterator.hasNext();
         }

         public V next() {
            Object var1 = this.val$iterator.next();
            this.valueToRemove = var1;
            return var1;
         }

         public void remove() {
            this.val$iterator.remove();
            AbstractBiMap var1 = AbstractBiMap.this;
            Object var2 = this.valueToRemove;
            var1.removeFromInverseMap(var2);
         }
      }
   }
}
