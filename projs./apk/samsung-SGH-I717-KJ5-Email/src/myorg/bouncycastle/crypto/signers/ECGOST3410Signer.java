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

public class ECGOST3410Signer implements DSA {

   ECKeyParameters key;
   SecureRandom random;


   public ECGOST3410Signer() {}

   public BigInteger[] generateSignature(byte[] var1) {
      byte[] var2 = new byte[var1.length];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            BigInteger var7 = new BigInteger(1, var2);
            BigInteger var8 = this.key.getParameters().getN();

            while(true) {
               BigInteger var11;
               BigInteger var12;
               do {
                  int var9 = var8.bitLength();
                  SecureRandom var10 = this.random;
                  var11 = new BigInteger(var9, var10);
                  var12 = ECConstants.ZERO;
               } while(var11.equals(var12));

               BigInteger var13 = this.key.getParameters().getG().multiply(var11).getX().toBigInteger().mod(var8);
               BigInteger var14 = ECConstants.ZERO;
               if(!var13.equals(var14)) {
                  BigInteger var15 = ((ECPrivateKeyParameters)this.key).getD();
                  BigInteger var16 = var11.multiply(var7);
                  BigInteger var17 = var15.multiply(var13);
                  BigInteger var18 = var16.add(var17).mod(var8);
                  BigInteger var19 = ECConstants.ZERO;
                  if(!var18.equals(var19)) {
                     BigInteger[] var20 = new BigInteger[]{var13, var18};
                     return var20;
                  }
               }
            }
         }

         int var5 = var2.length - 1 - var3;
         byte var6 = var1[var5];
         var2[var3] = var6;
         ++var3;
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
      byte[] var4 = new byte[var1.length];
      int var5 = 0;

      while(true) {
         int var6 = var4.length;
         if(var5 == var6) {
            BigInteger var9 = new BigInteger(1, var4);
            BigInteger var10 = this.key.getParameters().getN();
            BigInteger var11 = ECConstants.ONE;
            boolean var16;
            if(var2.compareTo(var11) >= 0 && var2.compareTo(var10) < 0) {
               BigInteger var17 = ECConstants.ONE;
               if(var3.compareTo(var17) >= 0 && var3.compareTo(var10) < 0) {
                  BigInteger var22 = var9.modInverse(var10);
                  BigInteger var25 = var3.multiply(var22).mod(var10);
                  BigInteger var28 = var10.subtract(var2).multiply(var22).mod(var10);
                  ECPoint var29 = this.key.getParameters().getG();
                  ECPoint var30 = ((ECPublicKeyParameters)this.key).getQ();
                  BigInteger var31 = ECAlgorithms.sumOfTwoMultiplies(var29, var25, var30, var28).getX().toBigInteger().mod(var10);
                  var16 = var31.equals(var2);
               } else {
                  var16 = false;
               }
            } else {
               var16 = false;
            }

            return var16;
         }

         int var7 = var4.length - 1 - var5;
         byte var8 = var1[var7];
         var4[var5] = var8;
         ++var5;
      }
   }
}
