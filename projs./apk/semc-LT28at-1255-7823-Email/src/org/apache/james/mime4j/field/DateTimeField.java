package org.apache.james.mime4j.field;

import com.android.email.Utility;
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
         String var6 = Utility.cleanUpMimeDate(var2);

         Date var7;
         try {
            var7 = DateTime.parse(var6).getDate();
         } catch (ParseException var16) {
            if(log.isDebugEnabled()) {
               Log var12 = log;
               StringBuilder var13 = (new StringBuilder()).append("Parsing value \'").append(var6).append("\': ");
               String var14 = var16.getMessage();
               String var15 = var13.append(var14).toString();
               var12.debug(var15);
            }

            var5 = var16;
            return new DateTimeField(var1, var6, var3, var4, var5);
         }

         var4 = var7;
         return new DateTimeField(var1, var6, var3, var4, var5);
      }
   }
}
