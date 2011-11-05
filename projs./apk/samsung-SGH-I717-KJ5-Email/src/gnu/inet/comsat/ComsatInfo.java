package gnu.inet.comsat;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComsatInfo {

   protected String body;
   protected Map headers;
   protected String mailbox;


   public ComsatInfo() {
      LinkedHashMap var1 = new LinkedHashMap();
      this.headers = var1;
   }

   public String getBody() {
      return this.body;
   }

   public String getHeader(String var1) {
      return (String)this.headers.get(var1);
   }

   public Iterator getHeaderNames() {
      return this.headers.keySet().iterator();
   }

   public String getMailbox() {
      return this.mailbox;
   }

   protected void setBody(String var1) {
      this.body = var1;
   }

   protected void setHeader(String var1, String var2) {
      this.headers.put(var1, var2);
   }

   protected void setMailbox(String var1) {
      this.mailbox = var1;
   }
}
