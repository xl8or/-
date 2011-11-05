package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleHttpConnectionManager implements HttpConnectionManager {

   private static final Log LOG = LogFactory.getLog(SimpleHttpConnectionManager.class);
   private static final String MISUSE_MESSAGE = "SimpleHttpConnectionManager being used incorrectly.  Be sure that HttpMethod.releaseConnection() is always called and that only one thread and/or method is using this connection manager at a time.";
   private boolean alwaysClose;
   protected HttpConnection httpConnection;
   private long idleStartTime;
   private volatile boolean inUse;
   private HttpConnectionManagerParams params;


   public SimpleHttpConnectionManager() {
      HttpConnectionManagerParams var1 = new HttpConnectionManagerParams();
      this.params = var1;
      this.idleStartTime = Long.MAX_VALUE;
      this.inUse = (boolean)0;
      this.alwaysClose = (boolean)0;
   }

   public SimpleHttpConnectionManager(boolean var1) {
      HttpConnectionManagerParams var2 = new HttpConnectionManagerParams();
      this.params = var2;
      this.idleStartTime = Long.MAX_VALUE;
      this.inUse = (boolean)0;
      this.alwaysClose = (boolean)0;
      this.alwaysClose = var1;
   }

   static void finishLastResponse(HttpConnection var0) {
      InputStream var1 = var0.getLastResponseInputStream();
      if(var1 != null) {
         var0.setLastResponseInputStream((InputStream)null);

         try {
            var1.close();
         } catch (IOException var3) {
            var0.close();
         }
      }
   }

   public void closeIdleConnections(long var1) {
      long var3 = System.currentTimeMillis() - var1;
      if(this.idleStartTime <= var3) {
         this.httpConnection.close();
      }
   }

   public HttpConnection getConnection(HostConfiguration var1) {
      return this.getConnection(var1, 0L);
   }

   public HttpConnection getConnection(HostConfiguration var1, long var2) {
      return this.getConnectionWithTimeout(var1, var2);
   }

   public HttpConnection getConnectionWithTimeout(HostConfiguration var1, long var2) {
      if(this.httpConnection == null) {
         HttpConnection var4 = new HttpConnection(var1);
         this.httpConnection = var4;
         this.httpConnection.setHttpConnectionManager(this);
         HttpConnectionParams var5 = this.httpConnection.getParams();
         HttpConnectionManagerParams var6 = this.params;
         var5.setDefaults(var6);
      } else {
         label26: {
            HttpConnection var7 = this.httpConnection;
            if(var1.hostEquals(var7)) {
               HttpConnection var8 = this.httpConnection;
               if(var1.proxyEquals(var8)) {
                  finishLastResponse(this.httpConnection);
                  break label26;
               }
            }

            if(this.httpConnection.isOpen()) {
               this.httpConnection.close();
            }

            HttpConnection var9 = this.httpConnection;
            String var10 = var1.getHost();
            var9.setHost(var10);
            HttpConnection var11 = this.httpConnection;
            int var12 = var1.getPort();
            var11.setPort(var12);
            HttpConnection var13 = this.httpConnection;
            Protocol var14 = var1.getProtocol();
            var13.setProtocol(var14);
            HttpConnection var15 = this.httpConnection;
            InetAddress var16 = var1.getLocalAddress();
            var15.setLocalAddress(var16);
            HttpConnection var17 = this.httpConnection;
            String var18 = var1.getProxyHost();
            var17.setProxyHost(var18);
            HttpConnection var19 = this.httpConnection;
            int var20 = var1.getProxyPort();
            var19.setProxyPort(var20);
         }
      }

      this.idleStartTime = Long.MAX_VALUE;
      if(this.inUse) {
         LOG.warn("SimpleHttpConnectionManager being used incorrectly.  Be sure that HttpMethod.releaseConnection() is always called and that only one thread and/or method is using this connection manager at a time.");
      }

      this.inUse = (boolean)1;
      return this.httpConnection;
   }

   public HttpConnectionManagerParams getParams() {
      return this.params;
   }

   public boolean isConnectionStaleCheckingEnabled() {
      return this.params.isStaleCheckingEnabled();
   }

   public void releaseConnection(HttpConnection var1) {
      HttpConnection var2 = this.httpConnection;
      if(var1 != var2) {
         throw new IllegalStateException("Unexpected release of an unknown connection.");
      } else {
         if(this.alwaysClose) {
            this.httpConnection.close();
         } else {
            finishLastResponse(this.httpConnection);
         }

         this.inUse = (boolean)0;
         long var3 = System.currentTimeMillis();
         this.idleStartTime = var3;
      }
   }

   public void setConnectionStaleCheckingEnabled(boolean var1) {
      this.params.setStaleCheckingEnabled(var1);
   }

   public void setParams(HttpConnectionManagerParams var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameters may not be null");
      } else {
         this.params = var1;
      }
   }

   public void shutdown() {
      this.httpConnection.close();
   }
}
