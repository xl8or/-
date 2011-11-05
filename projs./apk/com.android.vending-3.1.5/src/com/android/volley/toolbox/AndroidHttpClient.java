package com.android.volley.toolbox;

import android.content.ContentResolver;
import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import android.os.Looper;
import android.util.Log;
import com.android.volley.toolbox.elegant.ElegantPlainSocketFactory;
import com.android.volley.toolbox.elegant.ElegantRequestDirector;
import com.android.volley.toolbox.elegant.ElegantThreadSafeConnManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParamBean;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;

public final class AndroidHttpClient implements HttpClient {

   public static long DEFAULT_SYNC_MIN_GZIP_BYTES = 256L;
   private static final String TAG = "AndroidHttpClient";
   private static final HttpRequestInterceptor sThreadCheckInterceptor = new AndroidHttpClient.1();
   private volatile AndroidHttpClient.LoggingConfiguration curlConfiguration;
   private final HttpClient delegate;
   private RuntimeException mLeakedException;


   private AndroidHttpClient(ClientConnectionManager var1, HttpParams var2) {
      IllegalStateException var3 = new IllegalStateException("AndroidHttpClient created and never closed");
      this.mLeakedException = var3;
      AndroidHttpClient.2 var4 = new AndroidHttpClient.2(var1, var2);
      this.delegate = var4;
   }

   public static AbstractHttpEntity getCompressedEntity(byte[] var0, ContentResolver var1) throws IOException {
      long var2 = (long)var0.length;
      long var4 = getMinGzipSize(var1);
      ByteArrayEntity var6;
      if(var2 < var4) {
         var6 = new ByteArrayEntity(var0);
      } else {
         ByteArrayOutputStream var7 = new ByteArrayOutputStream();
         GZIPOutputStream var8 = new GZIPOutputStream(var7);
         var8.write(var0);
         var8.close();
         byte[] var9 = var7.toByteArray();
         var6 = new ByteArrayEntity(var9);
         var6.setContentEncoding("gzip");
      }

      return var6;
   }

   public static long getMinGzipSize(ContentResolver var0) {
      return DEFAULT_SYNC_MIN_GZIP_BYTES;
   }

   public static InputStream getUngzippedContent(HttpEntity var0) throws IOException {
      Object var1 = var0.getContent();
      Object var2;
      if(var1 == null) {
         var2 = var1;
      } else {
         Header var3 = var0.getContentEncoding();
         if(var3 == null) {
            var2 = var1;
         } else {
            String var4 = var3.getValue();
            if(var4 == null) {
               var2 = var1;
            } else {
               if(var4.contains("gzip")) {
                  var1 = new GZIPInputStream((InputStream)var1);
               }

               var2 = var1;
            }
         }
      }

      return (InputStream)var2;
   }

   public static void modifyRequestToAcceptGzipResponse(HttpRequest var0) {
      var0.addHeader("Accept-Encoding", "gzip");
   }

   public static AndroidHttpClient newInstance(String var0) {
      return newInstance(var0, (Context)null);
   }

   public static AndroidHttpClient newInstance(String var0, Context var1) {
      BasicHttpParams var2 = new BasicHttpParams();
      HttpConnectionParams.setStaleCheckingEnabled(var2, (boolean)0);
      HttpConnectionParams.setConnectionTimeout(var2, 20000);
      HttpConnectionParams.setSoTimeout(var2, 20000);
      HttpConnectionParams.setSocketBufferSize(var2, 8192);
      HttpClientParams.setRedirecting(var2, (boolean)0);
      SSLSessionCache var3;
      if(var1 == null) {
         var3 = null;
      } else {
         var3 = new SSLSessionCache(var1);
      }

      HttpProtocolParams.setUserAgent(var2, var0);
      SchemeRegistry var4 = new SchemeRegistry();
      ElegantPlainSocketFactory var5 = ElegantPlainSocketFactory.getSocketFactory();
      Scheme var6 = new Scheme("http", var5, 80);
      var4.register(var6);
      SSLSocketFactory var8 = SSLCertificateSocketFactory.getHttpSocketFactory(5000, var3);
      Scheme var9 = new Scheme("https", var8, 443);
      var4.register(var9);
      ConnManagerParamBean var11 = new ConnManagerParamBean(var2);
      ConnPerRouteBean var12 = new ConnPerRouteBean(4);
      var11.setConnectionsPerRoute(var12);
      var11.setMaxTotalConnections(8);
      ElegantThreadSafeConnManager var13 = new ElegantThreadSafeConnManager(var2, var4);
      return new AndroidHttpClient(var13, var2);
   }

