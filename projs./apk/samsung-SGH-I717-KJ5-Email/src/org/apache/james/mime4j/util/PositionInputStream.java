package org.apache.james.mime4j.util;

import java.io.IOException;
import java.io.InputStream;

public class PositionInputStream extends InputStream {

   private final InputStream inputStream;
   private long markedPosition = 0L;
   protected long position = 0L;


   public PositionInputStream(InputStream var1) {
      this.inputStream = var1;
   }

   public int available() throws IOException {
      return this.inputStream.available();
   }

   public void close() throws IOException {
      this.inputStream.close();
   }

   public long getPosition() {
      return this.position;
   }

   public void mark(int var1) {
      this.inputStream.mark(var1);
      long var2 = this.position;
      this.markedPosition = var2;
   }

   public boolean markSupported() {
      return this.inputStream.markSupported();
   }

   public int read() throws IOException {
      int var1 = this.inputStream.read();
      if(var1 != -1) {
         long var2 = this.position + 1L;
         this.position = var2;
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = this.inputStream.read(var1);
      long var3 = this.position;
      long var5 = (long)var2;
      long var7 = var3 + var5;
      this.position = var7;
      return var2;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.inputStream.read(var1, var2, var3);
      long var5 = this.position;
      long var7 = (long)var4;
      long var9 = var5 + var7;
      this.position = var9;
      return var4;
   }

   public void reset() throws IOException {
      this.inputStream.reset();
      long var1 = this.markedPosition;
      this.position = var1;
   }

   public long skip(long var1) throws IOException {
      long var3 = this.inputStream.skip(var1);
      long var5 = this.position + var3;
      this.position = var5;
      return var3;
   }
}
