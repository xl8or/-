package gnu.inet.util;

import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LineInputStream extends FilterInputStream {

   private final boolean blockReads;
   private ByteArrayOutputStream buf;
   private String encoding;
   private boolean eof;


   public LineInputStream(InputStream var1) {
      this(var1, "US-ASCII");
   }

   public LineInputStream(InputStream var1, String var2) {
      super(var1);
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      this.buf = var3;
      this.encoding = var2;
      this.eof = (boolean)0;
      boolean var4 = var1.markSupported();
      this.blockReads = var4;
   }

   private int indexOf(byte[] var1, int var2, byte var3) {
      int var4 = 0;

      while(true) {
         if(var4 >= var2) {
            var4 = -1;
            break;
         }

         if(var1[var4] == var3) {
            break;
         }

         ++var4;
      }

      return var4;
   }

   public String readLine() throws IOException {
      // $FF: Couldn't be decompiled
   }
}
