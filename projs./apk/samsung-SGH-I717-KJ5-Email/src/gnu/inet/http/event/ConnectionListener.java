package gnu.inet.http.event;

import gnu.inet.http.event.ConnectionEvent;
import java.util.EventListener;

public interface ConnectionListener extends EventListener {

   void connectionClosed(ConnectionEvent var1);
}
