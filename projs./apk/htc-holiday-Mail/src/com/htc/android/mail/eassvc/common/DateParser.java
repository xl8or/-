package com.htc.android.mail.eassvc.common;

import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.util.EASLog;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {

   private static final int DATE = 2;
   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final int HOURS = 5;
   private static final int MINUTES = 6;
   private static final int MONTH = 3;
   private static final int SECONDS = 7;
   private static final int TZ = 8;
   private static final int YEAR = 4;
   private static final String[] sMonths;
   private static final Pattern sPattern;
   private static final Pattern sPattern2;


   static {
      String[] var0 = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
      sMonths = var0;
      sPattern = Pattern.compile("(?:(Mon|Tue|Wed|Thu|Fri|Sat|Sun),\\s*)?(\\d{1,2})\\s*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s*(\\d{2}|\\d{4})\\s*(\\d{1,2}):(\\d{2})(?::(\\d{2}))?\\s*(UT|GMT|EST|EDT|CST|CDT|MST|MDT|PST|PDT|[A-IK-Z]|(?:(\\+|\\-)(\\d{4}))).*");
      sPattern2 = Pattern.compile("(\\d{1,2})-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-(\\d{2}|\\d{4})\\s*(\\d{1,2}):(\\d{2})(?::(\\d{2}))?\\s*(UT|GMT|EST|EDT|CST|CDT|MST|MDT|PST|PDT|[A-IK-Z]|(?:(\\+|\\-)(\\d{4})))");
   }

   public DateParser() {}

   public static Calendar parse(String var0) {
      Calendar var1;
      if(var0 == null) {
         if(DEBUG) {
            EASLog.d("DateParser", "### date is null");
         }

         var1 = Calendar.getInstance();
      } else {
         Pattern var2 = sPattern;
         String var3 = var0.trim();
         Matcher var4 = var2.matcher(var3);
         if(!var4.matches()) {
            var1 = parseInternalDate(var0);
         } else {
            String var5 = var4.group(8);
            char var6 = var5.charAt(0);
            if(var6 == 43 || var6 == 45) {
               var5 = "GMT" + var5;
            }

            Calendar var7 = Calendar.getInstance(TimeZone.getTimeZone(var5));
            String var8 = var4.group(4);
            if(var8.length() == 2) {
               var8 = "19" + var8;
            }

            setField(var7, 1, var8);
            String var9 = var4.group(3);
            setMonth(var7, var9);
            String var10 = var4.group(2);
            setField(var7, 5, var10);
            String var11 = var4.group(5);
            setField(var7, 11, var11);
            String var12 = var4.group(6);
            setField(var7, 12, var12);
            String var13 = var4.group(7);
            setField(var7, 13, var13);
            var1 = var7;
         }
      }

      return var1;
   }

   public static Calendar parseInternalDate(String var0) {
      Calendar var1;
      if(var0 == null) {
         if(DEBUG) {
            EASLog.d("DateParser", "### internal date is null");
         }

         var1 = Calendar.getInstance();
      } else {
         Pattern var2 = sPattern2;
         String var3 = var0.trim();
         Matcher var4 = var2.matcher(var3);
         if(!var4.matches()) {
            if(DEBUG) {
               String var5 = "### none match date (parseInternalDate) is " + var0;
               EASLog.d("DateParser", var5);
            }

            var1 = Calendar.getInstance();
         } else {
            String var6 = var4.group(7);
            char var7 = var6.charAt(0);
            if(var7 == 43 || var7 == 45) {
               var6 = "GMT" + var6;
            }

            Calendar var8 = Calendar.getInstance(TimeZone.getTimeZone(var6));
            String var9 = var4.group(3);
            if(var9.length() == 2) {
               var9 = "19" + var9;
            }

            setField(var8, 1, var9);
            String var10 = var4.group(2);
            setMonth(var8, var10);
            String var11 = var4.group(1);
            setField(var8, 5, var11);
            String var12 = var4.group(4);
            setField(var8, 11, var12);
            String var13 = var4.group(5);
            setField(var8, 12, var13);
            String var14 = var4.group(6);
            setField(var8, 13, var14);
            var1 = var8;
         }
      }

      return var1;
   }

   private static final void setField(Calendar var0, int var1, String var2) {
      int var3 = var0.getMinimum(var1);
      int var4 = var0.getMaximum(var1);
      int var5;
      if(var2 == null) {
         var5 = 0;
      } else {
         var5 = Integer.parseInt(var2);
      }

      if(var5 < var3 || var5 > var4) {
         var5 = var3;
      }

      var0.set(var1, var5);
   }

   private static final void setMonth(Calendar var0, String var1) {
      int var2 = 0;

      while(true) {
         int var3 = sMonths.length;
         if(var2 >= var3 || sMonths[var2].equals(var1)) {
            int var4 = var2 + 0;
            var0.set(2, var4);
            return;
         }

         ++var2;
      }
   }
}
