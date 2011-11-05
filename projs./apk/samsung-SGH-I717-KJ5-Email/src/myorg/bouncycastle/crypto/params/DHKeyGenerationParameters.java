package myorg.bouncycastle.crypto.params;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.DHParameters;

public class DHKeyGenerationParameters extends KeyGenerationParameters {

   private DHParameters params;


   public DHKeyGenerationParameters(SecureRandom var1, DHParameters var2) {
      int var3 = getStrength(var2);
      super(var1, var3);
      this.params = var2;
   }

   static int getStrength(DHParameters var0) {
      int var1;
      if(var0.getL() != 0) {
         var1 = var0.getL();
      } else {
         var1 = var0.getP().bitLength();
      }

      return var1;
   }

   public DHParameters getParameters() {
      return this.params;
   }
}
