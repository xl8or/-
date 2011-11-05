package com.facebook.katana.util;

import android.content.Context;
import android.text.format.DateUtils;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

   public static final int AFTER_THIS_MONTH = 5;
   public static final int ANY_TIME = 100;
   public static final int HAPPENING_NOW = 0;
   public static final int IN_PAST = 255;
   public static final int THIS_MONTH = 4;
   public static final int THIS_WEEK = 3;
   public static final int TODAY = 1;
   public static final int TOMORROW = 2;


   public TimeUtils() {}

   public static final int getAge(int var0, long var1) {
      return (new Date(var1)).getYear() + 1900 - var0;
   }

   public static int getPstOffset() {
      TimeZone var0 = TimeZone.getDefault();
      TimeZone var1 = TimeZone.getTimeZone("America/Los_Angeles");
      Date var2 = new Date();
      int var3;
      if(var0.inDaylightTime(var2)) {
         var3 = var0.getDSTSavings();
      } else {
         var3 = 0;
      }

      int var4;
      if(var1.inDaylightTime(var2)) {
         var4 = var1.getDSTSavings();
      } else {
         var4 = 0;
      }

      int var5 = var1.getRawOffset() + var4;
      int var6 = var0.getRawOffset();
      return var5 - var6 - var3;
   }

   public static final String getStringOfTimePeriod(int var0, Context var1) {
      String var4;
      String var5;
      switch(var0) {
      case -1:
         var5 = "";
         break;
      case 0:
         var5 = var1.getString(2131362288);
         break;
      case 1:
         var5 = var1.getString(2131362294);
         break;
      case 2:
         var5 = var1.getString(2131362296);
         break;
      case 3:
         var5 = var1.getString(2131362293);
         break;
      case 4:
         var5 = var1.getString(2131362292);
         break;
      default:
         String[] var2 = (new DateFormatSymbols()).getMonths();
         int var3 = var0 - 5;
         var4 = var2[var3];
         return var4;
      }

      var4 = var5;
      return var4;
   }

   public static final String getTimeAsStringForTimePeriod(Context var0, int var1, long var2) {
      String var7;
      switch(var1) {
      case 0:
         var7 = var0.getString(2131362291);
         break;
      case 1:
      case 2:
         var7 = DateUtils.formatDateTime(var0, var2, 2561);
         break;
      case 3:
         var7 = DateUtils.formatDateTime(var0, var2, 2563);
         break;
      case 100:
         var7 = DateUtils.formatDateTime(var0, var2, 26);
         break;
      default:
         Object[] var4 = new Object[2];
         String var5 = DateUtils.formatDateTime(var0, var2, 65560);
         var4[0] = var5;
         String var6 = DateUtils.formatDateTime(var0, var2, 2561);
         var4[1] = var6;
         var7 = var0.getString(2131362287, var4);
      }

      return var7;
   }

   public static final int getTimePeriod(long var0) {
      long var2 = System.currentTimeMillis();
      return getTimePeriod(var2, var0, 0L);
   }

   public static final int getTimePeriod(long var0, long var2) {
      long var4 = System.currentTimeMillis();
      return getTimePeriod(var4, var0, var2);
   }

   public static final int getTimePeriod(long var0, long var2, long var4) {
      Date var6 = new Date(var2);
      Date var7 = new Date();
      long var8 = var2 - var0;
      int var10;
      if(var4 != 0L && var2 <= var0 && var0 <= var4) {
         var10 = 0;
      } else if(var8 < 0L) {
         var10 = -1;
      } else {
         if(var8 < 86400000L) {
            int var11 = var6.getDate();
            int var12 = var7.getDate();
            if(var11 == var12) {
               var10 = 1;
               return var10;
            }
         }

         if(var8 < 172800000L) {
            int var13 = (var7.getDay() + 1) % 7;
            int var14 = var6.getDay();
            if(var13 == var14) {
               var10 = 2;
               return var10;
            }
         }

         int var15 = var6.getDay();
         int var16 = var7.getDay();
         if(var15 >= var16 && var8 < 604800000L) {
            var10 = 3;
         } else {
            long var17 = 744L * 1000L * 3600L;
            int var19 = var6.getMonth();
            int var20 = var7.getMonth();
            if(var19 == var20 && var8 < var17) {
               var10 = 4;
            } else {
               var10 = var6.getMonth() + 5;
            }
         }
      }

      return var10;
   }

   public static final long timeInSeconds(int var0, int var1, boolean var2) {
      int var3 = (new Date()).getYear();
      if(var2) {
         ++var3;
      }

      Calendar var4 = Calendar.getInstance();
      byte var7 = 59;
      Date var8 = new Date(var3, var0, var1, 23, 59, var7);
      var4.setTime(var8);
      return var4.getTimeInMillis() / 1000L;
   }
}
