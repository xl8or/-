package org.apache.james.mime4j.field.address;

import java.util.ArrayList;

public class DomainList {

   private ArrayList domains;


   public DomainList(ArrayList var1, boolean var2) {
      if(var1 != null) {
         ArrayList var3;
         if(var2) {
            var3 = var1;
         } else {
            var3 = (ArrayList)var1.clone();
         }

         this.domains = var3;
      } else {
         ArrayList var4 = new ArrayList(0);
         this.domains = var4;
      }
   }

   public String get(int var1) {
      if(var1 >= 0 && this.size() > var1) {
         return (String)this.domains.get(var1);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int size() {
      return this.domains.size();
   }

   public String toRouteString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = 0;

      while(true) {
         int var3 = this.domains.size();
         if(var2 >= var3) {
            return var1.toString();
         }

         StringBuffer var4 = var1.append("@");
         String var5 = this.get(var2);
         var1.append(var5);
         int var7 = var2 + 1;
         int var8 = this.domains.size();
         if(var7 < var8) {
            StringBuffer var9 = var1.append(",");
         }

         ++var2;
      }
   }
}
