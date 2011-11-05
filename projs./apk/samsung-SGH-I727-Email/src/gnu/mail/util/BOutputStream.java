package gnu.mail.util;

import gnu.mail.util.Base64OutputStream;
import java.io.OutputStream;

public class BOutputStream extends Base64OutputStream {

   public BOutputStream(OutputStream var1) {
      super(var1, Integer.MAX_VALUE);
   }

   public static int encodedLength(byte[] var0) {
      return (var0.length + 2) / 3 * 4;
   }
}
