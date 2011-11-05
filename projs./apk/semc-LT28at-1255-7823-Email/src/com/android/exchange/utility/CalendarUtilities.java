package com.android.exchange.utility;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Entity;
import android.content.EntityIterator;
import android.content.res.Resources;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Base64;
import com.android.email.Email;
import com.android.email.mail.Address;
import com.android.email.provider.EmailContent;
import com.android.exchange.Eas;
import com.android.exchange.EasSyncService;
import com.android.exchange.SyncManager;
import com.android.exchange.adapter.Serializer;
import com.android.exchange.provider.Calendar;
import com.android.exchange.utility.SimpleIcsWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

public class CalendarUtilities {

   public static final int BUSY_STATUS_BUSY = 2;
   public static final int BUSY_STATUS_FREE = 0;
   public static final int BUSY_STATUS_OUT_OF_OFFICE = 3;
   public static final int BUSY_STATUS_TENTATIVE = 1;
   static final long DAYS = 86400000L;
   static final int HOURS = 3600000;
   private static final String ICALENDAR_ATTENDEE = "ATTENDEE;ROLE=REQ-PARTICIPANT";
   static final String ICALENDAR_ATTENDEE_ACCEPT = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=ACCEPTED";
   static final String ICALENDAR_ATTENDEE_CANCEL = "ATTENDEE;ROLE=REQ-PARTICIPANT";
   static final String ICALENDAR_ATTENDEE_DECLINE = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=DECLINED";
   static final String ICALENDAR_ATTENDEE_INVITE = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE";
   static final String ICALENDAR_ATTENDEE_TENTATIVE = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=TENTATIVE";
   static final int MINUTES = 60000;
   static final int MSFT_LONG_SIZE = 4;
   static final int MSFT_SYSTEMTIME_DAY = 6;
   static final int MSFT_SYSTEMTIME_DAY_OF_WEEK = 4;
   static final int MSFT_SYSTEMTIME_HOUR = 8;
   static final int MSFT_SYSTEMTIME_MINUTE = 10;
   static final int MSFT_SYSTEMTIME_MONTH = 2;
   static final int MSFT_SYSTEMTIME_SIZE = 16;
   static final int MSFT_SYSTEMTIME_YEAR = 0;
   static final int MSFT_TIME_ZONE_BIAS_OFFSET = 0;
   static final int MSFT_TIME_ZONE_DAYLIGHT_BIAS_OFFSET = 168;
   static final int MSFT_TIME_ZONE_DAYLIGHT_DATE_OFFSET = 152;
   static final int MSFT_TIME_ZONE_DAYLIGHT_NAME_OFFSET = 88;
   static final int MSFT_TIME_ZONE_SIZE = 172;
   static final int MSFT_TIME_ZONE_STANDARD_BIAS_OFFSET = 84;
   static final int MSFT_TIME_ZONE_STANDARD_DATE_OFFSET = 68;
   static final int MSFT_TIME_ZONE_STANDARD_NAME_OFFSET = 4;
   static final int MSFT_WCHAR_SIZE = 2;
   static final int MSFT_WORD_SIZE = 2;
   static final int SECONDS = 1000;
   private static final String TAG = "CalendarUtility";
   private static final TimeZone UTC_TIMEZONE = TimeZone.getTimeZone("UTC");
   static final int sCurrentYear;
   static final String[] sDayTokens;
   static final TimeZone sGmtTimeZone;
   private static HashMap<String, TimeZone> sTimeZoneCache = new HashMap();
   static final String[] sTwoCharacterNumbers;
   static final String[] sTypeToFreq;
   private static HashMap<TimeZone, String> sTziStringCache = new HashMap();


   static {
      String[] var0 = new String[]{"DAILY", "WEEKLY", "MONTHLY", "MONTHLY", "", "YEARLY", "YEARLY"};
      sTypeToFreq = var0;
      String[] var1 = new String[]{"SU", "MO", "TU", "WE", "TH", "FR", "SA"};
      sDayTokens = var1;
      String[] var2 = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
      sTwoCharacterNumbers = var2;
      sCurrentYear = (new GregorianCalendar()).get(1);
      sGmtTimeZone = TimeZone.getTimeZone("GMT");
   }

   public CalendarUtilities() {}

   private static void addAttendeeToMessage(SimpleIcsWriter var0, ArrayList<Address> var1, String var2, String var3, int var4, EmailContent.Account var5) {
      String var6;
      if((var4 & 48) != 0) {
         var6 = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE";
         if((var4 & 32) != 0) {
            var6 = "ATTENDEE;ROLE=REQ-PARTICIPANT";
         }

         if(var2 != null) {
            StringBuilder var7 = (new StringBuilder()).append(var6).append(";CN=");
            String var8 = SimpleIcsWriter.quoteParamValue(var2);
            var6 = var7.append(var8).toString();
         }

         String var9 = "MAILTO:" + var3;
         var0.writeTag(var6, var9);
         Address var10;
         if(var2 == null) {
            var10 = new Address(var3);
         } else {
            var10 = new Address(var3, var2);
         }

         var1.add(var10);
      } else {
         String var12 = var5.mEmailAddress;
         if(var3.equalsIgnoreCase(var12)) {
            var6 = null;
            switch(var4) {
            case 64:
               var6 = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=ACCEPTED";
               break;
            case 128:
               var6 = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=DECLINED";
               break;
            case 256:
               var6 = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=TENTATIVE";
            }

            if(var6 != null) {
               if(var2 != null) {
                  StringBuilder var13 = (new StringBuilder()).append(var6).append(";CN=");
                  String var14 = SimpleIcsWriter.quoteParamValue(var2);
                  var6 = var13.append(var14).toString();
               }

               String var15 = "MAILTO:" + var3;
               var0.writeTag(var6, var15);
            }
         }
      }
   }

