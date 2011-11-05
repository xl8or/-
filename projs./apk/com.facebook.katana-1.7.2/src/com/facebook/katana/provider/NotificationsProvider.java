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

public class NotificationsProvider extends ContentProvider {

   private static final String AUTHORITY = "com.facebook.katana.provider.NotificationsProvider";
   public static final Uri CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.NotificationsProvider/notifications");
   private static final int NOTIFICATIONS = 1;
   private static final HashMap<String, String> NOTIFICATIONS_PROJECTION_MAP;
   private static final String NOTIFICATIONS_TABLE = "notifications";
   private static final int NOTIFICATION_ID = 2;
   private static final String SQL_NOTIFICATIONS = "CREATE TABLE notifications (_id INTEGER PRIMARY KEY,notif_id INT,app_id INT,sender_id INT,sender_name TEXT,sender_url TEXT,created INT,title TEXT,body TEXT,href TEXT,is_read INT,app_image TEXT,object_id TEXT,object_type TEXT);";
   private static final UriMatcher URL_MATCHER = new UriMatcher(-1);
   private FacebookDatabaseHelper mDbHelper;


   static {
      URL_MATCHER.addURI("com.facebook.katana.provider.NotificationsProvider", "notifications", 1);
      URL_MATCHER.addURI("com.facebook.katana.provider.NotificationsProvider", "notifications/#", 2);
      NOTIFICATIONS_PROJECTION_MAP = new HashMap();
      Object var0 = NOTIFICATIONS_PROJECTION_MAP.put("_id", "_id");
      Object var1 = NOTIFICATIONS_PROJECTION_MAP.put("app_id", "app_id");
      Object var2 = NOTIFICATIONS_PROJECTION_MAP.put("notif_id", "notif_id");
      Object var3 = NOTIFICATIONS_PROJECTION_MAP.put("sender_id", "sender_id");
      Object var4 = NOTIFICATIONS_PROJECTION_MAP.put("sender_name", "sender_name");
      Object var5 = NOTIFICATIONS_PROJECTION_MAP.put("sender_url", "sender_url");
      Object var6 = NOTIFICATIONS_PROJECTION_MAP.put("created", "created");
      Object var7 = NOTIFICATIONS_PROJECTION_MAP.put("title", "title");
      Object var8 = NOTIFICATIONS_PROJECTION_MAP.put("body", "body");
      Object var9 = NOTIFICATIONS_PROJECTION_MAP.put("href", "href");
      Object var10 = NOTIFICATIONS_PROJECTION_MAP.put("is_read", "is_read");
      Object var11 = NOTIFICATIONS_PROJECTION_MAP.put("app_image", "app_image");
      Object var12 = NOTIFICATIONS_PROJECTION_MAP.put("object_id", "object_id");
      Object var13 = NOTIFICATIONS_PROJECTION_MAP.put("object_type", "object_type");
   }

   public NotificationsProvider() {}

   public static String getTableName() {
      return "notifications";
   }

   public static String getTableSQL() {
      return "CREATE TABLE notifications (_id INTEGER PRIMARY KEY,notif_id INT,app_id INT,sender_id INT,sender_name TEXT,sender_url TEXT,created INT,title TEXT,body TEXT,href TEXT,is_read INT,app_image TEXT,object_id TEXT,object_type TEXT);";
   }

   public int bulkInsert(Uri var1, ContentValues[] var2) {
      int var3 = 0;
      if(URL_MATCHER.match(var1) != 1) {
         String var4 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var4);
      } else {
         SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
         int var6 = 0;

         while(true) {
            int var7 = var2.length;
            if(var6 >= var7) {
               if(var3 > 0) {
                  this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
                  return var3;
               }

               String var9 = "Failed to insert row into " + var1;
               throw new SQLException(var9);
            }

            ContentValues var8 = var2[var6];
            if(var5.insert("notifications", "notif_id", var8) > 0L) {
               ++var3;
            }

            ++var6;
         }
      }
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var6;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6 = var4.delete("notifications", var2, var3);
         break;
      case 2:
         String var7 = (String)var1.getPathSegments().get(1);
         String var8 = "_id=" + var7;
         var6 = var4.delete("notifications", var8, (String[])null);
         break;
      default:
         String var5 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var5);
      }

      this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
      return var6;
   }

   public String getType(Uri var1) {
      switch(URL_MATCHER.match(var1)) {
      case 1:
      case 2:
         return "vnd.android.cursor.dir/vnd.facebook.katana.notifications";
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

      if(URL_MATCHER.match(var1) != 1) {
         String var4 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var4);
      } else {
         long var5 = this.mDbHelper.getWritableDatabase().insert("notifications", "notif_id", var3);
         if(var5 > 0L) {
            Uri var7 = CONTENT_URI;
            String var8 = Long.valueOf(var5).toString();
            Uri var9 = Uri.withAppendedPath(var7, var8);
            this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
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
         var6.setTables("notifications");
         HashMap var8 = NOTIFICATIONS_PROJECTION_MAP;
         var6.setProjectionMap(var8);
         break;
      case 2:
         var6.setTables("notifications");
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
         var9 = "created DESC";
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
         var7 = var5.update("notifications", var2, var3, var4);
         break;
      case 2:
         String var8 = (String)var1.getPathSegments().get(1);
         String var9 = "_id=" + var8;
         var7 = var5.update("notifications", var2, var9, (String[])null);
         break;
      default:
         String var6 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var6);
      }

      this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
      return var7;
   }

   public static final class Columns implements BaseColumns {

      public static final String APP_ID = "app_id";
      public static final String APP_IMAGE = "app_image";
      public static final String BODY = "body";
      public static final String CREATED_TIME = "created";
      public static final String DEFAULT_SORT_ORDER = "created DESC";
      public static final String HREF = "href";
      public static final String IS_READ = "is_read";
      public static final String NOTIFICATION_ID = "notif_id";
      public static final String OBJECT_ID = "object_id";
      public static final String OBJECT_TYPE = "object_type";
      public static final String SENDER_ID = "sender_id";
      public static final String SENDER_IMAGE_URL = "sender_url";
      public static final String SENDER_NAME = "sender_name";
      public static final String TITLE = "title";


      public Columns() {}
   }
}
