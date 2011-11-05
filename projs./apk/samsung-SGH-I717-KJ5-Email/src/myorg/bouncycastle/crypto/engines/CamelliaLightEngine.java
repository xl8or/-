package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class CamelliaLightEngine implements BlockCipher {

   private static final int BLOCK_SIZE = 16;
   private static final int MASK8 = 255;
   private static final byte[] SBOX1 = new byte[]{(byte)112, (byte)130, (byte)44, (byte)236, (byte)179, (byte)39, (byte)192, (byte)229, (byte)228, (byte)133, (byte)87, (byte)53, (byte)234, (byte)12, (byte)174, (byte)65, (byte)35, (byte)239, (byte)107, (byte)147, (byte)69, (byte)25, (byte)165, (byte)33, (byte)237, (byte)14, (byte)79, (byte)78, (byte)29, (byte)101, (byte)146, (byte)189, (byte)134, (byte)184, (byte)175, (byte)143, (byte)124, (byte)235, (byte)31, (byte)206, (byte)62, (byte)48, (byte)220, (byte)95, (byte)94, (byte)197, (byte)11, (byte)26, (byte)166, (byte)225, (byte)57, (byte)202, (byte)213, (byte)71, (byte)93, (byte)61, (byte)217, (byte)1, (byte)90, (byte)214, (byte)81, (byte)86, (byte)108, (byte)77, (byte)139, (byte)13, (byte)154, (byte)102, (byte)251, (byte)204, (byte)176, (byte)45, (byte)116, (byte)18, (byte)43, (byte)32, (byte)240, (byte)177, (byte)132, (byte)153, (byte)223, (byte)76, (byte)203, (byte)194, (byte)52, (byte)126, (byte)118, (byte)5, (byte)109, (byte)183, (byte)169, (byte)49, (byte)209, (byte)23, (byte)4, (byte)215, (byte)20, (byte)88, (byte)58, (byte)97, (byte)222, (byte)27, (byte)17, (byte)28, (byte)50, (byte)15, (byte)156, (byte)22, (byte)83, (byte)24, (byte)242, (byte)34, (byte)254, (byte)68, (byte)207, (byte)178, (byte)195, (byte)181, (byte)122, (byte)145, (byte)36, (byte)8, (byte)232, (byte)168, (byte)96, (byte)252, (byte)105, (byte)80, (byte)170, (byte)208, (byte)160, (byte)125, (byte)161, (byte)137, (byte)98, (byte)151, (byte)84, (byte)91, (byte)30, (byte)149, (byte)224, (byte)255, (byte)100, (byte)210, (byte)16, (byte)196, (byte)0, (byte)72, (byte)163, (byte)247, (byte)117, (byte)219, (byte)138, (byte)3, (byte)230, (byte)218, (byte)9, (byte)63, (byte)221, (byte)148, (byte)135, (byte)92, (byte)131, (byte)2, (byte)205, (byte)74, (byte)144, (byte)51, (byte)115, (byte)103, (byte)246, (byte)243, (byte)157, (byte)127, (byte)191, (byte)226, (byte)82, (byte)155, (byte)216, (byte)38, (byte)200, (byte)55, (byte)198, (byte)59, (byte)129, (byte)150, (byte)111, (byte)75, (byte)19, (byte)190, (byte)99, (byte)46, (byte)233, (byte)121, (byte)167, (byte)140, (byte)159, (byte)110, (byte)188, (byte)142, (byte)41, (byte)245, (byte)249, (byte)182, (byte)47, (byte)253, (byte)180, (byte)89, (byte)120, (byte)152, (byte)6, (byte)106, (byte)231, (byte)70, (byte)113, (byte)186, (byte)212, (byte)37, (byte)171, (byte)66, (byte)136, (byte)162, (byte)141, (byte)250, (byte)114, (byte)7, (byte)185, (byte)85, (byte)248, (byte)238, (byte)172, (byte)10, (byte)54, (byte)73, (byte)42, (byte)104, (byte)60, (byte)56, (byte)241, (byte)164, (byte)64, (byte)40, (byte)211, (byte)123, (byte)187, (byte)201, (byte)67, (byte)193, (byte)21, (byte)227, (byte)173, (byte)244, (byte)119, (byte)199, (byte)128, (byte)158};
   private static final int[] SIGMA = new int[]{-1600231809, 1003262091, -1233459112, 1286239154, -957401297, -380665154, 1426019237, -237801700, 283453434, -563598051, -1336506174, -1276722691};
   private boolean _keyis128;
   private boolean initialized;
   private int[] ke;
   private int[] kw;
   private int[] state;
   private int[] subkey;


   public CamelliaLightEngine() {
      int[] var1 = new int[96];
      this.subkey = var1;
      int[] var2 = new int[8];
      this.kw = var2;
      int[] var3 = new int[12];
      this.ke = var3;
      int[] var4 = new int[4];
      this.state = var4;
   }

   private int bytes2int(byte[] var1, int var2) {
      int var3 = 0;

      for(int var4 = 0; var4 < 4; ++var4) {
         int var5 = var3 << 8;
         int var6 = var4 + var2;
         int var7 = var1[var6] & 255;
         var3 = var5 + var7;
      }

      return var3;
   }

   private void camelliaF2(int[] var1, int[] var2, int var3) {
      int var4 = var1[0];
      int var5 = var3 + 0;
      int var6 = var2[var5];
      int var7 = var4 ^ var6;
      int var8 = var7 & 255;
      int var9 = this.sbox4(var8);
      int var10 = var7 >>> 8 & 255;
      int var11 = this.sbox3(var10) << 8;
      int var12 = var9 | var11;
      int var13 = var7 >>> 16 & 255;
      int var14 = this.sbox2(var13) << 16;
      int var15 = var12 | var14;
      byte[] var16 = SBOX1;
      int var17 = var7 >>> 24 & 255;
      int var18 = (var16[var17] & 255) << 24;
      int var19 = var15 | var18;
      int var20 = var1[1];
      int var21 = var3 + 1;
      int var22 = var2[var21];
      int var23 = var20 ^ var22;
      byte[] var24 = SBOX1;
      int var25 = var23 & 255;
      int var26 = var24[var25] & 255;
      int var27 = var23 >>> 8 & 255;
      int var28 = this.sbox4(var27) << 8;
      int var29 = var26 | var28;
      int var30 = var23 >>> 16 & 255;
      int var31 = this.sbox3(var30) << 16;
      int var32 = var29 | var31;
      int var33 = var23 >>> 24 & 255;
      int var34 = this.sbox2(var33) << 24;
      int var35 = leftRotate(var32 | var34, 8);
      int var36 = var19 ^ var35;
      int var37 = leftRotate(var35, 8) ^ var36;
      int var38 = rightRotate(var36, 8) ^ var37;
      int var39 = var1[2];
      int var40 = leftRotate(var37, 16) ^ var38;
      int var41 = var39 ^ var40;
      var1[2] = var41;
      int var42 = var1[3];
      int var43 = leftRotate(var38, 8);
      int var44 = var42 ^ var43;
      var1[3] = var44;
      int var45 = var1[2];
      int var46 = var3 + 2;
      int var47 = var2[var46];
      int var48 = var45 ^ var47;
      int var49 = var48 & 255;
      int var50 = this.sbox4(var49);
      int var51 = var48 >>> 8 & 255;
      int var52 = this.sbox3(var51) << 8;
      int var53 = var50 | var52;
      int var54 = var48 >>> 16 & 255;
      int var55 = this.sbox2(var54) << 16;
      int var56 = var53 | var55;
      byte[] var57 = SBOX1;
      int var58 = var48 >>> 24 & 255;
      int var59 = (var57[var58] & 255) << 24;
      int var60 = var56 | var59;
      int var61 = var1[3];
      int var62 = var3 + 3;
      int var63 = var2[var62];
      int var64 = var61 ^ var63;
      byte[] var65 = SBOX1;
      int var66 = var64 & 255;
      int var67 = var65[var66] & 255;
      int var68 = var64 >>> 8 & 255;
      int var69 = this.sbox4(var68) << 8;
      int var70 = var67 | var69;
      int var71 = var64 >>> 16 & 255;
      int var72 = this.sbox3(var71) << 16;
      int var73 = var70 | var72;
      int var74 = var64 >>> 24 & 255;
      int var75 = this.sbox2(var74) << 24;
      int var76 = leftRotate(var73 | var75, 8);
      int var77 = var60 ^ var76;
      int var78 = leftRotate(var76, 8) ^ var77;
      int var79 = rightRotate(var77, 8) ^ var78;
      int var80 = var1[0];
      int var81 = leftRotate(var78, 16) ^ var79;
      int var82 = var80 ^ var81;
      var1[0] = var82;
      int var83 = var1[1];
      int var84 = leftRotate(var79, 8);
      int var85 = var83 ^ var84;
      var1[1] = var85;
   }

   private void camelliaFLs(int[] var1, int[] var2, int var3) {
      int var4 = var1[1];
      int var5 = var1[0];
      int var6 = var3 + 0;
      int var7 = var2[var6];
      int var8 = leftRotate(var5 & var7, 1);
      int var9 = var4 ^ var8;
      var1[1] = var9;
      int var10 = var1[0];
      int var11 = var3 + 1;
      int var12 = var2[var11];
      int var13 = var1[1];
      int var14 = var12 | var13;
      int var15 = var10 ^ var14;
      var1[0] = var15;
      int var16 = var1[2];
      int var17 = var3 + 3;
      int var18 = var2[var17];
      int var19 = var1[3];
      int var20 = var18 | var19;
      int var21 = var16 ^ var20;
      var1[2] = var21;
      int var22 = var1[3];
      int var23 = var3 + 2;
      int var24 = var2[var23];
      int var25 = var1[2];
      int var26 = leftRotate(var24 & var25, 1);
      int var27 = var22 ^ var26;
      var1[3] = var27;
   }

   private static void decroldq(int var0, int[] var1, int var2, int[] var3, int var4) {
      int var5 = var4 + 2;
      int var6 = var2 + 0;
      int var7 = var1[var6] << var0;
      int var8 = var2 + 1;
      int var9 = var1[var8];
      int var10 = 32 - var0;
      int var11 = var9 >>> var10;
      int var12 = var7 | var11;
      var3[var5] = var12;
      int var13 = var4 + 3;
      int var14 = var2 + 1;
      int var15 = var1[var14] << var0;
      int var16 = var2 + 2;
      int var17 = var1[var16];
      int var18 = 32 - var0;
      int var19 = var17 >>> var18;
      int var20 = var15 | var19;
      var3[var13] = var20;
      int var21 = var4 + 0;
      int var22 = var2 + 2;
      int var23 = var1[var22] << var0;
      int var24 = var2 + 3;
      int var25 = var1[var24];
      int var26 = 32 - var0;
      int var27 = var25 >>> var26;
      int var28 = var23 | var27;
      var3[var21] = var28;
      int var29 = var4 + 1;
      int var30 = var2 + 3;
      int var31 = var1[var30] << var0;
      int var32 = var2 + 0;
      int var33 = var1[var32];
      int var34 = 32 - var0;
      int var35 = var33 >>> var34;
      int var36 = var31 | var35;
      var3[var29] = var36;
      int var37 = var2 + 0;
      int var38 = var4 + 2;
      int var39 = var3[var38];
      var1[var37] = var39;
      int var40 = var2 + 1;
      int var41 = var4 + 3;
      int var42 = var3[var41];
      var1[var40] = var42;
      int var43 = var2 + 2;
      int var44 = var4 + 0;
      int var45 = var3[var44];
      var1[var43] = var45;
      int var46 = var2 + 3;
      int var47 = var4 + 1;
      int var48 = var3[var47];
      var1[var46] = var48;
   }

   private static void decroldqo32(int var0, int[] var1, int var2, int[] var3, int var4) {
      int var5 = var4 + 2;
      int var6 = var2 + 1;
      int var7 = var1[var6];
      int var8 = var0 - 32;
      int var9 = var7 << var8;
      int var10 = var2 + 2;
      int var11 = var1[var10];
      int var12 = 64 - var0;
      int var13 = var11 >>> var12;
      int var14 = var9 | var13;
      var3[var5] = var14;
      int var15 = var4 + 3;
      int var16 = var2 + 2;
      int var17 = var1[var16];
      int var18 = var0 - 32;
      int var19 = var17 << var18;
      int var20 = var2 + 3;
      int var21 = var1[var20];
      int var22 = 64 - var0;
      int var23 = var21 >>> var22;
      int var24 = var19 | var23;
      var3[var15] = var24;
      int var25 = var4 + 0;
      int var26 = var2 + 3;
      int var27 = var1[var26];
      int var28 = var0 - 32;
      int var29 = var27 << var28;
      int var30 = var2 + 0;
      int var31 = var1[var30];
      int var32 = 64 - var0;
      int var33 = var31 >>> var32;
      int var34 = var29 | var33;
      var3[var25] = var34;
      int var35 = var4 + 1;
      int var36 = var2 + 0;
      int var37 = var1[var36];
      int var38 = var0 - 32;
      int var39 = var37 << var38;
      int var40 = var2 + 1;
      int var41 = var1[var40];
      int var42 = 64 - var0;
      int var43 = var41 >>> var42;
      int var44 = var39 | var43;
      var3[var35] = var44;
      int var45 = var2 + 0;
      int var46 = var4 + 2;
      int var47 = var3[var46];
      var1[var45] = var47;
      int var48 = var2 + 1;
      int var49 = var4 + 3;
      int var50 = var3[var49];
      var1[var48] = var50;
      int var51 = var2 + 2;
      int var52 = var4 + 0;
      int var53 = var3[var52];
      var1[var51] = var53;
      int var54 = var2 + 3;
      int var55 = var4 + 1;
      int var56 = var3[var55];
      var1[var54] = var56;
   }

   private void int2bytes(int var1, byte[] var2, int var3) {
      for(int var4 = 0; var4 < 4; ++var4) {
         int var5 = 3 - var4 + var3;
         byte var6 = (byte)var1;
         var2[var5] = var6;
         var1 >>>= 8;
      }

   }

   private byte lRot8(byte var1, int var2) {
      int var3 = var1 << var2;
      int var4 = var1 & 255;
      int var5 = 8 - var2;
      int var6 = var4 >>> var5;
      return (byte)(var3 | var6);
   }

   private static int leftRotate(int var0, int var1) {
      int var2 = var0 << var1;
      int var3 = 32 - var1;
      int var4 = var0 >>> var3;
      return var2 + var4;
   }

   private int processBlock128(byte[] var1, int var2, byte[] var3, int var4) {
      for(int var5 = 0; var5 < 4; ++var5) {
         int[] var6 = this.state;
         int var7 = var5 * 4 + var2;
         int var8 = this.bytes2int(var1, var7);
         var6[var5] = var8;
         int[] var9 = this.state;
         int var10 = var9[var5];
         int var11 = this.kw[var5];
         int var12 = var10 ^ var11;
         var9[var5] = var12;
      }

      int[] var13 = this.state;
      int[] var14 = this.subkey;
      this.camelliaF2(var13, var14, 0);
      int[] var15 = this.state;
      int[] var16 = this.subkey;
      this.camelliaF2(var15, var16, 4);
      int[] var17 = this.state;
      int[] var18 = this.subkey;
      this.camelliaF2(var17, var18, 8);
      int[] var19 = this.state;
      int[] var20 = this.ke;
      this.camelliaFLs(var19, var20, 0);
      int[] var21 = this.state;
      int[] var22 = this.subkey;
      this.camelliaF2(var21, var22, 12);
      int[] var23 = this.state;
      int[] var24 = this.subkey;
      this.camelliaF2(var23, var24, 16);
      int[] var25 = this.state;
      int[] var26 = this.subkey;
      this.camelliaF2(var25, var26, 20);
      int[] var27 = this.state;
      int[] var28 = this.ke;
      this.camelliaFLs(var27, var28, 4);
      int[] var29 = this.state;
      int[] var30 = this.subkey;
      this.camelliaF2(var29, var30, 24);
      int[] var31 = this.state;
      int[] var32 = this.subkey;
      this.camelliaF2(var31, var32, 28);
      int[] var33 = this.state;
      int[] var34 = this.subkey;
      this.camelliaF2(var33, var34, 32);
      int[] var35 = this.state;
      int var36 = var35[2];
      int var37 = this.kw[4];
      int var38 = var36 ^ var37;
      var35[2] = var38;
      int[] var39 = this.state;
      int var40 = var39[3];
      int var41 = this.kw[5];
      int var42 = var40 ^ var41;
      var39[3] = var42;
      int[] var43 = this.state;
      int var44 = var43[0];
      int var45 = this.kw[6];
      int var46 = var44 ^ var45;
      var43[0] = var46;
      int[] var47 = this.state;
      int var48 = var47[1];
      int var49 = this.kw[7];
      int var50 = var48 ^ var49;
      var47[1] = var50;
      int var51 = this.state[2];
      this.int2bytes(var51, var3, var4);
      int var52 = this.state[3];
      int var53 = var4 + 4;
      this.int2bytes(var52, var3, var53);
      int var54 = this.state[0];
      int var55 = var4 + 8;
      this.int2bytes(var54, var3, var55);
      int var56 = this.state[1];
      int var57 = var4 + 12;
      this.int2bytes(var56, var3, var57);
      return 16;
   }

   private int processBlock192or256(byte[] var1, int var2, byte[] var3, int var4) {
      for(int var5 = 0; var5 < 4; ++var5) {
         int[] var6 = this.state;
         int var7 = var5 * 4 + var2;
         int var8 = this.bytes2int(var1, var7);
         var6[var5] = var8;
         int[] var9 = this.state;
         int var10 = var9[var5];
         int var11 = this.kw[var5];
         int var12 = var10 ^ var11;
         var9[var5] = var12;
      }

      int[] var13 = this.state;
      int[] var14 = this.subkey;
      this.camelliaF2(var13, var14, 0);
      int[] var15 = this.state;
      int[] var16 = this.subkey;
      this.camelliaF2(var15, var16, 4);
      int[] var17 = this.state;
      int[] var18 = this.subkey;
      this.camelliaF2(var17, var18, 8);
      int[] var19 = this.state;
      int[] var20 = this.ke;
      this.camelliaFLs(var19, var20, 0);
      int[] var21 = this.state;
      int[] var22 = this.subkey;
      this.camelliaF2(var21, var22, 12);
      int[] var23 = this.state;
      int[] var24 = this.subkey;
      this.camelliaF2(var23, var24, 16);
      int[] var25 = this.state;
      int[] var26 = this.subkey;
      this.camelliaF2(var25, var26, 20);
      int[] var27 = this.state;
      int[] var28 = this.ke;
      this.camelliaFLs(var27, var28, 4);
      int[] var29 = this.state;
      int[] var30 = this.subkey;
      this.camelliaF2(var29, var30, 24);
      int[] var31 = this.state;
      int[] var32 = this.subkey;
      this.camelliaF2(var31, var32, 28);
      int[] var33 = this.state;
      int[] var34 = this.subkey;
      this.camelliaF2(var33, var34, 32);
      int[] var35 = this.state;
      int[] var36 = this.ke;
      this.camelliaFLs(var35, var36, 8);
      int[] var37 = this.state;
      int[] var38 = this.subkey;
      this.camelliaF2(var37, var38, 36);
      int[] var39 = this.state;
      int[] var40 = this.subkey;
      this.camelliaF2(var39, var40, 40);
      int[] var41 = this.state;
      int[] var42 = this.subkey;
      this.camelliaF2(var41, var42, 44);
      int[] var43 = this.state;
      int var44 = var43[2];
      int var45 = this.kw[4];
      int var46 = var44 ^ var45;
      var43[2] = var46;
      int[] var47 = this.state;
      int var48 = var47[3];
      int var49 = this.kw[5];
      int var50 = var48 ^ var49;
      var47[3] = var50;
      int[] var51 = this.state;
      int var52 = var51[0];
      int var53 = this.kw[6];
      int var54 = var52 ^ var53;
      var51[0] = var54;
      int[] var55 = this.state;
      int var56 = var55[1];
      int var57 = this.kw[7];
      int var58 = var56 ^ var57;
      var55[1] = var58;
      int var59 = this.state[2];
      this.int2bytes(var59, var3, var4);
      int var60 = this.state[3];
      int var61 = var4 + 4;
      this.int2bytes(var60, var3, var61);
      int var62 = this.state[0];
      int var63 = var4 + 8;
      this.int2bytes(var62, var3, var63);
      int var64 = this.state[1];
      int var65 = var4 + 12;
      this.int2bytes(var64, var3, var65);
      return 16;
   }

   private static int rightRotate(int var0, int var1) {
      int var2 = var0 >>> var1;
      int var3 = 32 - var1;
      int var4 = var0 << var3;
      return var2 + var4;
   }

   private static void roldq(int var0, int[] var1, int var2, int[] var3, int var4) {
      int var5 = var4 + 0;
      int var6 = var2 + 0;
      int var7 = var1[var6] << var0;
      int var8 = var2 + 1;
      int var9 = var1[var8];
      int var10 = 32 - var0;
      int var11 = var9 >>> var10;
      int var12 = var7 | var11;
      var3[var5] = var12;
      int var13 = var4 + 1;
      int var14 = var2 + 1;
      int var15 = var1[var14] << var0;
      int var16 = var2 + 2;
      int var17 = var1[var16];
      int var18 = 32 - var0;
      int var19 = var17 >>> var18;
      int var20 = var15 | var19;
      var3[var13] = var20;
      int var21 = var4 + 2;
      int var22 = var2 + 2;
      int var23 = var1[var22] << var0;
      int var24 = var2 + 3;
      int var25 = var1[var24];
      int var26 = 32 - var0;
      int var27 = var25 >>> var26;
      int var28 = var23 | var27;
      var3[var21] = var28;
      int var29 = var4 + 3;
      int var30 = var2 + 3;
      int var31 = var1[var30] << var0;
      int var32 = var2 + 0;
      int var33 = var1[var32];
      int var34 = 32 - var0;
      int var35 = var33 >>> var34;
      int var36 = var31 | var35;
      var3[var29] = var36;
      int var37 = var2 + 0;
      int var38 = var4 + 0;
      int var39 = var3[var38];
      var1[var37] = var39;
      int var40 = var2 + 1;
      int var41 = var4 + 1;
      int var42 = var3[var41];
      var1[var40] = var42;
      int var43 = var2 + 2;
      int var44 = var4 + 2;
      int var45 = var3[var44];
      var1[var43] = var45;
      int var46 = var2 + 3;
      int var47 = var4 + 3;
      int var48 = var3[var47];
      var1[var46] = var48;
   }

   private static void roldqo32(int var0, int[] var1, int var2, int[] var3, int var4) {
      int var5 = var4 + 0;
      int var6 = var2 + 1;
      int var7 = var1[var6];
      int var8 = var0 - 32;
      int var9 = var7 << var8;
      int var10 = var2 + 2;
      int var11 = var1[var10];
      int var12 = 64 - var0;
      int var13 = var11 >>> var12;
      int var14 = var9 | var13;
      var3[var5] = var14;
      int var15 = var4 + 1;
      int var16 = var2 + 2;
      int var17 = var1[var16];
      int var18 = var0 - 32;
      int var19 = var17 << var18;
      int var20 = var2 + 3;
      int var21 = var1[var20];
      int var22 = 64 - var0;
      int var23 = var21 >>> var22;
      int var24 = var19 | var23;
      var3[var15] = var24;
      int var25 = var4 + 2;
      int var26 = var2 + 3;
      int var27 = var1[var26];
      int var28 = var0 - 32;
      int var29 = var27 << var28;
      int var30 = var2 + 0;
      int var31 = var1[var30];
      int var32 = 64 - var0;
      int var33 = var31 >>> var32;
      int var34 = var29 | var33;
      var3[var25] = var34;
      int var35 = var4 + 3;
      int var36 = var2 + 0;
      int var37 = var1[var36];
      int var38 = var0 - 32;
      int var39 = var37 << var38;
      int var40 = var2 + 1;
      int var41 = var1[var40];
      int var42 = 64 - var0;
      int var43 = var41 >>> var42;
      int var44 = var39 | var43;
      var3[var35] = var44;
      int var45 = var2 + 0;
      int var46 = var4 + 0;
      int var47 = var3[var46];
      var1[var45] = var47;
      int var48 = var2 + 1;
      int var49 = var4 + 1;
      int var50 = var3[var49];
      var1[var48] = var50;
      int var51 = var2 + 2;
      int var52 = var4 + 2;
      int var53 = var3[var52];
      var1[var51] = var53;
      int var54 = var2 + 3;
      int var55 = var4 + 3;
      int var56 = var3[var55];
      var1[var54] = var56;
   }

   private int sbox2(int var1) {
      byte var2 = SBOX1[var1];
      return this.lRot8(var2, 1) & 255;
   }

   private int sbox3(int var1) {
      byte var2 = SBOX1[var1];
      return this.lRot8(var2, 7) & 255;
   }

   private int sbox4(int var1) {
      byte[] var2 = SBOX1;
      byte var3 = (byte)var1;
      int var4 = this.lRot8(var3, 1) & 255;
      return var2[var4] & 255;
   }

   private void setKey(boolean var1, byte[] var2) {
      int[] var3 = new int[8];
      int[] var4 = new int[4];
      int[] var5 = new int[4];
      int[] var6 = new int[4];
      switch(var2.length) {
      case 16:
         this._keyis128 = (boolean)1;
         int var7 = this.bytes2int(var2, 0);
         var3[0] = var7;
         int var8 = this.bytes2int(var2, 4);
         var3[1] = var8;
         int var9 = this.bytes2int(var2, 8);
         var3[2] = var9;
         int var10 = this.bytes2int(var2, 12);
         var3[3] = var10;
         var3[7] = 0;
         var3[6] = 0;
         var3[5] = 0;
         var3[4] = 0;
         break;
      case 24:
         int var16 = this.bytes2int(var2, 0);
         var3[0] = var16;
         int var17 = this.bytes2int(var2, 4);
         var3[1] = var17;
         int var18 = this.bytes2int(var2, 8);
         var3[2] = var18;
         int var19 = this.bytes2int(var2, 12);
         var3[3] = var19;
         int var20 = this.bytes2int(var2, 16);
         var3[4] = var20;
         int var21 = this.bytes2int(var2, 20);
         var3[5] = var21;
         int var22 = ~var3[4];
         var3[6] = var22;
         int var23 = ~var3[5];
         var3[7] = var23;
         this._keyis128 = (boolean)0;
         break;
      case 32:
         int var24 = this.bytes2int(var2, 0);
         var3[0] = var24;
         int var25 = this.bytes2int(var2, 4);
         var3[1] = var25;
         int var26 = this.bytes2int(var2, 8);
         var3[2] = var26;
         int var27 = this.bytes2int(var2, 12);
         var3[3] = var27;
         int var28 = this.bytes2int(var2, 16);
         var3[4] = var28;
         int var29 = this.bytes2int(var2, 20);
         var3[5] = var29;
         int var30 = this.bytes2int(var2, 24);
         var3[6] = var30;
         int var31 = this.bytes2int(var2, 28);
         var3[7] = var31;
         this._keyis128 = (boolean)0;
         break;
      default:
         throw new IllegalArgumentException("key sizes are only 16/24/32 bytes.");
      }

      for(int var11 = 0; var11 < 4; ++var11) {
         int var12 = var3[var11];
         int var13 = var11 + 4;
         int var14 = var3[var13];
         int var15 = var12 ^ var14;
         var4[var11] = var15;
      }

      int[] var32 = SIGMA;
      this.camelliaF2(var4, var32, 0);

      for(int var33 = 0; var33 < 4; ++var33) {
         int var34 = var4[var33];
         int var35 = var3[var33];
         int var36 = var34 ^ var35;
         var4[var33] = var36;
      }

      int[] var37 = SIGMA;
      this.camelliaF2(var4, var37, 4);
      if(this._keyis128) {
         if(var1) {
            int[] var38 = this.kw;
            int var39 = var3[0];
            var38[0] = var39;
            int[] var40 = this.kw;
            int var41 = var3[1];
            var40[1] = var41;
            int[] var42 = this.kw;
            int var43 = var3[2];
            var42[2] = var43;
            int[] var44 = this.kw;
            int var45 = var3[3];
            var44[3] = var45;
            int[] var46 = this.subkey;
            roldq(15, var3, 0, var46, 4);
            int[] var47 = this.subkey;
            roldq(30, var3, 0, var47, 12);
            roldq(15, var3, 0, var6, 0);
            int[] var48 = this.subkey;
            int var49 = var6[2];
            var48[18] = var49;
            int[] var50 = this.subkey;
            int var51 = var6[3];
            var50[19] = var51;
            int[] var52 = this.ke;
            roldq(17, var3, 0, var52, 4);
            int[] var53 = this.subkey;
            roldq(17, var3, 0, var53, 24);
            int[] var54 = this.subkey;
            roldq(17, var3, 0, var54, 32);
            int[] var55 = this.subkey;
            int var56 = var4[0];
            var55[0] = var56;
            int[] var57 = this.subkey;
            int var58 = var4[1];
            var57[1] = var58;
            int[] var59 = this.subkey;
            int var60 = var4[2];
            var59[2] = var60;
            int[] var61 = this.subkey;
            int var62 = var4[3];
            var61[3] = var62;
            int[] var63 = this.subkey;
            roldq(15, var4, 0, var63, 8);
            int[] var64 = this.ke;
            roldq(15, var4, 0, var64, 0);
            roldq(15, var4, 0, var6, 0);
            int[] var65 = this.subkey;
            int var66 = var6[0];
            var65[16] = var66;
            int[] var67 = this.subkey;
            int var68 = var6[1];
            var67[17] = var68;
            int[] var69 = this.subkey;
            roldq(15, var4, 0, var69, 20);
            int[] var70 = this.subkey;
            roldqo32(34, var4, 0, var70, 28);
            int[] var71 = this.kw;
            roldq(17, var4, 0, var71, 4);
         } else {
            int[] var72 = this.kw;
            int var73 = var3[0];
            var72[4] = var73;
            int[] var74 = this.kw;
            int var75 = var3[1];
            var74[5] = var75;
            int[] var76 = this.kw;
            int var77 = var3[2];
            var76[6] = var77;
            int[] var78 = this.kw;
            int var79 = var3[3];
            var78[7] = var79;
            int[] var80 = this.subkey;
            decroldq(15, var3, 0, var80, 28);
            int[] var81 = this.subkey;
            decroldq(30, var3, 0, var81, 20);
            decroldq(15, var3, 0, var6, 0);
            int[] var82 = this.subkey;
            int var83 = var6[0];
            var82[16] = var83;
            int[] var84 = this.subkey;
            int var85 = var6[1];
            var84[17] = var85;
            int[] var86 = this.ke;
            decroldq(17, var3, 0, var86, 0);
            int[] var87 = this.subkey;
            decroldq(17, var3, 0, var87, 8);
            int[] var88 = this.subkey;
            decroldq(17, var3, 0, var88, 0);
            int[] var89 = this.subkey;
            int var90 = var4[0];
            var89[34] = var90;
            int[] var91 = this.subkey;
            int var92 = var4[1];
            var91[35] = var92;
            int[] var93 = this.subkey;
            int var94 = var4[2];
            var93[32] = var94;
            int[] var95 = this.subkey;
            int var96 = var4[3];
            var95[33] = var96;
            int[] var97 = this.subkey;
            decroldq(15, var4, 0, var97, 24);
            int[] var98 = this.ke;
            decroldq(15, var4, 0, var98, 4);
            decroldq(15, var4, 0, var6, 0);
            int[] var99 = this.subkey;
            int var100 = var6[2];
            var99[18] = var100;
            int[] var101 = this.subkey;
            int var102 = var6[3];
            var101[19] = var102;
            int[] var103 = this.subkey;
            decroldq(15, var4, 0, var103, 12);
            int[] var104 = this.subkey;
            decroldqo32(34, var4, 0, var104, 4);
            int[] var105 = this.kw;
            roldq(17, var4, 0, var105, 0);
         }
      } else {
         int var110;
         for(byte var188 = 0; var188 < 4; var110 = var188 + 1) {
            int var106 = var4[var188];
            int var107 = var188 + 4;
            int var108 = var3[var107];
            int var109 = var106 ^ var108;
            var5[var188] = var109;
         }

         int[] var111 = SIGMA;
         this.camelliaF2(var5, var111, 8);
         if(var1) {
            int[] var112 = this.kw;
            int var113 = var3[0];
            var112[0] = var113;
            int[] var114 = this.kw;
            int var115 = var3[1];
            var114[1] = var115;
            int[] var116 = this.kw;
            int var117 = var3[2];
            var116[2] = var117;
            int[] var118 = this.kw;
            int var119 = var3[3];
            var118[3] = var119;
            int[] var120 = this.subkey;
            roldqo32(45, var3, 0, var120, 16);
            int[] var121 = this.ke;
            roldq(15, var3, 0, var121, 4);
            int[] var122 = this.subkey;
            roldq(17, var3, 0, var122, 32);
            int[] var123 = this.subkey;
            roldqo32(34, var3, 0, var123, 44);
            int[] var124 = this.subkey;
            roldq(15, var3, 4, var124, 4);
            int[] var125 = this.ke;
            roldq(15, var3, 4, var125, 0);
            int[] var126 = this.subkey;
            roldq(30, var3, 4, var126, 24);
            int[] var127 = this.subkey;
            roldqo32(34, var3, 4, var127, 36);
            int[] var128 = this.subkey;
            roldq(15, var4, 0, var128, 8);
            int[] var129 = this.subkey;
            roldq(30, var4, 0, var129, 20);
            int[] var130 = this.ke;
            int var131 = var4[1];
            var130[8] = var131;
            int[] var132 = this.ke;
            int var133 = var4[2];
            var132[9] = var133;
            int[] var134 = this.ke;
            int var135 = var4[3];
            var134[10] = var135;
            int[] var136 = this.ke;
            int var137 = var4[0];
            var136[11] = var137;
            int[] var138 = this.subkey;
            roldqo32(49, var4, 0, var138, 40);
            int[] var139 = this.subkey;
            int var140 = var5[0];
            var139[0] = var140;
            int[] var141 = this.subkey;
            int var142 = var5[1];
            var141[1] = var142;
            int[] var143 = this.subkey;
            int var144 = var5[2];
            var143[2] = var144;
            int[] var145 = this.subkey;
            int var146 = var5[3];
            var145[3] = var146;
            int[] var147 = this.subkey;
            roldq(30, var5, 0, var147, 12);
            int[] var148 = this.subkey;
            roldq(30, var5, 0, var148, 28);
            int[] var149 = this.kw;
            roldqo32(51, var5, 0, var149, 4);
         } else {
            int[] var150 = this.kw;
            int var151 = var3[0];
            var150[4] = var151;
            int[] var152 = this.kw;
            int var153 = var3[1];
            var152[5] = var153;
            int[] var154 = this.kw;
            int var155 = var3[2];
            var154[6] = var155;
            int[] var156 = this.kw;
            int var157 = var3[3];
            var156[7] = var157;
            int[] var158 = this.subkey;
            decroldqo32(45, var3, 0, var158, 28);
            int[] var159 = this.ke;
            decroldq(15, var3, 0, var159, 4);
            int[] var160 = this.subkey;
            decroldq(17, var3, 0, var160, 12);
            int[] var161 = this.subkey;
            decroldqo32(34, var3, 0, var161, 0);
            int[] var162 = this.subkey;
            decroldq(15, var3, 4, var162, 40);
            int[] var163 = this.ke;
            decroldq(15, var3, 4, var163, 8);
            int[] var164 = this.subkey;
            decroldq(30, var3, 4, var164, 20);
            int[] var165 = this.subkey;
            decroldqo32(34, var3, 4, var165, 8);
            int[] var166 = this.subkey;
            decroldq(15, var4, 0, var166, 36);
            int[] var167 = this.subkey;
            decroldq(30, var4, 0, var167, 24);
            int[] var168 = this.ke;
            int var169 = var4[1];
            var168[2] = var169;
            int[] var170 = this.ke;
            int var171 = var4[2];
            var170[3] = var171;
            int[] var172 = this.ke;
            int var173 = var4[3];
            var172[0] = var173;
            int[] var174 = this.ke;
            int var175 = var4[0];
            var174[1] = var175;
            int[] var176 = this.subkey;
            decroldqo32(49, var4, 0, var176, 4);
            int[] var177 = this.subkey;
            int var178 = var5[0];
            var177[46] = var178;
            int[] var179 = this.subkey;
            int var180 = var5[1];
            var179[47] = var180;
            int[] var181 = this.subkey;
            int var182 = var5[2];
            var181[44] = var182;
            int[] var183 = this.subkey;
            int var184 = var5[3];
            var183[45] = var184;
            int[] var185 = this.subkey;
            decroldq(30, var5, 0, var185, 32);
            int[] var186 = this.subkey;
            decroldq(30, var5, 0, var186, 16);
            int[] var187 = this.kw;
            roldqo32(51, var5, 0, var187, 0);
         }
      }
   }

   public String getAlgorithmName() {
      return "Camellia";
   }

   public int getBlockSize() {
      return 16;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(!(var2 instanceof KeyParameter)) {
         throw new IllegalArgumentException("only simple KeyParameter expected.");
      } else {
         byte[] var3 = ((KeyParameter)var2).getKey();
         this.setKey(var1, var3);
         this.initialized = (boolean)1;
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws IllegalStateException {
      if(!this.initialized) {
         throw new IllegalStateException("Camellia is not initialized");
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
               int var9;
               if(this._keyis128) {
                  var9 = this.processBlock128(var1, var2, var3, var4);
               } else {
                  var9 = this.processBlock192or256(var1, var2, var3, var4);
               }

               return var9;
            }
         }
      }
   }

   public void reset() {}
}
