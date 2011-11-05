package myorg.bouncycastle.crypto.macs;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.macs.MacCFBBlockCipher;
import myorg.bouncycastle.crypto.paddings.BlockCipherPadding;

public class CFBBlockCipherMac implements Mac {

   private byte[] buf;
   private int bufOff;
   private MacCFBBlockCipher cipher;
   private byte[] mac;
   private int macSize;
   private BlockCipherPadding padding;


   public CFBBlockCipherMac(BlockCipher var1) {
      int var2 = var1.getBlockSize() * 8 / 2;
      this(var1, 8, var2, (BlockCipherPadding)null);
   }

   public CFBBlockCipherMac(BlockCipher var1, int var2, int var3) {
      this(var1, var2, var3, (BlockCipherPadding)null);
   }

   public CFBBlockCipherMac(BlockCipher var1, int var2, int var3, BlockCipherPadding var4) {
      this.padding = null;
      if(var3 % 8 != 0) {
         throw new IllegalArgumentException("MAC size must be multiple of 8");
      } else {
         byte[] var5 = new byte[var1.getBlockSize()];
         this.mac = var5;
         MacCFBBlockCipher var6 = new MacCFBBlockCipher(var1, var2);
         this.cipher = var6;
         this.padding = var4;
         int var7 = var3 / 8;
         this.macSize = var7;
         byte[] var8 = new byte[this.cipher.getBlockSize()];
         this.buf = var8;
         this.bufOff = 0;
      }
   }

   public CFBBlockCipherMac(BlockCipher var1, BlockCipherPadding var2) {
      int var3 = var1.getBlockSize() * 8 / 2;
      this(var1, 8, var3, var2);
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
         BlockCipherPadding var7 = this.padding;
         byte[] var8 = this.buf;
         int var9 = this.bufOff;
         var7.addPadding(var8, var9);
      }

      MacCFBBlockCipher var11 = this.cipher;
      byte[] var12 = this.buf;
      byte[] var13 = this.mac;
      var11.processBlock(var12, 0, var13, 0);
      MacCFBBlockCipher var15 = this.cipher;
      byte[] var16 = this.mac;
      var15.getMacBlock(var16);
      byte[] var17 = this.mac;
      int var18 = this.macSize;
      System.arraycopy(var17, 0, var1, var2, var18);
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
      this.cipher.init(var1);
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
         MacCFBBlockCipher var4 = this.cipher;
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
            MacCFBBlockCipher var10 = this.cipher;
            byte[] var11 = this.buf;
            byte[] var12 = this.mac;
            int var13 = var10.processBlock(var11, 0, var12, 0);
            int var20 = var5 + var13;
            this.bufOff = 0;
            var3 -= var7;

            for(var2 += var7; var3 > var4; var2 += var4) {
               MacCFBBlockCipher var14 = this.cipher;
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
