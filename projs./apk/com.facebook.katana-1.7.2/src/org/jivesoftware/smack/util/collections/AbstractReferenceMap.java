package org.jivesoftware.smack.util.collections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import org.jivesoftware.smack.util.collections.AbstractHashedMap;
import org.jivesoftware.smack.util.collections.DefaultMapEntry;
import org.jivesoftware.smack.util.collections.MapIterator;

public abstract class AbstractReferenceMap<K extends Object, V extends Object> extends AbstractHashedMap<K, V> {

   public static final int HARD = 0;
   public static final int SOFT = 1;
   public static final int WEAK = 2;
   protected int keyType;
   protected boolean purgeValues;
   private transient ReferenceQueue queue;
   protected int valueType;


   protected AbstractReferenceMap() {}

   protected AbstractReferenceMap(int var1, int var2, int var3, float var4, boolean var5) {
      super(var3, var4);
      verify("keyType", var1);
      verify("valueType", var2);
      this.keyType = var1;
      this.valueType = var2;
      this.purgeValues = var5;
   }

   private static void verify(String var0, int var1) {
      if(var1 < 0 || var1 > 2) {
         String var2 = var0 + " must be HARD, SOFT, WEAK.";
         throw new IllegalArgumentException(var2);
      }
   }

   public void clear() {
      super.clear();

      while(this.queue.poll() != null) {
         ;
      }

   }

   public boolean containsKey(Object var1) {
      this.purgeBeforeRead();
      AbstractHashedMap.HashEntry var2 = this.getEntry(var1);
      boolean var3;
      if(var2 == null) {
         var3 = false;
      } else if(var2.getValue() != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean containsValue(Object var1) {
      this.purgeBeforeRead();
      byte var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = super.containsValue(var1);
      }

      return (boolean)var2;
   }

   public AbstractHashedMap.HashEntry<K, V> createEntry(AbstractHashedMap.HashEntry<K, V> var1, int var2, K var3, V var4) {
      AbstractReferenceMap.ReferenceEntry var5 = (AbstractReferenceMap.ReferenceEntry)var1;
      return new AbstractReferenceMap.ReferenceEntry(this, var5, var2, var3, var4);
   }

   protected Iterator<Entry<K, V>> createEntrySetIterator() {
      return new AbstractReferenceMap.ReferenceEntrySetIterator(this);
   }

   protected Iterator<K> createKeySetIterator() {
      return new AbstractReferenceMap.ReferenceKeySetIterator(this);
   }

   protected Iterator<V> createValuesIterator() {
      return new AbstractReferenceMap.ReferenceValuesIterator(this);
   }

   protected void doReadObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      int var2 = var1.readInt();
      this.keyType = var2;
      int var3 = var1.readInt();
      this.valueType = var3;
      boolean var4 = var1.readBoolean();
      this.purgeValues = var4;
      float var5 = var1.readFloat();
      this.loadFactor = var5;
      int var6 = var1.readInt();
      this.init();
      AbstractHashedMap.HashEntry[] var7 = new AbstractHashedMap.HashEntry[var6];
      this.data = var7;

      while(true) {
         Object var8 = var1.readObject();
         if(var8 == null) {
            int var9 = this.data.length;
            float var10 = this.loadFactor;
            int var11 = this.calculateThreshold(var9, var10);
            this.threshold = var11;
            return;
         }

         Object var12 = var1.readObject();
         this.put(var8, var12);
      }
   }

   protected void doWriteObject(ObjectOutputStream var1) throws IOException {
      int var2 = this.keyType;
      var1.writeInt(var2);
      int var3 = this.valueType;
      var1.writeInt(var3);
      boolean var4 = this.purgeValues;
      var1.writeBoolean(var4);
      float var5 = this.loadFactor;
      var1.writeFloat(var5);
      int var6 = this.data.length;
      var1.writeInt(var6);
      MapIterator var7 = this.mapIterator();

      while(var7.hasNext()) {
         Object var8 = var7.next();
         var1.writeObject(var8);
         Object var9 = var7.getValue();
         var1.writeObject(var9);
      }

      var1.writeObject((Object)null);
   }

