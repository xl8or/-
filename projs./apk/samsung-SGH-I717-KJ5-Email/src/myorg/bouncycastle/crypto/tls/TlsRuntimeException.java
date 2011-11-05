package myorg.bouncycastle.crypto.tls;


public class TlsRuntimeException extends RuntimeException {

   Throwable e;


   public TlsRuntimeException(String var1) {
      super(var1);
   }

   public TlsRuntimeException(String var1, Throwable var2) {
      super(var1);
      this.e = var2;
   }

   public Throwable getCause() {
      return this.e;
   }
}
