package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.util.Arrays;

public class CBCBlockCipher implements BlockCipher {

   private byte[] IV;
   private int blockSize;
   private byte[] cbcNextV;
   private byte[] cbcV;
   private BlockCipher cipher = null;
   private boolean encrypting;


   public CBCBlockCipher(BlockCipher var1) {
      this.cipher = var1;
      int var2 = var1.getBlockSize();
      this.blockSize = var2;
      byte[] var3 = new byte[this.blockSize];
      this.IV = var3;
      byte[] var4 = new byte[this.blockSize];
      this.cbcV = var4;
      byte[] var5 = new byte[this.blockSize];
      this.cbcNextV = var5;
   }

   private int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      int var5 = this.blockSize + var2;
      int var6 = var1.length;
      if(var5 > var6) {
         throw new DataLengthException("input buffer too short");
      } else {
         byte[] var7 = this.cbcNextV;
         int var8 = this.blockSize;
         System.arraycopy(var1, var2, var7, 0, var8);
         int var9 = this.cipher.processBlock(var1, var2, var3, var4);
         int var10 = 0;

         while(true) {
            int var11 = this.blockSize;
            if(var10 >= var11) {
               byte[] var16 = this.cbcV;
               byte[] var17 = this.cbcNextV;
               this.cbcV = var17;
               this.cbcNextV = var16;
               return var9;
            }

            int var12 = var4 + var10;
            byte var13 = var3[var12];
            byte var14 = this.cbcV[var10];
            byte var15 = (byte)(var13 ^ var14);
            var3[var12] = var15;
            ++var10;
         }
      }
   }

   private int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      int var5 = this.blockSize + var2;
      int var6 = var1.length;
      if(var5 > var6) {
         throw new DataLengthException("input buffer too short");
      } else {
         int var7 = 0;

         while(true) {
            int var8 = this.blockSize;
            if(var7 >= var8) {
               BlockCipher var14 = this.cipher;
               byte[] var15 = this.cbcV;
               int var16 = var14.processBlock(var15, 0, var3, var4);
               byte[] var17 = this.cbcV;
               int var18 = this.cbcV.length;
               System.arraycopy(var3, var4, var17, 0, var18);
               return var16;
            }

            byte[] var9 = this.cbcV;
            byte var10 = var9[var7];
            int var11 = var2 + var7;
            byte var12 = var1[var11];
            byte var13 = (byte)(var10 ^ var12);
            var9[var7] = var13;
            ++var7;
         }
      }
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.cipher.getAlgorithmName();
      return var1.append(var2).append("/CBC").toString();
   }

   public int getBlockSize() {
      return this.cipher.getBlockSize();
   }

   public BlockCipher getUnderlyingCipher() {
      return this.cipher;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.encrypting = var1;
      if(var2 instanceof ParametersWithIV) {
         ParametersWithIV var3 = (ParametersWithIV)var2;
         byte[] var4 = var3.getIV();
         int var5 = var4.length;
         int var6 = this.blockSize;
         if(var5 != var6) {
            throw new IllegalArgumentException("initialisation vector must be the same length as block size");
         } else {
            byte[] var7 = this.IV;
            int var8 = var4.length;
            System.arraycopy(var4, 0, var7, 0, var8);
            this.reset();
            BlockCipher var9 = this.cipher;
            CipherParameters var10 = var3.getParameters();
            var9.init(var1, var10);
         }
      } else {
         this.reset();
         this.cipher.init(var1, var2);
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
      byte[] var2 = this.cbcV;
      int var3 = this.IV.length;
      System.arraycopy(var1, 0, var2, 0, var3);
      Arrays.fill(this.cbcNextV, (byte)0);
      this.cipher.reset();
   }
}
