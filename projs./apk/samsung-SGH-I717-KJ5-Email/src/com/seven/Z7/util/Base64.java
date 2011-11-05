package com.seven.Z7.util;

import java.io.IOException;
import java.io.OutputStream;

public class Base64 {

   private static final byte[] decodingTable = new byte[128];
   private static final byte[] encodingTable = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)43, (byte)47};


   static {
      for(int var0 = 65; var0 <= 90; ++var0) {
         byte[] var1 = decodingTable;
         byte var2 = (byte)(var0 - 65);
         var1[var0] = var2;
      }

      for(int var3 = 97; var3 <= 122; ++var3) {
         byte[] var4 = decodingTable;
         byte var5 = (byte)(var3 - 97 + 26);
         var4[var3] = var5;
      }

      for(int var6 = 48; var6 <= 57; ++var6) {
         byte[] var7 = decodingTable;
         byte var8 = (byte)(var6 - 48 + 52);
         var7[var6] = var8;
      }

      decodingTable[43] = 62;
      decodingTable[47] = 63;
   }

   public Base64() {}

   public static int decode(String var0, OutputStream var1) throws IOException {
      int var2 = var0.length() - 2;
      int var3;
      if(var0.charAt(var2) == 61) {
         var3 = (var0.length() / 4 - 1) * 3 + 1;
      } else {
         int var29 = var0.length() - 1;
         if(var0.charAt(var29) == 61) {
            var3 = (var0.length() / 4 - 1) * 3 + 2;
         } else {
            var3 = var0.length() / 4 * 3;
         }
      }

      int var4 = 0;
      int var5 = 0;

      while(true) {
         int var6 = var0.length() - 4;
         if(var4 >= var6) {
            int var30 = var0.length() - 2;
            if(var0.charAt(var30) == 61) {
               byte[] var31 = decodingTable;
               int var32 = var0.length() - 4;
               char var33 = var0.charAt(var32);
               byte var34 = var31[var33];
               byte[] var35 = decodingTable;
               int var36 = var0.length() - 3;
               char var37 = var0.charAt(var36);
               byte var38 = var35[var37];
               int var39 = var34 << 2;
               int var40 = var38 >> 4;
               int var41 = var39 | var40;
               var1.write(var41);
            } else {
               int var42 = var0.length() - 1;
               if(var0.charAt(var42) == 61) {
                  byte[] var43 = decodingTable;
                  int var44 = var0.length() - 4;
                  char var45 = var0.charAt(var44);
                  byte var46 = var43[var45];
                  byte[] var47 = decodingTable;
                  int var48 = var0.length() - 3;
                  char var49 = var0.charAt(var48);
                  byte var50 = var47[var49];
                  byte[] var51 = decodingTable;
                  int var52 = var0.length() - 2;
                  char var53 = var0.charAt(var52);
                  byte var54 = var51[var53];
                  int var55 = var46 << 2;
                  int var56 = var50 >> 4;
                  int var57 = var55 | var56;
                  var1.write(var57);
                  int var58 = var50 << 4;
                  int var59 = var54 >> 2;
                  int var60 = var58 | var59;
                  var1.write(var60);
               } else {
                  byte[] var61 = decodingTable;
                  int var62 = var0.length() - 4;
                  char var63 = var0.charAt(var62);
                  byte var64 = var61[var63];
                  byte[] var65 = decodingTable;
                  int var66 = var0.length() - 3;
                  char var67 = var0.charAt(var66);
                  byte var68 = var65[var67];
                  byte[] var69 = decodingTable;
                  int var70 = var0.length() - 2;
                  char var71 = var0.charAt(var70);
                  byte var72 = var69[var71];
                  byte[] var73 = decodingTable;
                  int var74 = var0.length() - 1;
                  char var75 = var0.charAt(var74);
                  byte var76 = var73[var75];
                  int var77 = var64 << 2;
                  int var78 = var68 >> 4;
                  int var79 = var77 | var78;
                  var1.write(var79);
                  int var80 = var68 << 4;
                  int var81 = var72 >> 2;
                  int var82 = var80 | var81;
                  var1.write(var82);
                  int var83 = var72 << 6 | var76;
                  var1.write(var83);
               }
            }

            return var3;
         }

         byte[] var7 = decodingTable;
         char var8 = var0.charAt(var4);
         byte var9 = var7[var8];
         byte[] var10 = decodingTable;
         int var11 = var4 + 1;
         char var12 = var0.charAt(var11);
         byte var13 = var10[var12];
         byte[] var14 = decodingTable;
         int var15 = var4 + 2;
         char var16 = var0.charAt(var15);
         byte var17 = var14[var16];
         byte[] var18 = decodingTable;
         int var19 = var4 + 3;
         char var20 = var0.charAt(var19);
         byte var21 = var18[var20];
         int var22 = var9 << 2;
         int var23 = var13 >> 4;
         int var24 = var22 | var23;
         var1.write(var24);
         int var25 = var13 << 4;
         int var26 = var17 >> 2;
         int var27 = var25 | var26;
         var1.write(var27);
         int var28 = var17 << 6 | var21;
         var1.write(var28);
         var4 += 4;
         var5 += 3;
      }
   }

   public static byte[] decode(String var0) {
      int var1 = var0.length() - 2;
      byte[] var2;
      if(var0.charAt(var1) == 61) {
         var2 = new byte[(var0.length() / 4 - 1) * 3 + 1];
      } else {
         int var30 = var0.length() - 1;
         if(var0.charAt(var30) == 61) {
            var2 = new byte[(var0.length() / 4 - 1) * 3 + 2];
         } else {
            var2 = new byte[var0.length() / 4 * 3];
         }
      }

      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = var0.length() - 4;
         if(var3 >= var5) {
            int var31 = var0.length() - 2;
            if(var0.charAt(var31) == 61) {
               byte[] var32 = decodingTable;
               int var33 = var0.length() - 4;
               char var34 = var0.charAt(var33);
               byte var35 = var32[var34];
               byte[] var36 = decodingTable;
               int var37 = var0.length() - 3;
               char var38 = var0.charAt(var37);
               byte var39 = var36[var38];
               int var40 = var2.length - 1;
               int var41 = var35 << 2;
               int var42 = var39 >> 4;
               byte var43 = (byte)(var41 | var42);
               var2[var40] = var43;
            } else {
               int var44 = var0.length() - 1;
               if(var0.charAt(var44) == 61) {
                  byte[] var45 = decodingTable;
                  int var46 = var0.length() - 4;
                  char var47 = var0.charAt(var46);
                  byte var48 = var45[var47];
                  byte[] var49 = decodingTable;
                  int var50 = var0.length() - 3;
                  char var51 = var0.charAt(var50);
                  byte var52 = var49[var51];
                  byte[] var53 = decodingTable;
                  int var54 = var0.length() - 2;
                  char var55 = var0.charAt(var54);
                  byte var56 = var53[var55];
                  int var57 = var2.length - 2;
                  int var58 = var48 << 2;
                  int var59 = var52 >> 4;
                  byte var60 = (byte)(var58 | var59);
                  var2[var57] = var60;
                  int var61 = var2.length - 1;
                  int var62 = var52 << 4;
                  int var63 = var56 >> 2;
                  byte var64 = (byte)(var62 | var63);
                  var2[var61] = var64;
               } else {
                  byte[] var65 = decodingTable;
                  int var66 = var0.length() - 4;
                  char var67 = var0.charAt(var66);
                  byte var68 = var65[var67];
                  byte[] var69 = decodingTable;
                  int var70 = var0.length() - 3;
                  char var71 = var0.charAt(var70);
                  byte var72 = var69[var71];
                  byte[] var73 = decodingTable;
                  int var74 = var0.length() - 2;
                  char var75 = var0.charAt(var74);
                  byte var76 = var73[var75];
                  byte[] var77 = decodingTable;
                  int var78 = var0.length() - 1;
                  char var79 = var0.charAt(var78);
                  byte var80 = var77[var79];
                  int var81 = var2.length - 3;
                  int var82 = var68 << 2;
                  int var83 = var72 >> 4;
                  byte var84 = (byte)(var82 | var83);
                  var2[var81] = var84;
                  int var85 = var2.length - 2;
                  int var86 = var72 << 4;
                  int var87 = var76 >> 2;
                  byte var88 = (byte)(var86 | var87);
                  var2[var85] = var88;
                  int var89 = var2.length - 1;
                  byte var90 = (byte)(var76 << 6 | var80);
                  var2[var89] = var90;
               }
            }

            return var2;
         }

         byte[] var6 = decodingTable;
         char var7 = var0.charAt(var3);
         byte var8 = var6[var7];
         byte[] var9 = decodingTable;
         int var10 = var3 + 1;
         char var11 = var0.charAt(var10);
         byte var12 = var9[var11];
         byte[] var13 = decodingTable;
         int var14 = var3 + 2;
         char var15 = var0.charAt(var14);
         byte var16 = var13[var15];
         byte[] var17 = decodingTable;
         int var18 = var3 + 3;
         char var19 = var0.charAt(var18);
         byte var20 = var17[var19];
         int var21 = var8 << 2;
         int var22 = var12 >> 4;
         byte var23 = (byte)(var21 | var22);
         var2[var4] = var23;
         int var24 = var4 + 1;
         int var25 = var12 << 4;
         int var26 = var16 >> 2;
         byte var27 = (byte)(var25 | var26);
         var2[var24] = var27;
         int var28 = var4 + 2;
         byte var29 = (byte)(var16 << 6 | var20);
         var2[var28] = var29;
         var3 += 4;
         var4 += 3;
      }
   }

   public static byte[] decode(byte[] var0) {
      int var1 = var0.length - 2;
      byte[] var2;
      if(var0[var1] == 61) {
         var2 = new byte[(var0.length / 4 - 1) * 3 + 1];
      } else {
         int var30 = var0.length - 1;
         if(var0[var30] == 61) {
            var2 = new byte[(var0.length / 4 - 1) * 3 + 2];
         } else {
            var2 = new byte[var0.length / 4 * 3];
         }
      }

      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = var0.length - 4;
         if(var3 >= var5) {
            int var31 = var0.length - 2;
            if(var0[var31] == 61) {
               byte[] var32 = decodingTable;
               int var33 = var0.length - 4;
               byte var34 = var0[var33];
               byte var35 = var32[var34];
               byte[] var36 = decodingTable;
               int var37 = var0.length - 3;
               byte var38 = var0[var37];
               byte var39 = var36[var38];
               int var40 = var2.length - 1;
               int var41 = var35 << 2;
               int var42 = var39 >> 4;
               byte var43 = (byte)(var41 | var42);
               var2[var40] = var43;
            } else {
               int var44 = var0.length - 1;
               if(var0[var44] == 61) {
                  byte[] var45 = decodingTable;
                  int var46 = var0.length - 4;
                  byte var47 = var0[var46];
                  byte var48 = var45[var47];
                  byte[] var49 = decodingTable;
                  int var50 = var0.length - 3;
                  byte var51 = var0[var50];
                  byte var52 = var49[var51];
                  byte[] var53 = decodingTable;
                  int var54 = var0.length - 2;
                  byte var55 = var0[var54];
                  byte var56 = var53[var55];
                  int var57 = var2.length - 2;
                  int var58 = var48 << 2;
                  int var59 = var52 >> 4;
                  byte var60 = (byte)(var58 | var59);
                  var2[var57] = var60;
                  int var61 = var2.length - 1;
                  int var62 = var52 << 4;
                  int var63 = var56 >> 2;
                  byte var64 = (byte)(var62 | var63);
                  var2[var61] = var64;
               } else {
                  byte[] var65 = decodingTable;
                  int var66 = var0.length - 4;
                  byte var67 = var0[var66];
                  byte var68 = var65[var67];
                  byte[] var69 = decodingTable;
                  int var70 = var0.length - 3;
                  byte var71 = var0[var70];
                  byte var72 = var69[var71];
                  byte[] var73 = decodingTable;
                  int var74 = var0.length - 2;
                  byte var75 = var0[var74];
                  byte var76 = var73[var75];
                  byte[] var77 = decodingTable;
                  int var78 = var0.length - 1;
                  byte var79 = var0[var78];
                  byte var80 = var77[var79];
                  int var81 = var2.length - 3;
                  int var82 = var68 << 2;
                  int var83 = var72 >> 4;
                  byte var84 = (byte)(var82 | var83);
                  var2[var81] = var84;
                  int var85 = var2.length - 2;
                  int var86 = var72 << 4;
                  int var87 = var76 >> 2;
                  byte var88 = (byte)(var86 | var87);
                  var2[var85] = var88;
                  int var89 = var2.length - 1;
                  byte var90 = (byte)(var76 << 6 | var80);
                  var2[var89] = var90;
               }
            }

            return var2;
         }

         byte[] var6 = decodingTable;
         byte var7 = var0[var3];
         byte var8 = var6[var7];
         byte[] var9 = decodingTable;
         int var10 = var3 + 1;
         byte var11 = var0[var10];
         byte var12 = var9[var11];
         byte[] var13 = decodingTable;
         int var14 = var3 + 2;
         byte var15 = var0[var14];
         byte var16 = var13[var15];
         byte[] var17 = decodingTable;
         int var18 = var3 + 3;
         byte var19 = var0[var18];
         byte var20 = var17[var19];
         int var21 = var8 << 2;
         int var22 = var12 >> 4;
         byte var23 = (byte)(var21 | var22);
         var2[var4] = var23;
         int var24 = var4 + 1;
         int var25 = var12 << 4;
         int var26 = var16 >> 2;
         byte var27 = (byte)(var25 | var26);
         var2[var24] = var27;
         int var28 = var4 + 2;
         byte var29 = (byte)(var16 << 6 | var20);
         var2[var28] = var29;
         var3 += 4;
         var4 += 3;
      }
   }

   public static byte[] encode(byte[] var0) {
      int var1 = var0.length % 3;
      byte[] var2;
      if(var1 == 0) {
         var2 = new byte[var0.length * 4 / 3];
      } else {
         var2 = new byte[(var0.length / 3 + 1) * 4];
      }

      int var3 = var0.length - var1;
      int var4 = 0;

      int var30;
      for(int var5 = 0; var5 < var3; var5 = var30) {
         int var6 = var0[var5] & 255;
         int var7 = var5 + 1;
         int var8 = var0[var7] & 255;
         int var9 = var5 + 2;
         int var10 = var0[var9] & 255;
         byte[] var11 = encodingTable;
         int var12 = var6 >>> 2 & 63;
         byte var13 = var11[var12];
         var2[var4] = var13;
         int var14 = var4 + 1;
         byte[] var15 = encodingTable;
         int var16 = var6 << 4;
         int var17 = var8 >>> 4;
         int var18 = (var16 | var17) & 63;
         byte var19 = var15[var18];
         var2[var14] = var19;
         int var20 = var4 + 2;
         byte[] var21 = encodingTable;
         int var22 = var8 << 2;
         int var23 = var10 >>> 6;
         int var24 = (var22 | var23) & 63;
         byte var25 = var21[var24];
         var2[var20] = var25;
         int var26 = var4 + 3;
         byte[] var27 = encodingTable;
         int var28 = var10 & 63;
         byte var29 = var27[var28];
         var2[var26] = var29;
         var30 = var5 + 3;
         var4 += 4;
      }

      switch(var1) {
      case 0:
      default:
         break;
      case 1:
         int var31 = var0.length - 1;
         int var32 = var0[var31] & 255;
         int var33 = var32 >>> 2 & 63;
         int var34 = var32 << 4 & 63;
         int var35 = var2.length - 4;
         byte var36 = encodingTable[var33];
         var2[var35] = var36;
         int var37 = var2.length - 3;
         byte var38 = encodingTable[var34];
         var2[var37] = var38;
         int var39 = var2.length - 2;
         var2[var39] = 61;
         int var40 = var2.length - 1;
         var2[var40] = 61;
         break;
      case 2:
         int var41 = var0.length - 2;
         int var42 = var0[var41] & 255;
         int var43 = var0.length - 1;
         int var44 = var0[var43] & 255;
         int var45 = var42 >>> 2 & 63;
         int var46 = var42 << 4;
         int var47 = var44 >>> 4;
         int var48 = (var46 | var47) & 63;
         int var49 = var44 << 2 & 63;
         int var50 = var2.length - 4;
         byte var51 = encodingTable[var45];
         var2[var50] = var51;
         int var52 = var2.length - 3;
         byte var53 = encodingTable[var48];
         var2[var52] = var53;
         int var54 = var2.length - 2;
         byte var55 = encodingTable[var49];
         var2[var54] = var55;
         int var56 = var2.length - 1;
         var2[var56] = 61;
      }

      return var2;
   }
}
