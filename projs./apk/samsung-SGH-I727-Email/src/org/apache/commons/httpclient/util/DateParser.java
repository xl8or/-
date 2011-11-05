package org.apache.commons.httpclient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.httpclient.util.DateParseException;

public class DateParser {

   private static final Collection DEFAULT_PATTERNS;
   public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
   public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
   public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";


   static {
      String[] var0 = new String[]{"EEE MMM d HH:mm:ss yyyy", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE, dd MMM yyyy HH:mm:ss zzz"};
      DEFAULT_PATTERNS = Arrays.asList(var0);
   }

   private DateParser() {}

   public static Date parseDate(String var0) throws DateParseException {
      return parseDate(var0, (Collection)null);
   }

   public static Date parseDate(String var0, Collection var1) throws DateParseException {
      if(var0 == null) {
         throw new IllegalArgumentException("dateValue is null");
      } else {
         if(var1 == null) {
            var1 = DEFAULT_PATTERNS;
         }

         if(var0.length() > 1 && var0.startsWith("\'") && var0.endsWith("\'")) {
            int var2 = var0.length() - 1;
            var0 = var0.substring(1, var2);
         }

         SimpleDateFormat var3 = null;
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            if(var3 == null) {
               Locale var6 = Locale.US;
               var3 = new SimpleDateFormat(var5, var6);
               TimeZone var7 = TimeZone.getTimeZone("GMT");
               var3.setTimeZone(var7);
            } else {
               var3.applyPattern(var5);
            }

            try {
               Date var8 = var3.parse(var0);
               return var8;
            } catch (ParseException var11) {
               ;
            }
         }

         String var9 = "Unable to parse the date " + var0;
         throw new DateParseException(var9);
      }
   }
}
