package com.facebook.katana.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import com.facebook.katana.provider.FacebookDatabaseHelper;
import java.util.HashMap;

public class CacheProvider extends ContentProvider {

   private static final String AUTHORITY = "com.facebook.katana.provider.CacheProvider";
   private static final int CACHE_ID_MATCHER = 2;
   private static final int CACHE_MATCHER = 1;
   private static final int CACHE_NAME_MATCHER = 3;
   private static final int CACHE_PREFIX_MATCHER = 4;
   private static final HashMap<String, String> CACHE_PROJECTION_MAP;
   private static final int CACHE_SWEEP_PREFIX_MATCHER = 5;
   public static final Uri CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.CacheProvider/cache");
   public static final Uri NAME_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.CacheProvider/cache/name");
   public static final Uri PREFIX_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.CacheProvider/cache/prefix");
   private static final String SQL_CACHE = "CREATE TABLE cache (_id INTEGER PRIMARY KEY,name TEXT,value TEXT,timestamp INTEGER DEFAULT 0);";
   public static final Uri SWEEP_PREFIX_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.CacheProvider/cache/sweep_prefix");
   private static final String TABLE_CACHE = "cache";
   private static final UriMatcher URI_MATCHER = new UriMatcher(-1);
   private FacebookDatabaseHelper mDbHelper;


   static {
      URI_MATCHER.addURI("com.facebook.katana.provider.CacheProvider", "cache", 1);
      URI_MATCHER.addURI("com.facebook.katana.provider.CacheProvider", "cache/#", 2);
      URI_MATCHER.addURI("com.facebook.katana.provider.CacheProvider", "cache/name/*", 3);
      URI_MATCHER.addURI("com.facebook.katana.provider.CacheProvider", "cache/prefix/*", 4);
      URI_MATCHER.addURI("com.facebook.katana.provider.CacheProvider", "cache/sweep_prefix/*/#", 5);
      CACHE_PROJECTION_MAP = new HashMap();
      Object var0 = CACHE_PROJECTION_MAP.put("_id", "_id");
      Object var1 = CACHE_PROJECTION_MAP.put("name", "name");
      Object var2 = CACHE_PROJECTION_MAP.put("value", "value");
      Object var3 = CACHE_PROJECTION_MAP.put("timestamp", "timestamp");
   }

   public CacheProvider() {}

   public static String[] getTableNames() {
      String[] var0 = new String[]{"cache"};
      return var0;
   }

   public static String[] getTableSQLs() {
      String[] var0 = new String[]{"CREATE TABLE cache (_id INTEGER PRIMARY KEY,name TEXT,value TEXT,timestamp INTEGER DEFAULT 0);"};
      return var0;
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var6;
      switch(URI_MATCHER.match(var1)) {
      case 1:
         var6 = var4.delete("cache", var2, var3);
         break;
      case 2:
         String var7 = (String)var1.getPathSegments().get(1);
         String var8 = "_id=" + var7;
         var6 = var4.delete("cache", var8, (String[])null);
         break;
      case 3:
         String var9 = (String)var1.getPathSegments().get(2);
         StringBuilder var10 = (new StringBuilder()).append("name=");
         String var11 = DatabaseUtils.sqlEscapeString(var9);
         String var12 = var10.append(var11).toString();
         var6 = var4.delete("cache", var12, (String[])null);
         break;
      case 4:
         String var13 = (String)var1.getPathSegments().get(2);
         Object[] var14 = new Object[]{"name", null, null};
         Integer var15 = Integer.valueOf(var13.length());
         var14[1] = var15;
         String var16 = DatabaseUtils.sqlEscapeString(var13);
         var14[2] = var16;
         String var17 = String.format("SUBSTR(%s, 1, %d)=%s", var14);
         var6 = var4.delete("cache", var17, (String[])null);
         break;
      case 5:
         String var18 = (String)var1.getPathSegments().get(2);
         int var19 = Integer.parseInt((String)var1.getPathSegments().get(3));
         Object[] var20 = new Object[6];
         var20[0] = "name";
         Integer var21 = Integer.valueOf(var18.length());
         var20[1] = var21;
         String var22 = DatabaseUtils.sqlEscapeString(var18);
         var20[2] = var22;
         Long var23 = Long.valueOf(System.currentTimeMillis() / 1000L);
         var20[3] = var23;
         var20[4] = "timestamp";
         Integer var24 = Integer.valueOf(var19);
         var20[5] = var24;
         String var25 = String.format("SUBSTR(%s, 1, %d)=%s AND (%d-%s > %d)", var20);
         var6 = var4.delete("cache", var25, (String[])null);
         break;
      default:
         String var5 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var5);
      }

