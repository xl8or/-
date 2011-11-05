package myorg.bouncycastle.crypto.macs;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.engines.DESEngine;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.crypto.paddings.BlockCipherPadding;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class ISO9797Alg3Mac implements Mac {

   private byte[] buf;
   private int bufOff;
   private BlockCipher cipher;
   private KeyParameter lastKey2;
   private KeyParameter lastKey3;
   private byte[] mac;
   private int macSize;
   private BlockCipherPadding padding;


   public ISO9797Alg3Mac(BlockCipher var1) {
      int var2 = var1.getBlockSize() * 8;
      this(var1, var2, (BlockCipherPadding)null);
   }

   public ISO9797Alg3Mac(BlockCipher var1, int var2) {
      this(var1, var2, (BlockCipherPadding)null);
   }

   public ISO9797Alg3Mac(BlockCipher var1, int var2, BlockCipherPadding var3) {
      if(var2 % 8 != 0) {
         throw new IllegalArgumentException("MAC size must be multiple of 8");
      } else if(!(var1 instanceof DESEngine)) {
         throw new IllegalArgumentException("cipher must be instance of DESEngine");
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

   public ISO9797Alg3Mac(BlockCipher var1, BlockCipherPadding var2) {
      int var3 = var1.getBlockSize() * 8;
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
      DESEngine var19 = new DESEngine();
      KeyParameter var20 = this.lastKey2;
      var19.init((boolean)0, var20);
      byte[] var21 = this.mac;
      byte[] var22 = this.mac;
      var19.processBlock(var21, 0, var22, 0);
      KeyParameter var24 = this.lastKey3;
      var19.init((boolean)1, var24);
      byte[] var25 = this.mac;
      byte[] var26 = this.mac;
      var19.processBlock(var25, 0, var26, 0);
      byte[] var28 = this.mac;
      int var29 = this.macSize;
      System.arraycopy(var28, 0, var1, var2, var29);
      this.reset();
      return this.macSize;
   }

   public String getAlgorithmName() {
      return "ISO9797Alg3";
   }

   public int getMacSize() {
      return this.macSize;
   }

   public void init(CipherParameters var1) {
      this.reset();
      if(!(var1 instanceof KeyParameter)) {
         throw new IllegalArgumentException("params must be an instance of KeyParameter");
      } else {
         byte[] var2 = ((KeyParameter)var1).getKey();
         KeyParameter var3;
         if(var2.length == 16) {
            var3 = new KeyParameter(var2, 0, 8);
            KeyParameter var4 = new KeyParameter(var2, 8, 8);
            this.lastKey2 = var4;
            this.lastKey3 = var3;
         } else {
            if(var2.length != 24) {
               throw new IllegalArgumentException("Key must be either 112 or 168 bit long");
            }

            var3 = new KeyParameter(var2, 0, 8);
            KeyParameter var5 = new KeyParameter(var2, 8, 8);
            this.lastKey2 = var5;
            KeyParameter var6 = new KeyParameter(var2, 16, 8);
            this.lastKey3 = var6;
         }

         this.cipher.init((boolean)1, var3);
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
