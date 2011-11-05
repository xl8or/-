package org.codehaus.jackson.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import org.codehaus.jackson.io.IOContext;

public final class UTF8Writer extends Writer {

   static final int SURR1_FIRST = 55296;
   static final int SURR1_LAST = 56319;
   static final int SURR2_FIRST = 56320;
   static final int SURR2_LAST = 57343;
   protected final IOContext mContext;
   OutputStream mOut;
   byte[] mOutBuffer;
   final int mOutBufferLast;
   int mOutPtr;
   int mSurrogate = 0;


   public UTF8Writer(IOContext var1, OutputStream var2) {
      this.mContext = var1;
      this.mOut = var2;
      byte[] var3 = var1.allocWriteIOBuffer();
      this.mOutBuffer = var3;
      int var4 = this.mOutBuffer.length - 4;
      this.mOutBufferLast = var4;
      this.mOutPtr = 0;
   }

   private int convertSurrogate(int var1) throws IOException {
      int var2 = this.mSurrogate;
      this.mSurrogate = 0;
      if(var1 >= '\udc00' && var1 <= '\udfff') {
         int var8 = (var2 - '\ud800' << 10) + 65536;
         int var9 = var1 - '\udc00';
         return var8 + var9;
      } else {
         StringBuilder var3 = (new StringBuilder()).append("Broken surrogate pair: first char 0x");
         String var4 = Integer.toHexString(var2);
         StringBuilder var5 = var3.append(var4).append(", second 0x");
         String var6 = Integer.toHexString(var1);
         String var7 = var5.append(var6).append("; illegal combination").toString();
         throw new IOException(var7);
      }
   }

   private void throwIllegal(int var1) throws IOException {
      if(var1 > 1114111) {
         StringBuilder var2 = (new StringBuilder()).append("Illegal character point (0x");
         String var3 = Integer.toHexString(var1);
         String var4 = var2.append(var3).append(") to output; max is 0x10FFFF as per RFC 4627").toString();
         throw new IOException(var4);
      } else if(var1 >= '\ud800') {
         if(var1 <= '\udbff') {
            StringBuilder var5 = (new StringBuilder()).append("Unmatched first part of surrogate pair (0x");
            String var6 = Integer.toHexString(var1);
            String var7 = var5.append(var6).append(")").toString();
            throw new IOException(var7);
         } else {
            StringBuilder var8 = (new StringBuilder()).append("Unmatched second part of surrogate pair (0x");
            String var9 = Integer.toHexString(var1);
            String var10 = var8.append(var9).append(")").toString();
            throw new IOException(var10);
         }
      } else {
         StringBuilder var11 = (new StringBuilder()).append("Illegal character point (0x");
         String var12 = Integer.toHexString(var1);
         String var13 = var11.append(var12).append(") to output").toString();
         throw new IOException(var13);
      }
   }

   public Writer append(char var1) throws IOException {
      this.write(var1);
      return this;
   }

   public void close() throws IOException {
      if(this.mOut != null) {
         if(this.mOutPtr > 0) {
            OutputStream var1 = this.mOut;
            byte[] var2 = this.mOutBuffer;
            int var3 = this.mOutPtr;
            var1.write(var2, 0, var3);
            this.mOutPtr = 0;
         }

         OutputStream var4 = this.mOut;
         this.mOut = null;
         byte[] var5 = this.mOutBuffer;
         if(var5 != null) {
            this.mOutBuffer = null;
            this.mContext.releaseWriteIOBuffer(var5);
         }

         var4.close();
         int var6 = this.mSurrogate;
         this.mSurrogate = 0;
         if(var6 > 0) {
            this.throwIllegal(var6);
         }
      }
   }

   public void flush() throws IOException {
      if(this.mOutPtr > 0) {
         OutputStream var1 = this.mOut;
         byte[] var2 = this.mOutBuffer;
         int var3 = this.mOutPtr;
         var1.write(var2, 0, var3);
         this.mOutPtr = 0;
      }

      this.mOut.flush();
   }

