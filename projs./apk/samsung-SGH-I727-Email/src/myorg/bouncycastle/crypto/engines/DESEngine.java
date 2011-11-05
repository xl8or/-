package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class DESEngine implements BlockCipher {

   protected static final int BLOCK_SIZE = 8;
   private static final int[] SP1;
   private static final int[] SP2;
   private static final int[] SP3;
   private static final int[] SP4;
   private static final int[] SP5;
   private static final int[] SP6;
   private static final int[] SP7;
   private static final int[] SP8;
   private static final int[] bigbyte;
   private static final short[] bytebit;
   private static final byte[] pc1;
   private static final byte[] pc2;
   private static final byte[] totrot;
   private int[] workingKey = null;


   static {
      ((Object[])null)[0] = (short)null;
      ((Object[])null)[1] = (short)null;
      ((Object[])null)[2] = (short)null;
      ((Object[])null)[3] = (short)null;
      ((Object[])null)[4] = (short)null;
      ((Object[])null)[5] = (short)null;
      ((Object[])null)[6] = (short)null;
      ((Object[])null)[7] = (short)null;
      bytebit = null;
      bigbyte = new int[]{8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, '\u8000', 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1};
      pc1 = new byte[]{(byte)56, (byte)48, (byte)40, (byte)32, (byte)24, (byte)16, (byte)8, (byte)0, (byte)57, (byte)49, (byte)41, (byte)33, (byte)25, (byte)17, (byte)9, (byte)1, (byte)58, (byte)50, (byte)42, (byte)34, (byte)26, (byte)18, (byte)10, (byte)2, (byte)59, (byte)51, (byte)43, (byte)35, (byte)62, (byte)54, (byte)46, (byte)38, (byte)30, (byte)22, (byte)14, (byte)6, (byte)61, (byte)53, (byte)45, (byte)37, (byte)29, (byte)21, (byte)13, (byte)5, (byte)60, (byte)52, (byte)44, (byte)36, (byte)28, (byte)20, (byte)12, (byte)4, (byte)27, (byte)19, (byte)11, (byte)3};
      totrot = new byte[]{(byte)1, (byte)2, (byte)4, (byte)6, (byte)8, (byte)10, (byte)12, (byte)14, (byte)15, (byte)17, (byte)19, (byte)21, (byte)23, (byte)25, (byte)27, (byte)28};
      pc2 = new byte[]{(byte)13, (byte)16, (byte)10, (byte)23, (byte)0, (byte)4, (byte)2, (byte)27, (byte)14, (byte)5, (byte)20, (byte)9, (byte)22, (byte)18, (byte)11, (byte)3, (byte)25, (byte)7, (byte)15, (byte)6, (byte)26, (byte)19, (byte)12, (byte)1, (byte)40, (byte)51, (byte)30, (byte)36, (byte)46, (byte)54, (byte)29, (byte)39, (byte)50, (byte)44, (byte)32, (byte)47, (byte)43, (byte)48, (byte)38, (byte)55, (byte)33, (byte)52, (byte)45, (byte)41, (byte)49, (byte)35, (byte)28, (byte)31};
      SP1 = new int[]{16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756};
      SP2 = new int[]{-2146402272, -2147450880, '\u8000', 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, Integer.MIN_VALUE, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, Integer.MIN_VALUE, '\u8000', 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, '\u8020', -2146402304, -2146435072, '\u8020', 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, '\u8000', -2146435072, -2147450880, 32, -2146402272, 1081376, 32, '\u8000', Integer.MIN_VALUE, '\u8020', -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, '\u8020', Integer.MIN_VALUE, -2146435040, -2146402272, 1081344};
      SP3 = new int[]{520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584};
      SP4 = new int[]{8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928};
      SP5 = new int[]{256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080};
      SP6 = new int[]{536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312};
      SP7 = new int[]{2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154};
      SP8 = new int[]{268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696};
   }

   public DESEngine() {}

   protected void desFunc(int[] var1, byte[] var2, int var3, byte[] var4, int var5) {
      int var6 = var3 + 0;
      int var7 = (var2[var6] & 255) << 24;
      int var8 = var3 + 1;
      int var9 = (var2[var8] & 255) << 16;
      int var10 = var7 | var9;
      int var11 = var3 + 2;
      int var12 = (var2[var11] & 255) << 8;
      int var13 = var10 | var12;
      int var14 = var3 + 3;
      int var15 = var2[var14] & 255;
      int var16 = var13 | var15;
      int var17 = var3 + 4;
      int var18 = (var2[var17] & 255) << 24;
      int var19 = var3 + 5;
      int var20 = (var2[var19] & 255) << 16;
      int var21 = var18 | var20;
      int var22 = var3 + 6;
      int var23 = (var2[var22] & 255) << 8;
      int var24 = var21 | var23;
      int var25 = var3 + 7;
      int var26 = var2[var25] & 255;
      int var27 = var24 | var26;
      int var28 = (var16 >>> 4 ^ var27) & 252645135;
      int var29 = var27 ^ var28;
      int var30 = var28 << 4;
      int var31 = var16 ^ var30;
      int var32 = (var31 >>> 16 ^ var29) & '\uffff';
      int var33 = var29 ^ var32;
      int var34 = var32 << 16;
      int var35 = var31 ^ var34;
      int var36 = (var33 >>> 2 ^ var35) & 858993459;
      int var37 = var35 ^ var36;
      int var38 = var36 << 2;
      int var39 = var33 ^ var38;
      int var40 = (var39 >>> 8 ^ var37) & 16711935;
      int var41 = var37 ^ var40;
      int var42 = var40 << 8;
      int var43 = var39 ^ var42;
      int var44 = var43 << 1;
      int var45 = var43 >>> 31 & 1;
      int var46 = (var44 | var45) & -1;
      int var47 = (var41 ^ var46) & -1431655766;
      int var48 = var41 ^ var47;
      int var49 = var46 ^ var47;
      int var50 = var48 << 1;
      int var51 = var48 >>> 31 & 1;
      int var52 = (var50 | var51) & -1;

      for(int var53 = 0; var53 < 8; ++var53) {
         int var54 = var49 << 28;
         int var55 = var49 >>> 4;
         int var56 = var54 | var55;
         int var57 = var53 * 4 + 0;
         int var58 = var1[var57];
         int var59 = var56 ^ var58;
         int[] var60 = SP7;
         int var61 = var59 & 63;
         int var62 = var60[var61];
         int[] var63 = SP5;
         int var64 = var59 >>> 8 & 63;
         int var65 = var63[var64];
         int var66 = var62 | var65;
         int[] var67 = SP3;
         int var68 = var59 >>> 16 & 63;
         int var69 = var67[var68];
         int var70 = var66 | var69;
         int[] var71 = SP1;
         int var72 = var59 >>> 24 & 63;
         int var73 = var71[var72];
         int var74 = var70 | var73;
         int var75 = var53 * 4 + 1;
         int var76 = var1[var75];
         int var77 = var49 ^ var76;
         int[] var78 = SP8;
         int var79 = var77 & 63;
         int var80 = var78[var79];
         int var81 = var74 | var80;
         int[] var82 = SP6;
         int var83 = var77 >>> 8 & 63;
         int var84 = var82[var83];
         int var85 = var81 | var84;
         int[] var86 = SP4;
         int var87 = var77 >>> 16 & 63;
         int var88 = var86[var87];
         int var89 = var85 | var88;
         int[] var90 = SP2;
         int var91 = var77 >>> 24 & 63;
         int var92 = var90[var91];
         int var93 = var89 | var92;
         var52 ^= var93;
         int var94 = var52 << 28;
         int var95 = var52 >>> 4;
         int var96 = var94 | var95;
         int var97 = var53 * 4 + 2;
         int var98 = var1[var97];
         int var99 = var96 ^ var98;
         int[] var100 = SP7;
         int var101 = var99 & 63;
         int var102 = var100[var101];
         int[] var103 = SP5;
         int var104 = var99 >>> 8 & 63;
         int var105 = var103[var104];
         int var106 = var102 | var105;
         int[] var107 = SP3;
         int var108 = var99 >>> 16 & 63;
         int var109 = var107[var108];
         int var110 = var106 | var109;
         int[] var111 = SP1;
         int var112 = var99 >>> 24 & 63;
         int var113 = var111[var112];
         int var114 = var110 | var113;
         int var115 = var53 * 4 + 3;
         int var116 = var1[var115];
         int var117 = var52 ^ var116;
         int[] var118 = SP8;
         int var119 = var117 & 63;
         int var120 = var118[var119];
         int var121 = var114 | var120;
         int[] var122 = SP6;
         int var123 = var117 >>> 8 & 63;
         int var124 = var122[var123];
         int var125 = var121 | var124;
         int[] var126 = SP4;
         int var127 = var117 >>> 16 & 63;
         int var128 = var126[var127];
         int var129 = var125 | var128;
         int[] var130 = SP2;
         int var131 = var117 >>> 24 & 63;
         int var132 = var130[var131];
         int var133 = var129 | var132;
         var49 ^= var133;
      }

      int var134 = var49 << 31;
      int var135 = var49 >>> 1;
      int var136 = var134 | var135;
      int var137 = (var52 ^ var136) & -1431655766;
      int var138 = var52 ^ var137;
      int var139 = var136 ^ var137;
      int var140 = var138 << 31;
      int var141 = var138 >>> 1;
      int var142 = var140 | var141;
      int var143 = (var142 >>> 8 ^ var139) & 16711935;
      int var144 = var139 ^ var143;
      int var145 = var143 << 8;
      int var146 = var142 ^ var145;
      int var147 = (var146 >>> 2 ^ var144) & 858993459;
      int var148 = var144 ^ var147;
      int var149 = var147 << 2;
      int var150 = var146 ^ var149;
      int var151 = (var148 >>> 16 ^ var150) & '\uffff';
      int var152 = var150 ^ var151;
      int var153 = var151 << 16;
      int var154 = var148 ^ var153;
      int var155 = (var154 >>> 4 ^ var152) & 252645135;
      int var156 = var152 ^ var155;
      int var157 = var155 << 4;
      int var158 = var154 ^ var157;
      int var159 = var5 + 0;
      byte var160 = (byte)(var158 >>> 24 & 255);
      var4[var159] = var160;
      int var161 = var5 + 1;
      byte var162 = (byte)(var158 >>> 16 & 255);
      var4[var161] = var162;
      int var163 = var5 + 2;
      byte var164 = (byte)(var158 >>> 8 & 255);
      var4[var163] = var164;
      int var165 = var5 + 3;
      byte var166 = (byte)(var158 & 255);
      var4[var165] = var166;
      int var167 = var5 + 4;
      byte var168 = (byte)(var156 >>> 24 & 255);
      var4[var167] = var168;
      int var169 = var5 + 5;
      byte var170 = (byte)(var156 >>> 16 & 255);
      var4[var169] = var170;
      int var171 = var5 + 6;
      byte var172 = (byte)(var156 >>> 8 & 255);
      var4[var171] = var172;
      int var173 = var5 + 7;
      byte var174 = (byte)(var156 & 255);
      var4[var173] = var174;
   }

   protected int[] generateWorkingKey(boolean var1, byte[] var2) {
      int[] var3 = new int[32];
      boolean[] var4 = new boolean[56];
      boolean[] var5 = new boolean[56];

      for(int var6 = 0; var6 < 56; ++var6) {
         byte var7 = pc1[var6];
         int var8 = var7 >>> 3;
         byte var9 = var2[var8];
         short[] var10 = bytebit;
         int var11 = var7 & 7;
         short var12 = var10[var11];
         boolean var13;
         if((var9 & var12) != 0) {
            var13 = true;
         } else {
            var13 = false;
         }

         var4[var6] = var13;
      }

      for(int var14 = 0; var14 < 16; ++var14) {
         int var15;
         if(var1) {
            var15 = var14 << 1;
         } else {
            var15 = 15 - var14 << 1;
         }

         int var16 = var15 + 1;
         var3[var16] = 0;
         var3[var15] = 0;

         for(int var17 = 0; var17 < 28; ++var17) {
            byte var18 = totrot[var14];
            int var19 = var17 + var18;
            if(var19 < 28) {
               boolean var20 = var4[var19];
               var5[var17] = var20;
            } else {
               int var21 = var19 - 28;
               boolean var22 = var4[var21];
               var5[var17] = var22;
            }
         }

         for(int var23 = 28; var23 < 56; ++var23) {
            byte var24 = totrot[var14];
            int var25 = var23 + var24;
            if(var25 < 56) {
               boolean var26 = var4[var25];
               var5[var23] = var26;
            } else {
               int var27 = var25 - 28;
               boolean var28 = var4[var27];
               var5[var23] = var28;
            }
         }

         int var39;
         for(byte var59 = 0; var59 < 24; var39 = var59 + 1) {
            byte var29 = pc2[var59];
            if(var5[var29]) {
               int var30 = var3[var15];
               int var31 = bigbyte[var59];
               int var32 = var30 | var31;
               var3[var15] = var32;
            }

            byte[] var33 = pc2;
            int var34 = var59 + 24;
            byte var35 = var33[var34];
            if(var5[var35]) {
               int var36 = var3[var16];
               int var37 = bigbyte[var59];
               int var38 = var36 | var37;
               var3[var16] = var38;
            }
         }
      }

      for(int var40 = 0; var40 != 32; var40 += 2) {
         int var41 = var3[var40];
         int var42 = var40 + 1;
         int var43 = var3[var42];
         int var44 = (16515072 & var41) << 6;
         int var45 = (var41 & 4032) << 10;
         int var46 = var44 | var45;
         int var47 = (16515072 & var43) >>> 10;
         int var48 = var46 | var47;
         int var49 = (var43 & 4032) >>> 6;
         int var50 = var48 | var49;
         var3[var40] = var50;
         int var51 = var40 + 1;
         int var52 = (258048 & var41) << 12;
         int var53 = (var41 & 63) << 16;
         int var54 = var52 | var53;
         int var55 = (258048 & var43) >>> 4;
         int var56 = var54 | var55;
         int var57 = var43 & 63;
         int var58 = var56 | var57;
         var3[var51] = var58;
      }

      return var3;
   }

   public String getAlgorithmName() {
      return "DES";
   }

   public int getBlockSize() {
      return 8;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof KeyParameter) {
         if(((KeyParameter)var2).getKey().length > 8) {
            throw new IllegalArgumentException("DES key too long - should be 8 bytes");
         } else {
            byte[] var3 = ((KeyParameter)var2).getKey();
            int[] var4 = this.generateWorkingKey(var1, var3);
            this.workingKey = var4;
         }
      } else {
         StringBuilder var5 = (new StringBuilder()).append("invalid parameter passed to DES init - ");
         String var6 = var2.getClass().getName();
         String var7 = var5.append(var6).toString();
         throw new IllegalArgumentException(var7);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(this.workingKey == null) {
         throw new IllegalStateException("DES engine not initialised");
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
               int[] var9 = this.workingKey;
               this.desFunc(var9, var1, var2, var3, var4);
               return 8;
            }
         }
      }
   }

   public void reset() {}
}
