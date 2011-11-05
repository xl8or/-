package com.htc.android.mail.eassvc.common;

import com.android.internal.net.DomainNameValidator;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import org.apache.harmony.xnet.provider.jsse.SSLParametersImpl;

public final class TrustManagerFactory {

   private static X509TrustManager sUnsecureTrustManager = new TrustManagerFactory.SimpleX509TrustManager((TrustManagerFactory.1)null);


   private TrustManagerFactory() {}

   public static X509TrustManager get(String var0, boolean var1) {
      Object var3;
      if(var1) {
         X509TrustManager var2 = SSLParametersImpl.getDefaultTrustManager();
         var3 = new TrustManagerFactory.SecureX509TrustManager(var2, var0);
      } else {
         var3 = sUnsecureTrustManager;
      }

      return (X509TrustManager)var3;
   }

   private static void logCertificates(X509Certificate[] var0, String var1, boolean var2) {}

   private static class SimpleX509TrustManager implements X509TrustManager {

      private SimpleX509TrustManager() {}

      // $FF: synthetic method
      SimpleX509TrustManager(TrustManagerFactory.1 var1) {
         this();
      }

      public void checkClientTrusted(X509Certificate[] var1, String var2) {
         TrustManagerFactory.logCertificates(var1, "Trusting client", (boolean)0);
      }

      public void checkServerTrusted(X509Certificate[] var1, String var2) {
         TrustManagerFactory.logCertificates(var1, "Trusting server", (boolean)0);
      }

      public X509Certificate[] getAcceptedIssuers() {
         return null;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private static class SecureX509TrustManager implements X509TrustManager {

      private String mHost;
      private X509TrustManager mTrustManager;


      SecureX509TrustManager(X509TrustManager var1, String var2) {
         this.mTrustManager = var1;
         this.mHost = var2;
      }

      public void checkClientTrusted(X509Certificate[] var1, String var2) throws CertificateException {
         try {
            this.mTrustManager.checkClientTrusted(var1, var2);
         } catch (CertificateException var4) {
            TrustManagerFactory.logCertificates(var1, "Failed client", (boolean)1);
            throw var4;
         }
      }

      public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
         try {
            this.mTrustManager.checkServerTrusted(var1, var2);
         } catch (CertificateException var9) {
            TrustManagerFactory.logCertificates(var1, "Failed server", (boolean)1);
            throw var9;
         }

         X509Certificate var3 = var1[0];
         String var4 = this.mHost;
         if(!DomainNameValidator.match(var3, var4)) {
            TrustManagerFactory.logCertificates(var1, "Failed domain name", (boolean)1);
            StringBuilder var5 = (new StringBuilder()).append("Certificate domain name does not match ");
            String var6 = this.mHost;
            String var7 = var5.append(var6).toString();
            throw new CertificateException(var7);
         }
      }

      public X509Certificate[] getAcceptedIssuers() {
         return this.mTrustManager.getAcceptedIssuers();
      }
   }
}
