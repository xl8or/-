package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.ChunkedInputStream;

public class ContentLengthInputStream extends InputStream {

   private boolean closed;
   private long contentLength;
   private long pos;
   private InputStream wrappedStream;


   public ContentLengthInputStream(InputStream var1, int var2) {
      long var3 = (long)var2;
      this(var1, var3);
   }

   public ContentLengthInputStream(InputStream var1, long var2) {
      this.pos = 0L;
      this.closed = (boolean)0;
      this.wrappedStream = null;
      this.wrappedStream = var1;
      this.contentLength = var2;
   }

   public int available() throws IOException {
      int var1;
      if(this.closed) {
         var1 = 0;
      } else {
         int var2 = this.wrappedStream.available();
         long var3 = this.pos;
         long var5 = (long)var2;
         long var7 = var3 + var5;
         long var9 = this.contentLength;
         if(var7 > var9) {
            long var11 = this.contentLength;
            long var13 = this.pos;
            var2 = (int)(var11 - var13);
         }

         var1 = var2;
      }

      return var1;
   }

   public void close() throws IOException {
      if(!this.closed) {
         try {
            ChunkedInputStream.exhaustInputStream(this);
         } finally {
            this.closed = (boolean)1;
         }

      }
   }

   public int read() throws IOException {
      if(this.closed) {
         throw new IOException("Attempted read from closed stream.");
      } else {
         long var1 = this.pos;
         long var3 = this.contentLength;
         int var5;
         if(var1 >= var3) {
            var5 = -1;
         } else {
            long var6 = this.pos + 1L;
            this.pos = var6;
            var5 = this.wrappedStream.read();
         }

         return var5;
      }
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if(this.closed) {
         throw new IOException("Attempted read from closed stream.");
      } else {
         long var4 = this.pos;
         long var6 = this.contentLength;
         int var8;
         if(var4 >= var6) {
            var8 = -1;
         } else {
            long var9 = this.pos;
            long var11 = (long)var3;
            long var13 = var9 + var11;
            long var15 = this.contentLength;
            if(var13 > var15) {
               long var17 = this.contentLength;
               long var19 = this.pos;
               var3 = (int)(var17 - var19);
            }

            int var21 = this.wrappedStream.read(var1, var2, var3);
            long var22 = this.pos;
            long var24 = (long)var21;
            long var26 = var22 + var24;
            this.pos = var26;
            var8 = var21;
         }

         return var8;
      }
   }

   public long skip(long var1) throws IOException {
      long var3 = this.contentLength;
      long var5 = this.pos;
      long var7 = var3 - var5;
      long var9 = Math.min(var1, var7);
      long var11 = this.wrappedStream.skip(var9);
      if(var11 > 0L) {
         long var13 = this.pos + var11;
         this.pos = var13;
      }

      return var11;
   }
}
