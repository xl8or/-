package myorg.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.crypto.tls.TlsProtocolHandler;

public class TlsOuputStream extends OutputStream {

   private byte[] buf;
   private TlsProtocolHandler handler;


   TlsOuputStream(TlsProtocolHandler var1) {
      byte[] var2 = new byte[1];
      this.buf = var2;
      this.handler = var1;
   }

   public void close() throws IOException {
      this.handler.close();
   }

   public void cose() throws IOException {
      this.handler.close();
   }

   public void flush() throws IOException {
      this.handler.flush();
   }

   public void write(int var1) throws IOException {
      byte[] var2 = this.buf;
      byte var3 = (byte)var1;
      var2[0] = var3;
      byte[] var4 = this.buf;
      this.write(var4, 0, 1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.handler.writeData(var1, var2, var3);
   }
}
