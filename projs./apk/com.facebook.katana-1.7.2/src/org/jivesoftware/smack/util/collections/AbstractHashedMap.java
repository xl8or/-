package org.jivesoftware.smack.util.collections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import org.jivesoftware.smack.util.collections.EmptyIterator;
import org.jivesoftware.smack.util.collections.EmptyMapIterator;
import org.jivesoftware.smack.util.collections.IterableMap;
import org.jivesoftware.smack.util.collections.KeyValue;
import org.jivesoftware.smack.util.collections.MapIterator;

public class AbstractHashedMap<K extends Object, V extends Object> extends AbstractMap<K, V> implements IterableMap<K, V> {

   protected static final int DEFAULT_CAPACITY = 16;
   protected static final float DEFAULT_LOAD_FACTOR = 0.75F;
   protected static final int DEFAULT_THRESHOLD = 12;
   protected static final String GETKEY_INVALID = "getKey() can only be called after next() and before remove()";
   protected static final String GETVALUE_INVALID = "getValue() can only be called after next() and before remove()";
   protected static final int MAXIMUM_CAPACITY = 1073741824;
   protected static final String NO_NEXT_ENTRY = "No next() entry in the iteration";
   protected static final String NO_PREVIOUS_ENTRY = "No previous() entry in the iteration";
   protected static final Object NULL = new Object();
   protected static final String REMOVE_INVALID = "remove() can only be called once after next()";
   protected static final String SETVALUE_INVALID = "setValue() can only be called after next() and before remove()";
   protected transient AbstractHashedMap.HashEntry<K, V>[] data;
   protected transient AbstractHashedMap.EntrySet<K, V> entrySet;
   protected transient AbstractHashedMap.KeySet<K, V> keySet;
   protected transient float loadFactor;
   protected transient int modCount;
   protected transient int size;
   protected transient int threshold;
   protected transient AbstractHashedMap.Values<K, V> values;


   protected AbstractHashedMap() {}

   protected AbstractHashedMap(int var1) {
      this(var1, 0.75F);
   }

   protected AbstractHashedMap(int var1, float var2) {
      if(var1 < 1) {
         throw new IllegalArgumentException("Initial capacity must be greater than 0");
      } else if(var2 > 0.0F && !Float.isNaN(var2)) {
         this.loadFactor = var2;
         int var3 = this.calculateThreshold(var1, var2);
         this.threshold = var3;
         AbstractHashedMap.HashEntry[] var4 = new AbstractHashedMap.HashEntry[this.calculateNewCapacity(var1)];
         this.data = var4;
         this.init();
      } else {
         throw new IllegalArgumentException("Load factor must be greater than 0");
      }
   }

   protected AbstractHashedMap(int var1, float var2, int var3) {
      this.loadFactor = var2;
      AbstractHashedMap.HashEntry[] var4 = new AbstractHashedMap.HashEntry[var1];
      this.data = var4;
      this.threshold = var3;
      this.init();
   }

   protected AbstractHashedMap(Map<? extends K, ? extends V> var1) {
      int var2 = Math.max(var1.size() * 2, 16);
      this(var2, 0.75F);
      this.putAll(var1);
   }

   protected void addEntry(AbstractHashedMap.HashEntry<K, V> var1, int var2) {
      this.data[var2] = var1;
   }

   protected void addMapping(int var1, int var2, K var3, V var4) {
      int var5 = this.modCount + 1;
      this.modCount = var5;
      AbstractHashedMap.HashEntry var6 = this.data[var1];
      AbstractHashedMap.HashEntry var7 = this.createEntry(var6, var2, var3, var4);
      this.addEntry(var7, var1);
      int var8 = this.size + 1;
      this.size = var8;
      this.checkCapacity();
   }

   protected int calculateNewCapacity(int var1) {
      int var2 = 1;
      if(var1 > 1073741824) {
         var2 = 1073741824;
      } else {
         while(1 < var1) {
            int var3 = 1 << 1;
         }

         if(1 > 1073741824) {
            var2 = 1073741824;
         }
      }

      return var2;
   }

   protected int calculateThreshold(int var1, float var2) {
      return (int)((float)var1 * var2);
   }

