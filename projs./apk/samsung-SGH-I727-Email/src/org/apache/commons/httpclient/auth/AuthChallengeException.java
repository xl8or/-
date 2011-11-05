package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.auth.AuthenticationException;

public class AuthChallengeException extends AuthenticationException {

   public AuthChallengeException() {}

   public AuthChallengeException(String var1) {
      super(var1);
   }

   public AuthChallengeException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
