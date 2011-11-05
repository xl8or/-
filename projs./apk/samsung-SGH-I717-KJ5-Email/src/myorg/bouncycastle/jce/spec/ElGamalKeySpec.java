package myorg.bouncycastle.jce.spec;

import java.security.spec.KeySpec;
import myorg.bouncycastle.jce.spec.ElGamalParameterSpec;

public class ElGamalKeySpec implements KeySpec {

   private ElGamalParameterSpec spec;


   public ElGamalKeySpec(ElGamalParameterSpec var1) {
      this.spec = var1;
   }

   public ElGamalParameterSpec getParams() {
      return this.spec;
   }
}
