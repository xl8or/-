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

public class KeyValueProvider extends ContentProvider {

   private static final String AUTHORITY = "com.facebook.katana.provider.KeyValueProvider";
   public static final Uri CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.KeyValueProvider/key_value");
   public static final Uri KEY_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.KeyValueProvider/key_value/key");
   private static final int KEY_VALUE = 1;
   private static final int KEY_VALUE_ID = 2;
   private static final int KEY_VALUE_KEY = 3;
   private static final HashMap<String, String> KEY_VALUE_PROJECTION_MAP;
   private static final String SQL_CREATE_KEY_VALUE = "CREATE TABLE key_value (_id INTEGER PRIMARY KEY,key TEXT UNIQUE,value TEXT);";
   private static final String TABLE_KEY_VALUE = "key_value";
   private static final UriMatcher URI_MATCHER = new UriMatcher(-1);
   private FacebookDatabaseHelper mDbHelper;


   static {
      URI_MATCHER.addURI("com.facebook.katana.provider.KeyValueProvider", "key_value", 1);
      URI_MATCHER.addURI("com.facebook.katana.provider.KeyValueProvider", "key_value/#", 2);
      URI_MATCHER.addURI("com.facebook.katana.provider.KeyValueProvider", "key_value/key/*", 3);
      KEY_VALUE_PROJECTION_MAP = new HashMap();
      Object var0 = KEY_VALUE_PROJECTION_MAP.put("_id", "_id");
      Object var1 = KEY_VALUE_PROJECTION_MAP.put("key", "key");
      Object var2 = KEY_VALUE_PROJECTION_MAP.put("value", "value");
   }

   public KeyValueProvider() {}

   public static String[] getTableNames() {
      String[] var0 = new String[]{"key_value"};
      return var0;
   }

   public static String[] getTableSQLs() {
      String[] var0 = new String[]{"CREATE TABLE key_value (_id INTEGER PRIMARY KEY,key TEXT UNIQUE,value TEXT);"};
      return var0;
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var6;
      switch(URI_MATCHER.match(var1)) {
      case 1:
         var6 = var4.delete("key_value", var2, var3);
         break;
      case 2:
         String var7 = (String)var1.getPathSegments().get(1);
         String var8 = "_id=" + var7;
         var6 = var4.delete("key_value", var8, (String[])null);
         break;
      case 3:
         String var9 = (String)var1.getPathSegments().get(1);
         String var10 = "key=" + var9;
         var6 = var4.delete("key_value", var10, (String[])null);
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
         return "vnd.android.cursor.item/vnd.facebook.katana.keyvalue";
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
         long var5 = this.mDbHelper.getWritableDatabase().insert("key_value", "key", var3);
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
      switch(URI_MATCHER.match(var1)) {
      case 1:
         var6.setTables("key_value");
         HashMap var8 = KEY_VALUE_PROJECTION_MAP;
         var6.setProjectionMap(var8);
         break;
      case 2:
         var6.setTables("key_value");
         StringBuilder var17 = (new StringBuilder()).append("_id=");
         String var18 = (String)var1.getPathSegments().get(1);
         String var19 = var17.append(var18).toString();
         var6.appendWhere(var19);
         break;
      case 3:
         var6.setTables("key_value");
         StringBuilder var20 = (new StringBuilder()).append("key=\'");
         String var21 = (String)var1.getPathSegments().get(2);
         String var22 = var20.append(var21).append("\'").toString();
         var6.appendWhere(var22);
         break;
      default:
         String var7 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var7);
      }

      String var9;
      if(TextUtils.isEmpty(var5)) {
         var9 = "key DESC";
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
      switch(URI_MATCHER.match(var1)) {
      case 1:
         var7 = var5.update("key_value", var2, var3, var4);
         break;
      case 2:
         String var8 = (String)var1.getPathSegments().get(1);
         String var9 = "_id=" + var8;
         var7 = var5.update("key_value", var2, var9, (String[])null);
         break;
      case 3:
         String var10 = (String)var1.getPathSegments().get(2);
         String var11 = "key=\'" + var10 + "\'";
         var7 = var5.update("key_value", var2, var11, (String[])null);
         break;
      default:
         String var6 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var6);
      }

      this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null, (boolean)0);
      return var7;
   }

   public static final class Columns implements BaseColumns {

      public static final String DEFAULT_SORT_ORDER = "key DESC";
      public static final String PROP_KEY = "key";
      public static final String PROP_VALUE = "value";


      public Columns() {}
   }
}
