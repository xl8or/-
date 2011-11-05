package myorg.bouncycastle.mail.smime;


public class SMIMEException extends Exception {

   Exception e;


   public SMIMEException(String var1) {
      super(var1);
   }

   public SMIMEException(String var1, Exception var2) {
      super(var1);
      this.e = var2;
   }

   public Throwable getCause() {
      return this.e;
   }

   public Exception getUnderlyingException() {
      return this.e;
   }
}
