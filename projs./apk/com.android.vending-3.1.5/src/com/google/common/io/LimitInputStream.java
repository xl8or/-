package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LimitInputStream extends FilterInputStream {

   private long left;
   private long mark = 65535L;


   public LimitInputStream(InputStream var1, long var2) {
      super(var1);
      Object var4 = Preconditions.checkNotNull(var1);
      byte var5;
      if(var2 >= 0L) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      Preconditions.checkArgument((boolean)var5, "limit must be non-negative");
      this.left = var2;
   }

   public int available() throws IOException {
      long var1 = (long)this.in.available();
      long var3 = this.left;
      return (int)Math.min(var1, var3);
   }

   public void mark(int var1) {
      this.in.mark(var1);
      long var2 = this.left;
      this.mark = var2;
   }

   public int read() throws IOException {
      int var1;
      if(this.left == 0L) {
         var1 = -1;
      } else {
         var1 = this.in.read();
         if(var1 != -1) {
            long var2 = this.left - 1L;
            this.left = var2;
         }
      }

      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this.left == 0L) {
         var4 = -1;
      } else {
         long var5 = (long)var3;
         long var7 = this.left;
         int var9 = (int)Math.min(var5, var7);
         var4 = this.in.read(var1, var2, var9);
         if(var4 != -1) {
            long var10 = this.left;
            long var12 = (long)var4;
            long var14 = var10 - var12;
            this.left = var14;
         }
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
         this.left = var1;
      }
   }

   public long skip(long var1) throws IOException {
      long var3 = this.left;
      long var5 = Math.min(var1, var3);
      long var7 = this.in.skip(var5);
      long var9 = this.left - var7;
      this.left = var9;
      return var7;
   }
}
