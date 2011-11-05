package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECAlgorithms {

   public ECAlgorithms() {}

   private static ECPoint implShamirsTrick(ECPoint var0, BigInteger var1, ECPoint var2, BigInteger var3) {
      int var4 = var1.bitLength();
      int var5 = var3.bitLength();
      int var6 = Math.max(var4, var5);
      ECPoint var7 = var0.add(var2);
      ECPoint var8 = var0.getCurve().getInfinity();

      for(int var9 = var6 - 1; var9 >= 0; var9 += -1) {
         var8 = var8.twice();
         if(var1.testBit(var9)) {
            if(var3.testBit(var9)) {
               var8 = var8.add(var7);
            } else {
               var8 = var8.add(var0);
            }
         } else if(var3.testBit(var9)) {
            var8 = var8.add(var2);
         }
      }

      return var8;
   }

   public static ECPoint shamirsTrick(ECPoint var0, BigInteger var1, ECPoint var2, BigInteger var3) {
      ECCurve var4 = var0.getCurve();
      ECCurve var5 = var2.getCurve();
      if(!var4.equals(var5)) {
         throw new IllegalArgumentException("P and Q must be on same curve");
      } else {
         return implShamirsTrick(var0, var1, var2, var3);
      }
   }

   public static ECPoint sumOfTwoMultiplies(ECPoint var0, BigInteger var1, ECPoint var2, BigInteger var3) {
      ECCurve var4 = var0.getCurve();
      ECCurve var5 = var2.getCurve();
      if(!var4.equals(var5)) {
         throw new IllegalArgumentException("P and Q must be on same curve");
      } else {
         return implShamirsTrick(var0, var1, var2, var3);
      }
   }
}
