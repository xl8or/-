package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.RC5Parameters;

public class RC532Engine implements BlockCipher {

   private static final int P32 = -1209970333;
   private static final int Q32 = -1640531527;
   private int[] _S = null;
   private int _noRounds = 12;
   private boolean forEncryption;


   public RC532Engine() {}

   private int bytesToWord(byte[] var1, int var2) {
      int var3 = var1[var2] & 255;
      int var4 = var2 + 1;
      int var5 = (var1[var4] & 255) << 8;
      int var6 = var3 | var5;
      int var7 = var2 + 2;
      int var8 = (var1[var7] & 255) << 16;
      int var9 = var6 | var8;
      int var10 = var2 + 3;
      int var11 = (var1[var10] & 255) << 24;
      return var9 | var11;
   }

   private int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = this.bytesToWord(var1, var2);
      int var6 = var2 + 4;
      int var7 = this.bytesToWord(var1, var6);

      for(int var8 = this._noRounds; var8 >= 1; var8 += -1) {
         int[] var9 = this._S;
         int var10 = var8 * 2 + 1;
         int var11 = var9[var10];
         int var12 = var7 - var11;
         var7 = this.rotateRight(var12, var5) ^ var5;
         int[] var13 = this._S;
         int var14 = var8 * 2;
         int var15 = var13[var14];
         int var16 = var5 - var15;
         var5 = this.rotateRight(var16, var7) ^ var7;
      }

      int var17 = this._S[0];
      int var18 = var5 - var17;
      this.wordToBytes(var18, var3, var4);
      int var19 = this._S[1];
      int var20 = var7 - var19;
      int var21 = var4 + 4;
      this.wordToBytes(var20, var3, var21);
      return 8;
   }

   private int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = this.bytesToWord(var1, var2);
      int var6 = this._S[0];
      int var7 = var5 + var6;
      int var8 = var2 + 4;
      int var9 = this.bytesToWord(var1, var8);
      int var10 = this._S[1];
      int var11 = var9 + var10;
      int var12 = 1;

      while(true) {
         int var13 = this._noRounds;
         if(var12 > var13) {
            this.wordToBytes(var7, var3, var4);
            int var24 = var4 + 4;
            this.wordToBytes(var11, var3, var24);
            return 8;
         }

         int var14 = var7 ^ var11;
         int var15 = this.rotateLeft(var14, var11);
         int[] var16 = this._S;
         int var17 = var12 * 2;
         int var18 = var16[var17];
         var7 = var15 + var18;
         int var19 = var11 ^ var7;
         int var20 = this.rotateLeft(var19, var7);
         int[] var21 = this._S;
         int var22 = var12 * 2 + 1;
         int var23 = var21[var22];
         var11 = var20 + var23;
         ++var12;
      }
   }

   private int rotateLeft(int var1, int var2) {
      int var3 = var2 & 31;
      int var4 = var1 << var3;
      int var5 = var2 & 31;
      int var6 = 32 - var5;
      int var7 = var1 >>> var6;
      return var4 | var7;
   }

   private int rotateRight(int var1, int var2) {
      int var3 = var2 & 31;
      int var4 = var1 >>> var3;
      int var5 = var2 & 31;
      int var6 = 32 - var5;
      int var7 = var1 << var6;
      return var4 | var7;
   }

   private void setKey(byte[] var1) {
      int[] var2 = new int[(var1.length + 3) / 4];
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 == var4) {
            int[] var11 = new int[(this._noRounds + 1) * 2];
            this._S = var11;
            this._S[0] = -1209970333;
            int var12 = 1;

            while(true) {
               int var13 = this._S.length;
               if(var12 >= var13) {
                  int var18 = var2.length;
                  int var19 = this._S.length;
                  int var20;
                  if(var18 > var19) {
                     var20 = var2.length * 3;
                  } else {
                     var20 = this._S.length * 3;
                  }

                  int var21 = 0;
                  int var22 = 0;
                  int var23 = 0;
                  int var24 = 0;

                  for(int var25 = 0; var25 < var20; ++var25) {
                     int[] var26 = this._S;
                     int var27 = this._S[var23] + var21 + var22;
                     var21 = this.rotateLeft(var27, 3);
                     var26[var23] = var21;
                     int var28 = var2[var24] + var21 + var22;
                     int var29 = var21 + var22;
                     var22 = this.rotateLeft(var28, var29);
                     var2[var24] = var22;
                     int var30 = var23 + 1;
                     int var31 = this._S.length;
                     var23 = var30 % var31;
                     int var32 = var24 + 1;
                     int var33 = var2.length;
                     var24 = var32 % var33;
                  }

                  return;
               }

               int[] var14 = this._S;
               int[] var15 = this._S;
               int var16 = var12 - 1;
               int var17 = var15[var16] + -1640531527;
               var14[var12] = var17;
               ++var12;
            }
         }

         int var5 = var3 / 4;
         int var6 = var2[var5];
         int var7 = var1[var3] & 255;
         int var8 = var3 % 4 * 8;
         int var9 = var7 << var8;
         int var10 = var6 + var9;
         var2[var5] = var10;
         ++var3;
      }
   }

   private void wordToBytes(int var1, byte[] var2, int var3) {
      byte var4 = (byte)var1;
      var2[var3] = var4;
      int var5 = var3 + 1;
      byte var6 = (byte)(var1 >> 8);
      var2[var5] = var6;
      int var7 = var3 + 2;
      byte var8 = (byte)(var1 >> 16);
      var2[var7] = var8;
      int var9 = var3 + 3;
      byte var10 = (byte)(var1 >> 24);
      var2[var9] = var10;
   }

   public String getAlgorithmName() {
      return "RC5-32";
   }

   public int getBlockSize() {
      return 8;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof RC5Parameters) {
         RC5Parameters var3 = (RC5Parameters)var2;
         int var4 = var3.getRounds();
         this._noRounds = var4;
         byte[] var5 = var3.getKey();
         this.setKey(var5);
      } else {
         if(!(var2 instanceof KeyParameter)) {
            StringBuilder var7 = (new StringBuilder()).append("invalid parameter passed to RC532 init - ");
            String var8 = var2.getClass().getName();
            String var9 = var7.append(var8).toString();
            throw new IllegalArgumentException(var9);
         }

         byte[] var6 = ((KeyParameter)var2).getKey();
         this.setKey(var6);
      }

      this.forEncryption = var1;
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
