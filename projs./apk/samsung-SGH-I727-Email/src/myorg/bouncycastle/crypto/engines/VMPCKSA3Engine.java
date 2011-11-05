package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.engines.VMPCEngine;

public class VMPCKSA3Engine extends VMPCEngine {

   public VMPCKSA3Engine() {}

   public String getAlgorithmName() {
      return "VMPC-KSA3";
   }

   protected void initKey(byte[] var1, byte[] var2) {
      this.s = 0;
      byte[] var3 = new byte[256];
      this.P = var3;

      for(int var4 = 0; var4 < 256; ++var4) {
         byte[] var5 = this.P;
         byte var6 = (byte)var4;
         var5[var4] = var6;
      }

      for(int var7 = 0; var7 < 768; ++var7) {
         byte[] var8 = this.P;
         byte var9 = this.s;
         byte[] var10 = this.P;
         int var11 = var7 & 255;
         byte var12 = var10[var11];
         int var13 = var9 + var12;
         int var14 = var1.length;
         int var15 = var7 % var14;
         byte var16 = var1[var15];
         int var17 = var13 + var16 & 255;
         byte var18 = var8[var17];
         this.s = var18;
         byte[] var19 = this.P;
         int var20 = var7 & 255;
         byte var21 = var19[var20];
         byte[] var22 = this.P;
         int var23 = var7 & 255;
         byte[] var24 = this.P;
         int var25 = this.s & 255;
         byte var26 = var24[var25];
         var22[var23] = var26;
         byte[] var27 = this.P;
         int var28 = this.s & 255;
         var27[var28] = var21;
      }

      for(int var29 = 0; var29 < 768; ++var29) {
         byte[] var30 = this.P;
         byte var31 = this.s;
         byte[] var32 = this.P;
         int var33 = var29 & 255;
         byte var34 = var32[var33];
         int var35 = var31 + var34;
         int var36 = var2.length;
         int var37 = var29 % var36;
         byte var38 = var2[var37];
         int var39 = var35 + var38 & 255;
         byte var40 = var30[var39];
         this.s = var40;
         byte[] var41 = this.P;
         int var42 = var29 & 255;
         byte var43 = var41[var42];
         byte[] var44 = this.P;
         int var45 = var29 & 255;
         byte[] var46 = this.P;
         int var47 = this.s & 255;
         byte var48 = var46[var47];
         var44[var45] = var48;
         byte[] var49 = this.P;
         int var50 = this.s & 255;
         var49[var50] = var43;
      }

      for(int var51 = 0; var51 < 768; ++var51) {
         byte[] var52 = this.P;
         byte var53 = this.s;
         byte[] var54 = this.P;
         int var55 = var51 & 255;
         byte var56 = var54[var55];
         int var57 = var53 + var56;
         int var58 = var1.length;
         int var59 = var51 % var58;
         byte var60 = var1[var59];
         int var61 = var57 + var60 & 255;
         byte var62 = var52[var61];
         this.s = var62;
         byte[] var63 = this.P;
         int var64 = var51 & 255;
         byte var65 = var63[var64];
         byte[] var66 = this.P;
         int var67 = var51 & 255;
         byte[] var68 = this.P;
         int var69 = this.s & 255;
         byte var70 = var68[var69];
         var66[var67] = var70;
         byte[] var71 = this.P;
         int var72 = this.s & 255;
         var71[var72] = var65;
      }

      this.n = 0;
   }
}
