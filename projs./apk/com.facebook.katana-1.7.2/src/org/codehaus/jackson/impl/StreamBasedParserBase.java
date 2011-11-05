package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.impl.JsonNumericParserBase;
import org.codehaus.jackson.io.IOContext;

public abstract class StreamBasedParserBase extends JsonNumericParserBase {

   protected boolean _bufferRecyclable;
   protected byte[] _inputBuffer;
   protected InputStream _inputStream;


   protected StreamBasedParserBase(IOContext var1, int var2, InputStream var3, byte[] var4, int var5, int var6, boolean var7) {
      super(var1, var2);
      this._inputStream = var3;
      this._inputBuffer = var4;
      this._inputPtr = var5;
      this._inputEnd = var6;
      this._bufferRecyclable = var7;
   }

   protected void _closeInput() throws IOException {
      if(this._inputStream != null) {
         label14: {
            if(!this._ioContext.isResourceManaged()) {
               JsonParser.Feature var1 = JsonParser.Feature.AUTO_CLOSE_SOURCE;
               if(!this.isFeatureEnabled(var1)) {
                  break label14;
               }
            }

            this._inputStream.close();
         }

         this._inputStream = null;
      }
   }

   protected void _releaseBuffers() throws IOException {
      super._releaseBuffers();
      if(this._bufferRecyclable) {
         byte[] var1 = this._inputBuffer;
         if(var1 != null) {
            this._inputBuffer = null;
            this._ioContext.releaseReadIOBuffer(var1);
         }
      }
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
      if(this._inputStream != null) {
         InputStream var10 = this._inputStream;
         byte[] var11 = this._inputBuffer;
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
