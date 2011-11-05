package org.apache.james.mime4j.field.address;

import java.io.PrintStream;
import java.util.ArrayList;
import org.apache.james.mime4j.field.address.Mailbox;

public class MailboxList {

   private ArrayList mailboxes;


   public MailboxList(ArrayList var1, boolean var2) {
      if(var1 != null) {
         ArrayList var3;
         if(var2) {
            var3 = var1;
         } else {
            var3 = (ArrayList)var1.clone();
         }

         this.mailboxes = var3;
      } else {
         ArrayList var4 = new ArrayList(0);
         this.mailboxes = var4;
      }
   }

   public Mailbox get(int var1) {
      if(var1 >= 0 && this.size() > var1) {
         return (Mailbox)this.mailboxes.get(var1);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void print() {
      int var1 = 0;

      while(true) {
         int var2 = this.size();
         if(var1 >= var2) {
            return;
         }

         Mailbox var3 = this.get(var1);
         PrintStream var4 = System.out;
         String var5 = var3.toString();
         var4.println(var5);
         ++var1;
      }
   }

   public int size() {
      return this.mailboxes.size();
   }
}
