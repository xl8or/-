package com.google.android.common.http;

import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;

public class TestHttpClient {

   private final ConnectionReuseStrategy connStrategy;
   private final HttpContext context;
   private final HttpRequestExecutor httpexecutor;
   private final BasicHttpProcessor httpproc;
   private final HttpParams params;


   public TestHttpClient() {
      BasicHttpParams var1 = new BasicHttpParams();
      this.params = var1;
      HttpParams var2 = this.params.setIntParameter("http.socket.timeout", 5000).setBooleanParameter("http.connection.stalecheck", (boolean)0);
      HttpVersion var3 = HttpVersion.HTTP_1_1;
      HttpParams var4 = var2.setParameter("http.protocol.version", var3).setParameter("http.useragent", "TEST-CLIENT/1.1");
      BasicHttpProcessor var5 = new BasicHttpProcessor();
      this.httpproc = var5;
      BasicHttpProcessor var6 = this.httpproc;
      RequestContent var7 = new RequestContent();
      var6.addInterceptor(var7);
      BasicHttpProcessor var8 = this.httpproc;
      RequestTargetHost var9 = new RequestTargetHost();
      var8.addInterceptor(var9);
      BasicHttpProcessor var10 = this.httpproc;
      RequestConnControl var11 = new RequestConnControl();
      var10.addInterceptor(var11);
      BasicHttpProcessor var12 = this.httpproc;
      RequestUserAgent var13 = new RequestUserAgent();
      var12.addInterceptor(var13);
      BasicHttpProcessor var14 = this.httpproc;
      RequestExpectContinue var15 = new RequestExpectContinue();
      var14.addInterceptor(var15);
      HttpRequestExecutor var16 = new HttpRequestExecutor();
      this.httpexecutor = var16;
      DefaultConnectionReuseStrategy var17 = new DefaultConnectionReuseStrategy();
      this.connStrategy = var17;
      BasicHttpContext var18 = new BasicHttpContext((HttpContext)null);
      this.context = var18;
   }

   public HttpResponse execute(HttpRequest var1, HttpHost var2, HttpClientConnection var3) throws HttpException, IOException {
      this.context.setAttribute("http.request", var1);
      this.context.setAttribute("http.target_host", var2);
      this.context.setAttribute("http.connection", var3);
      HttpParams var4 = var1.getParams();
      HttpParams var5 = this.params;
      DefaultedHttpParams var6 = new DefaultedHttpParams(var4, var5);
      var1.setParams(var6);
      HttpRequestExecutor var7 = this.httpexecutor;
      BasicHttpProcessor var8 = this.httpproc;
      HttpContext var9 = this.context;
      var7.preProcess(var1, var8, var9);
      HttpRequestExecutor var10 = this.httpexecutor;
      HttpContext var11 = this.context;
      HttpResponse var12 = var10.execute(var1, var3, var11);
      HttpParams var13 = var12.getParams();
      HttpParams var14 = this.params;
      DefaultedHttpParams var15 = new DefaultedHttpParams(var13, var14);
      var12.setParams(var15);
      HttpRequestExecutor var16 = this.httpexecutor;
      BasicHttpProcessor var17 = this.httpproc;
      HttpContext var18 = this.context;
      var16.postProcess(var12, var17, var18);
      return var12;
   }

   public HttpParams getParams() {
      return this.params;
   }

   public boolean keepAlive(HttpResponse var1) {
      ConnectionReuseStrategy var2 = this.connStrategy;
      HttpContext var3 = this.context;
      return var2.keepAlive(var1, var3);
   }
}
