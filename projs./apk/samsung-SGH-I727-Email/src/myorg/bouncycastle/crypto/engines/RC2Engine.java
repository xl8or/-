package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.RC2Parameters;

public class RC2Engine implements BlockCipher {

   private static final int BLOCK_SIZE = 8;
   private static byte[] piTable = new byte[]{(byte)217, (byte)120, (byte)249, (byte)196, (byte)25, (byte)221, (byte)181, (byte)237, (byte)40, (byte)233, (byte)253, (byte)121, (byte)74, (byte)160, (byte)216, (byte)157, (byte)198, (byte)126, (byte)55, (byte)131, (byte)43, (byte)118, (byte)83, (byte)142, (byte)98, (byte)76, (byte)100, (byte)136, (byte)68, (byte)139, (byte)251, (byte)162, (byte)23, (byte)154, (byte)89, (byte)245, (byte)135, (byte)179, (byte)79, (byte)19, (byte)97, (byte)69, (byte)109, (byte)141, (byte)9, (byte)129, (byte)125, (byte)50, (byte)189, (byte)143, (byte)64, (byte)235, (byte)134, (byte)183, (byte)123, (byte)11, (byte)240, (byte)149, (byte)33, (byte)34, (byte)92, (byte)107, (byte)78, (byte)130, (byte)84, (byte)214, (byte)101, (byte)147, (byte)206, (byte)96, (byte)178, (byte)28, (byte)115, (byte)86, (byte)192, (byte)20, (byte)167, (byte)140, (byte)241, (byte)220, (byte)18, (byte)117, (byte)202, (byte)31, (byte)59, (byte)190, (byte)228, (byte)209, (byte)66, (byte)61, (byte)212, (byte)48, (byte)163, (byte)60, (byte)182, (byte)38, (byte)111, (byte)191, (byte)14, (byte)218, (byte)70, (byte)105, (byte)7, (byte)87, (byte)39, (byte)242, (byte)29, (byte)155, (byte)188, (byte)148, (byte)67, (byte)3, (byte)248, (byte)17, (byte)199, (byte)246, (byte)144, (byte)239, (byte)62, (byte)231, (byte)6, (byte)195, (byte)213, (byte)47, (byte)200, (byte)102, (byte)30, (byte)215, (byte)8, (byte)232, (byte)234, (byte)222, (byte)128, (byte)82, (byte)238, (byte)247, (byte)132, (byte)170, (byte)114, (byte)172, (byte)53, (byte)77, (byte)106, (byte)42, (byte)150, (byte)26, (byte)210, (byte)113, (byte)90, (byte)21, (byte)73, (byte)116, (byte)75, (byte)159, (byte)208, (byte)94, (byte)4, (byte)24, (byte)164, (byte)236, (byte)194, (byte)224, (byte)65, (byte)110, (byte)15, (byte)81, (byte)203, (byte)204, (byte)36, (byte)145, (byte)175, (byte)80, (byte)161, (byte)244, (byte)112, (byte)57, (byte)153, (byte)124, (byte)58, (byte)133, (byte)35, (byte)184, (byte)180, (byte)122, (byte)252, (byte)2, (byte)54, (byte)91, (byte)37, (byte)85, (byte)151, (byte)49, (byte)45, (byte)93, (byte)250, (byte)152, (byte)227, (byte)138, (byte)146, (byte)174, (byte)5, (byte)223, (byte)41, (byte)16, (byte)103, (byte)108, (byte)186, (byte)201, (byte)211, (byte)0, (byte)230, (byte)207, (byte)225, (byte)158, (byte)168, (byte)44, (byte)99, (byte)22, (byte)1, (byte)63, (byte)88, (byte)226, (byte)137, (byte)169, (byte)13, (byte)56, (byte)52, (byte)27, (byte)171, (byte)51, (byte)255, (byte)176, (byte)187, (byte)72, (byte)12, (byte)95, (byte)185, (byte)177, (byte)205, (byte)46, (byte)197, (byte)243, (byte)219, (byte)71, (byte)229, (byte)165, (byte)156, (byte)119, (byte)10, (byte)166, (byte)32, (byte)104, (byte)254, (byte)127, (byte)193, (byte)173};
   private boolean encrypting;
   private int[] workingKey;


   public RC2Engine() {}

