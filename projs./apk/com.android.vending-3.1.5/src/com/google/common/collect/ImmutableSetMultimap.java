package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.EmptyImmutableSetMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Serialization;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public class ImmutableSetMultimap<K extends Object, V extends Object> extends ImmutableMultimap<K, V> implements SetMultimap<K, V> {

   private static final long serialVersionUID;
   private transient ImmutableSet<Entry<K, V>> entries;


   ImmutableSetMultimap(ImmutableMap<K, ImmutableSet<V>> var1, int var2) {
      super(var1, var2);
   }

   public static <K extends Object, V extends Object> ImmutableSetMultimap.Builder<K, V> builder() {
      return new ImmutableSetMultimap.Builder();
   }

   public static <K extends Object, V extends Object> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> var0) {
      ImmutableSetMultimap var1;
      if(var0.isEmpty()) {
         var1 = of();
      } else if(var0 instanceof ImmutableSetMultimap) {
         var1 = (ImmutableSetMultimap)var0;
      } else {
         ImmutableMap.Builder var2 = ImmutableMap.builder();
         int var3 = 0;
         Iterator var4 = var0.asMap().entrySet().iterator();

         while(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            Object var6 = var5.getKey();
            ImmutableSet var7 = ImmutableSet.copyOf((Iterable)((Collection)var5.getValue()));
            if(!var7.isEmpty()) {
               var2.put(var6, var7);
               int var9 = var7.size();
               var3 += var9;
            }
         }

         ImmutableMap var10 = var2.build();
         var1 = new ImmutableSetMultimap(var10, var3);
      }

      return var1;
   }

   public static <K extends Object, V extends Object> ImmutableSetMultimap<K, V> of() {
      return EmptyImmutableSetMultimap.INSTANCE;
   }

   public static <K extends Object, V extends Object> ImmutableSetMultimap<K, V> of(K var0, V var1) {
      ImmutableSetMultimap.Builder var2 = builder();
      var2.put(var0, var1);
      return var2.build();
   }

   public static <K extends Object, V extends Object> ImmutableSetMultimap<K, V> of(K var0, V var1, K var2, V var3) {
      ImmutableSetMultimap.Builder var4 = builder();
      var4.put(var0, var1);
      var4.put(var2, var3);
      return var4.build();
   }

   public static <K extends Object, V extends Object> ImmutableSetMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      ImmutableSetMultimap.Builder var6 = builder();
      var6.put(var0, var1);
      var6.put(var2, var3);
      var6.put(var4, var5);
      return var6.build();
   }

   public static <K extends Object, V extends Object> ImmutableSetMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      ImmutableSetMultimap.Builder var8 = builder();
      var8.put(var0, var1);
      var8.put(var2, var3);
      var8.put(var4, var5);
      var8.put(var6, var7);
      return var8.build();
   }

   public static <K extends Object, V extends Object> ImmutableSetMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      ImmutableSetMultimap.Builder var10 = builder();
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

            ImmutableSet var13 = ImmutableSet.of(var10);
            int var14 = var13.size();
            int var15 = var10.length;
            if(var14 != var15) {
               String var16 = "Duplicate key-value pairs exist for key " + var7;
               throw new InvalidObjectException(var16);
            }

            var4.put(var7, var13);
            var5 += var8;
         }

         ImmutableMap var18;
         try {
            var18 = var4.build();
         } catch (IllegalArgumentException var22) {
            String var21 = var22.getMessage();
            throw (InvalidObjectException)(new InvalidObjectException(var21)).initCause(var22);
         }

         ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, var18);
         ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, var5);
      }
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMultimap(this, var1);
   }

   public ImmutableSet<Entry<K, V>> entries() {
      ImmutableSet var1 = this.entries;
      if(var1 == null) {
         var1 = ImmutableSet.copyOf((Iterable)super.entries());
         this.entries = var1;
      }

      return var1;
   }

   public ImmutableSet<V> get(@Nullable K var1) {
      ImmutableSet var2 = (ImmutableSet)this.map.get(var1);
      if(var2 == null) {
         var2 = ImmutableSet.of();
      }

      return var2;
   }

   public ImmutableSet<V> removeAll(Object var1) {
      throw new UnsupportedOperationException();
   }

   public ImmutableSet<V> replaceValues(K var1, Iterable<? extends V> var2) {
      throw new UnsupportedOperationException();
   }

   public static final class Builder<K extends Object, V extends Object> extends ImmutableMultimap.Builder<K, V> {

      private final Multimap<K, V> builderMultimap;


      public Builder() {
         ImmutableSetMultimap.BuilderMultimap var1 = new ImmutableSetMultimap.BuilderMultimap();
         this.builderMultimap = var1;
      }

      public ImmutableSetMultimap<K, V> build() {
         return ImmutableSetMultimap.copyOf(this.builderMultimap);
      }

      public ImmutableSetMultimap.Builder<K, V> put(K var1, V var2) {
         Multimap var3 = this.builderMultimap;
         Object var4 = Preconditions.checkNotNull(var1);
         Object var5 = Preconditions.checkNotNull(var2);
         var3.put(var4, var5);
         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> putAll(Multimap<? extends K, ? extends V> var1) {
         Iterator var2 = var1.asMap().entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            Object var4 = var3.getKey();
            Iterable var5 = (Iterable)var3.getValue();
            this.putAll(var4, var5);
         }

         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> putAll(K var1, Iterable<? extends V> var2) {
         Multimap var3 = this.builderMultimap;
         Object var4 = Preconditions.checkNotNull(var1);
         Collection var5 = var3.get(var4);
         Iterator var6 = var2.iterator();

         while(var6.hasNext()) {
            Object var7 = Preconditions.checkNotNull(var6.next());
            var5.add(var7);
         }

         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> putAll(K var1, V ... var2) {
         List var3 = Arrays.asList(var2);
         return this.putAll(var1, (Iterable)var3);
      }
   }

   private static class BuilderMultimap<K extends Object, V extends Object> extends AbstractMultimap<K, V> {

      private static final long serialVersionUID;


      BuilderMultimap() {
         LinkedHashMap var1 = new LinkedHashMap();
         super(var1);
      }

      Collection<V> createCollection() {
         return Sets.newLinkedHashSet();
      }
   }
}
