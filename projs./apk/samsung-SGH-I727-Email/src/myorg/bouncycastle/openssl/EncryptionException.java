package myorg.bouncycastle.openssl;

import java.io.IOException;

public class EncryptionException extends IOException {

   private Throwable cause;


   public EncryptionException(String var1) {
      super(var1);
   }

   public EncryptionException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }
}