   private void decryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = var2 + 7;
      int var6 = (var1[var5] & 255) << 8;
      int var7 = var2 + 6;
      int var8 = var1[var7] & 255;
      int var9 = var6 + var8;
      int var10 = var2 + 5;
      int var11 = (var1[var10] & 255) << 8;
      int var12 = var2 + 4;
      int var13 = var1[var12] & 255;
      int var14 = var11 + var13;
      int var15 = var2 + 3;
      int var16 = (var1[var15] & 255) << 8;
      int var17 = var2 + 2;
      int var18 = var1[var17] & 255;
      int var19 = var16 + var18;
      int var20 = var2 + 1;
      int var21 = (var1[var20] & 255) << 8;
      int var22 = var2 + 0;
      int var23 = var1[var22] & 255;
      int var24 = var21 + var23;

      for(int var25 = 60; var25 >= 44; var25 += -4) {
         int var26 = this.rotateWordLeft(var9, 11);
         int var27 = ~var14 & var24;
         int var28 = var19 & var14;
         int var29 = var27 + var28;
         int[] var30 = this.workingKey;
         int var31 = var25 + 3;
         int var32 = var30[var31];
         int var33 = var29 + var32;
         var9 = var26 - var33;
         int var34 = this.rotateWordLeft(var14, 13);
         int var35 = ~var19 & var9;
         int var36 = var24 & var19;
         int var37 = var35 + var36;
         int[] var38 = this.workingKey;
         int var39 = var25 + 2;
         int var40 = var38[var39];
         int var41 = var37 + var40;
         var14 = var34 - var41;
         int var42 = this.rotateWordLeft(var19, 14);
         int var43 = ~var24 & var14;
         int var44 = var9 & var24;
         int var45 = var43 + var44;
         int[] var46 = this.workingKey;
         int var47 = var25 + 1;
         int var48 = var46[var47];
         int var49 = var45 + var48;
         var19 = var42 - var49;
         int var50 = this.rotateWordLeft(var24, 15);
         int var51 = ~var9 & var19;
         int var52 = var14 & var9;
         int var53 = var51 + var52;
         int var54 = this.workingKey[var25];
         int var55 = var53 + var54;
         var24 = var50 - var55;
      }

      int[] var56 = this.workingKey;
      int var57 = var14 & 63;
      int var58 = var56[var57];
      int var59 = var9 - var58;
      int[] var60 = this.workingKey;
      int var61 = var19 & 63;
      int var62 = var60[var61];
      int var63 = var14 - var62;
      int[] var64 = this.workingKey;
      int var65 = var24 & 63;
      int var66 = var64[var65];
      int var67 = var19 - var66;
      int[] var68 = this.workingKey;
      int var69 = var59 & 63;
      int var70 = var68[var69];
      int var71 = var24 - var70;

      for(int var72 = 40; var72 >= 20; var72 += -4) {
         int var73 = this.rotateWordLeft(var59, 11);
         int var74 = ~var63 & var71;
         int var75 = var67 & var63;
         int var76 = var74 + var75;
         int[] var77 = this.workingKey;
         int var78 = var72 + 3;
         int var79 = var77[var78];
         int var80 = var76 + var79;
         var59 = var73 - var80;
         int var81 = this.rotateWordLeft(var63, 13);
         int var82 = ~var67 & var59;
         int var83 = var71 & var67;
         int var84 = var82 + var83;
         int[] var85 = this.workingKey;
         int var86 = var72 + 2;
         int var87 = var85[var86];
         int var88 = var84 + var87;
         var63 = var81 - var88;
         int var89 = this.rotateWordLeft(var67, 14);
         int var90 = ~var71 & var63;
         int var91 = var59 & var71;
         int var92 = var90 + var91;
         int[] var93 = this.workingKey;
         int var94 = var72 + 1;
         int var95 = var93[var94];
         int var96 = var92 + var95;
         var67 = var89 - var96;
         int var97 = this.rotateWordLeft(var71, 15);
         int var98 = ~var59 & var67;
         int var99 = var63 & var59;
         int var100 = var98 + var99;
         int var101 = this.workingKey[var72];
         int var102 = var100 + var101;
         var71 = var97 - var102;
      }

      int[] var103 = this.workingKey;
      int var104 = var63 & 63;
      int var105 = var103[var104];
      int var106 = var59 - var105;
      int[] var107 = this.workingKey;
      int var108 = var67 & 63;
      int var109 = var107[var108];
      int var110 = var63 - var109;
      int[] var111 = this.workingKey;
      int var112 = var71 & 63;
      int var113 = var111[var112];
      int var114 = var67 - var113;
      int[] var115 = this.workingKey;
      int var116 = var106 & 63;
      int var117 = var115[var116];
      int var118 = var71 - var117;

