package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.DHKeyParameters;
import myorg.bouncycastle.crypto.params.DHParameters;

public class DHPrivateKeyParameters extends DHKeyParameters {

   private BigInteger x;


   public DHPrivateKeyParameters(BigInteger var1, DHParameters var2) {
      super((boolean)1, var2);
      this.x = var1;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof DHPrivateKeyParameters)) {
         var2 = false;
      } else {
         BigInteger var3 = ((DHPrivateKeyParameters)var1).getX();
         BigInteger var4 = this.x;
         if(var3.equals(var4) && super.equals(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public BigInteger getX() {
      return this.x;
   }

   public int hashCode() {
      int var1 = this.x.hashCode();
      int var2 = super.hashCode();
      return var1 ^ var2;
   }
}
