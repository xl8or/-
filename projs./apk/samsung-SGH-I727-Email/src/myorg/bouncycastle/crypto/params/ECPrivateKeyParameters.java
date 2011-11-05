package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECKeyParameters;

public class ECPrivateKeyParameters extends ECKeyParameters {

   BigInteger d;


   public ECPrivateKeyParameters(BigInteger var1, ECDomainParameters var2) {
      super((boolean)1, var2);
      this.d = var1;
   }

   public BigInteger getD() {
      return this.d;
   }
}
