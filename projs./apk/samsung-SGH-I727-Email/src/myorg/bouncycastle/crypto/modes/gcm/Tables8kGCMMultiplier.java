package myorg.bouncycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import myorg.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import myorg.bouncycastle.crypto.modes.gcm.GCMUtil;
import myorg.bouncycastle.crypto.util.Pack;

public class Tables8kGCMMultiplier implements GCMMultiplier {

   private final int[][][] M;


   public Tables8kGCMMultiplier() {
      int[] var1 = new int[]{32, 16};
      int[][][] var2 = (int[][][])Array.newInstance(int[].class, var1);
      this.M = var2;
   }

   public void init(byte[] var1) {
      int[][] var2 = this.M[0];
      int[] var3 = new int[4];
      var2[0] = var3;
      int[][] var4 = this.M[1];
      int[] var5 = new int[4];
      var4[0] = var5;
      int[][] var6 = this.M[1];
      int[] var7 = GCMUtil.asInts(var1);
      var6[8] = var7;

      for(int var8 = 4; var8 >= 1; var8 >>= 1) {
         int[] var9 = new int[4];
         int[][] var10 = this.M[1];
         int var11 = var8 + var8;
         System.arraycopy(var10[var11], 0, var9, 0, 4);
         GCMUtil.multiplyP(var9);
         this.M[1][var8] = var9;
      }

      int[] var12 = new int[4];
      System.arraycopy(this.M[1][1], 0, var12, 0, 4);
      GCMUtil.multiplyP(var12);
      this.M[0][8] = var12;

      int var13;
      for(var13 = 4; var13 >= 1; var13 >>= 1) {
         int[] var14 = new int[4];
         int[][] var15 = this.M[0];
         int var16 = var13 + var13;
         System.arraycopy(var15[var16], 0, var14, 0, 4);
         GCMUtil.multiplyP(var14);
         this.M[0][var13] = var14;
      }

      int var17 = 0;

      while(true) {
         int var23;
         for(byte var29 = 2; var29 < 16; var23 = var29 + var29) {
            for(int var18 = 1; var18 < var29; ++var18) {
               int[] var19 = new int[4];
               System.arraycopy(this.M[var17][var29], 0, var19, 0, 4);
               int[] var20 = this.M[var17][var18];
               GCMUtil.xor(var19, var20);
               int[][] var21 = this.M[var17];
               int var22 = var29 + var18;
               var21[var22] = var19;
            }
         }

         ++var17;
         if(var17 == 32) {
            return;
         }

         if(var17 > 1) {
            int[][] var24 = this.M[var17];
            int[] var25 = new int[4];
            var24[0] = var25;

            for(var13 = 8; var13 > 0; var13 >>= 1) {
               int[] var26 = new int[4];
               int[][][] var27 = this.M;
               int var28 = var17 - 2;
               System.arraycopy(var27[var28][var13], 0, var26, 0, 4);
               GCMUtil.multiplyP8(var26);
               this.M[var17][var13] = var26;
            }
         }
      }
   }

   public void multiplyH(byte[] var1) {
      int[] var2 = new int[4];

      for(int var3 = 15; var3 >= 0; var3 += -1) {
         int[][][] var4 = this.M;
         int var5 = var3 + var3;
         int[][] var6 = var4[var5];
         int var7 = var1[var3] & 15;
         int[] var8 = var6[var7];
         int var9 = var2[0];
         int var10 = var8[0];
         int var11 = var9 ^ var10;
         var2[0] = var11;
         int var12 = var2[1];
         int var13 = var8[1];
         int var14 = var12 ^ var13;
         var2[1] = var14;
         int var15 = var2[2];
         int var16 = var8[2];
         int var17 = var15 ^ var16;
         var2[2] = var17;
         int var18 = var2[3];
         int var19 = var8[3];
         int var20 = var18 ^ var19;
         var2[3] = var20;
         int[][][] var21 = this.M;
         int var22 = var3 + var3 + 1;
         int[][] var23 = var21[var22];
         int var24 = (var1[var3] & 240) >>> 4;
         int[] var25 = var23[var24];
         int var26 = var2[0];
         int var27 = var25[0];
         int var28 = var26 ^ var27;
         var2[0] = var28;
         int var29 = var2[1];
         int var30 = var25[1];
         int var31 = var29 ^ var30;
         var2[1] = var31;
         int var32 = var2[2];
         int var33 = var25[2];
         int var34 = var32 ^ var33;
         var2[2] = var34;
         int var35 = var2[3];
         int var36 = var25[3];
         int var37 = var35 ^ var36;
         var2[3] = var37;
      }

      Pack.intToBigEndian(var2[0], var1, 0);
      Pack.intToBigEndian(var2[1], var1, 4);
      Pack.intToBigEndian(var2[2], var1, 8);
      Pack.intToBigEndian(var2[3], var1, 12);
   }
}
