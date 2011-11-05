package com.google.common.base;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public final class Suppliers {

   private Suppliers() {}

   public static <F extends Object, T extends Object> Supplier<T> compose(Function<? super F, T> var0, Supplier<F> var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new Suppliers.SupplierComposition(var0, var1);
   }

   public static <T extends Object> Supplier<T> memoize(Supplier<T> var0) {
      Supplier var1 = (Supplier)Preconditions.checkNotNull(var0);
      return new Suppliers.MemoizingSupplier(var1);
   }

   public static <T extends Object> Supplier<T> memoizeWithExpiration(Supplier<T> var0, long var1, TimeUnit var3) {
      return new Suppliers.ExpiringMemoizingSupplier(var0, var1, var3);
   }

   public static <T extends Object> Supplier<T> ofInstance(@Nullable T var0) {
      return new Suppliers.SupplierOfInstance(var0);
   }

   public static <T extends Object> Supplier<T> synchronizedSupplier(Supplier<T> var0) {
      Supplier var1 = (Supplier)Preconditions.checkNotNull(var0);
      return new Suppliers.ThreadSafeSupplier(var1);
   }

   private static class SupplierOfInstance<T extends Object> implements Supplier<T>, Serializable {

      private static final long serialVersionUID;
      final T instance;


      SupplierOfInstance(T var1) {
         this.instance = var1;
      }

      public T get() {
         return this.instance;
      }
   }

   private static class ThreadSafeSupplier<T extends Object> implements Supplier<T>, Serializable {

      private static final long serialVersionUID;
      final Supplier<T> delegate;


      ThreadSafeSupplier(Supplier<T> var1) {
         this.delegate = var1;
      }

      public T get() {
         Supplier var1 = this.delegate;
         synchronized(var1) {
            Object var2 = this.delegate.get();
            return var2;
         }
      }
   }

   @VisibleForTesting
   static class MemoizingSupplier<T extends Object> implements Supplier<T>, Serializable {

      private static final long serialVersionUID;
      final Supplier<T> delegate;
      transient boolean initialized;
      transient T value;


      MemoizingSupplier(Supplier<T> var1) {
         this.delegate = var1;
      }

      public T get() {
         synchronized(this){}

         Object var2;
         try {
            if(!this.initialized) {
               Object var1 = this.delegate.get();
               this.value = var1;
               this.initialized = (boolean)1;
            }

            var2 = this.value;
         } finally {
            ;
         }

         return var2;
      }
   }

   @VisibleForTesting
   static class ExpiringMemoizingSupplier<T extends Object> implements Supplier<T>, Serializable {

      private static final long serialVersionUID;
      final Supplier<T> delegate;
      final long durationNanos;
      transient long expirationNanos;
      transient boolean initialized;
      transient T value;


      ExpiringMemoizingSupplier(Supplier<T> var1, long var2, TimeUnit var4) {
         Supplier var5 = (Supplier)Preconditions.checkNotNull(var1);
         this.delegate = var5;
         long var6 = var4.toNanos(var2);
         this.durationNanos = var6;
         byte var8;
         if(var2 > 0L) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         Preconditions.checkArgument((boolean)var8);
      }

      public T get() {
         synchronized(this){}

         Object var12;
         try {
            label35: {
               if(this.initialized) {
                  long var1 = System.nanoTime();
                  long var3 = this.expirationNanos;
                  if(var1 - var3 < 0L) {
                     break label35;
                  }
               }

               Object var5 = this.delegate.get();
               this.value = var5;
               this.initialized = (boolean)1;
               long var6 = System.nanoTime();
               long var8 = this.durationNanos;
               long var10 = var6 + var8;
               this.expirationNanos = var10;
            }

            var12 = this.value;
         } finally {
            ;
         }

         return var12;
      }
   }

   private static class SupplierComposition<F extends Object, T extends Object> implements Supplier<T>, Serializable {

      private static final long serialVersionUID;
      final Supplier<? extends F> first;
      final Function<? super F, ? extends T> function;


      SupplierComposition(Function<? super F, ? extends T> var1, Supplier<? extends F> var2) {
         this.function = var1;
         this.first = var2;
      }

      public T get() {
         Function var1 = this.function;
         Object var2 = this.first.get();
         return var1.apply(var2);
      }
   }
}
