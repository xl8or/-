package com.htc.android.mail.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class SimpleX509TrustManager implements X509TrustManager {

   public X509Certificate[] chain;


   public SimpleX509TrustManager() {}

   public void checkClientTrusted(X509Certificate[] var1, String var2) throws CertificateException {
      this.chain = var1;
   }

   public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
      this.chain = var1;
   }

   public X509Certificate[] getAcceptedIssuers() {
      return null;
   }
}
