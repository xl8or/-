package gnu.inet.imap;

import java.util.List;

public final class Pair {

   private String key;
   private List value;


   Pair(String var1, List var2) {
      this.key = var1;
      this.value = var2;
   }

   public String getKey() {
      return this.key;
   }

   public List getValue() {
      return this.value;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.key;
      StringBuilder var3 = var1.append(var2);
      List var4 = this.value;
      return var3.append(var4).toString();
   }
}
