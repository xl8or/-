package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.digests.GeneralDigest;

public class MD4Digest extends GeneralDigest {

   private static final int DIGEST_LENGTH = 16;
   private static final int S11 = 3;
   private static final int S12 = 7;
   private static final int S13 = 11;
   private static final int S14 = 19;
   private static final int S21 = 3;
   private static final int S22 = 5;
   private static final int S23 = 9;
   private static final int S24 = 13;
   private static final int S31 = 3;
   private static final int S32 = 9;
   private static final int S33 = 11;
   private static final int S34 = 15;
   private int H1;
   private int H2;
   private int H3;
   private int H4;
   private int[] X;
   private int xOff;


   public MD4Digest() {
      int[] var1 = new int[16];
      this.X = var1;
      this.reset();
   }

   public MD4Digest(MD4Digest var1) {
      super(var1);
      int[] var2 = new int[16];
      this.X = var2;
      int var3 = var1.H1;
      this.H1 = var3;
      int var4 = var1.H2;
      this.H2 = var4;
      int var5 = var1.H3;
      this.H3 = var5;
      int var6 = var1.H4;
      this.H4 = var6;
      int[] var7 = var1.X;
      int[] var8 = this.X;
      int var9 = var1.X.length;
      System.arraycopy(var7, 0, var8, 0, var9);
      int var10 = var1.xOff;
      this.xOff = var10;
   }

   private int F(int var1, int var2, int var3) {
      int var4 = var1 & var2;
      int var5 = ~var1 & var3;
      return var4 | var5;
   }

   private int G(int var1, int var2, int var3) {
      int var4 = var1 & var2;
      int var5 = var1 & var3;
      int var6 = var4 | var5;
      int var7 = var2 & var3;
      return var6 | var7;
   }

   private int H(int var1, int var2, int var3) {
      return var1 ^ var2 ^ var3;
   }

   private int rotateLeft(int var1, int var2) {
      int var3 = var1 << var2;
      int var4 = 32 - var2;
      int var5 = var1 >>> var4;
      return var3 | var5;
   }

   private void unpackWord(int var1, byte[] var2, int var3) {
      byte var4 = (byte)var1;
      var2[var3] = var4;
      int var5 = var3 + 1;
      byte var6 = (byte)(var1 >>> 8);
      var2[var5] = var6;
      int var7 = var3 + 2;
      byte var8 = (byte)(var1 >>> 16);
      var2[var7] = var8;
      int var9 = var3 + 3;
      byte var10 = (byte)(var1 >>> 24);
      var2[var9] = var10;
   }

   public int doFinal(byte[] var1, int var2) {
      this.finish();
      int var3 = this.H1;
      this.unpackWord(var3, var1, var2);
      int var4 = this.H2;
      int var5 = var2 + 4;
      this.unpackWord(var4, var1, var5);
      int var6 = this.H3;
      int var7 = var2 + 8;
      this.unpackWord(var6, var1, var7);
      int var8 = this.H4;
      int var9 = var2 + 12;
      this.unpackWord(var8, var1, var9);
      this.reset();
      return 16;
   }

   public String getAlgorithmName() {
      return "MD4";
   }

   public int getDigestSize() {
      return 16;
   }

