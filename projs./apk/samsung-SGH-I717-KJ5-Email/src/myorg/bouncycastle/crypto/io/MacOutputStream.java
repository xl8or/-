package myorg.bouncycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.crypto.Mac;

public class MacOutputStream extends FilterOutputStream {

   protected Mac mac;


   public MacOutputStream(OutputStream var1, Mac var2) {
      super(var1);
      this.mac = var2;
   }

   public Mac getMac() {
      return this.mac;
   }

   public void write(int var1) throws IOException {
      Mac var2 = this.mac;
      byte var3 = (byte)var1;
      var2.update(var3);
      this.out.write(var1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.mac.update(var1, var2, var3);
      this.out.write(var1, var2, var3);
   }
}
