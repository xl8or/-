package javax.mail.search;

import javax.mail.search.ComparisonTerm;

public abstract class IntegerComparisonTerm extends ComparisonTerm {

   protected int number;


   protected IntegerComparisonTerm(int var1, int var2) {
      this.comparison = var1;
      this.number = var2;
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof IntegerComparisonTerm) {
         int var2 = ((IntegerComparisonTerm)var1).number;
         int var3 = this.number;
         if(var2 == var3 && super.equals(var1)) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public int getComparison() {
      return super.comparison;
   }

   public int getNumber() {
      return this.number;
   }

   public int hashCode() {
      int var1 = this.number;
      int var2 = super.hashCode();
      return var1 + var2;
   }

   protected boolean match(int var1) {
      boolean var2;
      switch(this.comparison) {
      case 1:
         int var3 = this.number;
         if(var1 <= var3) {
            var2 = true;
         } else {
            var2 = false;
         }
         break;
      case 2:
         int var4 = this.number;
         if(var1 < var4) {
            var2 = true;
         } else {
            var2 = false;
         }
         break;
      case 3:
         int var5 = this.number;
         if(var1 == var5) {
            var2 = true;
         } else {
            var2 = false;
         }
         break;
      case 4:
         int var6 = this.number;
         if(var1 != var6) {
            var2 = true;
         } else {
            var2 = false;
         }
         break;
      case 5:
         int var7 = this.number;
         if(var1 > var7) {
            var2 = true;
         } else {
            var2 = false;
         }
         break;
      case 6:
         int var8 = this.number;
         if(var1 >= var8) {
            var2 = true;
         } else {
            var2 = false;
         }
         break;
      default:
         var2 = false;
      }

      return var2;
   }
}
