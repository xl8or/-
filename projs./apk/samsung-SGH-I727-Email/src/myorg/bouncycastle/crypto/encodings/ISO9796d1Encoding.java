package myorg.bouncycastle.crypto.encodings;

import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;

public class ISO9796d1Encoding implements AsymmetricBlockCipher {

   private static byte[] inverse = new byte[]{(byte)8, (byte)15, (byte)6, (byte)1, (byte)5, (byte)2, (byte)11, (byte)12, (byte)3, (byte)4, (byte)13, (byte)10, (byte)14, (byte)9, (byte)0, (byte)7};
   private static byte[] shadows = new byte[]{(byte)14, (byte)3, (byte)5, (byte)8, (byte)9, (byte)4, (byte)2, (byte)15, (byte)0, (byte)13, (byte)11, (byte)6, (byte)7, (byte)10, (byte)12, (byte)1};
   private int bitSize;
   private AsymmetricBlockCipher engine;
   private boolean forEncryption;
   private int padBits = 0;


   public ISO9796d1Encoding(AsymmetricBlockCipher var1) {
      this.engine = var1;
   }

   private byte[] decodeBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      AsymmetricBlockCipher var4 = this.engine;
      byte[] var8 = var4.processBlock(var1, var2, var3);
      int var9 = 1;
      int var10 = (this.bitSize + 13) / 16;
      int var11 = var8.length - 1;
      if((var8[var11] & 15) != 6) {
         throw new InvalidCipherTextException("invalid forcing byte in block");
      } else {
         int var12 = var8.length - 1;
         int var13 = var8.length - 1;
         int var14 = (var8[var13] & 255) >>> 4;
         byte[] var15 = inverse;
         int var16 = var8.length - 2;
         int var17 = (var8[var16] & 255) >> 4;
         int var18 = var15[var17] << 4;
         byte var19 = (byte)(var14 | var18);
         var8[var12] = var19;
         byte[] var20 = shadows;
         int var21 = (var8[1] & 255) >>> 4;
         int var22 = var20[var21] << 4;
         byte[] var23 = shadows;
         int var24 = var8[1] & 15;
         byte var25 = var23[var24];
         byte var26 = (byte)(var22 | var25);
         var8[0] = var26;
         boolean var27 = false;
         int var28 = 0;
         int var29 = var8.length - 1;

         while(true) {
            int var30 = var8.length;
            int var31 = var10 * 2;
            int var32 = var30 - var31;
            if(var29 < var32) {
               var8[var28] = 0;
               byte[] var42 = new byte[(var8.length - var28) / 2];
               int var43 = 0;

               while(true) {
                  int var44 = var42.length;
                  if(var43 >= var44) {
                     int var47 = var9 - 1;
                     this.padBits = var47;
                     return var42;
                  }

                  int var45 = var43 * 2 + var28 + 1;
                  byte var46 = var8[var45];
                  var42[var43] = var46;
                  ++var43;
               }
            }

            byte[] var33 = shadows;
            int var34 = (var8[var29] & 255) >>> 4;
            int var35 = var33[var34] << 4;
            byte[] var36 = shadows;
            int var37 = var8[var29] & 15;
            byte var38 = var36[var37];
            int var39 = var35 | var38;
            int var40 = var29 - 1;
            if(((var8[var40] ^ var39) & 255) != 0) {
               if(var27) {
                  throw new InvalidCipherTextException("invalid tsums in block");
               }

               int var41 = var29 - 1;
               var9 = (var8[var41] ^ var39) & 255;
               var28 = var29 - 1;
            }

            var29 += -2;
         }
      }
   }

   private byte[] encodeBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      byte[] var4 = new byte[(this.bitSize + 7) / 8];
      int var5 = this.padBits + 1;
      int var6 = var3;
      int var7 = (this.bitSize + 13) / 16;

      for(int var8 = 0; var8 < var7; var8 += var6) {
         int var9 = var7 - var6;
         if(var8 > var9) {
            int var10 = var2 + var3;
            int var11 = var7 - var8;
            int var12 = var10 - var11;
            int var13 = var4.length - var7;
            int var14 = var7 - var8;
            System.arraycopy(var1, var12, var4, var13, var14);
         } else {
            int var15 = var4.length;
            int var16 = var8 + var6;
            int var17 = var15 - var16;
            System.arraycopy(var1, var2, var4, var17, var6);
         }
      }

      int var18 = var4.length;
      int var19 = var7 * 2;
      int var20 = var18 - var19;

      while(true) {
         int var21 = var4.length;
         if(var20 == var21) {
            int var34 = var4.length;
            int var35 = var6 * 2;
            int var36 = var34 - var35;
            byte var37 = (byte)(var4[var36] ^ var5);
            var4[var36] = var37;
            int var38 = var4.length - 1;
            int var39 = var4.length - 1;
            byte var40 = (byte)(var4[var39] << 4 | 6);
            var4[var38] = var40;
            int var41 = (this.bitSize - 1) % 8;
            int var42 = 8 - var41;
            byte var43 = 0;
            if(var42 != 8) {
               byte var44 = var4[0];
               int var45 = 255 >>> var42;
               byte var46 = (byte)(var44 & var45);
               var4[0] = var46;
               byte var47 = var4[0];
               int var48 = 128 >>> var42;
               byte var49 = (byte)(var47 | var48);
               var4[0] = var49;
            } else {
               var4[0] = 0;
               byte var52 = (byte)(var4[1] | 128);
               var4[1] = var52;
               var43 = 1;
            }

            AsymmetricBlockCipher var50 = this.engine;
            int var51 = var4.length - var43;
            return var50.processBlock(var4, var43, var51);
         }

         int var22 = var4.length - var7;
         int var23 = var20 / 2;
         int var24 = var22 + var23;
         byte var25 = var4[var24];
         byte[] var26 = shadows;
         int var27 = (var25 & 255) >>> 4;
         int var28 = var26[var27] << 4;
         byte[] var29 = shadows;
         int var30 = var25 & 15;
         byte var31 = var29[var30];
         byte var32 = (byte)(var28 | var31);
         var4[var20] = var32;
         int var33 = var20 + 1;
         var4[var33] = var25;
         var20 += 2;
      }
   }

   public int getInputBlockSize() {
      int var1 = this.engine.getInputBlockSize();
      int var2;
      if(this.forEncryption) {
         var2 = (var1 + 1) / 2;
      } else {
         var2 = var1;
      }

      return var2;
   }

   public int getOutputBlockSize() {
      int var1 = this.engine.getOutputBlockSize();
      int var2;
      if(this.forEncryption) {
         var2 = var1;
      } else {
         var2 = (var1 + 1) / 2;
      }

      return var2;
   }

   public int getPadBits() {
      return this.padBits;
   }

   public AsymmetricBlockCipher getUnderlyingCipher() {
      return this.engine;
   }

   public void init(boolean var1, CipherParameters var2) {
      RSAKeyParameters var3;
      if(var2 instanceof ParametersWithRandom) {
         var3 = (RSAKeyParameters)((ParametersWithRandom)var2).getParameters();
      } else {
         var3 = (RSAKeyParameters)var2;
      }

      this.engine.init(var1, var2);
      int var4 = var3.getModulus().bitLength();
      this.bitSize = var4;
      this.forEncryption = var1;
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      byte[] var4;
      if(this.forEncryption) {
         var4 = this.encodeBlock(var1, var2, var3);
      } else {
         var4 = this.decodeBlock(var1, var2, var3);
      }

      return var4;
   }

   public void setPadBits(int var1) {
      if(var1 > 7) {
         throw new IllegalArgumentException("padBits > 7");
      } else {
         this.padBits = var1;
      }
   }
}
