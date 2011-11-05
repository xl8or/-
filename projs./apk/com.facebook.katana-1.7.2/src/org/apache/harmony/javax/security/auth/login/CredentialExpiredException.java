package org.apache.harmony.javax.security.auth.login;

import org.apache.harmony.javax.security.auth.login.CredentialException;

public class CredentialExpiredException extends CredentialException {

   private static final long serialVersionUID = -5344739593859737937L;


   public CredentialExpiredException() {}

   public CredentialExpiredException(String var1) {
      super(var1);
   }
}
