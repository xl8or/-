package myorg.bouncycastle.crypto.params;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.ECDomainParameters;

public class ECKeyGenerationParameters extends KeyGenerationParameters {

   private ECDomainParameters domainParams;


   public ECKeyGenerationParameters(ECDomainParameters var1, SecureRandom var2) {
      int var3 = var1.getN().bitLength();
      super(var2, var3);
      this.domainParams = var1;
   }

   public ECDomainParameters getDomainParameters() {
      return this.domainParams;
   }
}
