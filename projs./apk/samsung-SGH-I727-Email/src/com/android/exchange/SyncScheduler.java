package com.android.exchange;

import android.util.Pair;
import com.android.exchange.SyncScheduleData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class SyncScheduler {

   private static final Calendar sCalendar = Calendar.getInstance();


   private SyncScheduler() {}

   public static Pair<Boolean, Long> getIsPeakAndNextAlarm(SyncScheduleData var0) {
      Date var1 = new Date();
      long var2 = var1.getTime();
      int var4 = var0.getStartMinute();
      int var5 = var0.getEndMinute();
      long var6 = getMinuteInMillis(var1, var4);
      long var8 = getMinuteInMillis(var1, var5);
      long var10 = var6 - var2;
      long var12 = var8 - var2;
      ArrayList var14 = getPeakDays(var0);
      sCalendar.setTime(var1);
      boolean var15 = true;
      Object[] var16 = var14.toArray();
      Integer var17 = Integer.valueOf(sCalendar.get(7));
      if(Arrays.binarySearch(var16, var17) < 0) {
         var15 = false;
      }

      sCalendar.add(5, -1);
      boolean var18 = true;
      Object[] var19 = var14.toArray();
      Integer var20 = Integer.valueOf(sCalendar.get(7));
      if(Arrays.binarySearch(var19, var20) < 0) {
         var18 = false;
      }

      byte var21;
      long var22;
      if(var10 == var12) {
         if(var12 > 0L) {
            if(var18) {
               var21 = 1;
            } else {
               var21 = 0;
            }

            var22 = var12;
         } else {
            if(var15) {
               var21 = 1;
            } else {
               var21 = 0;
            }

            var22 = 86400000L + var10;
         }
      } else if(var10 <= 0L) {
         if(var12 < var10) {
            var12 += 86400000L;
         }

         if(var12 > 0L) {
            if(var15) {
               var21 = 1;
            } else {
               var21 = 0;
            }

            var22 = var12;
         } else {
            long var34 = 86400000L + var10;
            var21 = 0;
            var22 = var34;
         }
      } else if(var12 > 0L && var12 < var10) {
         if(var18) {
            var21 = 1;
         } else {
            var21 = 0;
         }

         var22 = var12;
      } else {
         var21 = 0;
         var22 = var10;
      }

      Boolean var28 = Boolean.valueOf((boolean)var21);
      Long var29 = Long.valueOf(var22);
      return new Pair(var28, var29);
   }

   private static long getMinuteInMillis(Date var0, int var1) {
      sCalendar.setTime(var0);
      int var2 = var1 / 60;
      int var3 = var1 % 60;
      sCalendar.set(11, var2);
      sCalendar.set(12, var3);
      sCalendar.set(13, 0);
      sCalendar.set(14, 0);
      return sCalendar.getTimeInMillis();
   }

   private static ArrayList<Integer> getPeakDays(SyncScheduleData var0) {
      int var1 = var0.getPeakDay();
      int var2 = 1;
      int var3 = 0;

      for(int var4 = 0; var4 < 7; ++var4) {
         if((var1 & var2) != 0) {
            ++var3;
         }

         var2 <<= 1;
      }

      ArrayList var5 = new ArrayList(var3);
      int var6 = 1;
      int var7 = 0;

      int var9;
      for(int var8 = 0; var7 < 7; var8 = var9) {
         if((var1 & var6) != 0) {
            var9 = var8 + 1;
            Integer var10 = Integer.valueOf(var7 + 1);
            var5.add(var8, var10);
         } else {
            var9 = var8;
         }

         var6 <<= 1;
         ++var7;
      }

      return var5;
   }
}
