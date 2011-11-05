package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.IntegerComparisonTerm;

public final class MessageNumberTerm extends IntegerComparisonTerm {

   public MessageNumberTerm(int var1) {
      super(3, var1);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 instanceof MessageNumberTerm && super.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean match(Message var1) {
      boolean var3;
      boolean var4;
      try {
         int var2 = var1.getMessageNumber();
         var3 = super.match(var2);
      } catch (Exception var6) {
         var4 = false;
         return var4;
      }

      var4 = var3;
      return var4;
   }
}
