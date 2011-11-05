package myorg.bouncycastle.crypto.macs;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.crypto.paddings.BlockCipherPadding;

public class CBCBlockCipherMac implements Mac {

   private byte[] buf;
   private int bufOff;
   private BlockCipher cipher;
   private byte[] mac;
   private int macSize;
   private BlockCipherPadding padding;


   public CBCBlockCipherMac(BlockCipher var1) {
      int var2 = var1.getBlockSize() * 8 / 2;
      this(var1, var2, (BlockCipherPadding)null);
   }

   public CBCBlockCipherMac(BlockCipher var1, int var2) {
      this(var1, var2, (BlockCipherPadding)null);
   }

   public CBCBlockCipherMac(BlockCipher var1, int var2, BlockCipherPadding var3) {
      if(var2 % 8 != 0) {
         throw new IllegalArgumentException("MAC size must be multiple of 8");
      } else {
         CBCBlockCipher var4 = new CBCBlockCipher(var1);
         this.cipher = var4;
         this.padding = var3;
         int var5 = var2 / 8;
         this.macSize = var5;
         byte[] var6 = new byte[var1.getBlockSize()];
         this.mac = var6;
         byte[] var7 = new byte[var1.getBlockSize()];
         this.buf = var7;
         this.bufOff = 0;
      }
   }

   public CBCBlockCipherMac(BlockCipher var1, BlockCipherPadding var2) {
      int var3 = var1.getBlockSize() * 8 / 2;
      this(var1, var3, var2);
   }

   public int doFinal(byte[] var1, int var2) {
      int var3 = this.cipher.getBlockSize();
      if(this.padding == null) {
         while(this.bufOff < var3) {
            byte[] var4 = this.buf;
            int var5 = this.bufOff;
            var4[var5] = 0;
            int var6 = this.bufOff + 1;
            this.bufOff = var6;
         }
      } else {
         if(this.bufOff == var3) {
            BlockCipher var7 = this.cipher;
            byte[] var8 = this.buf;
            byte[] var9 = this.mac;
            var7.processBlock(var8, 0, var9, 0);
            this.bufOff = 0;
         }

         BlockCipherPadding var11 = this.padding;
         byte[] var12 = this.buf;
         int var13 = this.bufOff;
         var11.addPadding(var12, var13);
      }

      BlockCipher var15 = this.cipher;
      byte[] var16 = this.buf;
      byte[] var17 = this.mac;
      var15.processBlock(var16, 0, var17, 0);
      byte[] var19 = this.mac;
      int var20 = this.macSize;
      System.arraycopy(var19, 0, var1, var2, var20);
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
         int var5 = this.bufOff;
         int var6 = var4 - var5;
         if(var3 > var6) {
            byte[] var7 = this.buf;
            int var8 = this.bufOff;
            System.arraycopy(var1, var2, var7, var8, var6);
            BlockCipher var9 = this.cipher;
            byte[] var10 = this.buf;
            byte[] var11 = this.mac;
            var9.processBlock(var10, 0, var11, 0);
            this.bufOff = 0;
            var3 -= var6;

            for(var2 += var6; var3 > var4; var2 += var4) {
               BlockCipher var13 = this.cipher;
               byte[] var14 = this.mac;
               var13.processBlock(var1, var2, var14, 0);
               var3 -= var4;
            }
         }

         byte[] var16 = this.buf;
         int var17 = this.bufOff;
         System.arraycopy(var1, var2, var16, var17, var3);
         int var18 = this.bufOff + var3;
         this.bufOff = var18;
      }
   }
}
