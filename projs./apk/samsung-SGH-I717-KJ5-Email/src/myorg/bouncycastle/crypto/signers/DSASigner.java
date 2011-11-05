package myorg.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DSA;
import myorg.bouncycastle.crypto.params.DSAKeyParameters;
import myorg.bouncycastle.crypto.params.DSAParameters;
import myorg.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.DSAPublicKeyParameters;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;

public class DSASigner implements DSA {

   DSAKeyParameters key;
   SecureRandom random;


   public DSASigner() {}

   private BigInteger calculateE(BigInteger var1, byte[] var2) {
      int var3 = var1.bitLength();
      int var4 = var2.length * 8;
      BigInteger var5;
      if(var3 >= var4) {
         var5 = new BigInteger(1, var2);
      } else {
         byte[] var6 = new byte[var1.bitLength() / 8];
         int var7 = var6.length;
         System.arraycopy(var2, 0, var6, 0, var7);
         var5 = new BigInteger(1, var6);
      }

      return var5;
   }

   public BigInteger[] generateSignature(byte[] var1) {
      DSAParameters var2 = this.key.getParameters();
      BigInteger var3 = var2.getQ();
      BigInteger var4 = this.calculateE(var3, var1);
      int var5 = var2.getQ().bitLength();

      BigInteger var7;
      BigInteger var8;
      do {
         SecureRandom var6 = this.random;
         var7 = new BigInteger(var5, var6);
         var8 = var2.getQ();
      } while(var7.compareTo(var8) >= 0);

      BigInteger var9 = var2.getG();
      BigInteger var10 = var2.getP();
      BigInteger var11 = var9.modPow(var7, var10);
      BigInteger var12 = var2.getQ();
      BigInteger var13 = var11.mod(var12);
      BigInteger var14 = var2.getQ();
      BigInteger var15 = var7.modInverse(var14);
      BigInteger var16 = ((DSAPrivateKeyParameters)this.key).getX().multiply(var13);
      BigInteger var17 = var4.add(var16);
      BigInteger var18 = var15.multiply(var17);
      BigInteger var19 = var2.getQ();
      BigInteger var20 = var18.mod(var19);
      BigInteger[] var21 = new BigInteger[]{var13, var20};
      return var21;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var1) {
         if(var2 instanceof ParametersWithRandom) {
            ParametersWithRandom var3 = (ParametersWithRandom)var2;
            SecureRandom var4 = var3.getRandom();
            this.random = var4;
            DSAPrivateKeyParameters var5 = (DSAPrivateKeyParameters)var3.getParameters();
            this.key = var5;
         } else {
            SecureRandom var6 = new SecureRandom();
            this.random = var6;
            DSAPrivateKeyParameters var7 = (DSAPrivateKeyParameters)var2;
            this.key = var7;
         }
      } else {
         DSAPublicKeyParameters var8 = (DSAPublicKeyParameters)var2;
         this.key = var8;
      }
   }

   public boolean verifySignature(byte[] var1, BigInteger var2, BigInteger var3) {
      DSAParameters var4 = this.key.getParameters();
      BigInteger var5 = var4.getQ();
      BigInteger var6 = this.calculateE(var5, var1);
      BigInteger var7 = BigInteger.valueOf(0L);
      boolean var8;
      if(var7.compareTo(var2) < 0 && var4.getQ().compareTo(var2) > 0) {
         if(var7.compareTo(var3) < 0 && var4.getQ().compareTo(var3) > 0) {
            BigInteger var9 = var4.getQ();
            BigInteger var10 = var3.modInverse(var9);
            BigInteger var11 = var6.multiply(var10);
            BigInteger var12 = var4.getQ();
            BigInteger var13 = var11.mod(var12);
            BigInteger var14 = var2.multiply(var10);
            BigInteger var15 = var4.getQ();
            BigInteger var16 = var14.mod(var15);
            BigInteger var17 = var4.getG();
            BigInteger var18 = var4.getP();
            BigInteger var19 = var17.modPow(var13, var18);
            BigInteger var20 = ((DSAPublicKeyParameters)this.key).getY();
            BigInteger var21 = var4.getP();
            BigInteger var22 = var20.modPow(var16, var21);
            BigInteger var23 = var19.multiply(var22);
            BigInteger var24 = var4.getP();
            BigInteger var25 = var23.mod(var24);
            BigInteger var26 = var4.getQ();
            var8 = var25.mod(var26).equals(var2);
         } else {
            var8 = false;
         }
      } else {
         var8 = false;
      }

      return var8;
   }
}
