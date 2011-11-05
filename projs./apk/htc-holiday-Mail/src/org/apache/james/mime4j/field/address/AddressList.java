package org.apache.james.mime4j.field.address;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import org.apache.james.mime4j.field.address.Address;
import org.apache.james.mime4j.field.address.Builder;
import org.apache.james.mime4j.field.address.Mailbox;
import org.apache.james.mime4j.field.address.MailboxList;
import org.apache.james.mime4j.field.address.parser.ASTaddress_list;
import org.apache.james.mime4j.field.address.parser.AddressListParser;
import org.apache.james.mime4j.field.address.parser.ParseException;

public class AddressList {

   private ArrayList addresses;


   public AddressList(ArrayList var1, boolean var2) {
      if(var1 != null) {
         ArrayList var3;
         if(var2) {
            var3 = var1;
         } else {
            var3 = (ArrayList)var1.clone();
         }

         this.addresses = var3;
      } else {
         ArrayList var4 = new ArrayList(0);
         this.addresses = var4;
      }
   }

   public static void main(String[] var0) throws Exception {
      InputStream var1 = System.in;
      InputStreamReader var2 = new InputStreamReader(var1);
      BufferedReader var3 = new BufferedReader(var2);

      while(true) {
         try {
            while(true) {
               System.out.print("> ");
               String var4 = var3.readLine();
               if(var4.length() == 0 || var4.toLowerCase().equals("exit") || var4.toLowerCase().equals("quit")) {
                  System.out.println("Goodbye.");
                  return;
               }

               parse(var4).print();
            }
         } catch (Exception var5) {
            var5.printStackTrace();
            Thread.sleep(300L);
         }
      }
   }

   public static AddressList parse(String var0) throws ParseException {
      StringReader var1 = new StringReader(var0);
      AddressListParser var2 = new AddressListParser(var1);
      Builder var3 = Builder.getInstance();
      ASTaddress_list var4 = var2.parse();
      return var3.buildAddressList(var4);
   }

   public MailboxList flatten() {
      boolean var1 = false;
      int var2 = 0;

      while(true) {
         int var3 = this.size();
         if(var2 >= var3) {
            break;
         }

         if(!(this.get(var2) instanceof Mailbox)) {
            var1 = true;
            break;
         }

         ++var2;
      }

      MailboxList var5;
      if(!var1) {
         ArrayList var4 = this.addresses;
         var5 = new MailboxList(var4, (boolean)1);
      } else {
         ArrayList var6 = new ArrayList();
         byte var9 = 0;

         while(true) {
            int var7 = this.size();
            if(var9 >= var7) {
               var5 = new MailboxList(var6, (boolean)0);
               break;
            }

            this.get(var9).addMailboxesTo(var6);
            int var8 = var9 + 1;
         }
      }

      return var5;
   }

   public Address get(int var1) {
      if(var1 >= 0 && this.size() > var1) {
         return (Address)this.addresses.get(var1);
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

         Address var3 = this.get(var1);
         PrintStream var4 = System.out;
         String var5 = var3.toString();
         var4.println(var5);
         ++var1;
      }
   }

   public int size() {
      return this.addresses.size();
   }
}
