package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public interface TimeLimiter {

   <T extends Object> T callWithTimeout(Callable<T> var1, long var2, TimeUnit var4, boolean var5) throws Exception;

   <T extends Object> T newProxy(T var1, Class<T> var2, long var3, TimeUnit var5);
}
