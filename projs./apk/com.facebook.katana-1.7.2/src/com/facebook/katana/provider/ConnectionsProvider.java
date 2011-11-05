package com.facebook.katana.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.platform.PlatformStorage;
import com.facebook.katana.provider.FacebookDatabaseHelper;
import com.facebook.katana.util.FileUtils;
import com.facebook.katana.util.ImageUtils;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.util.Utils;
import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

public class ConnectionsProvider extends ContentProvider {

   private static final String AUTHORITY = "com.facebook.katana.provider.ConnectionsProvider";
   private static final String BASE_CONTENT_URI = "content://com.facebook.katana.provider.ConnectionsProvider/";
   public static final Uri CONNECTIONS_CONTENT_URI;
   private static final HashMap<String, String> CONNECTIONS_PROJECTION_MAP;
   static final String CONNECTIONS_TABLE = "connections";
   public static final Uri CONNECTION_ID_CONTENT_URI;
   public static final Uri FRIENDS_BIRTHDAY_CONTENT_URI;
   public static final Uri FRIENDS_CONTENT_URI;
   private static final Set<String> FRIENDS_DATA_COLUMNS;
   static final String FRIENDS_DATA_TABLE = "friends_data";
   private static final HashMap<String, String> FRIENDS_PROJECTION_MAP;
   public static final Uri FRIENDS_SEARCH_CONTENT_URI;
   private static final String FRIENDS_VIEW = "friends";
   public static final Uri FRIEND_UID_CONTENT_URI;
   private static final String PAGES_CATEGORY = "pages";
   public static final Uri PAGES_CONTENT_URI;
   public static final Uri PAGES_SEARCH_CONTENT_URI;
   public static final Uri PAGE_ID_CONTENT_URI;
   private static final HashMap<String, String> SEARCH_RESULTS_PROJECTION_MAP;
   static final String SEARCH_RESULTS_TABLE = "search_results";
   private static final String SQL_CONNECTIONS;
   static final String SQL_FRIENDS_DATA = "CREATE TABLE friends_data (_id INTEGER PRIMARY KEY,user_id INT UNIQUE,first_name TEXT,last_name TEXT,cell TEXT,other TEXT,email TEXT,birthday_month INT,birthday_day INT,birthday_year INT);";
   static final String SQL_FRIENDS_VIEW;
   static final String SQL_SEARCH_RESULTS = "CREATE TABLE search_results (_id INTEGER PRIMARY KEY,user_id INT,display_name TEXT,user_image_url TEXT);";
   private static final UriMatcher URL_MATCHER;
   public static final Uri USER_SEARCH_CONTENT_URI;
   private FacebookDatabaseHelper mDbHelper;


