package gnu.inet.nntp;


public class StatusResponse {

   protected String message;
   protected short status;


   protected StatusResponse(short var1, String var2) {
      this.status = var1;
      this.message = var2;
   }

   public String getMessage() {
      return this.message;
   }

   public short getStatus() {
      return this.status;
   }
}
