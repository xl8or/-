package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.digests.GeneralDigest;

public class RIPEMD256Digest extends GeneralDigest {

   private static final int DIGEST_LENGTH = 32;
   private int H0;
   private int H1;
   private int H2;
   private int H3;
   private int H4;
   private int H5;
   private int H6;
   private int H7;
   private int[] X;
   private int xOff;


   public RIPEMD256Digest() {
      int[] var1 = new int[16];
      this.X = var1;
      this.reset();
   }

   public RIPEMD256Digest(RIPEMD256Digest var1) {
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
      int var7 = var1.H4;
      this.H4 = var7;
      int var8 = var1.H5;
      this.H5 = var8;
      int var9 = var1.H6;
      this.H6 = var9;
      int var10 = var1.H7;
      this.H7 = var10;
      int[] var11 = var1.X;
      int[] var12 = this.X;
      int var13 = var1.X.length;
      System.arraycopy(var11, 0, var12, 0, var13);
      int var14 = var1.xOff;
      this.xOff = var14;
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
      int var10 = this.H4;
      int var11 = var2 + 16;
      this.unpackWord(var10, var1, var11);
      int var12 = this.H5;
      int var13 = var2 + 20;
      this.unpackWord(var12, var1, var13);
      int var14 = this.H6;
      int var15 = var2 + 24;
      this.unpackWord(var14, var1, var15);
      int var16 = this.H7;
      int var17 = var2 + 28;
      this.unpackWord(var16, var1, var17);
      this.reset();
      return 32;
   }

   public String getAlgorithmName() {
      return "RIPEMD256";
   }

   public int getDigestSize() {
      return 32;
   }

