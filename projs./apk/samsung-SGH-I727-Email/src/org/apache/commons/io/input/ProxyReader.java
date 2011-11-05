package org.apache.commons.io.input;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public abstract class ProxyReader extends FilterReader {

   public ProxyReader(Reader var1) {
      super(var1);
   }

   public void close() throws IOException {
      this.in.close();
   }

   public void mark(int var1) throws IOException {
      synchronized(this){}

      try {
         this.in.mark(var1);
      } finally {
         ;
      }

   }

   public boolean markSupported() {
      return this.in.markSupported();
   }

   public int read() throws IOException {
      return this.in.read();
   }

   public int read(char[] var1) throws IOException {
      return this.in.read(var1);
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      return this.in.read(var1, var2, var3);
   }

   public boolean ready() throws IOException {
      return this.in.ready();
   }

   public void reset() throws IOException {
      synchronized(this){}

      try {
         this.in.reset();
      } finally {
         ;
      }

   }

   public long skip(long var1) throws IOException {
      return this.in.skip(var1);
   }
}
