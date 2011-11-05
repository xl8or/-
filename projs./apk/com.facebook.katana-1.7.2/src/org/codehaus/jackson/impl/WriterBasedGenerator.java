package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.impl.JsonGeneratorBase;
import org.codehaus.jackson.impl.JsonWriteContext;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.io.NumberOutput;
import org.codehaus.jackson.util.CharTypes;

public final class WriterBasedGenerator extends JsonGeneratorBase {

   static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
   static final int SHORT_WRITE = 32;
   protected char[] _entityBuffer;
   protected final IOContext _ioContext;
   protected char[] _outputBuffer;
   protected int _outputEnd;
   protected int _outputHead = 0;
   protected int _outputTail = 0;
   protected final Writer _writer;


   public WriterBasedGenerator(IOContext var1, int var2, ObjectCodec var3, Writer var4) {
      super(var2, var3);
      this._ioContext = var1;
      this._writer = var4;
      char[] var5 = var1.allocConcatBuffer();
      this._outputBuffer = var5;
      int var6 = this._outputBuffer.length;
      this._outputEnd = var6;
   }

   private void _appendSingleEscape(int var1, char[] var2, int var3) {
      if(var1 < 0) {
         int var4 = -(var1 + 1);
         var2[var3] = 92;
         int var5 = var3 + 1;
         var2[var5] = 117;
         int var6 = var5 + 1;
         var2[var6] = 48;
         int var7 = var6 + 1;
         var2[var7] = 48;
         int var8 = var7 + 1;
         char[] var9 = HEX_CHARS;
         int var10 = var4 >> 4;
         char var11 = var9[var10];
         var2[var8] = var11;
         int var12 = var8 + 1;
         char[] var13 = HEX_CHARS;
         int var14 = var4 & 15;
         char var15 = var13[var14];
         var2[var12] = var15;
      } else {
         var2[var3] = 92;
         int var16 = var3 + 1;
         char var17 = (char)var1;
         var2[var16] = var17;
      }
   }

   private void _writeLongString(String var1) throws IOException, JsonGenerationException {
      this._flushBuffer();
      int var2 = var1.length();
      int var3 = 0;

      do {
         int var4 = this._outputEnd;
         if(var3 + var4 > var2) {
            var4 = var2 - var3;
         }

         int var5 = var3 + var4;
         char[] var6 = this._outputBuffer;
         var1.getChars(var3, var5, var6, 0);
         this._writeSegment(var4);
         var3 += var4;
      } while(var3 < var2);

   }

   private final void _writeSegment(int var1) throws IOException, JsonGenerationException {
      int[] var2 = CharTypes.getOutputEscapes();
      int var3 = var2.length;
      int var4 = 0;

      while(var4 < var1) {
         int var5 = var4;

         do {
            char var6 = this._outputBuffer[var5];
            if(var6 < var3 && var2[var6] != 0) {
               break;
            }

            ++var5;
         } while(var5 < var1);

         int var7 = var5 - var4;
         if(var7 > 0) {
            Writer var8 = this._writer;
            char[] var9 = this._outputBuffer;
            var8.write(var9, var4, var7);
            if(var5 >= var1) {
               return;
            }
         }

         char var10 = this._outputBuffer[var5];
         int var11 = var2[var10];
         int var12 = var5 + 1;
         byte var13;
         if(var11 < 0) {
            var13 = 6;
         } else {
            var13 = 2;
         }

         int var14 = this._outputTail;
         if(var13 > var14) {
            this._writeSingleEscape(var11);
            var4 = var12;
         } else {
            int var15 = var12 - var13;
            char[] var16 = this._outputBuffer;
            this._appendSingleEscape(var11, var16, var15);
            var4 = var15;
         }
      }

   }

   private void _writeSingleEscape(int var1) throws IOException {
      char[] var2 = this._entityBuffer;
      if(var2 == null) {
         var2 = new char[]{'\\', '\u0000', '0', '0', '\u0000', '\u0000'};
      }

      if(var1 < 0) {
         int var3 = -(var1 + 1);
         var2[1] = 117;
         char[] var4 = HEX_CHARS;
         int var5 = var3 >> 4;
         char var6 = var4[var5];
         var2[4] = var6;
         char[] var7 = HEX_CHARS;
         int var8 = var3 & 15;
         char var9 = var7[var8];
         var2[5] = var9;
         this._writer.write(var2, 0, 6);
      } else {
         char var10 = (char)var1;
         var2[1] = var10;
         this._writer.write(var2, 0, 2);
      }
   }

