package com.google.common.util.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamingThreadFactory implements ThreadFactory {

   public static final ThreadFactory DEFAULT_FACTORY = java.util.concurrent.Executors.defaultThreadFactory();
   private final ThreadFactory backingFactory;
   private final AtomicInteger count;
   private final String format;


   public NamingThreadFactory(String var1) {
      ThreadFactory var2 = DEFAULT_FACTORY;
      this(var1, var2);
   }

   public NamingThreadFactory(String var1, ThreadFactory var2) {
      AtomicInteger var3 = new AtomicInteger(0);
      this.count = var3;
      this.format = var1;
      this.backingFactory = var2;
      String var4 = this.makeName(0);
   }

   private String makeName(int var1) {
      String var2 = this.format;
      Object[] var3 = new Object[1];
      Integer var4 = Integer.valueOf(var1);
      var3[0] = var4;
      return String.format(var2, var3);
   }

   public Thread newThread(Runnable var1) {
      Thread var2 = this.backingFactory.newThread(var1);
      int var3 = this.count.getAndIncrement();
      String var4 = this.makeName(var3);
      var2.setName(var4);
      return var2;
   }
}
