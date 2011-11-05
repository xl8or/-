package com.google.common.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends FilterOutputStream {

   private long count;


   public CountingOutputStream(OutputStream var1) {
      super(var1);
   }

   public long getCount() {
      return this.count;
   }

   public void write(int var1) throws IOException {
      this.out.write(var1);
      long var2 = this.count + 1L;
      this.count = var2;
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
      long var4 = this.count;
      long var6 = (long)var3;
      long var8 = var4 + var6;
      this.count = var8;
   }
}