   private void _writeString(String var1) throws IOException, JsonGenerationException {
      int var2 = var1.length();
      int var3 = this._outputEnd;
      if(var2 > var3) {
         this._writeLongString(var1);
      } else {
         int var4 = this._outputTail + var2;
         int var5 = this._outputEnd;
         if(var4 > var5) {
            this._flushBuffer();
         }

         char[] var6 = this._outputBuffer;
         int var7 = this._outputTail;
         var1.getChars(0, var2, var6, var7);
         int var8 = this._outputTail;
         var2 += var8;
         int[] var9 = CharTypes.getOutputEscapes();
         int var10 = var9.length;

         label40:
         while(this._outputTail < var2) {
            int var28;
            do {
               char[] var11 = this._outputBuffer;
               int var12 = this._outputTail;
               char var13 = var11[var12];
               if(var13 < var10 && var9[var13] != 0) {
                  int var14 = this._outputTail;
                  int var15 = this._outputHead;
                  int var16 = var14 - var15;
                  if(var16 > 0) {
                     Writer var17 = this._writer;
                     char[] var18 = this._outputBuffer;
                     int var19 = this._outputHead;
                     var17.write(var18, var19, var16);
                  }

                  char[] var20 = this._outputBuffer;
                  int var21 = this._outputTail;
                  char var22 = var20[var21];
                  int var23 = var9[var22];
                  int var24 = this._outputTail + 1;
                  this._outputTail = var24;
                  byte var25;
                  if(var23 < 0) {
                     var25 = 6;
                  } else {
                     var25 = 2;
                  }

                  int var26 = this._outputTail;
                  if(var25 > var26) {
                     int var27 = this._outputTail;
                     this._outputHead = var27;
                     this._writeSingleEscape(var23);
                  } else {
                     int var29 = this._outputTail - var25;
                     this._outputHead = var29;
                     char[] var30 = this._outputBuffer;
                     this._appendSingleEscape(var23, var30, var29);
                  }
                  continue label40;
               }

               var28 = this._outputTail + 1;
               this._outputTail = var28;
            } while(var28 < var2);

            return;
         }

      }
   }

   private void _writeString(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var4 = var3 + var2;
      int[] var5 = CharTypes.getOutputEscapes();
      int var6 = var5.length;

      int var18;
      for(int var7 = var2; var7 < var4; var7 = var18) {
         int var8 = var7;

         do {
            char var9 = var1[var8];
            if(var9 < var6 && var5[var9] != 0) {
               break;
            }

            ++var8;
         } while(var8 < var4);

         int var10 = var8 - var7;
         if(var10 < 32) {
            int var11 = this._outputTail + var10;
            int var12 = this._outputEnd;
            if(var11 > var12) {
               this._flushBuffer();
            }

            if(var10 > 0) {
               char[] var13 = this._outputBuffer;
               int var14 = this._outputTail;
               System.arraycopy(var1, var7, var13, var14, var10);
               int var15 = this._outputTail + var10;
               this._outputTail = var15;
            }
         } else {
            this._flushBuffer();
            this._writer.write(var1, var7, var10);
         }

         if(var8 >= var4) {
            return;
         }

         char var16 = var1[var8];
         int var17 = var5[var16];
         var18 = var8 + 1;
         byte var19;
         if(var17 < 0) {
            var19 = 6;
         } else {
            var19 = 2;
         }

         int var20 = this._outputTail + var19;
         int var21 = this._outputEnd;
         if(var20 > var21) {
            this._flushBuffer();
         }

         char[] var22 = this._outputBuffer;
         int var23 = this._outputTail;
         this._appendSingleEscape(var17, var22, var23);
         int var24 = this._outputTail + var19;
         this._outputTail = var24;
      }

   }

   private void writeRawLong(String var1) throws IOException, JsonGenerationException {
      int var2 = this._outputEnd;
      int var3 = this._outputTail;
      int var4 = var2 - var3;
      char[] var5 = this._outputBuffer;
      int var6 = this._outputTail;
      var1.getChars(0, var4, var5, var6);
      int var7 = this._outputTail + var4;
      this._outputTail = var7;
      this._flushBuffer();
      int var8 = var1.length() - var4;
      int var9 = var4;
      int var10 = var8;

      while(true) {
         int var11 = this._outputEnd;
         if(var10 <= var11) {
            int var15 = var9 + var10;
            char[] var16 = this._outputBuffer;
            var1.getChars(var9, var15, var16, 0);
            this._outputHead = 0;
            this._outputTail = var10;
            return;
         }

         int var12 = this._outputEnd;
         int var13 = var9 + var12;
         char[] var14 = this._outputBuffer;
         var1.getChars(var9, var13, var14, 0);
         this._outputHead = 0;
         this._outputTail = var12;
         this._flushBuffer();
         var9 += var12;
         var10 -= var12;
      }
   }

