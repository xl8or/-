package org.apache.commons.io.input;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class NullReader extends Reader {

   private boolean eof;
   private long mark;
   private boolean markSupported;
   private long position;
   private long readlimit;
   private long size;
   private boolean throwEofException;


   public NullReader(long var1) {
      this(var1, (boolean)1, (boolean)0);
   }

   public NullReader(long var1, boolean var3, boolean var4) {
      this.mark = 65535L;
      this.size = var1;
      this.markSupported = var3;
      this.throwEofException = var4;
   }

   private int doEndOfFile() throws EOFException {
      this.eof = (boolean)1;
      if(this.throwEofException) {
         throw new EOFException();
      } else {
         return -1;
      }
   }

   public void close() throws IOException {
      this.eof = (boolean)0;
      this.position = 0L;
      this.mark = 65535L;
   }

   public long getPosition() {
      return this.position;
   }

   public long getSize() {
      return this.size;
   }

   public void mark(int var1) {
      synchronized(this){}

      try {
         if(!this.markSupported) {
            throw new UnsupportedOperationException("Mark not supported");
         }

         long var3 = this.position;
         this.mark = var3;
         long var5 = (long)var1;
         this.readlimit = var5;
      } finally {
         ;
      }

   }

   public boolean markSupported() {
      return this.markSupported;
   }

   protected int processChar() {
      return 0;
   }

   protected void processChars(char[] var1, int var2, int var3) {}

   public int read() throws IOException {
      if(this.eof) {
         throw new IOException("Read after end of file");
      } else {
         long var1 = this.position;
         long var3 = this.size;
         int var5;
         if(var1 == var3) {
            var5 = this.doEndOfFile();
         } else {
            long var6 = this.position + 1L;
            this.position = var6;
            var5 = this.processChar();
         }

         return var5;
      }
   }

   public int read(char[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if(this.eof) {
         throw new IOException("Read after end of file");
      } else {
         long var4 = this.position;
         long var6 = this.size;
         int var8;
         if(var4 == var6) {
            var8 = this.doEndOfFile();
         } else {
            long var9 = this.position;
            long var11 = (long)var3;
            long var13 = var9 + var11;
            this.position = var13;
            int var15 = var3;
            long var16 = this.position;
            long var18 = this.size;
            if(var16 > var18) {
               long var20 = this.position;
               long var22 = this.size;
               int var24 = (int)(var20 - var22);
               var15 = var3 - var24;
               long var25 = this.size;
               this.position = var25;
            }

            this.processChars(var1, var2, var15);
            var8 = var15;
         }

         return var8;
      }
   }

   public void reset() throws IOException {
      synchronized(this){}

      try {
         if(!this.markSupported) {
            throw new UnsupportedOperationException("Mark not supported");
         }

         if(this.mark < 0L) {
            throw new IOException("No position has been marked");
         }

         long var2 = this.position;
         long var4 = this.mark;
         long var6 = this.readlimit;
         long var8 = var4 + var6;
         if(var2 > var8) {
            StringBuilder var10 = (new StringBuilder()).append("Marked position [");
            long var11 = this.mark;
            StringBuilder var13 = var10.append(var11).append("] is no longer valid - passed the read limit [");
            long var14 = this.readlimit;
            String var16 = var13.append(var14).append("]").toString();
            throw new IOException(var16);
         }

         long var17 = this.mark;
         this.position = var17;
         this.eof = (boolean)0;
      } finally {
         ;
      }

   }

   public long skip(long var1) throws IOException {
      if(this.eof) {
         throw new IOException("Skip after end of file");
      } else {
         long var3 = this.position;
         long var5 = this.size;
         long var7;
         if(var3 == var5) {
            var7 = (long)this.doEndOfFile();
         } else {
            long var9 = this.position + var1;
            this.position = var9;
            long var11 = var1;
            long var13 = this.position;
            long var15 = this.size;
            if(var13 > var15) {
               long var17 = this.position;
               long var19 = this.size;
               long var21 = var17 - var19;
               var11 = var1 - var21;
               long var23 = this.size;
               this.position = var23;
            }

            var7 = var11;
         }

         return var7;
      }
   }
}
