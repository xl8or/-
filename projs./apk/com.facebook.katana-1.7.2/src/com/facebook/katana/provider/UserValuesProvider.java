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

public class UserValuesProvider extends ContentProvider {

   private static final String AUTHORITY = "com.facebook.katana.provider.UserValuesProvider";
   public static final Uri CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.UserValuesProvider/user_values");
   public static final Uri NAME_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.UserValuesProvider/user_values/name");
   private static final String SQL_USER_VALUES = "CREATE TABLE user_values (_id INTEGER PRIMARY KEY,name TEXT,value TEXT);";
   private static final String TABLE_USER_VALUES = "user_values";
   private static final UriMatcher URI_MATCHER = new UriMatcher(-1);
   private static final int USER_VALUES = 1;
   private static final HashMap<String, String> USER_VALUES_PROJECTION_MAP;
   private static final int USER_VALUE_ID = 2;
   private static final int USER_VALUE_NAME = 3;
   private FacebookDatabaseHelper mDbHelper;


   static {
      URI_MATCHER.addURI("com.facebook.katana.provider.UserValuesProvider", "user_values", 1);
      URI_MATCHER.addURI("com.facebook.katana.provider.UserValuesProvider", "user_values/#", 2);
      URI_MATCHER.addURI("com.facebook.katana.provider.UserValuesProvider", "user_values/name/*", 3);
      USER_VALUES_PROJECTION_MAP = new HashMap();
      Object var0 = USER_VALUES_PROJECTION_MAP.put("_id", "_id");
      Object var1 = USER_VALUES_PROJECTION_MAP.put("name", "name");
      Object var2 = USER_VALUES_PROJECTION_MAP.put("value", "value");
   }

   public UserValuesProvider() {}

   public static String getTableName() {
      return "user_values";
   }

   public static String getTableSQL() {
      return "CREATE TABLE user_values (_id INTEGER PRIMARY KEY,name TEXT,value TEXT);";
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var6;
      switch(URI_MATCHER.match(var1)) {
      case 1:
         var6 = var4.delete("user_values", var2, var3);
         break;
      case 2:
         String var7 = (String)var1.getPathSegments().get(1);
         String var8 = "_id=" + var7;
         var6 = var4.delete("user_values", var8, (String[])null);
         break;
      case 3:
         String var9 = (String)var1.getPathSegments().get(1);
         String var10 = "name=" + var9;
         var6 = var4.delete("user_values", var10, (String[])null);
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
         return "vnd.android.cursor.item/vnd.facebook.katana.uservalues";
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
         long var5 = this.mDbHelper.getWritableDatabase().insert("user_values", "name", var3);
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
         var6.setTables("user_values");
         HashMap var8 = USER_VALUES_PROJECTION_MAP;
         var6.setProjectionMap(var8);
         break;
      case 2:
         var6.setTables("user_values");
         StringBuilder var17 = (new StringBuilder()).append("_id=");
         String var18 = (String)var1.getPathSegments().get(1);
         String var19 = var17.append(var18).toString();
         var6.appendWhere(var19);
         break;
      case 3:
         var6.setTables("user_values");
         StringBuilder var20 = (new StringBuilder()).append("name=\'");
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
         var9 = "name DESC";
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
         var7 = var5.update("user_values", var2, var3, var4);
         break;
      case 2:
         String var8 = (String)var1.getPathSegments().get(1);
         String var9 = "_id=" + var8;
         var7 = var5.update("user_values", var2, var9, (String[])null);
         break;
      case 3:
         String var10 = (String)var1.getPathSegments().get(2);
         String var11 = "name=\'" + var10 + "\'";
         var7 = var5.update("user_values", var2, var11, (String[])null);
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
      public static final String PROP_VALUE = "value";


      public Columns() {}
   }
}
