package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthChallengeParser;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.apache.commons.httpclient.auth.MalformedChallengeException;
import org.apache.commons.httpclient.auth.NTLM;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NTLMScheme implements AuthScheme {

   private static final int FAILED = Integer.MAX_VALUE;
   private static final int INITIATED = 1;
   private static final Log LOG = LogFactory.getLog(NTLMScheme.class);
   private static final int TYPE1_MSG_GENERATED = 2;
   private static final int TYPE2_MSG_RECEIVED = 3;
   private static final int TYPE3_MSG_GENERATED = 4;
   private static final int UNINITIATED;
   private String ntlmchallenge = null;
   private int state;


   public NTLMScheme() {
      this.state = 0;
   }

   public NTLMScheme(String var1) throws MalformedChallengeException {
      this.processChallenge(var1);
   }

   public static String authenticate(NTCredentials var0, String var1) throws AuthenticationException {
      LOG.trace("enter NTLMScheme.authenticate(NTCredentials, String)");
      if(var0 == null) {
         throw new IllegalArgumentException("Credentials may not be null");
      } else {
         NTLM var2 = new NTLM();
         String var3 = var0.getUserName();
         String var4 = var0.getPassword();
         String var5 = var0.getHost();
         String var6 = var0.getDomain();
         String var8 = var2.getResponseFor(var1, var3, var4, var5, var6);
         return "NTLM " + var8;
      }
   }

   public static String authenticate(NTCredentials var0, String var1, String var2) throws AuthenticationException {
      LOG.trace("enter NTLMScheme.authenticate(NTCredentials, String)");
      if(var0 == null) {
         throw new IllegalArgumentException("Credentials may not be null");
      } else {
         NTLM var3 = new NTLM();
         var3.setCredentialCharset(var2);
         String var4 = var0.getUserName();
         String var5 = var0.getPassword();
         String var6 = var0.getHost();
         String var7 = var0.getDomain();
         String var9 = var3.getResponseFor(var1, var4, var5, var6, var7);
         return "NTLM " + var9;
      }
   }

   public String authenticate(Credentials var1, String var2, String var3) throws AuthenticationException {
      LOG.trace("enter NTLMScheme.authenticate(Credentials, String, String)");

      NTCredentials var4;
      try {
         var4 = (NTCredentials)var1;
      } catch (ClassCastException var10) {
         StringBuilder var7 = (new StringBuilder()).append("Credentials cannot be used for NTLM authentication: ");
         String var8 = var1.getClass().getName();
         String var9 = var7.append(var8).toString();
         throw new InvalidCredentialsException(var9);
      }

      String var5 = this.ntlmchallenge;
      return authenticate(var4, var5);
   }

   public String authenticate(Credentials var1, HttpMethod var2) throws AuthenticationException {
      LOG.trace("enter NTLMScheme.authenticate(Credentials, HttpMethod)");
      if(this.state == 0) {
         throw new IllegalStateException("NTLM authentication process has not been initiated");
      } else {
         NTCredentials var3;
         try {
            var3 = (NTCredentials)var1;
         } catch (ClassCastException var19) {
            StringBuilder var10 = (new StringBuilder()).append("Credentials cannot be used for NTLM authentication: ");
            String var11 = var1.getClass().getName();
            String var12 = var10.append(var11).toString();
            throw new InvalidCredentialsException(var12);
         }

         NTLM var4 = new NTLM();
         String var5 = var2.getParams().getCredentialCharset();
         var4.setCredentialCharset(var5);
         String var8;
         if(this.state != 1 && this.state != Integer.MAX_VALUE) {
            String var13 = var3.getUserName();
            String var14 = var3.getPassword();
            String var15 = var3.getHost();
            String var16 = var3.getDomain();
            String var17 = this.ntlmchallenge;
            byte[] var18 = var4.parseType2Message(var17);
            var8 = var4.getType3Message(var13, var14, var15, var16, var18);
            this.state = 4;
         } else {
            String var6 = var3.getHost();
            String var7 = var3.getDomain();
            var8 = var4.getType1Message(var6, var7);
            this.state = 2;
         }

         return "NTLM " + var8;
      }
   }

   public String getID() {
      return this.ntlmchallenge;
   }

   public String getParameter(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter name may not be null");
      } else {
         return null;
      }
   }

   public String getRealm() {
      return null;
   }

   public String getSchemeName() {
      return "ntlm";
   }

   public boolean isComplete() {
      boolean var1;
      if(this.state != 4 && this.state != Integer.MAX_VALUE) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isConnectionBased() {
      return true;
   }

   public void processChallenge(String var1) throws MalformedChallengeException {
      String var2 = AuthChallengeParser.extractScheme(var1);
      String var3 = this.getSchemeName();
      if(!var2.equalsIgnoreCase(var3)) {
         String var4 = "Invalid NTLM challenge: " + var1;
         throw new MalformedChallengeException(var4);
      } else {
         int var5 = var1.indexOf(32);
         if(var5 != -1) {
            int var6 = var1.length();
            String var7 = var1.substring(var5, var6).trim();
            this.ntlmchallenge = var7;
            this.state = 3;
         } else {
            this.ntlmchallenge = "";
            if(this.state == 0) {
               this.state = 1;
            } else {
               this.state = Integer.MAX_VALUE;
            }
         }
      }
   }
}
