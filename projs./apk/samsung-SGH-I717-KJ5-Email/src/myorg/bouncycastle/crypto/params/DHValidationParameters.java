package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.util.Arrays;

public class DHValidationParameters {

   private int counter;
   private byte[] seed;


   public DHValidationParameters(byte[] var1, int var2) {
      this.seed = var1;
      this.counter = var2;
   }

   public boolean equals(Object var1) {
      byte var2;
      if(!(var1 instanceof DHValidationParameters)) {
         var2 = 0;
      } else {
         DHValidationParameters var3 = (DHValidationParameters)var1;
         int var4 = var3.counter;
         int var5 = this.counter;
         if(var4 != var5) {
            var2 = 0;
         } else {
            byte[] var6 = this.seed;
            byte[] var7 = var3.seed;
            var2 = Arrays.areEqual(var6, var7);
         }
      }

      return (boolean)var2;
   }

   public int getCounter() {
      return this.counter;
   }

   public byte[] getSeed() {
      return this.seed;
   }

   public int hashCode() {
      int var1 = this.counter;
      int var2 = Arrays.hashCode(this.seed);
      return var1 ^ var2;
   }
}
