package gnu.inet.imap;

import java.io.IOException;

public class IMAPException extends IOException {

   protected String id;


   public IMAPException(String var1, String var2) {
      super(var2);
      this.id = var1;
   }

   public String getId() {
      return this.id;
   }
}
