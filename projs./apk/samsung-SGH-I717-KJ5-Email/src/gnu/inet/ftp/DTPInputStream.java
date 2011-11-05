package gnu.inet.ftp;

import gnu.inet.ftp.DTP;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

abstract class DTPInputStream extends FilterInputStream {

   DTP dtp;
   boolean transferComplete;


   DTPInputStream(DTP var1, InputStream var2) {
      super(var2);
      this.dtp = var1;
      this.transferComplete = (boolean)0;
   }

   public void close() throws IOException {
      this.dtp.transferComplete();
   }

   void setTransferComplete(boolean var1) {
      this.transferComplete = var1;
   }
}
