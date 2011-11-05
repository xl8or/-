package javax.mail.event;

import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

public abstract class TransportAdapter implements TransportListener {

   public TransportAdapter() {}

   public void messageDelivered(TransportEvent var1) {}

   public void messageNotDelivered(TransportEvent var1) {}

   public void messagePartiallyDelivered(TransportEvent var1) {}
}
