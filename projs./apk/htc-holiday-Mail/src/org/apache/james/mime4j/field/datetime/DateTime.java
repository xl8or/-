package org.apache.james.mime4j.field.datetime;

import java.io.PrintStream;
import java.io.StringReader;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.james.mime4j.field.datetime.parser.DateTimeParser;
import org.apache.james.mime4j.field.datetime.parser.ParseException;
import org.apache.james.mime4j.field.datetime.parser.TokenMgrError;

public class DateTime {

   private final Date date;
   private final int day;
   private final int hour;
   private final int minute;
   private final int month;
   private final int second;
   private final int timeZone;
   private final int year;


   public DateTime(String var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = this.convertToYear(var1);
      this.year = var8;
      int var9 = this.year;
      Date var16 = convertToDate(var9, var2, var3, var4, var5, var6, var7);
      this.date = var16;
      this.month = var2;
      this.day = var3;
      this.hour = var4;
      this.minute = var5;
      this.second = var6;
      this.timeZone = var7;
   }

   public static Date convertToDate(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      TimeZone var7 = TimeZone.getTimeZone("GMT+0");
      GregorianCalendar var8 = new GregorianCalendar(var7);
      int var9 = var1 - 1;
      var8.set(var0, var9, var2, var3, var4, var5);
      var8.set(14, 0);
      if(var6 != Integer.MIN_VALUE) {
         int var15 = var6 / 100 * 60;
         int var16 = var6 % 100;
         int var17 = (var15 + var16) * -1;
         var8.add(12, var17);
      }

      return var8.getTime();
   }

   private int convertToYear(String var1) {
      int var2 = Integer.parseInt(var1);
      int var3;
      switch(var1.length()) {
      case 1:
      case 2:
         if(var2 >= 0 && var2 < 50) {
            var3 = var2 + 2000;
         } else {
            var3 = var2 + 1900;
         }
         break;
      case 3:
         var3 = var2 + 1900;
         break;
      default:
         var3 = var2;
      }

      return var3;
   }

   public static DateTime parse(String var0) throws ParseException {
      try {
         StringReader var1 = new StringReader(var0);
         DateTime var2 = (new DateTimeParser(var1)).parseAll();
         return var2;
      } catch (TokenMgrError var4) {
         String var3 = var4.getMessage();
         throw new ParseException(var3);
      }
   }

   public Date getDate() {
      return this.date;
   }

   public int getDay() {
      return this.day;
   }

   public int getHour() {
      return this.hour;
   }

   public int getMinute() {
      return this.minute;
   }

   public int getMonth() {
      return this.month;
   }

   public int getSecond() {
      return this.second;
   }

   public int getTimeZone() {
      return this.timeZone;
   }

   public int getYear() {
      return this.year;
   }

   public void print() {
      PrintStream var1 = System.out;
      StringBuilder var2 = new StringBuilder();
      int var3 = this.getYear();
      StringBuilder var4 = var2.append(var3).append(" ");
      int var5 = this.getMonth();
      StringBuilder var6 = var4.append(var5).append(" ");
      int var7 = this.getDay();
      StringBuilder var8 = var6.append(var7).append("; ");
      int var9 = this.getHour();
      StringBuilder var10 = var8.append(var9).append(" ");
      int var11 = this.getMinute();
      StringBuilder var12 = var10.append(var11).append(" ");
      int var13 = this.getSecond();
      StringBuilder var14 = var12.append(var13).append(" ");
      int var15 = this.getTimeZone();
      String var16 = var14.append(var15).toString();
      var1.println(var16);
   }
}
