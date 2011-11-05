package org.codehaus.jackson.io;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.io.BaseReader;
import org.codehaus.jackson.io.IOContext;

public final class UTF32Reader extends BaseReader {

   final boolean mBigEndian;
   int mByteCount = 0;
   int mCharCount = 0;
   char mSurrogate = 0;


   public UTF32Reader(IOContext var1, InputStream var2, byte[] var3, int var4, int var5, boolean var6) {
      super(var1, var2, var3, var4, var5);
      this.mBigEndian = var6;
   }

   private boolean loadMore(int var1) throws IOException {
      int var2 = this.mByteCount;
      int var3 = this.mLength - var1;
      int var4 = var2 + var3;
      this.mByteCount = var4;
      boolean var23;
      if(var1 > 0) {
         if(this.mPtr > 0) {
            int var10;
            for(byte var5 = 0; var5 < var1; var10 = var5 + 1) {
               byte[] var6 = this.mBuffer;
               byte[] var7 = this.mBuffer;
               int var8 = this.mPtr + var5;
               byte var9 = var7[var8];
               var6[var5] = var9;
            }

            this.mPtr = 0;
         }

         this.mLength = var1;
      } else {
         this.mPtr = 0;
         InputStream var21 = this.mIn;
         byte[] var22 = this.mBuffer;
         int var24 = var21.read(var22);
         if(var24 < 1) {
            this.mLength = 0;
            if(var24 < 0) {
               this.freeBuffers();
               var23 = false;
               return var23;
            }

            this.reportStrangeStream();
         }

         this.mLength = var24;
      }

      while(this.mLength < 4) {
         InputStream var11 = this.mIn;
         byte[] var12 = this.mBuffer;
         int var13 = this.mLength;
         int var14 = this.mBuffer.length;
         int var15 = this.mLength;
         int var16 = var14 - var15;
         int var17 = var11.read(var12, var13, var16);
         if(var17 < 1) {
            if(var17 < 0) {
               this.freeBuffers();
               int var18 = this.mLength;
               this.reportUnexpectedEOF(var18, 4);
            }

            this.reportStrangeStream();
         }

         int var19 = this.mLength;
         int var20 = var17 + var19;
         this.mLength = var20;
      }

      var23 = true;
      return var23;
   }

   private void reportInvalid(int var1, int var2, String var3) throws IOException {
      int var4 = this.mByteCount;
      int var5 = this.mPtr;
      int var6 = var4 + var5 - 1;
      int var7 = this.mCharCount + var2;
      StringBuilder var8 = (new StringBuilder()).append("Invalid UTF-32 character 0x");
      String var9 = Integer.toHexString(var1);
      String var10 = var8.append(var9).append(var3).append(" at char #").append(var7).append(", byte #").append(var6).append(")").toString();
      throw new CharConversionException(var10);
   }

   private void reportUnexpectedEOF(int var1, int var2) throws IOException {
      int var3 = this.mByteCount + var1;
      int var4 = this.mCharCount;
      String var5 = "Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + var1 + ", needed " + var2 + ", at char #" + var4 + ", byte #" + var3 + ")";
      throw new CharConversionException(var5);
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      boolean var4 = true;
      int var5;
      if(this.mBuffer == null) {
         var5 = -1;
      } else if(var3 < 1) {
         var5 = var3;
      } else {
         label58: {
            if(var2 >= 0) {
               int var6 = var2 + var3;
               int var7 = var1.length;
               if(var6 <= var7) {
                  break label58;
               }
            }

            this.reportBounds(var1, var2, var3);
         }

         int var8 = var3 + var2;
         int var9;
         if(this.mSurrogate != 0) {
            var9 = var2 + 1;
            char var10 = this.mSurrogate;
            var1[var2] = var10;
            this.mSurrogate = 0;
         } else {
            int var36 = this.mLength;
            int var37 = this.mPtr;
            int var38 = var36 - var37;
            if(var38 < 4 && !this.loadMore(var38)) {
               var5 = -1;
               return var5;
            }

            var9 = var2;
         }

         int var34;
         while(true) {
            if(var9 >= var8) {
               var34 = var9;
               break;
            }

            int var11 = this.mPtr;
            int var23;
            if(this.mBigEndian) {
               int var12 = this.mBuffer[var11] << 24;
               byte[] var13 = this.mBuffer;
               int var14 = var11 + 1;
               int var15 = (var13[var14] & 255) << 16;
               int var16 = var12 | var15;
               byte[] var17 = this.mBuffer;
               int var18 = var11 + 2;
               int var19 = (var17[var18] & 255) << 8;
               int var20 = var16 | var19;
               byte[] var21 = this.mBuffer;
               int var22 = var11 + 3;
               var23 = var21[var22] & 255 | var20;
            } else {
               int var39 = this.mBuffer[var11] & 255;
               byte[] var40 = this.mBuffer;
               int var41 = var11 + 1;
               int var42 = (var40[var41] & 255) << 8;
               int var43 = var39 | var42;
               byte[] var44 = this.mBuffer;
               int var45 = var11 + 2;
               int var46 = (var44[var45] & 255) << 16;
               int var47 = var43 | var46;
               byte[] var48 = this.mBuffer;
               int var49 = var11 + 3;
               var23 = var48[var49] << 24 | var47;
            }

            int var24 = this.mPtr + 4;
            this.mPtr = var24;
            int var50;
            int var55;
            if(var23 > '\uffff') {
               if(var23 > 1114111) {
                  int var25 = var9 - var2;
                  StringBuilder var26 = (new StringBuilder()).append("(above ");
                  String var27 = Integer.toHexString(1114111);
                  String var28 = var26.append(var27).append(") ").toString();
                  this.reportInvalid(var23, var25, var28);
               }

               int var29 = var23 - 65536;
               var55 = var9 + 1;
               int var30 = var29 >> 10;
               char var31 = (char)('\ud800' + var30);
               var1[var9] = var31;
               int var32 = var29 & 1023;
               var9 = '\udc00' | var32;
               if(var55 >= var8) {
                  char var33 = (char)var9;
                  this.mSurrogate = var33;
                  var34 = var55;
                  break;
               }

               var50 = var55;
            } else {
               var50 = var9;
               var9 = var23;
            }

            var55 = var50 + 1;
            char var51 = (char)var9;
            var1[var50] = var51;
            var9 = this.mPtr;
            int var52 = this.mLength;
            if(var9 >= var52) {
               var34 = var55;
               break;
            }
         }

         var5 = var34 - var2;
         int var35 = this.mCharCount + var5;
         this.mCharCount = var35;
      }

      return var5;
   }
}
