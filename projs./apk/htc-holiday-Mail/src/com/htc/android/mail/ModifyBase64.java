package com.htc.android.mail;

import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

public class ModifyBase64 implements BinaryEncoder, BinaryDecoder {

   static final int BASELENGTH = 255;
   static final byte[] CHUNK_SEPARATOR = "\r\n".getBytes();
   static final int CHUNK_SIZE = 76;
   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   static final int EIGHTBIT = 8;
   static final int FOURBYTE = 4;
   static final int LOOKUPLENGTH = 64;
   static final byte PAD = 61;
   static final int SIGN = 128;
   static final int SIXTEENBIT = 16;
   private static final String TAG = "ModifyBase64";
   static final int TWENTYFOURBITGROUP = 24;
   private static byte[] base64Alphabet = new byte[255];
   private static byte[] lookUpBase64Alphabet = new byte[64];
   public int size = 0;


   static {
      for(int var0 = 0; var0 < 255; ++var0) {
         base64Alphabet[var0] = -1;
      }

      for(int var1 = 90; var1 >= 65; var1 += -1) {
         byte[] var2 = base64Alphabet;
         byte var3 = (byte)(var1 - 65);
         var2[var1] = var3;
      }

      for(int var4 = 122; var4 >= 97; var4 += -1) {
         byte[] var5 = base64Alphabet;
         byte var6 = (byte)(var4 - 97 + 26);
         var5[var4] = var6;
      }

      for(int var7 = 57; var7 >= 48; var7 += -1) {
         byte[] var8 = base64Alphabet;
         byte var9 = (byte)(var7 - 48 + 52);
         var8[var7] = var9;
      }

      base64Alphabet[43] = 62;
      base64Alphabet[47] = 63;

      for(int var10 = 0; var10 <= 25; ++var10) {
         byte[] var11 = lookUpBase64Alphabet;
         byte var12 = (byte)(var10 + 65);
         var11[var10] = var12;
      }

      int var13 = 26;

      for(int var14 = 0; var13 <= 51; ++var14) {
         byte[] var15 = lookUpBase64Alphabet;
         byte var16 = (byte)(var14 + 97);
         var15[var13] = var16;
         ++var13;
      }

      int var17 = 52;

      for(int var18 = 0; var17 <= 61; ++var18) {
         byte[] var19 = lookUpBase64Alphabet;
         byte var20 = (byte)(var18 + 48);
         var19[var17] = var20;
         ++var17;
      }

      lookUpBase64Alphabet[62] = 43;
      lookUpBase64Alphabet[63] = 47;
   }

   public ModifyBase64() {}

   public static byte[] decodeBase64(byte[] var0, int[] var1) {
      int var2 = discardNonBase64(var0);
      var1[0] = 0;
      if(var2 == 0) {
         var0 = new byte[0];
      } else {
         int var3 = var2 / 4;
         boolean var4 = false;
         boolean var5 = false;
         boolean var6 = false;
         boolean var7 = false;
         byte var8 = 0;
         boolean var9 = false;
         int var10 = var2;

         while(true) {
            int var11 = var10 - 1;
            if(var0[var11] == 61) {
               if(var10 + -1 != 0) {
                  continue;
               }

               var0 = new byte[0];
               break;
            }

            int var12 = var10 - var3;
            var1[0] = var12;
            byte[] var55 = var0;
            byte var13 = 0;
            byte var56 = var8;

            int var43;
            for(var8 = var13; var8 < var3; var43 = var8 + 1) {
               int var19 = var8 * 4;
               int var20 = var19 + 2;
               byte var21 = var0[var20];
               int var22 = var19 + 3;
               byte var23 = var0[var22];
               byte[] var24 = base64Alphabet;
               byte var25 = var0[var19];
               byte var26 = var24[var25];
               byte[] var27 = base64Alphabet;
               int var28 = var19 + 1;
               byte var29 = var0[var28];
               byte var30 = var27[var29];
               if(var21 != 61 && var23 != 61) {
                  byte var31 = base64Alphabet[var21];
                  byte var32 = base64Alphabet[var23];
                  int var33 = var26 << 2;
                  int var34 = var30 >> 4;
                  byte var35 = (byte)(var33 | var34);
                  var55[var56] = var35;
                  int var36 = var56 + 1;
                  int var37 = (var30 & 15) << 4;
                  int var38 = var31 >> 2 & 15;
                  byte var39 = (byte)(var37 | var38);
                  var55[var36] = var39;
                  int var40 = var56 + 2;
                  byte var41 = (byte)(var31 << 6 | var32);
                  var55[var40] = var41;
               } else if(var21 == 61) {
                  int var44 = var26 << 2;
                  int var45 = var30 >> 4;
                  byte var46 = (byte)(var44 | var45);
                  var55[var56] = var46;
               } else if(var23 == 61) {
                  byte var47 = base64Alphabet[var21];
                  int var48 = var26 << 2;
                  int var49 = var30 >> 4;
                  byte var50 = (byte)(var48 | var49);
                  var55[var56] = var50;
                  int var51 = var56 + 1;
                  int var52 = (var30 & 15) << 4;
                  int var53 = var47 >> 2 & 15;
                  byte var54 = (byte)(var52 | var53);
                  var55[var51] = var54;
               }

               int var42 = var56 + 3;
            }

            var0 = var55;
            break;
         }
      }

      return var0;
   }

