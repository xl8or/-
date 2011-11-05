package gnu.inet.imap;

import java.util.List;

public final class MessageStatus {

   private List code;
   private int messageNumber;


   MessageStatus(int var1, List var2) {
      this.messageNumber = var1;
      this.code = var2;
   }

   public List getCode() {
      return this.code;
   }

   public int getMessageNumber() {
      return this.messageNumber;
   }
}
