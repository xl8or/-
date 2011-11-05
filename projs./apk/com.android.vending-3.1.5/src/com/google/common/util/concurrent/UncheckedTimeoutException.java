package com.google.common.util.concurrent;


public class UncheckedTimeoutException extends RuntimeException {

   private static final long serialVersionUID;


   public UncheckedTimeoutException() {}

   public UncheckedTimeoutException(String var1) {
      super(var1);
   }

   public UncheckedTimeoutException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public UncheckedTimeoutException(Throwable var1) {
      super(var1);
   }
}
