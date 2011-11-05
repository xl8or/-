package com.android.exchange.cba;

import android.os.SystemProperties;
import android.util.Log;
import com.android.exchange.cba.SSLSessionCache;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.harmony.xnet.provider.jsse.ClientSessionContext;
import org.apache.harmony.xnet.provider.jsse.OpenSSLContextImpl;
import org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl;
import org.apache.harmony.xnet.provider.jsse.SSLClientSessionCache;

public class SSLCertificateSocketFactory extends SSLSocketFactory {

   private static final HostnameVerifier HOSTNAME_VERIFIER;
   private static final TrustManager[] INSECURE_TRUST_MANAGER;
   private static final String TAG = "SSLCertificateSocketFactory";
   private final int mHandshakeTimeoutMillis;
   private SSLSocketFactory mInsecureFactory;
   private KeyManager[] mKeyManager;
   private final boolean mSecure;
   private SSLSocketFactory mSecureFactory;
   private final SSLClientSessionCache mSessionCache;


   static {
      TrustManager[] var0 = new TrustManager[1];
      SSLCertificateSocketFactory.1 var1 = new SSLCertificateSocketFactory.1();
      var0[0] = var1;
      INSECURE_TRUST_MANAGER = var0;
      HOSTNAME_VERIFIER = HttpsURLConnection.getDefaultHostnameVerifier();
   }

   @Deprecated
   public SSLCertificateSocketFactory(int var1) {
      this(var1, (SSLSessionCache)null, (boolean)1);
   }

   private SSLCertificateSocketFactory(int var1, SSLSessionCache var2, boolean var3) {
      SSLClientSessionCache var4 = null;
      super();
      this.mKeyManager = var4;
      this.mInsecureFactory = var4;
      this.mSecureFactory = var4;
      this.mHandshakeTimeoutMillis = var1;
      if(var2 != null) {
         var4 = var2.mSessionCache;
      }

      this.mSessionCache = var4;
      this.mSecure = var3;
   }

   public static SocketFactory getDefault(int var0) {
      return new SSLCertificateSocketFactory(var0, (SSLSessionCache)null, (boolean)1);
   }

   public static SSLSocketFactory getDefault(int var0, SSLSessionCache var1) {
      return new SSLCertificateSocketFactory(var0, var1, (boolean)1);
   }

   public static SSLSocketFactory getDefault(KeyManager[] var0, int var1, SSLSessionCache var2) {
      SSLCertificateSocketFactory var3 = new SSLCertificateSocketFactory(var1, var2, (boolean)1);
      var3.mKeyManager = var0;
      return var3;
   }

   private SSLSocketFactory getDelegate() {
      synchronized(this){}

      SSLSocketFactory var4;
      try {
         if(this.mSecure && !isSslCheckRelaxed()) {
            if(this.mSecureFactory == null) {
               SSLSocketFactory var7 = this.makeSocketFactory((TrustManager[])null);
               this.mSecureFactory = var7;
            }

            var4 = this.mSecureFactory;
         } else {
            if(this.mInsecureFactory == null) {
               if(this.mSecure) {
                  int var1 = Log.w("SSLCertificateSocketFactory", "*** BYPASSING SSL SECURITY CHECKS (socket.relaxsslcheck=yes) ***");
               } else {
                  int var5 = Log.w("SSLCertificateSocketFactory", "Bypassing SSL security checks at caller\'s request");
               }

               TrustManager[] var2 = INSECURE_TRUST_MANAGER;
               SSLSocketFactory var3 = this.makeSocketFactory(var2);
               this.mInsecureFactory = var3;
            }

            var4 = this.mInsecureFactory;
         }
      } finally {
         ;
      }

      return var4;
   }

   public static org.apache.http.conn.ssl.SSLSocketFactory getHttpSocketFactory(int var0, SSLSessionCache var1) {
      SSLCertificateSocketFactory var2 = new SSLCertificateSocketFactory(var0, var1, (boolean)1);
      return new org.apache.http.conn.ssl.SSLSocketFactory(var2);
   }

   public static SSLSocketFactory getInsecure(int var0, SSLSessionCache var1) {
      return new SSLCertificateSocketFactory(var0, var1, (boolean)0);
   }

   public static SSLSocketFactory getInsecure(KeyManager[] var0, int var1, SSLSessionCache var2) {
      SSLCertificateSocketFactory var3 = new SSLCertificateSocketFactory(var1, var2, (boolean)0);
      var3.mKeyManager = var0;
      return var3;
   }

