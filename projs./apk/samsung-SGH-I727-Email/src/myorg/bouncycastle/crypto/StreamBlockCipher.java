package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.StreamCipher;

public class StreamBlockCipher implements StreamCipher {

   private BlockCipher cipher;
   private byte[] oneByte;


   public StreamBlockCipher(BlockCipher var1) {
      byte[] var2 = new byte[1];
      this.oneByte = var2;
      if(var1.getBlockSize() != 1) {
         throw new IllegalArgumentException("block cipher block size != 1.");
      } else {
         this.cipher = var1;
      }
   }

   public String getAlgorithmName() {
      return this.cipher.getAlgorithmName();
   }

   public void init(boolean var1, CipherParameters var2) {
      this.cipher.init(var1, var2);
   }

   public void processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException {
      int var6 = var5 + var3;
      int var7 = var4.length;
      if(var6 > var7) {
         throw new DataLengthException("output buffer too small in processBytes()");
      } else {
         for(int var8 = 0; var8 != var3; ++var8) {
            BlockCipher var9 = this.cipher;
            int var10 = var2 + var8;
            int var11 = var5 + var8;
            var9.processBlock(var1, var10, var4, var11);
         }

      }
   }

   public void reset() {
      this.cipher.reset();
   }

   public byte returnByte(byte var1) {
      this.oneByte[0] = var1;
      BlockCipher var2 = this.cipher;
      byte[] var3 = this.oneByte;
      byte[] var4 = this.oneByte;
      var2.processBlock(var3, 0, var4, 0);
      return this.oneByte[0];
   }
}
