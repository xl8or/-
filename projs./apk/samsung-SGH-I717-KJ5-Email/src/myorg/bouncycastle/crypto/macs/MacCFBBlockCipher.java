package myorg.bouncycastle.crypto.macs;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

class MacCFBBlockCipher {

   private byte[] IV;
   private int blockSize;
   private byte[] cfbOutV;
   private byte[] cfbV;
   private BlockCipher cipher = null;


   public MacCFBBlockCipher(BlockCipher var1, int var2) {
      this.cipher = var1;
      int var3 = var2 / 8;
      this.blockSize = var3;
      byte[] var4 = new byte[var1.getBlockSize()];
      this.IV = var4;
      byte[] var5 = new byte[var1.getBlockSize()];
      this.cfbV = var5;
      byte[] var6 = new byte[var1.getBlockSize()];
      this.cfbOutV = var6;
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.cipher.getAlgorithmName();
      StringBuilder var3 = var1.append(var2).append("/CFB");
      int var4 = this.blockSize * 8;
      return var3.append(var4).toString();
   }

   public int getBlockSize() {
      return this.blockSize;
   }

   void getMacBlock(byte[] var1) {
      BlockCipher var2 = this.cipher;
      byte[] var3 = this.cfbV;
      var2.processBlock(var3, 0, var1, 0);
   }

   public void init(CipherParameters var1) throws IllegalArgumentException {
      if(var1 instanceof ParametersWithIV) {
         ParametersWithIV var2 = (ParametersWithIV)var1;
         byte[] var3 = var2.getIV();
         int var4 = var3.length;
         int var5 = this.IV.length;
         if(var4 < var5) {
            byte[] var6 = this.IV;
            int var7 = this.IV.length;
            int var8 = var3.length;
            int var9 = var7 - var8;
            int var10 = var3.length;
            System.arraycopy(var3, 0, var6, var9, var10);
         } else {
            byte[] var13 = this.IV;
            int var14 = this.IV.length;
            System.arraycopy(var3, 0, var13, 0, var14);
         }

         this.reset();
         BlockCipher var11 = this.cipher;
         CipherParameters var12 = var2.getParameters();
         var11.init((boolean)1, var12);
      } else {
         this.reset();
         this.cipher.init((boolean)1, var1);
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
            BlockCipher var9 = this.cipher;
            byte[] var10 = this.cfbV;
            byte[] var11 = this.cfbOutV;
            var9.processBlock(var10, 0, var11, 0);
            int var13 = 0;

            while(true) {
               int var14 = this.blockSize;
               if(var13 >= var14) {
                  byte[] var20 = this.cfbV;
                  int var21 = this.blockSize;
                  byte[] var22 = this.cfbV;
                  int var23 = this.cfbV.length;
                  int var24 = this.blockSize;
                  int var25 = var23 - var24;
                  System.arraycopy(var20, var21, var22, 0, var25);
                  byte[] var26 = this.cfbV;
                  int var27 = this.cfbV.length;
                  int var28 = this.blockSize;
                  int var29 = var27 - var28;
                  int var30 = this.blockSize;
                  System.arraycopy(var3, var4, var26, var29, var30);
                  return this.blockSize;
               }

               int var15 = var4 + var13;
               byte var16 = this.cfbOutV[var13];
               int var17 = var2 + var13;
               byte var18 = var1[var17];
               byte var19 = (byte)(var16 ^ var18);
               var3[var15] = var19;
               ++var13;
            }
         }
      }
   }

   public void reset() {
      byte[] var1 = this.IV;
      byte[] var2 = this.cfbV;
      int var3 = this.IV.length;
      System.arraycopy(var1, 0, var2, 0, var3);
      this.cipher.reset();
   }
}
