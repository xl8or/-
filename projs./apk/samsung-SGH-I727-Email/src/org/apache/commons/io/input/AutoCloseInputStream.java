package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.input.ClosedInputStream;
import org.apache.commons.io.input.ProxyInputStream;

public class AutoCloseInputStream extends ProxyInputStream {

   public AutoCloseInputStream(InputStream var1) {
      super(var1);
   }

   public void close() throws IOException {
      this.in.close();
      ClosedInputStream var1 = new ClosedInputStream();
      this.in = var1;
   }

   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   public int read() throws IOException {
      int var1 = this.in.read();
      if(var1 == -1) {
         this.close();
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = this.in.read(var1);
      if(var2 == -1) {
         this.close();
      }

      return var2;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.in.read(var1, var2, var3);
      if(var4 == -1) {
         this.close();
      }

      return var4;
   }
}