   protected final void _flushBuffer() throws IOException {
      int var1 = this._outputTail;
      int var2 = this._outputHead;
      int var3 = var1 - var2;
      if(var3 > 0) {
         int var4 = this._outputHead;
         this._outputHead = 0;
         this._outputTail = 0;
         Writer var5 = this._writer;
         char[] var6 = this._outputBuffer;
         var5.write(var6, var4, var3);
      }
   }

   protected void _releaseBuffers() {
      char[] var1 = this._outputBuffer;
      if(var1 != null) {
         this._outputBuffer = null;
         this._ioContext.releaseConcatBuffer(var1);
      }
   }

   protected final void _verifyPrettyValueWrite(String var1, int var2) throws IOException, JsonGenerationException {
      switch(var2) {
      case 0:
         if(this._writeContext.inArray()) {
            this._cfgPrettyPrinter.beforeArrayValues(this);
            return;
         } else {
            if(!this._writeContext.inObject()) {
               return;
            }

            this._cfgPrettyPrinter.beforeObjectEntries(this);
            return;
         }
      case 1:
         this._cfgPrettyPrinter.writeArrayValueSeparator(this);
         return;
      case 2:
         this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
         return;
      case 3:
         this._cfgPrettyPrinter.writeRootValueSeparator(this);
         return;
      default:
         this._cantHappen();
      }
   }

   protected final void _verifyValueWrite(String var1) throws IOException, JsonGenerationException {
      int var2 = this._writeContext.writeValue();
      if(var2 == 5) {
         String var3 = "Can not " + var1 + ", expecting field name";
         this._reportError(var3);
      }

      if(this._cfgPrettyPrinter == null) {
         byte var4;
         switch(var2) {
         case 1:
            var4 = 44;
            break;
         case 2:
            var4 = 58;
            break;
         case 3:
            var4 = 32;
            break;
         default:
            return;
         }

         int var5 = this._outputTail;
         int var6 = this._outputEnd;
         if(var5 >= var6) {
            this._flushBuffer();
         }

         char[] var7 = this._outputBuffer;
         int var8 = this._outputTail;
         var7[var8] = (char)var4;
         int var9 = this._outputTail + 1;
         this._outputTail = var9;
      } else {
         this._verifyPrettyValueWrite(var1, var2);
      }
   }

   protected void _writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      int var5 = var4 - 3;
      int var6 = this._outputEnd - 6;
      int var7 = var1.getMaxLineLength() >> 2;

      int var8;
      int var14;
      for(var8 = var3; var8 <= var5; var8 = var14) {
         if(this._outputTail > var6) {
            this._flushBuffer();
         }

         int var9 = var8 + 1;
         int var10 = var2[var8] << 8;
         int var11 = var9 + 1;
         int var12 = var2[var9] & 255;
         int var13 = (var10 | var12) << 8;
         var14 = var11 + 1;
         int var15 = var2[var11] & 255;
         int var16 = var13 | var15;
         char[] var17 = this._outputBuffer;
         int var18 = this._outputTail;
         int var19 = var1.encodeBase64Chunk(var16, var17, var18);
         this._outputTail = var19;
         var7 += -1;
         if(var7 <= 0) {
            char[] var20 = this._outputBuffer;
            int var21 = this._outputTail;
            int var22 = var21 + 1;
            this._outputTail = var22;
            var20[var21] = 92;
            char[] var23 = this._outputBuffer;
            int var24 = this._outputTail;
            int var25 = var24 + 1;
            this._outputTail = var25;
            var23[var24] = 110;
            int var26 = var1.getMaxLineLength() >> 2;
         }
      }

