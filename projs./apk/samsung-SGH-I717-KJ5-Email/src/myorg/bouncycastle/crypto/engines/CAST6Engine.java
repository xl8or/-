package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.engines.CAST5Engine;

public final class CAST6Engine extends CAST5Engine {

   protected static final int BLOCK_SIZE = 16;
   protected static final int ROUNDS = 12;
   protected int[] _Km;
   protected int[] _Kr;
   protected int[] _Tm;
   protected int[] _Tr;
   private int[] _workingKey;


   public CAST6Engine() {
      int[] var1 = new int[48];
      this._Kr = var1;
      int[] var2 = new int[48];
      this._Km = var2;
      int[] var3 = new int[192];
      this._Tr = var3;
      int[] var4 = new int[192];
      this._Tm = var4;
      int[] var5 = new int[8];
      this._workingKey = var5;
   }

   protected final void CAST_Decipher(int var1, int var2, int var3, int var4, int[] var5) {
      for(int var6 = 0; var6 < 6; ++var6) {
         int var7 = (11 - var6) * 4;
         int var8 = this._Km[var7];
         int var9 = this._Kr[var7];
         int var10 = this.F1(var4, var8, var9);
         var3 ^= var10;
         int[] var11 = this._Km;
         int var12 = var7 + 1;
         int var13 = var11[var12];
         int[] var14 = this._Kr;
         int var15 = var7 + 1;
         int var16 = var14[var15];
         int var17 = this.F2(var3, var13, var16);
         var2 ^= var17;
         int[] var18 = this._Km;
         int var19 = var7 + 2;
         int var20 = var18[var19];
         int[] var21 = this._Kr;
         int var22 = var7 + 2;
         int var23 = var21[var22];
         int var24 = this.F3(var2, var20, var23);
         var1 ^= var24;
         int[] var25 = this._Km;
         int var26 = var7 + 3;
         int var27 = var25[var26];
         int[] var28 = this._Kr;
         int var29 = var7 + 3;
         int var30 = var28[var29];
         int var31 = this.F1(var1, var27, var30);
         var4 ^= var31;
      }

      for(int var32 = 6; var32 < 12; ++var32) {
         int var33 = (11 - var32) * 4;
         int[] var34 = this._Km;
         int var35 = var33 + 3;
         int var36 = var34[var35];
         int[] var37 = this._Kr;
         int var38 = var33 + 3;
         int var39 = var37[var38];
         int var40 = this.F1(var1, var36, var39);
         var4 ^= var40;
         int[] var41 = this._Km;
         int var42 = var33 + 2;
         int var43 = var41[var42];
         int[] var44 = this._Kr;
         int var45 = var33 + 2;
         int var46 = var44[var45];
         int var47 = this.F3(var2, var43, var46);
         var1 ^= var47;
         int[] var48 = this._Km;
         int var49 = var33 + 1;
         int var50 = var48[var49];
         int[] var51 = this._Kr;
         int var52 = var33 + 1;
         int var53 = var51[var52];
         int var54 = this.F2(var3, var50, var53);
         var2 ^= var54;
         int var55 = this._Km[var33];
         int var56 = this._Kr[var33];
         int var57 = this.F1(var4, var55, var56);
         var3 ^= var57;
      }

      var5[0] = var1;
      var5[1] = var2;
      var5[2] = var3;
      var5[3] = var4;
   }

