package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECKeyParameters;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECPublicKeyParameters extends ECKeyParameters {

   ECPoint Q;


   public ECPublicKeyParameters(ECPoint var1, ECDomainParameters var2) {
      super((boolean)0, var2);
      this.Q = var1;
   }

   public ECPoint getQ() {
      return this.Q;
   }
}
