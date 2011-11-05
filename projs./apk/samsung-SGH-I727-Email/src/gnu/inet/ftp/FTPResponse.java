package gnu.inet.ftp;


public final class FTPResponse {

   protected final int code;
   protected final String data;
   protected final String message;


   public FTPResponse(int var1, String var2) {
      this(var1, var2, (String)null);
   }

   public FTPResponse(int var1, String var2, String var3) {
      this.code = var1;
      this.message = var2;
      this.data = var3;
   }

   public int getCode() {
      return this.code;
   }

   public String getData() {
      return this.data;
   }

   public String getMessage() {
      return this.message;
   }
}
