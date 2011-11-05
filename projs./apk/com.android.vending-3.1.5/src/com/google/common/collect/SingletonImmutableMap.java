package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class SingletonImmutableMap<K extends Object, V extends Object> extends ImmutableMap<K, V> {

   private transient Entry<K, V> entry;
   private transient ImmutableSet<Entry<K, V>> entrySet;
   private transient ImmutableSet<K> keySet;
   final transient K singleKey;
   final transient V singleValue;
   private transient ImmutableCollection<V> values;


   SingletonImmutableMap(K var1, V var2) {
      this.singleKey = var1;
      this.singleValue = var2;
   }

   SingletonImmutableMap(Entry<K, V> var1) {
      Entry var2 = (Entry)Preconditions.checkNotNull(var1);
      this.entry = var2;
      Object var3 = Preconditions.checkNotNull(var1.getKey());
      this.singleKey = var3;
      Object var4 = Preconditions.checkNotNull(var1.getValue());
      this.singleValue = var4;
   }

   private Entry<K, V> entry() {
      Entry var1 = this.entry;
      if(var1 == null) {
         Object var2 = this.singleKey;
         Object var3 = this.singleValue;
         var1 = Maps.immutableEntry(var2, var3);
         this.entry = var1;
      }

      return var1;
   }

   public boolean containsKey(Object var1) {
      return this.singleKey.equals(var1);
   }

   public boolean containsValue(Object var1) {
      return this.singleValue.equals(var1);
   }

   public ImmutableSet<Entry<K, V>> entrySet() {
      ImmutableSet var1 = this.entrySet;
      if(var1 == null) {
         var1 = ImmutableSet.of((Object)this.entry());
         this.entrySet = var1;
      }

      return var1;
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if(var1 != this) {
         if(var1 instanceof Map) {
            Map var3 = (Map)var1;
            if(var3.size() != 1) {
               var2 = false;
            } else {
               Entry var4 = (Entry)var3.entrySet().iterator().next();
               Object var5 = this.singleKey;
               Object var6 = var4.getKey();
               if(var5.equals(var6)) {
                  Object var7 = this.singleValue;
                  Object var8 = var4.getValue();
                  if(var7.equals(var8)) {
                     return var2;
                  }
               }

               var2 = false;
            }
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public V get(Object var1) {
      Object var2;
      if(this.singleKey.equals(var1)) {
         var2 = this.singleValue;
      } else {
         var2 = null;
      }

      return var2;
   }

   public int hashCode() {
      int var1 = this.singleKey.hashCode();
      int var2 = this.singleValue.hashCode();
      return var1 ^ var2;
   }

   public boolean isEmpty() {
      return false;
   }

   public ImmutableSet<K> keySet() {
      ImmutableSet var1 = this.keySet;
      if(var1 == null) {
         var1 = ImmutableSet.of(this.singleKey);
         this.keySet = var1;
      }

      return var1;
   }

   public int size() {
      return 1;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append('{');
      String var2 = this.singleKey.toString();
      StringBuilder var3 = var1.append(var2).append('=');
      String var4 = this.singleValue.toString();
      return var3.append(var4).append('}').toString();
   }

   public ImmutableCollection<V> values() {
      Object var1 = this.values;
      if(var1 == null) {
         Object var2 = this.singleValue;
         var1 = new SingletonImmutableMap.Values(var2);
         this.values = (ImmutableCollection)var1;
      }

      return (ImmutableCollection)var1;
   }

   private static class Values<V extends Object> extends ImmutableCollection<V> {

      final V singleValue;


      Values(V var1) {
         this.singleValue = var1;
      }

      public boolean contains(Object var1) {
         return this.singleValue.equals(var1);
      }

      public boolean isEmpty() {
         return false;
      }

      public UnmodifiableIterator<V> iterator() {
         return Iterators.singletonIterator(this.singleValue);
      }

      public int size() {
         return 1;
      }
   }
}
