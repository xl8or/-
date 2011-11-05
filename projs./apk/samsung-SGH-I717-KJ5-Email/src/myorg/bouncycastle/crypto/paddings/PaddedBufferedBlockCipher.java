package myorg.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.BufferedBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.paddings.BlockCipherPadding;
import myorg.bouncycastle.crypto.paddings.PKCS7Padding;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;

public class PaddedBufferedBlockCipher extends BufferedBlockCipher {

   BlockCipherPadding padding;


   public PaddedBufferedBlockCipher(BlockCipher var1) {
      PKCS7Padding var2 = new PKCS7Padding();
      this(var1, var2);
   }

   public PaddedBufferedBlockCipher(BlockCipher var1, BlockCipherPadding var2) {
      this.cipher = var1;
      this.padding = var2;
      byte[] var3 = new byte[var1.getBlockSize()];
      this.buf = var3;
      this.bufOff = 0;
   }

   public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
      int var3 = this.cipher.getBlockSize();
      int var4 = 0;
      int var17;
      if(this.forEncryption) {
         if(this.bufOff == var3) {
            int var5 = var3 * 2 + var2;
            int var6 = var1.length;
            if(var5 > var6) {
               this.reset();
               throw new DataLengthException("output buffer too short");
            }

            BlockCipher var7 = this.cipher;
            byte[] var8 = this.buf;
            var4 = var7.processBlock(var8, 0, var1, var2);
            this.bufOff = 0;
         }

         BlockCipherPadding var9 = this.padding;
         byte[] var10 = this.buf;
         int var11 = this.bufOff;
         var9.addPadding(var10, var11);
         BlockCipher var13 = this.cipher;
         byte[] var14 = this.buf;
         int var15 = var2 + var4;
         int var16 = var13.processBlock(var14, 0, var1, var15);
         var17 = var4 + var16;
         this.reset();
      } else {
         if(this.bufOff != var3) {
            this.reset();
            throw new DataLengthException("last block incomplete in decryption");
         }

         BlockCipher var18 = this.cipher;
         byte[] var19 = this.buf;
         byte[] var20 = this.buf;
         int var21 = var18.processBlock(var19, 0, var20, 0);
         this.bufOff = 0;

         try {
            BlockCipherPadding var22 = this.padding;
            byte[] var23 = this.buf;
            int var24 = var22.padCount(var23);
            var17 = var21 - var24;
            System.arraycopy(this.buf, 0, var1, var2, var17);
         } finally {
            this.reset();
         }
      }

      return var17;
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

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.forEncryption = var1;
      this.reset();
      if(var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         BlockCipherPadding var4 = this.padding;
         SecureRandom var5 = var3.getRandom();
         var4.init(var5);
         BlockCipher var6 = this.cipher;
         CipherParameters var7 = var3.getParameters();
         var6.init(var1, var7);
      } else {
         this.padding.init((SecureRandom)null);
         this.cipher.init(var1, var2);
      }
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
