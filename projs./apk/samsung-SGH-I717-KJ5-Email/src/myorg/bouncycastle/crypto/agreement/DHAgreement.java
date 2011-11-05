package myorg.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.generators.DHKeyPairGenerator;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.DHKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.DHPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.DHPublicKeyParameters;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;

public class DHAgreement {

   private DHParameters dhParams;
   private DHPrivateKeyParameters key;
   private BigInteger privateValue;
   private SecureRandom random;


   public DHAgreement() {}

   public BigInteger calculateAgreement(DHPublicKeyParameters var1, BigInteger var2) {
      DHParameters var3 = var1.getParameters();
      DHParameters var4 = this.dhParams;
      if(!var3.equals(var4)) {
         throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
      } else {
         BigInteger var5 = this.dhParams.getP();
         BigInteger var6 = this.key.getX();
         BigInteger var7 = var2.modPow(var6, var5);
         BigInteger var8 = var1.getY();
         BigInteger var9 = this.privateValue;
         BigInteger var10 = var8.modPow(var9, var5);
         return var7.multiply(var10).mod(var5);
      }
   }

   public BigInteger calculateMessage() {
      DHKeyPairGenerator var1 = new DHKeyPairGenerator();
      SecureRandom var2 = this.random;
      DHParameters var3 = this.dhParams;
      DHKeyGenerationParameters var4 = new DHKeyGenerationParameters(var2, var3);
      var1.init(var4);
      AsymmetricCipherKeyPair var5 = var1.generateKeyPair();
      BigInteger var6 = ((DHPrivateKeyParameters)var5.getPrivate()).getX();
      this.privateValue = var6;
      return ((DHPublicKeyParameters)var5.getPublic()).getY();
   }

   public void init(CipherParameters var1) {
      AsymmetricKeyParameter var4;
      if(var1 instanceof ParametersWithRandom) {
         ParametersWithRandom var2 = (ParametersWithRandom)var1;
         SecureRandom var3 = var2.getRandom();
         this.random = var3;
         var4 = (AsymmetricKeyParameter)var2.getParameters();
      } else {
         SecureRandom var5 = new SecureRandom();
         this.random = var5;
         var4 = (AsymmetricKeyParameter)var1;
      }

      if(!(var4 instanceof DHPrivateKeyParameters)) {
         throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
      } else {
         DHPrivateKeyParameters var6 = (DHPrivateKeyParameters)var4;
         this.key = var6;
         DHParameters var7 = this.key.getParameters();
         this.dhParams = var7;
      }
   }
}
