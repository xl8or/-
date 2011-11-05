package myorg.bouncycastle.jce.spec;

import java.math.BigInteger;
import myorg.bouncycastle.jce.spec.ElGamalKeySpec;
import myorg.bouncycastle.jce.spec.ElGamalParameterSpec;

public class ElGamalPublicKeySpec extends ElGamalKeySpec {

   private BigInteger y;


   public ElGamalPublicKeySpec(BigInteger var1, ElGamalParameterSpec var2) {
      super(var2);
      this.y = var1;
   }

   public BigInteger getY() {
      return this.y;
   }
}
