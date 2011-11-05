package myorg.bouncycastle.jce.provider;

import myorg.bouncycastle.jce.exception.ExtException;

public class AnnotatedException extends Exception implements ExtException {

   private Throwable _underlyingException;


   AnnotatedException(String var1) {
      this(var1, (Throwable)null);
   }

   AnnotatedException(String var1, Throwable var2) {
      super(var1);
      this._underlyingException = var2;
   }

   public Throwable getCause() {
      return this._underlyingException;
   }

   Throwable getUnderlyingException() {
      return this._underlyingException;
   }
}
