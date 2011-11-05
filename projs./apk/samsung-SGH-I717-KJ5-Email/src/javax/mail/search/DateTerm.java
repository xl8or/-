package javax.mail.search;

import java.util.Date;
import javax.mail.search.ComparisonTerm;

public abstract class DateTerm extends ComparisonTerm {

   protected Date date;


   protected DateTerm(int var1, Date var2) {
      this.comparison = var1;
      this.date = var2;
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof DateTerm) {
         Date var2 = ((DateTerm)var1).date;
         Date var3 = this.date;
         if(var2.equals(var3) && super.equals(var1)) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public int getComparison() {
      return this.comparison;
   }

   public Date getDate() {
      long var1 = this.date.getTime();
      return new Date(var1);
   }

   public int hashCode() {
      int var1 = this.date.hashCode();
      int var2 = super.hashCode();
      return var1 + var2;
   }

   protected boolean match(Date var1) {
      boolean var2;
      switch(this.comparison) {
      case 1:
         Date var3 = this.date;
         if(!var1.before(var3)) {
            Date var4 = this.date;
            if(!var1.equals(var4)) {
               var2 = false;
               break;
            }
         }

         var2 = true;
         break;
      case 2:
         Date var5 = this.date;
         var2 = var1.before(var5);
         break;
      case 3:
         Date var6 = this.date;
         var2 = var1.equals(var6);
         break;
      case 4:
         Date var7 = this.date;
         if(!var1.equals(var7)) {
            var2 = true;
         } else {
            var2 = false;
         }
         break;
      case 5:
         Date var8 = this.date;
         var2 = var1.after(var8);
         break;
      case 6:
         Date var9 = this.date;
         if(!var1.after(var9)) {
            Date var10 = this.date;
            if(!var1.equals(var10)) {
               var2 = false;
               break;
            }
         }

         var2 = true;
         break;
      default:
         var2 = false;
      }

      return var2;
   }
}
