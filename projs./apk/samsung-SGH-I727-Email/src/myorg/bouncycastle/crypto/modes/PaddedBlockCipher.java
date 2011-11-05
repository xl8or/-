package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.BufferedBlockCipher;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;

public class PaddedBlockCipher extends BufferedBlockCipher {

   public PaddedBlockCipher(BlockCipher var1) {
      this.cipher = var1;
      byte[] var2 = new byte[var1.getBlockSize()];
      this.buf = var2;
      this.bufOff = 0;
   }

   public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
      int var3 = this.cipher.getBlockSize();
      int var4 = 0;
      int var18;
      if(this.forEncryption) {
         if(this.bufOff == var3) {
            int var5 = var3 * 2 + var2;
            int var6 = var1.length;
            if(var5 > var6) {
               throw new DataLengthException("output buffer too short");
            }

            BlockCipher var7 = this.cipher;
            byte[] var8 = this.buf;
            var4 = var7.processBlock(var8, 0, var1, var2);
            this.bufOff = 0;
         }

         int var9 = this.bufOff;

         int var13;
         for(byte var10 = (byte)(var3 - var9); this.bufOff < var3; this.bufOff = var13) {
            byte[] var11 = this.buf;
            int var12 = this.bufOff;
            var11[var12] = var10;
            var13 = this.bufOff + 1;
         }

         BlockCipher var14 = this.cipher;
         byte[] var15 = this.buf;
         int var16 = var2 + var4;
         int var17 = var14.processBlock(var15, 0, var1, var16);
         var18 = var4 + var17;
      } else {
         if(this.bufOff != var3) {
            throw new DataLengthException("last block incomplete in decryption");
         }

         BlockCipher var19 = this.cipher;
         byte[] var20 = this.buf;
         byte[] var21 = this.buf;
         int var22 = var19.processBlock(var20, 0, var21, 0);
         this.bufOff = 0;
         byte[] var23 = this.buf;
         int var24 = var3 - 1;
         int var25 = var23[var24] & 255;
         if(var25 < 0 || var25 > var3) {
            throw new InvalidCipherTextException("pad block corrupted");
         }

         var18 = var22 - var25;
         System.arraycopy(this.buf, 0, var1, var2, var18);
      }

      this.reset();
      return var18;
   }

   public int getOutputSize(int var1) {
      int var2 = this.bufOff;
      int var3 = var1 + var2;
      int var4 = this.buf.length;
      int var5 = var3 % var4;
      int var6;
      if(var5 == 0) {
         if(this.forEncryption) {
            var6 = this.buf.length + var3;
         } else {
            var6 = var3;
         }
      } else {
         int var7 = var3 - var5;
         int var8 = this.buf.length;
         var6 = var7 + var8;
      }

      return var6;
   }

   public int getUpdateOutputSize(int var1) {
      int var2 = this.bufOff;
      int var3 = var1 + var2;
      int var4 = this.buf.length;
      int var5 = var3 % var4;
      int var7;
      if(var5 == 0) {
         int var6 = this.buf.length;
         var7 = var3 - var6;
      } else {
         var7 = var3 - var5;
      }

      return var7;
   }

   public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException, IllegalStateException {
      int var4 = 0;
      int var5 = this.bufOff;
      int var6 = this.buf.length;
      if(var5 == var6) {
         BlockCipher var7 = this.cipher;
         byte[] var8 = this.buf;
         var4 = var7.processBlock(var8, 0, var2, var3);
         this.bufOff = 0;
      }

      byte[] var9 = this.buf;
      int var10 = this.bufOff;
      int var11 = var10 + 1;
      this.bufOff = var11;
      var9[var10] = var1;
      return var4;
   }

   public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException, IllegalStateException {
      if(var3 < 0) {
         throw new IllegalArgumentException("Can\'t have a negative input length!");
      } else {
         int var6 = this.getBlockSize();
         int var7 = this.getUpdateOutputSize(var3);
         if(var7 > 0) {
            int var8 = var5 + var7;
            int var9 = var4.length;
            if(var8 > var9) {
               throw new DataLengthException("output buffer too short");
            }
         }

         int var10 = 0;
         int var11 = this.buf.length;
         int var12 = this.bufOff;
         int var13 = var11 - var12;
         if(var3 > var13) {
            byte[] var14 = this.buf;
            int var15 = this.bufOff;
            System.arraycopy(var1, var2, var14, var15, var13);
            BlockCipher var16 = this.cipher;
            byte[] var17 = this.buf;
            int var18 = var16.processBlock(var17, 0, var4, var5);
            var10 += var18;
            this.bufOff = 0;
            var3 -= var13;
            var2 += var13;

            while(true) {
               int var19 = this.buf.length;
               if(var3 <= var19) {
                  break;
               }

               BlockCipher var20 = this.cipher;
               int var21 = var5 + var10;
               int var22 = var20.processBlock(var1, var2, var4, var21);
               var10 += var22;
               var3 -= var6;
               var2 += var6;
            }
         }

         byte[] var23 = this.buf;
         int var24 = this.bufOff;
         System.arraycopy(var1, var2, var23, var24, var3);
         int var25 = this.bufOff + var3;
         this.bufOff = var25;
         return var10;
      }
   }
}
