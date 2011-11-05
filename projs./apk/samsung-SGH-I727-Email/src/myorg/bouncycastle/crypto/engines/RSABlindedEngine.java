package myorg.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.engines.RSACoreEngine;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import myorg.bouncycastle.util.BigIntegers;

public class RSABlindedEngine implements AsymmetricBlockCipher {

   private static BigInteger ONE = BigInteger.valueOf(1L);
   private RSACoreEngine core;
   private RSAKeyParameters key;
   private SecureRandom random;


   public RSABlindedEngine() {
      RSACoreEngine var1 = new RSACoreEngine();
      this.core = var1;
   }

   public int getInputBlockSize() {
      return this.core.getInputBlockSize();
   }

   public int getOutputBlockSize() {
      return this.core.getOutputBlockSize();
   }

   public void init(boolean var1, CipherParameters var2) {
      this.core.init(var1, var2);
      if(var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         RSAKeyParameters var4 = (RSAKeyParameters)var3.getParameters();
         this.key = var4;
         SecureRandom var5 = var3.getRandom();
         this.random = var5;
      } else {
         RSAKeyParameters var6 = (RSAKeyParameters)var2;
         this.key = var6;
         SecureRandom var7 = new SecureRandom();
         this.random = var7;
      }
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) {
      if(this.key == null) {
         throw new IllegalStateException("RSA engine not initialised");
      } else {
         BigInteger var4 = this.core.convertInput(var1, var2, var3);
         BigInteger var16;
         if(this.key instanceof RSAPrivateCrtKeyParameters) {
            RSAPrivateCrtKeyParameters var5 = (RSAPrivateCrtKeyParameters)this.key;
            BigInteger var6 = var5.getPublicExponent();
            if(var6 != null) {
               BigInteger var7 = var5.getModulus();
               BigInteger var8 = ONE;
               BigInteger var9 = ONE;
               BigInteger var10 = var7.subtract(var9);
               SecureRandom var11 = this.random;
               BigInteger var12 = BigIntegers.createRandomInRange(var8, var10, var11);
               BigInteger var13 = var12.modPow(var6, var7).multiply(var4).mod(var7);
               BigInteger var14 = this.core.processBlock(var13);
               BigInteger var15 = var12.modInverse(var7);
               var16 = var14.multiply(var15).mod(var7);
            } else {
               var16 = this.core.processBlock(var4);
            }
         } else {
            var16 = this.core.processBlock(var4);
         }

         return this.core.convertOutput(var16);
      }
   }
}
