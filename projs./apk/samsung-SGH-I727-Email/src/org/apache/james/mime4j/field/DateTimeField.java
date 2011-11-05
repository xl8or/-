package org.apache.james.mime4j.field;

import java.util.Date;
import org.apache.james.mime4j.Log;
import org.apache.james.mime4j.LogFactory;
import org.apache.james.mime4j.field.Field;
import org.apache.james.mime4j.field.FieldParser;
import org.apache.james.mime4j.field.datetime.DateTime;
import org.apache.james.mime4j.field.datetime.parser.ParseException;

public class DateTimeField extends Field {

   private Date date;
   private ParseException parseException;


   protected DateTimeField(String var1, String var2, String var3, Date var4, ParseException var5) {
      super(var1, var2, var3);
      this.date = var4;
      this.parseException = var5;
   }

   public Date getDate() {
      return this.date;
   }

   public ParseException getParseException() {
      return this.parseException;
   }

   public static class Parser implements FieldParser {

      private static Log log = LogFactory.getLog(DateTimeField.Parser.class);


      public Parser() {}

      public Field parse(String var1, String var2, String var3) {
         Date var4 = null;
         ParseException var5 = null;

         Date var6;
         try {
            var6 = DateTime.parse(var2).getDate();
         } catch (ParseException var15) {
            if(log.isDebugEnabled()) {
               Log var11 = log;
               StringBuilder var12 = (new StringBuilder()).append("Parsing value \'").append(var2).append("\': ");
               String var13 = var15.getMessage();
               String var14 = var12.append(var13).toString();
               var11.debug(var14);
            }

            var5 = var15;
            return new DateTimeField(var1, var2, var3, var4, var5);
         }

         var4 = var6;
         return new DateTimeField(var1, var2, var3, var4, var5);
      }
   }
}
