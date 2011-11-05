package com.google.common.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CountingInputStream extends FilterInputStream {

   private long count;
   private long mark = 65535L;


   public CountingInputStream(InputStream var1) {
      super(var1);
   }

   public long getCount() {
      return this.count;
   }

   public void mark(int var1) {
      this.in.mark(var1);
      long var2 = this.count;
      this.mark = var2;
   }

   public int read() throws IOException {
      int var1 = this.in.read();
      if(var1 != -1) {
         long var2 = this.count + 1L;
         this.count = var2;
      }

      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.in.read(var1, var2, var3);
      if(var4 != -1) {
         long var5 = this.count;
         long var7 = (long)var4;
         long var9 = var5 + var7;
         this.count = var9;
      }

      return var4;
   }

   public void reset() throws IOException {
      if(!this.in.markSupported()) {
         throw new IOException("Mark not supported");
      } else if(this.mark == 65535L) {
         throw new IOException("Mark not set");
      } else {
         this.in.reset();
         long var1 = this.mark;
         this.count = var1;
      }
   }

   public long skip(long var1) throws IOException {
      long var3 = this.in.skip(var1);
      long var5 = this.count + var3;
      this.count = var5;
      return var3;
   }
}
