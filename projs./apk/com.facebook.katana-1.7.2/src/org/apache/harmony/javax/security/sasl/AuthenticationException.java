package org.apache.harmony.javax.security.sasl;

import org.apache.harmony.javax.security.sasl.SaslException;

public class AuthenticationException extends SaslException {

   private static final long serialVersionUID = -3579708765071815007L;


   public AuthenticationException() {}

   public AuthenticationException(String var1) {
      super(var1);
   }

   public AuthenticationException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
