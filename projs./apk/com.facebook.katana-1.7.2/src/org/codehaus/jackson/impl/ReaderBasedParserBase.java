package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.Reader;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.impl.JsonNumericParserBase;
import org.codehaus.jackson.io.IOContext;

public abstract class ReaderBasedParserBase extends JsonNumericParserBase {

   protected char[] _inputBuffer;
   protected Reader _reader;


   protected ReaderBasedParserBase(IOContext var1, int var2, Reader var3) {
      super(var1, var2);
      this._reader = var3;
      char[] var4 = var1.allocTokenBuffer();
      this._inputBuffer = var4;
   }

   protected void _closeInput() throws IOException {
      if(this._reader != null) {
         label14: {
            if(!this._ioContext.isResourceManaged()) {
               JsonParser.Feature var1 = JsonParser.Feature.AUTO_CLOSE_SOURCE;
               if(!this.isFeatureEnabled(var1)) {
                  break label14;
               }
            }

            this._reader.close();
         }

         this._reader = null;
      }
   }

   protected void _releaseBuffers() throws IOException {
      super._releaseBuffers();
      char[] var1 = this._inputBuffer;
      if(var1 != null) {
         this._inputBuffer = null;
         this._ioContext.releaseTokenBuffer(var1);
      }
   }

   protected char getNextChar(String var1) throws IOException, JsonParseException {
      int var2 = this._inputPtr;
      int var3 = this._inputEnd;
      if(var2 >= var3 && !this.loadMore()) {
         this._reportInvalidEOF(var1);
      }

      char[] var4 = this._inputBuffer;
      int var5 = this._inputPtr;
      int var6 = var5 + 1;
      this._inputPtr = var6;
      return var4[var5];
   }

   protected final boolean loadMore() throws IOException {
      long var1 = this._currInputProcessed;
      long var3 = (long)this._inputEnd;
      long var5 = var1 + var3;
      this._currInputProcessed = var5;
      int var7 = this._currInputRowStart;
      int var8 = this._inputEnd;
      int var9 = var7 - var8;
      this._currInputRowStart = var9;
      boolean var14;
      if(this._reader != null) {
         Reader var10 = this._reader;
         char[] var11 = this._inputBuffer;
         int var12 = this._inputBuffer.length;
         int var13 = var10.read(var11, 0, var12);
         if(var13 > 0) {
            this._inputPtr = 0;
            this._inputEnd = var13;
            var14 = true;
            return var14;
         }

         this._closeInput();
         if(var13 == 0) {
            StringBuilder var15 = (new StringBuilder()).append("Reader returned 0 characters when trying to read ");
            int var16 = this._inputEnd;
            String var17 = var15.append(var16).toString();
            throw new IOException(var17);
         }
      }

      var14 = false;
      return var14;
   }
}
