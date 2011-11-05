package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.TimeLimiter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleTimeLimiter implements TimeLimiter {

   private final ExecutorService executor;


   public SimpleTimeLimiter() {
      ExecutorService var1 = java.util.concurrent.Executors.newCachedThreadPool();
      this(var1);
   }

   public SimpleTimeLimiter(ExecutorService var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      this.executor = var1;
   }

   private static boolean declaresInterruptedEx(Method var0) {
      Class[] var1 = var0.getExceptionTypes();
      int var2 = var1.length;
      int var3 = 0;

      boolean var4;
      while(true) {
         if(var3 >= var2) {
            var4 = false;
            break;
         }

         if(var1[var3] == InterruptedException.class) {
            var4 = true;
            break;
         }

         ++var3;
      }

      return var4;
   }

   private static Set<Method> findInterruptibleMethods(Class<?> var0) {
      HashSet var1 = Sets.newHashSet();
      Method[] var2 = var0.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method var5 = var2[var4];
         if(declaresInterruptedEx(var5)) {
            var1.add(var5);
         }
      }

      return var1;
   }

   private static <T extends Object> T newProxy(Class<T> var0, InvocationHandler var1) {
      ClassLoader var2 = var0.getClassLoader();
      Class[] var3 = new Class[]{var0};
      Object var4 = Proxy.newProxyInstance(var2, var3, var1);
      return var0.cast(var4);
   }

   public <T extends Object> T callWithTimeout(Callable<T> param1, long param2, TimeUnit param4, boolean param5) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public <T extends Object> T newProxy(T var1, Class<T> var2, long var3, TimeUnit var5) {
      Object var6 = Preconditions.checkNotNull(var1);
      Object var7 = Preconditions.checkNotNull(var2);
      Object var8 = Preconditions.checkNotNull(var5);
      byte var9;
      if(var3 > 0L) {
         var9 = 1;
      } else {
         var9 = 0;
      }

      String var10 = "bad timeout: " + var3;
      Preconditions.checkArgument((boolean)var9, var10);
      Preconditions.checkArgument(var2.isInterface(), "interfaceType must be an interface type");
      Set var11 = findInterruptibleMethods(var2);
      SimpleTimeLimiter.1 var17 = new SimpleTimeLimiter.1(var1, var3, var5, var11);
      return newProxy(var2, var17);
   }

   class 1 implements InvocationHandler {

      // $FF: synthetic field
      final Set val$interruptibleMethods;
      // $FF: synthetic field
      final Object val$target;
      // $FF: synthetic field
      final long val$timeoutDuration;
      // $FF: synthetic field
      final TimeUnit val$timeoutUnit;


      1(Object var2, long var3, TimeUnit var5, Set var6) {
         this.val$target = var2;
         this.val$timeoutDuration = var3;
         this.val$timeoutUnit = var5;
         this.val$interruptibleMethods = var6;
      }

      public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
         SimpleTimeLimiter.1.1 var4 = new SimpleTimeLimiter.1.1(var2, var3);
         SimpleTimeLimiter var5 = SimpleTimeLimiter.this;
         long var6 = this.val$timeoutDuration;
         TimeUnit var8 = this.val$timeoutUnit;
         boolean var9 = this.val$interruptibleMethods.contains(var2);
         return var5.callWithTimeout(var4, var6, var8, var9);
      }

      class 1 implements Callable<Object> {

         // $FF: synthetic field
         final Object[] val$args;
         // $FF: synthetic field
         final Method val$method;


         1(Method var2, Object[] var3) {
            this.val$method = var2;
            this.val$args = var3;
         }

         public Object call() throws Exception {
            try {
               Method var1 = this.val$method;
               Object var2 = 1.this.val$target;
               Object[] var3 = this.val$args;
               Object var4 = var1.invoke(var2, var3);
               return var4;
            } catch (InvocationTargetException var6) {
               Exception var5 = Throwables.throwCause(var6, (boolean)0);
               throw new AssertionError("can\'t get here");
            }
         }
      }
   }
}
