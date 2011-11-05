package gnu.inet.http.event;

import java.util.EventObject;

public class ConnectionEvent extends EventObject {

   public static final int CONNECTION_CLOSED;
   protected int type;


   public ConnectionEvent(Object var1, int var2) {
      super(var1);
      this.type = var2;
   }

   public int getType() {
      return this.type;
   }
}
