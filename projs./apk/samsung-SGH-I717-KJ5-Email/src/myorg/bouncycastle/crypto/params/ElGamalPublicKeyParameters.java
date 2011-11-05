package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.ElGamalKeyParameters;
import myorg.bouncycastle.crypto.params.ElGamalParameters;

public class ElGamalPublicKeyParameters extends ElGamalKeyParameters {

   private BigInteger y;


   public ElGamalPublicKeyParameters(BigInteger var1, ElGamalParameters var2) {
      super((boolean)0, var2);
      this.y = var1;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof ElGamalPublicKeyParameters)) {
         var2 = false;
      } else {
         BigInteger var3 = ((ElGamalPublicKeyParameters)var1).getY();
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
