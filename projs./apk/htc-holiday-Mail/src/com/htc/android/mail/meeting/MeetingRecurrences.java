package com.htc.android.mail.meeting;

import android.text.format.Time;

public class MeetingRecurrences {

   public int dayOfMonth;
   public int dayOfWeek;
   public int interval;
   public int monthOfYear;
   public int occurrences;
   public int type;
   public Time until;
   public int weekOfMonth;


   public MeetingRecurrences() {}

   public static class DayOfWeek {

      public static final int Friday = 32;
      public static final int Monday = 2;
      public static final int Satureday = 64;
      public static final int Sunday = 1;
      public static final int Thuesday = 4;
      public static final int Thursday = 16;
      public static final int Wednesday = 8;


      public DayOfWeek() {}
   }

   public static class Type {

      public static final int RECURS_DAILY = 0;
      public static final int RECURS_MONTHLY = 2;
      public static final int RECURS_MONTHLY_ON_nTh_DAY = 4;
      public static final int RECURS_WEEKLY = 1;
      public static final int RECURS_YEARLY = 5;
      public static final int RECURS_YEARLY_ON_nTh_day = 6;


      public Type() {}
   }
}
