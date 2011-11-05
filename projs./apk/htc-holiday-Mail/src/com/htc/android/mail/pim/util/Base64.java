package com.htc.android.mail.pim.util;

import java.io.UnsupportedEncodingException;

public class Base64 {

   private static final byte[] decodingTable = new byte[128];
   private static final byte[] encodingTable = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)43, (byte)47};


   static {
      for(int var0 = 0; var0 < 128; ++var0) {
         decodingTable[var0] = -1;
      }

      for(int var1 = 65; var1 <= 90; ++var1) {
         byte[] var2 = decodingTable;
         byte var3 = (byte)(var1 - 65);
         var2[var1] = var3;
      }

      for(int var4 = 97; var4 <= 122; ++var4) {
         byte[] var5 = decodingTable;
         byte var6 = (byte)(var4 - 97 + 26);
         var5[var4] = var6;
      }

      for(int var7 = 48; var7 <= 57; ++var7) {
         byte[] var8 = decodingTable;
         byte var9 = (byte)(var7 - 48 + 52);
         var8[var7] = var9;
      }

      decodingTable[43] = 62;
      decodingTable[47] = 63;
   }

   public Base64() {}

   public static String decode(String var0, String var1) {
      String var3;
      if(var1 == null) {
         byte[] var2 = decode(var0);
         var3 = new String(var2);
      } else {
         try {
            byte[] var4 = decode(var0);
            var3 = new String(var4, var1);
         } catch (UnsupportedEncodingException var7) {
            byte[] var6 = decode(var0);
            var3 = new String(var6);
         }
      }

      return var3;
   }

   public static String decode(byte[] var0, String var1) {
      String var3;
      if(var1 == null) {
         byte[] var2 = decode(var0);
         var3 = new String(var2);
      } else {
         try {
            byte[] var4 = decode(var0);
            var3 = new String(var4, var1);
         } catch (UnsupportedEncodingException var7) {
            byte[] var6 = decode(var0);
            var3 = new String(var6);
         }
      }

      return var3;
   }

   public static byte[] decode(String var0) {
      byte[] var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var0 = discardNonBase64Chars(var0);
         int var2 = var0.length() - 2;
         byte[] var3;
         if(var0.charAt(var2) == 61) {
            var3 = new byte[(var0.length() / 4 - 1) * 3 + 1];
         } else {
            int var31 = var0.length() - 1;
            if(var0.charAt(var31) == 61) {
               var3 = new byte[(var0.length() / 4 - 1) * 3 + 2];
            } else {
               var3 = new byte[var0.length() / 4 * 3];
            }
         }

         int var4 = 0;
         int var5 = 0;

         while(true) {
            int var6 = var0.length() - 4;
            if(var4 >= var6) {
               int var32 = var0.length() - 2;
               if(var0.charAt(var32) == 61) {
                  byte[] var33 = decodingTable;
                  int var34 = var0.length() - 4;
                  char var35 = var0.charAt(var34);
                  byte var36 = var33[var35];
                  byte[] var37 = decodingTable;
                  int var38 = var0.length() - 3;
                  char var39 = var0.charAt(var38);
                  byte var40 = var37[var39];
                  int var41 = var3.length - 1;
                  int var42 = var36 << 2;
                  int var43 = var40 >> 4;
                  byte var44 = (byte)(var42 | var43);
                  var3[var41] = var44;
               } else {
                  int var45 = var0.length() - 1;
                  if(var0.charAt(var45) == 61) {
                     byte[] var46 = decodingTable;
                     int var47 = var0.length() - 4;
                     char var48 = var0.charAt(var47);
                     byte var49 = var46[var48];
                     byte[] var50 = decodingTable;
                     int var51 = var0.length() - 3;
                     char var52 = var0.charAt(var51);
                     byte var53 = var50[var52];
                     byte[] var54 = decodingTable;
                     int var55 = var0.length() - 2;
                     char var56 = var0.charAt(var55);
                     byte var57 = var54[var56];
                     int var58 = var3.length - 2;
                     int var59 = var49 << 2;
                     int var60 = var53 >> 4;
                     byte var61 = (byte)(var59 | var60);
                     var3[var58] = var61;
                     int var62 = var3.length - 1;
                     int var63 = var53 << 4;
                     int var64 = var57 >> 2;
                     byte var65 = (byte)(var63 | var64);
                     var3[var62] = var65;
                  } else {
                     byte[] var66 = decodingTable;
                     int var67 = var0.length() - 4;
                     char var68 = var0.charAt(var67);
                     byte var69 = var66[var68];
                     byte[] var70 = decodingTable;
                     int var71 = var0.length() - 3;
                     char var72 = var0.charAt(var71);
                     byte var73 = var70[var72];
                     byte[] var74 = decodingTable;
                     int var75 = var0.length() - 2;
                     char var76 = var0.charAt(var75);
                     byte var77 = var74[var76];
                     byte[] var78 = decodingTable;
                     int var79 = var0.length() - 1;
                     char var80 = var0.charAt(var79);
                     byte var81 = var78[var80];
                     int var82 = var3.length - 3;
                     int var83 = var69 << 2;
                     int var84 = var73 >> 4;
                     byte var85 = (byte)(var83 | var84);
                     var3[var82] = var85;
                     int var86 = var3.length - 2;
                     int var87 = var73 << 4;
                     int var88 = var77 >> 2;
                     byte var89 = (byte)(var87 | var88);
                     var3[var86] = var89;
                     int var90 = var3.length - 1;
                     byte var91 = (byte)(var77 << 6 | var81);
                     var3[var90] = var91;
                  }
               }

               var1 = var3;
               break;
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
            byte var24 = (byte)(var22 | var23);
            var3[var5] = var24;
            int var25 = var5 + 1;
            int var26 = var13 << 4;
            int var27 = var17 >> 2;
            byte var28 = (byte)(var26 | var27);
            var3[var25] = var28;
            int var29 = var5 + 2;
            byte var30 = (byte)(var17 << 6 | var21);
            var3[var29] = var30;
            var4 += 4;
            var5 += 3;
         }
      }

      return var1;
   }

   public static byte[] decode(byte[] var0) {
      byte[] var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var0 = discardNonBase64Bytes(var0);
         int var2 = var0.length - 2;
         byte[] var3;
         if(var0[var2] == 61) {
            var3 = new byte[(var0.length / 4 - 1) * 3 + 1];
         } else {
            int var31 = var0.length - 1;
            if(var0[var31] == 61) {
               var3 = new byte[(var0.length / 4 - 1) * 3 + 2];
            } else {
               var3 = new byte[var0.length / 4 * 3];
            }
         }

         int var4 = 0;
         int var5 = 0;

         while(true) {
            int var6 = var0.length - 4;
            if(var4 >= var6) {
               int var32 = var0.length - 2;
               if(var0[var32] == 61) {
                  byte[] var33 = decodingTable;
                  int var34 = var0.length - 4;
                  byte var35 = var0[var34];
                  byte var36 = var33[var35];
                  byte[] var37 = decodingTable;
                  int var38 = var0.length - 3;
                  byte var39 = var0[var38];
                  byte var40 = var37[var39];
                  int var41 = var3.length - 1;
                  int var42 = var36 << 2;
                  int var43 = var40 >> 4;
                  byte var44 = (byte)(var42 | var43);
                  var3[var41] = var44;
               } else {
                  int var45 = var0.length - 1;
                  if(var0[var45] == 61) {
                     byte[] var46 = decodingTable;
                     int var47 = var0.length - 4;
                     byte var48 = var0[var47];
                     byte var49 = var46[var48];
                     byte[] var50 = decodingTable;
                     int var51 = var0.length - 3;
                     byte var52 = var0[var51];
                     byte var53 = var50[var52];
                     byte[] var54 = decodingTable;
                     int var55 = var0.length - 2;
                     byte var56 = var0[var55];
                     byte var57 = var54[var56];
                     int var58 = var3.length - 2;
                     int var59 = var49 << 2;
                     int var60 = var53 >> 4;
                     byte var61 = (byte)(var59 | var60);
                     var3[var58] = var61;
                     int var62 = var3.length - 1;
                     int var63 = var53 << 4;
                     int var64 = var57 >> 2;
                     byte var65 = (byte)(var63 | var64);
                     var3[var62] = var65;
                  } else {
                     byte[] var66 = decodingTable;
                     int var67 = var0.length - 4;
                     byte var68 = var0[var67];
                     byte var69 = var66[var68];
                     byte[] var70 = decodingTable;
                     int var71 = var0.length - 3;
                     byte var72 = var0[var71];
                     byte var73 = var70[var72];
                     byte[] var74 = decodingTable;
                     int var75 = var0.length - 2;
                     byte var76 = var0[var75];
                     byte var77 = var74[var76];
                     byte[] var78 = decodingTable;
                     int var79 = var0.length - 1;
                     byte var80 = var0[var79];
                     byte var81 = var78[var80];
                     int var82 = var3.length - 3;
                     int var83 = var69 << 2;
                     int var84 = var73 >> 4;
                     byte var85 = (byte)(var83 | var84);
                     var3[var82] = var85;
                     int var86 = var3.length - 2;
                     int var87 = var73 << 4;
                     int var88 = var77 >> 2;
                     byte var89 = (byte)(var87 | var88);
                     var3[var86] = var89;
                     int var90 = var3.length - 1;
                     byte var91 = (byte)(var77 << 6 | var81);
                     var3[var90] = var91;
                  }
               }

               var1 = var3;
               break;
            }

            byte[] var7 = decodingTable;
            byte var8 = var0[var4];
            byte var9 = var7[var8];
            byte[] var10 = decodingTable;
            int var11 = var4 + 1;
            byte var12 = var0[var11];
            byte var13 = var10[var12];
            byte[] var14 = decodingTable;
            int var15 = var4 + 2;
            byte var16 = var0[var15];
            byte var17 = var14[var16];
            byte[] var18 = decodingTable;
            int var19 = var4 + 3;
            byte var20 = var0[var19];
            byte var21 = var18[var20];
            int var22 = var9 << 2;
            int var23 = var13 >> 4;
            byte var24 = (byte)(var22 | var23);
            var3[var5] = var24;
            int var25 = var5 + 1;
            int var26 = var13 << 4;
            int var27 = var17 >> 2;
            byte var28 = (byte)(var26 | var27);
            var3[var25] = var28;
            int var29 = var5 + 2;
            byte var30 = (byte)(var17 << 6 | var21);
            var3[var29] = var30;
            var4 += 4;
            var5 += 3;
         }
      }

      return var1;
   }

   private static byte[] discardNonBase64Bytes(byte[] var0) {
      byte[] var1 = new byte[var0.length];
      int var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            byte[] var7 = new byte[var2];
            System.arraycopy(var1, 0, var7, 0, var2);
            return var7;
         }

         if(isValidBase64Byte(var0[var3])) {
            int var5 = var2 + 1;
            byte var6 = var0[var3];
            var1[var2] = var6;
            var2 = var5;
         }

         ++var3;
      }
   }

   private static String discardNonBase64Chars(String var0) {
      StringBuffer var1 = new StringBuffer();
      int var2 = var0.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         if(isValidBase64Byte((byte)var0.charAt(var3))) {
            char var4 = var0.charAt(var3);
            var1.append(var4);
         }
      }

      return var1.toString();
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

   private static boolean isValidBase64Byte(byte var0) {
      boolean var1;
      if(var0 == 61) {
         var1 = true;
      } else if(var0 >= 0 && var0 < 128) {
         if(decodingTable[var0] == -1) {
            var1 = false;
         } else {
            var1 = true;
         }
      } else {
         var1 = false;
      }

      return var1;
   }
}
