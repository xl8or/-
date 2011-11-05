package myorg.bouncycastle.crypto.params;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.CipherParameters;

public class ParametersWithRandom implements CipherParameters {

   private CipherParameters parameters;
   private SecureRandom random;


   public ParametersWithRandom(CipherParameters var1) {
      SecureRandom var2 = new SecureRandom();
      this(var1, var2);
   }

   public ParametersWithRandom(CipherParameters var1, SecureRandom var2) {
      this.random = var2;
      this.parameters = var1;
   }

   public CipherParameters getParameters() {
      return this.parameters;
   }

   public SecureRandom getRandom() {
      return this.random;
   }
}
