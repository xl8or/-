package com.htc.android.mail.ssl;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.apache.harmony.xnet.provider.jsse.TrustManagerImpl;

public class SelfSignTrustManager extends TrustManagerImpl {

   static final String TAG = "SelfSignTrustManager";


   public SelfSignTrustManager(KeyStore var1) {
      super(var1);
   }

   public void checkServerTrusted(X509Certificate[] param1, String param2) throws CertificateException {
      // $FF: Couldn't be decompiled
   }
}
