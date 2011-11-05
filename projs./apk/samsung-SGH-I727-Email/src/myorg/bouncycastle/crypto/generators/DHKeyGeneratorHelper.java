package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.util.BigIntegers;

class DHKeyGeneratorHelper {

   static final DHKeyGeneratorHelper INSTANCE = new DHKeyGeneratorHelper();
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);


   private DHKeyGeneratorHelper() {}

   BigInteger calculatePrivate(DHParameters var1, SecureRandom var2) {
      BigInteger var3 = var1.getP();
      int var4 = var1.getL();
      BigInteger var7;
      if(var4 != 0) {
         BigInteger var5 = new BigInteger(var4, var2);
         int var6 = var4 - 1;
         var7 = var5.setBit(var6);
      } else {
         BigInteger var8 = TWO;
         int var9 = var1.getM();
         if(var9 != 0) {
            BigInteger var10 = ONE;
            int var11 = var9 - 1;
            var8 = var10.shiftLeft(var11);
         }

         BigInteger var12 = TWO;
         BigInteger var13 = var3.subtract(var12);
         BigInteger var14 = var1.getQ();
         if(var14 != null) {
            BigInteger var15 = TWO;
            var13 = var14.subtract(var15);
         }

         var7 = BigIntegers.createRandomInRange(var8, var13, var2);
      }

      return var7;
   }

   BigInteger calculatePublic(DHParameters var1, BigInteger var2) {
      BigInteger var3 = var1.getG();
      BigInteger var4 = var1.getP();
      return var3.modPow(var2, var4);
   }
}
