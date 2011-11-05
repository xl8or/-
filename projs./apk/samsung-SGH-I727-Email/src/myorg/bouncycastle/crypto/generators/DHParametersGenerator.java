package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.generators.DHParametersHelper;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.DHValidationParameters;

public class DHParametersGenerator {

   private static final BigInteger TWO = BigInteger.valueOf(2L);
   private int certainty;
   private SecureRandom random;
   private int size;


   public DHParametersGenerator() {}

   public DHParameters generateParameters() {
      int var1 = this.size;
      int var2 = this.certainty;
      SecureRandom var3 = this.random;
      BigInteger[] var4 = DHParametersHelper.generateSafePrimes(var1, var2, var3);
      BigInteger var5 = var4[0];
      BigInteger var6 = var4[1];
      SecureRandom var7 = this.random;
      BigInteger var8 = DHParametersHelper.selectGenerator(var5, var6, var7);
      BigInteger var9 = TWO;
      return new DHParameters(var5, var8, var6, var9, (DHValidationParameters)null);
   }

   public void init(int var1, int var2, SecureRandom var3) {
      this.size = var1;
      this.certainty = var2;
      this.random = var3;
   }
}