   protected void processBlock() {
      int var1 = this.H0;
      int var2 = this.H1;
      int var3 = this.H2;
      int var4 = this.H3;
      int var5 = this.H4;
      int var6 = this.H5;
      int var7 = this.H6;
      int var8 = this.H7;
      int var9 = this.X[0];
      int var10 = this.F1(var1, var2, var3, var4, var9, 11);
      int var11 = this.X[1];
      int var17 = this.F1(var4, var10, var2, var3, var11, 14);
      int var18 = this.X[2];
      int var24 = this.F1(var3, var17, var10, var2, var18, 15);
      int var25 = this.X[3];
      int var31 = this.F1(var2, var24, var17, var10, var25, 12);
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
      int var101 = this.X[5];
      int var107 = this.FF4(var5, var6, var7, var8, var101, 8);
      int var108 = this.X[14];
      int var113 = this.FF4(var8, var107, var6, var7, var108, 9);
      int var114 = this.X[7];
      int var118 = this.FF4(var7, var113, var107, var6, var114, 9);
      int var119 = this.X[0];
      int var123 = this.FF4(var6, var118, var113, var107, var119, 11);
      int var124 = this.X[9];
      int var130 = this.FF4(var107, var123, var118, var113, var124, 13);
      int var131 = this.X[2];
      int var134 = this.FF4(var113, var130, var123, var118, var131, 15);
      int var135 = this.X[11];
      int var141 = this.FF4(var118, var134, var130, var123, var135, 15);
      int var142 = this.X[4];
      int var146 = this.FF4(var123, var141, var134, var130, var142, 5);
      int var147 = this.X[13];
      int var153 = this.FF4(var130, var146, var141, var134, var147, 7);
      int var154 = this.X[6];
      int var157 = this.FF4(var134, var153, var146, var141, var154, 7);
      int var158 = this.X[15];
      int var164 = this.FF4(var141, var157, var153, var146, var158, 8);
      int var165 = this.X[8];
      int var169 = this.FF4(var146, var164, var157, var153, var165, 11);
      int var170 = this.X[1];
      int var176 = this.FF4(var153, var169, var164, var157, var170, 14);
      int var177 = this.X[10];
      int var180 = this.FF4(var157, var176, var169, var164, var177, 14);
      int var181 = this.X[3];
      int var187 = this.FF4(var164, var180, var176, var169, var181, 12);
      int var188 = this.X[12];
      int var192 = this.FF4(var169, var187, var180, var176, var188, 6);
      int var196 = this.X[7];
      int var197 = this.F2(var176, var100, var93, var86, var196, 7);
      int var198 = this.X[4];
      int var204 = this.F2(var86, var197, var100, var93, var198, 6);
      int var205 = this.X[13];
      int var211 = this.F2(var93, var204, var197, var100, var205, 8);
      int var212 = this.X[1];
      int var218 = this.F2(var100, var211, var204, var197, var212, 13);
      int var219 = this.X[10];
      int var220 = this.F2(var197, var218, var211, var204, var219, 11);
      int var221 = this.X[6];
      int var227 = this.F2(var204, var220, var218, var211, var221, 9);
      int var228 = this.X[15];
      int var234 = this.F2(var211, var227, var220, var218, var228, 7);
      int var235 = this.X[3];
      int var241 = this.F2(var218, var234, var227, var220, var235, 15);
      int var242 = this.X[12];
      int var243 = this.F2(var220, var241, var234, var227, var242, 7);
      int var244 = this.X[0];
      int var250 = this.F2(var227, var243, var241, var234, var244, 12);
      int var251 = this.X[9];
      int var257 = this.F2(var234, var250, var243, var241, var251, 15);
      int var258 = this.X[5];
      int var264 = this.F2(var241, var257, var250, var243, var258, 9);
      int var265 = this.X[2];
      int var266 = this.F2(var243, var264, var257, var250, var265, 11);
      int var267 = this.X[14];
      int var273 = this.F2(var250, var266, var264, var257, var267, 7);
      int var274 = this.X[11];
      int var280 = this.F2(var257, var273, var266, var264, var274, 13);
      int var281 = this.X[8];
      int var287 = this.F2(var264, var280, var273, var266, var281, 12);
      int var288 = this.X[6];
      int var294 = this.FF3(var79, var192, var187, var180, var288, 9);
      int var295 = this.X[11];
      int var298 = this.FF3(var180, var294, var192, var187, var295, 13);
      int var299 = this.X[3];
      int var305 = this.FF3(var187, var298, var294, var192, var299, 15);
      int var306 = this.X[7];
      int var310 = this.FF3(var192, var305, var298, var294, var306, 7);
      int var311 = this.X[0];
      int var317 = this.FF3(var294, var310, var305, var298, var311, 12);
      int var318 = this.X[13];
      int var321 = this.FF3(var298, var317, var310, var305, var318, 8);
      int var322 = this.X[5];
      int var328 = this.FF3(var305, var321, var317, var310, var322, 9);
      int var329 = this.X[10];
      int var333 = this.FF3(var310, var328, var321, var317, var329, 11);
      int var334 = this.X[14];
      int var340 = this.FF3(var317, var333, var328, var321, var334, 7);
      int var341 = this.X[15];
      int var344 = this.FF3(var321, var340, var333, var328, var341, 7);
      int var345 = this.X[8];
      int var351 = this.FF3(var328, var344, var340, var333, var345, 12);
      int var352 = this.X[12];
      int var356 = this.FF3(var333, var351, var344, var340, var352, 7);
      int var357 = this.X[4];
      int var363 = this.FF3(var340, var356, var351, var344, var357, 6);
      int var364 = this.X[9];
      int var367 = this.FF3(var344, var363, var356, var351, var364, 15);
      int var368 = this.X[1];
      int var374 = this.FF3(var351, var367, var363, var356, var368, 13);
      int var375 = this.X[2];
      int var379 = this.FF3(var356, var374, var367, var363, var375, 11);
      int var383 = this.X[3];
      int var384 = this.F3(var266, var379, var280, var273, var383, 11);
      int var385 = this.X[10];
      int var391 = this.F3(var273, var384, var379, var280, var385, 13);
      int var392 = this.X[14];
      int var398 = this.F3(var280, var391, var384, var379, var392, 6);
      int var399 = this.X[4];
      int var405 = this.F3(var379, var398, var391, var384, var399, 7);
      int var406 = this.X[9];
      int var407 = this.F3(var384, var405, var398, var391, var406, 14);
      int var408 = this.X[15];
      int var414 = this.F3(var391, var407, var405, var398, var408, 9);
      int var415 = this.X[8];
      int var421 = this.F3(var398, var414, var407, var405, var415, 13);
      int var422 = this.X[1];
      int var428 = this.F3(var405, var421, var414, var407, var422, 15);
      int var429 = this.X[2];
      int var430 = this.F3(var407, var428, var421, var414, var429, 14);
      int var431 = this.X[7];
      int var437 = this.F3(var414, var430, var428, var421, var431, 8);
      int var438 = this.X[0];
      int var444 = this.F3(var421, var437, var430, var428, var438, 13);
      int var445 = this.X[6];
      int var451 = this.F3(var428, var444, var437, var430, var445, 6);
      int var452 = this.X[13];
      int var453 = this.F3(var430, var451, var444, var437, var452, 5);
      int var454 = this.X[11];
      int var460 = this.F3(var437, var453, var451, var444, var454, 12);
      int var461 = this.X[5];
      int var467 = this.F3(var444, var460, var453, var451, var461, 7);
      int var468 = this.X[12];
      int var474 = this.F3(var451, var467, var460, var453, var468, 5);
      int var475 = this.X[15];
      int var481 = this.FF2(var363, var287, var374, var367, var475, 9);
      int var482 = this.X[5];
      int var485 = this.FF2(var367, var481, var287, var374, var482, 7);
      int var486 = this.X[1];
      int var492 = this.FF2(var374, var485, var481, var287, var486, 15);
      int var493 = this.X[3];
      int var497 = this.FF2(var287, var492, var485, var481, var493, 11);
      int var498 = this.X[7];
      int var504 = this.FF2(var481, var497, var492, var485, var498, 8);
      int var505 = this.X[14];
      int var508 = this.FF2(var485, var504, var497, var492, var505, 6);
      int var509 = this.X[6];
      int var515 = this.FF2(var492, var508, var504, var497, var509, 6);
      int var516 = this.X[9];
      int var520 = this.FF2(var497, var515, var508, var504, var516, 14);
      int var521 = this.X[11];
      int var527 = this.FF2(var504, var520, var515, var508, var521, 12);
      int var528 = this.X[8];
      int var531 = this.FF2(var508, var527, var520, var515, var528, 13);
      int var532 = this.X[12];
      int var538 = this.FF2(var515, var531, var527, var520, var532, 5);
      int var539 = this.X[2];
      int var543 = this.FF2(var520, var538, var531, var527, var539, 14);
      int var544 = this.X[10];
      int var550 = this.FF2(var527, var543, var538, var531, var544, 13);
      int var551 = this.X[0];
      int var554 = this.FF2(var531, var550, var543, var538, var551, 13);
      int var555 = this.X[4];
      int var561 = this.FF2(var538, var554, var550, var543, var555, 7);
      int var562 = this.X[13];
      int var566 = this.FF2(var543, var561, var554, var550, var562, 5);
      int var570 = this.X[1];
      int var571 = this.F4(var453, var474, var561, var460, var570, 11);
      int var572 = this.X[9];
      int var578 = this.F4(var460, var571, var474, var561, var572, 12);
      int var579 = this.X[11];
      int var585 = this.F4(var561, var578, var571, var474, var579, 14);
      int var586 = this.X[10];
      int var592 = this.F4(var474, var585, var578, var571, var586, 15);
      int var593 = this.X[0];
      int var594 = this.F4(var571, var592, var585, var578, var593, 14);
      int var595 = this.X[8];
      int var601 = this.F4(var578, var594, var592, var585, var595, 15);
      int var602 = this.X[12];
      int var608 = this.F4(var585, var601, var594, var592, var602, 9);
      int var609 = this.X[4];
      int var615 = this.F4(var592, var608, var601, var594, var609, 8);
      int var616 = this.X[13];
      int var617 = this.F4(var594, var615, var608, var601, var616, 9);
      int var618 = this.X[3];
      int var624 = this.F4(var601, var617, var615, var608, var618, 14);
      int var625 = this.X[7];
      int var631 = this.F4(var608, var624, var617, var615, var625, 5);
      int var632 = this.X[15];
      int var638 = this.F4(var615, var631, var624, var617, var632, 6);
      int var639 = this.X[14];
      int var640 = this.F4(var617, var638, var631, var624, var639, 8);
      int var641 = this.X[5];
      int var647 = this.F4(var624, var640, var638, var631, var641, 6);
      int var648 = this.X[6];
      int var654 = this.F4(var631, var647, var640, var638, var648, 5);
      int var655 = this.X[2];
      int var661 = this.F4(var638, var654, var647, var640, var655, 12);
      int var662 = this.X[8];
      int var668 = this.FF1(var550, var566, var467, var554, var662, 15);
      int var669 = this.X[6];
      int var672 = this.FF1(var554, var668, var566, var467, var669, 5);
      int var673 = this.X[4];
      int var679 = this.FF1(var467, var672, var668, var566, var673, 8);
      int var680 = this.X[1];
      int var684 = this.FF1(var566, var679, var672, var668, var680, 11);
      int var685 = this.X[3];
      int var691 = this.FF1(var668, var684, var679, var672, var685, 14);
      int var692 = this.X[11];
      int var695 = this.FF1(var672, var691, var684, var679, var692, 14);
      int var696 = this.X[15];
      int var702 = this.FF1(var679, var695, var691, var684, var696, 6);
      int var703 = this.X[0];
      int var707 = this.FF1(var684, var702, var695, var691, var703, 14);
      int var708 = this.X[5];
      int var714 = this.FF1(var691, var707, var702, var695, var708, 6);
      int var715 = this.X[12];
      int var718 = this.FF1(var695, var714, var707, var702, var715, 9);
      int var719 = this.X[2];
      int var725 = this.FF1(var702, var718, var714, var707, var719, 12);
      int var726 = this.X[13];
      int var730 = this.FF1(var707, var725, var718, var714, var726, 9);
      int var731 = this.X[9];
      int var737 = this.FF1(var714, var730, var725, var718, var731, 12);
      int var738 = this.X[7];
      int var741 = this.FF1(var718, var737, var730, var725, var738, 5);
      int var742 = this.X[10];
      int var748 = this.FF1(var725, var741, var737, var730, var742, 15);
      int var749 = this.X[14];
      int var753 = this.FF1(var730, var748, var741, var737, var749, 8);
      int var757 = this.H0 + var640;
      this.H0 = var757;
      int var758 = this.H1 + var661;
      this.H1 = var758;
      int var759 = this.H2 + var654;
      this.H2 = var759;
      int var760 = this.H3 + var741;
      this.H3 = var760;
      int var761 = this.H4 + var737;
      this.H4 = var761;
      int var762 = this.H5 + var753;
      this.H5 = var762;
      int var763 = this.H6 + var748;
      this.H6 = var763;
      int var764 = this.H7 + var647;
      this.H7 = var764;
      byte var765 = 0;
      this.xOff = var765;
      int var766 = 0;

      while(true) {
         int var767 = this.X.length;
         if(var766 == var767) {
            return;
         }

         this.X[var766] = 0;
         ++var766;
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
      this.H4 = 1985229328;
      this.H5 = -19088744;
      this.H6 = -1985229329;
      this.H7 = 19088743;
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
