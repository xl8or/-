package javax.mail.internet;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class MailDateFormat extends SimpleDateFormat {

   private static final String[] DAYS_OF_WEEK;
   private static final String[] MONTHS;


   static {
      String[] var0 = new String[]{false, "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
      DAYS_OF_WEEK = var0;
      String[] var1 = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
      MONTHS = var1;
   }

   public MailDateFormat() {
      TimeZone var1 = TimeZone.getTimeZone("GMT");
      GregorianCalendar var2 = new GregorianCalendar(var1);
      this.calendar = var2;
      DecimalFormat var3 = new DecimalFormat();
      this.numberFormat = var3;
   }

   private int skipNonWhitespace(String var1, int var2, int var3) {
      int var4;
      for(var4 = var2; var4 < var3 && !Character.isWhitespace(var1.charAt(var4)); ++var4) {
         ;
      }

      return var4;
   }

   private int skipToColon(String var1, int var2, int var3) {
      int var4;
      for(var4 = var2; var4 < var3 && var1.charAt(var4) != 58; ++var4) {
         ;
      }

      return var4;
   }

   private int skipWhitespace(String var1, int var2, int var3) {
      int var4;
      for(var4 = var2; var4 < var3 && Character.isWhitespace(var1.charAt(var4)); ++var4) {
         ;
      }

      return var4;
   }

   public StringBuffer format(Date var1, StringBuffer var2, FieldPosition var3) {
      this.calendar.clear();
      this.calendar.setTime(var1);
      var2.setLength(0);
      String[] var4 = DAYS_OF_WEEK;
      int var5 = this.calendar.get(7);
      String var6 = var4[var5];
      var2.append(var6);
      StringBuffer var8 = var2.append(',');
      StringBuffer var9 = var2.append(' ');
      String var10 = Integer.toString(this.calendar.get(5));
      var2.append(var10);
      StringBuffer var12 = var2.append(' ');
      String[] var13 = MONTHS;
      int var14 = this.calendar.get(2);
      String var15 = var13[var14];
      var2.append(var15);
      StringBuffer var17 = var2.append(' ');
      int var18 = this.calendar.get(1);
      if(var18 < 1000) {
         StringBuffer var19 = var2.append('0');
         if(var18 < 100) {
            StringBuffer var20 = var2.append('0');
            if(var18 < 10) {
               StringBuffer var21 = var2.append('0');
            }
         }
      }

      String var22 = Integer.toString(var18);
      var2.append(var22);
      StringBuffer var24 = var2.append(' ');
      int var25 = this.calendar.get(11);
      char var26 = Character.forDigit(var25 / 10, 10);
      var2.append(var26);
      char var28 = Character.forDigit(var25 % 10, 10);
      var2.append(var28);
      StringBuffer var30 = var2.append(':');
      int var31 = this.calendar.get(12);
      char var32 = Character.forDigit(var31 / 10, 10);
      var2.append(var32);
      char var34 = Character.forDigit(var31 % 10, 10);
      var2.append(var34);
      StringBuffer var36 = var2.append(':');
      int var37 = this.calendar.get(13);
      char var38 = Character.forDigit(var37 / 10, 10);
      var2.append(var38);
      char var40 = Character.forDigit(var37 % 10, 10);
      var2.append(var40);
      StringBuffer var42 = var2.append(' ');
      int var43 = this.calendar.get(15);
      int var44 = this.calendar.get(16);
      int var45 = (var43 + var44) / '\uea60';
      if(var45 < 0) {
         var45 = -var45;
         StringBuffer var46 = var2.append('-');
      } else {
         StringBuffer var58 = var2.append('+');
      }

      int var47 = var45 / 60;
      char var48 = Character.forDigit(var47 / 10, 10);
      var2.append(var48);
      char var50 = Character.forDigit(var47 % 10, 10);
      var2.append(var50);
      int var52 = var45 % 60;
      char var53 = Character.forDigit(var52 / 10, 10);
      var2.append(var53);
      char var55 = Character.forDigit(var52 % 10, 10);
      var2.append(var55);
      var3.setBeginIndex(0);
      int var57 = var2.length();
      var3.setEndIndex(var57);
      return var2;
   }

   public Date parse(String param1, ParsePosition param2) {
      // $FF: Couldn't be decompiled
   }

   public void setCalendar(Calendar var1) {
      throw new UnsupportedOperationException();
   }

   public void setNumberFormat(NumberFormat var1) {
      throw new UnsupportedOperationException();
   }
}
