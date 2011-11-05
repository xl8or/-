package com.google.android.common.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpServerConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpExpectationVerifier;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpRequestHandlerRegistry;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;

public class TestHttpServer {

   private final ConnectionReuseStrategy connStrategy;
   private HttpExpectationVerifier expectationVerifier;
   private final BasicHttpProcessor httpproc;
   private Thread listener;
   private final HttpParams params;
   private final HttpRequestHandlerRegistry reqistry;
   private final HttpResponseFactory responseFactory;
   private final ServerSocket serversocket;
   private volatile boolean shutdown;


   public TestHttpServer() throws IOException {
      this(0);
   }

   public TestHttpServer(int var1) throws IOException {
      BasicHttpParams var2 = new BasicHttpParams();
      this.params = var2;
      HttpParams var3 = this.params.setIntParameter("http.socket.timeout", 20000).setIntParameter("http.socket.buffer-size", 8192).setBooleanParameter("http.connection.stalecheck", (boolean)0).setBooleanParameter("http.tcp.nodelay", (boolean)1).setParameter("http.origin-server", "TEST-SERVER/1.1");
      BasicHttpProcessor var4 = new BasicHttpProcessor();
      this.httpproc = var4;
      BasicHttpProcessor var5 = this.httpproc;
      ResponseDate var6 = new ResponseDate();
      var5.addInterceptor(var6);
      BasicHttpProcessor var7 = this.httpproc;
      ResponseServer var8 = new ResponseServer();
      var7.addInterceptor(var8);
      BasicHttpProcessor var9 = this.httpproc;
      ResponseContent var10 = new ResponseContent();
      var9.addInterceptor(var10);
      BasicHttpProcessor var11 = this.httpproc;
      ResponseConnControl var12 = new ResponseConnControl();
      var11.addInterceptor(var12);
      DefaultConnectionReuseStrategy var13 = new DefaultConnectionReuseStrategy();
      this.connStrategy = var13;
      DefaultHttpResponseFactory var14 = new DefaultHttpResponseFactory();
      this.responseFactory = var14;
      HttpRequestHandlerRegistry var15 = new HttpRequestHandlerRegistry();
      this.reqistry = var15;
      ServerSocket var16 = new ServerSocket(var1);
      this.serversocket = var16;
   }

   private HttpServerConnection acceptConnection() throws IOException {
      Socket var1 = this.serversocket.accept();
      DefaultHttpServerConnection var2 = new DefaultHttpServerConnection();
      HttpParams var3 = this.params;
      var2.bind(var1, var3);
      return var2;
   }

   public InetAddress getInetAddress() {
      return this.serversocket.getInetAddress();
   }

   public int getPort() {
      return this.serversocket.getLocalPort();
   }

   public void registerHandler(String var1, HttpRequestHandler var2) {
      this.reqistry.register(var1, var2);
   }

   public void setExpectationVerifier(HttpExpectationVerifier var1) {
      this.expectationVerifier = var1;
   }

   public void shutdown() {
      if(!this.shutdown) {
         this.shutdown = (boolean)1;

         try {
            this.serversocket.close();
         } catch (IOException var4) {
            ;
         }

         this.listener.interrupt();

         try {
            this.listener.join(1000L);
         } catch (InterruptedException var3) {
            ;
         }
      }
   }

   public void start() {
      if(this.listener != null) {
         throw new IllegalStateException("Listener already running");
      } else {
         TestHttpServer.1 var1 = new TestHttpServer.1();
         Thread var2 = new Thread(var1);
         this.listener = var2;
         this.listener.start();
      }
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         while(!TestHttpServer.this.shutdown) {
            if(Thread.interrupted()) {
               return;
            }

            try {
               HttpServerConnection var1 = TestHttpServer.this.acceptConnection();
               BasicHttpProcessor var2 = TestHttpServer.this.httpproc;
               ConnectionReuseStrategy var3 = TestHttpServer.this.connStrategy;
               HttpResponseFactory var4 = TestHttpServer.this.responseFactory;
               HttpService var5 = new HttpService(var2, var3, var4);
               HttpParams var6 = TestHttpServer.this.params;
               var5.setParams(var6);
               HttpExpectationVerifier var7 = TestHttpServer.this.expectationVerifier;
               var5.setExpectationVerifier(var7);
               HttpRequestHandlerRegistry var8 = TestHttpServer.this.reqistry;
               var5.setHandlerResolver(var8);
               TestHttpServer.WorkerThread var9 = new TestHttpServer.WorkerThread(var5, var1);
               var9.setDaemon((boolean)1);
               var9.start();
            } catch (InterruptedIOException var12) {
               return;
            } catch (IOException var13) {
               return;
            }
         }

      }
   }

   static class WorkerThread extends Thread {

      private final HttpServerConnection conn;
      private final HttpService httpservice;


      public WorkerThread(HttpService var1, HttpServerConnection var2) {
         this.httpservice = var1;
         this.conn = var2;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }
}
