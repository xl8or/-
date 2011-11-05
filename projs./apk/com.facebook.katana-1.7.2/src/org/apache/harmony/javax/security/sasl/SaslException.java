package org.apache.harmony.javax.security.sasl;

import java.io.IOException;

public class SaslException extends IOException {

   private static final long serialVersionUID = 4579784287983423626L;
   private Throwable _exception;


   public SaslException() {}

   public SaslException(String var1) {
      super(var1);
   }

   public SaslException(String var1, Throwable var2) {
      super(var1);
      if(var2 != null) {
         super.initCause(var2);
         this._exception = var2;
      }
   }

   public Throwable getCause() {
      return this._exception;
   }

   public Throwable initCause(Throwable var1) {
      super.initCause(var1);
      this._exception = var1;
      return this;
   }

   public String toString() {
      String var1;
      if(this._exception == null) {
         var1 = super.toString();
      } else {
         String var2 = super.toString();
         StringBuilder var3 = new StringBuilder(var2);
         StringBuilder var4 = var3.append(", caused by: ");
         String var5 = this._exception.toString();
         var3.append(var5);
         var1 = var3.toString();
      }

      return var1;
   }
}
