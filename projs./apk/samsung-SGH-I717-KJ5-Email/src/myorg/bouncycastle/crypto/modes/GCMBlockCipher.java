package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.modes.AEADBlockCipher;
import myorg.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import myorg.bouncycastle.crypto.modes.gcm.Tables8kGCMMultiplier;
import myorg.bouncycastle.crypto.params.AEADParameters;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.crypto.util.Pack;
import myorg.bouncycastle.util.Arrays;

public class GCMBlockCipher implements AEADBlockCipher {

   private static final int BLOCK_SIZE = 16;
   private static final byte[] ZEROES = new byte[16];
   private byte[] A;
   private byte[] H;
   private byte[] J0;
   private byte[] S;
   private byte[] bufBlock;
   private int bufOff;
   private BlockCipher cipher;
   private byte[] counter;
   private boolean forEncryption;
   private byte[] initS;
   private KeyParameter keyParam;
   private byte[] macBlock;
   private int macSize;
   private GCMMultiplier multiplier;
   private byte[] nonce;
   private long totalLength;


   public GCMBlockCipher(BlockCipher var1) {
      this(var1, (GCMMultiplier)null);
   }

   public GCMBlockCipher(BlockCipher var1, GCMMultiplier var2) {
      if(var1.getBlockSize() != 16) {
         throw new IllegalArgumentException("cipher required with a block size of 16.");
      } else {
         if(var2 == null) {
            var2 = new Tables8kGCMMultiplier();
         }

         this.cipher = var1;
         this.multiplier = (GCMMultiplier)var2;
      }
   }

   private void gCTRBlock(byte[] var1, int var2, byte[] var3, int var4) {
      for(int var5 = 15; var5 >= 12; var5 += -1) {
         byte var6 = (byte)(this.counter[var5] + 1 & 255);
         this.counter[var5] = var6;
         if(var6 != 0) {
            break;
         }
      }

      byte[] var7 = new byte[16];
      BlockCipher var8 = this.cipher;
      byte[] var9 = this.counter;
      var8.processBlock(var9, 0, var7, 0);
      byte[] var13;
      if(this.forEncryption) {
         byte[] var11 = ZEROES;
         int var12 = 16 - var2;
         System.arraycopy(var11, var2, var7, var2, var12);
         var13 = var7;
      } else {
         var13 = var1;
      }

      for(int var14 = var2 - 1; var14 >= 0; var14 += -1) {
         byte var15 = var7[var14];
         byte var16 = var1[var14];
         byte var17 = (byte)(var15 ^ var16);
         var7[var14] = var17;
         int var18 = var4 + var14;
         byte var19 = var7[var14];
         var3[var18] = var19;
      }

      xor(this.S, var13);
      GCMMultiplier var20 = this.multiplier;
      byte[] var21 = this.S;
      var20.multiplyH(var21);
      long var22 = this.totalLength;
      long var24 = (long)var2;
      long var26 = var22 + var24;
      this.totalLength = var26;
   }

   private byte[] gHASH(byte[] var1) {
      byte[] var2 = new byte[16];
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 >= var4) {
            return var2;
         }

