package com.android.email.mail;

import com.android.email.mail.MessagingException;

public class CertificateValidationException extends MessagingException {

   public static final long serialVersionUID = 255L;


   public CertificateValidationException(String var1) {
      super(var1);
   }

   public CertificateValidationException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
