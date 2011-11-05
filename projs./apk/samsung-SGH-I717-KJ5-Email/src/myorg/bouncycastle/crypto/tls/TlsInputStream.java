package myorg.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.crypto.tls.TlsProtocolHandler;

public class TlsInputStream extends InputStream {

   private byte[] buf;
   private TlsProtocolHandler handler;


   TlsInputStream(TlsProtocolHandler var1) {
      byte[] var2 = new byte[1];
      this.buf = var2;
      this.handler = null;
      this.handler = var1;
   }

   public void close() throws IOException {
      this.handler.close();
   }

   public int read() throws IOException {
      byte[] var1 = this.buf;
      int var2;
      if(this.read(var1) < 0) {
         var2 = -1;
      } else {
         var2 = this.buf[0] & 255;
      }

      return var2;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      return this.handler.readApplicationData(var1, var2, var3);
   }
}
