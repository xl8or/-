package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.ElGamalParameters;

public class ElGamalKeyParameters extends AsymmetricKeyParameter {

   private ElGamalParameters params;


   protected ElGamalKeyParameters(boolean var1, ElGamalParameters var2) {
      super(var1);
      this.params = var2;
   }

   public boolean equals(Object var1) {
      byte var2;
      if(!(var1 instanceof ElGamalKeyParameters)) {
         var2 = 0;
      } else {
         ElGamalKeyParameters var3 = (ElGamalKeyParameters)var1;
         if(this.params == null) {
            if(var3.getParameters() == null) {
               var2 = 1;
            } else {
               var2 = 0;
            }
         } else {
            ElGamalParameters var4 = this.params;
            ElGamalParameters var5 = var3.getParameters();
            var2 = var4.equals(var5);
         }
      }

      return (boolean)var2;
   }

   public ElGamalParameters getParameters() {
      return this.params;
   }

   public int hashCode() {
      int var1;
      if(this.params != null) {
         var1 = this.params.hashCode();
      } else {
         var1 = 0;
      }

      return var1;
   }
}