   protected void checkCapacity() {
      int var1 = this.size;
      int var2 = this.threshold;
      if(var1 >= var2) {
         int var3 = this.data.length * 2;
         if(var3 <= 1073741824) {
            this.ensureCapacity(var3);
         }
      }
   }

   public void clear() {
      int var1 = this.modCount + 1;
      this.modCount = var1;
      AbstractHashedMap.HashEntry[] var2 = this.data;

      for(int var3 = var2.length - 1; var3 >= 0; var3 += -1) {
         var2[var3] = false;
      }

      this.size = 0;
   }

   protected Object clone() {
      AbstractHashedMap var1;
      try {
         var1 = (AbstractHashedMap)super.clone();
         AbstractHashedMap.HashEntry[] var2 = new AbstractHashedMap.HashEntry[this.data.length];
         var1.data = var2;
         var1.entrySet = null;
         var1.keySet = null;
         var1.values = null;
         var1.modCount = 0;
         var1.size = 0;
         var1.init();
         var1.putAll(this);
      } catch (CloneNotSupportedException var4) {
         var1 = null;
      }

      return var1;
   }

   public boolean containsKey(Object var1) {
      Object var2;
      if(var1 == null) {
         var2 = NULL;
      } else {
         var2 = var1;
      }

      int var3 = this.hash(var2);
      AbstractHashedMap.HashEntry[] var4 = this.data;
      int var5 = this.data.length;
      int var6 = this.hashIndex(var3, var5);
      AbstractHashedMap.HashEntry var7 = var4[var6];

      boolean var9;
      while(true) {
         if(var7 == null) {
            var9 = false;
            break;
         }

         if(var7.hashCode == var3) {
            Object var8 = var7.getKey();
            if(this.isEqualKey(var1, var8)) {
               var9 = true;
               break;
            }
         }

         var7 = var7.next;
      }

      return var9;
   }

   public boolean containsValue(Object var1) {
      int var2;
      int var3;
      AbstractHashedMap.HashEntry var4;
      boolean var5;
      if(var1 == null) {
         var2 = this.data.length;

         for(var3 = 0; var3 < var2; ++var3) {
            for(var4 = this.data[var3]; var4 != null; var4 = var4.next) {
               if(var4.getValue() == null) {
                  var5 = true;
                  return var5;
               }
            }
         }
      } else {
         var2 = this.data.length;

         for(var3 = 0; var3 < var2; ++var3) {
            for(var4 = this.data[var3]; var4 != null; var4 = var4.next) {
               Object var6 = var4.getValue();
               if(this.isEqualValue(var1, var6)) {
                  var5 = true;
                  return var5;
               }
            }
         }
      }

      var5 = false;
      return var5;
   }

   protected AbstractHashedMap.HashEntry<K, V> createEntry(AbstractHashedMap.HashEntry<K, V> var1, int var2, K var3, V var4) {
      return new AbstractHashedMap.HashEntry(var1, var2, var3, var4);
   }

   protected Iterator<Entry<K, V>> createEntrySetIterator() {
      Object var1;
      if(this.size() == 0) {
         var1 = EmptyIterator.INSTANCE;
      } else {
         var1 = new AbstractHashedMap.EntrySetIterator(this);
      }

      return (Iterator)var1;
   }

   protected Iterator<K> createKeySetIterator() {
      Object var1;
      if(this.size() == 0) {
         var1 = EmptyIterator.INSTANCE;
      } else {
         var1 = new AbstractHashedMap.KeySetIterator(this);
      }

      return (Iterator)var1;
   }

   protected Iterator<V> createValuesIterator() {
      Object var1;
      if(this.size() == 0) {
         var1 = EmptyIterator.INSTANCE;
      } else {
         var1 = new AbstractHashedMap.ValuesIterator(this);
      }

      return (Iterator)var1;
   }

   protected void destroyEntry(AbstractHashedMap.HashEntry<K, V> var1) {
      var1.next = null;
      Object var2 = var1.key = null;
      Object var3 = var1.value = null;
   }

   protected void doReadObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      float var2 = var1.readFloat();
      this.loadFactor = var2;
      int var3 = var1.readInt();
      int var4 = var1.readInt();
      this.init();
      AbstractHashedMap.HashEntry[] var5 = new AbstractHashedMap.HashEntry[var3];
      this.data = var5;

