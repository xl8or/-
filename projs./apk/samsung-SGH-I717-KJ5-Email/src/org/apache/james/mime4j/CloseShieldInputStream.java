package org.apache.james.mime4j;

import java.io.IOException;
import java.io.InputStream;

public class CloseShieldInputStream extends InputStream {

   private InputStream is;


   public CloseShieldInputStream(InputStream var1) {
      this.is = var1;
   }

   private void checkIfClosed() throws IOException {
      if(this.is == null) {
         throw new IOException("Stream is closed");
      }
   }

   public int available() throws IOException {
      this.checkIfClosed();
      return this.is.available();
   }

   public void close() throws IOException {
      this.is = null;
   }

   public InputStream getUnderlyingStream() {
      return this.is;
   }

   public void mark(int var1) {
      synchronized(this){}

      try {
         if(this.is != null) {
            this.is.mark(var1);
         }
      } finally {
         ;
      }

   }

   public boolean markSupported() {
      byte var1;
      if(this.is == null) {
         var1 = 0;
      } else {
         var1 = this.is.markSupported();
      }

      return (boolean)var1;
   }

   public int read() throws IOException {
      this.checkIfClosed();
      return this.is.read();
   }

   public int read(byte[] var1) throws IOException {
      this.checkIfClosed();
      return this.is.read(var1);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      this.checkIfClosed();
      return this.is.read(var1, var2, var3);
   }

   public void reset() throws IOException {
      synchronized(this){}

      try {
         this.checkIfClosed();
         this.is.reset();
      } finally {
         ;
      }

   }

   public long skip(long var1) throws IOException {
      this.checkIfClosed();
      return this.is.skip(var1);
   }
}
