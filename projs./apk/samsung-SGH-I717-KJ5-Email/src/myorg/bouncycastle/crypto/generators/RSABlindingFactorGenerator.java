package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSABlindingFactorGenerator {

   private static BigInteger ONE = BigInteger.valueOf(1L);
   private static BigInteger ZERO = BigInteger.valueOf(0L);
   private RSAKeyParameters key;
   private SecureRandom random;


   public RSABlindingFactorGenerator() {}

   public BigInteger generateBlindingFactor() {
      if(this.key == null) {
         throw new IllegalStateException("generator not initialised");
      } else {
         BigInteger var1 = this.key.getModulus();
         int var2 = var1.bitLength() - 1;

         while(true) {
            BigInteger var4;
            BigInteger var5;
            BigInteger var6;
            do {
               SecureRandom var3 = this.random;
               var4 = new BigInteger(var2, var3);
               var5 = var4.gcd(var1);
               var6 = ZERO;
            } while(var4.equals(var6));

            BigInteger var7 = ONE;
            if(!var4.equals(var7)) {
               BigInteger var8 = ONE;
               if(var5.equals(var8)) {
                  return var4;
               }
            }
         }
      }
   }

   public void init(CipherParameters var1) {
      if(var1 instanceof ParametersWithRandom) {
         ParametersWithRandom var2 = (ParametersWithRandom)var1;
         RSAKeyParameters var3 = (RSAKeyParameters)var2.getParameters();
         this.key = var3;
         SecureRandom var4 = var2.getRandom();
         this.random = var4;
      } else {
         RSAKeyParameters var5 = (RSAKeyParameters)var1;
         this.key = var5;
         SecureRandom var6 = new SecureRandom();
         this.random = var6;
      }

      if(this.key instanceof RSAPrivateCrtKeyParameters) {
         throw new IllegalArgumentException("generator requires RSA public key");
      }
   }
}