   public void write(int var1) throws IOException {
      int var2;
      if(this.mSurrogate > 0) {
         var2 = this.convertSurrogate(var1);
      } else {
         if(var1 >= '\ud800' && var1 <= '\udfff') {
            if(var1 > '\udbff') {
               this.throwIllegal(var1);
            }

            this.mSurrogate = var1;
            return;
         }

         var2 = var1;
      }

      int var3 = this.mOutPtr;
      int var4 = this.mOutBufferLast;
      if(var3 >= var4) {
         OutputStream var5 = this.mOut;
         byte[] var6 = this.mOutBuffer;
         int var7 = this.mOutPtr;
         var5.write(var6, 0, var7);
         this.mOutPtr = 0;
      }

      if(var2 < 128) {
         byte[] var8 = this.mOutBuffer;
         int var9 = this.mOutPtr;
         int var10 = var9 + 1;
         this.mOutPtr = var10;
         byte var11 = (byte)var2;
         var8[var9] = var11;
      } else {
         int var12 = this.mOutPtr;
         int var19;
         if(var2 < 2048) {
            byte[] var13 = this.mOutBuffer;
            int var14 = var12 + 1;
            byte var15 = (byte)(var2 >> 6 | 192);
            var13[var12] = var15;
            byte[] var16 = this.mOutBuffer;
            int var17 = var14 + 1;
            byte var18 = (byte)(var2 & 63 | 128);
            var16[var14] = var18;
            var19 = var17;
         } else if(var2 <= '\uffff') {
            byte[] var20 = this.mOutBuffer;
            int var21 = var12 + 1;
            byte var22 = (byte)(var2 >> 12 | 224);
            var20[var12] = var22;
            byte[] var23 = this.mOutBuffer;
            int var24 = var21 + 1;
            byte var25 = (byte)(var2 >> 6 & 63 | 128);
            var23[var21] = var25;
            byte[] var26 = this.mOutBuffer;
            int var27 = var24 + 1;
            byte var28 = (byte)(var2 & 63 | 128);
            var26[var24] = var28;
            var19 = var27;
         } else {
            if(var2 > 1114111) {
               this.throwIllegal(var2);
            }

            byte[] var29 = this.mOutBuffer;
            int var30 = var12 + 1;
            byte var31 = (byte)(var2 >> 18 | 240);
            var29[var12] = var31;
            byte[] var32 = this.mOutBuffer;
            int var33 = var30 + 1;
            byte var34 = (byte)(var2 >> 12 & 63 | 128);
            var32[var30] = var34;
            byte[] var35 = this.mOutBuffer;
            int var36 = var33 + 1;
            byte var37 = (byte)(var2 >> 6 & 63 | 128);
            var35[var33] = var37;
            byte[] var38 = this.mOutBuffer;
            int var39 = var36 + 1;
            byte var40 = (byte)(var2 & 63 | 128);
            var38[var36] = var40;
            var19 = var39;
         }

         this.mOutPtr = var19;
      }
   }

   public void write(String var1) throws IOException {
      int var2 = var1.length();
      this.write(var1, 0, var2);
   }

   public void write(String var1, int var2, int var3) throws IOException {
      if(var3 < 2) {
         if(var3 == 1) {
            char var4 = var1.charAt(var2);
            this.write(var4);
         }
      } else {
         int var9;
         int var10;
         if(this.mSurrogate > 0) {
            int var5 = var2 + 1;
            char var6 = var1.charAt(var2);
            int var7 = var3 + -1;
            int var8 = this.convertSurrogate(var6);
            this.write(var8);
            var9 = var5;
            var10 = var7;
         } else {
            var10 = var3;
            var9 = var2;
         }

         int var11 = this.mOutPtr;
         byte[] var12 = this.mOutBuffer;
         int var13 = this.mOutBufferLast;
         int var14 = var10 + var9;
         int var16 = var9;
         int var17 = var11;

         int var45;
         label73:
         while(true) {
            if(var16 >= var14) {
               var45 = var17;
               break;
            }

            if(var17 >= var13) {
               this.mOut.write(var12, 0, var17);
               boolean var18 = false;
            }

            int var19 = var16 + 1;
            char var20 = var1.charAt(var16);
            if(var20 < 128) {
               int var21 = var17 + 1;
               byte var22 = (byte)var20;
               var12[var17] = var22;
               int var23 = var14 - var19;
               int var24 = var13 - var21;
               if(var23 > var24) {
                  var23 = var24;
               }

               int var25 = var23 + var19;
               var16 = var21;

               while(true) {
                  if(var19 >= var25) {
                     continue label73;
                  }

                  var21 = var19 + 1;
                  char var28 = var1.charAt(var19);
                  if(var28 >= 128) {
                     var17 = var28;
                     var19 = var21;
                     break;
                  }

                  int var35 = var16 + 1;
                  byte var36 = (byte)var28;
                  var12[var16] = var36;
                  var16 = var35;
                  var19 = var21;
               }
            } else {
               var16 = var17;
               var17 = var20;
            }

            if(var17 < 2048) {
               int var29 = var16 + 1;
               byte var30 = (byte)(var17 >> 6 | 192);
               var12[var16] = var30;
               int var31 = var29 + 1;
               byte var32 = (byte)(var17 & 63 | 128);
               var12[var29] = var32;
            } else if(var17 >= '\ud800' && var17 <= '\udfff') {
               if(var17 > '\udbff') {
                  this.mOutPtr = var16;
                  this.throwIllegal(var17);
               }

               this.mSurrogate = var17;
               if(var19 >= var14) {
                  var45 = var16;
                  break;
               }

               int var46 = var19 + 1;
               char var47 = var1.charAt(var19);
               int var48 = this.convertSurrogate(var47);
               if(var48 > 1114111) {
                  this.mOutPtr = var16;
                  this.throwIllegal(var48);
               }

               int var49 = var16 + 1;
               byte var50 = (byte)(var48 >> 18 | 240);
               var12[var16] = var50;
               int var51 = var49 + 1;
               byte var52 = (byte)(var48 >> 12 & 63 | 128);
               var12[var49] = var52;
               int var53 = var51 + 1;
               byte var54 = (byte)(var48 >> 6 & 63 | 128);
               var12[var51] = var54;
               int var55 = var53 + 1;
               byte var56 = (byte)(var48 & 63 | 128);
               var12[var53] = var56;
            } else {
               int var37 = var16 + 1;
               byte var38 = (byte)(var17 >> 12 | 224);
               var12[var16] = var38;
               int var39 = var37 + 1;
               byte var40 = (byte)(var17 >> 6 & 63 | 128);
               var12[var37] = var40;
               int var41 = var39 + 1;
               byte var42 = (byte)(var17 & 63 | 128);
               var12[var39] = var42;
            }
         }

         this.mOutPtr = var45;
      }
   }

