package com.android.exchange.provider;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Entity;
import android.content.EntityIterator;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.provider.SyncStateContract.Columns;

public final class Calendar {

   public static final String AUTHORITY = "com.android.calendar";
   public static final String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";
   public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar");
   public static final String EVENT_BEGIN_TIME = "beginTime";
   public static final String EVENT_END_TIME = "endTime";
   public static final String EVENT_REMINDER_ACTION = "android.intent.action.EVENT_REMINDER";
   public static final String TAG = "Calendar";


   public Calendar() {}

   public static final class Reminders implements BaseColumns, Calendar.RemindersColumns, Calendar.EventsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/reminders");
      public static final String TABLE_NAME = "Reminders";


      public Reminders() {}
   }

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

   public static final class Events implements BaseColumns, Calendar.EventsColumns, Calendar.CalendarsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/events");
      public static final String DEFAULT_SORT_ORDER = "";
      public static final Uri DELETED_CONTENT_URI = Uri.parse("content://com.android.calendar/deleted_events");


      public Events() {}
   }

   public interface CalendarsColumns {

      String ACCESS_LEVEL = "access_level";
      String ACCOUNT_NAME = "account_name";
      String ACCOUNT_TYPE = "account_type";
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
      String SYNC_EVENTS = "sync_events";
      String SYNC_STATE = "sync_state";
      String TIMEZONE = "timezone";
      String _SYNC_ACCOUNT = "_sync_account";
      String _SYNC_ACCOUNT_TYPE = "_sync_account_type";
      String _SYNC_DATA = "_sync_local_id";
      String _SYNC_DIRTY = "_sync_dirty";
      String _SYNC_ID = "_sync_id";
      String _SYNC_MARK = "_sync_mark";
      String _SYNC_TIME = "_sync_time";
      String _SYNC_VERSION = "_sync_version";

   }

   public static class Calendars implements BaseColumns, Calendar.CalendarsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/calendars");
      public static final String DEFAULT_SORT_ORDER = "displayName";
      public static final String DISPLAY_NAME = "displayName";
      public static final String HIDDEN = "hidden";
      public static final String LOCATION = "location";
      public static final String NAME = "name";
      public static final String ORGANIZER_CAN_RESPOND = "organizerCanRespond";
      public static final String OWNER_ACCOUNT = "ownerAccount";
      public static final String URL = "url";


      public Calendars() {}
   }

   public static final class ExtendedProperties implements BaseColumns, Calendar.ExtendedPropertiesColumns, Calendar.EventsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/extendedproperties");


      public ExtendedProperties() {}
   }

   public static final class EventsEntity implements BaseColumns, Calendar.EventsColumns, Calendar.CalendarsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/event_entities");


      public EventsEntity() {}

      public static EntityIterator newEntityIterator(Cursor var0, ContentProviderClient var1) {
         return new Calendar.EventsEntity.EntityIteratorImpl(var0, var1);
      }

      public static EntityIterator newEntityIterator(Cursor var0, ContentResolver var1) {
         return new Calendar.EventsEntity.EntityIteratorImpl(var0, var1);
      }

      private static class EntityIteratorImpl extends Calendar.EventsEntity.CursorEntityIterator {

         private static final String[] ATTENDEES_PROJECTION;
         private static final int COLUMN_ATTENDEE_EMAIL = 1;
         private static final int COLUMN_ATTENDEE_NAME = 0;
         private static final int COLUMN_ATTENDEE_RELATIONSHIP = 2;
         private static final int COLUMN_ATTENDEE_STATUS = 4;
         private static final int COLUMN_ATTENDEE_TYPE = 3;
         private static final int COLUMN_ID = 0;
         private static final int COLUMN_METHOD = 1;
         private static final int COLUMN_MINUTES = 0;
         private static final int COLUMN_NAME = 1;
         private static final int COLUMN_VALUE = 2;
         private static final String[] EXTENDED_PROJECTION;
         private static final String[] REMINDERS_PROJECTION;
         private static final String WHERE_EVENT_ID = "event_id=?";
         private final ContentProviderClient mProvider;
         private final ContentResolver mResolver;


         static {
            String[] var0 = new String[]{"minutes", "method"};
            REMINDERS_PROJECTION = var0;
            String[] var1 = new String[]{"attendeeName", "attendeeEmail", "attendeeRelationship", "attendeeType", "attendeeStatus"};
            ATTENDEES_PROJECTION = var1;
            String[] var2 = new String[]{"_id", "name", "value"};
            EXTENDED_PROJECTION = var2;
         }

         public EntityIteratorImpl(Cursor var1, ContentProviderClient var2) {
            super(var1);
            this.mResolver = null;
            this.mProvider = var2;
         }

         public EntityIteratorImpl(Cursor var1, ContentResolver var2) {
            super(var1);
            this.mResolver = var2;
            this.mProvider = null;
         }

         public Entity getEntityAndIncrementCursor(Cursor var1) throws RemoteException {
            int var2 = var1.getColumnIndexOrThrow("_id");
            long var3 = var1.getLong(var2);
            ContentValues var5 = new ContentValues();
            Long var6 = Long.valueOf(var3);
            var5.put("_id", var6);
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "calendar_id");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "htmlUri");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "title");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "description");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "eventLocation");
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "eventStatus");
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "selfAttendeeStatus");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "commentsUri");
            DatabaseUtils.cursorLongToContentValuesIfPresent(var1, var5, "dtstart");
            DatabaseUtils.cursorLongToContentValuesIfPresent(var1, var5, "dtend");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "duration");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "eventTimezone");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "allDay");
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "visibility");
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "transparency");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "hasAlarm");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "hasExtendedProperties");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "rrule");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "rdate");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "exrule");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "exdate");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "originalEvent");
            DatabaseUtils.cursorLongToContentValuesIfPresent(var1, var5, "originalInstanceTime");
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "originalAllDay");
            DatabaseUtils.cursorLongToContentValuesIfPresent(var1, var5, "lastDate");
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "hasAttendeeData");
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "guestsCanInviteOthers");
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "guestsCanModify");
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "guestsCanSeeGuests");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "organizer");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "_sync_id");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "_sync_local_id");
            DatabaseUtils.cursorLongToContentValuesIfPresent(var1, var5, "_sync_dirty");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "_sync_version");
            DatabaseUtils.cursorIntToContentValuesIfPresent(var1, var5, "deleted");
            DatabaseUtils.cursorStringToContentValuesIfPresent(var1, var5, "url");
            Entity var7 = new Entity(var5);
            Cursor var13;
            if(this.mResolver != null) {
               ContentResolver var8 = this.mResolver;
               Uri var9 = Calendar.Reminders.CONTENT_URI;
               String[] var10 = REMINDERS_PROJECTION;
               String[] var11 = new String[1];
               String var12 = Long.toString(var3);
               var11[0] = var12;
               var13 = var8.query(var9, var10, "event_id=?", var11, (String)null);
            } else {
               ContentProviderClient var19 = this.mProvider;
               Uri var20 = Calendar.Reminders.CONTENT_URI;
               String[] var21 = REMINDERS_PROJECTION;
               String[] var22 = new String[1];
               String var23 = Long.toString(var3);
               var22[0] = var23;
               var13 = var19.query(var20, var21, "event_id=?", var22, (String)null);
            }

            try {
               while(var13.moveToNext()) {
                  ContentValues var14 = new ContentValues();
                  Integer var15 = Integer.valueOf(var13.getInt(0));
                  var14.put("minutes", var15);
                  Integer var16 = Integer.valueOf(var13.getInt(1));
                  var14.put("method", var16);
                  Uri var17 = Calendar.Reminders.CONTENT_URI;
                  var7.addSubValue(var17, var14);
               }
            } finally {
               var13.close();
            }

            Cursor var29;
            if(this.mResolver != null) {
               ContentResolver var24 = this.mResolver;
               Uri var25 = Calendar.Attendees.CONTENT_URI;
               String[] var26 = ATTENDEES_PROJECTION;
               String[] var27 = new String[1];
               String var28 = Long.toString(var3);
               var27[0] = var28;
               var29 = var24.query(var25, var26, "event_id=?", var27, (String)null);
            } else {
               ContentProviderClient var38 = this.mProvider;
               Uri var39 = Calendar.Attendees.CONTENT_URI;
               String[] var40 = ATTENDEES_PROJECTION;
               String[] var41 = new String[1];
               String var42 = Long.toString(var3);
               var41[0] = var42;
               var29 = var38.query(var39, var40, "event_id=?", var41, (String)null);
            }

            try {
               while(var29.moveToNext()) {
                  ContentValues var30 = new ContentValues();
                  String var31 = var29.getString(0);
                  var30.put("attendeeName", var31);
                  String var32 = var29.getString(1);
                  var30.put("attendeeEmail", var32);
                  Integer var33 = Integer.valueOf(var29.getInt(2));
                  var30.put("attendeeRelationship", var33);
                  Integer var34 = Integer.valueOf(var29.getInt(3));
                  var30.put("attendeeType", var34);
                  Integer var35 = Integer.valueOf(var29.getInt(4));
                  var30.put("attendeeStatus", var35);
                  Uri var36 = Calendar.Attendees.CONTENT_URI;
                  var7.addSubValue(var36, var30);
               }
            } finally {
               var29.close();
            }

            Cursor var48;
            if(this.mResolver != null) {
               ContentResolver var43 = this.mResolver;
               Uri var44 = Calendar.ExtendedProperties.CONTENT_URI;
               String[] var45 = EXTENDED_PROJECTION;
               String[] var46 = new String[1];
               String var47 = Long.toString(var3);
               var46[0] = var47;
               var48 = var43.query(var44, var45, "event_id=?", var46, (String)null);
            } else {
               ContentProviderClient var55 = this.mProvider;
               Uri var56 = Calendar.ExtendedProperties.CONTENT_URI;
               String[] var57 = EXTENDED_PROJECTION;
               String[] var58 = new String[1];
               String var59 = Long.toString(var3);
               var58[0] = var59;
               var48 = var55.query(var56, var57, "event_id=?", var58, (String)null);
            }

            try {
               while(var48.moveToNext()) {
                  ContentValues var49 = new ContentValues();
                  String var50 = var48.getString(0);
                  var49.put("_id", var50);
                  String var51 = var48.getString(1);
                  var49.put("name", var51);
                  String var52 = var48.getString(2);
                  var49.put("value", var52);
                  Uri var53 = Calendar.ExtendedProperties.CONTENT_URI;
                  var7.addSubValue(var53, var49);
               }
            } finally {
               var48.close();
            }

            boolean var60 = var1.moveToNext();
            return var7;
         }
      }

      private abstract static class CursorEntityIterator implements EntityIterator {

         private final Cursor mCursor;
         private boolean mIsClosed = 0;


         public CursorEntityIterator(Cursor var1) {
            this.mCursor = var1;
            boolean var2 = this.mCursor.moveToFirst();
         }

         public final void close() {
            if(this.mIsClosed) {
               throw new IllegalStateException("closing when already closed");
            } else {
               this.mIsClosed = (boolean)1;
               this.mCursor.close();
            }
         }

         public abstract Entity getEntityAndIncrementCursor(Cursor var1) throws RemoteException;

         public final boolean hasNext() {
            if(this.mIsClosed) {
               throw new IllegalStateException("calling hasNext() when the iterator is closed");
            } else {
               boolean var1;
               if(!this.mCursor.isAfterLast()) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               return var1;
            }
         }

         public Entity next() {
            if(this.mIsClosed) {
               throw new IllegalStateException("calling next() when the iterator is closed");
            } else if(!this.hasNext()) {
               throw new IllegalStateException("you may only call next() if hasNext() is true");
            } else {
               try {
                  Cursor var1 = this.mCursor;
                  Entity var2 = this.getEntityAndIncrementCursor(var1);
                  return var2;
               } catch (RemoteException var4) {
                  throw new RuntimeException("caught a remote exception, this process will die soon", var4);
               }
            }
         }

         public void remove() {
            throw new UnsupportedOperationException("remove not supported by EntityIterators");
         }

         public final void reset() {
            if(this.mIsClosed) {
               throw new IllegalStateException("calling reset() when the iterator is closed");
            } else {
               boolean var1 = this.mCursor.moveToFirst();
            }
         }
      }
   }

   public static final class Attendees implements BaseColumns, Calendar.AttendeesColumns, Calendar.EventsColumns {

      public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/attendees");


      public Attendees() {}
   }

   public interface EventsColumns {

      String ALL_DAY = "allDay";
      String CALENDAR_ID = "calendar_id";
      String CAN_INVITE_OTHERS = "canInviteOthers";
      String COMMENTS_URI = "commentsUri";
      String DELETED = "deleted";
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
      String SYNC_ADAPTER_DATA = "syncAdapterData";
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

   public static final class SyncState implements Columns {

      public static final String CONTENT_DIRECTORY = "syncstate";
      public static final Uri CONTENT_URI = Uri.withAppendedPath(Calendar.CONTENT_URI, "syncstate");


      private SyncState() {}
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

   public interface ExtendedPropertiesColumns {

      String EVENT_ID = "event_id";
      String NAME = "name";
      String VALUE = "value";

   }
}
