package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.StringTerm;

public final class BodyTerm extends StringTerm {

   public BodyTerm(String var1) {
      super(var1);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 instanceof BodyTerm && super.equals(var1)) {
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
