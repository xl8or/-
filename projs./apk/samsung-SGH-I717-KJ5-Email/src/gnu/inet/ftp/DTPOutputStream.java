package gnu.inet.ftp;

import gnu.inet.ftp.DTP;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

abstract class DTPOutputStream extends FilterOutputStream {

   DTP dtp;
   boolean transferComplete;


   DTPOutputStream(DTP var1, OutputStream var2) {
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
