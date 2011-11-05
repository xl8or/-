package org.apache.harmony.javax.security.auth.login;

import org.apache.harmony.javax.security.auth.login.LoginException;

public class FailedLoginException extends LoginException {

   private static final long serialVersionUID = 802556922354616286L;


   public FailedLoginException() {}

   public FailedLoginException(String var1) {
      super(var1);
   }
}
