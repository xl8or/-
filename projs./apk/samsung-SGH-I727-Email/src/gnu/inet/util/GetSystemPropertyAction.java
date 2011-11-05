package gnu.inet.util;

import java.security.PrivilegedAction;

public class GetSystemPropertyAction implements PrivilegedAction {

   final String name;


   public GetSystemPropertyAction(String var1) {
      this.name = var1;
   }

   public Object run() {
      return System.getProperty(this.name);
   }
}
