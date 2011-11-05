package com.google.common.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface UninterruptibleFuture<V extends Object> extends Future<V> {

   V get() throws ExecutionException;

   V get(long var1, TimeUnit var3) throws ExecutionException, TimeoutException;
}
