package com.seven.Z7.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.provider.BaseColumns;
import android.text.TextUtils;
import com.seven.Z7.provider.SyncConstValue;

public final class Calendar {

   public static final String AUTHORITY = "calendar";
   public static final Uri CONTENT_URI = Uri.parse("content://calendar");
   public static final String EVENT_BEGIN_TIME = "beginTime";
   public static final String EVENT_END_TIME = "endTime";
   public static final String EVENT_REMINDER_ACTION = "android.intent.action.EVENT_REMINDER";
   public static final String TAG = "Calendar";


   public Calendar() {}

   public interface AttendeesColumns {

      String ATTENDEE_EMAIL = "attendeeEmail";
      String ATTENDEE_NAME = "attendeeName";
      String ATTENDEE_RELATIONSHIP = "attendeeRelationship";
      String ATTENDEE_STATUS = "attendeeStatus";
      int ATTENDEE_STATUS_ACCEPTED = 1;
      int ATTENDEE_STATUS_DECLINED = 2;
      int ATTENDEE_STATUS_INVITED = 3;
      int ATTENDEE_STATUS_NONE = 0;
      int ATTENDEE_STATUS_TENTATIVE = 4;
      String ATTENDEE_TYPE = "attendeeType";
      String EVENT_ID = "event_id";
      int RELATIONSHIP_ATTENDEE = 1;
      int RELATIONSHIP_NONE = 0;
      int RELATIONSHIP_ORGANIZER = 2;
      int RELATIONSHIP_PERFORMER = 3;
      int RELATIONSHIP_SPEAKER = 4;
      int TYPE_NONE = 0;
      int TYPE_OPTIONAL = 2;
      int TYPE_REQUIRED = 1;

   }

   public static class Calendars implements BaseColumns, SyncConstValue, Calendar.CalendarsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://calendar/calendars");
      public static final String DEFAULT_SORT_ORDER = "displayName";
      public static final String DISPLAY_NAME = "displayName";
      public static final String HIDDEN = "hidden";
      public static final String LOCATION = "location";
      public static final String NAME = "name";
      public static final String OWNER_ACCOUNT = "ownerAccount";
      public static final String URL = "url";


      public Calendars() {}

      public static int delete(ContentResolver var0, String var1, String[] var2) {
         Uri var3 = CONTENT_URI;
         return var0.delete(var3, var1, var2);
      }

