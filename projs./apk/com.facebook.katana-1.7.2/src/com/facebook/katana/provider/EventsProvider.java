package com.facebook.katana.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import com.facebook.katana.provider.FacebookDatabaseHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventsProvider extends ContentProvider {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final String AUTHORITY = "com.facebook.katana.provider.EventsProvider";
   private static final String BASE_CONTENT_URI = "content://com.facebook.katana.provider.EventsProvider/";
   private static final int EVENTS = 1;
   public static final Uri EVENTS_CONTENT_URI;
   private static final HashMap<String, String> EVENTS_PROJECTION_MAP;
   private static final String EVENTS_TABLE = "events";
   private static final int EVENT_EID = 3;
   public static final Uri EVENT_EID_CONTENT_URI;
   private static final int EVENT_ID = 2;
   private static final String SQL_EVENTS = "CREATE TABLE events (_id INTEGER PRIMARY KEY,event_id INT,event_name TEXT,tagline TEXT,image_url TEXT,medium_image_url TEXT,host TEXT,description TEXT,event_type TEXT,event_subtype TEXT,start_time INT,end_time INT,creator_id INT,display_name TEXT,creator_image_url TEXT,location TEXT,venue BLOB,hide_guest_list INT,rsvp_status INT);";
   private static final UriMatcher URL_MATCHER;
   private FacebookDatabaseHelper mDbHelper;


   static {
      byte var0;
      if(!EventsProvider.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      EVENTS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.EventsProvider/events");
      EVENT_EID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.EventsProvider/events/eid");
      URL_MATCHER = new UriMatcher(-1);
      URL_MATCHER.addURI("com.facebook.katana.provider.EventsProvider", "events", 1);
      URL_MATCHER.addURI("com.facebook.katana.provider.EventsProvider", "events/#", 2);
      URL_MATCHER.addURI("com.facebook.katana.provider.EventsProvider", "events/eid/#", 3);
      EVENTS_PROJECTION_MAP = new HashMap();
      Object var1 = EVENTS_PROJECTION_MAP.put("_id", "_id");
      Object var2 = EVENTS_PROJECTION_MAP.put("event_id", "event_id");
      Object var3 = EVENTS_PROJECTION_MAP.put("event_name", "event_name");
      Object var4 = EVENTS_PROJECTION_MAP.put("tagline", "tagline");
      Object var5 = EVENTS_PROJECTION_MAP.put("image_url", "image_url");
      Object var6 = EVENTS_PROJECTION_MAP.put("medium_image_url", "medium_image_url");
      Object var7 = EVENTS_PROJECTION_MAP.put("host", "host");
      Object var8 = EVENTS_PROJECTION_MAP.put("description", "description");
      Object var9 = EVENTS_PROJECTION_MAP.put("event_type", "event_type");
      Object var10 = EVENTS_PROJECTION_MAP.put("event_subtype", "event_subtype");
      Object var11 = EVENTS_PROJECTION_MAP.put("start_time", "start_time");
      Object var12 = EVENTS_PROJECTION_MAP.put("end_time", "end_time");
      Object var13 = EVENTS_PROJECTION_MAP.put("creator_id", "creator_id");
      Object var14 = EVENTS_PROJECTION_MAP.put("display_name", "display_name");
      Object var15 = EVENTS_PROJECTION_MAP.put("creator_image_url", "creator_image_url");
      Object var16 = EVENTS_PROJECTION_MAP.put("location", "location");
      Object var17 = EVENTS_PROJECTION_MAP.put("venue", "venue");
      Object var18 = EVENTS_PROJECTION_MAP.put("hide_guest_list", "hide_guest_list");
      Object var19 = EVENTS_PROJECTION_MAP.put("rsvp_status", "rsvp_status");
   }

   public EventsProvider() {}

   public static String[] getTableNames() {
      String[] var0 = new String[]{"events"};
      return var0;
   }

   public static String[] getTableSQLs() {
      String[] var0 = new String[]{"CREATE TABLE events (_id INTEGER PRIMARY KEY,event_id INT,event_name TEXT,tagline TEXT,image_url TEXT,medium_image_url TEXT,host TEXT,description TEXT,event_type TEXT,event_subtype TEXT,start_time INT,end_time INT,creator_id INT,display_name TEXT,creator_image_url TEXT,location TEXT,venue BLOB,hide_guest_list INT,rsvp_status INT);"};
      return var0;
   }

   public int bulkInsert(Uri var1, ContentValues[] var2) {
      return this.insertHelper(var1, var2, (List)null);
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var6;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6 = var4.delete("events", var2, var3);
         break;
      case 2:
         String var7 = (String)var1.getPathSegments().get(1);
         String var8 = "_id=" + var7;
         var6 = var4.delete("events", var8, (String[])null);
         break;
      case 3:
         String var9 = (String)var1.getPathSegments().get(2);
         String var10 = "event_id=" + var9;
         var6 = var4.delete("events", var10, (String[])null);
         break;
      default:
         String var5 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var5);
      }

      if(var6 > 0) {
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
      }

      return var6;
   }

   public String getType(Uri var1) {
      switch(URL_MATCHER.match(var1)) {
      case 1:
      case 2:
      case 3:
         return "vnd.android.cursor.item/vnd.com.facebook.katana.provider.events";
      default:
         String var2 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var2);
      }
   }

   public Uri insert(Uri var1, ContentValues var2) {
      ContentValues[] var3 = new ContentValues[]{var2};
      ArrayList var4 = new ArrayList();
      int var5 = this.insertHelper(var1, var3, var4);
      if(!$assertionsDisabled && var5 != 1) {
         throw new AssertionError();
      } else if(!$assertionsDisabled && var4.size() != 1) {
         throw new AssertionError();
      } else {
         return (Uri)var4.get(0);
      }
   }

   protected int insertHelper(Uri var1, ContentValues[] var2, List<Uri> var3) {
      int var4 = 0;
      SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
      switch(URL_MATCHER.match(var1)) {
      case 1:
         int var7 = 0;

         while(true) {
            int var8 = var2.length;
            if(var7 >= var8) {
               if(var4 > 0) {
                  this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
                  return var4;
               }

               String var16 = "Failed to insert rows into " + var1;
               throw new SQLException(var16);
            }

            ContentValues var9 = var2[var7];
            long var10 = var5.insert("events", "event_name", var9);
            if(var10 > 0L) {
               ++var4;
               if(var3 != null) {
                  Uri var12 = EVENTS_CONTENT_URI;
                  String var13 = Long.toString(var10);
                  Uri var14 = Uri.withAppendedPath(var12, var13);
                  var3.add(var14);
               }
            }

            ++var7;
         }
      default:
         String var6 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var6);
      }
   }

   public boolean onCreate() {
      FacebookDatabaseHelper var1 = FacebookDatabaseHelper.getDatabaseHelper(this.getContext());
      this.mDbHelper = var1;
      boolean var2;
      if(this.mDbHelper.getReadableDatabase() != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      SQLiteQueryBuilder var6 = new SQLiteQueryBuilder();
      String var9;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6.setTables("events");
         HashMap var8 = EVENTS_PROJECTION_MAP;
         var6.setProjectionMap(var8);
         var9 = "start_time ASC";
         break;
      case 2:
         var6.setTables("events");
         StringBuilder var17 = (new StringBuilder()).append("_id=");
         String var18 = (String)var1.getPathSegments().get(1);
         String var19 = var17.append(var18).toString();
         var6.appendWhere(var19);
         HashMap var20 = EVENTS_PROJECTION_MAP;
         var6.setProjectionMap(var20);
         var9 = "start_time ASC";
         break;
      case 3:
         var6.setTables("events");
         StringBuilder var21 = (new StringBuilder()).append("event_id=");
         String var22 = (String)var1.getPathSegments().get(2);
         String var23 = var21.append(var22).toString();
         var6.appendWhere(var23);
         HashMap var24 = EVENTS_PROJECTION_MAP;
         var6.setProjectionMap(var24);
         var9 = "start_time ASC";
         break;
      default:
         String var7 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var7);
      }

      String var10;
      if(TextUtils.isEmpty(var5)) {
         var10 = var9;
      } else {
         var10 = var5;
      }

      SQLiteDatabase var11 = this.mDbHelper.getReadableDatabase();
      Cursor var15 = var6.query(var11, var2, var3, var4, (String)null, (String)null, var10, (String)null);
      ContentResolver var16 = this.getContext().getContentResolver();
      var15.setNotificationUri(var16, var1);
      return var15;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
      int var7;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var7 = var5.update("events", var2, var3, var4);
         break;
      case 2:
         String var8 = (String)var1.getPathSegments().get(1);
         String var9 = "_id=" + var8;
         var7 = var5.update("events", var2, var9, (String[])null);
         break;
      case 3:
         String var10 = (String)var1.getPathSegments().get(2);
         String var11 = "event_id=" + var10;
         var7 = var5.update("events", var2, var11, (String[])null);
         break;
      default:
         String var6 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var6);
      }

      if(var7 > 0) {
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
      }

      return var7;
   }

   public static final class EventColumns implements BaseColumns {

      public static final String CREATOR_DISPLAY_NAME = "display_name";
      public static final String CREATOR_ID = "creator_id";
      public static final String CREATOR_IMAGE_URL = "creator_image_url";
      public static final String DEFAULT_SORT_ORDER = "start_time ASC";
      public static final String DESCRIPTION = "description";
      public static final String END_TIME = "end_time";
      public static final String EVENT_ID = "event_id";
      public static final String EVENT_NAME = "event_name";
      public static final String EVENT_SUBTYPE = "event_subtype";
      public static final String EVENT_TYPE = "event_type";
      public static final String HIDE_GUEST_LIST = "hide_guest_list";
      public static final String HOST = "host";
      public static final String IMAGE_URL = "image_url";
      public static final String LOCATION = "location";
      public static final String MEDIUM_IMAGE_URL = "medium_image_url";
      public static final String RSVP_STATUS = "rsvp_status";
      public static final String START_TIME = "start_time";
      public static final String TAGLINE = "tagline";
      public static final String VENUE = "venue";


      public EventColumns() {}
   }
}
