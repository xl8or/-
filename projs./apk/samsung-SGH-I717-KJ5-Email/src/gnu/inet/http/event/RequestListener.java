package gnu.inet.http.event;

import gnu.inet.http.event.RequestEvent;
import java.util.EventListener;

public interface RequestListener extends EventListener {

   void requestCreated(RequestEvent var1);

   void requestSending(RequestEvent var1);

   void requestSent(RequestEvent var1);
}
