package org.apache.commons.httpclient.auth;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.apache.commons.httpclient.auth.MalformedChallengeException;
import org.apache.commons.httpclient.auth.RFC2617Scheme;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BasicScheme extends RFC2617Scheme {

   private static final Log LOG = LogFactory.getLog(BasicScheme.class);
   private boolean complete;


   public BasicScheme() {
      this.complete = (boolean)0;
   }

   public BasicScheme(String var1) throws MalformedChallengeException {
      super(var1);
      this.complete = (boolean)1;
   }

   public static String authenticate(UsernamePasswordCredentials var0) {
      return authenticate(var0, "ISO-8859-1");
   }

   public static String authenticate(UsernamePasswordCredentials var0, String var1) {
      LOG.trace("enter BasicScheme.authenticate(UsernamePasswordCredentials, String)");
      if(var0 == null) {
         throw new IllegalArgumentException("Credentials may not be null");
      } else if(var1 != null && var1.length() != 0) {
         StringBuffer var2 = new StringBuffer();
         String var3 = var0.getUserName();
         var2.append(var3);
         StringBuffer var5 = var2.append(":");
         String var6 = var0.getPassword();
         var2.append(var6);
         StringBuilder var8 = (new StringBuilder()).append("Basic ");
         String var9 = EncodingUtil.getAsciiString(Base64.encodeBase64(EncodingUtil.getBytes(var2.toString(), var1)));
         return var8.append(var9).toString();
      } else {
         throw new IllegalArgumentException("charset may not be null or empty");
      }
   }

   public String authenticate(Credentials var1, String var2, String var3) throws AuthenticationException {
      LOG.trace("enter BasicScheme.authenticate(Credentials, String, String)");

      UsernamePasswordCredentials var4;
      try {
         var4 = (UsernamePasswordCredentials)var1;
      } catch (ClassCastException var9) {
         StringBuilder var6 = (new StringBuilder()).append("Credentials cannot be used for basic authentication: ");
         String var7 = var1.getClass().getName();
         String var8 = var6.append(var7).toString();
         throw new InvalidCredentialsException(var8);
      }

      return authenticate(var4);
   }

   public String authenticate(Credentials var1, HttpMethod var2) throws AuthenticationException {
      LOG.trace("enter BasicScheme.authenticate(Credentials, HttpMethod)");
      if(var2 == null) {
         throw new IllegalArgumentException("Method may not be null");
      } else {
         UsernamePasswordCredentials var3;
         try {
            var3 = (UsernamePasswordCredentials)var1;
         } catch (ClassCastException var9) {
            StringBuilder var6 = (new StringBuilder()).append("Credentials cannot be used for basic authentication: ");
            String var7 = var1.getClass().getName();
            String var8 = var6.append(var7).toString();
            throw new InvalidCredentialsException(var8);
         }

         String var4 = var2.getParams().getCredentialCharset();
         return authenticate(var3, var4);
      }
   }

   public String getSchemeName() {
      return "basic";
   }

   public boolean isComplete() {
      return this.complete;
   }

   public boolean isConnectionBased() {
      return false;
   }

   public void processChallenge(String var1) throws MalformedChallengeException {
      super.processChallenge(var1);
      this.complete = (boolean)1;
   }
}