   private static String toCurl(HttpUriRequest var0, boolean var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      StringBuilder var3 = var2.append("curl ");
      Header[] var4 = var0.getAllHeaders();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Header var7 = var4[var6];
         if(var1 || !var7.getName().equals("Authorization") && !var7.getName().equals("Cookie")) {
            StringBuilder var8 = var2.append("--header \"");
            String var9 = var7.toString().trim();
            var2.append(var9);
            StringBuilder var11 = var2.append("\" ");
         }
      }

      URI var12 = var0.getURI();
      if(var0 instanceof RequestWrapper) {
         HttpRequest var13 = ((RequestWrapper)var0).getOriginal();
         if(var13 instanceof HttpUriRequest) {
            var12 = ((HttpUriRequest)var13).getURI();
         }
      }

      StringBuilder var14 = var2.append("\"");
      var2.append(var12);
      StringBuilder var16 = var2.append("\"");
      if(var0 instanceof HttpEntityEnclosingRequest) {
         HttpEntity var17 = ((HttpEntityEnclosingRequest)var0).getEntity();
         if(var17 != null && var17.isRepeatable()) {
            if(var17.getContentLength() < 1024L) {
               ByteArrayOutputStream var18 = new ByteArrayOutputStream();
               var17.writeTo(var18);
               String var19 = var18.toString();
               StringBuilder var20 = var2.append(" --data-ascii \"").append(var19).append("\"");
            } else {
               StringBuilder var21 = var2.append(" [TOO MUCH DATA TO INCLUDE]");
            }
         }
      }

