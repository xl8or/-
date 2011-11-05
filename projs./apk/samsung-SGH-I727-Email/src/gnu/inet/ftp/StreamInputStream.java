package gnu.inet.ftp;

import gnu.inet.ftp.DTP;
import gnu.inet.ftp.DTPInputStream;
import java.io.IOException;
import java.io.InputStream;

class StreamInputStream extends DTPInputStream {

   StreamInputStream(DTP var1, InputStream var2) {
      super(var1, var2);
   }

   public int read() throws IOException {
      int var1;
      if(this.transferComplete) {
         var1 = -1;
      } else {
         var1 = this.in.read();
         if(var1 == -1) {
            this.close();
         }
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this.transferComplete) {
         var4 = -1;
      } else {
         var4 = this.in.read(var1, var2, var3);
         if(var4 == -1) {
            this.close();
         }
      }

      return var4;
   }
}