      for(int var119 = 16; var119 >= 0; var119 += -4) {
         int var120 = this.rotateWordLeft(var106, 11);
         int var121 = ~var110 & var118;
         int var122 = var114 & var110;
         int var123 = var121 + var122;
         int[] var124 = this.workingKey;
         int var125 = var119 + 3;
         int var126 = var124[var125];
         int var127 = var123 + var126;
         var106 = var120 - var127;
         int var128 = this.rotateWordLeft(var110, 13);
         int var129 = ~var114 & var106;
         int var130 = var118 & var114;
         int var131 = var129 + var130;
         int[] var132 = this.workingKey;
         int var133 = var119 + 2;
         int var134 = var132[var133];
         int var135 = var131 + var134;
         var110 = var128 - var135;
         int var136 = this.rotateWordLeft(var114, 14);
         int var137 = ~var118 & var110;
         int var138 = var106 & var118;
         int var139 = var137 + var138;
         int[] var140 = this.workingKey;
         int var141 = var119 + 1;
         int var142 = var140[var141];
         int var143 = var139 + var142;
         var114 = var136 - var143;
         int var144 = this.rotateWordLeft(var118, 15);
         int var145 = ~var106 & var114;
         int var146 = var110 & var106;
         int var147 = var145 + var146;
         int var148 = this.workingKey[var119];
         int var149 = var147 + var148;
         var118 = var144 - var149;
      }