   public void write(char[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      if(var3 < 2) {
         if(var3 == 1) {
            char var4 = var1[var2];
            this.write(var4);
         }
      } else {
         int var9;
         int var10;
         if(this.mSurrogate > 0) {
            int var5 = var2 + 1;
            char var6 = var1[var2];
            int var7 = var3 + -1;
            int var8 = this.convertSurrogate(var6);
            this.write(var8);
            var9 = var5;
            var10 = var7;
         } else {
            var10 = var3;
            var9 = var2;
         }

         int var11 = this.mOutPtr;
         byte[] var12 = this.mOutBuffer;
         int var13 = this.mOutBufferLast;
         int var14 = var10 + var9;
         int var16 = var9;
         int var17 = var11;

         int var45;
         label73:
         while(true) {
            if(var16 >= var14) {
               var45 = var17;
               break;
            }

            if(var17 >= var13) {
               this.mOut.write(var12, 0, var17);
               boolean var18 = false;
            }

            int var19 = var16 + 1;
            char var20 = var1[var16];
            if(var20 < 128) {
               int var21 = var17 + 1;
               byte var22 = (byte)var20;
               var12[var17] = var22;
               int var23 = var14 - var19;
               int var24 = var13 - var21;
               if(var23 > var24) {
                  var23 = var24;
               }

               int var25 = var23 + var19;
               var16 = var21;

               while(true) {
                  if(var19 >= var25) {
                     continue label73;
                  }

                  var21 = var19 + 1;
                  char var28 = var1[var19];
                  if(var28 >= 128) {
                     var17 = var28;
                     var19 = var21;
                     break;
                  }

                  int var35 = var16 + 1;
                  byte var36 = (byte)var28;
                  var12[var16] = var36;
                  var16 = var35;
                  var19 = var21;
               }
            } else {
               var16 = var17;
               var17 = var20;
            }

            if(var17 < 2048) {
               int var29 = var16 + 1;
               byte var30 = (byte)(var17 >> 6 | 192);
               var12[var16] = var30;
               int var31 = var29 + 1;
               byte var32 = (byte)(var17 & 63 | 128);
               var12[var29] = var32;
            } else if(var17 >= '\ud800' && var17 <= '\udfff') {
               if(var17 > '\udbff') {
                  this.mOutPtr = var16;
                  this.throwIllegal(var17);
               }

               this.mSurrogate = var17;
               if(var19 >= var14) {
                  var45 = var16;
                  break;
               }

               int var46 = var19 + 1;
               char var47 = var1[var19];
               int var48 = this.convertSurrogate(var47);
               if(var48 > 1114111) {
                  this.mOutPtr = var16;
                  this.throwIllegal(var48);
               }

               int var49 = var16 + 1;
               byte var50 = (byte)(var48 >> 18 | 240);
               var12[var16] = var50;
               int var51 = var49 + 1;
               byte var52 = (byte)(var48 >> 12 & 63 | 128);
               var12[var49] = var52;
               int var53 = var51 + 1;
               byte var54 = (byte)(var48 >> 6 & 63 | 128);
               var12[var51] = var54;
               int var55 = var53 + 1;
               byte var56 = (byte)(var48 & 63 | 128);
               var12[var53] = var56;
            } else {
               int var37 = var16 + 1;
               byte var38 = (byte)(var17 >> 12 | 224);
               var12[var16] = var38;
               int var39 = var37 + 1;
               byte var40 = (byte)(var17 >> 6 & 63 | 128);
               var12[var37] = var40;
               int var41 = var39 + 1;
               byte var42 = (byte)(var17 & 63 | 128);
               var12[var39] = var42;
            }
         }

         this.mOutPtr = var45;
      }
   }
}
