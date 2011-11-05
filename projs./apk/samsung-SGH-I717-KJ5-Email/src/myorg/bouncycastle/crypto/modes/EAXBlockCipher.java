package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.macs.CMac;
import myorg.bouncycastle.crypto.modes.AEADBlockCipher;
import myorg.bouncycastle.crypto.modes.SICBlockCipher;
import myorg.bouncycastle.crypto.params.AEADParameters;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.util.Arrays;

public class EAXBlockCipher implements AEADBlockCipher {

   private static final byte cTAG = 2;
   private static final byte hTAG = 1;
   private static final byte nTAG;
   private byte[] associatedTextMac;
   private int blockSize;
   private byte[] bufBlock;
   private int bufOff;
   private SICBlockCipher cipher;
   private boolean forEncryption;
   private Mac mac;
   private byte[] macBlock;
   private int macSize;
   private byte[] nonceMac;


   public EAXBlockCipher(BlockCipher var1) {
      int var2 = var1.getBlockSize();
      this.blockSize = var2;
      CMac var3 = new CMac(var1);
      this.mac = var3;
      byte[] var4 = new byte[this.blockSize];
      this.macBlock = var4;
      byte[] var5 = new byte[this.blockSize * 2];
      this.bufBlock = var5;
      byte[] var6 = new byte[this.mac.getMacSize()];
      this.associatedTextMac = var6;
      byte[] var7 = new byte[this.mac.getMacSize()];
      this.nonceMac = var7;
      SICBlockCipher var8 = new SICBlockCipher(var1);
      this.cipher = var8;
   }

   private void calculateMac() {
      byte[] var1 = new byte[this.blockSize];
      this.mac.doFinal(var1, 0);
      int var3 = 0;

      while(true) {
         int var4 = this.macBlock.length;
         if(var3 >= var4) {
            return;
         }

         byte[] var5 = this.macBlock;
         byte var6 = this.nonceMac[var3];
         byte var7 = this.associatedTextMac[var3];
         int var8 = var6 ^ var7;
         byte var9 = var1[var3];
         byte var10 = (byte)(var8 ^ var9);
         var5[var3] = var10;
         ++var3;
      }
   }

   private int process(byte var1, byte[] var2, int var3) {
      byte[] var4 = this.bufBlock;
      int var5 = this.bufOff;
      int var6 = var5 + 1;
      this.bufOff = var6;
      var4[var5] = var1;
      int var7 = this.bufOff;
      int var8 = this.bufBlock.length;
      int var19;
      if(var7 == var8) {
         int var11;
         if(this.forEncryption) {
            SICBlockCipher var9 = this.cipher;
            byte[] var10 = this.bufBlock;
            var11 = var9.processBlock(var10, 0, var2, var3);
            Mac var12 = this.mac;
            int var13 = this.blockSize;
            var12.update(var2, var3, var13);
         } else {
            Mac var20 = this.mac;
            byte[] var21 = this.bufBlock;
            int var22 = this.blockSize;
            var20.update(var21, 0, var22);
            SICBlockCipher var23 = this.cipher;
            byte[] var24 = this.bufBlock;
            var11 = var23.processBlock(var24, 0, var2, var3);
         }

         int var14 = this.blockSize;
         this.bufOff = var14;
         byte[] var15 = this.bufBlock;
         int var16 = this.blockSize;
         byte[] var17 = this.bufBlock;
         int var18 = this.blockSize;
         System.arraycopy(var15, var16, var17, 0, var18);
         var19 = var11;
      } else {
         var19 = 0;
      }

      return var19;
   }

   private void reset(boolean var1) {
      this.cipher.reset();
      this.mac.reset();
      this.bufOff = 0;
      Arrays.fill(this.bufBlock, (byte)0);
      if(var1) {
         Arrays.fill(this.macBlock, (byte)0);
      }

      byte[] var2 = new byte[this.blockSize];
      int var3 = this.blockSize - 1;
      var2[var3] = 2;
      Mac var4 = this.mac;
      int var5 = this.blockSize;
      var4.update(var2, 0, var5);
   }

   private boolean verifyMac(byte[] var1, int var2) {
      int var3 = 0;

      boolean var8;
      while(true) {
         int var4 = this.macSize;
         if(var3 >= var4) {
            var8 = true;
            break;
         }

         byte var5 = this.macBlock[var3];
         int var6 = var2 + var3;
         byte var7 = var1[var6];
         if(var5 != var7) {
            var8 = false;
            break;
         }

         ++var3;
      }

      return var8;
   }

