package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.EmptyImmutableMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.RegularImmutableMap;
import com.google.common.collect.SingletonImmutableMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public abstract class ImmutableMap<K extends Object, V extends Object> implements Map<K, V>, Serializable {

   ImmutableMap() {}

   public static <K extends Object, V extends Object> ImmutableMap.Builder<K, V> builder() {
      return new ImmutableMap.Builder();
   }

   public static <K extends Object, V extends Object> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> var0) {
      Object var1;
      if(var0 instanceof ImmutableMap && !(var0 instanceof ImmutableSortedMap)) {
         var1 = (ImmutableMap)var0;
      } else {
         Set var2 = var0.entrySet();
         Entry[] var3 = new Entry[0];
         Entry[] var4 = (Entry[])var2.toArray(var3);
         switch(var4.length) {
         case 0:
            var1 = of();
            break;
         case 1:
            Object var10 = var4[0].getKey();
            Object var11 = var4[0].getValue();
            Entry var12 = entryOf(var10, var11);
            var1 = new SingletonImmutableMap(var12);
            break;
         default:
            int var5 = 0;

            while(true) {
               int var6 = var4.length;
               if(var5 >= var6) {
                  var1 = new RegularImmutableMap(var4);
                  break;
               }

               Object var7 = var4[var5].getKey();
               Object var8 = var4[var5].getValue();
               Entry var9 = entryOf(var7, var8);
               var4[var5] = var9;
               ++var5;
            }
         }
      }

      return (ImmutableMap)var1;
   }

   static <K extends Object, V extends Object> Entry<K, V> entryOf(K var0, V var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return Maps.immutableEntry(var2, var3);
   }

   public static <K extends Object, V extends Object> ImmutableMap<K, V> of() {
      return EmptyImmutableMap.INSTANCE;
   }

   public static <K extends Object, V extends Object> ImmutableMap<K, V> of(K var0, V var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new SingletonImmutableMap(var2, var3);
   }

   public static <K extends Object, V extends Object> ImmutableMap<K, V> of(K var0, V var1, K var2, V var3) {
      Entry[] var4 = new Entry[2];
      Entry var5 = entryOf(var0, var1);
      var4[0] = var5;
      Entry var6 = entryOf(var2, var3);
      var4[1] = var6;
      return new RegularImmutableMap(var4);
   }

   public static <K extends Object, V extends Object> ImmutableMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      Entry[] var6 = new Entry[3];
      Entry var7 = entryOf(var0, var1);
      var6[0] = var7;
      Entry var8 = entryOf(var2, var3);
      var6[1] = var8;
      Entry var9 = entryOf(var4, var5);
      var6[2] = var9;
      return new RegularImmutableMap(var6);
   }

   public static <K extends Object, V extends Object> ImmutableMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      Entry[] var8 = new Entry[4];
      Entry var9 = entryOf(var0, var1);
      var8[0] = var9;
      Entry var10 = entryOf(var2, var3);
      var8[1] = var10;
      Entry var11 = entryOf(var4, var5);
      var8[2] = var11;
      Entry var12 = entryOf(var6, var7);
      var8[3] = var12;
      return new RegularImmutableMap(var8);
   }

   public static <K extends Object, V extends Object> ImmutableMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      Entry[] var10 = new Entry[5];
      Entry var11 = entryOf(var0, var1);
      var10[0] = var11;
      Entry var12 = entryOf(var2, var3);
      var10[1] = var12;
      Entry var13 = entryOf(var4, var5);
      var10[2] = var13;
      Entry var14 = entryOf(var6, var7);
      var10[3] = var14;
      Entry var15 = entryOf(var8, var9);
      var10[4] = var15;
      return new RegularImmutableMap(var10);
   }

   public final void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean containsKey(@Nullable Object var1) {
      boolean var2;
      if(this.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public abstract boolean containsValue(@Nullable Object var1);

   public abstract ImmutableSet<Entry<K, V>> entrySet();

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 instanceof Map) {
         Map var3 = (Map)var1;
         ImmutableSet var4 = this.entrySet();
         Set var5 = var3.entrySet();
         var2 = var4.equals(var5);
      } else {
         var2 = false;
      }

      return var2;
   }

   public abstract V get(@Nullable Object var1);

   public int hashCode() {
      return this.entrySet().hashCode();
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract ImmutableSet<K> keySet();

   public final V put(K var1, V var2) {
      throw new UnsupportedOperationException();
   }

   public final void putAll(Map<? extends K, ? extends V> var1) {
      throw new UnsupportedOperationException();
   }

   public final V remove(Object var1) {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      int var1 = this.size() * 16;
      StringBuilder var2 = (new StringBuilder(var1)).append('{');
      Maps.standardJoiner.appendTo(var2, this);
      return var2.append('}').toString();
   }

   public abstract ImmutableCollection<V> values();

   Object writeReplace() {
      return new ImmutableMap.SerializedForm(this);
   }

   public static class Builder<K extends Object, V extends Object> {

      final List<Entry<K, V>> entries;


      public Builder() {
         ArrayList var1 = Lists.newArrayList();
         this.entries = var1;
      }

      private static <K extends Object, V extends Object> ImmutableMap<K, V> fromEntryList(List<Entry<K, V>> var0) {
         Object var3;
         switch(var0.size()) {
         case 0:
            var3 = ImmutableMap.of();
            break;
         case 1:
            Entry var4 = (Entry)Iterables.getOnlyElement(var0);
            var3 = new SingletonImmutableMap(var4);
            break;
         default:
            Entry[] var1 = new Entry[var0.size()];
            Entry[] var2 = (Entry[])var0.toArray(var1);
            var3 = new RegularImmutableMap(var2);
         }

         return (ImmutableMap)var3;
      }

      public ImmutableMap<K, V> build() {
         return fromEntryList(this.entries);
      }

      public ImmutableMap.Builder<K, V> put(K var1, V var2) {
         List var3 = this.entries;
         Entry var4 = ImmutableMap.entryOf(var1, var2);
         var3.add(var4);
         return this;
      }

      public ImmutableMap.Builder<K, V> putAll(Map<? extends K, ? extends V> var1) {
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

   static class SerializedForm implements Serializable {

      private static final long serialVersionUID;
      private final Object[] keys;
      private final Object[] values;


      SerializedForm(ImmutableMap<?, ?> var1) {
         Object[] var2 = new Object[var1.size()];
         this.keys = var2;
         Object[] var3 = new Object[var1.size()];
         this.values = var3;
         int var4 = 0;

         for(Iterator var5 = var1.entrySet().iterator(); var5.hasNext(); ++var4) {
            Entry var6 = (Entry)var5.next();
            Object[] var7 = this.keys;
            Object var8 = var6.getKey();
            var7[var4] = var8;
            Object[] var9 = this.values;
            Object var10 = var6.getValue();
            var9[var4] = var10;
         }

      }

      Object createMap(ImmutableMap.Builder<Object, Object> var1) {
         int var2 = 0;

         while(true) {
            int var3 = this.keys.length;
            if(var2 >= var3) {
               return var1.build();
            }

            Object var4 = this.keys[var2];
            Object var5 = this.values[var2];
            var1.put(var4, var5);
            ++var2;
         }
      }

      Object readResolve() {
         ImmutableMap.Builder var1 = new ImmutableMap.Builder();
         return this.createMap(var1);
      }
   }
}