      int var27 = var4 - var8;
      if(var27 > 0) {
         if(this._outputTail > var6) {
            this._flushBuffer();
         }

         int var28 = var8 + 1;
         int var29 = var2[var8] << 16;
         if(var27 == 2) {
            int var30 = var28 + 1;
            var6 = (var2[var28] & 255) << 8 | var29;
         } else {
            var6 = var29;
         }

         char[] var31 = this._outputBuffer;
         int var32 = this._outputTail;
         int var33 = var1.encodeBase64Partial(var6, var27, var31, var32);
         this._outputTail = var33;
      }
   }

   protected void _writeEndArray() throws IOException, JsonGenerationException {
      int var1 = this._outputTail;
      int var2 = this._outputEnd;
      if(var1 >= var2) {
         this._flushBuffer();
      }

      char[] var3 = this._outputBuffer;
      int var4 = this._outputTail;
      int var5 = var4 + 1;
      this._outputTail = var5;
      var3[var4] = 93;
   }

   protected void _writeEndObject() throws IOException, JsonGenerationException {
      int var1 = this._outputTail;
      int var2 = this._outputEnd;
      if(var1 >= var2) {
         this._flushBuffer();
      }

      char[] var3 = this._outputBuffer;
      int var4 = this._outputTail;
      int var5 = var4 + 1;
      this._outputTail = var5;
      var3[var4] = 125;
   }

   protected void _writeFieldName(String var1, boolean var2) throws IOException, JsonGenerationException {
      if(this._cfgPrettyPrinter != null) {
         this._writePPFieldName(var1, var2);
      } else {
         int var3 = this._outputTail + 1;
         int var4 = this._outputEnd;
         if(var3 >= var4) {
            this._flushBuffer();
         }

         if(var2) {
            char[] var5 = this._outputBuffer;
            int var6 = this._outputTail;
            int var7 = var6 + 1;
            this._outputTail = var7;
            var5[var6] = 44;
         }

         JsonGenerator.Feature var8 = JsonGenerator.Feature.QUOTE_FIELD_NAMES;
         if(!this.isFeatureEnabled(var8)) {
            this._writeString(var1);
         } else {
            char[] var9 = this._outputBuffer;
            int var10 = this._outputTail;
            int var11 = var10 + 1;
            this._outputTail = var11;
            var9[var10] = 34;
            this._writeString(var1);
            int var12 = this._outputTail;
            int var13 = this._outputEnd;
            if(var12 >= var13) {
               this._flushBuffer();
            }

            char[] var14 = this._outputBuffer;
            int var15 = this._outputTail;
            int var16 = var15 + 1;
            this._outputTail = var16;
            var14[var15] = 34;
         }
      }
   }

   protected final void _writePPFieldName(String var1, boolean var2) throws IOException, JsonGenerationException {
      if(var2) {
         this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
      } else {
         this._cfgPrettyPrinter.beforeObjectEntries(this);
      }

      JsonGenerator.Feature var3 = JsonGenerator.Feature.QUOTE_FIELD_NAMES;
      if(this.isFeatureEnabled(var3)) {
         int var4 = this._outputTail;
         int var5 = this._outputEnd;
         if(var4 >= var5) {
            this._flushBuffer();
         }

         char[] var6 = this._outputBuffer;
         int var7 = this._outputTail;
         int var8 = var7 + 1;
         this._outputTail = var8;
         var6[var7] = 34;
         this._writeString(var1);
         int var9 = this._outputTail;
         int var10 = this._outputEnd;
         if(var9 >= var10) {
            this._flushBuffer();
         }

         char[] var11 = this._outputBuffer;
         int var12 = this._outputTail;
         int var13 = var12 + 1;
         this._outputTail = var13;
         var11[var12] = 34;
      } else {
         this._writeString(var1);
      }
   }

   protected void _writeStartArray() throws IOException, JsonGenerationException {
      int var1 = this._outputTail;
      int var2 = this._outputEnd;
      if(var1 >= var2) {
         this._flushBuffer();
      }

      char[] var3 = this._outputBuffer;
      int var4 = this._outputTail;
      int var5 = var4 + 1;
      this._outputTail = var5;
      var3[var4] = 91;
   }

   protected void _writeStartObject() throws IOException, JsonGenerationException {
      int var1 = this._outputTail;
      int var2 = this._outputEnd;
      if(var1 >= var2) {
         this._flushBuffer();
      }

      char[] var3 = this._outputBuffer;
      int var4 = this._outputTail;
      int var5 = var4 + 1;
      this._outputTail = var5;
      var3[var4] = 123;
   }

   public void close() throws IOException {
      super.close();
      if(this._outputBuffer != null) {
         JsonGenerator.Feature var1 = JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT;
         if(this.isFeatureEnabled(var1)) {
            label28:
            while(true) {
               while(true) {
                  JsonWriteContext var2 = this.getOutputContext();
                  if(!var2.inArray()) {
                     if(!var2.inObject()) {
                        break label28;
                     }

                     this.writeEndObject();
                  } else {
                     this.writeEndArray();
                  }
               }
            }
         }
      }

      label18: {
         this._flushBuffer();
         if(!this._ioContext.isResourceManaged()) {
            JsonGenerator.Feature var3 = JsonGenerator.Feature.AUTO_CLOSE_TARGET;
            if(!this.isFeatureEnabled(var3)) {
               this._writer.flush();
               break label18;
            }
         }

         this._writer.close();
      }

      this._releaseBuffers();
   }

   public final void flush() throws IOException {
      this._flushBuffer();
      this._writer.flush();
   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write binary value");
      int var5 = this._outputTail;
      int var6 = this._outputEnd;
      if(var5 >= var6) {
         this._flushBuffer();
      }

      char[] var7 = this._outputBuffer;
      int var8 = this._outputTail;
      int var9 = var8 + 1;
      this._outputTail = var9;
      var7[var8] = 34;
      int var10 = var3 + var4;
      this._writeBinary(var1, var2, var3, var10);
      int var11 = this._outputTail;
      int var12 = this._outputEnd;
      if(var11 >= var12) {
         this._flushBuffer();
      }

      char[] var13 = this._outputBuffer;
      int var14 = this._outputTail;
      int var15 = var14 + 1;
      this._outputTail = var15;
      var13[var14] = 34;
   }

   public void writeBoolean(boolean var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write boolean value");
      int var2 = this._outputTail + 5;
      int var3 = this._outputEnd;
      if(var2 >= var3) {
         this._flushBuffer();
      }

      int var4 = this._outputTail;
      char[] var5 = this._outputBuffer;
      int var8;
      if(var1) {
         var5[var4] = 116;
         int var6 = var4 + 1;
         var5[var6] = 114;
         int var7 = var6 + 1;
         var5[var7] = 117;
         var8 = var7 + 1;
         var5[var8] = 101;
      } else {
         var5[var4] = 102;
         int var10 = var4 + 1;
         var5[var10] = 97;
         int var11 = var10 + 1;
         var5[var11] = 108;
         int var12 = var11 + 1;
         var5[var12] = 115;
         var8 = var12 + 1;
         var5[var8] = 101;
      }

      int var9 = var8 + 1;
      this._outputTail = var9;
   }

   public void writeNull() throws IOException, JsonGenerationException {
      this._verifyValueWrite("write null value");
      int var1 = this._outputTail + 4;
      int var2 = this._outputEnd;
      if(var1 >= var2) {
         this._flushBuffer();
      }

      int var3 = this._outputTail;
      char[] var4 = this._outputBuffer;
      var4[var3] = 110;
      int var5 = var3 + 1;
      var4[var5] = 117;
      int var6 = var5 + 1;
      var4[var6] = 108;
      int var7 = var6 + 1;
      var4[var7] = 108;
      int var8 = var7 + 1;
      this._outputTail = var8;
   }

   public void writeNumber(double var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      String var3 = String.valueOf(var1);
      this.writeRaw(var3);
   }

   public void writeNumber(float var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      String var2 = String.valueOf(var1);
      this.writeRaw(var2);
   }

   public void writeNumber(int var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      int var2 = this._outputTail + 11;
      int var3 = this._outputEnd;
      if(var2 >= var3) {
         this._flushBuffer();
      }

      char[] var4 = this._outputBuffer;
      int var5 = this._outputTail;
      int var6 = NumberOutput.outputInt(var1, var4, var5);
      this._outputTail = var6;
   }

   public void writeNumber(long var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      int var3 = this._outputTail + 21;
      int var4 = this._outputEnd;
      if(var3 >= var4) {
         this._flushBuffer();
      }

      char[] var5 = this._outputBuffer;
      int var6 = this._outputTail;
      int var7 = NumberOutput.outputLong(var1, var5, var6);
      this._outputTail = var7;
   }

   public void writeNumber(String var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      this.writeRaw(var1);
   }

   public void writeNumber(BigDecimal var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      String var2 = var1.toString();
      this.writeRaw(var2);
   }

   public void writeNumber(BigInteger var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write number");
      String var2 = var1.toString();
      this.writeRaw(var2);
   }

   public final void writeObject(Object var1) throws IOException, JsonProcessingException {
      if(this._objectCodec == null) {
         throw new IllegalStateException("No ObjectCodec defined for the generator, can not serialize regular Java objects");
      } else {
         this._objectCodec.writeValue(this, var1);
      }
   }

   public void writeRaw(char var1) throws IOException, JsonGenerationException {
      int var2 = this._outputTail;
      int var3 = this._outputEnd;
      if(var2 >= var3) {
         this._flushBuffer();
      }

      char[] var4 = this._outputBuffer;
      int var5 = this._outputTail;
      int var6 = var5 + 1;
      this._outputTail = var6;
      var4[var5] = var1;
   }

   public void writeRaw(String var1) throws IOException, JsonGenerationException {
      int var2 = var1.length();
      int var3 = this._outputEnd;
      int var4 = this._outputTail;
      int var5 = var3 - var4;
      if(var5 == 0) {
         this._flushBuffer();
         int var6 = this._outputEnd;
         int var7 = this._outputTail;
         int var10000 = var6 - var7;
      }

      if(var5 >= var2) {
         char[] var9 = this._outputBuffer;
         int var10 = this._outputTail;
         var1.getChars(0, var2, var9, var10);
         int var11 = this._outputTail;
         int var12 = var2 + var11;
         this._outputTail = var12;
      } else {
         this.writeRawLong(var1);
      }
   }

   public void writeRaw(String var1, int var2, int var3) throws IOException, JsonGenerationException {
      int var4 = this._outputEnd;
      int var5 = this._outputTail;
      int var6 = var4 - var5;
      if(var6 < var3) {
         this._flushBuffer();
         int var7 = this._outputEnd;
         int var8 = this._outputTail;
         var6 = var7 - var8;
      }

      if(var6 >= var3) {
         int var9 = var2 + var3;
         char[] var10 = this._outputBuffer;
         int var11 = this._outputTail;
         var1.getChars(var2, var9, var10, var11);
         int var12 = this._outputTail + var3;
         this._outputTail = var12;
      } else {
         int var13 = var2 + var3;
         String var14 = var1.substring(var2, var13);
         this.writeRawLong(var14);
      }
   }

   public void writeRaw(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      if(var3 < 32) {
         int var4 = this._outputEnd;
         int var5 = this._outputTail;
         int var6 = var4 - var5;
         if(var3 > var6) {
            this._flushBuffer();
         }

         char[] var7 = this._outputBuffer;
         int var8 = this._outputTail;
         System.arraycopy(var1, var2, var7, var8, var3);
         int var9 = this._outputTail + var3;
         this._outputTail = var9;
      } else {
         this._flushBuffer();
         this._writer.write(var1, var2, var3);
      }
   }

   public void writeRawValue(String var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write raw value");
      this.writeRaw(var1);
   }

   public void writeRawValue(String var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write raw value");
      this.writeRaw(var1, var2, var3);
   }

   public void writeRawValue(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write raw value");
      this.writeRaw(var1, var2, var3);
   }

   public void writeString(String var1) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write text value");
      int var2 = this._outputTail;
      int var3 = this._outputEnd;
      if(var2 >= var3) {
         this._flushBuffer();
      }

      char[] var4 = this._outputBuffer;
      int var5 = this._outputTail;
      int var6 = var5 + 1;
      this._outputTail = var6;
      var4[var5] = 34;
      this._writeString(var1);
      int var7 = this._outputTail;
      int var8 = this._outputEnd;
      if(var7 >= var8) {
         this._flushBuffer();
      }

      char[] var9 = this._outputBuffer;
      int var10 = this._outputTail;
      int var11 = var10 + 1;
      this._outputTail = var11;
      var9[var10] = 34;
   }

   public void writeString(char[] var1, int var2, int var3) throws IOException, JsonGenerationException {
      this._verifyValueWrite("write text value");
      int var4 = this._outputTail;
      int var5 = this._outputEnd;
      if(var4 >= var5) {
         this._flushBuffer();
      }

      char[] var6 = this._outputBuffer;
      int var7 = this._outputTail;
      int var8 = var7 + 1;
      this._outputTail = var8;
      var6[var7] = 34;
      this._writeString(var1, var2, var3);
      int var9 = this._outputTail;
      int var10 = this._outputEnd;
      if(var9 >= var10) {
         this._flushBuffer();
      }

      char[] var11 = this._outputBuffer;
      int var12 = this._outputTail;
      int var13 = var12 + 1;
      this._outputTail = var13;
      var11[var12] = 34;
   }

   public final void writeTree(JsonNode var1) throws IOException, JsonProcessingException {
      if(this._objectCodec == null) {
         throw new IllegalStateException("No ObjectCodec defined for the generator, can not serialize JsonNode-based trees");
      } else {
         this._objectCodec.writeTree(this, var1);
      }
   }
}