      for(int var6 = 0; var6 < var4; ++var6) {
         Object var7 = var1.readObject();
         Object var8 = var1.readObject();
         this.put(var7, var8);
      }

      int var10 = this.data.length;
      float var11 = this.loadFactor;
      int var12 = this.calculateThreshold(var10, var11);
      this.threshold = var12;
   }

   protected void doWriteObject(ObjectOutputStream var1) throws IOException {
      float var2 = this.loadFactor;
      var1.writeFloat(var2);
      int var3 = this.data.length;
      var1.writeInt(var3);
      int var4 = this.size;
      var1.writeInt(var4);
      MapIterator var5 = this.mapIterator();

      while(var5.hasNext()) {
         Object var6 = var5.next();
         var1.writeObject(var6);
         Object var7 = var5.getValue();
         var1.writeObject(var7);
      }

   }

   protected void ensureCapacity(int var1) {
      int var2 = this.data.length;
      if(var1 > var2) {
         if(this.size == 0) {
            float var3 = this.loadFactor;
            int var4 = this.calculateThreshold(var1, var3);
            this.threshold = var4;
            AbstractHashedMap.HashEntry[] var5 = new AbstractHashedMap.HashEntry[var1];
            this.data = var5;
         } else {
            AbstractHashedMap.HashEntry[] var6 = this.data;
            AbstractHashedMap.HashEntry[] var7 = new AbstractHashedMap.HashEntry[var1];
            int var8 = this.modCount + 1;
            this.modCount = var8;

            for(int var9 = var2 - 1; var9 >= 0; var9 += -1) {
               AbstractHashedMap.HashEntry var10 = var6[var9];
               if(var10 != null) {
                  var6[var9] = false;

                  while(true) {
                     AbstractHashedMap.HashEntry var11 = var10.next;
                     int var12 = var10.hashCode;
                     int var13 = this.hashIndex(var12, var1);
                     AbstractHashedMap.HashEntry var14 = var7[var13];
                     var10.next = var14;
                     var7[var13] = var10;
                     if(var11 == null) {
                        break;
                     }

                     var10 = var11;
                  }
               }
            }

            float var15 = this.loadFactor;
            int var16 = this.calculateThreshold(var1, var15);
            this.threshold = var16;
            this.data = var7;
         }
      }
   }

   protected int entryHashCode(AbstractHashedMap.HashEntry<K, V> var1) {
      return var1.hashCode;
   }

   protected K entryKey(AbstractHashedMap.HashEntry<K, V> var1) {
      return var1.key;
   }

   protected AbstractHashedMap.HashEntry<K, V> entryNext(AbstractHashedMap.HashEntry<K, V> var1) {
      return var1.next;
   }

   public Set<Entry<K, V>> entrySet() {
      if(this.entrySet == null) {
         AbstractHashedMap.EntrySet var1 = new AbstractHashedMap.EntrySet(this);
         this.entrySet = var1;
      }

      return this.entrySet;
   }

   protected V entryValue(AbstractHashedMap.HashEntry<K, V> var1) {
      return var1.value;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof Map)) {
         var2 = false;
      } else {
         Map var14 = (Map)var1;
         int var3 = var14.size();
         int var4 = this.size();
         if(var3 != var4) {
            var2 = false;
         } else {
            MapIterator var5 = this.mapIterator();

            while(true) {
               boolean var9;
               try {
                  if(!var5.hasNext()) {
                     break;
                  }

                  Object var6 = var5.next();
                  Object var7 = var5.getValue();
                  if(var7 == null) {
                     if(var14.get(var6) == null && var14.containsKey(var6)) {
                        continue;
                     }

                     var2 = false;
                     return var2;
                  }

                  Object var8 = var14.get(var6);
                  var9 = var7.equals(var8);
               } catch (ClassCastException var12) {
                  var2 = false;
                  return var2;
               } catch (NullPointerException var13) {
                  var2 = false;
                  return var2;
               }

               if(!var9) {
                  var2 = false;
                  return var2;
               }
            }

            var2 = true;
         }
      }

      return var2;
   }

   public V get(Object var1) {
      Object var2;
      if(var1 == null) {
         var2 = NULL;
      } else {
         var2 = var1;
      }

      int var3 = this.hash(var2);
      AbstractHashedMap.HashEntry[] var4 = this.data;
      int var5 = this.data.length;
      int var6 = this.hashIndex(var3, var5);
      AbstractHashedMap.HashEntry var7 = var4[var6];

      Object var9;
      while(true) {
         if(var7 == null) {
            var9 = null;
            break;
         }

         if(var7.hashCode == var3) {
            Object var8 = var7.key;
            if(this.isEqualKey(var1, var8)) {
               var9 = var7.getValue();
               break;
            }
         }

         var7 = var7.next;
      }

      return var9;
   }

   protected AbstractHashedMap.HashEntry<K, V> getEntry(Object var1) {
      Object var2;
      if(var1 == null) {
         var2 = NULL;
      } else {
         var2 = var1;
      }

      int var3 = this.hash(var2);
      AbstractHashedMap.HashEntry[] var4 = this.data;
      int var5 = this.data.length;
      int var6 = this.hashIndex(var3, var5);
      AbstractHashedMap.HashEntry var7 = var4[var6];

      AbstractHashedMap.HashEntry var9;
      while(true) {
         if(var7 == null) {
            var9 = null;
            break;
         }

         if(var7.hashCode == var3) {
            Object var8 = var7.getKey();
            if(this.isEqualKey(var1, var8)) {
               var9 = var7;
               break;
            }
         }

         var7 = var7.next;
      }

      return var9;
   }

   protected int hash(Object var1) {
      int var2 = var1.hashCode();
      int var3 = ~(var2 << 9);
      int var4 = var2 + var3;
      int var5 = var4 >>> 14;
      int var6 = var4 ^ var5;
      int var7 = var6 << 4;
      int var8 = var6 + var7;
      int var9 = var8 >>> 10;
      return var8 ^ var9;
   }

   public int hashCode() {
      int var1 = 0;

      int var3;
      for(Iterator var2 = this.createEntrySetIterator(); var2.hasNext(); var1 += var3) {
         var3 = var2.next().hashCode();
      }

      return var1;
   }

   protected int hashIndex(int var1, int var2) {
      return var2 - 1 & var1;
   }

   protected void init() {}

   public boolean isEmpty() {
      boolean var1;
      if(this.size == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected boolean isEqualKey(Object var1, Object var2) {
      boolean var3;
      if(var1 != var2 && (var1 == null || !var1.equals(var2))) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   protected boolean isEqualValue(Object var1, Object var2) {
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
         AbstractHashedMap.KeySet var1 = new AbstractHashedMap.KeySet(this);
         this.keySet = var1;
      }

      return this.keySet;
   }

   public MapIterator<K, V> mapIterator() {
      Object var1;
      if(this.size == 0) {
         var1 = EmptyMapIterator.INSTANCE;
      } else {
         var1 = new AbstractHashedMap.HashMapIterator(this);
      }

      return (MapIterator)var1;
   }

   public V put(K var1, V var2) {
      Object var3;
      if(var1 == null) {
         var3 = NULL;
      } else {
         var3 = var1;
      }

      int var4 = this.hash(var3);
      int var5 = this.data.length;
      int var6 = this.hashIndex(var4, var5);
      AbstractHashedMap.HashEntry var7 = this.data[var6];

      Object var9;
      while(true) {
         if(var7 == null) {
            this.addMapping(var6, var4, var1, var2);
            var9 = null;
            break;
         }

         if(var7.hashCode == var4) {
            Object var8 = var7.getKey();
            if(this.isEqualKey(var1, var8)) {
               var9 = var7.getValue();
               this.updateEntry(var7, var2);
               break;
            }
         }

         var7 = var7.next;
      }

      return var9;
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      int var2 = var1.size();
      if(var2 != 0) {
         int var3 = this.size;
         float var4 = (float)(var2 + var3);
         float var5 = this.loadFactor;
         int var6 = (int)(var4 / var5 + 1.0F);
         int var7 = this.calculateNewCapacity(var6);
         this.ensureCapacity(var7);
         Iterator var8 = var1.entrySet().iterator();

         while(var8.hasNext()) {
            Entry var9 = (Entry)var8.next();
            Object var10 = var9.getKey();
            Object var11 = var9.getValue();
            this.put(var10, var11);
         }

      }
   }

   public V remove(Object var1) {
      Object var2;
      if(var1 == null) {
         var2 = NULL;
      } else {
         var2 = var1;
      }

      int var3 = this.hash(var2);
      int var4 = this.data.length;
      int var5 = this.hashIndex(var3, var4);
      AbstractHashedMap.HashEntry var6 = this.data[var5];
      AbstractHashedMap.HashEntry var7 = null;

      Object var9;
      while(true) {
         if(var6 == null) {
            var9 = null;
            break;
         }

         if(var6.hashCode == var3) {
            Object var8 = var6.getKey();
            if(this.isEqualKey(var1, var8)) {
               var9 = var6.getValue();
               this.removeMapping(var6, var5, var7);
               break;
            }
         }

         AbstractHashedMap.HashEntry var10 = var6.next;
         AbstractHashedMap.HashEntry var11 = var6;
         var6 = var10;
         var7 = var11;
      }

      return var9;
   }

   protected void removeEntry(AbstractHashedMap.HashEntry<K, V> var1, int var2, AbstractHashedMap.HashEntry<K, V> var3) {
      if(var3 == null) {
         AbstractHashedMap.HashEntry[] var4 = this.data;
         AbstractHashedMap.HashEntry var5 = var1.next;
         var4[var2] = var5;
      } else {
         AbstractHashedMap.HashEntry var6 = var1.next;
         var3.next = var6;
      }
   }

   protected void removeMapping(AbstractHashedMap.HashEntry<K, V> var1, int var2, AbstractHashedMap.HashEntry<K, V> var3) {
      int var4 = this.modCount + 1;
      this.modCount = var4;
      this.removeEntry(var1, var2, var3);
      int var5 = this.size - 1;
      this.size = var5;
      this.destroyEntry(var1);
   }

   protected void reuseEntry(AbstractHashedMap.HashEntry<K, V> var1, int var2, int var3, K var4, V var5) {
      AbstractHashedMap.HashEntry var6 = this.data[var2];
      var1.next = var6;
      var1.hashCode = var3;
      var1.key = var4;
      var1.value = var5;
   }

   public int size() {
      return this.size;
   }

   public String toString() {
      String var1;
      if(this.size() == 0) {
         var1 = "{}";
      } else {
         int var2 = this.size() * 32;
         StringBuilder var3 = new StringBuilder(var2);
         StringBuilder var4 = var3.append('{');
         MapIterator var5 = this.mapIterator();
         boolean var6 = var5.hasNext();

         while(var6) {
            Object var7 = var5.next();
            Object var8 = var5.getValue();
            if(var7 == this) {
               var7 = "(this Map)";
            }

            StringBuilder var9 = var3.append(var7).append('=');
            if(var8 == this) {
               var8 = "(this Map)";
            }

            var9.append(var8);
            var6 = var5.hasNext();
            if(var6) {
               StringBuilder var11 = var3.append(',').append(' ');
            }
         }

         StringBuilder var12 = var3.append('}');
         var1 = var3.toString();
      }

      return var1;
   }

   protected void updateEntry(AbstractHashedMap.HashEntry<K, V> var1, V var2) {
      var1.setValue(var2);
   }

   public Collection<V> values() {
      if(this.values == null) {
         AbstractHashedMap.Values var1 = new AbstractHashedMap.Values(this);
         this.values = var1;
      }

      return this.values;
   }

   protected static class HashMapIterator<K extends Object, V extends Object> extends AbstractHashedMap.HashIterator<K, V> implements MapIterator<K, V> {

      protected HashMapIterator(AbstractHashedMap<K, V> var1) {
         super(var1);
      }

      public K getKey() {
         AbstractHashedMap.HashEntry var1 = this.currentEntry();
         if(var1 == null) {
            throw new IllegalStateException("getKey() can only be called after next() and before remove()");
         } else {
            return var1.getKey();
         }
      }

      public V getValue() {
         AbstractHashedMap.HashEntry var1 = this.currentEntry();
         if(var1 == null) {
            throw new IllegalStateException("getValue() can only be called after next() and before remove()");
         } else {
            return var1.getValue();
         }
      }

      public K next() {
         return super.nextEntry().getKey();
      }

      public V setValue(V var1) {
         AbstractHashedMap.HashEntry var2 = this.currentEntry();
         if(var2 == null) {
            throw new IllegalStateException("setValue() can only be called after next() and before remove()");
         } else {
            return var2.setValue(var1);
         }
      }
   }

   protected static class ValuesIterator<K extends Object, V extends Object> extends AbstractHashedMap.HashIterator<K, V> implements Iterator<V> {

      protected ValuesIterator(AbstractHashedMap<K, V> var1) {
         super(var1);
      }

      public V next() {
         return super.nextEntry().getValue();
      }
   }

   protected static class KeySetIterator<K extends Object, V extends Object> extends AbstractHashedMap.HashIterator<K, V> implements Iterator<K> {

      protected KeySetIterator(AbstractHashedMap<K, V> var1) {
         super(var1);
      }

      public K next() {
         return super.nextEntry().getKey();
      }
   }

   protected static class KeySet<K extends Object, V extends Object> extends AbstractSet<K> {

      protected final AbstractHashedMap<K, V> parent;


      protected KeySet(AbstractHashedMap<K, V> var1) {
         this.parent = var1;
      }

      public void clear() {
         this.parent.clear();
      }

      public boolean contains(Object var1) {
         return this.parent.containsKey(var1);
      }

      public Iterator<K> iterator() {
         return this.parent.createKeySetIterator();
      }

      public boolean remove(Object var1) {
         boolean var2 = this.parent.containsKey(var1);
         this.parent.remove(var1);
         return var2;
      }

      public int size() {
         return this.parent.size();
      }
   }

   protected static class EntrySetIterator<K extends Object, V extends Object> extends AbstractHashedMap.HashIterator<K, V> implements Iterator<Entry<K, V>> {

      protected EntrySetIterator(AbstractHashedMap<K, V> var1) {
         super(var1);
      }

      public AbstractHashedMap.HashEntry<K, V> next() {
         return super.nextEntry();
      }
   }

   protected static class HashEntry<K extends Object, V extends Object> implements Entry<K, V>, KeyValue<K, V> {

      protected int hashCode;
      private K key;
      protected AbstractHashedMap.HashEntry<K, V> next;
      private V value;


      protected HashEntry(AbstractHashedMap.HashEntry<K, V> var1, int var2, K var3, V var4) {
         this.next = var1;
         this.hashCode = var2;
         this.key = var3;
         this.value = var4;
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else if(!(var1 instanceof Entry)) {
            var2 = false;
         } else {
            label36: {
               Entry var7 = (Entry)var1;
               if(this.getKey() == null) {
                  if(var7.getKey() != null) {
                     break label36;
                  }
               } else {
                  Object var3 = this.getKey();
                  Object var4 = var7.getKey();
                  if(!var3.equals(var4)) {
                     break label36;
                  }
               }

               if(this.getValue() == null) {
                  if(var7.getValue() != null) {
                     break label36;
                  }
               } else {
                  Object var5 = this.getValue();
                  Object var6 = var7.getValue();
                  if(!var5.equals(var6)) {
                     break label36;
                  }
               }

               var2 = true;
               return var2;
            }

            var2 = false;
         }

         return var2;
      }

      public K getKey() {
         return this.key;
      }

      public V getValue() {
         return this.value;
      }

      public int hashCode() {
         int var1;
         if(this.getKey() == null) {
            var1 = 0;
         } else {
            var1 = this.getKey().hashCode();
         }

         int var2;
         if(this.getValue() == null) {
            var2 = 0;
         } else {
            var2 = this.getValue().hashCode();
         }

         return var1 ^ var2;
      }

      public void setKey(K var1) {
         this.key = var1;
      }

      public V setValue(V var1) {
         Object var2 = this.value;
         this.value = var1;
         return var2;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         Object var2 = this.getKey();
         StringBuilder var3 = var1.append(var2).append('=');
         Object var4 = this.getValue();
         return var3.append(var4).toString();
      }
   }

   protected static class Values<K extends Object, V extends Object> extends AbstractCollection<V> {

      protected final AbstractHashedMap<K, V> parent;


      protected Values(AbstractHashedMap<K, V> var1) {
         this.parent = var1;
      }

      public void clear() {
         this.parent.clear();
      }

      public boolean contains(Object var1) {
         return this.parent.containsValue(var1);
      }

      public Iterator<V> iterator() {
         return this.parent.createValuesIterator();
      }

      public int size() {
         return this.parent.size();
      }
   }

   protected abstract static class HashIterator<K extends Object, V extends Object> {

      protected int expectedModCount;
      protected int hashIndex;
      protected AbstractHashedMap.HashEntry<K, V> last;
      protected AbstractHashedMap.HashEntry<K, V> next;
      protected final AbstractHashedMap parent;


      protected HashIterator(AbstractHashedMap<K, V> var1) {
         this.parent = var1;
         AbstractHashedMap.HashEntry[] var2 = var1.data;
         int var3 = var2.length;
         Object var4 = null;
         int var5 = var3;

         AbstractHashedMap.HashEntry var6;
         AbstractHashedMap.HashEntry var8;
         for(var6 = (AbstractHashedMap.HashEntry)var4; var5 > 0 && var6 == null; var6 = var8) {
            int var7 = var5 + -1;
            var8 = var2[var7];
            var5 = var7;
         }

         this.next = var6;
         this.hashIndex = var5;
         int var9 = var1.modCount;
         this.expectedModCount = var9;
      }

      protected AbstractHashedMap.HashEntry<K, V> currentEntry() {
         return this.last;
      }

      public boolean hasNext() {
         boolean var1;
         if(this.next != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      protected AbstractHashedMap.HashEntry<K, V> nextEntry() {
         int var1 = this.parent.modCount;
         int var2 = this.expectedModCount;
         if(var1 != var2) {
            throw new ConcurrentModificationException();
         } else {
            AbstractHashedMap.HashEntry var3 = this.next;
            if(var3 == null) {
               throw new NoSuchElementException("No next() entry in the iteration");
            } else {
               AbstractHashedMap.HashEntry[] var4 = this.parent.data;
               int var5 = this.hashIndex;
               AbstractHashedMap.HashEntry var6 = var3.next;
               int var7 = var5;

               AbstractHashedMap.HashEntry var8;
               AbstractHashedMap.HashEntry var10;
               for(var8 = var6; var8 == null && var7 > 0; var8 = var10) {
                  int var9 = var7 + -1;
                  var10 = var4[var9];
                  var7 = var9;
               }

               this.next = var8;
               this.hashIndex = var7;
               this.last = var3;
               return var3;
            }
         }
      }

      public void remove() {
         if(this.last == null) {
            throw new IllegalStateException("remove() can only be called once after next()");
         } else {
            int var1 = this.parent.modCount;
            int var2 = this.expectedModCount;
            if(var1 != var2) {
               throw new ConcurrentModificationException();
            } else {
               AbstractHashedMap var3 = this.parent;
               Object var4 = this.last.getKey();
               var3.remove(var4);
               this.last = null;
               int var6 = this.parent.modCount;
               this.expectedModCount = var6;
            }
         }
      }

      public String toString() {
         String var5;
         if(this.last != null) {
            StringBuilder var1 = (new StringBuilder()).append("Iterator[");
            Object var2 = this.last.getKey();
            StringBuilder var3 = var1.append(var2).append("=");
            Object var4 = this.last.getValue();
            var5 = var3.append(var4).append("]").toString();
         } else {
            var5 = "Iterator[]";
         }

         return var5;
      }
   }

   protected static class EntrySet<K extends Object, V extends Object> extends AbstractSet<Entry<K, V>> {

      protected final AbstractHashedMap<K, V> parent;


      protected EntrySet(AbstractHashedMap<K, V> var1) {
         this.parent = var1;
      }

      public void clear() {
         this.parent.clear();
      }

      public boolean contains(Entry<K, V> var1) {
         AbstractHashedMap var2 = this.parent;
         Object var3 = var1.getKey();
         AbstractHashedMap.HashEntry var4 = var2.getEntry(var3);
         boolean var5;
         if(var4 != null && var4.equals(var1)) {
            var5 = true;
         } else {
            var5 = false;
         }

         return var5;
      }

      public Iterator<Entry<K, V>> iterator() {
         return this.parent.createEntrySetIterator();
      }

      public boolean remove(Object var1) {
         boolean var2;
         if(!(var1 instanceof Entry)) {
            var2 = false;
         } else if(!this.contains(var1)) {
            var2 = false;
         } else {
            Object var3 = ((Entry)var1).getKey();
            this.parent.remove(var3);
            var2 = true;
         }

         return var2;
      }

      public int size() {
         return this.parent.size();
      }
   }
}
