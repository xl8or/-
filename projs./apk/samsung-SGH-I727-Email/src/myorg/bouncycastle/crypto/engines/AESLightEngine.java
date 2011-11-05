package myorg.bouncycastle.crypto.engines;

import java.lang.reflect.Array;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class AESLightEngine implements BlockCipher {

   private static final int BLOCK_SIZE = 16;
   private static final byte[] S = new byte[]{(byte)99, (byte)124, (byte)119, (byte)123, (byte)242, (byte)107, (byte)111, (byte)197, (byte)48, (byte)1, (byte)103, (byte)43, (byte)254, (byte)215, (byte)171, (byte)118, (byte)202, (byte)130, (byte)201, (byte)125, (byte)250, (byte)89, (byte)71, (byte)240, (byte)173, (byte)212, (byte)162, (byte)175, (byte)156, (byte)164, (byte)114, (byte)192, (byte)183, (byte)253, (byte)147, (byte)38, (byte)54, (byte)63, (byte)247, (byte)204, (byte)52, (byte)165, (byte)229, (byte)241, (byte)113, (byte)216, (byte)49, (byte)21, (byte)4, (byte)199, (byte)35, (byte)195, (byte)24, (byte)150, (byte)5, (byte)154, (byte)7, (byte)18, (byte)128, (byte)226, (byte)235, (byte)39, (byte)178, (byte)117, (byte)9, (byte)131, (byte)44, (byte)26, (byte)27, (byte)110, (byte)90, (byte)160, (byte)82, (byte)59, (byte)214, (byte)179, (byte)41, (byte)227, (byte)47, (byte)132, (byte)83, (byte)209, (byte)0, (byte)237, (byte)32, (byte)252, (byte)177, (byte)91, (byte)106, (byte)203, (byte)190, (byte)57, (byte)74, (byte)76, (byte)88, (byte)207, (byte)208, (byte)239, (byte)170, (byte)251, (byte)67, (byte)77, (byte)51, (byte)133, (byte)69, (byte)249, (byte)2, (byte)127, (byte)80, (byte)60, (byte)159, (byte)168, (byte)81, (byte)163, (byte)64, (byte)143, (byte)146, (byte)157, (byte)56, (byte)245, (byte)188, (byte)182, (byte)218, (byte)33, (byte)16, (byte)255, (byte)243, (byte)210, (byte)205, (byte)12, (byte)19, (byte)236, (byte)95, (byte)151, (byte)68, (byte)23, (byte)196, (byte)167, (byte)126, (byte)61, (byte)100, (byte)93, (byte)25, (byte)115, (byte)96, (byte)129, (byte)79, (byte)220, (byte)34, (byte)42, (byte)144, (byte)136, (byte)70, (byte)238, (byte)184, (byte)20, (byte)222, (byte)94, (byte)11, (byte)219, (byte)224, (byte)50, (byte)58, (byte)10, (byte)73, (byte)6, (byte)36, (byte)92, (byte)194, (byte)211, (byte)172, (byte)98, (byte)145, (byte)149, (byte)228, (byte)121, (byte)231, (byte)200, (byte)55, (byte)109, (byte)141, (byte)213, (byte)78, (byte)169, (byte)108, (byte)86, (byte)244, (byte)234, (byte)101, (byte)122, (byte)174, (byte)8, (byte)186, (byte)120, (byte)37, (byte)46, (byte)28, (byte)166, (byte)180, (byte)198, (byte)232, (byte)221, (byte)116, (byte)31, (byte)75, (byte)189, (byte)139, (byte)138, (byte)112, (byte)62, (byte)181, (byte)102, (byte)72, (byte)3, (byte)246, (byte)14, (byte)97, (byte)53, (byte)87, (byte)185, (byte)134, (byte)193, (byte)29, (byte)158, (byte)225, (byte)248, (byte)152, (byte)17, (byte)105, (byte)217, (byte)142, (byte)148, (byte)155, (byte)30, (byte)135, (byte)233, (byte)206, (byte)85, (byte)40, (byte)223, (byte)140, (byte)161, (byte)137, (byte)13, (byte)191, (byte)230, (byte)66, (byte)104, (byte)65, (byte)153, (byte)45, (byte)15, (byte)176, (byte)84, (byte)187, (byte)22};
   private static final byte[] Si = new byte[]{(byte)82, (byte)9, (byte)106, (byte)213, (byte)48, (byte)54, (byte)165, (byte)56, (byte)191, (byte)64, (byte)163, (byte)158, (byte)129, (byte)243, (byte)215, (byte)251, (byte)124, (byte)227, (byte)57, (byte)130, (byte)155, (byte)47, (byte)255, (byte)135, (byte)52, (byte)142, (byte)67, (byte)68, (byte)196, (byte)222, (byte)233, (byte)203, (byte)84, (byte)123, (byte)148, (byte)50, (byte)166, (byte)194, (byte)35, (byte)61, (byte)238, (byte)76, (byte)149, (byte)11, (byte)66, (byte)250, (byte)195, (byte)78, (byte)8, (byte)46, (byte)161, (byte)102, (byte)40, (byte)217, (byte)36, (byte)178, (byte)118, (byte)91, (byte)162, (byte)73, (byte)109, (byte)139, (byte)209, (byte)37, (byte)114, (byte)248, (byte)246, (byte)100, (byte)134, (byte)104, (byte)152, (byte)22, (byte)212, (byte)164, (byte)92, (byte)204, (byte)93, (byte)101, (byte)182, (byte)146, (byte)108, (byte)112, (byte)72, (byte)80, (byte)253, (byte)237, (byte)185, (byte)218, (byte)94, (byte)21, (byte)70, (byte)87, (byte)167, (byte)141, (byte)157, (byte)132, (byte)144, (byte)216, (byte)171, (byte)0, (byte)140, (byte)188, (byte)211, (byte)10, (byte)247, (byte)228, (byte)88, (byte)5, (byte)184, (byte)179, (byte)69, (byte)6, (byte)208, (byte)44, (byte)30, (byte)143, (byte)202, (byte)63, (byte)15, (byte)2, (byte)193, (byte)175, (byte)189, (byte)3, (byte)1, (byte)19, (byte)138, (byte)107, (byte)58, (byte)145, (byte)17, (byte)65, (byte)79, (byte)103, (byte)220, (byte)234, (byte)151, (byte)242, (byte)207, (byte)206, (byte)240, (byte)180, (byte)230, (byte)115, (byte)150, (byte)172, (byte)116, (byte)34, (byte)231, (byte)173, (byte)53, (byte)133, (byte)226, (byte)249, (byte)55, (byte)232, (byte)28, (byte)117, (byte)223, (byte)110, (byte)71, (byte)241, (byte)26, (byte)113, (byte)29, (byte)41, (byte)197, (byte)137, (byte)111, (byte)183, (byte)98, (byte)14, (byte)170, (byte)24, (byte)190, (byte)27, (byte)252, (byte)86, (byte)62, (byte)75, (byte)198, (byte)210, (byte)121, (byte)32, (byte)154, (byte)219, (byte)192, (byte)254, (byte)120, (byte)205, (byte)90, (byte)244, (byte)31, (byte)221, (byte)168, (byte)51, (byte)136, (byte)7, (byte)199, (byte)49, (byte)177, (byte)18, (byte)16, (byte)89, (byte)39, (byte)128, (byte)236, (byte)95, (byte)96, (byte)81, (byte)127, (byte)169, (byte)25, (byte)181, (byte)74, (byte)13, (byte)45, (byte)229, (byte)122, (byte)159, (byte)147, (byte)201, (byte)156, (byte)239, (byte)160, (byte)224, (byte)59, (byte)77, (byte)174, (byte)42, (byte)245, (byte)176, (byte)200, (byte)235, (byte)187, (byte)60, (byte)131, (byte)83, (byte)153, (byte)97, (byte)23, (byte)43, (byte)4, (byte)126, (byte)186, (byte)119, (byte)214, (byte)38, (byte)225, (byte)105, (byte)20, (byte)99, (byte)85, (byte)33, (byte)12, (byte)125};
   private static final int m1 = -2139062144;
   private static final int m2 = 2139062143;
   private static final int m3 = 27;
   private static final int[] rcon = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, 197, 145};
   private int C0;
   private int C1;
   private int C2;
   private int C3;
   private int ROUNDS;
   private int[][] WorkingKey;
   private boolean forEncryption;


   public AESLightEngine() {
      int[][] var1 = (int[][])false;
      this.WorkingKey = var1;
   }

   private int FFmulX(int var1) {
      int var2 = (2139062143 & var1) << 1;
      int var3 = ((-2139062144 & var1) >>> 7) * 27;
      return var2 ^ var3;
   }

   private void decryptBlock(int[][] var1) {
      int var2 = this.C0;
      int var3 = this.ROUNDS;
      int var4 = var1[var3][0];
      int var5 = var2 ^ var4;
      this.C0 = var5;
      int var6 = this.C1;
      int var7 = this.ROUNDS;
      int var8 = var1[var7][1];
      int var9 = var6 ^ var8;
      this.C1 = var9;
      int var10 = this.C2;
      int var11 = this.ROUNDS;
      int var12 = var1[var11][2];
      int var13 = var10 ^ var12;
      this.C2 = var13;
      int var14 = this.C3;
      int var15 = this.ROUNDS;
      int var16 = var1[var15][3];
      int var17 = var14 ^ var16;
      this.C3 = var17;

      int var18;
      int var163;
      for(var18 = this.ROUNDS - 1; var18 > 1; this.C3 = var163) {
         byte[] var19 = Si;
         int var20 = this.C0 & 255;
         int var21 = var19[var20] & 255;
         byte[] var22 = Si;
         int var23 = this.C3 >> 8 & 255;
         int var24 = (var22[var23] & 255) << 8;
         int var25 = var21 ^ var24;
         byte[] var26 = Si;
         int var27 = this.C2 >> 16 & 255;
         int var28 = (var26[var27] & 255) << 16;
         int var29 = var25 ^ var28;
         byte[] var30 = Si;
         int var31 = this.C1 >> 24 & 255;
         int var32 = var30[var31] << 24;
         int var33 = var29 ^ var32;
         int var34 = this.inv_mcol(var33);
         int var35 = var1[var18][0];
         int var36 = var34 ^ var35;
         byte[] var37 = Si;
         int var38 = this.C1 & 255;
         int var39 = var37[var38] & 255;
         byte[] var40 = Si;
         int var41 = this.C0 >> 8 & 255;
         int var42 = (var40[var41] & 255) << 8;
         int var43 = var39 ^ var42;
         byte[] var44 = Si;
         int var45 = this.C3 >> 16 & 255;
         int var46 = (var44[var45] & 255) << 16;
         int var47 = var43 ^ var46;
         byte[] var48 = Si;
         int var49 = this.C2 >> 24 & 255;
         int var50 = var48[var49] << 24;
         int var51 = var47 ^ var50;
         int var52 = this.inv_mcol(var51);
         int var53 = var1[var18][1];
         int var54 = var52 ^ var53;
         byte[] var55 = Si;
         int var56 = this.C2 & 255;
         int var57 = var55[var56] & 255;
         byte[] var58 = Si;
         int var59 = this.C1 >> 8 & 255;
         int var60 = (var58[var59] & 255) << 8;
         int var61 = var57 ^ var60;
         byte[] var62 = Si;
         int var63 = this.C0 >> 16 & 255;
         int var64 = (var62[var63] & 255) << 16;
         int var65 = var61 ^ var64;
         byte[] var66 = Si;
         int var67 = this.C3 >> 24 & 255;
         int var68 = var66[var67] << 24;
         int var69 = var65 ^ var68;
         int var70 = this.inv_mcol(var69);
         int var71 = var1[var18][2];
         int var72 = var70 ^ var71;
         byte[] var73 = Si;
         int var74 = this.C3 & 255;
         int var75 = var73[var74] & 255;
         byte[] var76 = Si;
         int var77 = this.C2 >> 8 & 255;
         int var78 = (var76[var77] & 255) << 8;
         int var79 = var75 ^ var78;
         byte[] var80 = Si;
         int var81 = this.C1 >> 16 & 255;
         int var82 = (var80[var81] & 255) << 16;
         int var83 = var79 ^ var82;
         byte[] var84 = Si;
         int var85 = this.C0 >> 24 & 255;
         int var86 = var84[var85] << 24;
         int var87 = var83 ^ var86;
         int var88 = this.inv_mcol(var87);
         int var89 = var18 + -1;
         int var90 = var1[var18][3];
         int var91 = var88 ^ var90;
         byte[] var92 = Si;
         int var93 = var36 & 255;
         int var94 = var92[var93] & 255;
         byte[] var95 = Si;
         int var96 = var91 >> 8 & 255;
         int var97 = (var95[var96] & 255) << 8;
         int var98 = var94 ^ var97;
         byte[] var99 = Si;
         int var100 = var72 >> 16 & 255;
         int var101 = (var99[var100] & 255) << 16;
         int var102 = var98 ^ var101;
         byte[] var103 = Si;
         int var104 = var54 >> 24 & 255;
         int var105 = var103[var104] << 24;
         int var106 = var102 ^ var105;
         int var107 = this.inv_mcol(var106);
         int var108 = var1[var89][0];
         int var109 = var107 ^ var108;
         this.C0 = var109;
         byte[] var110 = Si;
         int var111 = var54 & 255;
         int var112 = var110[var111] & 255;
         byte[] var113 = Si;
         int var114 = var36 >> 8 & 255;
         int var115 = (var113[var114] & 255) << 8;
         int var116 = var112 ^ var115;
         byte[] var117 = Si;
         int var118 = var91 >> 16 & 255;
         int var119 = (var117[var118] & 255) << 16;
         int var120 = var116 ^ var119;
         byte[] var121 = Si;
         int var122 = var72 >> 24 & 255;
         int var123 = var121[var122] << 24;
         int var124 = var120 ^ var123;
         int var125 = this.inv_mcol(var124);
         int var126 = var1[var89][1];
         int var127 = var125 ^ var126;
         this.C1 = var127;
         byte[] var128 = Si;
         int var129 = var72 & 255;
         int var130 = var128[var129] & 255;
         byte[] var131 = Si;
         int var132 = var54 >> 8 & 255;
         int var133 = (var131[var132] & 255) << 8;
         int var134 = var130 ^ var133;
         byte[] var135 = Si;
         int var136 = var36 >> 16 & 255;
         int var137 = (var135[var136] & 255) << 16;
         int var138 = var134 ^ var137;
         byte[] var139 = Si;
         int var140 = var91 >> 24 & 255;
         int var141 = var139[var140] << 24;
         int var142 = var138 ^ var141;
         int var143 = this.inv_mcol(var142);
         int var144 = var1[var89][2];
         int var145 = var143 ^ var144;
         this.C2 = var145;
         byte[] var146 = Si;
         int var147 = var91 & 255;
         int var148 = var146[var147] & 255;
         byte[] var149 = Si;
         int var150 = var72 >> 8 & 255;
         int var151 = (var149[var150] & 255) << 8;
         int var152 = var148 ^ var151;
         byte[] var153 = Si;
         int var154 = var54 >> 16 & 255;
         int var155 = (var153[var154] & 255) << 16;
         int var156 = var152 ^ var155;
         byte[] var157 = Si;
         int var158 = var36 >> 24 & 255;
         int var159 = var157[var158] << 24;
         int var160 = var156 ^ var159;
         int var161 = this.inv_mcol(var160);
         var18 = var89 + -1;
         int var162 = var1[var89][3];
         var163 = var161 ^ var162;
      }

      byte[] var164 = Si;
      int var165 = this.C0 & 255;
      int var166 = var164[var165] & 255;
      byte[] var167 = Si;
      int var168 = this.C3 >> 8 & 255;
      int var169 = (var167[var168] & 255) << 8;
      int var170 = var166 ^ var169;
      byte[] var171 = Si;
      int var172 = this.C2 >> 16 & 255;
      int var173 = (var171[var172] & 255) << 16;
      int var174 = var170 ^ var173;
      byte[] var175 = Si;
      int var176 = this.C1 >> 24 & 255;
      int var177 = var175[var176] << 24;
      int var178 = var174 ^ var177;
      int var179 = this.inv_mcol(var178);
      int var180 = var1[var18][0];
      int var181 = var179 ^ var180;
      byte[] var182 = Si;
      int var183 = this.C1 & 255;
      int var184 = var182[var183] & 255;
      byte[] var185 = Si;
      int var186 = this.C0 >> 8 & 255;
      int var187 = (var185[var186] & 255) << 8;
      int var188 = var184 ^ var187;
      byte[] var189 = Si;
      int var190 = this.C3 >> 16 & 255;
      int var191 = (var189[var190] & 255) << 16;
      int var192 = var188 ^ var191;
      byte[] var193 = Si;
      int var194 = this.C2 >> 24 & 255;
      int var195 = var193[var194] << 24;
      int var196 = var192 ^ var195;
      int var197 = this.inv_mcol(var196);
      int var198 = var1[var18][1];
      int var199 = var197 ^ var198;
      byte[] var200 = Si;
      int var201 = this.C2 & 255;
      int var202 = var200[var201] & 255;
      byte[] var203 = Si;
      int var204 = this.C1 >> 8 & 255;
      int var205 = (var203[var204] & 255) << 8;
      int var206 = var202 ^ var205;
      byte[] var207 = Si;
      int var208 = this.C0 >> 16 & 255;
      int var209 = (var207[var208] & 255) << 16;
      int var210 = var206 ^ var209;
      byte[] var211 = Si;
      int var212 = this.C3 >> 24 & 255;
      int var213 = var211[var212] << 24;
      int var214 = var210 ^ var213;
      int var215 = this.inv_mcol(var214);
      int var216 = var1[var18][2];
      int var217 = var215 ^ var216;
      byte[] var218 = Si;
      int var219 = this.C3 & 255;
      int var220 = var218[var219] & 255;
      byte[] var221 = Si;
      int var222 = this.C2 >> 8 & 255;
      int var223 = (var221[var222] & 255) << 8;
      int var224 = var220 ^ var223;
      byte[] var225 = Si;
      int var226 = this.C1 >> 16 & 255;
      int var227 = (var225[var226] & 255) << 16;
      int var228 = var224 ^ var227;
      byte[] var229 = Si;
      int var230 = this.C0 >> 24 & 255;
      int var231 = var229[var230] << 24;
      int var232 = var228 ^ var231;
      int var233 = this.inv_mcol(var232);
      int var234 = var1[var18][3];
      int var235 = var233 ^ var234;
      byte[] var236 = Si;
      int var237 = var181 & 255;
      int var238 = var236[var237] & 255;
      byte[] var239 = Si;
      int var240 = var235 >> 8 & 255;
      int var241 = (var239[var240] & 255) << 8;
      int var242 = var238 ^ var241;
      byte[] var243 = Si;
      int var244 = var217 >> 16 & 255;
      int var245 = (var243[var244] & 255) << 16;
      int var246 = var242 ^ var245;
      byte[] var247 = Si;
      int var248 = var199 >> 24 & 255;
      int var249 = var247[var248] << 24;
      int var250 = var246 ^ var249;
      int var251 = var1[0][0];
      int var252 = var250 ^ var251;
      this.C0 = var252;
      byte[] var253 = Si;
      int var254 = var199 & 255;
      int var255 = var253[var254] & 255;
      byte[] var256 = Si;
      int var257 = var181 >> 8 & 255;
      int var258 = (var256[var257] & 255) << 8;
      int var259 = var255 ^ var258;
      byte[] var260 = Si;
      int var261 = var235 >> 16 & 255;
      int var262 = (var260[var261] & 255) << 16;
      int var263 = var259 ^ var262;
      byte[] var264 = Si;
      int var265 = var217 >> 24 & 255;
      int var266 = var264[var265] << 24;
      int var267 = var263 ^ var266;
      int var268 = var1[0][1];
      int var269 = var267 ^ var268;
      this.C1 = var269;
      byte[] var270 = Si;
      int var271 = var217 & 255;
      int var272 = var270[var271] & 255;
      byte[] var273 = Si;
      int var274 = var199 >> 8 & 255;
      int var275 = (var273[var274] & 255) << 8;
      int var276 = var272 ^ var275;
      byte[] var277 = Si;
      int var278 = var181 >> 16 & 255;
      int var279 = (var277[var278] & 255) << 16;
      int var280 = var276 ^ var279;
      byte[] var281 = Si;
      int var282 = var235 >> 24 & 255;
      int var283 = var281[var282] << 24;
      int var284 = var280 ^ var283;
      int var285 = var1[0][2];
      int var286 = var284 ^ var285;
      this.C2 = var286;
      byte[] var287 = Si;
      int var288 = var235 & 255;
      int var289 = var287[var288] & 255;
      byte[] var290 = Si;
      int var291 = var217 >> 8 & 255;
      int var292 = (var290[var291] & 255) << 8;
      int var293 = var289 ^ var292;
      byte[] var294 = Si;
      int var295 = var199 >> 16 & 255;
      int var296 = (var294[var295] & 255) << 16;
      int var297 = var293 ^ var296;
      byte[] var298 = Si;
      int var299 = var181 >> 24 & 255;
      int var300 = var298[var299] << 24;
      int var301 = var297 ^ var300;
      int var302 = var1[0][3];
      int var303 = var301 ^ var302;
      this.C3 = var303;
   }

   private void encryptBlock(int[][] var1) {
      int var2 = this.C0;
      int var3 = var1[0][0];
      int var4 = var2 ^ var3;
      this.C0 = var4;
      int var5 = this.C1;
      int var6 = var1[0][1];
      int var7 = var5 ^ var6;
      this.C1 = var7;
      int var8 = this.C2;
      int var9 = var1[0][2];
      int var10 = var8 ^ var9;
      this.C2 = var10;
      int var11 = this.C3;
      int var12 = var1[0][3];
      int var13 = var11 ^ var12;
      this.C3 = var13;
      int var14 = 1;

      while(true) {
         int var15 = this.ROUNDS - 1;
         if(var14 >= var15) {
            byte[] var161 = S;
            int var162 = this.C0 & 255;
            int var163 = var161[var162] & 255;
            byte[] var164 = S;
            int var165 = this.C1 >> 8 & 255;
            int var166 = (var164[var165] & 255) << 8;
            int var167 = var163 ^ var166;
            byte[] var168 = S;
            int var169 = this.C2 >> 16 & 255;
            int var170 = (var168[var169] & 255) << 16;
            int var171 = var167 ^ var170;
            byte[] var172 = S;
            int var173 = this.C3 >> 24 & 255;
            int var174 = var172[var173] << 24;
            int var175 = var171 ^ var174;
            int var176 = this.mcol(var175);
            int var177 = var1[var14][0];
            int var178 = var176 ^ var177;
            byte[] var179 = S;
            int var180 = this.C1 & 255;
            int var181 = var179[var180] & 255;
            byte[] var182 = S;
            int var183 = this.C2 >> 8 & 255;
            int var184 = (var182[var183] & 255) << 8;
            int var185 = var181 ^ var184;
            byte[] var186 = S;
            int var187 = this.C3 >> 16 & 255;
            int var188 = (var186[var187] & 255) << 16;
            int var189 = var185 ^ var188;
            byte[] var190 = S;
            int var191 = this.C0 >> 24 & 255;
            int var192 = var190[var191] << 24;
            int var193 = var189 ^ var192;
            int var194 = this.mcol(var193);
            int var195 = var1[var14][1];
            int var196 = var194 ^ var195;
            byte[] var197 = S;
            int var198 = this.C2 & 255;
            int var199 = var197[var198] & 255;
            byte[] var200 = S;
            int var201 = this.C3 >> 8 & 255;
            int var202 = (var200[var201] & 255) << 8;
            int var203 = var199 ^ var202;
            byte[] var204 = S;
            int var205 = this.C0 >> 16 & 255;
            int var206 = (var204[var205] & 255) << 16;
            int var207 = var203 ^ var206;
            byte[] var208 = S;
            int var209 = this.C1 >> 24 & 255;
            int var210 = var208[var209] << 24;
            int var211 = var207 ^ var210;
            int var212 = this.mcol(var211);
            int var213 = var1[var14][2];
            int var214 = var212 ^ var213;
            byte[] var215 = S;
            int var216 = this.C3 & 255;
            int var217 = var215[var216] & 255;
            byte[] var218 = S;
            int var219 = this.C0 >> 8 & 255;
            int var220 = (var218[var219] & 255) << 8;
            int var221 = var217 ^ var220;
            byte[] var222 = S;
            int var223 = this.C1 >> 16 & 255;
            int var224 = (var222[var223] & 255) << 16;
            int var225 = var221 ^ var224;
            byte[] var226 = S;
            int var227 = this.C2 >> 24 & 255;
            int var228 = var226[var227] << 24;
            int var229 = var225 ^ var228;
            int var230 = this.mcol(var229);
            int var231 = var14 + 1;
            int var232 = var1[var14][3];
            int var233 = var230 ^ var232;
            byte[] var234 = S;
            int var235 = var178 & 255;
            int var236 = var234[var235] & 255;
            byte[] var237 = S;
            int var238 = var196 >> 8 & 255;
            int var239 = (var237[var238] & 255) << 8;
            int var240 = var236 ^ var239;
            byte[] var241 = S;
            int var242 = var214 >> 16 & 255;
            int var243 = (var241[var242] & 255) << 16;
            int var244 = var240 ^ var243;
            byte[] var245 = S;
            int var246 = var233 >> 24 & 255;
            int var247 = var245[var246] << 24;
            int var248 = var244 ^ var247;
            int var249 = var1[var231][0];
            int var250 = var248 ^ var249;
            this.C0 = var250;
            byte[] var251 = S;
            int var252 = var196 & 255;
            int var253 = var251[var252] & 255;
            byte[] var254 = S;
            int var255 = var214 >> 8 & 255;
            int var256 = (var254[var255] & 255) << 8;
            int var257 = var253 ^ var256;
            byte[] var258 = S;
            int var259 = var233 >> 16 & 255;
            int var260 = (var258[var259] & 255) << 16;
            int var261 = var257 ^ var260;
            byte[] var262 = S;
            int var263 = var178 >> 24 & 255;
            int var264 = var262[var263] << 24;
            int var265 = var261 ^ var264;
            int var266 = var1[var231][1];
            int var267 = var265 ^ var266;
            this.C1 = var267;
            byte[] var268 = S;
            int var269 = var214 & 255;
            int var270 = var268[var269] & 255;
            byte[] var271 = S;
            int var272 = var233 >> 8 & 255;
            int var273 = (var271[var272] & 255) << 8;
            int var274 = var270 ^ var273;
            byte[] var275 = S;
            int var276 = var178 >> 16 & 255;
            int var277 = (var275[var276] & 255) << 16;
            int var278 = var274 ^ var277;
            byte[] var279 = S;
            int var280 = var196 >> 24 & 255;
            int var281 = var279[var280] << 24;
            int var282 = var278 ^ var281;
            int var283 = var1[var231][2];
            int var284 = var282 ^ var283;
            this.C2 = var284;
            byte[] var285 = S;
            int var286 = var233 & 255;
            int var287 = var285[var286] & 255;
            byte[] var288 = S;
            int var289 = var178 >> 8 & 255;
            int var290 = (var288[var289] & 255) << 8;
            int var291 = var287 ^ var290;
            byte[] var292 = S;
            int var293 = var196 >> 16 & 255;
            int var294 = (var292[var293] & 255) << 16;
            int var295 = var291 ^ var294;
            byte[] var296 = S;
            int var297 = var214 >> 24 & 255;
            int var298 = var296[var297] << 24;
            int var299 = var295 ^ var298;
            int var300 = var1[var231][3];
            int var301 = var299 ^ var300;
            this.C3 = var301;
            return;
         }

         byte[] var16 = S;
         int var17 = this.C0 & 255;
         int var18 = var16[var17] & 255;
         byte[] var19 = S;
         int var20 = this.C1 >> 8 & 255;
         int var21 = (var19[var20] & 255) << 8;
         int var22 = var18 ^ var21;
         byte[] var23 = S;
         int var24 = this.C2 >> 16 & 255;
         int var25 = (var23[var24] & 255) << 16;
         int var26 = var22 ^ var25;
         byte[] var27 = S;
         int var28 = this.C3 >> 24 & 255;
         int var29 = var27[var28] << 24;
         int var30 = var26 ^ var29;
         int var31 = this.mcol(var30);
         int var32 = var1[var14][0];
         int var33 = var31 ^ var32;
         byte[] var34 = S;
         int var35 = this.C1 & 255;
         int var36 = var34[var35] & 255;
         byte[] var37 = S;
         int var38 = this.C2 >> 8 & 255;
         int var39 = (var37[var38] & 255) << 8;
         int var40 = var36 ^ var39;
         byte[] var41 = S;
         int var42 = this.C3 >> 16 & 255;
         int var43 = (var41[var42] & 255) << 16;
         int var44 = var40 ^ var43;
         byte[] var45 = S;
         int var46 = this.C0 >> 24 & 255;
         int var47 = var45[var46] << 24;
         int var48 = var44 ^ var47;
         int var49 = this.mcol(var48);
         int var50 = var1[var14][1];
         int var51 = var49 ^ var50;
         byte[] var52 = S;
         int var53 = this.C2 & 255;
         int var54 = var52[var53] & 255;
         byte[] var55 = S;
         int var56 = this.C3 >> 8 & 255;
         int var57 = (var55[var56] & 255) << 8;
         int var58 = var54 ^ var57;
         byte[] var59 = S;
         int var60 = this.C0 >> 16 & 255;
         int var61 = (var59[var60] & 255) << 16;
         int var62 = var58 ^ var61;
         byte[] var63 = S;
         int var64 = this.C1 >> 24 & 255;
         int var65 = var63[var64] << 24;
         int var66 = var62 ^ var65;
         int var67 = this.mcol(var66);
         int var68 = var1[var14][2];
         int var69 = var67 ^ var68;
         byte[] var70 = S;
         int var71 = this.C3 & 255;
         int var72 = var70[var71] & 255;
         byte[] var73 = S;
         int var74 = this.C0 >> 8 & 255;
         int var75 = (var73[var74] & 255) << 8;
         int var76 = var72 ^ var75;
         byte[] var77 = S;
         int var78 = this.C1 >> 16 & 255;
         int var79 = (var77[var78] & 255) << 16;
         int var80 = var76 ^ var79;
         byte[] var81 = S;
         int var82 = this.C2 >> 24 & 255;
         int var83 = var81[var82] << 24;
         int var84 = var80 ^ var83;
         int var85 = this.mcol(var84);
         int var86 = var14 + 1;
         int var87 = var1[var14][3];
         int var88 = var85 ^ var87;
         byte[] var89 = S;
         int var90 = var33 & 255;
         int var91 = var89[var90] & 255;
         byte[] var92 = S;
         int var93 = var51 >> 8 & 255;
         int var94 = (var92[var93] & 255) << 8;
         int var95 = var91 ^ var94;
         byte[] var96 = S;
         int var97 = var69 >> 16 & 255;
         int var98 = (var96[var97] & 255) << 16;
         int var99 = var95 ^ var98;
         byte[] var100 = S;
         int var101 = var88 >> 24 & 255;
         int var102 = var100[var101] << 24;
         int var103 = var99 ^ var102;
         int var104 = this.mcol(var103);
         int var105 = var1[var86][0];
         int var106 = var104 ^ var105;
         this.C0 = var106;
         byte[] var107 = S;
         int var108 = var51 & 255;
         int var109 = var107[var108] & 255;
         byte[] var110 = S;
         int var111 = var69 >> 8 & 255;
         int var112 = (var110[var111] & 255) << 8;
         int var113 = var109 ^ var112;
         byte[] var114 = S;
         int var115 = var88 >> 16 & 255;
         int var116 = (var114[var115] & 255) << 16;
         int var117 = var113 ^ var116;
         byte[] var118 = S;
         int var119 = var33 >> 24 & 255;
         int var120 = var118[var119] << 24;
         int var121 = var117 ^ var120;
         int var122 = this.mcol(var121);
         int var123 = var1[var86][1];
         int var124 = var122 ^ var123;
         this.C1 = var124;
         byte[] var125 = S;
         int var126 = var69 & 255;
         int var127 = var125[var126] & 255;
         byte[] var128 = S;
         int var129 = var88 >> 8 & 255;
         int var130 = (var128[var129] & 255) << 8;
         int var131 = var127 ^ var130;
         byte[] var132 = S;
         int var133 = var33 >> 16 & 255;
         int var134 = (var132[var133] & 255) << 16;
         int var135 = var131 ^ var134;
         byte[] var136 = S;
         int var137 = var51 >> 24 & 255;
         int var138 = var136[var137] << 24;
         int var139 = var135 ^ var138;
         int var140 = this.mcol(var139);
         int var141 = var1[var86][2];
         int var142 = var140 ^ var141;
         this.C2 = var142;
         byte[] var143 = S;
         int var144 = var88 & 255;
         int var145 = var143[var144] & 255;
         byte[] var146 = S;
         int var147 = var33 >> 8 & 255;
         int var148 = (var146[var147] & 255) << 8;
         int var149 = var145 ^ var148;
         byte[] var150 = S;
         int var151 = var51 >> 16 & 255;
         int var152 = (var150[var151] & 255) << 16;
         int var153 = var149 ^ var152;
         byte[] var154 = S;
         int var155 = var69 >> 24 & 255;
         int var156 = var154[var155] << 24;
         int var157 = var153 ^ var156;
         int var158 = this.mcol(var157);
         var14 = var86 + 1;
         int var159 = var1[var86][3];
         int var160 = var158 ^ var159;
         this.C3 = var160;
      }
   }

   private int[][] generateWorkingKey(byte[] var1, boolean var2) {
      int var3 = var1.length / 4;
      if(var3 == 4 || var3 == 6 || var3 == 8) {
         int var4 = var3 * 4;
         int var5 = var1.length;
         if(var4 == var5) {
            int var6 = var3 + 6;
            this.ROUNDS = var6;
            int var7 = this.ROUNDS + 1;
            int[] var8 = new int[]{var7, 4};
            int[][] var9 = (int[][])Array.newInstance(Integer.TYPE, var8);
            int var10 = 0;
            int var11 = 0;

            while(true) {
               int var12 = var1.length;
               if(var11 >= var12) {
                  int var26 = this.ROUNDS + 1 << 2;

                  for(int var27 = var3; var27 < var26; ++var27) {
                     int var28 = var27 - 1 >> 2;
                     int[] var29 = var9[var28];
                     int var30 = var27 - 1 & 3;
                     int var31 = var29[var30];
                     if(var27 % var3 == 0) {
                        int var32 = this.shift(var31, 8);
                        int var33 = this.subWord(var32);
                        int[] var34 = rcon;
                        int var35 = var27 / var3 - 1;
                        int var36 = var34[var35];
                        var31 = var33 ^ var36;
                     } else if(var3 > 6 && var27 % var3 == 4) {
                        var31 = this.subWord(var31);
                     }

                     int var37 = var27 >> 2;
                     int[] var38 = var9[var37];
                     int var39 = var27 & 3;
                     int var40 = var27 - var3 >> 2;
                     int[] var41 = var9[var40];
                     int var42 = var27 - var3 & 3;
                     int var43 = var41[var42] ^ var31;
                     var38[var39] = var43;
                  }

                  if(!var2) {
                     int var44 = 1;

                     while(true) {
                        int var45 = this.ROUNDS;
                        if(var44 >= var45) {
                           break;
                        }

                        int var49;
                        for(byte var50 = 0; var50 < 4; var49 = var50 + 1) {
                           int[] var46 = var9[var44];
                           int var47 = var9[var44][var50];
                           int var48 = this.inv_mcol(var47);
                           var46[var50] = var48;
                        }

                        ++var44;
                     }
                  }

                  return var9;
               }

               int var13 = var10 >> 2;
               int[] var14 = var9[var13];
               int var15 = var10 & 3;
               int var16 = var1[var11] & 255;
               int var17 = var11 + 1;
               int var18 = (var1[var17] & 255) << 8;
               int var19 = var16 | var18;
               int var20 = var11 + 2;
               int var21 = (var1[var20] & 255) << 16;
               int var22 = var19 | var21;
               int var23 = var11 + 3;
               int var24 = var1[var23] << 24;
               int var25 = var22 | var24;
               var14[var15] = var25;
               var11 += 4;
               ++var10;
            }
         }
      }

      throw new IllegalArgumentException("Key length not 128/192/256 bits.");
   }

   private int inv_mcol(int var1) {
      int var2 = this.FFmulX(var1);
      int var3 = this.FFmulX(var2);
      int var4 = this.FFmulX(var3);
      int var5 = var1 ^ var4;
      int var6 = var2 ^ var3 ^ var4;
      int var7 = var2 ^ var5;
      int var8 = this.shift(var7, 8);
      int var9 = var6 ^ var8;
      int var10 = var3 ^ var5;
      int var11 = this.shift(var10, 16);
      int var12 = var9 ^ var11;
      int var13 = this.shift(var5, 24);
      return var12 ^ var13;
   }

   private int mcol(int var1) {
      int var2 = this.FFmulX(var1);
      int var3 = var1 ^ var2;
      int var4 = this.shift(var3, 8) ^ var2;
      int var5 = this.shift(var1, 16);
      int var6 = var4 ^ var5;
      int var7 = this.shift(var1, 24);
      return var6 ^ var7;
   }

   private void packBlock(byte[] var1, int var2) {
      int var4 = var2 + 1;
      byte var5 = (byte)this.C0;
      var1[var2] = var5;
      int var6 = var4 + 1;
      byte var7 = (byte)(this.C0 >> 8);
      var1[var4] = var7;
      int var8 = var6 + 1;
      byte var9 = (byte)(this.C0 >> 16);
      var1[var6] = var9;
      int var10 = var8 + 1;
      byte var11 = (byte)(this.C0 >> 24);
      var1[var8] = var11;
      int var12 = var10 + 1;
      byte var13 = (byte)this.C1;
      var1[var10] = var13;
      int var14 = var12 + 1;
      byte var15 = (byte)(this.C1 >> 8);
      var1[var12] = var15;
      int var16 = var14 + 1;
      byte var17 = (byte)(this.C1 >> 16);
      var1[var14] = var17;
      int var18 = var16 + 1;
      byte var19 = (byte)(this.C1 >> 24);
      var1[var16] = var19;
      int var20 = var18 + 1;
      byte var21 = (byte)this.C2;
      var1[var18] = var21;
      int var22 = var20 + 1;
      byte var23 = (byte)(this.C2 >> 8);
      var1[var20] = var23;
      int var24 = var22 + 1;
      byte var25 = (byte)(this.C2 >> 16);
      var1[var22] = var25;
      int var26 = var24 + 1;
      byte var27 = (byte)(this.C2 >> 24);
      var1[var24] = var27;
      int var28 = var26 + 1;
      byte var29 = (byte)this.C3;
      var1[var26] = var29;
      int var30 = var28 + 1;
      byte var31 = (byte)(this.C3 >> 8);
      var1[var28] = var31;
      int var32 = var30 + 1;
      byte var33 = (byte)(this.C3 >> 16);
      var1[var30] = var33;
      int var34 = var32 + 1;
      byte var35 = (byte)(this.C3 >> 24);
      var1[var32] = var35;
   }

   private int shift(int var1, int var2) {
      int var3 = var1 >>> var2;
      int var4 = -var2;
      int var5 = var1 << var4;
      return var3 | var5;
   }

   private int subWord(int var1) {
      byte[] var2 = S;
      int var3 = var1 & 255;
      int var4 = var2[var3] & 255;
      byte[] var5 = S;
      int var6 = var1 >> 8 & 255;
      int var7 = (var5[var6] & 255) << 8;
      int var8 = var4 | var7;
      byte[] var9 = S;
      int var10 = var1 >> 16 & 255;
      int var11 = (var9[var10] & 255) << 16;
      int var12 = var8 | var11;
      byte[] var13 = S;
      int var14 = var1 >> 24 & 255;
      int var15 = var13[var14] << 24;
      return var12 | var15;
   }

   private void unpackBlock(byte[] var1, int var2) {
      int var4 = var2 + 1;
      int var5 = var1[var2] & 255;
      this.C0 = var5;
      int var6 = this.C0;
      int var7 = var4 + 1;
      int var8 = (var1[var4] & 255) << 8;
      int var9 = var6 | var8;
      this.C0 = var9;
      int var10 = this.C0;
      int var11 = var7 + 1;
      int var12 = (var1[var7] & 255) << 16;
      int var13 = var10 | var12;
      this.C0 = var13;
      int var14 = this.C0;
      int var15 = var11 + 1;
      int var16 = var1[var11] << 24;
      int var17 = var14 | var16;
      this.C0 = var17;
      int var18 = var15 + 1;
      int var19 = var1[var15] & 255;
      this.C1 = var19;
      int var20 = this.C1;
      int var21 = var18 + 1;
      int var22 = (var1[var18] & 255) << 8;
      int var23 = var20 | var22;
      this.C1 = var23;
      int var24 = this.C1;
      int var25 = var21 + 1;
      int var26 = (var1[var21] & 255) << 16;
      int var27 = var24 | var26;
      this.C1 = var27;
      int var28 = this.C1;
      int var29 = var25 + 1;
      int var30 = var1[var25] << 24;
      int var31 = var28 | var30;
      this.C1 = var31;
      int var32 = var29 + 1;
      int var33 = var1[var29] & 255;
      this.C2 = var33;
      int var34 = this.C2;
      int var35 = var32 + 1;
      int var36 = (var1[var32] & 255) << 8;
      int var37 = var34 | var36;
      this.C2 = var37;
      int var38 = this.C2;
      int var39 = var35 + 1;
      int var40 = (var1[var35] & 255) << 16;
      int var41 = var38 | var40;
      this.C2 = var41;
      int var42 = this.C2;
      int var43 = var39 + 1;
      int var44 = var1[var39] << 24;
      int var45 = var42 | var44;
      this.C2 = var45;
      int var46 = var43 + 1;
      int var47 = var1[var43] & 255;
      this.C3 = var47;
      int var48 = this.C3;
      int var49 = var46 + 1;
      int var50 = (var1[var46] & 255) << 8;
      int var51 = var48 | var50;
      this.C3 = var51;
      int var52 = this.C3;
      int var53 = var49 + 1;
      int var54 = (var1[var49] & 255) << 16;
      int var55 = var52 | var54;
      this.C3 = var55;
      int var56 = this.C3;
      int var57 = var53 + 1;
      int var58 = var1[var53] << 24;
      int var59 = var56 | var58;
      this.C3 = var59;
   }

   public String getAlgorithmName() {
      return "AES";
   }

   public int getBlockSize() {
      return 16;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof KeyParameter) {
         byte[] var3 = ((KeyParameter)var2).getKey();
         int[][] var4 = this.generateWorkingKey(var3, var1);
         this.WorkingKey = var4;
         this.forEncryption = var1;
      } else {
         StringBuilder var5 = (new StringBuilder()).append("invalid parameter passed to AES init - ");
         String var6 = var2.getClass().getName();
         String var7 = var5.append(var6).toString();
         throw new IllegalArgumentException(var7);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(this.WorkingKey == null) {
         throw new IllegalStateException("AES engine not initialised");
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
               if(this.forEncryption) {
                  this.unpackBlock(var1, var2);
                  int[][] var9 = this.WorkingKey;
                  this.encryptBlock(var9);
                  this.packBlock(var3, var4);
               } else {
                  this.unpackBlock(var1, var2);
                  int[][] var10 = this.WorkingKey;
                  this.decryptBlock(var10);
                  this.packBlock(var3, var4);
               }

               return 16;
            }
         }
      }
   }

   public void reset() {}
}
