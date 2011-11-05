package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.StreamCipher;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class HC256Engine implements StreamCipher {

   private byte[] buf;
   private int cnt;
   private int idx;
   private boolean initialised;
   private byte[] iv;
   private byte[] key;
   private int[] p;
   private int[] q;


   public HC256Engine() {
      int[] var1 = new int[1024];
      this.p = var1;
      int[] var2 = new int[1024];
      this.q = var2;
      this.cnt = 0;
      byte[] var3 = new byte[4];
      this.buf = var3;
      this.idx = 0;
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

   private void init() {
      if(this.key.length != 32 && this.key.length != 16) {
         throw new IllegalArgumentException("The key must be 128/256 bits long");
      } else if(this.iv.length < 16) {
         throw new IllegalArgumentException("The IV must be at least 128 bits long");
      } else {
         if(this.key.length != 32) {
            byte[] var1 = new byte[32];
            byte[] var2 = this.key;
            int var3 = this.key.length;
            System.arraycopy(var2, 0, var1, 0, var3);
            byte[] var4 = this.key;
            int var5 = this.key.length;
            System.arraycopy(var4, 0, var1, 16, var5);
            this.key = var1;
         }

         if(this.iv.length < 32) {
            byte[] var6 = new byte[32];
            byte[] var7 = this.iv;
            int var8 = this.iv.length;
            System.arraycopy(var7, 0, var6, 0, var8);
            byte[] var9 = this.iv;
            int var10 = this.iv.length;
            int var11 = var6.length;
            int var12 = this.iv.length;
            int var13 = var11 - var12;
            System.arraycopy(var9, 0, var6, var10, var13);
            this.iv = var6;
         }

         this.cnt = 0;
         int[] var14 = new int[2560];

         for(int var15 = 0; var15 < 32; ++var15) {
            int var16 = var15 >> 2;
            int var17 = var14[var16];
            int var18 = this.key[var15] & 255;
            int var19 = (var15 & 3) * 8;
            int var20 = var18 << var19;
            int var21 = var17 | var20;
            var14[var16] = var21;
         }

         for(int var22 = 0; var22 < 32; ++var22) {
            int var23 = (var22 >> 2) + 8;
            int var24 = var14[var23];
            int var25 = this.iv[var22] & 255;
            int var26 = (var22 & 3) * 8;
            int var27 = var25 << var26;
            int var28 = var24 | var27;
            var14[var23] = var28;
         }

         for(int var29 = 16; var29 < 2560; ++var29) {
            int var30 = var29 - 2;
            int var31 = var14[var30];
            int var32 = var29 - 15;
            int var33 = var14[var32];
            int var34 = rotateRight(var31, 17);
            int var35 = rotateRight(var31, 19);
            int var36 = var34 ^ var35;
            int var37 = var31 >>> 10;
            int var38 = var36 ^ var37;
            int var39 = var29 - 7;
            int var40 = var14[var39];
            int var41 = var38 + var40;
            int var42 = rotateRight(var33, 7);
            int var43 = rotateRight(var33, 18);
            int var44 = var42 ^ var43;
            int var45 = var33 >>> 3;
            int var46 = var44 ^ var45;
            int var47 = var41 + var46;
            int var48 = var29 - 16;
            int var49 = var14[var48];
            int var50 = var47 + var49 + var29;
            var14[var29] = var50;
         }

         int[] var51 = this.p;
         System.arraycopy(var14, 512, var51, 0, 1024);
         int[] var52 = this.q;
         System.arraycopy(var14, 1536, var52, 0, 1024);

         for(int var53 = 0; var53 < 4096; ++var53) {
            int var54 = this.step();
         }

         this.cnt = 0;
      }
   }

   private static int rotateRight(int var0, int var1) {
      int var2 = var0 >>> var1;
      int var3 = -var1;
      int var4 = var0 << var3;
      return var2 | var4;
   }

   private int step() {
      int var1 = this.cnt & 1023;
      int var41;
      if(this.cnt < 1024) {
         int[] var2 = this.p;
         int var3 = var1 - 3 & 1023;
         int var4 = var2[var3];
         int[] var5 = this.p;
         int var6 = var1 - 1023 & 1023;
         int var7 = var5[var6];
         int[] var8 = this.p;
         int var9 = var8[var1];
         int[] var10 = this.p;
         int var11 = var1 - 10 & 1023;
         int var12 = var10[var11];
         int var13 = rotateRight(var4, 10);
         int var14 = rotateRight(var7, 23);
         int var15 = var13 ^ var14;
         int var16 = var12 + var15;
         int[] var17 = this.q;
         int var18 = (var4 ^ var7) & 1023;
         int var19 = var17[var18];
         int var20 = var16 + var19;
         int var21 = var9 + var20;
         var8[var1] = var21;
         int[] var22 = this.p;
         int var23 = var1 - 12 & 1023;
         int var24 = var22[var23];
         int[] var25 = this.q;
         int var26 = var24 & 255;
         int var27 = var25[var26];
         int[] var28 = this.q;
         int var29 = (var24 >> 8 & 255) + 256;
         int var30 = var28[var29];
         int var31 = var27 + var30;
         int[] var32 = this.q;
         int var33 = (var24 >> 16 & 255) + 512;
         int var34 = var32[var33];
         int var35 = var31 + var34;
         int[] var36 = this.q;
         int var37 = (var24 >> 24 & 255) + 768;
         int var38 = var36[var37];
         int var39 = var35 + var38;
         int var40 = this.p[var1];
         var41 = var39 ^ var40;
      } else {
         int[] var43 = this.q;
         int var44 = var1 - 3 & 1023;
         int var45 = var43[var44];
         int[] var46 = this.q;
         int var47 = var1 - 1023 & 1023;
         int var48 = var46[var47];
         int[] var49 = this.q;
         int var50 = var49[var1];
         int[] var51 = this.q;
         int var52 = var1 - 10 & 1023;
         int var53 = var51[var52];
         int var54 = rotateRight(var45, 10);
         int var55 = rotateRight(var48, 23);
         int var56 = var54 ^ var55;
         int var57 = var53 + var56;
         int[] var58 = this.p;
         int var59 = (var45 ^ var48) & 1023;
         int var60 = var58[var59];
         int var61 = var57 + var60;
         int var62 = var50 + var61;
         var49[var1] = var62;
         int[] var63 = this.q;
         int var64 = var1 - 12 & 1023;
         int var65 = var63[var64];
         int[] var66 = this.p;
         int var67 = var65 & 255;
         int var68 = var66[var67];
         int[] var69 = this.p;
         int var70 = (var65 >> 8 & 255) + 256;
         int var71 = var69[var70];
         int var72 = var68 + var71;
         int[] var73 = this.p;
         int var74 = (var65 >> 16 & 255) + 512;
         int var75 = var73[var74];
         int var76 = var72 + var75;
         int[] var77 = this.p;
         int var78 = (var65 >> 24 & 255) + 768;
         int var79 = var77[var78];
         int var80 = var76 + var79;
         int var81 = this.q[var1];
         var41 = var80 ^ var81;
      }

      int var42 = this.cnt + 1 & 2047;
      this.cnt = var42;
      return var41;
   }

   public String getAlgorithmName() {
      return "HC-256";
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
         StringBuilder var7 = (new StringBuilder()).append("Invalid parameter passed to HC256 init - ");
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
