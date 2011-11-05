package myorg.bouncycastle.jce.exception;

import java.security.cert.CertificateEncodingException;
import myorg.bouncycastle.jce.exception.ExtException;

public class ExtCertificateEncodingException extends CertificateEncodingException implements ExtException {

   private Throwable cause;


   public ExtCertificateEncodingException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
