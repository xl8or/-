package com.android.volley.toolbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import com.android.volley.Cache;
import com.android.volley.VolleyLog;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SqliteCache implements Cache {

   private static final String COLUMN_CONTENT = "content";
   private static final String COLUMN_CONTENT_SIZE = "content_size";
   private static final String COLUMN_ETAG = "etag";
   private static final String COLUMN_EXPIRE = "expire";
   private static final String COLUMN_ID = "_Id";
   private static final String COLUMN_SERVER_DATE = "server_date";
   private static final String COLUMN_SOFT_EXPIRE = "soft_expire";
   private static final String COLUMN_URL = "url";
   private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS cache (_Id INTEGER PRIMARY KEY AUTOINCREMENT, url TEXT UNIQUE NOT NULL, etag TEXT, server_date LONG, expire LONG, soft_expire LONG, content_size INTEGER, content BLOB)";
   private static final int DATABASE_DEFAULT_MAX_SIZE = 5242880;
   private static final String DATABASE_TABLE = "cache";
   private static final int DATABASE_VERSION = 3;
   private static final boolean DEBUG;
   private long mCacheSize;
   private final ReadWriteLock mCursorLock;
   private final SQLiteOpenHelper mDbHelper;
   private long mMaxSize = 5242880L;


   public SqliteCache(Context var1, String var2) {
      ReentrantReadWriteLock var3 = new ReentrantReadWriteLock();
      this.mCursorLock = var3;
      SqliteCache.CacheHelper var4 = new SqliteCache.CacheHelper(var1, var2);
      this.mDbHelper = var4;
   }

   private long getSimple(String var1) {
      boolean var9 = false;

      long var3;
      try {
         var9 = true;
         this.mCursorLock.writeLock().lock();
         SQLiteDatabase var2 = this.mDbHelper.getWritableDatabase();
         var3 = this.getSimpleNoLock(var2, var1);
         var9 = false;
      } finally {
         if(var9) {
            this.mCursorLock.writeLock().unlock();
         }
      }

      this.mCursorLock.writeLock().unlock();
      return var3;
   }

   private long getSimpleNoLock(SQLiteDatabase var1, String var2) {
      long var3 = 0L;
      String var5 = "SELECT " + var2 + " FROM " + "cache";
      Cursor var6 = var1.rawQuery(var5, (String[])null);
      boolean var11 = false;

      label36: {
         long var7;
         try {
            var11 = true;
            if(!var6.moveToFirst()) {
               var11 = false;
               break label36;
            }

            var7 = var6.getLong(0);
            var11 = false;
         } finally {
            if(var11) {
               var6.close();
            }
         }

         var3 = var7;
      }

      var6.close();
      return var3;
   }

   private long prune(int var1) {
      try {
         this.mCursorLock.writeLock().lock();
         SQLiteDatabase var2 = this.mDbHelper.getWritableDatabase();
         this.pruneNoLock(var2, var1);
      } finally {
         this.mCursorLock.writeLock().unlock();
      }

      return this.mCacheSize;
   }

   private long pruneNoLock(SQLiteDatabase var1, int var2) {
      long var3 = this.mMaxSize / 10L;
      long var5 = 9L * var3;
      if(var2 < 0) {
         var2 = 0;
      }

      long var7 = this.mCacheSize;
      long var9 = (long)var2;
      if(var7 + var9 >= var5) {
         long var11 = this.mMaxSize / 10L;
         long var13 = 7L * var11;
         int var15 = 0;
         long var16;
         if((long)var2 > var13) {
            var16 = 0L;
         } else {
            long var23 = (long)var2;
            var16 = var13 - var23;
         }

         long var18 = this.getCacheSize();

         int var22;
         for(long var20 = this.getCacheCount(); var18 > var16; var15 += var22) {
            if(var20 >= 3L && var16 > 0L) {
               StringBuilder var25 = (new StringBuilder()).append("_Id in (  SELECT _Id FROM cache ORDER BY expire ASC,_Id ASC  LIMIT ");
               long var26 = var20 / 3L;
               String var28 = var25.append(var26).append(")").toString();
               var22 = var1.delete("cache", var28, (String[])null);
               var18 = this.getSimpleNoLock(var1, "sum(content_size)");
               var20 = this.getSimpleNoLock(var1, "count(*)");
            } else {
               var22 = var1.delete("cache", (String)null, (String[])null);
               var18 = 0L;
               var20 = 0L;
            }
         }

         this.mCacheSize = var18;
         if(var15 > 0) {
            ;
         }
      }

      return this.mCacheSize;
   }

   private void put(String var1, byte[] var2, String var3, long var4, long var6, long var8) {
      if(var2 == null) {
         throw new IllegalArgumentException("Null content not allowed in cache");
      } else {
         if(var6 > 0L && var8 > var6) {
            var8 = var6;
         }

         try {
            this.mCursorLock.writeLock().lock();
            SQLiteDatabase var10 = this.mDbHelper.getWritableDatabase();
            int var11 = var2.length;
            this.pruneNoLock(var10, var11);
            ContentValues var14 = new ContentValues();
            var14.put("url", var1);
            String var15 = "etag";
            if(var3 == null) {
               var3 = "";
            }

            var14.put(var15, var3);
            Long var16 = Long.valueOf(var4);
            var14.put("server_date", var16);
            Long var17 = Long.valueOf(var6);
            var14.put("expire", var17);
            Long var18 = Long.valueOf(var8);
            var14.put("soft_expire", var18);
            var14.put("content", var2);
            Integer var19 = Integer.valueOf(var2.length);
            var14.put("content_size", var19);
            var10.insertWithOnConflict("cache", (String)null, var14, 5);
            long var22 = this.mCacheSize;
            long var24 = (long)var2.length;
            long var26 = var22 + var24;
            this.mCacheSize = var26;
         } finally {
            this.mCursorLock.writeLock().unlock();
         }

      }
   }

   private boolean renew(String var1, String var2, long var3, long var5, long var7) {
      int var9 = 0;
      boolean var20 = false;

      label86: {
         int var16;
         try {
            var20 = true;
            this.mCursorLock.writeLock().lock();
            SQLiteDatabase var10 = this.mDbHelper.getWritableDatabase();
            ContentValues var11 = new ContentValues();
            if(var2 != null) {
               var11.put("etag", var2);
            }

            if(var3 >= 0L) {
               Long var12 = Long.valueOf(var3);
               var11.put("server_date", var12);
            }

            if(var5 >= 0L) {
               Long var13 = Long.valueOf(var5);
               var11.put("expire", var13);
            }

            if(var7 >= 0L) {
               Long var14 = Long.valueOf(var7);
               var11.put("soft_expire", var14);
            }

            if(var11.size() <= 0) {
               var20 = false;
               break label86;
            }

            String[] var15 = new String[]{var1};
            var16 = var10.updateWithOnConflict("cache", var11, "url=?", var15, 4);
            var20 = false;
         } finally {
            if(var20) {
               this.mCursorLock.writeLock().unlock();
            }
         }

         var9 = var16;
      }

      this.mCursorLock.writeLock().unlock();
      boolean var17;
      if(var9 > 0) {
         var17 = true;
      } else {
         var17 = false;
      }

      return var17;
   }

   public void clear() {
      try {
         this.mCursorLock.writeLock().lock();
         SQLiteDatabase var1 = this.mDbHelper.getWritableDatabase();
         int var2 = var1.delete("cache", (String)null, (String[])null);
         var1.close();
      } finally {
         this.mCursorLock.writeLock().unlock();
      }

   }

   Cache.Entry fetch(String param1) {
      // $FF: Couldn't be decompiled
   }

   public Cache.Entry get(String var1) {
      return this.fetch(var1);
   }

   long getCacheCount() {
      return this.getSimple("count(*)");
   }

   long getCacheSize() {
      return this.getSimple("sum(content_size)");
   }

   long getMaxSize() {
      return this.mMaxSize;
   }

   public void initialize() {}

   public void invalidate(String var1, boolean var2) {
      long var3;
      if(var2) {
         var3 = 0L;
      } else {
         var3 = 65535L;
      }

      this.renew(var1, (String)null, 65535L, var3, 0L);
   }

   public void put(String var1, Cache.Entry var2) {
      byte[] var3 = var2.data;
      String var4 = var2.etag;
      long var5 = var2.serverDate;
      long var7 = var2.ttl;
      long var9 = var2.softTtl;
      this.put(var1, var3, var4, var5, var7, var9);
   }

   public void remove(String var1) {
      try {
         this.mCursorLock.writeLock().lock();
         SQLiteDatabase var2 = this.mDbHelper.getWritableDatabase();
         String[] var3 = new String[]{var1};
         var2.delete("cache", "url=?", var3);
      } finally {
         this.mCursorLock.writeLock().unlock();
      }

   }

   long reset() {
      try {
         this.mCursorLock.writeLock().lock();
         SQLiteDatabase var1 = this.mDbHelper.getWritableDatabase();
         SQLiteOpenHelper var2 = this.mDbHelper;
         int var3 = var1.getVersion();
         int var4 = var1.getVersion();
         var2.onUpgrade(var1, var3, var4);
      } finally {
         this.mCursorLock.writeLock().unlock();
      }

      this.mMaxSize = 5242880L;
      return this.mCacheSize;
   }

   long setMaxSize(long var1) {
      if(var1 > 0L) {
         this.mMaxSize = var1;
      }

      return this.prune(0);
   }

   private static class CacheHelper extends SQLiteOpenHelper {

      CacheHelper(Context var1, String var2) {
         super(var1, var2, (CursorFactory)null, 3);
      }

      public void onCreate(SQLiteDatabase var1) {
         var1.execSQL("CREATE TABLE IF NOT EXISTS cache (_Id INTEGER PRIMARY KEY AUTOINCREMENT, url TEXT UNIQUE NOT NULL, etag TEXT, server_date LONG, expire LONG, soft_expire LONG, content_size INTEGER, content BLOB)");
      }

      public void onOpen(SQLiteDatabase var1) {
         var1.setLockingEnabled((boolean)1);
      }

      public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
         Object[] var4 = new Object[2];
         Integer var5 = Integer.valueOf(var2);
         var4[0] = var5;
         Integer var6 = Integer.valueOf(var3);
         var4[1] = var6;
         VolleyLog.d("Upgrading cache database from v%d to v%d", var4);
         var1.execSQL("DROP TABLE IF EXISTS cache");
         this.onCreate(var1);
      }
   }
}
