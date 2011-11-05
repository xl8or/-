package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.IntegerComparisonTerm;

public final class SizeTerm extends IntegerComparisonTerm {

   public SizeTerm(int var1, int var2) {
      super(var1, var2);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 instanceof SizeTerm && super.equals(var1)) {
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
            int var2 = var1.getSize();
            if(var2 != -1) {
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
