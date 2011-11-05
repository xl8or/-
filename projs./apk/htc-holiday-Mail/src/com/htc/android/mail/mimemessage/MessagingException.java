package com.htc.android.mail.mimemessage;


public class MessagingException extends Exception {

   public static final int AUTHENTICATION_FAILED = 5;
   public static final int AUTH_REQUIRED = 3;
   public static final int DUPLICATE_ACCOUNT = 6;
   public static final int GENERAL_SECURITY = 4;
   public static final int IOERROR = 1;
   public static final int NO_ERROR = 255;
   public static final int TLS_REQUIRED = 2;
   public static final int UNSPECIFIED_EXCEPTION = 0;
   public static final long serialVersionUID = 255L;
   protected int mExceptionType;


   public MessagingException(int var1) {
      this.mExceptionType = var1;
   }

   public MessagingException(int var1, String var2) {
      super(var2);
      this.mExceptionType = var1;
   }

   public MessagingException(String var1) {
      super(var1);
      this.mExceptionType = 0;
   }

   public MessagingException(String var1, Throwable var2) {
      super(var1, var2);
      this.mExceptionType = 0;
   }

   public int getExceptionType() {
      return this.mExceptionType;
   }
}
