package gnu.inet.http;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class HTTPDateFormat extends DateFormat {

   static final String[] DAYS_OF_WEEK;
   static final String[] MONTHS;


   static {
      String[] var0 = new String[]{false, "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
      DAYS_OF_WEEK = var0;
      String[] var1 = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
      MONTHS = var1;
   }

   public HTTPDateFormat() {
      TimeZone var1 = TimeZone.getTimeZone("GMT");
      GregorianCalendar var2 = new GregorianCalendar(var1);
      this.calendar = var2;
      DecimalFormat var3 = new DecimalFormat();
      this.numberFormat = var3;
   }

   private int skipNonWhitespace(String var1, int var2) {
      int var3;
      for(var3 = var2; !Character.isWhitespace(var1.charAt(var3)); ++var3) {
         ;
      }

      return var3;
   }

   private int skipTo(String var1, int var2, char var3) {
      int var4;
      for(var4 = var2; var1.charAt(var4) != var3; ++var4) {
         ;
      }

      return var4;
   }

   private int skipWhitespace(String var1, int var2) {
      int var3;
      for(var3 = var2; Character.isWhitespace(var1.charAt(var3)); ++var3) {
         ;
      }

      return var3;
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
      int var10 = this.calendar.get(5);
      char var11 = Character.forDigit(var10 / 10, 10);
      var2.append(var11);
      char var13 = Character.forDigit(var10 % 10, 10);
      var2.append(var13);
      StringBuffer var15 = var2.append(' ');
      String[] var16 = MONTHS;
      int var17 = this.calendar.get(2);
      String var18 = var16[var17];
      var2.append(var18);
      StringBuffer var20 = var2.append(' ');
      int var21 = this.calendar.get(1);
      if(var21 < 1000) {
         StringBuffer var22 = var2.append('0');
         if(var21 < 100) {
            StringBuffer var23 = var2.append('0');
            if(var21 < 10) {
               StringBuffer var24 = var2.append('0');
            }
         }
      }

      String var25 = Integer.toString(var21);
      var2.append(var25);
      StringBuffer var27 = var2.append(' ');
      int var28 = this.calendar.get(11);
      char var29 = Character.forDigit(var28 / 10, 10);
      var2.append(var29);
      char var31 = Character.forDigit(var28 % 10, 10);
      var2.append(var31);
      StringBuffer var33 = var2.append(':');
      int var34 = this.calendar.get(12);
      char var35 = Character.forDigit(var34 / 10, 10);
      var2.append(var35);
      char var37 = Character.forDigit(var34 % 10, 10);
      var2.append(var37);
      StringBuffer var39 = var2.append(':');
      int var40 = this.calendar.get(13);
      char var41 = Character.forDigit(var40 / 10, 10);
      var2.append(var41);
      char var43 = Character.forDigit(var40 % 10, 10);
      var2.append(var43);
      StringBuffer var45 = var2.append(' ');
      int var46 = this.calendar.get(15);
      int var47 = this.calendar.get(16);
      int var48 = (var46 + var47) / '\uea60';
      if(var48 < 0) {
         var48 = -var48;
         StringBuffer var49 = var2.append('-');
      } else {
         StringBuffer var61 = var2.append('+');
      }

      int var50 = var48 / 60;
      char var51 = Character.forDigit(var50 / 10, 10);
      var2.append(var51);
      char var53 = Character.forDigit(var50 % 10, 10);
      var2.append(var53);
      int var55 = var48 % 60;
      char var56 = Character.forDigit(var55 / 10, 10);
      var2.append(var56);
      char var58 = Character.forDigit(var55 % 10, 10);
      var2.append(var58);
      var3.setBeginIndex(0);
      int var60 = var2.length();
      var3.setEndIndex(var60);
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
