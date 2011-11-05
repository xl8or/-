package gnu.inet.ldap;

import java.util.List;
import java.util.Map;

public interface ResultHandler {

   void searchResultEntry(String var1, Map var2);

   void searchResultReference(List var1);
}
