package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {

   private final ThreadFactory factory;


   public DaemonThreadFactory(ThreadFactory var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      this.factory = var1;
   }

   public Thread newThread(Runnable var1) {
      Thread var2 = this.factory.newThread(var1);
      var2.setDaemon((boolean)1);
      return var2;
   }
}