   static byte[] decodeBase64_old(byte[] var0) {
      int var1 = discardNonBase64(var0);
      if(var1 == 0) {
         var0 = new byte[0];
      } else {
         int var2 = var1 / 4;
         int var3 = 0;
         int var4 = var1;

         while(true) {
            int var5 = var4 - 1;
            if(var0[var5] == 61) {
               if(var4 + -1 != 0) {
                  continue;
               }

               var0 = new byte[0];
               break;
            }

            byte[] var42 = new byte[var4 - var2];

            int var30;
            for(byte var6 = 0; var6 < var2; var30 = var6 + 1) {
               int var7 = var6 * 4;
               int var8 = var7 + 2;
               byte var9 = var0[var8];
               int var10 = var7 + 3;
               byte var11 = var0[var10];
               byte[] var12 = base64Alphabet;
               byte var13 = var0[var7];
               byte var14 = var12[var13];
               byte[] var15 = base64Alphabet;
               int var16 = var7 + 1;
               byte var17 = var0[var16];
               byte var18 = var15[var17];
               if(var9 != 61 && var11 != 61) {
                  byte var19 = base64Alphabet[var9];
                  byte var20 = base64Alphabet[var11];
                  int var21 = var14 << 2;
                  int var22 = var18 >> 4;
                  byte var23 = (byte)(var21 | var22);
                  var42[var3] = var23;
                  int var24 = var3 + 1;
                  int var25 = (var18 & 15) << 4;
                  int var26 = var19 >> 2 & 15;
                  byte var27 = (byte)(var25 | var26);
                  var42[var24] = var27;
                  int var28 = var3 + 2;
                  byte var29 = (byte)(var19 << 6 | var20);
                  var42[var28] = var29;
               } else if(var9 == 61) {
                  int var31 = var14 << 2;
                  int var32 = var18 >> 4;
                  byte var33 = (byte)(var31 | var32);
                  var42[var3] = var33;
               } else if(var11 == 61) {
                  byte var34 = base64Alphabet[var9];
                  int var35 = var14 << 2;
                  int var36 = var18 >> 4;
                  byte var37 = (byte)(var35 | var36);
                  var42[var3] = var37;
                  int var38 = var3 + 1;
                  int var39 = (var18 & 15) << 4;
                  int var40 = var34 >> 2 & 15;
                  byte var41 = (byte)(var39 | var40);
                  var42[var38] = var41;
               }

               var3 += 3;
            }

            var0 = var42;
            break;
         }
      }

      return var0;
   }

