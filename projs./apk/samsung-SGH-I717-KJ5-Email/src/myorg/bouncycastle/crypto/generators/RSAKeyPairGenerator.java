package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSAKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {

   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private RSAKeyGenerationParameters param;


   public RSAKeyPairGenerator() {}

   public AsymmetricCipherKeyPair generateKeyPair() {
      int var1 = this.param.getStrength();
      int var2 = (var1 + 1) / 2;
      int var3 = var1 - var2;
      int var4 = var1 / 3;
      BigInteger var5 = this.param.getPublicExponent();

      while(true) {
         BigInteger var7;
         BigInteger var8;
         BigInteger var9;
         do {
            SecureRandom var6 = this.param.getRandom();
            var7 = new BigInteger(var2, 1, var6);
            var8 = var7.mod(var5);
            var9 = ONE;
         } while(var8.equals(var9));

         int var10 = this.param.getCertainty();
         if(var7.isProbablePrime(var10)) {
            BigInteger var11 = ONE;
            BigInteger var12 = var7.subtract(var11);
            BigInteger var13 = var5.gcd(var12);
            BigInteger var14 = ONE;
            if(var13.equals(var14)) {
               while(true) {
                  BigInteger var16;
                  do {
                     SecureRandom var15 = this.param.getRandom();
                     var16 = new BigInteger(var3, 1, var15);
                  } while(var16.subtract(var7).abs().bitLength() < var4);

                  BigInteger var17 = var16.mod(var5);
                  BigInteger var18 = ONE;
                  if(!var17.equals(var18)) {
                     int var19 = this.param.getCertainty();
                     if(var16.isProbablePrime(var19)) {
                        BigInteger var20 = ONE;
                        BigInteger var21 = var16.subtract(var20);
                        BigInteger var22 = var5.gcd(var21);
                        BigInteger var23 = ONE;
                        if(var22.equals(var23)) {
                           BigInteger var24 = var7.multiply(var16);
                           int var25 = var24.bitLength();
                           int var26 = this.param.getStrength();
                           if(var25 == var26) {
                              if(var7.compareTo(var16) < 0) {
                                 BigInteger var27 = var7;
                                 var7 = var16;
                                 var16 = var27;
                              }

                              BigInteger var28 = ONE;
                              BigInteger var29 = var7.subtract(var28);
                              BigInteger var30 = ONE;
                              BigInteger var31 = var16.subtract(var30);
                              BigInteger var32 = var29.multiply(var31);
                              BigInteger var33 = var5.modInverse(var32);
                              BigInteger var34 = var33.remainder(var29);
                              BigInteger var35 = var33.remainder(var31);
                              BigInteger var36 = var16.modInverse(var7);
                              RSAKeyParameters var37 = new RSAKeyParameters((boolean)0, var24, var5);
                              RSAPrivateCrtKeyParameters var38 = new RSAPrivateCrtKeyParameters(var24, var5, var33, var7, var16, var34, var35, var36);
                              return new AsymmetricCipherKeyPair(var37, var38);
                           }

                           var7 = var7.max(var16);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void init(KeyGenerationParameters var1) {
      RSAKeyGenerationParameters var2 = (RSAKeyGenerationParameters)var1;
      this.param = var2;
   }
}
