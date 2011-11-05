package myorg.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DSA;
import myorg.bouncycastle.crypto.params.ECKeyParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.math.ec.ECAlgorithms;
import myorg.bouncycastle.math.ec.ECConstants;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECDSASigner implements ECConstants, DSA {

   ECKeyParameters key;
   SecureRandom random;


   public ECDSASigner() {}

   private BigInteger calculateE(BigInteger var1, byte[] var2) {
      int var3 = var1.bitLength();
      int var4 = var2.length * 8;
      BigInteger var5;
      if(var3 > var4) {
         var5 = new BigInteger(1, var2);
      } else {
         int var6 = var2.length * 8;
         BigInteger var7 = new BigInteger(1, var2);
         int var8 = var1.bitLength();
         if(var6 - var8 > 0) {
            int var9 = var1.bitLength();
            int var10 = var6 - var9;
            var7 = var7.shiftRight(var10);
         }

         var5 = var7;
      }

      return var5;
   }

   public BigInteger[] generateSignature(byte[] var1) {
      BigInteger var2 = this.key.getParameters().getN();
      BigInteger var3 = this.calculateE(var2, var1);

      while(true) {
         int var4 = var2.bitLength();

         while(true) {
            SecureRandom var5 = this.random;
            BigInteger var6 = new BigInteger(var4, var5);
            BigInteger var7 = ZERO;
            if(!var6.equals(var7)) {
               BigInteger var8 = this.key.getParameters().getG().multiply(var6).getX().toBigInteger().mod(var2);
               BigInteger var9 = ZERO;
               if(!var8.equals(var9)) {
                  BigInteger var10 = ((ECPrivateKeyParameters)this.key).getD();
                  BigInteger var11 = var6.modInverse(var2);
                  BigInteger var12 = var10.multiply(var8);
                  BigInteger var13 = var3.add(var12);
                  BigInteger var14 = var11.multiply(var13).mod(var2);
                  BigInteger var15 = ZERO;
                  if(!var14.equals(var15)) {
                     BigInteger[] var16 = new BigInteger[]{var8, var14};
                     return var16;
                  }
                  break;
               }
            }
         }
      }
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var1) {
         if(var2 instanceof ParametersWithRandom) {
            ParametersWithRandom var3 = (ParametersWithRandom)var2;
            SecureRandom var4 = var3.getRandom();
            this.random = var4;
            ECPrivateKeyParameters var5 = (ECPrivateKeyParameters)var3.getParameters();
            this.key = var5;
         } else {
            SecureRandom var6 = new SecureRandom();
            this.random = var6;
            ECPrivateKeyParameters var7 = (ECPrivateKeyParameters)var2;
            this.key = var7;
         }
      } else {
         ECPublicKeyParameters var8 = (ECPublicKeyParameters)var2;
         this.key = var8;
      }
   }

   public boolean verifySignature(byte[] var1, BigInteger var2, BigInteger var3) {
      BigInteger var4 = this.key.getParameters().getN();
      BigInteger var5 = this.calculateE(var4, var1);
      BigInteger var6 = ONE;
      boolean var7;
      if(var2.compareTo(var6) >= 0 && var2.compareTo(var4) < 0) {
         BigInteger var8 = ONE;
         if(var3.compareTo(var8) >= 0 && var3.compareTo(var4) < 0) {
            BigInteger var9 = var3.modInverse(var4);
            BigInteger var10 = var5.multiply(var9).mod(var4);
            BigInteger var11 = var2.multiply(var9).mod(var4);
            ECPoint var12 = this.key.getParameters().getG();
            ECPoint var13 = ((ECPublicKeyParameters)this.key).getQ();
            var7 = ECAlgorithms.sumOfTwoMultiplies(var12, var10, var13, var11).getX().toBigInteger().mod(var4).equals(var2);
         } else {
            var7 = false;
         }
      } else {
         var7 = false;
      }

      return var7;
   }
}
