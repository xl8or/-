package org.apache.commons.httpclient.params;

import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpMethodParams extends DefaultHttpParams {

   public static final String BUFFER_WARN_TRIGGER_LIMIT = "http.method.response.buffer.warnlimit";
   public static final String COOKIE_POLICY = "http.protocol.cookie-policy";
   public static final String CREDENTIAL_CHARSET = "http.protocol.credential-charset";
   public static final String DATE_PATTERNS = "http.dateparser.patterns";
   public static final String HEAD_BODY_CHECK_TIMEOUT = "http.protocol.head-body-timeout";
   public static final String HTTP_CONTENT_CHARSET = "http.protocol.content-charset";
   public static final String HTTP_ELEMENT_CHARSET = "http.protocol.element-charset";
   public static final String HTTP_URI_CHARSET = "http.protocol.uri-charset";
   private static final Log LOG = LogFactory.getLog(HttpMethodParams.class);
   public static final String MULTIPART_BOUNDARY = "http.method.multipart.boundary";
   private static final String[] PROTOCOL_STRICTNESS_PARAMETERS;
   public static final String PROTOCOL_VERSION = "http.protocol.version";
   public static final String REJECT_HEAD_BODY = "http.protocol.reject-head-body";
   public static final String RETRY_HANDLER = "http.method.retry-handler";
   public static final String SINGLE_COOKIE_HEADER = "http.protocol.single-cookie-header";
   public static final String SO_TIMEOUT = "http.socket.timeout";
   public static final String STATUS_LINE_GARBAGE_LIMIT = "http.protocol.status-line-garbage-limit";
   public static final String STRICT_TRANSFER_ENCODING = "http.protocol.strict-transfer-encoding";
   public static final String UNAMBIGUOUS_STATUS_LINE = "http.protocol.unambiguous-statusline";
   public static final String USER_AGENT = "http.useragent";
   public static final String USE_EXPECT_CONTINUE = "http.protocol.expect-continue";
   public static final String VIRTUAL_HOST = "http.virtual-host";
   public static final String WARN_EXTRA_INPUT = "http.protocol.warn-extra-input";


   static {
      String[] var0 = new String[]{"http.protocol.unambiguous-statusline", "http.protocol.single-cookie-header", "http.protocol.strict-transfer-encoding", "http.protocol.reject-head-body", "http.protocol.warn-extra-input"};
      PROTOCOL_STRICTNESS_PARAMETERS = var0;
   }

   public HttpMethodParams() {
      HttpParams var1 = getDefaultParams();
      super(var1);
   }

   public HttpMethodParams(HttpParams var1) {
      super(var1);
   }

   public String getContentCharset() {
      String var1 = (String)this.getParameter("http.protocol.content-charset");
      if(var1 == null) {
         LOG.warn("Default content charset not configured, using ISO-8859-1");
         var1 = "ISO-8859-1";
      }

      return var1;
   }

   public String getCookiePolicy() {
      Object var1 = this.getParameter("http.protocol.cookie-policy");
      String var2;
      if(var1 == null) {
         var2 = "default";
      } else {
         var2 = (String)var1;
      }

      return var2;
   }

   public String getCredentialCharset() {
      String var1 = (String)this.getParameter("http.protocol.credential-charset");
      if(var1 == null) {
         LOG.debug("Credential charset not configured, using HTTP element charset");
         var1 = this.getHttpElementCharset();
      }

      return var1;
   }

   public String getHttpElementCharset() {
      String var1 = (String)this.getParameter("http.protocol.element-charset");
      if(var1 == null) {
         LOG.warn("HTTP element charset not configured, using US-ASCII");
         var1 = "US-ASCII";
      }

      return var1;
   }

   public int getSoTimeout() {
      return this.getIntParameter("http.socket.timeout", 0);
   }

   public String getUriCharset() {
      String var1 = (String)this.getParameter("http.protocol.uri-charset");
      if(var1 == null) {
         var1 = "UTF-8";
      }

      return var1;
   }

   public HttpVersion getVersion() {
      Object var1 = this.getParameter("http.protocol.version");
      HttpVersion var2;
      if(var1 == null) {
         var2 = HttpVersion.HTTP_1_1;
      } else {
         var2 = (HttpVersion)var1;
      }

      return var2;
   }

   public String getVirtualHost() {
      return (String)this.getParameter("http.virtual-host");
   }

   public void makeLenient() {
      String[] var1 = PROTOCOL_STRICTNESS_PARAMETERS;
      Boolean var2 = Boolean.FALSE;
      this.setParameters(var1, var2);
      this.setIntParameter("http.protocol.status-line-garbage-limit", Integer.MAX_VALUE);
   }

   public void makeStrict() {
      String[] var1 = PROTOCOL_STRICTNESS_PARAMETERS;
      Boolean var2 = Boolean.TRUE;
      this.setParameters(var1, var2);
      this.setIntParameter("http.protocol.status-line-garbage-limit", 0);
   }

   public void setContentCharset(String var1) {
      this.setParameter("http.protocol.content-charset", var1);
   }

   public void setCookiePolicy(String var1) {
      this.setParameter("http.protocol.cookie-policy", var1);
   }

   public void setCredentialCharset(String var1) {
      this.setParameter("http.protocol.credential-charset", var1);
   }

   public void setHttpElementCharset(String var1) {
      this.setParameter("http.protocol.element-charset", var1);
   }

   public void setSoTimeout(int var1) {
      this.setIntParameter("http.socket.timeout", var1);
   }

   public void setUriCharset(String var1) {
      this.setParameter("http.protocol.uri-charset", var1);
   }

   public void setVersion(HttpVersion var1) {
      this.setParameter("http.protocol.version", var1);
   }

   public void setVirtualHost(String var1) {
      this.setParameter("http.virtual-host", var1);
   }
}
