package com.google.common.util.concurrent;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface CheckedFuture<V extends Object, E extends Exception> extends ListenableFuture<V> {

   V checkedGet() throws E;

   V checkedGet(long var1, TimeUnit var3) throws TimeoutException, E;
}
