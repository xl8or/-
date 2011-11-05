package com.kenai.jbosh;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class RequestIDSequence {

   private static final int INCREMENT_BITS = 32;
   private static final Lock LOCK = new ReentrantLock();
   private static final long MASK = 9007199254740991L;
   private static final int MAX_BITS = 53;
   private static final long MAX_INITIAL = 9007194959773696L;
   private static final long MIN_INCREMENTS = 4294967296L;
   private static final SecureRandom RAND = new SecureRandom();
   private AtomicLong nextRequestID;


   RequestIDSequence() {
      AtomicLong var1 = new AtomicLong();
      this.nextRequestID = var1;
      long var2 = this.generateInitialValue();
      AtomicLong var4 = new AtomicLong(var2);
      this.nextRequestID = var4;
   }

   private long generateInitialValue() {
      LOCK.lock();

      long var3;
      do {
         boolean var7 = false;

         long var1;
         try {
            var7 = true;
            var1 = RAND.nextLong();
            var7 = false;
         } finally {
            if(var7) {
               LOCK.unlock();
            }
         }

         var3 = var1 & 9007199254740991L;
      } while(var3 > 9007194959773696L);

      LOCK.unlock();
      return var3;
   }

   public long getNextRID() {
      return this.nextRequestID.getAndIncrement();
   }
}
