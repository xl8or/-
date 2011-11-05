package com.htc.android.mail;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.provider.Calendar.Instances;
import android.text.format.Time;
import java.util.TimeZone;

public class CheckCalendar {

   static final String EVENT_SORT_ORDER = "startDay ASC, allDay DESC, begin ASC";
   public static final int INDEX_ALL_DAY = 2;
   public static final int INDEX_BEGIN = 3;
   public static final int INDEX_END = 4;
   public static final int INDEX_END_DAY = 6;
   public static final int INDEX_END_MINUTE = 8;
   public static final int INDEX_START_DAY = 5;
   public static final int INDEX_START_MINUTE = 7;
   public static final int INDEX_TITLE = 1;
   public static final String[] INSTANCES_PROJ;
   private static final String TAG = "CheckCalendar";
   private static long end;
   private static Time mEndTime;
   private static Time mStartTime;
   private static long start;


   static {
      String[] var0 = new String[]{"_id", "title", "allDay", "begin", "end", "startDay", "endDay", "startMinute", "endMinute"};
      INSTANCES_PROJ = var0;
   }

   public CheckCalendar() {}

   private static long convertUtcToLocal(Time var0, long var1) {
      if(var0 == null) {
         var0 = new Time();
      }

      var0.timezone = "UTC";
      var0.set(var1);
      String var3 = TimeZone.getDefault().getID();
      var0.timezone = var3;
      return var0.normalize((boolean)1);
   }

   private static void getTime(String var0, String var1) {
      mStartTime = new Time();
      mEndTime = new Time();
      String var2 = var0.replaceAll("\\-", "").replaceAll("\\:", "");
      int var3 = var2.length() - 5;
      String var4 = var2.substring(0, var3);
      String var5 = var4 + "Z";
      boolean var6 = mStartTime.parse(var5);
      start = mStartTime.toMillis((boolean)1);
      String var7 = var1.replaceAll("\\-", "").replaceAll("\\:", "");
      int var8 = var7.length() - 5;
      String var9 = var7.substring(0, var8);
      String var10 = var9 + "Z";
      boolean var11 = mEndTime.parse(var10);
      end = mEndTime.toMillis((boolean)1);
      Time var12 = new Time();
      long var13 = start;
      start = convertUtcToLocal(var12, var13);
      long var15 = end;
      end = convertUtcToLocal(var12, var15);
   }

   private static long getTimeMills(int var0, int var1, int var2, int var3, int var4, int var5) {
      Time var6 = new Time();
      var6.set(var0, var1, var2, var3, var4, var5);
      return var6.normalize((boolean)1);
   }

   public static boolean isConflict(Context var0, String var1, String var2, String var3) {
      getTime(var1, var2);
      Time var4 = new Time();
      Time var5 = new Time();
      int var6 = mStartTime.monthDay;
      int var7 = mStartTime.month;
      int var8 = mStartTime.year;
      var4.set(0, 0, 0, var6, var7, var8);
      int var9 = mEndTime.monthDay;
      int var10 = mEndTime.month;
      int var11 = mEndTime.year;
      var5.set(59, 59, 23, var9, var10, var11);
      Builder var12 = Instances.CONTENT_URI.buildUpon();
      long var13 = var4.normalize((boolean)1);
      ContentUris.appendId(var12, var13);
      long var16 = var5.normalize((boolean)1);
      ContentUris.appendId(var12, var16);

      Cursor var22;
      boolean var27;
      try {
         ContentResolver var19 = var0.getContentResolver();
         Uri var20 = var12.build();
         String[] var21 = INSTANCES_PROJ;
         var22 = var19.query(var20, var21, "selected=1", (String[])null, "startDay ASC, allDay DESC, begin ASC");
      } catch (Exception var32) {
         var27 = false;
         return var27;
      }

      boolean var24 = false;
      if(var22 != null && var22.getCount() > 0) {
         var24 = parseCursor(var22, var3);
      }

      boolean var26 = false;
      var27 = var24;
      return var27;
   }

   private static boolean isRegularMeeting(long var0, long var2, boolean var4) {
      long var5 = System.currentTimeMillis();
      Time var7 = new Time();
      var7.set(var5);
      if(var4) {
         var7.timezone = "UTC";
      } else {
         String var12 = TimeZone.getDefault().getID();
         var7.timezone = var12;
      }

      long var8 = var7.gmtoff;
      int var10 = Time.getJulianDay(var5, var8);
      boolean var11;
      if((long)var10 > var0 && (long)var10 < var2) {
         var11 = true;
      } else {
         var11 = false;
      }

      return var11;
   }

   private static boolean parseCursor(Cursor param0, String param1) {
      // $FF: Couldn't be decompiled
   }
}
