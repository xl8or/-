package com.facebook.katana.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
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
import java.io.File;
import java.util.HashMap;

public class PhotosProvider extends ContentProvider {

   private static final int ALBUMS = 10;
   public static final Uri ALBUMS_AID_CONTENT_URI;
   private static final String ALBUMS_BASE_URI = "content://com.facebook.katana.provider.PhotosProvider/albums";
   public static final Uri ALBUMS_CONTENT_URI;
   private static final int ALBUMS_OWNER = 13;
   public static final Uri ALBUMS_OWNER_CONTENT_URI;
   private static final HashMap<String, String> ALBUMS_PROJECTION_MAP;
   private static final int ALBUM_AID = 12;
   private static final int ALBUM_ID = 11;
   public static final String ALBUM_SELECTION = "aid IN(?)";
   private static final String AUTHORITY = "com.facebook.katana.provider.PhotosProvider";
   private static final String CONTENT_SCHEME = "content://";
   private static final int PHOTOS = 1;
   private static final int PHOTOS_AID = 4;
   public static final Uri PHOTOS_AID_CONTENT_URI;
   private static final String PHOTOS_BASE_URI = "content://com.facebook.katana.provider.PhotosProvider/photos";
   public static final Uri PHOTOS_CONTENT_URI;
   private static final String[] PHOTOS_FILENAME_PROJECTION;
   public static final Uri PHOTOS_PID_CONTENT_URI;
   private static HashMap<String, String> PHOTOS_PROJECTION_MAP;
   private static final int PHOTO_ID = 2;
   private static final int PHOTO_PID = 3;
   public static final String PHOTO_SELECTION = "pid IN(?)";
   private static final String SQL_ALBUMS = "CREATE TABLE albums (_id INTEGER PRIMARY KEY,aid TEXT,cover_pid TEXT,cover_url TEXT,owner INT,name TEXT,created INT,modified INT,description TEXT,location TEXT,size INT,visibility TEXT,type TEXT,thumbnail BLOB,object_id INTEGER);";
   private static final String SQL_PHOTOS = "CREATE TABLE photos (_id INTEGER PRIMARY KEY,pid TEXT,aid TEXT,owner INT,src TEXT,src_big TEXT,src_small TEXT,caption TEXT,created INT,position INT,thumbnail BLOB,filename TEXT);";
   private static final String SQL_STREAM_PHOTOS = "CREATE TABLE stream_photos (_id INTEGER PRIMARY KEY,url TEXT,filename TEXT);";
   private static final String[] STREAM_FILENAME_PROJECTION;
   private static final int STREAM_PHOTOS = 20;
   private static final String STREAM_PHOTOS_BASE_URI = "content://com.facebook.katana.provider.PhotosProvider/stream_photos";
   public static final Uri STREAM_PHOTOS_CONTENT_URI;
   private static final int STREAM_PHOTOS_ID = 21;
   private static HashMap<String, String> STREAM_PHOTOS_PROJECTION_MAP;
   private static final String TABLE_ALBUMS = "albums";
   private static final String TABLE_PHOTOS = "photos";
   private static final String TABLE_STREAM_PHOTOS = "stream_photos";
   private static final UriMatcher URL_MATCHER;
   private FacebookDatabaseHelper mDbHelper;


