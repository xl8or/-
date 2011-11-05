package gnu.inet.nntp;


public final class Pair {

   String key;
   String value;


   Pair(String var1, String var2) {
      this.key = var1;
      this.value = var2;
   }

   public String getKey() {
      return this.key;
   }

   public String getValue() {
      return this.value;
   }
}
