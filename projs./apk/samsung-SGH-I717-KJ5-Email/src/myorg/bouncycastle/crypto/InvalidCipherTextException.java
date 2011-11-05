package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.CryptoException;

public class InvalidCipherTextException extends CryptoException {

   public InvalidCipherTextException() {}

   public InvalidCipherTextException(String var1) {
      super(var1);
   }
}
