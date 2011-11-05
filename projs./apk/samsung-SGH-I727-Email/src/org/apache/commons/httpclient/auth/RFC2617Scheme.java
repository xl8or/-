package org.apache.commons.httpclient.auth;

import java.util.Map;
import org.apache.commons.httpclient.auth.AuthChallengeParser;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.MalformedChallengeException;

public abstract class RFC2617Scheme implements AuthScheme {

   private Map params = null;


   public RFC2617Scheme() {}

   public RFC2617Scheme(String var1) throws MalformedChallengeException {
      this.processChallenge(var1);
   }

   public String getID() {
      return this.getRealm();
   }

   public String getParameter(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter name may not be null");
      } else {
         String var2;
         if(this.params == null) {
            var2 = null;
         } else {
            Map var3 = this.params;
            String var4 = var1.toLowerCase();
            var2 = (String)var3.get(var4);
         }

         return var2;
      }
   }

   protected Map getParameters() {
      return this.params;
   }

   public String getRealm() {
      return this.getParameter("realm");
   }

   public void processChallenge(String var1) throws MalformedChallengeException {
      String var2 = AuthChallengeParser.extractScheme(var1);
      String var3 = this.getSchemeName();
      if(!var2.equalsIgnoreCase(var3)) {
         StringBuilder var4 = (new StringBuilder()).append("Invalid ");
         String var5 = this.getSchemeName();
         String var6 = var4.append(var5).append(" challenge: ").append(var1).toString();
         throw new MalformedChallengeException(var6);
      } else {
         Map var7 = AuthChallengeParser.extractParams(var1);
         this.params = var7;
      }
   }
}
