package com.google.common.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public interface ListenableFuture<V extends Object> extends Future<V> {

   void addListener(Runnable var1, Executor var2);
}
