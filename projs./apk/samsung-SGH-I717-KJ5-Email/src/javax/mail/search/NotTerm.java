package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

public final class NotTerm extends SearchTerm {

   protected SearchTerm term;


   public NotTerm(SearchTerm var1) {
      this.term = var1;
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof NotTerm) {
         SearchTerm var2 = ((NotTerm)var1).term;
         SearchTerm var3 = this.term;
         if(var2.equals(var3)) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public SearchTerm getTerm() {
      return this.term;
   }

   public int hashCode() {
      return this.term.hashCode() << 1;
   }

   public boolean match(Message var1) {
      boolean var2;
      if(!this.term.match(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
