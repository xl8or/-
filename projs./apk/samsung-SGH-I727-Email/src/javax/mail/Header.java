package javax.mail;


public class Header {

   String name;
   String value;


   public Header(String var1, String var2) {
      this.name = var1;
      this.value = var2;
   }

   public String getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }
}
