package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.digests.GeneralDigest;
import myorg.bouncycastle.crypto.util.Pack;

public class SHA1Digest extends GeneralDigest {

   private static final int DIGEST_LENGTH = 20;
   private static final int Y1 = 1518500249;
   private static final int Y2 = 1859775393;
   private static final int Y3 = -1894007588;
   private static final int Y4 = -899497514;
   private int H1;
   private int H2;
   private int H3;
   private int H4;
   private int H5;
   private int[] X;
   private int xOff;


   public SHA1Digest() {
      int[] var1 = new int[80];
      this.X = var1;
      this.reset();
   }

   public SHA1Digest(SHA1Digest var1) {
      super(var1);
      int[] var2 = new int[80];
      this.X = var2;
      int var3 = var1.H1;
      this.H1 = var3;
      int var4 = var1.H2;
      this.H2 = var4;
      int var5 = var1.H3;
      this.H3 = var5;
      int var6 = var1.H4;
      this.H4 = var6;
      int var7 = var1.H5;
      this.H5 = var7;
      int[] var8 = var1.X;
      int[] var9 = this.X;
      int var10 = var1.X.length;
      System.arraycopy(var8, 0, var9, 0, var10);
      int var11 = var1.xOff;
      this.xOff = var11;
   }

   private int f(int var1, int var2, int var3) {
      int var4 = var1 & var2;
      int var5 = ~var1 & var3;
      return var4 | var5;
   }

   private int g(int var1, int var2, int var3) {
      int var4 = var1 & var2;
      int var5 = var1 & var3;
      int var6 = var4 | var5;
      int var7 = var2 & var3;
      return var6 | var7;
   }

   private int h(int var1, int var2, int var3) {
      return var1 ^ var2 ^ var3;
   }

   public int doFinal(byte[] var1, int var2) {
      this.finish();
      Pack.intToBigEndian(this.H1, var1, var2);
      int var3 = this.H2;
      int var4 = var2 + 4;
      Pack.intToBigEndian(var3, var1, var4);
      int var5 = this.H3;
      int var6 = var2 + 8;
      Pack.intToBigEndian(var5, var1, var6);
      int var7 = this.H4;
      int var8 = var2 + 12;
      Pack.intToBigEndian(var7, var1, var8);
      int var9 = this.H5;
      int var10 = var2 + 16;
      Pack.intToBigEndian(var9, var1, var10);
      this.reset();
      return 20;
   }

   public String getAlgorithmName() {
      return "SHA-1";
   }

   public int getDigestSize() {
      return 20;
   }

