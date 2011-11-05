package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractCheckedFuture;
import com.google.common.util.concurrent.AbstractListenableFuture;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ExecutionList;
import com.google.common.util.concurrent.Executors;
import com.google.common.util.concurrent.ForwardingFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.UninterruptibleFuture;
import com.google.common.util.concurrent.ValueFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

public class Futures {

   private Futures() {}

   public static <I extends Object, O extends Object> ListenableFuture<O> chain(ListenableFuture<I> var0, Function<? super I, ? extends ListenableFuture<? extends O>> var1) {
      ExecutorService var2 = Executors.sameThreadExecutor();
      return chain(var0, var1, var2);
   }

   public static <I extends Object, O extends Object> ListenableFuture<O> chain(ListenableFuture<I> var0, Function<? super I, ? extends ListenableFuture<? extends O>> var1, Executor var2) {
      Futures.ChainingListenableFuture var3 = new Futures.ChainingListenableFuture(var1, var0, (Futures.1)null);
      var0.addListener(var3, var2);
      return var3;
   }

   public static <I extends Object, O extends Object> ListenableFuture<O> compose(ListenableFuture<I> var0, Function<? super I, ? extends O> var1) {
      ExecutorService var2 = Executors.sameThreadExecutor();
      return compose(var0, var1, var2);
   }

   public static <I extends Object, O extends Object> ListenableFuture<O> compose(ListenableFuture<I> var0, Function<? super I, ? extends O> var1, Executor var2) {
      Futures.4 var3 = new Futures.4(var1);
      return chain(var0, var3, var2);
   }

   public static <I extends Object, O extends Object> Future<O> compose(Future<I> var0, Function<? super I, ? extends O> var1) {
      return new Futures.5(var0, var1);
   }

   public static <T extends Object, E extends Exception> CheckedFuture<T, E> immediateCheckedFuture(@Nullable T var0) {
      ValueFuture var1 = ValueFuture.create();
      var1.set(var0);
      Futures.2 var3 = new Futures.2();
      return makeChecked(var1, var3);
   }

   public static <T extends Object, E extends Exception> CheckedFuture<T, E> immediateFailedCheckedFuture(E var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      ListenableFuture var2 = immediateFailedFuture(var0);
      Futures.3 var3 = new Futures.3(var0);
      return makeChecked(var2, var3);
   }

   public static <T extends Object> ListenableFuture<T> immediateFailedFuture(Throwable var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      ValueFuture var2 = ValueFuture.create();
      var2.setException(var0);
      return var2;
   }

   public static <T extends Object> ListenableFuture<T> immediateFuture(@Nullable T var0) {
      ValueFuture var1 = ValueFuture.create();
      var1.set(var0);
      return var1;
   }

   public static <T extends Object, E extends Exception> CheckedFuture<T, E> makeChecked(Future<T> var0, Function<Exception, E> var1) {
      ListenableFuture var2 = makeListenable(var0);
      return new Futures.MappingCheckedFuture(var2, var1);
   }

   public static <T extends Object> ListenableFuture<T> makeListenable(Future<T> var0) {
      Object var1;
      if(var0 instanceof ListenableFuture) {
         var1 = (ListenableFuture)var0;
      } else {
         var1 = new Futures.ListenableFutureAdapter(var0);
      }

      return (ListenableFuture)var1;
   }

   public static <V extends Object> UninterruptibleFuture<V> makeUninterruptible(Future<V> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      Object var2;
      if(var0 instanceof UninterruptibleFuture) {
         var2 = (UninterruptibleFuture)var0;
      } else {
         var2 = new Futures.1(var0);
      }

      return (UninterruptibleFuture)var2;
   }

   private static class MappingCheckedFuture<T extends Object, E extends Exception> extends AbstractCheckedFuture<T, E> {

      final Function<Exception, E> mapper;


      MappingCheckedFuture(ListenableFuture<T> var1, Function<Exception, E> var2) {
         super(var1);
         this.mapper = var2;
      }

      protected E mapException(Exception var1) {
         return (Exception)this.mapper.apply(var1);
      }
   }

   private static class ListenableFutureAdapter<T extends Object> extends ForwardingFuture<T> implements ListenableFuture<T> {

      private static final Executor adapterExecutor = java.util.concurrent.Executors.newCachedThreadPool();
      private final Future<T> delegate;
      private final ExecutionList executionList;
      private final AtomicBoolean hasListeners;


      ListenableFutureAdapter(Future<T> var1) {
         ExecutionList var2 = new ExecutionList();
         this.executionList = var2;
         AtomicBoolean var3 = new AtomicBoolean((boolean)0);
         this.hasListeners = var3;
         this.delegate = var1;
      }

      public void addListener(Runnable var1, Executor var2) {
         if(!this.hasListeners.get() && this.hasListeners.compareAndSet((boolean)0, (boolean)1)) {
            Executor var3 = adapterExecutor;
            Futures.ListenableFutureAdapter.1 var4 = new Futures.ListenableFutureAdapter.1();
            var3.execute(var4);
         }

         this.executionList.add(var1, var2);
      }

