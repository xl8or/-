package com.google.common.util.concurrent;

import com.google.common.util.concurrent.AbstractListenableFuture;

public class ValueFuture<V extends Object> extends AbstractListenableFuture<V> {

   private ValueFuture() {}

   public static <T extends Object> ValueFuture<T> create() {
      return new ValueFuture();
   }

   public boolean cancel(boolean var1) {
      return super.cancel();
   }

   public boolean set(V var1) {
      return super.set(var1);
   }

   public boolean setException(Throwable var1) {
      return super.setException(var1);
   }
}
