package com.google.common.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public abstract class AbstractFuture<V extends Object> implements Future<V> {

   private final AbstractFuture.Sync<V> sync;


   public AbstractFuture() {
      AbstractFuture.Sync var1 = new AbstractFuture.Sync();
      this.sync = var1;
   }

   protected final boolean cancel() {
      boolean var1 = this.sync.cancel();
      if(var1) {
         this.done();
      }

      return var1;
   }

   public boolean cancel(boolean var1) {
      return false;
   }

   protected void done() {}

   public V get() throws InterruptedException, ExecutionException {
      return this.sync.get();
   }

   public V get(long var1, TimeUnit var3) throws InterruptedException, TimeoutException, ExecutionException {
      AbstractFuture.Sync var4 = this.sync;
      long var5 = var3.toNanos(var1);
      return var4.get(var5);
   }

   public boolean isCancelled() {
      return this.sync.isCancelled();
   }

   public boolean isDone() {
      return this.sync.isDone();
   }

   protected boolean set(V var1) {
      boolean var2 = this.sync.set(var1);
      if(var2) {
         this.done();
      }

      return var2;
   }

   protected boolean setException(Throwable var1) {
      boolean var2 = this.sync.setException(var1);
      if(var2) {
         this.done();
      }

      if(var1 instanceof Error) {
         throw (Error)var1;
      } else {
         return var2;
      }
   }

   static final class Sync<V extends Object> extends AbstractQueuedSynchronizer {

      static final int CANCELLED = 4;
      static final int COMPLETED = 2;
      static final int COMPLETING = 1;
      static final int RUNNING;
      private static final long serialVersionUID;
      private ExecutionException exception;
      private V value;


      Sync() {}

      private boolean complete(V var1, Throwable var2, int var3) {
         byte var4 = 0;
         if(this.compareAndSetState(var4, 1)) {
            this.value = var1;
            ExecutionException var5;
            if(var2 == null) {
               var5 = null;
            } else {
               var5 = new ExecutionException(var2);
            }

            this.exception = var5;
            this.releaseShared(var3);
            var4 = 1;
         }

         return (boolean)var4;
      }

      private V getValue() throws CancellationException, ExecutionException {
         int var1 = this.getState();
         switch(var1) {
         case 2:
            if(this.exception != null) {
               throw this.exception;
            }

            return this.value;
         case 3:
         default:
            String var2 = "Error, synchronizer in invalid state: " + var1;
            throw new IllegalStateException(var2);
         case 4:
            throw new CancellationException("Task was cancelled.");
         }
      }

      boolean cancel() {
         return this.complete((Object)null, (Throwable)null, 4);
      }

      V get() throws CancellationException, ExecutionException, InterruptedException {
         this.acquireSharedInterruptibly(-1);
         return this.getValue();
      }

      V get(long var1) throws TimeoutException, CancellationException, ExecutionException, InterruptedException {
         if(!this.tryAcquireSharedNanos(-1, var1)) {
            throw new TimeoutException("Timeout waiting for task.");
         } else {
            return this.getValue();
         }
      }

      boolean isCancelled() {
         boolean var1;
         if(this.getState() == 4) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      boolean isDone() {
         boolean var1;
         if((this.getState() & 6) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      boolean set(V var1) {
         return this.complete(var1, (Throwable)null, 2);
      }

      boolean setException(Throwable var1) {
         return this.complete((Object)null, var1, 2);
      }

      protected int tryAcquireShared(int var1) {
         byte var2;
         if(this.isDone()) {
            var2 = 1;
         } else {
            var2 = -1;
         }

         return var2;
      }

      protected boolean tryReleaseShared(int var1) {
         this.setState(var1);
         return true;
      }
   }
}