   static int discardNonBase64(byte[] var0) {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            for(int var6 = var1; var6 >= var1; ++var6) {
               int var7 = var0.length;
               if(var6 >= var7) {
                  break;
               }

               var0[var6] = 0;
            }

            if(DEBUG) {
               String var8 = "end discard!:" + var1;
               ll.i("ModifyBase64", var8);
            }

            return var1;
         }

         if(isBase64(var0[var2])) {
            int var4 = var1 + 1;
            byte var5 = var0[var2];
            var0[var1] = var5;
            var1 = var4;
         }

         ++var2;
      }
   }

   static byte[] discardWhitespace(byte[] var0) {
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

         switch(var0[var3]) {
         default:
            int var5 = var2 + 1;
            byte var6 = var0[var3];
            var1[var2] = var6;
            var2 = var5;
         case 9:
         case 10:
         case 13:
         case 32:
            ++var3;
         }
      }
   }

   public static byte[] encodeBase64(byte[] var0) {
      return encodeBase64(var0, (boolean)0);
   }

   public static byte[] encodeBase64(byte[] var0, boolean var1) {
      int var2 = var0.length * 8;
      int var3 = var2 % 24;
      int var4 = var2 / 24;
      boolean var5 = false;
      int var6;
      if(var3 != 0) {
         var6 = (var4 + 1) * 4;
      } else {
         int var58 = var4 * 4;
      }

      int var9;
      int var10;
      if(var1) {
         int var109;
         if(CHUNK_SEPARATOR.length == 0) {
            var109 = 0;
         } else {
            var109 = (int)Math.ceil((double)((float)var6 / 76.0F));
         }

         int var7 = CHUNK_SEPARATOR.length * var109;
         int var8 = var6 + var7;
         var9 = var109;
         var10 = var8;
      } else {
         var9 = 0;
         var10 = var6;
      }

      byte[] var11 = new byte[var10];
      boolean var12 = false;
      boolean var13 = false;
      boolean var14 = false;
      boolean var15 = false;
      int var16 = 0;
      byte var17 = 76;
      int var18 = 0;

      int var19;
      byte var112;
      for(var19 = 0; var19 < var4; ++var19) {
         int var22 = var19 * 3;
         byte var113 = var0[var22];
         int var23 = var22 + 1;
         var112 = var0[var23];
         int var24 = var22 + 2;
         byte var25 = var0[var24];
         byte var111 = (byte)(var112 & 15);
         byte var110 = (byte)(var113 & 3);
         byte var26;
         if((var113 & -128) == 0) {
            var26 = (byte)(var113 >> 2);
         } else {
            var26 = (byte)(var113 >> 2 ^ 192);
         }

         byte var27;
         if((var112 & -128) == 0) {
            var27 = (byte)(var112 >> 4);
         } else {
            var27 = (byte)(var112 >> 4 ^ 240);
         }

         byte var28;
         if((var25 & -128) == 0) {
            var28 = (byte)(var25 >> 6);
         } else {
            var28 = (byte)(var25 >> 6 ^ 252);
         }

         byte var29 = lookUpBase64Alphabet[var26];
         var11[var16] = var29;
         int var30 = var16 + 1;
         byte[] var31 = lookUpBase64Alphabet;
         int var32 = var110 << 4;
         int var33 = var27 | var32;
         byte var34 = var31[var33];
         var11[var30] = var34;
         int var35 = var16 + 2;
         byte[] var36 = lookUpBase64Alphabet;
         int var37 = var111 << 2;
         int var38 = var28 | var37;
         byte var39 = var36[var38];
         var11[var35] = var39;
         int var40 = var16 + 3;
         byte[] var41 = lookUpBase64Alphabet;
         int var42 = var25 & 63;
         byte var43 = var41[var42];
         var11[var40] = var43;
         var16 += 4;
         if(var1 && var16 == var17) {
            byte[] var46 = CHUNK_SEPARATOR;
            int var47 = CHUNK_SEPARATOR.length;
            byte var49 = 0;
            System.arraycopy(var46, var49, var11, var16, var47);
            ++var18;
            int var53 = (var18 + 1) * 76;
            int var54 = CHUNK_SEPARATOR.length * var18;
            int var10000 = var53 + var54;
            int var56 = CHUNK_SEPARATOR.length;
            var10000 = var16 + var56;
         }
      }

      int var59 = var19 * 3;
      if(var3 == 8) {
         byte var60 = var0[var59];
         byte var61 = (byte)(var60 & 3);
         byte var62;
         if((var60 & -128) == 0) {
            var62 = (byte)(var60 >> 2);
         } else {
            var62 = (byte)(var60 >> 2 ^ 192);
         }

         byte var63 = lookUpBase64Alphabet[var62];
         var11[var16] = var63;
         int var64 = var16 + 1;
         byte[] var65 = lookUpBase64Alphabet;
         int var66 = var61 << 4;
         byte var67 = var65[var66];
         var11[var64] = var67;
         int var68 = var16 + 2;
         var11[var68] = 61;
         int var69 = var16 + 3;
         var11[var69] = 61;
      } else if(var3 == 16) {
         byte var85 = var0[var59];
         int var86 = var59 + 1;
         byte var87 = var0[var86];
         byte var88 = (byte)(var87 & 15);
         var112 = (byte)(var85 & 3);
         byte var89;
         if((var85 & -128) == 0) {
            var89 = (byte)(var85 >> 2);
         } else {
            var89 = (byte)(var85 >> 2 ^ 192);
         }

         byte var90;
         if((var87 & -128) == 0) {
            var90 = (byte)(var87 >> 4);
         } else {
            var90 = (byte)(var87 >> 4 ^ 240);
         }

         byte var91 = lookUpBase64Alphabet[var89];
         var11[var16] = var91;
         int var92 = var16 + 1;
         byte[] var93 = lookUpBase64Alphabet;
         int var94 = var112 << 4;
         int var95 = var90 | var94;
         byte var96 = var93[var95];
         var11[var92] = var96;
         int var97 = var16 + 2;
         byte[] var98 = lookUpBase64Alphabet;
         int var99 = var88 << 2;
         byte var100 = var98[var99];
         var11[var97] = var100;
         int var101 = var16 + 3;
         var11[var101] = 61;
      }

      if(var1 && var18 < var9) {
         byte[] var76 = CHUNK_SEPARATOR;
         int var77 = CHUNK_SEPARATOR.length;
         int var78 = var10 - var77;
         int var79 = CHUNK_SEPARATOR.length;
         byte var81 = 0;
         System.arraycopy(var76, var81, var11, var78, var79);
      }

      return var11;
   }

   public static byte[] encodeBase64Chunked(byte[] var0) {
      return encodeBase64(var0, (boolean)1);
   }

   public static boolean isArrayByteBase64(byte[] var0) {
      byte[] var1 = discardWhitespace(var0);
      int var2 = var1.length;
      boolean var3;
      if(var2 == 0) {
         var3 = true;
      } else {
         int var4 = 0;

         while(true) {
            if(var4 >= var2) {
               var3 = true;
               break;
            }

            if(!isBase64(var1[var4])) {
               var3 = false;
               break;
            }

            ++var4;
         }
      }

      return var3;
   }

   private static boolean isBase64(byte var0) {
      boolean var1;
      if(var0 == 61) {
         var1 = true;
      } else if(base64Alphabet[var0] == -1) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public Object decode(Object var1) throws DecoderException {
      if(!(var1 instanceof byte[])) {
         throw new DecoderException("Parameter supplied to Base64 decode is not a byte[]");
      } else {
         byte[] var2 = (byte[])((byte[])var1);
         return this.decode(var2);
      }
   }

   public byte[] decode(byte[] var1) {
      return decodeBase64_old(var1);
   }

   public Object encode(Object var1) throws EncoderException {
      if(!(var1 instanceof byte[])) {
         throw new EncoderException("Parameter supplied to Base64 encode is not a byte[]");
      } else {
         byte[] var2 = (byte[])((byte[])var1);
         return this.encode(var2);
      }
   }

   public byte[] encode(byte[] var1) {
      return encodeBase64(var1, (boolean)0);
   }
}
