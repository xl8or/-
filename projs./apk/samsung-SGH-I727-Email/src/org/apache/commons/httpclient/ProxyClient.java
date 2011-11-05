package org.apache.commons.httpclient;

import java.io.IOException;
import java.net.Socket;
import org.apache.commons.httpclient.ConnectMethod;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodDirector;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParams;

public class ProxyClient {

   private HostConfiguration hostConfiguration;
   private HttpClientParams params;
   private HttpState state;


   public ProxyClient() {
      HttpClientParams var1 = new HttpClientParams();
      this(var1);
   }

   public ProxyClient(HttpClientParams var1) {
      HttpState var2 = new HttpState();
      this.state = var2;
      this.params = null;
      HostConfiguration var3 = new HostConfiguration();
      this.hostConfiguration = var3;
      if(var1 == null) {
         throw new IllegalArgumentException("Params may not be null");
      } else {
         this.params = var1;
      }
   }

   public ProxyClient.ConnectResponse connect() throws IOException, HttpException {
      HostConfiguration var1 = this.getHostConfiguration();
      if(var1.getProxyHost() == null) {
         throw new IllegalStateException("proxy host must be configured");
      } else if(var1.getHost() == null) {
         throw new IllegalStateException("destination host must be configured");
      } else if(var1.getProtocol().isSecure()) {
         throw new IllegalStateException("secure protocol socket factory may not be used");
      } else {
         HostConfiguration var2 = this.getHostConfiguration();
         ConnectMethod var3 = new ConnectMethod(var2);
         HttpMethodParams var4 = var3.getParams();
         HttpClientParams var5 = this.getParams();
         var4.setDefaults(var5);
         ProxyClient.DummyConnectionManager var6 = new ProxyClient.DummyConnectionManager();
         HttpClientParams var7 = this.getParams();
         var6.setConnectionParams(var7);
         HttpClientParams var8 = this.getParams();
         HttpState var9 = this.getState();
         (new HttpMethodDirector(var6, var1, var8, var9)).executeMethod(var3);
         ProxyClient.ConnectResponse var10 = new ProxyClient.ConnectResponse((ProxyClient.1)null);
         var10.setConnectMethod(var3);
         if(var3.getStatusCode() == 200) {
            Socket var11 = var6.getConnection().getSocket();
            var10.setSocket(var11);
         } else {
            var6.getConnection().close();
         }

         return var10;
      }
   }

   public HostConfiguration getHostConfiguration() {
      synchronized(this){}

      HostConfiguration var1;
      try {
         var1 = this.hostConfiguration;
      } finally {
         ;
      }

      return var1;
   }

   public HttpClientParams getParams() {
      synchronized(this){}

      HttpClientParams var1;
      try {
         var1 = this.params;
      } finally {
         ;
      }

      return var1;
   }

   public HttpState getState() {
      synchronized(this){}

      HttpState var1;
      try {
         var1 = this.state;
      } finally {
         ;
      }

      return var1;
   }

   public void setHostConfiguration(HostConfiguration var1) {
      synchronized(this){}

      try {
         this.hostConfiguration = var1;
      } finally {
         ;
      }

   }

   public void setParams(HttpClientParams param1) {
      // $FF: Couldn't be decompiled
   }

   public void setState(HttpState var1) {
      synchronized(this){}

      try {
         this.state = var1;
      } finally {
         ;
      }

   }

   static class DummyConnectionManager implements HttpConnectionManager {

      private HttpParams connectionParams;
      private HttpConnection httpConnection;


      DummyConnectionManager() {}

      public void closeIdleConnections(long var1) {}

      public HttpConnection getConnection() {
         return this.httpConnection;
      }

      public HttpConnection getConnection(HostConfiguration var1) {
         return this.getConnectionWithTimeout(var1, 65535L);
      }

      public HttpConnection getConnection(HostConfiguration var1, long var2) throws HttpException {
         return this.getConnectionWithTimeout(var1, var2);
      }

      public HttpConnection getConnectionWithTimeout(HostConfiguration var1, long var2) {
         HttpConnection var4 = new HttpConnection(var1);
         this.httpConnection = var4;
         this.httpConnection.setHttpConnectionManager(this);
         HttpConnectionParams var5 = this.httpConnection.getParams();
         HttpParams var6 = this.connectionParams;
         var5.setDefaults(var6);
         return this.httpConnection;
      }

      public HttpConnectionManagerParams getParams() {
         return null;
      }

      public void releaseConnection(HttpConnection var1) {}

      public void setConnectionParams(HttpParams var1) {
         this.connectionParams = var1;
      }

      public void setParams(HttpConnectionManagerParams var1) {}
   }

   public static class ConnectResponse {

      private ConnectMethod connectMethod;
      private Socket socket;


      private ConnectResponse() {}

      // $FF: synthetic method
      ConnectResponse(ProxyClient.1 var1) {
         this();
      }

      private void setConnectMethod(ConnectMethod var1) {
         this.connectMethod = var1;
      }

      private void setSocket(Socket var1) {
         this.socket = var1;
      }

      public ConnectMethod getConnectMethod() {
         return this.connectMethod;
      }

      public Socket getSocket() {
         return this.socket;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