   protected void processBlock() {
      int var1 = this.H1;
      int var2 = this.H2;
      int var3 = this.H3;
      int var4 = this.H4;
      int var5 = this.F(var2, var3, var4) + var1;
      int var6 = this.X[0];
      int var7 = var5 + var6;
      int var8 = this.rotateLeft(var7, 3);
      int var9 = this.F(var8, var2, var3) + var4;
      int var10 = this.X[1];
      int var11 = var9 + var10;
      int var12 = this.rotateLeft(var11, 7);
      int var13 = this.F(var12, var8, var2) + var3;
      int var14 = this.X[2];
      int var15 = var13 + var14;
      int var16 = this.rotateLeft(var15, 11);
      int var17 = this.F(var16, var12, var8) + var2;
      int var18 = this.X[3];
      int var19 = var17 + var18;
      int var20 = this.rotateLeft(var19, 19);
      int var21 = this.F(var20, var16, var12) + var8;
      int var22 = this.X[4];
      int var23 = var21 + var22;
      int var24 = this.rotateLeft(var23, 3);
      int var25 = this.F(var24, var20, var16) + var12;
      int var26 = this.X[5];
      int var27 = var25 + var26;
      int var28 = this.rotateLeft(var27, 7);
      int var29 = this.F(var28, var24, var20) + var16;
      int var30 = this.X[6];
      int var31 = var29 + var30;
      int var32 = this.rotateLeft(var31, 11);
      int var33 = this.F(var32, var28, var24) + var20;
      int var34 = this.X[7];
      int var35 = var33 + var34;
      int var36 = this.rotateLeft(var35, 19);
      int var37 = this.F(var36, var32, var28) + var24;
      int var38 = this.X[8];
      int var39 = var37 + var38;
      int var40 = this.rotateLeft(var39, 3);
      int var41 = this.F(var40, var36, var32) + var28;
      int var42 = this.X[9];
      int var43 = var41 + var42;
      int var44 = this.rotateLeft(var43, 7);
      int var45 = this.F(var44, var40, var36) + var32;
      int var46 = this.X[10];
      int var47 = var45 + var46;
      int var48 = this.rotateLeft(var47, 11);
      int var49 = this.F(var48, var44, var40) + var36;
      int var50 = this.X[11];
      int var51 = var49 + var50;
      int var52 = this.rotateLeft(var51, 19);
      int var53 = this.F(var52, var48, var44) + var40;
      int var54 = this.X[12];
      int var55 = var53 + var54;
      int var56 = this.rotateLeft(var55, 3);
      int var57 = this.F(var56, var52, var48) + var44;
      int var58 = this.X[13];
      int var59 = var57 + var58;
      int var60 = this.rotateLeft(var59, 7);
      int var61 = this.F(var60, var56, var52) + var48;
      int var62 = this.X[14];
      int var63 = var61 + var62;
      int var64 = this.rotateLeft(var63, 11);
      int var65 = this.F(var64, var60, var56) + var52;
      int var66 = this.X[15];
      int var67 = var65 + var66;
      int var68 = this.rotateLeft(var67, 19);
      int var69 = this.G(var68, var64, var60) + var56;
      int var70 = this.X[0];
      int var71 = var69 + var70 + 1518500249;
      int var72 = this.rotateLeft(var71, 3);
      int var73 = this.G(var72, var68, var64) + var60;
      int var74 = this.X[4];
      int var75 = var73 + var74 + 1518500249;
      int var76 = this.rotateLeft(var75, 5);
      int var77 = this.G(var76, var72, var68) + var64;
      int var78 = this.X[8];
      int var79 = var77 + var78 + 1518500249;
      int var80 = this.rotateLeft(var79, 9);
      int var81 = this.G(var80, var76, var72) + var68;
      int var82 = this.X[12];
      int var83 = var81 + var82 + 1518500249;
      int var84 = this.rotateLeft(var83, 13);
      int var85 = this.G(var84, var80, var76) + var72;
      int var86 = this.X[1];
      int var87 = var85 + var86 + 1518500249;
      int var88 = this.rotateLeft(var87, 3);
      int var89 = this.G(var88, var84, var80) + var76;
      int var90 = this.X[5];
      int var91 = var89 + var90 + 1518500249;
      int var92 = this.rotateLeft(var91, 5);
      int var93 = this.G(var92, var88, var84) + var80;
      int var94 = this.X[9];
      int var95 = var93 + var94 + 1518500249;
      int var96 = this.rotateLeft(var95, 9);
      int var97 = this.G(var96, var92, var88) + var84;
      int var98 = this.X[13];
      int var99 = var97 + var98 + 1518500249;
      int var100 = this.rotateLeft(var99, 13);
      int var101 = this.G(var100, var96, var92) + var88;
      int var102 = this.X[2];
      int var103 = var101 + var102 + 1518500249;
      int var104 = this.rotateLeft(var103, 3);
      int var105 = this.G(var104, var100, var96) + var92;
      int var106 = this.X[6];
      int var107 = var105 + var106 + 1518500249;
      int var108 = this.rotateLeft(var107, 5);
      int var109 = this.G(var108, var104, var100) + var96;
      int var110 = this.X[10];
      int var111 = var109 + var110 + 1518500249;
      int var112 = this.rotateLeft(var111, 9);
      int var113 = this.G(var112, var108, var104) + var100;
      int var114 = this.X[14];
      int var115 = var113 + var114 + 1518500249;
      int var116 = this.rotateLeft(var115, 13);
      int var117 = this.G(var116, var112, var108) + var104;
      int var118 = this.X[3];
      int var119 = var117 + var118 + 1518500249;
      int var120 = this.rotateLeft(var119, 3);
      int var121 = this.G(var120, var116, var112) + var108;
      int var122 = this.X[7];
      int var123 = var121 + var122 + 1518500249;
      int var124 = this.rotateLeft(var123, 5);
      int var125 = this.G(var124, var120, var116) + var112;
      int var126 = this.X[11];
      int var127 = var125 + var126 + 1518500249;
      int var128 = this.rotateLeft(var127, 9);
      int var129 = this.G(var128, var124, var120) + var116;
      int var130 = this.X[15];
      int var131 = var129 + var130 + 1518500249;
      int var132 = this.rotateLeft(var131, 13);
      int var133 = this.H(var132, var128, var124) + var120;
      int var134 = this.X[0];
      int var135 = var133 + var134 + 1859775393;
      int var136 = this.rotateLeft(var135, 3);
      int var137 = this.H(var136, var132, var128) + var124;
      int var138 = this.X[8];
      int var139 = var137 + var138 + 1859775393;
      int var140 = this.rotateLeft(var139, 9);
      int var141 = this.H(var140, var136, var132) + var128;
      int var142 = this.X[4];
      int var143 = var141 + var142 + 1859775393;
      int var144 = this.rotateLeft(var143, 11);
      int var145 = this.H(var144, var140, var136) + var132;
      int var146 = this.X[12];
      int var147 = var145 + var146 + 1859775393;
      int var148 = this.rotateLeft(var147, 15);
      int var149 = this.H(var148, var144, var140) + var136;
      int var150 = this.X[2];
      int var151 = var149 + var150 + 1859775393;
      int var152 = this.rotateLeft(var151, 3);
      int var153 = this.H(var152, var148, var144) + var140;
      int var154 = this.X[10];
      int var155 = var153 + var154 + 1859775393;
      int var156 = this.rotateLeft(var155, 9);
      int var157 = this.H(var156, var152, var148) + var144;
      int var158 = this.X[6];
      int var159 = var157 + var158 + 1859775393;
      int var160 = this.rotateLeft(var159, 11);
      int var161 = this.H(var160, var156, var152) + var148;
      int var162 = this.X[14];
      int var163 = var161 + var162 + 1859775393;
      int var164 = this.rotateLeft(var163, 15);
      int var165 = this.H(var164, var160, var156) + var152;
      int var166 = this.X[1];
      int var167 = var165 + var166 + 1859775393;
      int var168 = this.rotateLeft(var167, 3);
      int var169 = this.H(var168, var164, var160) + var156;
      int var170 = this.X[9];
      int var171 = var169 + var170 + 1859775393;
      int var172 = this.rotateLeft(var171, 9);
      int var173 = this.H(var172, var168, var164) + var160;
      int var174 = this.X[5];
      int var175 = var173 + var174 + 1859775393;
      int var176 = this.rotateLeft(var175, 11);
      int var177 = this.H(var176, var172, var168) + var164;
      int var178 = this.X[13];
      int var179 = var177 + var178 + 1859775393;
      int var180 = this.rotateLeft(var179, 15);
      int var181 = this.H(var180, var176, var172) + var168;
      int var182 = this.X[3];
      int var183 = var181 + var182 + 1859775393;
      int var184 = this.rotateLeft(var183, 3);
      int var185 = this.H(var184, var180, var176) + var172;
      int var186 = this.X[11];
      int var187 = var185 + var186 + 1859775393;
      int var188 = this.rotateLeft(var187, 9);
      int var189 = this.H(var188, var184, var180) + var176;
      int var190 = this.X[7];
      int var191 = var189 + var190 + 1859775393;
      int var192 = this.rotateLeft(var191, 11);
      int var193 = this.H(var192, var188, var184) + var180;
      int var194 = this.X[15];
      int var195 = var193 + var194 + 1859775393;
      int var196 = this.rotateLeft(var195, 15);
      int var197 = this.H1 + var184;
      this.H1 = var197;
      int var198 = this.H2 + var196;
      this.H2 = var198;
      int var199 = this.H3 + var192;
      this.H3 = var199;
      int var200 = this.H4 + var188;
      this.H4 = var200;
      this.xOff = 0;
      int var201 = 0;

      while(true) {
         int var202 = this.X.length;
         if(var201 == var202) {
            return;
         }

         this.X[var201] = 0;
         ++var201;
      }
   }

