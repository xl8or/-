package myorg.bouncycastle.crypto.params;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.params.ElGamalKeyParameters;
import myorg.bouncycastle.crypto.params.ElGamalParameters;

public class ElGamalPrivateKeyParameters extends ElGamalKeyParameters {

   private BigInteger x;


   public ElGamalPrivateKeyParameters(BigInteger var1, ElGamalParameters var2) {
      super((boolean)1, var2);
      this.x = var1;
   }

   public boolean equals(Object var1) {
      byte var2;
      if(!(var1 instanceof ElGamalPrivateKeyParameters)) {
         var2 = 0;
      } else {
         BigInteger var3 = ((ElGamalPrivateKeyParameters)var1).getX();
         BigInteger var4 = this.x;
         if(!var3.equals(var4)) {
            var2 = 0;
         } else {
            var2 = super.equals(var1);
         }
      }

      return (boolean)var2;
   }

   public BigInteger getX() {
      return this.x;
   }

   public int hashCode() {
      return this.getX().hashCode();
   }
}
