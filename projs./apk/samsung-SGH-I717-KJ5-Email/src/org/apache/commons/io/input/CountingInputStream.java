package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.input.ProxyInputStream;

public class CountingInputStream extends ProxyInputStream {

   private long count;


   public CountingInputStream(InputStream var1) {
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

   public int read() throws IOException {
      int var1 = super.read();
      long var2 = this.count;
      long var4;
      if(var1 >= 0) {
         var4 = 1L;
      } else {
         var4 = 0L;
      }

      long var6 = var2 + var4;
      this.count = var6;
      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = super.read(var1);
      long var3 = this.count;
      long var5;
      if(var2 >= 0) {
         var5 = (long)var2;
      } else {
         var5 = 0L;
      }

      long var7 = var3 + var5;
      this.count = var7;
      return var2;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = super.read(var1, var2, var3);
      long var5 = this.count;
      long var7;
      if(var4 >= 0) {
         var7 = (long)var4;
      } else {
         var7 = 0L;
      }

      long var9 = var5 + var7;
      this.count = var9;
      return var4;
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

   public long skip(long var1) throws IOException {
      long var3 = super.skip(var1);
      long var5 = this.count + var3;
      this.count = var5;
      return var3;
   }
}
