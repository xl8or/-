package javax.mail.search;

import javax.mail.search.SearchTerm;

public abstract class ComparisonTerm extends SearchTerm {

   public static final int EQ = 3;
   public static final int GE = 6;
   public static final int GT = 5;
   public static final int LE = 1;
   public static final int LT = 2;
   public static final int NE = 4;
   protected int comparison;


   public ComparisonTerm() {}

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof ComparisonTerm) {
         int var2 = ((ComparisonTerm)var1).comparison;
         int var3 = this.comparison;
         if(var2 == var3) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public int hashCode() {
      return this.comparison;
   }
}
