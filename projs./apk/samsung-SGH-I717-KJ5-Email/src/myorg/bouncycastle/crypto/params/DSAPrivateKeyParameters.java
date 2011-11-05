package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.DSAKeyParameters;
import myorg.bouncycastle.crypto.params.DSAParameters;

public class DSAPrivateKeyParameters extends DSAKeyParameters {

   private BigInteger x;


   public DSAPrivateKeyParameters(BigInteger var1, DSAParameters var2) {
      super((boolean)1, var2);
      this.x = var1;
   }

   public BigInteger getX() {
      return this.x;
   }
}
