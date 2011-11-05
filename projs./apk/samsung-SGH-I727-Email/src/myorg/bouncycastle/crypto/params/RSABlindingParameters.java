package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSABlindingParameters implements CipherParameters {

   private BigInteger blindingFactor;
   private RSAKeyParameters publicKey;


   public RSABlindingParameters(RSAKeyParameters var1, BigInteger var2) {
      if(var1 instanceof RSAPrivateCrtKeyParameters) {
         throw new IllegalArgumentException("RSA parameters should be for a public key");
      } else {
         this.publicKey = var1;
         this.blindingFactor = var2;
      }
   }

   public BigInteger getBlindingFactor() {
      return this.blindingFactor;
   }

   public RSAKeyParameters getPublicKey() {
      return this.publicKey;
   }
}
