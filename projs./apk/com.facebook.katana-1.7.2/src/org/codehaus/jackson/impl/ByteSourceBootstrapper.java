package org.codehaus.jackson.impl;

import java.io.ByteArrayInputStream;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.impl.ReaderBasedParser;
import org.codehaus.jackson.impl.Utf8StreamParser;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.io.MergedStream;
import org.codehaus.jackson.io.UTF32Reader;
import org.codehaus.jackson.sym.BytesToNameCanonicalizer;
import org.codehaus.jackson.sym.CharsToNameCanonicalizer;

public final class ByteSourceBootstrapper {

   boolean _bigEndian = 1;
   private final boolean _bufferRecyclable;
   int _bytesPerChar = 0;
   final IOContext _context;
   final InputStream _in;
   final byte[] _inputBuffer;
   private int _inputEnd;
   protected int _inputProcessed;
   private int _inputPtr;


   public ByteSourceBootstrapper(IOContext var1, InputStream var2) {
      this._context = var1;
      this._in = var2;
      byte[] var3 = var1.allocReadIOBuffer();
      this._inputBuffer = var3;
      this._inputPtr = 0;
      this._inputEnd = 0;
      this._inputProcessed = 0;
      this._bufferRecyclable = (boolean)1;
   }

   public ByteSourceBootstrapper(IOContext var1, byte[] var2, int var3, int var4) {
      this._context = var1;
      this._in = null;
      this._inputBuffer = var2;
      this._inputPtr = var3;
      int var5 = var3 + var4;
      this._inputEnd = var5;
      int var6 = -var3;
      this._inputProcessed = var6;
      this._bufferRecyclable = (boolean)0;
   }

   private boolean checkUTF16(int var1) {
      boolean var2;
      if(('\uff00' & var1) == 0) {
         this._bigEndian = (boolean)1;
      } else {
         if((var1 & 255) != 0) {
            var2 = false;
            return var2;
         }

         this._bigEndian = (boolean)0;
      }

      this._bytesPerChar = 2;
      var2 = true;
      return var2;
   }

   private boolean checkUTF32(int var1) throws IOException {
      boolean var2;
      if(var1 >> 8 == 0) {
         this._bigEndian = (boolean)1;
      } else if((16777215 & var1) == 0) {
         this._bigEndian = (boolean)0;
      } else if((-16711681 & var1) == 0) {
         this.reportWeirdUCS4("3412");
      } else {
         if((-65281 & var1) != 0) {
            var2 = false;
            return var2;
         }

         this.reportWeirdUCS4("2143");
      }

      this._bytesPerChar = 4;
      var2 = true;
      return var2;
   }

   private boolean handleBOM(int var1) throws IOException {
      boolean var4;
      switch(var1) {
      case -131072:
         int var6 = this._inputPtr + 4;
         this._inputPtr = var6;
         this._bytesPerChar = 4;
         this._bigEndian = (boolean)0;
         var4 = true;
         break;
      case '\ufeff':
         this._bigEndian = (boolean)1;
         int var5 = this._inputPtr + 4;
         this._inputPtr = var5;
         this._bytesPerChar = 4;
         var4 = true;
         break;
      case '\ufffe':
         this.reportWeirdUCS4("2143");
      case -16842752:
         this.reportWeirdUCS4("3412");
      default:
         int var2 = var1 >>> 16;
         if(var2 == '\ufeff') {
            int var3 = this._inputPtr + 2;
            this._inputPtr = var3;
            this._bytesPerChar = 2;
            this._bigEndian = (boolean)1;
            var4 = true;
         } else if(var2 == '\ufffe') {
            int var7 = this._inputPtr + 2;
            this._inputPtr = var7;
            this._bytesPerChar = 2;
            this._bigEndian = (boolean)0;
            var4 = true;
         } else if(var1 >>> 8 == 15711167) {
            int var8 = this._inputPtr + 3;
            this._inputPtr = var8;
            this._bytesPerChar = 1;
            this._bigEndian = (boolean)1;
            var4 = true;
         } else {
            var4 = false;
         }
      }

      return var4;
   }

   private void reportWeirdUCS4(String var1) throws IOException {
      String var2 = "Unsupported UCS-4 endianness (" + var1 + ") detected";
      throw new CharConversionException(var2);
   }

   public JsonParser constructParser(int var1, ObjectCodec var2, BytesToNameCanonicalizer var3, CharsToNameCanonicalizer var4) throws IOException, JsonParseException {
      JsonEncoding var5 = this.detectEncoding();
      JsonEncoding var6 = JsonEncoding.UTF8;
      Object var16;
      if(var5 == var6) {
         IOContext var7 = this._context;
         InputStream var8 = this._in;
         BytesToNameCanonicalizer var9 = var3.makeChild();
         byte[] var10 = this._inputBuffer;
         int var11 = this._inputPtr;
         int var12 = this._inputEnd;
         boolean var13 = this._bufferRecyclable;
         var16 = new Utf8StreamParser(var7, var1, var8, var2, var9, var10, var11, var12, var13);
      } else {
         IOContext var17 = this._context;
         Reader var18 = this.constructReader();
         CharsToNameCanonicalizer var19 = var4.makeChild();
         var16 = new ReaderBasedParser(var17, var1, var18, var2, var19);
      }

      return (JsonParser)var16;
   }