      return var2.toString();
   }

   public void close() {
      if(this.mLeakedException != null) {
         this.getConnectionManager().shutdown();
         this.mLeakedException = null;
      }
   }

   public void disableCurlLogging() {
      this.curlConfiguration = null;
   }

   public void enableCurlLogging(String var1, int var2) {
      if(var1 == null) {
         throw new NullPointerException("name");
      } else if(var2 >= 2 && var2 <= 7) {
         AndroidHttpClient.LoggingConfiguration var3 = new AndroidHttpClient.LoggingConfiguration(var1, var2, (AndroidHttpClient.1)null);
         this.curlConfiguration = var3;
      } else {
         throw new IllegalArgumentException("Level is out of range [2..7]");
      }
   }

   public <T extends Object> T execute(HttpHost var1, HttpRequest var2, ResponseHandler<? extends T> var3) throws IOException, ClientProtocolException {
      return this.delegate.execute(var1, var2, var3);
   }

   public <T extends Object> T execute(HttpHost var1, HttpRequest var2, ResponseHandler<? extends T> var3, HttpContext var4) throws IOException, ClientProtocolException {
      return this.delegate.execute(var1, var2, var3, var4);
   }

   public <T extends Object> T execute(HttpUriRequest var1, ResponseHandler<? extends T> var2) throws IOException, ClientProtocolException {
      return this.delegate.execute(var1, var2);
   }

   public <T extends Object> T execute(HttpUriRequest var1, ResponseHandler<? extends T> var2, HttpContext var3) throws IOException, ClientProtocolException {
      return this.delegate.execute(var1, var2, var3);
   }

   public HttpResponse execute(HttpHost var1, HttpRequest var2) throws IOException {
      return this.delegate.execute(var1, var2);
   }

   public HttpResponse execute(HttpHost var1, HttpRequest var2, HttpContext var3) throws IOException {
      return this.delegate.execute(var1, var2, var3);
   }

   public HttpResponse execute(HttpUriRequest var1) throws IOException {
      return this.delegate.execute(var1);
   }

   public HttpResponse execute(HttpUriRequest var1, HttpContext var2) throws IOException {
      return this.delegate.execute(var1, var2);
   }

   protected void finalize() throws Throwable {
      super.finalize();
      if(this.mLeakedException != null) {
         RuntimeException var1 = this.mLeakedException;
         int var2 = Log.e("AndroidHttpClient", "Leak found", var1);
         this.mLeakedException = null;
      }
   }

   public ClientConnectionManager getConnectionManager() {
      return this.delegate.getConnectionManager();
   }

   public HttpParams getParams() {
      return this.delegate.getParams();
   }

   private class CurlLogger implements HttpRequestInterceptor {

      private CurlLogger() {}

      // $FF: synthetic method
      CurlLogger(AndroidHttpClient.1 var2) {
         this();
      }

      public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException {
         AndroidHttpClient.LoggingConfiguration var3 = AndroidHttpClient.this.curlConfiguration;
         if(var3 != null) {
            if(var3.isLoggable()) {
               if(var1 instanceof HttpUriRequest) {
                  String var4 = AndroidHttpClient.toCurl((HttpUriRequest)var1, (boolean)1);
                  var3.println(var4);
               }
            }
         }
      }
   }

   static class 1 implements HttpRequestInterceptor {

      1() {}

      public void process(HttpRequest var1, HttpContext var2) {
         if(Looper.myLooper() != null) {
            Looper var3 = Looper.myLooper();
            Looper var4 = Looper.getMainLooper();
            if(var3 == var4) {
               throw new RuntimeException("This thread forbids HTTP requests");
            }
         }
      }
   }

   class 2 extends DefaultHttpClient {

      2(ClientConnectionManager var2, HttpParams var3) {
         super(var2, var3);
      }

      protected RequestDirector createClientRequestDirector(HttpRequestExecutor var1, ClientConnectionManager var2, ConnectionReuseStrategy var3, ConnectionKeepAliveStrategy var4, HttpRoutePlanner var5, HttpProcessor var6, HttpRequestRetryHandler var7, RedirectHandler var8, AuthenticationHandler var9, AuthenticationHandler var10, UserTokenHandler var11, HttpParams var12) {
         return new ElegantRequestDirector(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
      }

      protected HttpContext createHttpContext() {
         BasicHttpContext var1 = new BasicHttpContext();
         AuthSchemeRegistry var2 = this.getAuthSchemes();
         var1.setAttribute("http.authscheme-registry", var2);
         CookieSpecRegistry var3 = this.getCookieSpecs();
         var1.setAttribute("http.cookiespec-registry", var3);
         CredentialsProvider var4 = this.getCredentialsProvider();
         var1.setAttribute("http.auth.credentials-provider", var4);
         return var1;
      }

      protected BasicHttpProcessor createHttpProcessor() {
         BasicHttpProcessor var1 = super.createHttpProcessor();
         HttpRequestInterceptor var2 = AndroidHttpClient.sThreadCheckInterceptor;
         var1.addRequestInterceptor(var2);
         AndroidHttpClient var3 = AndroidHttpClient.this;
         AndroidHttpClient.CurlLogger var4 = var3.new CurlLogger((AndroidHttpClient.1)null);
         var1.addRequestInterceptor(var4);
         return var1;
      }
   }

   private static class LoggingConfiguration {

      private final int level;
      private final String tag;


      private LoggingConfiguration(String var1, int var2) {
         this.tag = var1;
         this.level = var2;
      }

      // $FF: synthetic method
      LoggingConfiguration(String var1, int var2, AndroidHttpClient.1 var3) {
         this(var1, var2);
      }

      private boolean isLoggable() {
         String var1 = this.tag;
         int var2 = this.level;
         return Log.isLoggable(var1, var2);
      }

      private void println(String var1) {
         int var2 = this.level;
         String var3 = this.tag;
         Log.println(var2, var3, var1);
      }
   }
}
