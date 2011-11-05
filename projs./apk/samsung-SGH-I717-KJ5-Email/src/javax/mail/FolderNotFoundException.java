package javax.mail;

import javax.mail.Folder;
import javax.mail.MessagingException;

public class FolderNotFoundException extends MessagingException {

   private Folder folder;


   public FolderNotFoundException() {}

   public FolderNotFoundException(String var1, Folder var2) {
      this(var2, var1);
   }

   public FolderNotFoundException(Folder var1) {
      this.folder = var1;
   }

   public FolderNotFoundException(Folder var1, String var2) {
      super(var2);
      this.folder = var1;
   }

   public Folder getFolder() {
      return this.folder;
   }
}