   public int doFinal(byte[] var1, int var2) throws IllegalStateException, InvalidCipherTextException {
      int var3 = this.bufOff;
      byte[] var4 = new byte[this.bufBlock.length];
      this.bufOff = 0;
      int var16;
      if(this.forEncryption) {
         SICBlockCipher var5 = this.cipher;
         byte[] var6 = this.bufBlock;
         var5.processBlock(var6, 0, var4, 0);
         SICBlockCipher var8 = this.cipher;
         byte[] var9 = this.bufBlock;
         int var10 = this.blockSize;
         int var11 = this.blockSize;
         var8.processBlock(var9, var10, var4, var11);
         System.arraycopy(var4, 0, var1, var2, var3);
         this.mac.update(var4, 0, var3);
         this.calculateMac();
         byte[] var13 = this.macBlock;
         int var14 = var2 + var3;
         int var15 = this.macSize;
         System.arraycopy(var13, 0, var1, var14, var15);
         this.reset((boolean)0);
         var16 = this.macSize + var3;
      } else {
         int var17 = this.macSize;
         if(var3 > var17) {
            Mac var18 = this.mac;
            byte[] var19 = this.bufBlock;
            int var20 = this.macSize;
            int var21 = var3 - var20;
            var18.update(var19, 0, var21);
            SICBlockCipher var22 = this.cipher;
            byte[] var23 = this.bufBlock;
            var22.processBlock(var23, 0, var4, 0);
            SICBlockCipher var25 = this.cipher;
            byte[] var26 = this.bufBlock;
            int var27 = this.blockSize;
            int var28 = this.blockSize;
            var25.processBlock(var26, var27, var4, var28);
            int var30 = this.macSize;
            int var31 = var3 - var30;
            System.arraycopy(var4, 0, var1, var2, var31);
         }

         this.calculateMac();
         byte[] var32 = this.bufBlock;
         int var33 = this.macSize;
         int var34 = var3 - var33;
         if(!this.verifyMac(var32, var34)) {
            throw new InvalidCipherTextException("mac check in EAX failed");
         }

         this.reset((boolean)0);
         int var35 = this.macSize;
         var16 = var3 - var35;
      }

      return var16;
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.cipher.getUnderlyingCipher().getAlgorithmName();
      return var1.append(var2).append("/EAX").toString();
   }

   public int getBlockSize() {
      return this.cipher.getBlockSize();
   }

   public byte[] getMac() {
      byte[] var1 = new byte[this.macSize];
      byte[] var2 = this.macBlock;
      int var3 = this.macSize;
      System.arraycopy(var2, 0, var1, 0, var3);
      return var1;
   }

   public int getOutputSize(int var1) {
      int var4;
      if(this.forEncryption) {
         int var2 = this.bufOff + var1;
         int var3 = this.macSize;
         var4 = var2 + var3;
      } else {
         int var5 = this.bufOff + var1;
         int var6 = this.macSize;
         var4 = var5 - var6;
      }

      return var4;
   }

   public BlockCipher getUnderlyingCipher() {
      return this.cipher.getUnderlyingCipher();
   }

   public int getUpdateOutputSize(int var1) {
      int var2 = this.bufOff + var1;
      int var3 = this.blockSize;
      int var4 = var2 / var3;
      int var5 = this.blockSize;
      return var4 * var5;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.forEncryption = var1;
      byte[] var4;
      byte[] var5;
      Object var7;
      if(var2 instanceof AEADParameters) {
         AEADParameters var3 = (AEADParameters)var2;
         var4 = var3.getNonce();
         var5 = var3.getAssociatedText();
         int var6 = var3.getMacSize() / 8;
         this.macSize = var6;
         var7 = var3.getKey();
      } else {
         if(!(var2 instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("invalid parameters passed to EAX");
         }

         ParametersWithIV var31 = (ParametersWithIV)var2;
         var4 = var31.getIV();
         var5 = new byte[0];
         int var32 = this.mac.getMacSize() / 2;
         this.macSize = var32;
         var7 = var31.getParameters();
      }

      byte[] var8 = new byte[this.blockSize];
      this.mac.init((CipherParameters)var7);
      int var9 = this.blockSize - 1;
      var8[var9] = 1;
      Mac var10 = this.mac;
      int var11 = this.blockSize;
      var10.update(var8, 0, var11);
      Mac var12 = this.mac;
      int var13 = var5.length;
      var12.update(var5, 0, var13);
      Mac var14 = this.mac;
      byte[] var15 = this.associatedTextMac;
      var14.doFinal(var15, 0);
      int var17 = this.blockSize - 1;
      var8[var17] = 0;
      Mac var18 = this.mac;
      int var19 = this.blockSize;
      var18.update(var8, 0, var19);
      Mac var20 = this.mac;
      int var21 = var4.length;
      var20.update(var4, 0, var21);
      Mac var22 = this.mac;
      byte[] var23 = this.nonceMac;
      var22.doFinal(var23, 0);
      int var25 = this.blockSize - 1;
      var8[var25] = 2;
      Mac var26 = this.mac;
      int var27 = this.blockSize;
      var26.update(var8, 0, var27);
      SICBlockCipher var28 = this.cipher;
      byte[] var29 = this.nonceMac;
      ParametersWithIV var30 = new ParametersWithIV((CipherParameters)var7, var29);
      var28.init((boolean)1, var30);
   }

   public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException {
      return this.process(var1, var2, var3);
   }

   public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException {
      int var6 = 0;

      for(int var7 = 0; var7 != var3; ++var7) {
         int var8 = var2 + var7;
         byte var9 = var1[var8];
         int var10 = var5 + var6;
         int var11 = this.process(var9, var4, var10);
         var6 += var11;
      }

      return var6;
   }

   public void reset() {
      this.reset((boolean)1);
   }
}