   static void addByDay(StringBuilder var0, int var1, int var2) {
      StringBuilder var3 = var0.append(";BYDAY=");

      for(int var4 = 0; var4 < 7; ++var4) {
         if((var1 & 1) == 1) {
            if(false) {
               StringBuilder var5 = var0.append(',');
            }

            String var6 = sDayTokens[var4];
            var0.append(var6);
         }

         var1 >>= 1;
      }

      if(var2 > 0) {
         StringBuilder var8 = var0.append(";BYSETPOS=");
         int var9;
         if(var2 == 5) {
            var9 = -1;
         } else {
            var9 = var2;
         }

         var0.append(var9);
      }
   }

   static void addByMonthDay(StringBuilder var0, int var1) {
      if(var1 == 127) {
         var1 = -1;
      }

      String var2 = ";BYMONTHDAY=" + var1;
      var0.append(var2);
   }

   static void addUntilAndCount(String var0, Serializer var1) throws IOException {
      String var2 = tokenFromRrule(var0, "UNTIL=");
      String var3 = tokenFromRrule(var0, "COUNT=");
      if(var2 != null) {
         String var4 = recurrenceUntilToEasUntil(var2);
         var1.data(285, var4);
      }

      if(var3 != null) {
         var1.data(286, var3);
      }
   }

   public static int attendeeStatusFromBusyStatus(int var0) {
      byte var1;
      switch(var0) {
      case 1:
         var1 = 4;
         break;
      case 2:
         var1 = 1;
         break;
      default:
         var1 = 0;
      }

      return var1;
   }

   public static String buildMessageTextFromEntityValues(Context var0, ContentValues var1, StringBuilder var2) {
      if(var2 == null) {
         var2 = new StringBuilder();
      }

      Resources var3 = var0.getResources();
      long var4 = var1.getAsLong("dtstart").longValue();
      Date var6 = new Date(var4);
      String var7 = DateFormat.getDateTimeInstance().format(var6);
      if(!var1.containsKey("originalEvent") && var1.containsKey("rrule")) {
         Object[] var8 = new Object[]{var7};
         String var9 = var3.getString(2131165447, var8);
         var2.append(var9);
      } else {
         Object[] var19 = new Object[]{var7};
         String var20 = var3.getString(2131165445, var19);
         var2.append(var20);
      }

      if(var1.containsKey("eventLocation")) {
         String var11 = var1.getAsString("eventLocation");
         if(!TextUtils.isEmpty(var11)) {
            StringBuilder var12 = var2.append("\n");
            Object[] var13 = new Object[]{var11};
            String var14 = var3.getString(2131165446, var13);
            var2.append(var14);
         }
      }

      String var16 = var1.getAsString("description");
      if(var16 != null) {
         StringBuilder var17 = var2.append("\n--\n");
         var2.append(var16);
      }

      return var2.toString();
   }

   public static int busyStatusFromAttendeeStatus(int var0) {
      byte var1;
      switch(var0) {
      case 0:
      case 2:
      case 3:
         var1 = 0;
         break;
      case 1:
      default:
         var1 = 2;
         break;
      case 4:
         var1 = 1;
      }

      return var1;
   }

   public static String convertEmailDateTimeToCalendarDateTime(String var0) {
      StringBuilder var1 = new StringBuilder();
      String var2 = var0.substring(0, 4);
      StringBuilder var3 = var1.append(var2);
      String var4 = var0.substring(5, 7);
      StringBuilder var5 = var3.append(var4);
      String var6 = var0.substring(8, 13);
      StringBuilder var7 = var5.append(var6);
      String var8 = var0.substring(14, 16);
      StringBuilder var9 = var7.append(var8);
      String var10 = var0.substring(17, 19);
      return var9.append(var10).append('Z').toString();
   }

   public static long createCalendar(EasSyncService var0, EmailContent.Account var1, EmailContent.Mailbox var2) {
      ContentValues var3 = new ContentValues();
      String var4 = var1.mDisplayName;
      var3.put("displayName", var4);
      String var5 = var1.mEmailAddress;
      var3.put("_sync_account", var5);
      var3.put("_sync_account_type", "com.android.exchange");
      Integer var6 = Integer.valueOf(1);
      var3.put("sync_events", var6);
      Integer var7 = Integer.valueOf(1);
      var3.put("selected", var7);
      Integer var8 = Integer.valueOf(0);
      var3.put("hidden", var8);
      Integer var9 = Integer.valueOf(0);
      var3.put("organizerCanRespond", var9);
      int var10 = Email.getAccountColor(var1.mId);
      Integer var11 = Integer.valueOf(-16777216 | var10);
      var3.put("color", var11);
      String var12 = Time.getCurrentTimezone();
      var3.put("timezone", var12);
      Integer var13 = Integer.valueOf(700);
      var3.put("access_level", var13);
      String var14 = var1.mEmailAddress;
      var3.put("ownerAccount", var14);
      ContentResolver var15 = var0.mContentResolver;
      Uri var16 = Calendar.Calendars.CONTENT_URI;
      Uri var17 = var15.insert(var16, var3);
      long var19;
      if(var17 != null) {
         String var18 = (String)var17.getPathSegments().get(1);
         var2.mSyncStatus = var18;
         var19 = Long.parseLong(var18);
      } else {
         var19 = 65535L;
      }

      return var19;
   }

