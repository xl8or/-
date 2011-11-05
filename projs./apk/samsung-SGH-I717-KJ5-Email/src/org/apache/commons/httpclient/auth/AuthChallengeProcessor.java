package org.apache.commons.httpclient.auth;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.httpclient.auth.AuthChallengeException;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.AuthState;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.auth.MalformedChallengeException;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class AuthChallengeProcessor {

   private static final Log LOG = LogFactory.getLog(AuthChallengeProcessor.class);
   private HttpParams params = null;


   public AuthChallengeProcessor(HttpParams var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter collection may not be null");
      } else {
         this.params = var1;
      }
   }

   public AuthScheme processChallenge(AuthState var1, Map var2) throws MalformedChallengeException, AuthenticationException {
      if(var1 == null) {
         throw new IllegalArgumentException("Authentication state may not be null");
      } else if(var2 == null) {
         throw new IllegalArgumentException("Challenge map may not be null");
      } else {
         if(var1.isPreemptive() || var1.getAuthScheme() == null) {
            AuthScheme var3 = this.selectAuthScheme(var2);
            var1.setAuthScheme(var3);
         }

         AuthScheme var4 = var1.getAuthScheme();
         String var5 = var4.getSchemeName();
         if(LOG.isDebugEnabled()) {
            Log var6 = LOG;
            String var7 = "Using authentication scheme: " + var5;
            var6.debug(var7);
         }

         String var8 = var5.toLowerCase();
         String var9 = (String)var2.get(var8);
         if(var9 == null) {
            String var10 = var5 + " authorization challenge expected, but not found";
            throw new AuthenticationException(var10);
         } else {
            var4.processChallenge(var9);
            LOG.debug("Authorization challenge processed");
            return var4;
         }
      }
   }

   public AuthScheme selectAuthScheme(Map var1) throws AuthChallengeException {
      if(var1 == null) {
         throw new IllegalArgumentException("Challenge map may not be null");
      } else {
         Object var2 = (Collection)this.params.getParameter("http.auth.scheme-priority");
         if(var2 == null || ((Collection)var2).isEmpty()) {
            var2 = AuthPolicy.getDefaultAuthPrefs();
         }

         if(LOG.isDebugEnabled()) {
            Log var3 = LOG;
            String var4 = "Supported authentication schemes in the order of preference: " + var2;
            var3.debug(var4);
         }

         AuthScheme var5 = null;
         Iterator var6 = ((Collection)var2).iterator();

         while(var6.hasNext()) {
            String var7 = (String)var6.next();
            String var8 = var7.toLowerCase();
            if((String)var1.get(var8) != null) {
               if(LOG.isInfoEnabled()) {
                  StringBuffer var9 = new StringBuffer();
                  StringBuffer var10 = var9.append(var7).append(" authentication scheme selected");
                  Log var11 = LOG;
                  String var12 = var9.toString();
                  var11.info(var12);
               }

               AuthScheme var13;
               try {
                  var13 = AuthPolicy.getAuthScheme(var7);
               } catch (IllegalStateException var20) {
                  String var15 = var20.getMessage();
                  throw new AuthChallengeException(var15);
               }

               var5 = var13;
               break;
            }

            if(LOG.isDebugEnabled()) {
               StringBuffer var16 = new StringBuffer();
               StringBuffer var17 = var16.append("Challenge for ").append(var7).append(" authentication scheme not available");
               Log var18 = LOG;
               String var19 = var16.toString();
               var18.debug(var19);
            }
         }

         if(var5 == null) {
            String var14 = "Unable to respond to any of these challenges: " + var1;
            throw new AuthChallengeException(var14);
         } else {
            return var5;
         }
      }
   }
}
