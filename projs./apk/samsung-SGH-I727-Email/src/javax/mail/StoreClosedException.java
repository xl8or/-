package javax.mail;

import javax.mail.MessagingException;
import javax.mail.Store;

public class StoreClosedException extends MessagingException {

   private Store store;


   public StoreClosedException(Store var1) {
      this(var1, (String)null);
   }

   public StoreClosedException(Store var1, String var2) {
      super(var2);
      this.store = var1;
   }

   public Store getStore() {
      return this.store;
   }
}
