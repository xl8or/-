package org.xbill.DNS;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.xbill.DNS.TextParseException;

final class FormattedTime {

   private static NumberFormat w2 = new DecimalFormat();
   private static NumberFormat w4;


   static {
      w2.setMinimumIntegerDigits(2);
      w4 = new DecimalFormat();
      w4.setMinimumIntegerDigits(4);
      w4.setGroupingUsed((boolean)0);
   }

   private FormattedTime() {}

   public static String format(Date var0) {
      TimeZone var1 = TimeZone.getTimeZone("UTC");
      GregorianCalendar var2 = new GregorianCalendar(var1);
      StringBuffer var3 = new StringBuffer();
      var2.setTime(var0);
      NumberFormat var4 = w4;
      long var5 = (long)var2.get(1);
      String var7 = var4.format(var5);
      var3.append(var7);
      NumberFormat var9 = w2;
      long var10 = (long)(var2.get(2) + 1);
      String var12 = var9.format(var10);
      var3.append(var12);
      NumberFormat var14 = w2;
      long var15 = (long)var2.get(5);
      String var17 = var14.format(var15);
      var3.append(var17);
      NumberFormat var19 = w2;
      long var20 = (long)var2.get(11);
      String var22 = var19.format(var20);
      var3.append(var22);
      NumberFormat var24 = w2;
      long var25 = (long)var2.get(12);
      String var27 = var24.format(var25);
      var3.append(var27);
      NumberFormat var29 = w2;
      long var30 = (long)var2.get(13);
      String var32 = var29.format(var30);
      var3.append(var32);
      return var3.toString();
   }

   public static Date parse(String var0) throws TextParseException {
      if(var0.length() != 14) {
         String var1 = "Invalid time encoding: " + var0;
         throw new TextParseException(var1);
      } else {
         TimeZone var2 = TimeZone.getTimeZone("UTC");
         GregorianCalendar var3 = new GregorianCalendar(var2);
         var3.clear();
         byte var4 = 0;
         byte var5 = 4;

         try {
            int var6 = Integer.parseInt(var0.substring(var4, var5));
            int var7 = Integer.parseInt(var0.substring(4, 6)) - 1;
            int var8 = Integer.parseInt(var0.substring(6, 8));
            int var9 = Integer.parseInt(var0.substring(8, 10));
            int var10 = Integer.parseInt(var0.substring(10, 12));
            int var11 = Integer.parseInt(var0.substring(12, 14));
            var3.set(var6, var7, var8, var9, var10, var11);
         } catch (NumberFormatException var14) {
            String var13 = "Invalid time encoding: " + var0;
            throw new TextParseException(var13);
         }

         return var3.getTime();
      }
   }
}
