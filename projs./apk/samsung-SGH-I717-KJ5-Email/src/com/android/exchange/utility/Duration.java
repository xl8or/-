package com.android.exchange.utility;

import java.text.ParseException;
import java.util.Calendar;

public class Duration {

   public int days;
   public int hours;
   public int minutes;
   public int seconds;
   public int sign = 1;
   public int weeks;


   public Duration() {}

   public long addTo(long var1) {
      return this.getMillis() + var1;
   }

   public void addTo(Calendar var1) {
      int var2 = this.sign;
      int var3 = this.weeks;
      int var4 = var2 * var3 * 7;
      var1.add(5, var4);
      int var5 = this.sign;
      int var6 = this.days;
      int var7 = var5 * var6;
      var1.add(5, var7);
      int var8 = this.sign;
      int var9 = this.hours;
      int var10 = var8 * var9;
      var1.add(10, var10);
      int var11 = this.sign;
      int var12 = this.minutes;
      int var13 = var11 * var12;
      var1.add(12, var13);
      int var14 = this.sign;
      int var15 = this.seconds;
      int var16 = var14 * var15;
      var1.add(13, var16);
   }

   public long getMillis() {
      long var1 = (long)(this.sign * 1000);
      int var3 = this.weeks;
      int var4 = 604800 * var3;
      int var5 = this.days;
      int var6 = 86400 * var5;
      int var7 = var4 + var6;
      int var8 = this.hours * 3600;
      int var9 = var7 + var8;
      int var10 = this.minutes * 60;
      int var11 = var9 + var10;
      int var12 = this.seconds;
      return (long)(var11 + var12) * var1;
   }

   public void parse(String var1) throws ParseException {
      this.sign = 1;
      this.weeks = 0;
      this.days = 0;
      this.hours = 0;
      this.minutes = 0;
      this.seconds = 0;
      int var2 = var1.length();
      int var3 = 0;
      if(var2 >= 1) {
         char var4 = var1.charAt(0);
         if(var4 == 45) {
            this.sign = -1;
            var3 = 0 + 1;
         } else if(var4 == 43) {
            var3 = 0 + 1;
         }

         if(var2 >= var3) {
            if(var1.charAt(var3) != 80) {
               String var5 = "Duration.parse(str=\'" + var1 + "\') expected \'P\' at index=" + var3;
               throw new ParseException(var5, var3);
            } else {
               ++var3;
               byte var6 = 0;

               for(StringBuffer var7 = new StringBuffer(); var3 < var2; ++var3) {
                  var4 = var1.charAt(var3);
                  if(var4 >= 48 && var4 <= 57) {
                     int var8 = var6 * 10;
                     int var9 = var4 - 48;
                     int var10000 = var8 + var9;
                  } else if(var4 == 87) {
                     this.weeks = var6;
                  } else if(var4 == 72) {
                     this.hours = var6;
                  } else if(var4 == 77) {
                     this.minutes = var6;
                  } else if(var4 == 83) {
                     this.seconds = var6;
                  } else if(var4 == 68) {
                     this.days = var6;
                  } else if(var4 != 84) {
                     String var11 = var7.append("Duration.parse(str=\'").append(var1).append("\') unexpected char \'").append(var4).append("\' at index=").append(var3).toString();
                     throw new ParseException(var11, var3);
                  }
               }

            }
         }
      }
   }
}
