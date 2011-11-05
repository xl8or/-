package com.google.common.util.concurrent;

import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.ExecutionList;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;

public abstract class AbstractListenableFuture<V extends Object> extends AbstractFuture<V> implements ListenableFuture<V> {

   private final ExecutionList executionList;


   public AbstractListenableFuture() {
      ExecutionList var1 = new ExecutionList();
      this.executionList = var1;
   }

   public void addListener(Runnable var1, Executor var2) {
      this.executionList.add(var1, var2);
   }

   protected void done() {
      this.executionList.run();
   }
}