   public Reader constructReader() throws IOException {
      JsonEncoding var1 = this._context.getEncoding();
      int[] var2 = ByteSourceBootstrapper.1.$SwitchMap$org$codehaus$jackson$JsonEncoding;
      int var3 = var1.ordinal();
      Object var10;
      switch(var2[var3]) {
      case 1:
      case 2:
         IOContext var4 = this._context;
         InputStream var5 = this._in;
         byte[] var6 = this._inputBuffer;
         int var7 = this._inputPtr;
         int var8 = this._inputEnd;
         boolean var9 = this._context.getEncoding().isBigEndian();
         var10 = new UTF32Reader(var4, var5, var6, var7, var8, var9);
         break;
      case 3:
      case 4:
         InputStream var11 = this._in;
         Object var15;
         if(var11 == null) {
            byte[] var12 = this._inputBuffer;
            int var13 = this._inputPtr;
            int var14 = this._inputEnd;
            var15 = new ByteArrayInputStream(var12, var13, var14);
         } else {
            int var17 = this._inputPtr;
            int var18 = this._inputEnd;
            if(var17 < var18) {
               IOContext var19 = this._context;
               byte[] var20 = this._inputBuffer;
               int var21 = this._inputPtr;
               int var22 = this._inputEnd;
               var15 = new MergedStream(var19, var11, var20, var21, var22);
            } else {
               var15 = var11;
            }
         }

         String var16 = var1.getJavaName();
         var10 = new InputStreamReader((InputStream)var15, var16);
         break;
      case 5:
         throw new RuntimeException("Internal error: should be using Utf8StreamParser directly");
      default:
         throw new RuntimeException("Internal error");
      }

      return (Reader)var10;
   }

   public JsonEncoding detectEncoding() throws IOException, JsonParseException {
      boolean var1 = false;
      if(this.ensureLoaded(4)) {
         byte[] var2 = this._inputBuffer;
         int var3 = this._inputPtr;
         int var4 = var2[var3] << 24;
         byte[] var5 = this._inputBuffer;
         int var6 = this._inputPtr + 1;
         int var7 = (var5[var6] & 255) << 16;
         int var8 = var4 | var7;
         byte[] var9 = this._inputBuffer;
         int var10 = this._inputPtr + 2;
         int var11 = (var9[var10] & 255) << 8;
         int var12 = var8 | var11;
         byte[] var13 = this._inputBuffer;
         int var14 = this._inputPtr + 3;
         int var15 = var13[var14] & 255;
         int var16 = var12 | var15;
         if(this.handleBOM(var16)) {
            var1 = true;
         } else if(this.checkUTF32(var16)) {
            var1 = true;
         } else {
            int var18 = var16 >>> 16;
            if(this.checkUTF16(var18)) {
               var1 = true;
            }
         }
      } else if(this.ensureLoaded(2)) {
         byte[] var19 = this._inputBuffer;
         int var20 = this._inputPtr;
         int var21 = (var19[var20] & 255) << 8;
         byte[] var22 = this._inputBuffer;
         int var23 = this._inputPtr + 1;
         int var24 = var22[var23] & 255;
         int var25 = var21 | var24;
         if(this.checkUTF16(var25)) {
            var1 = true;
         }
      }

      JsonEncoding var17;
      if(!var1) {
         var17 = JsonEncoding.UTF8;
      } else if(this._bytesPerChar == 2) {
         if(this._bigEndian) {
            var17 = JsonEncoding.UTF16_BE;
         } else {
            var17 = JsonEncoding.UTF16_LE;
         }
      } else {
         if(this._bytesPerChar != 4) {
            throw new RuntimeException("Internal error");
         }

         if(this._bigEndian) {
            var17 = JsonEncoding.UTF32_BE;
         } else {
            var17 = JsonEncoding.UTF32_LE;
         }
      }

      this._context.setEncoding(var17);
      return var17;
   }

   protected boolean ensureLoaded(int var1) throws IOException {
      int var2 = this._inputEnd;
      int var3 = this._inputPtr;
      int var4 = var2 - var3;

      boolean var5;
      while(true) {
         if(var4 >= var1) {
            var5 = true;
            break;
         }

         if(this._in == null) {
            var3 = -1;
         } else {
            InputStream var6 = this._in;
            byte[] var7 = this._inputBuffer;
            int var8 = this._inputEnd;
            int var9 = this._inputBuffer.length;
            int var10 = this._inputEnd;
            int var11 = var9 - var10;
            var6.read(var7, var8, var11);
         }

         if(var3 < 1) {
            var5 = false;
            break;
         }

         int var13 = this._inputEnd + var3;
         this._inputEnd = var13;
         var4 += var3;
      }

      return var5;
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$org$codehaus$jackson$JsonEncoding = new int[JsonEncoding.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$org$codehaus$jackson$JsonEncoding;
            int var1 = JsonEncoding.UTF32_BE.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var19) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$org$codehaus$jackson$JsonEncoding;
            int var3 = JsonEncoding.UTF32_LE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var18) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$org$codehaus$jackson$JsonEncoding;
            int var5 = JsonEncoding.UTF16_BE.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var17) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$org$codehaus$jackson$JsonEncoding;
            int var7 = JsonEncoding.UTF16_LE.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var16) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$org$codehaus$jackson$JsonEncoding;
            int var9 = JsonEncoding.UTF8.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var15) {
            ;
         }
      }
   }
}
