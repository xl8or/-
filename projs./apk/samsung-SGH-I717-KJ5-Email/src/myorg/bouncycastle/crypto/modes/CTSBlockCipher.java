package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.BufferedBlockCipher;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.crypto.modes.CFBBlockCipher;
import myorg.bouncycastle.crypto.modes.OFBBlockCipher;

public class CTSBlockCipher extends BufferedBlockCipher {

   private int blockSize;


   public CTSBlockCipher(BlockCipher var1) {
      if(!(var1 instanceof OFBBlockCipher) && !(var1 instanceof CFBBlockCipher)) {
         this.cipher = var1;
         int var2 = var1.getBlockSize();
         this.blockSize = var2;
         byte[] var3 = new byte[this.blockSize * 2];
         this.buf = var3;
         this.bufOff = 0;
      } else {
         throw new IllegalArgumentException("CTSBlockCipher can only accept ECB, or CBC ciphers");
      }
   }

   public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
      int var3 = this.bufOff + var2;
      int var4 = var1.length;
      if(var3 > var4) {
         throw new DataLengthException("output buffer to small in doFinal");
      } else {
         int var5 = this.cipher.getBlockSize();
         int var6 = this.bufOff - var5;
         byte[] var7 = new byte[var5];
         int var16;
         if(this.forEncryption) {
            BlockCipher var8 = this.cipher;
            byte[] var9 = this.buf;
            var8.processBlock(var9, 0, var7, 0);
            if(this.bufOff < var5) {
               throw new DataLengthException("need at least one block of input for CTS");
            }

            int var11 = this.bufOff;

            label43:
            while(true) {
               int var12 = this.buf.length;
               if(var11 == var12) {
                  var16 = var5;

                  while(true) {
                     int var17 = this.bufOff;
                     if(var16 == var17) {
                        if(this.cipher instanceof CBCBlockCipher) {
                           BlockCipher var23 = ((CBCBlockCipher)this.cipher).getUnderlyingCipher();
                           byte[] var24 = this.buf;
                           var23.processBlock(var24, var5, var1, var2);
                        } else {
                           BlockCipher var28 = this.cipher;
                           byte[] var29 = this.buf;
                           var28.processBlock(var29, var5, var1, var2);
                        }

                        int var26 = var2 + var5;
                        System.arraycopy(var7, 0, var1, var26, var6);
                        break label43;
                     }

                     byte[] var18 = this.buf;
                     byte var19 = var18[var16];
                     int var20 = var16 - var5;
                     byte var21 = var7[var20];
                     byte var22 = (byte)(var19 ^ var21);
                     var18[var16] = var22;
                     ++var16;
                  }
               }

               byte[] var13 = this.buf;
               int var14 = var11 - var5;
               byte var15 = var7[var14];
               var13[var11] = var15;
               ++var11;
            }
         } else {
            byte[] var31 = new byte[var5];
            if(this.cipher instanceof CBCBlockCipher) {
               BlockCipher var32 = ((CBCBlockCipher)this.cipher).getUnderlyingCipher();
               byte[] var33 = this.buf;
               var32.processBlock(var33, 0, var7, 0);
            } else {
               BlockCipher var42 = this.cipher;
               byte[] var43 = this.buf;
               var42.processBlock(var43, 0, var7, 0);
            }

            var16 = var5;

            while(true) {
               int var35 = this.bufOff;
               if(var16 == var35) {
                  System.arraycopy(this.buf, var5, var7, 0, var6);
                  this.cipher.processBlock(var7, 0, var1, var2);
                  int var46 = var2 + var5;
                  System.arraycopy(var31, 0, var1, var46, var6);
                  break;
               }

               int var36 = var16 - var5;
               int var37 = var16 - var5;
               byte var38 = var7[var37];
               byte var39 = this.buf[var16];
               byte var40 = (byte)(var38 ^ var39);
               var31[var36] = var40;
               int var41 = var16 + 1;
            }
         }

         int var27 = this.bufOff;
         this.reset();
         return var27;
      }
   }

   public int getOutputSize(int var1) {
      return this.bufOff + var1;
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
         byte[] var9 = this.buf;
         int var10 = this.blockSize;
         byte[] var11 = this.buf;
         int var12 = this.blockSize;
         System.arraycopy(var9, var10, var11, 0, var12);
         int var13 = this.blockSize;
         this.bufOff = var13;
      }

      byte[] var14 = this.buf;
      int var15 = this.bufOff;
      int var16 = var15 + 1;
      this.bufOff = var16;
      var14[var15] = var1;
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
            byte[] var19 = this.buf;
            byte[] var20 = this.buf;
            System.arraycopy(var19, var6, var20, 0, var6);
            this.bufOff = var6;
            var3 -= var13;

            for(var2 += var13; var3 > var6; var2 += var6) {
               byte[] var21 = this.buf;
               int var22 = this.bufOff;
               System.arraycopy(var1, var2, var21, var22, var6);
               BlockCipher var23 = this.cipher;
               byte[] var24 = this.buf;
               int var25 = var5 + var10;
               int var26 = var23.processBlock(var24, 0, var4, var25);
               var10 += var26;
               byte[] var27 = this.buf;
               byte[] var28 = this.buf;
               System.arraycopy(var27, var6, var28, 0, var6);
               var3 -= var6;
            }
         }

         byte[] var29 = this.buf;
         int var30 = this.bufOff;
         System.arraycopy(var1, var2, var29, var30, var3);
         int var31 = this.bufOff + var3;
         this.bufOff = var31;
         return var10;
      }
   }
}
