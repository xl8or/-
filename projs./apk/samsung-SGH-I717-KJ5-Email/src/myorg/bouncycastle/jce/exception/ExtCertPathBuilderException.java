package myorg.bouncycastle.jce.exception;

import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import myorg.bouncycastle.jce.exception.ExtException;

public class ExtCertPathBuilderException extends CertPathBuilderException implements ExtException {

   private Throwable cause;


   public ExtCertPathBuilderException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public ExtCertPathBuilderException(String var1, Throwable var2, CertPath var3, int var4) {
      super(var1, var2);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
