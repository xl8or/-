package com.google.common.io;

import com.google.common.io.InputSupplier;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

final class MultiInputStream extends InputStream {

   private InputStream in;
   private Iterator<? extends InputSupplier<? extends InputStream>> it;


   public MultiInputStream(Iterator<? extends InputSupplier<? extends InputStream>> var1) throws IOException {
      this.it = var1;
      this.advance();
   }

   private void advance() throws IOException {
      this.close();
      if(this.it.hasNext()) {
         InputStream var1 = (InputStream)((InputSupplier)this.it.next()).getInput();
         this.in = var1;
      }
   }

   public int available() throws IOException {
      int var1;
      if(this.in == null) {
         var1 = 0;
      } else {
         var1 = this.in.available();
      }

      return var1;
   }

   public void close() throws IOException {
      if(this.in != null) {
         try {
            this.in.close();
         } finally {
            this.in = null;
         }

      }
   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      int var1;
      if(this.in == null) {
         var1 = -1;
      } else {
         var1 = this.in.read();
         if(var1 == -1) {
            this.advance();
            var1 = this.read();
         }
      }

      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this.in == null) {
         var4 = -1;
      } else {
         var4 = this.in.read(var1, var2, var3);
         if(var4 == -1) {
            this.advance();
            var4 = this.read(var1, var2, var3);
         }
      }

      return var4;
   }

   public long skip(long var1) throws IOException {
      long var3;
      if(this.in != null && var1 > 0L) {
         var3 = this.in.skip(var1);
         if(var3 == 0L) {
            if(this.read() == -1) {
               var3 = 0L;
            } else {
               InputStream var5 = this.in;
               long var6 = var1 - 1L;
               long var8 = var5.skip(var6);
               var3 = 1L + var8;
            }
         }
      } else {
         var3 = 0L;
      }

      return var3;
   }
}