      int var150 = var4 + 0;
      byte var151 = (byte)var118;
      var3[var150] = var151;
      int var152 = var4 + 1;
      byte var153 = (byte)(var118 >> 8);
      var3[var152] = var153;
      int var154 = var4 + 2;
      byte var155 = (byte)var114;
      var3[var154] = var155;
      int var156 = var4 + 3;
      byte var157 = (byte)(var114 >> 8);
      var3[var156] = var157;
      int var158 = var4 + 4;
      byte var159 = (byte)var110;
      var3[var158] = var159;
      int var160 = var4 + 5;
      byte var161 = (byte)(var110 >> 8);
      var3[var160] = var161;
      int var162 = var4 + 6;
      byte var163 = (byte)var106;
      var3[var162] = var163;
      int var164 = var4 + 7;
      byte var165 = (byte)(var106 >> 8);
      var3[var164] = var165;
   }

   private void encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = var2 + 7;
      int var6 = (var1[var5] & 255) << 8;
      int var7 = var2 + 6;
      int var8 = var1[var7] & 255;
      int var9 = var6 + var8;
      int var10 = var2 + 5;
      int var11 = (var1[var10] & 255) << 8;
      int var12 = var2 + 4;
      int var13 = var1[var12] & 255;
      int var14 = var11 + var13;
      int var15 = var2 + 3;
      int var16 = (var1[var15] & 255) << 8;
      int var17 = var2 + 2;
      int var18 = var1[var17] & 255;
      int var19 = var16 + var18;
      int var20 = var2 + 1;
      int var21 = (var1[var20] & 255) << 8;
      int var22 = var2 + 0;
      int var23 = var1[var22] & 255;
      int var24 = var21 + var23;

      for(int var25 = 0; var25 <= 16; var25 += 4) {
         int var26 = (~var9 & var19) + var24;
         int var27 = var14 & var9;
         int var28 = var26 + var27;
         int var29 = this.workingKey[var25];
         int var30 = var28 + var29;
         var24 = this.rotateWordLeft(var30, 1);
         int var31 = (~var24 & var14) + var19;
         int var32 = var9 & var24;
         int var33 = var31 + var32;
         int[] var34 = this.workingKey;
         int var35 = var25 + 1;
         int var36 = var34[var35];
         int var37 = var33 + var36;
         var19 = this.rotateWordLeft(var37, 2);
         int var38 = (~var19 & var9) + var14;
         int var39 = var24 & var19;
         int var40 = var38 + var39;
         int[] var41 = this.workingKey;
         int var42 = var25 + 2;
         int var43 = var41[var42];
         int var44 = var40 + var43;
         var14 = this.rotateWordLeft(var44, 3);
         int var45 = (~var14 & var24) + var9;
         int var46 = var19 & var14;
         int var47 = var45 + var46;
         int[] var48 = this.workingKey;
         int var49 = var25 + 3;
         int var50 = var48[var49];
         int var51 = var47 + var50;
         var9 = this.rotateWordLeft(var51, 5);
      }

      int[] var52 = this.workingKey;
      int var53 = var9 & 63;
      int var54 = var52[var53];
      int var55 = var24 + var54;
      int[] var56 = this.workingKey;
      int var57 = var55 & 63;
      int var58 = var56[var57];
      int var59 = var19 + var58;
      int[] var60 = this.workingKey;
      int var61 = var59 & 63;
      int var62 = var60[var61];
      int var63 = var14 + var62;
      int[] var64 = this.workingKey;
      int var65 = var63 & 63;
      int var66 = var64[var65];
      int var67 = var9 + var66;

      for(int var68 = 20; var68 <= 40; var68 += 4) {
         int var69 = (~var67 & var59) + var55;
         int var70 = var63 & var67;
         int var71 = var69 + var70;
         int var72 = this.workingKey[var68];
         int var73 = var71 + var72;
         var55 = this.rotateWordLeft(var73, 1);
         int var74 = (~var55 & var63) + var59;
         int var75 = var67 & var55;
         int var76 = var74 + var75;
         int[] var77 = this.workingKey;
         int var78 = var68 + 1;
         int var79 = var77[var78];
         int var80 = var76 + var79;
         var59 = this.rotateWordLeft(var80, 2);
         int var81 = (~var59 & var67) + var63;
         int var82 = var55 & var59;
         int var83 = var81 + var82;
         int[] var84 = this.workingKey;
         int var85 = var68 + 2;
         int var86 = var84[var85];
         int var87 = var83 + var86;
         var63 = this.rotateWordLeft(var87, 3);
         int var88 = (~var63 & var55) + var67;
         int var89 = var59 & var63;
         int var90 = var88 + var89;
         int[] var91 = this.workingKey;
         int var92 = var68 + 3;
         int var93 = var91[var92];
         int var94 = var90 + var93;
         var67 = this.rotateWordLeft(var94, 5);
      }

      int[] var95 = this.workingKey;
      int var96 = var67 & 63;
      int var97 = var95[var96];
      int var98 = var55 + var97;
      int[] var99 = this.workingKey;
      int var100 = var98 & 63;
      int var101 = var99[var100];
      int var102 = var59 + var101;
      int[] var103 = this.workingKey;
      int var104 = var102 & 63;
      int var105 = var103[var104];
      int var106 = var63 + var105;
      int[] var107 = this.workingKey;
      int var108 = var106 & 63;
      int var109 = var107[var108];
      int var110 = var67 + var109;

      for(int var111 = 44; var111 < 64; var111 += 4) {
         int var112 = (~var110 & var102) + var98;
         int var113 = var106 & var110;
         int var114 = var112 + var113;
         int var115 = this.workingKey[var111];
         int var116 = var114 + var115;
         var98 = this.rotateWordLeft(var116, 1);
         int var117 = (~var98 & var106) + var102;
         int var118 = var110 & var98;
         int var119 = var117 + var118;
         int[] var120 = this.workingKey;
         int var121 = var111 + 1;
         int var122 = var120[var121];
         int var123 = var119 + var122;
         var102 = this.rotateWordLeft(var123, 2);
         int var124 = (~var102 & var110) + var106;
         int var125 = var98 & var102;
         int var126 = var124 + var125;
         int[] var127 = this.workingKey;
         int var128 = var111 + 2;
         int var129 = var127[var128];
         int var130 = var126 + var129;
         var106 = this.rotateWordLeft(var130, 3);
         int var131 = (~var106 & var98) + var110;
         int var132 = var102 & var106;
         int var133 = var131 + var132;
         int[] var134 = this.workingKey;
         int var135 = var111 + 3;
         int var136 = var134[var135];
         int var137 = var133 + var136;
         var110 = this.rotateWordLeft(var137, 5);
      }

      int var138 = var4 + 0;
      byte var139 = (byte)var98;
      var3[var138] = var139;
      int var140 = var4 + 1;
      byte var141 = (byte)(var98 >> 8);
      var3[var140] = var141;
      int var142 = var4 + 2;
      byte var143 = (byte)var102;
      var3[var142] = var143;
      int var144 = var4 + 3;
      byte var145 = (byte)(var102 >> 8);
      var3[var144] = var145;
      int var146 = var4 + 4;
      byte var147 = (byte)var106;
      var3[var146] = var147;
      int var148 = var4 + 5;
      byte var149 = (byte)(var106 >> 8);
      var3[var148] = var149;
      int var150 = var4 + 6;
      byte var151 = (byte)var110;
      var3[var150] = var151;
      int var152 = var4 + 7;
      byte var153 = (byte)(var110 >> 8);
      var3[var152] = var153;
   }

   private int[] generateWorkingKey(byte[] var1, int var2) {
      int[] var3 = new int[128];
      int var4 = 0;

      while(true) {
         int var5 = var1.length;
         if(var4 == var5) {
            int var7 = var1.length;
            if(var7 < 128) {
               int var8 = 0;
               int var9 = var7 - 1;
               int var10 = var3[var9];

               while(true) {
                  byte[] var11 = piTable;
                  int var12 = var8 + 1;
                  int var13 = var3[var8] + var10 & 255;
                  var10 = var11[var13] & 255;
                  int var14 = var7 + 1;
                  var3[var7] = var10;
                  if(var14 >= 128) {
                     break;
                  }

                  var8 = var12;
                  var7 = var14;
               }
            }

            var7 = var2 + 7 >> 3;
            byte[] var16 = piTable;
            int var17 = 128 - var7;
            int var18 = var3[var17];
            int var19 = -var2 & 7;
            int var20 = 255 >> var19;
            int var21 = var18 & var20;
            int var22 = var16[var21] & 255;
            int var23 = 128 - var7;
            var3[var23] = var22;

            for(int var24 = 128 - var7 - 1; var24 >= 0; var24 += -1) {
               byte[] var25 = piTable;
               int var26 = var24 + var7;
               int var27 = var3[var26] ^ var22;
               int var28 = var25[var27] & 255;
               var3[var24] = var28;
            }

            int[] var29 = new int[64];
            byte var37 = 0;

            while(true) {
               int var30 = var29.length;
               if(var37 == var30) {
                  return var29;
               }

               int var31 = var37 * 2;
               int var32 = var3[var31];
               int var33 = var37 * 2 + 1;
               int var34 = var3[var33] << 8;
               int var35 = var32 + var34;
               var29[var37] = var35;
               int var36 = var37 + 1;
            }
         }

         int var6 = var1[var4] & 255;
         var3[var4] = var6;
         ++var4;
      }
   }

   private int rotateWordLeft(int var1, int var2) {
      int var3 = var1 & '\uffff';
      int var4 = var3 << var2;
      int var5 = 16 - var2;
      int var6 = var3 >> var5;
      return var4 | var6;
   }

   public String getAlgorithmName() {
      return "RC2";
   }

   public int getBlockSize() {
      return 8;
   }

   public void init(boolean var1, CipherParameters var2) {
      this.encrypting = var1;
      if(var2 instanceof RC2Parameters) {
         RC2Parameters var3 = (RC2Parameters)var2;
         byte[] var4 = var3.getKey();
         int var5 = var3.getEffectiveKeyBits();
         int[] var6 = this.generateWorkingKey(var4, var5);
         this.workingKey = var6;
      } else if(var2 instanceof KeyParameter) {
         byte[] var7 = ((KeyParameter)var2).getKey();
         int var8 = var7.length * 8;
         int[] var9 = this.generateWorkingKey(var7, var8);
         this.workingKey = var9;
      } else {
         StringBuilder var10 = (new StringBuilder()).append("invalid parameter passed to RC2 init - ");
         String var11 = var2.getClass().getName();
         String var12 = var10.append(var11).toString();
         throw new IllegalArgumentException(var12);
      }
   }

   public final int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(this.workingKey == null) {
         throw new IllegalStateException("RC2 engine not initialised");
      } else {
         int var5 = var2 + 8;
         int var6 = var1.length;
         if(var5 > var6) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var7 = var4 + 8;
            int var8 = var3.length;
            if(var7 > var8) {
               throw new DataLengthException("output buffer too short");
            } else {
               if(this.encrypting) {
                  this.encryptBlock(var1, var2, var3, var4);
               } else {
                  this.decryptBlock(var1, var2, var3, var4);
               }

               return 8;
            }
         }
      }
   }

   public void reset() {}
}
