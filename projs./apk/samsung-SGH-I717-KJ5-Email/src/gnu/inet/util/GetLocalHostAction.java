package gnu.inet.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PrivilegedAction;

public class GetLocalHostAction implements PrivilegedAction {

   public GetLocalHostAction() {}

   public Object run() {
      InetAddress var1;
      InetAddress var2;
      try {
         var1 = InetAddress.getLocalHost();
      } catch (UnknownHostException var4) {
         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }
}