   protected void processLength(long var1) {
      if(this.xOff > 14) {
         this.processBlock();
      }

      int[] var3 = this.X;
      int var4 = (int)(65535L & var1);
      var3[14] = var4;
      int[] var5 = this.X;
      int var6 = (int)(var1 >>> 32);
      var5[15] = var6;
   }

   protected void processWord(byte[] var1, int var2) {
      int[] var3 = this.X;
      int var4 = this.xOff;
      int var5 = var4 + 1;
      this.xOff = var5;
      int var6 = var1[var2] & 255;
      int var7 = var2 + 1;
      int var8 = (var1[var7] & 255) << 8;
      int var9 = var6 | var8;
      int var10 = var2 + 2;
      int var11 = (var1[var10] & 255) << 16;
      int var12 = var9 | var11;
      int var13 = var2 + 3;
      int var14 = (var1[var13] & 255) << 24;
      int var15 = var12 | var14;
      var3[var4] = var15;
      if(this.xOff == 16) {
         this.processBlock();
      }
   }

   public void reset() {
      super.reset();
      this.H1 = 1732584193;
      this.H2 = -271733879;
      this.H3 = -1732584194;
      this.H4 = 271733878;
      this.xOff = 0;
      int var1 = 0;

      while(true) {
         int var2 = this.X.length;
         if(var1 == var2) {
            return;
         }

         this.X[var1] = 0;
         ++var1;
      }
   }
}
