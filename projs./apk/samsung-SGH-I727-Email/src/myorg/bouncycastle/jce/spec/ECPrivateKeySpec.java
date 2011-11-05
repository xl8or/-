package myorg.bouncycastle.jce.spec;

import java.math.BigInteger;
import myorg.bouncycastle.jce.spec.ECKeySpec;
import myorg.bouncycastle.jce.spec.ECParameterSpec;

public class ECPrivateKeySpec extends ECKeySpec {

   private BigInteger d;


   public ECPrivateKeySpec(BigInteger var1, ECParameterSpec var2) {
      super(var2);
      this.d = var1;
   }

   public BigInteger getD() {
      return this.d;
   }
}
