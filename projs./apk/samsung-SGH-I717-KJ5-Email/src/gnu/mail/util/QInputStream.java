package gnu.mail.util;

import gnu.mail.util.QPInputStream;
import java.io.IOException;
import java.io.InputStream;

public class QInputStream extends QPInputStream {

   private static final int EQ = 61;
   private static final int SPACE = 32;
   private static final int UNDERSCORE = 95;


   public QInputStream(InputStream var1) {
      super(var1);
   }

   public int read() throws IOException {
      int var1 = this.in.read();
      if(var1 == 95) {
         var1 = 32;
      } else if(var1 == 61) {
         byte[] var2 = this.buf;
         byte var3 = (byte)this.in.read();
         var2[0] = var3;
         byte[] var4 = this.buf;
         byte var5 = (byte)this.in.read();
         var4[1] = var5;

         int var7;
         try {
            byte[] var6 = this.buf;
            var7 = Integer.parseInt(new String(var6, 0, 2), 16);
         } catch (NumberFormatException var12) {
            StringBuilder var9 = (new StringBuilder()).append("Quoted-Printable encoding error: ");
            String var10 = var12.getMessage();
            String var11 = var9.append(var10).toString();
            throw new IOException(var11);
         }

         var1 = var7;
      }

      return var1;
   }
}
