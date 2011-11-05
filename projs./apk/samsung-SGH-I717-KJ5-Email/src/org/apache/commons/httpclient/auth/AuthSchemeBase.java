package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.MalformedChallengeException;

public abstract class AuthSchemeBase implements AuthScheme {

   private String challenge = null;


   public AuthSchemeBase(String var1) throws MalformedChallengeException {
      if(var1 == null) {
         throw new IllegalArgumentException("Challenge may not be null");
      } else {
         this.challenge = var1;
      }
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof AuthSchemeBase) {
         String var2 = this.challenge;
         String var3 = ((AuthSchemeBase)var1).challenge;
         var4 = var2.equals(var3);
      } else {
         var4 = super.equals(var1);
      }

      return var4;
   }

   public int hashCode() {
      return this.challenge.hashCode();
   }

   public String toString() {
      return this.challenge;
   }
}
