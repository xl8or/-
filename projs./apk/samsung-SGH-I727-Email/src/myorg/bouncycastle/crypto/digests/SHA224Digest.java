package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.digests.GeneralDigest;
import myorg.bouncycastle.crypto.util.Pack;

public class SHA224Digest extends GeneralDigest {

   private static final int DIGEST_LENGTH = 28;
   static final int[] K = new int[]{1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
   private int H1;
   private int H2;
   private int H3;
   private int H4;
   private int H5;
   private int H6;
   private int H7;
   private int H8;
   private int[] X;
   private int xOff;


   public SHA224Digest() {
      int[] var1 = new int[64];
      this.X = var1;
      this.reset();
   }

   public SHA224Digest(SHA224Digest var1) {
      super(var1);
      int[] var2 = new int[64];
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
      int var8 = var1.H6;
      this.H6 = var8;
      int var9 = var1.H7;
      this.H7 = var9;
      int var10 = var1.H8;
      this.H8 = var10;
      int[] var11 = var1.X;
      int[] var12 = this.X;
      int var13 = var1.X.length;
      System.arraycopy(var11, 0, var12, 0, var13);
      int var14 = var1.xOff;
      this.xOff = var14;
   }

   private int Ch(int var1, int var2, int var3) {
      int var4 = var1 & var2;
      int var5 = ~var1 & var3;
      return var4 ^ var5;
   }

   private int Maj(int var1, int var2, int var3) {
      int var4 = var1 & var2;
      int var5 = var1 & var3;
      int var6 = var4 ^ var5;
      int var7 = var2 & var3;
      return var6 ^ var7;
   }

   private int Sum0(int var1) {
      int var2 = var1 >>> 2;
      int var3 = var1 << 30;
      int var4 = var2 | var3;
      int var5 = var1 >>> 13;
      int var6 = var1 << 19;
      int var7 = var5 | var6;
      int var8 = var4 ^ var7;
      int var9 = var1 >>> 22;
      int var10 = var1 << 10;
      int var11 = var9 | var10;
      return var8 ^ var11;
   }

   private int Sum1(int var1) {
      int var2 = var1 >>> 6;
      int var3 = var1 << 26;
      int var4 = var2 | var3;
      int var5 = var1 >>> 11;
      int var6 = var1 << 21;
      int var7 = var5 | var6;
      int var8 = var4 ^ var7;
      int var9 = var1 >>> 25;
      int var10 = var1 << 7;
      int var11 = var9 | var10;
      return var8 ^ var11;
   }

   private int Theta0(int var1) {
      int var2 = var1 >>> 7;
      int var3 = var1 << 25;
      int var4 = var2 | var3;
      int var5 = var1 >>> 18;
      int var6 = var1 << 14;
      int var7 = var5 | var6;
      int var8 = var4 ^ var7;
      int var9 = var1 >>> 3;
      return var8 ^ var9;
   }

   private int Theta1(int var1) {
      int var2 = var1 >>> 17;
      int var3 = var1 << 15;
      int var4 = var2 | var3;
      int var5 = var1 >>> 19;
      int var6 = var1 << 13;
      int var7 = var5 | var6;
      int var8 = var4 ^ var7;
      int var9 = var1 >>> 10;
      return var8 ^ var9;
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
      int var11 = this.H6;
      int var12 = var2 + 20;
      Pack.intToBigEndian(var11, var1, var12);
      int var13 = this.H7;
      int var14 = var2 + 24;
      Pack.intToBigEndian(var13, var1, var14);
      this.reset();
      return 28;
   }

   public String getAlgorithmName() {
      return "SHA-224";
   }

   public int getDigestSize() {
      return 28;
   }

   protected void processBlock() {
      for(int var1 = 16; var1 <= 63; ++var1) {
         int[] var2 = this.X;
         int[] var3 = this.X;
         int var4 = var1 - 2;
         int var5 = var3[var4];
         int var6 = this.Theta1(var5);
         int[] var7 = this.X;
         int var8 = var1 - 7;
         int var9 = var7[var8];
         int var10 = var6 + var9;
         int[] var11 = this.X;
         int var12 = var1 - 15;
         int var13 = var11[var12];
         int var14 = this.Theta0(var13);
         int var15 = var10 + var14;
         int[] var16 = this.X;
         int var17 = var1 - 16;
         int var18 = var16[var17];
         int var19 = var15 + var18;
         var2[var1] = var19;
      }

      int var20 = this.H1;
      int var21 = this.H2;
      int var22 = this.H3;
      int var23 = this.H4;
      int var24 = this.H5;
      int var25 = this.H6;
      int var26 = this.H7;
      int var27 = this.H8;
      int var28 = 0;

      for(int var29 = 0; var29 < 8; ++var29) {
         int var30 = this.Sum1(var24);
         int var31 = this.Ch(var24, var25, var26);
         int var32 = var30 + var31;
         int var33 = K[var28];
         int var34 = var32 + var33;
         int var35 = this.X[var28];
         int var36 = var34 + var35;
         int var37 = var27 + var36;
         int var38 = var23 + var37;
         int var39 = this.Sum0(var20);
         int var40 = this.Maj(var20, var21, var22);
         int var41 = var39 + var40;
         int var42 = var37 + var41;
         int var43 = var28 + 1;
         int var44 = this.Sum1(var38);
         int var45 = this.Ch(var38, var24, var25);
         int var46 = var44 + var45;
         int var47 = K[var43];
         int var48 = var46 + var47;
         int var49 = this.X[var43];
         int var50 = var48 + var49;
         int var51 = var26 + var50;
         int var52 = var22 + var51;
         int var53 = this.Sum0(var42);
         int var54 = this.Maj(var42, var20, var21);
         int var55 = var53 + var54;
         int var56 = var51 + var55;
         int var57 = var43 + 1;
         int var58 = this.Sum1(var52);
         int var59 = this.Ch(var52, var38, var24);
         int var60 = var58 + var59;
         int var61 = K[var57];
         int var62 = var60 + var61;
         int var63 = this.X[var57];
         int var64 = var62 + var63;
         int var65 = var25 + var64;
         int var66 = var21 + var65;
         int var67 = this.Sum0(var56);
         int var68 = this.Maj(var56, var42, var20);
         int var69 = var67 + var68;
         int var70 = var65 + var69;
         int var71 = var57 + 1;
         int var72 = this.Sum1(var66);
         int var73 = this.Ch(var66, var52, var38);
         int var74 = var72 + var73;
         int var75 = K[var71];
         int var76 = var74 + var75;
         int var77 = this.X[var71];
         int var78 = var76 + var77;
         int var79 = var24 + var78;
         int var80 = var20 + var79;
         int var81 = this.Sum0(var70);
         int var82 = this.Maj(var70, var56, var42);
         int var83 = var81 + var82;
         int var84 = var79 + var83;
         int var85 = var71 + 1;
         int var86 = this.Sum1(var80);
         int var87 = this.Ch(var80, var66, var52);
         int var88 = var86 + var87;
         int var89 = K[var85];
         int var90 = var88 + var89;
         int var91 = this.X[var85];
         int var92 = var90 + var91;
         int var93 = var38 + var92;
         var27 = var42 + var93;
         int var94 = this.Sum0(var84);
         int var95 = this.Maj(var84, var70, var56);
         int var96 = var94 + var95;
         var23 = var93 + var96;
         int var97 = var85 + 1;
         int var98 = this.Sum1(var27);
         int var99 = this.Ch(var27, var80, var66);
         int var100 = var98 + var99;
         int var101 = K[var97];
         int var102 = var100 + var101;
         int var103 = this.X[var97];
         int var104 = var102 + var103;
         int var105 = var52 + var104;
         var26 = var56 + var105;
         int var106 = this.Sum0(var23);
         int var107 = this.Maj(var23, var84, var70);
         int var108 = var106 + var107;
         var22 = var105 + var108;
         int var109 = var97 + 1;
         int var110 = this.Sum1(var26);
         int var111 = this.Ch(var26, var27, var80);
         int var112 = var110 + var111;
         int var113 = K[var109];
         int var114 = var112 + var113;
         int var115 = this.X[var109];
         int var116 = var114 + var115;
         int var117 = var66 + var116;
         var25 = var70 + var117;
         int var118 = this.Sum0(var22);
         int var119 = this.Maj(var22, var23, var84);
         int var120 = var118 + var119;
         var21 = var117 + var120;
         int var121 = var109 + 1;
         int var122 = this.Sum1(var25);
         int var123 = this.Ch(var25, var26, var27);
         int var124 = var122 + var123;
         int var125 = K[var121];
         int var126 = var124 + var125;
         int var127 = this.X[var121];
         int var128 = var126 + var127;
         int var129 = var80 + var128;
         var24 = var84 + var129;
         int var130 = this.Sum0(var21);
         int var131 = this.Maj(var21, var22, var23);
         int var132 = var130 + var131;
         var20 = var129 + var132;
         var28 = var121 + 1;
      }

      int var133 = this.H1 + var20;
      this.H1 = var133;
      int var134 = this.H2 + var21;
      this.H2 = var134;
      int var135 = this.H3 + var22;
      this.H3 = var135;
      int var136 = this.H4 + var23;
      this.H4 = var136;
      int var137 = this.H5 + var24;
      this.H5 = var137;
      int var138 = this.H6 + var25;
      this.H6 = var138;
      int var139 = this.H7 + var26;
      this.H7 = var139;
      int var140 = this.H8 + var27;
      this.H8 = var140;
      this.xOff = 0;

      for(int var141 = 0; var141 < 16; ++var141) {
         this.X[var141] = 0;
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
      this.H1 = -1056596264;
      this.H2 = 914150663;
      this.H3 = 812702999;
      this.H4 = -150054599;
      this.H5 = -4191439;
      this.H6 = 1750603025;
      this.H7 = 1694076839;
      this.H8 = -1090891868;
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
