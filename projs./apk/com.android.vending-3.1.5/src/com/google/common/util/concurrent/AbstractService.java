package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Service;
import com.google.common.base.Throwables;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractService implements Service {

   private final ReentrantLock lock;
   private final AbstractService.Transition shutdown;
   private boolean shutdownWhenStartupFinishes;
   private final AbstractService.Transition startup;
   private Service.State state;


   public AbstractService() {
      ReentrantLock var1 = new ReentrantLock();
      this.lock = var1;
      AbstractService.Transition var2 = new AbstractService.Transition((AbstractService.1)null);
      this.startup = var2;
      AbstractService.Transition var3 = new AbstractService.Transition((AbstractService.1)null);
      this.shutdown = var3;
      Service.State var4 = Service.State.NEW;
      this.state = var4;
      this.shutdownWhenStartupFinishes = (boolean)0;
   }

   protected abstract void doStart();

   protected abstract void doStop();

   public final boolean isRunning() {
      Service.State var1 = this.state();
      Service.State var2 = Service.State.RUNNING;
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   protected final void notifyFailed(Throwable var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      this.lock.lock();

      try {
         Service.State var3 = this.state;
         Service.State var4 = Service.State.STARTING;
         if(var3 == var4) {
            this.startup.transitionFailed(var1);
            AbstractService.Transition var5 = this.shutdown;
            Exception var6 = new Exception("Service failed to start.", var1);
            var5.transitionFailed(var6);
         } else {
            Service.State var8 = this.state;
            Service.State var9 = Service.State.STOPPING;
            if(var8 == var9) {
               this.shutdown.transitionFailed(var1);
            }
         }

         Service.State var7 = Service.State.FAILED;
         this.state = var7;
      } finally {
         this.lock.unlock();
      }

   }

   protected final void notifyStarted() {
      this.lock.lock();

      try {
         Service.State var1 = this.state;
         Service.State var2 = Service.State.STARTING;
         if(var1 != var2) {
            StringBuilder var3 = (new StringBuilder()).append("Cannot notifyStarted() when the service is ");
            Service.State var4 = this.state;
            String var5 = var3.append(var4).toString();
            IllegalStateException var6 = new IllegalStateException(var5);
            this.notifyFailed(var6);
            throw var6;
         }

         Service.State var8 = Service.State.RUNNING;
         this.state = var8;
         if(this.shutdownWhenStartupFinishes) {
            Future var9 = this.stop();
         } else {
            AbstractService.Transition var10 = this.startup;
            Service.State var11 = Service.State.RUNNING;
            var10.transitionSucceeded(var11);
         }
      } finally {
         this.lock.unlock();
      }

   }

   protected final void notifyStopped() {
      this.lock.lock();

      try {
         Service.State var1 = this.state;
         Service.State var2 = Service.State.STOPPING;
         if(var1 != var2) {
            Service.State var3 = this.state;
            Service.State var4 = Service.State.RUNNING;
            if(var3 != var4) {
               StringBuilder var5 = (new StringBuilder()).append("Cannot notifyStopped() when the service is ");
               Service.State var6 = this.state;
               String var7 = var5.append(var6).toString();
               IllegalStateException var8 = new IllegalStateException(var7);
               this.notifyFailed(var8);
               throw var8;
            }
         }

         Service.State var10 = Service.State.TERMINATED;
         this.state = var10;
         AbstractService.Transition var11 = this.shutdown;
         Service.State var12 = Service.State.TERMINATED;
         var11.transitionSucceeded(var12);
      } finally {
         this.lock.unlock();
      }

   }

   public final Future<Service.State> start() {
      this.lock.lock();

      try {
         Service.State var1 = this.state;
         Service.State var2 = Service.State.NEW;
         if(var1 == var2) {
            Service.State var3 = Service.State.STARTING;
            this.state = var3;
            this.doStart();
         }
      } catch (Throwable var8) {
         this.notifyFailed(var8);
      } finally {
         this.lock.unlock();
      }

      return this.startup;
   }

   public Service.State startAndWait() {
      try {
         Service.State var1 = (Service.State)this.start().get();
         return var1;
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
         throw new RuntimeException(var3);
      } catch (ExecutionException var4) {
         throw Throwables.propagate(var4.getCause());
      }
   }

   public final Service.State state() {
      this.lock.lock();

      Service.State var3;
      try {
         if(this.shutdownWhenStartupFinishes) {
            Service.State var1 = this.state;
            Service.State var2 = Service.State.STARTING;
            if(var1 == var2) {
               var3 = Service.State.STOPPING;
               return var3;
            }
         }

         var3 = this.state;
      } finally {
         this.lock.unlock();
      }

      return var3;
   }

   public final Future<Service.State> stop() {
      this.lock.lock();

      try {
         Service.State var1 = this.state;
         Service.State var2 = Service.State.NEW;
         if(var1 == var2) {
            Service.State var3 = Service.State.TERMINATED;
            this.state = var3;
            AbstractService.Transition var4 = this.startup;
            Service.State var5 = Service.State.TERMINATED;
            var4.transitionSucceeded(var5);
            AbstractService.Transition var6 = this.shutdown;
            Service.State var7 = Service.State.TERMINATED;
            var6.transitionSucceeded(var7);
         } else {
            Service.State var8 = this.state;
            Service.State var9 = Service.State.STARTING;
            if(var8 == var9) {
               this.shutdownWhenStartupFinishes = (boolean)1;
               AbstractService.Transition var10 = this.startup;
               Service.State var11 = Service.State.STOPPING;
               var10.transitionSucceeded(var11);
            } else {
               Service.State var13 = this.state;
               Service.State var14 = Service.State.RUNNING;
               if(var13 == var14) {
                  Service.State var15 = Service.State.STOPPING;
                  this.state = var15;
                  this.doStop();
               }
            }
         }
      } catch (Throwable var19) {
         this.notifyFailed(var19);
      } finally {
         this.lock.unlock();
      }

      return this.shutdown;
   }

   public Service.State stopAndWait() {
      try {
         Service.State var1 = (Service.State)this.stop().get();
         return var1;
      } catch (ExecutionException var3) {
         throw Throwables.propagate(var3.getCause());
      } catch (InterruptedException var4) {
         Thread.currentThread().interrupt();
         throw new RuntimeException(var4);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private static class Transition implements Future<Service.State> {

      private final CountDownLatch done;
      private Throwable failureCause;
      private Service.State result;


      private Transition() {
         CountDownLatch var1 = new CountDownLatch(1);
         this.done = var1;
      }

      // $FF: synthetic method
      Transition(AbstractService.1 var1) {
         this();
      }

      private Service.State getImmediately() throws ExecutionException {
         Service.State var1 = this.result;
         Service.State var2 = Service.State.FAILED;
         if(var1 == var2) {
            Throwable var3 = this.failureCause;
            throw new ExecutionException(var3);
         } else {
            return this.result;
         }
      }

      public boolean cancel(boolean var1) {
         return false;
      }

      public Service.State get() throws InterruptedException, ExecutionException {
         this.done.await();
         return this.getImmediately();
      }

      public Service.State get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
         if(this.done.await(var1, var3)) {
            return this.getImmediately();
         } else {
            throw new TimeoutException();
         }
      }

      public boolean isCancelled() {
         return false;
      }

      public boolean isDone() {
         boolean var1;
         if(this.done.getCount() == 0L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      void transitionFailed(Throwable var1) {
         byte var2;
         if(this.result == null) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         Preconditions.checkState((boolean)var2);
         Service.State var3 = Service.State.FAILED;
         this.result = var3;
         this.failureCause = var1;
         this.done.countDown();
      }

      void transitionSucceeded(Service.State var1) {
         byte var2;
         if(this.result == null) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         Preconditions.checkState((boolean)var2);
         this.result = var1;
         this.done.countDown();
      }
   }
}
