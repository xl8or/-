package myorg.bouncycastle.jce.exception;

import java.io.IOException;
import myorg.bouncycastle.jce.exception.ExtException;

public class ExtIOException extends IOException implements ExtException {

   private Throwable cause;


   public ExtIOException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
