package myorg.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.BasicAgreement;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECDHBasicAgreement implements BasicAgreement {

   private ECPrivateKeyParameters key;


   public ECDHBasicAgreement() {}

   public BigInteger calculateAgreement(CipherParameters var1) {
      ECPoint var2 = ((ECPublicKeyParameters)var1).getQ();
      BigInteger var3 = this.key.getD();
      return var2.multiply(var3).getX().toBigInteger();
   }

   public void init(CipherParameters var1) {
      ECPrivateKeyParameters var2 = (ECPrivateKeyParameters)var1;
      this.key = var2;
   }
}
