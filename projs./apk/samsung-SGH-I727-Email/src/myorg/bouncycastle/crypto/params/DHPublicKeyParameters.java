package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.DHKeyParameters;
import myorg.bouncycastle.crypto.params.DHParameters;

public class DHPublicKeyParameters extends DHKeyParameters {

   private BigInteger y;


   public DHPublicKeyParameters(BigInteger var1, DHParameters var2) {
      super((boolean)0, var2);
      this.y = var1;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof DHPublicKeyParameters)) {
         var2 = false;
      } else {
         BigInteger var3 = ((DHPublicKeyParameters)var1).getY();
         BigInteger var4 = this.y;
         if(var3.equals(var4) && super.equals(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public BigInteger getY() {
      return this.y;
   }

   public int hashCode() {
      int var1 = this.y.hashCode();
      int var2 = super.hashCode();
      return var1 ^ var2;
   }
}
