package com.htc.android.mail.eassvc.util;

import com.htc.android.mail.eassvc.util.TimeZoneParser;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

class EASTimeZone {

   static TimeZoneParser.SystemTime DaylightTime;
   static TimeZoneParser.SystemTime StandardTime;
   static TimeZone mTimeZone = null;
   static int mYear;


   EASTimeZone() {}

   public static TimeZoneParser.SystemTime[] calculate(TimeZone var0, int var1) {
      TimeZoneParser.SystemTime[] var29;
      if(mTimeZone != null) {
         String var2 = mTimeZone.getID();
         String var3 = var0.getID();
         if(var2.equals(var3) && mYear == var1) {
            System.out.println("find match");
            var29 = new TimeZoneParser.SystemTime[2];
            TimeZoneParser.SystemTime var4 = DaylightTime;
            var29[0] = var4;
            TimeZoneParser.SystemTime var5 = StandardTime;
            var29[1] = var5;
            return var29;
         }
      }

      mTimeZone = (TimeZone)var0.clone();
      mYear = var1;
      Calendar var6 = Calendar.getInstance(var0);
      var6.setLenient((boolean)0);
      var6.set(var1, 0, 1, 1, 0, 0);
      var6.set(14, 0);
      if(var0.getDSTSavings() == 0) {
         var29 = null;
      } else {
         byte var8 = 0;
         int var9 = var8;

         do {
            Date var10 = var6.getTime();
            boolean var11 = var0.inDaylightTime(var10);
            if(var11 && var9 == 0) {
               boolean var30 = true;
               Calendar var12 = Calendar.getInstance(var0);
               long var13 = var6.getTimeInMillis();
               var12.setTimeInMillis(var13);
               var12.add(5, 7);
               int var15 = var12.get(2);
               int var16 = var6.get(2);
               if(var15 != var16) {
                  var9 = 5;
               } else {
                  var9 = (var6.get(5) - 1) / 7 + 1;
               }

               int var17 = var6.get(7) - 1;
               int var18 = var6.get(2) + 1;
               DaylightTime = new TimeZoneParser.SystemTime(var18, var17, var9, 1);
            } else if(!var11) {
               if(var9 != 0) {
                  Calendar var21 = Calendar.getInstance(var0);
                  long var22 = var6.getTimeInMillis();
                  var21.setTimeInMillis(var22);
                  var21.add(5, 7);
                  int var24 = var21.get(2);
                  int var25 = var6.get(2);
                  if(var24 != var25) {
                     var9 = 5;
                  } else {
                     var9 = (var6.get(5) - 1) / 7 + 1;
                  }

                  int var26 = var6.get(7) - 1;
                  int var27 = var6.get(2) + 1;
                  StandardTime = new TimeZoneParser.SystemTime(var27, var26, var9, 1);
               }
            }

            var6.add(10, 16);
         } while(var6.get(1) == var1);

         var29 = new TimeZoneParser.SystemTime[2];
         if(DaylightTime == null || StandardTime == null) {
            StandardTime = new TimeZoneParser.SystemTime(0, 0, 0);
            DaylightTime = new TimeZoneParser.SystemTime(0, 0, 0);
         }

         TimeZoneParser.SystemTime var19 = DaylightTime;
         var29[0] = var19;
         TimeZoneParser.SystemTime var20 = StandardTime;
         var29[1] = var20;
      }

      return var29;
   }
}
