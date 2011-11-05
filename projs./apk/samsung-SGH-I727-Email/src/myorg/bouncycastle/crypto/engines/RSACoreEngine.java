package myorg.bouncycastle.crypto.engines;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

class RSACoreEngine {

   private boolean forEncryption;
   private RSAKeyParameters key;


   RSACoreEngine() {}

   public BigInteger convertInput(byte[] var1, int var2, int var3) {
      int var4 = this.getInputBlockSize() + 1;
      if(var3 > var4) {
         throw new DataLengthException("input too large for RSA cipher.");
      } else {
         int var5 = this.getInputBlockSize() + 1;
         if(var3 == var5 && !this.forEncryption) {
            throw new DataLengthException("input too large for RSA cipher.");
         } else {
            byte[] var7;
            label23: {
               if(var2 == 0) {
                  int var6 = var1.length;
                  if(var3 == var6) {
                     var7 = var1;
                     break label23;
                  }
               }

               var7 = new byte[var3];
               System.arraycopy(var1, var2, var7, 0, var3);
            }

            BigInteger var8 = new BigInteger(1, var7);
            BigInteger var9 = this.key.getModulus();
            if(var8.compareTo(var9) >= 0) {
               throw new DataLengthException("input too large for RSA cipher.");
            } else {
               return var8;
            }
         }
      }
   }

   public byte[] convertOutput(BigInteger var1) {
      byte[] var2 = var1.toByteArray();
      byte[] var7;
      if(this.forEncryption) {
         if(var2[0] == 0) {
            int var3 = var2.length;
            int var4 = this.getOutputBlockSize();
            if(var3 > var4) {
               byte[] var5 = new byte[var2.length - 1];
               int var6 = var5.length;
               System.arraycopy(var2, 1, var5, 0, var6);
               var7 = var5;
               return var7;
            }
         }

         int var8 = var2.length;
         int var9 = this.getOutputBlockSize();
         if(var8 < var9) {
            byte[] var10 = new byte[this.getOutputBlockSize()];
            int var11 = var10.length;
            int var12 = var2.length;
            int var13 = var11 - var12;
            int var14 = var2.length;
            System.arraycopy(var2, 0, var10, var13, var14);
            var7 = var10;
            return var7;
         }
      } else if(var2[0] == 0) {
         byte[] var15 = new byte[var2.length - 1];
         int var16 = var15.length;
         System.arraycopy(var2, 1, var15, 0, var16);
         var7 = var15;
         return var7;
      }

      var7 = var2;
      return var7;
   }

   public int getInputBlockSize() {
      int var1 = this.key.getModulus().bitLength();
      int var2;
      if(this.forEncryption) {
         var2 = (var1 + 7) / 8 - 1;
      } else {
         var2 = (var1 + 7) / 8;
      }

      return var2;
   }

   public int getOutputBlockSize() {
      int var1 = this.key.getModulus().bitLength();
      int var2;
      if(this.forEncryption) {
         var2 = (var1 + 7) / 8;
      } else {
         var2 = (var1 + 7) / 8 - 1;
      }

      return var2;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof ParametersWithRandom) {
         RSAKeyParameters var3 = (RSAKeyParameters)((ParametersWithRandom)var2).getParameters();
         this.key = var3;
      } else {
         RSAKeyParameters var4 = (RSAKeyParameters)var2;
         this.key = var4;
      }

      this.forEncryption = var1;
   }

   public BigInteger processBlock(BigInteger var1) {
      BigInteger var10;
      if(this.key instanceof RSAPrivateCrtKeyParameters) {
         RSAPrivateCrtKeyParameters var2 = (RSAPrivateCrtKeyParameters)this.key;
         BigInteger var3 = var2.getP();
         BigInteger var4 = var2.getQ();
         BigInteger var5 = var2.getDP();
         BigInteger var6 = var2.getDQ();
         BigInteger var7 = var2.getQInv();
         BigInteger var8 = var1.remainder(var3).modPow(var5, var3);
         BigInteger var9 = var1.remainder(var4).modPow(var6, var4);
         var10 = var8.subtract(var9).multiply(var7).mod(var3).multiply(var4).add(var9);
      } else {
         BigInteger var11 = this.key.getExponent();
         BigInteger var12 = this.key.getModulus();
         var10 = var1.modPow(var11, var12);
      }

      return var10;
   }
}
