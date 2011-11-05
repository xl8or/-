package javax.mail.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import javax.mail.internet.SharedInputStream;

public class SharedFileInputStream extends BufferedInputStream implements SharedInputStream {

   protected long bufpos;
   protected int bufsize;
   protected long datalen;
   protected RandomAccessFile in;
   private final int[] openCount;
   protected long start;


   public SharedFileInputStream(File var1) throws IOException {
      super((InputStream)null);
      this.start = 0L;
      int var2 = this.buf.length;
      this.bufsize = var2;
      RandomAccessFile var3 = new RandomAccessFile(var1, "r");
      this.in = var3;
      long var4 = this.in.length();
      this.datalen = var4;
      int[] var6 = new int[]{1};
      this.openCount = var6;
   }

   public SharedFileInputStream(File var1, int var2) throws IOException {
      super((InputStream)null, var2);
      this.start = 0L;
      this.bufsize = var2;
      RandomAccessFile var3 = new RandomAccessFile(var1, "r");
      this.in = var3;
      long var4 = this.in.length();
      this.datalen = var4;
      int[] var6 = new int[]{1};
      this.openCount = var6;
   }

   public SharedFileInputStream(String var1) throws IOException {
      super((InputStream)null);
      this.start = 0L;
      int var2 = this.buf.length;
      this.bufsize = var2;
      RandomAccessFile var3 = new RandomAccessFile(var1, "r");
      this.in = var3;
      long var4 = this.in.length();
      this.datalen = var4;
      int[] var6 = new int[]{1};
      this.openCount = var6;
   }

   public SharedFileInputStream(String var1, int var2) throws IOException {
      super((InputStream)null, var2);
      this.start = 0L;
      this.bufsize = var2;
      RandomAccessFile var3 = new RandomAccessFile(var1, "r");
      this.in = var3;
      long var4 = this.in.length();
      this.datalen = var4;
      int[] var6 = new int[]{1};
      this.openCount = var6;
   }

   private SharedFileInputStream(SharedFileInputStream var1, long var2, long var4) {
      int var6 = var1.bufsize;
      super((InputStream)null, var6);
      this.start = 0L;
      int[] var7 = var1.openCount;
      this.openCount = var7;
      RandomAccessFile var8 = var1.in;
      this.in = var8;
      int var9 = var1.bufsize;
      this.bufsize = var9;
      this.start = var2;
      this.datalen = var4;
      this.bufpos = var2;
   }

   public int available() throws IOException {
      long var1 = this.in.length();
      long var3 = this.start;
      long var5 = this.datalen;
      long var7 = var3 + var5;
      long var9 = Math.min(var1, var7);
      long var11 = this.bufpos;
      return (int)(var9 - var11);
   }

   public void close() throws IOException {
      // $FF: Couldn't be decompiled
   }

   protected void finalize() throws Throwable {
      this.close();
   }

   public long getPosition() {
      long var1 = this.bufpos;
      long var3 = this.start;
      return var1 - var3;
   }

   public void mark(int var1) {
      super.mark(var1);
   }

   public boolean markSupported() {
      return super.markSupported();
   }

   public InputStream newStream(long param1, long param3) {
      // $FF: Couldn't be decompiled
   }

   public int read() throws IOException {
      RandomAccessFile var1 = this.in;
      long var2 = this.bufpos;
      var1.seek(var2);
      int var4 = this.in.read();
      if(var4 != -1) {
         long var5 = this.bufpos + 1L;
         this.bufpos = var5;
      }

      return var4;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      RandomAccessFile var4 = this.in;
      long var5 = this.bufpos;
      var4.seek(var5);
      int var7 = this.in.read(var1, var2, var3);
      if(var7 > 0) {
         long var8 = this.bufpos;
         long var10 = (long)var7;
         long var12 = var8 + var10;
         this.bufpos = var12;
      }

      return var7;
   }

   public void reset() throws IOException {
      super.reset();
   }

   public long skip(long var1) throws IOException {
      long var3 = this.in.length();
      long var5 = this.start;
      long var7 = this.datalen;
      long var9 = var5 + var7;
      long var11 = Math.min(var3, var9);
      long var15;
      if(this.bufpos + var1 > var11) {
         long var13 = this.bufpos + var1;
         var15 = var11 - var13;
      } else {
         var15 = var1;
      }

      long var17 = this.bufpos + var15;
      this.bufpos = var17;
      return var15;
   }
}
