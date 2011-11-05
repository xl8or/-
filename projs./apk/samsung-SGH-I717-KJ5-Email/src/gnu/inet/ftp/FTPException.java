package gnu.inet.ftp;

import gnu.inet.ftp.FTPResponse;
import java.io.IOException;

public class FTPException extends IOException {

   protected final FTPResponse response;


   public FTPException(FTPResponse var1) {
      String var2 = var1.getMessage();
      super(var2);
      this.response = var1;
   }

   public FTPResponse getResponse() {
      return this.response;
   }
}
