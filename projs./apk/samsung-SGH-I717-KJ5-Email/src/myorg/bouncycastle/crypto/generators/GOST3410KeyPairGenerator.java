package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.GOST3410KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.GOST3410Parameters;
import myorg.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import myorg.bouncycastle.crypto.params.GOST3410PublicKeyParameters;

public class GOST3410KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {

   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private GOST3410KeyGenerationParameters param;


   public GOST3410KeyPairGenerator() {}

   public AsymmetricCipherKeyPair generateKeyPair() {
      GOST3410Parameters var1 = this.param.getParameters();
      SecureRandom var2 = this.param.getRandom();
      BigInteger var3 = var1.getQ();
      BigInteger var4 = var1.getP();
      BigInteger var5 = var1.getA();

      BigInteger var6;
      BigInteger var7;
      do {
         do {
            var6 = new BigInteger(256, var2);
            var7 = ZERO;
         } while(var6.equals(var7));
      } while(var6.compareTo(var3) >= 0);

      BigInteger var8 = var5.modPow(var6, var4);
      GOST3410PublicKeyParameters var9 = new GOST3410PublicKeyParameters(var8, var1);
      GOST3410PrivateKeyParameters var10 = new GOST3410PrivateKeyParameters(var6, var1);
      return new AsymmetricCipherKeyPair(var9, var10);
   }

   public void init(KeyGenerationParameters var1) {
      GOST3410KeyGenerationParameters var2 = (GOST3410KeyGenerationParameters)var1;
      this.param = var2;
   }
}
