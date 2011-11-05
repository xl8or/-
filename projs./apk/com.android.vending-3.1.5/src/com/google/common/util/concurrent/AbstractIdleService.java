package com.google.common.util.concurrent;

import com.google.common.base.Service;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.AbstractService;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public abstract class AbstractIdleService implements Service {

   private final Service delegate;


   public AbstractIdleService() {
      AbstractIdleService.1 var1 = new AbstractIdleService.1();
      this.delegate = var1;
   }

   protected Executor executor(Service.State var1) {
      return new AbstractIdleService.2(var1);
   }

   public final boolean isRunning() {
      return this.delegate.isRunning();
   }

   protected abstract void shutDown() throws Exception;

   public final Future<Service.State> start() {
      return this.delegate.start();
   }

   public final Service.State startAndWait() {
      return this.delegate.startAndWait();
   }

   protected abstract void startUp() throws Exception;

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

   class 1 extends AbstractService {

      1() {}

      protected final void doStart() {
         AbstractIdleService var1 = AbstractIdleService.this;
         Service.State var2 = Service.State.STARTING;
         Executor var3 = var1.executor(var2);
         AbstractIdleService.1.1 var4 = new AbstractIdleService.1.1();
         var3.execute(var4);
      }

      protected final void doStop() {
         AbstractIdleService var1 = AbstractIdleService.this;
         Service.State var2 = Service.State.STOPPING;
         Executor var3 = var1.executor(var2);
         AbstractIdleService.1.2 var4 = new AbstractIdleService.1.2();
         var3.execute(var4);
      }

      class 2 implements Runnable {

         2() {}

         public void run() {
            try {
               AbstractIdleService.this.shutDown();
               1.this.notifyStopped();
            } catch (Throwable var2) {
               1.this.notifyFailed(var2);
               throw Throwables.propagate(var2);
            }
         }
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            try {
               AbstractIdleService.this.startUp();
               1.this.notifyStarted();
            } catch (Throwable var2) {
               1.this.notifyFailed(var2);
               throw Throwables.propagate(var2);
            }
         }
      }
   }

   class 2 implements Executor {

      // $FF: synthetic field
      final Service.State val$state;


      2(Service.State var2) {
         this.val$state = var2;
      }

      public void execute(Runnable var1) {
         StringBuilder var2 = new StringBuilder();
         String var3 = AbstractIdleService.this.toString();
         StringBuilder var4 = var2.append(var3).append(" ");
         Service.State var5 = this.val$state;
         String var6 = var4.append(var5).toString();
         (new Thread(var1, var6)).start();
      }
   }
}
