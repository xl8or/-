package org.apache.commons.httpclient.auth;

import java.util.HashMap;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthChallengeParser;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.auth.BasicScheme;
import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;
import org.apache.commons.httpclient.auth.DigestScheme;
import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.apache.commons.httpclient.auth.MalformedChallengeException;
import org.apache.commons.httpclient.auth.NTLMScheme;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class HttpAuthenticator {

   private static final Log LOG = LogFactory.getLog(HttpAuthenticator.class);
   public static final String PROXY_AUTH = "Proxy-Authenticate";
   public static final String PROXY_AUTH_RESP = "Proxy-Authorization";
   public static final String WWW_AUTH = "WWW-Authenticate";
   public static final String WWW_AUTH_RESP = "Authorization";


   public HttpAuthenticator() {}

   public static boolean authenticate(AuthScheme var0, HttpMethod var1, HttpConnection var2, HttpState var3) throws AuthenticationException {
      LOG.trace("enter HttpAuthenticator.authenticate(AuthScheme, HttpMethod, HttpConnection, HttpState)");
      return doAuthenticate(var0, var1, var2, var3, (boolean)0);
   }

   public static boolean authenticateDefault(HttpMethod var0, HttpConnection var1, HttpState var2) throws AuthenticationException {
      LOG.trace("enter HttpAuthenticator.authenticateDefault(HttpMethod, HttpConnection, HttpState)");
      return doAuthenticateDefault(var0, var1, var2, (boolean)0);
   }

   public static boolean authenticateProxy(AuthScheme var0, HttpMethod var1, HttpConnection var2, HttpState var3) throws AuthenticationException {
      LOG.trace("enter HttpAuthenticator.authenticateProxy(AuthScheme, HttpMethod, HttpState)");
      return doAuthenticate(var0, var1, var2, var3, (boolean)1);
   }

   public static boolean authenticateProxyDefault(HttpMethod var0, HttpConnection var1, HttpState var2) throws AuthenticationException {
      LOG.trace("enter HttpAuthenticator.authenticateProxyDefault(HttpMethod, HttpState)");
      return doAuthenticateDefault(var0, var1, var2, (boolean)1);
   }

   private static boolean doAuthenticate(AuthScheme var0, HttpMethod var1, HttpConnection var2, HttpState var3, boolean var4) throws AuthenticationException {
      if(var0 == null) {
         throw new IllegalArgumentException("Authentication scheme may not be null");
      } else if(var1 == null) {
         throw new IllegalArgumentException("HTTP method may not be null");
      } else if(var3 == null) {
         throw new IllegalArgumentException("HTTP state may not be null");
      } else {
         String var5 = null;
         if(var2 != null) {
            if(var4) {
               var5 = var2.getProxyHost();
            } else {
               var5 = var1.getParams().getVirtualHost();
               if(var5 == null) {
                  var5 = var2.getHost();
               }
            }
         }

         String var6 = var0.getRealm();
         if(LOG.isDebugEnabled()) {
            StringBuffer var7 = new StringBuffer();
            StringBuffer var8 = var7.append("Using credentials for ");
            if(var6 == null) {
               StringBuffer var9 = var7.append("default");
            } else {
               StringBuffer var21 = var7.append('\'');
               var7.append(var6);
               StringBuffer var23 = var7.append('\'');
            }

            StringBuffer var10 = var7.append(" authentication realm at ");
            var7.append(var5);
            Log var12 = LOG;
            String var13 = var7.toString();
            var12.debug(var13);
         }

         Credentials var14;
         if(var4) {
            var14 = ((HttpState)var3).getProxyCredentials(var6, var5);
         } else {
            ((HttpState)var3).getCredentials(var6, var5);
         }

         if(var14 == null) {
            StringBuffer var15 = new StringBuffer();
            StringBuffer var16 = var15.append("No credentials available for the ");
            if(var6 == null) {
               StringBuffer var17 = var15.append("default");
            } else {
               StringBuffer var25 = var15.append('\'');
               var15.append(var6);
               StringBuffer var27 = var15.append('\'');
            }

            StringBuffer var18 = var15.append(" authentication realm at ");
            var15.append(var5);
            String var20 = var15.toString();
            throw new CredentialsNotAvailableException(var20);
         } else {
            String var28 = var0.authenticate(var14, var1);
            boolean var30;
            if(var28 != null) {
               if(var4) {
                  var3 = "Proxy-Authorization";
               } else {
                  String var31 = "Authorization";
               }

               Header var29 = new Header((String)var3, var28, (boolean)1);
               var1.addRequestHeader(var29);
               var30 = true;
            } else {
               var30 = false;
            }

            return var30;
         }
      }
   }

   private static boolean doAuthenticateDefault(HttpMethod var0, HttpConnection var1, HttpState var2, boolean var3) throws AuthenticationException {
      if(var0 == null) {
         throw new IllegalArgumentException("HTTP method may not be null");
      } else if(var2 == null) {
         throw new IllegalArgumentException("HTTP state may not be null");
      } else {
         String var4 = null;
         if(var1 != null) {
            if(var3) {
               var4 = var1.getProxyHost();
            } else {
               var4 = var1.getHost();
            }
         }

         Credentials var5;
         if(var3) {
            var5 = var2.getProxyCredentials((String)null, var4);
         } else {
            var5 = var2.getCredentials((String)null, var4);
         }

         boolean var6;
         if(var5 == null) {
            var6 = false;
         } else {
            if(!(var5 instanceof UsernamePasswordCredentials)) {
               StringBuilder var7 = (new StringBuilder()).append("Credentials cannot be used for basic authentication: ");
               String var8 = var5.toString();
               String var9 = var7.append(var8).toString();
               throw new InvalidCredentialsException(var9);
            }

            UsernamePasswordCredentials var10 = (UsernamePasswordCredentials)var5;
            String var11 = var0.getParams().getCredentialCharset();
            String var12 = BasicScheme.authenticate(var10, var11);
            if(var12 != null) {
               String var13;
               if(var3) {
                  var13 = "Proxy-Authorization";
               } else {
                  var13 = "Authorization";
               }

               Header var14 = new Header(var13, var12, (boolean)1);
               var0.addRequestHeader(var14);
               var6 = true;
            } else {
               var6 = false;
            }
         }

         return var6;
      }
   }

   public static AuthScheme selectAuthScheme(Header[] var0) throws MalformedChallengeException {
      LOG.trace("enter HttpAuthenticator.selectAuthScheme(Header[])");
      if(var0 == null) {
         throw new IllegalArgumentException("Array of challenges may not be null");
      } else if(var0.length == 0) {
         throw new IllegalArgumentException("Array of challenges may not be empty");
      } else {
         int var1 = var0.length;
         HashMap var2 = new HashMap(var1);
         int var3 = 0;

         while(true) {
            int var4 = var0.length;
            if(var3 >= var4) {
               String var8 = (String)var2.get("ntlm");
               Object var9;
               if(var8 != null) {
                  var9 = new NTLMScheme(var8);
               } else {
                  var8 = (String)var2.get("digest");
                  if(var8 != null) {
                     var9 = new DigestScheme(var8);
                  } else {
                     var8 = (String)var2.get("basic");
                     if(var8 == null) {
                        StringBuilder var10 = (new StringBuilder()).append("Authentication scheme(s) not supported: ");
                        String var11 = var2.toString();
                        String var12 = var10.append(var11).toString();
                        throw new UnsupportedOperationException(var12);
                     }

                     var9 = new BasicScheme(var8);
                  }
               }

               return (AuthScheme)var9;
            }

            String var5 = var0[var3].getValue();
            String var6 = AuthChallengeParser.extractScheme(var5);
            var2.put(var6, var5);
            ++var3;
         }
      }
   }
}
