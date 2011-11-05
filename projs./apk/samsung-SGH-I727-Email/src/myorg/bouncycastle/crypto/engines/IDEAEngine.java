package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class IDEAEngine implements BlockCipher {

   private static final int BASE = 65537;
   protected static final int BLOCK_SIZE = 8;
   private static final int MASK = 65535;
   private int[] workingKey = null;


   public IDEAEngine() {}

   private int bytesToWord(byte[] var1, int var2) {
      int var3 = var1[var2] << 8 & '\uff00';
      int var4 = var2 + 1;
      int var5 = var1[var4] & 255;
      return var3 + var5;
   }

   private int[] expandKey(byte[] var1) {
      int[] var2 = new int[52];
      if(var1.length < 16) {
         byte[] var3 = new byte[16];
         int var4 = var3.length;
         int var5 = var1.length;
         int var6 = var4 - var5;
         int var7 = var1.length;
         System.arraycopy(var1, 0, var3, var6, var7);
         var1 = var3;
      }

      for(int var8 = 0; var8 < 8; ++var8) {
         int var9 = var8 * 2;
         int var10 = this.bytesToWord(var1, var9);
         var2[var8] = var10;
      }

      for(int var11 = 8; var11 < 52; ++var11) {
         if((var11 & 7) < 6) {
            int var12 = var11 - 7;
            int var13 = (var2[var12] & 127) << 9;
            int var14 = var11 - 6;
            int var15 = var2[var14] >> 7;
            int var16 = (var13 | var15) & '\uffff';
            var2[var11] = var16;
         } else if((var11 & 7) == 6) {
            int var17 = var11 - 7;
            int var18 = (var2[var17] & 127) << 9;
            int var19 = var11 - 14;
            int var20 = var2[var19] >> 7;
            int var21 = (var18 | var20) & '\uffff';
            var2[var11] = var21;
         } else {
            int var22 = var11 - 15;
            int var23 = (var2[var22] & 127) << 9;
            int var24 = var11 - 14;
            int var25 = var2[var24] >> 7;
            int var26 = (var23 | var25) & '\uffff';
            var2[var11] = var26;
         }
      }

      return var2;
   }

   private int[] generateWorkingKey(boolean var1, byte[] var2) {
      int[] var3;
      if(var1) {
         var3 = this.expandKey(var2);
      } else {
         int[] var4 = this.expandKey(var2);
         var3 = this.invertKey(var4);
      }

      return var3;
   }

   private void ideaFunc(int[] var1, byte[] var2, int var3, byte[] var4, int var5) {
      int var9 = this.bytesToWord(var2, var3);
      int var10 = var3 + 2;
      int var14 = this.bytesToWord(var2, var10);
      int var15 = var3 + 4;
      int var19 = this.bytesToWord(var2, var15);
      int var20 = var3 + 6;
      int var24 = this.bytesToWord(var2, var20);
      int var25 = 0;

      int var26;
      for(var26 = 0; var25 < 8; ++var25) {
         int var27 = var26 + 1;
         int var28 = var1[var26];
         int var29 = this.mul(var9, var28);
         int var30 = var27 + 1;
         int var31 = var1[var27];
         int var32 = var14 + var31 & '\uffff';
         int var33 = var30 + 1;
         int var34 = var1[var30];
         int var35 = var19 + var34 & '\uffff';
         int var36 = var33 + 1;
         int var37 = var1[var33];
         int var38 = this.mul(var24, var37);
         int var41 = var35 ^ var29;
         int var42 = var32 ^ var38;
         int var43 = var36 + 1;
         int var44 = var1[var36];
         int var45 = this.mul(var41, var44);
         int var46 = var42 + var45 & '\uffff';
         var26 = var43 + 1;
         int var47 = var1[var43];
         int var48 = this.mul(var46, var47);
         int var49 = var45 + var48 & '\uffff';
         var9 = var29 ^ var48;
         var24 = var38 ^ var49;
         var14 = var48 ^ var35;
         var19 = var49 ^ var32;
      }

      int var50 = var26 + 1;
      int var51 = var1[var26];
      int var52 = this.mul(var9, var51);
      this.wordToBytes(var52, var4, var5);
      int var57 = var50 + 1;
      int var58 = var1[var50] + var19;
      int var59 = var5 + 2;
      this.wordToBytes(var58, var4, var59);
      int var64 = var57 + 1;
      int var65 = var1[var57] + var14;
      int var66 = var5 + 4;
      this.wordToBytes(var65, var4, var66);
      int var71 = var1[var64];
      int var72 = this.mul(var24, var71);
      int var73 = var5 + 6;
      this.wordToBytes(var72, var4, var73);
   }

   private int[] invertKey(int[] var1) {
      int[] var2 = new int[52];
      int var3 = 0 + 1;
      int var4 = var1[0];
      int var5 = this.mulInv(var4);
      int var6 = var3 + 1;
      int var7 = var1[var3];
      int var8 = this.addInv(var7);
      int var9 = var6 + 1;
      int var10 = var1[var6];
      int var11 = this.addInv(var10);
      int var12 = var9 + 1;
      int var13 = var1[var9];
      int var14 = this.mulInv(var13);
      int var15 = 52 + -1;
      var2[var15] = var14;
      int var16 = var15 + -1;
      var2[var16] = var11;
      int var17 = var16 + -1;
      var2[var17] = var8;
      int var18 = var17 + -1;
      var2[var18] = var5;
      int var19 = 1;

      int var20;
      for(var20 = var12; var19 < 8; ++var19) {
         int var21 = var20 + 1;
         int var22 = var1[var20];
         int var23 = var21 + 1;
         int var24 = var1[var21];
         int var25 = var18 + -1;
         var2[var25] = var24;
         int var26 = var25 + -1;
         var2[var26] = var22;
         int var27 = var23 + 1;
         int var28 = var1[var23];
         int var29 = this.mulInv(var28);
         int var30 = var27 + 1;
         int var31 = var1[var27];
         int var32 = this.addInv(var31);
         int var33 = var30 + 1;
         int var34 = var1[var30];
         int var35 = this.addInv(var34);
         var20 = var33 + 1;
         int var36 = var1[var33];
         int var37 = this.mulInv(var36);
         int var38 = var26 + -1;
         var2[var38] = var37;
         int var39 = var38 + -1;
         var2[var39] = var32;
         int var40 = var39 + -1;
         var2[var40] = var35;
         var18 = var40 + -1;
         var2[var18] = var29;
      }

      int var41 = var20 + 1;
      int var42 = var1[var20];
      int var43 = var41 + 1;
      int var44 = var1[var41];
      int var45 = var18 + -1;
      var2[var45] = var44;
      int var46 = var45 + -1;
      var2[var46] = var42;
      int var47 = var43 + 1;
      int var48 = var1[var43];
      int var49 = this.mulInv(var48);
      int var50 = var47 + 1;
      int var51 = var1[var47];
      int var52 = this.addInv(var51);
      int var53 = var50 + 1;
      int var54 = var1[var50];
      int var55 = this.addInv(var54);
      int var56 = var1[var53];
      int var57 = this.mulInv(var56);
      int var58 = var46 + -1;
      var2[var58] = var57;
      int var59 = var58 + -1;
      var2[var59] = var55;
      int var60 = var59 + -1;
      var2[var60] = var52;
      int var61 = var60 + -1;
      var2[var61] = var49;
      return var2;
   }

   private int mul(int var1, int var2) {
      int var3 = 65537;
      int var4;
      if(var1 == 0) {
         var4 = 65537 - var2;
      } else if(var2 == 0) {
         var4 = 65537 - var1;
      } else {
         int var5 = var1 * var2;
         int var6 = var5 & '\uffff';
         int var7 = var5 >>> 16;
         var3 = var6 - var7;
         byte var8;
         if(var6 < var7) {
            var8 = 1;
         } else {
            var8 = 0;
         }

         var4 = var3 + var8;
      }

      return var4 & '\uffff';
   }

   private int mulInv(int var1) {
      int var2;
      if(var1 < 2) {
         var2 = var1;
      } else {
         int var3 = 1;
         int var4 = 65537 / var1;
         int var5 = 65537 % var1;

         while(true) {
            if(var5 == 1) {
               var2 = 1 - var4 & '\uffff';
               break;
            }

            int var6 = var1 / var5;
            var1 %= var5;
            var3 = var4 * var6 + var3 & '\uffff';
            if(var1 == 1) {
               var2 = var3;
               break;
            }

            int var7 = var5 / var1;
            var5 %= var1;
            var4 = var3 * var7 + var4 & '\uffff';
         }
      }

      return var2;
   }

   private void wordToBytes(int var1, byte[] var2, int var3) {
      byte var4 = (byte)(var1 >>> 8);
      var2[var3] = var4;
      int var5 = var3 + 1;
      byte var6 = (byte)var1;
      var2[var5] = var6;
   }

   int addInv(int var1) {
      return 0 - var1 & '\uffff';
   }

   public String getAlgorithmName() {
      return "IDEA";
   }

   public int getBlockSize() {
      return 8;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof KeyParameter) {
         byte[] var3 = ((KeyParameter)var2).getKey();
         int[] var4 = this.generateWorkingKey(var1, var3);
         this.workingKey = var4;
      } else {
         StringBuilder var5 = (new StringBuilder()).append("invalid parameter passed to IDEA init - ");
         String var6 = var2.getClass().getName();
         String var7 = var5.append(var6).toString();
         throw new IllegalArgumentException(var7);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(this.workingKey == null) {
         throw new IllegalStateException("IDEA engine not initialised");
      } else {
         int var5 = var2 + 8;
         int var6 = var1.length;
         if(var5 > var6) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var7 = var4 + 8;
            int var8 = var3.length;
            if(var7 > var8) {
               throw new DataLengthException("output buffer too short");
            } else {
               int[] var9 = this.workingKey;
               this.ideaFunc(var9, var1, var2, var3, var4);
               return 8;
            }
         }
      }
   }

   public void reset() {}
}
