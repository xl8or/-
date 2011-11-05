package javax.mail.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.mail.internet.SharedInputStream;

public class SharedByteArrayInputStream extends ByteArrayInputStream implements SharedInputStream {

   private int off;


   public SharedByteArrayInputStream(byte[] var1) {
      super(var1);
      this.off = 0;
   }

   public SharedByteArrayInputStream(byte[] var1, int var2, int var3) {
      super(var1, var2, var3);
      this.off = var2;
   }

   public long getPosition() {
      int var1 = this.pos;
      int var2 = this.off;
      return (long)(var1 - var2);
   }

   public InputStream newStream(long var1, long var3) {
      int var5 = (int)(var3 - var1);
      byte[] var6 = this.buf;
      int var7 = this.off;
      int var8 = (int)var1;
      int var9 = var7 + var8;
      return new SharedByteArrayInputStream(var6, var9, var5);
   }
}
