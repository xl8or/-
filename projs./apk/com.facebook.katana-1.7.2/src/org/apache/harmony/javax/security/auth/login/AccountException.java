package org.apache.harmony.javax.security.auth.login;

import org.apache.harmony.javax.security.auth.login.LoginException;

public class AccountException extends LoginException {

   private static final long serialVersionUID = -2112878680072211787L;


   public AccountException() {}

   public AccountException(String var1) {
      super(var1);
   }
}
