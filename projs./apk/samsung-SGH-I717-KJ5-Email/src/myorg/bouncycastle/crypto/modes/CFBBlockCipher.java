package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class CFBBlockCipher implements BlockCipher {

   private byte[] IV;
   private int blockSize;
   private byte[] cfbOutV;
   private byte[] cfbV;
   private BlockCipher cipher = null;
   private boolean encrypting;


   public CFBBlockCipher(BlockCipher var1, int var2) {
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

   public int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
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
            byte[] var13 = this.cfbV;
            int var14 = this.blockSize;
            byte[] var15 = this.cfbV;
            int var16 = this.cfbV.length;
            int var17 = this.blockSize;
            int var18 = var16 - var17;
            System.arraycopy(var13, var14, var15, 0, var18);
            byte[] var19 = this.cfbV;
            int var20 = this.cfbV.length;
            int var21 = this.blockSize;
            int var22 = var20 - var21;
            int var23 = this.blockSize;
            System.arraycopy(var1, var2, var19, var22, var23);
            int var24 = 0;

            while(true) {
               int var25 = this.blockSize;
               if(var24 >= var25) {
                  return this.blockSize;
               }

               int var26 = var4 + var24;
               byte var27 = this.cfbOutV[var24];
               int var28 = var2 + var24;
               byte var29 = var1[var28];
               byte var30 = (byte)(var27 ^ var29);
               var3[var26] = var30;
               ++var24;
            }
         }
      }
   }

   public int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
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

   public BlockCipher getUnderlyingCipher() {
      return this.cipher;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.encrypting = var1;
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
      int var5;
      if(this.encrypting) {
         var5 = this.encryptBlock(var1, var2, var3, var4);
      } else {
         var5 = this.decryptBlock(var1, var2, var3, var4);
      }

      return var5;
   }

   public void reset() {
      byte[] var1 = this.IV;
      byte[] var2 = this.cfbV;
      int var3 = this.IV.length;
      System.arraycopy(var1, 0, var2, 0, var3);
      this.cipher.reset();
   }
}
