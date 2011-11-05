package javax.mail.event;

import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;

public abstract class ConnectionAdapter implements ConnectionListener {

   public ConnectionAdapter() {}

   public void closed(ConnectionEvent var1) {}

   public void disconnected(ConnectionEvent var1) {}

   public void opened(ConnectionEvent var1) {}
}
