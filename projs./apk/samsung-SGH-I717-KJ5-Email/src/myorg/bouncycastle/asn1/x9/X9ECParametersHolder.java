package myorg.bouncycastle.asn1.x9;

import myorg.bouncycastle.asn1.x9.X9ECParameters;

public abstract class X9ECParametersHolder {

   private X9ECParameters params;


   public X9ECParametersHolder() {}

   protected abstract X9ECParameters createParameters();

   public X9ECParameters getParameters() {
      if(this.params == null) {
         X9ECParameters var1 = this.createParameters();
         this.params = var1;
      }

      return this.params;
   }
}
