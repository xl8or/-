package org.codehaus.jackson.impl;

import java.io.IOException;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.impl.JsonReadContext;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.util.ByteArrayBuilder;
import org.codehaus.jackson.util.TextBuffer;

public abstract class JsonParserBase extends JsonParser {

   static final int INT_ASTERISK = 42;
   static final int INT_BACKSLASH = 92;
   static final int INT_COLON = 58;
   static final int INT_COMMA = 44;
   static final int INT_CR = 13;
   static final int INT_LBRACKET = 91;
   static final int INT_LCURLY = 123;
   static final int INT_LF = 10;
   static final int INT_QUOTE = 34;
   static final int INT_RBRACKET = 93;
   static final int INT_RCURLY = 125;
   static final int INT_SLASH = 47;
   static final int INT_SPACE = 32;
   static final int INT_TAB = 9;
   static final int INT_b = 98;
   static final int INT_f = 102;
   static final int INT_n = 110;
   static final int INT_r = 114;
   static final int INT_t = 116;
   static final int INT_u = 117;
   protected byte[] _binaryValue;
   ByteArrayBuilder _byteArrayBuilder = null;
   protected boolean _closed;
   protected long _currInputProcessed = 0L;
   protected int _currInputRow = 1;
   protected int _currInputRowStart = 0;
   protected int _inputEnd = 0;
   protected int _inputPtr = 0;
   protected final IOContext _ioContext;
   protected boolean _nameCopied = 0;
   protected char[] _nameCopyBuffer = null;
   protected JsonToken _nextToken;
   protected JsonReadContext _parsingContext;
   protected final TextBuffer _textBuffer;
   protected boolean _tokenIncomplete = 0;
   protected int _tokenInputCol = 0;
   protected int _tokenInputRow = 1;
   protected long _tokenInputTotal = 0L;


   protected JsonParserBase(IOContext var1, int var2) {
      this._ioContext = var1;
      this._features = var2;
      TextBuffer var3 = var1.constructTextBuffer();
      this._textBuffer = var3;
      int var4 = this._tokenInputRow;
      int var5 = this._tokenInputCol;
      JsonReadContext var6 = JsonReadContext.createRootContext(var4, var5);
      this._parsingContext = var6;
   }

   protected static final String _getCharDesc(int var0) {
      char var1 = (char)var0;
      String var4;
      if(Character.isISOControl(var1)) {
         var4 = "(CTRL-CHAR, code " + var0 + ")";
      } else if(var0 > 255) {
         StringBuilder var2 = (new StringBuilder()).append("\'").append(var1).append("\' (code ").append(var0).append(" / 0x");
         String var3 = Integer.toHexString(var0);
         var4 = var2.append(var3).append(")").toString();
      } else {
         var4 = "\'" + var1 + "\' (code " + var0 + ")";
      }

      return var4;
   }

   protected abstract void _closeInput() throws IOException;

   protected final JsonParseException _constructError(String var1) {
      JsonLocation var2 = this.getCurrentLocation();
      return new JsonParseException(var1, var2);
   }

   protected final JsonParseException _constructError(String var1, Throwable var2) {
      JsonLocation var3 = this.getCurrentLocation();
      return new JsonParseException(var1, var3, var2);
   }

   protected abstract byte[] _decodeBase64(Base64Variant var1) throws IOException, JsonParseException;

   protected abstract void _finishString() throws IOException, JsonParseException;

   public ByteArrayBuilder _getByteArrayBuilder() {
      if(this._byteArrayBuilder == null) {
         ByteArrayBuilder var1 = new ByteArrayBuilder();
         this._byteArrayBuilder = var1;
      } else {
         this._byteArrayBuilder.reset();
      }

      return this._byteArrayBuilder;
   }