      this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null, (boolean)0);
      return var6;
   }

   public String getType(Uri var1) {
      switch(URI_MATCHER.match(var1)) {
      case 1:
      case 2:
      case 3:
         return "vnd.android.cursor.item/vnd.facebook.katana.cache";
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

      if(URI_MATCHER.match(var1) != 1) {
         String var4 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var4);
      } else {
         long var5 = this.mDbHelper.getWritableDatabase().insert("cache", "name", var3);
         if(var5 > 0L) {
            Uri var7 = CONTENT_URI;
            String var8 = Long.toString(var5);
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
      switch(URI_MATCHER.match(var1)) {
      case 1:
         var6.setTables("cache");
         HashMap var8 = CACHE_PROJECTION_MAP;
         var6.setProjectionMap(var8);
         break;
      case 2:
         var6.setTables("cache");
         StringBuilder var16 = (new StringBuilder()).append("_id=");
         String var17 = (String)var1.getPathSegments().get(1);
         String var18 = var16.append(var17).toString();
         var6.appendWhere(var18);
         break;
      case 3:
         var6.setTables("cache");
         StringBuilder var19 = (new StringBuilder()).append("name=");
         String var20 = DatabaseUtils.sqlEscapeString((String)var1.getPathSegments().get(2));
         String var21 = var19.append(var20).toString();
         var6.appendWhere(var21);
         break;
      case 4:
         String var22 = (String)var1.getPathSegments().get(2);
         var6.setTables("cache");
         Object[] var23 = new Object[]{"name", null, null};
         Integer var24 = Integer.valueOf(var22.length());
         var23[1] = var24;
         String var25 = DatabaseUtils.sqlEscapeString(var22);
         var23[2] = var25;
         String var26 = String.format("SUBSTR(%s, 1, %d)=%s", var23);
         var6.appendWhere(var26);
         break;
      default:
         String var7 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var7);
      }

      String var9;
      if(TextUtils.isEmpty(var5)) {
         var9 = "name DESC";
      } else {
         var9 = var5;
      }

      SQLiteDatabase var10 = this.mDbHelper.getReadableDatabase();
      Cursor var14 = var6.query(var10, var2, var3, var4, (String)null, (String)null, var9);
      ContentResolver var15 = this.getContext().getContentResolver();
      var14.setNotificationUri(var15, var1);
      return var14;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
      int var7;
      switch(URI_MATCHER.match(var1)) {
      case 1:
         var7 = var5.update("cache", var2, var3, var4);
         break;
      case 2:
         String var8 = (String)var1.getPathSegments().get(1);
         String var9 = "_id=" + var8;
         var7 = var5.update("cache", var2, var9, (String[])null);
         break;
      case 3:
         String var10 = (String)var1.getPathSegments().get(2);
         StringBuilder var11 = (new StringBuilder()).append("name=");
         String var12 = DatabaseUtils.sqlEscapeString(var10);
         String var13 = var11.append(var12).toString();
         var7 = var5.update("cache", var2, var13, (String[])null);
         break;
      default:
         String var6 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var6);
      }

      this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null, (boolean)0);
      return var7;
   }

   public static final class Columns implements BaseColumns {

      public static final String DEFAULT_SORT_ORDER = "name DESC";
      public static final String PROP_NAME = "name";
      public static final String PROP_TIMESTAMP = "timestamp";
      public static final String PROP_VALUE = "value";


      public Columns() {}
   }
}
