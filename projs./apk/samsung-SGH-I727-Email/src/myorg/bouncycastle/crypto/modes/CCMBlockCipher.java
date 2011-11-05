package myorg.bouncycastle.crypto.modes;

import java.io.ByteArrayOutputStream;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.macs.CBCBlockCipherMac;
import myorg.bouncycastle.crypto.modes.AEADBlockCipher;
import myorg.bouncycastle.crypto.modes.SICBlockCipher;
import myorg.bouncycastle.crypto.params.AEADParameters;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.util.Arrays;

public class CCMBlockCipher implements AEADBlockCipher {

   private byte[] associatedText;
   private int blockSize;
   private BlockCipher cipher;
   private ByteArrayOutputStream data;
   private boolean forEncryption;
   private CipherParameters keyParam;
   private byte[] macBlock;
   private int macSize;
   private byte[] nonce;


   public CCMBlockCipher(BlockCipher var1) {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      this.data = var2;
      this.cipher = var1;
      int var3 = var1.getBlockSize();
      this.blockSize = var3;
      byte[] var4 = new byte[this.blockSize];
      this.macBlock = var4;
      if(this.blockSize != 16) {
         throw new IllegalArgumentException("cipher required with a block size of 16.");
      }
   }

   private int calculateMac(byte[] var1, int var2, int var3, byte[] var4) {
      BlockCipher var5 = this.cipher;
      int var6 = this.macSize * 8;
      CBCBlockCipherMac var7 = new CBCBlockCipherMac(var5, var6);
      CipherParameters var8 = this.keyParam;
      var7.init(var8);
      byte[] var9 = new byte[16];
      if(this.hasAssociatedText()) {
         byte var10 = (byte)(var9[0] | 64);
         var9[0] = var10;
      }

      byte var11 = var9[0];
      int var12 = ((var7.getMacSize() - 2) / 2 & 7) << 3;
      byte var13 = (byte)(var11 | var12);
      var9[0] = var13;
      byte var14 = var9[0];
      int var15 = this.nonce.length;
      int var16 = 15 - var15 - 1 & 7;
      byte var17 = (byte)(var14 | var16);
      var9[0] = var17;
      byte[] var18 = this.nonce;
      int var19 = this.nonce.length;
      System.arraycopy(var18, 0, var9, 1, var19);
      int var20 = var3;

      for(int var21 = 1; var20 > 0; ++var21) {
         int var22 = var9.length - var21;
         byte var23 = (byte)(var20 & 255);
         var9[var22] = var23;
         var20 >>>= 8;
      }

      int var24 = var9.length;
      var7.update(var9, 0, var24);
      if(this.hasAssociatedText()) {
         byte var27;
         if(this.associatedText.length < '\uff00') {
            byte var25 = (byte)(this.associatedText.length >> 8);
            var7.update(var25);
            byte var26 = (byte)this.associatedText.length;
            var7.update(var26);
            var27 = 2;
         } else {
            var7.update((byte)-1);
            var7.update((byte)-1);
            byte var33 = (byte)(this.associatedText.length >> 24);
            var7.update(var33);
            byte var34 = (byte)(this.associatedText.length >> 16);
            var7.update(var34);
            byte var35 = (byte)(this.associatedText.length >> 8);
            var7.update(var35);
            byte var36 = (byte)this.associatedText.length;
            var7.update(var36);
            var27 = 6;
         }

         byte[] var28 = this.associatedText;
         int var29 = this.associatedText.length;
         var7.update(var28, 0, var29);
         int var30 = (this.associatedText.length + var27) % 16;
         if(var30 != 0) {
            int var31 = 0;

            while(true) {
               int var32 = 16 - var30;
               if(var31 == var32) {
                  break;
               }

               var7.update((byte)0);
               ++var31;
            }
         }
      }

      var7.update(var1, var2, var3);
      return var7.doFinal(var4, 0);
   }

