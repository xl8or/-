package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.digests.GeneralDigest;

public class MD5Digest extends GeneralDigest {

   private static final int DIGEST_LENGTH = 16;
   private static final int S11 = 7;
   private static final int S12 = 12;
   private static final int S13 = 17;
   private static final int S14 = 22;
   private static final int S21 = 5;
   private static final int S22 = 9;
   private static final int S23 = 14;
   private static final int S24 = 20;
   private static final int S31 = 4;
   private static final int S32 = 11;
   private static final int S33 = 16;
   private static final int S34 = 23;
   private static final int S41 = 6;
   private static final int S42 = 10;
   private static final int S43 = 15;
   private static final int S44 = 21;
   private int H1;
   private int H2;
   private int H3;
   private int H4;
   private int[] X;
   private int xOff;


   public MD5Digest() {
      int[] var1 = new int[16];
      this.X = var1;
      this.reset();
   }

   public MD5Digest(MD5Digest var1) {
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
      int var4 = var1 & var3;
      int var5 = ~var3 & var2;
      return var4 | var5;
   }

   private int H(int var1, int var2, int var3) {
      return var1 ^ var2 ^ var3;
   }

   private int K(int var1, int var2, int var3) {
      return (~var3 | var1) ^ var2;
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
      return "MD5";
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
      int var7 = var5 + var6 + -680876936;
      int var8 = this.rotateLeft(var7, 7) + var2;
      int var9 = this.F(var8, var2, var3) + var4;
      int var10 = this.X[1];
      int var11 = var9 + var10 + -389564586;
      int var12 = this.rotateLeft(var11, 12) + var8;
      int var13 = this.F(var12, var8, var2) + var3;
      int var14 = this.X[2];
      int var15 = var13 + var14 + 606105819;
      int var16 = this.rotateLeft(var15, 17) + var12;
      int var17 = this.F(var16, var12, var8) + var2;
      int var18 = this.X[3];
      int var19 = var17 + var18 + -1044525330;
      int var20 = this.rotateLeft(var19, 22) + var16;
      int var21 = this.F(var20, var16, var12) + var8;
      int var22 = this.X[4];
      int var23 = var21 + var22 + -176418897;
      int var24 = this.rotateLeft(var23, 7) + var20;
      int var25 = this.F(var24, var20, var16) + var12;
      int var26 = this.X[5];
      int var27 = var25 + var26 + 1200080426;
      int var28 = this.rotateLeft(var27, 12) + var24;
      int var29 = this.F(var28, var24, var20) + var16;
      int var30 = this.X[6];
      int var31 = var29 + var30 + -1473231341;
      int var32 = this.rotateLeft(var31, 17) + var28;
      int var33 = this.F(var32, var28, var24) + var20;
      int var34 = this.X[7];
      int var35 = var33 + var34 + -45705983;
      int var36 = this.rotateLeft(var35, 22) + var32;
      int var37 = this.F(var36, var32, var28) + var24;
      int var38 = this.X[8];
      int var39 = var37 + var38 + 1770035416;
      int var40 = this.rotateLeft(var39, 7) + var36;
      int var41 = this.F(var40, var36, var32) + var28;
      int var42 = this.X[9];
      int var43 = var41 + var42 + -1958414417;
      int var44 = this.rotateLeft(var43, 12) + var40;
      int var45 = this.F(var44, var40, var36) + var32;
      int var46 = this.X[10];
      int var47 = var45 + var46 + -42063;
      int var48 = this.rotateLeft(var47, 17) + var44;
      int var49 = this.F(var48, var44, var40) + var36;
      int var50 = this.X[11];
      int var51 = var49 + var50 + -1990404162;
      int var52 = this.rotateLeft(var51, 22) + var48;
      int var53 = this.F(var52, var48, var44) + var40;
      int var54 = this.X[12];
      int var55 = var53 + var54 + 1804603682;
      int var56 = this.rotateLeft(var55, 7) + var52;
      int var57 = this.F(var56, var52, var48) + var44;
      int var58 = this.X[13];
      int var59 = var57 + var58 + -40341101;
      int var60 = this.rotateLeft(var59, 12) + var56;
      int var61 = this.F(var60, var56, var52) + var48;
      int var62 = this.X[14];
      int var63 = var61 + var62 + -1502002290;
      int var64 = this.rotateLeft(var63, 17) + var60;
      int var65 = this.F(var64, var60, var56) + var52;
      int var66 = this.X[15];
      int var67 = var65 + var66 + 1236535329;
      int var68 = this.rotateLeft(var67, 22) + var64;
      int var69 = this.G(var68, var64, var60) + var56;
      int var70 = this.X[1];
      int var71 = var69 + var70 + -165796510;
      int var72 = this.rotateLeft(var71, 5) + var68;
      int var73 = this.G(var72, var68, var64) + var60;
      int var74 = this.X[6];
      int var75 = var73 + var74 + -1069501632;
      int var76 = this.rotateLeft(var75, 9) + var72;
      int var77 = this.G(var76, var72, var68) + var64;
      int var78 = this.X[11];
      int var79 = var77 + var78 + 643717713;
      int var80 = this.rotateLeft(var79, 14) + var76;
      int var81 = this.G(var80, var76, var72) + var68;
      int var82 = this.X[0];
      int var83 = var81 + var82 + -373897302;
      int var84 = this.rotateLeft(var83, 20) + var80;
      int var85 = this.G(var84, var80, var76) + var72;
      int var86 = this.X[5];
      int var87 = var85 + var86 + -701558691;
      int var88 = this.rotateLeft(var87, 5) + var84;
      int var89 = this.G(var88, var84, var80) + var76;
      int var90 = this.X[10];
      int var91 = var89 + var90 + 38016083;
      int var92 = this.rotateLeft(var91, 9) + var88;
      int var93 = this.G(var92, var88, var84) + var80;
      int var94 = this.X[15];
      int var95 = var93 + var94 + -660478335;
      int var96 = this.rotateLeft(var95, 14) + var92;
      int var97 = this.G(var96, var92, var88) + var84;
      int var98 = this.X[4];
      int var99 = var97 + var98 + -405537848;
      int var100 = this.rotateLeft(var99, 20) + var96;
      int var101 = this.G(var100, var96, var92) + var88;
      int var102 = this.X[9];
      int var103 = var101 + var102 + 568446438;
      int var104 = this.rotateLeft(var103, 5) + var100;
      int var105 = this.G(var104, var100, var96) + var92;
      int var106 = this.X[14];
      int var107 = var105 + var106 + -1019803690;
      int var108 = this.rotateLeft(var107, 9) + var104;
      int var109 = this.G(var108, var104, var100) + var96;
      int var110 = this.X[3];
      int var111 = var109 + var110 + -187363961;
      int var112 = this.rotateLeft(var111, 14) + var108;
      int var113 = this.G(var112, var108, var104) + var100;
      int var114 = this.X[8];
      int var115 = var113 + var114 + 1163531501;
      int var116 = this.rotateLeft(var115, 20) + var112;
      int var117 = this.G(var116, var112, var108) + var104;
      int var118 = this.X[13];
      int var119 = var117 + var118 + -1444681467;
      int var120 = this.rotateLeft(var119, 5) + var116;
      int var121 = this.G(var120, var116, var112) + var108;
      int var122 = this.X[2];
      int var123 = var121 + var122 + -51403784;
      int var124 = this.rotateLeft(var123, 9) + var120;
      int var125 = this.G(var124, var120, var116) + var112;
      int var126 = this.X[7];
      int var127 = var125 + var126 + 1735328473;
      int var128 = this.rotateLeft(var127, 14) + var124;
      int var129 = this.G(var128, var124, var120) + var116;
      int var130 = this.X[12];
      int var131 = var129 + var130 + -1926607734;
      int var132 = this.rotateLeft(var131, 20) + var128;
      int var133 = this.H(var132, var128, var124) + var120;
      int var134 = this.X[5];
      int var135 = var133 + var134 + -378558;
      int var136 = this.rotateLeft(var135, 4) + var132;
      int var137 = this.H(var136, var132, var128) + var124;
      int var138 = this.X[8];
      int var139 = var137 + var138 + -2022574463;
      int var140 = this.rotateLeft(var139, 11) + var136;
      int var141 = this.H(var140, var136, var132) + var128;
      int var142 = this.X[11];
      int var143 = var141 + var142 + 1839030562;
      int var144 = this.rotateLeft(var143, 16) + var140;
      int var145 = this.H(var144, var140, var136) + var132;
      int var146 = this.X[14];
      int var147 = var145 + var146 + -35309556;
      int var148 = this.rotateLeft(var147, 23) + var144;
      int var149 = this.H(var148, var144, var140) + var136;
      int var150 = this.X[1];
      int var151 = var149 + var150 + -1530992060;
      int var152 = this.rotateLeft(var151, 4) + var148;
      int var153 = this.H(var152, var148, var144) + var140;
      int var154 = this.X[4];
      int var155 = var153 + var154 + 1272893353;
      int var156 = this.rotateLeft(var155, 11) + var152;
      int var157 = this.H(var156, var152, var148) + var144;
      int var158 = this.X[7];
      int var159 = var157 + var158 + -155497632;
      int var160 = this.rotateLeft(var159, 16) + var156;
      int var161 = this.H(var160, var156, var152) + var148;
      int var162 = this.X[10];
      int var163 = var161 + var162 + -1094730640;
      int var164 = this.rotateLeft(var163, 23) + var160;
      int var165 = this.H(var164, var160, var156) + var152;
      int var166 = this.X[13];
      int var167 = var165 + var166 + 681279174;
      int var168 = this.rotateLeft(var167, 4) + var164;
      int var169 = this.H(var168, var164, var160) + var156;
      int var170 = this.X[0];
      int var171 = var169 + var170 + -358537222;
      int var172 = this.rotateLeft(var171, 11) + var168;
      int var173 = this.H(var172, var168, var164) + var160;
      int var174 = this.X[3];
      int var175 = var173 + var174 + -722521979;
      int var176 = this.rotateLeft(var175, 16) + var172;
      int var177 = this.H(var176, var172, var168) + var164;
      int var178 = this.X[6];
      int var179 = var177 + var178 + 76029189;
      int var180 = this.rotateLeft(var179, 23) + var176;
      int var181 = this.H(var180, var176, var172) + var168;
      int var182 = this.X[9];
      int var183 = var181 + var182 + -640364487;
      int var184 = this.rotateLeft(var183, 4) + var180;
      int var185 = this.H(var184, var180, var176) + var172;
      int var186 = this.X[12];
      int var187 = var185 + var186 + -421815835;
      int var188 = this.rotateLeft(var187, 11) + var184;
      int var189 = this.H(var188, var184, var180) + var176;
      int var190 = this.X[15];
      int var191 = var189 + var190 + 530742520;
      int var192 = this.rotateLeft(var191, 16) + var188;
      int var193 = this.H(var192, var188, var184) + var180;
      int var194 = this.X[2];
      int var195 = var193 + var194 + -995338651;
      int var196 = this.rotateLeft(var195, 23) + var192;
      int var197 = this.K(var196, var192, var188) + var184;
      int var198 = this.X[0];
      int var199 = var197 + var198 + -198630844;
      int var200 = this.rotateLeft(var199, 6) + var196;
      int var201 = this.K(var200, var196, var192) + var188;
      int var202 = this.X[7];
      int var203 = var201 + var202 + 1126891415;
      int var204 = this.rotateLeft(var203, 10) + var200;
      int var205 = this.K(var204, var200, var196) + var192;
      int var206 = this.X[14];
      int var207 = var205 + var206 + -1416354905;
      int var208 = this.rotateLeft(var207, 15) + var204;
      int var209 = this.K(var208, var204, var200) + var196;
      int var210 = this.X[5];
      int var211 = var209 + var210 + -57434055;
      int var212 = this.rotateLeft(var211, 21) + var208;
      int var213 = this.K(var212, var208, var204) + var200;
      int var214 = this.X[12];
      int var215 = var213 + var214 + 1700485571;
      int var216 = this.rotateLeft(var215, 6) + var212;
      int var217 = this.K(var216, var212, var208) + var204;
      int var218 = this.X[3];
      int var219 = var217 + var218 + -1894986606;
      int var220 = this.rotateLeft(var219, 10) + var216;
      int var221 = this.K(var220, var216, var212) + var208;
      int var222 = this.X[10];
      int var223 = var221 + var222 + -1051523;
      int var224 = this.rotateLeft(var223, 15) + var220;
      int var225 = this.K(var224, var220, var216) + var212;
      int var226 = this.X[1];
      int var227 = var225 + var226 + -2054922799;
      int var228 = this.rotateLeft(var227, 21) + var224;
      int var229 = this.K(var228, var224, var220) + var216;
      int var230 = this.X[8];
      int var231 = var229 + var230 + 1873313359;
      int var232 = this.rotateLeft(var231, 6) + var228;
      int var233 = this.K(var232, var228, var224) + var220;
      int var234 = this.X[15];
      int var235 = var233 + var234 + -30611744;
      int var236 = this.rotateLeft(var235, 10) + var232;
      int var237 = this.K(var236, var232, var228) + var224;
      int var238 = this.X[6];
      int var239 = var237 + var238 + -1560198380;
      int var240 = this.rotateLeft(var239, 15) + var236;
      int var241 = this.K(var240, var236, var232) + var228;
      int var242 = this.X[13];
      int var243 = var241 + var242 + 1309151649;
      int var244 = this.rotateLeft(var243, 21) + var240;
      int var245 = this.K(var244, var240, var236) + var232;
      int var246 = this.X[4];
      int var247 = var245 + var246 + -145523070;
      int var248 = this.rotateLeft(var247, 6) + var244;
      int var249 = this.K(var248, var244, var240) + var236;
      int var250 = this.X[11];
      int var251 = var249 + var250 + -1120210379;
      int var252 = this.rotateLeft(var251, 10) + var248;
      int var253 = this.K(var252, var248, var244) + var240;
      int var254 = this.X[2];
      int var255 = var253 + var254 + 718787259;
      int var256 = this.rotateLeft(var255, 15) + var252;
      int var257 = this.K(var256, var252, var248) + var244;
      int var258 = this.X[9];
      int var259 = var257 + var258 + -343485551;
      int var260 = this.rotateLeft(var259, 21) + var256;
      int var261 = this.H1 + var248;
      this.H1 = var261;
      int var262 = this.H2 + var260;
      this.H2 = var262;
      int var263 = this.H3 + var256;
      this.H3 = var263;
      int var264 = this.H4 + var252;
      this.H4 = var264;
      this.xOff = 0;
      int var265 = 0;

      while(true) {
         int var266 = this.X.length;
         if(var265 == var266) {
            return;
         }

         this.X[var265] = 0;
         ++var265;
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
