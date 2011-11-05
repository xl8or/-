package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.digests.GeneralDigest;

public class RIPEMD128Digest extends GeneralDigest {

   private static final int DIGEST_LENGTH = 16;
   private int H0;
   private int H1;
   private int H2;
   private int H3;
   private int[] X;
   private int xOff;


   public RIPEMD128Digest() {
      int[] var1 = new int[16];
      this.X = var1;
      this.reset();
   }

   public RIPEMD128Digest(RIPEMD128Digest var1) {
      super(var1);
      int[] var2 = new int[16];
      this.X = var2;
      int var3 = var1.H0;
      this.H0 = var3;
      int var4 = var1.H1;
      this.H1 = var4;
      int var5 = var1.H2;
      this.H2 = var5;
      int var6 = var1.H3;
      this.H3 = var6;
      int[] var7 = var1.X;
      int[] var8 = this.X;
      int var9 = var1.X.length;
      System.arraycopy(var7, 0, var8, 0, var9);
      int var10 = var1.xOff;
      this.xOff = var10;
   }

   private int F1(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = this.f1(var2, var3, var4) + var1 + var5;
      return this.RL(var7, var6);
   }

   private int F2(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = this.f2(var2, var3, var4) + var1 + var5 + 1518500249;
      return this.RL(var7, var6);
   }

   private int F3(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = this.f3(var2, var3, var4) + var1 + var5 + 1859775393;
      return this.RL(var7, var6);
   }

   private int F4(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = this.f4(var2, var3, var4) + var1 + var5 + -1894007588;
      return this.RL(var7, var6);
   }

   private int FF1(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = this.f1(var2, var3, var4) + var1 + var5;
      return this.RL(var7, var6);
   }

   private int FF2(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = this.f2(var2, var3, var4) + var1 + var5 + 1836072691;
      return this.RL(var7, var6);
   }

   private int FF3(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = this.f3(var2, var3, var4) + var1 + var5 + 1548603684;
      return this.RL(var7, var6);
   }

   private int FF4(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = this.f4(var2, var3, var4) + var1 + var5 + 1352829926;
      return this.RL(var7, var6);
   }

   private int RL(int var1, int var2) {
      int var3 = var1 << var2;
      int var4 = 32 - var2;
      int var5 = var1 >>> var4;
      return var3 | var5;
   }

   private int f1(int var1, int var2, int var3) {
      return var1 ^ var2 ^ var3;
   }

   private int f2(int var1, int var2, int var3) {
      int var4 = var1 & var2;
      int var5 = ~var1 & var3;
      return var4 | var5;
   }

   private int f3(int var1, int var2, int var3) {
      return (~var2 | var1) ^ var3;
   }

   private int f4(int var1, int var2, int var3) {
      int var4 = var1 & var3;
      int var5 = ~var3 & var2;
      return var4 | var5;
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
      int var3 = this.H0;
      this.unpackWord(var3, var1, var2);
      int var4 = this.H1;
      int var5 = var2 + 4;
      this.unpackWord(var4, var1, var5);
      int var6 = this.H2;
      int var7 = var2 + 8;
      this.unpackWord(var6, var1, var7);
      int var8 = this.H3;
      int var9 = var2 + 12;
      this.unpackWord(var8, var1, var9);
      this.reset();
      return 16;
   }

   public String getAlgorithmName() {
      return "RIPEMD128";
   }

   public int getDigestSize() {
      return 16;
   }

