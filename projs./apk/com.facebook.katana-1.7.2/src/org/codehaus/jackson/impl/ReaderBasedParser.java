package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.Reader;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.impl.JsonReadContext;
import org.codehaus.jackson.impl.ReaderBasedNumericParser;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.sym.CharsToNameCanonicalizer;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.util.ByteArrayBuilder;
import org.codehaus.jackson.util.CharTypes;
import org.codehaus.jackson.util.TextBuffer;

public class ReaderBasedParser extends ReaderBasedNumericParser {

   final ObjectCodec _objectCodec;
   protected final CharsToNameCanonicalizer _symbols;


   public ReaderBasedParser(IOContext var1, int var2, Reader var3, ObjectCodec var4, CharsToNameCanonicalizer var5) {
      super(var1, var2, var3);
      this._objectCodec = var4;
      this._symbols = var5;
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

         char[] var8 = this._inputBuffer;
         int var9 = this._inputPtr;
         char var10 = var8[var9];
         if(!Character.isJavaIdentifierPart(var10)) {
            break;
         }

         int var11 = this._inputPtr + 1;
         this._inputPtr = var11;
         var2.append(var10);
      }

      StringBuilder var5 = (new StringBuilder()).append("Unrecognized token \'");
      String var6 = var2.toString();
      String var7 = var5.append(var6).append("\': was expecting \'null\', \'true\' or \'false\'").toString();
      this._reportError(var7);
   }

   private final void _skipCComment() throws IOException, JsonParseException {
      while(true) {
         int var1 = this._inputPtr;
         int var2 = this._inputEnd;
         if(var1 >= var2 && !this.loadMore()) {
            break;
         }

         char[] var3 = this._inputBuffer;
         int var4 = this._inputPtr;
         int var5 = var4 + 1;
         this._inputPtr = var5;
         char var6 = var3[var4];
         if(var6 <= 42) {
            if(var6 != 42) {
               if(var6 < 32) {
                  if(var6 == 10) {
                     this._skipLF();
                  } else if(var6 == 13) {
                     this._skipCR();
                  } else if(var6 != 9) {
                     this._throwInvalidSpace(var6);
                  }
               }
            } else {
               int var7 = this._inputPtr;
               int var8 = this._inputEnd;
               if(var7 >= var8 && !this.loadMore()) {
                  break;
               }

               char[] var9 = this._inputBuffer;
               int var10 = this._inputPtr;
               if(var9[var10] == 47) {
                  int var11 = this._inputPtr + 1;
                  this._inputPtr = var11;
                  return;
               }
            }
         }
      }

      this._reportInvalidEOF(" in a comment");
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

      char[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      char var7 = var4[var5];
      if(var7 == 47) {
         this._skipCppComment();
      } else if(var7 == 42) {
         this._skipCComment();
      } else {
         this._reportUnexpectedChar(var7, "was expecting either \'*\' or \'/\' for a comment");
      }
   }

   private final void _skipCppComment() throws IOException, JsonParseException {
      while(true) {
         int var1 = this._inputPtr;
         int var2 = this._inputEnd;
         if(var1 >= var2 && !this.loadMore()) {
            return;
         }

         char[] var3 = this._inputBuffer;
         int var4 = this._inputPtr;
         int var5 = var4 + 1;
         this._inputPtr = var5;
         char var6 = var3[var4];
         if(var6 < 32) {
            if(var6 == 10) {
               this._skipLF();
               return;
            }

            if(var6 == 13) {
               this._skipCR();
               return;
            }

            if(var6 != 9) {
               this._throwInvalidSpace(var6);
            }
         }
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

         char[] var3 = this._inputBuffer;
         int var4 = this._inputPtr;
         int var5 = var4 + 1;
         this._inputPtr = var5;
         char var6 = var3[var4];
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

         char[] var3 = this._inputBuffer;
         int var4 = this._inputPtr;
         int var5 = var4 + 1;
         this._inputPtr = var5;
         var6 = var3[var4];
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

   private void handleFieldName2(int var1, int var2) throws IOException, JsonParseException {
      TextBuffer var3 = this._textBuffer;
      char[] var4 = this._inputBuffer;
      int var5 = this._inputPtr - var1;
      var3.resetWithShared(var4, var1, var5);
      char[] var6 = this._textBuffer.getCurrentSegment();
      int var7 = this._textBuffer.getCurrentSegmentSize();
      int var8 = var2;
      int var10 = var7;
      char[] var11 = var6;

      while(true) {
         int var12 = this._inputPtr;
         int var13 = this._inputEnd;
         if(var12 >= var13 && !this.loadMore()) {
            this._reportInvalidEOF(": was expecting closing quote for name");
         }

         char var17;
         char var18;
         label32: {
            char[] var14 = this._inputBuffer;
            int var15 = this._inputPtr;
            int var16 = var15 + 1;
            this._inputPtr = var16;
            var17 = var14[var15];
            if(var17 <= 92) {
               if(var17 == 92) {
                  var18 = this._decodeEscaped();
                  break label32;
               }

               if(var17 <= 34) {
                  if(var17 == 34) {
                     this._textBuffer.setCurrentLength(var10);
                     TextBuffer var23 = this._textBuffer;
                     char[] var24 = var23.getTextBuffer();
                     int var25 = var23.getTextOffset();
                     int var26 = var23.size();
                     JsonReadContext var27 = this._parsingContext;
                     String var28 = this._symbols.findSymbol(var24, var25, var26, var8);
                     var27.setCurrentName(var28);
                     return;
                  }

                  if(var17 < 32) {
                     this._throwUnquotedSpace(var17, "name");
                  }
               }
            }

            var18 = var17;
         }

         var8 = var8 * 31 + var17;
         int var19 = var10 + 1;
         var11[var10] = var18;
         int var20 = var11.length;
         if(var19 >= var20) {
            char[] var21 = this._textBuffer.finishCurrentSegment();
            byte var22 = 0;
            var11 = var21;
            var10 = var22;
         } else {
            var10 = var19;
         }
      }
   }

   protected byte[] _decodeBase64(Base64Variant var1) throws IOException, JsonParseException {
      ByteArrayBuilder var2 = this._getByteArrayBuilder();

      while(true) {
         int var3 = this._inputPtr;
         int var4 = this._inputEnd;
         if(var3 >= var4) {
            this.loadMoreGuaranteed();
         }

         char[] var5 = this._inputBuffer;
         int var6 = this._inputPtr;
         int var7 = var6 + 1;
         this._inputPtr = var7;
         char var8 = var5[var6];
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

            char[] var12 = this._inputBuffer;
            int var13 = this._inputPtr;
            int var14 = var13 + 1;
            this._inputPtr = var14;
            char var15 = var12[var13];
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

            char[] var20 = this._inputBuffer;
            int var21 = this._inputPtr;
            int var22 = var21 + 1;
            this._inputPtr = var22;
            char var23 = var20[var21];
            int var24 = var1.decodeBase64Char(var23);
            char var30;
            if(var24 < 0) {
               if(var24 != -1) {
                  throw this.reportInvalidChar(var1, var23, 2);
               }

               int var25 = this._inputPtr;
               int var26 = this._inputEnd;
               if(var25 >= var26) {
                  this.loadMoreGuaranteed();
               }

               char[] var27 = this._inputBuffer;
               int var28 = this._inputPtr;
               int var29 = var28 + 1;
               this._inputPtr = var29;
               var30 = var27[var28];
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

               char[] var38 = this._inputBuffer;
               int var39 = this._inputPtr;
               int var40 = var39 + 1;
               this._inputPtr = var40;
               var30 = var38[var39];
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

   protected final char _decodeEscaped() throws IOException, JsonParseException {
      boolean var1 = false;
      int var2 = this._inputPtr;
      int var3 = this._inputEnd;
      if(var2 >= var3 && !this.loadMore()) {
         this._reportInvalidEOF(" in character escape sequence");
      }

      char[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      char var7 = var4[var5];
      switch(var7) {
      case 34:
      case 47:
      case 92:
         break;
      case 98:
         var7 = 8;
         break;
      case 102:
         var7 = 12;
         break;
      case 110:
         var7 = 10;
         break;
      case 114:
         var7 = 13;
         break;
      case 116:
         var7 = 9;
         break;
      default:
         StringBuilder var8 = (new StringBuilder()).append("Unrecognized character escape ");
         String var9 = _getCharDesc(var7);
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

            char[] var15 = this._inputBuffer;
            int var16 = this._inputPtr;
            int var17 = var16 + 1;
            this._inputPtr = var17;
            char var18 = var15[var16];
            int var20 = CharTypes.charToHex(var18);
            if(var20 < 0) {
               this._reportUnexpectedChar(var18, "expected a hex-digit for character escape sequence");
            }

            int var19 = var12 << 4 | var20;
         }

         var7 = (char)var12;
      }

      return var7;
   }

   protected void _finishString() throws IOException, JsonParseException {
      int var1 = this._inputPtr;
      int var2 = this._inputEnd;
      if(var1 < var2) {
         int[] var3 = CharTypes.getInputCodeLatin1();
         int var4 = var3.length;

         do {
            char var5 = this._inputBuffer[var1];
            if(var5 < var4 && var3[var5] != 0) {
               if(var5 == 34) {
                  TextBuffer var6 = this._textBuffer;
                  char[] var7 = this._inputBuffer;
                  int var8 = this._inputPtr;
                  int var9 = this._inputPtr;
                  int var10 = var1 - var9;
                  var6.resetWithShared(var7, var8, var10);
                  int var11 = var1 + 1;
                  this._inputPtr = var11;
                  return;
               }
               break;
            }
         } while(var1 + 1 < var2);
      }

      TextBuffer var12 = this._textBuffer;
      char[] var13 = this._inputBuffer;
      int var14 = this._inputPtr;
      int var15 = this._inputPtr;
      int var16 = var1 - var15;
      var12.resetWithCopy(var13, var14, var16);
      this._inputPtr = var1;
      this._finishString2();
   }

   protected void _finishString2() throws IOException, JsonParseException {
      char[] var1 = this._textBuffer.getCurrentSegment();
      int var2 = this._textBuffer.getCurrentSegmentSize();
      char[] var3 = var1;
      int var4 = var2;

      while(true) {
         int var5 = this._inputPtr;
         int var6 = this._inputEnd;
         if(var5 >= var6 && !this.loadMore()) {
            this._reportInvalidEOF(": was expecting closing quote for a string value");
         }

         char[] var7 = this._inputBuffer;
         int var8 = this._inputPtr;
         int var9 = var8 + 1;
         this._inputPtr = var9;
         char var10 = var7[var8];
         if(var10 <= 92) {
            if(var10 == 92) {
               var10 = this._decodeEscaped();
            } else if(var10 <= 34) {
               if(var10 == 34) {
                  this._textBuffer.setCurrentLength(var4);
                  return;
               }

               if(var10 < 32) {
                  this._throwUnquotedSpace(var10, "string value");
               }
            }
         }

         int var11 = var3.length;
         if(var4 >= var11) {
            char[] var12 = this._textBuffer.finishCurrentSegment();
            byte var13 = 0;
            var3 = var12;
            var4 = var13;
         }

         int var14 = var4 + 1;
         var3[var4] = var10;
         var4 = var14;
      }
   }

   protected void _handleFieldName(int var1) throws IOException, JsonParseException {
      if(var1 != 34) {
         this._reportUnexpectedChar(var1, "was expecting double-quote to start field name");
      }

      int var2 = this._inputPtr;
      byte var3 = 0;
      int var4 = this._inputEnd;
      int var8;
      byte var9;
      if(var2 < var4) {
         int[] var5 = CharTypes.getInputCodeLatin1();
         int var6 = var5.length;
         var8 = var2;
         var9 = var3;

         do {
            char var10 = this._inputBuffer[var8];
            if(var10 < var6 && var5[var10] != 0) {
               if(var10 == 34) {
                  int var11 = this._inputPtr;
                  int var12 = var8 + 1;
                  this._inputPtr = var12;
                  CharsToNameCanonicalizer var13 = this._symbols;
                  char[] var14 = this._inputBuffer;
                  int var15 = var8 - var11;
                  String var16 = var13.findSymbol(var14, var11, var15, var9);
                  this._parsingContext.setCurrentName(var16);
                  return;
               }
               break;
            }

            int var17 = var9 * 31 + var10;
         } while(var8 + 1 < var4);
      } else {
         var8 = var2;
         var9 = var3;
      }

      int var18 = this._inputPtr;
      this._inputPtr = var8;
      this.handleFieldName2(var18, var9);
   }

   protected void _matchToken(JsonToken var1) throws IOException, JsonParseException {
      String var2 = var1.asString();
      int var3 = 1;

      for(int var4 = var2.length(); var3 < var4; ++var3) {
         int var5 = this._inputPtr;
         int var6 = this._inputEnd;
         if(var5 >= var6 && !this.loadMore()) {
            this._reportInvalidEOF(" in a value");
         }

         char[] var7 = this._inputBuffer;
         int var8 = this._inputPtr;
         char var9 = var7[var8];
         char var10 = var2.charAt(var3);
         if(var9 != var10) {
            String var11 = var2.substring(0, var3);
            this._reportInvalidToken(var11);
         }

         int var12 = this._inputPtr + 1;
         this._inputPtr = var12;
      }

   }

   protected final void _skipCR() throws IOException {
      int var1 = this._inputPtr;
      int var2 = this._inputEnd;
      if(var1 < var2 || this.loadMore()) {
         char[] var3 = this._inputBuffer;
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
      int var1 = this._inputPtr;
      int var2 = this._inputEnd;
      char[] var3 = this._inputBuffer;
      int var5 = var1;
      int var6 = var2;

      while(true) {
         if(var5 >= var6) {
            this._inputPtr = var5;
            if(!this.loadMore()) {
               this._reportInvalidEOF(": was expecting closing quote for a string value");
            }

            int var7 = this._inputPtr;
            int var8 = this._inputEnd;
            var5 = var7;
            var6 = var8;
         }

         int var9 = var5 + 1;
         char var10 = var3[var5];
         if(var10 <= 92) {
            if(var10 == 92) {
               this._inputPtr = var9;
               char var11 = this._decodeEscaped();
               int var12 = this._inputPtr;
               int var13 = this._inputEnd;
               var5 = var12;
               var6 = var13;
               continue;
            }

            if(var10 <= 34) {
               if(var10 == 34) {
                  this._inputPtr = var9;
                  return;
               }

               if(var10 < 32) {
                  this._inputPtr = var9;
                  this._throwUnquotedSpace(var10, "string value");
               }
            }
         }

         var5 = var9;
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
                  this._handleFieldName(var3);
                  JsonToken var20 = JsonToken.FIELD_NAME;
                  this._currToken = var20;
                  int var21 = this._skipWS();
                  if(var21 != 58) {
                     this._reportUnexpectedChar(var21, "was expecting a colon to separate field name and value");
                  }

                  var3 = this._skipWS();
               }

               switch(var3) {
               case 34:
                  this._tokenIncomplete = (boolean)1;
                  JsonToken var22 = JsonToken.VALUE_STRING;
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
                     JsonReadContext var23 = this._parsingContext;
                     int var24 = this._tokenInputRow;
                     int var25 = this._tokenInputCol;
                     JsonReadContext var26 = var23.createChildArrayContext(var24, var25);
                     this._parsingContext = var26;
                  }

                  JsonToken var27 = JsonToken.START_ARRAY;
                  break;
               case 93:
               case 125:
                  this._reportUnexpectedChar(var3, "expected a value");
               case 102:
                  JsonToken var35 = JsonToken.VALUE_FALSE;
                  this._matchToken(var35);
                  JsonToken var36 = JsonToken.VALUE_FALSE;
                  break;
               case 110:
                  JsonToken var37 = JsonToken.VALUE_NULL;
                  this._matchToken(var37);
                  JsonToken var38 = JsonToken.VALUE_NULL;
                  break;
               case 116:
                  JsonToken var33 = JsonToken.VALUE_TRUE;
                  this._matchToken(var33);
                  JsonToken var34 = JsonToken.VALUE_TRUE;
                  break;
               case 123:
                  if(!var19) {
                     JsonReadContext var28 = this._parsingContext;
                     int var29 = this._tokenInputRow;
                     int var30 = this._tokenInputCol;
                     JsonReadContext var31 = var28.createChildObjectContext(var29, var30);
                     this._parsingContext = var31;
                  }

                  JsonToken var32 = JsonToken.START_OBJECT;
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

   protected IllegalArgumentException reportInvalidChar(Base64Variant var1, char var2, int var3) throws IllegalArgumentException {
      return this.reportInvalidChar(var1, var2, var3, (String)null);
   }

   protected IllegalArgumentException reportInvalidChar(Base64Variant var1, char var2, int var3, String var4) throws IllegalArgumentException {
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
         StringBuilder var17 = (new StringBuilder()).append("Illegal character \'").append(var2).append("\' (code 0x");
         String var18 = Integer.toHexString(var2);
         var9 = var17.append(var18).append(") in base64 content").toString();
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
}
