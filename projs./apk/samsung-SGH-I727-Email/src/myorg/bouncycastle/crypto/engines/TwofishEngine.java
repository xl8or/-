package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public final class TwofishEngine implements BlockCipher {

   private static final int BLOCK_SIZE = 16;
   private static final int GF256_FDBK = 361;
   private static final int GF256_FDBK_2 = 180;
   private static final int GF256_FDBK_4 = 90;
   private static final int INPUT_WHITEN = 0;
   private static final int MAX_KEY_BITS = 256;
   private static final int MAX_ROUNDS = 16;
   private static final int OUTPUT_WHITEN = 4;
   private static final byte[][] P;
   private static final int P_00 = 1;
   private static final int P_01 = 0;
   private static final int P_02 = 0;
   private static final int P_03 = 1;
   private static final int P_04 = 1;
   private static final int P_10 = 0;
   private static final int P_11 = 0;
   private static final int P_12 = 1;
   private static final int P_13 = 1;
   private static final int P_14 = 0;
   private static final int P_20 = 1;
   private static final int P_21 = 1;
   private static final int P_22 = 0;
   private static final int P_23 = 0;
   private static final int P_24 = 0;
   private static final int P_30 = 0;
   private static final int P_31 = 1;
   private static final int P_32 = 1;
   private static final int P_33 = 0;
   private static final int P_34 = 1;
   private static final int ROUNDS = 16;
   private static final int ROUND_SUBKEYS = 8;
   private static final int RS_GF_FDBK = 333;
   private static final int SK_BUMP = 16843009;
   private static final int SK_ROTL = 9;
   private static final int SK_STEP = 33686018;
   private static final int TOTAL_SUBKEYS = 40;
   private boolean encrypting = 0;
   private int[] gMDS0;
   private int[] gMDS1;
   private int[] gMDS2;
   private int[] gMDS3;
   private int[] gSBox;
   private int[] gSubKeys;
   private int k64Cnt;
   private byte[] workingKey;


   static {
      byte[] var0 = new byte[2];
      byte[] var1 = new byte[]{(byte)169, (byte)103, (byte)179, (byte)232, (byte)4, (byte)253, (byte)163, (byte)118, (byte)154, (byte)146, (byte)128, (byte)120, (byte)228, (byte)221, (byte)209, (byte)56, (byte)13, (byte)198, (byte)53, (byte)152, (byte)24, (byte)247, (byte)236, (byte)108, (byte)67, (byte)117, (byte)55, (byte)38, (byte)250, (byte)19, (byte)148, (byte)72, (byte)242, (byte)208, (byte)139, (byte)48, (byte)132, (byte)84, (byte)223, (byte)35, (byte)25, (byte)91, (byte)61, (byte)89, (byte)243, (byte)174, (byte)162, (byte)130, (byte)99, (byte)1, (byte)131, (byte)46, (byte)217, (byte)81, (byte)155, (byte)124, (byte)166, (byte)235, (byte)165, (byte)190, (byte)22, (byte)12, (byte)227, (byte)97, (byte)192, (byte)140, (byte)58, (byte)245, (byte)115, (byte)44, (byte)37, (byte)11, (byte)187, (byte)78, (byte)137, (byte)107, (byte)83, (byte)106, (byte)180, (byte)241, (byte)225, (byte)230, (byte)189, (byte)69, (byte)226, (byte)244, (byte)182, (byte)102, (byte)204, (byte)149, (byte)3, (byte)86, (byte)212, (byte)28, (byte)30, (byte)215, (byte)251, (byte)195, (byte)142, (byte)181, (byte)233, (byte)207, (byte)191, (byte)186, (byte)234, (byte)119, (byte)57, (byte)175, (byte)51, (byte)201, (byte)98, (byte)113, (byte)129, (byte)121, (byte)9, (byte)173, (byte)36, (byte)205, (byte)249, (byte)216, (byte)229, (byte)197, (byte)185, (byte)77, (byte)68, (byte)8, (byte)134, (byte)231, (byte)161, (byte)29, (byte)170, (byte)237, (byte)6, (byte)112, (byte)178, (byte)210, (byte)65, (byte)123, (byte)160, (byte)17, (byte)49, (byte)194, (byte)39, (byte)144, (byte)32, (byte)246, (byte)96, (byte)255, (byte)150, (byte)92, (byte)177, (byte)171, (byte)158, (byte)156, (byte)82, (byte)27, (byte)95, (byte)147, (byte)10, (byte)239, (byte)145, (byte)133, (byte)73, (byte)238, (byte)45, (byte)79, (byte)143, (byte)59, (byte)71, (byte)135, (byte)109, (byte)70, (byte)214, (byte)62, (byte)105, (byte)100, (byte)42, (byte)206, (byte)203, (byte)47, (byte)252, (byte)151, (byte)5, (byte)122, (byte)172, (byte)127, (byte)213, (byte)26, (byte)75, (byte)14, (byte)167, (byte)90, (byte)40, (byte)20, (byte)63, (byte)41, (byte)136, (byte)60, (byte)76, (byte)2, (byte)184, (byte)218, (byte)176, (byte)23, (byte)85, (byte)31, (byte)138, (byte)125, (byte)87, (byte)199, (byte)141, (byte)116, (byte)183, (byte)196, (byte)159, (byte)114, (byte)126, (byte)21, (byte)34, (byte)18, (byte)88, (byte)7, (byte)153, (byte)52, (byte)110, (byte)80, (byte)222, (byte)104, (byte)101, (byte)188, (byte)219, (byte)248, (byte)200, (byte)168, (byte)43, (byte)64, (byte)220, (byte)254, (byte)50, (byte)164, (byte)202, (byte)16, (byte)33, (byte)240, (byte)211, (byte)93, (byte)15, (byte)0, (byte)111, (byte)157, (byte)54, (byte)66, (byte)74, (byte)94, (byte)193, (byte)224};
      var0[0] = (byte)var1;
      byte[] var2 = new byte[]{(byte)117, (byte)243, (byte)198, (byte)244, (byte)219, (byte)123, (byte)251, (byte)200, (byte)74, (byte)211, (byte)230, (byte)107, (byte)69, (byte)125, (byte)232, (byte)75, (byte)214, (byte)50, (byte)216, (byte)253, (byte)55, (byte)113, (byte)241, (byte)225, (byte)48, (byte)15, (byte)248, (byte)27, (byte)135, (byte)250, (byte)6, (byte)63, (byte)94, (byte)186, (byte)174, (byte)91, (byte)138, (byte)0, (byte)188, (byte)157, (byte)109, (byte)193, (byte)177, (byte)14, (byte)128, (byte)93, (byte)210, (byte)213, (byte)160, (byte)132, (byte)7, (byte)20, (byte)181, (byte)144, (byte)44, (byte)163, (byte)178, (byte)115, (byte)76, (byte)84, (byte)146, (byte)116, (byte)54, (byte)81, (byte)56, (byte)176, (byte)189, (byte)90, (byte)252, (byte)96, (byte)98, (byte)150, (byte)108, (byte)66, (byte)247, (byte)16, (byte)124, (byte)40, (byte)39, (byte)140, (byte)19, (byte)149, (byte)156, (byte)199, (byte)36, (byte)70, (byte)59, (byte)112, (byte)202, (byte)227, (byte)133, (byte)203, (byte)17, (byte)208, (byte)147, (byte)184, (byte)166, (byte)131, (byte)32, (byte)255, (byte)159, (byte)119, (byte)195, (byte)204, (byte)3, (byte)111, (byte)8, (byte)191, (byte)64, (byte)231, (byte)43, (byte)226, (byte)121, (byte)12, (byte)170, (byte)130, (byte)65, (byte)58, (byte)234, (byte)185, (byte)228, (byte)154, (byte)164, (byte)151, (byte)126, (byte)218, (byte)122, (byte)23, (byte)102, (byte)148, (byte)161, (byte)29, (byte)61, (byte)240, (byte)222, (byte)179, (byte)11, (byte)114, (byte)167, (byte)28, (byte)239, (byte)209, (byte)83, (byte)62, (byte)143, (byte)51, (byte)38, (byte)95, (byte)236, (byte)118, (byte)42, (byte)73, (byte)129, (byte)136, (byte)238, (byte)33, (byte)196, (byte)26, (byte)235, (byte)217, (byte)197, (byte)57, (byte)153, (byte)205, (byte)173, (byte)49, (byte)139, (byte)1, (byte)24, (byte)35, (byte)221, (byte)31, (byte)78, (byte)45, (byte)249, (byte)72, (byte)79, (byte)242, (byte)101, (byte)142, (byte)120, (byte)92, (byte)88, (byte)25, (byte)141, (byte)229, (byte)152, (byte)87, (byte)103, (byte)127, (byte)5, (byte)100, (byte)175, (byte)99, (byte)182, (byte)254, (byte)245, (byte)183, (byte)60, (byte)165, (byte)206, (byte)233, (byte)104, (byte)68, (byte)224, (byte)77, (byte)67, (byte)105, (byte)41, (byte)46, (byte)172, (byte)21, (byte)89, (byte)168, (byte)10, (byte)158, (byte)110, (byte)71, (byte)223, (byte)52, (byte)53, (byte)106, (byte)207, (byte)220, (byte)34, (byte)201, (byte)192, (byte)155, (byte)137, (byte)212, (byte)237, (byte)171, (byte)18, (byte)162, (byte)13, (byte)82, (byte)187, (byte)2, (byte)47, (byte)169, (byte)215, (byte)97, (byte)30, (byte)180, (byte)80, (byte)4, (byte)246, (byte)194, (byte)22, (byte)37, (byte)134, (byte)86, (byte)85, (byte)9, (byte)190, (byte)145};
      var0[1] = (byte)var2;
      P = (byte[][])var0;
   }

   public TwofishEngine() {
      int[] var1 = new int[256];
      this.gMDS0 = var1;
      int[] var2 = new int[256];
      this.gMDS1 = var2;
      int[] var3 = new int[256];
      this.gMDS2 = var3;
      int[] var4 = new int[256];
      this.gMDS3 = var4;
      this.k64Cnt = 0;
      this.workingKey = null;
      int[] var5 = new int[2];
      int[] var6 = new int[2];
      int[] var7 = new int[2];

      for(int var8 = 0; var8 < 256; ++var8) {
         int var9 = P[0][var8] & 255;
         var5[0] = var9;
         int var10 = this.Mx_X(var9) & 255;
         var6[0] = var10;
         int var11 = this.Mx_Y(var9) & 255;
         var7[0] = var11;
         int var12 = P[1][var8] & 255;
         var5[1] = var12;
         int var13 = this.Mx_X(var12) & 255;
         var6[1] = var13;
         int var14 = this.Mx_Y(var12) & 255;
         var7[1] = var14;
         int[] var15 = this.gMDS0;
         int var16 = var5[1];
         int var17 = var6[1] << 8;
         int var18 = var16 | var17;
         int var19 = var7[1] << 16;
         int var20 = var18 | var19;
         int var21 = var7[1] << 24;
         int var22 = var20 | var21;
         var15[var8] = var22;
         int[] var23 = this.gMDS1;
         int var24 = var7[0];
         int var25 = var7[0] << 8;
         int var26 = var24 | var25;
         int var27 = var6[0] << 16;
         int var28 = var26 | var27;
         int var29 = var5[0] << 24;
         int var30 = var28 | var29;
         var23[var8] = var30;
         int[] var31 = this.gMDS2;
         int var32 = var6[1];
         int var33 = var7[1] << 8;
         int var34 = var32 | var33;
         int var35 = var5[1] << 16;
         int var36 = var34 | var35;
         int var37 = var7[1] << 24;
         int var38 = var36 | var37;
         var31[var8] = var38;
         int[] var39 = this.gMDS3;
         int var40 = var6[0];
         int var41 = var5[0] << 8;
         int var42 = var40 | var41;
         int var43 = var7[0] << 16;
         int var44 = var42 | var43;
         int var45 = var6[0] << 24;
         int var46 = var44 | var45;
         var39[var8] = var46;
      }

   }

   private void Bits32ToBytes(int var1, byte[] var2, int var3) {
      byte var4 = (byte)var1;
      var2[var3] = var4;
      int var5 = var3 + 1;
      byte var6 = (byte)(var1 >> 8);
      var2[var5] = var6;
      int var7 = var3 + 2;
      byte var8 = (byte)(var1 >> 16);
      var2[var7] = var8;
      int var9 = var3 + 3;
      byte var10 = (byte)(var1 >> 24);
      var2[var9] = var10;
   }

   private int BytesTo32Bits(byte[] var1, int var2) {
      int var3 = var1[var2] & 255;
      int var4 = var2 + 1;
      int var5 = (var1[var4] & 255) << 8;
      int var6 = var3 | var5;
      int var7 = var2 + 2;
      int var8 = (var1[var7] & 255) << 16;
      int var9 = var6 | var8;
      int var10 = var2 + 3;
      int var11 = (var1[var10] & 255) << 24;
      return var9 | var11;
   }

   private int F32(int var1, int[] var2) {
      int var3 = this.b0(var1);
      int var4 = this.b1(var1);
      int var5 = this.b2(var1);
      int var6 = this.b3(var1);
      int var7 = var2[0];
      int var8 = var2[1];
      int var9 = var2[2];
      int var10 = var2[3];
      int var11 = 0;
      switch(this.k64Cnt & 3) {
      case 0:
         int var34 = P[1][var3] & 255;
         int var35 = this.b0(var10);
         var3 = var34 ^ var35;
         int var36 = P[0][var4] & 255;
         int var37 = this.b1(var10);
         var4 = var36 ^ var37;
         int var38 = P[0][var5] & 255;
         int var39 = this.b2(var10);
         var5 = var38 ^ var39;
         int var40 = P[1][var6] & 255;
         int var41 = this.b3(var10);
         var6 = var40 ^ var41;
      case 1:
         int[] var12 = this.gMDS0;
         int var13 = P[0][var3] & 255;
         int var14 = this.b0(var7);
         int var15 = var13 ^ var14;
         int var16 = var12[var15];
         int[] var17 = this.gMDS1;
         int var18 = P[0][var4] & 255;
         int var19 = this.b1(var7);
         int var20 = var18 ^ var19;
         int var21 = var17[var20];
         int var22 = var16 ^ var21;
         int[] var23 = this.gMDS2;
         int var24 = P[1][var5] & 255;
         int var25 = this.b2(var7);
         int var26 = var24 ^ var25;
         int var27 = var23[var26];
         int var28 = var22 ^ var27;
         int[] var29 = this.gMDS3;
         int var30 = P[1][var6] & 255;
         int var31 = this.b3(var7);
         int var32 = var30 ^ var31;
         int var33 = var29[var32];
         var11 = var28 ^ var33;
         break;
      case 3:
         int var42 = P[1][var3] & 255;
         int var43 = this.b0(var9);
         var3 = var42 ^ var43;
         int var44 = P[1][var4] & 255;
         int var45 = this.b1(var9);
         var4 = var44 ^ var45;
         int var46 = P[0][var5] & 255;
         int var47 = this.b2(var9);
         var5 = var46 ^ var47;
         int var48 = P[0][var6] & 255;
         int var49 = this.b3(var9);
         var6 = var48 ^ var49;
      case 2:
         int[] var50 = this.gMDS0;
         byte[] var51 = P[0];
         int var52 = P[0][var3] & 255;
         int var53 = this.b0(var8);
         int var54 = var52 ^ var53;
         int var55 = var51[var54] & 255;
         int var56 = this.b0(var7);
         int var57 = var55 ^ var56;
         int var58 = var50[var57];
         int[] var59 = this.gMDS1;
         byte[] var60 = P[0];
         int var61 = P[1][var4] & 255;
         int var62 = this.b1(var8);
         int var63 = var61 ^ var62;
         int var64 = var60[var63] & 255;
         int var65 = this.b1(var7);
         int var66 = var64 ^ var65;
         int var67 = var59[var66];
         int var68 = var58 ^ var67;
         int[] var69 = this.gMDS2;
         byte[] var70 = P[1];
         int var71 = P[0][var5] & 255;
         int var72 = this.b2(var8);
         int var73 = var71 ^ var72;
         int var74 = var70[var73] & 255;
         int var75 = this.b2(var7);
         int var76 = var74 ^ var75;
         int var77 = var69[var76];
         int var78 = var68 ^ var77;
         int[] var79 = this.gMDS3;
         byte[] var80 = P[1];
         int var81 = P[1][var6] & 255;
         int var82 = this.b3(var8);
         int var83 = var81 ^ var82;
         int var84 = var80[var83] & 255;
         int var85 = this.b3(var7);
         int var86 = var84 ^ var85;
         int var87 = var79[var86];
         var11 = var78 ^ var87;
      }

      return var11;
   }

   private int Fe32_0(int var1) {
      int[] var2 = this.gSBox;
      int var3 = (var1 & 255) * 2 + 0;
      int var4 = var2[var3];
      int[] var5 = this.gSBox;
      int var6 = (var1 >>> 8 & 255) * 2 + 1;
      int var7 = var5[var6];
      int var8 = var4 ^ var7;
      int[] var9 = this.gSBox;
      int var10 = (var1 >>> 16 & 255) * 2 + 512;
      int var11 = var9[var10];
      int var12 = var8 ^ var11;
      int[] var13 = this.gSBox;
      int var14 = (var1 >>> 24 & 255) * 2 + 513;
      int var15 = var13[var14];
      return var12 ^ var15;
   }

   private int Fe32_3(int var1) {
      int[] var2 = this.gSBox;
      int var3 = (var1 >>> 24 & 255) * 2 + 0;
      int var4 = var2[var3];
      int[] var5 = this.gSBox;
      int var6 = (var1 & 255) * 2 + 1;
      int var7 = var5[var6];
      int var8 = var4 ^ var7;
      int[] var9 = this.gSBox;
      int var10 = (var1 >>> 8 & 255) * 2 + 512;
      int var11 = var9[var10];
      int var12 = var8 ^ var11;
      int[] var13 = this.gSBox;
      int var14 = (var1 >>> 16 & 255) * 2 + 513;
      int var15 = var13[var14];
      return var12 ^ var15;
   }

   private int LFSR1(int var1) {
      int var2 = var1 >> 1;
      short var3;
      if((var1 & 1) != 0) {
         var3 = 180;
      } else {
         var3 = 0;
      }

      return var2 ^ var3;
   }

   private int LFSR2(int var1) {
      int var2 = var1 >> 2;
      short var3;
      if((var1 & 2) != 0) {
         var3 = 180;
      } else {
         var3 = 0;
      }

      int var4 = var2 ^ var3;
      byte var5;
      if((var1 & 1) != 0) {
         var5 = 90;
      } else {
         var5 = 0;
      }

      return var4 ^ var5;
   }

   private int Mx_X(int var1) {
      return this.LFSR2(var1) ^ var1;
   }

   private int Mx_Y(int var1) {
      int var2 = this.LFSR1(var1) ^ var1;
      int var3 = this.LFSR2(var1);
      return var2 ^ var3;
   }

   private int RS_MDS_Encode(int var1, int var2) {
      int var3 = var2;

      for(int var4 = 0; var4 < 4; ++var4) {
         var3 = this.RS_rem(var3);
      }

      int var5 = var3 ^ var1;

      for(int var6 = 0; var6 < 4; ++var6) {
         var5 = this.RS_rem(var5);
      }

      return var5;
   }

   private int RS_rem(int var1) {
      int var2 = var1 >>> 24 & 255;
      int var3 = var2 << 1;
      short var4;
      if((var2 & 128) != 0) {
         var4 = 333;
      } else {
         var4 = 0;
      }

      int var5 = (var3 ^ var4) & 255;
      int var6 = var2 >>> 1;
      short var7;
      if((var2 & 1) != 0) {
         var7 = 166;
      } else {
         var7 = 0;
      }

      int var8 = var6 ^ var7 ^ var5;
      int var9 = var1 << 8;
      int var10 = var8 << 24;
      int var11 = var9 ^ var10;
      int var12 = var5 << 16;
      int var13 = var11 ^ var12;
      int var14 = var8 << 8;
      return var13 ^ var14 ^ var2;
   }

   private int b0(int var1) {
      return var1 & 255;
   }

   private int b1(int var1) {
      return var1 >>> 8 & 255;
   }

   private int b2(int var1) {
      return var1 >>> 16 & 255;
   }

   private int b3(int var1) {
      return var1 >>> 24 & 255;
   }

   private void decryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = this.BytesTo32Bits(var1, var2);
      int var6 = this.gSubKeys[4];
      int var7 = var5 ^ var6;
      int var8 = var2 + 4;
      int var12 = this.BytesTo32Bits(var1, var8);
      int var13 = this.gSubKeys[5];
      int var14 = var12 ^ var13;
      int var15 = var2 + 8;
      int var19 = this.BytesTo32Bits(var1, var15);
      int var20 = this.gSubKeys[6];
      int var21 = var19 ^ var20;
      int var22 = var2 + 12;
      int var26 = this.BytesTo32Bits(var1, var22);
      int var27 = this.gSubKeys[7];
      int var28 = var26 ^ var27;
      int var29 = 0;

      for(int var30 = 39; var29 < 16; var29 += 2) {
         int var33 = this.Fe32_0(var7);
         int var36 = this.Fe32_3(var14);
         int var37 = var36 * 2 + var33;
         int[] var38 = this.gSubKeys;
         int var39 = var30 + -1;
         int var40 = var38[var30];
         int var41 = var37 + var40;
         int var42 = var28 ^ var41;
         int var43 = var21 << 1;
         int var44 = var21 >>> 31;
         int var45 = var43 | var44;
         int var46 = var33 + var36;
         int[] var47 = this.gSubKeys;
         int var48 = var39 + -1;
         int var49 = var47[var39];
         int var50 = var46 + var49;
         var21 = var45 ^ var50;
         int var51 = var42 >>> 1;
         int var52 = var42 << 31;
         var28 = var51 | var52;
         int var55 = this.Fe32_0(var21);
         int var58 = this.Fe32_3(var28);
         int var59 = var58 * 2 + var55;
         int[] var60 = this.gSubKeys;
         int var61 = var48 + -1;
         int var62 = var60[var48];
         int var63 = var59 + var62;
         int var64 = var14 ^ var63;
         int var65 = var7 << 1;
         int var66 = var7 >>> 31;
         int var67 = var65 | var66;
         int var68 = var55 + var58;
         int[] var69 = this.gSubKeys;
         var30 = var61 + -1;
         int var70 = var69[var61];
         int var71 = var68 + var70;
         var7 = var67 ^ var71;
         int var72 = var64 >>> 1;
         int var73 = var64 << 31;
         var14 = var72 | var73;
      }

      int var74 = this.gSubKeys[0] ^ var21;
      this.Bits32ToBytes(var74, var3, var4);
      int var79 = this.gSubKeys[1] ^ var28;
      int var80 = var4 + 4;
      this.Bits32ToBytes(var79, var3, var80);
      int var85 = this.gSubKeys[2] ^ var7;
      int var86 = var4 + 8;
      this.Bits32ToBytes(var85, var3, var86);
      int var91 = this.gSubKeys[3] ^ var14;
      int var92 = var4 + 12;
      this.Bits32ToBytes(var91, var3, var92);
   }

   private void encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = this.BytesTo32Bits(var1, var2);
      int var6 = this.gSubKeys[0];
      int var7 = var5 ^ var6;
      int var8 = var2 + 4;
      int var12 = this.BytesTo32Bits(var1, var8);
      int var13 = this.gSubKeys[1];
      int var14 = var12 ^ var13;
      int var15 = var2 + 8;
      int var19 = this.BytesTo32Bits(var1, var15);
      int var20 = this.gSubKeys[2];
      int var21 = var19 ^ var20;
      int var22 = var2 + 12;
      int var26 = this.BytesTo32Bits(var1, var22);
      int var27 = this.gSubKeys[3];
      int var28 = var26 ^ var27;
      int var29 = 0;

      for(int var30 = 8; var29 < 16; var29 += 2) {
         int var33 = this.Fe32_0(var7);
         int var36 = this.Fe32_3(var14);
         int var37 = var33 + var36;
         int[] var38 = this.gSubKeys;
         int var39 = var30 + 1;
         int var40 = var38[var30];
         int var41 = var37 + var40;
         int var42 = var21 ^ var41;
         int var43 = var42 >>> 1;
         int var44 = var42 << 31;
         var21 = var43 | var44;
         int var45 = var28 << 1;
         int var46 = var28 >>> 31;
         int var47 = var45 | var46;
         int var48 = var36 * 2 + var33;
         int[] var49 = this.gSubKeys;
         int var50 = var39 + 1;
         int var51 = var49[var39];
         int var52 = var48 + var51;
         var28 = var47 ^ var52;
         int var55 = this.Fe32_0(var21);
         int var58 = this.Fe32_3(var28);
         int var59 = var55 + var58;
         int[] var60 = this.gSubKeys;
         int var61 = var50 + 1;
         int var62 = var60[var50];
         int var63 = var59 + var62;
         int var64 = var7 ^ var63;
         int var65 = var64 >>> 1;
         int var66 = var64 << 31;
         var7 = var65 | var66;
         int var67 = var14 << 1;
         int var68 = var14 >>> 31;
         int var69 = var67 | var68;
         int var70 = var58 * 2 + var55;
         int[] var71 = this.gSubKeys;
         var30 = var61 + 1;
         int var72 = var71[var61];
         int var73 = var70 + var72;
         var14 = var69 ^ var73;
      }

      int var74 = this.gSubKeys[4] ^ var21;
      this.Bits32ToBytes(var74, var3, var4);
      int var79 = this.gSubKeys[5] ^ var28;
      int var80 = var4 + 4;
      this.Bits32ToBytes(var79, var3, var80);
      int var85 = this.gSubKeys[6] ^ var7;
      int var86 = var4 + 8;
      this.Bits32ToBytes(var85, var3, var86);
      int var91 = this.gSubKeys[7] ^ var14;
      int var92 = var4 + 12;
      this.Bits32ToBytes(var91, var3, var92);
   }

   private void setKey(byte[] var1) {
      int[] var2 = new int[4];
      int[] var3 = new int[4];
      int[] var4 = new int[4];
      int[] var5 = new int[40];
      this.gSubKeys = var5;
      int var6 = this.k64Cnt;
      byte var7 = 1;
      if(var6 < var7) {
         throw new IllegalArgumentException("Key size less than 64 bits");
      } else {
         int var8 = this.k64Cnt;
         byte var9 = 4;
         if(var8 > var9) {
            throw new IllegalArgumentException("Key size larger than 256 bits");
         } else {
            int var10 = 0;

            while(true) {
               int var11 = this.k64Cnt;
               if(var10 >= var11) {
                  int var31 = 0;

                  while(true) {
                     byte var33 = 20;
                     if(var31 >= var33) {
                        int var56 = var4[0];
                        int var57 = var4[1];
                        int var58 = var4[2];
                        int var59 = var4[3];
                        int[] var60 = new int[1024];
                        this.gSBox = var60;
                        int var61 = 0;

                        while(true) {
                           short var63 = 256;
                           if(var61 >= var63) {
                              return;
                           }

                           int var64 = var61;
                           int var65 = var61;
                           int var66 = var61;
                           int var67 = var61;
                           switch(this.k64Cnt & 3) {
                           case 0:
                              int var104 = P[1][var61] & 255;
                              int var107 = this.b0(var59);
                              var67 = var104 ^ var107;
                              int var108 = P[0][var61] & 255;
                              int var111 = this.b1(var59);
                              var66 = var108 ^ var111;
                              int var112 = P[0][var61] & 255;
                              int var115 = this.b2(var59);
                              var65 = var112 ^ var115;
                              int var116 = P[1][var61] & 255;
                              int var119 = this.b3(var59);
                              var64 = var116 ^ var119;
                           case 1:
                              int[] var68 = this.gSBox;
                              int var69 = var61 * 2;
                              int[] var70 = this.gMDS0;
                              int var71 = P[0][var61] & 255;
                              int var74 = this.b0(var56);
                              int var75 = var71 ^ var74;
                              int var76 = var70[var75];
                              var68[var69] = var76;
                              int[] var77 = this.gSBox;
                              int var78 = var61 * 2 + 1;
                              int[] var79 = this.gMDS1;
                              int var80 = P[0][var61] & 255;
                              int var83 = this.b1(var56);
                              int var84 = var80 ^ var83;
                              int var85 = var79[var84];
                              var77[var78] = var85;
                              int[] var86 = this.gSBox;
                              int var87 = var61 * 2 + 512;
                              int[] var88 = this.gMDS2;
                              int var89 = P[1][var61] & 255;
                              int var92 = this.b2(var56);
                              int var93 = var89 ^ var92;
                              int var94 = var88[var93];
                              var86[var87] = var94;
                              int[] var95 = this.gSBox;
                              int var96 = var61 * 2 + 513;
                              int[] var97 = this.gMDS3;
                              int var98 = P[1][var61] & 255;
                              int var101 = this.b3(var56);
                              int var102 = var98 ^ var101;
                              int var103 = var97[var102];
                              var95[var96] = var103;
                              break;
                           case 3:
                              int var120 = P[1][var67] & 255;
                              int var123 = this.b0(var58);
                              var67 = var120 ^ var123;
                              int var124 = P[1][var66] & 255;
                              int var127 = this.b1(var58);
                              var66 = var124 ^ var127;
                              int var128 = P[0][var65] & 255;
                              int var131 = this.b2(var58);
                              var65 = var128 ^ var131;
                              int var132 = P[0][var64] & 255;
                              int var135 = this.b3(var58);
                              var64 = var132 ^ var135;
                           case 2:
                              int[] var136 = this.gSBox;
                              int var137 = var61 * 2;
                              int[] var138 = this.gMDS0;
                              byte[] var139 = P[0];
                              int var140 = P[0][var67] & 255;
                              int var143 = this.b0(var57);
                              int var144 = var140 ^ var143;
                              int var145 = var139[var144] & 255;
                              int var148 = this.b0(var56);
                              int var149 = var145 ^ var148;
                              int var150 = var138[var149];
                              var136[var137] = var150;
                              int[] var151 = this.gSBox;
                              int var152 = var61 * 2 + 1;
                              int[] var153 = this.gMDS1;
                              byte[] var154 = P[0];
                              int var155 = P[1][var66] & 255;
                              int var158 = this.b1(var57);
                              int var159 = var155 ^ var158;
                              int var160 = var154[var159] & 255;
                              int var163 = this.b1(var56);
                              int var164 = var160 ^ var163;
                              int var165 = var153[var164];
                              var151[var152] = var165;
                              int[] var166 = this.gSBox;
                              int var167 = var61 * 2 + 512;
                              int[] var168 = this.gMDS2;
                              byte[] var169 = P[1];
                              int var170 = P[0][var65] & 255;
                              int var173 = this.b2(var57);
                              int var174 = var170 ^ var173;
                              int var175 = var169[var174] & 255;
                              int var178 = this.b2(var56);
                              int var179 = var175 ^ var178;
                              int var180 = var168[var179];
                              var166[var167] = var180;
                              int[] var181 = this.gSBox;
                              int var182 = var61 * 2 + 513;
                              int[] var183 = this.gMDS3;
                              byte[] var184 = P[1];
                              int var185 = P[1][var64] & 255;
                              int var188 = this.b3(var57);
                              int var189 = var185 ^ var188;
                              int var190 = var184[var189] & 255;
                              int var193 = this.b3(var56);
                              int var194 = var190 ^ var193;
                              int var195 = var183[var194];
                              var181[var182] = var195;
                           }

                           ++var61;
                        }
                     }

                     int var34 = var31 * 33686018;
                     int var38 = this.F32(var34, var2);
                     int var39 = 16843009 + var34;
                     int var43 = this.F32(var39, var3);
                     int var44 = var43 << 8;
                     int var45 = var43 >>> 24;
                     int var46 = var44 | var45;
                     int var47 = var38 + var46;
                     int[] var48 = this.gSubKeys;
                     int var49 = var31 * 2;
                     var48[var49] = var47;
                     int var50 = var47 + var46;
                     int[] var51 = this.gSubKeys;
                     int var52 = var31 * 2 + 1;
                     int var53 = var50 << 9;
                     int var54 = var50 >>> 23;
                     int var55 = var53 | var54;
                     var51[var52] = var55;
                     ++var31;
                  }
               }

               int var14 = var10 * 8;
               int var18 = this.BytesTo32Bits(var1, var14);
               var2[var10] = var18;
               int var19 = var14 + 4;
               int var23 = this.BytesTo32Bits(var1, var19);
               var3[var10] = var23;
               int var24 = this.k64Cnt - 1 - var10;
               int var25 = var2[var10];
               int var26 = var3[var10];
               int var30 = this.RS_MDS_Encode(var25, var26);
               var4[var24] = var30;
               ++var10;
            }
         }
      }
   }

   public String getAlgorithmName() {
      return "Twofish";
   }

   public int getBlockSize() {
      return 16;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof KeyParameter) {
         this.encrypting = var1;
         byte[] var3 = ((KeyParameter)var2).getKey();
         this.workingKey = var3;
         int var4 = this.workingKey.length / 8;
         this.k64Cnt = var4;
         byte[] var5 = this.workingKey;
         this.setKey(var5);
      } else {
         StringBuilder var6 = (new StringBuilder()).append("invalid parameter passed to Twofish init - ");
         String var7 = var2.getClass().getName();
         String var8 = var6.append(var7).toString();
         throw new IllegalArgumentException(var8);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(this.workingKey == null) {
         throw new IllegalStateException("Twofish not initialised");
      } else {
         int var5 = var2 + 16;
         int var6 = var1.length;
         if(var5 > var6) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var7 = var4 + 16;
            int var8 = var3.length;
            if(var7 > var8) {
               throw new DataLengthException("output buffer too short");
            } else {
               if(this.encrypting) {
                  this.encryptBlock(var1, var2, var3, var4);
               } else {
                  this.decryptBlock(var1, var2, var3, var4);
               }

               return 16;
            }
         }
      }
   }

   public void reset() {
      if(this.workingKey != null) {
         byte[] var1 = this.workingKey;
         this.setKey(var1);
      }
   }
}