   protected void _handleEOF() throws JsonParseException {
      if(!this._parsingContext.inRoot()) {
         StringBuilder var1 = (new StringBuilder()).append(": expected close marker for ");
         String var2 = this._parsingContext.getTypeDesc();
         StringBuilder var3 = var1.append(var2).append(" (from ");
         JsonReadContext var4 = this._parsingContext;
         Object var5 = this._ioContext.getSourceReference();
         JsonLocation var6 = var4.getStartLocation(var5);
         String var7 = var3.append(var6).append(")").toString();
         this._reportInvalidEOF(var7);
      }
   }

   protected void _releaseBuffers() throws IOException {
      this._textBuffer.releaseBuffers();
      char[] var1 = this._nameCopyBuffer;
      if(var1 != null) {
         this._nameCopyBuffer = null;
         this._ioContext.releaseNameCopyBuffer(var1);
      }
   }

   protected final void _reportError(String var1) throws JsonParseException {
      throw this._constructError(var1);
   }

   protected void _reportInvalidEOF() throws JsonParseException {
      StringBuilder var1 = (new StringBuilder()).append(" in ");
      JsonToken var2 = this._currToken;
      String var3 = var1.append(var2).toString();
      this._reportInvalidEOF(var3);
   }

   protected void _reportInvalidEOF(String var1) throws JsonParseException {
      String var2 = "Unexpected end-of-input" + var1;
      this._reportError(var2);
   }

   protected void _reportMismatchedEndMarker(int var1, char var2) throws JsonParseException {
      StringBuilder var3 = (new StringBuilder()).append("");
      JsonReadContext var4 = this._parsingContext;
      Object var5 = this._ioContext.getSourceReference();
      JsonLocation var6 = var4.getStartLocation(var5);
      String var7 = var3.append(var6).toString();
      StringBuilder var8 = (new StringBuilder()).append("Unexpected close marker \'");
      char var9 = (char)var1;
      StringBuilder var10 = var8.append(var9).append("\': expected \'").append(var2).append("\' (for ");
      String var11 = this._parsingContext.getTypeDesc();
      String var12 = var10.append(var11).append(" starting at ").append(var7).append(")").toString();
      this._reportError(var12);
   }

   protected void _reportUnexpectedChar(int var1, String var2) throws JsonParseException {
      StringBuilder var3 = (new StringBuilder()).append("Unexpected character (");
      String var4 = _getCharDesc(var1);
      String var5 = var3.append(var4).append(")").toString();
      if(var2 != null) {
         var5 = var5 + ": " + var2;
      }

      this._reportError(var5);
   }

   protected final void _throwInternal() {
      throw new RuntimeException("Internal error: this code path should never get executed");
   }

   protected void _throwInvalidSpace(int var1) throws JsonParseException {
      char var2 = (char)var1;
      StringBuilder var3 = (new StringBuilder()).append("Illegal character (");
      String var4 = _getCharDesc(var2);
      String var5 = var3.append(var4).append("): only regular white space (\\r, \\n, \\t) is allowed between tokens").toString();
      this._reportError(var5);
   }

   protected void _throwUnquotedSpace(int var1, String var2) throws JsonParseException {
      char var3 = (char)var1;
      StringBuilder var4 = (new StringBuilder()).append("Illegal unquoted character (");
      String var5 = _getCharDesc(var3);
      String var6 = var4.append(var5).append("): has to be escaped using backslash to be included in ").append(var2).toString();
      this._reportError(var6);
   }

   protected final void _wrapError(String var1, Throwable var2) throws JsonParseException {
      throw this._constructError(var1, var2);
   }

   public void close() throws IOException {
      this._closed = (boolean)1;
      this._closeInput();
      this._releaseBuffers();
   }

