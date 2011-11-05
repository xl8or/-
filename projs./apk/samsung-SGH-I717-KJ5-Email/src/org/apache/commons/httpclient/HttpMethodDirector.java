package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import org.apache.commons.httpclient.ConnectMethod;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.RedirectException;
import org.apache.commons.httpclient.StatusLine;
import org.apache.commons.httpclient.auth.AuthChallengeException;
import org.apache.commons.httpclient.auth.AuthChallengeParser;
import org.apache.commons.httpclient.auth.AuthChallengeProcessor;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.auth.AuthState;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.apache.commons.httpclient.auth.MalformedChallengeException;
import org.apache.commons.httpclient.params.HostParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class HttpMethodDirector {

   private static final Log LOG = LogFactory.getLog(HttpMethodDirector.class);
   public static final String PROXY_AUTH_CHALLENGE = "Proxy-Authenticate";
   public static final String PROXY_AUTH_RESP = "Proxy-Authorization";
   public static final String WWW_AUTH_CHALLENGE = "WWW-Authenticate";
   public static final String WWW_AUTH_RESP = "Authorization";
   private AuthChallengeProcessor authProcessor = null;
   private HttpConnection conn;
   private ConnectMethod connectMethod;
   private HttpConnectionManager connectionManager;
   private HostConfiguration hostConfiguration;
   private HttpClientParams params;
   private Set redirectLocations = null;
   private boolean releaseConnection = 0;
   private HttpState state;


   public HttpMethodDirector(HttpConnectionManager var1, HostConfiguration var2, HttpClientParams var3, HttpState var4) {
      this.connectionManager = var1;
      this.hostConfiguration = var2;
      this.params = var3;
      this.state = var4;
      HttpClientParams var5 = this.params;
      AuthChallengeProcessor var6 = new AuthChallengeProcessor(var5);
      this.authProcessor = var6;
   }

   private void applyConnectionParams(HttpMethod var1) throws IOException {
      int var2 = 0;
      Object var3 = var1.getParams().getParameter("http.socket.timeout");
      if(var3 == null) {
         var3 = this.conn.getParams().getParameter("http.socket.timeout");
      }

      if(var3 != null) {
         var2 = ((Integer)var3).intValue();
      }

      this.conn.setSocketTimeout(var2);
   }

   private void authenticate(HttpMethod var1) {
      try {
         if(this.conn.isProxied() && !this.conn.isSecure()) {
            this.authenticateProxy(var1);
         }

         this.authenticateHost(var1);
      } catch (AuthenticationException var5) {
         Log var3 = LOG;
         String var4 = var5.getMessage();
         var3.error(var4, var5);
      }
   }

   private void authenticateHost(HttpMethod var1) throws AuthenticationException {
      if(this.cleanAuthHeaders(var1, "Authorization")) {
         AuthState var2 = var1.getHostAuthState();
         AuthScheme var3 = var2.getAuthScheme();
         if(var3 != null) {
            if(var2.isAuthRequested() || !var3.isConnectionBased()) {
               String var4 = var1.getParams().getVirtualHost();
               if(var4 == null) {
                  var4 = this.conn.getHost();
               }

               int var5 = this.conn.getPort();
               String var6 = var3.getRealm();
               String var7 = var3.getSchemeName();
               AuthScope var8 = new AuthScope(var4, var5, var6, var7);
               if(LOG.isDebugEnabled()) {
                  Log var9 = LOG;
                  String var10 = "Authenticating with " + var8;
                  var9.debug(var10);
               }

               Credentials var11 = this.state.getCredentials(var8);
               if(var11 != null) {
                  String var12 = var3.authenticate(var11, var1);
                  if(var12 != null) {
                     Header var13 = new Header("Authorization", var12, (boolean)1);
                     var1.addRequestHeader(var13);
                  }
               } else if(LOG.isWarnEnabled()) {
                  Log var14 = LOG;
                  String var15 = "Required credentials not available for " + var8;
                  var14.warn(var15);
                  if(var1.getHostAuthState().isPreemptive()) {
                     LOG.warn("Preemptive authentication requested but no default credentials available");
                  }
               }
            }
         }
      }
   }

   private void authenticateProxy(HttpMethod var1) throws AuthenticationException {
      if(this.cleanAuthHeaders(var1, "Proxy-Authorization")) {
         AuthState var2 = var1.getProxyAuthState();
         AuthScheme var3 = var2.getAuthScheme();
         if(var3 != null) {
            if(var2.isAuthRequested() || !var3.isConnectionBased()) {
               String var4 = this.conn.getProxyHost();
               int var5 = this.conn.getProxyPort();
               String var6 = var3.getRealm();
               String var7 = var3.getSchemeName();
               AuthScope var8 = new AuthScope(var4, var5, var6, var7);
               if(LOG.isDebugEnabled()) {
                  Log var9 = LOG;
                  String var10 = "Authenticating with " + var8;
                  var9.debug(var10);
               }

               Credentials var11 = this.state.getProxyCredentials(var8);
               if(var11 != null) {
                  String var12 = var3.authenticate(var11, var1);
                  if(var12 != null) {
                     Header var13 = new Header("Proxy-Authorization", var12, (boolean)1);
                     var1.addRequestHeader(var13);
                  }
               } else if(LOG.isWarnEnabled()) {
                  Log var14 = LOG;
                  String var15 = "Required proxy credentials not available for " + var8;
                  var14.warn(var15);
                  if(var1.getProxyAuthState().isPreemptive()) {
                     LOG.warn("Preemptive authentication requested but no default proxy credentials available");
                  }
               }
            }
         }
      }
   }

   private boolean cleanAuthHeaders(HttpMethod var1, String var2) {
      Header[] var3 = var1.getRequestHeaders(var2);
      boolean var4 = true;
      int var5 = 0;

      while(true) {
         int var6 = var3.length;
         if(var5 >= var6) {
            return var4;
         }

         Header var7 = var3[var5];
         if(var7.isAutogenerated()) {
            var1.removeRequestHeader(var7);
         } else {
            var4 = false;
         }

         ++var5;
      }
   }

   private boolean executeConnect() throws IOException, HttpException {
      HostConfiguration var1 = this.hostConfiguration;
      ConnectMethod var2 = new ConnectMethod(var1);
      this.connectMethod = var2;
      HttpMethodParams var3 = this.connectMethod.getParams();
      HostParams var4 = this.hostConfiguration.getParams();
      var3.setDefaults(var4);

      while(true) {
         if(!this.conn.isOpen()) {
            this.conn.open();
         }

         if(this.params.isAuthenticationPreemptive() || this.state.isAuthenticationPreemptive()) {
            LOG.debug("Preemptively sending default basic credentials");
            this.connectMethod.getProxyAuthState().setPreemptive();
            this.connectMethod.getProxyAuthState().setAuthAttempted((boolean)1);
         }

         try {
            ConnectMethod var5 = this.connectMethod;
            this.authenticateProxy(var5);
         } catch (AuthenticationException var20) {
            Log var18 = LOG;
            String var19 = var20.getMessage();
            var18.error(var19, var20);
         }

         ConnectMethod var6 = this.connectMethod;
         this.applyConnectionParams(var6);
         ConnectMethod var7 = this.connectMethod;
         HttpState var8 = this.state;
         HttpConnection var9 = this.conn;
         var7.execute(var8, var9);
         int var11 = this.connectMethod.getStatusCode();
         boolean var12 = false;
         AuthState var13 = this.connectMethod.getProxyAuthState();
         byte var14;
         if(var11 == 407) {
            var14 = 1;
         } else {
            var14 = 0;
         }

         var13.setAuthRequested((boolean)var14);
         if(var13.isAuthRequested()) {
            ConnectMethod var15 = this.connectMethod;
            if(this.processAuthenticationResponse(var15)) {
               var12 = true;
            }
         }

         if(!var12) {
            boolean var16;
            if(var11 >= 200 && var11 < 300) {
               this.conn.tunnelCreated();
               this.connectMethod = null;
               var16 = true;
            } else {
               this.conn.close();
               var16 = false;
            }

            return var16;
         }

         if(this.connectMethod.getResponseBodyAsStream() != null) {
            this.connectMethod.getResponseBodyAsStream().close();
         }
      }
   }

   private void executeWithRetry(HttpMethod param1) throws IOException, HttpException {
      // $FF: Couldn't be decompiled
   }

   private void fakeResponse(HttpMethod var1) throws IOException, HttpException {
      LOG.debug("CONNECT failed, fake the response for the original method");
      if(var1 instanceof HttpMethodBase) {
         HttpMethodBase var2 = (HttpMethodBase)var1;
         StatusLine var3 = this.connectMethod.getStatusLine();
         HeaderGroup var4 = this.connectMethod.getResponseHeaderGroup();
         InputStream var5 = this.connectMethod.getResponseBodyAsStream();
         var2.fakeResponse(var3, var4, var5);
         AuthState var6 = var1.getProxyAuthState();
         AuthScheme var7 = this.connectMethod.getProxyAuthState().getAuthScheme();
         var6.setAuthScheme(var7);
         this.connectMethod = null;
      } else {
         this.releaseConnection = (boolean)1;
         LOG.warn("Unable to fake response on method as it is not derived from HttpMethodBase.");
      }
   }

   private boolean isAuthenticationNeeded(HttpMethod var1) {
      AuthState var2 = var1.getHostAuthState();
      byte var3;
      if(var1.getStatusCode() == 401) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      var2.setAuthRequested((boolean)var3);
      AuthState var4 = var1.getProxyAuthState();
      byte var5;
      if(var1.getStatusCode() == 407) {
         var5 = 1;
      } else {
         boolean var7 = false;
      }

      var4.setAuthRequested((boolean)var5);
      boolean var6;
      if(!var1.getHostAuthState().isAuthRequested() && !var1.getProxyAuthState().isAuthRequested()) {
         var6 = false;
      } else {
         LOG.debug("Authorization required");
         if(var1.getDoAuthentication()) {
            var6 = true;
         } else {
            LOG.info("Authentication requested but doAuthentication is disabled");
            var6 = false;
         }
      }

      return var6;
   }

   private boolean isRedirectNeeded(HttpMethod var1) {
      boolean var2;
      switch(var1.getStatusCode()) {
      case 301:
      case 302:
      case 303:
      case 307:
         LOG.debug("Redirect required");
         if(var1.getFollowRedirects()) {
            var2 = true;
         } else {
            var2 = false;
         }
         break;
      case 304:
      case 305:
      case 306:
      default:
         var2 = false;
      }

      return var2;
   }

   private boolean processAuthenticationResponse(HttpMethod param1) {
      // $FF: Couldn't be decompiled
   }

   private boolean processProxyAuthChallenge(HttpMethod var1) throws MalformedChallengeException, AuthenticationException {
      AuthState var2 = var1.getProxyAuthState();
      Map var3 = AuthChallengeParser.parseChallenges(var1.getResponseHeaders("Proxy-Authenticate"));
      boolean var4;
      if(var3.isEmpty()) {
         LOG.debug("Proxy authentication challenge(s) not found");
         var4 = false;
      } else {
         AuthScheme var5 = null;

         label52: {
            AuthScheme var6;
            try {
               var6 = this.authProcessor.processChallenge(var2, var3);
            } catch (AuthChallengeException var25) {
               if(LOG.isWarnEnabled()) {
                  Log var8 = LOG;
                  String var9 = var25.getMessage();
                  var8.warn(var9);
               }
               break label52;
            }

            var5 = var6;
         }

         if(var5 == null) {
            var4 = false;
         } else {
            String var10 = this.conn.getProxyHost();
            int var11 = this.conn.getProxyPort();
            String var12 = var5.getRealm();
            String var13 = var5.getSchemeName();
            AuthScope var14 = new AuthScope(var10, var11, var12, var13);
            if(LOG.isDebugEnabled()) {
               Log var15 = LOG;
               String var16 = "Proxy authentication scope: " + var14;
               var15.debug(var16);
            }

            if(var2.isAuthAttempted() && var5.isComplete()) {
               HttpMethodParams var17 = var1.getParams();
               if(this.promptForProxyCredentials(var5, var17, var14) == null) {
                  if(LOG.isInfoEnabled()) {
                     Log var18 = LOG;
                     String var19 = "Failure authenticating with " + var14;
                     var18.info(var19);
                  }

                  var4 = false;
               } else {
                  var4 = true;
               }
            } else {
               var2.setAuthAttempted((boolean)1);
               Credentials var20 = this.state.getProxyCredentials(var14);
               if(var20 == null) {
                  HttpMethodParams var21 = var1.getParams();
                  this.promptForProxyCredentials(var5, var21, var14);
               }

               if(var20 == null) {
                  if(LOG.isInfoEnabled()) {
                     Log var23 = LOG;
                     String var24 = "No credentials available for " + var14;
                     var23.info(var24);
                  }

                  var4 = false;
               } else {
                  var4 = true;
               }
            }
         }
      }

      return var4;
   }

   private boolean processRedirectResponse(HttpMethod param1) throws RedirectException {
      // $FF: Couldn't be decompiled
   }

   private boolean processWWWAuthChallenge(HttpMethod var1) throws MalformedChallengeException, AuthenticationException {
      AuthState var2 = var1.getHostAuthState();
      Map var3 = AuthChallengeParser.parseChallenges(var1.getResponseHeaders("WWW-Authenticate"));
      boolean var4;
      if(var3.isEmpty()) {
         LOG.debug("Authentication challenge(s) not found");
         var4 = false;
      } else {
         AuthScheme var5 = null;

         label56: {
            AuthScheme var6;
            try {
               var6 = this.authProcessor.processChallenge(var2, var3);
            } catch (AuthChallengeException var25) {
               if(LOG.isWarnEnabled()) {
                  Log var8 = LOG;
                  String var9 = var25.getMessage();
                  var8.warn(var9);
               }
               break label56;
            }

            var5 = var6;
         }

         if(var5 == null) {
            var4 = false;
         } else {
            String var10 = var1.getParams().getVirtualHost();
            if(var10 == null) {
               var10 = this.conn.getHost();
            }

            int var11 = this.conn.getPort();
            String var12 = var5.getRealm();
            String var13 = var5.getSchemeName();
            AuthScope var14 = new AuthScope(var10, var11, var12, var13);
            if(LOG.isDebugEnabled()) {
               Log var15 = LOG;
               String var16 = "Authentication scope: " + var14;
               var15.debug(var16);
            }

            if(var2.isAuthAttempted() && var5.isComplete()) {
               HttpMethodParams var17 = var1.getParams();
               if(this.promptForCredentials(var5, var17, var14) == null) {
                  if(LOG.isInfoEnabled()) {
                     Log var18 = LOG;
                     String var19 = "Failure authenticating with " + var14;
                     var18.info(var19);
                  }

                  var4 = false;
               } else {
                  var4 = true;
               }
            } else {
               var2.setAuthAttempted((boolean)1);
               Credentials var20 = this.state.getCredentials(var14);
               if(var20 == null) {
                  HttpMethodParams var21 = var1.getParams();
                  this.promptForCredentials(var5, var21, var14);
               }

               if(var20 == null) {
                  if(LOG.isInfoEnabled()) {
                     Log var23 = LOG;
                     String var24 = "No credentials available for " + var14;
                     var23.info(var24);
                  }

                  var4 = false;
               } else {
                  var4 = true;
               }
            }
         }
      }

      return var4;
   }

   private Credentials promptForCredentials(AuthScheme var1, HttpParams var2, AuthScope var3) {
      LOG.debug("Credentials required");
      Credentials var4 = null;
      CredentialsProvider var5 = (CredentialsProvider)var2.getParameter("http.authentication.credential-provider");
      if(var5 != null) {
         label19: {
            Credentials var8;
            try {
               String var6 = var3.getHost();
               int var7 = var3.getPort();
               var8 = var5.getCredentials(var1, var6, var7, (boolean)0);
            } catch (CredentialsNotAvailableException var14) {
               Log var12 = LOG;
               String var13 = var14.getMessage();
               var12.warn(var13);
               break label19;
            }

            var4 = var8;
         }

         if(var4 != null) {
            this.state.setCredentials(var3, var4);
            if(LOG.isDebugEnabled()) {
               Log var9 = LOG;
               String var10 = var3 + " new credentials given";
               var9.debug(var10);
            }
         }
      } else {
         LOG.debug("Credentials provider not available");
      }

      return var4;
   }

   private Credentials promptForProxyCredentials(AuthScheme var1, HttpParams var2, AuthScope var3) {
      LOG.debug("Proxy credentials required");
      Credentials var4 = null;
      CredentialsProvider var5 = (CredentialsProvider)var2.getParameter("http.authentication.credential-provider");
      if(var5 != null) {
         label19: {
            Credentials var8;
            try {
               String var6 = var3.getHost();
               int var7 = var3.getPort();
               var8 = var5.getCredentials(var1, var6, var7, (boolean)1);
            } catch (CredentialsNotAvailableException var14) {
               Log var12 = LOG;
               String var13 = var14.getMessage();
               var12.warn(var13);
               break label19;
            }

            var4 = var8;
         }

         if(var4 != null) {
            this.state.setProxyCredentials(var3, var4);
            if(LOG.isDebugEnabled()) {
               Log var9 = LOG;
               String var10 = var3 + " new credentials given";
               var9.debug(var10);
            }
         }
      } else {
         LOG.debug("Proxy credentials provider not available");
      }

      return var4;
   }

   public void executeMethod(HttpMethod param1) throws IOException, HttpException {
      // $FF: Couldn't be decompiled
   }

   public HttpConnectionManager getConnectionManager() {
      return this.connectionManager;
   }

   public HostConfiguration getHostConfiguration() {
      return this.hostConfiguration;
   }

   public HttpParams getParams() {
      return this.params;
   }

   public HttpState getState() {
      return this.state;
   }
}
