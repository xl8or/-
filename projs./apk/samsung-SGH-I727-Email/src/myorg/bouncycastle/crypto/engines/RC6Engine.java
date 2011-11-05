package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class RC6Engine implements BlockCipher {

   private static final int LGW = 5;
   private static final int P32 = -1209970333;
   private static final int Q32 = -1640531527;
   private static final int _noRounds = 20;
   private static final int bytesPerWord = 4;
   private static final int wordSize = 32;
   private int[] _S = null;
   private boolean forEncryption;


   public RC6Engine() {}

   private int bytesToWord(byte[] var1, int var2) {
      int var3 = 0;

      for(int var4 = 3; var4 >= 0; var4 += -1) {
         int var5 = var3 << 8;
         int var6 = var4 + var2;
         int var7 = var1[var6] & 255;
         var3 = var5 + var7;
      }

      return var3;
   }

   private int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = this.bytesToWord(var1, var2);
      int var6 = var2 + 4;
      int var7 = this.bytesToWord(var1, var6);
      int var8 = var2 + 8;
      int var9 = this.bytesToWord(var1, var8);
      int var10 = var2 + 12;
      int var11 = this.bytesToWord(var1, var10);
      int var12 = this._S[43];
      int var13 = var9 - var12;
      int var14 = this._S[42];
      int var15 = var5 - var14;

      for(int var16 = 20; var16 >= 1; var16 += -1) {
         int var17 = var11;
         var11 = var13;
         int var18 = var7;
         var7 = var15;
         int var20 = var15 * 2 + 1;
         int var21 = var15 * var20;
         int var22 = this.rotateLeft(var21, 5);
         int var23 = var13 * 2 + 1;
         int var24 = var13 * var23;
         int var25 = this.rotateLeft(var24, 5);
         int[] var26 = this._S;
         int var27 = var16 * 2 + 1;
         int var28 = var26[var27];
         int var29 = var18 - var28;
         var13 = this.rotateRight(var29, var22) ^ var25;
         int[] var30 = this._S;
         int var31 = var16 * 2;
         int var32 = var30[var31];
         int var33 = var17 - var32;
         var15 = this.rotateRight(var33, var25) ^ var22;
      }

      int var34 = this._S[1];
      int var35 = var11 - var34;
      int var36 = this._S[0];
      int var37 = var7 - var36;
      this.wordToBytes(var15, var3, var4);
      int var38 = var4 + 4;
      this.wordToBytes(var37, var3, var38);
      int var39 = var4 + 8;
      this.wordToBytes(var13, var3, var39);
      int var40 = var4 + 12;
      this.wordToBytes(var35, var3, var40);
      return 16;
   }

   private int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = this.bytesToWord(var1, var2);
      int var6 = var2 + 4;
      int var7 = this.bytesToWord(var1, var6);
      int var8 = var2 + 8;
      int var9 = this.bytesToWord(var1, var8);
      int var10 = var2 + 12;
      int var11 = this.bytesToWord(var1, var10);
      int var12 = this._S[0];
      int var13 = var7 + var12;
      int var14 = this._S[1];
      int var15 = var11 + var14;

      for(int var16 = 1; var16 <= 20; ++var16) {
         int var17 = var13 * 2 + 1;
         int var18 = var13 * var17;
         int var19 = this.rotateLeft(var18, 5);
         int var20 = var15 * 2 + 1;
         int var21 = var15 * var20;
         int var22 = this.rotateLeft(var21, 5);
         int var23 = var5 ^ var19;
         int var24 = this.rotateLeft(var23, var22);
         int[] var25 = this._S;
         int var26 = var16 * 2;
         int var27 = var25[var26];
         int var28 = var24 + var27;
         int var29 = var9 ^ var22;
         int var30 = this.rotateLeft(var29, var19);
         int[] var31 = this._S;
         int var32 = var16 * 2 + 1;
         int var33 = var31[var32];
         int var34 = var30 + var33;
         var5 = var13;
         var13 = var34;
         var9 = var15;
         var15 = var28;
      }

      int var36 = this._S[42];
      int var37 = var5 + var36;
      int var38 = this._S[43];
      int var39 = var9 + var38;
      this.wordToBytes(var37, var3, var4);
      int var40 = var4 + 4;
      this.wordToBytes(var13, var3, var40);
      int var41 = var4 + 8;
      this.wordToBytes(var39, var3, var41);
      int var42 = var4 + 12;
      this.wordToBytes(var15, var3, var42);
      return 16;
   }

   private int rotateLeft(int var1, int var2) {
      int var3 = var1 << var2;
      int var4 = -var2;
      int var5 = var1 >>> var4;
      return var3 | var5;
   }

   private int rotateRight(int var1, int var2) {
      int var3 = var1 >>> var2;
      int var4 = -var2;
      int var5 = var1 << var4;
      return var3 | var5;
   }

   private void setKey(byte[] var1) {
      if((var1.length + 3) / 4 == 0) {
         ;
      }

      int[] var2 = new int[(var1.length + 4 - 1) / 4];

      for(int var3 = var1.length - 1; var3 >= 0; var3 += -1) {
         int var4 = var3 / 4;
         int var5 = var3 / 4;
         int var6 = var2[var5] << 8;
         int var7 = var1[var3] & 255;
         int var8 = var6 + var7;
         var2[var4] = var8;
      }

      int[] var9 = new int[44];
      this._S = var9;
      this._S[0] = -1209970333;
      int var10 = 1;

      while(true) {
         int var11 = this._S.length;
         if(var10 >= var11) {
            int var16 = var2.length;
            int var17 = this._S.length;
            int var18;
            if(var16 > var17) {
               var18 = var2.length * 3;
            } else {
               var18 = this._S.length * 3;
            }

            int var19 = 0;
            int var20 = 0;
            int var21 = 0;
            int var22 = 0;

            for(int var23 = 0; var23 < var18; ++var23) {
               int[] var24 = this._S;
               int var25 = this._S[var21] + var19 + var20;
               var19 = this.rotateLeft(var25, 3);
               var24[var21] = var19;
               int var26 = var2[var22] + var19 + var20;
               int var27 = var19 + var20;
               var20 = this.rotateLeft(var26, var27);
               var2[var22] = var20;
               int var28 = var21 + 1;
               int var29 = this._S.length;
               var21 = var28 % var29;
               int var30 = var22 + 1;
               int var31 = var2.length;
               var22 = var30 % var31;
            }

            return;
         }

         int[] var12 = this._S;
         int[] var13 = this._S;
         int var14 = var10 - 1;
         int var15 = var13[var14] + -1640531527;
         var12[var10] = var15;
         ++var10;
      }
   }

   private void wordToBytes(int var1, byte[] var2, int var3) {
      for(int var4 = 0; var4 < 4; ++var4) {
         int var5 = var4 + var3;
         byte var6 = (byte)var1;
         var2[var5] = var6;
         var1 >>>= 8;
      }

   }

   public String getAlgorithmName() {
      return "RC6";
   }

   public int getBlockSize() {
      return 16;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(!(var2 instanceof KeyParameter)) {
         StringBuilder var3 = (new StringBuilder()).append("invalid parameter passed to RC6 init - ");
         String var4 = var2.getClass().getName();
         String var5 = var3.append(var4).toString();
         throw new IllegalArgumentException(var5);
      } else {
         KeyParameter var6 = (KeyParameter)var2;
         this.forEncryption = var1;
         byte[] var7 = var6.getKey();
         this.setKey(var7);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = this.getBlockSize();
      if(this._S == null) {
         throw new IllegalStateException("RC6 engine not initialised");
      } else {
         int var6 = var2 + var5;
         int var7 = var1.length;
         if(var6 > var7) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var8 = var4 + var5;
            int var9 = var3.length;
            if(var8 > var9) {
               throw new DataLengthException("output buffer too short");
            } else {
               int var10;
               if(this.forEncryption) {
                  var10 = this.encryptBlock(var1, var2, var3, var4);
               } else {
                  var10 = this.decryptBlock(var1, var2, var3, var4);
               }

               return var10;
            }
         }
      }
   }

   public void reset() {}
}
