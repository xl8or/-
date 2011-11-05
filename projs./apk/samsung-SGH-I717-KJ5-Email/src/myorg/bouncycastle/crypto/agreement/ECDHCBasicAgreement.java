package myorg.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.BasicAgreement;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECDHCBasicAgreement implements BasicAgreement {

   ECPrivateKeyParameters key;


   public ECDHCBasicAgreement() {}

   public BigInteger calculateAgreement(CipherParameters var1) {
      ECPublicKeyParameters var2 = (ECPublicKeyParameters)var1;
      ECDomainParameters var3 = var2.getParameters();
      ECPoint var4 = var2.getQ();
      BigInteger var5 = var3.getH();
      BigInteger var6 = this.key.getD();
      BigInteger var7 = var5.multiply(var6);
      return var4.multiply(var7).getX().toBigInteger();
   }

   public void init(CipherParameters var1) {
      ECPrivateKeyParameters var2 = (ECPrivateKeyParameters)var1;
      this.key = var2;
   }
}
