package org.apache.commons.httpclient;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.Wire;

class WireLogInputStream extends FilterInputStream {

   private InputStream in;
   private Wire wire;


   public WireLogInputStream(InputStream var1, Wire var2) {
      super(var1);
      this.in = var1;
      this.wire = var2;
   }

   public int read() throws IOException {
      int var1 = this.in.read();
      if(var1 > 0) {
         this.wire.input(var1);
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = this.in.read(var1);
      if(var2 > 0) {
         this.wire.input(var1, 0, var2);
      }

      return var2;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.in.read(var1, var2, var3);
      if(var4 > 0) {
         this.wire.input(var1, var2, var4);
      }

      return var4;
   }
}
