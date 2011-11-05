package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;

public class BufferedBlockCipher {

   protected byte[] buf;
   protected int bufOff;
   protected BlockCipher cipher;
   protected boolean forEncryption;
   protected boolean partialBlockOkay;
   protected boolean pgpCFB;


   protected BufferedBlockCipher() {}

   public BufferedBlockCipher(BlockCipher var1) {
      this.cipher = var1;
      byte[] var2 = new byte[var1.getBlockSize()];
      this.buf = var2;
      this.bufOff = 0;
      String var3 = var1.getAlgorithmName();
      int var4 = var3.indexOf(47) + 1;
      byte var5;
      if(var4 > 0 && var3.startsWith("PGP", var4)) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      this.pgpCFB = (boolean)var5;
      if(this.pgpCFB) {
         this.partialBlockOkay = (boolean)1;
      } else {
         byte var6;
         if(var4 > 0 && (var3.startsWith("CFB", var4) || var3.startsWith("OFB", var4) || var3.startsWith("OpenPGP", var4) || var3.startsWith("SIC", var4) || var3.startsWith("GCTR", var4))) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         this.partialBlockOkay = (boolean)var6;
      }
   }

   public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
      int var3 = 0;
      int var4 = this.bufOff + var2;
      int var5 = var1.length;
      if(var4 > var5) {
         throw new DataLengthException("output buffer too short for doFinal()");
      } else {
         if(this.bufOff != 0 && this.partialBlockOkay) {
            BlockCipher var6 = this.cipher;
            byte[] var7 = this.buf;
            byte[] var8 = this.buf;
            var6.processBlock(var7, 0, var8, 0);
            var3 = this.bufOff;
            this.bufOff = 0;
            System.arraycopy(this.buf, 0, var1, var2, var3);
         } else if(this.bufOff != 0) {
            throw new DataLengthException("data not block size aligned");
         }

         this.reset();
         return var3;
      }
   }

   public int getBlockSize() {
      return this.cipher.getBlockSize();
   }

   public int getOutputSize(int var1) {
      return this.bufOff + var1;
   }

   public BlockCipher getUnderlyingCipher() {
      return this.cipher;
   }

   public int getUpdateOutputSize(int var1) {
      int var2 = this.bufOff;
      int var3 = var1 + var2;
      int var7;
      if(this.pgpCFB) {
         int var4 = this.buf.length;
         int var5 = var3 % var4;
         int var6 = this.cipher.getBlockSize() + 2;
         var7 = var5 - var6;
      } else {
         int var8 = this.buf.length;
         var7 = var3 % var8;
      }

      return var3 - var7;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.forEncryption = var1;
      this.reset();
      this.cipher.init(var1, var2);
   }

   public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException, IllegalStateException {
      int var4 = 0;
      byte[] var5 = this.buf;
      int var6 = this.bufOff;
      int var7 = var6 + 1;
      this.bufOff = var7;
      var5[var6] = var1;
      int var8 = this.bufOff;
      int var9 = this.buf.length;
      if(var8 == var9) {
         BlockCipher var10 = this.cipher;
         byte[] var11 = this.buf;
         var4 = var10.processBlock(var11, 0, var2, var3);
         this.bufOff = 0;
      }

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
         int var26 = this.bufOff;
         int var27 = this.buf.length;
         if(var26 == var27) {
            BlockCipher var28 = this.cipher;
            byte[] var29 = this.buf;
            int var30 = var5 + var10;
            int var31 = var28.processBlock(var29, 0, var4, var30);
            var10 += var31;
            this.bufOff = 0;
         }

         return var10;
      }
   }

   public void reset() {
      int var1 = 0;

      while(true) {
         int var2 = this.buf.length;
         if(var1 >= var2) {
            this.bufOff = 0;
            this.cipher.reset();
            return;
         }

         this.buf[var1] = 0;
         ++var1;
      }
   }
}
