package org.apache.harmony.javax.security.auth.login;

import org.apache.harmony.javax.security.auth.login.AccountException;

public class AccountLockedException extends AccountException {

   private static final long serialVersionUID = 8280345554014066334L;


   public AccountLockedException() {}

   public AccountLockedException(String var1) {
      super(var1);
   }
}
