package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.DHParameters;

public class DHKeyParameters extends AsymmetricKeyParameter {

   private DHParameters params;


   protected DHKeyParameters(boolean var1, DHParameters var2) {
      super(var1);
      this.params = var2;
   }

   public boolean equals(Object var1) {
      byte var2;
      if(!(var1 instanceof DHKeyParameters)) {
         var2 = 0;
      } else {
         DHKeyParameters var3 = (DHKeyParameters)var1;
         if(this.params == null) {
            if(var3.getParameters() == null) {
               var2 = 1;
            } else {
               var2 = 0;
            }
         } else {
            DHParameters var4 = this.params;
            DHParameters var5 = var3.getParameters();
            var2 = var4.equals(var5);
         }
      }

      return (boolean)var2;
   }

   public DHParameters getParameters() {
      return this.params;
   }

   public int hashCode() {
      int var1;
      if(this.isPrivate()) {
         var1 = 0;
      } else {
         var1 = 1;
      }

      if(this.params != null) {
         int var2 = this.params.hashCode();
         var1 ^= var2;
      }

      return var1;
   }
}
