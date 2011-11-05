package com.google.common.util.concurrent;

import com.google.common.util.concurrent.ExecutionList;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

public class ListenableFutureTask<V extends Object> extends FutureTask<V> implements ListenableFuture<V> {

   private final ExecutionList executionList;


   public ListenableFutureTask(Runnable var1, V var2) {
      super(var1, var2);
      ExecutionList var3 = new ExecutionList();
      this.executionList = var3;
   }

   public ListenableFutureTask(Callable<V> var1) {
      super(var1);
      ExecutionList var2 = new ExecutionList();
      this.executionList = var2;
   }

   public void addListener(Runnable var1, Executor var2) {
      this.executionList.add(var1, var2);
   }

   protected void done() {
      this.executionList.run();
   }
}