   private boolean hasAssociatedText() {
      boolean var1;
      if(this.associatedText != null && this.associatedText.length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int doFinal(byte[] var1, int var2) throws IllegalStateException, InvalidCipherTextException {
      byte[] var3 = this.data.toByteArray();
      int var4 = var3.length;
      byte[] var5 = this.processPacket(var3, 0, var4);
      int var6 = var5.length;
      System.arraycopy(var5, 0, var1, var2, var6);
      this.reset();
      return var5.length;
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.cipher.getAlgorithmName();
      return var1.append(var2).append("/CCM").toString();
   }

   public byte[] getMac() {
      byte[] var1 = new byte[this.macSize];
      byte[] var2 = this.macBlock;
      int var3 = var1.length;
      System.arraycopy(var2, 0, var1, 0, var3);
      return var1;
   }

   public int getOutputSize(int var1) {
      int var4;
      if(this.forEncryption) {
         int var2 = this.data.size() + var1;
         int var3 = this.macSize;
         var4 = var2 + var3;
      } else {
         int var5 = this.data.size() + var1;
         int var6 = this.macSize;
         var4 = var5 - var6;
      }

      return var4;
   }

   public BlockCipher getUnderlyingCipher() {
      return this.cipher;
   }

   public int getUpdateOutputSize(int var1) {
      return 0;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.forEncryption = var1;
      if(var2 instanceof AEADParameters) {
         AEADParameters var3 = (AEADParameters)var2;
         byte[] var4 = var3.getNonce();
         this.nonce = var4;
         byte[] var5 = var3.getAssociatedText();
         this.associatedText = var5;
         int var6 = var3.getMacSize() / 8;
         this.macSize = var6;
         KeyParameter var7 = var3.getKey();
         this.keyParam = var7;
      } else if(var2 instanceof ParametersWithIV) {
         ParametersWithIV var8 = (ParametersWithIV)var2;
         byte[] var9 = var8.getIV();
         this.nonce = var9;
         this.associatedText = null;
         int var10 = this.macBlock.length / 2;
         this.macSize = var10;
         CipherParameters var11 = var8.getParameters();
         this.keyParam = var11;
      } else {
         throw new IllegalArgumentException("invalid parameters passed to CCM");
      }
   }

   public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException, IllegalStateException {
      this.data.write(var1);
      return 0;
   }

   public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException, IllegalStateException {
      this.data.write(var1, var2, var3);
      return 0;
   }

   public byte[] processPacket(byte[] var1, int var2, int var3) throws IllegalStateException, InvalidCipherTextException {
      if(this.keyParam == null) {
         throw new IllegalStateException("CCM cipher unitialized.");
      } else {
         BlockCipher var4 = this.cipher;
         SICBlockCipher var5 = new SICBlockCipher(var4);
         byte[] var6 = new byte[this.blockSize];
         int var7 = this.nonce.length;
         byte var8 = (byte)(15 - var7 - 1 & 7);
         var6[0] = var8;
         byte[] var9 = this.nonce;
         int var10 = this.nonce.length;
         System.arraycopy(var9, 0, var6, 1, var10);
         boolean var11 = this.forEncryption;
         CipherParameters var12 = this.keyParam;
         ParametersWithIV var13 = new ParametersWithIV(var12, var6);
         var5.init(var11, var13);
         int var14;
         byte[] var16;
         if(this.forEncryption) {
            var14 = var2;
            int var15 = 0;
            var16 = new byte[this.macSize + var3];
            byte[] var17 = this.macBlock;
            this.calculateMac(var1, var2, var3, var17);
            byte[] var19 = this.macBlock;
            byte[] var20 = this.macBlock;
            var5.processBlock(var19, 0, var20, 0);

            while(true) {
               int var22 = this.blockSize;
               int var23 = var3 - var22;
               if(var14 >= var23) {
                  byte[] var27 = new byte[this.blockSize];
                  int var28 = var3 - var14;
                  System.arraycopy(var1, var14, var27, 0, var28);
                  var5.processBlock(var27, 0, var27, 0);
                  int var30 = var3 - var14;
                  System.arraycopy(var27, 0, var16, var15, var30);
                  int var31 = var3 - var14;
                  int var32 = var15 + var31;
                  byte[] var33 = this.macBlock;
                  int var34 = var16.length - var32;
                  System.arraycopy(var33, 0, var16, var32, var34);
                  return var16;
               }

               var5.processBlock(var1, var14, var16, var15);
               int var25 = this.blockSize;
               var15 += var25;
               int var26 = this.blockSize;
               var14 += var26;
            }
         } else {
            var14 = var2;
            byte var35 = 0;
            int var36 = this.macSize;
            var16 = new byte[var3 - var36];
            int var37 = var2 + var3;
            int var38 = this.macSize;
            int var39 = var37 - var38;
            byte[] var40 = this.macBlock;
            int var41 = this.macSize;
            System.arraycopy(var1, var39, var40, 0, var41);
            byte[] var42 = this.macBlock;
            byte[] var43 = this.macBlock;
            var5.processBlock(var42, 0, var43, 0);
            int var45 = this.macSize;

            while(true) {
               int var46 = this.macBlock.length;
               if(var45 == var46) {
                  while(true) {
                     int var47 = var16.length;
                     int var48 = this.blockSize;
                     int var49 = var47 - var48;
                     if(var35 >= var49) {
                        byte[] var55 = new byte[this.blockSize];
                        int var56 = var16.length - var35;
                        System.arraycopy(var1, var14, var55, 0, var56);
                        var5.processBlock(var55, 0, var55, 0);
                        int var58 = var16.length - var35;
                        System.arraycopy(var55, 0, var16, var35, var58);
                        byte[] var59 = new byte[this.blockSize];
                        int var60 = var16.length;
                        this.calculateMac(var16, 0, var60, var59);
                        if(!Arrays.constantTimeAreEqual(this.macBlock, var59)) {
                           throw new InvalidCipherTextException("mac check in CCM failed");
                        }

                        return var16;
                     }

                     var5.processBlock(var1, var14, var16, var35);
                     int var51 = this.blockSize;
                     int var10000 = var35 + var51;
                     int var53 = this.blockSize;
                     var10000 = var14 + var53;
                  }
               }

               this.macBlock[var45] = 0;
               ++var45;
            }
         }
      }
   }

   public void reset() {
      this.cipher.reset();
      this.data.reset();
   }
}
