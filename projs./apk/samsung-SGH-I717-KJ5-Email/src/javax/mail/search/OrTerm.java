package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

public final class OrTerm extends SearchTerm {

   protected SearchTerm[] terms;


   public OrTerm(SearchTerm var1, SearchTerm var2) {
      SearchTerm[] var3 = new SearchTerm[2];
      this.terms = var3;
      this.terms[0] = var1;
      this.terms[1] = var2;
   }

   public OrTerm(SearchTerm[] var1) {
      SearchTerm[] var2 = new SearchTerm[var1.length];
      this.terms = var2;
      SearchTerm[] var3 = this.terms;
      int var4 = var1.length;
      System.arraycopy(var1, 0, var3, 0, var4);
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof OrTerm) {
         OrTerm var9 = (OrTerm)var1;
         int var2 = var9.terms.length;
         int var3 = this.terms.length;
         if(var2 != var3) {
            var4 = false;
         } else {
            int var5 = 0;

            while(true) {
               int var6 = this.terms.length;
               if(var5 >= var6) {
                  var4 = true;
                  break;
               }

               SearchTerm var7 = this.terms[var5];
               SearchTerm var8 = var9.terms[var5];
               if(!var7.equals(var8)) {
                  var4 = false;
                  break;
               }

               ++var5;
            }
         }
      } else {
         var4 = false;
      }

      return var4;
   }

   public SearchTerm[] getTerms() {
      return (SearchTerm[])((SearchTerm[])this.terms.clone());
   }

   public int hashCode() {
      int var1 = 0;
      int var2 = var1;

      while(true) {
         int var3 = this.terms.length;
         if(var1 >= var3) {
            return var2;
         }

         int var4 = this.terms[var1].hashCode();
         var2 += var4;
         ++var1;
      }
   }

   public boolean match(Message var1) {
      int var2 = 0;

      boolean var4;
      while(true) {
         int var3 = this.terms.length;
         if(var2 >= var3) {
            var4 = false;
            break;
         }

         if(this.terms[var2].match(var1)) {
            var4 = true;
            break;
         }

         ++var2;
      }

      return var4;
   }
}
