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
import com.facebook.katana.provider.FacebookDatabaseHelper;
import java.util.HashMap;

public class LoggingProvider extends ContentProvider {

   private static final String AUTHORITY = "com.facebook.katana.provider.LoggingProvider";
   private static final String CONTENT_SCHEME = "content://";
   private static final int PERF_SESSIONS = 1;
   public static final HashMap<String, String> PERF_SESSIONS_PROJECTION_MAP;
   private static final int PERF_SESSIONS_SID = 2;
   private static final String SESSIONS_BASE_URI = "content://com.facebook.katana.provider.LoggingProvider/perf_sessions";
   public static final Uri SESSIONS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.LoggingProvider/perf_sessions");
   private static final String SESSIONS_PATH = "/sid";
   public static final Uri SESSIONS_SID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.LoggingProvider/perf_sessions/sid");
   private static final String SQL_PERF_SESSIONS = "CREATE TABLE perf_sessions (_id INTEGER PRIMARY KEY,session_id INT,start_time INT,end_time INT,api_log TEXT);";
   private static final String TABLE_PERF_SESSIONS = "perf_sessions";
   private static final UriMatcher URL_MATCHER = new UriMatcher(-1);
   private FacebookDatabaseHelper mDbHelper;


   static {
      URL_MATCHER.addURI("com.facebook.katana.provider.LoggingProvider", "perf_sessions", 1);
      URL_MATCHER.addURI("com.facebook.katana.provider.LoggingProvider", "perf_sessions/#", 2);
      PERF_SESSIONS_PROJECTION_MAP = new HashMap();
      Object var0 = PERF_SESSIONS_PROJECTION_MAP.put("session_id", "session_id");
      Object var1 = PERF_SESSIONS_PROJECTION_MAP.put("start_time", "start_time");
      Object var2 = PERF_SESSIONS_PROJECTION_MAP.put("end_time", "end_time");
      Object var3 = PERF_SESSIONS_PROJECTION_MAP.put("api_log", "api_log");
   }

   public LoggingProvider() {}

   static String[] getTableNames() {
      String[] var0 = new String[]{"perf_sessions"};
      return var0;
   }

   static String[] getTableSQLs() {
      String[] var0 = new String[]{"CREATE TABLE perf_sessions (_id INTEGER PRIMARY KEY,session_id INT,start_time INT,end_time INT,api_log TEXT);"};
      return var0;
   }

   private Uri insertSessionValues(Uri var1, ContentValues var2) throws SQLException {
      long var3 = this.mDbHelper.getWritableDatabase().insert("perf_sessions", "session_id", var2);
      if(var3 > 0L) {
         Uri var5 = SESSIONS_CONTENT_URI;
         String var6 = Long.valueOf(var3).toString();
         Uri var7 = Uri.withAppendedPath(var5, var6);
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
         return var7;
      } else {
         String var8 = "Failed to insert row into " + var1;
         throw new SQLException(var8);
      }
   }

   private int insertSessionsValues(Uri var1, ContentValues[] var2) throws SQLException {
      int var3 = 0;
      ContentValues[] var4 = var2;
      int var5 = var2.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ContentValues var7 = var4[var6];
         if(this.mDbHelper.getWritableDatabase().insert("perf_sessions", "session_id", var7) > 0L) {
            ++var3;
         }
      }

      if(var3 > 0) {
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
         return var3;
      } else {
         String var8 = "Failed to insert rows into " + var1;
         throw new SQLException(var8);
      }
   }

   public int bulkInsert(Uri var1, ContentValues[] var2) {
      switch(URL_MATCHER.match(var1)) {
      case 1:
         return this.insertSessionsValues(var1, var2);
      default:
         String var3 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var3);
      }
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      switch(URL_MATCHER.match(var1)) {
      case 1:
         int var6 = var4.delete("perf_sessions", var2, var3);
         if(var6 > 0) {
            this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
         }

         return var6;
      default:
         String var5 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var5);
      }
   }

   public String getType(Uri var1) {
      switch(URL_MATCHER.match(var1)) {
      case 1:
      case 2:
         return "vnd.android.cursor.dir/vnd.facebook.logresult";
      default:
         String var2 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var2);
      }
   }

   public Uri insert(Uri var1, ContentValues var2) {
      ContentValues var3;
      if(var2 != null) {
         var3 = new ContentValues(var2);
      } else {
         var3 = new ContentValues();
      }

      switch(URL_MATCHER.match(var1)) {
      case 1:
         return this.insertSessionValues(var1, var3);
      default:
         String var4 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var4);
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
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6.setTables("perf_sessions");
         HashMap var8 = PERF_SESSIONS_PROJECTION_MAP;
         var6.setProjectionMap(var8);
         SQLiteDatabase var9 = this.mDbHelper.getReadableDatabase();
         Object var13 = null;
         Cursor var14 = var6.query(var9, var2, var3, var4, (String)null, (String)var13, "session_id ASC");
         ContentResolver var15 = this.getContext().getContentResolver();
         var14.setNotificationUri(var15, var1);
         return var14;
      default:
         String var7 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var7);
      }
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
      switch(URL_MATCHER.match(var1)) {
      case 1:
         int var7 = var5.update("perf_sessions", var2, var3, var4);
         if(var7 > 0) {
            this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
         }

         return var7;
      default:
         String var6 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var6);
      }
   }

   public static final class SessionColumns implements BaseColumns {

      public static final String API_LOG = "api_log";
      public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.facebook.logresult";
      public static final String DEFAULT_SORT_ORDER = "session_id ASC";
      public static final String END_TIME = "end_time";
      public static final String SESSION_ID = "session_id";
      public static final String SPECIFIC_ID = "perf_sessions._id";
      public static final String START_TIME = "start_time";


      public SessionColumns() {}
   }
}
