package gnu.inet.ldap;

import gnu.inet.ldap.AttributeValues;
import java.util.Set;

public final class Modification extends AttributeValues {

   public static final int ADD = 0;
   public static final int DELETE = 1;
   public static final int REPLACE = 2;
   protected final int operation;


   public Modification(int var1, String var2, Set var3) {
      super(var2, var3);
      if(var1 >= 0 && var1 <= 2) {
         this.operation = var1;
      } else {
         throw new IllegalArgumentException("unknown operation");
      }
   }

   public int getOperation() {
      return this.operation;
   }
}
