package com.android.email.mail.internet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class EasySSLSocketFactory implements SocketFactory, LayeredSocketFactory {

   private SSLContext sslcontext = null;


   public EasySSLSocketFactory() {}

   private static SSLContext createEasySSLContext() throws IOException {
      TrustManager[] var0 = new TrustManager[1];
      EasySSLSocketFactory.1 var1 = new EasySSLSocketFactory.1();
      var0[0] = var1;

      try {
         SSLContext var2 = SSLContext.getInstance("TLS");
         var2.init((KeyManager[])null, var0, (SecureRandom)null);
         return var2;
      } catch (Exception var4) {
         String var3 = var4.getMessage();
         throw new IOException(var3);
      }
   }

   private SSLContext getSSLContext() throws IOException {
      if(this.sslcontext == null) {
         SSLContext var1 = createEasySSLContext();
         this.sslcontext = var1;
      }

      return this.sslcontext;
   }

   public Socket connectSocket(Socket var1, String var2, int var3, InetAddress var4, int var5, HttpParams var6) throws IOException, UnknownHostException, ConnectTimeoutException {
      int var7 = HttpConnectionParams.getConnectionTimeout(var6);
      int var8 = HttpConnectionParams.getSoTimeout(var6);
      InetSocketAddress var9 = new InetSocketAddress(var2, var3);
      Socket var10;
      if(var1 != null) {
         var10 = var1;
      } else {
         var10 = this.createSocket();
      }

      SSLSocket var11 = (SSLSocket)var10;
      if(var4 != null || var5 > 0) {
         if(var5 < 0) {
            var5 = 0;
         }

         InetSocketAddress var12 = new InetSocketAddress(var4, var5);
         var11.bind(var12);
      }

      var11.connect(var9, var7);
      var11.setSoTimeout(var8);
      return var11;
   }

   public Socket createSocket() throws IOException {
      return this.getSSLContext().getSocketFactory().createSocket();
   }

   public Socket createSocket(Socket var1, String var2, int var3, boolean var4) throws IOException, UnknownHostException {
      return this.getSSLContext().getSocketFactory().createSocket();
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 != null && var1.getClass().equals(EasySSLSocketFactory.class)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return EasySSLSocketFactory.class.hashCode();
   }

   public boolean isSecure(Socket var1) throws IllegalArgumentException {
      return true;
   }

   static class 1 implements X509TrustManager {

      1() {}

      public void checkClientTrusted(X509Certificate[] var1, String var2) {}

      public void checkServerTrusted(X509Certificate[] var1, String var2) {}

      public X509Certificate[] getAcceptedIssuers() {
         return null;
      }
   }
}
