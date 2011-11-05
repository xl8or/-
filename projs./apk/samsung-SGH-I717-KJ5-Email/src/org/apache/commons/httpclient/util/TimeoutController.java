package org.apache.commons.httpclient.util;


public final class TimeoutController {

   private TimeoutController() {}

   public static void execute(Runnable var0, long var1) throws TimeoutController.TimeoutException {
      Thread var3 = new Thread(var0, "Timeout guard");
      var3.setDaemon((boolean)1);
      execute(var3, var1);
   }

   public static void execute(Thread var0, long var1) throws TimeoutController.TimeoutException {
      var0.start();

      try {
         var0.join(var1);
      } catch (InterruptedException var4) {
         ;
      }

      if(var0.isAlive()) {
         var0.interrupt();
         throw new TimeoutController.TimeoutException();
      }
   }

   public static class TimeoutException extends Exception {

      public TimeoutException() {}
   }
}
