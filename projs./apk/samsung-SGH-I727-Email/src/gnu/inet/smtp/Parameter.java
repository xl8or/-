package gnu.inet.smtp;


public final class Parameter {

   final String key;
   final String value;


   public Parameter(String var1, String var2) {
      this.key = var1;
      this.value = var2;
   }

   public String getKey() {
      return this.key;
   }

   public String getValue() {
      return this.value;
   }

   public String toString() {
      String var1;
      if(this.value == null) {
         var1 = this.key;
      } else {
         StringBuilder var2 = new StringBuilder();
         String var3 = this.key;
         StringBuilder var4 = var2.append(var3).append('=');
         String var5 = this.value;
         var1 = var4.append(var5).toString();
      }

      return var1;
   }
}
