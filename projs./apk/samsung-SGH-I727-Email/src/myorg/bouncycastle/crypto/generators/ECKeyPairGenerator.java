package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.math.ec.ECConstants;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECKeyPairGenerator implements AsymmetricCipherKeyPairGenerator, ECConstants {

   ECDomainParameters params;
   SecureRandom random;


   public ECKeyPairGenerator() {}

   public AsymmetricCipherKeyPair generateKeyPair() {
      BigInteger var1 = this.params.getN();
      int var2 = var1.bitLength();

      BigInteger var4;
      BigInteger var5;
      do {
         do {
            SecureRandom var3 = this.random;
            var4 = new BigInteger(var2, var3);
            var5 = ZERO;
         } while(var4.equals(var5));
      } while(var4.compareTo(var1) >= 0);

      ECPoint var6 = this.params.getG().multiply(var4);
      ECDomainParameters var7 = this.params;
      ECPublicKeyParameters var8 = new ECPublicKeyParameters(var6, var7);
      ECDomainParameters var9 = this.params;
      ECPrivateKeyParameters var10 = new ECPrivateKeyParameters(var4, var9);
      return new AsymmetricCipherKeyPair(var8, var10);
   }

   public void init(KeyGenerationParameters var1) {
      ECKeyGenerationParameters var2 = (ECKeyGenerationParameters)var1;
      SecureRandom var3 = var2.getRandom();
      this.random = var3;
      ECDomainParameters var4 = var2.getDomainParameters();
      this.params = var4;
   }
}
