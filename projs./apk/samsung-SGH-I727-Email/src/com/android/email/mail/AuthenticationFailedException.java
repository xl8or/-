package com.android.email.mail;

import com.android.email.mail.MessagingException;

public class AuthenticationFailedException extends MessagingException {

   public static final long serialVersionUID = 255L;


   public AuthenticationFailedException(String var1) {
      super(5, var1);
   }

   public AuthenticationFailedException(String var1, Throwable var2) {
      super(var1, var2);
      this.mExceptionType = 5;
   }
}