   public final byte[] getBinaryValue(Base64Variant var1) throws IOException, JsonParseException {
      JsonToken var2 = this._currToken;
      JsonToken var3 = JsonToken.VALUE_STRING;
      if(var2 != var3) {
         StringBuilder var4 = (new StringBuilder()).append("Current token (");
         JsonToken var5 = this._currToken;
         String var6 = var4.append(var5).append(") not VALUE_STRING, can not access as binary").toString();
         this._reportError(var6);
      }

      if(this._tokenIncomplete) {
         try {
            byte[] var7 = this._decodeBase64(var1);
            this._binaryValue = var7;
         } catch (IllegalArgumentException var12) {
            StringBuilder var9 = (new StringBuilder()).append("Failed to decode VALUE_STRING as base64 (").append(var1).append("): ");
            String var10 = var12.getMessage();
            String var11 = var9.append(var10).toString();
            throw this._constructError(var11);
         }

         this._tokenIncomplete = (boolean)0;
      }

      return this._binaryValue;
   }

   public JsonLocation getCurrentLocation() {
      int var1 = this._inputPtr;
      int var2 = this._currInputRowStart;
      int var3 = var1 - var2 + 1;
      Object var4 = this._ioContext.getSourceReference();
      long var5 = this._currInputProcessed;
      long var7 = (long)this._inputPtr;
      long var9 = var5 + var7 - 1L;
      int var11 = this._currInputRow;
      return new JsonLocation(var4, var9, var11, var3);
   }

   public String getCurrentName() throws IOException, JsonParseException {
      return this._parsingContext.getCurrentName();
   }

   public JsonReadContext getParsingContext() {
      return this._parsingContext;
   }

   public String getText() throws IOException, JsonParseException {
      String var3;
      if(this._currToken != null) {
         int[] var1 = JsonParserBase.1.$SwitchMap$org$codehaus$jackson$JsonToken;
         int var2 = this._currToken.ordinal();
         switch(var1[var2]) {
         case 5:
            var3 = this._parsingContext.getCurrentName();
            break;
         case 6:
            if(this._tokenIncomplete) {
               this._tokenIncomplete = (boolean)0;
               this._finishString();
            }
         case 7:
         case 8:
            var3 = this._textBuffer.contentsAsString();
            break;
         default:
            var3 = this._currToken.asString();
         }
      } else {
         var3 = null;
      }

      return var3;
   }

   public char[] getTextCharacters() throws IOException, JsonParseException {
      char[] var3;
      if(this._currToken != null) {
         int[] var1 = JsonParserBase.1.$SwitchMap$org$codehaus$jackson$JsonToken;
         int var2 = this._currToken.ordinal();
         switch(var1[var2]) {
         case 5:
            if(!this._nameCopied) {
               String var4 = this._parsingContext.getCurrentName();
               int var5 = var4.length();
               if(this._nameCopyBuffer == null) {
                  char[] var6 = this._ioContext.allocNameCopyBuffer(var5);
                  this._nameCopyBuffer = var6;
               } else if(this._nameCopyBuffer.length < var5) {
                  char[] var8 = new char[var5];
                  this._nameCopyBuffer = var8;
               }

               char[] var7 = this._nameCopyBuffer;
               var4.getChars(0, var5, var7, 0);
               this._nameCopied = (boolean)1;
            }

            var3 = this._nameCopyBuffer;
            break;
         case 6:
            if(this._tokenIncomplete) {
               this._tokenIncomplete = (boolean)0;
               this._finishString();
            }
         case 7:
         case 8:
            var3 = this._textBuffer.getTextBuffer();
            break;
         default:
            var3 = this._currToken.asCharArray();
         }
      } else {
         var3 = null;
      }

      return var3;
   }

   public int getTextLength() throws IOException, JsonParseException {
      int var3;
      if(this._currToken != null) {
         int[] var1 = JsonParserBase.1.$SwitchMap$org$codehaus$jackson$JsonToken;
         int var2 = this._currToken.ordinal();
         switch(var1[var2]) {
         case 5:
            var3 = this._parsingContext.getCurrentName().length();
            break;
         case 6:
            if(this._tokenIncomplete) {
               this._tokenIncomplete = (boolean)0;
               this._finishString();
            }
         case 7:
         case 8:
            var3 = this._textBuffer.size();
            break;
         default:
            var3 = this._currToken.asCharArray().length;
         }
      } else {
         var3 = 0;
      }

      return var3;
   }

