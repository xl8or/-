package myorg.bouncycastle.crypto.params;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.ElGamalParameters;

public class ElGamalKeyGenerationParameters extends KeyGenerationParameters {

   private ElGamalParameters params;


   public ElGamalKeyGenerationParameters(SecureRandom var1, ElGamalParameters var2) {
      int var3 = getStrength(var2);
      super(var1, var3);
      this.params = var2;
   }

   static int getStrength(ElGamalParameters var0) {
      int var1;
      if(var0.getL() != 0) {
         var1 = var0.getL();
      } else {
         var1 = var0.getP().bitLength();
      }

      return var1;
   }

   public ElGamalParameters getParameters() {
      return this.params;
   }
}
