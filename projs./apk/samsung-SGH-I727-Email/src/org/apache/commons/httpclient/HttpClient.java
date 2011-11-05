package org.apache.commons.httpclient;

import java.io.IOException;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodDirector;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.logging.Log;

public class HttpClient {

   private static final Log LOG;
   private HostConfiguration hostConfiguration;
   private HttpConnectionManager httpConnectionManager;
   private HttpClientParams params;
   private HttpState state;


   static {
      // $FF: Couldn't be decompiled
   }

   public HttpClient() {
      HttpClientParams var1 = new HttpClientParams();
      this(var1);
   }

   public HttpClient(HttpConnectionManager var1) {
      HttpClientParams var2 = new HttpClientParams();
      this(var2, var1);
   }

   public HttpClient(HttpClientParams var1) {
      HttpState var2 = new HttpState();
      this.state = var2;
      this.params = null;
      HostConfiguration var3 = new HostConfiguration();
      this.hostConfiguration = var3;
      if(var1 == null) {
         throw new IllegalArgumentException("Params may not be null");
      } else {
         this.params = var1;
         this.httpConnectionManager = null;
         Class var4 = var1.getConnectionManagerClass();
         if(var4 != null) {
            try {
               HttpConnectionManager var5 = (HttpConnectionManager)var4.newInstance();
               this.httpConnectionManager = var5;
            } catch (Exception var10) {
               LOG.warn("Error instantiating connection manager class, defaulting to SimpleHttpConnectionManager", var10);
            }
         }

         if(this.httpConnectionManager == null) {
            SimpleHttpConnectionManager var6 = new SimpleHttpConnectionManager();
            this.httpConnectionManager = var6;
         }

         if(this.httpConnectionManager != null) {
            HttpConnectionManagerParams var7 = this.httpConnectionManager.getParams();
            HttpClientParams var8 = this.params;
            var7.setDefaults(var8);
         }
      }
   }

   public HttpClient(HttpClientParams var1, HttpConnectionManager var2) {
      HttpState var3 = new HttpState();
      this.state = var3;
      this.params = null;
      HostConfiguration var4 = new HostConfiguration();
      this.hostConfiguration = var4;
      if(var2 == null) {
         throw new IllegalArgumentException("httpConnectionManager cannot be null");
      } else if(var1 == null) {
         throw new IllegalArgumentException("Params may not be null");
      } else {
         this.params = var1;
         this.httpConnectionManager = var2;
         HttpConnectionManagerParams var5 = this.httpConnectionManager.getParams();
         HttpClientParams var6 = this.params;
         var5.setDefaults(var6);
      }
   }

   public int executeMethod(HostConfiguration var1, HttpMethod var2) throws IOException, HttpException {
      LOG.trace("enter HttpClient.executeMethod(HostConfiguration,HttpMethod)");
      return this.executeMethod(var1, var2, (HttpState)null);
   }

   public int executeMethod(HostConfiguration var1, HttpMethod var2, HttpState var3) throws IOException, HttpException {
      LOG.trace("enter HttpClient.executeMethod(HostConfiguration,HttpMethod,HttpState)");
      if(var2 == null) {
         throw new IllegalArgumentException("HttpMethod parameter may not be null");
      } else {
         HostConfiguration var4 = this.getHostConfiguration();
         if(var1 == null) {
            var1 = var4;
         }

         URI var5 = var2.getURI();
         if(var1 == var4 || var5.isAbsoluteURI()) {
            var1 = (HostConfiguration)var1.clone();
            if(var5.isAbsoluteURI()) {
               var1.setHost(var5);
            }
         }

         HttpMethodDirector var6 = new HttpMethodDirector;
         HttpConnectionManager var7 = this.getHttpConnectionManager();
         HttpClientParams var8 = this.params;
         HttpState var9;
         if(var3 == null) {
            var9 = this.getState();
         } else {
            var9 = var3;
         }

         var6.<init>(var7, var1, var8, var9);
         var6.executeMethod(var2);
         return var2.getStatusCode();
      }
   }

   public int executeMethod(HttpMethod var1) throws IOException, HttpException {
      LOG.trace("enter HttpClient.executeMethod(HttpMethod)");
      return this.executeMethod((HostConfiguration)null, var1, (HttpState)null);
   }

   public String getHost() {
      return this.hostConfiguration.getHost();
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

   public HttpConnectionManager getHttpConnectionManager() {
      synchronized(this){}

      HttpConnectionManager var1;
      try {
         var1 = this.httpConnectionManager;
      } finally {
         ;
      }

      return var1;
   }

   public HttpClientParams getParams() {
      return this.params;
   }

   public int getPort() {
      return this.hostConfiguration.getPort();
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

   public boolean isStrictMode() {
      synchronized(this){}
      return false;
   }

   public void setConnectionTimeout(int var1) {
      synchronized(this){}

      try {
         this.httpConnectionManager.getParams().setConnectionTimeout(var1);
      } finally {
         ;
      }

   }

   public void setHostConfiguration(HostConfiguration var1) {
      synchronized(this){}

      try {
         this.hostConfiguration = var1;
      } finally {
         ;
      }

   }

   public void setHttpConnectionFactoryTimeout(long var1) {
      synchronized(this){}

      try {
         this.params.setConnectionManagerTimeout(var1);
      } finally {
         ;
      }

   }

   public void setHttpConnectionManager(HttpConnectionManager var1) {
      synchronized(this){}

      try {
         this.httpConnectionManager = var1;
         if(this.httpConnectionManager != null) {
            HttpConnectionManagerParams var2 = this.httpConnectionManager.getParams();
            HttpClientParams var3 = this.params;
            var2.setDefaults(var3);
         }
      } finally {
         ;
      }

   }

   public void setParams(HttpClientParams var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameters may not be null");
      } else {
         this.params = var1;
      }
   }

   public void setState(HttpState var1) {
      synchronized(this){}

      try {
         this.state = var1;
      } finally {
         ;
      }

   }

   public void setStrictMode(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public void setTimeout(int var1) {
      synchronized(this){}

      try {
         this.params.setSoTimeout(var1);
      } finally {
         ;
      }

   }
}
