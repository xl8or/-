package com.google.common.util.concurrent;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractCheckedFuture<V extends Object, E extends Exception> implements CheckedFuture<V, E> {

   protected final ListenableFuture<V> delegate;


   protected AbstractCheckedFuture(ListenableFuture<V> var1) {
      this.delegate = var1;
   }

   public void addListener(Runnable var1, Executor var2) {
      this.delegate.addListener(var1, var2);
   }

   public boolean cancel(boolean var1) {
      return this.delegate.cancel(var1);
   }

   public V checkedGet() throws E {
      try {
         Object var1 = this.get();
         return var1;
      } catch (InterruptedException var6) {
         boolean var3 = this.cancel((boolean)1);
         throw this.mapException(var6);
      } catch (CancellationException var7) {
         throw this.mapException(var7);
      } catch (ExecutionException var8) {
         throw this.mapException(var8);
      }
   }

   public V checkedGet(long var1, TimeUnit var3) throws TimeoutException, E {
      try {
         Object var4 = this.get(var1, var3);
         return var4;
      } catch (InterruptedException var9) {
         boolean var6 = this.cancel((boolean)1);
         throw this.mapException(var9);
      } catch (CancellationException var10) {
         throw this.mapException(var10);
      } catch (ExecutionException var11) {
         throw this.mapException(var11);
      }
   }

   public V get() throws InterruptedException, ExecutionException {
      return this.delegate.get();
   }

   public V get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
      return this.delegate.get(var1, var3);
   }

   public boolean isCancelled() {
      return this.delegate.isCancelled();
   }

   public boolean isDone() {
      return this.delegate.isDone();
   }

   protected abstract E mapException(Exception var1);
}