   protected final void CAST_Encipher(int var1, int var2, int var3, int var4, int[] var5) {
      for(int var6 = 0; var6 < 6; ++var6) {
         int var7 = var6 * 4;
         int var8 = this._Km[var7];
         int var9 = this._Kr[var7];
         int var10 = this.F1(var4, var8, var9);
         var3 ^= var10;
         int[] var11 = this._Km;
         int var12 = var7 + 1;
         int var13 = var11[var12];
         int[] var14 = this._Kr;
         int var15 = var7 + 1;
         int var16 = var14[var15];
         int var17 = this.F2(var3, var13, var16);
         var2 ^= var17;
         int[] var18 = this._Km;
         int var19 = var7 + 2;
         int var20 = var18[var19];
         int[] var21 = this._Kr;
         int var22 = var7 + 2;
         int var23 = var21[var22];
         int var24 = this.F3(var2, var20, var23);
         var1 ^= var24;
         int[] var25 = this._Km;
         int var26 = var7 + 3;
         int var27 = var25[var26];
         int[] var28 = this._Kr;
         int var29 = var7 + 3;
         int var30 = var28[var29];
         int var31 = this.F1(var1, var27, var30);
         var4 ^= var31;
      }

      for(int var32 = 6; var32 < 12; ++var32) {
         int var33 = var32 * 4;
         int[] var34 = this._Km;
         int var35 = var33 + 3;
         int var36 = var34[var35];
         int[] var37 = this._Kr;
         int var38 = var33 + 3;
         int var39 = var37[var38];
         int var40 = this.F1(var1, var36, var39);
         var4 ^= var40;
         int[] var41 = this._Km;
         int var42 = var33 + 2;
         int var43 = var41[var42];
         int[] var44 = this._Kr;
         int var45 = var33 + 2;
         int var46 = var44[var45];
         int var47 = this.F3(var2, var43, var46);
         var1 ^= var47;
         int[] var48 = this._Km;
         int var49 = var33 + 1;
         int var50 = var48[var49];
         int[] var51 = this._Kr;
         int var52 = var33 + 1;
         int var53 = var51[var52];
         int var54 = this.F2(var3, var50, var53);
         var2 ^= var54;
         int var55 = this._Km[var33];
         int var56 = this._Kr[var33];
         int var57 = this.F1(var4, var55, var56);
         var3 ^= var57;
      }

      var5[0] = var1;
      var5[1] = var2;
      var5[2] = var3;
      var5[3] = var4;
   }