      protected Future<T> delegate() {
         return this.delegate;
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            try {
               Object var1 = ListenableFutureAdapter.this.delegate.get();
            } catch (CancellationException var5) {
               ;
            } catch (InterruptedException var6) {
               throw new IllegalStateException("Adapter thread interrupted!", var6);
            } catch (ExecutionException var7) {
               ;
            }

            ListenableFutureAdapter.this.executionList.run();
         }
      }
   }

   private static class ChainingListenableFuture<I extends Object, O extends Object> extends AbstractListenableFuture<O> implements Runnable {

      private final Function<? super I, ? extends ListenableFuture<? extends O>> function;
      private final UninterruptibleFuture<? extends I> inputFuture;


      private ChainingListenableFuture(Function<? super I, ? extends ListenableFuture<? extends O>> var1, ListenableFuture<? extends I> var2) {
         this.function = var1;
         UninterruptibleFuture var3 = Futures.makeUninterruptible(var2);
         this.inputFuture = var3;
      }

      // $FF: synthetic method
      ChainingListenableFuture(Function var1, ListenableFuture var2, Futures.1 var3) {
         this(var1, var2);
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final ListenableFuture val$outputFuture;


         1(ListenableFuture var2) {
            this.val$outputFuture = var2;
         }

         public void run() {
            try {
               Futures.ChainingListenableFuture var1 = ChainingListenableFuture.this;
               Object var2 = Futures.makeUninterruptible(this.val$outputFuture).get();
               var1.set(var2);
            } catch (ExecutionException var8) {
               Futures.ChainingListenableFuture var5 = ChainingListenableFuture.this;
               Throwable var6 = var8.getCause();
               var5.setException(var6);
            }
         }
      }
   }

   static class 4 implements Function<I, ListenableFuture<O>> {

      // $FF: synthetic field
      final Function val$function;


      4(Function var1) {
         this.val$function = var1;
      }

      public ListenableFuture<O> apply(I var1) {
         return Futures.immediateFuture(this.val$function.apply(var1));
      }
   }

   static class 3 implements Function<Exception, E> {

      // $FF: synthetic field
      final Exception val$exception;


      3(Exception var1) {
         this.val$exception = var1;
      }

      public E apply(Exception var1) {
         return this.val$exception;
      }
   }

   static class 5 implements Future<O> {

      private final Object lock;
      private boolean set;
      // $FF: synthetic field
      final Function val$function;
      // $FF: synthetic field
      final Future val$future;
      private O value;


      5(Future var1, Function var2) {
         this.val$future = var1;
         this.val$function = var2;
         Object var3 = new Object();
         this.lock = var3;
         this.set = (boolean)0;
         this.value = null;
      }

      private O apply(I var1) {
         Object var2 = this.lock;
         synchronized(var2) {
            if(!this.set) {
               Object var3 = this.val$function.apply(var1);
               this.value = var3;
               this.set = (boolean)1;
            }

            Object var4 = this.value;
            return var4;
         }
      }

      public boolean cancel(boolean var1) {
         return this.val$future.cancel(var1);
      }

      public O get() throws InterruptedException, ExecutionException {
         Object var1 = this.val$future.get();
         return this.apply(var1);
      }

      public O get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
         Object var4 = this.val$future.get(var1, var3);
         return this.apply(var4);
      }

      public boolean isCancelled() {
         return this.val$future.isCancelled();
      }

      public boolean isDone() {
         return this.val$future.isDone();
      }
   }

   static class 2 implements Function<Exception, E> {

      2() {}

      public E apply(Exception var1) {
         throw new AssertionError("impossible");
      }
   }

   static class 1 implements UninterruptibleFuture<V> {

      // $FF: synthetic field
      final Future val$future;


      1(Future var1) {
         this.val$future = var1;
      }

      public boolean cancel(boolean var1) {
         return this.val$future.cancel(var1);
      }

      public V get() throws ExecutionException {
         boolean var1 = false;

         Object var2;
         while(true) {
            boolean var8 = false;

            try {
               var8 = true;
               var2 = this.val$future.get();
               var8 = false;
               break;
            } catch (InterruptedException var9) {
               var8 = false;
            } finally {
               if(var8) {
                  if(var1) {
                     Thread.currentThread().interrupt();
                  }

               }
            }

            var1 = true;
         }

         if(var1) {
            Thread.currentThread().interrupt();
         }

         return var2;
      }

      public V get(long param1, TimeUnit param3) throws TimeoutException, ExecutionException {
         // $FF: Couldn't be decompiled
      }

      public boolean isCancelled() {
         return this.val$future.isCancelled();
      }

      public boolean isDone() {
         return this.val$future.isDone();
      }
   }
}
