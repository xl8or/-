package com.google.common.util.concurrent;

import com.google.common.util.concurrent.TimeLimiter;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class FakeTimeLimiter implements TimeLimiter {

   public FakeTimeLimiter() {}

   public <T extends Object> T callWithTimeout(Callable<T> var1, long var2, TimeUnit var4, boolean var5) throws Exception {
      return var1.call();
   }

   public <T extends Object> T newProxy(T var1, Class<T> var2, long var3, TimeUnit var5) {
      return var1;
   }
}
