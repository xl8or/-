package myorg.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DSA;
import myorg.bouncycastle.crypto.params.GOST3410KeyParameters;
import myorg.bouncycastle.crypto.params.GOST3410Parameters;
import myorg.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import myorg.bouncycastle.crypto.params.GOST3410PublicKeyParameters;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;

public class GOST3410Signer implements DSA {

   GOST3410KeyParameters key;
   SecureRandom random;


   public GOST3410Signer() {}

   public BigInteger[] generateSignature(byte[] var1) {
      byte[] var2 = new byte[var1.length];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            BigInteger var7 = new BigInteger(1, var2);
            GOST3410Parameters var8 = this.key.getParameters();

            BigInteger var11;
            BigInteger var12;
            do {
               int var9 = var8.getQ().bitLength();
               SecureRandom var10 = this.random;
               var11 = new BigInteger(var9, var10);
               var12 = var8.getQ();
            } while(var11.compareTo(var12) >= 0);

            BigInteger var13 = var8.getA();
            BigInteger var14 = var8.getP();
            BigInteger var15 = var13.modPow(var11, var14);
            BigInteger var16 = var8.getQ();
            BigInteger var17 = var15.mod(var16);
            BigInteger var18 = var11.multiply(var7);
            BigInteger var19 = ((GOST3410PrivateKeyParameters)this.key).getX().multiply(var17);
            BigInteger var20 = var18.add(var19);
            BigInteger var21 = var8.getQ();
            BigInteger var22 = var20.mod(var21);
            BigInteger[] var23 = new BigInteger[]{var17, var22};
            return var23;
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
            GOST3410PrivateKeyParameters var5 = (GOST3410PrivateKeyParameters)var3.getParameters();
            this.key = var5;
         } else {
            SecureRandom var6 = new SecureRandom();
            this.random = var6;
            GOST3410PrivateKeyParameters var7 = (GOST3410PrivateKeyParameters)var2;
            this.key = var7;
         }
      } else {
         GOST3410PublicKeyParameters var8 = (GOST3410PublicKeyParameters)var2;
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
            GOST3410Parameters var10 = this.key.getParameters();
            BigInteger var11 = BigInteger.valueOf(0L);
            boolean var12;
            if(var11.compareTo(var2) < 0 && var10.getQ().compareTo(var2) > 0) {
               if(var11.compareTo(var3) < 0 && var10.getQ().compareTo(var3) > 0) {
                  BigInteger var13 = var10.getQ();
                  BigInteger var14 = new BigInteger("2");
                  BigInteger var15 = var13.subtract(var14);
                  BigInteger var16 = var10.getQ();
                  BigInteger var17 = var9.modPow(var15, var16);
                  BigInteger var18 = var3.multiply(var17);
                  BigInteger var19 = var10.getQ();
                  BigInteger var20 = var18.mod(var19);
                  BigInteger var21 = var10.getQ().subtract(var2).multiply(var17);
                  BigInteger var22 = var10.getQ();
                  BigInteger var23 = var21.mod(var22);
                  BigInteger var24 = var10.getA();
                  BigInteger var25 = var10.getP();
                  BigInteger var26 = var24.modPow(var20, var25);
                  BigInteger var27 = ((GOST3410PublicKeyParameters)this.key).getY();
                  BigInteger var28 = var10.getP();
                  BigInteger var29 = var27.modPow(var23, var28);
                  BigInteger var30 = var26.multiply(var29);
                  BigInteger var31 = var10.getP();
                  BigInteger var32 = var30.mod(var31);
                  BigInteger var33 = var10.getQ();
                  var12 = var32.mod(var33).equals(var2);
               } else {
                  var12 = false;
               }
            } else {
               var12 = false;
            }

            return var12;
         }

         int var7 = var4.length - 1 - var5;
         byte var8 = var1[var7];
         var4[var5] = var8;
         ++var5;
      }
   }
}
