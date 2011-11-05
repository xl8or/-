package myorg.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.BasicAgreement;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.DHPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.DHPublicKeyParameters;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;

public class DHBasicAgreement implements BasicAgreement {

   private DHParameters dhParams;
   private DHPrivateKeyParameters key;


   public DHBasicAgreement() {}

   public BigInteger calculateAgreement(CipherParameters var1) {
      DHPublicKeyParameters var2 = (DHPublicKeyParameters)var1;
      DHParameters var3 = var2.getParameters();
      DHParameters var4 = this.dhParams;
      if(!var3.equals(var4)) {
         throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
      } else {
         BigInteger var5 = var2.getY();
         BigInteger var6 = this.key.getX();
         BigInteger var7 = this.dhParams.getP();
         return var5.modPow(var6, var7);
      }
   }

   public void init(CipherParameters var1) {
      AsymmetricKeyParameter var2;
      if(var1 instanceof ParametersWithRandom) {
         var2 = (AsymmetricKeyParameter)((ParametersWithRandom)var1).getParameters();
      } else {
         var2 = (AsymmetricKeyParameter)var1;
      }

      if(!(var2 instanceof DHPrivateKeyParameters)) {
         throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
      } else {
         DHPrivateKeyParameters var3 = (DHPrivateKeyParameters)var2;
         this.key = var3;
         DHParameters var4 = this.key.getParameters();
         this.dhParams = var4;
      }
   }
}
