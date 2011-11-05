package org.acra.util;

import java.io.UnsupportedEncodingException;

public class Base64 {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   public static final int CRLF = 4;
   public static final int DEFAULT = 0;
   public static final int NO_CLOSE = 16;
   public static final int NO_PADDING = 1;
   public static final int NO_WRAP = 2;
   public static final int URL_SAFE = 8;


   static {
      byte var0;
      if(!Base64.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   private Base64() {}

   public static byte[] decode(String var0, int var1) {
      return decode(var0.getBytes(), var1);
   }

   public static byte[] decode(byte[] var0, int var1) {
      int var2 = var0.length;
      return decode(var0, 0, var2, var1);
   }

   public static byte[] decode(byte[] var0, int var1, int var2, int var3) {
      byte[] var4 = new byte[var2 * 3 / 4];
      Base64.Decoder var5 = new Base64.Decoder(var3, var4);
      if(!var5.process(var0, var1, var2, (boolean)1)) {
         throw new IllegalArgumentException("bad base-64");
      } else {
         int var6 = var5.op;
         int var7 = var5.output.length;
         byte[] var8;
         if(var6 == var7) {
            var8 = var5.output;
         } else {
            byte[] var9 = new byte[var5.op];
            byte[] var10 = var5.output;
            int var11 = var5.op;
            System.arraycopy(var10, 0, var9, 0, var11);
            var8 = var9;
         }

         return var8;
      }
   }

   public static byte[] encode(byte[] var0, int var1) {
      int var2 = var0.length;
      return encode(var0, 0, var2, var1);
   }

   public static byte[] encode(byte[] var0, int var1, int var2, int var3) {
      Base64.Encoder var4 = new Base64.Encoder(var3, (byte[])null);
      int var5 = var2 / 3 * 4;
      if(var4.do_padding) {
         if(var2 % 3 > 0) {
            var5 += 4;
         }
      } else {
         switch(var2 % 3) {
         case 0:
         default:
            break;
         case 1:
            var5 += 2;
            break;
         case 2:
            var5 += 3;
         }
      }

      if(var4.do_newline && var2 > 0) {
         int var6 = (var2 - 1) / 57 + 1;
         byte var7;
         if(var4.do_cr) {
            var7 = 2;
         } else {
            var7 = 1;
         }

         int var8 = var6 * var7;
         var5 += var8;
      }

      byte[] var9 = new byte[var5];
      var4.output = var9;
      var4.process(var0, var1, var2, (boolean)1);
      if(!$assertionsDisabled && var4.op != var5) {
         throw new AssertionError();
      } else {
         return var4.output;
      }
   }

   public static String encodeToString(byte[] var0, int var1) {
      try {
         byte[] var2 = encode(var0, var1);
         String var3 = new String(var2, "US-ASCII");
         return var3;
      } catch (UnsupportedEncodingException var5) {
         throw new AssertionError(var5);
      }
   }

   public static String encodeToString(byte[] var0, int var1, int var2, int var3) {
      try {
         byte[] var4 = encode(var0, var1, var2, var3);
         String var5 = new String(var4, "US-ASCII");
         return var5;
      } catch (UnsupportedEncodingException var7) {
         throw new AssertionError(var7);
      }
   }

   abstract static class Coder {

      public int op;
      public byte[] output;


      Coder() {}

      public abstract int maxOutputSize(int var1);

      public abstract boolean process(byte[] var1, int var2, int var3, boolean var4);
   }

   static class Decoder extends Base64.Coder {

      private static final int[] DECODE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
      private static final int[] DECODE_WEBSAFE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
      private static final int EQUALS = 254;
      private static final int SKIP = 255;
      private final int[] alphabet;
      private int state;
      private int value;


      public Decoder(int var1, byte[] var2) {
         this.output = var2;
         int[] var3;
         if((var1 & 8) == 0) {
            var3 = DECODE;
         } else {
            var3 = DECODE_WEBSAFE;
         }

         this.alphabet = var3;
         this.state = 0;
         this.value = 0;
      }

      public int maxOutputSize(int var1) {
         return var1 * 3 / 4 + 10;
      }

      public boolean process(byte[] var1, int var2, int var3, boolean var4) {
         boolean var5;
         if(this.state == 6) {
            var5 = false;
         } else {
            int var6 = var2;
            var3 += var2;
            int var7 = this.state;
            int var8 = this.value;
            int var9 = 0;
            byte[] var10 = this.output;
            int[] var11 = this.alphabet;

            while(true) {
               int var30;
               if(var6 < var3) {
                  label93: {
                     if(var7 == 0) {
                        while(var6 + 4 <= var3) {
                           int var12 = var1[var6] & 255;
                           int var13 = var11[var12] << 18;
                           int var14 = var6 + 1;
                           int var15 = var1[var14] & 255;
                           int var16 = var11[var15] << 12;
                           int var17 = var13 | var16;
                           int var18 = var6 + 2;
                           int var19 = var1[var18] & 255;
                           int var20 = var11[var19] << 6;
                           int var21 = var17 | var20;
                           int var22 = var6 + 3;
                           int var23 = var1[var22] & 255;
                           int var24 = var11[var23];
                           var8 = var21 | var24;
                           if(var8 < 0) {
                              break;
                           }

                           int var25 = var9 + 2;
                           byte var26 = (byte)var8;
                           var10[var25] = var26;
                           int var27 = var9 + 1;
                           byte var28 = (byte)(var8 >> 8);
                           var10[var27] = var28;
                           byte var29 = (byte)(var8 >> 16);
                           var10[var9] = var29;
                           var9 += 3;
                           var6 += 4;
                        }

                        if(var6 >= var3) {
                           var30 = var9;
                           break label93;
                        }
                     }

                     int var31 = var6 + 1;
                     int var32 = var1[var6] & 255;
                     int var33 = var11[var32];
                     switch(var7) {
                     case 0:
                        if(var33 >= 0) {
                           var8 = var33;
                           ++var7;
                        } else if(var33 != -1) {
                           this.state = 6;
                           var5 = false;
                           return var5;
                        }
                        break;
                     case 1:
                        if(var33 >= 0) {
                           var8 = var8 << 6 | var33;
                           ++var7;
                        } else if(var33 != -1) {
                           this.state = 6;
                           var5 = false;
                           return var5;
                        }
                        break;
                     case 2:
                        if(var33 >= 0) {
                           var8 = var8 << 6 | var33;
                           ++var7;
                        } else if(var33 == -1) {
                           int var34 = var9 + 1;
                           byte var35 = (byte)(var8 >> 4);
                           var10[var9] = var35;
                           var7 = 4;
                           var9 = var34;
                        } else if(var33 != -1) {
                           this.state = 6;
                           var5 = false;
                           return var5;
                        }
                        break;
                     case 3:
                        if(var33 >= 0) {
                           var8 = var8 << 6 | var33;
                           int var36 = var9 + 2;
                           byte var37 = (byte)var8;
                           var10[var36] = var37;
                           int var38 = var9 + 1;
                           byte var39 = (byte)(var8 >> 8);
                           var10[var38] = var39;
                           byte var40 = (byte)(var8 >> 16);
                           var10[var9] = var40;
                           var9 += 3;
                           var7 = 0;
                        } else if(var33 == -1) {
                           int var41 = var9 + 1;
                           byte var42 = (byte)(var8 >> 2);
                           var10[var41] = var42;
                           byte var43 = (byte)(var8 >> 10);
                           var10[var9] = var43;
                           var9 += 2;
                           var7 = 5;
                        } else if(var33 != -1) {
                           this.state = 6;
                           var5 = false;
                           return var5;
                        }
                        break;
                     case 4:
                        if(var33 == -1) {
                           ++var7;
                        } else if(var33 != -1) {
                           this.state = 6;
                           var5 = false;
                           return var5;
                        }
                        break;
                     case 5:
                        if(var33 != -1) {
                           this.state = 6;
                           var5 = false;
                           return var5;
                        }
                     }

                     var6 = var31;
                     continue;
                  }
               } else {
                  var30 = var9;
               }

               if(!var4) {
                  this.state = var7;
                  this.value = var8;
                  this.op = var30;
                  var5 = true;
               } else {
                  switch(var7) {
                  case 0:
                     var9 = var30;
                     break;
                  case 1:
                     this.state = 6;
                     var5 = false;
                     return var5;
                  case 2:
                     var9 = var30 + 1;
                     byte var44 = (byte)(var8 >> 4);
                     var10[var30] = var44;
                     break;
                  case 3:
                     int var45 = var30 + 1;
                     byte var46 = (byte)(var8 >> 10);
                     var10[var30] = var46;
                     int var47 = var45 + 1;
                     byte var48 = (byte)(var8 >> 2);
                     var10[var45] = var48;
                     var9 = var47;
                     break;
                  case 4:
                     this.state = 6;
                     var5 = false;
                     return var5;
                  default:
                     var9 = var30;
                  }

                  this.state = var7;
                  this.op = var9;
                  var5 = true;
               }
               break;
            }
         }

         return var5;
      }
   }

   static class Encoder extends Base64.Coder {

      // $FF: synthetic field
      static final boolean $assertionsDisabled = false;
      private static final byte[] ENCODE;
      private static final byte[] ENCODE_WEBSAFE;
      public static final int LINE_GROUPS = 19;
      private final byte[] alphabet;
      private int count;
      public final boolean do_cr;
      public final boolean do_newline;
      public final boolean do_padding;
      private final byte[] tail;
      int tailLen;


      static {
         byte var0;
         if(!Base64.class.desiredAssertionStatus()) {
            var0 = 1;
         } else {
            var0 = 0;
         }

         $assertionsDisabled = (boolean)var0;
         ENCODE = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)43, (byte)47};
         ENCODE_WEBSAFE = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)45, (byte)95};
      }

      public Encoder(int var1, byte[] var2) {
         this.output = var2;
         byte var3;
         if((var1 & 1) == 0) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         this.do_padding = (boolean)var3;
         byte var4;
         if((var1 & 2) == 0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         this.do_newline = (boolean)var4;
         byte var5;
         if((var1 & 4) != 0) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         this.do_cr = (boolean)var5;
         byte[] var6;
         if((var1 & 8) == 0) {
            var6 = ENCODE;
         } else {
            var6 = ENCODE_WEBSAFE;
         }

         this.alphabet = var6;
         byte[] var7 = new byte[2];
         this.tail = var7;
         this.tailLen = 0;
         byte var8;
         if(this.do_newline) {
            var8 = 19;
         } else {
            var8 = -1;
         }

         this.count = var8;
      }

      public int maxOutputSize(int var1) {
         return var1 * 8 / 5 + 10;
      }

      public boolean process(byte[] var1, int var2, int var3, boolean var4) {
         byte[] var5 = this.alphabet;
         byte[] var6 = this.output;
         byte var7 = 0;
         int var8 = this.count;
         int var9 = var2;
         int var10 = var3 + var2;
         int var10000;
         switch(this.tailLen) {
         case 0:
         default:
            break;
         case 1:
            int var47 = var2 + 2;
            if(var47 <= var10) {
               int var49 = (this.tail[0] & 255) << 16;
               int var50 = var2 + 1;
               int var51 = (var1[var2] & 255) << 8;
               int var52 = var49 | var51;
               var9 = var50 + 1;
               int var53 = var1[var50] & 255;
               var10000 = var52 | var53;
               this.tailLen = 0;
            }
            break;
         case 2:
            int var55 = var2 + 1;
            if(var55 <= var10) {
               int var57 = (this.tail[0] & 255) << 16;
               int var58 = (this.tail[1] & 255) << 8;
               int var59 = var57 | var58;
               int var60 = var2 + 1;
               int var61 = var1[var2] & 255;
               var10000 = var59 | var61;
               this.tailLen = 0;
               var9 = var60;
            }
         }

         int var23;
         int var24;
         int var127;
         if(-1 != -1) {
            int var11 = 0 + 1;
            int var12 = -1 >> 18 & 63;
            byte var13 = var5[var12];
            var6[0] = var13;
            int var14 = var11 + 1;
            int var15 = -1 >> 12 & 63;
            byte var16 = var5[var15];
            var6[var11] = var16;
            int var17 = var14 + 1;
            int var18 = -1 >> 6 & 63;
            byte var19 = var5[var18];
            var6[var14] = var19;
            var127 = var17 + 1;
            int var20 = -1 & 63;
            byte var21 = var5[var20];
            var6[var17] = var21;
            if(var8 + -1 == 0) {
               if(this.do_cr) {
                  int var22 = var127 + 1;
                  var6[var127] = 13;
                  var127 = var22;
               }

               var23 = var127 + 1;
               var6[var127] = 10;
               var8 = 19;
               var24 = var9;
            } else {
               var24 = var9;
               var23 = var127;
            }
         } else {
            var24 = var9;
            var23 = var7;
         }

         while(true) {
            while(true) {
               int var25 = var24 + 3;
               if(var25 > var10) {
                  int var79;
                  if(var4) {
                     int var70;
                     label110: {
                        int var63 = this.tailLen;
                        int var64 = var24 - var63;
                        int var65 = var10 - 1;
                        int var66;
                        if(var64 == var65) {
                           var66 = 0;
                           byte var69;
                           if(this.tailLen > 0) {
                              byte[] var67 = this.tail;
                              int var68 = var66 + 1;
                              var69 = var67[var66];
                              var66 = var68;
                              var70 = var24;
                           } else {
                              var70 = var24 + 1;
                              var69 = var1[var24];
                           }

                           int var71 = (var69 & 255) << 4;
                           int var72 = this.tailLen - var66;
                           this.tailLen = var72;
                           int var73 = var23 + 1;
                           int var74 = var71 >> 6 & 63;
                           byte var75 = var5[var74];
                           var6[var23] = var75;
                           var23 = var73 + 1;
                           int var76 = var71 & 63;
                           byte var77 = var5[var76];
                           var6[var73] = var77;
                           if(this.do_padding) {
                              int var78 = var23 + 1;
                              var6[var23] = 61;
                              var23 = var78 + 1;
                              var6[var78] = 61;
                           }

                           var79 = var23;
                           if(!this.do_newline) {
                              break label110;
                           }

                           if(this.do_cr) {
                              int var80 = var23 + 1;
                              var6[var23] = 13;
                              var79 = var80;
                           }

                           var23 = var79 + 1;
                           var6[var79] = 10;
                        } else {
                           int var81 = this.tailLen;
                           int var82 = var24 - var81;
                           int var83 = var10 - 2;
                           if(var82 != var83) {
                              if(this.do_newline && var23 > 0 && var8 != 19) {
                                 if(this.do_cr) {
                                    var127 = var23 + 1;
                                    var6[var23] = 13;
                                 } else {
                                    var127 = var23;
                                 }

                                 int var107 = var127 + 1;
                                 var6[var127] = 10;
                              }

                              var70 = var24;
                              var79 = var23;
                              break label110;
                           }

                           byte var84 = 0;
                           byte var87;
                           if(this.tailLen > 1) {
                              byte[] var85 = this.tail;
                              int var86 = var84 + 1;
                              var87 = var85[var84];
                              var66 = var86;
                              var70 = var24;
                           } else {
                              var70 = var24 + 1;
                              var87 = var1[var24];
                           }

                           int var88 = (var87 & 255) << 10;
                           byte var91;
                           if(this.tailLen > 0) {
                              byte[] var89 = this.tail;
                              int var90 = var66 + 1;
                              var91 = var89[var66];
                              var66 = var90;
                           } else {
                              int var106 = var70 + 1;
                              var91 = var1[var70];
                              var70 = var106;
                           }

                           int var92 = (var91 & 255) << 2;
                           int var93 = var88 | var92;
                           int var94 = this.tailLen - var66;
                           this.tailLen = var94;
                           int var95 = var23 + 1;
                           int var96 = var93 >> 12 & 63;
                           byte var97 = var5[var96];
                           var6[var23] = var97;
                           int var98 = var95 + 1;
                           int var99 = var93 >> 6 & 63;
                           byte var100 = var5[var99];
                           var6[var95] = var100;
                           var79 = var98 + 1;
                           int var101 = var93 & 63;
                           byte var102 = var5[var101];
                           var6[var98] = var102;
                           if(this.do_padding) {
                              int var103 = var79 + 1;
                              var6[var79] = 61;
                           }

                           if(!this.do_newline) {
                              break label110;
                           }

                           if(this.do_cr) {
                              int var105 = var79 + 1;
                              var6[var79] = 13;
                              var79 = var105;
                           }

                           var23 = var79 + 1;
                           var6[var79] = 10;
                        }

                        var79 = var23;
                     }

                     if(!$assertionsDisabled && this.tailLen != 0) {
                        throw new AssertionError();
                     }

                     if(!$assertionsDisabled && var70 != var10) {
                        throw new AssertionError();
                     }
                  } else {
                     int var110 = var10 - 1;
                     if(var24 == var110) {
                        byte[] var111 = this.tail;
                        int var112 = this.tailLen;
                        int var113 = var112 + 1;
                        this.tailLen = var113;
                        byte var114 = var1[var24];
                        var111[var112] = var114;
                        var79 = var23;
                     } else {
                        int var116 = var10 - 2;
                        if(var24 == var116) {
                           byte[] var117 = this.tail;
                           int var118 = this.tailLen;
                           int var119 = var118 + 1;
                           this.tailLen = var119;
                           byte var120 = var1[var24];
                           var117[var118] = var120;
                           byte[] var121 = this.tail;
                           int var122 = this.tailLen;
                           int var123 = var122 + 1;
                           this.tailLen = var123;
                           int var124 = var24 + 1;
                           byte var125 = var1[var124];
                           var121[var122] = var125;
                        }

                        var79 = var23;
                     }
                  }

                  this.op = var79;
                  this.count = var8;
                  return true;
               }

               int var27 = (var1[var24] & 255) << 16;
               int var28 = var24 + 1;
               int var29 = (var1[var28] & 255) << 8;
               int var30 = var27 | var29;
               int var31 = var24 + 2;
               int var32 = var1[var31] & 255;
               int var33 = var30 | var32;
               int var34 = var33 >> 18 & 63;
               byte var35 = var5[var34];
               var6[var23] = var35;
               int var36 = var23 + 1;
               int var37 = var33 >> 12 & 63;
               byte var38 = var5[var37];
               var6[var36] = var38;
               int var39 = var23 + 2;
               int var40 = var33 >> 6 & 63;
               byte var41 = var5[var40];
               var6[var39] = var41;
               int var42 = var23 + 3;
               int var43 = var33 & 63;
               byte var44 = var5[var43];
               var6[var42] = var44;
               var9 = var24 + 3;
               var127 = var23 + 4;
               if(var8 + -1 != 0) {
                  var24 = var9;
                  var23 = var127;
               } else {
                  if(this.do_cr) {
                     int var45 = var127 + 1;
                     var6[var127] = 13;
                     var127 = var45;
                  }

                  int var46 = var127 + 1;
                  var6[var127] = 10;
                  var8 = 19;
                  var24 = var9;
               }
            }
         }
      }
   }
}
