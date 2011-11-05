package com.kenai.jbosh;

import com.kenai.jbosh.AbstractBody;
import com.kenai.jbosh.HTTPResponse;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

final class HTTPExchange {

   private static final Logger LOG = Logger.getLogger(HTTPExchange.class.getName());
   private final Lock lock;
   private final Condition ready;
   private final AbstractBody request;
   private HTTPResponse response;


   HTTPExchange(AbstractBody var1) {
      ReentrantLock var2 = new ReentrantLock();
      this.lock = var2;
      Condition var3 = this.lock.newCondition();
      this.ready = var3;
      if(var1 == null) {
         throw new IllegalArgumentException("Request body cannot be null");
      } else {
         this.request = var1;
      }
   }

   HTTPResponse getHTTPResponse() {
      // $FF: Couldn't be decompiled
   }

   AbstractBody getRequest() {
      return this.request;
   }

   void setHTTPResponse(HTTPResponse var1) {
      this.lock.lock();

      try {
         if(this.response != null) {
            throw new IllegalStateException("HTTPResponse was already set");
         }

         this.response = var1;
         this.ready.signalAll();
      } finally {
         this.lock.unlock();
      }

   }
}
