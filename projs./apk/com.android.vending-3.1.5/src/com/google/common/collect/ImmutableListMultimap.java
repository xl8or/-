package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.EmptyImmutableListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public class ImmutableListMultimap<K extends Object, V extends Object> extends ImmutableMultimap<K, V> implements ListMultimap<K, V> {

   private static final long serialVersionUID;


   ImmutableListMultimap(ImmutableMap<K, ImmutableList<V>> var1, int var2) {
      super(var1, var2);
   }

   public static <K extends Object, V extends Object> ImmutableListMultimap.Builder<K, V> builder() {
      return new ImmutableListMultimap.Builder();
   }

   public static <K extends Object, V extends Object> ImmutableListMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> var0) {
      ImmutableListMultimap var1;
      if(var0.isEmpty()) {
         var1 = of();
      } else if(var0 instanceof ImmutableListMultimap) {
         var1 = (ImmutableListMultimap)var0;
      } else {
         ImmutableMap.Builder var2 = ImmutableMap.builder();
         int var3 = 0;
         Iterator var4 = var0.asMap().entrySet().iterator();

         while(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            ImmutableList var6 = ImmutableList.copyOf((Collection)var5.getValue());
            if(!var6.isEmpty()) {
               Object var7 = var5.getKey();
               var2.put(var7, var6);
               int var9 = var6.size();
               var3 += var9;
            }
         }

         ImmutableMap var10 = var2.build();
         var1 = new ImmutableListMultimap(var10, var3);
      }

      return var1;
   }

   public static <K extends Object, V extends Object> ImmutableListMultimap<K, V> of() {
      return EmptyImmutableListMultimap.INSTANCE;
   }

   public static <K extends Object, V extends Object> ImmutableListMultimap<K, V> of(K var0, V var1) {
      ImmutableListMultimap.Builder var2 = builder();
      var2.put(var0, var1);
      return var2.build();
   }

   public static <K extends Object, V extends Object> ImmutableListMultimap<K, V> of(K var0, V var1, K var2, V var3) {
      ImmutableListMultimap.Builder var4 = builder();
      var4.put(var0, var1);
      var4.put(var2, var3);
      return var4.build();
   }

   public static <K extends Object, V extends Object> ImmutableListMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      ImmutableListMultimap.Builder var6 = builder();
      var6.put(var0, var1);
      var6.put(var2, var3);
      var6.put(var4, var5);
      return var6.build();
   }

   public static <K extends Object, V extends Object> ImmutableListMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      ImmutableListMultimap.Builder var8 = builder();
      var8.put(var0, var1);
      var8.put(var2, var3);
      var8.put(var4, var5);
      var8.put(var6, var7);
      return var8.build();
   }

   public static <K extends Object, V extends Object> ImmutableListMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      ImmutableListMultimap.Builder var10 = builder();
      var10.put(var0, var1);
      var10.put(var2, var3);
      var10.put(var4, var5);
      var10.put(var6, var7);
      var10.put(var8, var9);
      return var10.build();
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = var1.readInt();
      if(var2 < 0) {
         String var3 = "Invalid key count " + var2;
         throw new InvalidObjectException(var3);
      } else {
         ImmutableMap.Builder var4 = ImmutableMap.builder();
         int var5 = 0;

         for(int var6 = 0; var6 < var2; ++var6) {
            Object var7 = var1.readObject();
            int var8 = var1.readInt();
            if(var8 <= 0) {
               String var9 = "Invalid value count " + var8;
               throw new InvalidObjectException(var9);
            }

            Object[] var10 = new Object[var8];

            for(int var11 = 0; var11 < var8; ++var11) {
               Object var12 = var1.readObject();
               var10[var11] = var12;
            }

            ImmutableList var13 = ImmutableList.of(var10);
            var4.put(var7, var13);
            var5 += var8;
         }

         ImmutableMap var15;
         try {
            var15 = var4.build();
         } catch (IllegalArgumentException var19) {
            String var18 = var19.getMessage();
            throw (InvalidObjectException)(new InvalidObjectException(var18)).initCause(var19);
         }

         ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, var15);
         ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, var5);
      }
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMultimap(this, var1);
   }

   public ImmutableList<V> get(@Nullable K var1) {
      ImmutableList var2 = (ImmutableList)this.map.get(var1);
      if(var2 == null) {
         var2 = ImmutableList.of();
      }

      return var2;
   }

   public ImmutableList<V> removeAll(Object var1) {
      throw new UnsupportedOperationException();
   }

   public ImmutableList<V> replaceValues(K var1, Iterable<? extends V> var2) {
      throw new UnsupportedOperationException();
   }

   public static final class Builder<K extends Object, V extends Object> extends ImmutableMultimap.Builder<K, V> {

      public Builder() {}

      public ImmutableListMultimap<K, V> build() {
         return (ImmutableListMultimap)super.build();
      }

      public ImmutableListMultimap.Builder<K, V> put(K var1, V var2) {
         super.put(var1, var2);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> putAll(Multimap<? extends K, ? extends V> var1) {
         super.putAll(var1);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> putAll(K var1, Iterable<? extends V> var2) {
         super.putAll(var1, var2);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> putAll(K var1, V ... var2) {
         super.putAll(var1, var2);
         return this;
      }
   }
}
