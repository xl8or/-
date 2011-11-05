package com.google.common.base;

import java.util.concurrent.Future;

public interface Service {

   boolean isRunning();

   Future<Service.State> start();

   Service.State startAndWait();

   Service.State state();

   Future<Service.State> stop();

   Service.State stopAndWait();

   public static enum State {

      // $FF: synthetic field
      private static final Service.State[] $VALUES;
      FAILED("FAILED", 5),
      NEW("NEW", 0),
      RUNNING("RUNNING", 2),
      STARTING("STARTING", 1),
      STOPPING("STOPPING", 3),
      TERMINATED("TERMINATED", 4);


      static {
         Service.State[] var0 = new Service.State[6];
         Service.State var1 = NEW;
         var0[0] = var1;
         Service.State var2 = STARTING;
         var0[1] = var2;
         Service.State var3 = RUNNING;
         var0[2] = var3;
         Service.State var4 = STOPPING;
         var0[3] = var4;
         Service.State var5 = TERMINATED;
         var0[4] = var5;
         Service.State var6 = FAILED;
         var0[5] = var6;
         $VALUES = var0;
      }

      private State(String var1, int var2) {}
   }
}