   protected void processBlock() {
      int var1 = this.H0;
      int var3 = this.H1;
      int var5 = this.H2;
      int var7 = this.H3;
      int var9 = this.X[0];
      int var10 = this.F1(var1, var3, var5, var7, var9, 11);
      int var11 = this.X[1];
      int var17 = this.F1(var7, var10, var3, var5, var11, 14);
      int var18 = this.X[2];
      int var24 = this.F1(var5, var17, var10, var3, var18, 15);
      int var25 = this.X[3];
      int var31 = this.F1(var3, var24, var17, var10, var25, 12);
      int var32 = this.X[4];
      int var33 = this.F1(var10, var31, var24, var17, var32, 5);
      int var34 = this.X[5];
      int var40 = this.F1(var17, var33, var31, var24, var34, 8);
      int var41 = this.X[6];
      int var47 = this.F1(var24, var40, var33, var31, var41, 7);
      int var48 = this.X[7];
      int var54 = this.F1(var31, var47, var40, var33, var48, 9);
      int var55 = this.X[8];
      int var56 = this.F1(var33, var54, var47, var40, var55, 11);
      int var57 = this.X[9];
      int var63 = this.F1(var40, var56, var54, var47, var57, 13);
      int var64 = this.X[10];
      int var70 = this.F1(var47, var63, var56, var54, var64, 14);
      int var71 = this.X[11];
      int var77 = this.F1(var54, var70, var63, var56, var71, 15);
      int var78 = this.X[12];
      int var79 = this.F1(var56, var77, var70, var63, var78, 6);
      int var80 = this.X[13];
      int var86 = this.F1(var63, var79, var77, var70, var80, 7);
      int var87 = this.X[14];
      int var93 = this.F1(var70, var86, var79, var77, var87, 9);
      int var94 = this.X[15];
      int var100 = this.F1(var77, var93, var86, var79, var94, 8);
      int var101 = this.X[7];
      int var102 = this.F2(var79, var100, var93, var86, var101, 7);
      int var103 = this.X[4];
      int var109 = this.F2(var86, var102, var100, var93, var103, 6);
      int var110 = this.X[13];
      int var116 = this.F2(var93, var109, var102, var100, var110, 8);
      int var117 = this.X[1];
      int var123 = this.F2(var100, var116, var109, var102, var117, 13);
      int var124 = this.X[10];
      int var125 = this.F2(var102, var123, var116, var109, var124, 11);
      int var126 = this.X[6];
      int var132 = this.F2(var109, var125, var123, var116, var126, 9);
      int var133 = this.X[15];
      int var139 = this.F2(var116, var132, var125, var123, var133, 7);
      int var140 = this.X[3];
      int var146 = this.F2(var123, var139, var132, var125, var140, 15);
      int var147 = this.X[12];
      int var148 = this.F2(var125, var146, var139, var132, var147, 7);
      int var149 = this.X[0];
      int var155 = this.F2(var132, var148, var146, var139, var149, 12);
      int var156 = this.X[9];
      int var162 = this.F2(var139, var155, var148, var146, var156, 15);
      int var163 = this.X[5];
      int var169 = this.F2(var146, var162, var155, var148, var163, 9);
      int var170 = this.X[2];
      int var171 = this.F2(var148, var169, var162, var155, var170, 11);
      int var172 = this.X[14];
      int var178 = this.F2(var155, var171, var169, var162, var172, 7);
      int var179 = this.X[11];
      int var185 = this.F2(var162, var178, var171, var169, var179, 13);
      int var186 = this.X[8];
      int var192 = this.F2(var169, var185, var178, var171, var186, 12);
      int var193 = this.X[3];
      int var194 = this.F3(var171, var192, var185, var178, var193, 11);
      int var195 = this.X[10];
      int var201 = this.F3(var178, var194, var192, var185, var195, 13);
      int var202 = this.X[14];
      int var208 = this.F3(var185, var201, var194, var192, var202, 6);
      int var209 = this.X[4];
      int var215 = this.F3(var192, var208, var201, var194, var209, 7);
      int var216 = this.X[9];
      int var217 = this.F3(var194, var215, var208, var201, var216, 14);
      int var218 = this.X[15];
      int var224 = this.F3(var201, var217, var215, var208, var218, 9);
      int var225 = this.X[8];
      int var231 = this.F3(var208, var224, var217, var215, var225, 13);
      int var232 = this.X[1];
      int var238 = this.F3(var215, var231, var224, var217, var232, 15);
      int var239 = this.X[2];
      int var240 = this.F3(var217, var238, var231, var224, var239, 14);
      int var241 = this.X[7];
      int var247 = this.F3(var224, var240, var238, var231, var241, 8);
      int var248 = this.X[0];
      int var254 = this.F3(var231, var247, var240, var238, var248, 13);
      int var255 = this.X[6];
      int var261 = this.F3(var238, var254, var247, var240, var255, 6);
      int var262 = this.X[13];
      int var263 = this.F3(var240, var261, var254, var247, var262, 5);
      int var264 = this.X[11];
      int var270 = this.F3(var247, var263, var261, var254, var264, 12);
      int var271 = this.X[5];
      int var277 = this.F3(var254, var270, var263, var261, var271, 7);
      int var278 = this.X[12];
      int var284 = this.F3(var261, var277, var270, var263, var278, 5);
      int var285 = this.X[1];
      int var286 = this.F4(var263, var284, var277, var270, var285, 11);
      int var287 = this.X[9];
      int var293 = this.F4(var270, var286, var284, var277, var287, 12);
      int var294 = this.X[11];
      int var300 = this.F4(var277, var293, var286, var284, var294, 14);
      int var301 = this.X[10];
      int var307 = this.F4(var284, var300, var293, var286, var301, 15);
      int var308 = this.X[0];
      int var309 = this.F4(var286, var307, var300, var293, var308, 14);
      int var310 = this.X[8];
      int var316 = this.F4(var293, var309, var307, var300, var310, 15);
      int var317 = this.X[12];
      int var323 = this.F4(var300, var316, var309, var307, var317, 9);
      int var324 = this.X[4];
      int var330 = this.F4(var307, var323, var316, var309, var324, 8);
      int var331 = this.X[13];
      int var332 = this.F4(var309, var330, var323, var316, var331, 9);
      int var333 = this.X[3];
      int var339 = this.F4(var316, var332, var330, var323, var333, 14);
      int var340 = this.X[7];
      int var346 = this.F4(var323, var339, var332, var330, var340, 5);
      int var347 = this.X[15];
      int var353 = this.F4(var330, var346, var339, var332, var347, 6);
      int var354 = this.X[14];
      int var355 = this.F4(var332, var353, var346, var339, var354, 8);
      int var356 = this.X[5];
      int var362 = this.F4(var339, var355, var353, var346, var356, 6);
      int var363 = this.X[6];
      int var369 = this.F4(var346, var362, var355, var353, var363, 5);
      int var370 = this.X[2];
      int var376 = this.F4(var353, var369, var362, var355, var370, 12);
      int var377 = this.X[5];
      int var383 = this.FF4(var1, var3, var5, var7, var377, 8);
      int var384 = this.X[14];
      int var389 = this.FF4(var7, var383, var3, var5, var384, 9);
      int var390 = this.X[7];
      int var394 = this.FF4(var5, var389, var383, var3, var390, 9);
      int var395 = this.X[0];
      int var399 = this.FF4(var3, var394, var389, var383, var395, 11);
      int var400 = this.X[9];
      int var406 = this.FF4(var383, var399, var394, var389, var400, 13);
      int var407 = this.X[2];
      int var410 = this.FF4(var389, var406, var399, var394, var407, 15);
      int var411 = this.X[11];
      int var417 = this.FF4(var394, var410, var406, var399, var411, 15);
      int var418 = this.X[4];
      int var422 = this.FF4(var399, var417, var410, var406, var418, 5);
      int var423 = this.X[13];
      int var429 = this.FF4(var406, var422, var417, var410, var423, 7);
      int var430 = this.X[6];
      int var433 = this.FF4(var410, var429, var422, var417, var430, 7);
      int var434 = this.X[15];
      int var440 = this.FF4(var417, var433, var429, var422, var434, 8);
      int var441 = this.X[8];
      int var445 = this.FF4(var422, var440, var433, var429, var441, 11);
      int var446 = this.X[1];
      int var452 = this.FF4(var429, var445, var440, var433, var446, 14);
      int var453 = this.X[10];
      int var456 = this.FF4(var433, var452, var445, var440, var453, 14);
      int var457 = this.X[3];
      int var463 = this.FF4(var440, var456, var452, var445, var457, 12);
      int var464 = this.X[12];
      int var468 = this.FF4(var445, var463, var456, var452, var464, 6);
      int var469 = this.X[6];
      int var475 = this.FF3(var452, var468, var463, var456, var469, 9);
      int var476 = this.X[11];
      int var479 = this.FF3(var456, var475, var468, var463, var476, 13);
      int var480 = this.X[3];
      int var486 = this.FF3(var463, var479, var475, var468, var480, 15);
      int var487 = this.X[7];
      int var491 = this.FF3(var468, var486, var479, var475, var487, 7);
      int var492 = this.X[0];
      int var498 = this.FF3(var475, var491, var486, var479, var492, 12);
      int var499 = this.X[13];
      int var502 = this.FF3(var479, var498, var491, var486, var499, 8);
      int var503 = this.X[5];
      int var509 = this.FF3(var486, var502, var498, var491, var503, 9);
      int var510 = this.X[10];
      int var514 = this.FF3(var491, var509, var502, var498, var510, 11);
      int var515 = this.X[14];
      int var521 = this.FF3(var498, var514, var509, var502, var515, 7);
      int var522 = this.X[15];
      int var525 = this.FF3(var502, var521, var514, var509, var522, 7);
      int var526 = this.X[8];
      int var532 = this.FF3(var509, var525, var521, var514, var526, 12);
      int var533 = this.X[12];
      int var537 = this.FF3(var514, var532, var525, var521, var533, 7);
      int var538 = this.X[4];
      int var544 = this.FF3(var521, var537, var532, var525, var538, 6);
      int var545 = this.X[9];
      int var548 = this.FF3(var525, var544, var537, var532, var545, 15);
      int var549 = this.X[1];
      int var555 = this.FF3(var532, var548, var544, var537, var549, 13);
      int var556 = this.X[2];
      int var560 = this.FF3(var537, var555, var548, var544, var556, 11);
      int var561 = this.X[15];
      int var567 = this.FF2(var544, var560, var555, var548, var561, 9);
      int var568 = this.X[5];
      int var571 = this.FF2(var548, var567, var560, var555, var568, 7);
      int var572 = this.X[1];
      int var578 = this.FF2(var555, var571, var567, var560, var572, 15);
      int var579 = this.X[3];
      int var583 = this.FF2(var560, var578, var571, var567, var579, 11);
      int var584 = this.X[7];
      int var590 = this.FF2(var567, var583, var578, var571, var584, 8);
      int var591 = this.X[14];
      int var594 = this.FF2(var571, var590, var583, var578, var591, 6);
      int var595 = this.X[6];
      int var601 = this.FF2(var578, var594, var590, var583, var595, 6);
      int var602 = this.X[9];
      int var606 = this.FF2(var583, var601, var594, var590, var602, 14);
      int var607 = this.X[11];
      int var613 = this.FF2(var590, var606, var601, var594, var607, 12);
      int var614 = this.X[8];
      int var617 = this.FF2(var594, var613, var606, var601, var614, 13);
      int var618 = this.X[12];
      int var624 = this.FF2(var601, var617, var613, var606, var618, 5);
      int var625 = this.X[2];
      int var629 = this.FF2(var606, var624, var617, var613, var625, 14);
      int var630 = this.X[10];
      int var636 = this.FF2(var613, var629, var624, var617, var630, 13);
      int var637 = this.X[0];
      int var640 = this.FF2(var617, var636, var629, var624, var637, 13);
      int var641 = this.X[4];
      int var647 = this.FF2(var624, var640, var636, var629, var641, 7);
      int var648 = this.X[13];
      int var652 = this.FF2(var629, var647, var640, var636, var648, 5);
      int var653 = this.X[8];
      int var659 = this.FF1(var636, var652, var647, var640, var653, 15);
      int var660 = this.X[6];
      int var663 = this.FF1(var640, var659, var652, var647, var660, 5);
      int var664 = this.X[4];
      int var670 = this.FF1(var647, var663, var659, var652, var664, 8);
      int var671 = this.X[1];
      int var675 = this.FF1(var652, var670, var663, var659, var671, 11);
      int var676 = this.X[3];
      int var682 = this.FF1(var659, var675, var670, var663, var676, 14);
      int var683 = this.X[11];
      int var686 = this.FF1(var663, var682, var675, var670, var683, 14);
      int var687 = this.X[15];
      int var693 = this.FF1(var670, var686, var682, var675, var687, 6);
      int var694 = this.X[0];
      int var698 = this.FF1(var675, var693, var686, var682, var694, 14);
      int var699 = this.X[5];
      int var705 = this.FF1(var682, var698, var693, var686, var699, 6);
      int var706 = this.X[12];
      int var709 = this.FF1(var686, var705, var698, var693, var706, 9);
      int var710 = this.X[2];
      int var716 = this.FF1(var693, var709, var705, var698, var710, 12);
      int var717 = this.X[13];
      int var721 = this.FF1(var698, var716, var709, var705, var717, 9);
      int var722 = this.X[9];
      int var728 = this.FF1(var705, var721, var716, var709, var722, 12);
      int var729 = this.X[7];
      int var732 = this.FF1(var709, var728, var721, var716, var729, 5);
      int var733 = this.X[10];
      int var739 = this.FF1(var716, var732, var728, var721, var733, 15);
      int var740 = this.X[14];
      int var744 = this.FF1(var721, var739, var732, var728, var740, 8);
      int var745 = this.H1 + var369;
      int var746 = var732 + var745;
      int var747 = this.H2 + var362 + var728;
      this.H1 = var747;
      int var748 = this.H3 + var355 + var744;
      this.H2 = var748;
      int var749 = this.H0 + var376 + var739;
      this.H3 = var749;
      this.H0 = var746;
      byte var751 = 0;
      this.xOff = var751;
      int var752 = 0;

      while(true) {
         int var753 = this.X.length;
         if(var752 == var753) {
            return;
         }

         this.X[var752] = 0;
         ++var752;
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
      this.H0 = 1732584193;
      this.H1 = -271733879;
      this.H2 = -1732584194;
      this.H3 = 271733878;
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
