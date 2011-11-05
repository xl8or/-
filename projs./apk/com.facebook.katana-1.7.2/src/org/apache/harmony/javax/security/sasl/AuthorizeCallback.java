package org.apache.harmony.javax.security.sasl;

import java.io.Serializable;
import org.apache.harmony.javax.security.auth.callback.Callback;

public class AuthorizeCallback implements Callback, Serializable {

   private static final long serialVersionUID = -2353344186490470805L;
   private final String authenticationID;
   private final String authorizationID;
   private boolean authorized;
   private String authorizedID;


   public AuthorizeCallback(String var1, String var2) {
      this.authenticationID = var1;
      this.authorizationID = var2;
      this.authorizedID = var2;
   }

   public String getAuthenticationID() {
      return this.authenticationID;
   }

   public String getAuthorizationID() {
      return this.authorizationID;
   }

   public String getAuthorizedID() {
      String var1;
      if(this.authorized) {
         var1 = this.authorizedID;
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean isAuthorized() {
      return this.authorized;
   }

   public void setAuthorized(boolean var1) {
      this.authorized = var1;
   }

   public void setAuthorizedID(String var1) {
      if(var1 != null) {
         this.authorizedID = var1;
      }
   }
}
