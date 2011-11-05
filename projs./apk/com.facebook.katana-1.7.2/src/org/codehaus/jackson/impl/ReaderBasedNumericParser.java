package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.Reader;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.impl.ReaderBasedParserBase;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.util.TextBuffer;

public abstract class ReaderBasedNumericParser extends ReaderBasedParserBase {

   public ReaderBasedNumericParser(IOContext var1, int var2, Reader var3) {
      super(var1, var2, var3);
   }

   private final JsonToken parseNumberText2(boolean var1) throws IOException, JsonParseException {
      char[] var2 = this._textBuffer.emptyAndGetCurrentSegment();
      int var3;
      if(var1) {
         var3 = 0 + 1;
         var2[0] = 45;
      } else {
         var3 = 0;
      }

      char[] var4 = var2;
      int var5 = 0;

      boolean var8;
      char var9;
      while(true) {
         int var6 = this._inputPtr;
         int var7 = this._inputEnd;
         if(var6 >= var7 && !this.loadMore()) {
            var8 = true;
            var9 = 0;
            break;
         }

         char[] var53 = this._inputBuffer;
         int var54 = this._inputPtr;
         int var55 = var54 + 1;
         this._inputPtr = var55;
         char var56 = var53[var54];
         if(var56 < 48) {
            var9 = var56;
            var8 = false;
            break;
         }

         if(var56 > 57) {
            var9 = var56;
            var8 = false;
            break;
         }

         ++var5;
         if(var5 == 2) {
            int var57 = var3 - 1;
            if(var4[var57] == 48) {
               this.reportInvalidNumber("Leading zeroes not allowed");
            }
         }

         int var58 = var4.length;
         if(var3 >= var58) {
            var4 = this._textBuffer.finishCurrentSegment();
            var3 = 0;
         }

         int var59 = var3 + 1;
         var4[var3] = var56;
         var3 = var59;
      }

      if(var5 == 0) {
         StringBuilder var10 = (new StringBuilder()).append("Missing integer part (next char ");
         String var11 = _getCharDesc(var9);
         String var12 = var10.append(var11).append(")").toString();
         this.reportInvalidNumber(var12);
      }

      int var15;
      char[] var16;
      boolean var21;
      char var20;
      if(var9 == 46) {
         int var13 = var3 + 1;
         var4[var3] = var9;
         var3 = 0;
         var15 = var13;
         var16 = var4;
         char var17 = var9;

         while(true) {
            int var18 = this._inputPtr;
            int var19 = this._inputEnd;
            if(var18 >= var19 && !this.loadMore()) {
               var20 = var17;
               var21 = true;
               break;
            }

            char[] var60 = this._inputBuffer;
            int var61 = this._inputPtr;
            int var62 = var61 + 1;
            this._inputPtr = var62;
            var17 = var60[var61];
            if(var17 < 48) {
               var20 = var17;
               var21 = var8;
               break;
            }

            if(var17 > 57) {
               var20 = var17;
               var21 = var8;
               break;
            }

            ++var3;
            int var64 = var16.length;
            if(var15 >= var64) {
               char[] var65 = this._textBuffer.finishCurrentSegment();
               boolean var66 = false;
            }

            int var67 = var15 + 1;
            var16[var15] = var17;
         }

         if(var3 == 0) {
            this.reportUnexpectedNumberChar(var20, "Decimal point not followed by a digit");
         }
      } else {
         var16 = var4;
         var21 = var8;
         var20 = var9;
         var15 = var3;
         var3 = 0;
      }

      boolean var51;
      int var76;
      if(var20 != 101 && var20 != 69) {
         var51 = var21;
         var76 = 0;
      } else {
         int var22 = var16.length;
         if(var15 >= var22) {
            char[] var23 = this._textBuffer.finishCurrentSegment();
            boolean var24 = false;
         }

         int var25 = var15 + 1;
         var16[var15] = var20;
         int var26 = this._inputPtr;
         int var27 = this._inputEnd;
         char var31;
         if(var26 < var27) {
            char[] var28 = this._inputBuffer;
            int var29 = this._inputPtr;
            int var30 = var29 + 1;
            this._inputPtr = var30;
            var31 = var28[var29];
         } else {
            var31 = this.getNextChar("expected a digit for number exponent");
         }

         char var42;
         int var43;
         char[] var45;
         int var77;
         if(var31 != 45 && var31 != 43) {
            var42 = var31;
            var43 = 0;
            var45 = var16;
            var77 = var25;
         } else {
            int var32 = var16.length;
            int var34;
            char[] var33;
            if(var25 >= var32) {
               var33 = this._textBuffer.finishCurrentSegment();
               var34 = 0;
            } else {
               var34 = var25;
            }

            int var35 = var34 + 1;
            var33[var34] = var31;
            int var36 = this._inputPtr;
            int var37 = this._inputEnd;
            char var41;
            if(var36 < var37) {
               char[] var38 = this._inputBuffer;
               int var39 = this._inputPtr;
               int var40 = var39 + 1;
               this._inputPtr = var40;
               var41 = var38[var39];
            } else {
               var41 = this.getNextChar("expected a digit for number exponent");
            }

            var42 = var41;
            var43 = 0;
            var45 = var33;
            var77 = var35;
         }

         while(true) {
            if(var42 > 57 || var42 < 48) {
               var51 = var21;
               var76 = var43;
               break;
            }

            ++var43;
            int var46 = var45.length;
            if(var77 >= var46) {
               var45 = this._textBuffer.finishCurrentSegment();
               boolean var47 = false;
            }

            int var48 = var77 + 1;
            var45[var77] = var42;
            int var49 = this._inputPtr;
            int var50 = this._inputEnd;
            if(var49 >= var50 && !this.loadMore()) {
               var76 = var43;
               var77 = var48;
               var51 = true;
               break;
            }

            char[] var69 = this._inputBuffer;
            int var70 = this._inputPtr;
            int var71 = var70 + 1;
            this._inputPtr = var71;
            var42 = var69[var70];
         }

         if(var76 == 0) {
            this.reportUnexpectedNumberChar(var42, "Exponent indicator not followed by a digit");
         }

         var15 = var77;
      }

      if(!var51) {
         int var52 = this._inputPtr - 1;
         this._inputPtr = var52;
      }

      this._textBuffer.setCurrentLength(var15);
      return this.reset(var1, var5, var3, var76);
   }

