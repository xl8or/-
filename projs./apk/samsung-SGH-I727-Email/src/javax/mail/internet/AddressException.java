package javax.mail.internet;

import javax.mail.internet.ParseException;

public class AddressException extends ParseException {

   protected int pos;
   protected String ref;


   public AddressException() {
      this((String)null, (String)null, -1);
   }

   public AddressException(String var1) {
      this(var1, (String)null, -1);
   }

   public AddressException(String var1, String var2) {
      this(var1, var2, -1);
   }

   public AddressException(String var1, String var2, int var3) {
      super(var1);
      this.ref = var2;
      this.pos = var3;
   }

   public int getPos() {
      return this.pos;
   }

   public String getRef() {
      return this.ref;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = super.toString();
      var1.append(var2);
      if(this.ref != null) {
         StringBuffer var4 = var1.append(" in string ");
         String var5 = this.ref;
         var1.append(var5);
         if(this.pos > -1) {
            StringBuffer var7 = var1.append(" at position ");
            int var8 = this.pos;
            var1.append(var8);
         }
      }

      return var1.toString();
   }
}