   public int getTextOffset() throws IOException, JsonParseException {
      int var3;
      if(this._currToken != null) {
         int[] var1 = JsonParserBase.1.$SwitchMap$org$codehaus$jackson$JsonToken;
         int var2 = this._currToken.ordinal();
         switch(var1[var2]) {
         case 5:
            var3 = 0;
            return var3;
         case 6:
            if(this._tokenIncomplete) {
               this._tokenIncomplete = (boolean)0;
               this._finishString();
            }
         case 7:
         case 8:
            var3 = this._textBuffer.getTextOffset();
            return var3;
         }
      }

      var3 = 0;
      return var3;
   }

   public final long getTokenCharacterOffset() {
      return this._tokenInputTotal;
   }

   public final int getTokenColumnNr() {
      return this._tokenInputCol + 1;
   }

   public final int getTokenLineNr() {
      return this._tokenInputRow;
   }

   public JsonLocation getTokenLocation() {
      Object var1 = this._ioContext.getSourceReference();
      long var2 = this.getTokenCharacterOffset();
      int var4 = this.getTokenLineNr();
      int var5 = this.getTokenColumnNr();
      return new JsonLocation(var1, var2, var4, var5);
   }

   public boolean isClosed() {
      return this._closed;
   }

   protected abstract boolean loadMore() throws IOException;

   protected final void loadMoreGuaranteed() throws IOException {
      if(!this.loadMore()) {
         this._reportInvalidEOF();
      }
   }

   public abstract JsonToken nextToken() throws IOException, JsonParseException;

   public final JsonToken nextValue() throws IOException, JsonParseException {
      JsonToken var1 = this.nextToken();
      JsonToken var2 = JsonToken.FIELD_NAME;
      if(var1 == var2) {
         var1 = this.nextToken();
      }

      return var1;
   }

   public void skipChildren() throws IOException, JsonParseException {
      JsonToken var1 = this._currToken;
      JsonToken var2 = JsonToken.START_OBJECT;
      if(var1 != var2) {
         JsonToken var3 = this._currToken;
         JsonToken var4 = JsonToken.START_ARRAY;
         if(var3 != var4) {
            return;
         }
      }

      int var5 = 1;

      while(true) {
         JsonToken var6 = this.nextToken();
         if(var6 == null) {
            this._handleEOF();
            return;
         }

         int[] var7 = JsonParserBase.1.$SwitchMap$org$codehaus$jackson$JsonToken;
         int var8 = var6.ordinal();
         switch(var7[var8]) {
         case 1:
         case 2:
            ++var5;
            break;
         case 3:
         case 4:
            var5 += -1;
            if(var5 == 0) {
               return;
            }
         }
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$org$codehaus$jackson$JsonToken = new int[JsonToken.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var1 = JsonToken.START_OBJECT.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var31) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var3 = JsonToken.START_ARRAY.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var30) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var5 = JsonToken.END_OBJECT.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var29) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var7 = JsonToken.END_ARRAY.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var28) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var9 = JsonToken.FIELD_NAME.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var27) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var11 = JsonToken.VALUE_STRING.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var26) {
            ;
         }

         try {
            int[] var12 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var13 = JsonToken.VALUE_NUMBER_INT.ordinal();
            var12[var13] = 7;
         } catch (NoSuchFieldError var25) {
            ;
         }

         try {
            int[] var14 = $SwitchMap$org$codehaus$jackson$JsonToken;
            int var15 = JsonToken.VALUE_NUMBER_FLOAT.ordinal();
            var14[var15] = 8;
         } catch (NoSuchFieldError var24) {
            ;
         }
      }
   }
}
