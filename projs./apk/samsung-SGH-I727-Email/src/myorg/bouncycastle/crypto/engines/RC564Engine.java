package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.RC5Parameters;

public class RC564Engine implements BlockCipher {

   private static final long P64 = -5196783011329398165L;
   private static final long Q64 = -7046029254386353131L;
   private static final int bytesPerWord = 8;
   private static final int wordSize = 64;
   private long[] _S = null;
   private int _noRounds = 12;
   private boolean forEncryption;


   public RC564Engine() {}

   private long bytesToWord(byte[] var1, int var2) {
      long var3 = 0L;

      for(int var5 = 7; var5 >= 0; var5 += -1) {
         long var6 = var3 << 8;
         int var8 = var5 + var2;
         long var9 = (long)(var1[var8] & 255);
         var3 = var6 + var9;
      }

      return var3;
   }

   private int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      long var5 = this.bytesToWord(var1, var2);
      int var7 = var2 + 8;
      long var8 = this.bytesToWord(var1, var7);

      for(int var10 = this._noRounds; var10 >= 1; var10 += -1) {
         long[] var11 = this._S;
         int var12 = var10 * 2 + 1;
         long var13 = var11[var12];
         long var15 = var8 - var13;
         var8 = this.rotateRight(var15, var5) ^ var5;
         long[] var17 = this._S;
         int var18 = var10 * 2;
         long var19 = var17[var18];
         long var21 = var5 - var19;
         var5 = this.rotateRight(var21, var8) ^ var8;
      }

      long var23 = this._S[0];
      long var25 = var5 - var23;
      this.wordToBytes(var25, var3, var4);
      long var27 = this._S[1];
      long var29 = var8 - var27;
      int var31 = var4 + 8;
      this.wordToBytes(var29, var3, var31);
      return 16;
   }

   private int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      long var5 = this.bytesToWord(var1, var2);
      long var7 = this._S[0];
      long var9 = var5 + var7;
      int var11 = var2 + 8;
      long var12 = this.bytesToWord(var1, var11);
      long var14 = this._S[1];
      long var16 = var12 + var14;
      int var18 = 1;

      while(true) {
         int var19 = this._noRounds;
         if(var18 > var19) {
            this.wordToBytes(var9, var3, var4);
            int var36 = var4 + 8;
            this.wordToBytes(var16, var3, var36);
            return 16;
         }

         long var20 = var9 ^ var16;
         long var22 = this.rotateLeft(var20, var16);
         long[] var24 = this._S;
         int var25 = var18 * 2;
         long var26 = var24[var25];
         var9 = var22 + var26;
         long var28 = var16 ^ var9;
         long var30 = this.rotateLeft(var28, var9);
         long[] var32 = this._S;
         int var33 = var18 * 2 + 1;
         long var34 = var32[var33];
         var16 = var30 + var34;
         ++var18;
      }
   }

   private long rotateLeft(long var1, long var3) {
      int var5 = (int)(var3 & 63L);
      long var6 = var1 << var5;
      long var8 = 63L & var3;
      int var10 = (int)(64L - var8);
      long var11 = var1 >>> var10;
      return var6 | var11;
   }

   private long rotateRight(long var1, long var3) {
      int var5 = (int)(var3 & 63L);
      long var6 = var1 >>> var5;
      long var8 = 63L & var3;
      int var10 = (int)(64L - var8);
      long var11 = var1 << var10;
      return var6 | var11;
   }

   private void setKey(byte[] param1) {
      // $FF: Couldn't be decompiled
   }

   private void wordToBytes(long var1, byte[] var3, int var4) {
      for(int var5 = 0; var5 < 8; ++var5) {
         int var6 = var5 + var4;
         byte var7 = (byte)((int)var1);
         var3[var6] = var7;
         var1 >>>= 8;
      }

   }

   public String getAlgorithmName() {
      return "RC5-64";
   }

   public int getBlockSize() {
      return 16;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(!(var2 instanceof RC5Parameters)) {
         StringBuilder var3 = (new StringBuilder()).append("invalid parameter passed to RC564 init - ");
         String var4 = var2.getClass().getName();
         String var5 = var3.append(var4).toString();
         throw new IllegalArgumentException(var5);
      } else {
         RC5Parameters var6 = (RC5Parameters)var2;
         this.forEncryption = var1;
         int var7 = var6.getRounds();
         this._noRounds = var7;
         byte[] var8 = var6.getKey();
         this.setKey(var8);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5;
      if(this.forEncryption) {
         var5 = this.encryptBlock(var1, var2, var3, var4);
      } else {
         var5 = this.decryptBlock(var1, var2, var3, var4);
      }

      return var5;
   }

   public void reset() {}
}
