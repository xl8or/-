package gnu.inet.http.event;

import gnu.inet.http.Request;
import java.util.EventObject;

public class RequestEvent extends EventObject {

   public static final int REQUEST_CREATED = 0;
   public static final int REQUEST_SENDING = 1;
   public static final int REQUEST_SENT = 2;
   protected Request request;
   protected int type;


   public RequestEvent(Object var1, int var2, Request var3) {
      super(var1);
      this.type = var2;
      this.request = var3;
   }

   public Request getRequest() {
      return this.request;
   }

   public int getType() {
      return this.type;
   }
}
