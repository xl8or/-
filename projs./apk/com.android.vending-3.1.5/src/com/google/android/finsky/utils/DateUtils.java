package com.google.android.finsky.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DateUtils {

   private static final DateFormat DISPLAY_DATE_FORMAT = DateFormat.getDateInstance(1);
   private static final DateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


   public DateUtils() {}

   public static String formatDisplayDate(Date var0) {
      synchronized(DateUtils.class){}
      boolean var5 = false;

      String var1;
      try {
         var5 = true;
         var1 = DISPLAY_DATE_FORMAT.format(var0);
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   public static String formatIso8601Date(String var0) throws ParseException {
      synchronized(DateUtils.class){}
      boolean var6 = false;

      label43: {
         String var1;
         try {
            var6 = true;
            var1 = formatDisplayDate(ISO8601_DATE_FORMAT.parse(var0));
            var6 = false;
         } catch (ParseException var7) {
            if(!Pattern.matches("^\\d\\d\\d\\d$", var0)) {
               throw var7;
            }

            var6 = false;
            break label43;
         } finally {
            if(var6) {
               ;
            }
         }

         var0 = var1;
      }

      return var0;
   }
}
