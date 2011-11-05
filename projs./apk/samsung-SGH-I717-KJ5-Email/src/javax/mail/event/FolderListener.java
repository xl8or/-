package javax.mail.event;

import java.util.EventListener;
import javax.mail.event.FolderEvent;

public interface FolderListener extends EventListener {

   void folderCreated(FolderEvent var1);

   void folderDeleted(FolderEvent var1);

   void folderRenamed(FolderEvent var1);
}
