package javax.mail.search;

import java.util.Date;
import javax.mail.Message;
import javax.mail.search.DateTerm;

public final class SentDateTerm extends DateTerm {

   public SentDateTerm(int var1, Date var2) {
      super(var1, var2);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 instanceof SentDateTerm && super.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean match(Message var1) {
      boolean var3;
      boolean var4;
      label23: {
         try {
            Date var2 = var1.getSentDate();
            if(var2 != null) {
               var3 = super.match(var2);
               break label23;
            }
         } catch (Exception var6) {
            ;
         }

         var4 = false;
         return var4;
      }

      var4 = var3;
      return var4;
   }
}
