package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.generators.DHKeyGeneratorHelper;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.ElGamalKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.ElGamalParameters;
import myorg.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ElGamalPublicKeyParameters;

public class ElGamalKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {

   private ElGamalKeyGenerationParameters param;


   public ElGamalKeyPairGenerator() {}

   public AsymmetricCipherKeyPair generateKeyPair() {
      DHKeyGeneratorHelper var1 = DHKeyGeneratorHelper.INSTANCE;
      ElGamalParameters var2 = this.param.getParameters();
      BigInteger var3 = var2.getP();
      BigInteger var4 = var2.getG();
      int var5 = var2.getL();
      DHParameters var6 = new DHParameters(var3, var4, (BigInteger)null, var5);
      SecureRandom var7 = this.param.getRandom();
      BigInteger var8 = var1.calculatePrivate(var6, var7);
      BigInteger var9 = var1.calculatePublic(var6, var8);
      ElGamalPublicKeyParameters var10 = new ElGamalPublicKeyParameters(var9, var2);
      ElGamalPrivateKeyParameters var11 = new ElGamalPrivateKeyParameters(var8, var2);
      return new AsymmetricCipherKeyPair(var10, var11);
   }

   public void init(KeyGenerationParameters var1) {
      ElGamalKeyGenerationParameters var2 = (ElGamalKeyGenerationParameters)var1;
      this.param = var2;
   }
}
