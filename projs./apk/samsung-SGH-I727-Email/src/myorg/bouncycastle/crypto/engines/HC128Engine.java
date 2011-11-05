package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.StreamCipher;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class HC128Engine implements StreamCipher {

   private byte[] buf;
   private int cnt;
   private int idx;
   private boolean initialised;
   private byte[] iv;
   private byte[] key;
   private int[] p;
   private int[] q;


   public HC128Engine() {
      int[] var1 = new int[512];
      this.p = var1;
      int[] var2 = new int[512];
      this.q = var2;
      this.cnt = 0;
      byte[] var3 = new byte[4];
      this.buf = var3;
      this.idx = 0;
   }

   private static int dim(int var0, int var1) {
      return mod512(var0 - var1);
   }

   private static int f1(int var0) {
      int var1 = rotateRight(var0, 7);
      int var2 = rotateRight(var0, 18);
      int var3 = var1 ^ var2;
      int var4 = var0 >>> 3;
      return var3 ^ var4;
   }

   private static int f2(int var0) {
      int var1 = rotateRight(var0, 17);
      int var2 = rotateRight(var0, 19);
      int var3 = var1 ^ var2;
      int var4 = var0 >>> 10;
      return var3 ^ var4;
   }

   private int g1(int var1, int var2, int var3) {
      int var4 = rotateRight(var1, 10);
      int var5 = rotateRight(var3, 23);
      int var6 = var4 ^ var5;
      int var7 = rotateRight(var2, 8);
      return var6 + var7;
   }

   private int g2(int var1, int var2, int var3) {
      int var4 = rotateLeft(var1, 10);
      int var5 = rotateLeft(var3, 23);
      int var6 = var4 ^ var5;
      int var7 = rotateLeft(var2, 8);
      return var6 + var7;
   }

   private byte getByte() {
      if(this.idx == 0) {
         int var1 = this.step();
         byte[] var2 = this.buf;
         byte var3 = (byte)(var1 & 255);
         var2[0] = var3;
         int var4 = var1 >> 8;
         byte[] var5 = this.buf;
         byte var6 = (byte)(var4 & 255);
         var5[1] = var6;
         int var7 = var4 >> 8;
         byte[] var8 = this.buf;
         byte var9 = (byte)(var7 & 255);
         var8[2] = var9;
         int var10 = var7 >> 8;
         byte[] var11 = this.buf;
         byte var12 = (byte)(var10 & 255);
         var11[3] = var12;
      }

      byte[] var13 = this.buf;
      int var14 = this.idx;
      byte var15 = var13[var14];
      int var16 = this.idx + 1 & 3;
      this.idx = var16;
      return var15;
   }

   private int h1(int var1) {
      int[] var2 = this.q;
      int var3 = var1 & 255;
      int var4 = var2[var3];
      int[] var5 = this.q;
      int var6 = (var1 >> 16 & 255) + 256;
      int var7 = var5[var6];
      return var4 + var7;
   }

   private int h2(int var1) {
      int[] var2 = this.p;
      int var3 = var1 & 255;
      int var4 = var2[var3];
      int[] var5 = this.p;
      int var6 = (var1 >> 16 & 255) + 256;
      int var7 = var5[var6];
      return var4 + var7;
   }

   private void init() {
      if(this.key.length != 16) {
         throw new IllegalArgumentException("The key must be 128 bits long");
      } else {
         this.cnt = 0;
         int[] var1 = new int[1280];

         for(int var2 = 0; var2 < 16; ++var2) {
            int var3 = var2 >> 2;
            int var4 = var1[var3];
            int var5 = this.key[var2] & 255;
            int var6 = (var2 & 3) * 8;
            int var7 = var5 << var6;
            int var8 = var4 | var7;
            var1[var3] = var8;
         }

         System.arraycopy(var1, 0, var1, 4, 4);
         int var9 = 0;

         while(true) {
            int var10 = this.iv.length;
            if(var9 >= var10 || var9 >= 16) {
               System.arraycopy(var1, 8, var1, 12, 4);

               for(int var17 = 16; var17 < 1280; ++var17) {
                  int var18 = var17 - 2;
                  int var19 = f2(var1[var18]);
                  int var20 = var17 - 7;
                  int var21 = var1[var20];
                  int var22 = var19 + var21;
                  int var23 = var17 - 15;
                  int var24 = f1(var1[var23]);
                  int var25 = var22 + var24;
                  int var26 = var17 - 16;
                  int var27 = var1[var26];
                  int var28 = var25 + var27 + var17;
                  var1[var17] = var28;
               }

               int[] var29 = this.p;
               System.arraycopy(var1, 256, var29, 0, 512);
               int[] var30 = this.q;
               System.arraycopy(var1, 768, var30, 0, 512);

               for(int var31 = 0; var31 < 512; ++var31) {
                  int[] var32 = this.p;
                  int var33 = this.step();
                  var32[var31] = var33;
               }

               for(int var34 = 0; var34 < 512; ++var34) {
                  int[] var35 = this.q;
                  int var36 = this.step();
                  var35[var34] = var36;
               }

               this.cnt = 0;
               return;
            }

            int var11 = (var9 >> 2) + 8;
            int var12 = var1[var11];
            int var13 = this.iv[var9] & 255;
            int var14 = (var9 & 3) * 8;
            int var15 = var13 << var14;
            int var16 = var12 | var15;
            var1[var11] = var16;
            ++var9;
         }
      }
   }

   private static int mod1024(int var0) {
      return var0 & 1023;
   }

   private static int mod512(int var0) {
      return var0 & 511;
   }

   private static int rotateLeft(int var0, int var1) {
      int var2 = var0 << var1;
      int var3 = -var1;
      int var4 = var0 >>> var3;
      return var2 | var4;
   }

   private static int rotateRight(int var0, int var1) {
      int var2 = var0 >>> var1;
      int var3 = -var1;
      int var4 = var0 << var3;
      return var2 | var4;
   }

   private int step() {
      int var1 = mod512(this.cnt);
      int var20;
      if(this.cnt < 512) {
         int[] var2 = this.p;
         int var3 = var2[var1];
         int[] var4 = this.p;
         int var5 = dim(var1, 3);
         int var6 = var4[var5];
         int[] var7 = this.p;
         int var8 = dim(var1, 10);
         int var9 = var7[var8];
         int[] var10 = this.p;
         int var11 = dim(var1, 511);
         int var12 = var10[var11];
         int var13 = this.g1(var6, var9, var12);
         int var14 = var3 + var13;
         var2[var1] = var14;
         int[] var15 = this.p;
         int var16 = dim(var1, 12);
         int var17 = var15[var16];
         int var18 = this.h1(var17);
         int var19 = this.p[var1];
         var20 = var18 ^ var19;
      } else {
         int[] var22 = this.q;
         int var23 = var22[var1];
         int[] var24 = this.q;
         int var25 = dim(var1, 3);
         int var26 = var24[var25];
         int[] var27 = this.q;
         int var28 = dim(var1, 10);
         int var29 = var27[var28];
         int[] var30 = this.q;
         int var31 = dim(var1, 511);
         int var32 = var30[var31];
         int var33 = this.g2(var26, var29, var32);
         int var34 = var23 + var33;
         var22[var1] = var34;
         int[] var35 = this.q;
         int var36 = dim(var1, 12);
         int var37 = var35[var36];
         int var38 = this.h2(var37);
         int var39 = this.q[var1];
         var20 = var38 ^ var39;
      }

      int var21 = mod1024(this.cnt + 1);
      this.cnt = var21;
      return var20;
   }

   public String getAlgorithmName() {
      return "HC-128";
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      CipherParameters var3 = var2;
      if(var2 instanceof ParametersWithIV) {
         byte[] var4 = ((ParametersWithIV)var2).getIV();
         this.iv = var4;
         var3 = ((ParametersWithIV)var2).getParameters();
      } else {
         byte[] var6 = new byte[0];
         this.iv = var6;
      }

      if(var3 instanceof KeyParameter) {
         byte[] var5 = ((KeyParameter)var3).getKey();
         this.key = var5;
         this.init();
         this.initialised = (boolean)1;
      } else {
         StringBuilder var7 = (new StringBuilder()).append("Invalid parameter passed to HC128 init - ");
         String var8 = var2.getClass().getName();
         String var9 = var7.append(var8).toString();
         throw new IllegalArgumentException(var9);
      }
   }

   public void processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException {
      if(!this.initialised) {
         StringBuilder var6 = new StringBuilder();
         String var7 = this.getAlgorithmName();
         String var8 = var6.append(var7).append(" not initialised").toString();
         throw new IllegalStateException(var8);
      } else {
         int var9 = var2 + var3;
         int var10 = var1.length;
         if(var9 > var10) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var11 = var5 + var3;
            int var12 = var4.length;
            if(var11 > var12) {
               throw new DataLengthException("output buffer too short");
            } else {
               for(int var13 = 0; var13 < var3; ++var13) {
                  int var14 = var5 + var13;
                  int var15 = var2 + var13;
                  byte var16 = var1[var15];
                  byte var17 = this.getByte();
                  byte var18 = (byte)(var16 ^ var17);
                  var4[var14] = var18;
               }

            }
         }
      }
   }

   public void reset() {
      this.idx = 0;
      this.init();
   }

   public byte returnByte(byte var1) {
      return (byte)(this.getByte() ^ var1);
   }
}