   public Set<Entry<K, V>> entrySet() {
      if(this.entrySet == null) {
         AbstractReferenceMap.ReferenceEntrySet var1 = new AbstractReferenceMap.ReferenceEntrySet(this);
         this.entrySet = var1;
      }

      return this.entrySet;
   }

   public V get(Object var1) {
      this.purgeBeforeRead();
      AbstractHashedMap.HashEntry var2 = this.getEntry(var1);
      Object var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = var2.getValue();
      }

      return var3;
   }

   protected AbstractHashedMap.HashEntry<K, V> getEntry(Object var1) {
      AbstractHashedMap.HashEntry var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = super.getEntry(var1);
      }

      return var2;
   }

   protected int hashEntry(Object var1, Object var2) {
      int var3 = 0;
      int var4;
      if(var1 == null) {
         var4 = 0;
      } else {
         var4 = var1.hashCode();
      }

      if(var2 != null) {
         var3 = var2.hashCode();
      }

      return var4 ^ var3;
   }

   protected void init() {
      ReferenceQueue var1 = new ReferenceQueue();
      this.queue = var1;
   }

   public boolean isEmpty() {
      this.purgeBeforeRead();
      return super.isEmpty();
   }

   protected boolean isEqualKey(Object var1, Object var2) {
      boolean var3;
      if(var1 != var2 && !var1.equals(var2)) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   public Set<K> keySet() {
      if(this.keySet == null) {
         AbstractReferenceMap.ReferenceKeySet var1 = new AbstractReferenceMap.ReferenceKeySet(this);
         this.keySet = var1;
      }

      return this.keySet;
   }

   public MapIterator<K, V> mapIterator() {
      return new AbstractReferenceMap.ReferenceMapIterator(this);
   }

   protected void purge() {
      for(Reference var1 = this.queue.poll(); var1 != null; var1 = this.queue.poll()) {
         this.purge(var1);
      }

   }

   protected void purge(Reference var1) {
      int var2 = var1.hashCode();
      int var3 = this.data.length;
      int var4 = this.hashIndex(var2, var3);
      AbstractHashedMap.HashEntry var5 = this.data[var4];

      AbstractHashedMap.HashEntry var11;
      for(AbstractHashedMap.HashEntry var6 = null; var5 != null; var5 = var11) {
         if(((AbstractReferenceMap.ReferenceEntry)var5).purge(var1)) {
            if(var6 == null) {
               AbstractHashedMap.HashEntry[] var7 = this.data;
               AbstractHashedMap.HashEntry var8 = var5.next;
               var7[var4] = var8;
            } else {
               AbstractHashedMap.HashEntry var10 = var5.next;
               var6.next = var10;
            }

            int var9 = this.size - 1;
            this.size = var9;
            return;
         }

         var11 = var5.next;
         var6 = var5;
      }

   }

   protected void purgeBeforeRead() {
      this.purge();
   }

   protected void purgeBeforeWrite() {
      this.purge();
   }

   public V put(K var1, V var2) {
      if(var1 == null) {
         throw new NullPointerException("null keys not allowed");
      } else if(var2 == null) {
         throw new NullPointerException("null values not allowed");
      } else {
         this.purgeBeforeWrite();
         return super.put(var1, var2);
      }
   }

   public V remove(Object var1) {
      Object var2;
      if(var1 == null) {
         var2 = null;
      } else {
         this.purgeBeforeWrite();
         var2 = super.remove(var1);
      }

      return var2;
   }

   public int size() {
      this.purgeBeforeRead();
      return super.size();
   }

   public Collection<V> values() {
      if(this.values == null) {
         AbstractReferenceMap.ReferenceValues var1 = new AbstractReferenceMap.ReferenceValues(this);
         this.values = var1;
      }

      return this.values;
   }

   static class WeakRef<T extends Object> extends WeakReference<T> {

      private int hash;


      public WeakRef(int var1, T var2, ReferenceQueue var3) {
         super(var2, var3);
         this.hash = var1;
      }

      public int hashCode() {
         return this.hash;
      }
   }

   protected static class ReferenceEntry<K extends Object, V extends Object> extends AbstractHashedMap.HashEntry<K, V> {

      protected final AbstractReferenceMap<K, V> parent;
      protected Reference<K> refKey;
      protected Reference<V> refValue;


      public ReferenceEntry(AbstractReferenceMap<K, V> var1, AbstractReferenceMap.ReferenceEntry<K, V> var2, int var3, K var4, V var5) {
         super(var2, var3, (Object)null, (Object)null);
         this.parent = var1;
         if(var1.keyType != 0) {
            int var6 = var1.keyType;
            Reference var7 = this.toReference(var6, var4, var3);
            this.refKey = var7;
         } else {
            this.setKey(var4);
         }

         if(var1.valueType != 0) {
            int var8 = var1.valueType;
            Reference var9 = this.toReference(var8, var5, var3);
            this.refValue = var9;
         } else {
            this.setValue(var5);
         }
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else if(!(var1 instanceof Entry)) {
            var2 = false;
         } else {
            Entry var3 = (Entry)var1;
            Object var4 = var3.getKey();
            Object var5 = var3.getValue();
            if(var4 != null && var5 != null) {
               AbstractReferenceMap var6 = this.parent;
               Object var7 = this.getKey();
               if(var6.isEqualKey(var4, var7)) {
                  AbstractReferenceMap var8 = this.parent;
                  Object var9 = this.getValue();
                  if(var8.isEqualValue(var5, var9)) {
                     var2 = true;
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

      public K getKey() {
         Object var1;
         if(this.parent.keyType > 0) {
            var1 = this.refKey.get();
         } else {
            var1 = super.getKey();
         }

         return var1;
      }

      public V getValue() {
         Object var1;
         if(this.parent.valueType > 0) {
            var1 = this.refValue.get();
         } else {
            var1 = super.getValue();
         }

         return var1;
      }

      public int hashCode() {
         AbstractReferenceMap var1 = this.parent;
         Object var2 = this.getKey();
         Object var3 = this.getValue();
         return var1.hashEntry(var2, var3);
      }

      protected AbstractReferenceMap.ReferenceEntry<K, V> next() {
         return (AbstractReferenceMap.ReferenceEntry)this.next;
      }

      boolean purge(Reference var1) {
         boolean var2;
         if(this.parent.keyType > 0 && this.refKey == var1) {
            var2 = true;
         } else {
            var2 = false;
         }

         boolean var3;
         if(!var2 && (this.parent.valueType <= 0 || this.refValue != var1)) {
            var3 = false;
         } else {
            var3 = true;
         }

         if(var3) {
            if(this.parent.keyType > 0) {
               this.refKey.clear();
            }

            if(this.parent.valueType > 0) {
               this.refValue.clear();
            } else if(this.parent.purgeValues) {
               Object var4 = this.setValue((Object)null);
            }
         }

         return var3;
      }

      public V setValue(V var1) {
         Object var2 = this.getValue();
         if(this.parent.valueType > 0) {
            this.refValue.clear();
            int var3 = this.parent.valueType;
            int var4 = this.hashCode;
            Reference var5 = this.toReference(var3, var1, var4);
            this.refValue = var5;
         } else {
            super.setValue(var1);
         }

         return var2;
      }

      protected <T extends Object> Reference<T> toReference(int var1, T var2, int var3) {
         Object var5;
         switch(var1) {
         case 1:
            ReferenceQueue var4 = this.parent.queue;
            var5 = new AbstractReferenceMap.SoftRef(var3, var2, var4);
            break;
         case 2:
            ReferenceQueue var6 = this.parent.queue;
            var5 = new AbstractReferenceMap.WeakRef(var3, var2, var6);
            break;
         default:
            throw new Error("Attempt to create hard reference in ReferenceMap!");
         }

         return (Reference)var5;
      }
   }

   static class ReferenceEntrySet<K extends Object, V extends Object> extends AbstractHashedMap.EntrySet<K, V> {

      protected ReferenceEntrySet(AbstractHashedMap<K, V> var1) {
         super(var1);
      }

      public Object[] toArray() {
         Object[] var1 = new Object[0];
         return this.toArray(var1);
      }

      public <T extends Object> T[] toArray(T[] var1) {
         ArrayList var2 = new ArrayList();
         Iterator var3 = this.iterator();

         while(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            Object var5 = var4.getKey();
            Object var6 = var4.getValue();
            DefaultMapEntry var7 = new DefaultMapEntry(var5, var6);
            var2.add(var7);
         }

         return var2.toArray(var1);
      }
   }

   static class ReferenceValuesIterator<K extends Object, V extends Object> extends AbstractReferenceMap.ReferenceIteratorBase<K, V> implements Iterator<V> {

      ReferenceValuesIterator(AbstractReferenceMap<K, V> var1) {
         super(var1);
      }

      public V next() {
         return this.nextEntry().getValue();
      }
   }

   static class ReferenceIteratorBase<K extends Object, V extends Object> {

      K currentKey;
      V currentValue;
      AbstractReferenceMap.ReferenceEntry<K, V> entry;
      int expectedModCount;
      int index;
      K nextKey;
      V nextValue;
      final AbstractReferenceMap<K, V> parent;
      AbstractReferenceMap.ReferenceEntry<K, V> previous;


      public ReferenceIteratorBase(AbstractReferenceMap<K, V> var1) {
         this.parent = var1;
         int var2;
         if(var1.size() != 0) {
            var2 = var1.data.length;
         } else {
            var2 = 0;
         }

         this.index = var2;
         int var3 = var1.modCount;
         this.expectedModCount = var3;
      }

      private void checkMod() {
         int var1 = this.parent.modCount;
         int var2 = this.expectedModCount;
         if(var1 != var2) {
            throw new ConcurrentModificationException();
         }
      }

      private boolean nextNull() {
         boolean var1;
         if(this.nextKey != null && this.nextValue != null) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      protected AbstractReferenceMap.ReferenceEntry<K, V> currentEntry() {
         this.checkMod();
         return this.previous;
      }

      public boolean hasNext() {
         this.checkMod();

         boolean var8;
         while(true) {
            if(!this.nextNull()) {
               var8 = true;
               break;
            }

            AbstractReferenceMap.ReferenceEntry var1 = this.entry;
            int var2 = this.index;
            AbstractReferenceMap.ReferenceEntry var3 = var1;

            int var4;
            int var5;
            for(var4 = var2; var3 == null && var4 > 0; var4 = var5) {
               var5 = var4 + -1;
               AbstractReferenceMap.ReferenceEntry var6 = (AbstractReferenceMap.ReferenceEntry)this.parent.data[var5];
               var3 = var6;
            }

            this.entry = var3;
            this.index = var4;
            if(var3 == null) {
               this.currentKey = null;
               this.currentValue = null;
               var8 = false;
               break;
            }

            Object var9 = var3.getKey();
            this.nextKey = var9;
            Object var10 = var3.getValue();
            this.nextValue = var10;
            if(this.nextNull()) {
               AbstractReferenceMap.ReferenceEntry var11 = this.entry.next();
               this.entry = var11;
            }
         }

         return var8;
      }

      protected AbstractReferenceMap.ReferenceEntry<K, V> nextEntry() {
         this.checkMod();
         if(this.nextNull() && !this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            AbstractReferenceMap.ReferenceEntry var1 = this.entry;
            this.previous = var1;
            AbstractReferenceMap.ReferenceEntry var2 = this.entry.next();
            this.entry = var2;
            Object var3 = this.nextKey;
            this.currentKey = var3;
            Object var4 = this.nextValue;
            this.currentValue = var4;
            this.nextKey = null;
            this.nextValue = null;
            return this.previous;
         }
      }

      public void remove() {
         this.checkMod();
         if(this.previous == null) {
            throw new IllegalStateException();
         } else {
            AbstractReferenceMap var1 = this.parent;
            Object var2 = this.currentKey;
            var1.remove(var2);
            this.previous = null;
            this.currentKey = null;
            this.currentValue = null;
            int var4 = this.parent.modCount;
            this.expectedModCount = var4;
         }
      }

      public AbstractReferenceMap.ReferenceEntry<K, V> superNext() {
         return this.nextEntry();
      }
   }

   static class ReferenceEntrySetIterator<K extends Object, V extends Object> extends AbstractReferenceMap.ReferenceIteratorBase<K, V> implements Iterator<Entry<K, V>> {

      public ReferenceEntrySetIterator(AbstractReferenceMap<K, V> var1) {
         super(var1);
      }

      public AbstractReferenceMap.ReferenceEntry<K, V> next() {
         return this.superNext();
      }
   }

   static class ReferenceValues<K extends Object, V extends Object> extends AbstractHashedMap.Values<K, V> {

      protected ReferenceValues(AbstractHashedMap<K, V> var1) {
         super(var1);
      }

      public Object[] toArray() {
         Object[] var1 = new Object[0];
         return this.toArray(var1);
      }

      public <T extends Object> T[] toArray(T[] var1) {
         int var2 = this.parent.size();
         ArrayList var3 = new ArrayList(var2);
         Iterator var4 = this.iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            var3.add(var5);
         }

         return var3.toArray(var1);
      }
   }

   static class ReferenceKeySet<K extends Object, V extends Object> extends AbstractHashedMap.KeySet<K, V> {

      protected ReferenceKeySet(AbstractHashedMap<K, V> var1) {
         super(var1);
      }

      public Object[] toArray() {
         Object[] var1 = new Object[0];
         return this.toArray(var1);
      }

      public <T extends Object> T[] toArray(T[] var1) {
         int var2 = this.parent.size();
         ArrayList var3 = new ArrayList(var2);
         Iterator var4 = this.iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            var3.add(var5);
         }

         return var3.toArray(var1);
      }
   }

   static class ReferenceKeySetIterator<K extends Object, V extends Object> extends AbstractReferenceMap.ReferenceIteratorBase<K, V> implements Iterator<K> {

      ReferenceKeySetIterator(AbstractReferenceMap<K, V> var1) {
         super(var1);
      }

      public K next() {
         return this.nextEntry().getKey();
      }
   }

   static class SoftRef<T extends Object> extends SoftReference<T> {

      private int hash;


      public SoftRef(int var1, T var2, ReferenceQueue var3) {
         super(var2, var3);
         this.hash = var1;
      }

      public int hashCode() {
         return this.hash;
      }
   }

   static class ReferenceMapIterator<K extends Object, V extends Object> extends AbstractReferenceMap.ReferenceIteratorBase<K, V> implements MapIterator<K, V> {

      protected ReferenceMapIterator(AbstractReferenceMap<K, V> var1) {
         super(var1);
      }

      public K getKey() {
         AbstractReferenceMap.ReferenceEntry var1 = this.currentEntry();
         if(var1 == null) {
            throw new IllegalStateException("getKey() can only be called after next() and before remove()");
         } else {
            return var1.getKey();
         }
      }

      public V getValue() {
         AbstractReferenceMap.ReferenceEntry var1 = this.currentEntry();
         if(var1 == null) {
            throw new IllegalStateException("getValue() can only be called after next() and before remove()");
         } else {
            return var1.getValue();
         }
      }

      public K next() {
         return this.nextEntry().getKey();
      }

      public V setValue(V var1) {
         AbstractReferenceMap.ReferenceEntry var2 = this.currentEntry();
         if(var2 == null) {
            throw new IllegalStateException("setValue() can only be called after next() and before remove()");
         } else {
            return var2.setValue(var1);
         }
      }
   }
}
