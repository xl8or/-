package javax.mail.search;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.search.StringTerm;

public abstract class AddressStringTerm extends StringTerm {

   protected AddressStringTerm(String var1) {
      super(var1, (boolean)1);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 instanceof AddressStringTerm && super.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected boolean match(Address var1) {
      boolean var3;
      if(var1 instanceof InternetAddress) {
         String var2 = ((InternetAddress)var1).toUnicodeString();
         var3 = super.match(var2);
      } else {
         String var4 = var1.toString();
         var3 = super.match(var4);
      }

      return var3;
   }
}
