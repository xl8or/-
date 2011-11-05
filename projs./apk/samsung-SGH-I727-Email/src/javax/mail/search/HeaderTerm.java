package javax.mail.search;

import javax.mail.Message;
import javax.mail.search.StringTerm;

public final class HeaderTerm extends StringTerm {

   protected String headerName;


   public HeaderTerm(String var1, String var2) {
      super(var2);
      this.headerName = var1;
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof HeaderTerm) {
         HeaderTerm var5 = (HeaderTerm)var1;
         String var2 = var5.headerName;
         String var3 = this.headerName;
         if(var2.equalsIgnoreCase(var3) && super.equals(var5)) {
            var4 = true;
         } else {
            var4 = false;
         }
      } else {
         var4 = false;
      }

      return var4;
   }

   public String getHeaderName() {
      return this.headerName;
   }

   public int hashCode() {
      int var1 = this.headerName.toLowerCase().hashCode();
      int var2 = super.hashCode();
      return var1 + var2;
   }

   public boolean match(Message param1) {
      // $FF: Couldn't be decompiled
   }
}
