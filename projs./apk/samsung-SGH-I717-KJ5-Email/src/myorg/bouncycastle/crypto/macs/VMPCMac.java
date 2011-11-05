package myorg.bouncycastle.crypto.macs;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class VMPCMac implements Mac {

   private byte[] P = null;
   private byte[] T;
   private byte g;
   private byte n = 0;
   private byte s = 0;
   private byte[] workingIV;
   private byte[] workingKey;
   private byte x1;
   private byte x2;
   private byte x3;
   private byte x4;


   public VMPCMac() {}

   private void initKey(byte[] var1, byte[] var2) {
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

      this.n = 0;
   }

   public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException {
      for(int var3 = 1; var3 < 25; ++var3) {
         byte[] var4 = this.P;
         byte var5 = this.s;
         byte[] var6 = this.P;
         int var7 = this.n & 255;
         byte var8 = var6[var7];
         int var9 = var5 + var8 & 255;
         byte var10 = var4[var9];
         this.s = var10;
         byte[] var11 = this.P;
         byte var12 = this.x4;
         byte var13 = this.x3;
         int var14 = var12 + var13 + var3 & 255;
         byte var15 = var11[var14];
         this.x4 = var15;
         byte[] var16 = this.P;
         byte var17 = this.x3;
         byte var18 = this.x2;
         int var19 = var17 + var18 + var3 & 255;
         byte var20 = var16[var19];
         this.x3 = var20;
         byte[] var21 = this.P;
         byte var22 = this.x2;
         byte var23 = this.x1;
         int var24 = var22 + var23 + var3 & 255;
         byte var25 = var21[var24];
         this.x2 = var25;
         byte[] var26 = this.P;
         byte var27 = this.x1;
         byte var28 = this.s;
         int var29 = var27 + var28 + var3 & 255;
         byte var30 = var26[var29];
         this.x1 = var30;
         byte[] var31 = this.T;
         int var32 = this.g & 31;
         byte[] var33 = this.T;
         int var34 = this.g & 31;
         byte var35 = var33[var34];
         byte var36 = this.x1;
         byte var37 = (byte)(var35 ^ var36);
         var31[var32] = var37;
         byte[] var38 = this.T;
         int var39 = this.g + 1 & 31;
         byte[] var40 = this.T;
         int var41 = this.g + 1 & 31;
         byte var42 = var40[var41];
         byte var43 = this.x2;
         byte var44 = (byte)(var42 ^ var43);
         var38[var39] = var44;
         byte[] var45 = this.T;
         int var46 = this.g + 2 & 31;
         byte[] var47 = this.T;
         int var48 = this.g + 2 & 31;
         byte var49 = var47[var48];
         byte var50 = this.x3;
         byte var51 = (byte)(var49 ^ var50);
         var45[var46] = var51;
         byte[] var52 = this.T;
         int var53 = this.g + 3 & 31;
         byte[] var54 = this.T;
         int var55 = this.g + 3 & 31;
         byte var56 = var54[var55];
         byte var57 = this.x4;
         byte var58 = (byte)(var56 ^ var57);
         var52[var53] = var58;
         byte var59 = (byte)(this.g + 4 & 31);
         this.g = var59;
         byte[] var60 = this.P;
         int var61 = this.n & 255;
         byte var62 = var60[var61];
         byte[] var63 = this.P;
         int var64 = this.n & 255;
         byte[] var65 = this.P;
         int var66 = this.s & 255;
         byte var67 = var65[var66];
         var63[var64] = var67;
         byte[] var68 = this.P;
         int var69 = this.s & 255;
         var68[var69] = var62;
         byte var70 = (byte)(this.n + 1 & 255);
         this.n = var70;
      }

      for(int var71 = 0; var71 < 768; ++var71) {
         byte[] var72 = this.P;
         byte var73 = this.s;
         byte[] var74 = this.P;
         int var75 = var71 & 255;
         byte var76 = var74[var75];
         int var77 = var73 + var76;
         byte[] var78 = this.T;
         int var79 = var71 & 31;
         byte var80 = var78[var79];
         int var81 = var77 + var80 & 255;
         byte var82 = var72[var81];
         this.s = var82;
         byte[] var83 = this.P;
         int var84 = var71 & 255;
         byte var85 = var83[var84];
         byte[] var86 = this.P;
         int var87 = var71 & 255;
         byte[] var88 = this.P;
         int var89 = this.s & 255;
         byte var90 = var88[var89];
         var86[var87] = var90;
         byte[] var91 = this.P;
         int var92 = this.s & 255;
         var91[var92] = var85;
      }

      byte[] var93 = new byte[20];

      for(int var94 = 0; var94 < 20; ++var94) {
         byte[] var95 = this.P;
         byte var96 = this.s;
         byte[] var97 = this.P;
         int var98 = var94 & 255;
         byte var99 = var97[var98];
         int var100 = var96 + var99 & 255;
         byte var101 = var95[var100];
         this.s = var101;
         byte[] var102 = this.P;
         byte[] var103 = this.P;
         byte[] var104 = this.P;
         int var105 = this.s & 255;
         int var106 = var104[var105] & 255;
         int var107 = var103[var106] + 1 & 255;
         byte var108 = var102[var107];
         var93[var94] = var108;
         byte[] var109 = this.P;
         int var110 = var94 & 255;
         byte var111 = var109[var110];
         byte[] var112 = this.P;
         int var113 = var94 & 255;
         byte[] var114 = this.P;
         int var115 = this.s & 255;
         byte var116 = var114[var115];
         var112[var113] = var116;
         byte[] var117 = this.P;
         int var118 = this.s & 255;
         var117[var118] = var111;
      }

      int var119 = var93.length;
      System.arraycopy(var93, 0, var1, var2, var119);
      this.reset();
      return var93.length;
   }

   public String getAlgorithmName() {
      return "VMPC-MAC";
   }

   public int getMacSize() {
      return 20;
   }

   public void init(CipherParameters var1) throws IllegalArgumentException {
      if(!(var1 instanceof ParametersWithIV)) {
         throw new IllegalArgumentException("VMPC-MAC Init parameters must include an IV");
      } else {
         ParametersWithIV var2 = (ParametersWithIV)var1;
         KeyParameter var3 = (KeyParameter)var2.getParameters();
         if(!(var2.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("VMPC-MAC Init parameters must include a key");
         } else {
            byte[] var4 = var2.getIV();
            this.workingIV = var4;
            if(this.workingIV != null && this.workingIV.length >= 1 && this.workingIV.length <= 768) {
               byte[] var5 = var3.getKey();
               this.workingKey = var5;
               this.reset();
            } else {
               throw new IllegalArgumentException("VMPC-MAC requires 1 to 768 bytes of IV");
            }
         }
      }
   }

   public void reset() {
      byte[] var1 = this.workingKey;
      byte[] var2 = this.workingIV;
      this.initKey(var1, var2);
      this.n = 0;
      this.x4 = 0;
      this.x3 = 0;
      this.x2 = 0;
      this.x1 = 0;
      this.g = 0;
      byte[] var3 = new byte[32];
      this.T = var3;

      for(int var4 = 0; var4 < 32; ++var4) {
         this.T[var4] = 0;
      }

   }

   public void update(byte var1) throws IllegalStateException {
      byte[] var2 = this.P;
      byte var3 = this.s;
      byte[] var4 = this.P;
      int var5 = this.n & 255;
      byte var6 = var4[var5];
      int var7 = var3 + var6 & 255;
      byte var8 = var2[var7];
      this.s = var8;
      byte[] var9 = this.P;
      byte[] var10 = this.P;
      byte[] var11 = this.P;
      int var12 = this.s & 255;
      int var13 = var11[var12] & 255;
      int var14 = var10[var13] + 1 & 255;
      byte var15 = (byte)(var9[var14] ^ var1);
      byte[] var16 = this.P;
      byte var17 = this.x4;
      byte var18 = this.x3;
      int var19 = var17 + var18 & 255;
      byte var20 = var16[var19];
      this.x4 = var20;
      byte[] var21 = this.P;
      byte var22 = this.x3;
      byte var23 = this.x2;
      int var24 = var22 + var23 & 255;
      byte var25 = var21[var24];
      this.x3 = var25;
      byte[] var26 = this.P;
      byte var27 = this.x2;
      byte var28 = this.x1;
      int var29 = var27 + var28 & 255;
      byte var30 = var26[var29];
      this.x2 = var30;
      byte[] var31 = this.P;
      byte var32 = this.x1;
      byte var33 = this.s;
      int var34 = var32 + var33 + var15 & 255;
      byte var35 = var31[var34];
      this.x1 = var35;
      byte[] var36 = this.T;
      int var37 = this.g & 31;
      byte[] var38 = this.T;
      int var39 = this.g & 31;
      byte var40 = var38[var39];
      byte var41 = this.x1;
      byte var42 = (byte)(var40 ^ var41);
      var36[var37] = var42;
      byte[] var43 = this.T;
      int var44 = this.g + 1 & 31;
      byte[] var45 = this.T;
      int var46 = this.g + 1 & 31;
      byte var47 = var45[var46];
      byte var48 = this.x2;
      byte var49 = (byte)(var47 ^ var48);
      var43[var44] = var49;
      byte[] var50 = this.T;
      int var51 = this.g + 2 & 31;
      byte[] var52 = this.T;
      int var53 = this.g + 2 & 31;
      byte var54 = var52[var53];
      byte var55 = this.x3;
      byte var56 = (byte)(var54 ^ var55);
      var50[var51] = var56;
      byte[] var57 = this.T;
      int var58 = this.g + 3 & 31;
      byte[] var59 = this.T;
      int var60 = this.g + 3 & 31;
      byte var61 = var59[var60];
      byte var62 = this.x4;
      byte var63 = (byte)(var61 ^ var62);
      var57[var58] = var63;
      byte var64 = (byte)(this.g + 4 & 31);
      this.g = var64;
      byte[] var65 = this.P;
      int var66 = this.n & 255;
      byte var67 = var65[var66];
      byte[] var68 = this.P;
      int var69 = this.n & 255;
      byte[] var70 = this.P;
      int var71 = this.s & 255;
      byte var72 = var70[var71];
      var68[var69] = var72;
      byte[] var73 = this.P;
      int var74 = this.s & 255;
      var73[var74] = var67;
      byte var75 = (byte)(this.n + 1 & 255);
      this.n = var75;
   }

   public void update(byte[] var1, int var2, int var3) throws DataLengthException, IllegalStateException {
      int var4 = var2 + var3;
      int var5 = var1.length;
      if(var4 > var5) {
         throw new DataLengthException("input buffer too short");
      } else {
         for(int var6 = 0; var6 < var3; ++var6) {
            byte var7 = var1[var6];
            this.update(var7);
         }

      }
   }
}
