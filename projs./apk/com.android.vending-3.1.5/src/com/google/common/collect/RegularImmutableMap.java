package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Collections2;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;

@GwtCompatible(
   serializable = true
)
final class RegularImmutableMap<K extends Object, V extends Object> extends ImmutableMap<K, V> {

   private static final long serialVersionUID;
   private final transient Entry<K, V>[] entries;
   private transient ImmutableSet<Entry<K, V>> entrySet;
   private transient ImmutableSet<K> keySet;
   private final transient int keySetHashCode;
   private final transient int mask;
   private final transient Object[] table;
   private transient ImmutableCollection<V> values;


   RegularImmutableMap(Entry<?, ?> ... var1) {
      Entry[] var2 = (Entry[])((Entry[])Preconditions.checkNotNull(var1));
      this.entries = var2;
      int var3 = Hashing.chooseTableSize(var1.length);
      Object[] var4 = new Object[var3 * 2];
      this.table = var4;
      int var5 = var3 + -1;
      this.mask = var5;
      int var6 = 0;
      Entry[] var7 = this.entries;
      int var8 = var7.length;
      int var9 = 0;

      while(var9 < var8) {
         Entry var10 = var7[var9];
         Object var11 = Preconditions.checkNotNull(var10);
         Object var12 = Preconditions.checkNotNull(var10.getKey());
         int var13 = var12.hashCode();
         int var14 = Hashing.smear(var13);

         while(true) {
            int var15 = (this.mask & var14) * 2;
            Object var16 = this.table[var15];
            if(var16 == null) {
               Object var17 = Preconditions.checkNotNull(var10.getValue());
               this.table[var15] = var12;
               Object[] var18 = this.table;
               int var19 = var15 + 1;
               var18[var19] = var17;
               var6 += var13;
               ++var9;
               break;
            }

            if(var16.equals(var12)) {
               String var20 = "duplicate key: " + var12;
               throw new IllegalArgumentException(var20);
            }

            ++var14;
         }
      }

      this.keySetHashCode = var6;
   }

   public boolean containsValue(Object var1) {
      boolean var2 = false;
      if(var1 != null) {
         Entry[] var3 = this.entries;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            if(var3[var5].getValue().equals(var1)) {
               var2 = true;
               break;
            }
         }
      }

      return var2;
   }

   public ImmutableSet<Entry<K, V>> entrySet() {
      Object var1 = this.entrySet;
      if(var1 == null) {
         var1 = new RegularImmutableMap.EntrySet(this);
         this.entrySet = (ImmutableSet)var1;
      }

      return (ImmutableSet)var1;
   }

   public V get(Object var1) {
      Object var2 = null;
      if(var1 != null) {
         int var3 = Hashing.smear(var1.hashCode());

         while(true) {
            int var4 = (this.mask & var3) * 2;
            Object var5 = this.table[var4];
            if(var5 == null) {
               break;
            }

            if(var5.equals(var1)) {
               Object[] var6 = this.table;
               int var7 = var4 + 1;
               var2 = var6[var7];
               break;
            }

            ++var3;
         }
      }

      return var2;
   }

   public boolean isEmpty() {
      return false;
   }

   public ImmutableSet<K> keySet() {
      Object var1 = this.keySet;
      if(var1 == null) {
         var1 = new RegularImmutableMap.KeySet(this);
         this.keySet = (ImmutableSet)var1;
      }

      return (ImmutableSet)var1;
   }

   public int size() {
      return this.entries.length;
   }

   public String toString() {
      int var1 = this.size() * 16;
      StringBuilder var2 = (new StringBuilder(var1)).append('{');
      Joiner var3 = Collections2.standardJoiner;
      Entry[] var4 = this.entries;
      var3.appendTo(var2, (Object[])var4);
      return var2.append('}').toString();
   }

   public ImmutableCollection<V> values() {
      Object var1 = this.values;
      if(var1 == null) {
         var1 = new RegularImmutableMap.Values(this);
         this.values = (ImmutableCollection)var1;
      }

      return (ImmutableCollection)var1;
   }

   private static class EntrySet<K extends Object, V extends Object> extends ImmutableSet.ArrayImmutableSet<Entry<K, V>> {

      final transient RegularImmutableMap<K, V> map;


      EntrySet(RegularImmutableMap<K, V> var1) {
         Entry[] var2 = var1.entries;
         super(var2);
         this.map = var1;
      }

      public boolean contains(Object var1) {
         boolean var2 = false;
         if(var1 instanceof Entry) {
            Entry var3 = (Entry)var1;
            RegularImmutableMap var4 = this.map;
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
   }

   private static class Values<V extends Object> extends ImmutableCollection<V> {

      final RegularImmutableMap<?, V> map;


      Values(RegularImmutableMap<?, V> var1) {
         this.map = var1;
      }

      public boolean contains(Object var1) {
         return this.map.containsValue(var1);
      }

      public UnmodifiableIterator<V> iterator() {
         return new RegularImmutableMap.Values.1();
      }

      public int size() {
         return this.map.entries.length;
      }

      class 1 extends AbstractIterator<V> {

         int index = 0;


         1() {}

         protected V computeNext() {
            int var1 = this.index;
            int var2 = Values.this.map.entries.length;
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

   private static class KeySet<K extends Object, V extends Object> extends ImmutableSet.TransformedImmutableSet<Entry<K, V>, K> {

      final RegularImmutableMap<K, V> map;


      KeySet(RegularImmutableMap<K, V> var1) {
         Entry[] var2 = var1.entries;
         int var3 = var1.keySetHashCode;
         super(var2, var3);
         this.map = var1;
      }

      public boolean contains(Object var1) {
         return this.map.containsKey(var1);
      }

      K transform(Entry<K, V> var1) {
         return var1.getKey();
      }
   }
}
