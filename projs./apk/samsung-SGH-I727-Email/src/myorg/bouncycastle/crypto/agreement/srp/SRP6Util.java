package myorg.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.util.BigIntegers;

public class SRP6Util {

   private static BigInteger ONE = BigInteger.valueOf(1L);
   private static BigInteger ZERO = BigInteger.valueOf(0L);


   public SRP6Util() {}

   public static BigInteger calculateK(Digest var0, BigInteger var1, BigInteger var2) {
      return hashPaddedPair(var0, var1, var1, var2);
   }

   public static BigInteger calculateU(Digest var0, BigInteger var1, BigInteger var2, BigInteger var3) {
      return hashPaddedPair(var0, var1, var2, var3);
   }

   public static BigInteger calculateX(Digest var0, BigInteger var1, byte[] var2, byte[] var3, byte[] var4) {
      byte[] var5 = new byte[var0.getDigestSize()];
      int var6 = var3.length;
      var0.update(var3, 0, var6);
      var0.update((byte)58);
      int var7 = var4.length;
      var0.update(var4, 0, var7);
      var0.doFinal(var5, 0);
      int var9 = var2.length;
      var0.update(var2, 0, var9);
      int var10 = var5.length;
      var0.update(var5, 0, var10);
      var0.doFinal(var5, 0);
      return (new BigInteger(1, var5)).mod(var1);
   }

   public static BigInteger generatePrivateValue(Digest var0, BigInteger var1, BigInteger var2, SecureRandom var3) {
      int var4 = var1.bitLength() / 2;
      int var5 = Math.min(256, var4);
      BigInteger var6 = ONE;
      int var7 = var5 - 1;
      BigInteger var8 = var6.shiftLeft(var7);
      BigInteger var9 = ONE;
      BigInteger var10 = var1.subtract(var9);
      return BigIntegers.createRandomInRange(var8, var10, var3);
   }

   private static byte[] getPadded(BigInteger var0, int var1) {
      byte[] var2 = BigIntegers.asUnsignedByteArray(var0);
      if(var2.length < var1) {
         byte[] var3 = new byte[var1];
         int var4 = var2.length;
         int var5 = var1 - var4;
         int var6 = var2.length;
         System.arraycopy(var2, 0, var3, var5, var6);
         var2 = var3;
      }

      return var2;
   }

   private static BigInteger hashPaddedPair(Digest var0, BigInteger var1, BigInteger var2, BigInteger var3) {
      int var4 = (var1.bitLength() + 7) / 8;
      byte[] var5 = getPadded(var2, var4);
      byte[] var6 = getPadded(var3, var4);
      int var7 = var5.length;
      var0.update(var5, 0, var7);
      int var8 = var6.length;
      var0.update(var6, 0, var8);
      byte[] var9 = new byte[var0.getDigestSize()];
      var0.doFinal(var9, 0);
      return (new BigInteger(1, var9)).mod(var1);
   }

   public static BigInteger validatePublicValue(BigInteger var0, BigInteger var1) throws CryptoException {
      BigInteger var2 = var1.mod(var0);
      BigInteger var3 = ZERO;
      if(var2.equals(var3)) {
         throw new CryptoException("Invalid public value: 0");
      } else {
         return var2;
      }
   }
}
