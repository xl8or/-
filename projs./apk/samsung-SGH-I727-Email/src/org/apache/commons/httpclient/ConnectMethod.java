package org.apache.commons.httpclient;

import java.io.IOException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.Wire;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnectMethod extends HttpMethodBase {

   private static final Log LOG = LogFactory.getLog(ConnectMethod.class);
   public static final String NAME = "CONNECT";
   private final HostConfiguration targethost;


   public ConnectMethod() {
      this.targethost = null;
   }

   public ConnectMethod(HostConfiguration var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Target host may not be null");
      } else {
         this.targethost = var1;
      }
   }

   public ConnectMethod(HttpMethod var1) {
      this.targethost = null;
   }

   protected void addCookieRequestHeader(HttpState var1, HttpConnection var2) throws IOException, HttpException {}

   protected void addRequestHeaders(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter ConnectMethod.addRequestHeaders(HttpState, HttpConnection)");
      this.addUserAgentRequestHeader(var1, var2);
      this.addHostRequestHeader(var1, var2);
      this.addProxyConnectionHeader(var1, var2);
   }

   public int execute(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter ConnectMethod.execute(HttpState, HttpConnection)");
      int var3 = super.execute(var1, var2);
      if(LOG.isDebugEnabled()) {
         Log var4 = LOG;
         String var5 = "CONNECT status code " + var3;
         var4.debug(var5);
      }

      return var3;
   }

   public String getName() {
      return "CONNECT";
   }

   public String getPath() {
      String var7;
      if(this.targethost != null) {
         StringBuffer var1 = new StringBuffer();
         String var2 = this.targethost.getHost();
         var1.append(var2);
         int var4 = this.targethost.getPort();
         if(var4 == -1) {
            var4 = this.targethost.getProtocol().getDefaultPort();
         }

         StringBuffer var5 = var1.append(':');
         var1.append(var4);
         var7 = var1.toString();
      } else {
         var7 = "/";
      }

      return var7;
   }

   public URI getURI() throws URIException {
      String var1 = this.getParams().getUriCharset();
      String var2 = this.getPath();
      return new URI(var2, (boolean)1, var1);
   }

   protected boolean shouldCloseConnection(HttpConnection var1) {
      byte var9;
      if(this.getStatusCode() == 200) {
         Header var2 = null;
         if(!var1.isTransparent()) {
            var2 = this.getResponseHeader("proxy-connection");
         }

         if(var2 == null) {
            var2 = this.getResponseHeader("connection");
         }

         if(var2 != null && var2.getValue().equalsIgnoreCase("close") && LOG.isWarnEnabled()) {
            Log var3 = LOG;
            StringBuilder var4 = (new StringBuilder()).append("Invalid header encountered \'");
            String var5 = var2.toExternalForm();
            StringBuilder var6 = var4.append(var5).append("\' in response ");
            String var7 = this.getStatusLine().toString();
            String var8 = var6.append(var7).toString();
            var3.warn(var8);
         }

         var9 = 0;
      } else {
         var9 = super.shouldCloseConnection(var1);
      }

      return (boolean)var9;
   }

   protected void writeRequestLine(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      StringBuffer var3 = new StringBuffer();
      String var4 = this.getName();
      var3.append(var4);
      StringBuffer var6 = var3.append(' ');
      if(this.targethost != null) {
         String var7 = this.getPath();
         var3.append(var7);
      } else {
         int var14 = var2.getPort();
         if(var14 == -1) {
            var14 = var2.getProtocol().getDefaultPort();
         }

         String var15 = var2.getHost();
         var3.append(var15);
         StringBuffer var17 = var3.append(':');
         var3.append(var14);
      }

      StringBuffer var9 = var3.append(" ");
      HttpVersion var10 = this.getEffectiveVersion();
      var3.append(var10);
      String var12 = var3.toString();
      String var13 = this.getParams().getHttpElementCharset();
      var2.printLine(var12, var13);
      if(Wire.HEADER_WIRE.enabled()) {
         Wire.HEADER_WIRE.output(var12);
      }
   }
}
