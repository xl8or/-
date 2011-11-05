package myorg.bouncycastle.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class BigIntegers {

   private static final int MAX_ITERATIONS = 1000;
   private static final BigInteger ZERO = BigInteger.valueOf(0L);


   public BigIntegers() {}

   public static byte[] asUnsignedByteArray(BigInteger var0) {
      byte[] var1 = var0.toByteArray();
      byte[] var4;
      if(var1[0] == 0) {
         byte[] var2 = new byte[var1.length - 1];
         int var3 = var2.length;
         System.arraycopy(var1, 1, var2, 0, var3);
         var4 = var2;
      } else {
         var4 = var1;
      }

      return var4;
   }

   public static BigInteger createRandomInRange(BigInteger var0, BigInteger var1, SecureRandom var2) {
      int var3 = var0.compareTo(var1);
      BigInteger var4;
      if(var3 >= 0) {
         if(var3 > 0) {
            throw new IllegalArgumentException("\'min\' may not be greater than \'max\'");
         }

         var4 = var0;
      } else {
         int var5 = var0.bitLength();
         int var6 = var1.bitLength() / 2;
         if(var5 > var6) {
            BigInteger var7 = ZERO;
            BigInteger var8 = var1.subtract(var0);
            var4 = createRandomInRange(var7, var8, var2).add(var0);
         } else {
            int var9 = 0;

            while(true) {
               if(var9 >= 1000) {
                  int var12 = var1.subtract(var0).bitLength() - 1;
                  var4 = (new BigInteger(var12, var2)).add(var0);
                  break;
               }

               int var10 = var1.bitLength();
               BigInteger var11 = new BigInteger(var10, var2);
               if(var11.compareTo(var0) >= 0 && var11.compareTo(var1) <= 0) {
                  var4 = var11;
                  break;
               }

               ++var9;
            }
         }
      }

      return var4;
   }
}
