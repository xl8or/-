package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.KeyGenerationParameters;

public class RSAKeyGenerationParameters extends KeyGenerationParameters {

   private int certainty;
   private BigInteger publicExponent;


   public RSAKeyGenerationParameters(BigInteger var1, SecureRandom var2, int var3, int var4) {
      super(var2, var3);
      if(var3 < 12) {
         throw new IllegalArgumentException("key strength too small");
      } else if(!var1.testBit(0)) {
         throw new IllegalArgumentException("public exponent cannot be even");
      } else {
         this.publicExponent = var1;
         this.certainty = var4;
      }
   }

   public int getCertainty() {
      return this.certainty;
   }

   public BigInteger getPublicExponent() {
      return this.publicExponent;
   }
}
