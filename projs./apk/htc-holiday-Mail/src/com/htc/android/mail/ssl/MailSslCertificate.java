package com.htc.android.mail.ssl;

import android.net.http.SslCertificate;
import java.security.cert.X509Certificate;

public class MailSslCertificate extends SslCertificate {

   private X509Certificate mX509Certificate;


   public MailSslCertificate(X509Certificate var1) {
      super(var1);
      this.mX509Certificate = var1;
   }

   public X509Certificate getX509Certificate() {
      return this.mX509Certificate;
   }
}
