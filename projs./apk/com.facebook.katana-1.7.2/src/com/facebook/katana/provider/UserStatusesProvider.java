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
import java.util.HashMap;

public class UserStatusesProvider extends ContentProvider {

   private static final String AUTHORITY = "com.facebook.katana.provider.UserStatusesProvider";
   public static final Uri CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.UserStatusesProvider/user_statuses");
   private static final String SQL_USER_STATUSES = "CREATE TABLE user_statuses (_id INTEGER PRIMARY KEY,user_id INT,first_name TEXT,last_name TEXT,display_name TEXT,user_pic TEXT,timestamp INT,message TEXT);";
   private static final UriMatcher URL_MATCHER = new UriMatcher(-1);
   private static final int USER_STATUSES = 1;
   private static final HashMap<String, String> USER_STATUSES_PROJECTION_MAP;
   private static final String USER_STATUSES_TABLE = "user_statuses";
   private static final int USER_STATUS_ID = 2;
   private FacebookDatabaseHelper mDbHelper;


   static {
      URL_MATCHER.addURI("com.facebook.katana.provider.UserStatusesProvider", "user_statuses", 1);
      URL_MATCHER.addURI("com.facebook.katana.provider.UserStatusesProvider", "user_statuses/#", 2);
      USER_STATUSES_PROJECTION_MAP = new HashMap();
      Object var0 = USER_STATUSES_PROJECTION_MAP.put("_id", "_id");
      Object var1 = USER_STATUSES_PROJECTION_MAP.put("user_id", "user_id");
      Object var2 = USER_STATUSES_PROJECTION_MAP.put("first_name", "first_name");
      Object var3 = USER_STATUSES_PROJECTION_MAP.put("last_name", "last_name");
      Object var4 = USER_STATUSES_PROJECTION_MAP.put("display_name", "display_name");
      Object var5 = USER_STATUSES_PROJECTION_MAP.put("user_pic", "user_pic");
      Object var6 = USER_STATUSES_PROJECTION_MAP.put("timestamp", "timestamp");
      Object var7 = USER_STATUSES_PROJECTION_MAP.put("message", "message");
   }

   public UserStatusesProvider() {}

   public static String getTableName() {
      return "user_statuses";
   }

   public static String getTableSQL() {
      return "CREATE TABLE user_statuses (_id INTEGER PRIMARY KEY,user_id INT,first_name TEXT,last_name TEXT,display_name TEXT,user_pic TEXT,timestamp INT,message TEXT);";
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var6;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6 = var4.delete("user_statuses", var2, var3);
         break;
      case 2:
         String var7 = (String)var1.getPathSegments().get(1);
         String var8 = "_id=" + var7;
         var6 = var4.delete("user_statuses", var8, (String[])null);
         break;
      default:
         String var5 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var5);
      }

      this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null, (boolean)0);
      return var6;
   }

   public String getType(Uri var1) {
      String var3;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var3 = "vnd.android.cursor.dir/vnd.facebook.katana.userstatuses";
         break;
      case 2:
         var3 = "vnd.android.cursor.item/vnd.facebook.katana.userstatuses";
         break;
      default:
         String var2 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var2);
      }

      return var3;
   }

   public Uri insert(Uri var1, ContentValues var2) {
      ContentValues var3;
      if(var2 != null) {
         var3 = new ContentValues(var2);
      } else {
         var3 = new ContentValues();
      }

      if(URL_MATCHER.match(var1) != 1) {
         String var4 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var4);
      } else {
         long var5 = this.mDbHelper.getWritableDatabase().insert("user_statuses", "user_id", var3);
         if(var5 > 0L) {
            Uri var7 = CONTENT_URI;
            String var8 = Long.valueOf(var5).toString();
            Uri var9 = Uri.withAppendedPath(var7, var8);
            this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null, (boolean)0);
            return var9;
         } else {
            String var10 = "Failed to insert row into " + var1;
            throw new SQLException(var10);
         }
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
         var6.setTables("user_statuses");
         HashMap var8 = USER_STATUSES_PROJECTION_MAP;
         var6.setProjectionMap(var8);
         break;
      case 2:
         var6.setTables("user_statuses");
         StringBuilder var17 = (new StringBuilder()).append("_id=");
         String var18 = (String)var1.getPathSegments().get(1);
         String var19 = var17.append(var18).toString();
         var6.appendWhere(var19);
         break;
      default:
         String var7 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var7);
      }

      String var9;
      if(TextUtils.isEmpty(var5)) {
         var9 = "timestamp DESC";
      } else {
         var9 = var5;
      }

      SQLiteDatabase var10 = this.mDbHelper.getReadableDatabase();
      Object var14 = null;
      Cursor var15 = var6.query(var10, var2, var3, var4, (String)null, (String)var14, var9);
      ContentResolver var16 = this.getContext().getContentResolver();
      var15.setNotificationUri(var16, var1);
      return var15;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
      int var7;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var7 = var5.update("user_statuses", var2, var3, var4);
         break;
      case 2:
         String var8 = (String)var1.getPathSegments().get(1);
         String var9 = "_id=" + var8;
         var7 = var5.update("user_statuses", var2, var9, (String[])null);
         break;
      default:
         String var6 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var6);
      }

      this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null, (boolean)0);
      return var7;
   }

   public static final class Columns implements BaseColumns {

      public static final String DEFAULT_SORT_ORDER = "timestamp DESC";
      public static final String MESSAGE = "message";
      public static final String TIMESTAMP = "timestamp";
      public static final String USER_DISPLAY_NAME = "display_name";
      public static final String USER_FIRST_NAME = "first_name";
      public static final String USER_ID = "user_id";
      public static final String USER_LAST_NAME = "last_name";
      public static final String USER_PIC = "user_pic";


      public Columns() {}
   }
}
