package com.kenai.jbosh;

import com.kenai.jbosh.AbstractBody;
import com.kenai.jbosh.ApacheHTTPResponse;
import com.kenai.jbosh.BOSHClientConfig;
import com.kenai.jbosh.CMSessionParams;
import com.kenai.jbosh.HTTPResponse;
import com.kenai.jbosh.HTTPSender;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;

final class ApacheHTTPSender implements HTTPSender {

   private BOSHClientConfig cfg;
   private HttpClient httpClient;
   private final Lock lock;


   ApacheHTTPSender() {
      ReentrantLock var1 = new ReentrantLock();
      this.lock = var1;
      String var2 = HttpClient.class.getName();
   }

   private HttpClient initHttpClient(BOSHClientConfig var1) {
      synchronized(this){}

      DefaultHttpClient var17;
      try {
         BasicHttpParams var2 = new BasicHttpParams();
         ConnManagerParams.setMaxTotalConnections(var2, 100);
         HttpVersion var3 = HttpVersion.HTTP_1_1;
         HttpProtocolParams.setVersion(var2, var3);
         HttpProtocolParams.setUseExpectContinue(var2, (boolean)0);
         if(var1 != null && var1.getProxyHost() != null && var1.getProxyPort() != 0) {
            String var4 = var1.getProxyHost();
            int var5 = var1.getProxyPort();
            HttpHost var6 = new HttpHost(var4, var5);
            var2.setParameter("http.route.default-proxy", var6);
         }

         SchemeRegistry var8 = new SchemeRegistry();
         PlainSocketFactory var9 = PlainSocketFactory.getSocketFactory();
         Scheme var10 = new Scheme("http", var9, 80);
         var8.register(var10);
         SSLSocketFactory var12 = SSLSocketFactory.getSocketFactory();
         X509HostnameVerifier var13 = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
         var12.setHostnameVerifier(var13);
         Scheme var14 = new Scheme("https", var12, 443);
         var8.register(var14);
         ThreadSafeClientConnManager var16 = new ThreadSafeClientConnManager(var2, var8);
         var17 = new DefaultHttpClient(var16, var2);
      } finally {
         ;
      }

      return var17;
   }

   public void destroy() {
      this.lock.lock();

      try {
         if(this.httpClient != null) {
            this.httpClient.getConnectionManager().shutdown();
         }
      } finally {
         this.cfg = null;
         this.httpClient = null;
         this.lock.unlock();
      }

   }

   public void init(BOSHClientConfig var1) {
      this.lock.lock();

      try {
         this.cfg = var1;
         HttpClient var2 = this.initHttpClient(var1);
         this.httpClient = var2;
      } finally {
         this.lock.unlock();
      }

   }

   public HTTPResponse send(CMSessionParams var1, AbstractBody var2) {
      this.lock.lock();

      HttpClient var5;
      BOSHClientConfig var6;
      try {
         if(this.httpClient == null) {
            BOSHClientConfig var3 = this.cfg;
            HttpClient var4 = this.initHttpClient(var3);
            this.httpClient = var4;
         }

         var5 = this.httpClient;
         var6 = this.cfg;
      } finally {
         this.lock.unlock();
      }

      return new ApacheHTTPResponse(var5, var6, var1, var2);
   }
}
