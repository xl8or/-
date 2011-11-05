package gnu.inet.nntp;

import gnu.inet.nntp.StatusResponse;
import java.io.IOException;

public class NNTPException extends IOException {

   protected final StatusResponse response;


   protected NNTPException(StatusResponse var1) {
      String var2 = var1.getMessage();
      super(var2);
      this.response = var1;
   }

   public StatusResponse getResponse() {
      return this.response;
   }
}
