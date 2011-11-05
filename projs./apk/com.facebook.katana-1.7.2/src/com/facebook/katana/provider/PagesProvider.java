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

public class PagesProvider extends ContentProvider {

   private static final String AUTHORITY = "com.facebook.katana.provider.PagesProvider";
   private static final String BASE_CONTENT_URI = "content://com.facebook.katana.provider.PagesProvider/";
   public static final Uri DEFAULT_PAGE_IMAGE_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PagesProvider/default_page_images");
   private static final HashMap<String, String> DEFAULT_PAGE_IMAGE_PROJECTION_MAP;
   private static final String DEFAULT_PAGE_IMAGE_TABLE = "default_page_images";
   private static final int SEARCH_RESULTS = 31;
   public static final Uri SEARCH_RESULTS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PagesProvider/page_search_results");
   private static final HashMap<String, String> SEARCH_RESULTS_PROJECTION_MAP;
   private static final String SEARCH_RESULTS_TABLE = "page_search_results";
   private static final int SEARCH_RESULT_ID = 32;
   private static final int SEARCH_RESULT_UID = 33;
   public static final Uri SEARCH_RESULT_UID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PagesProvider/page_search_results/uid");
   private static final String SQL_DEFAULT_PAGE_IMAGE = "CREATE TABLE default_page_images (_id INTEGER PRIMARY KEY,pic BLOB);";
   private static final String SQL_SEARCH_RESULTS = "CREATE TABLE page_search_results (_id INTEGER PRIMARY KEY,page_id INT,display_name TEXT,pic TEXT);";
   private static final UriMatcher URL_MATCHER = new UriMatcher(-1);
   private FacebookDatabaseHelper mDbHelper;


   static {
      URL_MATCHER.addURI("com.facebook.katana.provider.PagesProvider", "page_search_results", 31);
      URL_MATCHER.addURI("com.facebook.katana.provider.PagesProvider", "page_search_results/#", 32);
      URL_MATCHER.addURI("com.facebook.katana.provider.PagesProvider", "page_search_results/uid/#", 33);
      DEFAULT_PAGE_IMAGE_PROJECTION_MAP = new HashMap();
      Object var0 = DEFAULT_PAGE_IMAGE_PROJECTION_MAP.put("_id", "_id");
      Object var1 = DEFAULT_PAGE_IMAGE_PROJECTION_MAP.put("pic", "pic");
      SEARCH_RESULTS_PROJECTION_MAP = new HashMap();
      Object var2 = SEARCH_RESULTS_PROJECTION_MAP.put("_id", "_id");
      Object var3 = SEARCH_RESULTS_PROJECTION_MAP.put("page_id", "page_id");
      Object var4 = SEARCH_RESULTS_PROJECTION_MAP.put("display_name", "display_name");
      Object var5 = SEARCH_RESULTS_PROJECTION_MAP.put("pic", "pic");
   }

   public PagesProvider() {}

   public static String[] getTableNames() {
      String[] var0 = new String[]{"page_search_results", "default_page_images"};
      return var0;
   }

   public static String[] getTableSQLs() {
      String[] var0 = new String[]{"CREATE TABLE page_search_results (_id INTEGER PRIMARY KEY,page_id INT,display_name TEXT,pic TEXT);", "CREATE TABLE default_page_images (_id INTEGER PRIMARY KEY,pic BLOB);"};
      return var0;
   }

   public int bulkInsert(Uri var1, ContentValues[] var2) {
      int var3 = 0;
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      switch(URL_MATCHER.match(var1)) {
      case 31:
         int var6 = 0;

         while(true) {
            int var7 = var2.length;
            if(var6 >= var7) {
               if(var3 > 0) {
                  this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
                  return var3;
               }

               String var9 = "Failed to insert rows into " + var1;
               throw new SQLException(var9);
            }

            ContentValues var8 = var2[var6];
            if(var4.insert("page_search_results", "page_id", var8) > 0L) {
               ++var3;
            }

            ++var6;
         }
      default:
         String var5 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var5);
      }
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var6;
      switch(URL_MATCHER.match(var1)) {
      case 31:
         var6 = var4.delete("page_search_results", var2, var3);
         break;
      case 32:
         String var7 = (String)var1.getPathSegments().get(1);
         String var8 = "_id=" + var7;
         var6 = var4.delete("page_search_results", var8, (String[])null);
         break;
      case 33:
         String var9 = (String)var1.getPathSegments().get(2);
         String var10 = "page_id=" + var9;
         var6 = var4.delete("page_search_results", var10, (String[])null);
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
      case 31:
      case 32:
      case 33:
         return "vnd.android.cursor.item/vnd.com.facebook.katana.provider.search_results";
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

      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      switch(URL_MATCHER.match(var1)) {
      case 31:
         long var6 = var4.insert("page_search_results", "display_name", var3);
         if(var6 > 0L) {
            Uri var8 = SEARCH_RESULTS_CONTENT_URI;
            String var9 = Long.valueOf(var6).toString();
            Uri var10 = Uri.withAppendedPath(var8, var9);
            this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
            return var10;
         }

         String var11 = "Failed to insert row into " + var1;
         throw new SQLException(var11);
      default:
         String var5 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var5);
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
      case 31:
         var6.setTables("page_search_results");
         HashMap var8 = SEARCH_RESULTS_PROJECTION_MAP;
         var6.setProjectionMap(var8);
         var9 = "_id ASC";
         break;
      case 32:
         var6.setTables("page_search_results");
         StringBuilder var17 = (new StringBuilder()).append("_id=");
         String var18 = (String)var1.getPathSegments().get(1);
         String var19 = var17.append(var18).toString();
         var6.appendWhere(var19);
         HashMap var20 = SEARCH_RESULTS_PROJECTION_MAP;
         var6.setProjectionMap(var20);
         var9 = "_id ASC";
         break;
      case 33:
         var6.setTables("page_search_results");
         StringBuilder var21 = (new StringBuilder()).append("page_id=");
         String var22 = (String)var1.getPathSegments().get(2);
         String var23 = var21.append(var22).toString();
         var6.appendWhere(var23);
         HashMap var24 = SEARCH_RESULTS_PROJECTION_MAP;
         var6.setProjectionMap(var24);
         var9 = "_id ASC";
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
      case 31:
         var7 = var5.update("page_search_results", var2, var3, var4);
         break;
      case 32:
         String var8 = (String)var1.getPathSegments().get(1);
         String var9 = "_id=" + var8;
         var7 = var5.update("page_search_results", var2, var9, (String[])null);
         break;
      case 33:
         String var10 = (String)var1.getPathSegments().get(2);
         String var11 = "page_id=" + var10;
         var7 = var5.update("page_search_results", var2, var11, (String[])null);
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

   public static final class DefaultPageImagesColumns implements BaseColumns {

      public static final String DEFAULT_SORT_ORDER = "_id ASC";
      public static final String PAGE_IMAGE = "pic";


      public DefaultPageImagesColumns() {}
   }

   public static final class SearchResultColumns extends PagesProvider.PageColumns {

      public static final String DEFAULT_SORT_ORDER = "_id ASC";


      public SearchResultColumns() {}
   }

   public static class PageColumns implements BaseColumns {

      public static final String DEFAULT_SORT_ORDER = "display_name ASC";
      public static final String PAGE_DISPLAY_NAME = "display_name";
      public static final String PAGE_ID = "page_id";
      public static final String PAGE_IMAGE_URL = "pic";


      public PageColumns() {}
   }
}
