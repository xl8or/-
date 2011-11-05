package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class GOFBBlockCipher implements BlockCipher {

   static final int C1 = 16843012;
   static final int C2 = 16843009;
   private byte[] IV;
   int N3;
   int N4;
   private final int blockSize;
   private final BlockCipher cipher;
   boolean firstStep = 1;
   private byte[] ofbOutV;
   private byte[] ofbV;


   public GOFBBlockCipher(BlockCipher var1) {
      this.cipher = var1;
      int var2 = var1.getBlockSize();
      this.blockSize = var2;
      if(this.blockSize != 8) {
         throw new IllegalArgumentException("GCTR only for 64 bit block ciphers");
      } else {
         byte[] var3 = new byte[var1.getBlockSize()];
         this.IV = var3;
         byte[] var4 = new byte[var1.getBlockSize()];
         this.ofbV = var4;
         byte[] var5 = new byte[var1.getBlockSize()];
         this.ofbOutV = var5;
      }
   }

   private int bytesToint(byte[] var1, int var2) {
      int var3 = var2 + 3;
      int var4 = var1[var3] << 24 & -16777216;
      int var5 = var2 + 2;
      int var6 = var1[var5] << 16 & 16711680;
      int var7 = var4 + var6;
      int var8 = var2 + 1;
      int var9 = var1[var8] << 8 & '\uff00';
      int var10 = var7 + var9;
      int var11 = var1[var2] & 255;
      return var10 + var11;
   }

   private void intTobytes(int var1, byte[] var2, int var3) {
      int var4 = var3 + 3;
      byte var5 = (byte)(var1 >>> 24);
      var2[var4] = var5;
      int var6 = var3 + 2;
      byte var7 = (byte)(var1 >>> 16);
      var2[var6] = var7;
      int var8 = var3 + 1;
      byte var9 = (byte)(var1 >>> 8);
      var2[var8] = var9;
      byte var10 = (byte)var1;
      var2[var3] = var10;
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.cipher.getAlgorithmName();
      return var1.append(var2).append("/GCTR").toString();
   }

   public int getBlockSize() {
      return this.blockSize;
   }

   public BlockCipher getUnderlyingCipher() {
      return this.cipher;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.firstStep = (boolean)1;
      this.N3 = 0;
      this.N4 = 0;
      if(!(var2 instanceof ParametersWithIV)) {
         this.reset();
         this.cipher.init((boolean)1, var2);
      } else {
         ParametersWithIV var3 = (ParametersWithIV)var2;
         byte[] var4 = var3.getIV();
         int var5 = var4.length;
         int var6 = this.IV.length;
         if(var5 < var6) {
            byte[] var7 = this.IV;
            int var8 = this.IV.length;
            int var9 = var4.length;
            int var10 = var8 - var9;
            int var11 = var4.length;
            System.arraycopy(var4, 0, var7, var10, var11);
            int var12 = 0;

            while(true) {
               int var13 = this.IV.length;
               int var14 = var4.length;
               int var15 = var13 - var14;
               if(var12 >= var15) {
                  break;
               }

               this.IV[var12] = 0;
               ++var12;
            }
         } else {
            byte[] var16 = this.IV;
            int var17 = this.IV.length;
            System.arraycopy(var4, 0, var16, 0, var17);
         }

         this.reset();
         BlockCipher var18 = this.cipher;
         CipherParameters var19 = var3.getParameters();
         var18.init((boolean)1, var19);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      int var5 = this.blockSize + var2;
      int var6 = var1.length;
      if(var5 > var6) {
         throw new DataLengthException("input buffer too short");
      } else {
         int var7 = this.blockSize + var4;
         int var8 = var3.length;
         if(var7 > var8) {
            throw new DataLengthException("output buffer too short");
         } else {
            if(this.firstStep) {
               this.firstStep = (boolean)0;
               BlockCipher var9 = this.cipher;
               byte[] var10 = this.ofbV;
               byte[] var11 = this.ofbOutV;
               var9.processBlock(var10, 0, var11, 0);
               byte[] var13 = this.ofbOutV;
               int var14 = this.bytesToint(var13, 0);
               this.N3 = var14;
               byte[] var15 = this.ofbOutV;
               int var16 = this.bytesToint(var15, 4);
               this.N4 = var16;
            }

            int var17 = this.N3 + 16843009;
            this.N3 = var17;
            int var18 = this.N4 + 16843012;
            this.N4 = var18;
            int var19 = this.N3;
            byte[] var20 = this.ofbV;
            this.intTobytes(var19, var20, 0);
            int var21 = this.N4;
            byte[] var22 = this.ofbV;
            this.intTobytes(var21, var22, 4);
            BlockCipher var23 = this.cipher;
            byte[] var24 = this.ofbV;
            byte[] var25 = this.ofbOutV;
            var23.processBlock(var24, 0, var25, 0);
            int var27 = 0;

            while(true) {
               int var28 = this.blockSize;
               if(var27 >= var28) {
                  byte[] var34 = this.ofbV;
                  int var35 = this.blockSize;
                  byte[] var36 = this.ofbV;
                  int var37 = this.ofbV.length;
                  int var38 = this.blockSize;
                  int var39 = var37 - var38;
                  System.arraycopy(var34, var35, var36, 0, var39);
                  byte[] var40 = this.ofbOutV;
                  byte[] var41 = this.ofbV;
                  int var42 = this.ofbV.length;
                  int var43 = this.blockSize;
                  int var44 = var42 - var43;
                  int var45 = this.blockSize;
                  System.arraycopy(var40, 0, var41, var44, var45);
                  return this.blockSize;
               }

               int var29 = var4 + var27;
               byte var30 = this.ofbOutV[var27];
               int var31 = var2 + var27;
               byte var32 = var1[var31];
               byte var33 = (byte)(var30 ^ var32);
               var3[var29] = var33;
               ++var27;
            }
         }
      }
   }

   public void reset() {
      byte[] var1 = this.IV;
      byte[] var2 = this.ofbV;
      int var3 = this.IV.length;
      System.arraycopy(var1, 0, var2, 0, var3);
      this.cipher.reset();
   }
}
