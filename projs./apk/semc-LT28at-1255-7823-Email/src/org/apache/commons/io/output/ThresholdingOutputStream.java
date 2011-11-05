package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ThresholdingOutputStream extends OutputStream {

   private int threshold;
   private boolean thresholdExceeded;
   private long written;


   public ThresholdingOutputStream(int var1) {
      this.threshold = var1;
   }

   protected void checkThreshold(int var1) throws IOException {
      if(!this.thresholdExceeded) {
         long var2 = this.written;
         long var4 = (long)var1;
         long var6 = var2 + var4;
         long var8 = (long)this.threshold;
         if(var6 > var8) {
            this.thresholdExceeded = (boolean)1;
            this.thresholdReached();
         }
      }
   }

   public void close() throws IOException {
      try {
         this.flush();
      } catch (IOException var2) {
         ;
      }

      this.getStream().close();
   }

   public void flush() throws IOException {
      this.getStream().flush();
   }

   public long getByteCount() {
      return this.written;
   }

   protected abstract OutputStream getStream() throws IOException;

   public int getThreshold() {
      return this.threshold;
   }

   public boolean isThresholdExceeded() {
      long var1 = this.written;
      long var3 = (long)this.threshold;
      boolean var5;
      if(var1 > var3) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   protected void resetByteCount() {
      this.thresholdExceeded = (boolean)0;
      this.written = 0L;
   }

   protected abstract void thresholdReached() throws IOException;

   public void write(int var1) throws IOException {
      this.checkThreshold(1);
      this.getStream().write(var1);
      long var2 = this.written + 1L;
      this.written = var2;
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.checkThreshold(var2);
      this.getStream().write(var1);
      long var3 = this.written;
      long var5 = (long)var1.length;
      long var7 = var3 + var5;
      this.written = var7;
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.checkThreshold(var3);
      this.getStream().write(var1, var2, var3);
      long var4 = this.written;
      long var6 = (long)var3;
      long var8 = var4 + var6;
      this.written = var8;
   }
}