   public static EmailContent.Message createMessageForEntity(Context var0, Entity var1, int var2, String var3, EmailContent.Account var4) {
      return createMessageForEntity(var0, var1, var2, var3, var4, (String)null);
   }

   public static EmailContent.Message createMessageForEntity(Context param0, Entity param1, int param2, String param3, EmailContent.Account param4, String param5) {
      // $FF: Couldn't be decompiled
   }

   public static EmailContent.Message createMessageForEventId(Context var0, long var1, int var3, String var4, EmailContent.Account var5) throws RemoteException {
      return createMessageForEventId(var0, var1, var3, var4, var5, (String)null);
   }

   public static EmailContent.Message createMessageForEventId(Context var0, long var1, int var3, String var4, EmailContent.Account var5, String var6) throws RemoteException {
      ContentResolver var7 = var0.getContentResolver();
      Uri var8 = ContentUris.withAppendedId(Calendar.Events.CONTENT_URI.buildUpon().appendQueryParameter("caller_is_syncadapter", "true").build(), var1);
      Object var9 = null;
      Object var10 = null;
      Object var11 = null;
      EntityIterator var12 = Calendar.EventsEntity.newEntityIterator(var7.query(var8, (String[])null, (String)var9, (String[])var10, (String)var11), var7);
      boolean var23 = false;

      EmailContent.Message var19;
      EmailContent.Message var20;
      label50: {
         try {
            var23 = true;
            if(var12.hasNext()) {
               Entity var13 = (Entity)var12.next();
               var19 = createMessageForEntity(var0, var13, var3, var4, var5, var6);
               var23 = false;
               break label50;
            }

            var23 = false;
         } finally {
            if(var23) {
               var12.close();
            }
         }

         var12.close();
         var20 = null;
         return var20;
      }

      var20 = var19;
      var12.close();
      return var20;
   }

   static long findNextTransition(long var0, GregorianCalendar[] var2) {
      GregorianCalendar[] var3 = var2;
      int var4 = var2.length;
      int var5 = 0;

      long var8;
      while(true) {
         if(var5 >= var4) {
            var8 = 0L;
            break;
         }

         long var6 = var3[var5].getTimeInMillis();
         if(var6 > var0) {
            var8 = var6;
            break;
         }

         ++var5;
      }

      return var8;
   }

   static GregorianCalendar findTransitionDate(TimeZone var0, long var1, long var3, boolean var5) {
      long var6 = var3;

      while(var3 - var1 > 60000L) {
         long var8 = (var1 + var3) / 2L + 1L;
         Date var10 = new Date(var8);
         if(var0.inDaylightTime(var10) != var5) {
            var3 = var8;
         } else {
            var1 = var8;
         }
      }

      GregorianCalendar var13;
      if(var3 == var6) {
         var13 = null;
      } else {
         GregorianCalendar var12 = new GregorianCalendar(var0);
         var12.setTimeInMillis(var1);
         var13 = var12;
      }

      return var13;
   }

   static String formatTwo(int var0) {
      String var1;
      if(var0 <= 12) {
         var1 = sTwoCharacterNumbers[var0];
      } else {
         var1 = Integer.toString(var0);
      }

      return var1;
   }

   static String generateEasDayOfWeek(String var0) {
      int var1 = 0;
      int var2 = 1;
      String[] var3 = sDayTokens;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         if(var0.indexOf(var6) >= 0) {
            var1 |= var2;
         }

         var2 <<= 1;
      }

