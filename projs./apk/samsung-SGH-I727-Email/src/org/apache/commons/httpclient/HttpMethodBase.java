package org.apache.commons.httpclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Collection;
import org.apache.commons.httpclient.AutoCloseInputStream;
import org.apache.commons.httpclient.ChunkedInputStream;
import org.apache.commons.httpclient.ContentLengthInputStream;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HeaderGroup;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpContentTooLargeException;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpHost;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpParser;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.MethodRetryHandler;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.ProtocolException;
import org.apache.commons.httpclient.ResponseConsumedWatcher;
import org.apache.commons.httpclient.StatusLine;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.Wire;
import org.apache.commons.httpclient.WireLogInputStream;
import org.apache.commons.httpclient.auth.AuthState;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.cookie.CookieVersionSupport;
import org.apache.commons.httpclient.cookie.MalformedCookieException;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.httpclient.util.ExceptionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class HttpMethodBase implements HttpMethod {

   private static final int DEFAULT_INITIAL_BUFFER_SIZE = 4096;
   private static final Log LOG = LogFactory.getLog(HttpMethodBase.class);
   private static final int RESPONSE_WAIT_TIME_MS = 3000;
   private volatile boolean aborted;
   private boolean connectionCloseForced;
   private CookieSpec cookiespec;
   private boolean doAuthentication;
   protected HttpVersion effectiveVersion;
   private boolean followRedirects;
   private AuthState hostAuthState;
   private HttpHost httphost;
   private MethodRetryHandler methodRetryHandler;
   private HttpMethodParams params;
   private String path;
   private AuthState proxyAuthState;
   private String queryString;
   private int recoverableExceptionCount;
   private HeaderGroup requestHeaders;
   private boolean requestSent;
   private byte[] responseBody;
   private HttpConnection responseConnection;
   private HeaderGroup responseHeaders;
   private InputStream responseStream;
   private HeaderGroup responseTrailerHeaders;
   protected StatusLine statusLine;
   private boolean used;


   public HttpMethodBase() {
      HeaderGroup var1 = new HeaderGroup();
      this.requestHeaders = var1;
      this.statusLine = null;
      HeaderGroup var2 = new HeaderGroup();
      this.responseHeaders = var2;
      HeaderGroup var3 = new HeaderGroup();
      this.responseTrailerHeaders = var3;
      this.path = null;
      this.queryString = null;
      this.responseStream = null;
      this.responseConnection = null;
      this.responseBody = null;
      this.followRedirects = (boolean)0;
      this.doAuthentication = (boolean)1;
      HttpMethodParams var4 = new HttpMethodParams();
      this.params = var4;
      AuthState var5 = new AuthState();
      this.hostAuthState = var5;
      AuthState var6 = new AuthState();
      this.proxyAuthState = var6;
      this.used = (boolean)0;
      this.recoverableExceptionCount = 0;
      this.httphost = null;
      this.connectionCloseForced = (boolean)0;
      this.effectiveVersion = null;
      this.aborted = (boolean)0;
      this.requestSent = (boolean)0;
      this.cookiespec = null;
   }

   public HttpMethodBase(String param1) throws IllegalArgumentException, IllegalStateException {
      // $FF: Couldn't be decompiled
   }

   private static boolean canResponseHaveBody(int var0) {
      LOG.trace("enter HttpMethodBase.canResponseHaveBody(int)");
      boolean var1 = true;
      if(var0 >= 100 && var0 <= 199 || var0 == 204 || var0 == 304) {
         var1 = false;
      }

      return var1;
   }

   private void checkExecuteConditions(HttpState var1, HttpConnection var2) throws HttpException {
      if(var1 == null) {
         throw new IllegalArgumentException("HttpState parameter may not be null");
      } else if(var2 == null) {
         throw new IllegalArgumentException("HttpConnection parameter may not be null");
      } else if(this.aborted) {
         throw new IllegalStateException("Method has been aborted");
      } else if(!this.validate()) {
         throw new ProtocolException("HttpMethodBase object not valid");
      }
   }

   private void ensureConnectionRelease() {
      if(this.responseConnection != null) {
         this.responseConnection.releaseConnection();
         this.responseConnection = null;
      }
   }

   protected static String generateRequestLine(HttpConnection var0, String var1, String var2, String var3, String var4) {
      LOG.trace("enter HttpMethodBase.generateRequestLine(HttpConnection, String, String, String, String)");
      StringBuffer var5 = new StringBuffer();
      var5.append(var1);
      StringBuffer var7 = var5.append(" ");
      if(!var0.isTransparent()) {
         Protocol var8 = var0.getProtocol();
         String var9 = var8.getScheme().toLowerCase();
         var5.append(var9);
         StringBuffer var11 = var5.append("://");
         String var12 = var0.getHost();
         var5.append(var12);
         if(var0.getPort() != -1) {
            int var14 = var0.getPort();
            int var15 = var8.getDefaultPort();
            if(var14 != var15) {
               StringBuffer var16 = var5.append(":");
               int var17 = var0.getPort();
               var5.append(var17);
            }
         }
      }

      if(var2 == null) {
         StringBuffer var19 = var5.append("/");
      } else {
         if(!var0.isTransparent() && !var2.startsWith("/")) {
            StringBuffer var25 = var5.append("/");
         }

         var5.append(var2);
      }

      if(var3 != null) {
         if(var3.indexOf("?") != 0) {
            StringBuffer var20 = var5.append("?");
         }

         var5.append(var3);
      }

      StringBuffer var22 = var5.append(" ");
      var5.append(var4);
      StringBuffer var24 = var5.append("\r\n");
      return var5.toString();
   }

   private CookieSpec getCookieSpec(HttpState var1) {
      if(this.cookiespec == null) {
         int var2 = var1.getCookiePolicy();
         if(var2 == -1) {
            CookieSpec var3 = CookiePolicy.getCookieSpec(this.params.getCookiePolicy());
            this.cookiespec = var3;
         } else {
            CookieSpec var6 = CookiePolicy.getSpecByPolicy(var2);
            this.cookiespec = var6;
         }

         CookieSpec var4 = this.cookiespec;
         Collection var5 = (Collection)this.params.getParameter("http.dateparser.patterns");
         var4.setValidDateFormats(var5);
      }

      return this.cookiespec;
   }

   private String getRequestLine(HttpConnection var1) {
      String var2 = this.getName();
      String var3 = this.getPath();
      String var4 = this.getQueryString();
      String var5 = this.effectiveVersion.toString();
      return generateRequestLine(var1, var2, var3, var4, var5);
   }

   private InputStream readResponseBody(HttpConnection var1) throws HttpException, IOException {
      LOG.trace("enter HttpMethodBase.readResponseBody(HttpConnection)");
      Object var2 = null;
      this.responseBody = (byte[])var2;
      Object var3 = var1.getResponseInputStream();
      if(Wire.CONTENT_WIRE.enabled()) {
         WireLogInputStream var4 = new WireLogInputStream;
         Wire var5 = Wire.CONTENT_WIRE;
         var4.<init>((InputStream)var3, var5);
         var3 = var4;
      }

      boolean var9 = canResponseHaveBody(this.statusLine.getStatusCode());
      Header var10 = this.responseHeaders.getFirstHeader("Transfer-Encoding");
      ChunkedInputStream var27;
      if(var10 != null) {
         label62: {
            String var11 = var10.getValue();
            String var12 = "chunked";
            if(!var12.equalsIgnoreCase(var11)) {
               String var14 = "identity";
               if(!var14.equalsIgnoreCase(var11) && LOG.isWarnEnabled()) {
                  Log var16 = LOG;
                  StringBuilder var17 = (new StringBuilder()).append("Unsupported transfer encoding: ");
                  String var19 = var17.append(var11).toString();
                  var16.warn(var19);
               }
            }

            HeaderElement[] var20 = var10.getElements();
            int var21 = var20.length;
            if(var21 > 0) {
               int var22 = var21 - 1;
               String var23 = var20[var22].getName();
               if("chunked".equalsIgnoreCase(var23)) {
                  int var24 = var1.getParams().getSoTimeout();
                  if(var1.isResponseAvailable(var24)) {
                     var27 = new ChunkedInputStream((InputStream)var3, this);
                  } else {
                     if(this.getParams().isParameterTrue("http.protocol.strict-transfer-encoding")) {
                        throw new ProtocolException("Chunk-encoded body declared but not sent");
                     }

                     LOG.warn("Chunk-encoded body missing");
                  }
                  break label62;
               }
            }

            LOG.info("Response content is not chunk-encoded");
            byte var40 = 1;
            this.setConnectionCloseForced((boolean)var40);
         }
      } else {
         long var42 = this.getResponseContentLength();
         if(var42 == 65535L) {
            if(var9) {
               HttpVersion var44 = this.effectiveVersion;
               HttpVersion var45 = HttpVersion.HTTP_1_1;
               if(var44.greaterEquals(var45)) {
                  Header var46 = this.responseHeaders.getFirstHeader("Connection");
                  String var47 = null;
                  if(var46 != null) {
                     var47 = var46.getValue();
                  }

                  String var48 = "close";
                  if(!var48.equalsIgnoreCase(var47)) {
                     LOG.info("Response content length is not known");
                     byte var51 = 1;
                     this.setConnectionCloseForced((boolean)var51);
                  }
               }
            }
         } else {
            new ContentLengthInputStream((InputStream)var3, var42);
         }
      }

      ChunkedInputStream var31;
      if(!var9) {
         var31 = null;
      } else {
         var31 = var27;
      }

      Object var32;
      if(var31 != null) {
         var32 = new AutoCloseInputStream;
         HttpMethodBase.1 var33 = new HttpMethodBase.1();
         var32.<init>(var31, var33);
      } else {
         var32 = var31;
      }

      return (InputStream)var32;
   }

   private boolean responseAvailable() {
      boolean var1;
      if(this.responseBody == null && this.responseStream == null) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void abort() {
      if(!this.aborted) {
         this.aborted = (boolean)1;
         HttpConnection var1 = this.responseConnection;
         if(var1 != null) {
            var1.close();
         }
      }
   }

   protected void addCookieRequestHeader(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.addCookieRequestHeader(HttpState, HttpConnection)");
      Header[] var3 = this.getRequestHeaderGroup().getHeaders("Cookie");
      int var4 = 0;

      while(true) {
         int var5 = var3.length;
         if(var4 >= var5) {
            CookieSpec var7 = this.getCookieSpec(var1);
            String var8 = this.params.getVirtualHost();
            if(var8 == null) {
               var8 = var2.getHost();
            }

            int var9 = var2.getPort();
            String var10 = this.getPath();
            boolean var11 = var2.isSecure();
            Cookie[] var12 = var1.getCookies();
            Cookie[] var13 = var7.match(var8, var9, var10, var11, var12);
            if(var13 == null) {
               return;
            } else if(var13.length <= 0) {
               return;
            } else {
               if(this.getParams().isParameterTrue("http.protocol.single-cookie-header")) {
                  String var14 = var7.formatCookies(var13);
                  HeaderGroup var15 = this.getRequestHeaderGroup();
                  Header var16 = new Header("Cookie", var14, (boolean)1);
                  var15.addHeader(var16);
               } else {
                  var4 = 0;

                  while(true) {
                     int var23 = var13.length;
                     if(var4 >= var23) {
                        break;
                     }

                     Cookie var24 = var13[var4];
                     String var25 = var7.formatCookie(var24);
                     HeaderGroup var26 = this.getRequestHeaderGroup();
                     Header var27 = new Header("Cookie", var25, (boolean)1);
                     var26.addHeader(var27);
                     ++var4;
                  }
               }

               if(!(var7 instanceof CookieVersionSupport)) {
                  return;
               } else {
                  CookieVersionSupport var17 = (CookieVersionSupport)var7;
                  int var18 = var17.getVersion();
                  boolean var19 = false;
                  byte var30 = 0;

                  while(true) {
                     int var20 = var13.length;
                     if(var30 >= var20) {
                        if(!var19) {
                           return;
                        }

                        HeaderGroup var28 = this.getRequestHeaderGroup();
                        Header var29 = var17.getVersionHeader();
                        var28.addHeader(var29);
                        return;
                     }

                     int var21 = var13[var30].getVersion();
                     if(var18 != var21) {
                        var19 = true;
                     }

                     int var22 = var30 + 1;
                  }
               }
            }
         }

         Header var6 = var3[var4];
         if(var6.isAutogenerated()) {
            this.getRequestHeaderGroup().removeHeader(var6);
         }

         ++var4;
      }
   }

   protected void addHostRequestHeader(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.addHostRequestHeader(HttpState, HttpConnection)");
      String var3 = this.params.getVirtualHost();
      if(var3 != null) {
         Log var4 = LOG;
         String var5 = "Using virtual host name: " + var3;
         var4.debug(var5);
      } else {
         var3 = var2.getHost();
      }

      int var6 = var2.getPort();
      if(LOG.isDebugEnabled()) {
         LOG.debug("Adding Host request header");
      }

      if(var2.getProtocol().getDefaultPort() != var6) {
         var3 = var3 + ":" + var6;
      }

      this.setRequestHeader("Host", var3);
   }

   protected void addProxyConnectionHeader(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.addProxyConnectionHeader(HttpState, HttpConnection)");
      if(!var2.isTransparent()) {
         if(this.getRequestHeader("Proxy-Connection") == null) {
            this.addRequestHeader("Proxy-Connection", "Keep-Alive");
         }
      }
   }

   public void addRequestHeader(String var1, String var2) {
      Header var3 = new Header(var1, var2);
      this.addRequestHeader(var3);
   }

   public void addRequestHeader(Header var1) {
      LOG.trace("HttpMethodBase.addRequestHeader(Header)");
      if(var1 == null) {
         LOG.debug("null header value ignored");
      } else {
         this.getRequestHeaderGroup().addHeader(var1);
      }
   }

   protected void addRequestHeaders(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.addRequestHeaders(HttpState, HttpConnection)");
      this.addUserAgentRequestHeader(var1, var2);
      this.addHostRequestHeader(var1, var2);
      this.addCookieRequestHeader(var1, var2);
      this.addProxyConnectionHeader(var1, var2);
   }

   public void addResponseFooter(Header var1) {
      this.getResponseTrailerHeaderGroup().addHeader(var1);
   }

   protected void addUserAgentRequestHeader(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.addUserAgentRequestHeaders(HttpState, HttpConnection)");
      if(this.getRequestHeader("User-Agent") == null) {
         String var3 = (String)this.getParams().getParameter("http.useragent");
         if(var3 == null) {
            var3 = "Jakarta Commons-HttpClient";
         }

         this.setRequestHeader("User-Agent", var3);
      }
   }

   protected void checkNotUsed() throws IllegalStateException {
      if(this.used) {
         throw new IllegalStateException("Already used.");
      }
   }

   protected void checkUsed() throws IllegalStateException {
      if(!this.used) {
         throw new IllegalStateException("Not Used.");
      }
   }

   public int execute(HttpState var1, HttpConnection var2) throws HttpException, IOException {
      LOG.trace("enter HttpMethodBase.execute(HttpState, HttpConnection)");
      this.responseConnection = var2;
      this.checkExecuteConditions(var1, var2);
      this.statusLine = null;
      this.connectionCloseForced = (boolean)0;
      var2.setLastResponseInputStream((InputStream)null);
      if(this.effectiveVersion == null) {
         HttpVersion var3 = this.params.getVersion();
         this.effectiveVersion = var3;
      }

      this.writeRequest(var1, var2);
      this.requestSent = (boolean)1;
      this.readResponse(var1, var2);
      this.used = (boolean)1;
      return this.statusLine.getStatusCode();
   }

   void fakeResponse(StatusLine var1, HeaderGroup var2, InputStream var3) {
      this.used = (boolean)1;
      this.statusLine = var1;
      this.responseHeaders = var2;
      this.responseBody = null;
      this.responseStream = var3;
   }

   public String getAuthenticationRealm() {
      return this.hostAuthState.getRealm();
   }

   protected String getContentCharSet(Header var1) {
      LOG.trace("enter getContentCharSet( Header contentheader )");
      String var2 = null;
      if(var1 != null) {
         HeaderElement[] var3 = var1.getElements();
         if(var3.length == 1) {
            NameValuePair var4 = var3[0].getParameterByName("charset");
            if(var4 != null) {
               var2 = var4.getValue();
            }
         }
      }

      if(var2 == null) {
         var2 = this.getParams().getContentCharset();
         if(LOG.isDebugEnabled()) {
            Log var5 = LOG;
            String var6 = "Default charset used: " + var2;
            var5.debug(var6);
         }
      }

      return var2;
   }

   public boolean getDoAuthentication() {
      return this.doAuthentication;
   }

   public HttpVersion getEffectiveVersion() {
      return this.effectiveVersion;
   }

   public boolean getFollowRedirects() {
      return this.followRedirects;
   }

   public AuthState getHostAuthState() {
      return this.hostAuthState;
   }

   public HostConfiguration getHostConfiguration() {
      HostConfiguration var1 = new HostConfiguration();
      HttpHost var2 = this.httphost;
      var1.setHost(var2);
      return var1;
   }

   public MethodRetryHandler getMethodRetryHandler() {
      return this.methodRetryHandler;
   }

   public abstract String getName();

   public HttpMethodParams getParams() {
      return this.params;
   }

   public String getPath() {
      String var1;
      if(this.path != null && this.path.length() != 0) {
         var1 = this.path;
      } else {
         var1 = "/";
      }

      return var1;
   }

   public AuthState getProxyAuthState() {
      return this.proxyAuthState;
   }

   public String getProxyAuthenticationRealm() {
      return this.proxyAuthState.getRealm();
   }

   public String getQueryString() {
      return this.queryString;
   }

   public int getRecoverableExceptionCount() {
      return this.recoverableExceptionCount;
   }

   public String getRequestCharSet() {
      Header var1 = this.getRequestHeader("Content-Type");
      return this.getContentCharSet(var1);
   }

   public Header getRequestHeader(String var1) {
      Header var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = this.getRequestHeaderGroup().getCondensedHeader(var1);
      }

      return var2;
   }

   protected HeaderGroup getRequestHeaderGroup() {
      return this.requestHeaders;
   }

   public Header[] getRequestHeaders() {
      return this.getRequestHeaderGroup().getAllHeaders();
   }

   public Header[] getRequestHeaders(String var1) {
      return this.getRequestHeaderGroup().getHeaders(var1);
   }

   public byte[] getResponseBody() throws IOException {
      if(this.responseBody == null) {
         InputStream var1 = this.getResponseBodyAsStream();
         if(var1 != null) {
            long var2 = this.getResponseContentLength();
            if(var2 > 2147483647L) {
               String var4 = "Content too large to be buffered: " + var2 + " bytes";
               throw new IOException(var4);
            }

            label31: {
               int var5 = this.getParams().getIntParameter("http.method.response.buffer.warnlimit", 1048576);
               if(var2 != 65535L) {
                  long var6 = (long)var5;
                  if(var2 <= var6) {
                     break label31;
                  }
               }

               LOG.warn("Going to buffer response body of large or unknown size. Using getResponseBodyAsStream instead is recommended.");
            }

            LOG.debug("Buffering response body");
            ByteArrayOutputStream var8 = new ByteArrayOutputStream;
            int var9;
            if(var2 > 0L) {
               var9 = (int)var2;
            } else {
               var9 = 4096;
            }

            var8.<init>(var9);
            byte[] var10 = new byte[4096];

            while(true) {
               int var11 = var1.read(var10);
               if(var11 <= 0) {
                  var8.close();
                  this.setResponseStream((InputStream)null);
                  byte[] var12 = var8.toByteArray();
                  this.responseBody = var12;
                  break;
               }

               var8.write(var10, 0, var11);
            }
         }
      }

      return this.responseBody;
   }

   public byte[] getResponseBody(int var1) throws IOException {
      if(var1 < 0) {
         throw new IllegalArgumentException("maxlen must be positive");
      } else {
         if(this.responseBody == null) {
            InputStream var2 = this.getResponseBodyAsStream();
            if(var2 != null) {
               long var3 = this.getResponseContentLength();
               if(var3 != 65535L) {
                  long var5 = (long)var1;
                  if(var3 > var5) {
                     String var7 = "Content-Length is " + var3;
                     throw new HttpContentTooLargeException(var7, var1);
                  }
               }

               LOG.debug("Buffering response body");
               ByteArrayOutputStream var8 = new ByteArrayOutputStream;
               int var9;
               if(var3 > 0L) {
                  var9 = (int)var3;
               } else {
                  var9 = 4096;
               }

               var8.<init>(var9);
               byte[] var10 = new byte[2048];
               int var11 = 0;

               do {
                  int var12 = var10.length;
                  int var13 = var1 - var11;
                  int var14 = Math.min(var12, var13);
                  int var15 = var2.read(var10, 0, var14);
                  if(var15 == -1) {
                     break;
                  }

                  var8.write(var10, 0, var15);
                  var11 += var15;
               } while(var11 < var1);

               this.setResponseStream((InputStream)null);
               if(var11 == var1 && var2.read() != -1) {
                  String var16 = "Content-Length not known but larger than " + var1;
                  throw new HttpContentTooLargeException(var16, var1);
               }

               byte[] var17 = var8.toByteArray();
               this.responseBody = var17;
            }
         }

         return this.responseBody;
      }
   }

   public InputStream getResponseBodyAsStream() throws IOException {
      Object var1;
      if(this.responseStream != null) {
         var1 = this.responseStream;
      } else if(this.responseBody != null) {
         byte[] var2 = this.responseBody;
         ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
         LOG.debug("re-creating response stream from byte array");
         var1 = var3;
      } else {
         var1 = null;
      }

      return (InputStream)var1;
   }

   public String getResponseBodyAsString() throws IOException {
      byte[] var1 = null;
      if(this.responseAvailable()) {
         var1 = this.getResponseBody();
      }

      String var3;
      if(var1 != null) {
         String var2 = this.getResponseCharSet();
         var3 = EncodingUtil.getString(var1, var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public String getResponseBodyAsString(int var1) throws IOException {
      if(var1 < 0) {
         throw new IllegalArgumentException("maxlen must be positive");
      } else {
         byte[] var2 = null;
         if(this.responseAvailable()) {
            var2 = this.getResponseBody(var1);
         }

         String var4;
         if(var2 != null) {
            String var3 = this.getResponseCharSet();
            var4 = EncodingUtil.getString(var2, var3);
         } else {
            var4 = null;
         }

         return var4;
      }
   }

   public String getResponseCharSet() {
      Header var1 = this.getResponseHeader("Content-Type");
      return this.getContentCharSet(var1);
   }

   public long getResponseContentLength() {
      Header[] var1 = this.getResponseHeaderGroup().getHeaders("Content-Length");
      long var2;
      if(var1.length == 0) {
         var2 = 65535L;
      } else {
         if(var1.length > 1) {
            LOG.warn("Multiple content-length headers detected");
         }

         int var4 = var1.length - 1;

         while(true) {
            if(var4 >= 0) {
               Header var5 = var1[var4];

               long var6;
               try {
                  var6 = Long.parseLong(var5.getValue());
               } catch (NumberFormatException var15) {
                  if(LOG.isWarnEnabled()) {
                     StringBuffer var9 = new StringBuffer();
                     StringBuffer var10 = var9.append("Invalid content-length value: ");
                     String var11 = var15.getMessage();
                     var10.append(var11);
                     Log var13 = LOG;
                     String var14 = var9.toString();
                     var13.warn(var14);
                  }

                  var4 += -1;
                  continue;
               }

               var2 = var6;
               break;
            }

            var2 = 65535L;
            break;
         }
      }

      return var2;
   }

   public Header getResponseFooter(String var1) {
      Header var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = this.getResponseTrailerHeaderGroup().getCondensedHeader(var1);
      }

      return var2;
   }

   public Header[] getResponseFooters() {
      return this.getResponseTrailerHeaderGroup().getAllHeaders();
   }

   public Header getResponseHeader(String var1) {
      Header var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = this.getResponseHeaderGroup().getCondensedHeader(var1);
      }

      return var2;
   }

   protected HeaderGroup getResponseHeaderGroup() {
      return this.responseHeaders;
   }

   public Header[] getResponseHeaders() {
      return this.getResponseHeaderGroup().getAllHeaders();
   }

   public Header[] getResponseHeaders(String var1) {
      return this.getResponseHeaderGroup().getHeaders(var1);
   }

   protected InputStream getResponseStream() {
      return this.responseStream;
   }

   protected HeaderGroup getResponseTrailerHeaderGroup() {
      return this.responseTrailerHeaders;
   }

   public int getStatusCode() {
      return this.statusLine.getStatusCode();
   }

   public StatusLine getStatusLine() {
      return this.statusLine;
   }

   public String getStatusText() {
      return this.statusLine.getReasonPhrase();
   }

   public URI getURI() throws URIException {
      StringBuffer var1 = new StringBuffer();
      if(this.httphost != null) {
         String var2 = this.httphost.getProtocol().getScheme();
         var1.append(var2);
         StringBuffer var4 = var1.append("://");
         String var5 = this.httphost.getHostName();
         var1.append(var5);
         int var7 = this.httphost.getPort();
         if(var7 != -1) {
            int var8 = this.httphost.getProtocol().getDefaultPort();
            if(var7 != var8) {
               StringBuffer var9 = var1.append(":");
               var1.append(var7);
            }
         }
      }

      String var11 = this.path;
      var1.append(var11);
      if(this.queryString != null) {
         StringBuffer var13 = var1.append('?');
         String var14 = this.queryString;
         var1.append(var14);
      }

      String var16 = this.getParams().getUriCharset();
      String var17 = var1.toString();
      return new URI(var17, (boolean)1, var16);
   }

   public boolean hasBeenUsed() {
      return this.used;
   }

   public boolean isAborted() {
      return this.aborted;
   }

   protected boolean isConnectionCloseForced() {
      return this.connectionCloseForced;
   }

   public boolean isHttp11() {
      HttpVersion var1 = this.params.getVersion();
      HttpVersion var2 = HttpVersion.HTTP_1_1;
      return var1.equals(var2);
   }

   public boolean isRequestSent() {
      return this.requestSent;
   }

   public boolean isStrictMode() {
      return false;
   }

   protected void processCookieHeaders(CookieSpec var1, Header[] var2, HttpState var3, HttpConnection var4) {
      LOG.trace("enter HttpMethodBase.processCookieHeaders(Header[], HttpState, HttpConnection)");
      String var5 = this.params.getVirtualHost();
      if(var5 == null) {
         var5 = var4.getHost();
      }

      int var6 = 0;

      while(true) {
         int var7 = var2.length;
         if(var6 >= var7) {
            return;
         }

         Header var10 = var2[var6];
         Cookie[] var11 = null;

         label52: {
            Cookie[] var15;
            try {
               int var12 = var4.getPort();
               String var13 = this.getPath();
               boolean var14 = var4.isSecure();
               var15 = var1.parse(var5, var12, var13, var14, var10);
            } catch (MalformedCookieException var63) {
               if(LOG.isWarnEnabled()) {
                  StringBuffer var39 = new StringBuffer();
                  String var41 = "Invalid cookie header: \"";
                  StringBuffer var42 = var39.append(var41);
                  String var43 = var10.getValue();
                  StringBuffer var44 = var42.append(var43).append("\". ");
                  String var45 = var63.getMessage();
                  var44.append(var45);
                  Log var47 = LOG;
                  String var48 = var39.toString();
                  var47.warn(var48);
               }
               break label52;
            }

            var11 = var15;
         }

         if(var11 != null) {
            int var16 = 0;

            while(true) {
               int var17 = var11.length;
               if(var16 >= var17) {
                  break;
               }

               Cookie var20 = var11[var16];

               try {
                  int var21 = var4.getPort();
                  String var22 = this.getPath();
                  boolean var23 = var4.isSecure();
                  var1.validate(var5, var21, var22, var23, var20);
                  var3.addCookie(var20);
                  if(LOG.isDebugEnabled()) {
                     StringBuffer var28 = new StringBuffer();
                     String var30 = "Cookie accepted: \"";
                     StringBuffer var31 = var28.append(var30);
                     String var34 = var1.formatCookie(var20);
                     StringBuffer var35 = var31.append(var34).append("\"");
                     Log var36 = LOG;
                     String var37 = var28.toString();
                     var36.debug(var37);
                  }
               } catch (MalformedCookieException var62) {
                  if(LOG.isWarnEnabled()) {
                     StringBuffer var50 = new StringBuffer();
                     String var52 = "Cookie rejected: \"";
                     StringBuffer var53 = var50.append(var52);
                     String var56 = var1.formatCookie(var20);
                     StringBuffer var57 = var53.append(var56).append("\". ");
                     String var58 = var62.getMessage();
                     var57.append(var58);
                     Log var60 = LOG;
                     String var61 = var50.toString();
                     var60.warn(var61);
                  }
               }

               ++var16;
            }
         }

         ++var6;
      }
   }

   protected void processResponseBody(HttpState var1, HttpConnection var2) {}

   protected void processResponseHeaders(HttpState var1, HttpConnection var2) {
      LOG.trace("enter HttpMethodBase.processResponseHeaders(HttpState, HttpConnection)");
      CookieSpec var3 = this.getCookieSpec(var1);
      Header[] var4 = this.getResponseHeaderGroup().getHeaders("set-cookie");
      this.processCookieHeaders(var3, var4, var1, var2);
      if(var3 instanceof CookieVersionSupport) {
         if(((CookieVersionSupport)var3).getVersion() > 0) {
            Header[] var5 = this.getResponseHeaderGroup().getHeaders("set-cookie2");
            this.processCookieHeaders(var3, var5, var1, var2);
         }
      }
   }

   protected void processStatusLine(HttpState var1, HttpConnection var2) {}

   protected void readResponse(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.readResponse(HttpState, HttpConnection)");

      while(this.statusLine == null) {
         this.readStatusLine(var1, var2);
         this.processStatusLine(var1, var2);
         this.readResponseHeaders(var1, var2);
         this.processResponseHeaders(var1, var2);
         int var3 = this.statusLine.getStatusCode();
         if(var3 >= 100 && var3 < 200) {
            if(LOG.isInfoEnabled()) {
               StringBuffer var4 = new StringBuffer();
               StringBuffer var5 = var4.append("Discarding unexpected response: ");
               String var6 = this.statusLine.toString();
               var5.append(var6);
               Log var8 = LOG;
               String var9 = var4.toString();
               var8.info(var9);
            }

            this.statusLine = null;
         }
      }

      this.readResponseBody(var1, var2);
      this.processResponseBody(var1, var2);
   }

   protected void readResponseBody(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.readResponseBody(HttpState, HttpConnection)");
      InputStream var3 = this.readResponseBody(var2);
      if(var3 == null) {
         this.responseBodyConsumed();
      } else {
         var2.setLastResponseInputStream(var3);
         this.setResponseStream(var3);
      }
   }

   protected void readResponseHeaders(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.readResponseHeaders(HttpState,HttpConnection)");
      this.getResponseHeaderGroup().clear();
      InputStream var3 = var2.getResponseInputStream();
      String var4 = this.getParams().getHttpElementCharset();
      Header[] var5 = HttpParser.parseHeaders(var3, var4);
      this.getResponseHeaderGroup().setHeaders(var5);
   }

   protected void readStatusLine(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.readStatusLine(HttpState, HttpConnection)");
      int var3 = this.getParams().getIntParameter("http.protocol.status-line-garbage-limit", Integer.MAX_VALUE);
      int var4 = 0;

      while(true) {
         String var5 = this.getParams().getHttpElementCharset();
         String var6 = var2.readLine(var5);
         if(var6 == null && var4 == 0) {
            StringBuilder var7 = (new StringBuilder()).append("The server ");
            String var8 = var2.getHost();
            String var9 = var7.append(var8).append(" failed to respond").toString();
            throw new NoHttpResponseException(var9);
         }

         if(Wire.HEADER_WIRE.enabled()) {
            Wire var10 = Wire.HEADER_WIRE;
            String var11 = var6 + "\r\n";
            var10.input(var11);
         }

         if(var6 != null && StatusLine.startsWithHTTP(var6)) {
            StatusLine var12 = new StatusLine(var6);
            this.statusLine = var12;
            String var13 = this.statusLine.getHttpVersion();
            if(this.getParams().isParameterFalse("http.protocol.unambiguous-statusline") && var13.equals("HTTP")) {
               HttpMethodParams var14 = this.getParams();
               HttpVersion var15 = HttpVersion.HTTP_1_0;
               var14.setVersion(var15);
               if(!LOG.isWarnEnabled()) {
                  return;
               }

               Log var16 = LOG;
               StringBuilder var17 = (new StringBuilder()).append("Ambiguous status line (HTTP protocol version missing):");
               String var18 = this.statusLine.toString();
               String var19 = var17.append(var18).toString();
               var16.warn(var19);
               return;
            }

            HttpVersion var23 = HttpVersion.parse(var13);
            this.effectiveVersion = var23;
            return;
         }

         if(var6 == null || var4 >= var3) {
            StringBuilder var20 = (new StringBuilder()).append("The server ");
            String var21 = var2.getHost();
            String var22 = var20.append(var21).append(" failed to respond with a valid HTTP response").toString();
            throw new ProtocolException(var22);
         }

         ++var4;
      }
   }

   public void recycle() {
      LOG.trace("enter HttpMethodBase.recycle()");
      this.releaseConnection();
      this.path = null;
      this.followRedirects = (boolean)0;
      this.doAuthentication = (boolean)1;
      this.queryString = null;
      this.getRequestHeaderGroup().clear();
      this.getResponseHeaderGroup().clear();
      this.getResponseTrailerHeaderGroup().clear();
      this.statusLine = null;
      this.effectiveVersion = null;
      this.aborted = (boolean)0;
      this.used = (boolean)0;
      HttpMethodParams var1 = new HttpMethodParams();
      this.params = var1;
      this.responseBody = null;
      this.recoverableExceptionCount = 0;
      this.connectionCloseForced = (boolean)0;
      this.hostAuthState.invalidate();
      this.proxyAuthState.invalidate();
      this.cookiespec = null;
      this.requestSent = (boolean)0;
   }

   public void releaseConnection() {
      // $FF: Couldn't be decompiled
   }

   public void removeRequestHeader(String var1) {
      Header[] var2 = this.getRequestHeaderGroup().getHeaders(var1);
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            return;
         }

         HeaderGroup var5 = this.getRequestHeaderGroup();
         Header var6 = var2[var3];
         var5.removeHeader(var6);
         ++var3;
      }
   }

   public void removeRequestHeader(Header var1) {
      if(var1 != null) {
         this.getRequestHeaderGroup().removeHeader(var1);
      }
   }

   protected void responseBodyConsumed() {
      this.responseStream = null;
      if(this.responseConnection != null) {
         this.responseConnection.setLastResponseInputStream((InputStream)null);
         HttpConnection var1 = this.responseConnection;
         if(this.shouldCloseConnection(var1)) {
            this.responseConnection.close();
         } else {
            try {
               if(this.responseConnection.isResponseAvailable()) {
                  if(this.getParams().isParameterTrue("http.protocol.warn-extra-input")) {
                     LOG.warn("Extra response data detected - closing connection");
                  }

                  this.responseConnection.close();
               }
            } catch (IOException var5) {
               Log var3 = LOG;
               String var4 = var5.getMessage();
               var3.warn(var4);
               this.responseConnection.close();
            }
         }
      }

      this.connectionCloseForced = (boolean)0;
      this.ensureConnectionRelease();
   }

   protected void setConnectionCloseForced(boolean var1) {
      if(LOG.isDebugEnabled()) {
         Log var2 = LOG;
         String var3 = "Force-close connection: " + var1;
         var2.debug(var3);
      }

      this.connectionCloseForced = var1;
   }

   public void setDoAuthentication(boolean var1) {
      this.doAuthentication = var1;
   }

   public void setFollowRedirects(boolean var1) {
      this.followRedirects = var1;
   }

   public void setHostConfiguration(HostConfiguration var1) {
      if(var1 != null) {
         String var2 = var1.getHost();
         int var3 = var1.getPort();
         Protocol var4 = var1.getProtocol();
         HttpHost var5 = new HttpHost(var2, var3, var4);
         this.httphost = var5;
      } else {
         this.httphost = null;
      }
   }

   public void setHttp11(boolean var1) {
      if(var1) {
         HttpMethodParams var2 = this.params;
         HttpVersion var3 = HttpVersion.HTTP_1_1;
         var2.setVersion(var3);
      } else {
         HttpMethodParams var4 = this.params;
         HttpVersion var5 = HttpVersion.HTTP_1_0;
         var4.setVersion(var5);
      }
   }

   public void setMethodRetryHandler(MethodRetryHandler var1) {
      this.methodRetryHandler = var1;
   }

   public void setParams(HttpMethodParams var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameters may not be null");
      } else {
         this.params = var1;
      }
   }

   public void setPath(String var1) {
      this.path = var1;
   }

   public void setQueryString(String var1) {
      this.queryString = var1;
   }

   public void setQueryString(NameValuePair[] var1) {
      LOG.trace("enter HttpMethodBase.setQueryString(NameValuePair[])");
      String var2 = EncodingUtil.formUrlEncode(var1, "UTF-8");
      this.queryString = var2;
   }

   public void setRequestHeader(String var1, String var2) {
      Header var3 = new Header(var1, var2);
      this.setRequestHeader(var3);
   }

   public void setRequestHeader(Header var1) {
      HeaderGroup var2 = this.getRequestHeaderGroup();
      String var3 = var1.getName();
      Header[] var4 = var2.getHeaders(var3);
      int var5 = 0;

      while(true) {
         int var6 = var4.length;
         if(var5 >= var6) {
            this.getRequestHeaderGroup().addHeader(var1);
            return;
         }

         HeaderGroup var7 = this.getRequestHeaderGroup();
         Header var8 = var4[var5];
         var7.removeHeader(var8);
         ++var5;
      }
   }

   protected void setResponseStream(InputStream var1) {
      this.responseStream = var1;
   }

   public void setStrictMode(boolean var1) {
      if(var1) {
         this.params.makeStrict();
      } else {
         this.params.makeLenient();
      }
   }

   public void setURI(URI var1) throws URIException {
      if(var1.isAbsoluteURI()) {
         HttpHost var2 = new HttpHost(var1);
         this.httphost = var2;
      }

      String var3;
      if(var1.getPath() == null) {
         var3 = "/";
      } else {
         var3 = var1.getEscapedPath();
      }

      this.setPath(var3);
      String var4 = var1.getEscapedQuery();
      this.setQueryString(var4);
   }

   protected boolean shouldCloseConnection(HttpConnection var1) {
      byte var2;
      if(this.isConnectionCloseForced()) {
         LOG.debug("Should force-close connection.");
         var2 = 1;
      } else {
         Header var3 = null;
         if(!var1.isTransparent()) {
            var3 = this.responseHeaders.getFirstHeader("proxy-connection");
         }

         if(var3 == null) {
            var3 = this.responseHeaders.getFirstHeader("connection");
         }

         if(var3 == null) {
            var3 = this.requestHeaders.getFirstHeader("connection");
         }

         if(var3 != null) {
            if(var3.getValue().equalsIgnoreCase("close")) {
               if(LOG.isDebugEnabled()) {
                  Log var4 = LOG;
                  StringBuilder var5 = (new StringBuilder()).append("Should close connection in response to directive: ");
                  String var6 = var3.getValue();
                  String var7 = var5.append(var6).toString();
                  var4.debug(var7);
               }

               var2 = 1;
               return (boolean)var2;
            }

            if(var3.getValue().equalsIgnoreCase("keep-alive")) {
               if(LOG.isDebugEnabled()) {
                  Log var8 = LOG;
                  StringBuilder var9 = (new StringBuilder()).append("Should NOT close connection in response to directive: ");
                  String var10 = var3.getValue();
                  String var11 = var9.append(var10).toString();
                  var8.debug(var11);
               }

               var2 = 0;
               return (boolean)var2;
            }

            if(LOG.isDebugEnabled()) {
               Log var12 = LOG;
               StringBuilder var13 = (new StringBuilder()).append("Unknown directive: ");
               String var14 = var3.toExternalForm();
               String var15 = var13.append(var14).toString();
               var12.debug(var15);
            }
         }

         LOG.debug("Resorting to protocol version default close connection policy");
         HttpVersion var16 = this.effectiveVersion;
         HttpVersion var17 = HttpVersion.HTTP_1_1;
         if(var16.greaterEquals(var17)) {
            if(LOG.isDebugEnabled()) {
               Log var18 = LOG;
               StringBuilder var19 = (new StringBuilder()).append("Should NOT close connection, using ");
               String var20 = this.effectiveVersion.toString();
               String var21 = var19.append(var20).toString();
               var18.debug(var21);
            }
         } else if(LOG.isDebugEnabled()) {
            Log var24 = LOG;
            StringBuilder var25 = (new StringBuilder()).append("Should close connection, using ");
            String var26 = this.effectiveVersion.toString();
            String var27 = var25.append(var26).toString();
            var24.debug(var27);
         }

         HttpVersion var22 = this.effectiveVersion;
         HttpVersion var23 = HttpVersion.HTTP_1_0;
         var2 = var22.lessEquals(var23);
      }

      return (boolean)var2;
   }

   public boolean validate() {
      return true;
   }

   protected void writeRequest(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.writeRequest(HttpState, HttpConnection)");
      this.writeRequestLine(var1, var2);
      this.writeRequestHeaders(var1, var2);
      var2.writeLine();
      if(Wire.HEADER_WIRE.enabled()) {
         Wire.HEADER_WIRE.output("\r\n");
      }

      HttpVersion var3 = this.getParams().getVersion();
      Header var4 = this.getRequestHeader("Expect");
      String var5 = null;
      if(var4 != null) {
         var5 = var4.getValue();
      }

      if(var5 != null && var5.compareToIgnoreCase("100-continue") == 0) {
         HttpVersion var6 = HttpVersion.HTTP_1_1;
         if(var3.greaterEquals(var6)) {
            label95: {
               var2.flushRequestOutputStream();
               int var7 = var2.getParams().getSoTimeout();
               short var8 = 3000;

               try {
                  var2.setSocketTimeout(var8);
                  this.readStatusLine(var1, var2);
                  this.processStatusLine(var1, var2);
                  this.readResponseHeaders(var1, var2);
                  this.processResponseHeaders(var1, var2);
                  if(this.statusLine.getStatusCode() == 100) {
                     this.statusLine = null;
                     LOG.debug("OK to continue received");
                     break label95;
                  }
               } catch (InterruptedIOException var14) {
                  if(!ExceptionUtil.isSocketTimeoutException(var14)) {
                     throw var14;
                  }

                  this.removeRequestHeader("Expect");
                  LOG.info("100 (continue) read timeout. Resume sending the request");
                  break label95;
               } finally {
                  var2.setSocketTimeout(var7);
               }

               return;
            }
         } else {
            this.removeRequestHeader("Expect");
            LOG.info("\'Expect: 100-continue\' handshake is only supported by HTTP/1.1 or higher");
         }
      }

      this.writeRequestBody(var1, var2);
      var2.flushRequestOutputStream();
   }

   protected boolean writeRequestBody(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      return true;
   }

   protected void writeRequestHeaders(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.writeRequestHeaders(HttpState,HttpConnection)");
      this.addRequestHeaders(var1, var2);
      String var3 = this.getParams().getHttpElementCharset();
      Header[] var4 = this.getRequestHeaders();
      int var5 = 0;

      while(true) {
         int var6 = var4.length;
         if(var5 >= var6) {
            return;
         }

         String var7 = var4[var5].toExternalForm();
         if(Wire.HEADER_WIRE.enabled()) {
            Wire.HEADER_WIRE.output(var7);
         }

         var2.print(var7, var3);
         ++var5;
      }
   }

   protected void writeRequestLine(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter HttpMethodBase.writeRequestLine(HttpState, HttpConnection)");
      String var3 = this.getRequestLine(var2);
      if(Wire.HEADER_WIRE.enabled()) {
         Wire.HEADER_WIRE.output(var3);
      }

      String var4 = this.getParams().getHttpElementCharset();
      var2.print(var3, var4);
   }

   class 1 implements ResponseConsumedWatcher {

      1() {}

      public void responseConsumed() {
         HttpMethodBase.this.responseBodyConsumed();
      }
   }
}
