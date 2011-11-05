package com.google.common.util.concurrent;

import com.google.common.base.Service;
import com.google.common.util.concurrent.AbstractService;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public abstract class AbstractExecutionThreadService implements Service {

   private final Service delegate;


   public AbstractExecutionThreadService() {
      AbstractExecutionThreadService.1 var1 = new AbstractExecutionThreadService.1();
      this.delegate = var1;
   }

   protected Executor executor() {
      return new AbstractExecutionThreadService.2();
   }

   public final boolean isRunning() {
      return this.delegate.isRunning();
   }

   protected abstract void run() throws Exception;

   protected void shutDown() throws Exception {}

   public final Future<Service.State> start() {
      return this.delegate.start();
   }

   public final Service.State startAndWait() {
      return this.delegate.startAndWait();
   }

   protected void startUp() throws Exception {}

   public final Service.State state() {
      return this.delegate.state();
   }

   public final Future<Service.State> stop() {
      return this.delegate.stop();
   }

   public final Service.State stopAndWait() {
      return this.delegate.stopAndWait();
   }

   public String toString() {
      return this.getClass().getSimpleName();
   }

   protected void triggerShutdown() {}

   class 2 implements Executor {

      2() {}

      public void execute(Runnable var1) {
         String var2 = AbstractExecutionThreadService.this.toString();
         (new Thread(var1, var2)).start();
      }
   }

   class 1 extends AbstractService {

      1() {}

      protected final void doStart() {
         Executor var1 = AbstractExecutionThreadService.this.executor();
         AbstractExecutionThreadService.1.1 var2 = new AbstractExecutionThreadService.1.1();
         var1.execute(var2);
      }

      protected void doStop() {
         AbstractExecutionThreadService.this.triggerShutdown();
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            // $FF: Couldn't be decompiled
         }
      }
   }
}