      return Integer.toString(var1);
   }

   static boolean getDSTCalendars(TimeZone var0, GregorianCalendar[] var1, GregorianCalendar[] var2) {
      int var3 = var1.length;
      int var4 = var2.length;
      boolean var6;
      if(var4 != var3) {
         var6 = false;
      } else {
         int var7 = 0;

         while(true) {
            if(var7 >= var3) {
               var6 = true;
               break;
            }

            GregorianCalendar var10 = new GregorianCalendar(var0);
            int var13 = sCurrentYear + var7;
            var10.set(var13, 0, 1, 0, 0, 0);
            long var14 = var10.getTimeInMillis();
            long var16 = 31536000000L + var14;
            long var18 = 21600000L + var16;
            Date var20 = new Date(var14);
            boolean var23 = var0.inDaylightTime(var20);
            GregorianCalendar var24 = findTransitionDate(var0, var14, var18, var23);
            if(var24 == null) {
               var6 = false;
               break;
            }

            if(var23) {
               var2[var7] = var24;
            } else {
               var1[var7] = var24;
            }

            byte var25;
            if(!var23) {
               var25 = 1;
            } else {
               var25 = 0;
            }

            var24 = findTransitionDate(var0, var14, var18, (boolean)var25);
            if(var24 == null) {
               var6 = false;
               break;
            }

            if(var23) {
               var1[var7] = var24;
            } else {
               var2[var7] = var24;
            }

            ++var7;
         }
      }

      return var6;
   }

   public static long getLocalAllDayCalendarTime(long var0, TimeZone var2) {
      TimeZone var3 = UTC_TIMEZONE;
      return transposeAllDayTime(var0, var3, var2);
   }

   static int getLong(byte[] var0, int var1) {
      int var2 = var1 + 1;
      int var3 = var0[var1] & 255;
      int var4 = var2 + 1;
      int var5 = (var0[var2] & 255) << 8;
      int var6 = var3 | var5;
      int var7 = var4 + 1;
      int var8 = (var0[var4] & 255) << 16;
      int var9 = var6 | var8;
      int var10 = (var0[var7] & 255) << 24;
      return var9 | var10;
   }

   static long getMillisAtTimeZoneDateTransition(TimeZone var0, CalendarUtilities.TimeZoneDate var1) {
      GregorianCalendar var2 = new GregorianCalendar(var0);
      int var3 = sCurrentYear;
      var2.set(1, var3);
      int var4 = var1.month;
      var2.set(2, var4);
      int var5 = var1.dayOfWeek;
      var2.set(7, var5);
      int var6 = var1.day;
      var2.set(8, var6);
      int var7 = var1.hour;
      var2.set(11, var7);
      int var8 = var1.minute;
      var2.set(12, var8);
      var2.set(13, 0);
      return var2.getTimeInMillis();
   }

   static CalendarUtilities.TimeZoneDate getTimeZoneDateFromSystemTime(byte[] var0, int var1) {
      CalendarUtilities.TimeZoneDate var2 = new CalendarUtilities.TimeZoneDate();
      int var3 = var1 + 0;
      String var4 = Integer.toString(getWord(var0, var3));
      var2.year = var4;
      int var5 = var1 + 2;
      int var6 = getWord(var0, var5);
      CalendarUtilities.TimeZoneDate var7;
      if(var6 == 0) {
         var7 = null;
      } else {
         int var8 = var6 - 1;
         var2.month = var8;
         int var9 = var1 + 4;
         int var10 = getWord(var0, var9) + 1;
         var2.dayOfWeek = var10;
         int var11 = var1 + 6;
         var6 = getWord(var0, var11);
         if(var6 == 5) {
            var2.day = -1;
         } else {
            var2.day = var6;
         }

         int var12 = var1 + 8;
         int var13 = getWord(var0, var12);
         var2.hour = var13;
         int var14 = var1 + 10;
         int var15 = getWord(var0, var14);
         var2.minute = var15;
         int var16 = 3600000 * var13;
         int var17 = '\uea60' * var15;
         int var18 = var16 + var17;
         var2.time = var18;
         var7 = var2;
      }

      return var7;
   }

   static int getTrueTransitionHour(GregorianCalendar var0) {
      int var1 = var0.get(11) + 1;
      if(var1 == 24) {
         var1 = 0;
      }

      return var1;
   }

   static int getTrueTransitionMinute(GregorianCalendar var0) {
      int var1 = var0.get(12);
      if(var1 == 59) {
         var1 = 0;
      }

      return var1;
   }

   public static String getUidFromGlobalObjId(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static long getUtcAllDayCalendarTime(long var0, TimeZone var2) {
      TimeZone var3 = UTC_TIMEZONE;
      return transposeAllDayTime(var0, var2, var3);
   }

   static int getWord(byte[] var0, int var1) {
      int var2 = var1 + 1;
      int var3 = var0[var1] & 255;
      int var4 = (var0[var2] & 255) << 8;
      return var3 | var4;
   }

   static CalendarUtilities.RRule inferRRuleFromCalendars(GregorianCalendar[] var0) {
      GregorianCalendar var1 = var0[0];
      CalendarUtilities.RRule var21;
      if(var1 == null) {
         var21 = null;
      } else {
         int var2 = var1.get(2);
         int var3 = var1.get(5);
         int var4 = var1.get(7);
         int var5 = var1.get(8);
         int var6 = var1.getActualMaximum(8);
         int var7 = var5;
         int var8 = 1;
         boolean var9 = false;
         boolean var10 = false;

         while(true) {
            int var11 = var0.length;
            if(var8 >= var11) {
               if(var10) {
                  int var19 = var2 + 1;
                  var21 = new CalendarUtilities.RRule(var19, var3);
               } else {
                  int var20 = var2 + 1;
                  var21 = new CalendarUtilities.RRule(var20, var4, var7);
               }
               break;
            }

            var1 = var0[var8];
            if(var1 == null) {
               var21 = null;
               break;
            }

            if(var1.get(2) != var2) {
               var21 = null;
               break;
            }

            int var12 = var1.get(7);
            int var15;
            boolean var17;
            boolean var16;
            if(var4 == var12) {
               if(var10) {
                  var21 = null;
                  break;
               }

               var9 = true;
               int var13 = var1.get(8);
               if(var7 != var13) {
                  label66: {
                     if(var7 < 0 || var7 == var6) {
                        int var14 = var1.getActualMaximum(8);
                        if(var13 == var14) {
                           var15 = -1;
                           var16 = var10;
                           var17 = var9;
                           break label66;
                        }
                     }

                     var21 = null;
                     break;
                  }
               } else {
                  var16 = var10;
                  var15 = var7;
                  var17 = var9;
               }
            } else {
               int var18 = var1.get(5);
               if(var3 != var18) {
                  var21 = null;
                  break;
               }

               if(var9) {
                  var21 = null;
                  break;
               }

               var16 = true;
               var17 = var9;
               var15 = var7;
            }

            ++var8;
            var7 = var15;
            var9 = var17;
            var10 = var16;
         }
      }

      return var21;
   }

   public static String millisToEasDateTime(long var0) {
      TimeZone var2 = sGmtTimeZone;
      return millisToEasDateTime(var0, var2, (boolean)1);
   }

   public static String millisToEasDateTime(long var0, TimeZone var2, boolean var3) {
      StringBuilder var4 = new StringBuilder();
      GregorianCalendar var5 = new GregorianCalendar(var2);
      var5.setTimeInMillis(var0);
      int var6 = var5.get(1);
      var4.append(var6);
      String var8 = formatTwo(var5.get(2) + 1);
      var4.append(var8);
      String var10 = formatTwo(var5.get(5));
      var4.append(var10);
      if(var3 != null) {
         StringBuilder var12 = var4.append('T');
         String var13 = formatTwo(var5.get(11));
         var4.append(var13);
         String var15 = formatTwo(var5.get(12));
         var4.append(var15);
         String var17 = formatTwo(var5.get(13));
         var4.append(var17);
         TimeZone var19 = sGmtTimeZone;
         if(var2 == var19) {
            StringBuilder var20 = var4.append('Z');
         }
      }

      return var4.toString();
   }

   static void putRuleIntoTimeZoneInformation(byte[] var0, int var1, CalendarUtilities.RRule var2, int var3, int var4) {
      int var5 = var1 + 2;
      int var6 = var2.month;
      setWord(var0, var5, var6);
      int var7 = var1 + 4;
      int var8 = var2.dayOfWeek - 1;
      setWord(var0, var7, var8);
      int var9 = var1 + 6;
      int var10;
      if(var2.week < 0) {
         var10 = 5;
      } else {
         var10 = var2.week;
      }

      setWord(var0, var9, var10);
      int var11 = var1 + 8;
      setWord(var0, var11, var3);
      int var12 = var1 + 10;
      setWord(var0, var12, var4);
   }

   static void putTransitionMillisIntoSystemTime(byte[] var0, int var1, long var2) {
      TimeZone var4 = TimeZone.getDefault();
      GregorianCalendar var5 = new GregorianCalendar(var4);
      long var6 = 30000L + var2;
      var5.setTimeInMillis(var6);
      int var8 = var1 + 2;
      int var9 = var5.get(2) + 1;
      setWord(var0, var8, var9);
      int var10 = var1 + 4;
      int var11 = var5.get(7) - 1;
      setWord(var0, var10, var11);
      int var12 = var5.get(8);
      int var13 = var1 + 6;
      int var14;
      if(var12 < 0) {
         var14 = 5;
      } else {
         var14 = var12;
      }

      setWord(var0, var13, var14);
      int var15 = var1 + 8;
      int var16 = getTrueTransitionHour(var5);
      setWord(var0, var15, var16);
      int var17 = var1 + 10;
      int var18 = getTrueTransitionMinute(var5);
      setWord(var0, var17, var18);
   }

   public static void recurrenceFromRrule(String var0, long var1, Serializer var3) throws IOException {
      if(Eas.USER_LOG) {
         String var4 = "RRULE: " + var0;
         SyncManager.log("CalendarUtility", var4);
      }

      String var5 = tokenFromRrule(var0, "FREQ=");
      if(var5 != null) {
         String var41;
         if(var5.equals("DAILY")) {
            Serializer var6 = var3.start(283);
            Serializer var7 = var3.data(284, "0");
            var41 = tokenFromRrule(var0, "INTERVAL=");
            if(var41 != null) {
               var3.data(287, var41);
            }

            addUntilAndCount(var0, var3);
            Serializer var9 = var3.end();
         } else if(var5.equals("WEEKLY")) {
            Serializer var10 = var3.start(283);
            Serializer var11 = var3.data(284, "1");
            String var12 = tokenFromRrule(var0, "INTERVAL=");
            if(var12 != null) {
               var3.data(287, var12);
            }

            var41 = tokenFromRrule(var0, "BYDAY=");
            if(var41 != null) {
               String var14 = generateEasDayOfWeek(var41);
               var3.data(288, var14);
            }

            addUntilAndCount(var0, var3);
            Serializer var16 = var3.end();
         } else if(var5.equals("MONTHLY")) {
            var41 = tokenFromRrule(var0, "BYMONTHDAY=");
            if(var41 != null) {
               Serializer var17 = var3.start(283);
               Serializer var18 = var3.data(284, "2");
               var3.data(289, var41);
               addUntilAndCount(var0, var3);
               Serializer var20 = var3.end();
            } else {
               var41 = tokenFromRrule(var0, "BYDAY=");
               if(var41 != null) {
                  char var21 = var41.charAt(0);
                  String var23;
                  int var22;
                  if(var21 == 45) {
                     var22 = 5;
                     var23 = var41.substring(2);
                  } else {
                     var22 = var21 - 48;
                     var23 = var41.substring(1);
                  }

                  Serializer var24 = var3.start(283);
                  Serializer var25 = var3.data(284, "3");
                  String var26 = Integer.toString(var22);
                  var3.data(290, var26);
                  String var28 = generateEasDayOfWeek(var23);
                  var3.data(288, var28);
                  addUntilAndCount(var0, var3);
                  Serializer var30 = var3.end();
               }
            }
         } else if(var5.equals("YEARLY")) {
            String var31 = tokenFromRrule(var0, "BYMONTH=");
            String var32 = tokenFromRrule(var0, "BYMONTHDAY=");
            String var35;
            if(var31 != null && var32 != null) {
               var35 = var32;
               var41 = var31;
            } else {
               GregorianCalendar var33 = new GregorianCalendar();
               var33.setTimeInMillis(var1);
               TimeZone var34 = TimeZone.getDefault();
               var33.setTimeZone(var34);
               var41 = Integer.toString(var33.get(2) + 1);
               var35 = Integer.toString(var33.get(5));
            }

            Serializer var36 = var3.start(283);
            Serializer var37 = var3.data(284, "5");
            var3.data(289, var35);
            var3.data(291, var41);
            addUntilAndCount(var0, var3);
            Serializer var40 = var3.end();
         }
      }
   }

   static String recurrenceUntilToEasUntil(String var0) {
      StringBuilder var1 = new StringBuilder();
      String var2 = var0.substring(0, 4);
      var1.append(var2);
      String var4 = var0.substring(4, 6);
      var1.append(var4);
      String var6 = var0.substring(6, 8);
      var1.append(var6);
      StringBuilder var8 = var1.append("T000000Z");
      return var1.toString();
   }

   public static String rruleFromRecurrence(int var0, int var1, int var2, int var3, int var4, int var5, int var6, String var7) {
      StringBuilder var8 = (new StringBuilder()).append("FREQ=");
      String var9 = sTypeToFreq[var0];
      String var10 = var8.append(var9).toString();
      StringBuilder var11 = new StringBuilder(var10);
      if(var2 > 0) {
         String var12 = ";INTERVAL=" + var2;
         var11.append(var12);
      }

      if(var1 > 0) {
         String var14 = ";COUNT=" + var1;
         var11.append(var14);
      }

      switch(var0) {
      case 0:
      case 1:
         if(var3 > 0) {
            addByDay(var11, var3, -1);
         }
         break;
      case 2:
         if(var4 > 0) {
            addByMonthDay(var11, var4);
         }
         break;
      case 3:
         if(var3 > 0) {
            addByDay(var11, var3, var5);
         }
      case 4:
      default:
         break;
      case 5:
         if(var4 > 0) {
            addByMonthDay(var11, var4);
         }

         if(var6 > 0) {
            String var18 = ";BYMONTH=" + var6;
            var11.append(var18);
         }
         break;
      case 6:
         if(var3 > 0) {
            addByDay(var11, var3, var5);
         }

         if(var4 > 0) {
            addByMonthDay(var11, var4);
         }

         if(var6 > 0) {
            String var20 = ";BYMONTH=" + var6;
            var11.append(var20);
         }
      }

      if(var7 != null) {
         String var16 = ";UNTIL=" + var7;
         var11.append(var16);
      }

      return var11.toString();
   }

   static void setLong(byte[] var0, int var1, int var2) {
      int var3 = var1 + 1;
      byte var4 = (byte)(var2 & 255);
      var0[var1] = var4;
      int var5 = var3 + 1;
      byte var6 = (byte)(var2 >> 8 & 255);
      var0[var3] = var6;
      int var7 = var5 + 1;
      byte var8 = (byte)(var2 >> 16 & 255);
      var0[var5] = var8;
      byte var9 = (byte)(var2 >> 24 & 255);
      var0[var7] = var9;
   }

   static void setWord(byte[] var0, int var1, int var2) {
      int var3 = var1 + 1;
      byte var4 = (byte)(var2 & 255);
      var0[var1] = var4;
      byte var5 = (byte)(var2 >> 8 & 255);
      var0[var3] = var5;
   }

   public static String timeZoneToTziString(TimeZone var0) {
      String var1 = (String)sTziStringCache.get(var0);
      String var5;
      if(var1 != null) {
         if(Eas.USER_LOG) {
            StringBuilder var2 = (new StringBuilder()).append("TZI string for ");
            String var3 = var0.getDisplayName();
            String var4 = var2.append(var3).append(" found in cache.").toString();
            SyncManager.log("CalendarUtility", var4);
         }

         var5 = var1;
      } else {
         String var6 = timeZoneToTziStringImpl(var0);
         sTziStringCache.put(var0, var6);
         var5 = var6;
      }

      return var5;
   }

   static String timeZoneToTziStringImpl(TimeZone var0) {
      byte[] var1 = new byte[172];
      int var2 = -var0.getRawOffset() / '\uea60';
      setLong(var1, 0, var2);
      if(var0.useDaylightTime()) {
         GregorianCalendar[] var3 = new GregorianCalendar[3];
         GregorianCalendar[] var4 = new GregorianCalendar[3];
         if(getDSTCalendars(var0, var3, var4)) {
            CalendarUtilities.RRule var5 = inferRRuleFromCalendars(var3);
            CalendarUtilities.RRule var6 = inferRRuleFromCalendars(var4);
            if(var5 != null && var5.type == 1 && var6 != null && var6.type == 1) {
               int var7 = getTrueTransitionHour(var4[0]);
               int var8 = getTrueTransitionMinute(var4[0]);
               putRuleIntoTimeZoneInformation(var1, 68, var6, var7, var8);
               int var9 = getTrueTransitionHour(var3[0]);
               int var10 = getTrueTransitionMinute(var3[0]);
               putRuleIntoTimeZoneInformation(var1, 152, var5, var9, var10);
            } else {
               long var13 = System.currentTimeMillis();
               long var15 = findNextTransition(var13, var4);
               long var16 = findNextTransition(var13, var3);
               if(var15 != 0L && var16 != 0L) {
                  putTransitionMillisIntoSystemTime(var1, 68, var15);
                  putTransitionMillisIntoSystemTime(var1, 152, var16);
               }
            }
         }

         int var11 = -var0.getDSTSavings() / '\uea60';
         setLong(var1, 168, var11);
      }

      byte[] var12 = Base64.encode(var1, 2);
      return new String(var12);
   }

   static void timeZoneToVTimezone(TimeZone var0, SimpleIcsWriter var1) throws IOException {
      int var2 = var0.getRawOffset() / '\uea60';
      String var3 = utcOffsetString(var2);
      var1.writeTag("BEGIN", "VTIMEZONE");
      String var4 = var0.getID();
      var1.writeTag("TZID", var4);
      String var5 = var0.getDisplayName();
      var1.writeTag("X-LIC-LOCATION", var5);
      if(!var0.useDaylightTime()) {
         writeNoDST(var1, var0, var3);
      } else {
         byte var6 = 3;
         GregorianCalendar[] var7 = new GregorianCalendar[var6];
         GregorianCalendar[] var8 = new GregorianCalendar[var6];
         if(!getDSTCalendars(var0, var7, var8)) {
            writeNoDST(var1, var0, var3);
         } else {
            CalendarUtilities.RRule var9 = inferRRuleFromCalendars(var7);
            CalendarUtilities.RRule var10 = inferRRuleFromCalendars(var8);
            int var11 = var0.getDSTSavings() / '\uea60';
            String var12 = utcOffsetString(var2 + var11);
            boolean var13;
            if(var9 != null && var10 != null) {
               var13 = true;
            } else {
               var13 = false;
            }

            var1.writeTag("BEGIN", "DAYLIGHT");
            var1.writeTag("TZOFFSETFROM", var3);
            var1.writeTag("TZOFFSETTO", var12);
            String var14 = transitionMillisToVCalendarTime(var7[0].getTimeInMillis(), var0, (boolean)1);
            var1.writeTag("DTSTART", var14);
            if(var13) {
               String var15 = var9.toString();
               var1.writeTag("RRULE", var15);
            } else {
               for(int var18 = 1; var18 < var6; ++var18) {
                  String var19 = transitionMillisToVCalendarTime(var7[var18].getTimeInMillis(), var0, (boolean)1);
                  var1.writeTag("RDATE", var19);
               }
            }

            var1.writeTag("END", "DAYLIGHT");
            var1.writeTag("BEGIN", "STANDARD");
            var1.writeTag("TZOFFSETFROM", var12);
            var1.writeTag("TZOFFSETTO", var3);
            String var16 = transitionMillisToVCalendarTime(var8[0].getTimeInMillis(), var0, (boolean)0);
            var1.writeTag("DTSTART", var16);
            if(var13) {
               String var17 = var10.toString();
               var1.writeTag("RRULE", var17);
            } else {
               for(int var20 = 1; var20 < var6; ++var20) {
                  String var21 = transitionMillisToVCalendarTime(var8[var20].getTimeInMillis(), var0, (boolean)1);
                  var1.writeTag("RDATE", var21);
               }
            }

            var1.writeTag("END", "STANDARD");
            var1.writeTag("END", "VTIMEZONE");
         }
      }
   }

   static String tokenFromRrule(String var0, String var1) {
      int var2 = var0.indexOf(var1);
      String var3;
      if(var2 < 0) {
         var3 = null;
      } else {
         int var4 = var0.length();
         int var5 = var1.length();
         var2 += var5;
         int var6 = var2;

         while(true) {
            int var7 = var6 + 1;
            if(var0.charAt(var6) == 59 || var7 == var4) {
               int var8;
               if(var7 == var4) {
                  var8 = var7 + 1;
               } else {
                  var8 = var7;
               }

               int var9 = var8 - 1;
               var3 = var0.substring(var2, var9);
               break;
            }

            var6 = var7;
         }
      }

      return var3;
   }

   static String transitionMillisToVCalendarTime(long var0, TimeZone var2, boolean var3) {
      StringBuilder var4 = new StringBuilder();
      GregorianCalendar var5 = new GregorianCalendar(var2);
      var5.setTimeInMillis(var0);
      int var6 = var5.get(1);
      var4.append(var6);
      String var8 = formatTwo(var5.get(2) + 1);
      var4.append(var8);
      String var10 = formatTwo(var5.get(5));
      var4.append(var10);
      StringBuilder var12 = var4.append('T');
      String var13 = formatTwo(getTrueTransitionHour(var5));
      var4.append(var13);
      String var15 = formatTwo(getTrueTransitionMinute(var5));
      var4.append(var15);
      String var17 = formatTwo(0);
      var4.append(var17);
      return var4.toString();
   }

   private static long transposeAllDayTime(long var0, TimeZone var2, TimeZone var3) {
      GregorianCalendar var4 = new GregorianCalendar(var2);
      var4.setTimeInMillis(var0);
      GregorianCalendar var5 = new GregorianCalendar(var3);
      var5.clear();
      int var6 = var4.get(1);
      int var7 = var4.get(2);
      int var8 = var4.get(5);
      var5.set(var6, var7, var8);
      return var5.getTimeInMillis();
   }

   public static TimeZone tziStringToTimeZone(String var0) {
      TimeZone var1 = (TimeZone)sTimeZoneCache.get(var0);
      if(var1 != null) {
         if(Eas.USER_LOG) {
            StringBuilder var2 = (new StringBuilder()).append(" Using cached TimeZone ");
            String var3 = var1.getDisplayName();
            String var4 = var2.append(var3).toString();
            SyncManager.log("CalendarUtility", var4);
         }
      } else {
         var1 = tziStringToTimeZoneImpl(var0);
         if(var1 == null) {
            SyncManager.alwaysLog("TimeZone not found using default: " + var0);
            TimeZone var5 = TimeZone.getDefault();
         }

         sTimeZoneCache.put(var0, var1);
      }

      return var1;
   }

   static TimeZone tziStringToTimeZoneImpl(String var0) {
      byte[] var1 = Base64.decode(var0, 0);
      String[] var2 = TimeZone.getAvailableIDs(getLong(var1, 0) * -1 * '\uea60');
      TimeZone var42;
      if(var2.length > 0) {
         CalendarUtilities.TimeZoneDate var3 = getTimeZoneDateFromSystemTime(var1, 68);
         TimeZone var4;
         if(var3 == null) {
            var4 = TimeZone.getTimeZone(var2[0]);
            if(Eas.USER_LOG) {
               StringBuilder var5 = (new StringBuilder()).append("TimeZone without DST found by offset: ");
               String var6 = var4.getDisplayName();
               String var7 = var5.append(var6).toString();
               SyncManager.log("CalendarUtility", var7);
            }

            var42 = var4;
         } else {
            CalendarUtilities.TimeZoneDate var8 = getTimeZoneDateFromSystemTime(var1, 152);
            if(var8 == null) {
               var42 = null;
               boolean var9 = false;
            } else {
               long var10 = (long)(getLong(var1, 168) * -1 * '\uea60');
               String[] var12 = var2;
               int var13 = var2.length;
               int var14 = 0;
               boolean var15 = false;

               while(true) {
                  if(var14 >= var13) {
                     var4 = TimeZone.getTimeZone(var2[0]);
                     if(Eas.USER_LOG) {
                        StringBuilder var38 = (new StringBuilder()).append("No TimeZone with correct DST settings; using first: ");
                        String var39 = var4.getDisplayName();
                        String var40 = var38.append(var39).toString();
                        SyncManager.log("CalendarUtility", var40);
                     }

                     var42 = var4;
                     break;
                  }

                  TimeZone var16 = TimeZone.getTimeZone(var12[var14]);
                  long var17 = getMillisAtTimeZoneDateTransition(var16, var8);
                  long var19 = var17 - 60000L;
                  Date var21 = new Date(var19);
                  long var22 = 60000L + var17;
                  Date var24 = new Date(var22);
                  if(!var16.inDaylightTime(var21) && var16.inDaylightTime(var24)) {
                     long var26 = getMillisAtTimeZoneDateTransition(var16, var3);
                     long var28 = 60000L + var10;
                     long var30 = var26 - var28;
                     Date var32 = new Date(var30);
                     long var33 = 60000L + var26;
                     var24 = new Date(var33);
                     if(var16.inDaylightTime(var32) && !var16.inDaylightTime(var24)) {
                        long var35 = (long)var16.getDSTSavings();
                        if(var10 == var35) {
                           var42 = var16;
                           break;
                        }
                     }
                  }

                  ++var14;
               }
            }
         }
      } else {
         var42 = null;
         boolean var41 = false;
      }

      return var42;
   }

   static String utcOffsetString(int var0) {
      StringBuilder var1 = new StringBuilder();
      int var2 = var0 / 60;
      if(var2 < 0) {
         StringBuilder var3 = var1.append('-');
         var2 = 0 - var2;
      } else {
         StringBuilder var9 = var1.append('+');
      }

      int var4 = var0 % 60;
      if(var2 < 10) {
         StringBuilder var5 = var1.append('0');
      }

      var1.append(var2);
      if(var4 < 10) {
         StringBuilder var7 = var1.append('0');
      }

      var1.append(var4);
      return var1.toString();
   }

   private static void writeNoDST(SimpleIcsWriter var0, TimeZone var1, String var2) throws IOException {
      var0.writeTag("BEGIN", "STANDARD");
      var0.writeTag("TZOFFSETFROM", var2);
      var0.writeTag("TZOFFSETTO", var2);
      String var3 = millisToEasDateTime(0L);
      var0.writeTag("DTSTART", var3);
      var0.writeTag("END", "STANDARD");
      var0.writeTag("END", "VTIMEZONE");
   }

   static class TimeZoneDate {

      int day;
      int dayOfWeek;
      int hour;
      int minute;
      int month;
      int time;
      String year;


      TimeZoneDate() {}
   }

   static class RRule {

      static final int RRULE_DATE = 2;
      static final int RRULE_DAY_WEEK = 1;
      static final int RRULE_NONE;
      int date;
      int dayOfWeek;
      int month;
      int type;
      int week;


      RRule(int var1, int var2) {
         this.type = 2;
         this.month = var1;
         this.date = var2;
      }

      RRule(int var1, int var2, int var3) {
         this.type = 1;
         this.month = var1;
         this.dayOfWeek = var2;
         this.week = var3;
      }

      public String toString() {
         String var9;
         if(this.type == 1) {
            StringBuilder var1 = (new StringBuilder()).append("FREQ=YEARLY;BYMONTH=");
            int var2 = this.month;
            StringBuilder var3 = var1.append(var2).append(";BYDAY=");
            int var4 = this.week;
            StringBuilder var5 = var3.append(var4);
            String[] var6 = CalendarUtilities.sDayTokens;
            int var7 = this.dayOfWeek - 1;
            String var8 = var6[var7];
            var9 = var5.append(var8).toString();
         } else {
            StringBuilder var10 = (new StringBuilder()).append("FREQ=YEARLY;BYMONTH=");
            int var11 = this.month;
            StringBuilder var12 = var10.append(var11).append(";BYMONTHDAY=");
            int var13 = this.date;
            var9 = var12.append(var13).toString();
         }

         return var9;
      }
   }
}