   protected void processBlock() {
      for(int var1 = 16; var1 < 80; ++var1) {
         int[] var2 = this.X;
         int var3 = var1 - 3;
         int var4 = var2[var3];
         int[] var5 = this.X;
         int var6 = var1 - 8;
         int var7 = var5[var6];
         int var8 = var4 ^ var7;
         int[] var9 = this.X;
         int var10 = var1 - 14;
         int var11 = var9[var10];
         int var12 = var8 ^ var11;
         int[] var13 = this.X;
         int var14 = var1 - 16;
         int var15 = var13[var14];
         int var16 = var12 ^ var15;
         int[] var17 = this.X;
         int var18 = var16 << 1;
         int var19 = var16 >>> 31;
         int var20 = var18 | var19;
         var17[var1] = var20;
      }

      int var21 = this.H1;
      int var22 = this.H2;
      int var23 = this.H3;
      int var24 = this.H4;
      int var25 = this.H5;
      int var26 = 0;

      int var27;
      int var83;
      for(var27 = 0; var26 < 4; var27 = var83) {
         int var28 = var21 << 5;
         int var29 = var21 >>> 27;
         int var30 = var28 | var29;
         int var31 = this.f(var22, var23, var24);
         int var32 = var30 + var31;
         int[] var33 = this.X;
         int var34 = var27 + 1;
         int var35 = var33[var27];
         int var36 = var32 + var35 + 1518500249;
         int var37 = var25 + var36;
         int var38 = var22 << 30;
         int var39 = var22 >>> 2;
         int var40 = var38 | var39;
         int var41 = var37 << 5;
         int var42 = var37 >>> 27;
         int var43 = var41 | var42;
         int var44 = this.f(var21, var40, var23);
         int var45 = var43 + var44;
         int[] var46 = this.X;
         int var47 = var34 + 1;
         int var48 = var46[var34];
         int var49 = var45 + var48 + 1518500249;
         int var50 = var24 + var49;
         int var51 = var21 << 30;
         int var52 = var21 >>> 2;
         int var53 = var51 | var52;
         int var54 = var50 << 5;
         int var55 = var50 >>> 27;
         int var56 = var54 | var55;
         int var57 = this.f(var37, var53, var40);
         int var58 = var56 + var57;
         int[] var59 = this.X;
         int var60 = var47 + 1;
         int var61 = var59[var47];
         int var62 = var58 + var61 + 1518500249;
         int var63 = var23 + var62;
         int var64 = var37 << 30;
         int var65 = var37 >>> 2;
         var25 = var64 | var65;
         int var66 = var63 << 5;
         int var67 = var63 >>> 27;
         int var68 = var66 | var67;
         int var69 = this.f(var50, var25, var53);
         int var70 = var68 + var69;
         int[] var71 = this.X;
         int var72 = var60 + 1;
         int var73 = var71[var60];
         int var74 = var70 + var73 + 1518500249;
         var22 = var40 + var74;
         int var75 = var50 << 30;
         int var76 = var50 >>> 2;
         var24 = var75 | var76;
         int var77 = var22 << 5;
         int var78 = var22 >>> 27;
         int var79 = var77 | var78;
         int var80 = this.f(var63, var24, var25);
         int var81 = var79 + var80;
         int[] var82 = this.X;
         var83 = var72 + 1;
         int var84 = var82[var72];
         int var85 = var81 + var84 + 1518500249;
         var21 = var53 + var85;
         int var86 = var63 << 30;
         int var87 = var63 >>> 2;
         var23 = var86 | var87;
         ++var26;
      }

      int var144;
      for(int var88 = 0; var88 < 4; var27 = var144) {
         int var89 = var21 << 5;
         int var90 = var21 >>> 27;
         int var91 = var89 | var90;
         int var92 = this.h(var22, var23, var24);
         int var93 = var91 + var92;
         int[] var94 = this.X;
         int var95 = var27 + 1;
         int var96 = var94[var27];
         int var97 = var93 + var96 + 1859775393;
         int var98 = var25 + var97;
         int var99 = var22 << 30;
         int var100 = var22 >>> 2;
         int var101 = var99 | var100;
         int var102 = var98 << 5;
         int var103 = var98 >>> 27;
         int var104 = var102 | var103;
         int var105 = this.h(var21, var101, var23);
         int var106 = var104 + var105;
         int[] var107 = this.X;
         int var108 = var95 + 1;
         int var109 = var107[var95];
         int var110 = var106 + var109 + 1859775393;
         int var111 = var24 + var110;
         int var112 = var21 << 30;
         int var113 = var21 >>> 2;
         int var114 = var112 | var113;
         int var115 = var111 << 5;
         int var116 = var111 >>> 27;
         int var117 = var115 | var116;
         int var118 = this.h(var98, var114, var101);
         int var119 = var117 + var118;
         int[] var120 = this.X;
         int var121 = var108 + 1;
         int var122 = var120[var108];
         int var123 = var119 + var122 + 1859775393;
         int var124 = var23 + var123;
         int var125 = var98 << 30;
         int var126 = var98 >>> 2;
         var25 = var125 | var126;
         int var127 = var124 << 5;
         int var128 = var124 >>> 27;
         int var129 = var127 | var128;
         int var130 = this.h(var111, var25, var114);
         int var131 = var129 + var130;
         int[] var132 = this.X;
         int var133 = var121 + 1;
         int var134 = var132[var121];
         int var135 = var131 + var134 + 1859775393;
         var22 = var101 + var135;
         int var136 = var111 << 30;
         int var137 = var111 >>> 2;
         var24 = var136 | var137;
         int var138 = var22 << 5;
         int var139 = var22 >>> 27;
         int var140 = var138 | var139;
         int var141 = this.h(var124, var24, var25);
         int var142 = var140 + var141;
         int[] var143 = this.X;
         var144 = var133 + 1;
         int var145 = var143[var133];
         int var146 = var142 + var145 + 1859775393;
         var21 = var114 + var146;
         int var147 = var124 << 30;
         int var148 = var124 >>> 2;
         var23 = var147 | var148;
         ++var88;
      }

      int var205;
      for(int var149 = 0; var149 < 4; var27 = var205) {
         int var150 = var21 << 5;
         int var151 = var21 >>> 27;
         int var152 = var150 | var151;
         int var153 = this.g(var22, var23, var24);
         int var154 = var152 + var153;
         int[] var155 = this.X;
         int var156 = var27 + 1;
         int var157 = var155[var27];
         int var158 = var154 + var157 + -1894007588;
         int var159 = var25 + var158;
         int var160 = var22 << 30;
         int var161 = var22 >>> 2;
         int var162 = var160 | var161;
         int var163 = var159 << 5;
         int var164 = var159 >>> 27;
         int var165 = var163 | var164;
         int var166 = this.g(var21, var162, var23);
         int var167 = var165 + var166;
         int[] var168 = this.X;
         int var169 = var156 + 1;
         int var170 = var168[var156];
         int var171 = var167 + var170 + -1894007588;
         int var172 = var24 + var171;
         int var173 = var21 << 30;
         int var174 = var21 >>> 2;
         int var175 = var173 | var174;
         int var176 = var172 << 5;
         int var177 = var172 >>> 27;
         int var178 = var176 | var177;
         int var179 = this.g(var159, var175, var162);
         int var180 = var178 + var179;
         int[] var181 = this.X;
         int var182 = var169 + 1;
         int var183 = var181[var169];
         int var184 = var180 + var183 + -1894007588;
         int var185 = var23 + var184;
         int var186 = var159 << 30;
         int var187 = var159 >>> 2;
         var25 = var186 | var187;
         int var188 = var185 << 5;
         int var189 = var185 >>> 27;
         int var190 = var188 | var189;
         int var191 = this.g(var172, var25, var175);
         int var192 = var190 + var191;
         int[] var193 = this.X;
         int var194 = var182 + 1;
         int var195 = var193[var182];
         int var196 = var192 + var195 + -1894007588;
         var22 = var162 + var196;
         int var197 = var172 << 30;
         int var198 = var172 >>> 2;
         var24 = var197 | var198;
         int var199 = var22 << 5;
         int var200 = var22 >>> 27;
         int var201 = var199 | var200;
         int var202 = this.g(var185, var24, var25);
         int var203 = var201 + var202;
         int[] var204 = this.X;
         var205 = var194 + 1;
         int var206 = var204[var194];
         int var207 = var203 + var206 + -1894007588;
         var21 = var175 + var207;
         int var208 = var185 << 30;
         int var209 = var185 >>> 2;
         var23 = var208 | var209;
         ++var149;
      }

      int var266;
      for(int var210 = 0; var210 <= 3; var27 = var266) {
         int var211 = var21 << 5;
         int var212 = var21 >>> 27;
         int var213 = var211 | var212;
         int var214 = this.h(var22, var23, var24);
         int var215 = var213 + var214;
         int[] var216 = this.X;
         int var217 = var27 + 1;
         int var218 = var216[var27];
         int var219 = var215 + var218 + -899497514;
         int var220 = var25 + var219;
         int var221 = var22 << 30;
         int var222 = var22 >>> 2;
         int var223 = var221 | var222;
         int var224 = var220 << 5;
         int var225 = var220 >>> 27;
         int var226 = var224 | var225;
         int var227 = this.h(var21, var223, var23);
         int var228 = var226 + var227;
         int[] var229 = this.X;
         int var230 = var217 + 1;
         int var231 = var229[var217];
         int var232 = var228 + var231 + -899497514;
         int var233 = var24 + var232;
         int var234 = var21 << 30;
         int var235 = var21 >>> 2;
         int var236 = var234 | var235;
         int var237 = var233 << 5;
         int var238 = var233 >>> 27;
         int var239 = var237 | var238;
         int var240 = this.h(var220, var236, var223);
         int var241 = var239 + var240;
         int[] var242 = this.X;
         int var243 = var230 + 1;
         int var244 = var242[var230];
         int var245 = var241 + var244 + -899497514;
         int var246 = var23 + var245;
         int var247 = var220 << 30;
         int var248 = var220 >>> 2;
         var25 = var247 | var248;
         int var249 = var246 << 5;
         int var250 = var246 >>> 27;
         int var251 = var249 | var250;
         int var252 = this.h(var233, var25, var236);
         int var253 = var251 + var252;
         int[] var254 = this.X;
         int var255 = var243 + 1;
         int var256 = var254[var243];
         int var257 = var253 + var256 + -899497514;
         var22 = var223 + var257;
         int var258 = var233 << 30;
         int var259 = var233 >>> 2;
         var24 = var258 | var259;
         int var260 = var22 << 5;
         int var261 = var22 >>> 27;
         int var262 = var260 | var261;
         int var263 = this.h(var246, var24, var25);
         int var264 = var262 + var263;
         int[] var265 = this.X;
         var266 = var255 + 1;
         int var267 = var265[var255];
         int var268 = var264 + var267 + -899497514;
         var21 = var236 + var268;
         int var269 = var246 << 30;
         int var270 = var246 >>> 2;
         var23 = var269 | var270;
         ++var210;
      }

      int var271 = this.H1 + var21;
      this.H1 = var271;
      int var272 = this.H2 + var22;
      this.H2 = var272;
      int var273 = this.H3 + var23;
      this.H3 = var273;
      int var274 = this.H4 + var24;
      this.H4 = var274;
      int var275 = this.H5 + var25;
      this.H5 = var275;
      this.xOff = 0;

      for(int var276 = 0; var276 < 16; ++var276) {
         this.X[var276] = 0;
      }

   }

   protected void processLength(long var1) {
      if(this.xOff > 14) {
         this.processBlock();
      }

      int[] var3 = this.X;
      int var4 = (int)(var1 >>> 32);
      var3[14] = var4;
      int[] var5 = this.X;
      int var6 = (int)(65535L & var1);
      var5[15] = var6;
   }

   protected void processWord(byte[] var1, int var2) {
      int var3 = var1[var2] << 24;
      int var4 = var2 + 1;
      int var5 = (var1[var4] & 255) << 16;
      int var6 = var3 | var5;
      int var7 = var4 + 1;
      int var8 = (var1[var7] & 255) << 8;
      int var9 = var6 | var8;
      int var10 = var7 + 1;
      int var11 = var1[var10] & 255;
      int var12 = var9 | var11;
      int[] var13 = this.X;
      int var14 = this.xOff;
      var13[var14] = var12;
      int var15 = this.xOff + 1;
      this.xOff = var15;
      if(var15 == 16) {
         this.processBlock();
      }
   }

   public void reset() {
      super.reset();
      this.H1 = 1732584193;
      this.H2 = -271733879;
      this.H3 = -1732584194;
      this.H4 = 271733878;
      this.H5 = -1009589776;
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
