package com.android.email.combined;


public class EmailException extends Exception {

   private static final long serialVersionUID = 1L;


   public EmailException() {}

   public EmailException(Exception var1) {
      super(var1);
   }

   public EmailException(String var1, Exception var2) {
      super(var1, var2);
   }
}
