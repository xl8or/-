package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.util.BigIntegers;

class DHParametersHelper {

   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);


   DHParametersHelper() {}

   static BigInteger[] generateSafePrimes(int var0, int var1, SecureRandom var2) {
      int var3 = var0 - 1;

      BigInteger var4;
      BigInteger var7;
      do {
         do {
            var4 = new BigInteger(var3, 2, var2);
            BigInteger var5 = var4.shiftLeft(1);
            BigInteger var6 = ONE;
            var7 = var5.add(var6);
         } while(!var7.isProbablePrime(var1));
      } while(var1 > 2 && !var4.isProbablePrime(var1));

      BigInteger[] var8 = new BigInteger[]{var7, var4};
      return var8;
   }

   static BigInteger selectGenerator(BigInteger var0, BigInteger var1, SecureRandom var2) {
      BigInteger var3 = TWO;
      BigInteger var4 = var0.subtract(var3);

      BigInteger var5;
      BigInteger var9;
      BigInteger var10;
      do {
         BigInteger var7;
         BigInteger var8;
         do {
            var5 = BigIntegers.createRandomInRange(TWO, var4, var2);
            BigInteger var6 = TWO;
            var7 = var5.modPow(var6, var0);
            var8 = ONE;
         } while(var7.equals(var8));

         var9 = var5.modPow(var1, var0);
         var10 = ONE;
      } while(var9.equals(var10));

      return var5;
   }
}
