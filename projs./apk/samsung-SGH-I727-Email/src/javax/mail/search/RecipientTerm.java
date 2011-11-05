package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.search.AddressTerm;

public final class RecipientTerm extends AddressTerm {

   protected Message.RecipientType type;


   public RecipientTerm(Message.RecipientType var1, Address var2) {
      super(var2);
      this.type = var1;
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof RecipientTerm) {
         Message.RecipientType var2 = ((RecipientTerm)var1).type;
         Message.RecipientType var3 = this.type;
         if(var2.equals(var3) && super.equals(var1)) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public Message.RecipientType getRecipientType() {
      return this.type;
   }

   public int hashCode() {
      int var1 = this.type.hashCode();
      int var2 = super.hashCode();
      return var1 + var2;
   }

   public boolean match(Message param1) {
      // $FF: Couldn't be decompiled
   }
}
