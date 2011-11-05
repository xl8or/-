package javax.mail.event;

import javax.mail.Folder;
import javax.mail.event.FolderListener;
import javax.mail.event.MailEvent;

public class FolderEvent extends MailEvent {

   public static final int CREATED = 1;
   public static final int DELETED = 2;
   public static final int RENAMED = 3;
   protected transient Folder folder;
   protected transient Folder newFolder;
   protected int type;


   public FolderEvent(Object var1, Folder var2, int var3) {
      this(var1, var2, var2, var3);
   }

   public FolderEvent(Object var1, Folder var2, Folder var3, int var4) {
      super(var1);
      this.folder = var2;
      this.newFolder = var3;
      this.type = var4;
   }

   public void dispatch(Object var1) {
      FolderListener var2 = (FolderListener)var1;
      switch(this.type) {
      case 1:
         var2.folderCreated(this);
         return;
      case 2:
         var2.folderDeleted(this);
         return;
      case 3:
         var2.folderRenamed(this);
         return;
      default:
      }
   }

   public Folder getFolder() {
      return this.folder;
   }

   public Folder getNewFolder() {
      return this.newFolder;
   }

   public int getType() {
      return this.type;
   }
}
