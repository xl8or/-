package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.auth.AuthenticationException;

public class CredentialsNotAvailableException extends AuthenticationException {

   public CredentialsNotAvailableException() {}

   public CredentialsNotAvailableException(String var1) {
      super(var1);
   }

   public CredentialsNotAvailableException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
