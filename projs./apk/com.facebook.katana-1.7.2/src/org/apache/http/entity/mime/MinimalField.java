package org.apache.http.entity.mime;


public class MinimalField {

   private final String name;
   private final String value;


   MinimalField(String var1, String var2) {
      this.name = var1;
      this.value = var2;
   }

   public String getBody() {
      return this.value;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.name;
      var1.append(var2);
      StringBuilder var4 = var1.append(": ");
      String var5 = this.value;
      var1.append(var5);
      return var1.toString();
   }
}
