package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.impl.JsonReadContext;
import org.codehaus.jackson.impl.Utf8NumericParser;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.sym.BytesToNameCanonicalizer;
import org.codehaus.jackson.sym.Name;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.util.ByteArrayBuilder;
import org.codehaus.jackson.util.CharTypes;

public final class Utf8StreamParser extends Utf8NumericParser {

   static final byte BYTE_LF = 10;
   final ObjectCodec _objectCodec;
   protected int[] _quadBuffer;
   protected final BytesToNameCanonicalizer _symbols;


   public Utf8StreamParser(IOContext var1, int var2, InputStream var3, ObjectCodec var4, BytesToNameCanonicalizer var5, byte[] var6, int var7, int var8, boolean var9) {
      super(var1, var2, var3, var6, var7, var8, var9);
      int[] var18 = new int[32];
      this._quadBuffer = var18;
      this._objectCodec = var4;
      this._symbols = var5;
   }

   private final int _decodeUtf8_2(int var1) throws IOException, JsonParseException {
      int var2 = this._inputPtr;
      int var3 = this._inputEnd;
      if(var2 >= var3) {
         this.loadMoreGuaranteed();
      }

      byte[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      byte var7 = var4[var5];
      if((var7 & 192) != 128) {
         int var8 = var7 & 255;
         int var9 = this._inputPtr;
         this._reportInvalidOther(var8, var9);
      }

      int var10 = (var1 & 31) << 6;
      return var7 & 63 | var10;
   }

   private final int _decodeUtf8_3(int var1) throws IOException, JsonParseException {
      int var2 = this._inputPtr;
      int var3 = this._inputEnd;
      if(var2 >= var3) {
         this.loadMoreGuaranteed();
      }

      int var4 = var1 & 15;
      byte[] var5 = this._inputBuffer;
      int var6 = this._inputPtr;
      int var7 = var6 + 1;
      this._inputPtr = var7;
      byte var8 = var5[var6];
      if((var8 & 192) != 128) {
         int var9 = var8 & 255;
         int var10 = this._inputPtr;
         this._reportInvalidOther(var9, var10);
      }

      int var11 = var4 << 6;
      int var12 = var8 & 63;
      int var13 = var11 | var12;
      int var14 = this._inputPtr;
      int var15 = this._inputEnd;
      if(var14 >= var15) {
         this.loadMoreGuaranteed();
      }

      byte[] var16 = this._inputBuffer;
      int var17 = this._inputPtr;
      int var18 = var17 + 1;
      this._inputPtr = var18;
      byte var19 = var16[var17];
      if((var19 & 192) != 128) {
         int var20 = var19 & 255;
         int var21 = this._inputPtr;
         this._reportInvalidOther(var20, var21);
      }

      int var22 = var13 << 6;
      int var23 = var19 & 63;
      return var22 | var23;
   }

   private final int _decodeUtf8_3fast(int var1) throws IOException, JsonParseException {
      int var2 = var1 & 15;
      byte[] var3 = this._inputBuffer;
      int var4 = this._inputPtr;
      int var5 = var4 + 1;
      this._inputPtr = var5;
      byte var6 = var3[var4];
      if((var6 & 192) != 128) {
         int var7 = var6 & 255;
         int var8 = this._inputPtr;
         this._reportInvalidOther(var7, var8);
      }

      int var9 = var2 << 6;
      int var10 = var6 & 63;
      int var11 = var9 | var10;
      byte[] var12 = this._inputBuffer;
      int var13 = this._inputPtr;
      int var14 = var13 + 1;
      this._inputPtr = var14;
      byte var15 = var12[var13];
      if((var15 & 192) != 128) {
         int var16 = var15 & 255;
         int var17 = this._inputPtr;
         this._reportInvalidOther(var16, var17);
      }

      int var18 = var11 << 6;
      int var19 = var15 & 63;
      return var18 | var19;
   }

   private final int _decodeUtf8_4(int var1) throws IOException, JsonParseException {
      int var2 = this._inputPtr;
      int var3 = this._inputEnd;
      if(var2 >= var3) {
         this.loadMoreGuaranteed();
      }

      byte[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      byte var7 = var4[var5];
      if((var7 & 192) != 128) {
         int var8 = var7 & 255;
         int var9 = this._inputPtr;
         this._reportInvalidOther(var8, var9);
      }

      int var10 = (var1 & 7) << 6;
      int var11 = var7 & 63 | var10;
      int var12 = this._inputPtr;
      int var13 = this._inputEnd;
      if(var12 >= var13) {
         this.loadMoreGuaranteed();
      }

      byte[] var14 = this._inputBuffer;
      int var15 = this._inputPtr;
      int var16 = var15 + 1;
      this._inputPtr = var16;
      byte var17 = var14[var15];
      if((var17 & 192) != 128) {
         int var18 = var17 & 255;
         int var19 = this._inputPtr;
         this._reportInvalidOther(var18, var19);
      }

      int var20 = var11 << 6;
      int var21 = var17 & 63;
      int var22 = var20 | var21;
      int var23 = this._inputPtr;
      int var24 = this._inputEnd;
      if(var23 >= var24) {
         this.loadMoreGuaranteed();
      }

      byte[] var25 = this._inputBuffer;
      int var26 = this._inputPtr;
      int var27 = var26 + 1;
      this._inputPtr = var27;
      byte var28 = var25[var26];
      if((var28 & 192) != 128) {
         int var29 = var28 & 255;
         int var30 = this._inputPtr;
         this._reportInvalidOther(var29, var30);
      }

      int var31 = var22 << 6;
      int var32 = var28 & 63;
      return (var31 | var32) - 65536;
   }

   private final JsonToken _nextAfterName() {
      this._nameCopied = (boolean)0;
      JsonToken var1 = this._nextToken;
      this._nextToken = null;
      JsonToken var2 = JsonToken.START_ARRAY;
      if(var1 == var2) {
         JsonReadContext var3 = this._parsingContext;
         int var4 = this._tokenInputRow;
         int var5 = this._tokenInputCol;
         JsonReadContext var6 = var3.createChildArrayContext(var4, var5);
         this._parsingContext = var6;
      } else {
         JsonToken var7 = JsonToken.START_OBJECT;
         if(var1 == var7) {
            JsonReadContext var8 = this._parsingContext;
            int var9 = this._tokenInputRow;
            int var10 = this._tokenInputCol;
            JsonReadContext var11 = var8.createChildObjectContext(var9, var10);
            this._parsingContext = var11;
         }
      }

      this._currToken = var1;
      return var1;
   }

   private void _reportInvalidToken(String var1) throws IOException, JsonParseException {
      StringBuilder var2 = new StringBuilder(var1);

      while(true) {
         int var3 = this._inputPtr;
         int var4 = this._inputEnd;
         if(var3 >= var4 && !this.loadMore()) {
            break;
         }

         byte[] var8 = this._inputBuffer;
         int var9 = this._inputPtr;
         int var10 = var9 + 1;
         this._inputPtr = var10;
         byte var11 = var8[var9];
         char var12 = (char)this._decodeCharForError(var11);
         if(!Character.isJavaIdentifierPart(var12)) {
            break;
         }

         int var13 = this._inputPtr + 1;
         this._inputPtr = var13;
         var2.append(var12);
      }

      StringBuilder var5 = (new StringBuilder()).append("Unrecognized token \'");
      String var6 = var2.toString();
      String var7 = var5.append(var6).append("\': was expecting \'null\', \'true\' or \'false\'").toString();
      this._reportError(var7);
   }

   private final void _skipCComment() throws IOException, JsonParseException {
      int[] var1 = CharTypes.getInputCodeComment();

      while(true) {
         int var2 = this._inputPtr;
         int var3 = this._inputEnd;
         if(var2 >= var3 && !this.loadMore()) {
            this._reportInvalidEOF(" in a comment");
            return;
         }

         byte[] var4 = this._inputBuffer;
         int var5 = this._inputPtr;
         int var6 = var5 + 1;
         this._inputPtr = var6;
         int var7 = var4[var5] & 255;
         int var8 = var1[var7];
         if(var8 != 0) {
            switch(var8) {
            case 10:
               this._skipLF();
               break;
            case 13:
               this._skipCR();
               break;
            case 42:
               byte[] var9 = this._inputBuffer;
               int var10 = this._inputPtr;
               if(var9[var10] == 47) {
                  int var11 = this._inputPtr + 1;
                  this._inputPtr = var11;
                  return;
               }
               break;
            default:
               this._reportInvalidChar(var7);
            }
         }
      }
   }

   private final void _skipComment() throws IOException, JsonParseException {
      JsonParser.Feature var1 = JsonParser.Feature.ALLOW_COMMENTS;
      if(!this.isFeatureEnabled(var1)) {
         this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature \'ALLOW_COMMENTS\' not enabled for parser)");
      }

      int var2 = this._inputPtr;
      int var3 = this._inputEnd;
      if(var2 >= var3 && !this.loadMore()) {
         this._reportInvalidEOF(" in a comment");
      }

      byte[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      int var7 = var4[var5] & 255;
      if(var7 == 47) {
         this._skipCppComment();
      } else if(var7 == 42) {
         this._skipCComment();
      } else {
         this._reportUnexpectedChar(var7, "was expecting either \'*\' or \'/\' for a comment");
      }
   }

   private final void _skipCppComment() throws IOException, JsonParseException {
      int[] var1 = CharTypes.getInputCodeComment();

      while(true) {
         int var2 = this._inputPtr;
         int var3 = this._inputEnd;
         if(var2 >= var3 && !this.loadMore()) {
            return;
         }

         byte[] var4 = this._inputBuffer;
         int var5 = this._inputPtr;
         int var6 = var5 + 1;
         this._inputPtr = var6;
         int var7 = var4[var5] & 255;
         int var8 = var1[var7];
         if(var8 != 0) {
            switch(var8) {
            case 10:
               this._skipLF();
               return;
            case 13:
               this._skipCR();
               return;
            case 42:
               break;
            default:
               this._reportInvalidChar(var7);
            }
         }
      }
   }

   private final void _skipUtf8_2(int var1) throws IOException, JsonParseException {
      int var2 = this._inputPtr;
      int var3 = this._inputEnd;
      if(var2 >= var3) {
         this.loadMoreGuaranteed();
      }

      byte[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      byte var7 = var4[var5];
      if((var7 & 192) != 128) {
         int var8 = var7 & 255;
         int var9 = this._inputPtr;
         this._reportInvalidOther(var8, var9);
      }
   }

   private final void _skipUtf8_3(int var1) throws IOException, JsonParseException {
      int var2 = this._inputPtr;
      int var3 = this._inputEnd;
      if(var2 >= var3) {
         this.loadMoreGuaranteed();
      }

      byte[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      byte var7 = var4[var5];
      if((var7 & 192) != 128) {
         int var8 = var7 & 255;
         int var9 = this._inputPtr;
         this._reportInvalidOther(var8, var9);
      }

      int var10 = this._inputPtr;
      int var11 = this._inputEnd;
      if(var10 >= var11) {
         this.loadMoreGuaranteed();
      }

      byte[] var12 = this._inputBuffer;
      int var13 = this._inputPtr;
      int var14 = var13 + 1;
      this._inputPtr = var14;
      byte var15 = var12[var13];
      if((var15 & 192) != 128) {
         int var16 = var15 & 255;
         int var17 = this._inputPtr;
         this._reportInvalidOther(var16, var17);
      }
   }

   private final void _skipUtf8_4(int var1) throws IOException, JsonParseException {
      int var2 = this._inputPtr;
      int var3 = this._inputEnd;
      if(var2 >= var3) {
         this.loadMoreGuaranteed();
      }

      byte[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      byte var7 = var4[var5];
      if((var7 & 192) != 128) {
         int var8 = var7 & 255;
         int var9 = this._inputPtr;
         this._reportInvalidOther(var8, var9);
      }

      int var10 = this._inputPtr;
      int var11 = this._inputEnd;
      if(var10 >= var11) {
         this.loadMoreGuaranteed();
      }

      if((var7 & 192) != 128) {
         int var12 = var7 & 255;
         int var13 = this._inputPtr;
         this._reportInvalidOther(var12, var13);
      }

      int var14 = this._inputPtr;
      int var15 = this._inputEnd;
      if(var14 >= var15) {
         this.loadMoreGuaranteed();
      }

      byte[] var16 = this._inputBuffer;
      int var17 = this._inputPtr;
      int var18 = var17 + 1;
      this._inputPtr = var18;
      byte var19 = var16[var17];
      if((var19 & 192) != 128) {
         int var20 = var19 & 255;
         int var21 = this._inputPtr;
         this._reportInvalidOther(var20, var21);
      }
   }

   private final int _skipWS() throws IOException, JsonParseException {
      while(true) {
         int var1 = this._inputPtr;
         int var2 = this._inputEnd;
         if(var1 >= var2 && !this.loadMore()) {
            StringBuilder var7 = (new StringBuilder()).append("Unexpected end-of-input within/between ");
            String var8 = this._parsingContext.getTypeDesc();
            String var9 = var7.append(var8).append(" entries").toString();
            throw this._constructError(var9);
         }

         byte[] var3 = this._inputBuffer;
         int var4 = this._inputPtr;
         int var5 = var4 + 1;
         this._inputPtr = var5;
         int var6 = var3[var4] & 255;
         if(var6 > 32) {
            if(var6 != 47) {
               return var6;
            }

            this._skipComment();
         } else if(var6 != 32) {
            if(var6 == 10) {
               this._skipLF();
            } else if(var6 == 13) {
               this._skipCR();
            } else if(var6 != 9) {
               this._throwInvalidSpace(var6);
            }
         }
      }
   }

   private final int _skipWSOrEnd() throws IOException, JsonParseException {
      int var6;
      while(true) {
         int var1 = this._inputPtr;
         int var2 = this._inputEnd;
         if(var1 >= var2 && !this.loadMore()) {
            this._handleEOF();
            var6 = -1;
            break;
         }

         byte[] var3 = this._inputBuffer;
         int var4 = this._inputPtr;
         int var5 = var4 + 1;
         this._inputPtr = var5;
         var6 = var3[var4] & 255;
         if(var6 <= 32) {
            if(var6 != 32) {
               if(var6 == 10) {
                  this._skipLF();
               } else if(var6 == 13) {
                  this._skipCR();
               } else if(var6 != 9) {
                  this._throwInvalidSpace(var6);
               }
            }
         } else {
            if(var6 == 47) {
               this._skipComment();
               continue;
            }
            break;
         }
      }

      return var6;
   }

   private final Name addName(int[] var1, int var2, int var3) throws JsonParseException {
      int var4 = (var2 << 2) - 4 + var3;
      int var6;
      if(var3 < 4) {
         int var5 = var2 - 1;
         var6 = var1[var5];
         int var7 = var2 - 1;
         int var8 = 4 - var3 << 3;
         int var9 = var6 << var8;
         var1[var7] = var9;
      } else {
         var6 = 0;
      }

      char[] var10 = this._textBuffer.emptyAndGetCurrentSegment();
      int var11 = 0;
      byte var12 = 0;
      char[] var13 = var10;

      int var63;
      for(int var14 = var12; var14 < var4; var11 = var63) {
         int var15 = var14 >> 2;
         int var16 = var1[var15];
         int var17 = var14 & 3;
         int var18 = 3 - var17 << 3;
         int var19 = var16 >> var18 & 255;
         int var20 = var14 + 1;
         int var52;
         int var58;
         int var61;
         char[] var60;
         if(var19 > 127) {
            int var23;
            byte var24;
            if((var19 & 224) == 192) {
               int var21 = var19 & 31;
               byte var22 = 1;
               var23 = var21;
               var24 = var22;
            } else if((var19 & 240) == 224) {
               int var65 = var19 & 15;
               byte var66 = 2;
               var23 = var65;
               var24 = var66;
            } else if((var19 & 248) == 240) {
               int var67 = var19 & 7;
               byte var68 = 3;
               var23 = var67;
               var24 = var68;
            } else {
               this._reportInvalidInitial(var19);
               var24 = 1;
               var23 = var24;
            }

            if(var20 + var24 > var4) {
               this._reportInvalidEOF(" in field name");
            }

            int var25 = var20 >> 2;
            int var26 = var1[var25];
            int var27 = var20 & 3;
            int var28 = 3 - var27 << 3;
            int var29 = var26 >> var28;
            int var30 = var20 + 1;
            if((var29 & 192) != 128) {
               this._reportInvalidOther(var29);
            }

            int var51;
            label67: {
               int var31 = var23 << 6;
               int var32 = var29 & 63;
               int var33 = var31 | var32;
               if(var24 > 1) {
                  int var34 = var30 >> 2;
                  int var35 = var1[var34];
                  int var36 = var30 & 3;
                  int var37 = 3 - var36 << 3;
                  int var38 = var35 >> var37;
                  ++var30;
                  if((var38 & 192) != 128) {
                     this._reportInvalidOther(var38);
                  }

                  int var39 = var33 << 6;
                  int var40 = var38 & 63;
                  var33 = var39 | var40;
                  if(var24 > 2) {
                     int var41 = var30 >> 2;
                     int var42 = var1[var41];
                     int var43 = var30 & 3;
                     int var44 = 3 - var43 << 3;
                     int var45 = var42 >> var44;
                     int var46 = var30 + 1;
                     if((var45 & 192) != 128) {
                        int var47 = var45 & 255;
                        this._reportInvalidOther(var47);
                     }

                     int var48 = var33 << 6;
                     int var49 = var45 & 63;
                     int var50 = var48 | var49;
                     var51 = var46;
                     var52 = var50;
                     break label67;
                  }
               }

               var51 = var30;
               var52 = var33;
            }

            if(var24 > 2) {
               int var53 = var52 - 65536;
               int var54 = var13.length;
               if(var11 >= var54) {
                  var13 = this._textBuffer.expandCurrentSegment();
               }

               int var55 = var11 + 1;
               int var56 = var53 >> 10;
               char var57 = (char)('\ud800' + var56);
               var13[var11] = var57;
               var52 = var53 & 1023 | '\udc00';
               var58 = var51;
               var60 = var13;
               var61 = var55;
            } else {
               var60 = var13;
               var61 = var11;
               var58 = var51;
            }
         } else {
            var60 = var13;
            var61 = var11;
            var58 = var20;
            var52 = var19;
         }

         int var62 = var60.length;
         if(var61 >= var62) {
            var60 = this._textBuffer.expandCurrentSegment();
         }

         var63 = var61 + 1;
         char var64 = (char)var52;
         var60[var61] = var64;
         var14 = var58;
         var13 = var60;
      }

      String var69 = new String(var13, 0, var11);
      if(var3 < 4) {
         int var70 = var2 - 1;
         var1[var70] = var6;
      }

      return this._symbols.addName(var69, var1, var2);
   }

   private final Name findName(int var1, int var2) throws JsonParseException {
      Name var3 = this._symbols.findName(var1);
      if(var3 == null) {
         this._quadBuffer[0] = var1;
         int[] var4 = this._quadBuffer;
         var3 = this.addName(var4, 1, var2);
      }

      return var3;
   }

   private final Name findName(int var1, int var2, int var3) throws JsonParseException {
      Name var4 = this._symbols.findName(var1, var2);
      if(var4 == null) {
         this._quadBuffer[0] = var1;
         this._quadBuffer[1] = var2;
         int[] var5 = this._quadBuffer;
         var4 = this.addName(var5, 2, var3);
      }

      return var4;
   }

   private final Name findName(int[] var1, int var2, int var3, int var4) throws JsonParseException {
      int var5 = var1.length;
      int[] var7;
      if(var2 >= var5) {
         int var6 = var1.length;
         var7 = growArrayBy(var1, var6);
         this._quadBuffer = var7;
      } else {
         var7 = var1;
      }

      int var8 = var2 + 1;
      var7[var2] = var3;
      Name var9 = this._symbols.findName(var7, var8);
      Name var10;
      if(var9 == null) {
         var10 = this.addName(var7, var8, var4);
      } else {
         var10 = var9;
      }

      return var10;
   }

   public static int[] growArrayBy(int[] var0, int var1) {
      int[] var2;
      if(var0 == null) {
         var2 = new int[var1];
      } else {
         int var3 = var0.length;
         int[] var4 = new int[var3 + var1];
         System.arraycopy(var0, 0, var4, 0, var3);
         var2 = var4;
      }

      return var2;
   }

   private int nextByte() throws IOException, JsonParseException {
      int var1 = this._inputPtr;
      int var2 = this._inputEnd;
      if(var1 >= var2) {
         this.loadMoreGuaranteed();
      }

      byte[] var3 = this._inputBuffer;
      int var4 = this._inputPtr;
      int var5 = var4 + 1;
      this._inputPtr = var5;
      return var3[var4] & 255;
   }

   private final Name parseFieldName(int var1, int var2, int var3) throws IOException, JsonParseException {
      int[] var4 = this._quadBuffer;
      return this.parseEscapedFieldName(var4, 0, var1, var2, var3);
   }

   private final Name parseFieldName(int var1, int var2, int var3, int var4) throws IOException, JsonParseException {
      this._quadBuffer[0] = var1;
      int[] var5 = this._quadBuffer;
      return this.parseEscapedFieldName(var5, 1, var2, var3, var4);
   }

   protected byte[] _decodeBase64(Base64Variant var1) throws IOException, JsonParseException {
      ByteArrayBuilder var2 = this._getByteArrayBuilder();

      while(true) {
         int var3 = this._inputPtr;
         int var4 = this._inputEnd;
         if(var3 >= var4) {
            this.loadMoreGuaranteed();
         }

         byte[] var5 = this._inputBuffer;
         int var6 = this._inputPtr;
         int var7 = var6 + 1;
         this._inputPtr = var7;
         int var8 = var5[var6] & 255;
         if(var8 > 32) {
            int var9 = var1.decodeBase64Char(var8);
            if(var9 < 0) {
               if(var8 == 34) {
                  return var2.toByteArray();
               }

               throw this.reportInvalidChar(var1, var8, 0);
            }

            int var10 = this._inputPtr;
            int var11 = this._inputEnd;
            if(var10 >= var11) {
               this.loadMoreGuaranteed();
            }

            byte[] var12 = this._inputBuffer;
            int var13 = this._inputPtr;
            int var14 = var13 + 1;
            this._inputPtr = var14;
            int var15 = var12[var13] & 255;
            int var16 = var1.decodeBase64Char(var15);
            if(var16 < 0) {
               throw this.reportInvalidChar(var1, var15, 1);
            }

            int var17 = var9 << 6 | var16;
            int var18 = this._inputPtr;
            int var19 = this._inputEnd;
            if(var18 >= var19) {
               this.loadMoreGuaranteed();
            }

            byte[] var20 = this._inputBuffer;
            int var21 = this._inputPtr;
            int var22 = var21 + 1;
            this._inputPtr = var22;
            int var23 = var20[var21] & 255;
            int var24 = var1.decodeBase64Char(var23);
            int var30;
            if(var24 < 0) {
               if(var24 != -1) {
                  throw this.reportInvalidChar(var1, var23, 2);
               }

               int var25 = this._inputPtr;
               int var26 = this._inputEnd;
               if(var25 >= var26) {
                  this.loadMoreGuaranteed();
               }

               byte[] var27 = this._inputBuffer;
               int var28 = this._inputPtr;
               int var29 = var28 + 1;
               this._inputPtr = var29;
               var30 = var27[var28] & 255;
               if(!var1.usesPaddingChar(var30)) {
                  StringBuilder var31 = (new StringBuilder()).append("expected padding character \'");
                  char var32 = var1.getPaddingChar();
                  String var33 = var31.append(var32).append("\'").toString();
                  throw this.reportInvalidChar(var1, var30, 3, var33);
               }

               int var34 = var17 >> 4;
               var2.append(var34);
            } else {
               int var35 = var17 << 6 | var24;
               int var36 = this._inputPtr;
               int var37 = this._inputEnd;
               if(var36 >= var37) {
                  this.loadMoreGuaranteed();
               }

               byte[] var38 = this._inputBuffer;
               int var39 = this._inputPtr;
               int var40 = var39 + 1;
               this._inputPtr = var40;
               var30 = var38[var39] & 255;
               int var41 = var1.decodeBase64Char(var30);
               if(var41 < 0) {
                  if(var41 != -1) {
                     throw this.reportInvalidChar(var1, var30, 3);
                  }

                  int var42 = var35 >> 2;
                  var2.appendTwoBytes(var42);
               } else {
                  int var43 = var35 << 6 | var41;
                  var2.appendThreeBytes(var43);
               }
            }
         }
      }
   }

   protected int _decodeCharForError(int var1) throws IOException, JsonParseException {
      int var17;
      if(var1 < 0) {
         int var2;
         byte var3;
         if((var1 & 224) == 192) {
            var2 = var1 & 31;
            var3 = 1;
         } else if((var1 & 240) == 224) {
            var2 = var1 & 15;
            var3 = 2;
         } else if((var1 & 248) == 240) {
            int var18 = var1 & 7;
            byte var19 = 3;
            var2 = var18;
            var3 = var19;
         } else {
            int var20 = var1 & 255;
            this._reportInvalidInitial(var20);
            var3 = 1;
            var2 = var1;
         }

         int var4 = this.nextByte();
         if((var4 & 192) != 128) {
            int var5 = var4 & 255;
            this._reportInvalidOther(var5);
         }

         int var6 = var2 << 6;
         int var7 = var4 & 63;
         int var8 = var6 | var7;
         if(var3 > 1) {
            int var9 = this.nextByte();
            if((var9 & 192) != 128) {
               int var10 = var9 & 255;
               this._reportInvalidOther(var10);
            }

            int var11 = var8 << 6;
            int var12 = var9 & 63;
            int var13 = var11 | var12;
            if(var3 > 2) {
               int var14 = this.nextByte();
               if((var14 & 192) != 128) {
                  int var15 = var14 & 255;
                  this._reportInvalidOther(var15);
               }

               int var16 = var13 << 6;
               var17 = var14 & 63 | var16;
               return var17;
            }
         }

         var17 = var8;
      } else {
         var17 = var1;
      }

      return var17;
   }

   protected final char _decodeEscaped() throws IOException, JsonParseException {
      boolean var1 = false;
      int var2 = this._inputPtr;
      int var3 = this._inputEnd;
      if(var2 >= var3 && !this.loadMore()) {
         this._reportInvalidEOF(" in character escape sequence");
      }

      byte[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      byte var7 = var4[var5];
      char var20;
      switch(var7) {
      case 34:
      case 47:
      case 92:
         var20 = (char)var7;
         break;
      case 98:
         var20 = 8;
         break;
      case 102:
         var20 = 12;
         break;
      case 110:
         var20 = 10;
         break;
      case 114:
         var20 = 13;
         break;
      case 116:
         var20 = 9;
         break;
      default:
         StringBuilder var8 = (new StringBuilder()).append("Unrecognized character escape \\ followed by ");
         String var9 = _getCharDesc(this._decodeCharForError(var7));
         String var10 = var8.append(var9).toString();
         this._reportError(var10);
      case 117:
         int var11 = 0;

         byte var12;
         for(var12 = 0; var11 < 4; ++var11) {
            int var13 = this._inputPtr;
            int var14 = this._inputEnd;
            if(var13 >= var14 && !this.loadMore()) {
               this._reportInvalidEOF(" in character escape sequence");
            }

            byte[] var15 = this._inputBuffer;
            int var16 = this._inputPtr;
            int var17 = var16 + 1;
            this._inputPtr = var17;
            byte var18 = var15[var16];
            int var21 = CharTypes.charToHex(var18);
            if(var21 < 0) {
               this._reportUnexpectedChar(var18, "expected a hex-digit for character escape sequence");
            }

            int var19 = var12 << 4 | var21;
         }

         var20 = (char)var12;
      }

      return var20;
   }

   protected void _finishString() throws IOException, JsonParseException {
      char[] var1 = this._textBuffer.emptyAndGetCurrentSegment();
      int[] var2 = CharTypes.getInputCodeUtf8();
      byte[] var3 = this._inputBuffer;
      int var4 = 0;

      label56:
      while(true) {
         int var5 = this._inputPtr;
         int var6 = this._inputEnd;
         if(var5 >= var6) {
            this.loadMoreGuaranteed();
            var5 = this._inputPtr;
         }

         int var7 = var1.length;
         if(var4 >= var7) {
            var1 = this._textBuffer.finishCurrentSegment();
            var4 = 0;
         }

         int var8 = this._inputEnd;
         int var9 = var1.length - var4 + var5;
         int var10;
         int var11;
         if(var9 < var8) {
            var10 = var4;
            var11 = var9;
         } else {
            var10 = var4;
            var11 = var8;
         }

         while(var5 < var11) {
            var9 = var5 + 1;
            int var12 = var3[var5] & 255;
            if(var2[var12] != 0) {
               this._inputPtr = var9;
               if(var12 == 34) {
                  this._textBuffer.setCurrentLength(var10);
                  return;
               }

               int var15;
               switch(var2[var12]) {
               case 1:
                  var15 = this._decodeEscaped();
                  var5 = var10;
                  break;
               case 2:
                  var15 = this._decodeUtf8_2(var12);
                  var5 = var10;
                  break;
               case 3:
                  int var20 = this._inputEnd;
                  int var21 = this._inputPtr;
                  if(var20 - var21 >= 2) {
                     var15 = this._decodeUtf8_3fast(var12);
                     var5 = var10;
                  } else {
                     var15 = this._decodeUtf8_3(var12);
                     var5 = var10;
                  }
                  break;
               case 4:
                  int var22 = this._decodeUtf8_4(var12);
                  var5 = var10 + 1;
                  int var23 = var22 >> 10;
                  char var24 = (char)('\ud800' | var23);
                  var1[var10] = var24;
                  int var25 = var1.length;
                  if(var5 >= var25) {
                     var1 = this._textBuffer.finishCurrentSegment();
                     boolean var26 = false;
                  }

                  var15 = var22 & 1023 | '\udc00';
                  break;
               default:
                  if(var12 < 32) {
                     this._throwUnquotedSpace(var12, "string value");
                  }

                  this._reportInvalidChar(var12);
                  var15 = var12;
                  var5 = var10;
               }

               int var16 = var1.length;
               if(var5 >= var16) {
                  var1 = this._textBuffer.finishCurrentSegment();
                  boolean var17 = false;
               }

               int var18 = var5 + 1;
               char var19 = (char)var15;
               var1[var5] = var19;
               var4 = var18;
               continue label56;
            }

            int var13 = var10 + 1;
            char var14 = (char)var12;
            var1[var10] = var14;
            var5 = var9;
            var10 = var13;
         }

         this._inputPtr = var5;
         var4 = var10;
      }
   }

   protected void _matchToken(JsonToken var1) throws IOException, JsonParseException {
      byte[] var2 = var1.asByteArray();
      int var3 = 1;

      for(int var4 = var2.length; var3 < var4; ++var3) {
         int var5 = this._inputPtr;
         int var6 = this._inputEnd;
         if(var5 >= var6) {
            this.loadMoreGuaranteed();
         }

         byte var7 = var2[var3];
         byte[] var8 = this._inputBuffer;
         int var9 = this._inputPtr;
         byte var10 = var8[var9];
         if(var7 != var10) {
            String var11 = var1.asString().substring(0, var3);
            this._reportInvalidToken(var11);
         }

         int var12 = this._inputPtr + 1;
         this._inputPtr = var12;
      }

   }

   protected final Name _parseFieldName(int var1) throws IOException, JsonParseException {
      if(var1 != 34) {
         this._reportUnexpectedChar(var1, "was expecting double-quote to start field name");
      }

      int var2 = this._inputEnd;
      int var3 = this._inputPtr;
      Name var4;
      if(var2 - var3 < 9) {
         var4 = this.slowParseFieldName();
      } else {
         int[] var5 = CharTypes.getInputCodeLatin1();
         byte[] var6 = this._inputBuffer;
         int var7 = this._inputPtr;
         int var8 = var7 + 1;
         this._inputPtr = var8;
         int var9 = var6[var7] & 255;
         if(var5[var9] != 0) {
            if(var9 == 34) {
               var4 = BytesToNameCanonicalizer.getEmptyName();
            } else {
               var4 = this.parseFieldName(0, var9, 0);
            }
         } else {
            byte[] var10 = this._inputBuffer;
            int var11 = this._inputPtr;
            int var12 = var11 + 1;
            this._inputPtr = var12;
            int var13 = var10[var11] & 255;
            if(var5[var13] != 0) {
               if(var13 == 34) {
                  var4 = this.findName(var9, 1);
               } else {
                  var4 = this.parseFieldName(var9, var13, 1);
               }
            } else {
               var9 = var9 << 8 | var13;
               byte[] var14 = this._inputBuffer;
               int var15 = this._inputPtr;
               int var16 = var15 + 1;
               this._inputPtr = var16;
               var13 = var14[var15] & 255;
               if(var5[var13] != 0) {
                  if(var13 == 34) {
                     var4 = this.findName(var9, 2);
                  } else {
                     var4 = this.parseFieldName(var9, var13, 2);
                  }
               } else {
                  var9 = var9 << 8 | var13;
                  byte[] var17 = this._inputBuffer;
                  int var18 = this._inputPtr;
                  int var19 = var18 + 1;
                  this._inputPtr = var19;
                  var13 = var17[var18] & 255;
                  if(var5[var13] != 0) {
                     if(var13 == 34) {
                        var4 = this.findName(var9, 3);
                     } else {
                        var4 = this.parseFieldName(var9, var13, 3);
                     }
                  } else {
                     var9 = var9 << 8 | var13;
                     byte[] var20 = this._inputBuffer;
                     int var21 = this._inputPtr;
                     int var22 = var21 + 1;
                     this._inputPtr = var22;
                     var13 = var20[var21] & 255;
                     if(var5[var13] != 0) {
                        if(var13 == 34) {
                           var4 = this.findName(var9, 4);
                        } else {
                           var4 = this.parseFieldName(var9, var13, 4);
                        }
                     } else {
                        var4 = this.parseMediumFieldName(var9, var13);
                     }
                  }
               }
            }
         }
      }

      return var4;
   }

   protected void _reportInvalidChar(int var1) throws JsonParseException {
      if(var1 < 32) {
         this._throwInvalidSpace(var1);
      }

      this._reportInvalidInitial(var1);
   }

   protected void _reportInvalidInitial(int var1) throws JsonParseException {
      StringBuilder var2 = (new StringBuilder()).append("Invalid UTF-8 start byte 0x");
      String var3 = Integer.toHexString(var1);
      String var4 = var2.append(var3).toString();
      this._reportError(var4);
   }

   protected void _reportInvalidOther(int var1) throws JsonParseException {
      StringBuilder var2 = (new StringBuilder()).append("Invalid UTF-8 middle byte 0x");
      String var3 = Integer.toHexString(var1);
      String var4 = var2.append(var3).toString();
      this._reportError(var4);
   }

   protected void _reportInvalidOther(int var1, int var2) throws JsonParseException {
      this._inputPtr = var2;
      this._reportInvalidOther(var1);
   }

   protected final void _skipCR() throws IOException {
      int var1 = this._inputPtr;
      int var2 = this._inputEnd;
      if(var1 < var2 || this.loadMore()) {
         byte[] var3 = this._inputBuffer;
         int var4 = this._inputPtr;
         if(var3[var4] == 10) {
            int var5 = this._inputPtr + 1;
            this._inputPtr = var5;
         }
      }

      int var6 = this._currInputRow + 1;
      this._currInputRow = var6;
      int var7 = this._inputPtr;
      this._currInputRowStart = var7;
   }

   protected final void _skipLF() throws IOException {
      int var1 = this._currInputRow + 1;
      this._currInputRow = var1;
      int var2 = this._inputPtr;
      this._currInputRowStart = var2;
   }

   protected void _skipString() throws IOException, JsonParseException {
      this._tokenIncomplete = (boolean)0;
      int[] var1 = CharTypes.getInputCodeUtf8();
      byte[] var2 = this._inputBuffer;

      label35:
      while(true) {
         int var3 = this._inputPtr;
         int var4 = this._inputEnd;
         int var7;
         int var8;
         if(var3 >= var4) {
            this.loadMoreGuaranteed();
            int var5 = this._inputPtr;
            int var6 = this._inputEnd;
            var7 = var5;
            var8 = var6;
         } else {
            var7 = var3;
            var8 = var4;
         }

         while(var7 < var8) {
            int var9 = var7 + 1;
            var7 = var2[var7] & 255;
            if(var1[var7] != 0) {
               this._inputPtr = var9;
               if(var7 == 34) {
                  return;
               }

               switch(var1[var7]) {
               case 1:
                  char var10 = this._decodeEscaped();
                  continue label35;
               case 2:
                  this._skipUtf8_2(var7);
                  continue label35;
               case 3:
                  this._skipUtf8_3(var7);
                  continue label35;
               case 4:
                  this._skipUtf8_4(var7);
                  continue label35;
               default:
                  if(var7 < 32) {
                     this._throwUnquotedSpace(var7, "string value");
                  }

                  this._reportInvalidChar(var7);
                  continue label35;
               }
            }

            var7 = var9;
         }

         this._inputPtr = var7;
      }
   }

   public void close() throws IOException {
      super.close();
      this._symbols.release();
   }

   public JsonToken nextToken() throws IOException, JsonParseException {
      JsonToken var1 = this._currToken;
      JsonToken var2 = JsonToken.FIELD_NAME;
      if(var1 == var2) {
         var1 = this._nextAfterName();
      } else {
         if(this._tokenIncomplete) {
            this._skipString();
         }

         int var3 = this._skipWSOrEnd();
         if(var3 < 0) {
            this.close();
            this._currToken = null;
            var1 = null;
         } else {
            long var4 = this._currInputProcessed;
            long var6 = (long)this._inputPtr;
            long var8 = var4 + var6 - 1L;
            this._tokenInputTotal = var8;
            int var10 = this._currInputRow;
            this._tokenInputRow = var10;
            int var11 = this._inputPtr;
            int var12 = this._currInputRowStart;
            int var13 = var11 - var12 - 1;
            this._tokenInputCol = var13;
            this._binaryValue = null;
            if(var3 == 93) {
               if(!this._parsingContext.inArray()) {
                  this._reportMismatchedEndMarker(var3, ']');
               }

               JsonReadContext var14 = this._parsingContext.getParent();
               this._parsingContext = var14;
               var1 = JsonToken.END_ARRAY;
               this._currToken = var1;
            } else if(var3 == 125) {
               if(!this._parsingContext.inObject()) {
                  this._reportMismatchedEndMarker(var3, '}');
               }

               JsonReadContext var15 = this._parsingContext.getParent();
               this._parsingContext = var15;
               var1 = JsonToken.END_OBJECT;
               this._currToken = var1;
            } else {
               if(this._parsingContext.expectComma()) {
                  if(var3 != 44) {
                     StringBuilder var16 = (new StringBuilder()).append("was expecting comma to separate ");
                     String var17 = this._parsingContext.getTypeDesc();
                     String var18 = var16.append(var17).append(" entries").toString();
                     this._reportUnexpectedChar(var3, var18);
                  }

                  var3 = this._skipWS();
               }

               boolean var19 = this._parsingContext.inObject();
               if(var19) {
                  Name var20 = this._parseFieldName(var3);
                  JsonReadContext var21 = this._parsingContext;
                  String var22 = var20.getName();
                  var21.setCurrentName(var22);
                  int var23 = this._skipWS();
                  if(var23 != 58) {
                     this._reportUnexpectedChar(var23, "was expecting a colon to separate field name and value");
                  }

                  JsonToken var24 = JsonToken.FIELD_NAME;
                  this._currToken = var24;
                  var3 = this._skipWS();
               }

               switch(var3) {
               case 34:
                  this._tokenIncomplete = (boolean)1;
                  JsonToken var25 = JsonToken.VALUE_STRING;
                  break;
               case 45:
               case 48:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               case 55:
               case 56:
               case 57:
                  this.parseNumberText(var3);
                  break;
               case 91:
                  if(!var19) {
                     JsonReadContext var26 = this._parsingContext;
                     int var27 = this._tokenInputRow;
                     int var28 = this._tokenInputCol;
                     JsonReadContext var29 = var26.createChildArrayContext(var27, var28);
                     this._parsingContext = var29;
                  }

                  JsonToken var30 = JsonToken.START_ARRAY;
                  break;
               case 93:
               case 125:
                  this._reportUnexpectedChar(var3, "expected a value");
               case 102:
                  JsonToken var38 = JsonToken.VALUE_FALSE;
                  this._matchToken(var38);
                  JsonToken var39 = JsonToken.VALUE_FALSE;
                  break;
               case 110:
                  JsonToken var40 = JsonToken.VALUE_NULL;
                  this._matchToken(var40);
                  JsonToken var41 = JsonToken.VALUE_NULL;
                  break;
               case 116:
                  JsonToken var36 = JsonToken.VALUE_TRUE;
                  this._matchToken(var36);
                  JsonToken var37 = JsonToken.VALUE_TRUE;
                  break;
               case 123:
                  if(!var19) {
                     JsonReadContext var31 = this._parsingContext;
                     int var32 = this._tokenInputRow;
                     int var33 = this._tokenInputCol;
                     JsonReadContext var34 = var31.createChildObjectContext(var32, var33);
                     this._parsingContext = var34;
                  }

                  JsonToken var35 = JsonToken.START_OBJECT;
                  break;
               default:
                  this._reportUnexpectedChar(var3, "expected a valid value (number, String, array, object, \'true\', \'false\' or \'null\')");
                  var1 = null;
               }

               if(var19) {
                  this._nextToken = var1;
                  var1 = this._currToken;
               } else {
                  this._currToken = var1;
               }
            }
         }
      }

      return var1;
   }

   protected Name parseEscapedFieldName(int[] var1, int var2, int var3, int var4, int var5) throws IOException, JsonParseException {
      int[] var6 = CharTypes.getInputCodeLatin1();
      int var7 = var5;
      int var8 = var4;
      int var9 = var3;
      int var10 = var2;
      int[] var11 = var1;

      while(true) {
         if(var6[var8] != 0) {
            if(var8 == 34) {
               int var18;
               int[] var54;
               if(var7 > 0) {
                  int var12 = var11.length;
                  int[] var14;
                  if(var10 >= var12) {
                     int var13 = var11.length;
                     var14 = growArrayBy(var11, var13);
                     this._quadBuffer = var14;
                  } else {
                     var14 = var11;
                  }

                  int var15 = var10 + 1;
                  var14[var10] = var9;
                  var54 = var14;
                  var18 = var15;
               } else {
                  var18 = var10;
                  var54 = var11;
               }

               Name var53 = this._symbols.findName(var54, var18);
               Name var52;
               if(var53 == null) {
                  var52 = this.addName(var54, var18, var7);
               } else {
                  var52 = var53;
               }

               return var52;
            }

            if(var8 != 92) {
               this._throwUnquotedSpace(var8, "name");
            }

            char var17 = this._decodeEscaped();
            if(var17 > 127) {
               if(var7 >= 4) {
                  int var19 = var11.length;
                  int[] var21;
                  if(var10 >= var19) {
                     int var20 = var11.length;
                     var21 = growArrayBy(var11, var20);
                     this._quadBuffer = var21;
                  } else {
                     var21 = var11;
                  }

                  int var22 = var10 + 1;
                  var21[var10] = var9;
                  var9 = 0;
                  byte var23 = 0;
                  var10 = var22;
                  var11 = var21;
                  var7 = var23;
               }

               if(var17 < 2048) {
                  int var24 = var9 << 8;
                  int var25 = var17 >> 6 | 192;
                  var9 = var24 | var25;
                  ++var7;
               } else {
                  int var37 = var9 << 8;
                  int var38 = var17 >> 12 | 224;
                  int var39 = var37 | var38;
                  int var40 = var7 + 1;
                  if(var40 >= 4) {
                     int var41 = var11.length;
                     int[] var43;
                     if(var10 >= var41) {
                        int var42 = var11.length;
                        var43 = growArrayBy(var11, var42);
                        this._quadBuffer = var43;
                     } else {
                        var43 = var11;
                     }

                     int var44 = var10 + 1;
                     var43[var10] = var39;
                     var39 = 0;
                     byte var45 = 0;
                     var10 = var44;
                     var11 = var43;
                     var40 = var45;
                  }

                  int var46 = var39 << 8;
                  int var47 = var17 >> 6 & 63 | 128;
                  var9 = var46 | var47;
                  var7 = var40 + 1;
               }

               int var26 = var17 & 63 | 128;
            }
         }

         int[] var29;
         int var28;
         if(var7 < 4) {
            ++var7;
            int var27 = var9 << 8;
            var8 |= var27;
            var28 = var10;
            var29 = var11;
         } else {
            int var48 = var11.length;
            int[] var50;
            if(var10 >= var48) {
               int var49 = var11.length;
               var50 = growArrayBy(var11, var49);
               this._quadBuffer = var50;
            } else {
               var50 = var11;
            }

            int var51 = var10 + 1;
            var50[var10] = var9;
            var29 = var50;
            var7 = 1;
            var28 = var51;
         }

         int var30 = this._inputPtr;
         int var31 = this._inputEnd;
         if(var30 >= var31 && !this.loadMore()) {
            this._reportInvalidEOF(" in field name");
         }

         byte[] var32 = this._inputBuffer;
         int var33 = this._inputPtr;
         int var34 = var33 + 1;
         this._inputPtr = var34;
         int var35 = var32[var33] & 255;
         var11 = var29;
         var10 = var28;
         var9 = var8;
      }
   }

   protected Name parseLongFieldName(int var1) throws IOException, JsonParseException {
      int[] var2 = CharTypes.getInputCodeLatin1();
      int var3 = 2;
      int var4 = var1;

      Name var46;
      while(true) {
         int var5 = this._inputEnd;
         int var6 = this._inputPtr;
         if(var5 - var6 < 4) {
            int[] var7 = this._quadBuffer;
            byte var9 = 0;
            var46 = this.parseEscapedFieldName(var7, var3, 0, var4, var9);
            break;
         }

         byte[] var10 = this._inputBuffer;
         int var11 = this._inputPtr;
         int var12 = var11 + 1;
         this._inputPtr = var12;
         int var13 = var10[var11] & 255;
         if(var2[var13] != 0) {
            if(var13 == 34) {
               int[] var14 = this._quadBuffer;
               var46 = this.findName(var14, var3, var4, 1);
            } else {
               int[] var15 = this._quadBuffer;
               var46 = this.parseEscapedFieldName(var15, var3, var4, var13, 1);
            }
            break;
         }

         int var19 = var4 << 8 | var13;
         byte[] var20 = this._inputBuffer;
         int var21 = this._inputPtr;
         int var22 = var21 + 1;
         this._inputPtr = var22;
         var4 = var20[var21] & 255;
         if(var2[var4] != 0) {
            if(var4 == 34) {
               int[] var23 = this._quadBuffer;
               var46 = this.findName(var23, var3, var19, 2);
            } else {
               int[] var24 = this._quadBuffer;
               var46 = this.parseEscapedFieldName(var24, var3, var19, var4, 2);
            }
            break;
         }

         var19 = var19 << 8 | var4;
         byte[] var27 = this._inputBuffer;
         int var28 = this._inputPtr;
         int var29 = var28 + 1;
         this._inputPtr = var29;
         var4 = var27[var28] & 255;
         if(var2[var4] != 0) {
            if(var4 == 34) {
               int[] var30 = this._quadBuffer;
               var46 = this.findName(var30, var3, var19, 3);
            } else {
               int[] var31 = this._quadBuffer;
               var46 = this.parseEscapedFieldName(var31, var3, var19, var4, 3);
            }
            break;
         }

         var19 = var19 << 8 | var4;
         byte[] var34 = this._inputBuffer;
         int var35 = this._inputPtr;
         int var36 = var35 + 1;
         this._inputPtr = var36;
         var4 = var34[var35] & 255;
         if(var2[var4] != 0) {
            if(var4 == 34) {
               int[] var37 = this._quadBuffer;
               var46 = this.findName(var37, var3, var19, 4);
            } else {
               int[] var38 = this._quadBuffer;
               byte var41 = 4;
               var46 = this.parseEscapedFieldName(var38, var3, var19, var4, var41);
            }
            break;
         }

         int var42 = this._quadBuffer.length;
         if(var3 >= var42) {
            int[] var43 = growArrayBy(this._quadBuffer, var3);
            this._quadBuffer = var43;
         }

         int[] var44 = this._quadBuffer;
         int var45 = var3 + 1;
         var44[var3] = var19;
         var3 = var45;
      }

      return var46;
   }

   protected Name parseMediumFieldName(int var1, int var2) throws IOException, JsonParseException {
      int[] var3 = CharTypes.getInputCodeLatin1();
      byte[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      int var7 = var4[var5] & 255;
      Name var19;
      if(var3[var7] != 0) {
         if(var7 == 34) {
            var19 = this.findName(var1, var2, 1);
         } else {
            var19 = this.parseFieldName(var1, var2, var7, 1);
         }
      } else {
         int var8 = var2 << 8;
         var7 |= var8;
         byte[] var9 = this._inputBuffer;
         int var10 = this._inputPtr;
         int var11 = var10 + 1;
         this._inputPtr = var11;
         int var12 = var9[var10] & 255;
         if(var3[var12] != 0) {
            if(var12 == 34) {
               var19 = this.findName(var1, var7, 2);
            } else {
               var19 = this.parseFieldName(var1, var7, var12, 2);
            }
         } else {
            var7 = var7 << 8 | var12;
            byte[] var13 = this._inputBuffer;
            int var14 = this._inputPtr;
            int var15 = var14 + 1;
            this._inputPtr = var15;
            var12 = var13[var14] & 255;
            if(var3[var12] != 0) {
               if(var12 == 34) {
                  var19 = this.findName(var1, var7, 3);
               } else {
                  var19 = this.parseFieldName(var1, var7, var12, 3);
               }
            } else {
               var7 = var7 << 8 | var12;
               byte[] var16 = this._inputBuffer;
               int var17 = this._inputPtr;
               int var18 = var17 + 1;
               this._inputPtr = var18;
               var12 = var16[var17] & 255;
               if(var3[var12] != 0) {
                  if(var12 == 34) {
                     var19 = this.findName(var1, var7, 4);
                  } else {
                     var19 = this.parseFieldName(var1, var7, var12, 4);
                  }
               } else {
                  this._quadBuffer[0] = var1;
                  this._quadBuffer[1] = var7;
                  var19 = this.parseLongFieldName(var12);
               }
            }
         }
      }

      return var19;
   }

   public final <T extends Object> T readValueAs(Class<T> var1) throws IOException, JsonProcessingException {
      if(this._objectCodec == null) {
         throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize Json into regular Java objects");
      } else {
         return this._objectCodec.readValue(this, var1);
      }
   }

   public final <T extends Object> T readValueAs(TypeReference<?> var1) throws IOException, JsonProcessingException {
      if(this._objectCodec == null) {
         throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize Json into regular Java objects");
      } else {
         return this._objectCodec.readValue(this, var1);
      }
   }

   public final JsonNode readValueAsTree() throws IOException, JsonProcessingException {
      if(this._objectCodec == null) {
         throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize Json into JsonNode tree");
      } else {
         return this._objectCodec.readTree(this);
      }
   }

   protected IllegalArgumentException reportInvalidChar(Base64Variant var1, int var2, int var3) throws IllegalArgumentException {
      return this.reportInvalidChar(var1, var2, var3, (String)null);
   }

   protected IllegalArgumentException reportInvalidChar(Base64Variant var1, int var2, int var3, String var4) throws IllegalArgumentException {
      String var9;
      if(var2 <= 32) {
         StringBuilder var5 = (new StringBuilder()).append("Illegal white space character (code 0x");
         String var6 = Integer.toHexString(var2);
         StringBuilder var7 = var5.append(var6).append(") as character #");
         int var8 = var3 + 1;
         var9 = var7.append(var8).append(" of 4-char base64 unit: can only used between units").toString();
      } else if(var1.usesPaddingChar(var2)) {
         StringBuilder var11 = (new StringBuilder()).append("Unexpected padding character (\'");
         char var12 = var1.getPaddingChar();
         StringBuilder var13 = var11.append(var12).append("\') as character #");
         int var14 = var3 + 1;
         var9 = var13.append(var14).append(" of 4-char base64 unit: padding only legal as 3rd or 4th character").toString();
      } else if(Character.isDefined(var2) && !Character.isISOControl(var2)) {
         StringBuilder var17 = (new StringBuilder()).append("Illegal character \'");
         char var18 = (char)var2;
         StringBuilder var19 = var17.append(var18).append("\' (code 0x");
         String var20 = Integer.toHexString(var2);
         var9 = var19.append(var20).append(") in base64 content").toString();
      } else {
         StringBuilder var15 = (new StringBuilder()).append("Illegal character (code 0x");
         String var16 = Integer.toHexString(var2);
         var9 = var15.append(var16).append(") in base64 content").toString();
      }

      if(var4 != null) {
         var9 + ": " + var4;
      }

      return new IllegalArgumentException(var9);
   }

   protected Name slowParseFieldName() throws IOException, JsonParseException {
      int var1 = this._inputPtr;
      int var2 = this._inputEnd;
      if(var1 >= var2 && !this.loadMore()) {
         this._reportInvalidEOF(": was expecting closing quote for name");
      }

      byte[] var3 = this._inputBuffer;
      int var4 = this._inputPtr;
      int var5 = var4 + 1;
      this._inputPtr = var5;
      int var6 = var3[var4] & 255;
      Name var7;
      if(var6 == 34) {
         var7 = BytesToNameCanonicalizer.getEmptyName();
      } else {
         int[] var8 = this._quadBuffer;
         byte var10 = 0;
         byte var11 = 0;
         var7 = this.parseEscapedFieldName(var8, 0, var10, var6, var11);
      }

      return var7;
   }
}
