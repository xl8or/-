package myorg.bouncycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import myorg.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import myorg.bouncycastle.crypto.modes.gcm.GCMUtil;
import myorg.bouncycastle.crypto.util.Pack;

public class Tables64kGCMMultiplier implements GCMMultiplier {

   private final int[][][] M;


   public Tables64kGCMMultiplier() {
      int[] var1 = new int[]{16, 256};
      int[][][] var2 = (int[][][])Array.newInstance(int[].class, var1);
      this.M = var2;
   }

   public void init(byte[] var1) {
      int[][] var2 = this.M[0];
      int[] var3 = new int[4];
      var2[0] = var3;
      int[][] var4 = this.M[0];
      int[] var5 = GCMUtil.asInts(var1);
      var4[128] = var5;

      int var6;
      for(var6 = 64; var6 >= 1; var6 >>= 1) {
         int[] var7 = new int[4];
         int[][] var8 = this.M[0];
         int var9 = var6 + var6;
         System.arraycopy(var8[var9], 0, var7, 0, 4);
         GCMUtil.multiplyP(var7);
         this.M[0][var6] = var7;
      }

      int var10 = 0;

      while(true) {
         for(int var11 = 2; var11 < 256; var11 += var11) {
            for(int var12 = 1; var12 < var11; ++var12) {
               int[] var13 = new int[4];
               System.arraycopy(this.M[var10][var11], 0, var13, 0, 4);
               int[] var14 = this.M[var10][var12];
               GCMUtil.xor(var13, var14);
               int[][] var15 = this.M[var10];
               int var16 = var11 + var12;
               var15[var16] = var13;
            }
         }

         ++var10;
         if(var10 == 16) {
            return;
         }

         int[][] var17 = this.M[var10];
         int[] var18 = new int[4];
         var17[0] = var18;

         for(var6 = 128; var6 > 0; var6 >>= 1) {
            int[] var19 = new int[4];
            int[][][] var20 = this.M;
            int var21 = var10 - 1;
            System.arraycopy(var20[var21][var6], 0, var19, 0, 4);
            GCMUtil.multiplyP8(var19);
            this.M[var10][var6] = var19;
         }
      }
   }

   public void multiplyH(byte[] var1) {
      int[] var2 = new int[4];

      for(int var3 = 15; var3 >= 0; var3 += -1) {
         int[][] var4 = this.M[var3];
         int var5 = var1[var3] & 255;
         int[] var6 = var4[var5];
         int var7 = var2[0];
         int var8 = var6[0];
         int var9 = var7 ^ var8;
         var2[0] = var9;
         int var10 = var2[1];
         int var11 = var6[1];
         int var12 = var10 ^ var11;
         var2[1] = var12;
         int var13 = var2[2];
         int var14 = var6[2];
         int var15 = var13 ^ var14;
         var2[2] = var15;
         int var16 = var2[3];
         int var17 = var6[3];
         int var18 = var16 ^ var17;
         var2[3] = var18;
      }

      Pack.intToBigEndian(var2[0], var1, 0);
      Pack.intToBigEndian(var2[1], var1, 4);
      Pack.intToBigEndian(var2[2], var1, 8);
      Pack.intToBigEndian(var2[3], var1, 12);
   }
}