         byte[] var5 = new byte[16];
         int var6 = Math.min(var1.length - var3, 16);
         System.arraycopy(var1, var3, var5, 0, var6);
         xor(var2, var5);
         this.multiplier.multiplyH(var2);
         var3 += 16;
      }
   }

   private static void packLength(long var0, byte[] var2, int var3) {
      Pack.intToBigEndian((int)(var0 >>> 32), var2, var3);
      int var4 = (int)var0;
      int var5 = var3 + 4;
      Pack.intToBigEndian(var4, var2, var5);
   }

   private int process(byte var1, byte[] var2, int var3) throws DataLengthException {
      byte[] var4 = this.bufBlock;
      int var5 = this.bufOff;
      int var6 = var5 + 1;
      this.bufOff = var6;
      var4[var5] = var1;
      int var7 = this.bufOff;
      int var8 = this.bufBlock.length;
      byte var14;
      if(var7 == var8) {
         byte[] var9 = this.bufBlock;
         this.gCTRBlock(var9, 16, var2, var3);
         if(!this.forEncryption) {
            byte[] var10 = this.bufBlock;
            byte[] var11 = this.bufBlock;
            int var12 = this.macSize;
            System.arraycopy(var10, 16, var11, 0, var12);
         }

         int var13 = this.bufBlock.length - 16;
         this.bufOff = var13;
         var14 = 16;
      } else {
         var14 = 0;
      }

      return var14;
   }

   private void reset(boolean var1) {
      byte[] var2 = Arrays.clone(this.initS);
      this.S = var2;
      byte[] var3 = Arrays.clone(this.J0);
      this.counter = var3;
      this.bufOff = 0;
      this.totalLength = 0L;
      if(this.bufBlock != null) {
         Arrays.fill(this.bufBlock, (byte)0);
      }

      if(var1) {
         this.macBlock = null;
      }

      this.cipher.reset();
   }

   private static void xor(byte[] var0, byte[] var1) {
      for(int var2 = 15; var2 >= 0; var2 += -1) {
         byte var3 = var0[var2];
         byte var4 = var1[var2];
         byte var5 = (byte)(var3 ^ var4);
         var0[var2] = var5;
      }

   }

   public int doFinal(byte[] var1, int var2) throws IllegalStateException, InvalidCipherTextException {
      int var3 = this.bufOff;
      if(!this.forEncryption) {
         int var4 = this.macSize;
         if(var3 < var4) {
            throw new InvalidCipherTextException("data too short");
         }

         int var5 = this.macSize;
         var3 -= var5;
      }

      if(var3 > 0) {
         byte[] var6 = new byte[16];
         System.arraycopy(this.bufBlock, 0, var6, 0, var3);
         this.gCTRBlock(var6, var3, var1, var2);
      }

      byte[] var7 = new byte[16];
      packLength((long)this.A.length * 8L, var7, 0);
      packLength(this.totalLength * 8L, var7, 8);
      xor(this.S, var7);
      GCMMultiplier var8 = this.multiplier;
      byte[] var9 = this.S;
      var8.multiplyH(var9);
      byte[] var10 = new byte[16];
      BlockCipher var11 = this.cipher;
      byte[] var12 = this.J0;
      var11.processBlock(var12, 0, var10, 0);
      byte[] var14 = this.S;
      xor(var10, var14);
      int var15 = var3;
      byte[] var16 = new byte[this.macSize];
      this.macBlock = var16;
      byte[] var17 = this.macBlock;
      int var18 = this.macSize;
      System.arraycopy(var10, 0, var17, 0, var18);
      if(this.forEncryption) {
         byte[] var19 = this.macBlock;
         int var20 = this.bufOff + var2;
         int var21 = this.macSize;
         System.arraycopy(var19, 0, var1, var20, var21);
         int var22 = this.macSize;
         var15 = var3 + var22;
      } else {
         byte[] var23 = new byte[this.macSize];
         byte[] var24 = this.bufBlock;
         int var25 = this.macSize;
         System.arraycopy(var24, var3, var23, 0, var25);
         if(!Arrays.constantTimeAreEqual(this.macBlock, var23)) {
            throw new InvalidCipherTextException("mac check in GCM failed");
         }
      }

      this.reset((boolean)0);
      return var15;
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.cipher.getAlgorithmName();
      return var1.append(var2).append("/GCM").toString();
   }

   public byte[] getMac() {
      return Arrays.clone(this.macBlock);
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
      return this.cipher;
   }

   public int getUpdateOutputSize(int var1) {
      return (this.bufOff + var1) / 16 * 16;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.forEncryption = var1;
      this.macBlock = null;
      if(var2 instanceof AEADParameters) {
         AEADParameters var3 = (AEADParameters)var2;
         byte[] var4 = var3.getNonce();
         this.nonce = var4;
         byte[] var5 = var3.getAssociatedText();
         this.A = var5;
         int var6 = var3.getMacSize();
         if(var6 < 96 || var6 > 128 || var6 % 8 != 0) {
            String var7 = "Invalid value for MAC size: " + var6;
            throw new IllegalArgumentException(var7);
         }

         int var8 = var6 / 8;
         this.macSize = var8;
         KeyParameter var9 = var3.getKey();
         this.keyParam = var9;
      } else {
         if(!(var2 instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("invalid parameters passed to GCM");
         }

         ParametersWithIV var12 = (ParametersWithIV)var2;
         byte[] var13 = var12.getIV();
         this.nonce = var13;
         this.A = null;
         this.macSize = 16;
         KeyParameter var14 = (KeyParameter)var12.getParameters();
         this.keyParam = var14;
      }

      int var10;
      if(var1) {
         var10 = 16;
      } else {
         var10 = this.macSize + 16;
      }

      byte[] var11 = new byte[var10];
      this.bufBlock = var11;
      if(this.nonce != null && this.nonce.length >= 1) {
         if(this.A == null) {
            byte[] var15 = new byte[0];
            this.A = var15;
         }

         BlockCipher var16 = this.cipher;
         KeyParameter var17 = this.keyParam;
         var16.init((boolean)1, var17);
         byte[] var18 = new byte[16];
         this.H = var18;
         BlockCipher var19 = this.cipher;
         byte[] var20 = ZEROES;
         byte[] var21 = this.H;
         var19.processBlock(var20, 0, var21, 0);
         GCMMultiplier var23 = this.multiplier;
         byte[] var24 = this.H;
         var23.init(var24);
         byte[] var25 = this.A;
         byte[] var26 = this.gHASH(var25);
         this.initS = var26;
         if(this.nonce.length == 12) {
            byte[] var27 = new byte[16];
            this.J0 = var27;
            byte[] var28 = this.nonce;
            byte[] var29 = this.J0;
            int var30 = this.nonce.length;
            System.arraycopy(var28, 0, var29, 0, var30);
            this.J0[15] = 1;
         } else {
            byte[] var33 = this.nonce;
            byte[] var34 = this.gHASH(var33);
            this.J0 = var34;
            byte[] var35 = new byte[16];
            packLength((long)this.nonce.length * 8L, var35, 8);
            xor(this.J0, var35);
            GCMMultiplier var36 = this.multiplier;
            byte[] var37 = this.J0;
            var36.multiplyH(var37);
         }

         byte[] var31 = Arrays.clone(this.initS);
         this.S = var31;
         byte[] var32 = Arrays.clone(this.J0);
         this.counter = var32;
         this.bufOff = 0;
         this.totalLength = 0L;
      } else {
         throw new IllegalArgumentException("IV must be at least 1 byte");
      }
   }

   public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException {
      return this.process(var1, var2, var3);
   }

   public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException {
      int var6 = 0;

      for(int var7 = 0; var7 != var3; ++var7) {
         byte[] var8 = this.bufBlock;
         int var9 = this.bufOff;
         int var10 = var9 + 1;
         this.bufOff = var10;
         int var11 = var2 + var7;
         byte var12 = var1[var11];
         var8[var9] = var12;
         int var13 = this.bufOff;
         int var14 = this.bufBlock.length;
         if(var13 == var14) {
            byte[] var15 = this.bufBlock;
            int var16 = var5 + var6;
            this.gCTRBlock(var15, 16, var4, var16);
            if(!this.forEncryption) {
               byte[] var17 = this.bufBlock;
               byte[] var18 = this.bufBlock;
               int var19 = this.macSize;
               System.arraycopy(var17, 16, var18, 0, var19);
            }

            int var20 = this.bufBlock.length - 16;
            this.bufOff = var20;
            var6 += 16;
         }
      }

      return var6;
   }

   public void reset() {
      this.reset((boolean)1);
   }
}
