package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.output.ProxyOutputStream;

public class CountingOutputStream extends ProxyOutputStream {

   private long count;


   public CountingOutputStream(OutputStream var1) {
      super(var1);
   }

   public long getByteCount() {
      synchronized(this){}

      long var1;
      try {
         var1 = this.count;
      } finally {
         ;
      }

      return var1;
   }

   public int getCount() {
      synchronized(this){}
      boolean var7 = false;

      long var1;
      try {
         var7 = true;
         var1 = this.getByteCount();
         if(var1 > 2147483647L) {
            String var3 = "The byte count " + var1 + " is too large to be converted to an int";
            throw new ArithmeticException(var3);
         }

         var7 = false;
      } finally {
         if(var7) {
            ;
         }
      }

      int var5 = (int)var1;
      return var5;
   }

   public long resetByteCount() {
      synchronized(this){}

      long var1;
      try {
         var1 = this.count;
         this.count = 0L;
      } finally {
         ;
      }

      return var1;
   }

   public int resetCount() {
      synchronized(this){}
      boolean var7 = false;

      long var1;
      try {
         var7 = true;
         var1 = this.resetByteCount();
         if(var1 > 2147483647L) {
            String var3 = "The byte count " + var1 + " is too large to be converted to an int";
            throw new ArithmeticException(var3);
         }

         var7 = false;
      } finally {
         if(var7) {
            ;
         }
      }

      int var5 = (int)var1;
      return var5;
   }

   public void write(int var1) throws IOException {
      long var2 = this.count + 1L;
      this.count = var2;
      super.write(var1);
   }

   public void write(byte[] var1) throws IOException {
      long var2 = this.count;
      long var4 = (long)var1.length;
      long var6 = var2 + var4;
      this.count = var6;
      super.write(var1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      long var4 = this.count;
      long var6 = (long)var3;
      long var8 = var4 + var6;
      this.count = var8;
      super.write(var1, var2, var3);
   }
}
