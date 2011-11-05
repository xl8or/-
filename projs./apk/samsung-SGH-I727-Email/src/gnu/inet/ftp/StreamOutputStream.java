package gnu.inet.ftp;

import gnu.inet.ftp.DTP;
import gnu.inet.ftp.DTPOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class StreamOutputStream extends DTPOutputStream {

   StreamOutputStream(DTP var1, OutputStream var2) {
      super(var1, var2);
   }

   public void write(int var1) throws IOException {
      if(!this.transferComplete) {
         this.out.write(var1);
      }
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if(!this.transferComplete) {
         this.out.write(var1, var2, var3);
      }
   }
}
