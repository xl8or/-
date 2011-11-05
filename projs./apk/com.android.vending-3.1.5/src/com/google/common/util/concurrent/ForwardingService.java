package com.google.common.util.concurrent;

import com.google.common.base.Service;
import com.google.common.collect.ForwardingObject;
import java.util.concurrent.Future;

public abstract class ForwardingService extends ForwardingObject implements Service {

   public ForwardingService() {}

   protected abstract Service delegate();

   public boolean isRunning() {
      return this.delegate().isRunning();
   }

   public Future<Service.State> start() {
      return this.delegate().start();
   }

   public Service.State startAndWait() {
      return this.delegate().startAndWait();
   }

   public Service.State state() {
      return this.delegate().state();
   }

   public Future<Service.State> stop() {
      return this.delegate().stop();
   }

   public Service.State stopAndWait() {
      return this.delegate().stopAndWait();
   }
}
