package myorg.bouncycastle.jce.exception;

import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import myorg.bouncycastle.jce.exception.ExtException;

public class ExtCertPathValidatorException extends CertPathValidatorException implements ExtException {

   private Throwable cause;


   public ExtCertPathValidatorException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public ExtCertPathValidatorException(String var1, Throwable var2, CertPath var3, int var4) {
      super(var1, var2, var3, var4);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
