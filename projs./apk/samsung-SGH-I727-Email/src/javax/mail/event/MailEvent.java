package javax.mail.event;

import java.util.EventObject;

public abstract class MailEvent extends EventObject {

   public MailEvent(Object var1) {
      super(var1);
   }

   public abstract void dispatch(Object var1);
}
