package org.apache.james.mime4j.field.address;

import java.util.ArrayList;
import org.apache.james.mime4j.field.address.Address;
import org.apache.james.mime4j.field.address.DomainList;

public class Mailbox extends Address {

   private String domain;
   private String localPart;
   private DomainList route;


   public Mailbox(String var1, String var2) {
      this((DomainList)null, var1, var2);
   }

   public Mailbox(DomainList var1, String var2, String var3) {
      this.route = var1;
      this.localPart = var2;
      this.domain = var3;
   }

   protected final void doAddMailboxesTo(ArrayList var1) {
      var1.add(this);
   }

   public String getAddressString() {
      return this.getAddressString((boolean)0);
   }

   public String getAddressString(boolean var1) {
      StringBuilder var2 = (new StringBuilder()).append("<");
      String var3;
      if(var1 && this.route != null) {
         StringBuilder var10 = new StringBuilder();
         String var11 = this.route.toRouteString();
         var3 = var10.append(var11).append(":").toString();
      } else {
         var3 = "";
      }

      StringBuilder var4 = var2.append(var3);
      String var5 = this.localPart;
      StringBuilder var6 = var4.append(var5);
      String var7;
      if(this.domain == null) {
         var7 = "";
      } else {
         var7 = "@";
      }

      StringBuilder var8 = var6.append(var7);
      String var9 = this.domain;
      return var8.append(var9).append(">").toString();
   }

   public String getDomain() {
      return this.domain;
   }

   public String getLocalPart() {
      return this.localPart;
   }

   public DomainList getRoute() {
      return this.route;
   }

   public String toString() {
      return this.getAddressString();
   }
}
