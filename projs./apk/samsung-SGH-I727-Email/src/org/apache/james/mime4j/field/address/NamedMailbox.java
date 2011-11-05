package org.apache.james.mime4j.field.address;

import org.apache.james.mime4j.field.address.DomainList;
import org.apache.james.mime4j.field.address.Mailbox;

public class NamedMailbox extends Mailbox {

   private String name;


   public NamedMailbox(String var1, String var2, String var3) {
      super(var2, var3);
      this.name = var1;
   }

   public NamedMailbox(String var1, DomainList var2, String var3, String var4) {
      super(var2, var3, var4);
      this.name = var1;
   }

   public NamedMailbox(String var1, Mailbox var2) {
      DomainList var3 = var2.getRoute();
      String var4 = var2.getLocalPart();
      String var5 = var2.getDomain();
      super(var3, var4, var5);
      this.name = var1;
   }

   public String getAddressString(boolean var1) {
      StringBuilder var2 = new StringBuilder();
      String var3;
      if(this.name == null) {
         var3 = "";
      } else {
         StringBuilder var6 = new StringBuilder();
         String var7 = this.name;
         var3 = var6.append(var7).append(" ").toString();
      }

      StringBuilder var4 = var2.append(var3);
      String var5 = super.getAddressString(var1);
      return var4.append(var5).toString();
   }

   public String getName() {
      return this.name;
   }
}
