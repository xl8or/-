package org.apache.harmony.javax.security.auth.login;

import org.apache.harmony.javax.security.auth.login.AccountException;

public class AccountExpiredException extends AccountException {

   private static final long serialVersionUID = -6064064890162661560L;


   public AccountExpiredException() {}

   public AccountExpiredException(String var1) {
      super(var1);
   }
}
