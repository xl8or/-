package com.android.exchange;


public abstract class Request {

   public long mMessageId;
   public long mTimeStamp;


   public Request() {
      long var1 = System.currentTimeMillis();
      this.mTimeStamp = var1;
   }
}
