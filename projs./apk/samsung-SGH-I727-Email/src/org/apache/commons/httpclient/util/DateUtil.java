package org.apache.commons.httpclient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.httpclient.util.DateParseException;

public class DateUtil {

   private static final Collection DEFAULT_PATTERNS;
   private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
   private static final TimeZone GMT;
   public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
   public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
   public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";


   static {
      String[] var0 = new String[]{"EEE MMM d HH:mm:ss yyyy", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE, dd MMM yyyy HH:mm:ss zzz"};
      DEFAULT_PATTERNS = Arrays.asList(var0);
      Calendar var1 = Calendar.getInstance();
      byte var2 = 0;
      byte var3 = 0;
      var1.set(2000, 0, 1, var2, var3);
      DEFAULT_TWO_DIGIT_YEAR_START = var1.getTime();
      GMT = TimeZone.getTimeZone("GMT");
   }

   private DateUtil() {}

   public static String formatDate(Date var0) {
      return formatDate(var0, "EEE, dd MMM yyyy HH:mm:ss zzz");
   }

   public static String formatDate(Date var0, String var1) {
      if(var0 == null) {
         throw new IllegalArgumentException("date is null");
      } else if(var1 == null) {
         throw new IllegalArgumentException("pattern is null");
      } else {
         Locale var2 = Locale.US;
         SimpleDateFormat var3 = new SimpleDateFormat(var1, var2);
         TimeZone var4 = GMT;
         var3.setTimeZone(var4);
         return var3.format(var0);
      }
   }

   public static Date parseDate(String var0) throws DateParseException {
      return parseDate(var0, (Collection)null, (Date)null);
   }

   public static Date parseDate(String var0, Collection var1) throws DateParseException {
      return parseDate(var0, var1, (Date)null);
   }

   public static Date parseDate(String var0, Collection var1, Date var2) throws DateParseException {
      if(var0 == null) {
         throw new IllegalArgumentException("dateValue is null");
      } else {
         if(var1 == null) {
            var1 = DEFAULT_PATTERNS;
         }

         if(var2 == null) {
            var2 = DEFAULT_TWO_DIGIT_YEAR_START;
         }

         if(var0.length() > 1 && var0.startsWith("\'") && var0.endsWith("\'")) {
            int var3 = var0.length() - 1;
            var0 = var0.substring(1, var3);
         }

         SimpleDateFormat var4 = null;
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            if(var4 == null) {
               Locale var7 = Locale.US;
               var4 = new SimpleDateFormat(var6, var7);
               TimeZone var8 = TimeZone.getTimeZone("GMT");
               var4.setTimeZone(var8);
               var4.set2DigitYearStart(var2);
            } else {
               var4.applyPattern(var6);
            }

            try {
               Date var9 = var4.parse(var0);
               return var9;
            } catch (ParseException var12) {
               ;
            }
         }

         String var10 = "Unable to parse the date " + var0;
         throw new DateParseException(var10);
      }
   }
}
