package gnu.mail.treeutil;

import gnu.mail.treeutil.StatusEvent;
import java.util.EventListener;

public interface StatusListener extends EventListener {

   void statusOperationEnded(StatusEvent var1);

   void statusOperationStarted(StatusEvent var1);

   void statusProgressUpdate(StatusEvent var1);
}
