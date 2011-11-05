package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.GOST3410KeyParameters;
import myorg.bouncycastle.crypto.params.GOST3410Parameters;

public class GOST3410PrivateKeyParameters extends GOST3410KeyParameters {

   private BigInteger x;


   public GOST3410PrivateKeyParameters(BigInteger var1, GOST3410Parameters var2) {
      super((boolean)1, var2);
      this.x = var1;
   }

   public BigInteger getX() {
      return this.x;
   }
}
