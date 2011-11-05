package myorg.bouncycastle.util;


public class StoreException extends RuntimeException {

   private Throwable _e;


   public StoreException(String var1, Throwable var2) {
      super(var1);
      this._e = var2;
   }

   public Throwable getCause() {
      return this._e;
   }
}
