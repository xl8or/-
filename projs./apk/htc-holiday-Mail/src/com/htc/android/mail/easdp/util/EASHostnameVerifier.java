package com.htc.android.mail.easdp.util;

import java.io.IOException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;

public class EASHostnameVerifier implements X509HostnameVerifier {

   public X509Certificate certificate;
   private AllowAllHostnameVerifier verifier;


   public EASHostnameVerifier() {
      AllowAllHostnameVerifier var1 = new AllowAllHostnameVerifier();
      this.verifier = var1;
      this.certificate = null;
   }

   public void verify(String var1, X509Certificate var2) throws SSLException {
      this.verifier.verify(var1, var2);
   }

   public void verify(String param1, SSLSocket param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void verify(String var1, String[] var2, String[] var3) throws SSLException {
      this.verifier.verify(var1, var2, var3);
   }

   public boolean verify(String param1, SSLSession param2) {
      // $FF: Couldn't be decompiled
   }
}
