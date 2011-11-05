package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.GOST3410KeyParameters;
import myorg.bouncycastle.crypto.params.GOST3410Parameters;

public class GOST3410PublicKeyParameters extends GOST3410KeyParameters {

   private BigInteger y;


   public GOST3410PublicKeyParameters(BigInteger var1, GOST3410Parameters var2) {
      super((boolean)0, var2);
      this.y = var1;
   }

   public BigInteger getY() {
      return this.y;
   }
}
