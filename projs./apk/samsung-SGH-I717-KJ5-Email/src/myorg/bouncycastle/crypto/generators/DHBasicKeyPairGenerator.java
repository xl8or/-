package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.generators.DHKeyGeneratorHelper;
import myorg.bouncycastle.crypto.params.DHKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.DHPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.DHPublicKeyParameters;

public class DHBasicKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {

   private DHKeyGenerationParameters param;


   public DHBasicKeyPairGenerator() {}

   public AsymmetricCipherKeyPair generateKeyPair() {
      DHKeyGeneratorHelper var1 = DHKeyGeneratorHelper.INSTANCE;
      DHParameters var2 = this.param.getParameters();
      SecureRandom var3 = this.param.getRandom();
      BigInteger var4 = var1.calculatePrivate(var2, var3);
      BigInteger var5 = var1.calculatePublic(var2, var4);
      DHPublicKeyParameters var6 = new DHPublicKeyParameters(var5, var2);
      DHPrivateKeyParameters var7 = new DHPrivateKeyParameters(var4, var2);
      return new AsymmetricCipherKeyPair(var6, var7);
   }

   public void init(KeyGenerationParameters var1) {
      DHKeyGenerationParameters var2 = (DHKeyGenerationParameters)var1;
      this.param = var2;
   }
}