   static {
      StringBuilder var0 = (new StringBuilder()).append("CREATE TABLE connections (_id INTEGER PRIMARY KEY,user_id INT UNIQUE,display_name TEXT,connection_type INT NOT NULL DEFAULT ");
      int var1 = ConnectionsProvider.ConnectionType.USER.ordinal();
      SQL_CONNECTIONS = var0.append(var1).append(",").append("user_image_url").append(" TEXT,").append("user_image").append(" BLOB,").append("hash").append(" INT);").toString();
      Object[] var2 = new Object[19];
      var2[0] = "friends";
      var2[1] = "connections";
      var2[2] = "friends_data";
      Integer var3 = Integer.valueOf(ConnectionsProvider.ConnectionType.USER.ordinal());
      var2[3] = var3;
      var2[4] = "_id";
      var2[5] = "user_id";
      var2[6] = "display_name";
      var2[7] = "connection_type";
      var2[8] = "user_image_url";
      var2[9] = "user_image";
      var2[10] = "hash";
      var2[11] = "first_name";
      var2[12] = "last_name";
      var2[13] = "cell";
      var2[14] = "other";
      var2[15] = "email";
      var2[16] = "birthday_month";
      var2[17] = "birthday_day";
      var2[18] = "birthday_year";
      SQL_FRIENDS_VIEW = String.format("CREATE VIEW %1$s AS SELECT %2$s.%5$s AS %5$s, %2$s.%6$s AS %6$s, %2$s.%7$s AS %7$s, %2$s.%8$s AS %8$s, %2$s.%9$s AS %9$s, %2$s.%10$s AS %10$s, %2$s.%11$s AS %11$s, %3$s.%12$s AS %12$s, %3$s.%13$s AS %13$s, %3$s.%14$s AS %14$s, %3$s.%15$s AS %15$s, %3$s.%16$s AS %16$s, %3$s.%17$s AS %17$s, %3$s.%18$s AS %18$s, %3$s.%19$s AS %19$s FROM %2$s LEFT OUTER JOIN %3$s ON %2$s.%6$s=%3$s.%6$s WHERE %2$s.%8$s=%4$d;", var2);
      StringBuilder var4 = (new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/");
      String var5 = ConnectionsProvider.Selectors.CONNECTIONS_CONTENT.category;
      StringBuilder var6 = var4.append(var5);
      String var7 = ConnectionsProvider.Selectors.CONNECTIONS_CONTENT.uriSuffix;
      CONNECTIONS_CONTENT_URI = Uri.parse(var6.append(var7).toString());
      StringBuilder var8 = (new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/");
      String var9 = ConnectionsProvider.Selectors.CONNECTION_ID.category;
      StringBuilder var10 = var8.append(var9);
      String var11 = ConnectionsProvider.Selectors.CONNECTION_ID.uriSuffix;
      CONNECTION_ID_CONTENT_URI = Uri.parse(var10.append(var11).toString());
      StringBuilder var12 = (new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/");
      String var13 = ConnectionsProvider.Selectors.FRIENDS_CONTENT.category;
      StringBuilder var14 = var12.append(var13);
      String var15 = ConnectionsProvider.Selectors.FRIENDS_CONTENT.uriSuffix;
      FRIENDS_CONTENT_URI = Uri.parse(var14.append(var15).toString());
      StringBuilder var16 = (new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/");
      String var17 = ConnectionsProvider.Selectors.FRIEND_UID.category;
      StringBuilder var18 = var16.append(var17);
      String var19 = ConnectionsProvider.Selectors.FRIEND_UID.uriSuffix;
      FRIEND_UID_CONTENT_URI = Uri.parse(var18.append(var19).toString());
      StringBuilder var20 = (new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/");
      String var21 = ConnectionsProvider.Selectors.FRIENDS_SEARCH.category;
      StringBuilder var22 = var20.append(var21);
      String var23 = ConnectionsProvider.Selectors.FRIENDS_SEARCH.uriSuffix;
      FRIENDS_SEARCH_CONTENT_URI = Uri.parse(var22.append(var23).toString());
      StringBuilder var24 = (new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/");
      String var25 = ConnectionsProvider.Selectors.FRIENDS_BIRTHDAYS.category;
      StringBuilder var26 = var24.append(var25);
      String var27 = ConnectionsProvider.Selectors.FRIENDS_BIRTHDAYS.uriSuffix;
      FRIENDS_BIRTHDAY_CONTENT_URI = Uri.parse(var26.append(var27).toString());
      StringBuilder var28 = (new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/");
      String var29 = ConnectionsProvider.Selectors.PAGES_CONTENT.category;
      StringBuilder var30 = var28.append(var29);
      String var31 = ConnectionsProvider.Selectors.PAGES_CONTENT.uriSuffix;
      PAGES_CONTENT_URI = Uri.parse(var30.append(var31).toString());
      StringBuilder var32 = (new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/");
      String var33 = ConnectionsProvider.Selectors.PAGE_ID.category;
      StringBuilder var34 = var32.append(var33);
      String var35 = ConnectionsProvider.Selectors.PAGE_ID.uriSuffix;
      PAGE_ID_CONTENT_URI = Uri.parse(var34.append(var35).toString());
      StringBuilder var36 = (new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/");
      String var37 = ConnectionsProvider.Selectors.PAGES_SEARCH.category;
      StringBuilder var38 = var36.append(var37);
      String var39 = ConnectionsProvider.Selectors.PAGES_SEARCH.uriSuffix;
      PAGES_SEARCH_CONTENT_URI = Uri.parse(var38.append(var39).toString());
      StringBuilder var40 = (new StringBuilder()).append("content://com.facebook.katana.provider.ConnectionsProvider/");
      String var41 = ConnectionsProvider.Selectors.SEARCH_CONTENT.category;
      StringBuilder var42 = var40.append(var41);
      String var43 = ConnectionsProvider.Selectors.SEARCH_CONTENT.uriSuffix;
      USER_SEARCH_CONTENT_URI = Uri.parse(var42.append(var43).toString());
      URL_MATCHER = new UriMatcher(-1);
      ConnectionsProvider.Selectors[] var44 = ConnectionsProvider.Selectors.values();
      int var45 = var44.length;

      for(int var46 = 0; var46 < var45; ++var46) {
         ConnectionsProvider.Selectors var47 = var44[var46];
         StringBuilder var48 = new StringBuilder();
         String var49 = var47.category;
         StringBuilder var50 = var48.append(var49);
         String var51 = var47.uriSuffix;
         StringBuilder var52 = var50.append(var51);
         String var53 = var47.uriMatcherSuffix;
         String var54 = var52.append(var53).toString();
         UriMatcher var55 = URL_MATCHER;
         int var56 = var47.uriMatcherIndex();
         var55.addURI("com.facebook.katana.provider.ConnectionsProvider", var54, var56);
      }

      CONNECTIONS_PROJECTION_MAP = new HashMap();
      Object var57 = CONNECTIONS_PROJECTION_MAP.put("_id", "_id");
      Object var58 = CONNECTIONS_PROJECTION_MAP.put("user_id", "user_id");
      Object var59 = CONNECTIONS_PROJECTION_MAP.put("display_name", "display_name");
      Object var60 = CONNECTIONS_PROJECTION_MAP.put("connection_type", "connection_type");
      Object var61 = CONNECTIONS_PROJECTION_MAP.put("user_image_url", "user_image_url");
      Object var62 = CONNECTIONS_PROJECTION_MAP.put("user_image", "user_image");
      Object var63 = CONNECTIONS_PROJECTION_MAP.put("hash", "hash");
      FRIENDS_PROJECTION_MAP = new HashMap();
      Object var64 = FRIENDS_PROJECTION_MAP.put("_id", "_id");
      Object var65 = FRIENDS_PROJECTION_MAP.put("user_id", "user_id");
      Object var66 = FRIENDS_PROJECTION_MAP.put("first_name", "first_name");
      Object var67 = FRIENDS_PROJECTION_MAP.put("last_name", "last_name");
      Object var68 = FRIENDS_PROJECTION_MAP.put("display_name", "display_name");
      Object var69 = FRIENDS_PROJECTION_MAP.put("user_image_url", "user_image_url");
      Object var70 = FRIENDS_PROJECTION_MAP.put("user_image", "user_image");
      Object var71 = FRIENDS_PROJECTION_MAP.put("birthday_month", "birthday_month");
      Object var72 = FRIENDS_PROJECTION_MAP.put("birthday_day", "birthday_day");
      Object var73 = FRIENDS_PROJECTION_MAP.put("birthday_year", "birthday_year");
      Object var74 = FRIENDS_PROJECTION_MAP.put("cell", "cell");
      Object var75 = FRIENDS_PROJECTION_MAP.put("other", "other");
      Object var76 = FRIENDS_PROJECTION_MAP.put("email", "email");
      Object var77 = FRIENDS_PROJECTION_MAP.put("hash", "hash");
      SEARCH_RESULTS_PROJECTION_MAP = new HashMap();
      Object var78 = SEARCH_RESULTS_PROJECTION_MAP.put("_id", "_id");
      Object var79 = SEARCH_RESULTS_PROJECTION_MAP.put("user_id", "user_id");
      Object var80 = SEARCH_RESULTS_PROJECTION_MAP.put("display_name", "display_name");
      Object var81 = SEARCH_RESULTS_PROJECTION_MAP.put("user_image_url", "user_image_url");
      FRIENDS_DATA_COLUMNS = new HashSet();
      boolean var82 = FRIENDS_DATA_COLUMNS.add("user_id");
      boolean var83 = FRIENDS_DATA_COLUMNS.add("first_name");
      boolean var84 = FRIENDS_DATA_COLUMNS.add("last_name");
      boolean var85 = FRIENDS_DATA_COLUMNS.add("birthday_month");
      boolean var86 = FRIENDS_DATA_COLUMNS.add("birthday_day");
      boolean var87 = FRIENDS_DATA_COLUMNS.add("birthday_year");
      boolean var88 = FRIENDS_DATA_COLUMNS.add("cell");
      boolean var89 = FRIENDS_DATA_COLUMNS.add("other");
      boolean var90 = FRIENDS_DATA_COLUMNS.add("email");
   }

   public ConnectionsProvider() {}

   public static FacebookProfile getAdminProfile(Context var0, long var1) {
      String[] var3 = new String[]{"user_id", "display_name", "user_image_url", "connection_type"};
      ContentResolver var4 = var0.getContentResolver();
      Uri var5 = PAGE_ID_CONTENT_URI;
      String var6 = String.valueOf(var1);
      Uri var7 = Uri.withAppendedPath(var5, var6);
      Object var8 = null;
      Object var9 = null;
      Cursor var10 = var4.query(var7, var3, (String)null, (String[])var8, (String)var9);
      FacebookProfile var21;
      if(var10.moveToFirst()) {
         int var11 = var10.getColumnIndex("connection_type");
         int var12 = var10.getInt(var11);
         int var13 = ConnectionsProvider.ConnectionType.PAGE_ADMIN.ordinal();
         if(var12 == var13) {
            int var14 = var10.getColumnIndex("user_id");
            long var15 = (long)var10.getInt(var14);
            int var17 = var10.getColumnIndex("display_name");
            String var18 = var10.getString(var17);
            int var19 = var10.getColumnIndex("user_image_url");
            String var20 = var10.getString(var19);
            var21 = new FacebookProfile(var15, var18, var20, 1);
            return var21;
         }
      }

      var21 = null;
      return var21;
   }

   public static FacebookProfile getFriendProfileFromId(Context var0, long var1) {
      String[] var3 = new String[]{"display_name", "user_image_url"};
      ContentResolver var4 = var0.getContentResolver();
      Uri var5 = ContentUris.withAppendedId(FRIEND_UID_CONTENT_URI, var1);
      Object var6 = null;
      Object var7 = null;
      Cursor var8 = var4.query(var5, var3, (String)null, (String[])var6, (String)var7);
      FacebookProfile var15;
      if(var8.moveToFirst()) {
         int var9 = var8.getColumnIndex("display_name");
         String var10 = var8.getString(var9);
         int var11 = var8.getColumnIndex("user_image_url");
         String var12 = var8.getString(var11);
         var15 = new FacebookProfile(var1, var10, var12, 0);
      } else {
         var15 = null;
      }

      return var15;
   }

   public static String[] getTableNames() {
      String[] var0 = new String[]{"connections", "friends_data", "search_results"};
      return var0;
   }

   public static String[] getTableSQLs() {
      String[] var0 = new String[4];
      String var1 = SQL_CONNECTIONS;
      var0[0] = var1;
      var0[1] = "CREATE TABLE friends_data (_id INTEGER PRIMARY KEY,user_id INT UNIQUE,first_name TEXT,last_name TEXT,cell TEXT,other TEXT,email TEXT,birthday_month INT,birthday_day INT,birthday_year INT);";
      var0[2] = "CREATE TABLE search_results (_id INTEGER PRIMARY KEY,user_id INT,display_name TEXT,user_image_url TEXT);";
      String var2 = SQL_FRIENDS_VIEW;
      var0[3] = var2;
      return var0;
   }

   static String[] getViewNames() {
      String[] var0 = new String[]{"friends"};
      return var0;
   }

   public static ProfileImage updateImage(Context var0, long var1, String var3, String var4) throws IOException {
      Options var5 = new Options();
      Bitmap var6 = ImageUtils.decodeFile(var4, var5);
      if(var6 == null) {
         throw new IOException("Cannot decode bitmap");
      } else {
         byte[] var7 = FileUtils.getBytesFromFile(new File(var4));
         ContentValues var8 = new ContentValues();
         var8.put("user_image", var7);
         var8.put("user_image_url", var3);
         Uri var9 = ContentUris.withAppendedId(CONNECTION_ID_CONTENT_URI, var1);
         var0.getContentResolver().update(var9, var8, (String)null, (String[])null);
         if(PlatformUtils.platformStorageSupported(var0)) {
            PlatformStorage.updateContactPhoto(var0, var1, var7);
         }

         return new ProfileImage(var1, var3, var6);
      }
   }

   public int bulkInsert(Uri var1, ContentValues[] var2) {
      int var3 = 0;
      int var4 = 0;
      SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
      UriMatcher var6 = URL_MATCHER;
      int var8 = var6.match(var1);
      int var9 = ConnectionsProvider.Selectors.FRIENDS_CONTENT.uriMatcherIndex();
      ContentValues[] var10;
      int var11;
      int var24;
      if(var8 == var9) {
         var10 = var2;
         var11 = var2.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            ContentValues var13 = var10[var12];
            if(this.friendsInsert(var5, var13) != null) {
               ++var3;
            } else {
               ++var4;
            }
         }

         if(var3 != 0) {
            ContentResolver var17 = this.getContext().getContentResolver();
            Uri var18 = CONNECTIONS_CONTENT_URI;
            var17.notifyChange(var18, (ContentObserver)null);
            ContentResolver var19 = this.getContext().getContentResolver();
            Uri var20 = FRIENDS_CONTENT_URI;
            var19.notifyChange(var20, (ContentObserver)null);
         }

         if(var4 > 0) {
            Object[] var21 = new Object[1];
            Integer var22 = Integer.valueOf(var4);
            var21[0] = var22;
            String var23 = String.format("Failed friendsInsert on %d rows", var21);
            Utils.reportSoftError("Failed call to friendsInsert", var23);
         }

         var24 = var3;
      } else {
         int var25 = ConnectionsProvider.Selectors.PAGES_CONTENT.uriMatcherIndex();
         byte var47;
         if(var8 == var25) {
            var10 = var2;
            var11 = var2.length;

            int var30;
            for(var47 = 0; var47 < var11; var30 = var47 + 1) {
               ContentValues var26 = var10[var47];
               if(this.pagesInsert(var5, var26) != null) {
                  ++var3;
               } else {
                  ++var4;
               }
            }

            if(var3 != 0) {
               ContentResolver var31 = this.getContext().getContentResolver();
               Uri var32 = USER_SEARCH_CONTENT_URI;
               var31.notifyChange(var32, (ContentObserver)null);
            }

            if(var4 > 0) {
               Object[] var33 = new Object[1];
               Integer var34 = Integer.valueOf(var4);
               var33[0] = var34;
               String var35 = String.format("Failed pagesInsert on %d rows", var33);
               Utils.reportSoftError("Failed call to pagesInsert", var35);
            }

            var24 = var3;
         } else {
            int var36 = ConnectionsProvider.Selectors.SEARCH_CONTENT.uriMatcherIndex();
            if(var8 != var36) {
               StringBuilder var44 = (new StringBuilder()).append("Unknown URL ");
               String var46 = var44.append(var1).toString();
               throw new IllegalArgumentException(var46);
            }

            var10 = var2;
            var11 = var2.length;

            int var38;
            for(var47 = 0; var47 < var11; var38 = var47 + 1) {
               ContentValues var37 = var10[var47];
               if(var5.insert("search_results", "user_id", var37) > 0L) {
                  ++var3;
               } else {
                  ++var4;
               }
            }

            if(var3 != 0) {
               ContentResolver var39 = this.getContext().getContentResolver();
               Uri var40 = USER_SEARCH_CONTENT_URI;
               var39.notifyChange(var40, (ContentObserver)null);
            }

            if(var4 > 0) {
               Object[] var41 = new Object[1];
               Integer var42 = Integer.valueOf(var4);
               var41[0] = var42;
               String var43 = String.format("Failed on %d rows", var41);
               Utils.reportSoftError("Failed insert into SEARCH_RESULTS_TABLE", var43);
            }

            var24 = var3;
         }
      }

      return var24;
   }

   public int delete(Uri var1, String var2, String[] var3) {
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var5 = URL_MATCHER.match(var1);
      int var6 = ConnectionsProvider.Selectors.CONNECTIONS_CONTENT.uriMatcherIndex();
      int var15;
      if(var5 == var6) {
         var4.beginTransaction();

         int var7;
         int var8;
         try {
            var7 = var4.delete("connections", var2, var3);
            var8 = var4.delete("friends_data", var2, var3);
            var4.setTransactionSuccessful();
         } catch (RuntimeException var25) {
            throw var25;
         } finally {
            var4.endTransaction();
         }

         if(var7 > 0 || var8 > 0) {
            ContentResolver var9 = this.getContext().getContentResolver();
            Uri var10 = CONNECTIONS_CONTENT_URI;
            var9.notifyChange(var10, (ContentObserver)null);
            ContentResolver var11 = this.getContext().getContentResolver();
            Uri var12 = FRIENDS_CONTENT_URI;
            var11.notifyChange(var12, (ContentObserver)null);
            ContentResolver var13 = this.getContext().getContentResolver();
            Uri var14 = PAGES_CONTENT_URI;
            var13.notifyChange(var14, (ContentObserver)null);
         }

         if(var7 > var8) {
            var15 = var7;
         } else {
            var15 = var8;
         }
      } else {
         int var18 = ConnectionsProvider.Selectors.SEARCH_CONTENT.uriMatcherIndex();
         if(var5 != var18) {
            String var22 = "Unknown URL " + var1;
            throw new IllegalArgumentException(var22);
         }

         int var19 = var4.delete("search_results", var2, var3);
         if(var19 > 0) {
            ContentResolver var20 = this.getContext().getContentResolver();
            Uri var21 = USER_SEARCH_CONTENT_URI;
            var20.notifyChange(var21, (ContentObserver)null);
         }

         var15 = var19;
      }

      return var15;
   }

   protected Uri friendsInsert(SQLiteDatabase var1, ContentValues var2) {
      if(!var2.containsKey("user_id")) {
         throw new IllegalArgumentException("friends inserts must contain a uid");
      } else {
         Integer var3 = Integer.valueOf(ConnectionsProvider.ConnectionType.USER.ordinal());
         var2.put("connection_type", var3);
         Tuple var4 = this.splitFriendsData(var2);
         ContentValues var5 = (ContentValues)var4.d0;
         ContentValues var6 = (ContentValues)var4.d1;
         boolean var7 = false;
         var1.beginTransaction();

         try {
            if(var5.size() != 0 && var1.insert("connections", (String)null, var5) > 0L) {
               var7 = true;
            }

            if(var6.size() != 0 && var1.insert("friends_data", (String)null, var6) > 0L) {
               var7 = true;
            }

            var1.setTransactionSuccessful();
         } catch (RuntimeException var15) {
            throw var15;
         } finally {
            var1.endTransaction();
         }

         Uri var10;
         if(var7) {
            Uri var8 = FRIEND_UID_CONTENT_URI;
            String var9 = var2.getAsString("user_id");
            var10 = Uri.withAppendedPath(var8, var9);
         } else {
            var10 = null;
         }

         return var10;
      }
   }

   public String getType(Uri var1) {
      if(URL_MATCHER.match(var1) > 0) {
         return "vnd.android.cursor.item/vnd.com.facebook.katana.provider.friends";
      } else {
         String var2 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var2);
      }
   }

   public Uri insert(Uri var1, ContentValues var2) {
      SQLiteDatabase var3 = this.mDbHelper.getWritableDatabase();
      int var4 = URL_MATCHER.match(var1);
      int var5 = ConnectionsProvider.Selectors.FRIENDS_CONTENT.uriMatcherIndex();
      Uri var6;
      Uri var11;
      if(var4 == var5) {
         var6 = this.friendsInsert(var3, var2);
         if(var6 != null) {
            ContentResolver var7 = this.getContext().getContentResolver();
            Uri var8 = CONNECTIONS_CONTENT_URI;
            var7.notifyChange(var8, (ContentObserver)null);
            ContentResolver var9 = this.getContext().getContentResolver();
            Uri var10 = FRIENDS_CONTENT_URI;
            var9.notifyChange(var10, (ContentObserver)null);
            var11 = var6;
            return var11;
         }
      } else {
         int var12 = ConnectionsProvider.Selectors.PAGES_CONTENT.uriMatcherIndex();
         if(var4 != var12) {
            int var17 = ConnectionsProvider.Selectors.SEARCH_CONTENT.uriMatcherIndex();
            if(var4 != var17) {
               String var24 = "Unknown URL " + var1;
               throw new IllegalArgumentException(var24);
            }

            long var18 = var3.insert("search_results", "display_name", var2);
            if(var18 > 0L) {
               ContentResolver var20 = this.getContext().getContentResolver();
               Uri var21 = USER_SEARCH_CONTENT_URI;
               var20.notifyChange(var21, (ContentObserver)null);
            }

            Uri var22 = USER_SEARCH_CONTENT_URI;
            String var23 = String.valueOf(var18);
            var11 = Uri.withAppendedPath(var22, var23);
            return var11;
         }

         var6 = this.pagesInsert(var3, var2);
         if(var6 != null) {
            ContentResolver var13 = this.getContext().getContentResolver();
            Uri var14 = CONNECTIONS_CONTENT_URI;
            var13.notifyChange(var14, (ContentObserver)null);
            ContentResolver var15 = this.getContext().getContentResolver();
            Uri var16 = PAGES_CONTENT_URI;
            var15.notifyChange(var16, (ContentObserver)null);
            var11 = var6;
            return var11;
         }
      }

      String var25 = "Failed to insert row into " + var1;
      Utils.reportSoftError("Single row insert failed", var25);
      var11 = null;
      return var11;
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

   protected Uri pagesInsert(SQLiteDatabase var1, ContentValues var2) {
      if(!var2.containsKey("user_id")) {
         throw new IllegalArgumentException("pages inserts must contain a id");
      } else {
         Integer var3 = var2.getAsInteger("connection_type");
         if(var3 == null) {
            throw new IllegalArgumentException("pages inserts must contain a connection type, and it must be an integer");
         } else {
            int var4 = var3.intValue();
            int var5 = ConnectionsProvider.ConnectionType.PAGE_ADMIN.ordinal();
            if(var4 != var5) {
               int var6 = var3.intValue();
               int var7 = ConnectionsProvider.ConnectionType.PAGE_FAN.ordinal();
               if(var6 != var7) {
                  throw new IllegalArgumentException("pages inserts must be PAGE_ADMIN or PAGE_FAN");
               }
            }

            Uri var10;
            if(var1.insert("connections", (String)null, var2) > 0L) {
               Uri var8 = PAGE_ID_CONTENT_URI;
               String var9 = var2.getAsString("user_id");
               var10 = Uri.withAppendedPath(var8, var9);
            } else {
               var10 = null;
            }

            return var10;
         }
      }
   }

   public Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      SQLiteQueryBuilder var6 = new SQLiteQueryBuilder();
      String var7 = null;
      UriMatcher var8 = URL_MATCHER;
      int var10 = var8.match(var1);
      int var11 = ConnectionsProvider.Selectors.CONNECTIONS_CONTENT.uriMatcherIndex();
      String var15;
      if(var10 == var11) {
         var6.setTables("connections");
         HashMap var14 = CONNECTIONS_PROJECTION_MAP;
         var6.setProjectionMap(var14);
         var15 = "display_name ASC";
      } else {
         int var26 = ConnectionsProvider.Selectors.CONNECTION_ID.uriMatcherIndex();
         if(var10 == var26) {
            var6.setTables("connections");
            StringBuilder var29 = (new StringBuilder()).append("user_id=");
            String var30 = (String)var1.getPathSegments().get(2);
            String var31 = var29.append(var30).toString();
            var6.appendWhere(var31);
            HashMap var32 = CONNECTIONS_PROJECTION_MAP;
            var6.setProjectionMap(var32);
            var15 = "display_name ASC";
         } else {
            int var33 = ConnectionsProvider.Selectors.FRIENDS_CONTENT.uriMatcherIndex();
            if(var10 == var33) {
               var6.setTables("friends");
               HashMap var36 = FRIENDS_PROJECTION_MAP;
               var6.setProjectionMap(var36);
               var15 = "display_name ASC";
            } else {
               int var37 = ConnectionsProvider.Selectors.FRIEND_UID.uriMatcherIndex();
               if(var10 == var37) {
                  var6.setTables("friends");
                  StringBuilder var40 = (new StringBuilder()).append("user_id=");
                  String var41 = (String)var1.getPathSegments().get(2);
                  String var42 = var40.append(var41).toString();
                  var6.appendWhere(var42);
                  HashMap var43 = FRIENDS_PROJECTION_MAP;
                  var6.setProjectionMap(var43);
                  var15 = "display_name ASC";
               } else {
                  int var44 = ConnectionsProvider.Selectors.FRIENDS_SEARCH.uriMatcherIndex();
                  if(var10 == var44) {
                     String var47 = (String)var1.getPathSegments().get(2);
                     var6.setTables("friends");
                     HashMap var48 = FRIENDS_PROJECTION_MAP;
                     var6.setProjectionMap(var48);
                     var6.appendWhere("display_name LIKE ");
                     StringBuilder var49 = (new StringBuilder()).append("%");
                     String var51 = var49.append(var47).append("%").toString();
                     var6.appendWhereEscapeString(var51);
                     var7 = "15";
                     var15 = "display_name ASC";
                  } else {
                     int var52 = ConnectionsProvider.Selectors.FRIENDS_BIRTHDAYS.uriMatcherIndex();
                     if(var10 == var52) {
                        HashMap var55 = FRIENDS_PROJECTION_MAP;
                        HashMap var56 = new HashMap(var55);
                        GregorianCalendar var57 = new GregorianCalendar();
                        boolean var58 = false;
                        if(var57.get(2) <= 1) {
                           int var59 = var57.get(1);
                           if(!var57.isLeapYear(var59)) {
                              var58 = true;
                           }
                        } else {
                           int var61 = var57.get(1) + 1;
                           if(!var57.isLeapYear(var61)) {
                              var58 = true;
                           }
                        }

                        if(var58) {
                           Object var60 = var56.put("normalized_birthday_day", "CASE WHEN (friends.birthday_month=2 AND            friends.birthday_day=29) THEN 28      ELSE friends.birthday_day END AS normalized_birthday_day");
                        } else {
                           Object var62 = var56.put("normalized_birthday_day", "birthday_day");
                        }

                        var6.setTables("friends");
                        var6.appendWhere("birthday_month!=-1 AND birthday_day!=-1");
                        var6.setProjectionMap(var56);
                        var15 = "display_name ASC";
                     } else {
                        int var63 = ConnectionsProvider.Selectors.PAGES_CONTENT.uriMatcherIndex();
                        if(var10 == var63) {
                           var6.setTables("connections");
                           HashMap var66 = CONNECTIONS_PROJECTION_MAP;
                           var6.setProjectionMap(var66);
                           Object[] var67 = new Object[]{"connection_type", null, null};
                           Integer var68 = Integer.valueOf(ConnectionsProvider.ConnectionType.PAGE_ADMIN.ordinal());
                           var67[1] = var68;
                           Integer var69 = Integer.valueOf(ConnectionsProvider.ConnectionType.PAGE_FAN.ordinal());
                           var67[2] = var69;
                           String var70 = String.format("(%1$s=%2$d OR %1$s=%3$d)", var67);
                           var6.appendWhere(var70);
                           var15 = "display_name ASC";
                        } else {
                           int var71 = ConnectionsProvider.Selectors.PAGE_ID.uriMatcherIndex();
                           if(var10 == var71) {
                              var6.setTables("connections");
                              Object[] var74 = new Object[5];
                              var74[0] = "user_id";
                              Object var75 = var1.getPathSegments().get(2);
                              var74[1] = var75;
                              var74[2] = "connection_type";
                              Integer var76 = Integer.valueOf(ConnectionsProvider.ConnectionType.PAGE_ADMIN.ordinal());
                              var74[3] = var76;
                              Integer var77 = Integer.valueOf(ConnectionsProvider.ConnectionType.PAGE_FAN.ordinal());
                              var74[4] = var77;
                              String var78 = String.format("%1$s=%2$s AND (%3$s=%4$d OR %3$s=%5$d)", var74);
                              var6.appendWhere(var78);
                              HashMap var79 = CONNECTIONS_PROJECTION_MAP;
                              var6.setProjectionMap(var79);
                              var15 = "display_name ASC";
                           } else {
                              int var80 = ConnectionsProvider.Selectors.PAGES_SEARCH.uriMatcherIndex();
                              if(var10 == var80) {
                                 String var83 = (String)var1.getPathSegments().get(2);
                                 var6.setTables("connections");
                                 HashMap var84 = CONNECTIONS_PROJECTION_MAP;
                                 var6.setProjectionMap(var84);
                                 Object[] var85 = new Object[]{"connection_type", null, null};
                                 Integer var86 = Integer.valueOf(ConnectionsProvider.ConnectionType.PAGE_ADMIN.ordinal());
                                 var85[1] = var86;
                                 Integer var87 = Integer.valueOf(ConnectionsProvider.ConnectionType.PAGE_FAN.ordinal());
                                 var85[2] = var87;
                                 String var88 = String.format("(%1$s=%2$d OR %1$s=%3$d)", var85);
                                 var6.appendWhere(var88);
                                 var6.appendWhere(" AND display_name LIKE ");
                                 StringBuilder var89 = (new StringBuilder()).append("%");
                                 String var91 = var89.append(var83).append("%").toString();
                                 var6.appendWhereEscapeString(var91);
                                 var7 = "15";
                                 var15 = "display_name ASC";
                              } else {
                                 int var92 = ConnectionsProvider.Selectors.SEARCH_CONTENT.uriMatcherIndex();
                                 if(var10 != var92) {
                                    StringBuilder var96 = (new StringBuilder()).append("Unknown URL ");
                                    String var98 = var96.append(var1).toString();
                                    throw new IllegalArgumentException(var98);
                                 }

                                 var6.setTables("search_results");
                                 HashMap var95 = SEARCH_RESULTS_PROJECTION_MAP;
                                 var6.setProjectionMap(var95);
                                 var15 = "_id ASC";
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      String var16;
      if(TextUtils.isEmpty(var5)) {
         var16 = var15;
      } else {
         var16 = var5;
      }

      SQLiteDatabase var17 = this.mDbHelper.getReadableDatabase();
      Cursor var21 = var6.query(var17, var2, var3, var4, (String)null, (String)null, var16, var7);
      ContentResolver var22 = this.getContext().getContentResolver();
      var21.setNotificationUri(var22, var1);
      return var21;
   }

   protected Tuple<ContentValues, ContentValues> splitFriendsData(ContentValues var1) {
      ContentValues var2 = new ContentValues(var1);
      ContentValues var3 = new ContentValues(var1);
      Iterator var4 = var1.valueSet().iterator();

      while(var4.hasNext()) {
         String var5 = (String)((Entry)var4.next()).getKey();
         if(!CONNECTIONS_PROJECTION_MAP.containsKey(var5)) {
            var2.remove(var5);
         }

         if(!FRIENDS_DATA_COLUMNS.contains(var5)) {
            var3.remove(var5);
         }
      }

      return new Tuple(var2, var3);
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      SQLiteDatabase var5 = this.mDbHelper.getWritableDatabase();
      UriMatcher var6 = URL_MATCHER;
      int var8 = var6.match(var1);
      int var9 = ConnectionsProvider.Selectors.CONNECTION_ID.uriMatcherIndex();
      String var15;
      int var28;
      Integer var34;
      if(var8 != var9) {
         int var12 = ConnectionsProvider.Selectors.PAGE_ID.uriMatcherIndex();
         if(var8 != var12) {
            int var60 = ConnectionsProvider.Selectors.FRIEND_UID.uriMatcherIndex();
            if(var8 != var60) {
               StringBuilder var114 = (new StringBuilder()).append("Unknown URL ");
               String var116 = var114.append(var1).toString();
               throw new IllegalArgumentException(var116);
            }

            var15 = (String)var1.getPathSegments().get(2);

            try {
               long var63 = Long.parseLong(var15);
            } catch (NumberFormatException var123) {
               StringBuilder var72 = (new StringBuilder()).append("uri ");
               String var74 = var72.append(var1).append(" must contain a long").toString();
               Utils.reportSoftError("NumberFormatException: URI must have a long argument", var74);
               var28 = 0;
               return var28;
            }

            String var66 = "connection_type";
            var34 = var2.getAsInteger(var66);
            if(var34 != null) {
               int var67 = var34.intValue();
               int var68 = ConnectionsProvider.ConnectionType.USER.ordinal();
               if(var67 != var68) {
                  throw new IllegalArgumentException("user updates must not change connection type USER");
               }
            }

            Tuple var77 = this.splitFriendsData(var2);
            ContentValues var78 = (ContentValues)var77.d0;
            ContentValues var79 = (ContentValues)var77.d1;
            int var80 = 0;
            int var81 = 0;
            var5.beginTransaction();

            try {
               if(var78.size() != 0) {
                  StringBuilder var82 = (new StringBuilder()).append("user_id=");
                  String var84 = var82.append(var15).toString();
                  String var86 = "connections";
                  Object var89 = null;
                  var80 = var5.update(var86, var78, var84, (String[])var89);
               }

               if(var79.size() != 0) {
                  StringBuilder var90 = (new StringBuilder()).append("user_id=");
                  String var92 = var90.append(var15).toString();
                  String var94 = "friends_data";
                  Object var97 = null;
                  var81 = var5.update(var94, var79, var92, (String[])var97);
                  if(var80 != 0 && var81 == 0) {
                     Long var98 = Long.valueOf(Long.parseLong(var15));
                     String var100 = "user_id";
                     var79.put(var100, var98);
                     String var103 = "friends_data";
                     Object var104 = null;
                     var5.insert(var103, (String)var104, var79);
                  }
               }

               var5.setTransactionSuccessful();
            } catch (RuntimeException var121) {
               throw var121;
            } finally {
               var5.endTransaction();
            }

            if(var80 > 0 || var81 > 0) {
               ContentResolver var108 = this.getContext().getContentResolver();
               Uri var109 = CONNECTIONS_CONTENT_URI;
               var108.notifyChange(var109, (ContentObserver)null);
               ContentResolver var110 = this.getContext().getContentResolver();
               Uri var111 = FRIENDS_CONTENT_URI;
               var110.notifyChange(var111, (ContentObserver)null);
            }

            if(var80 > var81) {
               var28 = var80;
            } else {
               var28 = var81;
            }

            return var28;
         }
      }

      var15 = (String)var1.getPathSegments().get(2);

      try {
         long var16 = Long.parseLong(var15);
      } catch (NumberFormatException var124) {
         StringBuilder var25 = (new StringBuilder()).append("Uri ");
         String var27 = var25.append(var1).append(" must have a long argument").toString();
         Utils.reportSoftError("NumberFormatException: URI must have a long argument", var27);
         var28 = 0;
         return var28;
      }

      int var18 = 0;
      int var19 = ConnectionsProvider.Selectors.CONNECTION_ID.uriMatcherIndex();
      if(var8 == var19) {
         String var23 = "connection_type";
         if(var2.containsKey(var23)) {
            throw new IllegalArgumentException("connection updates should not touch the connection type column");
         }
      } else {
         int var29 = ConnectionsProvider.Selectors.PAGE_ID.uriMatcherIndex();
         if(var8 == var29) {
            String var33 = "connection_type";
            var34 = var2.getAsInteger(var33);
            if(var34 != null) {
               int var35 = var34.intValue();
               int var36 = ConnectionsProvider.ConnectionType.PAGE_ADMIN.ordinal();
               if(var35 != var36) {
                  int var39 = var34.intValue();
                  int var40 = ConnectionsProvider.ConnectionType.PAGE_FAN.ordinal();
                  if(var39 != var40) {
                     throw new IllegalArgumentException("page updates must stay PAGE_ADMIN or PAGE_FAN");
                  }
               }
            }
         }
      }

      if(var2.size() != 0) {
         StringBuilder var43 = (new StringBuilder()).append("user_id=");
         String var45 = var43.append(var15).toString();
         String var47 = "connections";
         Object var50 = null;
         var18 = var5.update(var47, var2, var45, (String[])var50);
      }

      if(var18 > 0) {
         int var51 = ConnectionsProvider.Selectors.CONNECTION_ID.uriMatcherIndex();
         if(var8 == var51) {
            ContentResolver var54 = this.getContext().getContentResolver();
            Uri var55 = CONNECTIONS_CONTENT_URI;
            var54.notifyChange(var55, (ContentObserver)null);
            ContentResolver var56 = this.getContext().getContentResolver();
            Uri var57 = FRIENDS_CONTENT_URI;
            var56.notifyChange(var57, (ContentObserver)null);
         }

         ContentResolver var58 = this.getContext().getContentResolver();
         Uri var59 = PAGES_CONTENT_URI;
         var58.notifyChange(var59, (ContentObserver)null);
      }

      var28 = var18;
      return var28;
   }

   public static final class BirthdayColumns extends ConnectionsProvider.FriendColumns {

      public static final String NORMALIZED_BIRTHDAY_DAY = "normalized_birthday_day";


      public BirthdayColumns() {}
   }

   private static enum Selectors {

      // $FF: synthetic field
      private static final ConnectionsProvider.Selectors[] $VALUES;
      CONNECTIONS_CONTENT("CONNECTIONS_CONTENT", 0, "connections", "", ""),
      CONNECTION_ID,
      FRIENDS_BIRTHDAYS,
      FRIENDS_CONTENT,
      FRIENDS_SEARCH,
      FRIEND_UID,
      PAGES_CONTENT,
      PAGES_SEARCH,
      PAGE_ID,
      SEARCH_CONTENT;
      final String category;
      final String uriMatcherSuffix;
      final String uriSuffix;


      static {
         byte var0 = 1;
         CONNECTION_ID = new ConnectionsProvider.Selectors("CONNECTION_ID", var0, "connections", "/id", "/#");
         byte var1 = 2;
         FRIENDS_CONTENT = new ConnectionsProvider.Selectors("FRIENDS_CONTENT", var1, "friends", "", "");
         byte var2 = 3;
         FRIEND_UID = new ConnectionsProvider.Selectors("FRIEND_UID", var2, "friends", "/uid", "/#");
         byte var3 = 4;
         FRIENDS_SEARCH = new ConnectionsProvider.Selectors("FRIENDS_SEARCH", var3, "friends", "/search", "/*");
         FRIENDS_BIRTHDAYS = new ConnectionsProvider.Selectors("FRIENDS_BIRTHDAYS", 5, "friends", "/birthdays", "");
         PAGES_CONTENT = new ConnectionsProvider.Selectors("PAGES_CONTENT", 6, "pages", "", "");
         PAGE_ID = new ConnectionsProvider.Selectors("PAGE_ID", 7, "pages", "/id", "/#");
         PAGES_SEARCH = new ConnectionsProvider.Selectors("PAGES_SEARCH", 8, "pages", "/search", "/*");
         SEARCH_CONTENT = new ConnectionsProvider.Selectors("SEARCH_CONTENT", 9, "search_results", "", "");
         ConnectionsProvider.Selectors[] var4 = new ConnectionsProvider.Selectors[10];
         ConnectionsProvider.Selectors var5 = CONNECTIONS_CONTENT;
         var4[0] = var5;
         ConnectionsProvider.Selectors var6 = CONNECTION_ID;
         var4[1] = var6;
         ConnectionsProvider.Selectors var7 = FRIENDS_CONTENT;
         var4[2] = var7;
         ConnectionsProvider.Selectors var8 = FRIEND_UID;
         var4[3] = var8;
         ConnectionsProvider.Selectors var9 = FRIENDS_SEARCH;
         var4[4] = var9;
         ConnectionsProvider.Selectors var10 = FRIENDS_BIRTHDAYS;
         var4[5] = var10;
         ConnectionsProvider.Selectors var11 = PAGES_CONTENT;
         var4[6] = var11;
         ConnectionsProvider.Selectors var12 = PAGE_ID;
         var4[7] = var12;
         ConnectionsProvider.Selectors var13 = PAGES_SEARCH;
         var4[8] = var13;
         ConnectionsProvider.Selectors var14 = SEARCH_CONTENT;
         var4[9] = var14;
         $VALUES = var4;
      }

      private Selectors(String var1, int var2, String var3, String var4, String var5) {
         this.category = var3;
         this.uriSuffix = var4;
         this.uriMatcherSuffix = var5;
      }

      public int uriMatcherIndex() {
         return this.ordinal() + 1;
      }
   }

   public static final class SearchResultColumns extends ConnectionsProvider.ProfileColumns {

      public static final String DEFAULT_SORT_ORDER = "_id ASC";


      public SearchResultColumns() {}
   }

   public static class FriendColumns extends ConnectionsProvider.ConnectionColumns {

      public static final String BIRTHDAY_DAY = "birthday_day";
      public static final String BIRTHDAY_MONTH = "birthday_month";
      public static final String BIRTHDAY_YEAR = "birthday_year";
      public static final String CELL = "cell";
      public static final String DEFAULT_SORT_ORDER = "user_id DESC";
      public static final String EMAIL = "email";
      public static final String OTHER_PHONE = "other";
      public static final String USER_FIRST_NAME = "first_name";
      public static final String USER_LAST_NAME = "last_name";


      public FriendColumns() {}
   }

   public static enum ConnectionType {

      // $FF: synthetic field
      private static final ConnectionsProvider.ConnectionType[] $VALUES;
      PAGE_ADMIN("PAGE_ADMIN", 1),
      PAGE_FAN("PAGE_FAN", 2),
      USER("USER", 0);


      static {
         ConnectionsProvider.ConnectionType[] var0 = new ConnectionsProvider.ConnectionType[3];
         ConnectionsProvider.ConnectionType var1 = USER;
         var0[0] = var1;
         ConnectionsProvider.ConnectionType var2 = PAGE_ADMIN;
         var0[1] = var2;
         ConnectionsProvider.ConnectionType var3 = PAGE_FAN;
         var0[2] = var3;
         $VALUES = var0;
      }

      private ConnectionType(String var1, int var2) {}
   }

   public static class ConnectionColumns extends ConnectionsProvider.ProfileColumns {

      public static final String CONNECTION_TYPE = "connection_type";
      public static final String HASH = "hash";
      public static final String IMAGE = "user_image";


      public ConnectionColumns() {}
   }

   public static class ProfileColumns implements BaseColumns {

      public static final String DEFAULT_SORT_ORDER = "display_name ASC";
      public static final String DISPLAY_NAME = "display_name";
      public static final String ID = "user_id";
      public static final String IMAGE_URL = "user_image_url";


      public ProfileColumns() {}
   }
}
