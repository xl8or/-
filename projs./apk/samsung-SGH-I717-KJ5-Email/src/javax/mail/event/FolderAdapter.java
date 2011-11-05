package javax.mail.event;

import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;

public abstract class FolderAdapter implements FolderListener {

   public FolderAdapter() {}

   public void folderCreated(FolderEvent var1) {}

   public void folderDeleted(FolderEvent var1) {}

   public void folderRenamed(FolderEvent var1) {}
}
