package javax.mail.event;

import java.util.EventListener;
import javax.mail.event.MessageChangedEvent;

public interface MessageChangedListener extends EventListener {

   void messageChanged(MessageChangedEvent var1);
}
