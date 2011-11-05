package org.acra.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import org.acra.util.NaiveTrustManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class FakeSocketFactory implements SocketFactory, LayeredSocketFactory {

   private SSLContext sslcontext = null;


   public FakeSocketFactory() {}

   private static SSLContext createEasySSLContext() throws IOException {
      try {
         SSLContext var0 = SSLContext.getInstance("TLS");
         TrustManager[] var1 = new TrustManager[1];
         NaiveTrustManager var2 = new NaiveTrustManager();
         var1[0] = var2;
         var0.init((KeyManager[])null, var1, (SecureRandom)null);
         return var0;
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
      return this.getSSLContext().getSocketFactory().createSocket(var1, var2, var3, var4);
   }

   public boolean isSecure(Socket var1) throws IllegalArgumentException {
      return true;
   }
}
