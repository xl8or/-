package myorg.bouncycastle.crypto.macs;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;

public class BlockCipherMac implements Mac {

   private byte[] buf;
   private int bufOff;
   private BlockCipher cipher;
   private byte[] mac;
   private int macSize;


   public BlockCipherMac(BlockCipher var1) {
      int var2 = var1.getBlockSize() * 8 / 2;
      this(var1, var2);
   }

   public BlockCipherMac(BlockCipher var1, int var2) {
      if(var2 % 8 != 0) {
         throw new IllegalArgumentException("MAC size must be multiple of 8");
      } else {
         CBCBlockCipher var3 = new CBCBlockCipher(var1);
         this.cipher = var3;
         int var4 = var2 / 8;
         this.macSize = var4;
         byte[] var5 = new byte[var1.getBlockSize()];
         this.mac = var5;
         byte[] var6 = new byte[var1.getBlockSize()];
         this.buf = var6;
         this.bufOff = 0;
      }
   }

   public int doFinal(byte[] var1, int var2) {
      int var6;
      for(int var3 = this.cipher.getBlockSize(); this.bufOff < var3; this.bufOff = var6) {
         byte[] var4 = this.buf;
         int var5 = this.bufOff;
         var4[var5] = 0;
         var6 = this.bufOff + 1;
      }

      BlockCipher var7 = this.cipher;
      byte[] var8 = this.buf;
      byte[] var9 = this.mac;
      var7.processBlock(var8, 0, var9, 0);
      byte[] var11 = this.mac;
      int var12 = this.macSize;
      System.arraycopy(var11, 0, var1, var2, var12);
      this.reset();
      return this.macSize;
   }

   public String getAlgorithmName() {
      return this.cipher.getAlgorithmName();
   }

   public int getMacSize() {
      return this.macSize;
   }

   public void init(CipherParameters var1) {
      this.reset();
      this.cipher.init((boolean)1, var1);
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

   public void update(byte var1) {
      int var2 = this.bufOff;
      int var3 = this.buf.length;
      if(var2 == var3) {
         BlockCipher var4 = this.cipher;
         byte[] var5 = this.buf;
         byte[] var6 = this.mac;
         var4.processBlock(var5, 0, var6, 0);
         this.bufOff = 0;
      }

      byte[] var8 = this.buf;
      int var9 = this.bufOff;
      int var10 = var9 + 1;
      this.bufOff = var10;
      var8[var9] = var1;
   }

   public void update(byte[] var1, int var2, int var3) {
      if(var3 < 0) {
         throw new IllegalArgumentException("Can\'t have a negative input length!");
      } else {
         int var4 = this.cipher.getBlockSize();
         byte var5 = 0;
         int var6 = this.bufOff;
         int var7 = var4 - var6;
         if(var3 > var7) {
            byte[] var8 = this.buf;
            int var9 = this.bufOff;
            System.arraycopy(var1, var2, var8, var9, var7);
            BlockCipher var10 = this.cipher;
            byte[] var11 = this.buf;
            byte[] var12 = this.mac;
            int var13 = var10.processBlock(var11, 0, var12, 0);
            int var20 = var5 + var13;
            this.bufOff = 0;
            var3 -= var7;

            for(var2 += var7; var3 > var4; var2 += var4) {
               BlockCipher var14 = this.cipher;
               byte[] var15 = this.mac;
               int var16 = var14.processBlock(var1, var2, var15, 0);
               var20 += var16;
               var3 -= var4;
            }
         }

         byte[] var17 = this.buf;
         int var18 = this.bufOff;
         System.arraycopy(var1, var2, var17, var18, var3);
         int var19 = this.bufOff + var3;
         this.bufOff = var19;
      }
   }
}
