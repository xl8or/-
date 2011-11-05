package org.apache.commons.httpclient;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.httpclient.Wire;

class WireLogOutputStream extends FilterOutputStream {

   private OutputStream out;
   private Wire wire;


   public WireLogOutputStream(OutputStream var1, Wire var2) {
      super(var1);
      this.out = var1;
      this.wire = var2;
   }

   public void write(int var1) throws IOException {
      this.out.write(var1);
      this.wire.output(var1);
   }

   public void write(byte[] var1) throws IOException {
      this.out.write(var1);
      this.wire.output(var1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
      this.wire.output(var1, var2, var3);
   }
}
