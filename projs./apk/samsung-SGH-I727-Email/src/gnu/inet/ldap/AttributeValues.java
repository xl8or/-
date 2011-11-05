package gnu.inet.ldap;

import java.util.Set;

public class AttributeValues {

   protected final String type;
   protected final Set values;


   public AttributeValues(String var1, Set var2) {
      if(var1 == null) {
         throw new NullPointerException("type");
      } else {
         this.type = var1;
         this.values = var2;
      }
   }

   public String getType() {
      return this.type;
   }

   public Set getValues() {
      return this.values;
   }
}
