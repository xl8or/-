package org.apache.james.mime4j.field;

import org.apache.james.mime4j.Log;
import org.apache.james.mime4j.LogFactory;
import org.apache.james.mime4j.field.Field;
import org.apache.james.mime4j.field.FieldParser;
import org.apache.james.mime4j.field.address.AddressList;
import org.apache.james.mime4j.field.address.parser.ParseException;

public class AddressListField extends Field {

   private AddressList addressList;
   private ParseException parseException;


   protected AddressListField(String var1, String var2, String var3, AddressList var4, ParseException var5) {
      super(var1, var2, var3);
      this.addressList = var4;
      this.parseException = var5;
   }

   public AddressList getAddressList() {
      return this.addressList;
   }

   public ParseException getParseException() {
      return this.parseException;
   }

   public static class Parser implements FieldParser {

      private static Log log = LogFactory.getLog(AddressListField.Parser.class);


      public Parser() {}

      public Field parse(String var1, String var2, String var3) {
         AddressList var4 = null;
         ParseException var5 = null;

         AddressList var6;
         try {
            var6 = AddressList.parse(var2);
         } catch (ParseException var15) {
            if(log.isDebugEnabled()) {
               Log var11 = log;
               StringBuilder var12 = (new StringBuilder()).append("Parsing value \'").append(var2).append("\': ");
               String var13 = var15.getMessage();
               String var14 = var12.append(var13).toString();
               var11.debug(var14);
            }

            var5 = var15;
            return new AddressListField(var1, var2, var3, var4, var5);
         }

         var4 = var6;
         return new AddressListField(var1, var2, var3, var4, var5);
      }
   }
}
