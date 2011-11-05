package com.google.common.util.concurrent;

import com.google.common.collect.ForwardingObject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class ForwardingFuture<V extends Object> extends ForwardingObject implements Future<V> {

   public ForwardingFuture() {}

   public boolean cancel(boolean var1) {
      return this.delegate().cancel(var1);
   }

   protected abstract Future<V> delegate();

   public V get() throws InterruptedException, ExecutionException {
      return this.delegate().get();
   }

   public V get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
      return this.delegate().get(var1, var3);
   }

   public boolean isCancelled() {
      return this.delegate().isCancelled();
   }

   public boolean isDone() {
      return this.delegate().isDone();
   }
}