   protected int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int[] var5 = new int[4];
      int var6 = this.BytesTo32bits(var1, var2);
      int var7 = var2 + 4;
      int var8 = this.BytesTo32bits(var1, var7);
      int var9 = var2 + 8;
      int var10 = this.BytesTo32bits(var1, var9);
      int var11 = var2 + 12;
      int var12 = this.BytesTo32bits(var1, var11);
      this.CAST_Decipher(var6, var8, var10, var12, var5);
      int var13 = var5[0];
      this.Bits32ToBytes(var13, var3, var4);
      int var14 = var5[1];
      int var15 = var4 + 4;
      this.Bits32ToBytes(var14, var3, var15);
      int var16 = var5[2];
      int var17 = var4 + 8;
      this.Bits32ToBytes(var16, var3, var17);
      int var18 = var5[3];
      int var19 = var4 + 12;
      this.Bits32ToBytes(var18, var3, var19);
      return 16;
   }

   protected int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int[] var5 = new int[4];
      int var6 = this.BytesTo32bits(var1, var2);
      int var7 = var2 + 4;
      int var8 = this.BytesTo32bits(var1, var7);
      int var9 = var2 + 8;
      int var10 = this.BytesTo32bits(var1, var9);
      int var11 = var2 + 12;
      int var12 = this.BytesTo32bits(var1, var11);
      this.CAST_Encipher(var6, var8, var10, var12, var5);
      int var13 = var5[0];
      this.Bits32ToBytes(var13, var3, var4);
      int var14 = var5[1];
      int var15 = var4 + 4;
      this.Bits32ToBytes(var14, var3, var15);
      int var16 = var5[2];
      int var17 = var4 + 8;
      this.Bits32ToBytes(var16, var3, var17);
      int var18 = var5[3];
      int var19 = var4 + 12;
      this.Bits32ToBytes(var18, var3, var19);
      return 16;
   }

   public String getAlgorithmName() {
      return "CAST6";
   }

   public int getBlockSize() {
      return 16;
   }

   public void reset() {}

   protected void setKey(byte[] var1) {
      int var2 = 1518500249;
      int var3 = 19;

      for(int var4 = 0; var4 < 24; ++var4) {
         for(int var5 = 0; var5 < 8; ++var5) {
            int[] var6 = this._Tm;
            int var7 = var4 * 8 + var5;
            var6[var7] = var2;
            var2 += 1859775393;
            int[] var8 = this._Tr;
            int var9 = var4 * 8 + var5;
            var8[var9] = var3;
            var3 = var3 + 17 & 31;
         }
      }

      byte[] var10 = new byte[64];
      int var11 = var1.length;
      byte var13 = 0;
      byte var15 = 0;
      System.arraycopy(var1, var13, var10, var15, var11);

      for(int var17 = 0; var17 < 8; ++var17) {
         int[] var18 = this._workingKey;
         int var19 = var17 * 4;
         int var23 = this.BytesTo32bits(var10, var19);
         var18[var17] = var23;
      }

      for(int var24 = 0; var24 < 12; ++var24) {
         int var25 = var24 * 2 * 8;
         int[] var26 = this._workingKey;
         int var27 = var26[6];
         int var28 = this._workingKey[7];
         int var29 = this._Tm[var25];
         int var30 = this._Tr[var25];
         int var35 = this.F1(var28, var29, var30);
         int var36 = var27 ^ var35;
         var26[6] = var36;
         int[] var37 = this._workingKey;
         int var38 = var37[5];
         int var39 = this._workingKey[6];
         int[] var40 = this._Tm;
         int var41 = var25 + 1;
         int var42 = var40[var41];
         int[] var43 = this._Tr;
         int var44 = var25 + 1;
         int var45 = var43[var44];
         int var50 = this.F2(var39, var42, var45);
         int var51 = var38 ^ var50;
         var37[5] = var51;
         int[] var52 = this._workingKey;
         int var53 = var52[4];
         int var54 = this._workingKey[5];
         int[] var55 = this._Tm;
         int var56 = var25 + 2;
         int var57 = var55[var56];
         int[] var58 = this._Tr;
         int var59 = var25 + 2;
         int var60 = var58[var59];
         int var65 = this.F3(var54, var57, var60);
         int var66 = var53 ^ var65;
         var52[4] = var66;
         int[] var67 = this._workingKey;
         int var68 = var67[3];
         int var69 = this._workingKey[4];
         int[] var70 = this._Tm;
         int var71 = var25 + 3;
         int var72 = var70[var71];
         int[] var73 = this._Tr;
         int var74 = var25 + 3;
         int var75 = var73[var74];
         int var80 = this.F1(var69, var72, var75);
         int var81 = var68 ^ var80;
         var67[3] = var81;
         int[] var82 = this._workingKey;
         int var83 = var82[2];
         int var84 = this._workingKey[3];
         int[] var85 = this._Tm;
         int var86 = var25 + 4;
         int var87 = var85[var86];
         int[] var88 = this._Tr;
         int var89 = var25 + 4;
         int var90 = var88[var89];
         int var95 = this.F2(var84, var87, var90);
         int var96 = var83 ^ var95;
         var82[2] = var96;
         int[] var97 = this._workingKey;
         int var98 = var97[1];
         int var99 = this._workingKey[2];
         int[] var100 = this._Tm;
         int var101 = var25 + 5;
         int var102 = var100[var101];
         int[] var103 = this._Tr;
         int var104 = var25 + 5;
         int var105 = var103[var104];
         int var110 = this.F3(var99, var102, var105);
         int var111 = var98 ^ var110;
         var97[1] = var111;
         int[] var112 = this._workingKey;
         int var113 = var112[0];
         int var114 = this._workingKey[1];
         int[] var115 = this._Tm;
         int var116 = var25 + 6;
         int var117 = var115[var116];
         int[] var118 = this._Tr;
         int var119 = var25 + 6;
         int var120 = var118[var119];
         int var125 = this.F1(var114, var117, var120);
         int var126 = var113 ^ var125;
         var112[0] = var126;
         int[] var127 = this._workingKey;
         int var128 = var127[7];
         int var129 = this._workingKey[0];
         int[] var130 = this._Tm;
         int var131 = var25 + 7;
         int var132 = var130[var131];
         int[] var133 = this._Tr;
         int var134 = var25 + 7;
         int var135 = var133[var134];
         int var140 = this.F2(var129, var132, var135);
         int var141 = var128 ^ var140;
         var127[7] = var141;
         int var142 = (var24 * 2 + 1) * 8;
         int[] var143 = this._workingKey;
         int var144 = var143[6];
         int var145 = this._workingKey[7];
         int var146 = this._Tm[var142];
         int var147 = this._Tr[var142];
         int var152 = this.F1(var145, var146, var147);
         int var153 = var144 ^ var152;
         var143[6] = var153;
         int[] var154 = this._workingKey;
         int var155 = var154[5];
         int var156 = this._workingKey[6];
         int[] var157 = this._Tm;
         int var158 = var142 + 1;
         int var159 = var157[var158];
         int[] var160 = this._Tr;
         int var161 = var142 + 1;
         int var162 = var160[var161];
         int var167 = this.F2(var156, var159, var162);
         int var168 = var155 ^ var167;
         var154[5] = var168;
         int[] var169 = this._workingKey;
         int var170 = var169[4];
         int var171 = this._workingKey[5];
         int[] var172 = this._Tm;
         int var173 = var142 + 2;
         int var174 = var172[var173];
         int[] var175 = this._Tr;
         int var176 = var142 + 2;
         int var177 = var175[var176];
         int var182 = this.F3(var171, var174, var177);
         int var183 = var170 ^ var182;
         var169[4] = var183;
         int[] var184 = this._workingKey;
         int var185 = var184[3];
         int var186 = this._workingKey[4];
         int[] var187 = this._Tm;
         int var188 = var142 + 3;
         int var189 = var187[var188];
         int[] var190 = this._Tr;
         int var191 = var142 + 3;
         int var192 = var190[var191];
         int var197 = this.F1(var186, var189, var192);
         int var198 = var185 ^ var197;
         var184[3] = var198;
         int[] var199 = this._workingKey;
         int var200 = var199[2];
         int var201 = this._workingKey[3];
         int[] var202 = this._Tm;
         int var203 = var142 + 4;
         int var204 = var202[var203];
         int[] var205 = this._Tr;
         int var206 = var142 + 4;
         int var207 = var205[var206];
         int var212 = this.F2(var201, var204, var207);
         int var213 = var200 ^ var212;
         var199[2] = var213;
         int[] var214 = this._workingKey;
         int var215 = var214[1];
         int var216 = this._workingKey[2];
         int[] var217 = this._Tm;
         int var218 = var142 + 5;
         int var219 = var217[var218];
         int[] var220 = this._Tr;
         int var221 = var142 + 5;
         int var222 = var220[var221];
         int var227 = this.F3(var216, var219, var222);
         int var228 = var215 ^ var227;
         var214[1] = var228;
         int[] var229 = this._workingKey;
         int var230 = var229[0];
         int var231 = this._workingKey[1];
         int[] var232 = this._Tm;
         int var233 = var142 + 6;
         int var234 = var232[var233];
         int[] var235 = this._Tr;
         int var236 = var142 + 6;
         int var237 = var235[var236];
         int var242 = this.F1(var231, var234, var237);
         int var243 = var230 ^ var242;
         var229[0] = var243;
         int[] var244 = this._workingKey;
         int var245 = var244[7];
         int var246 = this._workingKey[0];
         int[] var247 = this._Tm;
         int var248 = var142 + 7;
         int var249 = var247[var248];
         int[] var250 = this._Tr;
         int var251 = var142 + 7;
         int var252 = var250[var251];
         int var257 = this.F2(var246, var249, var252);
         int var258 = var245 ^ var257;
         var244[7] = var258;
         int[] var259 = this._Kr;
         int var260 = var24 * 4;
         int var261 = this._workingKey[0] & 31;
         var259[var260] = var261;
         int[] var262 = this._Kr;
         int var263 = var24 * 4 + 1;
         int var264 = this._workingKey[2] & 31;
         var262[var263] = var264;
         int[] var265 = this._Kr;
         int var266 = var24 * 4 + 2;
         int var267 = this._workingKey[4] & 31;
         var265[var266] = var267;
         int[] var268 = this._Kr;
         int var269 = var24 * 4 + 3;
         int var270 = this._workingKey[6] & 31;
         var268[var269] = var270;
         int[] var271 = this._Km;
         int var272 = var24 * 4;
         int var273 = this._workingKey[7];
         var271[var272] = var273;
         int[] var274 = this._Km;
         int var275 = var24 * 4 + 1;
         int var276 = this._workingKey[5];
         var274[var275] = var276;
         int[] var277 = this._Km;
         int var278 = var24 * 4 + 2;
         int var279 = this._workingKey[3];
         var277[var278] = var279;
         int[] var280 = this._Km;
         int var281 = var24 * 4 + 3;
         int var282 = this._workingKey[1];
         var280[var281] = var282;
      }

   }
}
