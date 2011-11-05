package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.output.ProxyOutputStream;

public class TeeOutputStream extends ProxyOutputStream {

   protected OutputStream branch;


   public TeeOutputStream(OutputStream var1, OutputStream var2) {
      super(var1);
      this.branch = var2;
   }

   public void close() throws IOException {
      super.close();
      this.branch.close();
   }

   public void flush() throws IOException {
      super.flush();
      this.branch.flush();
   }

   public void write(int var1) throws IOException {
      synchronized(this){}

      try {
         super.write(var1);
         this.branch.write(var1);
      } finally {
         ;
      }

   }

   public void write(byte[] var1) throws IOException {
      synchronized(this){}

      try {
         super.write(var1);
         this.branch.write(var1);
      } finally {
         ;
      }

   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      synchronized(this){}

      try {
         super.write(var1, var2, var3);
         this.branch.write(var1, var2, var3);
      } finally {
         ;
      }

   }
}
