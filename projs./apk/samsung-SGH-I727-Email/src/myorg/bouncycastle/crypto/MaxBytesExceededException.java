package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.RuntimeCryptoException;

public class MaxBytesExceededException extends RuntimeCryptoException {

   public MaxBytesExceededException() {}

   public MaxBytesExceededException(String var1) {
      super(var1);
   }
}
