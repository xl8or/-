package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.GDataException;

public class ResourceUnavailableException extends GDataException {

   private long retryAfter;


   public ResourceUnavailableException(long var1) {
      this.retryAfter = var1;
   }

   public ResourceUnavailableException(String var1, long var2) {
      super(var1);
      this.retryAfter = var2;
   }

   public ResourceUnavailableException(String var1, Throwable var2, long var3) {
      super(var1, var2);
      this.retryAfter = var3;
   }

   public long getRetryAfter() {
      return this.retryAfter;
   }
}
