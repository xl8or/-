package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.DSAKeyParameters;
import myorg.bouncycastle.crypto.params.DSAParameters;

public class DSAPublicKeyParameters extends DSAKeyParameters {

   private BigInteger y;


   public DSAPublicKeyParameters(BigInteger var1, DSAParameters var2) {
      super((boolean)0, var2);
      this.y = var1;
   }

   public BigInteger getY() {
      return this.y;
   }
}
