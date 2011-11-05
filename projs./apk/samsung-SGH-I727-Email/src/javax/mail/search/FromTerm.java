package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.search.AddressTerm;

public final class FromTerm extends AddressTerm {

   public FromTerm(Address var1) {
      super(var1);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 instanceof FromTerm && super.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean match(Message param1) {
      // $FF: Couldn't be decompiled
   }
}
