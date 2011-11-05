package org.apache.james.mime4j.field.address;

import java.util.ArrayList;
import org.apache.james.mime4j.field.address.Address;
import org.apache.james.mime4j.field.address.Mailbox;
import org.apache.james.mime4j.field.address.MailboxList;

public class Group extends Address {

   private MailboxList mailboxList;
   private String name;


   public Group(String var1, MailboxList var2) {
      this.name = var1;
      this.mailboxList = var2;
   }

   protected void doAddMailboxesTo(ArrayList var1) {
      int var2 = 0;

      while(true) {
         int var3 = this.mailboxList.size();
         if(var2 >= var3) {
            return;
         }

         Mailbox var4 = this.mailboxList.get(var2);
         var1.add(var4);
         ++var2;
      }
   }

   public MailboxList getMailboxes() {
      return this.mailboxList;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = this.name;
      var1.append(var2);
      StringBuffer var4 = var1.append(":");
      int var5 = 0;

      while(true) {
         int var6 = this.mailboxList.size();
         if(var5 >= var6) {
            StringBuffer var12 = var1.append(";");
            return var1.toString();
         }

         String var7 = this.mailboxList.get(var5).toString();
         var1.append(var7);
         int var9 = var5 + 1;
         int var10 = this.mailboxList.size();
         if(var9 < var10) {
            StringBuffer var11 = var1.append(",");
         }

         ++var5;
      }
   }
}
