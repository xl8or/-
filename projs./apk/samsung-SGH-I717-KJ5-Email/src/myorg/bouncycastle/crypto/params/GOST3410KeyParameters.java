package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.GOST3410Parameters;

public class GOST3410KeyParameters extends AsymmetricKeyParameter {

   private GOST3410Parameters params;


   public GOST3410KeyParameters(boolean var1, GOST3410Parameters var2) {
      super(var1);
      this.params = var2;
   }

   public GOST3410Parameters getParameters() {
      return this.params;
   }
}
