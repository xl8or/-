package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import myorg.bouncycastle.math.ec.ECMultiplier;
import myorg.bouncycastle.math.ec.ECPoint;
import myorg.bouncycastle.math.ec.PreCompInfo;

class FpNafMultiplier implements ECMultiplier {

   FpNafMultiplier() {}

   public ECPoint multiply(ECPoint var1, BigInteger var2, PreCompInfo var3) {
      BigInteger var4 = var2;
      BigInteger var5 = BigInteger.valueOf(3L);
      BigInteger var6 = var2.multiply(var5);
      ECPoint var7 = var1.negate();
      ECPoint var8 = var1;

      for(int var9 = var6.bitLength() - 2; var9 > 0; var9 += -1) {
         var8 = var8.twice();
         boolean var10 = var6.testBit(var9);
         boolean var11 = var4.testBit(var9);
         if(var10 != var11) {
            ECPoint var12;
            if(var10) {
               var12 = var1;
            } else {
               var12 = var7;
            }

            var8.add(var12);
         }
      }

      return var8;
   }
}
