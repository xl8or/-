package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.MaxBytesExceededException;
import myorg.bouncycastle.crypto.StreamCipher;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.util.Strings;

public class Salsa20Engine implements StreamCipher {

   private static final byte[] sigma = Strings.toByteArray("expand 32-byte k");
   private static final int stateSize = 16;
   private static final byte[] tau = Strings.toByteArray("expand 16-byte k");
   private int cW0;
   private int cW1;
   private int cW2;
   private int[] engineState;
   private int index = 0;
   private boolean initialised;
   private byte[] keyStream;
   private byte[] workingIV;
   private byte[] workingKey;
   private int[] x;


   public Salsa20Engine() {
      int[] var1 = new int[16];
      this.engineState = var1;
      int[] var2 = new int[16];
      this.x = var2;
      byte[] var3 = new byte[64];
      this.keyStream = var3;
      this.workingKey = null;
      this.workingIV = null;
      this.initialised = (boolean)0;
   }

   private int byteToIntLittle(byte[] var1, int var2) {
      int var3 = var1[var2] & 255;
      int var4 = var2 + 1;
      int var5 = (var1[var4] & 255) << 8;
      int var6 = var3 | var5;
      int var7 = var2 + 2;
      int var8 = (var1[var7] & 255) << 16;
      int var9 = var6 | var8;
      int var10 = var2 + 3;
      int var11 = var1[var10] << 24;
      return var9 | var11;
   }

   private byte[] intToByteLittle(int var1, byte[] var2, int var3) {
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
      return var2;
   }

