package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class SICBlockCipher implements BlockCipher {

   private byte[] IV;
   private final int blockSize;
   private final BlockCipher cipher;
   private byte[] counter;
   private byte[] counterOut;


   public SICBlockCipher(BlockCipher var1) {
      this.cipher = var1;
      int var2 = this.cipher.getBlockSize();
      this.blockSize = var2;
      byte[] var3 = new byte[this.blockSize];
      this.IV = var3;
      byte[] var4 = new byte[this.blockSize];
      this.counter = var4;
      byte[] var5 = new byte[this.blockSize];
      this.counterOut = var5;
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.cipher.getAlgorithmName();
      return var1.append(var2).append("/SIC").toString();
   }

   public int getBlockSize() {
      return this.cipher.getBlockSize();
   }

   public BlockCipher getUnderlyingCipher() {
      return this.cipher;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      if(var2 instanceof ParametersWithIV) {
         ParametersWithIV var3 = (ParametersWithIV)var2;
         byte[] var4 = var3.getIV();
         byte[] var5 = this.IV;
         int var6 = this.IV.length;
         System.arraycopy(var4, 0, var5, 0, var6);
         this.reset();
         BlockCipher var7 = this.cipher;
         CipherParameters var8 = var3.getParameters();
         var7.init((boolean)1, var8);
      } else {
         throw new IllegalArgumentException("SIC mode requires ParametersWithIV");
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      BlockCipher var5 = this.cipher;
      byte[] var6 = this.counter;
      byte[] var7 = this.counterOut;
      var5.processBlock(var6, 0, var7, 0);
      int var9 = 0;

      while(true) {
         int var10 = this.counterOut.length;
         if(var9 >= var10) {
            byte var16 = 1;

            for(int var17 = this.counter.length - 1; var17 >= 0; var17 += -1) {
               int var18 = (this.counter[var17] & 255) + var16;
               if(var18 > 255) {
                  var16 = 1;
               } else {
                  var16 = 0;
               }

               byte[] var19 = this.counter;
               byte var20 = (byte)var18;
               var19[var17] = var20;
            }

            return this.counter.length;
         }

         int var11 = var4 + var9;
         byte var12 = this.counterOut[var9];
         int var13 = var2 + var9;
         byte var14 = var1[var13];
         byte var15 = (byte)(var12 ^ var14);
         var3[var11] = var15;
         ++var9;
      }
   }

   public void reset() {
      byte[] var1 = this.IV;
      byte[] var2 = this.counter;
      int var3 = this.counter.length;
      System.arraycopy(var1, 0, var2, 0, var3);
      this.cipher.reset();
   }
}
