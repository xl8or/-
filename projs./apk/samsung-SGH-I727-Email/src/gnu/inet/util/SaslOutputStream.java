package gnu.inet.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.security.sasl.SaslClient;

public class SaslOutputStream extends FilterOutputStream {

   private final SaslClient sasl;


   public SaslOutputStream(SaslClient var1, OutputStream var2) {
      super(var2);
      this.sasl = var1;
   }

   public void write(int var1) throws IOException {
      byte[] var2 = new byte[1];
      byte var3 = (byte)var1;
      var2[0] = var3;
      this.write(var2, 0, 1);
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      byte[] var4 = this.sasl.wrap(var1, var2, var3);
      int var5 = var4.length;
      super.write(var4, 0, var5);
   }
}
