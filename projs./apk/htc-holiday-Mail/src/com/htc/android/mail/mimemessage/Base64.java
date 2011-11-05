package com.htc.android.mail.mimemessage;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

public class Base64 implements BinaryEncoder, BinaryDecoder {

   static final byte[] CHUNK_SEPARATOR = new byte[]{(byte)13, (byte)10};
   static final int CHUNK_SIZE = 76;
   private static final int MASK_6BITS = 63;
   private static final int MASK_8BITS = 255;
   private static final byte PAD = 61;
   private static final byte[] base64ToInt = new byte[]{(byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)62, (byte)255, (byte)255, (byte)255, (byte)63, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)58, (byte)59, (byte)60, (byte)61, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9, (byte)10, (byte)11, (byte)12, (byte)13, (byte)14, (byte)15, (byte)16, (byte)17, (byte)18, (byte)19, (byte)20, (byte)21, (byte)22, (byte)23, (byte)24, (byte)25, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)26, (byte)27, (byte)28, (byte)29, (byte)30, (byte)31, (byte)32, (byte)33, (byte)34, (byte)35, (byte)36, (byte)37, (byte)38, (byte)39, (byte)40, (byte)41, (byte)42, (byte)43, (byte)44, (byte)45, (byte)46, (byte)47, (byte)48, (byte)49, (byte)50, (byte)51};
   private static final byte[] intToBase64 = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)43, (byte)47};
   private byte[] buf;
   private int currentLinePos;
   private final int decodeSize;
   private final int encodeSize;
   private boolean eof;
   private final int lineLength;
   private final byte[] lineSeparator;
   private int modulus;
   private int pos;
   private int readPos;
   private int x;


   public Base64() {
      byte[] var1 = CHUNK_SEPARATOR;
      this(76, var1);
   }

   public Base64(int var1) {
      byte[] var2 = CHUNK_SEPARATOR;
      this(var1, var2);
   }

   public Base64(int var1, byte[] var2) {
      this.lineLength = var1;
      byte[] var3 = new byte[var2.length];
      this.lineSeparator = var3;
      byte[] var4 = this.lineSeparator;
      int var5 = var2.length;
      System.arraycopy(var2, 0, var4, 0, var5);
      if(var1 > 0) {
         int var6 = var2.length + 4;
         this.encodeSize = var6;
      } else {
         this.encodeSize = 4;
      }

      int var7 = this.encodeSize - 1;
      this.decodeSize = var7;
      if(containsBase64Byte(var2)) {
         String var8;
         try {
            var8 = new String(var2, "UTF-8");
         } catch (UnsupportedEncodingException var11) {
            var8 = new String(var2);
         }

         String var9 = "lineSeperator must not contain base64 characters: [" + var8 + "]";
         throw new IllegalArgumentException(var9);
      }
   }

   private static boolean containsBase64Byte(byte[] var0) {
      int var1 = 0;

      boolean var3;
      while(true) {
         int var2 = var0.length;
         if(var1 >= var2) {
            var3 = false;
            break;
         }

         if(isBase64(var0[var1])) {
            var3 = true;
            break;
         }

         ++var1;
      }

      return var3;
   }

   public static byte[] decodeBase64(byte[] var0) {
      byte[] var1;
      if(var0 != null && var0.length != 0) {
         Base64 var2 = new Base64();
         byte[] var3 = new byte[(int)((long)(var0.length * 3 / 4))];
         int var4 = var3.length;
         var2.setInitialBuffer(var3, 0, var4);
         int var5 = var0.length;
         var2.decode(var0, 0, var5);
         var2.decode(var0, 0, -1);
         byte[] var6 = new byte[var2.pos];
         int var7 = var6.length;
         var2.readResults(var6, 0, var7);
         var1 = var6;
      } else {
         var1 = var0;
      }

      return var1;
   }

   public static BigInteger decodeInteger(byte[] var0) {
      byte[] var1 = decodeBase64(var0);
      return new BigInteger(1, var1);
   }

   static byte[] discardNonBase64(byte[] var0) {
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

         if(isBase64(var0[var3])) {
            int var5 = var2 + 1;
            byte var6 = var0[var3];
            var1[var2] = var6;
            var2 = var5;
         }

         ++var3;
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
      byte[] var2;
      if(var0 != null && var0.length != 0) {
         Base64 var3;
         if(var1) {
            var3 = new Base64();
         } else {
            var3 = new Base64(0);
         }

         long var4 = (long)(var0.length * 4 / 3);
         long var6 = var4 % 4L;
         if(var6 != 0L) {
            long var8 = 4L - var6;
            var4 += var8;
         }

         if(var1) {
            long var10 = (var4 + 76L - 1L) / 76L;
            long var12 = (long)CHUNK_SEPARATOR.length;
            long var14 = var10 * var12;
            var4 += var14;
         }

         if(var4 > 2147483647L) {
            throw new IllegalArgumentException("Input array too big, output array would be bigger than Integer.MAX_VALUE=2147483647");
         }

         byte[] var16 = new byte[(int)var4];
         int var17 = var16.length;
         var3.setInitialBuffer(var16, 0, var17);
         int var18 = var0.length;
         var3.encode(var0, 0, var18);
         var3.encode(var0, 0, -1);
         if(var3.buf != var16) {
            int var19 = var16.length;
            var3.readResults(var16, 0, var19);
         }

         var2 = var16;
      } else {
         var2 = var0;
      }

      return var2;
   }

   public static byte[] encodeBase64Chunked(byte[] var0) {
      return encodeBase64(var0, (boolean)1);
   }

   public static byte[] encodeInteger(BigInteger var0) {
      if(var0 == null) {
         throw new NullPointerException("encodeInteger called with null parameter");
      } else {
         return encodeBase64(toIntegerBytes(var0), (boolean)0);
      }
   }

   public static boolean isArrayByteBase64(byte[] var0) {
      int var1 = 0;

      boolean var3;
      while(true) {
         int var2 = var0.length;
         if(var1 >= var2) {
            var3 = true;
            break;
         }

         if(!isBase64(var0[var1]) && !isWhiteSpace(var0[var1])) {
            var3 = false;
            break;
         }

         ++var1;
      }

      return var3;
   }

   public static boolean isBase64(byte var0) {
      boolean var2;
      if(var0 != 61) {
         label24: {
            if(var0 >= 0) {
               int var1 = base64ToInt.length;
               if(var0 < var1 && base64ToInt[var0] != -1) {
                  break label24;
               }
            }

            var2 = false;
            return var2;
         }
      }

      var2 = true;
      return var2;
   }

   private static boolean isWhiteSpace(byte var0) {
      boolean var1;
      switch(var0) {
      case 9:
      case 10:
      case 13:
      case 32:
         var1 = true;
         break;
      default:
         var1 = false;
      }

      return var1;
   }

   private void resizeBuf() {
      if(this.buf == null) {
         byte[] var1 = new byte[8192];
         this.buf = var1;
         this.pos = 0;
         this.readPos = 0;
      } else {
         byte[] var2 = new byte[this.buf.length * 2];
         byte[] var3 = this.buf;
         int var4 = this.buf.length;
         System.arraycopy(var3, 0, var2, 0, var4);
         this.buf = var2;
      }
   }

   static byte[] toIntegerBytes(BigInteger var0) {
      int var1 = var0.bitLength() + 7 >> 3 << 3;
      byte[] var2 = var0.toByteArray();
      byte[] var5;
      if(var0.bitLength() % 8 != 0) {
         int var3 = var0.bitLength() / 8 + 1;
         int var4 = var1 / 8;
         if(var3 == var4) {
            var5 = var2;
            return var5;
         }
      }

      byte var6 = 0;
      int var7 = var2.length;
      if(var0.bitLength() % 8 == 0) {
         var6 = 1;
         var7 += -1;
      }

      int var8 = var1 / 8 - var7;
      byte[] var9 = new byte[var1 / 8];
      System.arraycopy(var2, var6, var9, var8, var7);
      var5 = var9;
      return var5;
   }

   int avail() {
      int var3;
      if(this.buf != null) {
         int var1 = this.pos;
         int var2 = this.readPos;
         var3 = var1 - var2;
      } else {
         var3 = 0;
      }

      return var3;
   }

   public Object decode(Object var1) throws DecoderException {
      if(!(var1 instanceof byte[])) {
         throw new DecoderException("Parameter supplied to Base64 decode is not a byte[]");
      } else {
         byte[] var2 = (byte[])((byte[])var1);
         return this.decode(var2);
      }
   }

   void decode(byte[] var1, int var2, int var3) {
      if(!this.eof) {
         if(var3 < 0) {
            this.eof = (boolean)1;
         }

         int var4 = 0;

         for(int var5 = var2; var4 < var3; var5 = var2) {
            label45: {
               if(this.buf != null) {
                  int var6 = this.buf.length;
                  int var7 = this.pos;
                  int var8 = var6 - var7;
                  int var9 = this.decodeSize;
                  if(var8 >= var9) {
                     break label45;
                  }
               }

               this.resizeBuf();
            }

            var2 = var5 + 1;
            byte var10 = var1[var5];
            if(var10 == 61) {
               int var11 = this.x << 6;
               this.x = var11;
               switch(this.modulus) {
               case 2:
                  int var12 = this.x << 6;
                  this.x = var12;
                  byte[] var13 = this.buf;
                  int var14 = this.pos;
                  int var15 = var14 + 1;
                  this.pos = var15;
                  byte var16 = (byte)(this.x >> 16 & 255);
                  var13[var14] = var16;
                  break;
               case 3:
                  byte[] var17 = this.buf;
                  int var18 = this.pos;
                  int var19 = var18 + 1;
                  this.pos = var19;
                  byte var20 = (byte)(this.x >> 16 & 255);
                  var17[var18] = var20;
                  byte[] var21 = this.buf;
                  int var22 = this.pos;
                  int var23 = var22 + 1;
                  this.pos = var23;
                  byte var24 = (byte)(this.x >> 8 & 255);
                  var21[var22] = var24;
               }

               this.eof = (boolean)1;
               return;
            }

            if(var10 >= 0) {
               int var25 = base64ToInt.length;
               if(var10 < var25) {
                  byte var26 = base64ToInt[var10];
                  if(var26 >= 0) {
                     int var27 = this.modulus + 1;
                     this.modulus = var27;
                     int var28 = var27 % 4;
                     this.modulus = var28;
                     int var29 = (this.x << 6) + var26;
                     this.x = var29;
                     if(this.modulus == 0) {
                        byte[] var30 = this.buf;
                        int var31 = this.pos;
                        int var32 = var31 + 1;
                        this.pos = var32;
                        byte var33 = (byte)(this.x >> 16 & 255);
                        var30[var31] = var33;
                        byte[] var34 = this.buf;
                        int var35 = this.pos;
                        int var36 = var35 + 1;
                        this.pos = var36;
                        byte var37 = (byte)(this.x >> 8 & 255);
                        var34[var35] = var37;
                        byte[] var38 = this.buf;
                        int var39 = this.pos;
                        int var40 = var39 + 1;
                        this.pos = var40;
                        byte var41 = (byte)(this.x & 255);
                        var38[var39] = var41;
                     }
                  }
               }
            }

            ++var4;
         }

      }
   }

   public byte[] decode(byte[] var1) {
      return decodeBase64(var1);
   }

   public Object encode(Object var1) throws EncoderException {
      if(!(var1 instanceof byte[])) {
         throw new EncoderException("Parameter supplied to Base64 encode is not a byte[]");
      } else {
         byte[] var2 = (byte[])((byte[])var1);
         return this.encode(var2);
      }
   }

   void encode(byte[] var1, int var2, int var3) {
      if(!this.eof) {
         if(var3 < 0) {
            label46: {
               this.eof = (boolean)1;
               if(this.buf != null) {
                  int var4 = this.buf.length;
                  int var5 = this.pos;
                  int var6 = var4 - var5;
                  int var7 = this.encodeSize;
                  if(var6 >= var7) {
                     break label46;
                  }
               }

               this.resizeBuf();
            }

            switch(this.modulus) {
            case 1:
               byte[] var15 = this.buf;
               int var16 = this.pos;
               int var17 = var16 + 1;
               this.pos = var17;
               byte[] var18 = intToBase64;
               int var19 = this.x >> 2 & 63;
               byte var20 = var18[var19];
               var15[var16] = var20;
               byte[] var21 = this.buf;
               int var22 = this.pos;
               int var23 = var22 + 1;
               this.pos = var23;
               byte[] var24 = intToBase64;
               int var25 = this.x << 4 & 63;
               byte var26 = var24[var25];
               var21[var22] = var26;
               byte[] var27 = this.buf;
               int var28 = this.pos;
               int var29 = var28 + 1;
               this.pos = var29;
               var27[var28] = 61;
               byte[] var30 = this.buf;
               int var31 = this.pos;
               int var32 = var31 + 1;
               this.pos = var32;
               var30[var31] = 61;
               break;
            case 2:
               byte[] var33 = this.buf;
               int var34 = this.pos;
               int var35 = var34 + 1;
               this.pos = var35;
               byte[] var36 = intToBase64;
               int var37 = this.x >> 10 & 63;
               byte var38 = var36[var37];
               var33[var34] = var38;
               byte[] var39 = this.buf;
               int var40 = this.pos;
               int var41 = var40 + 1;
               this.pos = var41;
               byte[] var42 = intToBase64;
               int var43 = this.x >> 4 & 63;
               byte var44 = var42[var43];
               var39[var40] = var44;
               byte[] var45 = this.buf;
               int var46 = this.pos;
               int var47 = var46 + 1;
               this.pos = var47;
               byte[] var48 = intToBase64;
               int var49 = this.x << 2 & 63;
               byte var50 = var48[var49];
               var45[var46] = var50;
               byte[] var51 = this.buf;
               int var52 = this.pos;
               int var53 = var52 + 1;
               this.pos = var53;
               var51[var52] = 61;
            }

            if(this.lineLength > 0) {
               byte[] var8 = this.lineSeparator;
               byte[] var9 = this.buf;
               int var10 = this.pos;
               int var11 = this.lineSeparator.length;
               System.arraycopy(var8, 0, var9, var10, var11);
               int var12 = this.pos;
               int var13 = this.lineSeparator.length;
               int var14 = var12 + var13;
               this.pos = var14;
            }
         } else {
            int var54 = 0;

            for(int var55 = var2; var54 < var3; var55 = var2) {
               label55: {
                  if(this.buf != null) {
                     int var56 = this.buf.length;
                     int var57 = this.pos;
                     int var58 = var56 - var57;
                     int var59 = this.encodeSize;
                     if(var58 >= var59) {
                        break label55;
                     }
                  }

                  this.resizeBuf();
               }

               int var60 = this.modulus + 1;
               this.modulus = var60;
               int var61 = var60 % 3;
               this.modulus = var61;
               var2 = var55 + 1;
               int var62 = var1[var55];
               if(var62 < 0) {
                  var62 += 256;
               }

               int var63 = (this.x << 8) + var62;
               this.x = var63;
               if(this.modulus == 0) {
                  byte[] var64 = this.buf;
                  int var65 = this.pos;
                  int var66 = var65 + 1;
                  this.pos = var66;
                  byte[] var67 = intToBase64;
                  int var68 = this.x >> 18 & 63;
                  byte var69 = var67[var68];
                  var64[var65] = var69;
                  byte[] var70 = this.buf;
                  int var71 = this.pos;
                  int var72 = var71 + 1;
                  this.pos = var72;
                  byte[] var73 = intToBase64;
                  int var74 = this.x >> 12 & 63;
                  byte var75 = var73[var74];
                  var70[var71] = var75;
                  byte[] var76 = this.buf;
                  int var77 = this.pos;
                  int var78 = var77 + 1;
                  this.pos = var78;
                  byte[] var79 = intToBase64;
                  int var80 = this.x >> 6 & 63;
                  byte var81 = var79[var80];
                  var76[var77] = var81;
                  byte[] var82 = this.buf;
                  int var83 = this.pos;
                  int var84 = var83 + 1;
                  this.pos = var84;
                  byte[] var85 = intToBase64;
                  int var86 = this.x & 63;
                  byte var87 = var85[var86];
                  var82[var83] = var87;
                  int var88 = this.currentLinePos + 4;
                  this.currentLinePos = var88;
                  if(this.lineLength > 0) {
                     int var89 = this.lineLength;
                     int var90 = this.currentLinePos;
                     if(var89 <= var90) {
                        byte[] var91 = this.lineSeparator;
                        byte[] var92 = this.buf;
                        int var93 = this.pos;
                        int var94 = this.lineSeparator.length;
                        System.arraycopy(var91, 0, var92, var93, var94);
                        int var95 = this.pos;
                        int var96 = this.lineSeparator.length;
                        int var97 = var95 + var96;
                        this.pos = var97;
                        this.currentLinePos = 0;
                     }
                  }
               }

               ++var54;
            }

         }
      }
   }

   public byte[] encode(byte[] var1) {
      return encodeBase64(var1, (boolean)0);
   }

   boolean hasData() {
      boolean var1;
      if(this.buf != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   int readResults(byte[] var1, int var2, int var3) {
      int var10;
      if(this.buf != null) {
         int var4 = Math.min(this.avail(), var3);
         if(this.buf != var1) {
            byte[] var5 = this.buf;
            int var6 = this.readPos;
            System.arraycopy(var5, var6, var1, var2, var4);
            int var7 = this.readPos + var4;
            this.readPos = var7;
            int var8 = this.readPos;
            int var9 = this.pos;
            if(var8 >= var9) {
               this.buf = null;
            }
         } else {
            this.buf = null;
         }

         var10 = var4;
      } else if(this.eof) {
         var10 = -1;
      } else {
         var10 = 0;
      }

      return var10;
   }

   void setInitialBuffer(byte[] var1, int var2, int var3) {
      if(var1 != null) {
         if(var1.length == var3) {
            this.buf = var1;
            this.pos = var2;
            this.readPos = var2;
         }
      }
   }
}
