package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.StreamCipher;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class ISAACEngine implements StreamCipher {

   private int a = 0;
   private int b = 0;
   private int c = 0;
   private int[] engineState = null;
   private int index = 0;
   private boolean initialised;
   private byte[] keyStream;
   private int[] results = null;
   private final int sizeL = 8;
   private final int stateArraySize = 256;
   private byte[] workingKey;


   public ISAACEngine() {
      byte[] var1 = new byte[1024];
      this.keyStream = var1;
      this.workingKey = null;
      this.initialised = (boolean)0;
   }

   private int byteToIntLittle(byte[] var1, int var2) {
      int var3 = var2 + 1;
      int var4 = var1[var2] & 255;
      int var5 = var3 + 1;
      int var6 = (var1[var3] & 255) << 8;
      int var7 = var4 | var6;
      int var8 = var5 + 1;
      int var9 = (var1[var5] & 255) << 16;
      int var10 = var7 | var9;
      int var11 = var8 + 1;
      int var12 = var1[var8] << 24;
      return var10 | var12;
   }

   private byte[] intToByteLittle(int var1) {
      byte[] var2 = new byte[4];
      byte var3 = (byte)var1;
      var2[3] = var3;
      byte var4 = (byte)(var1 >>> 8);
      var2[2] = var4;
      byte var5 = (byte)(var1 >>> 16);
      var2[1] = var5;
      byte var6 = (byte)(var1 >>> 24);
      var2[0] = var6;
      return var2;
   }

   private byte[] intToByteLittle(int[] var1) {
      byte[] var2 = new byte[var1.length * 4];
      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = var1.length;
         if(var3 >= var5) {
            return var2;
         }

         int var6 = var1[var3];
         System.arraycopy(this.intToByteLittle(var6), 0, var2, var4, 4);
         ++var3;
         var4 += 4;
      }
   }

   private void isaac() {
      int var1 = this.b;
      int var2 = this.c + 1;
      this.c = var2;
      int var3 = var1 + var2;
      this.b = var3;

      for(int var4 = 0; var4 < 256; ++var4) {
         int var5 = this.engineState[var4];
         switch(var4 & 3) {
         case 0:
            int var23 = this.a;
            int var24 = this.a << 13;
            int var25 = var23 ^ var24;
            this.a = var25;
            break;
         case 1:
            int var26 = this.a;
            int var27 = this.a >>> 6;
            int var28 = var26 ^ var27;
            this.a = var28;
            break;
         case 2:
            int var29 = this.a;
            int var30 = this.a << 2;
            int var31 = var29 ^ var30;
            this.a = var31;
            break;
         case 3:
            int var32 = this.a;
            int var33 = this.a >>> 16;
            int var34 = var32 ^ var33;
            this.a = var34;
         }

         int var6 = this.a;
         int[] var7 = this.engineState;
         int var8 = var4 + 128 & 255;
         int var9 = var7[var8];
         int var10 = var6 + var9;
         this.a = var10;
         int[] var11 = this.engineState;
         int[] var12 = this.engineState;
         int var13 = var5 >>> 2 & 255;
         int var14 = var12[var13];
         int var15 = this.a;
         int var16 = var14 + var15;
         int var17 = this.b;
         int var18 = var16 + var17;
         var11[var4] = var18;
         int[] var19 = this.results;
         int[] var20 = this.engineState;
         int var21 = var18 >>> 10 & 255;
         int var22 = var20[var21] + var5;
         this.b = var22;
         var19[var4] = var22;
      }

   }

   private void mix(int[] var1) {
      int var2 = var1[0];
      int var3 = var1[1] << 11;
      int var4 = var2 ^ var3;
      var1[0] = var4;
      int var5 = var1[3];
      int var6 = var1[0];
      int var7 = var5 + var6;
      var1[3] = var7;
      int var8 = var1[1];
      int var9 = var1[2];
      int var10 = var8 + var9;
      var1[1] = var10;
      int var11 = var1[1];
      int var12 = var1[2] >>> 2;
      int var13 = var11 ^ var12;
      var1[1] = var13;
      int var14 = var1[4];
      int var15 = var1[1];
      int var16 = var14 + var15;
      var1[4] = var16;
      int var17 = var1[2];
      int var18 = var1[3];
      int var19 = var17 + var18;
      var1[2] = var19;
      int var20 = var1[2];
      int var21 = var1[3] << 8;
      int var22 = var20 ^ var21;
      var1[2] = var22;
      int var23 = var1[5];
      int var24 = var1[2];
      int var25 = var23 + var24;
      var1[5] = var25;
      int var26 = var1[3];
      int var27 = var1[4];
      int var28 = var26 + var27;
      var1[3] = var28;
      int var29 = var1[3];
      int var30 = var1[4] >>> 16;
      int var31 = var29 ^ var30;
      var1[3] = var31;
      int var32 = var1[6];
      int var33 = var1[3];
      int var34 = var32 + var33;
      var1[6] = var34;
      int var35 = var1[4];
      int var36 = var1[5];
      int var37 = var35 + var36;
      var1[4] = var37;
      int var38 = var1[4];
      int var39 = var1[5] << 10;
      int var40 = var38 ^ var39;
      var1[4] = var40;
      int var41 = var1[7];
      int var42 = var1[4];
      int var43 = var41 + var42;
      var1[7] = var43;
      int var44 = var1[5];
      int var45 = var1[6];
      int var46 = var44 + var45;
      var1[5] = var46;
      int var47 = var1[5];
      int var48 = var1[6] >>> 4;
      int var49 = var47 ^ var48;
      var1[5] = var49;
      int var50 = var1[0];
      int var51 = var1[5];
      int var52 = var50 + var51;
      var1[0] = var52;
      int var53 = var1[6];
      int var54 = var1[7];
      int var55 = var53 + var54;
      var1[6] = var55;
      int var56 = var1[6];
      int var57 = var1[7] << 8;
      int var58 = var56 ^ var57;
      var1[6] = var58;
      int var59 = var1[1];
      int var60 = var1[6];
      int var61 = var59 + var60;
      var1[1] = var61;
      int var62 = var1[7];
      int var63 = var1[0];
      int var64 = var62 + var63;
      var1[7] = var64;
      int var65 = var1[7];
      int var66 = var1[0] >>> 9;
      int var67 = var65 ^ var66;
      var1[7] = var67;
      int var68 = var1[2];
      int var69 = var1[7];
      int var70 = var68 + var69;
      var1[2] = var70;
      int var71 = var1[0];
      int var72 = var1[1];
      int var73 = var71 + var72;
      var1[0] = var73;
   }

   private void setKey(byte[] var1) {
      this.workingKey = var1;
      if(this.engineState == null) {
         int[] var2 = new int[256];
         this.engineState = var2;
      }

      if(this.results == null) {
         int[] var3 = new int[256];
         this.results = var3;
      }

      for(int var4 = 0; var4 < 256; ++var4) {
         int[] var5 = this.engineState;
         this.results[var4] = 0;
         var5[var4] = 0;
      }

      this.c = 0;
      this.b = 0;
      this.a = 0;
      this.index = 0;
      int var6 = var1.length;
      int var7 = var1.length & 3;
      byte[] var8 = new byte[var6 + var7];
      int var9 = var1.length;
      System.arraycopy(var1, 0, var8, 0, var9);
      int var10 = 0;

      while(true) {
         int var11 = var8.length;
         if(var10 >= var11) {
            int[] var15 = new int[8];

            for(int var16 = 0; var16 < 8; ++var16) {
               var15[var16] = -1640531527;
            }

            for(int var17 = 0; var17 < 4; ++var17) {
               this.mix(var15);
            }

            for(int var18 = 0; var18 < 2; ++var18) {
               for(int var19 = 0; var19 < 256; var19 += 8) {
                  for(int var20 = 0; var20 < 8; ++var20) {
                     int var21 = var15[var20];
                     int var24;
                     if(var18 < 1) {
                        int[] var22 = this.results;
                        int var23 = var19 + var20;
                        var24 = var22[var23];
                     } else {
                        int[] var26 = this.engineState;
                        int var27 = var19 + var20;
                        var24 = var26[var27];
                     }

                     int var25 = var21 + var24;
                     var15[var20] = var25;
                  }

                  this.mix(var15);

                  for(int var28 = 0; var28 < 8; ++var28) {
                     int[] var29 = this.engineState;
                     int var30 = var19 + var28;
                     int var31 = var15[var28];
                     var29[var30] = var31;
                  }
               }
            }

            this.isaac();
            this.initialised = (boolean)1;
            return;
         }

         int[] var12 = this.results;
         int var13 = var10 >> 2;
         int var14 = this.byteToIntLittle(var8, var10);
         var12[var13] = var14;
         var10 += 4;
      }
   }

   public String getAlgorithmName() {
      return "ISAAC";
   }

   public void init(boolean var1, CipherParameters var2) {
      if(!(var2 instanceof KeyParameter)) {
         StringBuilder var3 = (new StringBuilder()).append("invalid parameter passed to ISAAC init - ");
         String var4 = var2.getClass().getName();
         String var5 = var3.append(var4).toString();
         throw new IllegalArgumentException(var5);
      } else {
         byte[] var6 = ((KeyParameter)var2).getKey();
         this.setKey(var6);
      }
   }

   public void processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) {
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
                  if(this.index == 0) {
                     this.isaac();
                     int[] var14 = this.results;
                     byte[] var15 = this.intToByteLittle(var14);
                     this.keyStream = var15;
                  }

                  int var16 = var13 + var5;
                  byte[] var17 = this.keyStream;
                  int var18 = this.index;
                  byte var19 = var17[var18];
                  int var20 = var13 + var2;
                  byte var21 = var1[var20];
                  byte var22 = (byte)(var19 ^ var21);
                  var4[var16] = var22;
                  int var23 = this.index + 1 & 1023;
                  this.index = var23;
               }

            }
         }
      }
   }

   public void reset() {
      byte[] var1 = this.workingKey;
      this.setKey(var1);
   }

   public byte returnByte(byte var1) {
      if(this.index == 0) {
         this.isaac();
         int[] var2 = this.results;
         byte[] var3 = this.intToByteLittle(var2);
         this.keyStream = var3;
      }

      byte[] var4 = this.keyStream;
      int var5 = this.index;
      byte var6 = (byte)(var4[var5] ^ var1);
      int var7 = this.index + 1 & 1023;
      this.index = var7;
      return var6;
   }
}
