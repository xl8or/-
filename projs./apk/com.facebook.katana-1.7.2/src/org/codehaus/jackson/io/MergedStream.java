package org.codehaus.jackson.io;

import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.io.IOContext;

public final class MergedStream extends InputStream {

   byte[] _buffer;
   protected final IOContext _context;
   final int _end;
   final InputStream _in;
   int _ptr;


   public MergedStream(IOContext var1, InputStream var2, byte[] var3, int var4, int var5) {
      this._context = var1;
      this._in = var2;
      this._buffer = var3;
      this._ptr = var4;
      this._end = var5;
   }

   private void freeMergedBuffer() {
      byte[] var1 = this._buffer;
      if(var1 != null) {
         this._buffer = null;
         this._context.releaseReadIOBuffer(var1);
      }
   }

   public int available() throws IOException {
      int var3;
      if(this._buffer != null) {
         int var1 = this._end;
         int var2 = this._ptr;
         var3 = var1 - var2;
      } else {
         var3 = this._in.available();
      }

      return var3;
   }

   public void close() throws IOException {
      this.freeMergedBuffer();
      this._in.close();
   }

   public void mark(int var1) {
      if(this._buffer == null) {
         this._in.mark(var1);
      }
   }

   public boolean markSupported() {
      boolean var1;
      if(this._buffer == null && this._in.markSupported()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int read() throws IOException {
      int var4;
      if(this._buffer != null) {
         byte[] var1 = this._buffer;
         int var2 = this._ptr;
         int var3 = var2 + 1;
         this._ptr = var3;
         var4 = var1[var2] & 255;
         int var5 = this._ptr;
         int var6 = this._end;
         if(var5 >= var6) {
            this.freeMergedBuffer();
         }
      } else {
         var4 = this._in.read();
      }

      return var4;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var6;
      if(this._buffer != null) {
         int var4 = this._end;
         int var5 = this._ptr;
         var6 = var4 - var5;
         if(var3 <= var6) {
            var6 = var3;
         }

         byte[] var7 = this._buffer;
         int var8 = this._ptr;
         System.arraycopy(var7, var8, var1, var2, var6);
         int var9 = this._ptr + var6;
         this._ptr = var9;
         int var10 = this._ptr;
         int var11 = this._end;
         if(var10 >= var11) {
            this.freeMergedBuffer();
         }
      } else {
         var6 = this._in.read(var1, var2, var3);
      }

      return var6;
   }

   public void reset() throws IOException {
      if(this._buffer == null) {
         this._in.reset();
      }
   }

   public long skip(long var1) throws IOException {
      long var9;
      long var17;
      if(this._buffer != null) {
         int var3 = this._end;
         int var4 = this._ptr;
         int var5 = var3 - var4;
         if((long)var5 > var1) {
            int var6 = this._ptr;
            int var7 = (int)var1;
            int var8 = var6 + var7;
            this._ptr = var8;
            var9 = (long)var5;
            return var9;
         }

         this.freeMergedBuffer();
         long var11 = (long)var5 + 0L;
         long var13 = (long)var5;
         long var15 = var1 - var13;
         var9 = var11;
         var17 = var15;
      } else {
         var9 = 0L;
         var17 = var1;
      }

      if(var17 > 0L) {
         long var19 = this._in.skip(var17);
         var9 += var19;
      }

      return var9;
   }
}
