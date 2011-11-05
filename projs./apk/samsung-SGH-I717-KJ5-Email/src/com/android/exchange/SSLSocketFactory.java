package com.android.exchange;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class SSLSocketFactory implements LayeredSocketFactory {

   public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
   public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
   private static final SSLSocketFactory DEFAULT_FACTORY = new SSLSocketFactory();
   public static final String SSL = "SSL";
   public static final String SSLV2 = "SSLv2";
   public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
   public static final String TLS = "TLS";
   private X509HostnameVerifier hostnameVerifier;
   private final HostNameResolver nameResolver;
   private final javax.net.ssl.SSLSocketFactory socketfactory;
   private final SSLContext sslcontext;


   private SSLSocketFactory() {
      X509HostnameVerifier var1 = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
      this.hostnameVerifier = var1;
      this.sslcontext = null;
      javax.net.ssl.SSLSocketFactory var2 = HttpsURLConnection.getDefaultSSLSocketFactory();
      this.socketfactory = var2;
      this.nameResolver = null;
   }

   public SSLSocketFactory(String var1, KeyStore var2, String var3, KeyStore var4, SecureRandom var5, HostNameResolver var6) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      X509HostnameVerifier var7 = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
      this.hostnameVerifier = var7;
      if(var1 == null) {
         var1 = "TLS";
      }

      KeyManager[] var8 = null;
      if(var2 != null) {
         var8 = createKeyManagers(var2, var3);
      }

      TrustManager[] var9 = null;
      if(var4 != null) {
         var9 = createTrustManagers(var4);
      }

      SSLContext var10 = SSLContext.getInstance(var1);
      this.sslcontext = var10;
      this.sslcontext.init(var8, var9, var5);
      javax.net.ssl.SSLSocketFactory var11 = this.sslcontext.getSocketFactory();
      this.socketfactory = var11;
      this.nameResolver = var6;
   }

   public SSLSocketFactory(KeyStore var1) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      Object var3 = null;
      Object var5 = null;
      Object var6 = null;
      this("TLS", (KeyStore)null, (String)var3, var1, (SecureRandom)var5, (HostNameResolver)var6);
   }

   public SSLSocketFactory(KeyStore var1, String var2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      Object var6 = null;
      Object var7 = null;
      this("TLS", var1, var2, (KeyStore)null, (SecureRandom)var6, (HostNameResolver)var7);
   }

   public SSLSocketFactory(KeyStore var1, String var2, KeyStore var3) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      Object var8 = null;
      this("TLS", var1, var2, var3, (SecureRandom)null, (HostNameResolver)var8);
   }

   public SSLSocketFactory(javax.net.ssl.SSLSocketFactory var1) {
      X509HostnameVerifier var2 = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
      this.hostnameVerifier = var2;
      this.sslcontext = null;
      this.socketfactory = var1;
      this.nameResolver = null;
   }

   private static KeyManager[] createKeyManagers(KeyStore var0, String var1) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
      if(var0 == null) {
         throw new IllegalArgumentException("Keystore may not be null");
      } else {
         KeyManagerFactory var2 = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
         char[] var3;
         if(var1 != null) {
            var3 = var1.toCharArray();
         } else {
            var3 = null;
         }

         var2.init(var0, var3);
         return var2.getKeyManagers();
      }
   }

   private static TrustManager[] createTrustManagers(KeyStore var0) throws KeyStoreException, NoSuchAlgorithmException {
      if(var0 == null) {
         throw new IllegalArgumentException("Keystore may not be null");
      } else {
         TrustManagerFactory var1 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
         var1.init(var0);
         return var1.getTrustManagers();
      }
   }

   public static SSLSocketFactory getSocketFactory() {
      return DEFAULT_FACTORY;
   }

   public Socket connectSocket(Socket var1, String var2, int var3, InetAddress var4, int var5, HttpParams var6) throws IOException {
      if(var2 == null) {
         throw new IllegalArgumentException("Target host may not be null.");
      } else if(var6 == null) {
         throw new IllegalArgumentException("Parameters may not be null.");
      } else {
         Socket var7;
         if(var1 != null) {
            var7 = var1;
         } else {
            var7 = this.createSocket();
         }

         SSLSocket var8 = (SSLSocket)var7;
         if(var4 != null || var5 > 0) {
            if(var5 < 0) {
               var5 = 0;
            }

            InetSocketAddress var9 = new InetSocketAddress(var4, var5);
            var8.bind(var9);
         }

         int var10 = HttpConnectionParams.getConnectionTimeout(var6);
         int var11 = HttpConnectionParams.getSoTimeout(var6);
         InetSocketAddress var13;
         if(this.nameResolver != null) {
            InetAddress var12 = this.nameResolver.resolve(var2);
            var13 = new InetSocketAddress(var12, var3);
         } else {
            var13 = new InetSocketAddress(var2, var3);
         }

         var8.connect(var13, var10);

         try {
            var8.setSoTimeout(var11);
         } catch (SocketException var21) {
            try {
               var8.close();
            } catch (Exception var18) {
               ;
            }

            throw var21;
         }

         try {
            this.hostnameVerifier.verify(var2, var8);
            return var8;
         } catch (IOException var20) {
            try {
               var8.close();
            } catch (Exception var19) {
               ;
            }

            throw var20;
         }
      }
   }

   public Socket createSocket() throws IOException {
      return (SSLSocket)this.socketfactory.createSocket();
   }

   public Socket createSocket(Socket var1, String var2, int var3, boolean var4) throws IOException, UnknownHostException {
      SSLSocket var5 = (SSLSocket)this.socketfactory.createSocket(var1, var2, var3, var4);

      try {
         this.hostnameVerifier.verify(var2, var5);
         return var5;
      } catch (IOException var9) {
         try {
            var5.close();
         } catch (Exception var8) {
            ;
         }

         throw var9;
      }
   }

   public X509HostnameVerifier getHostnameVerifier() {
      return this.hostnameVerifier;
   }

   public boolean isSecure(Socket var1) throws IllegalArgumentException {
      if(var1 == null) {
         throw new IllegalArgumentException("Socket may not be null.");
      } else if(!(var1 instanceof SSLSocket)) {
         throw new IllegalArgumentException("Socket not created by this factory.");
      } else if(var1.isClosed()) {
         throw new IllegalArgumentException("Socket is closed.");
      } else {
         return true;
      }
   }

   public void setHostnameVerifier(X509HostnameVerifier var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Hostname verifier may not be null");
      } else {
         this.hostnameVerifier = var1;
      }
   }
}