   protected final JsonToken parseNumberText(int var1) throws IOException, JsonParseException {
      boolean var2 = true;
      boolean var3 = true;
      byte var4;
      if(var1 == 45) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      int var6;
      int var9;
      JsonToken var10;
      label125: {
         int var5 = this._inputPtr;
         var6 = var5 - 1;
         int var7 = this._inputEnd;
         if(var4 != 0) {
            int var8 = this._inputEnd;
            if(var5 >= var8) {
               break label125;
            }

            char[] var11 = this._inputBuffer;
            int var12 = var5 + 1;
            char var13 = var11[var5];
            if(var13 > 57 || var13 < 48) {
               this.reportUnexpectedNumberChar(var13, "expected digit (0-9) to follow minus sign, for valid numeric value");
            }

            var5 = var12;
         }

         int var14 = var5;
         var9 = 1;

         label113:
         while(true) {
            int var15 = this._inputEnd;
            if(var14 >= var15) {
               break;
            }

            char[] var16 = this._inputBuffer;
            int var35 = var14 + 1;
            char var17 = var16[var14];
            if(var17 < 48 || var17 > 57) {
               int var21;
               int var33;
               char var36;
               if(var17 == 46) {
                  var14 = 0;
                  int var18 = var35;

                  while(true) {
                     if(var18 >= var7) {
                        break label113;
                     }

                     char[] var19 = this._inputBuffer;
                     var33 = var18 + 1;
                     char var20 = var19[var18];
                     if(var20 < 48 || var20 > 57) {
                        if(var14 == 0) {
                           this.reportUnexpectedNumberChar(var20, "Decimal point not followed by a digit");
                        }

                        var36 = var20;
                        var21 = var33;
                        break;
                     }

                     ++var14;
                     var18 = var33;
                  }
               } else {
                  var21 = var35;
                  var36 = var17;
                  var14 = 0;
               }

               if(var36 != 101 && var36 != 69) {
                  var7 = 0;
               } else {
                  if(var21 >= var7) {
                     break;
                  }

                  char[] var22 = this._inputBuffer;
                  var33 = var21 + 1;
                  char var37 = var22[var21];
                  int var24;
                  char var34;
                  if(var37 != 45 && var37 != 43) {
                     var35 = var33;
                     var34 = var37;
                     var24 = 0;
                  } else {
                     if(var33 >= var7) {
                        break;
                     }

                     char[] var23 = this._inputBuffer;
                     var35 = var33 + 1;
                     var34 = var23[var33];
                     var24 = 0;
                  }

                  while(var34 <= 57 && var34 >= 48) {
                     ++var24;
                     if(var35 >= var7) {
                        break label113;
                     }

                     char[] var25 = this._inputBuffer;
                     int var26 = var35 + 1;
                     var34 = var25[var35];
                     var35 = var26;
                  }

                  if(var24 == 0) {
                     this.reportUnexpectedNumberChar(var34, "Exponent indicator not followed by a digit");
                  }

                  var7 = var24;
                  var21 = var35;
               }

               int var29 = var21 + -1;
               this._inputPtr = var29;
               int var30 = var29 - var6;
               TextBuffer var31 = this._textBuffer;
               char[] var32 = this._inputBuffer;
               var31.resetWithShared(var32, var6, var30);
               var10 = this.reset((boolean)var4, var9, var14, var7);
               return var10;
            }

            ++var9;
            if(var9 == 2) {
               char[] var27 = this._inputBuffer;
               int var28 = var35 - 2;
               if(var27[var28] == 48) {
                  this.reportInvalidNumber("Leading zeroes not allowed");
                  var14 = var35;
                  continue;
               }
            }

            var14 = var35;
         }
      }

      if(var4 != 0) {
         var9 = var6 + 1;
      } else {
         var9 = var6;
      }

      this._inputPtr = var9;
      var10 = this.parseNumberText2((boolean)var4);
      return var10;
   }
}
