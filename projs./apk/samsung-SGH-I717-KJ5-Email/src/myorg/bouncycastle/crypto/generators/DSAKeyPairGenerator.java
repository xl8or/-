package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.DSAKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.DSAParameters;
import myorg.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.DSAPublicKeyParameters;
import myorg.bouncycastle.util.BigIntegers;

public class DSAKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {

   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private DSAKeyGenerationParameters param;


   public DSAKeyPairGenerator() {}

   private static BigInteger calculatePublicKey(BigInteger var0, BigInteger var1, BigInteger var2) {
      return var1.modPow(var2, var0);
   }

   private static BigInteger generatePrivateKey(BigInteger var0, SecureRandom var1) {
      BigInteger var2 = ONE;
      BigInteger var3 = ONE;
      BigInteger var4 = var0.subtract(var3);
      return BigIntegers.createRandomInRange(var2, var4, var1);
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      DSAParameters var1 = this.param.getParameters();
      BigInteger var2 = var1.getQ();
      SecureRandom var3 = this.param.getRandom();
      BigInteger var4 = generatePrivateKey(var2, var3);
      BigInteger var5 = var1.getP();
      BigInteger var6 = var1.getG();
      BigInteger var7 = calculatePublicKey(var5, var6, var4);
      DSAPublicKeyParameters var8 = new DSAPublicKeyParameters(var7, var1);
      DSAPrivateKeyParameters var9 = new DSAPrivateKeyParameters(var4, var1);
      return new AsymmetricCipherKeyPair(var8, var9);
   }

   public void init(KeyGenerationParameters var1) {
      DSAKeyGenerationParameters var2 = (DSAKeyGenerationParameters)var1;
      this.param = var2;
   }
}
