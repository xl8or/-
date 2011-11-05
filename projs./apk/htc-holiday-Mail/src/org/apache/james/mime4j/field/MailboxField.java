package org.apache.james.mime4j.field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.james.mime4j.field.Field;
import org.apache.james.mime4j.field.FieldParser;
import org.apache.james.mime4j.field.address.AddressList;
import org.apache.james.mime4j.field.address.Mailbox;
import org.apache.james.mime4j.field.address.MailboxList;
import org.apache.james.mime4j.field.address.parser.ParseException;

public class MailboxField extends Field {

   private final Mailbox mailbox;
   private final ParseException parseException;


   protected MailboxField(String var1, String var2, String var3, Mailbox var4, ParseException var5) {
      super(var1, var2, var3);
      this.mailbox = var4;
      this.parseException = var5;
   }

   public Mailbox getMailbox() {
      return this.mailbox;
   }

   public ParseException getParseException() {
      return this.parseException;
   }

   public static class Parser implements FieldParser {

      private static Log log = LogFactory.getLog(MailboxField.Parser.class);


      public Parser() {}

      public Field parse(String var1, String var2, String var3) {
         Mailbox var4 = null;
         ParseException var5 = null;

         Mailbox var7;
         try {
            MailboxList var6 = AddressList.parse(var2).flatten();
            if(var6.size() <= 0) {
               return new MailboxField(var1, var2, var3, var4, var5);
            }

            var7 = var6.get(0);
         } catch (ParseException var16) {
            if(log.isDebugEnabled()) {
               Log var12 = log;
               StringBuilder var13 = (new StringBuilder()).append("Parsing value \'").append(var2).append("\': ");
               String var14 = var16.getMessage();
               String var15 = var13.append(var14).toString();
               var12.debug(var15);
            }

            var5 = var16;
            return new MailboxField(var1, var2, var3, var4, var5);
         }

         var4 = var7;
         return new MailboxField(var1, var2, var3, var4, var5);
      }
   }
}