   private static boolean isSslCheckRelaxed() {
      String var0 = SystemProperties.get("ro.debuggable");
      boolean var2;
      if("1".equals(var0)) {
         String var1 = SystemProperties.get("socket.relaxsslcheck");
         if("yes".equals(var1)) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }

   private SSLSocketFactory makeSocketFactory(TrustManager[] var1) {
      SSLSocketFactory var6;
      SSLSocketFactory var7;
      try {
         OpenSSLContextImpl var2 = new OpenSSLContextImpl();
         KeyManager[] var3 = this.mKeyManager;
         var2.engineInit(var3, var1, (SecureRandom)null);
         ClientSessionContext var4 = var2.engineGetClientSessionContext();
         SSLClientSessionCache var5 = this.mSessionCache;
         var4.setPersistentCache(var5);
         var6 = var2.engineGetSocketFactory();
      } catch (KeyManagementException var10) {
         int var9 = Log.wtf("SSLCertificateSocketFactory", var10);
         var7 = (SSLSocketFactory)SSLSocketFactory.getDefault();
         return var7;
      }

      var7 = var6;
      return var7;
   }

   public static void verifyHostname(Socket var0, String var1) throws IOException {
      if(!(var0 instanceof SSLSocket)) {
         throw new IllegalArgumentException("Attempt to verify non-SSL socket");
      } else if(!isSslCheckRelaxed()) {
         SSLSocket var2 = (SSLSocket)var0;
         var2.startHandshake();
         SSLSession var3 = var2.getSession();
         if(var3 == null) {
            throw new SSLException("Cannot verify SSL socket without session");
         } else if(!HOSTNAME_VERIFIER.verify(var1, var3)) {
            String var4 = "Cannot verify hostname: " + var1;
            throw new SSLPeerUnverifiedException(var4);
         }
      }
   }

   public Socket createSocket() throws IOException {
      OpenSSLSocketImpl var1 = (OpenSSLSocketImpl)this.getDelegate().createSocket();
      int var2 = this.mHandshakeTimeoutMillis;
      var1.setHandshakeTimeout(var2);
      return var1;
   }

   public Socket createSocket(String var1, int var2) throws IOException {
      OpenSSLSocketImpl var3 = (OpenSSLSocketImpl)this.getDelegate().createSocket(var1, var2);
      int var4 = this.mHandshakeTimeoutMillis;
      var3.setHandshakeTimeout(var4);
      if(this.mSecure) {
         verifyHostname(var3, var1);
      }

      return var3;
   }

   public Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws IOException {
      OpenSSLSocketImpl var5 = (OpenSSLSocketImpl)this.getDelegate().createSocket(var1, var2, var3, var4);
      if(var5 != null) {
         int var6 = this.mHandshakeTimeoutMillis;
         var5.setHandshakeTimeout(var6);
         if(this.mSecure) {
            verifyHostname(var5, var1);
         }
      }

      return var5;
   }

   public Socket createSocket(InetAddress var1, int var2) throws IOException {
      OpenSSLSocketImpl var3 = (OpenSSLSocketImpl)this.getDelegate().createSocket(var1, var2);
      if(var3 != null) {
         int var4 = this.mHandshakeTimeoutMillis;
         var3.setHandshakeTimeout(var4);
      }

      return var3;
   }

   public Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      OpenSSLSocketImpl var5 = (OpenSSLSocketImpl)this.getDelegate().createSocket(var1, var2, var3, var4);
      if(var5 != null) {
         int var6 = this.mHandshakeTimeoutMillis;
         var5.setHandshakeTimeout(var6);
      }

      return var5;
   }

   public Socket createSocket(Socket var1, String var2, int var3, boolean var4) throws IOException {
      OpenSSLSocketImpl var5 = (OpenSSLSocketImpl)this.getDelegate().createSocket(var1, var2, var3, var4);
      int var6 = this.mHandshakeTimeoutMillis;
      var5.setHandshakeTimeout(var6);
      if(this.mSecure) {
         verifyHostname(var5, var2);
      }

      return var5;
   }

   public String[] getDefaultCipherSuites() {
      return this.getDelegate().getSupportedCipherSuites();
   }

   public String[] getSupportedCipherSuites() {
      return this.getDelegate().getSupportedCipherSuites();
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
