package com.htc.android.mail;

import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {

   private static final int DATE = 2;
   private static final boolean DEBUG = false;
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
      DEBUG = Mail.MAIL_DEBUG;
      sPattern = Pattern.compile("(?:(Mon|Tue|Wed|Thu|Fri|Sat|Sun),\\s*)?(\\d{1,2})\\s*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s*(\\d{2}|\\d{4})\\s*(\\d{1,2}):(\\d{2})(?::(\\d{2}))?\\s*(UT|GMT|EST|EDT|CST|CDT|MST|MDT|PST|PDT|[A-IK-Z]|(?:(\\+|\\-)(\\d{4}))).*");
      sPattern2 = Pattern.compile("(\\d{1,2})-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-(\\d{2}|\\d{4})\\s*(\\d{1,2}):(\\d{2})(?::(\\d{2}))?\\s*(UT|GMT|EST|EDT|CST|CDT|MST|MDT|PST|PDT|[A-IK-Z]|(?:(\\+|\\-)(\\d{4})))");
   }

   public DateParser() {}

   public static Calendar parse(String var0) {
      Calendar var1;
      if(var0 == null) {
         if(DEBUG) {
            ll.d("DateParser", "### date is null");
         }

         var1 = Calendar.getInstance();
      } else {
         Pattern var2 = sPattern;
         String var3 = var0.trim();
         Matcher var4 = var2.matcher(var3);
         if(!var4.matches()) {
            if(DEBUG) {
               StringBuilder var5 = (new StringBuilder()).append("### none match (parse) date is ");
               String var6 = var0.trim();
               String var7 = var5.append(var6).toString();
               ll.d("DateParser", var7);
            }

            var1 = parseInternalDate(var0);
         } else {
            String var8 = var4.group(8);
            char var9 = var8.charAt(0);
            if(var9 == 43 || var9 == 45) {
               var8 = "GMT" + var8;
            }

            Calendar var10 = Calendar.getInstance(TimeZone.getTimeZone(translateToGMT(var8)));
            String var11 = var4.group(4);
            if(var11.length() == 2) {
               var11 = "19" + var11;
            }

            setField(var10, 1, var11);
            String var12 = var4.group(3);
            setMonth(var10, var12);
            String var13 = var4.group(2);
            setField(var10, 5, var13);
            String var14 = var4.group(5);
            setField(var10, 11, var14);
            String var15 = var4.group(6);
            setField(var10, 12, var15);
            String var16 = var4.group(7);
            setField(var10, 13, var16);
            var1 = var10;
         }
      }

      return var1;
   }

   public static Calendar parseDateWithTimeClass(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static Calendar parseInternalDate(String var0) {
      Calendar var1;
      if(var0 == null) {
         if(DEBUG) {
            ll.d("DateParser", "### internal date is null");
         }

         var1 = Calendar.getInstance();
      } else {
         Pattern var2 = sPattern2;
         String var3 = var0.trim();
         Matcher var4 = var2.matcher(var3);
         if(!var4.matches()) {
            if(DEBUG) {
               String var5 = "### none match date (parseInternalDate) is " + var0;
               ll.d("DateParser", var5);
            }

            var1 = parseDateWithTimeClass(var0);
         } else {
            String var6 = var4.group(7);
            char var7 = var6.charAt(0);
            if(var7 == 43 || var7 == 45) {
               var6 = "GMT" + var6;
            }

            if(DEBUG) {
               StringBuilder var8 = (new StringBuilder()).append("************ parseInternalDate match date is ").append(var0).append(", gmt value:");
               String var9 = translateToGMT(var6);
               String var10 = var8.append(var9).toString();
               ll.d("ROY", var10);
            }

            Calendar var11 = Calendar.getInstance(TimeZone.getTimeZone(translateToGMT(var6)));
            String var12 = var4.group(3);
            if(var12.length() == 2) {
               var12 = "19" + var12;
            }

            setField(var11, 1, var12);
            String var13 = var4.group(2);
            setMonth(var11, var13);
            String var14 = var4.group(1);
            setField(var11, 5, var14);
            String var15 = var4.group(4);
            setField(var11, 11, var15);
            String var16 = var4.group(5);
            setField(var11, 12, var16);
            String var17 = var4.group(6);
            setField(var11, 13, var17);
            var1 = var11;
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

   public static String translateToGMT(String var0) {
      String var1;
      if(var0 == null) {
         var1 = var0;
      } else if(var0.equals("UT")) {
         var1 = "GMT+0000";
      } else if(var0.equals("EST")) {
         var1 = "GMT-0500";
      } else if(var0.equals("EDT")) {
         var1 = "GMT-0400";
      } else if(var0.equals("CST")) {
         var1 = "GMT-0600";
      } else if(var0.equals("CDT")) {
         var1 = "GMT-0500";
      } else if(var0.equals("MST")) {
         var1 = "GMT-0700";
      } else if(var0.equals("MDT")) {
         var1 = "GMT-0600";
      } else if(var0.equals("PST")) {
         var1 = "GMT-0800";
      } else if(var0.equals("PDT")) {
         var1 = "GMT-0700";
      } else {
         var1 = var0;
      }

      return var1;
   }
}
