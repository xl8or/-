package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.httpclient.util.EncodingUtil;

public class ChunkedOutputStream extends OutputStream {

   private static final byte[] CRLF = new byte[]{(byte)13, (byte)10};
   private static final byte[] ENDCHUNK = CRLF;
   private static final byte[] ZERO;
   private byte[] cache;
   private int cachePosition;
   private OutputStream stream;
   private boolean wroteLastChunk;


   static {
      byte[] var0 = new byte[]{(byte)48};
      ZERO = var0;
   }

   public ChunkedOutputStream(OutputStream var1) throws IOException {
      this(var1, 2048);
   }

   public ChunkedOutputStream(OutputStream var1, int var2) throws IOException {
      this.stream = null;
      this.cachePosition = 0;
      this.wroteLastChunk = (boolean)0;
      byte[] var3 = new byte[var2];
      this.cache = var3;
      this.stream = var1;
   }

   public void close() throws IOException {
      this.finish();
      super.close();
   }

   public void finish() throws IOException {
      if(!this.wroteLastChunk) {
         this.flushCache();
         this.writeClosingChunk();
         this.wroteLastChunk = (boolean)1;
      }
   }

   public void flush() throws IOException {
      this.stream.flush();
   }

   protected void flushCache() throws IOException {
      if(this.cachePosition > 0) {
         StringBuilder var1 = new StringBuilder();
         String var2 = Integer.toHexString(this.cachePosition);
         byte[] var3 = EncodingUtil.getAsciiBytes(var1.append(var2).append("\r\n").toString());
         OutputStream var4 = this.stream;
         int var5 = var3.length;
         var4.write(var3, 0, var5);
         OutputStream var6 = this.stream;
         byte[] var7 = this.cache;
         int var8 = this.cachePosition;
         var6.write(var7, 0, var8);
         OutputStream var9 = this.stream;
         byte[] var10 = ENDCHUNK;
         int var11 = ENDCHUNK.length;
         var9.write(var10, 0, var11);
         this.cachePosition = 0;
      }
   }

   protected void flushCacheWithAppend(byte[] var1, int var2, int var3) throws IOException {
      StringBuilder var4 = new StringBuilder();
      String var5 = Integer.toHexString(this.cachePosition + var3);
      byte[] var6 = EncodingUtil.getAsciiBytes(var4.append(var5).append("\r\n").toString());
      OutputStream var7 = this.stream;
      int var8 = var6.length;
      var7.write(var6, 0, var8);
      OutputStream var9 = this.stream;
      byte[] var10 = this.cache;
      int var11 = this.cachePosition;
      var9.write(var10, 0, var11);
      this.stream.write(var1, var2, var3);
      OutputStream var12 = this.stream;
      byte[] var13 = ENDCHUNK;
      int var14 = ENDCHUNK.length;
      var12.write(var13, 0, var14);
      this.cachePosition = 0;
   }

   public void write(int var1) throws IOException {
      byte[] var2 = this.cache;
      int var3 = this.cachePosition;
      byte var4 = (byte)var1;
      var2[var3] = var4;
      int var5 = this.cachePosition + 1;
      this.cachePosition = var5;
      int var6 = this.cachePosition;
      int var7 = this.cache.length;
      if(var6 == var7) {
         this.flushCache();
      }
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.cache.length;
      int var5 = this.cachePosition;
      int var6 = var4 - var5;
      if(var3 >= var6) {
         this.flushCacheWithAppend(var1, var2, var3);
      } else {
         byte[] var7 = this.cache;
         int var8 = this.cachePosition;
         System.arraycopy(var1, var2, var7, var8, var3);
         int var9 = this.cachePosition + var3;
         this.cachePosition = var9;
      }
   }

   protected void writeClosingChunk() throws IOException {
      OutputStream var1 = this.stream;
      byte[] var2 = ZERO;
      int var3 = ZERO.length;
      var1.write(var2, 0, var3);
      OutputStream var4 = this.stream;
      byte[] var5 = CRLF;
      int var6 = CRLF.length;
      var4.write(var5, 0, var6);
      OutputStream var7 = this.stream;
      byte[] var8 = ENDCHUNK;
      int var9 = ENDCHUNK.length;
      var7.write(var8, 0, var9);
   }
}
