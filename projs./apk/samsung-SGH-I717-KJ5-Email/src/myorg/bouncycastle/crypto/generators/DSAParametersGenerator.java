package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.digests.SHA256Digest;
import myorg.bouncycastle.crypto.params.DSAParameters;
import myorg.bouncycastle.crypto.params.DSAValidationParameters;
import myorg.bouncycastle.util.Arrays;
import myorg.bouncycastle.util.BigIntegers;

public class DSAParametersGenerator {

   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);
   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private int L;
   private int N;
   private int certainty;
   private SecureRandom random;


   public DSAParametersGenerator() {}

   private static BigInteger calculateGenerator_FIPS186_2(BigInteger var0, BigInteger var1, SecureRandom var2) {
      BigInteger var3 = ONE;
      BigInteger var4 = var0.subtract(var3).divide(var1);
      BigInteger var5 = TWO;
      BigInteger var6 = var0.subtract(var5);

      BigInteger var7;
      do {
         var7 = BigIntegers.createRandomInRange(TWO, var6, var2).modPow(var4, var0);
      } while(var7.bitLength() <= 1);

      return var7;
   }

   private static BigInteger calculateGenerator_FIPS186_3_Unverifiable(BigInteger var0, BigInteger var1, SecureRandom var2) {
      return calculateGenerator_FIPS186_2(var0, var1, var2);
   }

   private DSAParameters generateParameters_FIPS186_2() {
      byte[] var1 = new byte[20];
      byte[] var2 = new byte[20];
      byte[] var3 = new byte[20];
      byte[] var4 = new byte[20];
      SHA1Digest var5 = new SHA1Digest();
      int var6 = (this.L - 1) / 160;
      byte[] var7 = new byte[this.L / 8];

      label37:
      while(true) {
         SecureRandom var8 = this.random;
         var8.nextBytes(var1);
         hash(var5, var1, var2);
         int var13 = var1.length;
         byte var15 = 0;
         byte var17 = 0;
         System.arraycopy(var1, var15, var3, var17, var13);
         inc(var3);
         hash(var5, var3, var3);
         int var22 = 0;

         while(true) {
            int var23 = var4.length;
            if(var22 == var23) {
               byte var29 = (byte)(var4[0] | -128);
               var4[0] = var29;
               byte var30 = (byte)(var4[19] | 1);
               var4[19] = var30;
               BigInteger var31 = new BigInteger;
               byte var33 = 1;
               var31.<init>(var33, var4);
               int var35 = this.certainty;
               if(!var31.isProbablePrime(var35)) {
                  break;
               }

               byte[] var38 = Arrays.clone(var1);
               inc(var38);
               int var39 = 0;

               while(true) {
                  short var41 = 4096;
                  if(var39 >= var41) {
                     continue label37;
                  }

                  for(int var42 = 0; var42 < var6; ++var42) {
                     inc(var38);
                     hash(var5, var38, var2);
                     int var46 = var7.length;
                     int var47 = var42 + 1;
                     int var48 = var2.length;
                     int var49 = var47 * var48;
                     int var50 = var46 - var49;
                     int var51 = var2.length;
                     byte var53 = 0;
                     System.arraycopy(var2, var53, var7, var50, var51);
                  }

                  inc(var38);
                  hash(var5, var38, var2);
                  int var60 = var2.length;
                  int var61 = var7.length;
                  int var62 = var2.length * var6;
                  int var63 = var61 - var62;
                  int var64 = var60 - var63;
                  int var65 = var7.length;
                  int var66 = var2.length * var6;
                  int var67 = var65 - var66;
                  byte var71 = 0;
                  System.arraycopy(var2, var64, var7, var71, var67);
                  byte var73 = (byte)(var7[0] | -128);
                  var7[0] = var73;
                  BigInteger var74 = new BigInteger;
                  byte var76 = 1;
                  var74.<init>(var76, var7);
                  byte var79 = 1;
                  BigInteger var80 = var31.shiftLeft(var79);
                  BigInteger var81 = var74.mod(var80);
                  BigInteger var82 = ONE;
                  BigInteger var85 = var81.subtract(var82);
                  BigInteger var86 = var74.subtract(var85);
                  int var87 = var86.bitLength();
                  int var88 = this.L;
                  if(var87 == var88) {
                     int var91 = this.certainty;
                     if(var86.isProbablePrime(var91)) {
                        SecureRandom var94 = this.random;
                        BigInteger var98 = calculateGenerator_FIPS186_2(var86, var31, var94);
                        DSAParameters var99 = new DSAParameters;
                        DSAValidationParameters var100 = new DSAValidationParameters(var1, var39);
                        var99.<init>(var86, var31, var98, var100);
                        return var99;
                     }
                  }

                  ++var39;
               }
            }

            byte var26 = var2[var22];
            byte var27 = var3[var22];
            byte var28 = (byte)(var26 ^ var27);
            var4[var22] = var28;
            ++var22;
         }
      }
   }

   private DSAParameters generateParameters_FIPS186_3() {
      SHA256Digest var1 = new SHA256Digest();
      int var2 = var1.getDigestSize() * 8;
      byte[] var3 = new byte[this.N / 8];
      int var4 = (this.L - 1) / var2;
      int var5 = (this.L - 1) % var2;
      byte[] var6 = new byte[var1.getDigestSize()];

      while(true) {
         BigInteger var31;
         int var32;
         do {
            SecureRandom var7 = this.random;
            var7.nextBytes(var3);
            hash(var1, var3, var6);
            BigInteger var12 = new BigInteger;
            byte var14 = 1;
            var12.<init>(var14, var6);
            BigInteger var16 = ONE;
            int var17 = this.N - 1;
            BigInteger var18 = var16.shiftLeft(var17);
            BigInteger var19 = var12.mod(var18);
            BigInteger var20 = ONE;
            int var21 = this.N - 1;
            BigInteger var22 = var20.shiftLeft(var21);
            BigInteger var24 = var22.add(var19);
            BigInteger var25 = ONE;
            BigInteger var26 = var24.add(var25);
            BigInteger var27 = TWO;
            BigInteger var30 = var19.mod(var27);
            var31 = var26.subtract(var30);
            var32 = this.certainty;
         } while(!var31.isProbablePrime(var32));

         byte[] var35 = Arrays.clone(var3);
         int var36 = this.L * 4;

         for(int var37 = 0; var37 < var36; ++var37) {
            BigInteger var38 = ZERO;
            int var39 = 0;

            for(int var40 = 0; var39 <= var4; var40 += var2) {
               inc(var35);
               hash(var1, var35, var6);
               BigInteger var46 = new BigInteger;
               byte var48 = 1;
               var46.<init>(var48, var6);
               if(var39 == var4) {
                  BigInteger var52 = ONE;
                  BigInteger var54 = var52.shiftLeft(var5);
                  var46 = var46.mod(var54);
               }

               BigInteger var57 = var46.shiftLeft(var40);
               var38 = var38.add(var57);
               ++var39;
            }

            BigInteger var60 = ONE;
            int var61 = this.L - 1;
            BigInteger var62 = var60.shiftLeft(var61);
            BigInteger var65 = var38.add(var62);
            byte var67 = 1;
            BigInteger var68 = var31.shiftLeft(var67);
            BigInteger var71 = var65.mod(var68);
            BigInteger var72 = ONE;
            BigInteger var75 = var71.subtract(var72);
            BigInteger var78 = var65.subtract(var75);
            int var79 = var78.bitLength();
            int var80 = this.L;
            if(var79 == var80) {
               int var83 = this.certainty;
               if(var78.isProbablePrime(var83)) {
                  SecureRandom var86 = this.random;
                  BigInteger var90 = calculateGenerator_FIPS186_3_Unverifiable(var78, var31, var86);
                  DSAParameters var91 = new DSAParameters;
                  DSAValidationParameters var92 = new DSAValidationParameters(var3, var37);
                  var91.<init>(var78, var31, var90, var92);
                  return var91;
               }
            }
         }
      }
   }

   private static int getDefaultN(int var0) {
      short var1;
      if(var0 > 1024) {
         var1 = 256;
      } else {
         var1 = 160;
      }

      return var1;
   }

   private static void hash(Digest var0, byte[] var1, byte[] var2) {
      int var3 = var1.length;
      var0.update(var1, 0, var3);
      var0.doFinal(var2, 0);
   }

   private static void inc(byte[] var0) {
      for(int var1 = var0.length - 1; var1 >= 0; var1 += -1) {
         byte var2 = (byte)(var0[var1] + 1 & 255);
         var0[var1] = var2;
         if(var2 != 0) {
            return;
         }
      }

   }

   private void init(int var1, int var2, int var3, SecureRandom var4) {
      this.L = var1;
      this.N = var2;
      this.certainty = var3;
      this.random = var4;
   }

   public DSAParameters generateParameters() {
      DSAParameters var1;
      if(this.L > 1024) {
         var1 = this.generateParameters_FIPS186_3();
      } else {
         var1 = this.generateParameters_FIPS186_2();
      }

      return var1;
   }

   public void init(int var1, int var2, SecureRandom var3) {
      int var4 = getDefaultN(var1);
      this.init(var1, var4, var2, var3);
   }
}
