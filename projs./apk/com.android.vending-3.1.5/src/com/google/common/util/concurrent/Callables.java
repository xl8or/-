package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import javax.annotation.Nullable;

public final class Callables {

   private Callables() {}

   public static <T extends Object> Callable<T> returning(@Nullable T var0) {
      return new Callables.1(var0);
   }

   static class 1 implements Callable<T> {

      // $FF: synthetic field
      final Object val$value;


      1(Object var1) {
         this.val$value = var1;
      }

      public T call() {
         return this.val$value;
      }
   }
}
