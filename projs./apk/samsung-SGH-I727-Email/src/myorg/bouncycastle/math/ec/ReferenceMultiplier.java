package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import myorg.bouncycastle.math.ec.ECMultiplier;
import myorg.bouncycastle.math.ec.ECPoint;
import myorg.bouncycastle.math.ec.PreCompInfo;

class ReferenceMultiplier implements ECMultiplier {

   ReferenceMultiplier() {}

   public ECPoint multiply(ECPoint var1, BigInteger var2, PreCompInfo var3) {
      ECPoint var4 = var1.getCurve().getInfinity();
      int var5 = var2.bitLength();

      for(int var6 = 0; var6 < var5; ++var6) {
         if(var2.testBit(var6)) {
            var4 = var4.add(var1);
         }

         var1 = var1.twice();
      }

      return var4;
   }
}
