package myorg.bouncycastle.crypto.params;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.DSAParameters;

public class DSAKeyGenerationParameters extends KeyGenerationParameters {

   private DSAParameters params;


   public DSAKeyGenerationParameters(SecureRandom var1, DSAParameters var2) {
      int var3 = var2.getP().bitLength() - 1;
      super(var1, var3);
      this.params = var2;
   }

   public DSAParameters getParameters() {
      return this.params;
   }
}
