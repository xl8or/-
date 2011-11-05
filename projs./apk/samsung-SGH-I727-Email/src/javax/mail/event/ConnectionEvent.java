package javax.mail.event;

import javax.mail.event.ConnectionListener;
import javax.mail.event.MailEvent;

public class ConnectionEvent extends MailEvent {

   public static final int CLOSED = 3;
   public static final int DISCONNECTED = 2;
   public static final int OPENED = 1;
   protected int type;


   public ConnectionEvent(Object var1, int var2) {
      super(var1);
      this.type = var2;
   }

   public void dispatch(Object var1) {
      ConnectionListener var2 = (ConnectionListener)var1;
      switch(this.type) {
      case 1:
         var2.opened(this);
         return;
      case 2:
         var2.disconnected(this);
         return;
      case 3:
         var2.closed(this);
         return;
      default:
      }
   }

   public int getType() {
      return this.type;
   }
}
