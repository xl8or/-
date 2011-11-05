package javax.mail.search;

import javax.mail.Address;
import javax.mail.search.SearchTerm;

public abstract class AddressTerm extends SearchTerm {

   protected Address address;


   protected AddressTerm(Address var1) {
      this.address = var1;
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof AddressTerm) {
         Address var2 = ((AddressTerm)var1).address;
         Address var3 = this.address;
         if(var2.equals(var3)) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public Address getAddress() {
      return this.address;
   }

   public int hashCode() {
      return this.address.hashCode();
   }

   protected boolean match(Address var1) {
      Address var2 = this.address;
      return var1.equals(var2);
   }
}
