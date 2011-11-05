package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;

public class BufferedAsymmetricBlockCipher {

   protected byte[] buf;
   protected int bufOff;
   private final AsymmetricBlockCipher cipher;


   public BufferedAsymmetricBlockCipher(AsymmetricBlockCipher var1) {
      this.cipher = var1;
   }

   public byte[] doFinal() throws InvalidCipherTextException {
      AsymmetricBlockCipher var1 = this.cipher;
      byte[] var2 = this.buf;
      int var3 = this.bufOff;
      byte[] var4 = var1.processBlock(var2, 0, var3);
      this.reset();
      return var4;
   }

   public int getBufferPosition() {
      return this.bufOff;
   }

   public int getInputBlockSize() {
      return this.cipher.getInputBlockSize();
   }

   public int getOutputBlockSize() {
      return this.cipher.getOutputBlockSize();
   }

   public AsymmetricBlockCipher getUnderlyingCipher() {
      return this.cipher;
   }

   public void init(boolean var1, CipherParameters var2) {
      this.reset();
      this.cipher.init(var1, var2);
      int var3 = this.cipher.getInputBlockSize();
      byte var4;
      if(var1) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      byte[] var5 = new byte[var3 + var4];
      this.buf = var5;
      this.bufOff = 0;
   }

   public void processByte(byte var1) {
      int var2 = this.bufOff;
      int var3 = this.buf.length;
      if(var2 >= var3) {
         throw new DataLengthException("attempt to process message too long for cipher");
      } else {
         byte[] var4 = this.buf;
         int var5 = this.bufOff;
         int var6 = var5 + 1;
         this.bufOff = var6;
         var4[var5] = var1;
      }
   }

   public void processBytes(byte[] var1, int var2, int var3) {
      if(var3 != 0) {
         if(var3 < 0) {
            throw new IllegalArgumentException("Can\'t have a negative input length!");
         } else {
            int var4 = this.bufOff + var3;
            int var5 = this.buf.length;
            if(var4 > var5) {
               throw new DataLengthException("attempt to process message too long for cipher");
            } else {
               byte[] var6 = this.buf;
               int var7 = this.bufOff;
               System.arraycopy(var1, var2, var6, var7, var3);
               int var8 = this.bufOff + var3;
               this.bufOff = var8;
            }
         }
      }
   }

   public void reset() {
      if(this.buf != null) {
         int var1 = 0;

         while(true) {
            int var2 = this.buf.length;
            if(var1 >= var2) {
               break;
            }

            this.buf[var1] = 0;
            ++var1;
         }
      }

      this.bufOff = 0;
   }
}
