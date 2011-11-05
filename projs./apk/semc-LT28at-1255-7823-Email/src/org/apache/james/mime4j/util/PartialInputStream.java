package org.apache.james.mime4j.util;

import java.io.IOException;
import java.io.InputStream;
import org.apache.james.mime4j.util.PositionInputStream;

public class PartialInputStream extends PositionInputStream {

   private final long limit;


   public PartialInputStream(InputStream var1, long var2, long var4) throws IOException {
      super(var1);
      var1.skip(var2);
      long var8 = var2 + var4;
      this.limit = var8;
   }

   private int getBytesLeft() {
      long var1 = this.limit;
      long var3 = this.position;
      long var5 = var1 - var3;
      return (int)Math.min(2147483647L, var5);
   }

   public int available() throws IOException {
      int var1 = super.available();
      int var2 = this.getBytesLeft();
      return Math.min(var1, var2);
   }

   public int read() throws IOException {
      long var1 = this.limit;
      long var3 = this.position;
      int var5;
      if(var1 > var3) {
         var5 = super.read();
      } else {
         var5 = -1;
      }

      return var5;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.getBytesLeft();
      int var5 = Math.min(var3, var4);
      return super.read(var1, var2, var5);
   }

   public long skip(long var1) throws IOException {
      long var3 = (long)this.getBytesLeft();
      long var5 = Math.min(var1, var3);
      return super.skip(var5);
   }
}
