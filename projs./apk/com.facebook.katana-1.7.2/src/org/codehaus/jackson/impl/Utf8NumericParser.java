package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.impl.StreamBasedParserBase;
import org.codehaus.jackson.io.IOContext;

public abstract class Utf8NumericParser extends StreamBasedParserBase {

   public Utf8NumericParser(IOContext var1, int var2, InputStream var3, byte[] var4, int var5, int var6, boolean var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   protected final JsonToken parseNumberText(int var1) throws IOException, JsonParseException {
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      int var3 = 0;
      byte var4;
      if(var1 == 45) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      int var13;
      if(var4 != 0) {
         int var5 = 0 + 1;
         var2[0] = 45;
         int var6 = this._inputPtr;
         int var7 = this._inputEnd;
         if(var6 >= var7) {
            this.loadMoreGuaranteed();
         }

         byte[] var8 = this._inputBuffer;
         int var9 = this._inputPtr;
         int var10 = var9 + 1;
         this._inputPtr = var10;
         int var11 = var8[var9] & 255;
         var13 = var11;
         var3 = var5;
      } else {
         var13 = var1;
      }

      boolean var14 = false;
      byte var15 = 0;
      int var16 = var13;
      char[] var17 = var2;
      int var18 = var15;

      char[] var21;
      int var20;
      int var23;
      int var22;
      boolean var24;
      while(true) {
         if(var16 < 48) {
            var20 = var16;
            var21 = var17;
            var22 = var3;
            var23 = var18;
            var24 = var14;
            break;
         }

         if(var16 > 57) {
            var20 = var16;
            var21 = var17;
            var22 = var3;
            var23 = var18;
            var24 = var14;
            break;
         }

         ++var18;
         if(var18 == 2) {
            int var77 = var3 - 1;
            if(var17[var77] == 48) {
               this.reportInvalidNumber("Leading zeroes not allowed");
            }
         }

         int var78 = var17.length;
         if(var3 >= var78) {
            char[] var79 = this._textBuffer.finishCurrentSegment();
            byte var80 = 0;
            var17 = var79;
            var3 = var80;
         }

         int var81 = var3 + 1;
         char var82 = (char)var16;
         var17[var3] = var82;
         int var83 = this._inputPtr;
         int var84 = this._inputEnd;
         if(var83 >= var84 && !this.loadMore()) {
            var20 = 0;
            var23 = var18;
            boolean var85 = true;
            var21 = var17;
            var22 = var81;
            break;
         }

         byte[] var86 = this._inputBuffer;
         int var87 = this._inputPtr;
         int var88 = var87 + 1;
         this._inputPtr = var88;
         var16 = var86[var87] & 255;
         var3 = var81;
      }

      if(var23 == 0) {
         StringBuilder var25 = (new StringBuilder()).append("Missing integer part (next char ");
         String var26 = _getCharDesc(var20);
         String var27 = var25.append(var26).append(")").toString();
         this.reportInvalidNumber(var27);
      }

      byte var28 = 0;
      int var34;
      int var32;
      char[] var33;
      boolean var38;
      int var39;
      if(var20 == 46) {
         int var29 = var22 + 1;
         char var30 = (char)var20;
         var21[var22] = var30;
         int var31 = var28;
         var32 = var20;
         var33 = var21;
         var34 = var29;

         while(true) {
            int var35 = this._inputPtr;
            int var36 = this._inputEnd;
            if(var35 >= var36 && !this.loadMore()) {
               var24 = true;
               break;
            }

            byte[] var89 = this._inputBuffer;
            int var90 = this._inputPtr;
            int var91 = var90 + 1;
            this._inputPtr = var91;
            int var92 = var89[var90] & 255;
            if(var92 < 48 || var92 > 57) {
               break;
            }

            ++var31;
            int var93 = var33.length;
            if(var34 >= var93) {
               char[] var94 = this._textBuffer.finishCurrentSegment();
               byte var95 = 0;
               var33 = var94;
               var34 = var95;
            }

            int var96 = var34 + 1;
            char var97 = (char)var92;
            var33[var34] = var97;
            var34 = var96;
         }

         if(var31 == 0) {
            this.reportUnexpectedNumberChar(var20, "Decimal point not followed by a digit");
         }

         var38 = var24;
         var39 = var31;
      } else {
         var32 = var20;
         var33 = var21;
         var34 = var22;
         var38 = var24;
         var39 = var28;
      }

      byte var40 = 0;
      int var63;
      int var74;
      boolean var75;
      if(var32 != 101 && var32 != 69) {
         var63 = var34;
         var75 = var38;
         var74 = var40;
      } else {
         int var41 = var33.length;
         if(var34 >= var41) {
            char[] var42 = this._textBuffer.finishCurrentSegment();
            byte var43 = 0;
            var33 = var42;
            var34 = var43;
         }

         int var44 = var34 + 1;
         char var45 = (char)var32;
         var33[var34] = var45;
         int var46 = this._inputPtr;
         int var47 = this._inputEnd;
         if(var46 >= var47) {
            this.loadMoreGuaranteed();
         }

         byte[] var48 = this._inputBuffer;
         int var49 = this._inputPtr;
         int var50 = var49 + 1;
         this._inputPtr = var50;
         int var51 = var48[var49] & 255;
         int var65;
         char[] var106;
         int var107;
         if(var51 != 45 && var51 != 43) {
            var106 = var33;
            var63 = var44;
            var107 = var51;
            var65 = var40;
         } else {
            int var52 = var33.length;
            int var55;
            if(var44 >= var52) {
               char[] var53 = this._textBuffer.finishCurrentSegment();
               byte var54 = 0;
               var106 = var53;
               var55 = var54;
            } else {
               var106 = var33;
               var55 = var44;
            }

            var44 = var55 + 1;
            char var56 = (char)var51;
            var106[var55] = var56;
            int var57 = this._inputPtr;
            int var58 = this._inputEnd;
            if(var57 >= var58) {
               this.loadMoreGuaranteed();
            }

            byte[] var59 = this._inputBuffer;
            int var60 = this._inputPtr;
            int var61 = var60 + 1;
            this._inputPtr = var61;
            int var62 = var59[var60] & 255;
            var63 = var44;
            var107 = var62;
            var65 = var40;
         }

         while(true) {
            if(var107 > 57 || var107 < 48) {
               var75 = var38;
               var74 = var65;
               break;
            }

            ++var65;
            int var66 = var106.length;
            if(var63 >= var66) {
               char[] var67 = this._textBuffer.finishCurrentSegment();
               boolean var68 = false;
               var106 = var67;
            }

            var44 = var63 + 1;
            char var70 = (char)var107;
            var106[var63] = var70;
            int var71 = this._inputPtr;
            int var72 = this._inputEnd;
            if(var71 >= var72 && !this.loadMore()) {
               var63 = var44;
               boolean var73 = true;
               var74 = var65;
               var75 = var73;
               break;
            }

            byte[] var98 = this._inputBuffer;
            int var99 = this._inputPtr;
            int var100 = var99 + 1;
            this._inputPtr = var100;
            var107 = var98[var99] & 255;
         }

         if(var74 == 0) {
            this.reportUnexpectedNumberChar(var107, "Exponent indicator not followed by a digit");
         }
      }

      if(!var75) {
         int var76 = this._inputPtr - 1;
         this._inputPtr = var76;
      }

      this._textBuffer.setCurrentLength(var63);
      return this.reset((boolean)var4, var23, var39, var74);
   }
}
