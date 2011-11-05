package javax.mail.event;

import java.util.EventListener;
import javax.mail.event.MessageCountEvent;

public interface MessageCountListener extends EventListener {

   void messagesAdded(MessageCountEvent var1);

   void messagesRemoved(MessageCountEvent var1);
}
