package org.apache.harmony.javax.security.auth.login;

import org.apache.harmony.javax.security.auth.login.CredentialException;

public class CredentialNotFoundException extends CredentialException {

   private static final long serialVersionUID = -7779934467214319475L;


   public CredentialNotFoundException() {}

   public CredentialNotFoundException(String var1) {
      super(var1);
   }
}
