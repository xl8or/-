package org.apache.harmony.javax.security.auth.login;

import org.apache.harmony.javax.security.auth.login.AccountException;

public class AccountNotFoundException extends AccountException {

   private static final long serialVersionUID = 1498349563916294614L;


   public AccountNotFoundException() {}

   public AccountNotFoundException(String var1) {
      super(var1);
   }
}
