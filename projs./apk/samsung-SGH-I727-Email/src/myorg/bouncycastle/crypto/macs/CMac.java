package myorg.bouncycastle.crypto.macs;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.crypto.paddings.ISO7816d4Padding;

public class CMac implements Mac {

   private static final byte CONSTANT_128 = -121;
   private static final byte CONSTANT_64 = 27;
   private byte[] L;
   private byte[] Lu;
   private byte[] Lu2;
   private byte[] ZEROES;
   private byte[] buf;
   private int bufOff;
   private BlockCipher cipher;
   private byte[] mac;
   private int macSize;


   public CMac(BlockCipher var1) {
      int var2 = var1.getBlockSize() * 8;
      this(var1, var2);
   }

   public CMac(BlockCipher var1, int var2) {
      if(var2 % 8 != 0) {
         throw new IllegalArgumentException("MAC size must be multiple of 8");
      } else {
         int var3 = var1.getBlockSize() * 8;
         if(var2 > var3) {
            StringBuilder var4 = (new StringBuilder()).append("MAC size must be less or equal to ");
            int var5 = var1.getBlockSize() * 8;
            String var6 = var4.append(var5).toString();
            throw new IllegalArgumentException(var6);
         } else if(var1.getBlockSize() != 8 && var1.getBlockSize() != 16) {
            throw new IllegalArgumentException("Block size must be either 64 or 128 bits");
         } else {
            CBCBlockCipher var7 = new CBCBlockCipher(var1);
            this.cipher = var7;
            int var8 = var2 / 8;
            this.macSize = var8;
            byte[] var9 = new byte[var1.getBlockSize()];
            this.mac = var9;
            byte[] var10 = new byte[var1.getBlockSize()];
            this.buf = var10;
            byte[] var11 = new byte[var1.getBlockSize()];
            this.ZEROES = var11;
            this.bufOff = 0;
         }
      }
   }

   private byte[] doubleLu(byte[] var1) {
      int var2 = (var1[0] & 255) >> 7;
      byte[] var3 = new byte[var1.length];
      int var4 = 0;

      while(true) {
         int var5 = var1.length - 1;
         if(var4 >= var5) {
            int var10 = var1.length - 1;
            int var11 = var1.length - 1;
            byte var12 = (byte)(var1[var11] << 1);
            var3[var10] = var12;
            if(var2 == 1) {
               int var13 = var1.length - 1;
               byte var14 = var3[var13];
               char var15;
               if(var1.length == 16) {
                  var15 = '\uff87';
               } else {
                  var15 = 27;
               }

               byte var16 = (byte)(var14 ^ var15);
               var3[var13] = var16;
            }

            return var3;
         }

         int var6 = var1[var4] << 1;
         int var7 = var4 + 1;
         int var8 = (var1[var7] & 255) >> 7;
         byte var9 = (byte)(var6 + var8);
         var3[var4] = var9;
         ++var4;
      }
   }

   public int doFinal(byte[] var1, int var2) {
      int var3 = this.cipher.getBlockSize();
      byte[] var4;
      if(this.bufOff == var3) {
         var4 = this.Lu;
      } else {
         ISO7816d4Padding var11 = new ISO7816d4Padding();
         byte[] var12 = this.buf;
         int var13 = this.bufOff;
         var11.addPadding(var12, var13);
         var4 = this.Lu2;
      }

      int var5 = 0;

      while(true) {
         int var6 = this.mac.length;
         if(var5 >= var6) {
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

         byte[] var7 = this.buf;
         byte var8 = var7[var5];
         byte var9 = var4[var5];
         byte var10 = (byte)(var8 ^ var9);
         var7[var5] = var10;
         ++var5;
      }
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
      byte[] var2 = new byte[this.ZEROES.length];
      this.L = var2;
      BlockCipher var3 = this.cipher;
      byte[] var4 = this.ZEROES;
      byte[] var5 = this.L;
      var3.processBlock(var4, 0, var5, 0);
      byte[] var7 = this.L;
      byte[] var8 = this.doubleLu(var7);
      this.Lu = var8;
      byte[] var9 = this.Lu;
      byte[] var10 = this.doubleLu(var9);
      this.Lu2 = var10;
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
