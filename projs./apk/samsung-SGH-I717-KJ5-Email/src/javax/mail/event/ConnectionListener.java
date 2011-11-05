package javax.mail.event;

import java.util.EventListener;
import javax.mail.event.ConnectionEvent;

public interface ConnectionListener extends EventListener {

   void closed(ConnectionEvent var1);

   void disconnected(ConnectionEvent var1);

   void opened(ConnectionEvent var1);
}