   private boolean limitExceeded() {
      int var1 = this.cW0 + 1;
      this.cW0 = var1;
      boolean var4;
      if(this.cW0 == 0) {
         int var2 = this.cW1 + 1;
         this.cW1 = var2;
         if(this.cW1 == 0) {
            int var3 = this.cW2 + 1;
            this.cW2 = var3;
            if((this.cW2 & 32) != 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   private boolean limitExceeded(int var1) {
      boolean var3;
      if(this.cW0 >= 0) {
         int var2 = this.cW0 + var1;
         this.cW0 = var2;
      } else {
         int var4 = this.cW0 + var1;
         this.cW0 = var4;
         if(this.cW0 >= 0) {
            int var5 = this.cW1 + 1;
            this.cW1 = var5;
            if(this.cW1 == 0) {
               int var6 = this.cW2 + 1;
               this.cW2 = var6;
               if((this.cW2 & 32) != 0) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               return var3;
            }
         }
      }

      var3 = false;
      return var3;
   }

   private void resetCounter() {
      this.cW0 = 0;
      this.cW1 = 0;
      this.cW2 = 0;
   }

   private int rotl(int var1, int var2) {
      int var3 = var1 << var2;
      int var4 = -var2;
      int var5 = var1 >>> var4;
      return var3 | var5;
   }

   private void salsa20WordToByte(int[] var1, byte[] var2) {
      int[] var3 = this.x;
      int var4 = var1.length;
      System.arraycopy(var1, 0, var3, 0, var4);

      for(int var5 = 0; var5 < 10; ++var5) {
         int[] var6 = this.x;
         int var7 = var6[4];
         int var8 = this.x[0];
         int var9 = this.x[12];
         int var10 = var8 + var9;
         int var11 = this.rotl(var10, 7);
         int var12 = var7 ^ var11;
         var6[4] = var12;
         int[] var13 = this.x;
         int var14 = var13[8];
         int var15 = this.x[4];
         int var16 = this.x[0];
         int var17 = var15 + var16;
         int var18 = this.rotl(var17, 9);
         int var19 = var14 ^ var18;
         var13[8] = var19;
         int[] var20 = this.x;
         int var21 = var20[12];
         int var22 = this.x[8];
         int var23 = this.x[4];
         int var24 = var22 + var23;
         int var25 = this.rotl(var24, 13);
         int var26 = var21 ^ var25;
         var20[12] = var26;
         int[] var27 = this.x;
         int var28 = var27[0];
         int var29 = this.x[12];
         int var30 = this.x[8];
         int var31 = var29 + var30;
         int var32 = this.rotl(var31, 18);
         int var33 = var28 ^ var32;
         var27[0] = var33;
         int[] var34 = this.x;
         int var35 = var34[9];
         int var36 = this.x[5];
         int var37 = this.x[1];
         int var38 = var36 + var37;
         int var39 = this.rotl(var38, 7);
         int var40 = var35 ^ var39;
         var34[9] = var40;
         int[] var41 = this.x;
         int var42 = var41[13];
         int var43 = this.x[9];
         int var44 = this.x[5];
         int var45 = var43 + var44;
         int var46 = this.rotl(var45, 9);
         int var47 = var42 ^ var46;
         var41[13] = var47;
         int[] var48 = this.x;
         int var49 = var48[1];
         int var50 = this.x[13];
         int var51 = this.x[9];
         int var52 = var50 + var51;
         int var53 = this.rotl(var52, 13);
         int var54 = var49 ^ var53;
         var48[1] = var54;
         int[] var55 = this.x;
         int var56 = var55[5];
         int var57 = this.x[1];
         int var58 = this.x[13];
         int var59 = var57 + var58;
         int var60 = this.rotl(var59, 18);
         int var61 = var56 ^ var60;
         var55[5] = var61;
         int[] var62 = this.x;
         int var63 = var62[14];
         int var64 = this.x[10];
         int var65 = this.x[6];
         int var66 = var64 + var65;
         int var67 = this.rotl(var66, 7);
         int var68 = var63 ^ var67;
         var62[14] = var68;
         int[] var69 = this.x;
         int var70 = var69[2];
         int var71 = this.x[14];
         int var72 = this.x[10];
         int var73 = var71 + var72;
         int var74 = this.rotl(var73, 9);
         int var75 = var70 ^ var74;
         var69[2] = var75;
         int[] var76 = this.x;
         int var77 = var76[6];
         int var78 = this.x[2];
         int var79 = this.x[14];
         int var80 = var78 + var79;
         int var81 = this.rotl(var80, 13);
         int var82 = var77 ^ var81;
         var76[6] = var82;
         int[] var83 = this.x;
         int var84 = var83[10];
         int var85 = this.x[6];
         int var86 = this.x[2];
         int var87 = var85 + var86;
         int var88 = this.rotl(var87, 18);
         int var89 = var84 ^ var88;
         var83[10] = var89;
         int[] var90 = this.x;
         int var91 = var90[3];
         int var92 = this.x[15];
         int var93 = this.x[11];
         int var94 = var92 + var93;
         int var95 = this.rotl(var94, 7);
         int var96 = var91 ^ var95;
         var90[3] = var96;
         int[] var97 = this.x;
         int var98 = var97[7];
         int var99 = this.x[3];
         int var100 = this.x[15];
         int var101 = var99 + var100;
         int var102 = this.rotl(var101, 9);
         int var103 = var98 ^ var102;
         var97[7] = var103;
         int[] var104 = this.x;
         int var105 = var104[11];
         int var106 = this.x[7];
         int var107 = this.x[3];
         int var108 = var106 + var107;
         int var109 = this.rotl(var108, 13);
         int var110 = var105 ^ var109;
         var104[11] = var110;
         int[] var111 = this.x;
         int var112 = var111[15];
         int var113 = this.x[11];
         int var114 = this.x[7];
         int var115 = var113 + var114;
         int var116 = this.rotl(var115, 18);
         int var117 = var112 ^ var116;
         var111[15] = var117;
         int[] var118 = this.x;
         int var119 = var118[1];
         int var120 = this.x[0];
         int var121 = this.x[3];
         int var122 = var120 + var121;
         int var123 = this.rotl(var122, 7);
         int var124 = var119 ^ var123;
         var118[1] = var124;
         int[] var125 = this.x;
         int var126 = var125[2];
         int var127 = this.x[1];
         int var128 = this.x[0];
         int var129 = var127 + var128;
         int var130 = this.rotl(var129, 9);
         int var131 = var126 ^ var130;
         var125[2] = var131;
         int[] var132 = this.x;
         int var133 = var132[3];
         int var134 = this.x[2];
         int var135 = this.x[1];
         int var136 = var134 + var135;
         int var137 = this.rotl(var136, 13);
         int var138 = var133 ^ var137;
         var132[3] = var138;
         int[] var139 = this.x;
         int var140 = var139[0];
         int var141 = this.x[3];
         int var142 = this.x[2];
         int var143 = var141 + var142;
         int var144 = this.rotl(var143, 18);
         int var145 = var140 ^ var144;
         var139[0] = var145;
         int[] var146 = this.x;
         int var147 = var146[6];
         int var148 = this.x[5];
         int var149 = this.x[4];
         int var150 = var148 + var149;
         int var151 = this.rotl(var150, 7);
         int var152 = var147 ^ var151;
         var146[6] = var152;
         int[] var153 = this.x;
         int var154 = var153[7];
         int var155 = this.x[6];
         int var156 = this.x[5];
         int var157 = var155 + var156;
         int var158 = this.rotl(var157, 9);
         int var159 = var154 ^ var158;
         var153[7] = var159;
         int[] var160 = this.x;
         int var161 = var160[4];
         int var162 = this.x[7];
         int var163 = this.x[6];
         int var164 = var162 + var163;
         int var165 = this.rotl(var164, 13);
         int var166 = var161 ^ var165;
         var160[4] = var166;
         int[] var167 = this.x;
         int var168 = var167[5];
         int var169 = this.x[4];
         int var170 = this.x[7];
         int var171 = var169 + var170;
         int var172 = this.rotl(var171, 18);
         int var173 = var168 ^ var172;
         var167[5] = var173;
         int[] var174 = this.x;
         int var175 = var174[11];
         int var176 = this.x[10];
         int var177 = this.x[9];
         int var178 = var176 + var177;
         int var179 = this.rotl(var178, 7);
         int var180 = var175 ^ var179;
         var174[11] = var180;
         int[] var181 = this.x;
         int var182 = var181[8];
         int var183 = this.x[11];
         int var184 = this.x[10];
         int var185 = var183 + var184;
         int var186 = this.rotl(var185, 9);
         int var187 = var182 ^ var186;
         var181[8] = var187;
         int[] var188 = this.x;
         int var189 = var188[9];
         int var190 = this.x[8];
         int var191 = this.x[11];
         int var192 = var190 + var191;
         int var193 = this.rotl(var192, 13);
         int var194 = var189 ^ var193;
         var188[9] = var194;
         int[] var195 = this.x;
         int var196 = var195[10];
         int var197 = this.x[9];
         int var198 = this.x[8];
         int var199 = var197 + var198;
         int var200 = this.rotl(var199, 18);
         int var201 = var196 ^ var200;
         var195[10] = var201;
         int[] var202 = this.x;
         int var203 = var202[12];
         int var204 = this.x[15];
         int var205 = this.x[14];
         int var206 = var204 + var205;
         int var207 = this.rotl(var206, 7);
         int var208 = var203 ^ var207;
         var202[12] = var208;
         int[] var209 = this.x;
         int var210 = var209[13];
         int var211 = this.x[12];
         int var212 = this.x[15];
         int var213 = var211 + var212;
         int var214 = this.rotl(var213, 9);
         int var215 = var210 ^ var214;
         var209[13] = var215;
         int[] var216 = this.x;
         int var217 = var216[14];
         int var218 = this.x[13];
         int var219 = this.x[12];
         int var220 = var218 + var219;
         int var221 = this.rotl(var220, 13);
         int var222 = var217 ^ var221;
         var216[14] = var222;
         int[] var223 = this.x;
         int var224 = var223[15];
         int var225 = this.x[14];
         int var226 = this.x[13];
         int var227 = var225 + var226;
         int var228 = this.rotl(var227, 18);
         int var229 = var224 ^ var228;
         var223[15] = var229;
      }

      int var230 = 0;

      for(int var231 = 0; var231 < 16; ++var231) {
         int var232 = this.x[var231];
         int var233 = var1[var231];
         int var234 = var232 + var233;
         this.intToByteLittle(var234, var2, var230);
         var230 += 4;
      }

      int var236 = 16;

      while(true) {
         int var237 = this.x.length;
         if(var236 >= var237) {
            return;
         }

         int var238 = this.x[var236];
         this.intToByteLittle(var238, var2, var230);
         var230 += 4;
         ++var236;
      }
   }

   private void setKey(byte[] var1, byte[] var2) {
      this.workingKey = var1;
      this.workingIV = var2;
      this.index = 0;
      this.resetCounter();
      byte var3 = 0;
      int[] var4 = this.engineState;
      byte[] var5 = this.workingKey;
      int var6 = this.byteToIntLittle(var5, 0);
      var4[1] = var6;
      int[] var7 = this.engineState;
      byte[] var8 = this.workingKey;
      int var9 = this.byteToIntLittle(var8, 4);
      var7[2] = var9;
      int[] var10 = this.engineState;
      byte[] var11 = this.workingKey;
      int var12 = this.byteToIntLittle(var11, 8);
      var10[3] = var12;
      int[] var13 = this.engineState;
      byte[] var14 = this.workingKey;
      int var15 = this.byteToIntLittle(var14, 12);
      var13[4] = var15;
      byte[] var16;
      if(this.workingKey.length == 32) {
         var16 = sigma;
         var3 = 16;
      } else {
         var16 = tau;
      }

      int[] var17 = this.engineState;
      byte[] var18 = this.workingKey;
      int var19 = this.byteToIntLittle(var18, var3);
      var17[11] = var19;
      int[] var20 = this.engineState;
      byte[] var21 = this.workingKey;
      int var22 = var3 + 4;
      int var23 = this.byteToIntLittle(var21, var22);
      var20[12] = var23;
      int[] var24 = this.engineState;
      byte[] var25 = this.workingKey;
      int var26 = var3 + 8;
      int var27 = this.byteToIntLittle(var25, var26);
      var24[13] = var27;
      int[] var28 = this.engineState;
      byte[] var29 = this.workingKey;
      int var30 = var3 + 12;
      int var31 = this.byteToIntLittle(var29, var30);
      var28[14] = var31;
      int[] var32 = this.engineState;
      int var33 = this.byteToIntLittle(var16, 0);
      var32[0] = var33;
      int[] var34 = this.engineState;
      int var35 = this.byteToIntLittle(var16, 4);
      var34[5] = var35;
      int[] var36 = this.engineState;
      int var37 = this.byteToIntLittle(var16, 8);
      var36[10] = var37;
      int[] var38 = this.engineState;
      int var39 = this.byteToIntLittle(var16, 12);
      var38[15] = var39;
      int[] var40 = this.engineState;
      byte[] var41 = this.workingIV;
      int var42 = this.byteToIntLittle(var41, 0);
      var40[6] = var42;
      int[] var43 = this.engineState;
      byte[] var44 = this.workingIV;
      int var45 = this.byteToIntLittle(var44, 4);
      var43[7] = var45;
      int[] var46 = this.engineState;
      this.engineState[9] = 0;
      var46[8] = 0;
      this.initialised = (boolean)1;
   }

   public String getAlgorithmName() {
      return "Salsa20";
   }

   public void init(boolean var1, CipherParameters var2) {
      if(!(var2 instanceof ParametersWithIV)) {
         throw new IllegalArgumentException("Salsa20 Init parameters must include an IV");
      } else {
         ParametersWithIV var3 = (ParametersWithIV)var2;
         byte[] var4 = var3.getIV();
         if(var4 != null && var4.length == 8) {
            if(!(var3.getParameters() instanceof KeyParameter)) {
               throw new IllegalArgumentException("Salsa20 Init parameters must include a key");
            } else {
               byte[] var5 = ((KeyParameter)var3.getParameters()).getKey();
               this.workingKey = var5;
               this.workingIV = var4;
               byte[] var6 = this.workingKey;
               byte[] var7 = this.workingIV;
               this.setKey(var6, var7);
            }
         } else {
            throw new IllegalArgumentException("Salsa20 requires exactly 8 bytes of IV");
         }
      }
   }

   public void processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      if(!this.initialised) {
         StringBuilder var6 = new StringBuilder();
         String var7 = this.getAlgorithmName();
         String var8 = var6.append(var7).append(" not initialised").toString();
         throw new IllegalStateException(var8);
      } else {
         int var9 = var2 + var3;
         int var10 = var1.length;
         if(var9 > var10) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var11 = var5 + var3;
            int var12 = var4.length;
            if(var11 > var12) {
               throw new DataLengthException("output buffer too short");
            } else if(this.limitExceeded(var3)) {
               throw new MaxBytesExceededException("2^70 byte limit per IV would be exceeded; Change IV");
            } else {
               for(int var13 = 0; var13 < var3; ++var13) {
                  if(this.index == 0) {
                     int[] var14 = this.engineState;
                     byte[] var15 = this.keyStream;
                     this.salsa20WordToByte(var14, var15);
                     int[] var16 = this.engineState;
                     int var17 = var16[8] + 1;
                     var16[8] = var17;
                     if(this.engineState[8] == 0) {
                        int[] var18 = this.engineState;
                        int var19 = var18[9] + 1;
                        var18[9] = var19;
                     }
                  }

                  int var20 = var13 + var5;
                  byte[] var21 = this.keyStream;
                  int var22 = this.index;
                  byte var23 = var21[var22];
                  int var24 = var13 + var2;
                  byte var25 = var1[var24];
                  byte var26 = (byte)(var23 ^ var25);
                  var4[var20] = var26;
                  int var27 = this.index + 1 & 63;
                  this.index = var27;
               }

            }
         }
      }
   }

   public void reset() {
      byte[] var1 = this.workingKey;
      byte[] var2 = this.workingIV;
      this.setKey(var1, var2);
   }

   public byte returnByte(byte var1) {
      if(this.limitExceeded()) {
         throw new MaxBytesExceededException("2^70 byte limit per IV; Change IV");
      } else {
         if(this.index == 0) {
            int[] var2 = this.engineState;
            byte[] var3 = this.keyStream;
            this.salsa20WordToByte(var2, var3);
            int[] var4 = this.engineState;
            int var5 = var4[8] + 1;
            var4[8] = var5;
            if(this.engineState[8] == 0) {
               int[] var6 = this.engineState;
               int var7 = var6[9] + 1;
               var6[9] = var7;
            }
         }

         byte[] var8 = this.keyStream;
         int var9 = this.index;
         byte var10 = (byte)(var8[var9] ^ var1);
         int var11 = this.index + 1 & 63;
         this.index = var11;
         return var10;
      }
   }
}