      public static final Cursor query(ContentResolver var0, String[] var1, String var2, String var3) {
         Uri var4 = CONTENT_URI;
         String var5;
         if(var3 == null) {
            var5 = "displayName";
         } else {
            var5 = var3;
         }

         return var0.query(var4, var1, var2, (String[])null, var5);
      }
   }

   public static final class Instances implements BaseColumns, Calendar.EventsColumns, Calendar.CalendarsColumns {

      public static final String BEGIN = "begin";
      public static final Uri CONTENT_BY_DAY_URI = Uri.parse("content://calendar/instances/whenbyday");
      public static final Uri CONTENT_URI = Uri.parse("content://calendar/instances/when");
      public static final String DEFAULT_SORT_ORDER = "begin ASC";
      public static final String END = "end";
      public static final String END_DAY = "endDay";
      public static final String END_MINUTE = "endMinute";
      public static final String EVENT_ID = "event_id";
      public static final String SORT_CALENDAR_VIEW = "begin ASC, end DESC, title ASC";
      public static final String START_DAY = "startDay";
      public static final String START_MINUTE = "startMinute";


      public Instances() {}

      public static final Cursor query(ContentResolver var0, String[] var1, long var2, long var4) {
         Builder var6 = CONTENT_URI.buildUpon();
         ContentUris.appendId(var6, var2);
         ContentUris.appendId(var6, var4);
         Uri var9 = var6.build();
         return var0.query(var9, var1, "selected=1", (String[])null, "begin ASC");
      }

      public static final Cursor query(ContentResolver var0, String[] var1, long var2, long var4, String var6, String var7) {
         Builder var8 = CONTENT_URI.buildUpon();
         ContentUris.appendId(var8, var2);
         ContentUris.appendId(var8, var4);
         if(TextUtils.isEmpty(var6)) {
            var6 = "selected=1";
         } else {
            var6 = "(" + var6 + ") AND " + "selected" + "=1";
         }

         Uri var11 = var8.build();
         Object var12 = null;
         String var13;
         if(var7 == null) {
            var13 = "begin ASC";
         } else {
            var13 = var7;
         }

         return var0.query(var11, var1, var6, (String[])var12, var13);
      }
   }

   public static final class Reminders implements BaseColumns, Calendar.RemindersColumns, Calendar.EventsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://calendar/reminders");
      public static final String TABLE_NAME = "Reminders";


      public Reminders() {}
   }

   public interface BusyBitsColumns {

      String ALL_DAY_COUNT = "allDayCount";
      String BUSYBITS = "busyBits";
      String DAY = "day";

   }

   public interface RemindersColumns {

      String EVENT_ID = "event_id";
      String METHOD = "method";
      int METHOD_ALERT = 1;
      int METHOD_DEFAULT = 0;
      int METHOD_EMAIL = 2;
      int METHOD_SMS = 3;
      String MINUTES = "minutes";
      int MINUTES_DEFAULT = 255;

   }

   public static final class ExtendedProperties implements BaseColumns, Calendar.ExtendedPropertiesColumns, Calendar.EventsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://calendar/extendedproperties");


      public ExtendedProperties() {}
   }

   public static final class CalendarMetaData implements Calendar.CalendarMetaDataColumns {

      public CalendarMetaData() {}
   }

   public static final class Events implements BaseColumns, SyncConstValue, Calendar.EventsColumns, Calendar.CalendarsColumns {

      private static final String[] ATTENDEES_COLUMNS;
      public static final Uri CONTENT_URI;
      public static final String DEFAULT_SORT_ORDER = "";
      public static final Uri DELETED_CONTENT_URI;
      private static final String[] FETCH_ENTRY_COLUMNS;


      static {
         String[] var0 = new String[]{"_sync_account", "_sync_id"};
         FETCH_ENTRY_COLUMNS = var0;
         String[] var1 = new String[]{"attendeeName", "attendeeEmail", "attendeeRelationship", "attendeeType", "attendeeStatus"};
         ATTENDEES_COLUMNS = var1;
         CONTENT_URI = Uri.parse("content://calendar/events");
         DELETED_CONTENT_URI = Uri.parse("content://calendar/deleted_events");
      }

      public Events() {}
   }

   public static final class BusyBits implements Calendar.BusyBitsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://calendar/busybits/when");
      public static final int INTERVALS_PER_DAY = 24;
      public static final int MINUTES_PER_BUSY_INTERVAL = 60;
      public static final String[] PROJECTION;


      static {
         String[] var0 = new String[]{"day", "busyBits", "allDayCount"};
         PROJECTION = var0;
      }

      public BusyBits() {}

      public static final Cursor query(ContentResolver var0, int var1, int var2) {
         Cursor var3;
         if(var2 < 1) {
            var3 = null;
         } else {
            int var4 = var1 + var2 - 1;
            Builder var5 = CONTENT_URI.buildUpon();
            long var6 = (long)var1;
            ContentUris.appendId(var5, var6);
            long var9 = (long)var4;
            ContentUris.appendId(var5, var9);
            Uri var12 = var5.build();
            String[] var13 = PROJECTION;
            Object var15 = null;
            var3 = var0.query(var12, var13, (String)null, (String[])var15, "day");
         }

         return var3;
      }
   }

   public interface CalendarAlertsColumns {

      String ALARM_TIME = "alarmTime";
      String BEGIN = "begin";
      String CREATION_TIME = "creationTime";
      String DEFAULT_SORT_ORDER = "alarmTime ASC,begin ASC,title ASC";
      int DISMISSED = 2;
      String END = "end";
      String EVENT_ID = "event_id";
      int FIRED = 1;
      String MINUTES = "minutes";
      String NOTIFY_TIME = "notifyTime";
      String RECEIVED_TIME = "receivedTime";
      int SCHEDULED = 0;
      String STATE = "state";

   }

   public static final class CalendarAlerts implements BaseColumns, Calendar.CalendarAlertsColumns, Calendar.EventsColumns, Calendar.CalendarsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://calendar/calendar_alerts");
      public static final Uri CONTENT_URI_BY_INSTANCE = Uri.parse("content://calendar/calendar_alerts/by_instance");
      public static final String TABLE_NAME = "CalendarAlerts";


      public CalendarAlerts() {}

      public static final boolean alarmExists(ContentResolver var0, long var1, long var3, long var5) {
         String var7 = "event_id=" + var1 + " AND " + "begin" + "=" + var3 + " AND " + "alarmTime" + "=" + var5;
         String[] var8 = new String[]{"alarmTime"};
         Cursor var9 = query(var0, var8, var7, (String[])null);
         boolean var10 = false;
         if(var9 != null) {
            boolean var14 = false;

            int var11;
            try {
               var14 = true;
               var11 = var9.getCount();
               var14 = false;
            } finally {
               if(var14) {
                  if(var9 != null) {
                     var9.close();
                  }

               }
            }

            if(var11 > 0) {
               var10 = true;
            }
         }

         if(var9 != null) {
            var9.close();
         }

         return var10;
      }

      public static final long findNextAlarmTime(ContentResolver var0, long var1) {
         String var3 = "alarmTime>=" + var1;
         String[] var4 = new String[]{"alarmTime"};
         Cursor var5 = query(var0, var4, var3, (String[])null);
         long var6 = 65535L;
         if(var5 != null) {
            label62: {
               boolean var12 = false;

               long var8;
               try {
                  var12 = true;
                  if(!var5.moveToFirst()) {
                     var12 = false;
                     break label62;
                  }

                  var8 = var5.getLong(0);
                  var12 = false;
               } finally {
                  if(var12) {
                     if(var5 != null) {
                        var5.close();
                     }

                  }
               }

               var6 = var8;
            }
         }

         if(var5 != null) {
            var5.close();
         }

         return var6;
      }

      public static final Uri insert(ContentResolver var0, long var1, long var3, long var5, long var7, int var9) {
         ContentValues var10 = new ContentValues();
         Long var11 = Long.valueOf(var1);
         var10.put("event_id", var11);
         Long var12 = Long.valueOf(var3);
         var10.put("begin", var12);
         Long var13 = Long.valueOf(var5);
         var10.put("end", var13);
         Long var14 = Long.valueOf(var7);
         var10.put("alarmTime", var14);
         Long var15 = Long.valueOf(System.currentTimeMillis());
         var10.put("creationTime", var15);
         Integer var16 = Integer.valueOf(0);
         var10.put("receivedTime", var16);
         Integer var17 = Integer.valueOf(0);
         var10.put("notifyTime", var17);
         Integer var18 = Integer.valueOf(0);
         var10.put("state", var18);
         Integer var19 = Integer.valueOf(var9);
         var10.put("minutes", var19);
         Uri var20 = CONTENT_URI;
         return var0.insert(var20, var10);
      }

      public static final Cursor query(ContentResolver var0, String[] var1, String var2, String[] var3) {
         Uri var4 = CONTENT_URI;
         return var0.query(var4, var1, var2, var3, "alarmTime ASC,begin ASC,title ASC");
      }
   }

   public interface CalendarMetaDataColumns {

      String LOCAL_TIMEZONE = "localTimezone";
      String MAX_BUSYBITS = "maxBusyBits";
      String MAX_INSTANCE = "maxInstance";
      String MIN_BUSYBITS = "minBusyBits";
      String MIN_INSTANCE = "minInstance";

   }

   public interface ExtendedPropertiesColumns {

      String EVENT_ID = "event_id";
      String NAME = "name";
      String VALUE = "value";

   }

   public interface EventsColumns {

      String ALL_DAY = "allDay";
      String CALENDAR_ID = "calendar_id";
      String CAN_INVITE_OTHERS = "canInviteOthers";
      String COMMENTS_URI = "commentsUri";
      String DESCRIPTION = "description";
      String DTEND = "dtend";
      String DTSTART = "dtstart";
      String DURATION = "duration";
      String EVENT_LOCATION = "eventLocation";
      String EVENT_TIMEZONE = "eventTimezone";
      String EXDATE = "exdate";
      String EXRULE = "exrule";
      String GUESTS_CAN_INVITE_OTHERS = "guestsCanInviteOthers";
      String GUESTS_CAN_MODIFY = "guestsCanModify";
      String GUESTS_CAN_SEE_GUESTS = "guestsCanSeeGuests";
      String HAS_ALARM = "hasAlarm";
      String HAS_ATTENDEE_DATA = "hasAttendeeData";
      String HAS_EXTENDED_PROPERTIES = "hasExtendedProperties";
      String HTML_URI = "htmlUri";
      String LAST_DATE = "lastDate";
      String ORGANIZER = "organizer";
      String ORIGINAL_ALL_DAY = "originalAllDay";
      String ORIGINAL_EVENT = "originalEvent";
      String ORIGINAL_INSTANCE_TIME = "originalInstanceTime";
      String OWNER_ACCOUNT = "ownerAccount";
      String RDATE = "rdate";
      String RRULE = "rrule";
      String SELF_ATTENDEE_STATUS = "selfAttendeeStatus";
      String STATUS = "eventStatus";
      int STATUS_CANCELED = 2;
      int STATUS_CONFIRMED = 1;
      int STATUS_TENTATIVE = 0;
      String TITLE = "title";
      String TRANSPARENCY = "transparency";
      int TRANSPARENCY_OPAQUE = 0;
      int TRANSPARENCY_TRANSPARENT = 1;
      String VISIBILITY = "visibility";
      int VISIBILITY_CONFIDENTIAL = 1;
      int VISIBILITY_DEFAULT = 0;
      int VISIBILITY_PRIVATE = 2;
      int VISIBILITY_PUBLIC = 3;

   }

   public static final class Attendees implements BaseColumns, Calendar.AttendeesColumns, Calendar.EventsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://calendar/attendees");


      public Attendees() {}
   }

   public interface CalendarsColumns {

      String ACCESS_LEVEL = "access_level";
      String COLOR = "color";
      int CONTRIBUTOR_ACCESS = 500;
      int EDITOR_ACCESS = 600;
      int FREEBUSY_ACCESS = 100;
      int NO_ACCESS = 0;
      int OVERRIDE_ACCESS = 400;
      int OWNER_ACCESS = 700;
      int READ_ACCESS = 200;
      int RESPOND_ACCESS = 300;
      int ROOT_ACCESS = 800;
      String SELECTED = "selected";
      String SOURCE_ID = "sourceid";
      String SYNC_EVENTS = "sync_events";
      String SYNC_STATE = "sync_state";
      String TIMEZONE = "timezone";

   }
}
