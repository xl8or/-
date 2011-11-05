package gnu.inet.http;

import gnu.inet.http.Headers;
import java.util.Date;

public class Response {

   protected final int code;
   protected final int codeClass;
   protected final Headers headers;
   protected final int majorVersion;
   protected final String message;
   protected final int minorVersion;


   protected Response(int var1, int var2, int var3, int var4, String var5, Headers var6) {
      this.majorVersion = var1;
      this.minorVersion = var2;
      this.code = var3;
      this.codeClass = var4;
      this.message = var5;
      this.headers = var6;
   }

   public int getCode() {
      return this.code;
   }

   public int getCodeClass() {
      return this.codeClass;
   }

   public Date getDateHeader(String var1) {
      return this.headers.getDateValue(var1);
   }

   public String getHeader(String var1) {
      return this.headers.getValue(var1);
   }

   public Headers getHeaders() {
      return this.headers;
   }

   public int getIntHeader(String var1) {
      return this.headers.getIntValue(var1);
   }

   public int getMajorVersion() {
      return this.majorVersion;
   }

   public String getMessage() {
      return this.message;
   }

   public int getMinorVersion() {
      return this.minorVersion;
   }
}
