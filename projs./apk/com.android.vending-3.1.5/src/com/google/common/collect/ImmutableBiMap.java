package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.RegularImmutableBiMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public abstract class ImmutableBiMap<K extends Object, V extends Object> extends ImmutableMap<K, V> implements BiMap<K, V> {

   private static final ImmutableBiMap<Object, Object> EMPTY_IMMUTABLE_BIMAP = new ImmutableBiMap.EmptyBiMap();


   ImmutableBiMap() {}

   public static <K extends Object, V extends Object> ImmutableBiMap.Builder<K, V> builder() {
      return new ImmutableBiMap.Builder();
   }

   public static <K extends Object, V extends Object> ImmutableBiMap<K, V> copyOf(Map<? extends K, ? extends V> var0) {
      Object var1;
      if(var0 instanceof ImmutableBiMap) {
         var1 = (ImmutableBiMap)var0;
      } else if(var0.isEmpty()) {
         var1 = of();
      } else {
         ImmutableMap var2 = ImmutableMap.copyOf(var0);
         var1 = new RegularImmutableBiMap(var2);
      }

      return (ImmutableBiMap)var1;
   }

   public static <K extends Object, V extends Object> ImmutableBiMap<K, V> of() {
      return EMPTY_IMMUTABLE_BIMAP;
   }

   public static <K extends Object, V extends Object> ImmutableBiMap<K, V> of(K var0, V var1) {
      ImmutableMap var2 = ImmutableMap.of(var0, var1);
      return new RegularImmutableBiMap(var2);
   }

   public static <K extends Object, V extends Object> ImmutableBiMap<K, V> of(K var0, V var1, K var2, V var3) {
      ImmutableMap var4 = ImmutableMap.of(var0, var1, var2, var3);
      return new RegularImmutableBiMap(var4);
   }

   public static <K extends Object, V extends Object> ImmutableBiMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      ImmutableMap var6 = ImmutableMap.of(var0, var1, var2, var3, var4, var5);
      return new RegularImmutableBiMap(var6);
   }

   public static <K extends Object, V extends Object> ImmutableBiMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      ImmutableMap var8 = ImmutableMap.of(var0, var1, var2, var3, var4, var5, var6, var7);
      return new RegularImmutableBiMap(var8);
   }

   public static <K extends Object, V extends Object> ImmutableBiMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      ImmutableMap var10 = ImmutableMap.of(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
      return new RegularImmutableBiMap(var10);
   }

   public boolean containsKey(@Nullable Object var1) {
      return this.delegate().containsKey(var1);
   }

   public boolean containsValue(@Nullable Object var1) {
      return this.inverse().containsKey(var1);
   }

   abstract ImmutableMap<K, V> delegate();

   public ImmutableSet<Entry<K, V>> entrySet() {
      return this.delegate().entrySet();
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 != this && !this.delegate().equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public V forcePut(K var1, V var2) {
      throw new UnsupportedOperationException();
   }

   public V get(@Nullable Object var1) {
      return this.delegate().get(var1);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public abstract ImmutableBiMap<V, K> inverse();

   public boolean isEmpty() {
      return this.delegate().isEmpty();
   }

   public ImmutableSet<K> keySet() {
      return this.delegate().keySet();
   }

   public int size() {
      return this.delegate().size();
   }

   public String toString() {
      return this.delegate().toString();
   }

   public ImmutableSet<V> values() {
      return this.inverse().keySet();
   }

   Object writeReplace() {
      return new ImmutableBiMap.SerializedForm(this);
   }

   public static final class Builder<K extends Object, V extends Object> extends ImmutableMap.Builder<K, V> {

      public Builder() {}

      public ImmutableBiMap<K, V> build() {
         Object var1;
         if(super.build().isEmpty()) {
            var1 = ImmutableBiMap.EMPTY_IMMUTABLE_BIMAP;
         } else {
            ImmutableMap var2 = super.build();
            var1 = new RegularImmutableBiMap(var2);
         }

         return (ImmutableBiMap)var1;
      }

      public ImmutableBiMap.Builder<K, V> put(K var1, V var2) {
         super.put(var1, var2);
         return this;
      }

      public ImmutableBiMap.Builder<K, V> putAll(Map<? extends K, ? extends V> var1) {
         super.putAll(var1);
         return this;
      }
   }

   private static class SerializedForm extends ImmutableMap.SerializedForm {

      private static final long serialVersionUID;


      SerializedForm(ImmutableBiMap<?, ?> var1) {
         super(var1);
      }

      Object readResolve() {
         ImmutableBiMap.Builder var1 = new ImmutableBiMap.Builder();
         return this.createMap(var1);
      }
   }

   static class EmptyBiMap extends ImmutableBiMap<Object, Object> {

      EmptyBiMap() {}

      ImmutableMap<Object, Object> delegate() {
         return ImmutableMap.of();
      }

      public ImmutableBiMap<Object, Object> inverse() {
         return this;
      }

      Object readResolve() {
         return ImmutableBiMap.EMPTY_IMMUTABLE_BIMAP;
      }
   }
}