   static {
      String[] var0 = new String[]{"filename"};
      STREAM_FILENAME_PROJECTION = var0;
      String[] var1 = new String[]{"filename"};
      PHOTOS_FILENAME_PROJECTION = var1;
      PHOTOS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/photos");
      PHOTOS_PID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/photos/pid");
      PHOTOS_AID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/photos/aid");
      ALBUMS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/albums");
      ALBUMS_AID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/albums/aid");
      ALBUMS_OWNER_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/albums/owner");
      STREAM_PHOTOS_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.PhotosProvider/stream_photos");
      URL_MATCHER = new UriMatcher(-1);
      URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "photos", 1);
      URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "photos/#", 2);
      URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "photos/pid/*", 3);
      URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "photos/aid/*", 4);
      URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "albums", 10);
      URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "albums/#", 11);
      URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "albums/aid/*", 12);
      URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "albums/owner/#", 13);
      URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "stream_photos", 20);
      URL_MATCHER.addURI("com.facebook.katana.provider.PhotosProvider", "stream_photos/#", 21);
      PHOTOS_PROJECTION_MAP = new HashMap();
      Object var2 = PHOTOS_PROJECTION_MAP.put("_id", "_id");
      Object var3 = PHOTOS_PROJECTION_MAP.put("pid", "pid");
      Object var4 = PHOTOS_PROJECTION_MAP.put("aid", "aid");
      Object var5 = PHOTOS_PROJECTION_MAP.put("owner", "owner");
      Object var6 = PHOTOS_PROJECTION_MAP.put("src", "src");
      Object var7 = PHOTOS_PROJECTION_MAP.put("src_big", "src_big");
      Object var8 = PHOTOS_PROJECTION_MAP.put("src_small", "src_small");
      Object var9 = PHOTOS_PROJECTION_MAP.put("caption", "caption");
      Object var10 = PHOTOS_PROJECTION_MAP.put("created", "created");
      Object var11 = PHOTOS_PROJECTION_MAP.put("position", "position");
      Object var12 = PHOTOS_PROJECTION_MAP.put("thumbnail", "thumbnail");
      Object var13 = PHOTOS_PROJECTION_MAP.put("filename", "filename");
      ALBUMS_PROJECTION_MAP = new HashMap();
      Object var14 = ALBUMS_PROJECTION_MAP.put("_id", "_id");
      Object var15 = ALBUMS_PROJECTION_MAP.put("aid", "aid");
      Object var16 = ALBUMS_PROJECTION_MAP.put("cover_pid", "cover_pid");
      Object var17 = ALBUMS_PROJECTION_MAP.put("cover_url", "cover_url");
      Object var18 = ALBUMS_PROJECTION_MAP.put("thumbnail", "thumbnail");
      Object var19 = ALBUMS_PROJECTION_MAP.put("owner", "owner");
      Object var20 = ALBUMS_PROJECTION_MAP.put("name", "name");
      Object var21 = ALBUMS_PROJECTION_MAP.put("created", "created");
      Object var22 = ALBUMS_PROJECTION_MAP.put("modified", "modified");
      Object var23 = ALBUMS_PROJECTION_MAP.put("description", "description");
      Object var24 = ALBUMS_PROJECTION_MAP.put("location", "location");
      Object var25 = ALBUMS_PROJECTION_MAP.put("size", "size");
      Object var26 = ALBUMS_PROJECTION_MAP.put("visibility", "visibility");
      Object var27 = ALBUMS_PROJECTION_MAP.put("type", "type");
      Object var28 = ALBUMS_PROJECTION_MAP.put("object_id", "object_id");
      STREAM_PHOTOS_PROJECTION_MAP = new HashMap();
      Object var29 = STREAM_PHOTOS_PROJECTION_MAP.put("_id", "_id");
      Object var30 = STREAM_PHOTOS_PROJECTION_MAP.put("url", "url");
      Object var31 = STREAM_PHOTOS_PROJECTION_MAP.put("filename", "filename");
   }

   public PhotosProvider() {}

   public static void clearPhotoFiles(Context var0, Uri var1) {
      ContentResolver var2 = var0.getContentResolver();
      String[] var3 = PHOTOS_FILENAME_PROJECTION;
      Object var5 = null;
      Object var6 = null;
      Cursor var7 = var2.query(var1, var3, (String)null, (String[])var5, (String)var6);
      if(var7 != null) {
         if(var7.moveToFirst()) {
            do {
               String var8 = var7.getString(0);
               if(var8 != null) {
                  boolean var9 = (new File(var8)).delete();
               }
            } while(var7.moveToNext());
         }

         var7.close();
         ContentValues var10 = new ContentValues();
         String var11 = (String)false;
         var10.put("filename", var11);
         var2.update(var1, var10, (String)null, (String[])null);
      }
   }

   private static void deletePhotoFiles(SQLiteDatabase var0, String var1, String[] var2) {
      String[] var3 = PHOTOS_FILENAME_PROJECTION;
      Object var7 = null;
      Object var8 = null;
      Cursor var9 = var0.query("photos", var3, var1, var2, (String)null, (String)var7, (String)var8);
      if(var9 != null) {
         if(var9.moveToFirst()) {
            do {
               String var10 = var9.getString(0);
               if(var10 != null) {
                  boolean var11 = (new File(var10)).delete();
               }
            } while(var9.moveToNext());
         }

         var9.close();
      }
   }

   private static void deleteStreamPhotoFiles(SQLiteDatabase var0, String var1, String[] var2) {
      String[] var3 = STREAM_FILENAME_PROJECTION;
      Object var7 = null;
      Object var8 = null;
      Cursor var9 = var0.query("stream_photos", var3, var1, var2, (String)null, (String)var7, (String)var8);
      if(var9 != null) {
         if(var9.moveToFirst()) {
            do {
               String var10 = var9.getString(0);
               boolean var11 = (new File(var10)).delete();
            } while(var9.moveToNext());
         }

         var9.close();
      }
   }

   public static String[] getTableNames() {
      String[] var0 = new String[]{"photos", "albums", "stream_photos"};
      return var0;
   }

   public static String[] getTableSQLs() {
      String[] var0 = new String[]{"CREATE TABLE photos (_id INTEGER PRIMARY KEY,pid TEXT,aid TEXT,owner INT,src TEXT,src_big TEXT,src_small TEXT,caption TEXT,created INT,position INT,thumbnail BLOB,filename TEXT);", "CREATE TABLE albums (_id INTEGER PRIMARY KEY,aid TEXT,cover_pid TEXT,cover_url TEXT,owner INT,name TEXT,created INT,modified INT,description TEXT,location TEXT,size INT,visibility TEXT,type TEXT,thumbnail BLOB,object_id INTEGER);", "CREATE TABLE stream_photos (_id INTEGER PRIMARY KEY,url TEXT,filename TEXT);"};
      return var0;
   }

   private static String setAlbumsOrderBy(String var0) {
      String var1;
      if(TextUtils.isEmpty(var0)) {
         var1 = "modified DESC";
      } else {
         var1 = var0;
      }

      return var1;
   }

   private static String setPhotosOrderBy(String var0) {
      String var1;
      if(TextUtils.isEmpty(var0)) {
         var1 = "position ASC";
      } else {
         var1 = var0;
      }

      return var1;
   }

   private static String setStreamPhotosOrderBy(String var0) {
      String var1;
      if(TextUtils.isEmpty(var0)) {
         var1 = "url DESC";
      } else {
         var1 = var0;
      }

      return var1;
   }

   public int bulkInsert(Uri var1, ContentValues[] var2) {
      int var3;
      var3 = 0;
      SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
      int var6;
      label59:
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6 = 0;

         while(true) {
            int var7 = var2.length;
            if(var6 >= var7) {
               break label59;
            }

            ContentValues var8 = var2[var6];
            if(var4.insert("photos", "pid", var8) > 0L) {
               ++var3;
            }

            ++var6;
         }
      case 4:
         var6 = 0;

         while(true) {
            int var9 = var2.length;
            if(var6 >= var9) {
               break label59;
            }

            ContentValues var10 = var2[var6];
            String var11 = (String)var1.getPathSegments().get(2);
            var10.put("aid", var11);
            ContentValues var12 = var2[var6];
            if(var4.insert("photos", "pid", var12) > 0L) {
               ++var3;
            }

            ++var6;
         }
      case 10:
         var6 = 0;

         while(true) {
            int var13 = var2.length;
            if(var6 >= var13) {
               break label59;
            }

            ContentValues var14 = var2[var6];
            if(var4.insert("albums", "aid", var14) > 0L) {
               ++var3;
            }

            ++var6;
         }
      case 13:
         var6 = 0;

         while(true) {
            int var15 = var2.length;
            if(var6 >= var15) {
               break label59;
            }

            ContentValues var16 = var2[var6];
            String var17 = (String)var1.getPathSegments().get(2);
            var16.put("owner", var17);
            ContentValues var18 = var2[var6];
            if(var4.insert("albums", "aid", var18) > 0L) {
               ++var3;
            }

            ++var6;
         }
      default:
         String var5 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var5);
      }

      if(var3 > 0) {
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
         return var3;
      } else {
         String var19 = "Failed to insert rows into " + var1;
         throw new SQLException(var19);
      }
   }

   public int delete(Uri var1, String var2, String[] var3) {
      String var4 = null;
      String var5 = null;
      SQLiteDatabase var6 = this.mDbHelper.getWritableDatabase();
      int var8;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var8 = var6.delete("photos", var2, var3);
         break;
      case 2:
         String var9 = (String)var1.getPathSegments().get(1);
         String var10 = "_id=" + var9;
         deletePhotoFiles(var6, var10, (String[])null);
         var8 = var6.delete("photos", var10, (String[])null);
         break;
      case 3:
         String var11 = (String)var1.getPathSegments().get(2);
         StringBuilder var12 = (new StringBuilder()).append("pid=");
         String var13 = DatabaseUtils.sqlEscapeString(var11);
         String var14 = var12.append(var13).toString();
         deletePhotoFiles(var6, var14, (String[])null);
         var8 = var6.delete("photos", var14, (String[])null);
         break;
      case 4:
         String var15 = (String)var1.getPathSegments().get(2);
         StringBuilder var16 = (new StringBuilder()).append("aid=");
         String var17 = DatabaseUtils.sqlEscapeString(var15);
         StringBuilder var18 = var16.append(var17);
         if(!TextUtils.isEmpty(var2)) {
            var5 = " AND (" + var2 + ')';
         } else {
            var5 = "";
         }

         String var19 = var18.append(var5).toString();
         deletePhotoFiles(var6, var19, var3);
         var8 = var6.delete("photos", var19, var3);
         break;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      default:
         String var7 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var7);
      case 10:
         var8 = var6.delete("albums", var2, var3);
         break;
      case 11:
         String var20 = (String)var1.getPathSegments().get(1);
         String var21 = "_id=" + var20;
         var8 = var6.delete("albums", var21, (String[])null);
         break;
      case 12:
         String var22 = (String)var1.getPathSegments().get(2);
         StringBuilder var23 = (new StringBuilder()).append("aid=");
         String var24 = DatabaseUtils.sqlEscapeString(var22);
         String var25 = var23.append(var24).toString();
         var8 = var6.delete("albums", var25, (String[])null);
         break;
      case 13:
         String var26 = (String)var1.getPathSegments().get(2);
         String var27 = "albums";
         StringBuilder var28 = (new StringBuilder()).append("owner=").append(var26);
         if(!TextUtils.isEmpty(var2)) {
            var4 = " AND (" + var2 + ')';
         } else {
            var4 = "";
         }

         String var29 = var28.append(var4).toString();
         var8 = var6.delete(var27, var29, var3);
         break;
      case 20:
         deleteStreamPhotoFiles(var6, var2, var3);
         var8 = var6.delete("stream_photos", var2, var3);
         break;
      case 21:
         String var30 = (String)var1.getPathSegments().get(1);
         String var31 = "_id=" + var30;
         deleteStreamPhotoFiles(var6, var31, (String[])null);
         var8 = var6.delete("stream_photos", var31, (String[])null);
      }

      this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
      return var8;
   }

   public String getType(Uri var1) {
      String var3;
      switch(URL_MATCHER.match(var1)) {
      case 1:
      case 2:
      case 3:
      case 4:
         var3 = "vnd.android.cursor.item/vnd.facebook.katana.photos";
         break;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      default:
         String var2 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var2);
      case 10:
      case 11:
      case 12:
      case 13:
         var3 = "vnd.android.cursor.item/vnd.facebook.katana.albums";
         break;
      case 20:
      case 21:
         var3 = "vnd.android.cursor.item/vnd.facebook.katana.stream_photos";
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

      label35: {
         SQLiteDatabase var4 = this.mDbHelper.getWritableDatabase();
         long var6;
         Uri var10;
         switch(URL_MATCHER.match(var1)) {
         case 1:
            var6 = var4.insert("photos", "pid", var3);
            if(var6 <= 0L) {
               break label35;
            }

            this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
            Uri var8 = PHOTOS_CONTENT_URI;
            String var9 = String.valueOf(var6);
            var10 = Uri.withAppendedPath(var8, var9);
            break;
         case 4:
            String var11 = (String)var1.getPathSegments().get(2);
            var3.put("aid", var11);
            var6 = var4.insert("photos", "pid", var3);
            if(var6 <= 0L) {
               break label35;
            }

            this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
            Uri var12 = PHOTOS_CONTENT_URI;
            String var13 = String.valueOf(var6);
            var10 = Uri.withAppendedPath(var12, var13);
            break;
         case 10:
            var6 = var4.insert("albums", "aid", var3);
            if(var6 <= 0L) {
               break label35;
            }

            this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
            Uri var14 = ALBUMS_CONTENT_URI;
            String var15 = String.valueOf(var6);
            var10 = Uri.withAppendedPath(var14, var15);
            break;
         case 13:
            String var16 = (String)var1.getPathSegments().get(2);
            var3.put("owner", var16);
            var6 = var4.insert("albums", "aid", var3);
            if(var6 <= 0L) {
               break label35;
            }

            this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
            Uri var17 = ALBUMS_CONTENT_URI;
            String var18 = String.valueOf(var6);
            var10 = Uri.withAppendedPath(var17, var18);
            break;
         case 20:
            var6 = var4.insert("stream_photos", "url", var3);
            if(var6 <= 0L) {
               break label35;
            }

            this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
            Uri var19 = STREAM_PHOTOS_CONTENT_URI;
            String var20 = String.valueOf(var6);
            var10 = Uri.withAppendedPath(var19, var20);
            break;
         default:
            String var5 = "Unknown URL " + var1;
            throw new IllegalArgumentException(var5);
         }

         return var10;
      }

      String var21 = "Failed to insert row into " + var1;
      throw new SQLException(var21);
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
      String var7 = null;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var6.setTables("photos");
         HashMap var9 = PHOTOS_PROJECTION_MAP;
         var6.setProjectionMap(var9);
         var7 = setPhotosOrderBy(var5);
         break;
      case 2:
         var6.setTables("photos");
         StringBuilder var17 = (new StringBuilder()).append("_id=");
         String var18 = (String)var1.getPathSegments().get(1);
         String var19 = var17.append(var18).toString();
         var6.appendWhere(var19);
         HashMap var20 = PHOTOS_PROJECTION_MAP;
         var6.setProjectionMap(var20);
         break;
      case 3:
         var6.setTables("photos");
         StringBuilder var21 = (new StringBuilder()).append("pid=");
         String var22 = DatabaseUtils.sqlEscapeString((String)var1.getPathSegments().get(2));
         String var23 = var21.append(var22).toString();
         var6.appendWhere(var23);
         HashMap var24 = PHOTOS_PROJECTION_MAP;
         var6.setProjectionMap(var24);
         break;
      case 4:
         var6.setTables("photos");
         StringBuilder var25 = (new StringBuilder()).append("aid=");
         String var26 = DatabaseUtils.sqlEscapeString((String)var1.getPathSegments().get(2));
         String var27 = var25.append(var26).toString();
         var6.appendWhere(var27);
         HashMap var28 = PHOTOS_PROJECTION_MAP;
         var6.setProjectionMap(var28);
         var7 = setPhotosOrderBy(var5);
         break;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      default:
         String var8 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var8);
      case 10:
         var6.setTables("albums");
         HashMap var29 = ALBUMS_PROJECTION_MAP;
         var6.setProjectionMap(var29);
         var7 = setAlbumsOrderBy(var5);
         break;
      case 11:
         var6.setTables("albums");
         StringBuilder var30 = (new StringBuilder()).append("_id=");
         String var31 = (String)var1.getPathSegments().get(1);
         String var32 = var30.append(var31).toString();
         var6.appendWhere(var32);
         HashMap var33 = ALBUMS_PROJECTION_MAP;
         var6.setProjectionMap(var33);
         break;
      case 12:
         var6.setTables("albums");
         StringBuilder var34 = (new StringBuilder()).append("aid=");
         String var35 = DatabaseUtils.sqlEscapeString((String)var1.getPathSegments().get(2));
         String var36 = var34.append(var35).toString();
         var6.appendWhere(var36);
         HashMap var37 = ALBUMS_PROJECTION_MAP;
         var6.setProjectionMap(var37);
         break;
      case 13:
         var6.setTables("albums");
         StringBuilder var38 = (new StringBuilder()).append("owner=");
         String var39 = DatabaseUtils.sqlEscapeString((String)var1.getPathSegments().get(2));
         String var40 = var38.append(var39).toString();
         var6.appendWhere(var40);
         HashMap var41 = ALBUMS_PROJECTION_MAP;
         var6.setProjectionMap(var41);
         var7 = setAlbumsOrderBy(var5);
         break;
      case 20:
         var6.setTables("stream_photos");
         HashMap var42 = STREAM_PHOTOS_PROJECTION_MAP;
         var6.setProjectionMap(var42);
         var7 = setStreamPhotosOrderBy(var5);
         break;
      case 21:
         var6.setTables("stream_photos");
         StringBuilder var43 = (new StringBuilder()).append("_id=");
         String var44 = (String)var1.getPathSegments().get(1);
         String var45 = var43.append(var44).toString();
         var6.appendWhere(var45);
      }

      SQLiteDatabase var10 = this.mDbHelper.getReadableDatabase();
      Object var14 = null;
      Cursor var15 = var6.query(var10, var2, var3, var4, (String)null, (String)var14, var7);
      ContentResolver var16 = this.getContext().getContentResolver();
      var15.setNotificationUri(var16, var1);
      return var15;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      String var5 = null;
      SQLiteDatabase var6 = this.mDbHelper.getWritableDatabase();
      int var8;
      String var16;
      switch(URL_MATCHER.match(var1)) {
      case 1:
         var8 = var6.update("photos", var2, var3, var4);
         break;
      case 2:
         String var9 = (String)var1.getPathSegments().get(1);
         String var10 = "_id=" + var9;
         var8 = var6.update("photos", var2, var10, (String[])null);
         break;
      case 3:
         String var11 = (String)var1.getPathSegments().get(2);
         StringBuilder var12 = (new StringBuilder()).append("pid=");
         String var13 = DatabaseUtils.sqlEscapeString(var11);
         String var14 = var12.append(var13).toString();
         var8 = var6.update("photos", var2, var14, (String[])null);
         break;
      case 4:
         String var15 = (String)var1.getPathSegments().get(2);
         var16 = "photos";
         StringBuilder var17 = (new StringBuilder()).append("aid=");
         String var18 = DatabaseUtils.sqlEscapeString(var15);
         StringBuilder var19 = var17.append(var18);
         if(!TextUtils.isEmpty(var3)) {
            var5 = " AND (" + var3 + ')';
         } else {
            var5 = "";
         }

         String var20 = var19.append(var5).toString();
         var8 = var6.update(var16, var2, var20, var4);
         break;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      default:
         String var7 = "Unknown URL " + var1;
         throw new IllegalArgumentException(var7);
      case 10:
         var8 = var6.update("albums", var2, var3, var4);
         break;
      case 11:
         String var21 = (String)var1.getPathSegments().get(1);
         String var22 = "_id=" + var21;
         var8 = var6.update("albums", var2, var22, (String[])null);
         break;
      case 12:
         String var23 = (String)var1.getPathSegments().get(2);
         StringBuilder var24 = (new StringBuilder()).append("aid=");
         String var25 = DatabaseUtils.sqlEscapeString(var23);
         String var26 = var24.append(var25).toString();
         var8 = var6.update("albums", var2, var26, (String[])null);
         break;
      case 13:
         String var27 = (String)var1.getPathSegments().get(2);
         var16 = "albums";
         StringBuilder var28 = (new StringBuilder()).append("owner=").append(var27);
         if(!TextUtils.isEmpty(var3)) {
            var5 = " AND (" + var3 + ')';
         } else {
            var5 = "";
         }

         String var29 = var28.append(var5).toString();
         var8 = var6.update(var16, var2, var29, var4);
         break;
      case 20:
         var8 = var6.update("stream_photos", var2, var3, var4);
         break;
      case 21:
         String var30 = (String)var1.getPathSegments().get(1);
         String var31 = "_id=" + var30;
         var8 = var6.update("stream_photos", var2, var31, (String[])null);
      }

      if(var8 > 0) {
         this.getContext().getContentResolver().notifyChange(var1, (ContentObserver)null);
      }

      return var8;
   }

   public static final class PhotoColumns implements BaseColumns {

      public static final String ALBUM_ID = "aid";
      public static final String CAPTION = "caption";
      public static final String CREATED = "created";
      public static final String DEFAULT_SORT_ORDER = "position ASC";
      public static final String FILENAME = "filename";
      public static final String OWNER = "owner";
      public static final String PHOTO_ID = "pid";
      public static final String POSITION = "position";
      public static final String SRC = "src";
      public static final String SRC_BIG = "src_big";
      public static final String SRC_SMALL = "src_small";
      public static final String THUMBNAIL = "thumbnail";


      public PhotoColumns() {}
   }

   public static final class StreamPhotoColumns implements BaseColumns {

      public static final String DEFAULT_SORT_ORDER = "url DESC";
      public static final String FILENAME = "filename";
      public static final String URL = "url";


      public StreamPhotoColumns() {}
   }

   public static final class AlbumColumns implements BaseColumns {

      public static final String ALBUM_ID = "aid";
      public static final String COVER_PHOTO_ID = "cover_pid";
      public static final String COVER_PHOTO_URL = "cover_url";
      public static final String COVER_THUMBNAIL = "thumbnail";
      public static final String CREATED = "created";
      public static final String DEFAULT_SORT_ORDER = "modified DESC";
      public static final String DESCRIPTION = "description";
      public static final String LOCATION = "location";
      public static final String MODIFIED = "modified";
      public static final String NAME = "name";
      public static final String OBJECT_ID = "object_id";
      public static final String OWNER = "owner";
      public static final String SIZE = "size";
      public static final String TYPE = "type";
      public static final String VISIBILITY = "visibility";


      public AlbumColumns() {}
   }
}
