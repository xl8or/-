package com.google.common.collect;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MutableClassToInstanceMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class ImmutableClassToInstanceMap<B extends Object> extends ForwardingMap<Class<? extends B>, B> implements ClassToInstanceMap<B> {

   private final ImmutableMap<Class<? extends B>, B> delegate;


   private ImmutableClassToInstanceMap(ImmutableMap<Class<? extends B>, B> var1) {
      this.delegate = var1;
   }

   // $FF: synthetic method
   ImmutableClassToInstanceMap(ImmutableMap var1, ImmutableClassToInstanceMap.1 var2) {
      this(var1);
   }

   public static <B extends Object> ImmutableClassToInstanceMap.Builder<B> builder() {
      return new ImmutableClassToInstanceMap.Builder();
   }

   public static <B extends Object, S extends B> ImmutableClassToInstanceMap<B> copyOf(Map<? extends Class<? extends S>, ? extends S> var0) {
      ImmutableClassToInstanceMap var1;
      if(var0 instanceof ImmutableClassToInstanceMap) {
         var1 = (ImmutableClassToInstanceMap)var0;
      } else {
         var1 = (new ImmutableClassToInstanceMap.Builder()).putAll(var0).build();
      }

      return var1;
   }

   protected Map<Class<? extends B>, B> delegate() {
      return this.delegate;
   }

   public <T extends B> T getInstance(Class<T> var1) {
      return this.delegate.get(var1);
   }

   public <T extends B> T putInstance(Class<T> var1, T var2) {
      throw new UnsupportedOperationException();
   }

   public static final class Builder<B extends Object> {

      private final ImmutableMap.Builder<Class<? extends B>, B> mapBuilder;


      public Builder() {
         ImmutableMap.Builder var1 = ImmutableMap.builder();
         this.mapBuilder = var1;
      }

      public ImmutableClassToInstanceMap<B> build() {
         ImmutableMap var1 = this.mapBuilder.build();
         return new ImmutableClassToInstanceMap(var1, (ImmutableClassToInstanceMap.1)null);
      }

      public <T extends B> ImmutableClassToInstanceMap.Builder<B> put(Class<T> var1, T var2) {
         this.mapBuilder.put(var1, var2);
         return this;
      }

      public <T extends B> ImmutableClassToInstanceMap.Builder<B> putAll(Map<? extends Class<? extends T>, ? extends T> var1) {
         Iterator var2 = var1.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            Class var4 = (Class)var3.getKey();
            Object var5 = var3.getValue();
            ImmutableMap.Builder var6 = this.mapBuilder;
            Object var7 = MutableClassToInstanceMap.cast(var4, var5);
            var6.put(var4, var7);
         }

         return this;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
