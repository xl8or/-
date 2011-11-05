package javax.mail;

import javax.mail.Folder;
import javax.mail.MessagingException;

public class FolderClosedException extends MessagingException {

   private Folder folder;


   public FolderClosedException(Folder var1) {
      this(var1, (String)null);
   }

   public FolderClosedException(Folder var1, String var2) {
      super(var2);
      this.folder = var1;
   }

   public Folder getFolder() {
      return this.folder;
   }
}
