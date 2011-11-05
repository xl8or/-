package javax.mail.event;

import java.util.EventListener;
import javax.mail.event.StoreEvent;

public interface StoreListener extends EventListener {

   void notification(StoreEvent var1);
}
